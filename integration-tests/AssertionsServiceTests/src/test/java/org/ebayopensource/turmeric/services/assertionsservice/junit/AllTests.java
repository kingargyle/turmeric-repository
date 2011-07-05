/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
//	BaseIndividualAssertionsTest.class,
//	TestAssertionGroups.class,
//	TestDocumentationStartsWithPDAssertions.class,
//	TestEbayMarketplaceSpecificAssertions.class,
//	TestElementRuleAssertions.class,
	TestExternalAssertionAndExternalArtifact.class
//	TestExternalAssertionAndInternalArtifact.class,
//	TestInternalAssertionAndExternalArtifact.class,
//	TestInternalAssertionAndInternalArtifact.class,
//	TestSchemaCheckerGeneralRulesAssertions.class,
//	TestUpaConstraintsAssertions.class,
//	TestWsdlAttributeRulesAssertions.class,
//	TestwsdlTypeRulesAssertions.class,
//	TestWSDLValidatorToolAssertions.class
})
public class AllTests {

}
