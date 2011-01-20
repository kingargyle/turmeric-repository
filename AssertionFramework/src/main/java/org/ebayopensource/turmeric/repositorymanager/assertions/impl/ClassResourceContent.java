/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import java.net.URI;

import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent;


/**
 * StringContent is an AssertionContent with underlying String content.
 * 
 * @author pcopeland
 */
public class ClassResourceContent
    implements AssertionContent
{
    private String contentName;
    private String sourceName;
    private String resourceName;
    private Class<?> resourceClass;

    public ClassResourceContent(
            String contentName,
            String resourceName,
            Class<?> resourceClass)
    {
        this.contentName = contentName.replace(' ','-');
        sourceName = resourceClass.getName()+":"+resourceName;
        this.resourceName = resourceName;
        this.resourceClass = resourceClass;
        URI.create(contentName); // throws IllegalArgumentException if invalid
    }

    /**
     * Returns a name for this AssertionContent. The name should be a valid URI.
     * 
     * @return a name for this AssertionContent.
     */
    public String getName() { return contentName; }

    /**
     * Sets the name of this AssertionContent.
     * 
     * @param name the new name of this AssertionContent.
     */
    public void setName(String contentName) { this.contentName = contentName; }

    /**
     * Returns a source name of the class resource.
     * 
     * @return a source name of the class resource.
     */
    public String getSourceName() { return sourceName; }

    public String getResourceName() { return resourceName; }

    public Class<?> getResourceClass() { return resourceClass; }

    public String toString()
    {
        return "ClassResourceContent["+sourceName+"]";
    }
}
