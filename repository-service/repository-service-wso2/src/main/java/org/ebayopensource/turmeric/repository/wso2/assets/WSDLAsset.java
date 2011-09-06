/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.assets;

import java.util.UUID;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.util.GovernanceConstants;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.governance.api.wsdls.WsdlManager;
import org.wso2.carbon.governance.api.wsdls.dataobjects.Wsdl;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.utils.RegistryUtils;

public class WSDLAsset implements Asset {

	private ArtifactInfo artifactInfo = null;
	private WsdlManager wsdlManager = null;
	private Wsdl wsdl;
	private Registry registry = null;

	
	public WSDLAsset(ArtifactInfo ai, Registry registry) throws Exception{
		artifactInfo = ai;
		this.registry = registry;
		wsdlManager = new WsdlManager(registry);
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
		try {
		//	OMElement element =  GovernanceUtils.buildOMElement(artifactInfo.getArtifactDetail());
		//	wsdl.setWsdlElement(element);
		//	wsdl.setAttribute(WSDLConstants.WSDL_NAME, artifactInfo.getArtifact().getArtifactName());
		//	wsdl.setAttribute(WSDLConstants.WSDL_DISPLAY_NAME, artifactInfo.getArtifact().getArtifactDisplayName());
		//	wsdl.setAttribute(WSDLConstants.WSDL_CATEGORY,artifactInfo.getArtifact().getArtifactCategory());
		//	wsdlManager.addWsdl(wsdl);
			Resource wsdlResource = registry.newResource();
            wsdlResource.setMediaType(GovernanceConstants.WSDL_MEDIA_TYPE);

            // setting the wsdl content
            wsdlResource.setContent(artifactInfo.getArtifactDetail().toString());
    		wsdlResource.setProperty(WSDLConstants.WSDL_NAME, artifactInfo.getArtifact().getArtifactName());
    		wsdlResource.setProperty(WSDLConstants.WSDL_DISPLAY_NAME, artifactInfo.getArtifact().getArtifactDisplayName());
    		wsdlResource.setProperty(WSDLConstants.WSDL_CATEGORY,artifactInfo.getArtifact().getArtifactCategory());

            // set the UUID for the WSDL resource
    		String wsdlId = UUID.randomUUID().toString();
            wsdlResource.addProperty(GovernanceConstants.ARTIFACT_ID_PROP_KEY, wsdlId);

            //String path = GovernanceUtils.derivePathFromQName(new QName(artifactInfo.getArtifact().getTargetNamespace(), artifactInfo.getArtifact().getArtifactName()));
            String path = "/trunk/" + artifactInfo.getArtifact().getArtifactName();
            registry.put(path, wsdlResource);
            
            wsdl = wsdlManager.getWsdl(wsdlId);
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
		return false;
	}

	@Override
	public String getId() {
		return wsdl.getId();
	}

	@Override
	public GovernanceArtifact addArtifact(ArtifactInfo artifact) {
		return null;
	}

}
