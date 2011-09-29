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
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

public abstract class AbstractRepositoryProvider implements RepositoryServiceProvider {

   @Override
   public abstract CreateAndSubmitAssetResponse createAndSubmitAsset(
            CreateAndSubmitAssetRequest createAndSubmitAssetRequest);

   @Override
   public abstract GetAssetTreeByAttributesResponse getAssetTreeByAttributes(
            GetAssetTreeByAttributesRequest getAssetTreeByAttributesRequest);

   @Override
   public abstract GetBasicAssetInfoResponse getBasicAssetInfo(GetBasicAssetInfoRequest getBasicAssetInfoRequest);

   @Override
   public abstract GetAssetDependenciesResponse getAssetDependencies(
            GetAssetDependenciesRequest getAssetDependenciesRequest);

   @Override
   public abstract UpdateAssetResponse updateAsset(UpdateAssetRequest updateAssetRequest);

   @Override
   public abstract RemoveAssetResponse removeAsset(RemoveAssetRequest removeAssetRequest);

   @Override
   public abstract CreateCompleteAssetResponse createCompleteAsset(CreateCompleteAssetRequest createCompleteAssetRequest);

   @Override
   public abstract SubscribeResponse subscribe(SubscribeRequest subscribeRequest);

   @Override
   public abstract GetAssetSubmissionPropertiesResponse getAssetSubmissionProperties(
            GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest);

   @Override
   public abstract SubmitForPublishingResponse submitForPublishing(SubmitForPublishingRequest submitForPublishingRequest);

   @Override
   public abstract GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategory(
            GetAllAssetsGroupedByCategoryRequest getAllAssetsGroupedByCategoryRequest);

   @Override
   public abstract CreateAssetResponse createAsset(CreateAssetRequest createAssetRequest);

   @Override
   public abstract GetAssetVersionsResponse getAssetVersions(GetAssetVersionsRequest getAssetVersionsRequest);

   @Override
   public abstract UpdateCompleteAssetResponse updateCompleteAsset(UpdateCompleteAssetRequest updateCompleteAssetRequest);

   @Override
   public abstract GetAssetStatusResponse getAssetStatus(GetAssetStatusRequest getAssetStatusRequest);

   @Override
   public abstract UnlockAssetResponse unlockAsset(UnlockAssetRequest unlockAssetRequest);

   @Override
   public abstract LockAssetResponse lockAsset(LockAssetRequest lockAssetRequest);

   @Override
   public abstract SearchAssetsDetailedResponse searchAssetsDetailed(
            SearchAssetsDetailedRequest searchAssetsDetailedRequest);

   @Override
   public abstract UpdateAssetDependenciesResponse updateAssetDependencies(
            UpdateAssetDependenciesRequest updateAssetDependenciesRequest);

   @Override
   public abstract ApproveAssetResponse approveAsset(ApproveAssetRequest approveAssetRequest);

   @Override
   public abstract ValidateAssetResponse validateAsset(ValidateAssetRequest validateAssetRequest);

   @Override
   public abstract UpdateSubscriptionResponse updateSubscription(UpdateSubscriptionRequest updateSubscriptionRequest);

   @Override
   public abstract RejectAssetResponse rejectAsset(RejectAssetRequest rejectAssetRequest);

   @Override
   public abstract GetAssetLifeCycleStatesResponse getAssetLifeCycleStates(
            GetAssetLifeCycleStatesRequest getAssetLifeCycleStatesRequest);

   @Override
   public abstract UnsubscribeResponse unsubscribe(UnsubscribeRequest unsubscribeRequest);

   @Override
   public abstract GetSubscriptionResponse getSubscription(GetSubscriptionRequest getSubscriptionRequest);

   @Override
   public abstract UpdateAssetAttributesResponse updateAssetAttributes(
            UpdateAssetAttributesRequest updateAssetAttributesRequest);

   @Override
   public abstract SearchAssetsResponse searchAssets(SearchAssetsRequest searchAssetsRequest);

   @Override
   public abstract UpdateAssetArtifactsResponse updateAssetArtifacts(
            UpdateAssetArtifactsRequest updateAssetArtifactsRequest);

   @Override
   public abstract GetAssetTypesResponse getAssetTypes(GetAssetTypesRequest getAssetTypesRequest);

   @Override
   public abstract GetAssetInfoResponse getAssetInfo(GetAssetInfoRequest getAssetInfoRequest);

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

   public static AttributeNameValue newAttribute(String name, boolean value) {
      AttributeNameValue attr = new AttributeNameValue();
      attr.setAttributeName(name);
      attr.setAttributeValueBoolean(value);
      return attr;
   }

}
