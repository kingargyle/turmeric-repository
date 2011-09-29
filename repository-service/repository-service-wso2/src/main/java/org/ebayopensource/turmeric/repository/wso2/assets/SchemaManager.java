/*
 * Copyright (c) 2008, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *    
 *    Original code from WSO2 governance registry WsdlManager class
 *    copyright header left in tact.
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.assets;

import java.util.*;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.JaxenException;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.schema.SchemaFilter;
import org.wso2.carbon.governance.api.schema.dataobjects.Schema;
import org.wso2.carbon.governance.api.util.GovernanceConstants;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.utils.RegistryUtils;

import javax.xml.namespace.QName;

/**
 * This provides the management functionality for schema artifacts stored on the registry.
 */
public class SchemaManager {

   private static final Log log = LogFactory.getLog(SchemaManager.class);
   private Registry registry;

   /**
    * Constructor accepting an instance of the registry to use.
    * 
    * @param registry
    *           the instance of the registry.
    */
   public SchemaManager(Registry registry) {
      this.registry = registry;
   }

   /**
    * Creates a new schema artifact from the given URL.
    * 
    * @param url
    *           the given URL.
    * 
    * @return the artifact added.
    * @throws GovernanceException
    *            if the operation failed.
    */
   public Schema newSchema(String url) throws GovernanceException {
      String schemaId = UUID.randomUUID().toString();
      Schema schema = new Schema(schemaId, url);
      schema.associateRegistry(registry);
      return schema;
   }

   /**
    * Create a new Schema based on content either embedded or passed to a service.
    * 
    * @param registry
    *           the associated registry
    * @param content
    *           the wsdl as bytes
    * @return a new Schema object
    * @throws GovernanceException
    *            a GovernanceException is thrown in case the wsdl can't be created.
    */
   public Schema newSchema(Registry registry, byte[] content) throws GovernanceException {
      String schemaId = UUID.randomUUID().toString();
      Schema schema = new Schema(schemaId, (String) null);
      schema.associateRegistry(registry);
      schema.setSchemaElement(GovernanceUtils.buildOMElement(content));
      return schema;
   }

   /**
    * Adds the given schema artifact to the registry.
    * 
    * @param schema
    *           the schema artifact.
    * 
    * @throws GovernanceException
    *            if the operation failed.
    */
   public void addSchema(Schema schema) throws GovernanceException {
      boolean succeeded = false;
      try {
         registry.beginTransaction();
         schema.getUrl();
         Resource schemaResource = registry.newResource();
         schemaResource.setMediaType(GovernanceConstants.SCHEMA_MEDIA_TYPE);

         // setting the schema content
         setContent(schema, schemaResource);

         String name = schema.getAttribute(AssetConstants.TURMERIC_NAME);

         if (!name.endsWith(".xsd")) {
            name = name + ".xsd";
         }

         // OK this is a hack to get the UUID of the newly added artifact. This needs to be fixed
         // properly with the fix for UUID support at Kernel-level - Janaka.
         Resource resource = registry.get(registry.put("/" + name, schemaResource));
         schema.setId(resource.getProperty(GovernanceConstants.ARTIFACT_ID_PROP_KEY));
         schema.updatePath();
         schema.loadSchemaDetails();
         succeeded = true;
      } catch (RegistryException e) {
         String msg = "Error in adding the Schema. schema id: " + schema.getId() + ".";
         log.error(msg, e);
         throw new GovernanceException(msg, e);
      } finally {
         if (succeeded) {
            try {
               registry.commitTransaction();
            } catch (RegistryException e) {
               String msg = "Error in committing transactions. Add schema failed: schema id: " + schema.getId()
                        + ", path: " + schema.getPath() + ".";
               log.error(msg, e);
            }
         } else {
            try {
               registry.rollbackTransaction();
            } catch (RegistryException e) {
               String msg = "Error in rolling back transactions. Add schema failed: schema id: " + schema.getId()
                        + ", path: " + schema.getPath() + ".";
               log.error(msg, e);
            }
         }
      }
   }

   /**
    * Updates the given schema artifact on the registry.
    * 
    * @param schema
    *           the schema artifact.
    * 
    * @throws GovernanceException
    *            if the operation failed.
    */
   public void updateSchema(Schema schema) throws GovernanceException {
      if (schema.getSchemaElement() == null) {
         // there won't be any updates
         String msg = "Updates are only accepted if the schemeElement is available. " + "So no updates will be done. "
                  + "schema id: " + schema.getId() + ", schema path: " + schema.getPath() + ".";
         log.error(msg);
         throw new GovernanceException(msg);
      }
      boolean succeeded = false;
      try {
         registry.beginTransaction();

         // getting the old schema.
         Schema oldSchema = getSchema(schema.getId());
         if (oldSchema == null) {
            addSchema(schema);
            return;
         }
         // we are expecting only the OMElement to be different.
         Resource schemaResource = registry.newResource();
         schemaResource.setMediaType(GovernanceConstants.SCHEMA_MEDIA_TYPE);

         // setting the schema content
         setContent(schema, schemaResource);

         registry.put(oldSchema.getPath(), schemaResource);
         schema.updatePath();
         schema.loadSchemaDetails();

         succeeded = true;
      } catch (RegistryException e) {
         String msg = "Error in updating the schema, schema id: " + schema.getId() + ", schema path: "
                  + schema.getPath() + ".";
         log.error(msg, e);
         throw new GovernanceException(msg, e);
      } finally {
         if (succeeded) {
            try {
               registry.commitTransaction();
            } catch (RegistryException e) {
               String msg = "Error in committing transactions. Update schema failed: " + "schema id: " + schema.getId()
                        + ", path: " + schema.getPath() + ".";
               log.error(msg, e);
            }
         } else {
            try {
               registry.rollbackTransaction();
            } catch (RegistryException e) {
               String msg = "Error in rolling back transactions. Update schema failed: " + "schema id: "
                        + schema.getId() + ", path: " + schema.getPath() + ".";
               log.error(msg, e);
            }
         }
      }
   }

   /**
    * Fetches the given schema artifact on the registry.
    * 
    * @param schemaId
    *           the identifier of the schema artifact.
    * 
    * @return the schema artifact.
    * @throws GovernanceException
    *            if the operation failed.
    */
   public Schema getSchema(String schemaId) throws GovernanceException {
      GovernanceArtifact artifact = GovernanceUtils.retrieveGovernanceArtifactById(registry, schemaId);
      if (artifact != null && !(artifact instanceof Schema)) {
         String msg = "The artifact request is not a Schema. id: " + schemaId + ".";
         log.error(msg);
         throw new GovernanceException(msg);
      }
      return (Schema) artifact;
   }

   /**
    * Removes the given schema artifact from the registry.
    * 
    * @param schemaId
    *           the identifier of the schema artifact.
    * 
    * @throws GovernanceException
    *            if the operation failed.
    */
   public void removeSchema(String schemaId) throws GovernanceException {
      GovernanceUtils.removeArtifact(registry, schemaId);
   }

   /**
    * Sets content of the given schema artifact to the given resource on the registry.
    * 
    * @param schema
    *           the schema artifact.
    * @param schemaResource
    *           the content resource.
    * 
    * @throws GovernanceException
    *            if the operation failed.
    */
   protected void setContent(Schema schema, Resource schemaResource) throws GovernanceException {
      if (schema.getSchemaElement() != null) {
         OMElement contentElement = schema.getSchemaElement().cloneOMElement();
         try {
            for (String importType : new String[] { "import", "include", "redefine" }) {
               List<OMElement> schemaImports = GovernanceUtils.evaluateXPathToElements("//xsd:" + importType,
                        contentElement);
               for (OMElement schemaImport : schemaImports) {
                  OMAttribute location = schemaImport.getAttribute(new QName("schemaLocation"));
                  if (location != null) {
                     String path = location.getAttributeValue();
                     if (path.indexOf(";version:") > 0) {
                        location.setAttributeValue(path.substring(0, path.lastIndexOf(";version:")));
                     }
                  }
               }
            }
         } catch (JaxenException ignore) {
         }
         String schemaContent = contentElement.toString();
         try {
            schemaResource.setContent(schemaContent);
         } catch (RegistryException e) {
            String msg = "Error in setting the content from schema, schema id: " + schema.getId() + ", schema path: "
                     + schema.getPath() + ".";
            log.error(msg, e);
            throw new GovernanceException(msg, e);
         }
      }
      // and set all the attributes as properties.
      String[] attributeKeys = schema.getAttributeKeys();
      if (attributeKeys != null) {
         Properties properties = new Properties();
         for (String attributeKey : attributeKeys) {
            String[] attributeValues = schema.getAttributes(attributeKey);
            if (attributeValues != null) {
               // The list obtained from the Arrays#asList method is
               // immutable. Therefore,
               // we create a mutable object out of it before adding it as
               // a property.
               properties.put(attributeKey, new ArrayList<String>(Arrays.asList(attributeValues)));
            }
         }
         schemaResource.setProperties(properties);
      }
      schemaResource.addProperty(GovernanceConstants.ARTIFACT_ID_PROP_KEY, schema.getId());
   }

   /**
    * Finds all schema artifacts matching the given filter criteria.
    * 
    * @param criteria
    *           the filter criteria to be matched.
    * 
    * @return the schema artifacts that match.
    * @throws GovernanceException
    *            if the operation failed.
    */
   public Schema[] findSchemas(SchemaFilter criteria) throws GovernanceException {
      List<Schema> schemas = new ArrayList<Schema>();
      for (Schema schema : getAllSchemas()) {
         if (schema != null) {
            if (criteria.matches(schema)) {
               schemas.add(schema);
            }
         }
      }
      return schemas.toArray(new Schema[schemas.size()]);
   }

   /**
    * Finds all schema artifacts on the registry.
    * 
    * @return all schema artifacts on the registry.
    * @throws GovernanceException
    *            if the operation failed.
    */
   public Schema[] getAllSchemas() throws GovernanceException {
      List<String> schemaPaths = Arrays.asList(GovernanceUtils.getResultPaths(registry,
               GovernanceConstants.SCHEMA_MEDIA_TYPE));
      Collections.sort(schemaPaths, new Comparator<String>() {
         @Override
         public int compare(String o1, String o2) {
            // First order by name
            int result = RegistryUtils.getResourceName(o1).compareToIgnoreCase(RegistryUtils.getResourceName(o2));
            if (result != 0) {
               return result;
            }
            // Then order by namespace
            return o1.compareToIgnoreCase(o2);
         }
      });
      List<Schema> schemas = new ArrayList<Schema>();
      for (String schemaPath : schemaPaths) {
         GovernanceArtifact artifact = GovernanceUtils.retrieveGovernanceArtifactByPath(registry, schemaPath);
         schemas.add((Schema) artifact);
      }
      return schemas.toArray(new Schema[schemas.size()]);
   }
}