/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

/**
 * A factory for creating AssetCreator objects.
 */
public class AssetCreatorFactory {
	
	/**
	 * Gets the asset creator.
	 *
	 * @param inputFilePath the input file path
	 * @return the asset creator
	 */
	public static AssetCreatorIntf getAssetCreator(String inputFilePath)
	{
		return new AssetCreator(inputFilePath);
	}

}
