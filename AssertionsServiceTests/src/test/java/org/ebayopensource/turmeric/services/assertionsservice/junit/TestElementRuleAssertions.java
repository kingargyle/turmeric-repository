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
public class TestElementRuleAssertions extends BaseIndividualAssertionsTest{
		
	@Test
	public void testElementNameBeginWithLowerCase_Positive(){
		System.out.println("\n***Starting testElementNameBeginWithLowerCase_Positive");
		String assertionAssetName = "soa_elementNameBeginWithLowerCase_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testElementNameBeginWithLowerCase_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameBeginWithLowerCase_Negative(){
		System.out.println("\n***Starting testElementNameBeginWithLowerCase_Negative");
		String assertionAssetName = "soa_elementNameBeginWithLowerCase_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testElementNameBeginWithLowerCase_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameContainingWordURLorURI_Positive(){
		System.out.println("\n***Starting testElementNameContainingWordURLorURI_Positive");
		String assertionAssetName = "soa_elementNameContainingWordURLorURI_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testElementNameContainingWordURLorURI_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameContainingWordURLorURI_Negative(){
		System.out.println("\n***Starting testElementNameContainingWordURLorURI_Negative");
		String assertionAssetName = "soa_elementNameContainingWordURLorURI_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testElementNameContainingWordURLorURI_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithURL.wsdl" which has an element name containing 
	//the word "URL" but not of type "anyURI"	
	@Test
	public void testElementNameContainingWordURLorURI_WithURL(){
		System.out.println("\n***Starting testElementNameContainingWordURLorURI_WithURL");
		String assertionAssetName = "soa_elementNameContainingWordURLorURI_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithURL.wsdl", NEGATIVE_CASE);		
		
		System.out.println("testElementNameContainingWordURLorURI_WithURL : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithURI.wsdl" which has an element name containing 
	//the word "URI" but not of type "anyURI"	
	@Test
	public void testElementNameContainingWordURLorURI_WithURI(){
		System.out.println("\n***Starting testElementNameContainingWordURLorURI_WithURI");
		String assertionAssetName = "soa_elementNameContainingWordURLorURI_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithURI.wsdl", NEGATIVE_CASE);		
		
		System.out.println("testElementNameContainingWordURLorURI_WithURI : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testMaxoccursValueShouldBeUnbounded_Positive(){
		System.out.println("\n***Starting testMaxoccursValueShouldBeUnbounded_Positive");
		String assertionAssetName = "soa_maxoccursValueShouldBeUnbounded_assertion";
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testMaxoccursValueShouldBeUnbounded_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testMaxoccursValueShouldBeUnbounded_Negative(){
		System.out.println("\n***Starting testMaxoccursValueShouldBeUnbounded_Negative");
		String assertionAssetName = "soa_maxoccursValueShouldBeUnbounded_assertion";
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testMaxoccursValueShouldBeUnbounded_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameWhenMaxoccursIsUnbounded_Positive(){
		System.out.println("\n***Starting testElementNameWhenMaxoccursIsUnbounded_Positive");
		String assertionAssetName = "soa_elementNameWhenMaxoccursIsUnbounded_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);

		
		System.out.println("testElementNameWhenMaxoccursIsUnbounded_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameWhenMaxoccursIsUnbounded_Negative(){
		System.out.println("\n***Starting testElementNameWhenMaxoccursIsUnbounded_Negative");
		String assertionAssetName = "soa_elementNameWhenMaxoccursIsUnbounded_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);

		
		System.out.println("testElementNameWhenMaxoccursIsUnbounded_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testLengthRestrictionOnStrings_Positive(){
		System.out.println("\n***Starting testLengthRestrictionOnStrings_Positive");
		String assertionAssetName = "soa_lengthRestrictionOnStrings_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);

		
		System.out.println("testLengthRestrictionOnStrings_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testLengthRestrictionOnStrings_Negative(){
		System.out.println("\n***Starting testLengthRestrictionOnStrings_Negative");
		String assertionAssetName = "soa_lengthRestrictionOnStrings_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);

		
		System.out.println("testLengthRestrictionOnStrings_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameEndingWithWordID_Positive(){
		System.out.println("\n***Starting testElementNameEndingWithWordID_Positive");
		String assertionAssetName = "soa_elementNameEndingWithWordID_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);

		
		System.out.println("testElementNameEndingWithWordID_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameEndingWithWordID_Negative(){
		System.out.println("\n***Starting testElementNameEndingWithWordID_Negative");
		String assertionAssetName = "soa_elementNameEndingWithWordID_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);

		
		System.out.println("testElementNameEndingWithWordID_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAmountTypeElementNameStandard_Positive(){
		System.out.println("\n***Starting testAmountTypeElementNameStandard_Positive");
		String assertionAssetName = "soa_amountTypeElementNameStandard_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testAmountTypeElementNameStandard_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAmountTypeElementNameStandard_Negative(){
		System.out.println("\n***Starting testAmountTypeElementNameStandard_Negative");
		String assertionAssetName = "soa_amountTypeElementNameStandard_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testAmountTypeElementNameStandard_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithAmount.wsdl" which has an element name ending 
	//with the word "Amount" and of type "AmountType"	
	@Test
	public void testAmountTypeElementNameStandard_WithAmount(){
		System.out.println("\n***Starting testAmountTypeElementNameStandard_WithAmount");
		String assertionAssetName = "soa_amountTypeElementNameStandard_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithAmount.wsdl", POSITIVE_CASE);		
		
		System.out.println("testAmountTypeElementNameStandard_WithAmount : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithCost.wsdl" which has an element name ending 
	//with the word "Cost" and of type "AmountType"	
	@Test
	public void testAmountTypeElementNameStandard_WithCost(){
		System.out.println("\n***Starting testAmountTypeElementNameStandard_WithCost");
		String assertionAssetName = "soa_amountTypeElementNameStandard_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithCost.wsdl", POSITIVE_CASE);		
		
		System.out.println("testAmountTypeElementNameStandard_WithCost : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithFee.wsdl" which has an element name ending 
	//with the word "Fee" and of type "AmountType"	
	@Test
	public void testAmountTypeElementNameStandard_WithFee(){
		System.out.println("\n***Starting testAmountTypeElementNameStandard_WithFee");
		String assertionAssetName = "soa_amountTypeElementNameStandard_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithFee.wsdl", POSITIVE_CASE);		
		
		System.out.println("testAmountTypeElementNameStandard_WithFee : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAmountTypeElementNameStandardConvrse_Positive(){
		System.out.println("\n***Starting testAmountTypeElementNameStandardConvrse_Positive");
		String assertionAssetName = "soa_amountTypeElementNameStandardConvrse_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		
		
		System.out.println("testAmountTypeElementNameStandardConvrse_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAmountTypeElementNameStandardConvrse_Negative(){
		System.out.println("\n***Starting testAmountTypeElementNameStandardConvrse_Negative");
		String assertionAssetName = "soa_amountTypeElementNameStandardConvrse_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		
		
		System.out.println("testAmountTypeElementNameStandardConvrse_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithAmount.wsdl" which has an element name ending 
	//with the word "Amount" but not of type "AmountType"	
	@Test
	public void testAmountTypeElementNameStandardConvrse_WithAmount(){
		System.out.println("\n***Starting testAmountTypeElementNameStandardConvrse_WithAmount");
		String assertionAssetName = "soa_amountTypeElementNameStandardConvrse_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithAmount.wsdl", NEGATIVE_CASE);		
		
		System.out.println("testAmountTypeElementNameStandardConvrse_WithAmount : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithCost.wsdl" which has an element name ending 
	//with the word "Cost" but not of type "AmountType"	
	@Test
	public void testAmountTypeElementNameStandardConvrse_WithCost(){
		System.out.println("\n***Starting testAmountTypeElementNameStandardConvrse_WithCost");
		String assertionAssetName = "soa_amountTypeElementNameStandardConvrse_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithCost.wsdl", NEGATIVE_CASE);		
		
		System.out.println("testAmountTypeElementNameStandardConvrse_WithCost : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithFee.wsdl" which has an element name ending 
	//with the word "Fee" but not of type "AmountType"	
	@Test
	public void testAmountTypeElementNameStandardConvrse_WithFee(){
		System.out.println("\n***Starting testAmountTypeElementNameStandardConvrse_WithFee");
		String assertionAssetName = "soa_amountTypeElementNameStandardConvrse_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithFee.wsdl", NEGATIVE_CASE);		
		
		System.out.println("testAmountTypeElementNameStandardConvrse_WithFee : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameContainingWordPercent_Positive(){
		System.out.println("\n***Starting testElementNameContainingWordPercent_Positive");
		String assertionAssetName = "soa_elementNameContainingWordPercent_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		
		
		System.out.println("testElementNameContainingWordPercent_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameContainingWordPercent_Negative(){
		System.out.println("\n***Starting testElementNameContainingWordPercent_Negative");
		String assertionAssetName = "soa_elementNameContainingWordPercent_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		
		
		System.out.println("testElementNameContainingWordPercent_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameEndingWithWordCount_Positive(){
		System.out.println("\n***Starting testElementNameEndingWithWordCount_Positive");
		String assertionAssetName = "soa_elementNameEndingWithWordCount_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		
		
		System.out.println("testElementNameEndingWithWordCount_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameEndingWithWordCount_Negative(){
		System.out.println("\n***Starting testElementNameEndingWithWordCount_Negative");
		String assertionAssetName = "soa_elementNameEndingWithWordCount_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testElementNameEndingWithWordCount_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameEndingWithWordDateOrTime_Positive(){
		System.out.println("\n***Starting testElementNameEndingWithWordDateOrTime_Positive");
		String assertionAssetName = "soa_elementNameEndingWithWordDateOrTime_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testElementNameEndingWithWordDateOrTime_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameEndingWithWordDateOrTime_Negative(){
		System.out.println("\n***Starting testElementNameEndingWithWordDateOrTime_Negative");
		String assertionAssetName = "soa_elementNameEndingWithWordDateOrTime_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		
		
		System.out.println("testElementNameEndingWithWordDateOrTime_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithDate.wsdl" which has an element name ending 
	//with the word "Date" but not of type "dateTime"	
	@Test
	public void testElementNameEndingWithWordDateOrTime_WithDate(){
		System.out.println("\n***Starting testElementNameEndingWithWordDateOrTime_WithDate");
		String assertionAssetName = "soa_elementNameEndingWithWordDateOrTime_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithDate.wsdl", NEGATIVE_CASE);		
		
		System.out.println("testElementNameEndingWithWordDateOrTime_WithDate : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithTime.wsdl" which has an element name ending 
	//with the word "Time" but not of type "dateTime"	
	@Test
	public void testElementNameEndingWithWordDateOrTime_WithTime(){
		System.out.println("\n***Starting testElementNameEndingWithWordDateOrTime_WithTime");
		String assertionAssetName = "soa_elementNameEndingWithWordDateOrTime_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithTime.wsdl", NEGATIVE_CASE);		
		
		System.out.println("testElementNameEndingWithWordDateOrTime_WithTime : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testCodeTypeElementNameStandard_Positive(){
		System.out.println("\n***Starting testCodeTypeElementNameStandard_Positive");
		String assertionAssetName = "soa_codeTypeElementNameStandard_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		
		
		System.out.println("testCodeTypeElementNameStandard_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testCodeTypeElementNameStandard_Negative(){
		System.out.println("\n***Starting testCodeTypeElementNameStandard_Negative");
		String assertionAssetName = "soa_codeTypeElementNameStandard_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		
		
		System.out.println("testCodeTypeElementNameStandard_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testBooleanTypeElementNameStandard_Positive(){
		System.out.println("\n***Starting testBooleanTypeElementNameStandard_Positive");
		String assertionAssetName = "soa_booleanTypeElementNameStandard_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
	
		
		System.out.println("testBooleanTypeElementNameStandard_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testBooleanTypeElementNameStandard_Negative(){
		System.out.println("\n***Starting testBooleanTypeElementNameStandard_Negative");
		String assertionAssetName = "soa_booleanTypeElementNameStandard_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testBooleanTypeElementNameStandard_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithIs.wsdl" which has an element name starting 
	//with the word "is" and is of type "Boolean"	
	@Test
	public void testBooleanTypeElementNameStandard_WithIs(){
		System.out.println("\n***Starting testBooleanTypeElementNameStandard_WithIs");
		String assertionAssetName = "soa_booleanTypeElementNameStandard_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithIs.wsdl", NEGATIVE_CASE);
		
		System.out.println("testBooleanTypeElementNameStandard_WithIs : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	//This test case requires an input artifact "Sample_WithHas.wsdl" which has an element name starting 
	//with the word "has" and is of type "Boolean"	
	@Test
	public void testBooleanTypeElementNameStandard_WithHas(){
		System.out.println("\n***Starting testBooleanTypeElementNameStandard_WithHas");
		String assertionAssetName = "soa_booleanTypeElementNameStandard_assertion";
		
		String result = testAssertion(assertionAssetName, "Sample_WithHas.wsdl", NEGATIVE_CASE);
		
		System.out.println("testBooleanTypeElementNameStandard_WithHas : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameContainingWordEbay_Positive(){
		System.out.println("\n***Starting testElementNameContainingWordEbay_Positive");
		String assertionAssetName = "soa_elementNameContainingWordEbay_assertion";
		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		
		
		System.out.println("testElementNameContainingWordEbay_Positive : "  +  result);
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testElementNameContainingWordEbay_Negative(){
		System.out.println("\n***Starting testElementNameContainingWordEbay_Negative");
		String assertionAssetName = "soa_elementNameContainingWordEbay_assertion";
		
		String result = testAssertionForNegativeScenario(assertionAssetName);
	
		
		System.out.println("testElementNameContainingWordEbay_Negative : "  +  result);
		TestCase.assertEquals(PASS, result);
	}

  }
