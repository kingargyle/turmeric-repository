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
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetResponse;

/**
 * @author mgorovoy
 * 
 */
public class UpdateAssetTest extends Wso2Base {
    // First resource path must be the primary resource created by the test
    // in order for the assumption checks to work correctly.
    private static final String[] resources = {
            "/_system/governance/trunk/services/http/www/ebay/com/marketplace/services/UpdateAssetTest",
            "/_system/governance/trunk/services/http/www/ebay/com/marketplace/services/UpdatedUpdateAssetTest", };

    private static final String assetName = "UpdateAssetTest";
    private static final String assetDesc = "UpdateAssetTest description";
    private static final String libraryName = "http://www.ebay.com/marketplace/services";

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

    private void createAsset(BasicAssetInfo basicInfo) {
        CreateAssetRequest request = new CreateAssetRequest();
        request.setBasicAssetInfo(basicInfo);

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        CreateAssetResponse response = provider.createAsset(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());
    }

    public void updateAsset(String newName, String newLib, String newDesc) throws Exception {
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

        createAsset(basicInfo);

        basicInfo.setAssetName(newName);
        basicInfo.setAssetDescription(newDesc);

        UpdateAssetRequest request = new UpdateAssetRequest();
        request.setBasicAssetInfo(basicInfo);

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        UpdateAssetResponse response = provider.updateAsset(request);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());
    }

    @Test
    public void updateTest() throws Exception {
        boolean exists = false;
        try {
            RemoteRegistry wso2 = RSProviderUtil.getRegistry();
            for (String resource : resources) {
                if (wso2.resourceExists(resource)) {
                    wso2.delete(resource);
                }
            }
            exists = wso2.resourceExists(resources[0]);
        }
        catch (RegistryException e) {
        }
        assumeTrue(!exists);

        updateAsset(assetName, libraryName, "Updated "+assetDesc);
    }

    @Test
    public void renameTest() throws Exception {
        boolean exists = false;
        try {
            RemoteRegistry wso2 = RSProviderUtil.getRegistry();
            for (String resource : resources) {
                if (wso2.resourceExists(resource)) {
                    wso2.delete(resource);
                }
            }
            exists = wso2.resourceExists(resources[0]);
        }
        catch (RegistryException e) {
        }
        assumeTrue(!exists);

        updateAsset("Updated"+assetName, libraryName, assetDesc);
    }
}
