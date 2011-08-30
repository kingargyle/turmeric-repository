/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.ebayopensource.turmeric.repositorymanager.assertions.URLContent;


/**
 * The Class URLContentSource.
 *
 * @author pcopeland
 */
public class URLContentSource
    extends AbstractContentSource
{
    
    /** The url content. */
    private URLContent urlContent;

    /**
     * Constructs an AssertionContentSource for a URLContent.
     * 
     * @param urlContent the underlying AssertionContent.
     */
    public URLContentSource(URLContent urlContent)
    {
        super(urlContent);
        this.urlContent = urlContent;
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
    	InputStream returnInputStream = null;
    	File checkFile=null;
		try 
		{
			checkFile = new File(urlContent.getURL().toString());
			returnInputStream = urlContent.getURL().openStream();
		}
		catch (Exception exception) 
		{
			FileInputStream fileInputStream = new FileInputStream(checkFile);
			returnInputStream = fileInputStream;
		} 
        return returnInputStream; 
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
