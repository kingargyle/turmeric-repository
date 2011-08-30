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
 * An AssertionModule contains reusable scripts that are used by Assertions.
 * 
 * @author pcopeland
 */
public interface AssertionModule
{
    /**
     * Returns the name of this AssertionModule.
     * 
     * @return the name of this AssertionModule.
     */
    public String getName();

    /**
     * Returns the version of this AssertionModule.
     * 
     * @return the version of this AssertionModule.
     */
    public String getVersion();

    /**
     * Returns the description of this AssertionModule.
     * 
     * @return the description of this AssertionModule.
     */
    public String getDescription();

    /**
     * Returns the script contents contained in this AssertionModule.
     * 
     * @return the script contents contained in this AssertionModule.
     */
    public Collection<AssertionContent> getModuleScripts();

    /**
     * Returns the AssertionProcessor type that uses this AssertionModule.
     * 
     * @return the AssertionProcessor type that uses this AssertionModule.
     */
    public String getAssertionProcessorType();
}
