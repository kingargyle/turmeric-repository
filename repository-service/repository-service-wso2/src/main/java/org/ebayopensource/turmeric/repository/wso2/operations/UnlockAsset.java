/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.operations;

import java.util.ArrayList;
import java.util.List;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.UnlockAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UnlockAssetResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.registry.core.Registry;

public class UnlockAsset extends AbstractRepositoryProvider {

   public UnlockAssetResponse process(UnlockAssetRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UnlockAssetResponse response = new UnlockAssetResponse();

      try {
         AssetKey assetKey = request.getAssetKey();
         BasicAssetInfo basicInfo = populateMinBasicAssetInfo(assetKey);

         AssetFactory factory = new AssetFactory(basicInfo, wso2);
         Asset asset = factory.createAsset();

         if (!asset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         asset.findAsset();

         if (asset.isLocked()) {
            asset.unlock();
            asset.save();
         }

         AssetInfo assetInfo = getAssetInfo(asset);

         // populate the response
         response.setAssetInfo(assetInfo);
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

}
