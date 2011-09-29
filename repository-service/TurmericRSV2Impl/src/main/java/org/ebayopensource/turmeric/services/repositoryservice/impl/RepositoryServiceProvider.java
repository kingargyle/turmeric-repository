/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.impl;

import org.ebayopensource.turmeric.repository.v2.services.*;

public interface RepositoryServiceProvider {

   public LockAssetResponse lockAsset(LockAssetRequest lockAssetRequest);

   public UnsubscribeResponse unsubscribe(UnsubscribeRequest unsubscribeRequest);

   public SearchAssetsResponse searchAssets(SearchAssetsRequest searchAssetsRequest);

   public UpdateAssetArtifactsResponse updateAssetArtifacts(UpdateAssetArtifactsRequest updateAssetArtifactsRequest);

   public ApproveAssetResponse approveAsset(ApproveAssetRequest approveAssetRequest);

   public CreateAndSubmitAssetResponse createAndSubmitAsset(CreateAndSubmitAssetRequest createAndSubmitAssetRequest);

   public GetAssetTypesResponse getAssetTypes(GetAssetTypesRequest getAssetTypesRequest);

   public GetAssetTreeByAttributesResponse getAssetTreeByAttributes(
            GetAssetTreeByAttributesRequest getAssetTreeByAttributesRequest);

   public RemoveAssetResponse removeAsset(RemoveAssetRequest removeAssetRequest);

   public GetAssetSubmissionPropertiesResponse getAssetSubmissionProperties(
            GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest);

   public UpdateAssetResponse updateAsset(UpdateAssetRequest updateAssetRequest);

   public RejectAssetResponse rejectAsset(RejectAssetRequest rejectAssetRequest);

   public SearchAssetsDetailedResponse searchAssetsDetailed(SearchAssetsDetailedRequest searchAssetsDetailedRequest);

   public GetSubscriptionResponse getSubscription(GetSubscriptionRequest getSubscriptionRequest);

   public SubmitForPublishingResponse submitForPublishing(SubmitForPublishingRequest submitForPublishingRequest);

   public GetAssetStatusResponse getAssetStatus(GetAssetStatusRequest getAssetStatusRequest);

   public CreateCompleteAssetResponse createCompleteAsset(CreateCompleteAssetRequest createCompleteAssetRequest);

   public SubscribeResponse subscribe(SubscribeRequest subscribeRequest);

   public ValidateAssetResponse validateAsset(ValidateAssetRequest validateAssetRequest);

   public UnlockAssetResponse unlockAsset(UnlockAssetRequest unlockAssetRequest);

   public GetAssetDependenciesResponse getAssetDependencies(GetAssetDependenciesRequest getAssetDependenciesRequest);

   public GetAssetInfoResponse getAssetInfo(GetAssetInfoRequest getAssetInfoRequest);

   public GetAssetLifeCycleStatesResponse getAssetLifeCycleStates(
            GetAssetLifeCycleStatesRequest getAssetLifeCycleStatesRequest);

   public GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategory(
            GetAllAssetsGroupedByCategoryRequest getAllAssetsGroupedByCategoryRequest);

   public UpdateAssetAttributesResponse updateAssetAttributes(UpdateAssetAttributesRequest updateAssetAttributesRequest);

   public GetAssetVersionsResponse getAssetVersions(GetAssetVersionsRequest getAssetVersionsRequest);

   public UpdateCompleteAssetResponse updateCompleteAsset(UpdateCompleteAssetRequest updateCompleteAssetRequest);

   public CreateAssetResponse createAsset(CreateAssetRequest createAssetRequest);

   public GetBasicAssetInfoResponse getBasicAssetInfo(GetBasicAssetInfoRequest getBasicAssetInfoRequest);

   public UpdateAssetDependenciesResponse updateAssetDependencies(
            UpdateAssetDependenciesRequest updateAssetDependenciesRequest);

   public UpdateSubscriptionResponse updateSubscription(UpdateSubscriptionRequest updateSubscriptionRequest);

}
