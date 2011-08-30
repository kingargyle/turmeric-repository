/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.xmlvalidator;

import java.util.List;

import org.ebayopensource.turmeric.repositorymanager.assertions.*;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionContentSource;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionProcessorContext;


/**
 * XML Validator AssertionProcessor validates XML for well formed-ness
 * and schema type usage.
 * 
 * @author pcopeland
 */
public class AssertionProcessor
    implements org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor
{
    
    /** The Constant assertionProcessorName. */
    static private final String assertionProcessorName = "XMLValidatorAssertionProcessor";

    /** The context. */
    private AssertionProcessorContext context;

    /* (non-Javadoc)
     * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#getName()
     */
    @Override
	public String getName() { return assertionProcessorName; }

    /**
     * Sets the context for this AssertionProcessor.
     *
     * @param apContext the ap context
     */
    @Override
	public void init(AssertionProcessorContext apContext)
    {
        this.context = apContext;
    }

    /**
     * Returns an AssertionContentSource for a given AssertionContent.
     *
     * @param assertionContent the assertion content
     * @return an AssertionContentSource for a given AssertionContent.
     */
    @Override
	public AssertionContentSource getAssertionContentSource(
            AssertionContent assertionContent)
    {
        return context.getAssertionContentSource(assertionContent);
    }

    /* (non-Javadoc)
     * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#applyAssertion(org.ebayopensource.turmeric.repositorymanager.assertions.Assertion, java.util.List)
     */
    @Override
	public AssertableResult applyAssertion(
            Assertion assertion,
            List<AssertionContent> content)
    {
        // TODO implement with java.xml.validation
        return null;
    }

    /* (non-Javadoc)
     * @see org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor#close()
     */
    @Override
	public void close() {}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() { return getName(); }
}
