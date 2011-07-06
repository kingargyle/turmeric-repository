/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import junit.framework.Assert;

import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetBasicAssetInfoConsumer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class GetAssetBasicAssetInfoTest {

    private static final String s_success = "PASSED";
    private static AssetInfo assetInfo;

    @BeforeClass
    public static void oneTimeSetUp() {
        assetInfo = CommonUtil.createBasicAsset("Service", "Propose Service Template");
        
    }
    /*
     * Method under test: getBasicAssetInfo Test Type : Positive Sub Type : validAsset
     */

    @Test
    public void testGetBasicAssetInfo_validAsset_positive() {
        String status = GetBasicAssetInfoConsumer.testGetBasicAssetInfo_validAsset(assetInfo);
        Assert.assertEquals(s_success, status);
    }

    /*
     * Method under test: getBasicAssetInfo Test Type : Positive Sub Type : validAsset Long
     * Description
     */

    @Test
    public void testGetBasicAssetInfo_validAsset_longDescription_positive() {
        String status = GetBasicAssetInfoConsumer
                        .testGetBasicAssetInfo_validAsset_Long_Description(assetInfo.getBasicAssetInfo().getAssetKey());
        Assert.assertEquals(s_success, status);
    }

    /*
     * Method under test: getBasicAssetInfo Test Type : Negative Sub Type : invalidAsset
     */

    @Test
    public void testGetBasicAssetInfo_invalidAsset_negative() {
        String status = GetBasicAssetInfoConsumer.testGetBasicAssetInfo_invalidAsset(assetInfo.getBasicAssetInfo().getAssetKey());
        Assert.assertEquals(s_success, status);
    }

    @AfterClass
    public static void oneTimeTearDown() {
        CommonUtil.removeAsset(assetInfo.getBasicAssetInfo().getAssetKey());
    }

}