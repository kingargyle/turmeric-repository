/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.repository.wso2.assets.EndPointAsset;
import org.ebayopensource.turmeric.repository.wso2.assets.NullAsset;
import org.ebayopensource.turmeric.repository.wso2.assets.SchemaAsset;
import org.ebayopensource.turmeric.repository.wso2.assets.ServiceAsset;
import org.ebayopensource.turmeric.repository.wso2.assets.WSDLAsset;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.registry.core.Registry;

/**
 * This creates one of several asset types depending on the information passed to it
 * from the BasicAssetInfo/ArtifactInfo.  The asset can the be used during the creation/update/removal process.
 * 
 * @author dcarver
 *
 */
public class AssetFactory {
	
	private static final String ASSET_TYPE_SERVICE = "Service";
	private static final String ASSET_TYPE_WSDL = "WSDL";
	private static final String ASSET_TYPE_ENDPOINT = "Endpoint";
	private static final String ASSET_TYPE_SCHEMA = "Schema";
	private BasicAssetInfo basicInfo = null;
	private ArtifactInfo artifactInfo = null;
	private Registry registry = null;
	
	/**
	 * Asset Factory constructor
	 * @param basicInfo A basic asset from the repository service
	 * @param registry A registry implementation from WSO2
	 */
	public AssetFactory(BasicAssetInfo basicInfo, Registry registry) {
		this.registry = registry;
		this.basicInfo = basicInfo;
	}

	@Deprecated
	public AssetFactory(ArtifactInfo artifactInfo, Registry registry) {
		this.artifactInfo = artifactInfo;
		this.registry = registry;
	}
	
	public AssetFactory(AssetKey assetKey, Registry registry) {
		this.basicInfo = populateMinBasicAssetInfo(assetKey);
		this.registry = registry;
	}
	
	public AssetFactory(GovernanceArtifact gart, Registry registry) {
		this.registry = registry;
		
		try {
			AssetKey assetKey = new AssetKey();
			assetKey.setAssetId(gart.getId());
			assetKey.setAssetName(gart.getAttribute(AssetConstants.TURMERIC_NAME));
			assetKey.setVersion(gart.getAttribute(AssetConstants.TURMERIC_VERSION));
			assetKey.setType(gart.getAttribute(AssetConstants.TURMERIC_TYPE));
			basicInfo = populateMinBasicAssetInfo(assetKey);
		} catch (GovernanceException ex) {
			
		}
	}
	
	/**
	 * Creates an simple asset.
	 * @return
	 */
	public Asset createAsset() {
		Asset asset = null;
		
		if (ASSET_TYPE_SERVICE.equalsIgnoreCase(basicInfo.getAssetType())) {
			return new ServiceAsset(basicInfo, registry);
		}
		
		if (ASSET_TYPE_ENDPOINT.equalsIgnoreCase(basicInfo.getAssetType())) {
			return new EndPointAsset(basicInfo, registry);
		}
				
		return asset;
	}
	
	public Asset createArtifactAsset() {
		String category = artifactInfo.getArtifact().getArtifactCategory();
		if (ASSET_TYPE_WSDL.equalsIgnoreCase(category)) {
			try {
				return new WSDLAsset(artifactInfo, registry);
			} catch (Exception e) {
			}
		}
		
		if (ASSET_TYPE_SCHEMA.equalsIgnoreCase(category)) {
			try {
				return new SchemaAsset(artifactInfo, registry);
			} catch (Exception e) {
			}
		}
		return new NullAsset();
	}
		
	private BasicAssetInfo populateMinBasicAssetInfo(AssetKey assetKey) {
		BasicAssetInfo basicInfo = new BasicAssetInfo();
		basicInfo.setAssetName(assetKey.getAssetName());
		basicInfo.setAssetType(assetKey.getType());
		basicInfo.setVersion(assetKey.getVersion());
		basicInfo.setAssetKey(assetKey);
		return basicInfo;
	}
	

}
