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
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;

//import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author mgorovoy
 * @dcarver refactored lots of the code
 * 
 */
public class RepositoryServiceProviderImpl extends AbstractRepositoryProvider {

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
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}
	
	private AssetInfo getAssetInfo(Asset asset) throws GovernanceException {
		AssetInfo assetInfo = new AssetInfo();
		
		BasicAssetInfo info = getBasicAssetInfo(asset);
		assetInfo.setBasicAssetInfo(info);
		
		ExtendedAssetInfo extendedInfo = getExtendedAssetInfo(asset);
		assetInfo.setExtendedAssetInfo(extendedInfo);
		
		GovernanceArtifact gart = asset.getGovernanceArtifact(); 
		GovernanceArtifact[] dependencies = gart.getDependencies();
		if (dependencies != null && dependencies.length > 0) {
			List <ArtifactInfo> artifacts = assetInfo.getArtifactInfo();
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
	
	private BasicAssetInfo getBasicAssetInfo(Asset asset) throws GovernanceException {
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
	
	private AssetKey getAssetKey(Asset asset) throws GovernanceException {
		GovernanceArtifact gart = asset.getGovernanceArtifact();
		AssetKey assetKey = new AssetKey();
		
		assetKey.setAssetId(asset.getId());
		assetKey.setAssetName(gart.getAttribute(AssetConstants.TURMERIC_NAME));
		assetKey.setType(gart.getAttribute(AssetConstants.TURMERIC_TYPE));
		assetKey.setVersion(gart.getAttribute(AssetConstants.TURMERIC_VERSION));
		
		return assetKey;
	}
	
	private ExtendedAssetInfo getExtendedAssetInfo(Asset asset) throws GovernanceException{
		ExtendedAssetInfo eai = new ExtendedAssetInfo();
		
		return eai;
	}

	private BasicAssetInfo populateMinBasicAssetInfo(AssetKey assetKey) {
		BasicAssetInfo basicInfo = new BasicAssetInfo();
		basicInfo.setAssetName(assetKey.getAssetName());
		basicInfo.setAssetType(assetKey.getType());
		basicInfo.setVersion(assetKey.getVersion());
		basicInfo.setAssetKey(assetKey);
		return basicInfo;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#unsubscribe(org.ebayopensource.turmeric.repository.v1.services.UnsubscribeRequest)
	 */
	@Override
	public UnsubscribeResponse unsubscribe(UnsubscribeRequest request) {
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#searchAssets(org.ebayopensource.turmeric.repository.v1.services.SearchAssetsRequest)
	 */
	@Override
	public SearchAssetsResponse searchAssets(SearchAssetsRequest request) {
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAssetArtifacts(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetArtifactsRequest)
	 */
	@Override
	public UpdateAssetArtifactsResponse updateAssetArtifacts(
			UpdateAssetArtifactsRequest request) {
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
				//TODO: remove and add artifacts
				// remove Artifacts
				// add artifacts
				
			} else {
				// Update existing artifacts and add new ones
			}

			// populate the response
			response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
			return RSProviderUtil.setSuccessResponse(response);

		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
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
			
			//TODO Re-implement approveAsset

			response.setTimestamp(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(new GregorianCalendar()));
			response.setVersion(gart.getAttribute(AssetConstants.TURMERIC_VERSION));
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#createAndSubmitAsset(org.ebayopensource.turmeric.repository.v1.services.CreateAndSubmitAssetRequest)
	 */
	@Override
	public CreateAndSubmitAssetResponse createAndSubmitAsset(
			CreateAndSubmitAssetRequest request) {
		Registry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		CreateAndSubmitAssetResponse response = new CreateAndSubmitAssetResponse();

		try {
			AssetInfo assetInfo = request.getAssetInfo();
			BasicAssetInfo basicInfo = assetInfo.getBasicAssetInfo();
			AssetKey assetKey = basicInfo.getAssetKey();
			if (assetKey.getAssetId() == null
					&& assetKey.getAssetName() == null) {
				return createErrorMissingAssetName(errorDataList, response);
			}
			
			AssetFactory factory = new AssetFactory(basicInfo, wso2);
			Asset asset = factory.createAsset();

			if (asset.hasDuplicates()) {
				return createErrorDuplicateAsset(errorDataList, response);
			}

			asset.createAsset();
			
			for (ArtifactInfo artinfo : assetInfo.getArtifactInfo()) {
				asset.addArtifact(artinfo);
			}

			asset.save();

			// TODO add submittal of asset
//			RSLifeCycle.submit(asset, request.getComment());

			assetKey = (getAssetKey(asset));
			response.setAssetKey(assetKey);
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetTypes(org.ebayopensource.turmeric.repository.v1.services.GetAssetTypesRequest)
	 */
	@Override
	public GetAssetTypesResponse getAssetTypes(GetAssetTypesRequest request) {
		GetAssetTypesResponse response = new GetAssetTypesResponse();
				
		String[] assetTypes = {"Service", "Endpoint", "WSDL", "Schema"};
		response.getAssetType().addAll(Arrays.asList(assetTypes));
		response.setAck(AckValue.SUCCESS);
		return response;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetTreeByAttributes(org.ebayopensource.turmeric.repository.v1.services.GetAssetTreeByAttributesRequest)
	 */
	@Override
	public GetAssetTreeByAttributesResponse getAssetTreeByAttributes(
			GetAssetTreeByAttributesRequest request) {
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
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetSubmissionProperties(org.ebayopensource.turmeric.repository.v1.services.GetAssetSubmissionPropertiesRequest)
	 */
	@Override
	public GetAssetSubmissionPropertiesResponse getAssetSubmissionProperties(
			GetAssetSubmissionPropertiesRequest request) {
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
				return createAssetNotLocked(errorDataList, response);
			}
			
			asset.save();
			
			// TODO: Add new update logic
			
			AssetInfo assetInfo = getAssetInfo(asset);

			// populate the response
			response.setAssetInfo(assetInfo);
			response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							new UpdateAssetResponse(),
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

			response.setTimestamp(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(new GregorianCalendar()));
			response.setVersion(asset
					.getProperty(RSProviderUtil.__artifactVersionPropName));
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#searchAssetsDetailed(org.ebayopensource.turmeric.repository.v1.services.SearchAssetsDetailedRequest)
	 */
	@Override
	public SearchAssetsDetailedResponse searchAssetsDetailed(
			SearchAssetsDetailedRequest request) {
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getSubscription(org.ebayopensource.turmeric.repository.v1.services.GetSubscriptionRequest)
	 */
	@Override
	public GetSubscriptionResponse getSubscription(
			GetSubscriptionRequest request) {
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#submitForPublishing(org.ebayopensource.turmeric.repository.v1.services.SubmitForPublishingRequest)
	 */
	@Override
	public SubmitForPublishingResponse submitForPublishing(
			SubmitForPublishingRequest request) {
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

			response.setTimestamp(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(new GregorianCalendar()));
			response.setVersion(asset
					.getProperty(AssetConstants.TURMERIC_VERSION));
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetStatus(org.ebayopensource.turmeric.repository.v1.services.GetAssetStatusRequest)
	 */
	@Override
	public GetAssetStatusResponse getAssetStatus(GetAssetStatusRequest request) {
		Registry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		GetAssetStatusResponse response = new GetAssetStatusResponse();

		try {
			AssetKey assetKey = request.getAssetKey();
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				return createAssetNotFoundError(errorDataList, response);
			}

			Resource asset = wso2.get(assetId);
			String state = RSLifeCycle.getState(asset);

			AssetStatus assetStatus = new AssetStatus();
			assetStatus.setState(state);
			response.setAssetStatus(assetStatus);

			response.setVersion(asset
					.getProperty(AssetConstants.TURMERIC_VERSION));
			return RSProviderUtil.setSuccessResponse(response);

		} catch (Exception ex) {
			ex.printStackTrace();
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#createCompleteAsset(org.ebayopensource.turmeric.repository.v1.services.CreateCompleteAssetRequest)
	 */
	@Override
	public CreateCompleteAssetResponse createCompleteAsset(
			CreateCompleteAssetRequest request) {
		Registry wso2Registry = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		CreateCompleteAssetResponse response = new CreateCompleteAssetResponse();

		try {
			AssetInfo assetInfo = request.getAssetInfo();
			BasicAssetInfo basicInfo = assetInfo.getBasicAssetInfo();
			AssetKey assetKey = basicInfo.getAssetKey();
			
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
			
//			RSProviderUtil.createDependencies(assetKey,
//					assetInfo.getFlattenedRelationship());

			response.setAssetKey(assetKey);
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
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
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		} 
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetDependencies(org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesRequest)
	 */
	@Override
	public GetAssetDependenciesResponse getAssetDependencies(
			GetAssetDependenciesRequest request) {
		Registry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		GetAssetDependenciesResponse response = new GetAssetDependenciesResponse();

		try {
			AssetKey assetKey = request.getAssetKey();
			BasicAssetInfo basicInfo = populateMinBasicAssetInfo(assetKey);
			
			AssetFactory factory = new AssetFactory(basicInfo, wso2);
			Asset asset = factory.createAsset();
			
			if (!asset.exists()) {
				return createAssetNotFoundError(errorDataList, response);
			}

			asset.findAsset();

			FlattenedRelationship relationship = getRelationShip(asset);

			// populate the response
			response.setFlattenedRelationship(relationship);
			response.setVersion(basicInfo.getVersion());
			return RSProviderUtil.setSuccessResponse(response);

		} catch (Exception ex) {
			ex.printStackTrace();
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}
	
	private FlattenedRelationship getRelationShip(Asset asset) {
		FlattenedRelationship relationship = new FlattenedRelationship();
		//TODO complete me!
		
		return relationship;
		
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetInfo(org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoRequest)
	 */
	@Override
	public GetAssetInfoResponse getAssetInfo(GetAssetInfoRequest request) {
		Registry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		GetAssetInfoResponse response = new GetAssetInfoResponse();

		try {
			
			AssetKey assetKey = request.getAssetKey();
			GovernanceArtifact artifact = null;
			AssetFactory factory = null;
			
			if (assetKey.getAssetId() != null) {
				artifact = GovernanceUtils.retrieveGovernanceArtifactById(wso2, assetKey.getAssetId());
				factory = new AssetFactory(artifact, wso2);
			}
			
			if (artifact == null) {
				factory = new AssetFactory(assetKey, wso2);
			}
			
			Asset asset = factory.createAsset();

			if (!asset.exists()) {
				return createAssetNotFoundError(errorDataList, response);
			}
			
			asset.findAsset();

			if (asset.getGovernanceArtifact() == null) {
				return createAssetNotFoundError(errorDataList, response);
			}
			
			AssetInfo assetInfo = getAssetInfo(asset);

			if (assetInfo == null) {
				return createAssetTypeException(errorDataList, response);
			}

			// populate the response
			response.setAssetInfo(assetInfo);
			response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
			return RSProviderUtil.setSuccessResponse(response);

		} catch (Exception ex) {
			ex.printStackTrace();
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}


	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetLifeCycleStates(org.ebayopensource.turmeric.repository.v1.services.GetAssetLifeCycleStatesRequest)
	 */
	@Override
	public GetAssetLifeCycleStatesResponse getAssetLifeCycleStates(
			GetAssetLifeCycleStatesRequest request) {
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
	public UpdateAssetAttributesResponse updateAssetAttributes(
			UpdateAssetAttributesRequest request) {
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
				return createAssetNotLocked(errorDataList, response);
			}

			// get the existing assetInfo
			AssetInfo assetInfo = getAssetInfo(asset);

			if (assetInfo == null) {
				return createAssetTypeException(errorDataList, response);
			}

			if (request.isReplaceCurrent()) {
				// Uh...this will never work...it never actually updates the item
				assetInfo.setExtendedAssetInfo(request.getExtendedAssetInfo());
			} else {
//				RSProviderUtil.updateExtendedInfo(
//						assetInfo.getExtendedAssetInfo(),
//						request.getExtendedAssetInfo());
			}
			
			asset.save();

			// populate the response
			response.setVersion(assetInfo.getBasicAssetInfo().getVersion());
			return RSProviderUtil.setSuccessResponse(response);

		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetVersions(org.ebayopensource.turmeric.repository.v1.services.GetAssetVersionsRequest)
	 */
	@Override
	public GetAssetVersionsResponse getAssetVersions(
			GetAssetVersionsRequest request) {
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateCompleteAsset(org.ebayopensource.turmeric.repository.v1.services.UpdateCompleteAssetRequest)
	 */
	@Override
	public UpdateCompleteAssetResponse updateCompleteAsset(
			UpdateCompleteAssetRequest request) {
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
			
//			if (!asset.isLocked()) {
//				return createAssetNotLocked(errorDataList, response);
//			}

			// get the existing assetInfo
			AssetInfo origAssetInfo = getAssetInfo(asset); 

			if (origAssetInfo == null) {
				return createAssetTypeException(errorDataList, response);
			}

			AssetKey origAssetKey = origAssetInfo.getBasicAssetInfo()
					.getAssetKey();

			// update the assetInfo
			AssetKey newAssetKey = new AssetKey();
			newAssetKey.setAssetName(basicInfo.getAssetName());
			newAssetKey.setAssetId(origAssetKey.getAssetId());

			basicInfo.setAssetKey(newAssetKey);
			origAssetInfo.setBasicAssetInfo(basicInfo);
			
			GovernanceArtifact wso2artifact = asset.getGovernanceArtifact();
			updateBasicInfo(basicInfo, wso2artifact);
			//updateRelationships - i.e. dependencies
			//update LifeCycles 
			
			// Update the asset
			asset.save();

			// populate the response
			response.setAssetKey(assetKey);
			response.setVersion(basicInfo.getVersion());
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		} 
	}
	
	private GovernanceArtifact updateBasicInfo(BasicAssetInfo basicInfo, GovernanceArtifact artifact) {
		GovernanceArtifact gart = artifact;
		return gart;
	}


	@Override
	public CreateAssetResponse createAsset(CreateAssetRequest request) {
		Registry wso2Registry = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		CreateAssetResponse response = new CreateAssetResponse();

		try {
			BasicAssetInfo basicInfo = request.getBasicAssetInfo();
			AssetKey assetKey = basicInfo.getAssetKey();

			AssetFactory assetFactory = new AssetFactory(basicInfo,
					wso2Registry);
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
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getBasicAssetInfo(org.ebayopensource.turmeric.repository.v1.services.GetBasicAssetInfoRequest)
	 */
	@Override
	public GetBasicAssetInfoResponse getBasicAssetInfo(
			GetBasicAssetInfoRequest request) {
		Registry wso2Registry = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		GetBasicAssetInfoResponse response = new GetBasicAssetInfoResponse();

		try {
			AssetKey assetKey = request.getAssetKey();
			String assetId = assetKey.getAssetId();
			
			GovernanceArtifact asset = GovernanceUtils.retrieveGovernanceArtifactById(wso2Registry, assetId);
			
			if (asset == null) {
				return createAssetNotFoundError(errorDataList, response);
			}
			
			String type = asset.getAttribute("type");
			
			// Create the basic service info structure
			BasicAssetInfo basicAssetInfo = createBasicAsset(assetKey, asset,
					type);
			
			// populate the response
			response.setBasicAssetInfo(basicAssetInfo);
			response.setVersion(basicAssetInfo.getVersion());
			return RSProviderUtil.setSuccessResponse(response);

		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							new GetBasicAssetInfoResponse(),
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	private BasicAssetInfo createBasicAsset(AssetKey assetKey,
			GovernanceArtifact asset, String type) throws GovernanceException {
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetKey(assetKey);
		
		basicAssetInfo.setAssetName(asset.getAttribute("name"));
		basicAssetInfo.setAssetType(type);
		basicAssetInfo.setGroupName(asset.getAttribute("Owner"));
		basicAssetInfo.setNamespace(asset.getQName().getNamespaceURI());
		basicAssetInfo.setAssetDescription(asset.getAttribute("description"));
		return basicAssetInfo;
	}


	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAssetDependencies(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesRequest)
	 */
	@Override
	public UpdateAssetDependenciesResponse updateAssetDependencies(
			UpdateAssetDependenciesRequest request) {
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
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateSubscription(org.ebayopensource.turmeric.repository.v1.services.UpdateSubscriptionRequest)
	 */
	@Override
	public UpdateSubscriptionResponse updateSubscription(
			UpdateSubscriptionRequest request) {
		return null;
	}

}
