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

import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetAllAssetsGroupedByCategoryConsumer;
import org.junit.Test;
public class GetAllAssetsGroupedByCategoryTest {
	private static final String s_success ="PASSED";
	/*
	 * Method under test: GetAllAssetsGroupedByCategory
	 * Test Type        : Positive
	 * Sub  Type        : withInvalidCategorizingClassifer
	 */ 
	@Test
	public void testGetAllAssetsGroupedByCategory_withInvalidCategorizingClassifer() {
		String status = GetAllAssetsGroupedByCategoryConsumer.testGetAllAssetsGroupedByCategory_withInvalidCategorizingClassifer();
		Assert.assertEquals(s_success, status);
	}     
	/*
	 * Method under test: GetAllAssetsGroupedByCategory
	 * Test Type        : Positive
	 * Sub  Type        : withoutCategorizingClassifer
	 */ 
	@Test
	public void testGetAllAssetsGroupedByCategory_withoutCategorizingClassifer() {
		String status = GetAllAssetsGroupedByCategoryConsumer.testGetAllAssetsGroupedByCategory_withoutCategorizingClassifer();
		Assert.assertEquals(s_success, status);
			
	}
	/*
	 * Method under test: GetAllAssetsGroupedByCategory
	 * Test Type        : Positive
	 * Sub  Type        : withCategorizingClassifer
	 */ 
	@Test
	public void testGetAllAssetsGroupedByCategory_withCategorizingClassifer() {
		String status = GetAllAssetsGroupedByCategoryConsumer.testGetAllAssetsGroupedByCategory_withCategorizingClassifer();
		Assert.assertEquals(s_success, status);
			
	}
	

}
