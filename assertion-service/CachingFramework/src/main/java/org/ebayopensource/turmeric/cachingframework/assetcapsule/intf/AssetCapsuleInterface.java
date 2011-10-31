/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.cachingframework.assetcapsule.intf;

public interface AssetCapsuleInterface {
   /*
    * public Boolean isAssetExpired(); public String getAssetNamespace(); public void refreshAsset();
    */
   public String getAssetName();

   public String getAssetId();

   public String getLibraryName();

   public String getAssetType();

   public String getAssetVersion();

   public Object getAsset(String assetId, String assetName, String assetVersion, String assetType);

   public Object getAsset(String assetId);
}
