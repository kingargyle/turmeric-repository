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

import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.RepositoryServiceClientConstants;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.UpdateAssetConsumer;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assume.assumeTrue;

public class UpdateAssetTest {

   private static final String s_success = "PASSED";
   private static AssetInfo assetInfo;
   private static AssetInfo assetInfo2;

   @BeforeClass
   public static void oneTimeSetUp() {
      assetInfo = CommonUtil.createBasicAsset("Service",
               RepositoryServiceClientConstants.VALID_SERVICE_CAPTURE_TEMPLATE);
      assetInfo2 = CommonUtil.createBasicAsset("Service",
               RepositoryServiceClientConstants.VALID_SERVICE_CAPTURE_TEMPLATE);
   }

   /*
    * Method under test: updateAsset Test Type : Positive Sub Type : validAsset
    */
   @Test
   public void testUpdateAsset_validAsset_positive() throws Exception {
      try {
         String status = UpdateAssetConsumer.testUpdateAsset_validAsset(assetInfo);
         Assert.assertEquals(s_success, status);
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: updateAsset Test Type : Positive Sub Type : validAsset Duplicate name
    */
   @Test
   public void testUpdateAsset_validAsset_duplicatename() throws Exception {
      try {
         // TODO: we need to have a preexsitent asset in the Repository for
         // this test
         String status = UpdateAssetConsumer.testUpdateAsset_validAsset_duplicatename(assetInfo, assetInfo2);
         assumeTrue(s_success.equalsIgnoreCase(status));
      } catch (Exception e) {
         e.printStackTrace();
         throw e;
      }
   }

   /*
    * Method under test: updateAsset Test Type : Negative Sub Type : invalidAsset
    */
   @Test
   public void testUpdateAsset_invalidAsset_negative() {
      String status = UpdateAssetConsumer.testUpdateAsset_invalidAsset();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: updateAsset Test Type : Negative Sub Type : invalidVersion
    */
   @Test
   public void testUpdateAsset_invalidVersion() {
      String status = UpdateAssetConsumer.testUpdateAsset_invalidVersion(assetInfo);
      Assert.assertEquals(s_success, status);
   }

}
