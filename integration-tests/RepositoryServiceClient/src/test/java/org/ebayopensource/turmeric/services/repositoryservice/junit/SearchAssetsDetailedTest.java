/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.SearchAssetsDetailedConsumer;
import org.junit.Test;
import junit.framework.Assert;

public class SearchAssetsDetailedTest {
   private static final String s_success = "PASSED";

   /*
    * Method under test: SearchDetailedAsset Test Type : Positive Sub Type : withoutSearchLevel
    */
   @Test
   public void testSearchAssetsDetailed_withoutSearchLevel() {
      String status = SearchAssetsDetailedConsumer.testSearchAssetsDetailed_withoutSearchLevel();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: SearchDetailedAsset Test Type : Positive Sub Type : withSearchLevel_basic
    */
   @Test
   public void testSearchAssetsDetailed_withSearchLevel_basic() {
      String status = SearchAssetsDetailedConsumer.testSearchAssetsDetailed_withSearchLevel_basic();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: SearchDetailedAsset Test Type : Positive Sub Type : withSearchLevel_extended
    */
   @Test
   public void testSearchAssetsDetailed_withSearchLevel_extended() {
      String status = SearchAssetsDetailedConsumer.testSearchAssetsDetailed_withSearchLevel_extended();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: SearchDetailedAsset Test Type : Positive Sub Type : withSearchLevel_artifacts
    */
   @Test
   public void testSearchAssetsDetailed_withSearchLevel_artifacts() {
      String status = SearchAssetsDetailedConsumer.testSearchAssetsDetailed_withSearchLevel_artifacts();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: SearchDetailedAsset Test Type : Positive Sub Type : withSearchLevel_lifecycle
    */
   @Test
   public void testSearchAssetsDetailed_withSearchLevel_lifecycle() {
      String status = SearchAssetsDetailedConsumer.testSearchAssetsDetailed_withSearchLevel_lifecycle();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: SearchDetailedAsset Test Type : Positive Sub Type : withSearchLevel_relationships
    */
   @Test
   public void testSearchAssetsDetailed_withSearchLevel_relationships() {
      String status = SearchAssetsDetailedConsumer.testSearchAssetsDetailed_withSearchLevel_relationships();
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: SearchDetailedAsset Test Type : Positive Sub Type : withSearchLevel_all
    */
   @Test
   public void testSearchAssetsDetailed_withSearchLevel_all() {
      String status = SearchAssetsDetailedConsumer.testSearchAssetsDetailed_withSearchLevel_all();
      Assert.assertEquals(s_success, status);
   }
}
