/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.cachingframework.exception;

public class AssetRetrivalException extends Exception 
{
	private static final long serialVersionUID = 7531625764263927089L;

	public AssetRetrivalException() 
	{
		super();
	}

	public AssetRetrivalException(String message) 
	{
		super(message);
	}

	public AssetRetrivalException(Throwable cause) 
	{
		super(cause);
	}

	public AssetRetrivalException(String message, Throwable cause) 
	{
		super(message, cause);
	}

}
