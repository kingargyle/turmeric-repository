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
import java.io.InputStreamReader;
import java.io.Reader;

import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent;


/**
 * 
 * @author pcopeland
 */
public abstract class AbstractContentSource
    implements AssertionContentSource
{
    private AssertionContent assertionContent;
    private String stringContent = null; // lazy evaluation

    /**
     * Constructs an AssertionContentSource for an AssetContent.
     * 
     * @param assertionContent the underlying AssertionContent.
     */
    public AbstractContentSource(AssertionContent assertionContent)
    {
        this.assertionContent = assertionContent;
    }

   /**
     * Returns the underlying AssertionContent.
     * 
     * @return the underlying AssertionContent.
     */
    public AssertionContent getAssertionContent() { return assertionContent; }

    public String getStringContent()
        throws IOException
    {
        if (stringContent == null) 
        {
        	Reader reader = getContentReader();
        	try
        	{
	            if (reader == null)
	                reader = new InputStreamReader(getContentStream());
	
	            int len;
	            StringBuffer sbuf = new StringBuffer();
	            char buf[] = new char[2048];
	            while((len = reader.read(buf)) > -1)
	            {
	                sbuf.append(buf, 0, len);
	            }
	            stringContent = sbuf.toString();
        	}
        	catch (Exception e) 
        	{
        		e.printStackTrace();
			}
        	finally
        	{
        		if(reader != null)
        		{
        			reader.close();
        		}
        	}
        }
        return stringContent;
    }

    public String toString()
    {
        return "AssertionContentSource:"+assertionContent;
    }
}
