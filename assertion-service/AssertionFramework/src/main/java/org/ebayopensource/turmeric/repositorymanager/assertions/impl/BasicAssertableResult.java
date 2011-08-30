/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.ebayopensource.turmeric.repositorymanager.assertions.Assertable;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertableResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.Assertion;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionGroup;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionResultItem;
import org.ebayopensource.turmeric.repositorymanager.assertions.ValidationResult;


// TODO: Auto-generated Javadoc
/**
 * BasicAssertableResult implements AssertionResult.
 * 
 * @author pcopeland
 */
public class BasicAssertableResult
    implements AssertableResult
{
    
    /** The assertable. */
    private Assertable assertable;
    
    /** The result. */
    private ValidationResult result = ValidationResult.PASSED;
    
    /** The result items. */
    private List<AssertionResultItem> resultItems;
    
    /** The member results. */
    private List<AssertableResult> memberResults = null;
    
    /** The parent group results. */
    private Set<AssertableResult> parentGroupResults = null;

    /**
     * Constructs an AssertableResult for an AssertionGroup.
     *
     * @param assertionGroup the AssertionGroup that generated this result.
     */
    public BasicAssertableResult(AssertionGroup assertionGroup)
    {
        this.assertable = assertionGroup;
    }

    /**
     * Constructs an AssertableResult for an Assertion.
     * 
     * @param assertion the Assertion that generated this result.
     * @param resultItems information on failures or warnings in this result.
     */
    public BasicAssertableResult(
            Assertion assertion,
            List<AssertionResultItem>resultItems)
    {
        this.assertable = assertion;
        this.resultItems = resultItems;
        this.result = (resultItems == null || resultItems.size() == 0)
            ? ValidationResult.PASSED
            : assertion.getErrorSeverity();
    }

    /**
     * Returns the Assertion that generated this result.
     * 
     * @return the Assertion that generated this result.
     */
    @Override
	public Assertable getAssertable() { return assertable; }

    /**
     * Returns the validation result for this AssertionResult. The
     * value is ValidationResult.PASSED if there are no result messages.
     *
     * @return the validation result for this AssertionResult.
     */
    @Override
	public ValidationResult getValidationResult() { return result; }

    /**
     * Returns a list of failure or warning messages, or null.
     * 
     * @return a list of failure or warning messages, or null.
     */
    @Override
	public List<AssertionResultItem> getResultItems()
    {
        return resultItems;
    }

    /**
     * Adds a group AssertableResult that contains this AssertableResult.
     *
     * @param groupMemberResult the group member result
     */
    @Override
	public void addAssertableResult(AssertableResult groupMemberResult)
    {
        ValidationResult memberResult = groupMemberResult.getValidationResult();
        if (result != ValidationResult.ERROR || result != ValidationResult.MUST) {
            if (memberResult == ValidationResult.ERROR || memberResult == ValidationResult.MUST) {
                result = memberResult; // promote to MUST
            } else if (memberResult == ValidationResult.WARNING || memberResult == ValidationResult.MAY || memberResult == ValidationResult.SHOULD) {
                result = memberResult; // promote to SHOULD
            }            
        }
        if (memberResults == null)
            memberResults = new ArrayList<AssertableResult>();
        memberResults.add(groupMemberResult);
        groupMemberResult.addContainingGroupResult(this);
    }

    /**
     * Adds an AssertionGroupResult that contains this AssertableResult.
     * 
     * @param groupResult the result from the containing AssertionGroup.
     */
    @Override
	public void addContainingGroupResult(AssertableResult groupResult)
    {
        if (parentGroupResults == null)
            parentGroupResults = new LinkedHashSet<AssertableResult>();
        parentGroupResults.add(groupResult);
    }

    /**
     * Returns the member Assertables if this is a group AssertableResult,
     * or null.
     * 
     * @return the member Assertables if this is a group AssertableResult
     */
    @Override
	public List<AssertableResult> getMemberAssertableResults()
    {
        return memberResults;
    }

    /**
     * Returns the containing AssertionGroups that generated this
     * AssertableResult.
     * 
     * @return the containing AssertionGroups.
     */
    @Override
	public Collection<AssertionGroup> getContainingGroups()
    {
        return getContainingGroups(new LinkedHashSet<AssertionGroup>());
    }

    /**
     * Gets the containing groups.
     *
     * @param containingGroups the containing groups
     * @return the containing groups
     */
    private Collection<AssertionGroup> getContainingGroups(
            Collection<AssertionGroup> containingGroups)
    {
        if (parentGroupResults != null) {
            for (AssertableResult parentResult : parentGroupResults) {
                Assertable parentAssertable = parentResult.getAssertable();
                if (!(parentAssertable instanceof AssertionGroup))
                    throw new IllegalArgumentException(
                            "Containing group result is not an AssertionGroup");
                AssertionGroup assertionGroup = (AssertionGroup)parentAssertable;
                containingGroups.add(assertionGroup);
                if (parentResult instanceof BasicAssertableResult) {
                    BasicAssertableResult bar = (BasicAssertableResult)parentResult;
                    containingGroups.addAll(bar.getContainingGroups(containingGroups));
                } else
                    containingGroups.addAll(parentResult.getContainingGroups());
            }
        }
        return containingGroups;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString()
    {
        return "AssertionResult["+assertable+",result="+getValidationResult()+"]";
    }
}
