/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

// TODO: Auto-generated Javadoc
/**
 * StringContent is an AssertionContent with underlying String content.
 * 
 * @author pcopeland
 */
public class StringContent
    implements AssertionContent
{
    
    /** The content name. */
    private String contentName;
    
    /** The source name. */
    private String sourceName;
    
    /** The content. */
    private String content;

    /**
     * Constructs a StringContent.
     * 
     * @param name the name of this StringContent.
     * @param content the ontent of this StringContent.
     */
    public StringContent(String name, String content)
    {
        contentName = name.replace(' ','-');
        this.content = content;
        sourceName = contentName+"-string";
    }

    /**
     * Constructs a StringContent.
     * 
     * @param name the name of this StringContent.
     * @param sourceName the source name of this StringContent.
     * @param content the ontent of this StringContent.
     */
    public StringContent(String name, String sourceName, String content)
    {
        contentName = name.replace(' ','-');
        this.sourceName = sourceName;
        this.content = content;
    }

    /**
     * Returns the name of this StringContent.
     * 
     * @return the name of this StringContent.
     */
    @Override
	public String getName() { return contentName; }

    /**
     * Sets the name of this StringContent.
     *
     * @param contentName the new name
     */
    @Override
	public void setName(String contentName) { this.contentName = contentName; }

    /**
     * Returns the source name for this StringContent.
     * 
     * @return the source name for this StringContent.
     */
    @Override
	public String getSourceName() { return sourceName; }

    /**
     * Adds a distinguishing identifier to the source name.
     * 
     * @param id a distinguishing identifier for this StringContent.
     */
    public void setSourceId(int id) { sourceName = contentName+"-string"+id; }

    /**
     * Returns the underlying String content for this StringContent.
     * 
     * @return the underlying String content for this StringContent.
     */
    public String getContent() { return content; }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString()
    {
        return "StringContent["+sourceName+"]";
    }
}
