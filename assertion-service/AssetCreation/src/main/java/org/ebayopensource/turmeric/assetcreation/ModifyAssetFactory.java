/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import org.ebayopensource.turmeric.assetcreation.artifacts.AssetInput;

/**
 * A factory for creating ModifyAsset objects.
 */
public class ModifyAssetFactory {
	
	/**
	 * Gets the modify asset.
	 *
	 * @param assetInput the asset input
	 * @return the modify asset
	 */
	public static ModifyAsset getModifyAsset(AssetInput assetInput)
	{
		String assetType = assetInput.getAssetType().value();
		
		if(assetType.equals("Service"))
			return new ModifyServiceAsset();
		else if(assetType.equals("Functional Domain"))
			return new ModifyFunctionalDomainAsset();
		else if(assetType.equals("Consumer"))
			return new ModifyConsumerAsset();
		else if(assetType.equals("Property")||assetType.equals("Flag Set")||assetType.equals("Feature Contingency")
				||assetType.equals("Command")||assetType.equals("Deliverable")||assetType.equals("Search Driver")
				||assetType.equals("Page Group")||assetType.equals("Template")||assetType.equals("Module"))
			return new ModifyType1Asset();
		else if(assetType.equals("Flag")||assetType.equals("Page"))
			return new ModifyType2Asset();
		return null;
	}

}
