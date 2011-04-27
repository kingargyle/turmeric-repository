/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * URLContent is an AssertionContent with an underlying URL.
 * 
 * @author pcopeland
 */
public class URLContent
    implements AssertionContent
{
    private String contentName;
    private String sourceName;
    private URL url;

    /**
     * Constructs a URLContent. The AssertionContent
     * name is derived from the filename part of the URL.
     *
     * @param urlSpec the URL for this URLContent.
     */
    public URLContent(String urlSpec)
    {
        try {
            url = getURL(urlSpec);
            sourceName = url.toExternalForm();
            File urlFile = new File(url.getPath());
            contentName = urlFile.getName();
        } catch (MalformedURLException mux) {
            throw new IllegalArgumentException(mux);
        }
    }
    
    @SuppressWarnings("deprecation")
	private URL getURL(String urlSpec) throws MalformedURLException
    {
    	boolean urlThrewException = false;
		boolean uriThrewException = false;
		boolean fileThrewException = false;
		String exceptionMessageString = ""; 
		URI uri = null;
		URL url = null;
		try 
		{
			url= new URL(urlSpec);
			File urlFile = new File(url.getPath());
			if(!urlFile.exists())
			{
				throw new MalformedURLException("The path " + urlSpec + " cannot be used in java.net.URL");
			}
		} 
		catch (MalformedURLException malformedURLException) 
		{
			urlThrewException = true;
			exceptionMessageString = malformedURLException.getMessage() +"\n";
		}
		if(urlThrewException)
		{
			try 
			{
				uri = new URI(urlSpec);
				url  = uri.toURL();
				File urlFile = new File(url.getPath());
				if(!urlFile.exists())
				{
					throw new MalformedURLException("The path " + urlSpec + " cannot be used in java.net.URI");
				}
			} 
			catch (MalformedURLException malformedURLException) 
			{
				uriThrewException = true;
				exceptionMessageString = exceptionMessageString.concat(malformedURLException.getMessage()+"\n");
			}
			catch (URISyntaxException uriSyntaxException)
			{
				uriThrewException = true;
				exceptionMessageString = exceptionMessageString.concat(uriSyntaxException.getMessage()+"\n");
			}
			catch (IllegalArgumentException illegalArgumentException) 
			{
				uriThrewException = true;
				exceptionMessageString = exceptionMessageString.concat(illegalArgumentException.getMessage()+"\n");
			}
		}
		if(uriThrewException)
		{
			File theFile = new File(urlSpec);
			try 
			{
				url = theFile.toURL();
				if(!theFile.exists())
				{
					throw new MalformedURLException("The path " + urlSpec + " cannot be used in with java.io.File");
				}
			} 
			catch (MalformedURLException malformedURLException) 
			{
				fileThrewException = true;
				exceptionMessageString = exceptionMessageString.concat(malformedURLException.getMessage()+"\n");
			}
		}
		if(!urlThrewException || !uriThrewException || !fileThrewException)
		{
	        return url;
		}
		else
		{
			throw new MalformedURLException(exceptionMessageString);
		}
    }

    /**
     * Constructs a URLContent.
     * 
     * @param name the name of this URLContent.
     * @param urlSpec the URL for this URLContent.
     */
    public URLContent(String name, String urlSpec)
    {
        this(urlSpec);
        contentName = name.replace(' ','-');
    }

    /**
     * Returns the name of this URLContent.
     * 
     * @return the name of this URLContent.
     */
    @Override
	public String getName() { return contentName; }

    /**
     * Sets the name of this URLContent.
     * 
     * @param name the new name of this URLContent.
     */
    @Override
	public void setName(String contentName) { this.contentName = contentName; }

    /**
     * Returns the URL for this URLContent.
     * 
     * @return the URL for this URLContent.
     */
    @Override
	public String getSourceName() { return sourceName; }

    /**
     * Returns the underlying URL for this URLContent.
     * 
     * @return the underlying URL for this URLContent.
     */
    public URL getURL() { return url; }

    @Override
	public String toString()
    {
    	return "URLContent["+sourceName+"]";
    }
}
