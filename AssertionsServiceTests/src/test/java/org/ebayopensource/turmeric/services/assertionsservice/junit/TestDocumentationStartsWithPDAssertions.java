/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import org.junit.Test;

import junit.framework.Assert;

public class TestDocumentationStartsWithPDAssertions extends BaseIndividualAssertionsTest {

	@Test
	public void testDocumentationStartsWithPD_Positive(){
		System.out.println("\n***Starting testDocumentationStartsWithPD_Positive");
		String assertionAssetName = "soa_documentationStartsWithPD_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testDocumentationStartsWithPD_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}

	@Test
	public void testDocumentationStartsWithPD_Negative(){
		System.out.println("\n***Starting testDocumentationStartsWithPD_Negative");
		String assertionAssetName = "soa_documentationStartsWithPD_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testDocumentationStartsWithPD_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
}
