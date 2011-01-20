/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.wsiassertionprocessor;

import java.io.IOException;
import java.util.List;

import org.ebayopensource.turmeric.repositorymanager.assertions.AssertableResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.Assertion;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionContentSource;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionProcessorContext;


/**
 * The AssertionProcessor for XQuery assertions.
 * 
 * @author pcopeland
 */
public class AssertionProcessor
    implements org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor
{

	public AssertableResult applyAssertion(Assertion assertion,
			List<AssertionContent> content) throws IOException {
		return null;
	}

	public void close() {
		
	}

	public AssertionContentSource getAssertionContentSource(
			AssertionContent assertionContent) {
		return null;
	}

	public String getName() {
		return null;
	}

	public void init(AssertionProcessorContext context) {
		
	}	
}
