/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * The Class ClassResourceContentSource.
 *
 * @author pcopeland
 */
public class ClassResourceContentSource
    extends AbstractContentSource
{
    
    /** The class resource content. */
    private ClassResourceContent classResourceContent;

    /**
     * Constructs an AssertionContentSource for a ClassResourceContentSource.
     * 
     * @param classResourceContent the underlying AssertionContent.
     */
    public ClassResourceContentSource(
            ClassResourceContent classResourceContent)
    {
        super(classResourceContent);
        this.classResourceContent = classResourceContent;
    }

    /**
     * Returns an InputStream for this ClassResourceAssertionContent.
     *
     * @return an InputStream for this ClassResourceAssertionContent.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
	public InputStream getContentStream()
        throws IOException
    {
        Class<?> resourceClass = classResourceContent.getResourceClass();
        String resourceName = classResourceContent.getResourceName();
        InputStream is = resourceClass.getResourceAsStream(resourceName);
        if (is == null)
            throw new FileNotFoundException(resourceName);
        return is;
    }

    /**
     * Returns null.
     *
     * @return the content reader
     */
    @Override
	public Reader getContentReader() { return null; }
}
