/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

import java.util.Collection;
import java.util.List;

/**
 * AssertionResult contains the result of an Assertion validation.
 * 
 * @see AssertionGroup#validateGroup(AssertionContent)
 * 
 * @author pcopeland
 */
public interface AssertionResult
{
    /**
     * Returns the name of the Assertion that generated this result.
     * 
     * @return the name of the Assertion that generated this result.
     */
    public String getAssertionName();

    /**
     * Returns the version of the Assertion that generated this result.
     * 
     * @return the version of the Assertion that generated this result.
     */
    public String getAssertionVersion();

    /**
     * Returns the description of the Assertion that generated this result.
     * 
     * @return the description of the Assertion that generated this result.
     */
    public String getAssertionDescription();

    /**
     * Returns the validation result for this AssertionResult. The
     * value is ValidationResult.PASSED if there are no result messages.
     *
     * @return the validation result for this AssertionResult.
     */
    public ValidationResult getValidationResult();

    /**
     * Returns a list of failure or warning messages, or null.
     * 
     * @return a list of failure or warning messages, or null.
     */
    public List<AssertionResultItem> getResultItems();

    /**
     * Returns the containing AssertionGroups that generated this
     * AssertionResult.
     * 
     * @return the containing AssertionGroups.
     */
    public Collection<AssertionGroup> getContainingGroups();
}
