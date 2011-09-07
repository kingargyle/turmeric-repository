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
package org.ebayopensource.turmeric.repository.wso2.assets;


import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.endpoints.dataobjects.Endpoint;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.schema.dataobjects.Schema;
import org.wso2.carbon.governance.api.util.GovernanceConstants;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Association;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.utils.RegistryUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * This represents a WSDL artifact stored on the Registry. WSDL artifacts are created as a result of
 * importing or uploading a WSDL, or when a service which has an attached WSDL is created.
 */
public class WSDLWSO2 extends GovernanceArtifact {

    private static final Log log = LogFactory.getLog(WSDLWSO2.class);

    private static final String WSDL_TARGET_NAMESPACE_ATTRIBUTE = "targetNamespace";
    private QName qname;
    private OMElement wsdlElement;
    private String url;


    /**
     * Constructor accepting resource identifier and the WSDL URL. Can be used
     * to create and save new wsdl instances.
     *
     * @param id  the resource identifier.
     * @param url the WSDL URL.
     */
    public WSDLWSO2(String id, Registry registry) {
        super(id);
    }

    public QName getQName() {
        return qname;
    }

    /**
     * Method to obtain the WSDL element of this WSDL artifact.
     *
     * @return the WSDL element.
     */
    public OMElement getWsdlElement() {
        return wsdlElement;
    }

    /**
     * Method to set the WSDL element of this WSDL artifact.
     *
     * @param wsdlElement the WSDL element.
     */
    @SuppressWarnings("unused")
    public void setWsdlElement(OMElement wsdlElement) {
        this.wsdlElement = wsdlElement;
    }

    /**
     * Attach a schema artifact to a WSDL artifact. Both the artifacts should be saved, before
     * calling this method.
     *
     * @param schema the schema to attach.
     *
     * @throws GovernanceException if the operation failed.
     */
    @SuppressWarnings("unused")
    public void attachSchema(Schema schema) throws GovernanceException {
        attach(schema);
    }

    /**
     * Detach a schema artifact from a WSDL artifact. Both the artifacts should be saved, before
     * calling this method.
     *
     * @param schemaId the identifier of the schema to detach.
     *
     * @throws GovernanceException if the operation failed.
     */
    @SuppressWarnings("unused")
    public void detachSchema(String schemaId) throws GovernanceException {
        detach(schemaId);
    }

    /**
     * Method to retrieve all schemas attached to this WSDL artifact.
     *
     * @return all schemas attached to this WSDL artifact.
     *
     * @throws GovernanceException if the operation failed.
     */
    public Schema[] getAttachedSchemas() throws GovernanceException {
        checkRegistryResourceAssociation();
        Registry registry = getAssociatedRegistry();
        String path = getPath();
        List<Schema> schemas = new ArrayList<Schema>();
        try {
            Association[] associations =
                    registry.getAssociations(path, GovernanceConstants.DEPENDS);
            for (Association association : associations) {
                String destinationPath = association.getDestinationPath();
                GovernanceArtifact governanceArtifact =
                        GovernanceUtils.retrieveGovernanceArtifactByPath(registry, destinationPath);
                if (governanceArtifact instanceof Schema) {
                    schemas.add((Schema) governanceArtifact);
                }
            }
        } catch (RegistryException e) {
            String msg =
                    "Error in getting attached schemas from the artifact at path: " + path + ".";
            log.error(msg, e);
            throw new GovernanceException(msg, e);
        }
        return schemas.toArray(new Schema[schemas.size()]);
    }

    /**
     * Attach an endpoint artifact to a WSDL artifact. Both the artifacts should be saved, before
     * calling this method.
     *
     * @param endpoint the endpoint to attach.
     *
     * @throws GovernanceException if the operation failed.
     */
    @SuppressWarnings("unused")
    public void attachEndpoint(Endpoint endpoint) throws GovernanceException {
        attach(endpoint);
    }

    /**
     * Detach an endpoint artifact from a WSDL artifact. Both the artifacts should be saved, before
     * calling this method.
     *
     * @param endpointId the identifier of the endpoint to detach.
     *
     * @throws GovernanceException if the operation failed.
     */
    @SuppressWarnings("unused")
    public void detachEndpoint(String endpointId) throws GovernanceException {
        detach(endpointId);
    }

    /**
     * Method to retrieve all endpoints attached to this WSDL artifact.
     *
     * @return all endpoints attached to this WSDL artifact.
     *
     * @throws GovernanceException if the operation failed.
     */
    public Endpoint[] getAttachedEndpoints() throws GovernanceException {
        checkRegistryResourceAssociation();
        Registry registry = getAssociatedRegistry();
        String path = getPath();
        List<Endpoint> endpoints = new ArrayList<Endpoint>();
        try {
            Association[] associations =
                    registry.getAssociations(path, GovernanceConstants.DEPENDS);
            for (Association association : associations) {
                String destinationPath = association.getDestinationPath();
                GovernanceArtifact governanceArtifact =
                        GovernanceUtils.retrieveGovernanceArtifactByPath(registry, destinationPath);
                if (governanceArtifact instanceof Endpoint) {
                    endpoints.add((Endpoint) governanceArtifact);
                }
            }
        } catch (RegistryException e) {
            String msg =
                    "Error in getting attached endpoints from the artifact at path: " + path + ".";
            log.error(msg, e);
            throw new GovernanceException(msg, e);
        }
        return endpoints.toArray(new Endpoint[endpoints.size()]);
    }
    
    public void setWsdlContents(byte[] content) {
    	
    }

    /**
     * Method to load the WSDL details into this artifact.
     *
     * @throws GovernanceException if the operation failed.
     */
    public void loadWsdlDetails() throws GovernanceException {
        //checkRegistryResourceAssociation();
        Registry registry = getAssociatedRegistry();
        String path = getPath();
        String id = getId();
        Resource resource;
        try {
            resource = registry.get(path);
            byte[] content = (byte[]) resource.getContent();
            wsdlElement = GovernanceUtils.buildOMElement(content);

        } catch (RegistryException e) {
            String msg = "Error in getting the qualified name for the artifact. artifact id: " +
                    id + ", " + "path: " + path + ".";
            log.error(msg);
            throw new GovernanceException(msg, e);
        }
        // get the target namespace.
        String fileName = RegistryUtils.getResourceName(path);
        String namespaceURI =
                wsdlElement.getAttributeValue(new QName(WSDL_TARGET_NAMESPACE_ATTRIBUTE));
        qname = new QName(namespaceURI, fileName);

        // and then iterate all the properties and add.
        Properties properties = resource.getProperties();
        if (properties != null) {
            Set keySet = properties.keySet();
            if (keySet != null) {
                for (Object keyObj : keySet) {
                    String key = (String) keyObj;
                    if (key.equals(GovernanceConstants.ARTIFACT_ID_PROP_KEY)) {
                        // it is not a property.
                        continue;
                    }
                    List values = (List) properties.get(key);
                    if (values != null) {
                        for (Object valueObj : values) {
                            String value = (String) valueObj;
                            addAttribute(key, value);
                        }
                    }
                }
            }
        }
    }
}