/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.* ;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.wso2.carbon.governance.api.services.ServiceManager;
import org.wso2.carbon.governance.api.services.dataobjects.Service;
import org.wso2.carbon.registry.core.Registry;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.GetBasicAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetBasicAssetInfoResponse;
import org.ebayopensource.turmeric.repository.wso2.filters.FindServiceByNameAndNamespaceFilter;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

public class GetBasicAssetInfoTest extends Wso2Base {
	
	BasicAssetInfo basicInfo = null;
    @Override
	@Before
    public void setUp() throws Exception {
    	super.setUp();
        boolean exists = false;
        try {
            exists = RSProviderUtil.getRegistry().resourceExists("/");
        }
        catch (Exception ex) {
        }

        assertTrue(exists);
        try {
            createRequiredAssetsInWso2();
        }
        catch (Exception ex) {
            fail("failed creating neccesary assets in wso2 registry");
        }
        
        basicInfo = new BasicAssetInfo();
    	basicInfo.setNamespace("http://www.ebay.com/marketplace/services");
    	basicInfo.setAssetName("RepositoryMetadataService");
    	basicInfo.setAssetDescription("The service description");
    	basicInfo.setVersion("2.0.0");
    	basicInfo.setAssetType("Service");
        
    }
    
    
    private String findAssetId() throws Exception {
    	
    	Registry registry = RSProviderUtil.getRegistry();
    	ServiceManager serviceManager = new ServiceManager(registry);
    	Service[] services = serviceManager.findServices(new FindServiceByNameAndNamespaceFilter(basicInfo));
    	if (services.length > 1) {
    		fail("More than one service was returned.");
    	}
    	return services[0].getId();
    }

    @Test
    public void getAssetByAssetId() throws Exception {

    	String assetId = findAssetId();
    	
        AssetKey key = new AssetKey();
        key.setAssetId(assetId);
        key.setAssetName(basicInfo.getAssetName());
        key.setType(basicInfo.getAssetType());
        key.setVersion(basicInfo.getVersion());
        
        GetBasicAssetInfoRequest GetBasicAssetInfoRequest = new GetBasicAssetInfoRequest();
        GetBasicAssetInfoRequest.setAssetKey(key);

        GetBasicAssetInfoRequest request = new GetBasicAssetInfoRequest();
        request.setAssetKey(key);
        RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
        GetBasicAssetInfoResponse response = provider.getBasicAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());
    }

    @Test
    @Ignore
    public void getNonServiceAssetByAssetId() throws Exception {

        AssetKey key = new AssetKey();
        key.setAssetId("/_system/governance/trunk/testassets/libraryname/NonServiceAsset");
        GetBasicAssetInfoRequest GetBasicAssetInfoRequest = new GetBasicAssetInfoRequest();
        GetBasicAssetInfoRequest.setAssetKey(key);

        GetBasicAssetInfoRequest request = new GetBasicAssetInfoRequest();
        request.setAssetKey(key);
        RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
        GetBasicAssetInfoResponse response = provider.getBasicAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        validateBasicAssetInfo(response.getBasicAssetInfo());
    }
}
