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
import org.ebayopensource.turmeric.repository.v2.services.*;

public class ModifyType1Asset extends ModifyAsset {
	
	@Override
	public void modify(AssetInfo assetInfo, AssetInput assetInput) {
		
		modifyAssetName(assetInfo, assetInput);
		modifyComments(assetInfo, assetInput.getComments());
		modifyAssetId(assetInfo);
		setClassifier(assetInfo);
		
	}

}
