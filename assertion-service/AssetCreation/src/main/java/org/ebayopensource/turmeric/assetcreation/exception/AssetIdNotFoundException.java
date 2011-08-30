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
 * The Class AssetIdNotFoundException.
 */
public class AssetIdNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3449852775731764092L;

	/**
	 * Instantiates a new asset id not found exception.
	 */
	public AssetIdNotFoundException() {
		
	}
	
	/**
	 * Instantiates a new asset id not found exception.
	 *
	 * @param message the message
	 */
	public AssetIdNotFoundException(String message) {
		super(message);
	}

}
