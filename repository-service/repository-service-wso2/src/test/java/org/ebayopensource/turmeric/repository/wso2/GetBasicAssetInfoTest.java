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
import org.junit.Test;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.GetBasicAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetBasicAssetInfoResponse;

public class GetBasicAssetInfoTest extends Wso2Base {
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
    }

    @Test
    public void getAssetByAssetId() throws Exception {

        AssetKey key = new AssetKey();
        key.setAssetId("/_system/governance/trunk/services/com/ebay/www/marketplace/services/RepositoryMetadataService");
        GetBasicAssetInfoRequest GetBasicAssetInfoRequest = new GetBasicAssetInfoRequest();
        GetBasicAssetInfoRequest.setAssetKey(key);

        GetBasicAssetInfoRequest request = new GetBasicAssetInfoRequest();
        request.setAssetKey(key);
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        GetBasicAssetInfoResponse response = provider.getBasicAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        checkIsRespositoryMetadataService(response);
    }

    @Test
    public void getNonServiceAssetByAssetId() throws Exception {

        AssetKey key = new AssetKey();
        key.setAssetId("/_system/governance/trunk/testassets/libraryname/NonServiceAsset");
        GetBasicAssetInfoRequest GetBasicAssetInfoRequest = new GetBasicAssetInfoRequest();
        GetBasicAssetInfoRequest.setAssetKey(key);

        GetBasicAssetInfoRequest request = new GetBasicAssetInfoRequest();
        request.setAssetKey(key);
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        GetBasicAssetInfoResponse response = provider.getBasicAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        validateBasicAssetInfo(response.getBasicAssetInfo());
    }

    private void checkIsRespositoryMetadataService(GetBasicAssetInfoResponse response)
                    throws Exception {

        BasicAssetInfo basic_info = response.getBasicAssetInfo();

        System.err.println(basic_info.getAssetName() + " version=" + response.getVersion());
        assertEquals("/_system/governance/trunk/services/com/ebay/www/marketplace/services/RepositoryMetadataService",
                        basic_info.getAssetKey().getAssetId());
        assertEquals("RepositoryMetadataService", basic_info.getAssetKey().getAssetName());
        assertEquals("RepositoryMetadataService", basic_info.getAssetName());
        validateBasicAssetInfo(response.getBasicAssetInfo());
    }

}
