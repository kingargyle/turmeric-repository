/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.Collection;
import org.wso2.carbon.registry.core.Resource;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;

import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

/**
 * @author mgorovoy
 * 
 */
public class RepositoryServiceProviderImpl implements RepositoryServiceProvider {

	private static final String _SYSTEM_GOVERNANCE = "/_system/governance/trunk";

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#lockAsset(org.ebayopensource.turmeric.repository.v1.services.LockAssetRequest)
	 */
	@Override
	public LockAssetResponse lockAsset(LockAssetRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		LockAssetResponse response = new LockAssetResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), null, null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = wso2.get(assetId);
			if (!RSProviderUtil.islocked(asset)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_LOCK_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}
			RSProviderUtil.lock(asset);
			wso2.put(assetId, asset);

			AssetInfo assetInfo = RSProviderUtil.getAssetInfo(assetKey, asset);

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
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#unsubscribe(org.ebayopensource.turmeric.repository.v1.services.UnsubscribeRequest)
	 */
	@Override
	public UnsubscribeResponse unsubscribe(UnsubscribeRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#searchAssets(org.ebayopensource.turmeric.repository.v1.services.SearchAssetsRequest)
	 */
	@Override
	public SearchAssetsResponse searchAssets(SearchAssetsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAssetArtifacts(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetArtifactsRequest)
	 */
	@Override
	public UpdateAssetArtifactsResponse updateAssetArtifacts(
			UpdateAssetArtifactsRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		UpdateAssetArtifactsResponse response = new UpdateAssetArtifactsResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), null, null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = wso2.get(assetId);
			if (!RSProviderUtil.islocked(asset)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_LOCK_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			// get the existing assetInfo
			AssetInfo assetInfo = RSProviderUtil.getAssetInfo(assetKey, asset);

			if (assetInfo == null) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_TYPE_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			if (request.isReplaceCurrent()) {
				RSProviderUtil.removeArtifacts(assetId);
				RSProviderUtil.updateArtifacts(assetKey, null,
						request.getArtifactInfo());
			} else {
				RSProviderUtil.updateArtifacts(assetKey,
						assetInfo.getArtifactInfo(), request.getArtifactInfo());
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
		} finally {
			try {
				if (request.isPartialUpdate()
						|| (response.getAck() == AckValue.SUCCESS && response
								.getErrorMessage() == null)) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#approveAsset(org.ebayopensource.turmeric.repository.v1.services.ApproveAssetRequest)
	 */
	@Override
	public ApproveAssetResponse approveAsset(ApproveAssetRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		ApproveAssetResponse response = new ApproveAssetResponse();

		try {
			ApprovalInfo approvalInfo = request.getApprovalInfo();
			String assetId = approvalInfo.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = wso2.get(assetId);
			RSLifeCycle.approve(asset, approvalInfo.getComments());

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
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#createAndSubmitAsset(org.ebayopensource.turmeric.repository.v1.services.CreateAndSubmitAssetRequest)
	 */
	@Override
	public CreateAndSubmitAssetResponse createAndSubmitAsset(
			CreateAndSubmitAssetRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		CreateAndSubmitAssetResponse response = new CreateAndSubmitAssetResponse();

		try {
			AssetInfo assetInfo = request.getAssetInfo();
			BasicAssetInfo basicInfo = assetInfo.getBasicAssetInfo();
			AssetKey assetKey = basicInfo.getAssetKey();

			if (assetKey.getAssetId() == null
					&& assetKey.getAssetName() == null) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NAME_MISSING
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			String assetType = basicInfo.getAssetType();
			assetKey = RSProviderUtil.completeAssetKey(assetKey, assetType,
					null);
			String assetId = assetKey.getAssetId();

			if (wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.DUPLICATE_ASSET
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = RSProviderUtil.newAssetResource();

			asset.setProperty(RSProviderUtil.__artifactVersionPropName,
					basicInfo.getVersion());
			RSProviderUtil.updateResourceProperties(asset,
					assetInfo.getExtendedAssetInfo());

			if ("Service".equalsIgnoreCase(assetType)) {
				asset.setMediaType("application/vnd.wso2-service+xml");
//				String content = RSProviderUtil.getAssetInfoXml(assetInfo);
//				if (content != null) {
//					InputStream contentStream = new ByteArrayInputStream(
//							content.getBytes("UTF-8"));
//					asset.setContentStream(contentStream);
//				}
			} else 	{
				asset.setDescription(assetInfo.getBasicAssetInfo()
						.getAssetDescription());
			}

			RSLifeCycle.submit(asset, request.getComment());

			wso2.put(assetId, asset);

			RSProviderUtil.removeArtifacts(assetId);
			RSProviderUtil.removeDependencies(assetId);

			RSProviderUtil.createArtifacts(assetKey,
					assetInfo.getArtifactInfo());
			RSProviderUtil.createDependencies(assetKey,
					assetInfo.getFlattenedRelationship());

			response.setAssetKey(assetKey);
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetTypes(org.ebayopensource.turmeric.repository.v1.services.GetAssetTypesRequest)
	 */
	@Override
	public GetAssetTypesResponse getAssetTypes(GetAssetTypesRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetTreeByAttributes(org.ebayopensource.turmeric.repository.v1.services.GetAssetTreeByAttributesRequest)
	 */
	@Override
	public GetAssetTreeByAttributesResponse getAssetTreeByAttributes(
			GetAssetTreeByAttributesRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#removeAsset(org.ebayopensource.turmeric.repository.v1.services.RemoveAssetRequest)
	 */
	@Override
	public RemoveAssetResponse removeAsset(RemoveAssetRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		RemoveAssetResponse response = new RemoveAssetResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), null, null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			RSProviderUtil.removeArtifacts(assetId);
			RSProviderUtil.removeDependencies(assetId);

			wso2.delete(assetId);

			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							new RemoveAssetResponse(),
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetSubmissionProperties(org.ebayopensource.turmeric.repository.v1.services.GetAssetSubmissionPropertiesRequest)
	 */
	@Override
	public GetAssetSubmissionPropertiesResponse getAssetSubmissionProperties(
			GetAssetSubmissionPropertiesRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAsset(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetRequest)
	 */
	@Override
	public UpdateAssetResponse updateAsset(UpdateAssetRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		UpdateAssetResponse response = new UpdateAssetResponse();

		try {
			BasicAssetInfo basicInfo = request.getBasicAssetInfo();
			AssetKey origAssetKey = RSProviderUtil.completeAssetKey(
					basicInfo.getAssetKey(), basicInfo.getAssetType(), null);
			String origAssetId = origAssetKey.getAssetId();

			if (!wso2.resourceExists(origAssetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = wso2.get(origAssetId);
			if (!RSProviderUtil.islocked(asset)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_LOCK_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			// get the existing assetInfo
			AssetInfo assetInfo = RSProviderUtil.getAssetInfo(origAssetKey,
					asset);
			if (assetInfo == null) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_TYPE_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}
			BasicAssetInfo origBasicInfo = assetInfo.getBasicAssetInfo();

			// update the assetInfo
			AssetKey newAssetKey = new AssetKey();
			newAssetKey.setAssetName(basicInfo.getAssetName());
			newAssetKey = RSProviderUtil.completeAssetKey(newAssetKey,
					basicInfo.getAssetType(), null);

			basicInfo.setAssetKey(newAssetKey);
			assetInfo.setBasicAssetInfo(basicInfo);
			// update the basicInfo.version if null
			if (basicInfo.getVersion() == null) {
				basicInfo.setVersion(origBasicInfo.getVersion());
			}
			asset.setProperty(RSProviderUtil.__artifactVersionPropName,
					basicInfo.getVersion() != null ? basicInfo.getVersion()
							: origBasicInfo.getVersion());

			if ("Service".equals(basicInfo.getAssetType())) {
	//			String content = RSProviderUtil.getAssetInfoXml(assetInfo);
	//			if (content != null) {
	//				InputStream contentStream = new ByteArrayInputStream(
	//						content.getBytes("UTF-8"));
	//				asset.setContentStream(contentStream);
	//			}
			} else {
				asset.setDescription(basicInfo.getAssetDescription());
			}

			// check if assetId have changed
			String newAssetId = basicInfo.getAssetKey().getAssetId();
			if (!origAssetId.equals(newAssetId)) {
				RSProviderUtil.moveAsset(origAssetKey, assetInfo);
			}

			// update the resource
			wso2.put(newAssetKey.getAssetId(), asset);

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
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#rejectAsset(org.ebayopensource.turmeric.repository.v1.services.RejectAssetRequest)
	 */
	@Override
	public RejectAssetResponse rejectAsset(RejectAssetRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		RejectAssetResponse response = new RejectAssetResponse();

		try {
			RejectionInfo rejectionInfo = request.getRejectionInfo();
			String assetId = rejectionInfo.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
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
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#searchAssetsDetailed(org.ebayopensource.turmeric.repository.v1.services.SearchAssetsDetailedRequest)
	 */
	@Override
	public SearchAssetsDetailedResponse searchAssetsDetailed(
			SearchAssetsDetailedRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getSubscription(org.ebayopensource.turmeric.repository.v1.services.GetSubscriptionRequest)
	 */
	@Override
	public GetSubscriptionResponse getSubscription(
			GetSubscriptionRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetDependenciesByGraph(org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesByGraphRequest)
	 */
	@Override
	public GetAssetDependenciesByGraphResponse getAssetDependenciesByGraph(
			GetAssetDependenciesByGraphRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#submitForPublishing(org.ebayopensource.turmeric.repository.v1.services.SubmitForPublishingRequest)
	 */
	@Override
	public SubmitForPublishingResponse submitForPublishing(
			SubmitForPublishingRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		SubmitForPublishingResponse response = new SubmitForPublishingResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), null, null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = wso2.get(assetId);
			RSLifeCycle.submit(asset, request.getComment());

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
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetStatus(org.ebayopensource.turmeric.repository.v1.services.GetAssetStatusRequest)
	 */
	@Override
	public GetAssetStatusResponse getAssetStatus(GetAssetStatusRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		GetAssetStatusResponse response = new GetAssetStatusResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), null, null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			Resource asset = wso2.get(assetId);
			String state = RSLifeCycle.getState(asset);

			AssetStatus assetStatus = new AssetStatus();
			assetStatus.setState(state);
			response.setAssetStatus(assetStatus);

			response.setVersion(asset
					.getProperty(RSProviderUtil.__artifactVersionPropName));
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
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		CreateCompleteAssetResponse response = new CreateCompleteAssetResponse();

		try {
			AssetInfo assetInfo = request.getAssetInfo();
			BasicAssetInfo basicInfo = assetInfo.getBasicAssetInfo();
			AssetKey assetKey = basicInfo.getAssetKey();

			if (assetKey.getAssetId() == null
					&& assetKey.getAssetName() == null) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NAME_MISSING
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			String assetType = basicInfo.getAssetType();
			assetKey = RSProviderUtil.completeAssetKey(assetKey, assetType,
					null);
			String assetId = assetKey.getAssetId();

			if (wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.DUPLICATE_ASSET
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = RSProviderUtil.newAssetResource();

			asset.setProperty(RSProviderUtil.__artifactVersionPropName,
					basicInfo.getVersion());
			RSProviderUtil.updateResourceProperties(asset,
					assetInfo.getExtendedAssetInfo());

			if ("Service".equalsIgnoreCase(assetType)) {
				asset.setMediaType("application/vnd.wso2-service+xml");
//				String content = RSProviderUtil.getAssetInfoXml(assetInfo);
//				if (content != null) {
//					InputStream contentStream = new ByteArrayInputStream(
//							content.getBytes("UTF-8"));
//					asset.setContentStream(contentStream);
//				}
			} else // TODO handle other known types
			{
				asset.setDescription(assetInfo.getBasicAssetInfo()
						.getAssetDescription());
			}

			wso2.put(assetId, asset);

			RSProviderUtil.removeArtifacts(assetId);
			RSProviderUtil.removeDependencies(assetId);

			RSProviderUtil.createArtifacts(assetKey,
					assetInfo.getArtifactInfo());
			RSProviderUtil.createDependencies(assetKey,
					assetInfo.getFlattenedRelationship());

			response.setAssetKey(assetKey);
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#subscribe(org.ebayopensource.turmeric.repository.v1.services.SubscribeRequest)
	 */
	@Override
	public SubscribeResponse subscribe(SubscribeRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#validateAsset(org.ebayopensource.turmeric.repository.v1.services.ValidateAssetRequest)
	 */
	@Override
	public ValidateAssetResponse validateAsset(ValidateAssetRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#unlockAsset(org.ebayopensource.turmeric.repository.v1.services.UnlockAssetRequest)
	 */
	@Override
	public UnlockAssetResponse unlockAsset(UnlockAssetRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		UnlockAssetResponse response = new UnlockAssetResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), null, null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource service = wso2.get(assetId);
			RSProviderUtil.unlock(service);
			wso2.put(assetId, service);

			AssetInfo assetInfo = RSProviderUtil
					.getAssetInfo(assetKey, service);

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
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetDependencies(org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesRequest)
	 */
	@Override
	public GetAssetDependenciesResponse getAssetDependencies(
			GetAssetDependenciesRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		GetAssetDependenciesResponse response = new GetAssetDependenciesResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), null, null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			Resource asset = wso2.get(assetId);

			FlattenedRelationship relationship = new FlattenedRelationship();
			RSProviderUtil.retrieveAssociations(null, relationship, asset);

			// populate the response
			response.setFlattenedRelationship(relationship);
			response.setVersion(asset
					.getProperty(RSProviderUtil.__artifactVersionPropName));
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
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetInfo(org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoRequest)
	 */
	@Override
	public GetAssetInfoResponse getAssetInfo(GetAssetInfoRequest request) {
		System.err
				.println("RepositoryServiceProviderImpl.getAssetInfo: getting assetInfo");
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		GetAssetInfoResponse response = new GetAssetInfoResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), request.getAssetType(), null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			Resource asset = wso2.get(assetId);
			AssetInfo assetInfo = RSProviderUtil.getAssetInfo(assetKey, asset);

			if (assetInfo == null) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_TYPE_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
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
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAllAssetsGroupedByCategory(org.ebayopensource.turmeric.repository.v1.services.GetAllAssetsGroupedByCategoryRequest)
	 */
	@Override
	public GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategory(
			GetAllAssetsGroupedByCategoryRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAssetAttributes(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetAttributesRequest)
	 */
	@Override
	public UpdateAssetAttributesResponse updateAssetAttributes(
			UpdateAssetAttributesRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		UpdateAssetAttributesResponse response = new UpdateAssetAttributesResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), null, null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = wso2.get(assetId);
			if (!RSProviderUtil.islocked(asset)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_LOCK_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			// get the existing assetInfo
			AssetInfo assetInfo = RSProviderUtil.getAssetInfo(assetKey, asset);

			if (assetInfo == null) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_TYPE_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			if (request.isReplaceCurrent()) {
				assetInfo.setExtendedAssetInfo(request.getExtendedAssetInfo());
			} else {
				RSProviderUtil.updateExtendedInfo(
						assetInfo.getExtendedAssetInfo(),
						request.getExtendedAssetInfo());
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
		} finally {
			try {
				if (request.isPartialUpdate()
						|| (response.getAck() == AckValue.SUCCESS && response
								.getErrorMessage() == null)) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getAssetVersions(org.ebayopensource.turmeric.repository.v1.services.GetAssetVersionsRequest)
	 */
	@Override
	public GetAssetVersionsResponse getAssetVersions(
			GetAssetVersionsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateCompleteAsset(org.ebayopensource.turmeric.repository.v1.services.UpdateCompleteAssetRequest)
	 */
	@Override
	public UpdateCompleteAssetResponse updateCompleteAsset(
			UpdateCompleteAssetRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		UpdateCompleteAssetResponse response = new UpdateCompleteAssetResponse();

		try {
			AssetInfoForUpdate updateInfo = request.getAssetInfoForUpdate();
			BasicAssetInfo basicInfo = updateInfo.getBasicAssetInfo();
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					basicInfo.getAssetKey(), basicInfo.getAssetType(), null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = wso2.get(assetId);
			if (!RSProviderUtil.islocked(asset)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_LOCK_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			// get the existing assetInfo
			AssetInfo origAssetInfo = RSProviderUtil.getAssetInfo(assetKey,
					asset);

			if (origAssetInfo == null) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_TYPE_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			AssetKey origAssetKey = origAssetInfo.getBasicAssetInfo()
					.getAssetKey();

			// update the assetInfo
			AssetKey newAssetKey = new AssetKey();
			newAssetKey.setAssetName(basicInfo.getAssetName());
			newAssetKey = RSProviderUtil.completeAssetKey(newAssetKey,
					basicInfo.getAssetType(), null);

			basicInfo.setAssetKey(newAssetKey);
			origAssetInfo.setBasicAssetInfo(basicInfo);

			FlattenedRelationshipForUpdate relationship = updateInfo
					.getFlattenedRelationshipForUpdate();
			if (request.isReplaceCurrent()) {
				origAssetInfo.setAssetLifeCycleInfo(updateInfo
						.getAssetLifeCycleInfo());
				origAssetInfo.setExtendedAssetInfo(updateInfo
						.getExtendedAssetInfo());
				// update resource properties
				asset.setProperty(RSProviderUtil.__artifactVersionPropName,
						basicInfo.getVersion());
				RSProviderUtil.updateResourceProperties(asset,
						origAssetInfo.getExtendedAssetInfo());
//				String content = RSProviderUtil.getAssetInfoXml(origAssetInfo);
//				if (content != null) {
//					InputStream contentStream = new ByteArrayInputStream(
//							content.getBytes("UTF-8"));
//					asset.setContentStream(contentStream);
//				}
				RSProviderUtil.removeArtifacts(assetId);
				RSProviderUtil.removeDependencies(assetId);
				// update the resource
				wso2.put(assetId, asset);
				RSProviderUtil.updateArtifacts(assetKey, null,
						updateInfo.getArtifactInfo());
				if (relationship != null) {
					RSProviderUtil.updateDependencies(assetKey, null,
							relationship);
				}
			} else {
				RSProviderUtil.updateLifeCycleInfo(
						origAssetInfo.getAssetLifeCycleInfo(),
						updateInfo.getAssetLifeCycleInfo());
				RSProviderUtil.updateExtendedInfo(
						origAssetInfo.getExtendedAssetInfo(),
						updateInfo.getExtendedAssetInfo());
				// update resource properties
				asset.setProperty(RSProviderUtil.__artifactVersionPropName,
						basicInfo.getVersion());
				RSProviderUtil.updateResourceProperties(asset,
						updateInfo.getExtendedAssetInfo());
//				if ("Service".equals(basicInfo.getAssetType())) {
//					String content = RSProviderUtil
//							.getAssetInfoXml(origAssetInfo);
//					if (content != null) {
//						InputStream contentStream = new ByteArrayInputStream(
//								content.getBytes("UTF-8"));
//						asset.setContentStream(contentStream);
//					}
//				}
				// update the resource
				wso2.put(assetId, asset);
				RSProviderUtil.updateArtifacts(assetKey,
						origAssetInfo.getArtifactInfo(),
						updateInfo.getArtifactInfo());
				if (relationship != null) {
					RSProviderUtil.updateDependencies(assetKey,
							origAssetInfo.getFlattenedRelationship(),
							relationship);
				}
			}

			// check if asset name or library name have changed
			if (!assetKey.getAssetId().equals(origAssetKey.getAssetId())) {
				RSProviderUtil.moveAsset(assetKey, origAssetInfo);
			}

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
		} finally {
			try {
				if (request.isPartialUpdate()
						|| (response.getAck() == AckValue.SUCCESS && response
								.getErrorMessage() == null)) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#createAsset(org.ebayopensource.turmeric.repository.v1.services.CreateAssetRequest)
	 */
	@Override
	public CreateAssetResponse createAsset(CreateAssetRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		CreateAssetResponse response = new CreateAssetResponse();

		try {
			BasicAssetInfo basicInfo = request.getBasicAssetInfo();
			AssetKey assetKey = basicInfo.getAssetKey();
			if (assetKey.getAssetId() == null
					&& assetKey.getAssetName() == null) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NAME_MISSING
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			String assetType = basicInfo.getAssetType();
			assetKey = RSProviderUtil.completeAssetKey(assetKey, assetType,
					null);

			String assetId = assetKey.getAssetId();
			if (wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.DUPLICATE_ASSET
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = RSProviderUtil.newAssetResource();
			asset.setProperty(RSProviderUtil.__artifactVersionPropName,
					basicInfo.getVersion());
			RSProviderUtil.lock(asset); // automatically lock created asset

			if ("Service".equalsIgnoreCase(assetType)) {
				asset.setMediaType("application/vnd.wso2-service+xml");
//				String content = RSProviderUtil.getBasicAssetInfoXml(basicInfo);
//				if (content != null) {
//					InputStream contentStream = new ByteArrayInputStream(
//							content.getBytes("UTF-8"));
//					asset.setContentStream(contentStream);
//				}
			} else // TODO handle other known types
			{
				asset.setDescription(basicInfo.getAssetDescription());
			}

			wso2.put(assetId, asset);

			RSProviderUtil.removeArtifacts(assetId);
			RSProviderUtil.removeDependencies(assetId);

			// populate the response
			response.setAssetKey(assetKey);
			return RSProviderUtil.setSuccessResponse(response);
		} catch (Exception ex) {
			return RSProviderUtil
					.handleException(
							ex,
							response,
							RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
		} finally {
			try {
				if (response.getAck() == AckValue.SUCCESS
						&& response.getErrorMessage() == null) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#getBasicAssetInfo(org.ebayopensource.turmeric.repository.v1.services.GetBasicAssetInfoRequest)
	 */
	@Override
	public GetBasicAssetInfoResponse getBasicAssetInfo(
			GetBasicAssetInfoRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		GetBasicAssetInfoResponse response = new GetBasicAssetInfoResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), request.getAssetType(), null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			Resource asset = wso2.get(assetId);

			String type = RSProviderUtil.getAssetType(assetId);

			// Create the basic service info structure
			BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
			basicAssetInfo.setAssetKey(assetKey);

			if ("Service".equals(type)) {
				Document doc = RSProviderUtil.parseContent(asset);
				XPath xpath = XPathFactory.newInstance().newXPath();

				if (Integer.parseInt(xpath.evaluate("count(/*)", doc)) != 1) {
					errorDataList
							.add(RepositoryServiceErrorDescriptor.ASSET_TYPE_EXCEPTION
									.newError());
					return RSProviderUtil.addErrorsToResponse(errorDataList,
							response);
				}

				RSProviderUtil.completeBasicAssetInfo(basicAssetInfo, asset,
						doc);
			} else {
				RSProviderUtil.completeBasicAssetInfo(basicAssetInfo, asset,
						null);
			}

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

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateAssetDependencies(org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesRequest)
	 */
	@Override
	public UpdateAssetDependenciesResponse updateAssetDependencies(
			UpdateAssetDependenciesRequest request) {
		RemoteRegistry wso2 = RSProviderUtil.getRegistry();
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		UpdateAssetDependenciesResponse response = new UpdateAssetDependenciesResponse();

		try {
			AssetKey assetKey = RSProviderUtil.completeAssetKey(
					request.getAssetKey(), null, null);
			String assetId = assetKey.getAssetId();

			if (!wso2.resourceExists(assetId)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_NOT_FOUND_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			wso2.beginTransaction();
			Resource asset = wso2.get(assetId);
			if (!RSProviderUtil.islocked(asset)) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_LOCK_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			// get the existing assetInfo
			AssetInfo assetInfo = RSProviderUtil.getAssetInfo(assetKey, asset);

			if (assetInfo == null) {
				errorDataList
						.add(RepositoryServiceErrorDescriptor.ASSET_TYPE_EXCEPTION
								.newError());
				return RSProviderUtil.addErrorsToResponse(errorDataList,
						response);
			}

			FlattenedRelationshipForUpdate relationship = request
					.getFlattenedRelationshipForUpdate();
			if (request.isReplaceCurrent()) {
				RSProviderUtil.removeDependencies(assetId);
				RSProviderUtil.updateDependencies(assetKey, null, relationship);
			} else {
				RSProviderUtil.updateDependencies(assetKey,
						assetInfo.getFlattenedRelationship(), relationship);
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
		} finally {
			try {
				if ((response.getAck() == AckValue.SUCCESS && response
						.getErrorMessage() == null)) {
					wso2.commitTransaction();
				} else {
					wso2.rollbackTransaction();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see org.ebayopensource.turmeric.repositoryservice.impl.RepositoryServiceProvider#updateSubscription(org.ebayopensource.turmeric.repository.v1.services.UpdateSubscriptionRequest)
	 */
	@Override
	public UpdateSubscriptionResponse updateSubscription(
			UpdateSubscriptionRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
