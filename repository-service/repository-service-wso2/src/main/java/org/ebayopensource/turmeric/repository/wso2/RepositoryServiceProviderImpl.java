/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.operations.*;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

/**
 * @author mgorovoy
 * @dcarver refactored lots of the code
 * 
 */
public class RepositoryServiceProviderImpl extends AbstractRepositoryProvider implements RepositoryServiceProvider {

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#lockAsset(org.ebayopensource.turmeric.repository.v1.services.LockAssetRequest)
    */
   @Override
   public LockAssetResponse lockAsset(LockAssetRequest request) {
      return new LockAsset().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#unsubscribe(org.ebayopensource.turmeric.repository.v1.services.UnsubscribeRequest)
    */
   @Override
   public UnsubscribeResponse unsubscribe(UnsubscribeRequest request) {
      return null;
   }

   /**
    * {@inheritDoc}
    * 
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#searchAssets(org.ebayopensource.turmeric.repository.v1.services.SearchAssetsRequest)
    */
   @Override
   public SearchAssetsResponse searchAssets(SearchAssetsRequest request) {
      return new SearchAssets().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAssetArtifacts(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetArtifactsRequest)
    */
   @Override
   public UpdateAssetArtifactsResponse updateAssetArtifacts(UpdateAssetArtifactsRequest request) {
      return new UpdateAssetArtifacts().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#approveAsset(org.ebayopensource.turmeric.repository.v1.services.ApproveAssetRequest)
    */
   @Override
   public ApproveAssetResponse approveAsset(ApproveAssetRequest request) {
      return new ApproveAsset().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#createAndSubmitAsset(org.ebayopensource.turmeric.repository.v1.services.CreateAndSubmitAssetRequest)
    */
   @Override
   public CreateAndSubmitAssetResponse createAndSubmitAsset(CreateAndSubmitAssetRequest request) {
      return new CreateAndSubmitAsset().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetTypes(org.ebayopensource.turmeric.repository.v1.services.GetAssetTypesRequest)
    */
   @Override
   public GetAssetTypesResponse getAssetTypes(GetAssetTypesRequest request) {
      return new GetAssetTypes().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetTreeByAttributes(org.ebayopensource.turmeric.repository.v1.services.GetAssetTreeByAttributesRequest)
    */
   @Override
   public GetAssetTreeByAttributesResponse getAssetTreeByAttributes(GetAssetTreeByAttributesRequest request) {
      return null;
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#removeAsset(org.ebayopensource.turmeric.repository.v1.services.RemoveAssetRequest)
    */
   @Override
   public RemoveAssetResponse removeAsset(RemoveAssetRequest request) {
      return new RemoveAsset().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetSubmissionProperties(org.ebayopensource.turmeric.repository.v1.services.GetAssetSubmissionPropertiesRequest)
    */
   @Override
   public GetAssetSubmissionPropertiesResponse getAssetSubmissionProperties(GetAssetSubmissionPropertiesRequest request) {
      return null;
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAsset(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetRequest)
    */
   @Override
   public UpdateAssetResponse updateAsset(UpdateAssetRequest request) {
      return new UpdateAsset().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#rejectAsset(org.ebayopensource.turmeric.repository.v1.services.RejectAssetRequest)
    */
   @Override
   public RejectAssetResponse rejectAsset(RejectAssetRequest request) {
      return new RejectAsset().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#searchAssetsDetailed(org.ebayopensource.turmeric.repository.v1.services.SearchAssetsDetailedRequest)
    */
   @Override
   public SearchAssetsDetailedResponse searchAssetsDetailed(SearchAssetsDetailedRequest request) {
      return null;
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getSubscription(org.ebayopensource.turmeric.repository.v1.services.GetSubscriptionRequest)
    */
   @Override
   public GetSubscriptionResponse getSubscription(GetSubscriptionRequest request) {
      return null;
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#submitForPublishing(org.ebayopensource.turmeric.repository.v1.services.SubmitForPublishingRequest)
    */
   @Override
   public SubmitForPublishingResponse submitForPublishing(SubmitForPublishingRequest request) {
      return new SubmitForPublishing().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetStatus(org.ebayopensource.turmeric.repository.v1.services.GetAssetStatusRequest)
    */
   @Override
   public GetAssetStatusResponse getAssetStatus(GetAssetStatusRequest request) {
      return new GetAssetStatus().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#createCompleteAsset(org.ebayopensource.turmeric.repository.v1.services.CreateCompleteAssetRequest)
    */
   @Override
   public CreateCompleteAssetResponse createCompleteAsset(CreateCompleteAssetRequest request) {
      return new CreateCompleteAsset().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#subscribe(org.ebayopensource.turmeric.repository.v1.services.SubscribeRequest)
    */
   @Override
   public SubscribeResponse subscribe(SubscribeRequest request) {
      return null;
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#validateAsset(org.ebayopensource.turmeric.repository.v1.services.ValidateAssetRequest)
    */
   @Override
   public ValidateAssetResponse validateAsset(ValidateAssetRequest request) {
      return null;
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#unlockAsset(org.ebayopensource.turmeric.repository.v1.services.UnlockAssetRequest)
    */
   @Override
   public UnlockAssetResponse unlockAsset(UnlockAssetRequest request) {
      return new UnlockAsset().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetDependencies(org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesRequest)
    */
   @Override
   public GetAssetDependenciesResponse getAssetDependencies(GetAssetDependenciesRequest request) {
      return new GetAssetDependencies().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetInfo(org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoRequest)
    */
   @Override
   public GetAssetInfoResponse getAssetInfo(GetAssetInfoRequest request) {
      return new GetAssetInfo().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetLifeCycleStates(org.ebayopensource.turmeric.repository.v1.services.GetAssetLifeCycleStatesRequest)
    */
   @Override
   public GetAssetLifeCycleStatesResponse getAssetLifeCycleStates(GetAssetLifeCycleStatesRequest request) {
      return null;
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAllAssetsGroupedByCategory(org.ebayopensource.turmeric.repository.v1.services.GetAllAssetsGroupedByCategoryRequest)
    */
   @Override
   public GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategory(
            GetAllAssetsGroupedByCategoryRequest request) {
      return null;
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAssetAttributes(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetAttributesRequest)
    */
   @Override
   public UpdateAssetAttributesResponse updateAssetAttributes(UpdateAssetAttributesRequest request) {
      return new UpdateAssetAttributesStrategy().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetVersions(org.ebayopensource.turmeric.repository.v1.services.GetAssetVersionsRequest)
    */
   @Override
   public GetAssetVersionsResponse getAssetVersions(GetAssetVersionsRequest request) {
      return null;
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateCompleteAsset(org.ebayopensource.turmeric.repository.v1.services.UpdateCompleteAssetRequest)
    */
   @Override
   public UpdateCompleteAssetResponse updateCompleteAsset(UpdateCompleteAssetRequest request) {
      return new UpdateCompleteAsset().process(request);
   }

   @Override
   public CreateAssetResponse createAsset(CreateAssetRequest request) {
      return new CreateAsset().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getBasicAssetInfo(org.ebayopensource.turmeric.repository.v1.services.GetBasicAssetInfoRequest)
    */
   @Override
   public GetBasicAssetInfoResponse getBasicAssetInfo(GetBasicAssetInfoRequest request) {
      return new GetBasicAssetInfo().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAssetDependencies(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesRequest)
    */
   @Override
   public UpdateAssetDependenciesResponse updateAssetDependencies(UpdateAssetDependenciesRequest request) {
      return new UpdateAssetDependencies().process(request);
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateSubscription(org.ebayopensource.turmeric.repository.v1.services.UpdateSubscriptionRequest)
    */
   @Override
   public UpdateSubscriptionResponse updateSubscription(UpdateSubscriptionRequest request) {
      return null;
   }
}
