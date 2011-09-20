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

import java.util.UUID;

import org.junit.Test;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.*;

/**
 * @author mgorovoy
 * 
 */
public class RemoveAssetTest extends Wso2Base {
    private static final String assetName = "RemoveAssetTest";
    private static final String assetDesc = "RemoveAssetTest description";

    private CreateAssetResponse createAsset() {
        AssetKey key = new AssetKey();
        key.setAssetName(assetName);
        key.setType("Service");
        key.setVersion("1.0.0");

        BasicAssetInfo basicInfo = new BasicAssetInfo();
        basicInfo.setAssetKey(key);
        basicInfo.setAssetName(assetName);
        basicInfo.setAssetDescription(assetDesc);
        basicInfo.setAssetType(key.getType());
        basicInfo.setVersion(key.getVersion());
        basicInfo.setNamespace("http://www.example.org");

        CreateAssetRequest request = new CreateAssetRequest();
        request.setBasicAssetInfo(basicInfo);

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        CreateAssetResponse response = provider.createAsset(request);
        
        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());
        
        return response;
    }
    
    @Test
    public void removeAssetTest() throws Exception {
        CreateAssetResponse response = createAsset();
        
        AssetKey key = new AssetKey();
        key.setAssetId(response.getAssetKey().getAssetId());

        RemoveAssetRequest request = new RemoveAssetRequest();
        request.setAssetKey(key);
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        RemoveAssetResponse responseRemove = provider.removeAsset(request);

        assertEquals(AckValue.SUCCESS, responseRemove.getAck());
        assertEquals(null, responseRemove.getErrorMessage());
        
        String path = GovernanceUtils.getArtifactPath(RSProviderUtil.getRegistry(), response.getAssetKey().getAssetId());
        assertNull("Path was returned, meaing id was found", path);
    }
    
    @Test
    public void removeNonExistantAsset() throws Exception {
        RemoveAssetRequest request = new RemoveAssetRequest();
        AssetKey assetKey = new AssetKey();
        assetKey.setAssetId(UUID.randomUUID().toString());
        request.setAssetKey(assetKey);
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        RemoveAssetResponse responseRemove = provider.removeAsset(request);

        assertEquals(AckValue.FAILURE, responseRemove.getAck());
    }
}
