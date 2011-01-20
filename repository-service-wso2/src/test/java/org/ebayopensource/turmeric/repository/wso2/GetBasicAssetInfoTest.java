/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.GetBasicAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetBasicAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;

public class GetBasicAssetInfoTest extends Wso2Base {
    @BeforeClass
    public static void checkRepository() {
        boolean exists = false;
        try {
            exists = RSProviderUtil.getRegistry().resourceExists("/");
        }
        catch (Exception ex) {
        }

        assumeTrue(exists);
        try {
            createRequiredAssetsInWso2();
        }
        catch (Exception ex) {
            fail("failed creating neccesary assets in wso2 registry");
        }
    }

    @Test
    public void getAssetByLibNameAndAssetName() throws Exception {
        // TODO this test assumes that the RepositoryMetadataService has been loaded into the wso2
        // repository.

        AssetKey key = new AssetKey();
        Library lib = new Library();
        lib.setLibraryName("http://www.ebay.com/marketplace/services");
        key.setLibrary(lib);
        key.setAssetName("RepositoryMetadataService");

        GetBasicAssetInfoRequest request = new GetBasicAssetInfoRequest();
        request.setAssetKey(key);
        request.setAssetType("Service");

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        GetBasicAssetInfoResponse response = provider.getBasicAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        checkIsRespositoryMetadataService(response);
        validateBasicAssetInfo(response.getBasicAssetInfo());
    }

    @Test
    public void getAssetByLibIdAndAssetName() throws Exception {
        // TODO this test assumes that the RepositoryMetadataService has been loaded into the wso2
        // repository.

        AssetKey key = new AssetKey();
        Library lib = new Library();
        lib.setLibraryId("/_system/governance/services/http/www/ebay/com/marketplace/services");
        key.setLibrary(lib);
        key.setAssetName("RepositoryMetadataService");

        GetBasicAssetInfoRequest request = new GetBasicAssetInfoRequest();
        request.setAssetKey(key);
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        GetBasicAssetInfoResponse response = provider.getBasicAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        checkIsRespositoryMetadataService(response);
        validateBasicAssetInfo(response.getBasicAssetInfo());

    }

    @Test
    public void getAssetByAssetId() throws Exception {
        // TODO this test assumes that the RepositoryMetadataService has been loaded into the wso2
        // repository.

        AssetKey key = new AssetKey();
        key.setAssetId("/_system/governance/services/http/www/ebay/com/marketplace/services/RepositoryMetadataService");
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
        // TODO this test assumes that the RepositoryMetadataService has been loaded into the wso2
        // repository.

        AssetKey key = new AssetKey();
        key.setAssetId("/_system/governance/testassets/libraryname/NonServiceAsset");
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
        assertEquals("/_system/governance/services/http/www/ebay/com/marketplace/services/RepositoryMetadataService",
                        basic_info.getAssetKey().getAssetId());
        assertEquals("RepositoryMetadataService", basic_info.getAssetKey().getAssetName());
        assertEquals("RepositoryMetadataService", basic_info.getAssetName());
        validateBasicAssetInfo(response.getBasicAssetInfo());
    }

}
