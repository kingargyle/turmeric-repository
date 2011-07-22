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
import static org.junit.Assume.assumeTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.Library;

/**
 * @author mgorovoy
 * 
 */
public class CreateAssetTest extends Wso2Base {
    private static final String resourcePath = "/_system/governance/trunk/services/com/domain/www/assets/CreateAssetTest";
    private static final String assetName = "CreateAssetTest";
    private static final String assetDesc = "CreateAssetTest description";
    private static final String libraryName = "http://www.domain.com/assets";

    @BeforeClass
    public static void checkRepository() {
        boolean exists = false;
        try {
            exists = RSProviderUtil.getRegistry().resourceExists("/");
        }
        catch (Exception ex) {
        }

        assumeTrue(exists);
    }

    private CreateAssetResponse createAsset() {
        AssetKey key = new AssetKey();
        Library lib = new Library();
        lib.setLibraryName(libraryName);
        key.setLibrary(lib);
        key.setAssetName(assetName);

        BasicAssetInfo basicInfo = new BasicAssetInfo();
        basicInfo.setAssetKey(key);
        basicInfo.setAssetName(assetName);
        basicInfo.setAssetDescription(assetDesc);
        basicInfo.setAssetType("Service");

        CreateAssetRequest request = new CreateAssetRequest();
        request.setBasicAssetInfo(basicInfo);

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        return provider.createAsset(request);
    }

    @Test
    public void createTest() {
        boolean exists = false;
        try {
            RemoteRegistry wso2 = RSProviderUtil.getRegistry();
            if (wso2.resourceExists(resourcePath)) {
                wso2.delete(resourcePath);
            }
            exists = wso2.resourceExists(resourcePath);
        }
        catch (RegistryException e) {
        }
        assumeTrue(!exists);

        CreateAssetResponse response = createAsset();

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());
        validateAssetKey(response.getAssetKey());
    }

    @Test
    public void createDuplicateTest() {
        boolean exists = false;
        try {
            RemoteRegistry wso2 = RSProviderUtil.getRegistry();
            exists = wso2.resourceExists(resourcePath);
        }
        catch (RegistryException e) {
        }
        assumeTrue(exists);

        CreateAssetResponse response = createAsset();

        assertEquals(AckValue.FAILURE, response.getAck());
    }
}
