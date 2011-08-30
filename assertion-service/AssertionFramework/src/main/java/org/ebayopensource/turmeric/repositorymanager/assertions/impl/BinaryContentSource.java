/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.ebayopensource.turmeric.repositorymanager.assertions.BinaryContent;


/**
 * The Class BinaryContentSource.
 *
 * @author pcopeland
 */
public class BinaryContentSource
    extends AbstractContentSource
{
    
    /** The binary content. */
    private BinaryContent binaryContent;
    
    /** The input stream. */
    private ByteArrayInputStream inputStream = null;

    /**
     * Constructs an AssertionContentSource for a BinaryContent.
     * 
     * @param binaryContent the underlying AssertionContent.
     */
    public BinaryContentSource(BinaryContent binaryContent)
    {
        super(binaryContent);
        this.binaryContent = binaryContent;
    }

    /**
     * Returns an InputStream for this AssertionContent or null
     * if the content should be read with a character Reader.
     *
     * @return an InputStream for this AssertioContent.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
	public InputStream getContentStream()
        throws IOException
    {
        if (inputStream == null)
            inputStream = new ByteArrayInputStream(binaryContent.getContent());
        inputStream.reset(); // ByteArrayInputStream can be reused after closed
        return inputStream;
    }

    /**
     * Returns a character stream reader for this AssertionContent or null
     * if the content is not character data.
     *
     * @return a character stream reader for this AssertionContent or null.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
	public Reader getContentReader()
        throws IOException
    {
        return null;
    }
}
