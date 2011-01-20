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

import junit.framework.TestCase;

public class TestwsdlTypeRulesAssertions extends BaseIndividualAssertionsTest {
	
	@Test
	public void testElementsMustNotUseRefAttribute_Positive(){
		System.out.println("\n***Starting testElementsMustNotUseRefAttribute_Positive");
		String assertionAssetName = "soa_elementsMustNotUseRefAttribute_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testUseEqualsRequiredNotAllowed_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementsMustNotUseRefAttribute_Negative(){
		System.out.println("\n***Starting testElementsMustNotUseRefAttribute_Negative");
		String assertionAssetName = "soa_elementsMustNotUseRefAttribute_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testElementsMustNotUseRefAttribute_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testComplexTypeWithinComplexType_Positive(){
		System.out.println("\n***Starting testComplexTypeWithinComplexType_Positive");
		String assertionAssetName = "soa_complexTypeWithinComplexType_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testUseEqualsRequiredNotAllowed_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testComplexTypeWithinComplexType_Negative(){	
		System.out.println("\n***Starting testComplexTypeWithinComplexType_Negative");
		String assertionAssetName = "soa_complexTypeWithinComplexType_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testComplexTypeWithinComplexType_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testArrayShouldNotHaveXsAny_Positive(){		
		System.out.println("\n***Starting testArrayShouldNotHaveXsAny_Positive");
		String assertionAssetName = "soa_arrayShouldNotHaveXsAny_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testUseEqualsRequiredNotAllowed_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}	
	
	@Test
	public void testArrayShouldNotHaveXsAny_Negative(){		
		System.out.println("\n***Starting testArrayShouldNotHaveXsAny_Negative");
		String assertionAssetName = "soa_arrayShouldNotHaveXsAny_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testArrayShouldNotHaveXsAny_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testTokenRestrictedTypeName_Positive(){	
		System.out.println("\n***Starting testTokenRestrictedTypeName_Positive");
		String assertionAssetName = "soa_tokenRestrictedTypeName_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testUseEqualsRequiredNotAllowed_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}	
	
	@Test
	public void testTokenRestrictedTypeName_Negative(){	
		System.out.println("\n***Starting testTokenRestrictedTypeName_Negative");
		String assertionAssetName = "soa_tokenRestrictedTypeName_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testTokenRestrictedTypeName_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testRequestResponseTypeMustNotHaveAny_Positive(){	
		System.out.println("\n***Starting testRequestResponseTypeMustNotHaveAny_Positive");
		String assertionAssetName = "soa_requestResponseTypeMustNotHaveAny_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testRequestResponseTypeMustNotHaveAny_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}	
	
	@Test
	public void testRequestResponseTypeMustNotHaveAny_Negative(){	
		System.out.println("\n***Starting testRequestResponseTypeMustNotHaveAny_Negative");
		String assertionAssetName = "soa_requestResponseTypeMustNotHaveAny_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testRequestResponseTypeMustNotHaveAny_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
}
