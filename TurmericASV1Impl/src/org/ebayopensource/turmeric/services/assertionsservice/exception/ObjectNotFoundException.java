/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.exception;

public class ObjectNotFoundException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7749740894635484173L;

	public ObjectNotFoundException(String message, Throwable cause) 
	{
		super(message, cause);
	}

	public ObjectNotFoundException(String message) 
	{
		super(message);
	}
	
}
