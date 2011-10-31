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
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.CreateAndSubmitAssetConsumer;

public class CreateAndSubmitTest {
   public static AssetCreatorIntf assetCreator = AssetCreatorFactory
            .getAssetCreator("resource/FunctionalDomainAsset.xml");

   @BeforeClass
   public static void oneTimeSetUp() throws AssetCreationException {
      assetCreator.createAsset();
   }

   private static final String s_success = "PASSED";

   /*
    * Method under test: createAndSubmitAsset Test Type : Positive Sub Type : ValidAsset
    */
   @Test
   public void testCreateAndSubmitAsset_validInput() throws Exception {
      try {
         AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
         String status = CreateAndSubmitAssetConsumer.testCreateAndSubmitAsset_validInput("", assetInfo);
         AssetKey assetKey = new AssetKey();
         assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
         assetCreator.removeAsset(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: createAndSubmitAsset Test Type : Positive Sub Type : withMandatoryClassifier
    */
   @Test
   public void testCreateAndSubmitAsset_validInput_withMandatoryClassifier() throws Exception {

      try {
         AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
         String status =

         CreateAndSubmitAssetConsumer.testCreateAndSubmitAsset_validInput("withMandatoryClassifiers", assetInfo);
         AssetKey assetKey = new AssetKey();
         assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
         assetCreator.removeAsset(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: createAndSubmitAsset Test Type : Positive Sub Type : nullRequest
    */
   @Test
   public void testCreateAndSubmitAsset_nullRequest() throws Exception {
      String status = CreateAndSubmitAssetConsumer.testCreateAndSubmitAsset_nullRequest();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: createAndSubmitAsset Test Type : Positive Sub Type : withinvalidLibrary
    */
   @Test
   public void testCreateAndSubmitAsset_validInput_withinvalidLibrary() throws Exception {

      try {
         AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
         String status = CreateAndSubmitAssetConsumer.testCreateAndSubmitAsset_validInput("invalidLibrary", assetInfo);
         AssetKey assetKey = new AssetKey();
         assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
         assetCreator.removeAsset(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: createAndSubmitAsset Test Type : Positive Sub Type : FDAssetType
    */
   @Test
   public void testCreateAndSubmitAsset_FDAssetType() {
      String status = CreateAndSubmitAssetConsumer.testCreateAndSubmitAsset_FDAssetType();
      Assert.assertEquals(s_success, status);
   }
}
