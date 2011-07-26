/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repository.wso2.test;

import java.io.IOException;

import org.ebayopensource.turmeric.repository.wso2.utils.AbstractCarbonIntegrationTestCase;
import org.junit.Test;

public class TestCarbonIntegration extends AbstractCarbonIntegrationTestCase {

	@Override
	protected void copyArtifacts() throws IOException {
		
	}
	
	@Test
	public void serverStartTest() throws Exception {
		System.out.println("Test");
	}
	
	@Test
	public void testAnotherInstance() throws Exception {
		System.out.println("Test");
	}

}