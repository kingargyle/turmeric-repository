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
 * An AssertableResult is the result of validating an assertion or group.
 * The assertion or group may be contained in one or more groups that
 * are applied together.  An AssertableResult keeps track of the
 * group AssertableResults that contain it.
 * 
 * @author pcopeland
 */
public interface AssertableResult
{
    /**
     * Returns the Assertable that generated this result.
     * 
     * @return the Assertable that generated this result.
     */
    public Assertable getAssertable();

    /**
     * Returns the ValidationResult value for this AssertableResult.
     * 
     * @return the ValidationResult value for this AssertableResult.
     */
    public ValidationResult getValidationResult();

    /**
     * Returns a list of failure or warning messages, or null.
     * 
     * @return a list of failure or warning messages, or null.
     */
    public List<AssertionResultItem> getResultItems();

    /**
     * Adds an AssertableResult to this group AssertableResult.
     * 
     * @param groupMemberResult the member in this group AssertableResult.
     */
    public void addAssertableResult(AssertableResult groupMemberResult);

    /**
     * Adds a group AssertableResult that contains this AssertableResult.
     * 
     * @param groupResult the group AssertableResult that contains this member.
     */
    public void addContainingGroupResult(AssertableResult groupResult);

    /**
     * Returns the member Assertables if this is a group AssertableResult,
     * or null.
     * 
     * @return the member Assertables if this is a group AssertableResult
     */
    public List<AssertableResult> getMemberAssertableResults();

    /**
     * Returns the containing AssertionGroups that generated this
     * AssertableResult.
     * 
     * @return the containing AssertionGroups.
     */
    public Collection<AssertionGroup> getContainingGroups();
}
