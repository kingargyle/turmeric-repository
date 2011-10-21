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
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.GetBasicAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetBasicAssetInfoResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Registry;

/**
 * Retrieves the basic asset information from the repository.
 * 
 * @author dcarver
 * 
 */
public class GetBasicAssetInfo extends AbstractRepositoryProvider {

   /**
    * Processes the request to retrieve basic asset information from the repository.
    * 
    * @param request
    *           the request
    * @return the response
    */
   public GetBasicAssetInfoResponse process(GetBasicAssetInfoRequest request) {
      Registry wso2Registry = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      GetBasicAssetInfoResponse response = new GetBasicAssetInfoResponse();

      try {
         AssetKey assetKey = request.getAssetKey();
         String assetId = assetKey.getAssetId();

         GovernanceArtifact asset = GovernanceUtils.retrieveGovernanceArtifactById(wso2Registry, assetId);

         if (asset == null) {
            return createAssetNotFoundError(errorDataList, response);
         }

         String type = asset.getAttribute("type");

         // Create the basic service info structure
         BasicAssetInfo basicAssetInfo = createBasicAsset(assetKey, asset, type);

         // populate the response
         response.setBasicAssetInfo(basicAssetInfo);
         response.setVersion(basicAssetInfo.getVersion());
         return RSProviderUtil.setSuccessResponse(response);

      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, new GetBasicAssetInfoResponse(),
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }

   }

}
