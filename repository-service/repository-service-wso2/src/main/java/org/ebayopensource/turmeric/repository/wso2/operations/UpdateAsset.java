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
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.registry.core.Registry;

public class UpdateAsset extends AbstractRepositoryProvider {

   public UpdateAssetResponse process(UpdateAssetRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UpdateAssetResponse response = new UpdateAssetResponse();

      try {
         BasicAssetInfo basicInfo = request.getBasicAssetInfo();

         AssetFactory factory = new AssetFactory(basicInfo, wso2);
         Asset asset = factory.createAsset();

         if (!asset.exists()) {
            return createErrorDuplicateAsset(errorDataList, response);
         }

         asset.findAsset();

         if (!asset.isLocked()) {
            asset.lockAsset();
         }

         GovernanceArtifact artifact = asset.getGovernanceArtifact();
         artifact.setAttribute(AssetConstants.TURMERIC_DESCRIPTION, basicInfo.getAssetDescription());
         artifact.setAttribute(AssetConstants.TURMERIC_NAME, basicInfo.getAssetName());
         artifact.setAttribute(AssetConstants.TURMERIC_NAMESPACE, basicInfo.getNamespace());
         artifact.setAttribute(AssetConstants.TURMERIC_LONG_DESCRIPTION, basicInfo.getAssetLongDescription());
         artifact.setAttribute(AssetConstants.TURMERIC_VERSION, basicInfo.getVersion());

         asset.save();

         AssetInfo assetInfo = getAssetInfo(asset);

         // populate the response
         response.setAssetInfo(assetInfo);
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, new UpdateAssetResponse(),
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

}
