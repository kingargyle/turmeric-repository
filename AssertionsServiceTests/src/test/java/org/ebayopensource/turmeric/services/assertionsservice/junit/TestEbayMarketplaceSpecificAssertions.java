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

/**
 * @author szacharias
 *
 */
public class TestEbayMarketplaceSpecificAssertions extends BaseIndividualAssertionsTest{
	
	@Test
	public void testWsdlOperationMustHaveGetVersion_Positive(){
		System.out.println("\n***Starting testWsdlOperationMustHaveGetVersion_Positive");
		String assertionAssetName = "soa_wsdlOperationMustHaveGetVersion_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testWsdlOperationMustHaveGetVersion_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testWsdlOperationMustHaveGetVersion_Negative(){
		System.out.println("\n***Starting testWsdlOperationMustHaveGetVersion_Negative");
		String assertionAssetName = "soa_wsdlOperationMustHaveGetVersion_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testWsdlOperationMustHaveGetVersion_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testNoAnonymousComplexType_Positive(){
		System.out.println("\n***Starting testNoAnonymousComplexType_Positive");
		String assertionAssetName = "soa_noAnonymousComplexType_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testNoAnonymousComplexType_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}	
	
	@Test
	public void testNoAnonymousComplexType_Negative(){
		System.out.println("\n***Starting testNoAnonymousComplexType_Negative");
		String assertionAssetName = "soa_noAnonymousComplexType_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testNoAnonymousComplexType_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testXmlDocDefinesOnlyOneNamespace_Positive(){
		System.out.println("\n***Starting testXmlDocDefinesOnlyOneNamespace_Positive");
		String assertionAssetName = "soa_xmlDocDefinesOnlyOneNamespace_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testXmlDocDefinesOnlyOneNamespace_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testXmlDocDefinesOnlyOneNamespace_Negative(){
		System.out.println("\n***Starting testXmlDocDefinesOnlyOneNamespace_Negative");
		String assertionAssetName = "soa_xmlDocDefinesOnlyOneNamespace_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testXmlDocDefinesOnlyOneNamespace_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testDatatypeInlineNotImported_Positive(){
		System.out.println("\n***Starting testDatatypeInlineNotImported_Positive");
		String assertionAssetName = "soa_datatypeInlineNotImported_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testDatatypeInlineNotImported_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testDatatypeInlineNotImported_Negative(){
		System.out.println("\n***Starting testDatatypeInlineNotImported_Negative");
		String assertionAssetName = "soa_datatypeInlineNotImported_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testDatatypeInlineNotImported_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testSimpleTypeHasLt20Enum_Positive(){
		System.out.println("\n***Starting testSimpleTypeHasLt20Enum_Positive");
		String assertionAssetName = "soa_simpleTypeHasLt20Enum_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testSimpleTypeHasLt20Enum_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testSimpleTypeHasLt20Enum_Negative(){
		System.out.println("\n***Starting testSimpleTypeHasLt20Enum_Negative");
		String assertionAssetName = "soa_simpleTypeHasLt20Enum_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testSimpleTypeHasLt20Enum_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testOperationNameLowercase_Positive(){
		System.out.println("\n***Starting testOperationNameLowercase_Positive");
		String assertionAssetName = "soa_operationNameLowercase_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testOperationNameLowercase_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testOperationNameLowercase_Negative(){
		System.out.println("\n***Starting testOperationNameLowercase_Negative");
		String assertionAssetName = "soa_operationNameLowercase_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testOperationNameLowercase_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testSchemaTypeStartWithUpperCase_Positive(){
		System.out.println("\n***Starting testSchemaTypeStartWithUpperCase_Positive");
		String assertionAssetName = "soa_schemaTypeStartWithUpperCase_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testSchemaTypeStartWithUpperCase_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testSchemaTypeStartWithUpperCase_Negative(){
		System.out.println("\n***Starting testSchemaTypeStartWithUpperCase_Negative");
		String assertionAssetName = "soa_schemaTypeStartWithUpperCase_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testSchemaTypeStartWithUpperCase_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testNoHypenInSchemaName_Positive(){
		System.out.println("\n***Starting testNoHypenInSchemaName_Positive");
		String assertionAssetName = "soa_noHypenInSchemaName_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testNoHypenInSchemaName_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testNoHypenInSchemaName_Negative(){
		System.out.println("\n***Starting testNoHypenInSchemaName_Negative");
		String assertionAssetName = "soa_noHypenInSchemaName_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testNoHypenInSchemaName_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAttributesAreArrangedInRequiredOrder_Positive(){		
		System.out.println("\n***Starting testAttributesAreArrangedInRequiredOrder_Positive");
		String assertionAssetName = "soa_attributesAreArrangedInRequiredOrder_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testAttributesAreArrangedInRequiredOrder_Positive: "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAttributesAreArrangedInRequiredOrder_Negative(){	
		System.out.println("\n***Starting testAttributesAreArrangedInRequiredOrder_Negative");
		String assertionAssetName = "soa_attributesAreArrangedInRequiredOrder_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testAttributesAreArrangedInRequiredOrder_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testDocumentationElementWithProperVersion_Positive(){	
		System.out.println("\n***Starting testDocumentationElementWithProperVersion_Positive");
		String assertionAssetName = "soa_documentationElementWithProperVersion_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testDocumentationElementWithProperVersion_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testDocumentationElementWithProperVersion_Negative(){	
		System.out.println("\n***Starting testDocumentationElementWithProperVersion_Negative");
		String assertionAssetName = "soa_documentationElementWithProperVersion_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testDocumentationElementWithProperVersion_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testRequestTypeEndsWithRequest_Positive(){	
		System.out.println("\n***Starting testRequestTypeEndsWithRequest_Positive");
		String assertionAssetName = "soa_requestTypeEndsWithRequest_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testRequestTypeEndsWithRequest_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testRequestTypeEndsWithRequest_Negative(){	
		System.out.println("\n***Starting testRequestTypeEndsWithRequest_Negative");
		String assertionAssetName = "soa_requestTypeEndsWithRequest_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testrequestTypeEndsWithRequest_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testResponseTypeEndsWithResponse_Positive(){	
		System.out.println("\n***Starting testResponseTypeEndsWithResponse_Positive");
		String assertionAssetName = "soa_responseTypeEndsWithResponse_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testResponseTypeEndsWithResponse_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testResponseTypeEndsWithResponse_Negative(){	
		System.out.println("\n***Starting testResponseTypeEndsWithResponse_Negative");
		String assertionAssetName = "soa_responseTypeEndsWithResponse_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testResponseTypeEndsWithResponse_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
  }
