/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import java.util.Properties;

import static org.junit.Assert.*;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.RemoveAssetConsumer;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoveAssetTest {

   private static final String s_success = "PASSED";
   private static AssetInfo assetInfo1;
   private static AssetInfo assetInfo2;
   private static Properties prop = CommonUtil.loadPropertyFile("properties/common.properties");

   @BeforeClass
   public static void oneTimeSetUp() {
      assetInfo1 = CommonUtil.createBasicAsset("Service", "Propose Service Template");
      assetInfo2 = CommonUtil.createBasicAsset("Service", "Propose Service Template");
   }

   /*
    * Method under test: RemoveAsset Test Type : Positive Sub Type : PositiveScenario
    */
   @Test
   public void testRemoveAsset_positiveScenario() {
      String status = RemoveAssetConsumer.testRemoveAsset_positiveScenario(assetInfo1);
      assertEquals(s_success, status);
   }

   /*
    * Method under test: RemoveAsset Test Type : Negative Sub Type : Asset Locked by Another User
    */
   @Test
   public void testRemoveAsset_assetIsLockedByAnotherUser() {
      String userId = prop.getProperty("userId", "");
      String password = prop.getProperty("password", "");
      CommonUtil.lockAsset(assetInfo2.getBasicAssetInfo().getAssetKey(), userId, password);
      String status = RemoveAssetConsumer.testRemoveAsset_assetIsLockedByAnotherUser(assetInfo2);
      assertEquals(s_success, status);
   }

}
