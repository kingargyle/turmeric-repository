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



public class AssertionRuntimeException extends java.lang.IllegalArgumentException 
{
	private ErrorMessage errorMessage;
	private Throwable throwable;
	
	public AssertionRuntimeException(String message, ErrorMessage errorMessage,
			Throwable throwable) 
	{
		super(message,throwable);
		this.errorMessage = errorMessage;
		this.throwable = throwable;
	}
	
	public AssertionRuntimeException(String message, Throwable throwable) 
	{
		super(message,throwable);
		this.throwable = throwable;
	}
	
	public AssertionRuntimeException(ErrorMessage errorMessage,
			Throwable throwable) 
	{
		super(throwable);
		this.errorMessage = errorMessage;
		this.throwable = throwable;
	}
	
	public AssertionRuntimeException(ErrorMessage errorMessage) 
	{
		this.errorMessage = errorMessage;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public Throwable getThrowable() {
		return throwable;
	}
	
	
}
