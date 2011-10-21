/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.BaseResponse;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;

public abstract class AbstractRepositoryProvider {

   /**
    * Creates the error message Missing Asset Name.
    * 
    * @param errorDataList
    *           CommonErrorData list
    * @param response
    *           A response that extends BaseResponse
    * @return the newly created error response
    */
   protected <T extends BaseResponse> T createErrorMissingAssetName(List<CommonErrorData> errorDataList, T response) {
      errorDataList.add(RepositoryServiceErrorDescriptor.ASSET_NAME_MISSING.newError());
      return RSProviderUtil.addErrorsToResponse(errorDataList, response);
   }

   /**
    * Creates the error message "Asset Not Found".
    * 
    * @param errorDataList
    *           CommonErrorData list
    * @param response
    *           A response that extends BaseResponse
    * @return the newly created error response
    */
   protected <T extends BaseResponse> T createAssetNotFoundError(List<CommonErrorData> errorDataList, T response) {
      errorDataList.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION.newError());
      return RSProviderUtil.addErrorsToResponse(errorDataList, response);
   }

   /**
    * Creates the error message "Asset Type Exception".
    * 
    * @param errorDataList
    *           CommonErrorData list
    * @param response
    *           A response that extends BaseResponse
    * @return the newly created error response
    */
   protected <T extends BaseResponse> T createAssetTypeException(List<CommonErrorData> errorDataList, T response) {
      errorDataList.add(RepositoryServiceErrorDescriptor.ASSET_TYPE_EXCEPTION.newError());
      return RSProviderUtil.addErrorsToResponse(errorDataList, response);
   }

   /**
    * Creates the error message "Asset Not Locked".
    * 
    * @param errorDataList
    *           CommonErrorData list
    * @param response
    *           A response that extends BaseResponse
    * @return the newly created error response
    */
   protected <T extends BaseResponse> T createAssetNotLocked(List<CommonErrorData> errorDataList, T response) {
      errorDataList.add(RepositoryServiceErrorDescriptor.ASSET_LOCK_EXCEPTION.newError());
      return RSProviderUtil.addErrorsToResponse(errorDataList, response);
   }

   /**
    * Creates the error message "Duplicate Asset".
    * 
    * @param errorDataList
    *           CommonErrorData list
    * @param response
    *           A response that extends BaseResponse
    * @return the newly created error response
    */
   protected <T extends BaseResponse> T createErrorDuplicateAsset(List<CommonErrorData> errorDataList, T response) {
      errorDataList.add(RepositoryServiceErrorDescriptor.DUPLICATE_ASSET.newError());
      return RSProviderUtil.addErrorsToResponse(errorDataList, response);
   }

   /**
    * Creates the error message for when an asset can't be created.
    * 
    * @param errorDataList
    *           CommonErrorData list
    * @param response
    *           A response that extends BaseResponse
    * @return the newly created error response
    */
   protected <T extends BaseResponse> T createErrorAssetCreation(List<CommonErrorData> errorDataList, T response) {
      errorDataList.add(RepositoryServiceErrorDescriptor.ASSET_CREATION_ERROR.newError());
      return RSProviderUtil.addErrorsToResponse(errorDataList, response);
   }

   /**
    * Creates the error message for when an asset can't be created.
    * 
    * @param errorDataList
    *           CommonErrorData list
    * @param response
    *           A response that extends BaseResponse
    * @return the newly created error response
    */
   protected <T extends BaseResponse> T createErrorInvalidInput(List<CommonErrorData> errorDataList, T response) {
      errorDataList.add(RepositoryServiceErrorDescriptor.INVALID_INPUT_EXCEPTION.newError());
      return RSProviderUtil.addErrorsToResponse(errorDataList, response);
   }

   /**
    * Creates the error message for when an asset is missing the namespace.
    * 
    * @param errorDataList
    *           CommonErrorData list
    * @param response
    *           A response that extends BaseResponse
    * @return the newly created error response
    */
   protected <T extends BaseResponse> T createErrorMissingNamespace(List<CommonErrorData> errorDataList, T response) {
      errorDataList.add(RepositoryServiceErrorDescriptor.ASSET_NAMESPACE_MISSING.newError());
      return RSProviderUtil.addErrorsToResponse(errorDataList, response);
   }

   public AttributeNameValue newAttribute(String name, String value) {
      AttributeNameValue attr = new AttributeNameValue();
      attr.setAttributeName(name);
      attr.setAttributeValueString(value);
      return attr;
   }

   public AttributeNameValue newAttribute(String name, long value) {
      AttributeNameValue attr = new AttributeNameValue();
      attr.setAttributeName(name);
      attr.setAttributeValueLong(value);
      return attr;
   }

   protected BasicAssetInfo populateMinBasicAssetInfo(AssetKey assetKey) {
      BasicAssetInfo basicInfo = new BasicAssetInfo();
      basicInfo.setAssetName(assetKey.getAssetName());
      basicInfo.setAssetType(assetKey.getType());
      basicInfo.setVersion(assetKey.getVersion());
      basicInfo.setAssetKey(assetKey);
      return basicInfo;
   }

   protected AssetInfo getAssetInfo(Asset asset) throws Exception {
      AssetInfo assetInfo = new AssetInfo();

      BasicAssetInfo info = getBasicAssetInfo(asset);
      assetInfo.setBasicAssetInfo(info);

      ExtendedAssetInfo extendedInfo = getExtendedAssetInfo(asset);
      assetInfo.setExtendedAssetInfo(extendedInfo);

      GovernanceArtifact gart = asset.getGovernanceArtifact();
      GovernanceArtifact[] dependencies = gart.getDependencies();
      if (dependencies != null && dependencies.length > 0) {
         List<ArtifactInfo> artifacts = assetInfo.getArtifactInfo();
         for (GovernanceArtifact dependency : dependencies) {
            ArtifactInfo ai = new ArtifactInfo();
            Artifact art = new Artifact();
            art.setArtifactCategory(dependency.getAttribute(AssetConstants.TURMERIC_ARTIFACT_CATEGORY));
            art.setArtifactDisplayName(dependency.getAttribute(AssetConstants.TURMERIC_DISPLAY_NAME));
            art.setArtifactIdentifier(dependency.getId());
            ai.setArtifact(art);
            artifacts.add(ai);
         }
      }

      return assetInfo;
   }

   BasicAssetInfo getBasicAssetInfo(Asset asset) throws Exception {
      GovernanceArtifact gart = asset.getGovernanceArtifact();
      BasicAssetInfo bi = new BasicAssetInfo();
      AssetKey assetKey = getAssetKey(asset);
      bi.setAssetKey(assetKey);
      bi.setAssetName(assetKey.getAssetName());
      bi.setAssetType(assetKey.getType());
      bi.setVersion(assetKey.getVersion());
      bi.setGroupName(gart.getAttribute(AssetConstants.TURMERIC_OWNER));
      bi.setNamespace(gart.getAttribute(AssetConstants.TURMERIC_NAMESPACE));
      bi.setAssetDescription(gart.getAttribute(AssetConstants.TURMERIC_DESCRIPTION));
      bi.setAssetLongDescription(gart.getAttribute(AssetConstants.TURMERIC_LONG_DESCRIPTION));
      return bi;
   }

   ExtendedAssetInfo getExtendedAssetInfo(Asset asset) throws GovernanceException {
      ExtendedAssetInfo eai = new ExtendedAssetInfo();

      return eai;
   }

   protected AssetKey getAssetKey(Asset asset) throws Exception {
      GovernanceArtifact gart = asset.getGovernanceArtifact();
      AssetKey assetKey = new AssetKey();

      assetKey.setAssetId(asset.getId());
      assetKey.setAssetName(gart.getAttribute(AssetConstants.TURMERIC_NAME));
      assetKey.setType(gart.getAttribute(AssetConstants.TURMERIC_TYPE));
      assetKey.setVersion(gart.getAttribute(AssetConstants.TURMERIC_VERSION));

      return assetKey;
   }

   public AttributeNameValue newAttribute(String name, boolean value) {
      AttributeNameValue attr = new AttributeNameValue();
      attr.setAttributeName(name);
      attr.setAttributeValueBoolean(value);
      return attr;
   }

   protected FlattenedRelationship getRelationShip(Asset asset) {
      FlattenedRelationship relationship = new FlattenedRelationship();
      // TODO complete me!
   
      return relationship;
   
   }

   protected BasicAssetInfo createBasicAsset(AssetKey assetKey, GovernanceArtifact asset, String type) throws Exception {
      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetKey(assetKey);
   
      basicAssetInfo.setAssetName(asset.getAttribute("name"));
      basicAssetInfo.setAssetType(type);
      basicAssetInfo.setGroupName(asset.getAttribute("Owner"));
      basicAssetInfo.setNamespace(asset.getQName().getNamespaceURI());
      basicAssetInfo.setAssetDescription(asset.getAttribute("description"));
      return basicAssetInfo;
   }

}
