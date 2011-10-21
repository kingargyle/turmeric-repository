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
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.registry.core.Registry;

/**
 * CreateAsset handles the request to create an asset.
 * 
 * @author dcarver
 * 
 */
public class CreateAsset extends AbstractRepositoryProvider {

   /**
    * Processes the request to create the asset.
    * 
    * @param request
    *           The asset request
    * @return the response from the process.
    */
   public CreateAssetResponse process(CreateAssetRequest request) {
      Registry wso2Registry = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      CreateAssetResponse response = new CreateAssetResponse();

      try {
         BasicAssetInfo basicInfo = request.getBasicAssetInfo();
         AssetKey assetKey = basicInfo.getAssetKey();

         AssetFactory assetFactory = new AssetFactory(basicInfo, wso2Registry);
         Asset asset = assetFactory.createAsset();

         if (!asset.hasName()) {
            return createErrorMissingAssetName(errorDataList, response);
         }

         if (asset.isNamespaceRequired()) {
            if (!asset.hasNamespace()) {
               return createErrorMissingNamespace(errorDataList, response);
            }
         }

         if (!asset.duplicatesAllowed()) {
            if (asset.hasDuplicates()) {
               return createErrorDuplicateAsset(errorDataList, response);
            }
         }

         if (!asset.createAsset()) {
            return createErrorAssetCreation(errorDataList, response);
         }

         asset.addAsset();
         assetKey.setAssetId(asset.getId());

         response.setAssetKey(assetKey);
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

}
