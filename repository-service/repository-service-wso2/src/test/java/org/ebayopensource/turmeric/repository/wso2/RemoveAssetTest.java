/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.wso2.carbon.registry.app.RemoteRegistry;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.Library;
import org.ebayopensource.turmeric.repository.v2.services.RemoveAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.RemoveAssetResponse;

/**
 * @author mgorovoy
 * 
 */
public class RemoveAssetTest extends Wso2Base {
    private static final String[] resources = {"/_system/governance/trunk/services/http/www/domain/com/assets/RemoveAssetTest"};
    private static final String assetName = "RemoveAssetTest";
    private static final String assetDesc = "RemoveAssetTest description";
    private static final String libraryName = "http://www.domain.com/assets";


    @Before
    public void setUp() throws Exception {
    	super.setUp();
        boolean exists = false;
        try {
            RemoteRegistry wso2 = RSProviderUtil.getRegistry();
            exists = wso2.resourceExists("/");

            for (String resource : resources) {
                if (wso2.resourceExists(resource)) {
                    wso2.delete(resource);
                }
            }
        }
        catch (Exception ex) {
        }

        assertTrue(exists);
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
        CreateAssetResponse response = provider.createAsset(request);
        
        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());
        
        return response;
    }
    
    @Test
    @Ignore
    public void removeAssetTest() {
        CreateAssetResponse response = createAsset();
        
        AssetKey key = new AssetKey();
        key.setAssetId(response.getAssetKey().getAssetId());

        RemoveAssetRequest request = new RemoveAssetRequest();
        request.setAssetKey(key);
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        RemoveAssetResponse responseRemove = provider.removeAsset(request);

        assertEquals(AckValue.SUCCESS, responseRemove.getAck());
        assertEquals(null, responseRemove.getErrorMessage());
        try {
            assertFalse(RSProviderUtil.getRegistry().resourceExists(resources[0]));
        }
        catch (Exception e) {
        }
    }
}
