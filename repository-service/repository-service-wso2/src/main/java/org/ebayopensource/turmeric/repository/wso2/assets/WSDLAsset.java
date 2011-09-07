/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.assets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.jaxen.JaxenException;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.util.GovernanceConstants;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.governance.api.wsdls.WsdlManager;
import org.wso2.carbon.governance.api.wsdls.dataobjects.Wsdl;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.utils.RegistryUtils;

public class WSDLAsset implements Asset {

	private ArtifactInfo artifactInfo = null;
	private WSDLManager wsdlManager = null;
	private Wsdl wsdl;
	private Registry registry = null;

	
	public WSDLAsset(ArtifactInfo ai, Registry registry) throws Exception{
		artifactInfo = ai;
		this.registry = registry;
		wsdlManager = new WSDLManager(registry);
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
		return "WSDL";
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
		return false;
	}

	@Override
	public boolean createAsset() {
		String wsdlId = UUID.randomUUID().toString();
		try {
			wsdl = wsdlManager.newWsdl(registry, artifactInfo.getArtifactDetail()); 
			wsdl.setAttribute(WSDLConstants.WSDL_NAME, artifactInfo.getArtifact().getArtifactName());
			wsdl.setAttribute(WSDLConstants.WSDL_DISPLAY_NAME, artifactInfo.getArtifact().getArtifactDisplayName());
			wsdl.setAttribute(WSDLConstants.WSDL_CATEGORY,artifactInfo.getArtifact().getArtifactCategory());
			
		} catch (Exception ex) {
			try {
				registry.rollbackTransaction();
			} catch (RegistryException e) {
				
			}
			return false;
		} finally {
			try {
				registry.commitTransaction();
			} catch (RegistryException e) {
				return false;
			}
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
			wsdlManager.addWsdl(wsdl);
			wsdl = wsdlManager.getWsdl(wsdl.getId());
		} catch (GovernanceException e) {
			return false;
		}
		return true;
	}

	@Override
	public String getId() {
		return wsdl.getId();
	}

	@Override
	public GovernanceArtifact addArtifact(ArtifactInfo artifact) {
		return null;
	}
	
    /**
     * Sets content of the given WSDL artifact to the given resource on the
     * registry.
     * 
     * @param wsdl the WSDL artifact.
     * @param wsdlResource the content resource.
     * 
     * @throws GovernanceException if the operation failed.
     */
    protected void setContent(Wsdl wsdl, Resource wsdlResource) throws GovernanceException {
        if (wsdl.getWsdlElement() != null) {
            OMElement contentElement = wsdl.getWsdlElement().cloneOMElement();
            try {
                for (String importType : new String[] {"import", "include"}) {
                    List<OMElement> wsdlImports =
                            GovernanceUtils.evaluateXPathToElements("//wsdl:" + importType,
                                    contentElement);
                    for (OMElement wsdlImport : wsdlImports) {
                        OMAttribute location = wsdlImport.getAttribute(new QName("location"));
                        if (location != null) {
                            String path = location.getAttributeValue();
                            if (path.indexOf(";version:") > 0) {
                                location.setAttributeValue(path.substring(0,
                                        path.lastIndexOf(";version:")));
                            }
                        }
                    }
                }
                for (String importType : new String[] {"import", "include", "redefine"}) {
                    List<OMElement> schemaImports =
                            GovernanceUtils.evaluateXPathToElements("//xsd:" + importType,
                                    contentElement);
                    for (OMElement schemaImport : schemaImports) {
                        OMAttribute location = schemaImport.getAttribute(
                                new QName("schemaLocation"));
                        if (location != null) {
                            String path = location.getAttributeValue();
                            if (path.indexOf(";version:") > 0) {
                                location.setAttributeValue(path.substring(0,
                                        path.lastIndexOf(";version:")));
                            }
                        }
                    }
                }
            } catch (JaxenException ignore) { }
            String wsdlContent = contentElement.toString();
            try {
                wsdlResource.setContent(wsdlContent);
            } catch (RegistryException e) {
                String msg =
                        "Error in setting the content from wsdl, wsdl id: " + wsdl.getId() +
                                ", wsdl path: " + wsdl.getPath() + ".";
                throw new GovernanceException(msg, e);
            }
        }
        // and set all the attributes as properties.
        String[] attributeKeys = wsdl.getAttributeKeys();
        if (attributeKeys != null) {
            Properties properties = new Properties();
            for (String attributeKey : attributeKeys) {
                String[] attributeValues = wsdl.getAttributes(attributeKey);
                if (attributeValues != null) {
                    // The list obtained from the Arrays#asList method is
                    // immutable. Therefore,
                    // we create a mutable object out of it before adding it as
                    // a property.
                    properties.put(attributeKey,
                            new ArrayList<String>(Arrays.asList(attributeValues)));
                }
            }
            wsdlResource.setProperties(properties);
        }
        wsdlResource.addProperty(GovernanceConstants.ARTIFACT_ID_PROP_KEY, wsdl.getId());
    }

	@Override
	public GovernanceArtifact getGovernanceArtifact() {
		return wsdl;
	}

}
