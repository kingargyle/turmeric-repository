/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import java.util.Collection;
import java.util.List;

import org.ebayopensource.turmeric.repositorymanager.assertions.AssertableResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionGroup;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionResultItem;
import org.ebayopensource.turmeric.repositorymanager.assertions.ValidationResult;


/**
 * The Class BasicAssertionResult.
 */
public class BasicAssertionResult
    implements AssertionResult
{
    
    /** The assertable result. */
    private AssertableResult assertableResult;

    /**
     * Instantiates a new basic assertion result.
     *
     * @param assertableResult the assertable result
     */
    public BasicAssertionResult(AssertableResult assertableResult)
    {
        this.assertableResult = assertableResult;
    }

    /**
     * Returns the name of the Assertion that generated this result.
     * 
     * @return the name of the Assertion that generated this result.
     */
    @Override
	public String getAssertionName()
    {
        return assertableResult.getAssertable().getName();
    }

    /**
     * Returns the version of the Assertion that generated this result.
     * 
     * @return the version of the Assertion that generated this result.
     */
    @Override
	public String getAssertionVersion()
    {
        return assertableResult.getAssertable().getVersion();
    }

    /**
     * Returns the description of the Assertion that generated this result.
     * 
     * @return the description of the Assertion that generated this result.
     */
    @Override
	public String getAssertionDescription()
    {
        return assertableResult.getAssertable().getDescription();
    }

    /**
     * Returns the validation result for this AssertionResult. The
     * value is ValidationResult.PASSED if there are no result messages.
     *
     * @return the validation result for this AssertionResult.
     */
    @Override
	public ValidationResult getValidationResult()
    {
        return assertableResult.getValidationResult();
    }

    /**
     * Returns a list of failure or warning messages, or null.
     * 
     * @return a list of failure or warning messages, or null.
     */
    @Override
	public List<AssertionResultItem> getResultItems()
    {
        return assertableResult.getResultItems();
    }

    /**
     * Returns the containing AssertionGroups that generated this
     * AssertionResult.
     * 
     * @return the containing AssertionGroups.
     */
    @Override
	public Collection<AssertionGroup> getContainingGroups()
    {
        return assertableResult.getContainingGroups();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString()
    {
        return getValidationResult()
            +",Assertion["+getAssertionName()
            +",version="+getAssertionVersion()+"]";
    }
}
