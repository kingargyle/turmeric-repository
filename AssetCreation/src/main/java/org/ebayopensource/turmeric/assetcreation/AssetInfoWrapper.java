/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;

public class AssetInfoWrapper {
	private AssetInfo assetInfo;
	private boolean consumed = false;

	public AssetInfoWrapper(AssetInfo assetInfo) {
		this.assetInfo = assetInfo;
	}

	public AssetInfo getAssetInfo() {
		return assetInfo;
	}

	public boolean isConsumed() {
		return consumed;
	}

	public void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}
}
