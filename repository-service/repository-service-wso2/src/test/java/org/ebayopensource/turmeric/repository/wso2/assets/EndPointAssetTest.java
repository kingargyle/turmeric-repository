/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.assets;

import static org.junit.Assert.*;


import org.junit.Test;
import org.wso2.carbon.registry.core.Registry;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.Wso2Base;

public class EndPointAssetTest extends Wso2Base {
	
	private static final String ASSETNAME = "TestEndPoint";
	private static final String ENDPOINT = "http://www.example.org/";

    @Test
    public void testCreateEndPoint() throws Exception {
        Registry wso2Registry = RSProviderUtil.getRegistry();
        BasicAssetInfo endPoint = new BasicAssetInfo();
        endPoint.setAssetName(ASSETNAME);
        endPoint.setAssetType("Endpoint");
        endPoint.setVersion("1.0");
        endPoint.setNamespace(ENDPOINT);
        
        Asset asset = new EndPointAsset(endPoint, wso2Registry);
        
        assertTrue("Endpoint was not created.", asset.createAsset());
        assertTrue("Endpoint was not added.", asset.addAsset());
        assertTrue("Endpoint doesn't exist", asset.exists());
    }
    
}
