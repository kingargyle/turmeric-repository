/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;
import static org.junit.Assume.assumeTrue;

import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetAssetTypesConsumer;
import org.junit.Test;
import junit.framework.Assert;

public class GetAssetTypesTest {
	private static final String s_success ="PASSED";


	/*
	 * Method under test: getAssetTypes
	 * Test Type        : Positive
	 * Sub  Type        : withoutLibraryArgument
	 */
	@Test
	public void testGetAssetTypes_withoutLibraryArgument_positive() {
		String status = GetAssetTypesConsumer.testGetAssetTypes_withoutLibraryArgument();
		assumeTrue(s_success.equalsIgnoreCase(status));
	}
	
	/*
	 * Method under test: getAssetTypes
	 * Test Type        : Positive
	 * Sub  Type        : withLibraryArgument
	 */
	@Test
	public void testGetAssetTypes_withLibraryArgument_positive() {
		String status = GetAssetTypesConsumer.testGetAssetTypes_withLibraryArgument();
		assumeTrue(s_success.equalsIgnoreCase(status));
	}
	
	/*
	 * Method under test: getAssetTypes
	 * Test Type        : Negative
	 * Sub  Type        : invalidLibrary
	 */
	@Test
	public void testGetAssetTypes_invalidLibrary_negative() {
		String status = GetAssetTypesConsumer.testGetAssetTypes_invalidLibrary();
		assumeTrue(s_success.equalsIgnoreCase(status));
	}
	

}
