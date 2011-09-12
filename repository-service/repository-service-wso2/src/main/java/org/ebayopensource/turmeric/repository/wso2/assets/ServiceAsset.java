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
public class ServiceAsset implements Asset {

	private BasicAssetInfo basicInfo = null;
	private ServiceManager serviceManager = null;
	private Service service = null;
	private Registry registry = null;
	
	public ServiceAsset(BasicAssetInfo bi, Registry registry) {
		this.basicInfo = bi;
		this.registry = registry;
		serviceManager =  new ServiceManager(registry);
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
			service = serviceManager.newService(new QName(
					basicInfo.getNamespace(), basicInfo.getAssetName()));
			service.setAttribute(AssetConstants.TURMERIC_NAME, basicInfo.getAssetName());
			service.setAttribute(AssetConstants.TURMERIC_DESCRIPTION,
					basicInfo.getAssetDescription());
			service.setAttribute(AssetConstants.TURMERIC_VERSION, basicInfo.getVersion());
			service.setAttribute(AssetConstants.TURMERIC_NAMESPACE, basicInfo.getNamespace());
			service.setAttribute(AssetConstants.TURMERIC_OWNER, basicInfo.getGroupName());
			service.setAttribute(AssetConstants.TURMERIC_LOCK, "false");
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
		Service[] services;
		try {
			services = serviceManager
					.findServices(new DuplicateServiceFilter(basicInfo));
		} catch (GovernanceException e) {
			return false;
		}
		return services.length > 0;
	}
	
	@Override
	public boolean addAsset() {
		try {
			serviceManager.addService(service);
		} catch (GovernanceException e) {
			return false;
		}
		
		if (basicInfo.getAssetKey() == null) {
			basicInfo.setAssetKey(new AssetKey());
		}
		basicInfo.getAssetKey().setAssetId(service.getId());

		return true;
	}

	@Override
	public String getId() {
		return service.getId();
	}
	
	@Override
	public GovernanceArtifact addArtifact(ArtifactInfo artifact) {
		AssetFactory factory = new AssetFactory(artifact, registry);
		Asset asset = factory.createArtifactAsset();
		
		asset.createAsset();
		asset.addAsset();		
		if (asset.getGovernanceArtifact() != null) {
			if ("WSDL".equalsIgnoreCase(asset.getType())) {
				try {
					String artifactId = asset.getId();
					WsdlManager manager = new WsdlManager(registry);
					Wsdl wsdl = manager.getWsdl(artifactId);
					service.attachWSDL(wsdl);
					return asset.getGovernanceArtifact();
				} catch (GovernanceException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public GovernanceArtifact getGovernanceArtifact() {
		return service;
	}

	@Override
	public boolean exists() {
		if (basicInfo.getAssetKey().getAssetId() != null) {
			if (findByID() != null){
				return true;
			}
		}
		
		Service[] services = findService();
		if (services == null) {
			return false;
		}
		return services.length > 0;
	}

	private Service findByID() {
		Service s = null;
		try {
			s = serviceManager.getService(basicInfo.getAssetKey().getAssetId());
		} catch (GovernanceException e) {
		}
		return s;
	}

	@Override
	public void findAsset() {
		AssetKey assetKey = null;
		if (basicInfo.getAssetKey() != null) {
			assetKey = basicInfo.getAssetKey();
		}
		
		if (assetKey.getAssetId() != null) {
			try {
				ServiceManager manager = new ServiceManager(RSProviderUtil.getRegistry());
				service = manager.getService(assetKey.getAssetId());
				return;
			} catch (GovernanceException e) {
			}
		}
		
		findService();
	}

	private Service[] findService() {
		Service[] services = null;
		try {
			services = serviceManager.findServices(new FindServiceByNameVersionFilter(basicInfo));
			if (services.length > 0) {
				service = services[0]; 
			}
		} catch (GovernanceException e) {
		}
		return services;
	}

	@Override
	public void lockAsset() {
		try {
			service.setAttribute(AssetConstants.TURMERIC_LOCK, "true");
		} catch (GovernanceException e) {
		}
		
	}

	@Override
	public void unlock() {
		try {
			service.setAttribute(AssetConstants.TURMERIC_LOCK, "false");
		} catch (GovernanceException e) {
		}
		
	}

	@Override
	public boolean isLocked() {
		try {
			String lock = service.getAttribute(AssetConstants.TURMERIC_LOCK);
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
			serviceManager.updateService(service);
	    	ServiceManager serviceManager = new ServiceManager(registry);
	    	Service updatedService = serviceManager.getService(service.getId());
	    	service = updatedService;
		} catch (GovernanceException e) {
			return false;
		}
		return true;
	}
	
}
