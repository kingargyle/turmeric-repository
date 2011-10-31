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

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.CreateCompleteAssetConsumer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assume.assumeTrue;

public class CreateCompleteAssetTest {

   private static final String s_success = "PASSED";
   private static AssetInfo fdAssetInfo;

   @BeforeClass
   public static void oneTimeSetUp() {
      fdAssetInfo = CommonUtil.createBasicAsset("Functional Domain", "Functional Domain Template");
   }

   /*
    * Method under test: createCompleteAsset Test Type : positive Sub Type : noAssetLongDescription
    */

   @Test
   public void testCreateAsset_noLibraryName_positive_noAssetLongDescription() {
      String status = CreateCompleteAssetConsumer.testCreateCompleteAsset_validInput("noAssetLongDescription",
               fdAssetInfo);
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: createCompleteAsset Test Type : positive Sub Type : noLibraryId
    */
   @Test
   public void testCreateCompleteAsset_validInput_noLibraryId() {
      String status = CreateCompleteAssetConsumer.testCreateCompleteAsset_validInput("noLibraryId", fdAssetInfo);
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: createCompleteAsset Test Type : positive Sub Type : withAllOptionalInputParams
    */
   @Test
   public void testCreateCompleteAsset_validInput_withAllOptionalInputParams() {
      String status = CreateCompleteAssetConsumer.testCreateCompleteAsset_validInput("withAllOptionalInputParams",
               fdAssetInfo);
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: createCompleteAsset Test Type : Negative Sub Type : noRequest
    */
   @Test
   public void testCreateCompleteAsset_invalidInput_noRequest() {

      String status = CreateCompleteAssetConsumer.testCreateCompleteAsset_invalidInput("noRequest", fdAssetInfo);
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: createCompleteAsset Test Type : Negative Sub Type : noRequest
    */
   @Test
   public void testCreateCompleteAsset_invalidInput_noCaptureTemplate() {
      String status = CreateCompleteAssetConsumer
               .testCreateCompleteAsset_invalidInput("noCaptureTemplate", fdAssetInfo);
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: createCompleteAsset Test Type : Negative Sub Type : noRequest
    */
   @Test
   public void testCreateCompleteAsset_invalidInput_noGroupName() {
      String status = CreateCompleteAssetConsumer.testCreateCompleteAsset_invalidInput("noGroupName", fdAssetInfo);
      assumeTrue(s_success.equalsIgnoreCase(status));
   }

   /*
    * Method under test: createCompleteAsset Test Type : Negative Sub Type : invalidLibraryName
    */
   @Test
   public void testCreateCompleteAsset_invalidInput_invalidLibraryName() {
      String status = CreateCompleteAssetConsumer.testCreateCompleteAsset_invalidInput("invalidLibraryName",
               fdAssetInfo);
      assumeTrue(s_success.equalsIgnoreCase(status));
   }

   /*
    * Method under test: createCompleteAsset Test Type : Negative Sub Type : invalidLibraryName
    */
   @Test
   public void testCreateCompleteAsset_invalidInput_invalidGroupName() {
      String status = CreateCompleteAssetConsumer.testCreateCompleteAsset_invalidInput("invalidGroupName", fdAssetInfo);
      assumeTrue(s_success.equals(status));
   }

   @AfterClass
   public static void oneTimeTearDown() {
      CommonUtil.removeAsset(fdAssetInfo.getBasicAssetInfo().getAssetKey());
   }

}
