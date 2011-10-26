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
import org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.registry.core.Registry;

/**
 * CreateAsset handles the request to create an asset.
 * 
 * @author dcarver
 * 
 */
public class CreateCompleteAsset extends AbstractRepositoryProvider {

   /**
    * Processes the request to create the asset.
    * 
    * @param request
    *           The asset request
    * @return the response from the process.
    */
   public CreateCompleteAssetResponse process(CreateCompleteAssetRequest request) {
      Registry wso2Registry = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      CreateCompleteAssetResponse response = new CreateCompleteAssetResponse();

      try {
         AssetInfo assetInfo = request.getAssetInfo();
         BasicAssetInfo basicInfo = assetInfo.getBasicAssetInfo();
         AssetKey assetKey = basicInfo.getAssetKey();
         response.setAssetKey(assetKey);

         AssetFactory factory = new AssetFactory(basicInfo, wso2Registry);
         Asset asset = factory.createAsset();

         if (!asset.hasName()) {
            return createErrorMissingAssetName(errorDataList, response);
         }

         if (asset.hasDuplicates()) {
            return createErrorDuplicateAsset(errorDataList, response);
         }

         if (!asset.createAsset()) {
            return createErrorAssetCreation(errorDataList, response);
         }

         asset.addAsset();

         // Attach items to the asset after it has been added.
         List<ArtifactInfo> artifactInfoList = request.getAssetInfo().getArtifactInfo();
         for (ArtifactInfo artifactInfo : artifactInfoList) {
            asset.addArtifact(artifactInfo);
         }

         asset.save();

         // Add Extended Info like Attributes
         ExtendedAssetInfo extInfo = request.getAssetInfo().getExtendedAssetInfo();
         List<AttributeNameValue> attributes = extInfo.getAttribute();
         GovernanceArtifact gart = asset.getGovernanceArtifact();
         for (AttributeNameValue attribute : attributes) {
            attribute.getAttributeName();
            if (attribute.getAttributeValueString() != null) {
               gart.addAttribute(attribute.getAttributeName(), attribute.getAttributeValueString());
            } else if (attribute.getAttributeValueLong() != null) {
               gart.addAttribute(attribute.getAttributeName(), attribute.getAttributeValueLong().toString());
            }
         }

         asset.save();

         // RSProviderUtil.createDependencies(assetKey,
         // assetInfo.getFlattenedRelationship());

         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }

   }

}
