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
 * StringContent is an AssertionContent with underlying String content.
 * 
 * @author pcopeland
 */
public class BinaryContent
    implements AssertionContent
{
    private String contentName;
    private String sourceName;
    private byte[] content;

    /**
     * Constructs a BinaryContent.
     * 
     * @param name the name of this BinaryContent.
     * @param content the content of this BinaryContent.
     */
    public BinaryContent(String name, byte[] content)
    {
        contentName = name.replace(' ','-');
        this.content = content;
        sourceName = contentName+"-binary";
    }

    /**
     * Returns the name of this BinaryContent.
     * 
     * @return the name of this BinaryContent.
     */
    @Override
	public String getName() { return contentName; }

    /**
     * Sets the name of this BinaryContent.
     * 
     * @param name the new name of this BinaryContent.
     */
    @Override
	public void setName(String contentName) { this.contentName = contentName; }

    /**
     * Returns the source name for this BinaryContent.
     * 
     * @return the source name for this BinaryContent.
     */
    @Override
	public String getSourceName() { return sourceName; }

    /**
     * Adds a distinguishing identifier to the source name.
     * 
     * @param id a distinguishing identifier for this BinaryContent.
     */
    public void setSourceId(int id) { sourceName = contentName+"-binary"+id; }

    /**
     * Returns the underlying binary content.
     * 
     * @return the underlying binary content.
     */
    public byte[] getContent() { return content; }

    @Override
	public String toString()
    {
        return "BinaryContent["+sourceName+"]";
    }
}
