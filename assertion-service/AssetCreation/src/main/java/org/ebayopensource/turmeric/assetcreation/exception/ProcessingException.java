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
 * The Class ProcessingException.
 */
public class ProcessingException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4729166933278002487L;
	
	/**
	 * Instantiates a new processing exception.
	 */
	public ProcessingException()
	{
	}
	
	/**
	 * Instantiates a new processing exception.
	 *
	 * @param message the message
	 */
	public ProcessingException(String message)
	{
		super(message);
	}
	
	/**
	 * Instantiates a new processing exception.
	 *
	 * @param cause the cause
	 */
	public ProcessingException(Throwable cause)
	{
		super(cause);
	}
	
	/**
	 * Instantiates a new processing exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ProcessingException(String message, Throwable cause)
	{
		super(message,cause);
	}

}
