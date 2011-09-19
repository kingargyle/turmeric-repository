/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.assets;

import javax.xml.namespace.QName;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.filters.DuplicateServiceFilter;
import org.ebayopensource.turmeric.repository.wso2.filters.FindServiceByNameVersionFilter;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.endpoints.EndpointManager;
import org.wso2.carbon.governance.api.endpoints.dataobjects.Endpoint;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.services.ServiceManager;
import org.wso2.carbon.governance.api.services.dataobjects.Service;
import org.wso2.carbon.governance.api.wsdls.WsdlManager;
import org.wso2.carbon.governance.api.wsdls.dataobjects.Wsdl;
import org.wso2.carbon.registry.core.Registry;

/**
 * This represents a Service asset.
 * @author dcarver
 *
 */
public class EndPointAsset implements Asset {

	private BasicAssetInfo basicInfo = null;
	private EndpointManager epManager = null;
	private Endpoint endpoint = null;
	private Registry registry = null;
	
	public EndPointAsset(BasicAssetInfo bi, Registry registry) {
		this.basicInfo = bi;
		this.registry = registry;
		epManager =  new EndpointManager(registry);
	}
	
	@Override
	public boolean isNamespaceRequired() {
		return true;
	}

	@Override
	public boolean hasNamespace() {
		return basicInfo.getNamespace() != null;
	}
	
	@Override
	public String getType() {
		return basicInfo.getAssetType();
	}

	@Override
	public boolean createAsset() {
		try {
			endpoint = epManager.newEndpoint(basicInfo.getNamespace());
			endpoint.setAttribute(AssetConstants.TURMERIC_NAME, basicInfo.getAssetName());
			endpoint.setAttribute(AssetConstants.TURMERIC_DESCRIPTION,
					basicInfo.getAssetDescription());
			endpoint.setAttribute(AssetConstants.TURMERIC_VERSION, basicInfo.getVersion());
			endpoint.setAttribute(AssetConstants.TURMERIC_NAMESPACE, basicInfo.getNamespace());
			endpoint.setAttribute(AssetConstants.TURMERIC_OWNER, basicInfo.getGroupName());
			endpoint.setAttribute(AssetConstants.TURMERIC_LOCK, "false");
		} catch (GovernanceException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean hasName() {
		return basicInfo.getAssetName() != null && basicInfo.getAssetName().length() > 0;
	}
	
	@Override
	public boolean duplicatesAllowed() {
		return false;
	}
	
	@Override
	public boolean hasDuplicates() {
		Endpoint ep;
		try {
			ep = epManager.getEndpointByUrl(basicInfo.getNamespace());

		} catch (GovernanceException e) {
			return false;
		}
		return ep != null;
	}
	
	@Override
	public boolean addAsset() {
		try {
			epManager.addEndpoint(endpoint);
		} catch (GovernanceException e) {
			return false;
		}
		
		if (basicInfo.getAssetKey() == null) {
			basicInfo.setAssetKey(new AssetKey());
		}
		basicInfo.getAssetKey().setAssetId(endpoint.getId());

		return true;
	}

	@Override
	public String getId() {
		return endpoint.getId();
	}
	
	@Override
	public GovernanceArtifact addArtifact(ArtifactInfo artifact) {
		return null;
	}

	@Override
	public GovernanceArtifact getGovernanceArtifact() {
		return endpoint;
	}

	@Override
	public boolean exists() {
		if (basicInfo.getAssetKey().getAssetId() != null) {
			if (findByID() != null){
				return true;
			}
		}
		
		Endpoint endpoint = findEndpoint();
		if (endpoint == null) {
			return false;
		}
		return endpoint != null;
	}

	private Endpoint findByID() {
		Endpoint ep = null;
		try {
			ep = epManager.getEndpoint(basicInfo.getAssetKey().getAssetId());
		} catch (GovernanceException e) {
		}
		return ep;
	}

	@Override
	public void findAsset() {
		AssetKey assetKey = null;
		if (basicInfo.getAssetKey() != null) {
			assetKey = basicInfo.getAssetKey();
		}
		
		if (assetKey.getAssetId() != null) {
			try {
				EndpointManager manager = new EndpointManager(RSProviderUtil.getRegistry());
				endpoint = manager.getEndpoint(assetKey.getAssetId());
				return;
			} catch (GovernanceException e) {
			}
		}
		
		endpoint = findEndpoint();
	}

	private Endpoint findEndpoint() {
		Endpoint ep = null;
		try {
			ep = epManager.getEndpointByUrl(basicInfo.getNamespace());
			if (ep != null) {
				return ep; 
			}
		} catch (GovernanceException e) {
		}
		return ep;
	}

	@Override
	public void lockAsset() {
		try {
			endpoint.setAttribute(AssetConstants.TURMERIC_LOCK, "true");
		} catch (GovernanceException e) {
		}
		
	}

	@Override
	public void unlock() {
		try {
			endpoint.setAttribute(AssetConstants.TURMERIC_LOCK, "false");
		} catch (GovernanceException e) {
		}
		
	}

	@Override
	public boolean isLocked() {
		try {
			String lock = endpoint.getAttribute(AssetConstants.TURMERIC_LOCK);
			if (lock == null || lock.equals("false")) {
				return false;
			}
		} catch (GovernanceException e) {
		}
		return true;
	}
	
	@Override
	public boolean save() {
		try {
			epManager.updateEndpoint(endpoint);
		} catch (GovernanceException e) {
			return false;
		}
		return true;
	}
	
}
