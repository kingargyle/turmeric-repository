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

import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetLibraryListConsumer;
import org.junit.Test;

public class GetLibraryListTest {
   private static final String s_success = "PASSED";

   /*
    * Method under test: GetLibraryList Test Type : Positive Sub Type : Positive scenario
    */

   @Test
   public void testGetLibraryList_positiveScenario() {
      String status = GetLibraryListConsumer.testGetLibraryList_positiveScenario();
      Assert.assertEquals(s_success, status);
   }

}
