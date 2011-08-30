/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionContentSource;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionProcessorContext;

// TODO: Auto-generated Javadoc
/**
 * AssertionContent represents the content of an assertion or the content
 * an Assertion is applied to.
 * 
 * @see AssertionProcessorContext
 * @see AssertionContentSource
 * 
 * @author pcopeland
 */
public interface AssertionContent
{
    /**
     * Returns the name of this AssertionContent. The name should be
     * a valid URI.
     * 
     * @return a name for this AssertionContent.
     */
    public String getName();

    /**
     * Sets the name of this AssetContent.
     *
     * @param contentName the new name
     */
    public void setName(String contentName);

    /**
     * Returns the source name for this AssertionContent.
     * This can be a URL, file pathname, or Asset name and version.
     * The source name should be a unique identifier for the content.
     * 
     * @return the source name for this AssertionContent.
     */
    public String getSourceName();
  
}
