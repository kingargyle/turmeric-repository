/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.assets;

import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.filters.DuplicateSchemaFilter;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.schema.dataobjects.Schema;
import org.wso2.carbon.registry.core.Registry;

/**
 * Represents a Schema asset.
 * 
 * @author dcarver
 * 
 */
public class SchemaAsset implements Asset {

   private ArtifactInfo artifactInfo = null;
   private SchemaManager schemaManager = null;
   private Schema schema;
   private Registry registry = null;

   /**
    * Constructor for a Schema created from an ArtifactInfo object.
    * 
    * @param ai
    *           the artifactInfo object
    * @param registry
    *           the associated registry
    * @throws Exception
    *            any exception that could be thrown
    */
   public SchemaAsset(ArtifactInfo ai, Registry registry) throws Exception {
      artifactInfo = ai;
      this.registry = registry;
      schemaManager = new SchemaManager(registry);
   }

   @Override
   public boolean isNamespaceRequired() {
      return true;
   }

   @Override
   public boolean hasNamespace() {
      return true;
   }

   @Override
   public String getType() {
      return "Schema";
   }

   @Override
   public boolean hasName() {
      return artifactInfo.getArtifact().getArtifactName() != null;
   }

   @Override
   public boolean duplicatesAllowed() {
      return false;
   }

   @Override
   public boolean hasDuplicates() {
      try {
         Schema[] schemas = schemaManager.findSchemas(new DuplicateSchemaFilter(artifactInfo));

         return schemas.length > 0;
      } catch (GovernanceException ex) {

      }
      return false;
   }

   @Override
   public boolean createAsset() {
      try {
         schema = schemaManager.newSchema(registry, artifactInfo.getArtifactDetail());
         schema.setAttribute(AssetConstants.TURMERIC_NAME, artifactInfo.getArtifact().getArtifactName());
         schema.setAttribute(AssetConstants.TURMERIC_DISPLAY_NAME, artifactInfo.getArtifact().getArtifactDisplayName());
         schema.setAttribute(AssetConstants.TURMERIC_ARTIFACT_CATEGORY, artifactInfo.getArtifact()
                  .getArtifactCategory());

      } catch (Exception ex) {
         return false;
      }

      return true;
   }

   /**
    * {@inheritDoc}
    * 
    * Only type of assets that can be added to a WSDL are Schemas and Endpoints.
    */
   @Override
   public boolean addAsset() {
      try {
         schemaManager.addSchema(schema);
         schema = schemaManager.getSchema(schema.getId());
         artifactInfo.getArtifact().setArtifactIdentifier(schema.getId());
      } catch (GovernanceException e) {
         e.printStackTrace();
         return false;
      }
      return true;
   }

   @Override
   public String getId() {
      return schema.getId();
   }

   @Override
   public GovernanceArtifact addArtifact(ArtifactInfo artifact) {
      return null;
   }

   @Override
   public GovernanceArtifact getGovernanceArtifact() {
      return schema;
   }

   @Override
   public boolean exists() {
      String id = artifactInfo.getArtifact().getArtifactIdentifier();
      if (id != null) {
         try {
            schema = schemaManager.getSchema(id);
            return true;
         } catch (GovernanceException ex) {
            return false;
         }
      }
      return hasDuplicates();
   }

   @Override
   public void findAsset() {

   }

   @Override
   public void lockAsset() {
      try {
         String lock = schema.getAttribute(AssetConstants.TURMERIC_LOCK);
         if (lock == null) {
            schema.addAttribute(AssetConstants.TURMERIC_LOCK, "true");
            return;
         }
         schema.setAttribute(AssetConstants.TURMERIC_LOCK, "true");
      } catch (GovernanceException e) {
      }

   }

   @Override
   public void unlock() {
      try {
         String lock = schema.getAttribute(AssetConstants.TURMERIC_LOCK);
         if (lock == null) {
            schema.addAttribute(AssetConstants.TURMERIC_LOCK, "false");
            return;
         }
         schema.setAttribute(AssetConstants.TURMERIC_LOCK, "false");
      } catch (GovernanceException e) {
      }

   }

   @Override
   public boolean isLocked() {
      String lock = null;
      try {
         lock = schema.getAttribute(AssetConstants.TURMERIC_LOCK);
         if (lock == null) {
            return false;
         }
      } catch (GovernanceException ex) {
         return false;
      }
      return lock.equals("true");
   }

   @Override
   public boolean save() {
      try {
         schemaManager.updateSchema(schema);
      } catch (GovernanceException ex) {
         return false;
      }
      return true;
   }

}
