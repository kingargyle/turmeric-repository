/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

/**
 * Assertions and AssertionGroups are Assertable. An AssertionGroup
 * contains a group of Assertables (Assertions and AssertionGroups).
 * 
 * @author pcopeland
 */
public interface Assertable
{
    /**
     * Returns the name of this Assertable.
     * 
     * @return the name of this Assertable.
     */
    public String getName();

    /**
     * Returns the version of this Assertion. When the definition of an
     * Assertion is updated a new version should be created.
     * 
     * @return the version of this Assertable.
     */
    public String getVersion();

    /**
     * Returns the description of this Assertable.
     * 
     * @return the description of this Assertable.
     */
    public String getDescription();
}
