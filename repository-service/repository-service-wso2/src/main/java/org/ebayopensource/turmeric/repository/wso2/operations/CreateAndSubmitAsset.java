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
import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.CreateAndSubmitAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateAndSubmitAssetResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.registry.core.Registry;

/**
 * CreateAndSubmitAsset handles the request to first create the asset and then submit it for approval through the
 * governance process.
 * 
 * @author dcarver
 * 
 */
public class CreateAndSubmitAsset extends AbstractRepositoryProvider {

   /**
    * Processes the request to create and then submit the asset.
    * 
    * @param request
    *           The asset request
    * @return the response from the process.
    */
   public CreateAndSubmitAssetResponse process(CreateAndSubmitAssetRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      CreateAndSubmitAssetResponse response = new CreateAndSubmitAssetResponse();

      try {
         AssetInfo assetInfo = request.getAssetInfo();
         BasicAssetInfo basicInfo = assetInfo.getBasicAssetInfo();
         AssetKey assetKey = basicInfo.getAssetKey();
         if (assetKey.getAssetId() == null && assetKey.getAssetName() == null) {
            return createErrorMissingAssetName(errorDataList, response);
         }

         AssetFactory factory = new AssetFactory(basicInfo, wso2);
         Asset asset = factory.createAsset();

         if (asset.hasDuplicates()) {
            return createErrorDuplicateAsset(errorDataList, response);
         }

         asset.createAsset();

         for (ArtifactInfo artinfo : assetInfo.getArtifactInfo()) {
            asset.addArtifact(artinfo);
         }

         asset.save();

         // TODO add submittal of asset
         // RSLifeCycle.submit(asset, request.getComment());

         assetKey = (getAssetKey(asset));
         response.setAssetKey(assetKey);
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }

   }

}
