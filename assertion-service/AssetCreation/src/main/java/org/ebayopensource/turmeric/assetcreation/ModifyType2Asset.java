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

/**
 * The Class ModifyType2Asset.
 */
public class ModifyType2Asset extends ModifyAsset {

   /*
    * (non-Javadoc)
    * 
    * @see
    * org.ebayopensource.turmeric.assetcreation.ModifyAsset#modify(org.ebayopensource.turmeric.repository.v1.services
    * .AssetInfo, org.ebayopensource.turmeric.assetcreation.artifacts.AssetInput)
    */
   @Override
   public void modify(AssetInfo assetInfo, AssetInput assetInput) {

      modifyAssetName(assetInfo, assetInput);
      modifyAssetId(assetInfo);
      modifySourceRelationship(assetInfo);
      setClassifier(assetInfo);
   }

   /**
    * Modify source relationship.
    * 
    * @param assetInfo
    *           the asset info
    */
   public void modifySourceRelationship(AssetInfo assetInfo) {

      if (assetInfo.getFlattenedRelationship() != null) {
         assetInfo.getFlattenedRelationship().setSourceAsset(null);
      }
   }

}
