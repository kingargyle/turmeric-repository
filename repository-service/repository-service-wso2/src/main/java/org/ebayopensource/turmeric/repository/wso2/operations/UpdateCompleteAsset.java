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
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfoForUpdate;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.UpdateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
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

         updateBasicInfo(basicInfo, asset);

         // Add Update Dependencies

         ExtendedAssetInfo extInfo = request.getAssetInfoForUpdate().getExtendedAssetInfo();

         updateExtendedAttributes(extInfo, asset);

         // update LifeCycles

         // Update the asset
         asset.save();

         // populate the response
         response.setAssetKey(assetKey);
         response.setVersion(basicInfo.getVersion());
         response.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

   private void updateBasicInfo(BasicAssetInfo basicInfo, Asset asset) throws GovernanceException {
      GovernanceArtifact gart = asset.getGovernanceArtifact();

      if (basicInfo.getAssetDescription() != null) {
         gart.setAttribute(AssetConstants.TURMERIC_DESCRIPTION, basicInfo.getAssetDescription());
      }

      if (basicInfo.getAssetLongDescription() != null) {
         gart.setAttribute(AssetConstants.TURMERIC_LONG_DESCRIPTION, basicInfo.getAssetLongDescription());
      }

      if (basicInfo.getGroupName() != null) {
         gart.setAttribute(AssetConstants.TURMERIC_OWNER, basicInfo.getGroupName());
      }
      asset.save();
   }

   private void updateExtendedAttributes(ExtendedAssetInfo extInfo, Asset asset) throws GovernanceException {
      GovernanceArtifact gart = asset.getGovernanceArtifact();

      for (AttributeNameValue attr : extInfo.getAttribute()) {
         if (attr.getAttributeName() != null) {
            if (attr.getAttributeValueString() != null) {
               gart.setAttribute(attr.getAttributeName(), attr.getAttributeValueString());
            } else if (attr.getAttributeValueLong() != null) {
               gart.setAttribute(attr.getAttributeName(), attr.getAttributeValueLong().toString());
            }
         }
      }

      asset.save();
   }
}
