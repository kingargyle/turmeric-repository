/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.xmlprologvalidator;

import java.util.List;

import org.ebayopensource.turmeric.repositorymanager.assertions.*;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionContentSource;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionProcessorContext;


/**
 * @author pcopeland
 */
public class AssertionProcessor
    implements org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor
{
    static private final String assertionProcessorName = "XMLPrologAssertionProcessor";

    private AssertionProcessorContext context;

    public String getName() { return assertionProcessorName; }

    /**
     * Sets the context for this AssertionProcessor.
     * 
     * @param the context for this AssertionProcessor.
     */
    public void init(AssertionProcessorContext apContext)
    {
        this.context = apContext;
    }

    /**
     * Returns an AssertionContentSource for a given AssertionContent.
     * 
     * @param assertionContent 
     * @return an AssertionContentSource for a given AssertionContent.
     */
    public AssertionContentSource getAssertionContentSource(
            AssertionContent assertionContent)
    {
        return context.getAssertionContentSource(assertionContent);
    }

    public AssertableResult applyAssertion(
            Assertion assertion,
            List<AssertionContent> content)
    {
        // TODO extract prolog text and validate that with XQuery
        return null;
    }

    public void close() {}

    public String toString() { return getName(); }
}
