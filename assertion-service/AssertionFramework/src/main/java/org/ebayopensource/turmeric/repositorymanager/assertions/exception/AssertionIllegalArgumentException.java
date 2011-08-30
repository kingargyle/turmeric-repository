/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.exception;

import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;



/**
 * The Class AssertionIllegalArgumentException.
 */
public class AssertionIllegalArgumentException extends java.lang.IllegalArgumentException 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3466311003091061462L;
	
	/** The error message. */
	private ErrorMessage errorMessage;
	
	/** The throwable. */
	private Throwable throwable;
	
	
	/**
	 * Instantiates a new assertion illegal argument exception.
	 *
	 * @param message the message
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public AssertionIllegalArgumentException(String message, ErrorMessage errorMessage,
			Throwable throwable) 
	{
		super(message,throwable);
		this.errorMessage = errorMessage;
		this.throwable = throwable;
	}
	
	/**
	 * Instantiates a new assertion illegal argument exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public AssertionIllegalArgumentException(String message, Throwable throwable) 
	{
		super(message,throwable);
		this.throwable = throwable;
	}
	
	/**
	 * Instantiates a new assertion illegal argument exception.
	 *
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public AssertionIllegalArgumentException(ErrorMessage errorMessage,
			Throwable throwable) 
	{
		super(throwable);
		this.errorMessage = errorMessage;
		this.throwable = throwable;
	}
	
	/**
	 * Instantiates a new assertion illegal argument exception.
	 *
	 * @param errorMessage the error message
	 */
	public AssertionIllegalArgumentException(ErrorMessage errorMessage) 
	{
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Gets the throwable.
	 *
	 * @return the throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}	
	
}
