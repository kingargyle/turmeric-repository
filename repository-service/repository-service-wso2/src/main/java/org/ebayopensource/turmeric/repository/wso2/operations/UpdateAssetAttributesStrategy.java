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
import org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetAttributesRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetAttributesResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.registry.core.Registry;

/**
 * UpdateAssetAttributesStrategy implements the functionality to update the Attributes for a particular asset. It
 * supports the ability to replaceAll or merge existing attributes. If during a replace all, an attribute name is set
 * but has no value, it is removed. Otherwise it is ignored.
 * 
 * @author dcarver
 * 
 */
public class UpdateAssetAttributesStrategy extends AbstractRepositoryProvider {

   /**
    * This processes the request for updating an Asset's Attributes.
    * 
    * @param request
    *           The initial request for updating
    * @return The result of the update.
    */
   public UpdateAssetAttributesResponse process(UpdateAssetAttributesRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UpdateAssetAttributesResponse response = new UpdateAssetAttributesResponse();

      try {
         AssetKey assetKey = request.getAssetKey();
         BasicAssetInfo basicInfo = populateMinBasicAssetInfo(assetKey);

         AssetFactory factory = new AssetFactory(basicInfo, wso2);
         Asset asset = factory.createAsset();

         if (!asset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         asset.findAsset();
         if (!asset.isLocked()) {
            asset.lockAsset();
            asset.save();
         }

         // get the existing assetInfo
         AssetInfo assetInfo = getAssetInfo(asset);

         if (assetInfo == null) {
            return createAssetTypeException(errorDataList, response);
         }

         ExtendedAssetInfo extInfo = request.getExtendedAssetInfo();
         List<AttributeNameValue> attributes = extInfo.getAttribute();
         List<String> respattrs = response.getAttributeName();

         if (attributes.size() > 0) {
            if (request.isReplaceCurrent()) {
               GovernanceArtifact artifact = asset.getGovernanceArtifact();
               for (AttributeNameValue attr : attributes) {
                  if (attr.getAttributeName() != null) {
                     if (attr.getAttributeValueString() != null) {
                        artifact.setAttribute(attr.getAttributeName(), attr.getAttributeValueString());
                        respattrs.add(attr.getAttributeName());
                     } else if (attr.getAttributeValueLong() != null) {
                        artifact.setAttribute(attr.getAttributeName(), attr.getAttributeValueLong().toString());
                        respattrs.add(attr.getAttributeName());
                     } else {
                        artifact.removeAttribute(attr.getAttributeName());
                     }
                  }
               }
            } else {
               // RSProviderUtil.updateExtendedInfo(
               // assetInfo.getExtendedAssetInfo(),
               // request.getExtendedAssetInfo());
            }
            asset.save();
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
