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
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.AssetStatus;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetStatusRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetStatusResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.RSLifeCycle;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;

public class GetAssetStatus extends AbstractRepositoryProvider {

   /**
    * Return the status of the asset.
    * 
    * @param request
    *           The request for the asset
    * @return the response for the operation.
    */
   public GetAssetStatusResponse process(GetAssetStatusRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      GetAssetStatusResponse response = new GetAssetStatusResponse();

      try {
         AssetKey assetKey = request.getAssetKey();
         String assetId = assetKey.getAssetId();

         if (!wso2.resourceExists(assetId)) {
            return createAssetNotFoundError(errorDataList, response);
         }

         Resource asset = wso2.get(assetId);
         String state = RSLifeCycle.getState(asset);

         AssetStatus assetStatus = new AssetStatus();
         assetStatus.setState(state);
         response.setAssetStatus(assetStatus);

         response.setVersion(asset.getProperty(AssetConstants.TURMERIC_VERSION));
         return RSProviderUtil.setSuccessResponse(response);

      } catch (Exception ex) {
         ex.printStackTrace();
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

}
