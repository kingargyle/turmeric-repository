/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.UpdateCompleteAssetException;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.utils.AbstractCarbonIntegrationTestCase;
import org.ebayopensource.turmeric.services.repositoryservice.exception.ProcessingException;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.ApproveAssetConsumer;

import org.ebayopensource.turmeric.services.repository.validation.exception.InvalidInputException;

public class ApproveAssetTest extends AbstractCarbonIntegrationTestCase {

   public static AssetCreatorIntf assetCreator = AssetCreatorFactory
            .getAssetCreator("resource/FunctionalDomainAsset.xml");

   @BeforeClass
   public static void oneTimeSetUp() throws AssetCreationException {
      try {
         assetCreator.createAsset();
         // Connection connection = ConnectionUtil.getInstance().getDefaultConnection();
         // assetSource = AssetSourceFactory.createAssetSource(connection);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // private Event event ;
   private static final String s_success = "PASSED";

   // private static AssetSource assetSource = null;
   /*
    * Method under test: ApproveAsset Test Type : Positive Sub Type : ValidAsset
    */
   @Test
   public void testGetAssetStatus_validAsset_WithId() throws AssetInfoNotFoundException, IdNotFoundException,
            UpdateCompleteAssetException, InvalidInputException, ProcessingException,
            org.ebayopensource.turmeric.assetcreation.exception.ProcessingException, Exception {
      try {
         AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
         AssetKey assetKey = assetInfo.getBasicAssetInfo().getAssetKey();
         assetInfo.getBasicAssetInfo().getAssetKey().getAssetId();
         // Asset asset = assetSource.getAsset(assetId, false);
         AssetInfoForUpdate updateInfo = assetCreator.getAssetInfoForUpdate(assetInfo);
         updateInfo.getBasicAssetInfo().setAssetDescription("Modified");
         assetCreator.updateCompleteAsset(updateInfo);
         // ExtListenerHandlerUtil.setEvent(asset, event, user);
         String status = ApproveAssetConsumer.testGetAssetStatus_validAsset_WithId(assetInfo);
         assetCreator.removeAsset(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }

   }

   @Override
   protected void copyArtifacts() throws IOException {
      // TODO Auto-generated method stub

   }
}
