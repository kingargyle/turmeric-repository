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

import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetCatalogAssetInfoConsumer;
import org.junit.Test;

public class GetCatalogAssetInfoTest {
   private static final String s_success = "PASSED";

   /*
    * Method under test: GetCatalogAssetInfo Test Type : Negative Sub Type : invalidLibrary
    */

   @Test
   public void testGetAllCatalogAsset_invalidSessionId() {
      String status = GetCatalogAssetInfoConsumer.testGetAllCatalogAsset_invalidSessionId();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: GetCatalogAssetInfo Test Type : Positive Sub Type : GetAllCatalogAsset
    */

   @Test
   public void testGetAllCatalogAsset() {
      String status = GetCatalogAssetInfoConsumer.testGetAllCatalogAsset();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: GetCatalogAssetInfo Test Type : Positive Sub Type : EditedCatalogAsset
    */

   @Test
   public void testGetEditedCatalogAsset() {
      String status = GetCatalogAssetInfoConsumer.testGetEditedCatalogAsset();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: GetCatalogAssetInfo Test Type : Positive Sub Type : LockedCatalogAsset
    */

   @Test
   public void testGetLockedCatalogAsset() {
      String status = GetCatalogAssetInfoConsumer.testGetLockedCatalogAsset();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: GetCatalogAssetInfo Test Type : Positive Sub Type : WhenSearchLevelProvided
    */

   @Test
   public void testGetCatalogAssetWhenSearchLevelProvided() {
      String status = GetCatalogAssetInfoConsumer.testGetCatalogAssetWhenSearchLevelProvided();
      Assert.assertEquals(s_success, status);
   }
}
