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
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetArtifactsRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetArtifactsResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.registry.core.Registry;

/**
 * UpdateAssetArtifacts implements the functionality to update the Artifacts for a particular asset. It supports the
 * ability to replaceAll or merge existing attributes. If during a replace all, an artifact is set but has no value, it
 * is removed. Otherwise it is ignored.
 * 
 * Currently this is limited to WSDL, Schemas, and Endpoints.
 * 
 * @author dcarver
 * 
 */
public class UpdateAssetArtifacts extends AbstractRepositoryProvider {

   /**
    * This processes the request for updating an Artifact.
    * 
    * @param request
    *           The initial request for updating
    * @return The result of the update.
    */
   public UpdateAssetArtifactsResponse process(UpdateAssetArtifactsRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UpdateAssetArtifactsResponse response = new UpdateAssetArtifactsResponse();

      try {
         AssetFactory factory = new AssetFactory(request.getAssetKey(), wso2);
         Asset asset = factory.createAsset();
         String assetId = asset.getId();

         if (!wso2.resourceExists(assetId)) {
            return createAssetNotFoundError(errorDataList, response);
         }

         if (!asset.isLocked()) {
            return createAssetNotLocked(errorDataList, response);
         }

         // get the existing assetInfo
         AssetInfo assetInfo = getAssetInfo(asset);

         if (assetInfo == null) {
            return createAssetTypeException(errorDataList, response);
         }

         if (request.isReplaceCurrent()) {
            // TODO: remove and add artifacts
            // remove Artifacts
            // add artifacts

         } else {
            // Update existing artifacts and add new ones
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
