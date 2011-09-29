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

import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.UpdateCompleteAssetConsumer;
import static org.junit.Assume.assumeTrue;

public class UpdateCompleteAssetTest {

   private static final String s_success = "PASSED";
   private static AssetKey assetKey;
   private static AssetInfo serviceAssetInfo;
   private static AssetInfo fdAssetInfo;

   @BeforeClass
   public static void oneTimeSetUp() {
      assetKey = CommonUtil.createCompleteServiceAsset();
      serviceAssetInfo = CommonUtil.getAssetInfo(assetKey);
      fdAssetInfo = CommonUtil.createBasicAsset("Functional Domain", "Functional Domain Template");
   }

   /*
    * Method under test: updateCompleteAsset Test Type : Positive Sub Type : validInput
    */
   @Test
   public void testUpdateCompleteAsset_validInput() throws Exception {
      try {
         String status = UpdateCompleteAssetConsumer.testUpdateCompleteAsset_validInput("withAllOptionalInputParams",
                  serviceAssetInfo, fdAssetInfo);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: updateCompleteAsset Test Type : Negative Sub Type : InvalidInput no request
    */

   @Test
   public void testUpdateCompleteAsset_InvalidInput_noRequest() throws Exception {
      try {
         String status = UpdateCompleteAssetConsumer.testUpdateCompleteAsset_invalidInput("noRequest",
                  serviceAssetInfo, fdAssetInfo);
         // TODO: Need to validate the logic in the consumer for a null request
         assumeTrue(s_success.equalsIgnoreCase(status));
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: updateCompleteAsset Test Type : Negative Sub Type : InvalidGroupName
    */
   @Test
   public void testUpdateCompleteAsset_invalidInput_inValidGroupName() throws Exception {
      try {
         String status = UpdateCompleteAssetConsumer.testUpdateCompleteAsset_invalidInput("inValidGroupName",
                  serviceAssetInfo, fdAssetInfo);
         // TODO: It is needed to validate the consumer's logic in this case
         assumeTrue(s_success.equalsIgnoreCase(status));
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: updateCompleteAsset Test Type : Negative Sub Type : InvalidInput Invalid library
    */
   @Test
   public void testUpdateCompleteAsset_InvalidInput_invalidLibrary() throws Exception {
      try {
         String status = UpdateCompleteAssetConsumer.testUpdateCompleteAsset_invalidInput("inValidLibraryName",
                  serviceAssetInfo, fdAssetInfo);
         // TODO: Need to validate what is an invalid library name for the wso2 impl
         assumeTrue(s_success.equalsIgnoreCase(status));
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: updateCompleteAsset Test Type : Negative Sub Type : InvalidInput Invalid Asset ID
    */
   @Test
   public void testUpdateCompleteAsset_InvalidInput_invalidAssetID() throws Exception {
      try {

         String status = UpdateCompleteAssetConsumer.testUpdateCompleteAsset_invalidInput("inValidAssetId",
                  serviceAssetInfo, fdAssetInfo);
         // TODO: need to validate in the consumer what is an invalid asset id
         assumeTrue(s_success.equalsIgnoreCase(status));
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

}
