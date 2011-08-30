/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation.exception;

/**
 * The Class AssetCreationException.
 */
public class AssetCreationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7812204546889051536L;
	
	/**
	 * Instantiates a new asset creation exception.
	 */
	public AssetCreationException()
	{
	}
	
	/**
	 * Instantiates a new asset creation exception.
	 *
	 * @param message the message
	 */
	public AssetCreationException(String message)
	{
		super(message);
	}
	
	/**
	 * Instantiates a new asset creation exception.
	 *
	 * @param cause the cause
	 */
	public AssetCreationException(Throwable cause)
	{
		super(cause);
	}
	
	/**
	 * Instantiates a new asset creation exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public AssetCreationException(String message, Throwable cause)
	{
		super(message,cause);
	}

}
