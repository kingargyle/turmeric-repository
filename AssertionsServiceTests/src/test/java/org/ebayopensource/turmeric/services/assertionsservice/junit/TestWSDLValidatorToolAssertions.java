/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import org.ebayopensource.turmeric.services.assertionsservice.junit.BaseIndividualAssertionsTest;
import org.junit.Test;

import junit.framework.Assert;

/**
 * @author szacharias
 *
 */
public class TestWSDLValidatorToolAssertions extends BaseIndividualAssertionsTest{
	
	@Test
	public void testDocumentLiteral_Positive(){
		System.out.println("\n***Starting testDocumentLiteral_Positive");
		String assertionAssetName = "soa_documentLiteral_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		
		
		System.out.println("testDocumentLiteral_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testDocumentLiteral_Negative(){
		System.out.println("\n***Starting testDocumentLiteral_Negative");
		String assertionAssetName = "soa_documentLiteral_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		
		
		System.out.println("testDocumentLiteral_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testOperationsHave0or1RequestParam_Positive(){
		System.out.println("\n***Starting testOperationsHave0or1RequestParam_Positive");
		String assertionAssetName = "soa_operationsHave0or1RequestParam_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testOperationsHave0or1RequestParam_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testOperationsHave0or1RequestParam_Negative(){
		System.out.println("\n***Starting testOperationsHave0or1RequestParam_Negative");
		String assertionAssetName = "soa_operationsHave0or1RequestParam_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testOperationsHave0or1RequestParam_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}	
	
	@Test
	public void testServiceOperationCannotReturnArray_Positive(){
		System.out.println("\n***Starting testServiceOperationCannotReturnArray_Positive");
		String assertionAssetName = "soa_serviceOperationCannotReturnArray_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
				
		System.out.println("testServiceOperationCannotReturnArray_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testServiceOperationCannotReturnArray_Negative(){
		System.out.println("\n***Starting testServiceOperationCannotReturnArray_Negative");
		String assertionAssetName = "soa_serviceOperationCannotReturnArray_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
				
		System.out.println("testServiceOperationCannotReturnArray_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testPortTypeNameStandard_Positive(){
		System.out.println("\n***Starting testPortTypeNameStandard_Positive");
		String assertionAssetName = "soa_portTypeNameStandard_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testPortTypeNameStandard_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testPortTypeNameStandard_Negative(){
		System.out.println("\n***Starting testPortTypeNameStandard_Negative");
		String assertionAssetName = "soa_portTypeNameStandard_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testPortTypeNameStandard_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testServicePortNameStandard_Positive(){
		System.out.println("\n***Starting testServicePortNameStandard_Positive");
		String assertionAssetName = "soa_servicePortNameStandard_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testServicePortNameStandard_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testServicePortNameStandard_Negative(){
		System.out.println("\n***Starting testServicePortNameStandard_Negative");
		String assertionAssetName = "soa_servicePortNameStandard_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testServicePortNameStandard_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testServiceBindingNameStandard_Positive(){
		System.out.println("\n***Starting testServiceBindingNameStandard_Positive");
		String assertionAssetName = "soa_serviceBindingNameStandard_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);

		
		System.out.println("testServiceBindingNameStandard_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testServiceBindingNameStandard_Negative(){
		System.out.println("\n***Starting testServiceBindingNameStandard_Negative");
		String assertionAssetName = "soa_serviceBindingNameStandard_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		
		
		System.out.println("testServiceBindingNameStandard_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testNoDuplicateElementsInsideComplexType_Positive(){
		System.out.println("\n***Starting testNoDuplicateElementsInsideComplexType_Positive");
		String assertionAssetName = "soa_noDuplicateElementsInsideComplexType_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testNoDuplicateElementsInsideComplexType_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testNoDuplicateElementsInsideComplexType_Negative(){
		System.out.println("\n***Starting testNoDuplicateElementsInsideComplexType_Negative");
		String assertionAssetName = "soa_noDuplicateElementsInsideComplexType_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		
		
		System.out.println("testNoDuplicateElementsInsideComplexType_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testServiceNameUppercase_Positive(){
		System.out.println("\n***Starting testServiceNameUppercase_Positive");
		String assertionAssetName = "soa_serviceNameUppercase_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);

		
		System.out.println("testServiceNameUppercase_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testServiceNameUppercase_Negative(){
		System.out.println("\n***Starting testServiceNameUppercase_Negative");
		String assertionAssetName = "soa_serviceNameUppercase_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testServiceNameUppercase_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testComplexTypeNameUppercase_Positive(){
		System.out.println("\n***Starting testComplexTypeNameUppercase_Positive");
		String assertionAssetName = "soa_complexTypeNameUppercase_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testComplexTypeNameUppercase_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testComplexTypeNameUppercase_Negative(){
		System.out.println("\n***Starting testComplexTypeNameUppercase_Negative");
		String assertionAssetName = "soa_complexTypeNameUppercase_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		
		
		System.out.println("testComplexTypeNameUppercase_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testServiceLocationUrlPrefix_Positive(){
		System.out.println("\n***Starting testServiceLocationUrlPrefix_Positive");
		String assertionAssetName = "soa_serviceLocationUrlPrefix_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testServiceLocationUrlPrefix_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testServiceLocationUrlPrefix_Negative(){
		System.out.println("\n***Starting testServiceLocationUrlPrefix_Negative");
		String assertionAssetName = "soa_serviceLocationUrlPrefix_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testServiceLocationUrlPrefix_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testEbayMarketPlaceHaveOnlyOneNamespace_Positive(){
		System.out.println("\n***Starting testEbayMarketPlaceHaveOnlyOneNamespace_Positive");
		String assertionAssetName = "soa_ebayMarketPlaceHaveOnlyOneNamespace_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testEbayMarketPlaceHaveOnlyOneNamespace_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testEbayMarketPlaceHaveOnlyOneNamespace_Negative(){
		System.out.println("\n***Starting testEbayMarketPlaceHaveOnlyOneNamespace_Negative");
		String assertionAssetName = "soa_ebayMarketPlaceHaveOnlyOneNamespace_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testEbayMarketPlaceHaveOnlyOneNamespace_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testXsAnyInsideComplexType_Positive(){	
		System.out.println("\n***Starting testXsAnyInsideComplexType_Positive");
		String assertionAssetName = "soa_xsAnyInsideComplexType_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testXsAnyInsideComplexType_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}	
	
	@Test
	public void testXsAnyInsideComplexType_Negative(){	
		System.out.println("\n***Starting testXsAnyInsideComplexType_Negative");
		String assertionAssetName = "soa_xsAnyInsideComplexType_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testXsAnyInsideComplexType_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testComplexTypeWithAllOrChoice_Positive(){	
		System.out.println("\n***Starting testComplexTypeWithAllOrChoice_Positive");
		String assertionAssetName = "soa_complexTypeWithAllOrChoice_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testComplexTypeWithAllOrChoice_Positive : "  +  result);
		Assert.assertEquals(PASS, result);
	}	
	
	@Test
	public void testComplexTypeWithAllOrChoice_Negative(){
		System.out.println("\n***Starting testComplexTypeWithAllOrChoice_Negative");
		String assertionAssetName = "soa_complexTypeWithAllOrChoice_assertion";	
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testComplexTypeWithAllOrChoice_Negative : "  +  result);
		Assert.assertEquals(PASS, result);
	}

  }
