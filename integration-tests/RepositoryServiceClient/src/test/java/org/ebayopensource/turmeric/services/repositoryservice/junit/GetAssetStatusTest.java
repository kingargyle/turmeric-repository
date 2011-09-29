/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import org.junit.BeforeClass;
import org.junit.Test;

import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetAssetStatusConsumer;
//import junit.framework.TestCase;
import junit.framework.Assert;

public class GetAssetStatusTest {
   public static AssetCreatorIntf assetCreator = AssetCreatorFactory
            .getAssetCreator("resource/FunctionalDomainAsset.xml");

   @BeforeClass
   public static void oneTimeSetUp() throws AssetCreationException {
      assetCreator.createAsset();
   }

   private static final String s_success = "PASSED";

   /*
    * Method under test: GetAssetStatus Test Type : Positive Sub Type : valid asset
    */
   @Test
   public void testGetAssetStatus_validAsset() throws Exception {
      try {
         AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
         AssetKey assetKey = new AssetKey();
         assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
         assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
         assetCreator.unlockAsset(assetKey);
         String status = GetAssetStatusConsumer.testGetAssetStatus_validAsset(assetInfo);
         assetCreator.removeAsset(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }

   }

   /*
    * Method under test: GetAssetStatus Test Type : Negative Sub Type : invalidAsset
    */
   @Test
   public void testGetAssetStatus_assetwithnoassetRequests() throws Exception {
      try {
         AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
         AssetKey assetKey = new AssetKey();
         assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
         assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
         String status = GetAssetStatusConsumer.testGetAssetStatus_assetwithnoassetRequests(assetInfo);
         assetCreator.removeAsset(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

}
