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


// TODO: Auto-generated Javadoc
/**
 * StringContent is an AssertionContent with underlying String content.
 * 
 * @author pcopeland
 */
public class ClassResourceContent
    implements AssertionContent
{
    
    /** The content name. */
    private String contentName;
    
    /** The source name. */
    private String sourceName;
    
    /** The resource name. */
    private String resourceName;
    
    /** The resource class. */
    private Class<?> resourceClass;

    /**
     * Instantiates a new class resource content.
     *
     * @param contentName the content name
     * @param resourceName the resource name
     * @param resourceClass the resource class
     */
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
    @Override
	public String getName() { return contentName; }

    /**
     * Sets the name of this AssertionContent.
     *
     * @param contentName the new name
     */
    @Override
	public void setName(String contentName) { this.contentName = contentName; }

    /**
     * Returns a source name of the class resource.
     * 
     * @return a source name of the class resource.
     */
    @Override
	public String getSourceName() { return sourceName; }

    /**
     * Gets the resource name.
     *
     * @return the resource name
     */
    public String getResourceName() { return resourceName; }

    /**
     * Gets the resource class.
     *
     * @return the resource class
     */
    public Class<?> getResourceClass() { return resourceClass; }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString()
    {
        return "ClassResourceContent["+sourceName+"]";
    }
}
