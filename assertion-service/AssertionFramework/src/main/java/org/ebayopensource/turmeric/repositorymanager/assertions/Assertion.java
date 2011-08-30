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

// TODO: Auto-generated Javadoc
/**
 * An Assertion contains an executable script that applies a
 * validation rule. Two assertions with the same name and version
 * are "equal" (assertion1.equals(assertion2) is true).
 * 
 * @author pcopeland
 */
public interface Assertion
    extends Assertable
{
    /**
     * Returns the failure or warning severity for this Assertion.
     * 
     * @return the failure or warning severity for this Assertion.
     */
    public ValidationResult getErrorSeverity();

    /**
     * Returns the executable script content that is the validation rule.
     * 
     * @return the executable script content that is the validation rule.
     */
    public AssertionContent getAssertionScript();

    /**
     * Returns the AssertionProcessor type that executes the validation
     * rule.
     * 
     * @return the AssertionProcessor type that executes the validation
     * rule.
     */
    public String getAssertionProcessorType();

    /**
     * Returns the AssertionModules that are used by the AssertionScript or null.
     * 
     * @return the AssertionModules that are used by the AssertionScript or null.
     */
    public Collection<AssertionModule> getAssertionModules();
}
