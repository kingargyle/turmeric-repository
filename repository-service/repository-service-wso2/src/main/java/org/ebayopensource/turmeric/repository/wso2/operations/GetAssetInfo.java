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
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Registry;

public class GetAssetInfo extends AbstractRepositoryProvider {

   public GetAssetInfoResponse process(GetAssetInfoRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      GetAssetInfoResponse response = new GetAssetInfoResponse();

      try {

         AssetKey assetKey = request.getAssetKey();
         GovernanceArtifact artifact = null;
         AssetFactory factory = null;

         if (assetKey.getAssetId() != null) {
            artifact = GovernanceUtils.retrieveGovernanceArtifactById(wso2, assetKey.getAssetId());
            factory = new AssetFactory(artifact, wso2);
         }

         if (artifact == null) {
            factory = new AssetFactory(assetKey, wso2);
         }

         Asset asset = factory.createAsset();

         if (!asset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         asset.findAsset();

         if (asset.getGovernanceArtifact() == null) {
            return createAssetNotFoundError(errorDataList, response);
         }

         AssetInfo assetInfo = getAssetInfo(asset);

         if (assetInfo == null) {
            return createAssetTypeException(errorDataList, response);
         }

         // populate the response
         response.setAssetInfo(assetInfo);
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);

      } catch (Exception ex) {
         ex.printStackTrace();
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }

   }

}
