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
import org.ebayopensource.turmeric.repository.v2.services.FlattenedRelationship;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetDependenciesRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetDependenciesResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.registry.core.Registry;

/**
 * GetAssetDepdencies processes the request and returns a list of all the dependencies for that asset.
 * 
 * @author dcarver
 * 
 */
public class GetAssetDependencies extends AbstractRepositoryProvider {

   /**
    * Processes the request to get the asset dependencies.
    * 
    * @param request
    *           The asset request
    * @return the response from the process.
    */
   public GetAssetDependenciesResponse process(GetAssetDependenciesRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      GetAssetDependenciesResponse response = new GetAssetDependenciesResponse();

      try {
         AssetKey assetKey = request.getAssetKey();
         BasicAssetInfo basicInfo = populateMinBasicAssetInfo(assetKey);

         AssetFactory factory = new AssetFactory(basicInfo, wso2);
         Asset asset = factory.createAsset();

         if (!asset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         asset.findAsset();

         FlattenedRelationship relationship = getRelationShip(asset);

         // populate the response
         response.setFlattenedRelationship(relationship);
         response.setVersion(basicInfo.getVersion());
         return RSProviderUtil.setSuccessResponse(response);

      } catch (Exception ex) {
         ex.printStackTrace();
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }

   }

}
