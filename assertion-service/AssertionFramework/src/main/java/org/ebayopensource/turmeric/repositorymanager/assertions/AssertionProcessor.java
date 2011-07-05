/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

import java.io.IOException;
import java.util.List;

import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionContentSource;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionProcessorContext;


/**
 * An AssertionProcessor processes Assertions with a assertion
 * script language.
 * 
 * @author pcopeland
 */
public interface AssertionProcessor
{
    public static final String XQUERY_TYPE = "XQuery";
    public static final String XMLPROLOGVALIDATOR_TYPE = "XMLPrologValidator";
    public static final String XMLSCHEMAVALIDATOR_TYPE = "XMLValidator";

    /**
     * Returns the name of this AssertionProcessor.
     * 
     * @return the name of this AssertionProcessor.
     */
    public String getName();

    /**
     * Sets the context for this AssertionProcessor.
     * 
     * @param the context for this AssertionProcessor.
     */
    public void init(AssertionProcessorContext context);

    /**
     * Returns an AssertionContentSource for a given AssertionContent.
     * 
     * @param assertionContent 
     * @return an AssertionContentSource for a given AssertionContent.
     */
    public AssertionContentSource getAssertionContentSource(
            AssertionContent assertionContent);

    /**
     * Returns the result of applying an Assertion to an AssertionContent.
     * 
     * @param assertion the Assertion that is applied.
     * @param assertionContent the AssertionContent that is validated.
     * @return the result of applying an Assertion to an AssertionContent.
     * @throws IOException
     */
    public AssertableResult applyAssertion(
            Assertion assertion,
            List<AssertionContent> content)
        throws IOException;

    /**
     * Releases resources used by this AssertionProcessor instance. The
     * applyAssertion() method should not be called after close() returns.
     * Recommended practice is to call applyAssertion() within a try
     * block and close the AssertionProcessor in the finally clause.
     */
    public void close();
}
