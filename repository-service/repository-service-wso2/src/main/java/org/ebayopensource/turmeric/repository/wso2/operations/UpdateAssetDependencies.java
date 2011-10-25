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
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetDependenciesRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetDependenciesResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.registry.core.Registry;

public class UpdateAssetDependencies extends AbstractRepositoryProvider {

   public UpdateAssetDependenciesResponse process(UpdateAssetDependenciesRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UpdateAssetDependenciesResponse response = new UpdateAssetDependenciesResponse();

      try {
         AssetFactory factory = new AssetFactory(request.getAssetKey(), wso2);
         Asset asset = factory.createAsset();

         if (asset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         asset.findAsset();

         if (asset.isLocked()) {
            return createAssetNotLocked(errorDataList, response);
         }

         // get the existing assetInfo
         AssetInfo assetInfo = getAssetInfo(asset);

         if (assetInfo == null) {
            return createAssetTypeException(errorDataList, response);
         }

         if (request.isReplaceCurrent()) {
            // Remove and Update everything.
         } else {
            // Add and Update
         }

         // populate the response
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);

      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

}
