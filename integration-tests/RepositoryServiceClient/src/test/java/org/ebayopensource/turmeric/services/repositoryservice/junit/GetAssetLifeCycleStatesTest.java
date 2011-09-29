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

import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetAssetLifeCycleStatesConsumer;
import org.junit.Test;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;

public class GetAssetLifeCycleStatesTest {

   private static final String s_success = "PASSED";

   /*
    * Method under test: getAssetLifeCycleStates Test Type : Positive Sub Type : with Library object as argument
    */
   @Test
   public void testGetAssetLifeCycleStates_withLibraryArgument_positive() throws AssetInfoNotFoundException,
            IdNotFoundException {
      String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_validInput("withLibrary");
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: getAssetLifeCycleStates Test Type : Positive Sub Type : without Library object as argument
    */
   @Test
   public void testGetAssetLifeCycleStates_withoutLibraryArgument_positive() throws AssetInfoNotFoundException,
            IdNotFoundException {
      String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_validInput("withoutLibrary");
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: getAssetLifeCycleStates Test Type : Positive Sub Type : without LibraryId in argument
    */
   @Test
   public void testGetAssetLifeCycleStates_withoutLibraryId_positive() throws AssetInfoNotFoundException,
            IdNotFoundException {
      String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_validInput("withoutLibraryId");
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: getAssetLifeCycleStates Test Type : Positive Sub Type :without LibraryName in argument
    */
   @Test
   public void testGetAssetLifeCycleStates_withoutLibraryName_positive() throws AssetInfoNotFoundException,
            IdNotFoundException {
      String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_validInput("withoutLibraryName");
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: getAssetLifeCycleStates Test Type : Negative Sub Type :invalid AssetType in argument
    */
   @Test
   public void testGetAssetLifeCycleStates_invalidAssetType_negative() throws AssetInfoNotFoundException,
            IdNotFoundException {
      String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_invalidInput("invalidAssetType");
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: getAssetLifeCycleStates Test Type : Negative Sub Type :without AssetType in argument
    */
   @Test
   public void testGetAssetLifeCycleStates_withoutAssetType_negative() throws AssetInfoNotFoundException,
            IdNotFoundException {
      String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_invalidInput("withoutAssetType");
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: getAssetLifeCycleStates Test Type : Negative Sub Type :invalid LibraryId in argument
    */
   @Test
   public void testGetAssetLifeCycleStates_invalidLibraryId_negative() throws AssetInfoNotFoundException,
            IdNotFoundException {
      String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_invalidInput("invalidLibraryId");
      Assert.assertEquals(s_success, status);
   }

   /*
    * Method under test: getAssetLifeCycleStates Test Type : Negative Sub Type :invalid LibraryName in argument
    */
   @Test
   public void testGetAssetLifeCycleStates_invalidLibraryName_negative() throws AssetInfoNotFoundException,
            IdNotFoundException {
      String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_invalidInput("invalidLibraryName");
      Assert.assertEquals(s_success, status);
   }

}
