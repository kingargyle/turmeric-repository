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
import junit.framework.Assert;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetServiceConsumer;

public class GetServiceTest {
   public static AssetCreatorIntf assetCreator = AssetCreatorFactory.getAssetCreator("resource/ServiceAsset.xml");

   @BeforeClass
   public static void oneTimeSetUp() throws AssetCreationException {
      assetCreator.createAsset();
   }

   private static final String s_success = "PASSED";

   /*
    * Method under test: getService Test Type : Positive Sub Type : validAsset
    */
   @Test
   public void testGetService_validAsset_positive() throws AssetInfoNotFoundException, IdNotFoundException {
      AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("approvedserviceasset");
      String status = GetServiceConsumer.testGetService_validAsset(assetInfo);
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: getService Test Type : Negative Sub Type : invalidAsset
    */
   @Test
   public void testGetService_invalidAsset_negative() {
      String status = GetServiceConsumer.testGetService_invalidAsset();
      Assert.assertEquals(s_success, status);
   }
}
