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

import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetUsersProjectsAndGroupsConsumer;
import org.junit.Test;

public class GetUsersProjectsAndGroupsTest {
	private static final String s_success ="PASSED";
	/*
	 * Method under test: GetUsersProjectsAndGroups
	 * Test Type        : Positive
	 * Sub  Type        : WithEmptyLibrary
	 */
	@Test
	public void testGetUsersProjectsAndGroupsWithEmptyLibrary(){
		String status = GetUsersProjectsAndGroupsConsumer.testGetUsersProjectsAndGroupsWithEmptyLibrary();
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: GetUsersProjectsAndGroups
	 * Test Type        : Positive
	 * Sub  Type        : WithEmptyRequest
	 */
	@Test
	public void testGetUsersProjectsAndGroupsWithEmptyRequest(){
		String status = GetUsersProjectsAndGroupsConsumer.testGetUsersProjectsAndGroupsWithEmptyRequest();
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: GetUsersProjectsAndGroups
	 * Test Type        : Positive
	 * Sub  Type        : WithInvalidLibrary
	 */
	@Test
	public void testGetUsersProjectsAndGroupsWithInvalidLibrary(){
		String status = GetUsersProjectsAndGroupsConsumer.testGetUsersProjectsAndGroupsWithInvalidLibrary();
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: GetUsersProjectsAndGroups
	 * Test Type        : Positive
	 * Sub  Type        : WithInvalidUser
	 */
	@Test
	public void testGetUsersProjectsAndGroupsWithInvalidUser(){
		String status = GetUsersProjectsAndGroupsConsumer.testGetUsersProjectsAndGroupsWithInvalidUser();
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: GetUsersProjectsAndGroups
	 * Test Type        : Positive
	 * Sub  Type        : WithValidLibrary
	 */
	@Test
	public void testGetUsersProjectsAndGroupsWithValidLibrary(){
		String status = GetUsersProjectsAndGroupsConsumer.testGetUsersProjectsAndGroupsWithValidLibrary();
		Assert.assertEquals(s_success, status);
	}


}
