/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.impl;

import org.ebayopensource.turmeric.repository.v2.services.ApproveAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.ApproveAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.CreateAndSubmitAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateAndSubmitAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAllAssetsGroupedByCategoryRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAllAssetsGroupedByCategoryResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAllProjectsAndGroupsRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAllProjectsAndGroupsResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetDependenciesByGraphRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetDependenciesByGraphResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetDependenciesRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetDependenciesResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetLifeCycleStatesRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetLifeCycleStatesResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetStatusRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetStatusResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetSubmissionPropertiesRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetSubmissionPropertiesResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetTreeByAttributesRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetTreeByAttributesResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetTypesRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetTypesResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetVersionsRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetVersionsResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetBasicAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetBasicAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetCatalogAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetCatalogAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetLibraryListRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetLibraryListResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetSubscriptionRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetSubscriptionResponse;
import org.ebayopensource.turmeric.repository.v2.services.GetUsersProjectsAndGroupsRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetUsersProjectsAndGroupsResponse;
import org.ebayopensource.turmeric.repository.v2.services.LockAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.LockAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.RejectAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.RejectAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.RemoveAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.RemoveAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.SearchAssetsDetailedRequest;
import org.ebayopensource.turmeric.repository.v2.services.SearchAssetsDetailedResponse;
import org.ebayopensource.turmeric.repository.v2.services.SearchAssetsRequest;
import org.ebayopensource.turmeric.repository.v2.services.SearchAssetsResponse;
import org.ebayopensource.turmeric.repository.v2.services.SubmitForPublishingRequest;
import org.ebayopensource.turmeric.repository.v2.services.SubmitForPublishingResponse;
import org.ebayopensource.turmeric.repository.v2.services.SubscribeRequest;
import org.ebayopensource.turmeric.repository.v2.services.SubscribeResponse;
import org.ebayopensource.turmeric.repository.v2.services.UnlockAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UnlockAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.UnsubscribeRequest;
import org.ebayopensource.turmeric.repository.v2.services.UnsubscribeResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetArtifactsRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetArtifactsResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetAttributesRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetAttributesResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetDependenciesByGraphRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetDependenciesByGraphResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetDependenciesRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetDependenciesResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateSubscriptionRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateSubscriptionResponse;
import org.ebayopensource.turmeric.repository.v2.services.ValidateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.ValidateAssetResponse;

public interface RepositoryServiceProvider {

	public LockAssetResponse lockAsset(LockAssetRequest lockAssetRequest);

	public UnsubscribeResponse unsubscribe(UnsubscribeRequest unsubscribeRequest);

	public GetUsersProjectsAndGroupsResponse getUsersProjectsAndGroups(
			GetUsersProjectsAndGroupsRequest getUsersProjectsAndGroupsRequest);

	public SearchAssetsResponse searchAssets(SearchAssetsRequest searchAssetsRequest);

	public UpdateAssetArtifactsResponse updateAssetArtifacts(UpdateAssetArtifactsRequest updateAssetArtifactsRequest);


	public UpdateAssetDependenciesByGraphResponse updateAssetDependenciesByGraph(
			UpdateAssetDependenciesByGraphRequest updateAssetDependenciesByGraphRequest);

	public ApproveAssetResponse approveAsset(
			ApproveAssetRequest approveAssetRequest);


	public CreateAndSubmitAssetResponse createAndSubmitAsset(
			CreateAndSubmitAssetRequest createAndSubmitAssetRequest);


	public GetAssetTypesResponse getAssetTypes(
			GetAssetTypesRequest getAssetTypesRequest);

	public GetAssetTreeByAttributesResponse getAssetTreeByAttributes(
			GetAssetTreeByAttributesRequest getAssetTreeByAttributesRequest);

	public RemoveAssetResponse removeAsset(
			RemoveAssetRequest removeAssetRequest);

	public GetAssetSubmissionPropertiesResponse getAssetSubmissionProperties(
			GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest);

	public UpdateAssetResponse updateAsset(
			UpdateAssetRequest updateAssetRequest);

	public RejectAssetResponse rejectAsset(
			RejectAssetRequest rejectAssetRequest);

	public GetAllProjectsAndGroupsResponse getAllProjectsAndGroups(
			GetAllProjectsAndGroupsRequest getAllProjectsAndGroupsRequest);

	public SearchAssetsDetailedResponse searchAssetsDetailed(
			SearchAssetsDetailedRequest searchAssetsDetailedRequest);

	public GetSubscriptionResponse getSubscription(
			GetSubscriptionRequest getSubscriptionRequest);

	public GetAssetDependenciesByGraphResponse getAssetDependenciesByGraph(
			GetAssetDependenciesByGraphRequest getAssetDependenciesByGraphRequest);

	public SubmitForPublishingResponse submitForPublishing(
			SubmitForPublishingRequest submitForPublishingRequest);

	public GetAssetStatusResponse getAssetStatus(
			GetAssetStatusRequest getAssetStatusRequest);

	public CreateCompleteAssetResponse createCompleteAsset(
			CreateCompleteAssetRequest createCompleteAssetRequest);

	public SubscribeResponse subscribe(
			SubscribeRequest subscribeRequest);

	public ValidateAssetResponse validateAsset(
			ValidateAssetRequest validateAssetRequest);

	public UnlockAssetResponse unlockAsset(
			UnlockAssetRequest unlockAssetRequest);

	public GetAssetDependenciesResponse getAssetDependencies(
			GetAssetDependenciesRequest getAssetDependenciesRequest);

	public GetAssetInfoResponse getAssetInfo(
			GetAssetInfoRequest getAssetInfoRequest);

	public GetAssetLifeCycleStatesResponse getAssetLifeCycleStates(
			GetAssetLifeCycleStatesRequest getAssetLifeCycleStatesRequest);

	public GetCatalogAssetInfoResponse getCatalogAssetInfo(
			GetCatalogAssetInfoRequest getCatalogAssetInfoRequest);

	public GetLibraryListResponse getLibraryList(
			GetLibraryListRequest getLibraryListRequest);

	public GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategory(
			GetAllAssetsGroupedByCategoryRequest getAllAssetsGroupedByCategoryRequest);

	public UpdateAssetAttributesResponse updateAssetAttributes(
			UpdateAssetAttributesRequest updateAssetAttributesRequest);

	public GetAssetVersionsResponse getAssetVersions(
			GetAssetVersionsRequest getAssetVersionsRequest);

	public UpdateCompleteAssetResponse updateCompleteAsset(
			UpdateCompleteAssetRequest updateCompleteAssetRequest);

	public CreateAssetResponse createAsset(
			CreateAssetRequest createAssetRequest);

	public GetBasicAssetInfoResponse getBasicAssetInfo(
			GetBasicAssetInfoRequest getBasicAssetInfoRequest);

	public UpdateAssetDependenciesResponse updateAssetDependencies(
			UpdateAssetDependenciesRequest updateAssetDependenciesRequest);

	public UpdateSubscriptionResponse updateSubscription(
			UpdateSubscriptionRequest updateSubscriptionRequest);

}
