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
 * The Class AssetIdWrapper.
 */
public class AssetIdWrapper {
	private String assetId;
	private boolean consumed = false;
	
	/**
	 * Instantiates a new asset id wrapper.
	 *
	 * @param assetId the asset id
	 */
	public AssetIdWrapper(String assetId) {
		this.assetId = assetId;
	}

	/**
	 * Gets the asset id.
	 *
	 * @return the asset id
	 */
	public String getAssetId() {
		return assetId;
	}

	/**
	 * Checks if is consumed.
	 *
	 * @return true, if is consumed
	 */
	public boolean isConsumed() {
		return consumed;
	}

	/**
	 * Sets the consumed.
	 *
	 * @param consumed the new consumed
	 */
	public void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}
}
