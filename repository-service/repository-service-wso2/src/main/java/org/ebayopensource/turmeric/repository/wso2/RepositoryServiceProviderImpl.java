/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.datatype.DatatypeFactory;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.repository.wso2.operations.*;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.RegistryConstants;
import org.wso2.carbon.registry.core.Resource;

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
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      LockAssetResponse response = new LockAssetResponse();

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

         AssetInfo assetInfo = getAssetInfo(asset);

         // populate the response
         response.setAssetInfo(assetInfo);
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
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
      RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      SearchAssetsResponse response = new SearchAssetsResponse();

      if (request.getAssetQuery() == null) {
         return createErrorInvalidInput(errorDataList, response);
      }

      ArtifactCriteria artifactCriteria = request.getAssetQuery().getArtifactCriteria();
      List<Artifact> artifacts = artifactCriteria.getArtifact();

      Registry registry = RSProviderUtil.getRegistry();

      String searchByID = "SELECT REG_PATH_ID, REG_NAME FROM REG_RESOURCE WHERE REG_PATH_ID = ?";
      try {
         Resource queryResource = registry.newResource();
         queryResource.setContent(searchByID);
         queryResource.setMediaType(RegistryConstants.SQL_QUERY_MEDIA_TYPE);
         queryResource
                  .addProperty(RegistryConstants.RESULT_TYPE_PROPERTY_NAME, RegistryConstants.RESOURCES_RESULT_TYPE);
         registry.put(RegistryConstants.CONFIG_REGISTRY_BASE_PATH + RegistryConstants.QUERIES_COLLECTION_PATH
                  + "/turmeric-queries-findbyid", queryResource);

         Map<String, String> parameters = new ConcurrentHashMap<String, String>();
         for (Artifact art : artifacts) {
            parameters.put(art.getArtifactIdentifier(), "%services%");
         }
         registry.executeQuery(RegistryConstants.CONFIG_REGISTRY_BASE_PATH + RegistryConstants.QUERIES_COLLECTION_PATH
                  + "/turmeric-queries-findbyid", parameters);

         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }

   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAssetArtifacts(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetArtifactsRequest)
    */
   @Override
   public UpdateAssetArtifactsResponse updateAssetArtifacts(UpdateAssetArtifactsRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UpdateAssetArtifactsResponse response = new UpdateAssetArtifactsResponse();

      try {
         AssetFactory factory = new AssetFactory(request.getAssetKey(), wso2);
         Asset asset = factory.createAsset();
         String assetId = asset.getId();

         if (!wso2.resourceExists(assetId)) {
            return createAssetNotFoundError(errorDataList, response);
         }

         if (!asset.isLocked()) {
            return createAssetNotLocked(errorDataList, response);
         }

         // get the existing assetInfo
         AssetInfo assetInfo = getAssetInfo(asset);

         if (assetInfo == null) {
            return createAssetTypeException(errorDataList, response);
         }

         if (request.isReplaceCurrent()) {
            // TODO: remove and add artifacts
            // remove Artifacts
            // add artifacts

         } else {
            // Update existing artifacts and add new ones
         }

         // populate the response
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);

      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#approveAsset(org.ebayopensource.turmeric.repository.v1.services.ApproveAssetRequest)
    */
   @Override
   public ApproveAssetResponse approveAsset(ApproveAssetRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      ApproveAssetResponse response = new ApproveAssetResponse();

      try {
         ApprovalInfo approvalInfo = request.getApprovalInfo();
         String assetId = approvalInfo.getAssetId();
         GovernanceArtifact gart = GovernanceUtils.retrieveGovernanceArtifactById(wso2, assetId);

         if (gart == null) {
            return createAssetNotFoundError(errorDataList, response);
         }

         // TODO Re-implement approveAsset

         response.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
         response.setVersion(gart.getAttribute(AssetConstants.TURMERIC_VERSION));
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
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
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      RemoveAssetResponse response = new RemoveAssetResponse();

      try {
         AssetKey assetKey = request.getAssetKey();
         String assetId = assetKey.getAssetId();
         String path = null;
         path = GovernanceUtils.getArtifactPath(wso2, assetId);

         if (path == null) {
            return createAssetNotFoundError(errorDataList, response);
         }

         GovernanceUtils.removeArtifact(wso2, assetId);

         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
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
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UpdateAssetResponse response = new UpdateAssetResponse();

      try {
         BasicAssetInfo basicInfo = request.getBasicAssetInfo();

         AssetFactory factory = new AssetFactory(basicInfo, wso2);
         Asset asset = factory.createAsset();

         if (!asset.exists()) {
            return createErrorDuplicateAsset(errorDataList, response);
         }

         asset.findAsset();

         if (!asset.isLocked()) {
            asset.lockAsset();
         }

         GovernanceArtifact artifact = asset.getGovernanceArtifact();
         artifact.setAttribute(AssetConstants.TURMERIC_DESCRIPTION, basicInfo.getAssetDescription());
         artifact.setAttribute(AssetConstants.TURMERIC_NAME, basicInfo.getAssetName());
         artifact.setAttribute(AssetConstants.TURMERIC_NAMESPACE, basicInfo.getNamespace());
         artifact.setAttribute(AssetConstants.TURMERIC_LONG_DESCRIPTION, basicInfo.getAssetLongDescription());
         artifact.setAttribute(AssetConstants.TURMERIC_VERSION, basicInfo.getVersion());

         asset.save();

         AssetInfo assetInfo = getAssetInfo(asset);

         // populate the response
         response.setAssetInfo(assetInfo);
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, new UpdateAssetResponse(),
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#rejectAsset(org.ebayopensource.turmeric.repository.v1.services.RejectAssetRequest)
    */
   @Override
   public RejectAssetResponse rejectAsset(RejectAssetRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      RejectAssetResponse response = new RejectAssetResponse();

      try {
         RejectionInfo rejectionInfo = request.getRejectionInfo();
         String assetId = rejectionInfo.getAssetId();
         GovernanceArtifact gart = GovernanceUtils.retrieveGovernanceArtifactById(wso2, assetId);

         if (gart == null) {
            return createAssetNotFoundError(errorDataList, response);
         }

         Resource asset = wso2.get(assetId);
         RSLifeCycle.reject(asset, rejectionInfo.getComments());

         wso2.put(assetId, asset);

         response.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
         response.setVersion(asset.getProperty(RSProviderUtil.__artifactVersionPropName));
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
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
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      SubmitForPublishingResponse response = new SubmitForPublishingResponse();

      try {

         AssetFactory factory = new AssetFactory(request.getAssetKey(), wso2);
         Asset gasset = factory.createAsset();
         AssetKey assetKey = getAssetKey(gasset);
         String assetId = assetKey.getAssetId();

         if (!gasset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         Resource asset = wso2.get(assetId);
         RSLifeCycle.submit(asset, request.getComment());

         wso2.put(assetId, asset);

         response.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
         response.setVersion(asset.getProperty(AssetConstants.TURMERIC_VERSION));
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
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
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UnlockAssetResponse response = new UnlockAssetResponse();

      try {
         AssetKey assetKey = request.getAssetKey();
         BasicAssetInfo basicInfo = populateMinBasicAssetInfo(assetKey);

         AssetFactory factory = new AssetFactory(basicInfo, wso2);
         Asset asset = factory.createAsset();

         if (!asset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         asset.findAsset();

         if (asset.isLocked()) {
            asset.unlock();
            asset.save();
         }

         AssetInfo assetInfo = getAssetInfo(asset);

         // populate the response
         response.setAssetInfo(assetInfo);
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
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

         GovernanceArtifact wso2artifact = asset.getGovernanceArtifact();
         updateBasicInfo(basicInfo, wso2artifact);
         // updateRelationships - i.e. dependencies
         // update LifeCycles

         // Update the asset
         asset.save();

         // populate the response
         response.setAssetKey(assetKey);
         response.setVersion(basicInfo.getVersion());
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

   private GovernanceArtifact updateBasicInfo(BasicAssetInfo basicInfo, GovernanceArtifact artifact) {
      GovernanceArtifact gart = artifact;
      return gart;
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
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      UpdateAssetDependenciesResponse response = new UpdateAssetDependenciesResponse();

      try {
         AssetFactory factory = new AssetFactory(request.getAssetKey(), wso2);
         Asset asset = factory.createAsset();

         if (asset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         asset.findAsset();

         if (asset.isLocked()) {
            return createAssetNotLocked(errorDataList, response);
         }

         // get the existing assetInfo
         AssetInfo assetInfo = getAssetInfo(asset);

         if (assetInfo == null) {
            return createAssetTypeException(errorDataList, response);
         }

         if (request.isReplaceCurrent()) {
            // Remove and Update everything.
         } else {
            // Add and Update
         }

         // populate the response
         response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
         return RSProviderUtil.setSuccessResponse(response);

      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

   /**
    * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateSubscription(org.ebayopensource.turmeric.repository.v1.services.UpdateSubscriptionRequest)
    */
   @Override
   public UpdateSubscriptionResponse updateSubscription(UpdateSubscriptionRequest request) {
      return null;
   }
}
