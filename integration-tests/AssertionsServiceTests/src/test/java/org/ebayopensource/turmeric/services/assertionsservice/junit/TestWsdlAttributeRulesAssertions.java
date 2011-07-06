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

public class TestWsdlAttributeRulesAssertions extends BaseIndividualAssertionsTest {
	
	@Test
	public void testAttributesAreUsedForMetaInf_Positive(){	
		System.out.println("\n***Starting testAttributesAreUsedForMetaInf_Positive");
		String assertionAssetName = "soa_attributesAreUsedForMetaInf_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testAttributesAreUsedForMetaInf_Positive: "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
    public void testAttributesAreUsedForMetaInf_Negative(){		
    	System.out.println("\n***Starting testAttributesAreUsedForMetaInf_Negative");
		String assertionAssetName = "soa_attributesAreUsedForMetaInf_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testAttributesAreUsedForMetaInf_Negative: "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
    public void testUseEqualsRequiredNotAllowed_Positive(){	
    	System.out.println("\n***Starting testUseEqualsRequiredNotAllowed_Positive");
		String assertionAssetName = "soa_useEqualsRequiredNotAllowed_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testUseEqualsRequiredNotAllowed_Positive: "  +  result);
		Assert.assertEquals(PASS, result);
	}
	
	@Test
    public void testUseEqualsRequiredNotAllowed_Negative(){		
    	System.out.println("\n***Starting testUseEqualsRequiredNotAllowed_Negative");
		String assertionAssetName = "soa_useEqualsRequiredNotAllowed_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testUseEqualsRequiredNotAllowed_Negative: "  +  result);
		Assert.assertEquals(PASS, result);
	}  
	
	@Test
    public void testNameEndsWithIdIsCaseSensetive_Positive(){	
    	System.out.println("\n***Starting testNameEndsWithIdIsCaseSensetive_Positive");
		String assertionAssetName = "soa_nameEndsWithIdIsCaseSensetive_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testNameEndsWithIdIsCaseSensetive_Positive: "  +  result);
		Assert.assertEquals(PASS, result);	
	}
	
	@Test
    public void testNameEndsWithIdIsCaseSensetive_Negative(){	
    	System.out.println("\n***Starting testNameEndsWithIdIsCaseSensetive_Negative");
		String assertionAssetName = "soa_nameEndsWithIdIsCaseSensetive_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testNameEndsWithIdIsCaseSensetive_Negative: "  +  result);
		Assert.assertEquals(PASS, result);	
	}
	
	@Test
    public void testAttributesAreArrangedInRequiredOrder_Positive(){	
    	System.out.println("\n***Starting testAttributesAreArrangedInRequiredOrder_Positive");
		String assertionAssetName = "soa_attributesAreArrangedInRequiredOrder_assertion";		
		String result = testAssertionForPositiveScenario(assertionAssetName);
		System.out.println("testAttributesAreArrangedInRequiredOrder_Positive: "  +  result);
		Assert.assertEquals(PASS, result);	
	}
	
	@Test
    public void testAttributesAreArrangedInRequiredOrder_Negative(){	
    	System.out.println("\n***Starting testAttributesAreArrangedInRequiredOrder_Negative");
		String assertionAssetName = "soa_attributesAreArrangedInRequiredOrder_assertion";		
		String result = testAssertionForNegativeScenario(assertionAssetName);
		System.out.println("testAttributesAreArrangedInRequiredOrder_Negative: "  +  result);
		Assert.assertEquals(PASS, result);	
	}
  }
