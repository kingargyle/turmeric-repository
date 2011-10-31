/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import org.ebayopensource.turmeric.repository.v2.services.*;

/**
 * The Class AssetInfoWrapper.
 */
public class AssetInfoWrapper {
   private final AssetInfo assetInfo;
   private boolean consumed = false;

   /**
    * Instantiates a new asset info wrapper.
    * 
    * @param assetInfo
    *           the asset info
    */
   public AssetInfoWrapper(AssetInfo assetInfo) {
      this.assetInfo = assetInfo;
   }

   /**
    * Gets the asset info.
    * 
    * @return the asset info
    */
   public AssetInfo getAssetInfo() {
      return assetInfo;
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
    * @param consumed
    *           the new consumed
    */
   public void setConsumed(boolean consumed) {
      this.consumed = consumed;
   }
}
