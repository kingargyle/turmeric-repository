/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.ebayopensource.turmeric.repositorymanager.assertions.StringContent;


/**
 * 
 * @author pcopeland
 */
public class StringContentSource
    extends AbstractContentSource
{
    private StringContent stringContent;
    private StringReader reader = null;

    /**
     * Constructs an AssertionContentSource for an StringContent.
     * 
     * @param stringContent the underlying AssertionContent.
     */
    public StringContentSource(StringContent stringContent)
    {
        super(stringContent);
        this.stringContent = stringContent;
    }

    /**
     * Returns an InputStream for this AssertionContent or null
     * if the content should be read with a character Reader.
     * 
     * @return an InputStream for this AssertioContent.
     * @throws IOException
     */
    public InputStream getContentStream()
        throws IOException
    {
        return null;
    }

    /**
     * Returns a character stream reader for this AssertionContent or null
     * if the content is not character data.
     * 
     * @return a character stream reader for this AssertionContent or null.
     * @throws IOException
     */
    public Reader getContentReader()
        throws IOException
    {
        if (reader == null)
            reader = new RereadableStringReader(stringContent.getContent());
        reader.reset();
        return reader;
    }

    static class RereadableStringReader
        extends StringReader
    {
        RereadableStringReader(String string) { super(string); }

        public void close()
        {
            // Keep open so that reset() can be used after close
        }
    }
}
