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

import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent;


/**
 * AssertionContentSource produces content from an AssertionContent.
 * 
 * @author pcopeland
 */
public interface AssertionContentSource
{
    /**
     * Returns the underlying AssertionContent.
     * 
     * @return the underlying AssertionContent.
     */
    public AssertionContent getAssertionContent();

    /**
     * Returns an InputStream for this AssertionContentSource or null
     * if the content should be read with a character Reader.
     *
     * @return an InputStream for this AssertionContentSource.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public InputStream getContentStream() throws IOException;

    /**
     * Returns a character stream reader for this AssertionContentSource or null
     * if the content is not character data.
     *
     * @return a character stream reader for this AssertionContentSource or null.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Reader getContentReader() throws IOException;

    /**
     * Returns the content as a String.
     *
     * @return the content as a String.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String getStringContent() throws IOException;
}
