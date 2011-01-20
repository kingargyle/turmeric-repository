/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.cachingframework.exception;

import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;


public class AssertionIllegalArgumentException extends java.lang.IllegalArgumentException 
{
	private ErrorMessage m_errorMessage;
	private Throwable m_throwable;
	
	
	public AssertionIllegalArgumentException(String message, ErrorMessage errorMessage,
			Throwable throwable) 
	{
		super(message,throwable);
		this.m_errorMessage = errorMessage;
		this.m_throwable = throwable;
	}
	
	public AssertionIllegalArgumentException(String message, Throwable throwable) 
	{
		super(message,throwable);
		this.m_throwable = throwable;
	}
	
	public AssertionIllegalArgumentException(ErrorMessage errorMessage,
			Throwable throwable) 
	{
		super(throwable);
		this.m_errorMessage = errorMessage;
		this.m_throwable = throwable;
	}
	
	public AssertionIllegalArgumentException(ErrorMessage errorMessage) 
	{
		this.m_errorMessage = errorMessage;
	}

	public ErrorMessage getErrorMessage() {
		return m_errorMessage;
	}

	public Throwable getThrowable() {
		return m_throwable;
	}	
	
}
