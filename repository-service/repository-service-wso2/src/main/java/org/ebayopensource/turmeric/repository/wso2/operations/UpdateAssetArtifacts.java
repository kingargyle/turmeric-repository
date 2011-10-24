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
import java.util.Arrays;
import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetArtifactsRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetArtifactsResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
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
         Asset asset = null;
         String assetId = request.getAssetKey().getAssetId();
         if (assetId != null) {
            asset = factory.createAssetById();
         } else {
            asset = factory.createAsset();
         }

         if (!asset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         asset.findAsset();

         if (!asset.isLocked()) {
            asset.lockAsset();
            asset.save();
            // return createAssetNotLocked(errorDataList, response);
         }

         // get the existing assetInfo
         AssetInfo assetInfo = getAssetInfo(asset);

         if (assetInfo == null) {
            return createAssetTypeException(errorDataList, response);
         }

         GovernanceArtifact artifact = asset.getGovernanceArtifact();

         if (request.isReplaceCurrent()) {
            ArrayList<GovernanceArtifact> gdependencies = new ArrayList(Arrays.asList(artifact.getDependencies()));
            for (ArtifactInfo ainfo : request.getArtifactInfo()) {
               for (GovernanceArtifact gart : gdependencies) {
                  if (gart.getAttribute(AssetConstants.TURMERIC_NAME).equals(ainfo.getArtifact().getArtifactName())) {
                     AssetFactory dfactory = new AssetFactory(ainfo, wso2);
                     Asset artAsset = dfactory.createAssetById();
                     if (artAsset.exists()) {

                     }
                  }
               }
            }

         } else {
            // Update existing artifacts and add new ones
         }

         asset.save();

         // populate the response
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);

      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

}
