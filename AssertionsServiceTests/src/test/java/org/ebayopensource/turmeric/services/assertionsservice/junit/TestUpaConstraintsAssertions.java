/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import junit.framework.TestCase;

import org.junit.Test;

public class TestUpaConstraintsAssertions extends BaseIndividualAssertionsTest {
	
	@Test
	public void testAllOperationReqRspShouldExtendSameBaseTypes_Positive(){
		System.out.println("\n***Starting testAllOperationReqRspShouldExtendSameBaseTypes_Positive");
		String assertionAssetName = "soa_AllOperationReqRspShouldExtendSameBaseTypes_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testAllOperationReqRspShouldExtendSameBaseTypes_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAllOperationReqRspShouldExtendSameBaseTypes_Negative(){
		System.out.println("\n***Starting testAllOperationReqRspShouldExtendSameBaseTypes_Negative");
		String assertionAssetName = "soa_AllOperationReqRspShouldExtendSameBaseTypes_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testAllOperationReqRspShouldExtendSameBaseTypes_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
		
	@Test
	public void testAttributesCanBeAnyPrimitiveTypeOnly_Positive(){
		System.out.println("\n***Starting testAttributesCanBeAnyPrimitiveTypeOnly_Positive");
		String assertionAssetName = "soa_attributesCanBeAnyPrimitiveTypeOnly_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testAttributesCanBeAnyPrimitiveTypeOnly_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAttributesCanBeAnyPrimitiveTypeOnly_Negative(){
		System.out.println("\n***Starting testAttributesCanBeAnyPrimitiveTypeOnly_Negative");
		String assertionAssetName = "soa_attributesCanBeAnyPrimitiveTypeOnly_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testAttributesCanBeAnyPrimitiveTypeOnly_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testBaseReqRspMustHaveExtensionElement_Positive(){
		System.out.println("\n***Starting testBaseReqRspMustHaveExtensionElement_Positive");
		String assertionAssetName = "soa_baseReqRspMustHaveExtensionElement_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testBaseReqRspMustHaveExtensionElement_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testBaseReqRspMustHaveExtensionElement_Negative(){
		System.out.println("\n***Starting testBaseReqRspMustHaveExtensionElement_Negative");
		String assertionAssetName = "soa_baseReqRspMustHaveExtensionElement_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testBaseReqRspMustHaveExtensionElement_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testBaseReqRspMustNotHaveXsAny_Positive(){
		System.out.println("\n***Starting testBaseReqRspMustNotHaveXsAny_Positive");
		String assertionAssetName = "soa_baseReqRspMustNotHaveXsAny_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testBaseReqRspMustNotHaveXsAny_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testBaseReqRspMustNotHaveXsAny_Negative(){
		System.out.println("\n***Starting testBaseReqRspMustNotHaveXsAny_Negative");
		String assertionAssetName = "soa_baseReqRspMustNotHaveXsAny_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testBaseReqRspMustNotHaveXsAny_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testWsdlDefineOnlyOneBaseReqRspTypes_Positive(){
		System.out.println("\n***Starting testWsdlDefineOnlyOneBaseReqRspTypes_Positive");
		String assertionAssetName = "soa_WsdlDefineOnlyOneBaseReqRspTypes_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testWsdlDefineOnlyOneBaseReqRspTypes_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testWsdlDefineOnlyOneBaseReqRspTypes_Negative(){
		System.out.println("\n***Starting testWsdlDefineOnlyOneBaseReqRspTypes_Negative");
		String assertionAssetName = "soa_WsdlDefineOnlyOneBaseReqRspTypes_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testWsdlDefineOnlyOneBaseReqRspTypes_Negative: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
}
