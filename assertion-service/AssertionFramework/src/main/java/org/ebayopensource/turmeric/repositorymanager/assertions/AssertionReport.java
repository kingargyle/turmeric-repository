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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.ebayopensource.turmeric.repositorymanager.assertions.impl.BasicAssertionResult;


/**
 * An AssertionReport represents the result of applying a collection
 * of Assertables.  An AssertionReport derives a flattened set
 * (without duplicates) of AssertionResults from the underlying
 * collection of Assertions and AssertionGroups.
 * 
 * @author pcopeland
 */
public class AssertionReport
{
    
    /** The result. */
    private ValidationResult result;
    
    /** The content. */
    private List<AssertionContent> content;
    
    /** The assertion results. */
    private Set<AssertionResult> assertionResults =
        new LinkedHashSet<AssertionResult>();

    /**
     * Returns a formatted list of the AssertionGroup names.
     * This is used th generate the names of containing
     * AssertionGroups that generated an AssertionResult.
     *
     * @param groups the groups
     * @return a formatted list of the names of ancestor AssertionGroups.
     */
    static public String getContainingGroupNames(Collection<AssertionGroup> groups)
    {
        StringBuffer buf = new StringBuffer();
        String comma = "";
        for (AssertionGroup group: groups) {
            buf.append(comma)
                .append(group.getName())
                .append('/')
                .append(group.getVersion());
            comma = ",";
        }
        return buf.toString();
    }

    /**
     * Creates an AssertionReport from the given AssertableResults.
     * Any subsequent changes in the input collection will not be
     * reflected in the report.
     *
     * @param content the content
     * @param assertableResults collection of AssertableResults.
     */
    public AssertionReport(
            List<AssertionContent> content,
            Collection<AssertableResult> assertableResults)
    {
        this.content = content;
        initValidationResult(assertableResults);
        initAssertionResults(assertableResults, new HashSet<AssertableResult>());
    }

    /**
     * Returns the overall validation result for this AssertionReport.
     * 
     * @return the overall validation result for this AssertionReport.
     */
    public ValidationResult getValidationResult() { return result; }

    /**
     * Returns the list of content source names the assertion validated.
     * 
     * @return the list of content source names the assertion validated.
     */
    public List<AssertionContent> getAssertionContent() { return content; }
 

    /**
     * Returns an ordered collection of AssertionResults. Duplicate
     * AssertionResults contained in more than one group in the input
     * Assertables are present only once in the returned collection.
     * 
     * @return the AssertionResults in this AssertionReport.
     */
    public Collection<AssertionResult> getAssertionResults()
    {
        return assertionResults;
    }

    /**
     * Inits the validation result.
     *
     * @param assertableResults the assertable results
     */
    private void initValidationResult(
            Collection<AssertableResult> assertableResults)
    {
    	ValidationResult newResult = null;
        result = ValidationResult.PASSED;
        for (AssertableResult assertableResult: assertableResults) {
            newResult = assertableResult.getValidationResult();
            if (newResult == ValidationResult.ERROR || newResult == ValidationResult.MUST) {
                result = newResult; // promote to FAILED
                break; // cannot get any worse :-)
            } else if (newResult == ValidationResult.WARNING || newResult == ValidationResult.MAY || newResult == ValidationResult.SHOULD) {
                result = newResult; // promote to WARNING
            }
        }
	}

    /**
     * Inits the assertion results.
     *
     * @param assertableResults the assertable results
     * @param assertableResultSet the assertable result set
     */
    private void initAssertionResults(
            Collection<AssertableResult> assertableResults,
            Set<AssertableResult> assertableResultSet) 
    {
        for (AssertableResult assertableResult: assertableResults) {
            List<AssertableResult> memberResults = 
                assertableResult.getMemberAssertableResults();
            if (memberResults != null) {
                initAssertionResults(memberResults, assertableResultSet);
            } else if (!assertableResultSet.contains(assertableResult)) {
                assertableResultSet.add(assertableResult);
                assertionResults.add(new BasicAssertionResult(assertableResult));
            }
        }
    }
}
