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

import org.junit.BeforeClass;
import org.junit.Test;

import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.UpdateAssetDependenciesConsumer;

public class UpdateAssetDependenciesTest {

   public static AssetCreatorIntf assetCreator = AssetCreatorFactory.getAssetCreator("resource/ServiceAsset.xml");

   @BeforeClass
   public static void oneTimeSetUp() throws AssetCreationException {
      assetCreator.createAsset();
   }

   private static final String s_success = "PASSED";

   /*
    * Method under test: updateAssetDependencies Test Type : Positive Sub Type : validInput
    */
   @Test
   public void testUpdateAssetDependencies_validInput() throws Exception {
      try {
         AssetInfo assetInfo1 = assetCreator.getAssetAsAssetInfo("Common");
         AssetInfo assetInfo2 = assetCreator.getAssetAsAssetInfo("Common1");
         AssetInfo assetInfo3 = assetCreator.getAssetAsAssetInfo("Common2");
         String status = UpdateAssetDependenciesConsumer.testUpdateAssetDependencies_validInput(assetInfo1, assetInfo2,
                  assetInfo3);
         AssetKey assetKey = new AssetKey();
         assetKey.setAssetId(assetInfo1.getBasicAssetInfo().getAssetKey().getAssetId());
         assetCreator.removeAsset(assetKey);
         assetKey.setAssetId(assetInfo2.getBasicAssetInfo().getAssetKey().getAssetId());
         assetCreator.removeAsset(assetKey);
         assetKey.setAssetId(assetInfo3.getBasicAssetInfo().getAssetKey().getAssetId());
         assetCreator.removeAsset(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: updateAssetDependencies Test Type : Positive Sub Type : deleteRelationship
    */
   @Test
   public void testUpdateAssetDependencies_deleteRelationship() throws Exception {
      try {
         AssetInfo assetInfo1 = assetCreator.getAssetAsAssetInfo("Common");
         AssetInfo assetInfo2 = assetCreator.getAssetAsAssetInfo("Common1");
         String status = UpdateAssetDependenciesConsumer.testUpdateAssetDependencies_deleteRelationship(assetInfo1,
                  assetInfo2);
         AssetKey assetKey = new AssetKey();
         assetKey.setAssetId(assetInfo1.getBasicAssetInfo().getAssetKey().getAssetId());
         assetCreator.removeAsset(assetKey);
         assetKey.setAssetId(assetInfo2.getBasicAssetInfo().getAssetKey().getAssetId());
         assetCreator.removeAsset(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }
}
