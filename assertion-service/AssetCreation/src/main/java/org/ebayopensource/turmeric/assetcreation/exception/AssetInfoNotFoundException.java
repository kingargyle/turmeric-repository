/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation.exception;

public class AssetInfoNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6473693355897714124L;

	public AssetInfoNotFoundException() {
		
	}
	
	public AssetInfoNotFoundException(String message)
	{
		super(message);
	}

}