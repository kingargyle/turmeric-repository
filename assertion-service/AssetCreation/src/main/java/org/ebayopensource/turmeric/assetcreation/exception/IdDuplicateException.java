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
 * The Class IdDuplicateException.
 */
public class IdDuplicateException extends AssetCreationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6915895967771012323L;

	/**
	 * Instantiates a new id duplicate exception.
	 */
	public IdDuplicateException() {
		
	}
	
	/**
	 * Instantiates a new id duplicate exception.
	 *
	 * @param message the message
	 */
	public IdDuplicateException(String message) {
		super(message);
	}

}
