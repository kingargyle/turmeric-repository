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
import junit.framework.TestCase;

import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.UnlockAssetConsumer;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnlockAssetTest {
   private static final String s_success = "PASSED";
   private static AssetInfo assetInfo;

   @BeforeClass
   public static void oneTimeSetUp() {
      assetInfo = CommonUtil.createBasicAsset("Service", "Propose Service Template");
   }

   /*
    * Method under test: unlockAsset Test Type : Positive Sub Type : assetCurrentlyLocked
    */
   @Test
   public void testUnlockAsset_assetCurrentlyLocked_positive() throws Exception {
      try {
         String status = UnlockAssetConsumer.testUnlockAsset_validAsset(assetInfo.getBasicAssetInfo().getAssetKey());
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         Assert.fail("Unexpected Exception Occured. Message : " + e.getMessage());
         e.printStackTrace();
         throw e;
      }

   }

   /*
    * Method under test: unlockAsset Test Type : Negative Sub Type : assetCurrentlyUnlocked
    */
   // @Test
   // public void testUnlockAsset_assetCurrentlyUnlocked_negative() throws Exception {
   // try {
   // AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
   // AssetKey assetKey = assetInfo.getBasicAssetInfo().getAssetKey();
   // String status = UnlockAssetConsumer.testUnlockAsset_assetCurrentlyNotLocked(assetInfo);
   // assetCreator.removeAsset(assetKey);
   // Assert.assertEquals(s_success, status);
   // }
   // catch (Exception e) {
   // e.printStackTrace();
   // throw e;
   // }
   //
   // }

   /*
    * Method under test: unlockAsset Test Type : Negative Sub Type : invalidAsset
    */
   @Test
   public void testUnlockAsset_invalidAsset_negative() {
      String status = UnlockAssetConsumer.testUnlockAsset_invalidAsset();
      Assert.assertEquals(s_success, status);
   }

}
