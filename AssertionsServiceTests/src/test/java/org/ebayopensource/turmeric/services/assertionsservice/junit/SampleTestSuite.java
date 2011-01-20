/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import junit.framework.TestSuite;

/**
 * @author szacharias
 *
 */
public class SampleTestSuite {

	public static junit.framework.Test suite() { 
        
		TestSuite suite = new TestSuite("AssertionsService Tests");		
		
/*		suite.addTestSuite(TestEbayMarketplaceSpecificAssertions.class);
        suite.addTestSuite(TestElementRuleAssertions.class);               
        suite.addTestSuite(TestWsdlAttributeRulesAssertions.class);
        suite.addTestSuite(TestwsdlTypeRulesAssertions.class);
        suite.addTestSuite(TestWSDLValidatorToolAssertions.class);
        suite.addTestSuite(TestDocumentationStartsWithPDAssertions.class);
        
        suite.addTestSuite(TestAssertionGroups.class);
        suite.addTestSuite(TestExternalAssertionAndExternalArtifact.class);
        suite.addTestSuite(TestExternalAssertionAndInternalArtifact.class);
        suite.addTestSuite(TestInternalAssertionAndExternalArtifact.class);
        suite.addTestSuite(TestInternalAssertionAndInternalArtifact.class);*/

        return suite; 
   }
	
}
	
