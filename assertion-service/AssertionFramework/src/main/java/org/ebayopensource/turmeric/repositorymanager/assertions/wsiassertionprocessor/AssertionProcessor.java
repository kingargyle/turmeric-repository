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

	/* (non-Javadoc)
	 * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#applyAssertion(org.ebayopensource.turmeric.repositorymanager.assertions.Assertion, java.util.List)
	 */
	@Override
	public AssertableResult applyAssertion(Assertion assertion,
			List<AssertionContent> content) throws IOException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#close()
	 */
	@Override
	public void close() {
		
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#getAssertionContentSource(org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent)
	 */
	@Override
	public AssertionContentSource getAssertionContentSource(
			AssertionContent assertionContent) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#getName()
	 */
	@Override
	public String getName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#init(org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionProcessorContext)
	 */
	@Override
	public void init(AssertionProcessorContext context) {
		
	}	
}
