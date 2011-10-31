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
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetAssetInfoConsumer;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.RepositoryServiceClientConstants;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assume.assumeTrue;

public class GetAssetInfoTest {

   private static final String s_success = "PASSED";
   private static AssetKey assetKey;
   private static AssetInfo assetInfo;

   @BeforeClass
   public static void oneTimeSetUp() {
      assetKey = CommonUtil.createCompleteServiceAsset();
      assetInfo = CommonUtil.createBasicAsset("Service",
               RepositoryServiceClientConstants.VALID_SERVICE_CAPTURE_TEMPLATE);
   }

   /*
    * Method under test: getAssetInfo Test Type : Positive Sub Type : validAsset
    */

   @Test
   public void testGetAssetInfo_validAsset_positive() throws Exception {
      try {

         String status = GetAssetInfoConsumer.testGetAssetInfo_validAsset(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: getAssetInfo Test Type : Positive Sub Type : validAsset
    */
   @Test
   public void testGetAssetInfo_validAssetbyName_positive() throws Exception {
      try {

         String status = GetAssetInfoConsumer.testGetAssetInfo_validAsset_byName(assetKey);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }

   }

   /*
    * Method under test: getAssetInfo Test Type : Negative Sub Type : invalidAssetKey
    */
   @Test
   public void testGetAssetInfo_invalidAssetKey_negative() throws Exception {
      try {
         String status = GetAssetInfoConsumer.testGetAssetInfo("invalidAssetKey", assetInfo);
         assumeTrue(s_success.equalsIgnoreCase(status));
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }

   }

   /*
    * Method under test: getAssetInfo Test Type : Negative Sub Type : invalidAssetVersion
    */
   @Test
   public void testGetAssetInfo_invalidAssetVersion_negative() throws Exception {
      try {
         String status = GetAssetInfoConsumer.testGetAssetInfo("invalidAssetVersion", assetInfo);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }

   }

   /*
    * Method under test: getAssetInfo Test Type : Negative Sub Type : invalidAssetType
    */
   @Test
   public void testGetAssetInfo_invalidAssetType_negative() throws Exception {
      try {
         String status = GetAssetInfoConsumer.testGetAssetInfo("invalidAssetType", assetInfo);
         assumeTrue(s_success.equalsIgnoreCase(status));
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }

   }

   /*
    * Method under test: getAssetInfo Test Type : Negative Sub Type : invalidAssetkey for published
    */
   // @Test
   // public void testGetAssetInfo_invalidAssetKey_forpublished_negative() throws Exception {
   // try {
   // String status = GetAssetInfoConsumer.testGetAssetInfo_forpublished("invalidAssetKey",
   // assetInfo);
   // Assert.assertEquals(s_success, status);
   // }
   // catch (Exception e) {
   // e.printStackTrace();
   // throw e;
   // }
   // }

   /*
    * Method under test: getAssetInfo Test Type : Negative Sub Type : invalidAssetVersion for published
    */
   // @Test
   // public void testGetAssetInfo_invalidAssetVersion_forpublished_negative()throws Exception {
   // try{
   // AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
   // AssetKey assetKey = new AssetKey();
   // assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
   // assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
   // String status =
   // GetAssetInfoConsumer.testGetAssetInfo_forpublished("invalidAssetVersion",assetInfo);
   // assetCreator.removeAsset(assetKey);
   // Assert.assertEquals(s_success, status);
   // }
   // catch(Exception e)
   // {
   // e.printStackTrace();
   // throw e;
   // }
   // }
   /*
    * Method under test: getAssetInfo Test Type : Negative Sub Type : invalidAssetVersion for published
    */
   // @Test
   // public void testGetAssetInfo_invalidAssetType_forpublished_negative() throws Exception {
   // try{
   // AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
   // AssetKey assetKey = new AssetKey();
   // assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
   // assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
   // String status =
   // GetAssetInfoConsumer.testGetAssetInfo_forpublished("invalidAssetType",assetInfo);
   // assetCreator.removeAsset(assetKey);
   // Assert.assertEquals(s_success, status);
   // }
   // catch(Exception e)
   // {
   // e.printStackTrace();
   // throw e;
   // }
   // }
   /*
    * Method under test: getAssetInfo Test Type : Positive Sub Type : valid asset for published
    */
   // @Test
   // public void testGetAssetInfo_validAsset_forpublished_positive()throws Exception {
   // try{
   // AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
   // AssetKey assetKey = new AssetKey();
   // assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
   // assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
   // String status = GetAssetInfoConsumer.testGetAssetInfo_validAsset_forpublished(assetInfo);
   // assetCreator.removeAsset(assetKey);
   // Assert.assertEquals(s_success, status);
   // }
   // catch(Exception e)
   // {
   // e.printStackTrace();
   // throw e;
   // }
   // }
   /*
    * Method under test: getAssetInfo Test Type : Positive Sub Type : valid asset for published
    */
   // @Test
   // public void testGetAssetInfo_validAsset_byName_forpublished_positive()throws Exception {
   // try{
   // AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
   // AssetKey assetKey = new AssetKey();
   // assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
   // assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
   // String status =
   // GetAssetInfoConsumer.testGetAssetInfo_validAsset_byName_forpublished(assetInfo);
   // assetCreator.removeAsset(assetKey);
   // Assert.assertEquals(s_success, status);
   // }
   // catch(Exception e)
   // {
   // e.printStackTrace();
   // throw e;
   // }
   // }

   @AfterClass
   public static void oneTimeTearDown() {
      CommonUtil.removeAsset(assetKey);
      CommonUtil.removeAsset(assetInfo.getBasicAssetInfo().getAssetKey());
   }

}
