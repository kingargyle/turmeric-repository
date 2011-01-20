/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetAllProjectsAndGroupsConsumer;
import org.junit.Test;
import junit.framework.Assert;
public class GetAllProjectsAndGroupsTest  
{
	private static final String s_success ="PASSED";
	/*
	 * Method under test: GetAllProjectsAndGroups
	 * Test Type        : Negative
	 * Sub  Type        : invalidLibrary
	 */

	@Test
	public void testGetAllProjectsAndGroupsWithInvalidLibrary() {
		String status=  GetAllProjectsAndGroupsConsumer.testGetAllProjectsAndGroupsWithInvalidLibrary();
		Assert.assertEquals(s_success, status);         
}
	/*
	 * Method under test: GetAllProjectsAndGroups
	 * Test Type        : Positive
	 * Sub  Type        : ValidLibraryId
	 */
	
	@Test
	public void GetAllProjectsAndGroups_WithValidLibrary() {
		String status=  GetAllProjectsAndGroupsConsumer.testGetAllProjectsAndGroupsWithValidLibrary();
		Assert.assertEquals(s_success, status);
	
}
	/*
	 * Method under test: GetAllProjectsAndGroups
	 * Test Type        : Negative
	 * Sub  Type        : Without Library
	 */
	
	@Test
	public void GetAllProjectsAndGroups_WithouLibrary() {
		String status=  GetAllProjectsAndGroupsConsumer.testGetAllProjectsAndGroupsWithoutLibrary();
		Assert.assertEquals(s_success, status);
	
}
	/*
	 * Method under test: GetAllProjectsAndGroups
	 * Test Type        : Positive
	 * Sub  Type        : With Empty Request
	 */
	
	@Test
	public void GetAllProjectsAndGroups_WithEmptyRequest() {
		String status=  GetAllProjectsAndGroupsConsumer.testGetAllProjectsAndGroupsWithEmptyRequest();
		Assert.assertEquals(s_success, status);
}
	/*
	 * Method under test: GetAllProjectsAndGroups
	 * Test Type        : Positive
	 * Sub  Type        : With Empty Library
	 */
	
	@Test
	public void GetAllProjectsAndGroups_WithEmptyLibrary() {
		String status=  GetAllProjectsAndGroupsConsumer.testGetAllProjectsAndGroupsWithEmptyLibrary();
		Assert.assertEquals(s_success, status);
	
}
}
	

