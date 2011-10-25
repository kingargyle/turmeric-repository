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
import org.ebayopensource.turmeric.repository.v2.services.AssetInfoForUpdate;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.UpdateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.registry.core.Registry;

public class UpdateCompleteAsset extends AbstractRepositoryProvider {

   public UpdateCompleteAssetResponse process(UpdateCompleteAssetRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UpdateCompleteAssetResponse response = new UpdateCompleteAssetResponse();

      try {
         AssetInfoForUpdate updateInfo = request.getAssetInfoForUpdate();
         BasicAssetInfo basicInfo = updateInfo.getBasicAssetInfo();
         AssetKey assetKey = basicInfo.getAssetKey();
         AssetFactory factory = new AssetFactory(basicInfo, wso2);
         Asset asset = factory.createAsset();

         if (!asset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         asset.findAsset();

         // if (!asset.isLocked()) {
         // return createAssetNotLocked(errorDataList, response);
         // }

         // get the existing assetInfo
         AssetInfo origAssetInfo = getAssetInfo(asset);

         if (origAssetInfo == null) {
            return createAssetTypeException(errorDataList, response);
         }

         AssetKey origAssetKey = origAssetInfo.getBasicAssetInfo().getAssetKey();

         // update the assetInfo
         AssetKey newAssetKey = new AssetKey();
         newAssetKey.setAssetName(basicInfo.getAssetName());
         newAssetKey.setAssetId(origAssetKey.getAssetId());

         basicInfo.setAssetKey(newAssetKey);
         origAssetInfo.setBasicAssetInfo(basicInfo);

         GovernanceArtifact wso2artifact = asset.getGovernanceArtifact();
         updateBasicInfo(basicInfo, wso2artifact);
         // updateRelationships - i.e. dependencies
         // update LifeCycles

         // Update the asset
         asset.save();

         // populate the response
         response.setAssetKey(assetKey);
         response.setVersion(basicInfo.getVersion());
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

   private GovernanceArtifact updateBasicInfo(BasicAssetInfo basicInfo, GovernanceArtifact artifact) {
      GovernanceArtifact gart = artifact;
      return gart;
   }

}
