/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.SearchAssetConsumer;
import org.junit.Test;
import junit.framework.Assert;

public class SearchAssetTest {
	private static final String s_success ="PASSED";
	/*  NULL
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : with Library argument , using the AND Conjunction
	 */
	@Test
	public void testSearchAsset_withLibrary_andConjunction_positive() {
		String status = SearchAssetConsumer.testSearchAsset_withLibrary_andConjunction(); 
		Assert.assertEquals(s_success, status);
	}

	/*FINE
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : with Library argument , using the OR Conjunction
	 */
	@Test
	public void testSearchAsset_withLibrary_orConjunction_positive() {
		String status = SearchAssetConsumer.testSearchAsset_withLibrary_orConjunction(); 
		Assert.assertEquals(s_success, status);
	}	

	/*
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : without Library argument , using the AND Conjunction
	 */
	@Test
	public void testSearchAsset_withoutLibrary_andConjunction_positive() {
		String status = SearchAssetConsumer.testSearchAsset_withoutLibrary_andConjunction(); 
		Assert.assertEquals(s_success, status);
	}

	/*
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : without Library argument , using the OR Conjunction
	 */
	@Test
	public void testSearchAsset_withoutLibrary_orConjunction_positive() {
		String status = SearchAssetConsumer.testSearchAsset_withoutLibrary_orConjunction(); 
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : withLibrary_andConjunction_relationCriteria
	 */
	@Test
	public void testSearchAsset_withLibrary_andConjunction_relationCriteria() {
		String status = SearchAssetConsumer.testSearchAsset_withLibrary_andConjunction_relationCriteria(); 
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : Without_AttributeCriteria_withLibrary
	 */
	@Test
	public void testSearchAsset_Without_AttributeCriteria_withLibrary() {
		String status = SearchAssetConsumer.testSearchAsset_Without_AttributeCriteria_withLibrary(); 
		Assert.assertEquals(s_success, status);
	}
	/*BOOTSTRAP USER
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : Without_AttributeCriteria_withoutLibrary
	 */
	@Test
	public void testSearchAsset_Without_AttributeCriteria_withoutLibrary() {
		String status = SearchAssetConsumer.testSearchAsset_Without_AttributeCriteria_withoutLibrary(); 
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : with_SingleAttribute_Criteria
	 */
	@Test
	public void testSearchAsset_with_SingleAttribute_Criteria() {
		String status = SearchAssetConsumer.testSearchAsset_with_SingleAttribute_Criteria(); 
		Assert.assertEquals(s_success, status);
	}
}
