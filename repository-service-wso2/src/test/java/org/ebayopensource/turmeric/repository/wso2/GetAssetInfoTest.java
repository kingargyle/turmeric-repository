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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.BasicServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetServiceResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;

public class GetAssetInfoTest extends Wso2Base {
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

        GetAssetInfoRequest request = new GetAssetInfoRequest();
        request.setAssetKey(key);
        request.setAssetType("Service");

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        GetAssetInfoResponse response = provider.getAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        checkIsRespositoryMetadataService(response);
        validateAssetInfo(response.getAssetInfo());
    }

    @Test
    public void getNonServiceAssetByLibNameAndAssetName() throws Exception {
        // TODO this test assumes that the RepositoryMetadataService has been loaded into the wso2
        // repository.

        AssetKey key = new AssetKey();
        Library lib = new Library();
        lib.setLibraryName("libraryname");
        key.setLibrary(lib);
        key.setAssetName("NonServiceAsset");

        GetAssetInfoRequest request = new GetAssetInfoRequest();
        request.setAssetKey(key);
        request.setAssetType("TestAsset");

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        GetAssetInfoResponse response = provider.getAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        validateAssetInfo(response.getAssetInfo());
        
    }

    @Test
    public void getServiceByLibIdAndAssetName() throws Exception {
        // TODO this test assumes that the RepositoryMetadataService has been loaded into the wso2
        // repository.

        AssetKey key = new AssetKey();
        Library lib = new Library();
        lib.setLibraryId("/_system/governance/services/http/www/ebay/com/marketplace/services");
        key.setLibrary(lib);
        key.setAssetName("RepositoryMetadataService");

        GetAssetInfoRequest request = new GetAssetInfoRequest();
        request.setAssetKey(key);
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        GetAssetInfoResponse response = provider.getAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        checkIsRespositoryMetadataService(response);
    }

    @Test
    public void getAssetByAssetId() throws Exception {
        // TODO this test assumes that the RepositoryMetadataService has been loaded into the wso2
        // repository.

        AssetKey key = new AssetKey();
        key.setAssetId("/_system/governance/services/http/www/ebay/com/marketplace/services/RepositoryMetadataService");
        GetAssetInfoRequest GetAssetInfoRequest = new GetAssetInfoRequest();
        GetAssetInfoRequest.setAssetKey(key);

        GetAssetInfoRequest request = new GetAssetInfoRequest();
        request.setAssetKey(key);
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        GetAssetInfoResponse response = provider.getAssetInfo(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        checkIsRespositoryMetadataService(response);
    }

    private void checkIsRespositoryMetadataService(GetAssetInfoResponse response) throws Exception {
        AssetInfo info = response.getAssetInfo();
        BasicAssetInfo basic_info = info.getBasicAssetInfo();

        System.err.println(basic_info.getAssetName() + " version=" + response.getVersion());
        assertEquals("/_system/governance/services/http/www/ebay/com/marketplace/services/RepositoryMetadataService",
                        basic_info.getAssetKey().getAssetId());
        assertEquals("RepositoryMetadataService", basic_info.getAssetKey().getAssetName());
        assertEquals("RepositoryMetadataService", basic_info.getAssetName());

        ExtendedAssetInfo extended = info.getExtendedAssetInfo();
        System.err.println("Extended Attributes: " + extended.getAttribute().size());
        for (AttributeNameValue attr : extended.getAttribute()) {
            System.err.println("Extended attr " + attr.getAttributeName() + " = "
                            + attr.getAttributeValueString());
        }

        boolean wsdl = false;
        for (ArtifactInfo ai : info.getArtifactInfo()) {

            wsdl |= "application/wsdl+xml".equals(ai.getContentType())
                            && "RepositoryMetadataService.wsdl".equals(ai.getArtifact()
                                            .getArtifactName())
                            && new String(ai.getArtifactDetail(), "utf-8").indexOf("<wsdl:") > 0;
        }
        assertTrue(wsdl);
        validateAssetInfo(info);
    }
}
