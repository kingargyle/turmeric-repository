/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repository.v2.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.BaseResponse;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;
import org.ebayopensource.turmeric.common.v1.types.ErrorParameter;
import org.ebayopensource.turmeric.common.v1.types.ErrorSeverity;
import org.ebayopensource.turmeric.errorlibrary.repository.ErrorConstants;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.TurmericRSV2;
import org.ebayopensource.turmeric.runtime.common.exceptions.ErrorDataFactory;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceConstants;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProviderFactory;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceValidateUtil;
import org.ebayopensource.turmeric.services.repositoryservice.impl.ServiceProviderException;

public class TurmericRSV2Impl implements TurmericRSV2 {
	
	
	private static Logger s_logger = Logger.getLogger(TurmericRSV2Impl.class);
	
	private volatile static RepositoryServiceProvider repositoryServiceProvider;
	
	public static void populateProvider() throws ServiceProviderException 
	{
		if (repositoryServiceProvider == null) {
			synchronized (TurmericRSV2Impl.class) {
				if (repositoryServiceProvider == null)	
					repositoryServiceProvider = RepositoryServiceProviderFactory.getInstance();
			}
		}
		
		if(repositoryServiceProvider == null)
		{
			String message = " Requested Service Provider is not properly configured in the " +
			"xml file "+RepositoryServiceConstants.s_providerConfigXml;
			s_logger.error(message);
			throw new ServiceProviderException(message);
		}
	}

	public ApproveAssetResponse approveAsset(
			ApproveAssetRequest approveAssetRequest) {
		
		ApproveAssetResponse approveAssetResponse = new ApproveAssetResponse();
    	try
    	{
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		populateProvider();
    		if (RepositoryServiceValidateUtil.validate(approveAssetRequest, errorDataList)) 
    		{
    			approveAssetResponse = repositoryServiceProvider.approveAsset(approveAssetRequest);
    			if (approveAssetResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, approveAssetResponse);
    			}
    			else 
    			{
    				setSuccessResponse(approveAssetResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, approveAssetResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,approveAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, approveAssetResponse);
		}
    	return approveAssetResponse;
		
	}

	public CreateAndSubmitAssetResponse createAndSubmitAsset(
			CreateAndSubmitAssetRequest createAndSubmitAssetRequest) {
		
		CreateAndSubmitAssetResponse createAndSubmitAssetResponse = new CreateAndSubmitAssetResponse();
    	try
    	{
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		populateProvider();
    		if (RepositoryServiceValidateUtil.validate(createAndSubmitAssetRequest, errorDataList)) 
    		{
    			createAndSubmitAssetResponse = repositoryServiceProvider.createAndSubmitAsset(createAndSubmitAssetRequest);
    			if (createAndSubmitAssetResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, createAndSubmitAssetResponse);
    			}
    			else 
    			{
    				setSuccessResponse(createAndSubmitAssetResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, createAndSubmitAssetResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,createAndSubmitAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, createAndSubmitAssetResponse);
		}
    	return createAndSubmitAssetResponse;
	}

	public CreateAssetResponse createAsset(CreateAssetRequest createAssetRequest) {
		CreateAssetResponse createAssetResponse = new CreateAssetResponse();
    	try
    	{
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		populateProvider();
    		if (RepositoryServiceValidateUtil.validate(createAssetRequest, errorDataList)) 
    		{
    			createAssetResponse = repositoryServiceProvider.createAsset(createAssetRequest);
    			if (createAssetResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, createAssetResponse);
    			}
    			else 
    			{
    				setSuccessResponse(createAssetResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, createAssetResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,createAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, createAssetResponse);
		}
    	return createAssetResponse;
	}

	public CreateCompleteAssetResponse createCompleteAsset(
			CreateCompleteAssetRequest createCompleteAssetRequest) {
		CreateCompleteAssetResponse createCompleteAssetResponse = new CreateCompleteAssetResponse();
    	try
    	{
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		populateProvider();
    		if (RepositoryServiceValidateUtil.validate(createCompleteAssetRequest, errorDataList)) 
    		{
    			createCompleteAssetResponse = repositoryServiceProvider
    			.createCompleteAsset(createCompleteAssetRequest);
    			if (createCompleteAssetResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, createCompleteAssetResponse);
    			}
    			else 
    			{
    				setSuccessResponse(createCompleteAssetResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, createCompleteAssetResponse);
    			createCompleteAssetResponse.setAck(AckValue.FAILURE);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,createCompleteAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, createCompleteAssetResponse);
		}
    	return createCompleteAssetResponse;
	}

	public GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategory(
			GetAllAssetsGroupedByCategoryRequest getAllAssetsGroupedByCategoryRequest) {

		GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategoryResponse = new GetAllAssetsGroupedByCategoryResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAllAssetsGroupedByCategoryRequest, errorDataList)) 
    		{
    			getAllAssetsGroupedByCategoryResponse = repositoryServiceProvider
		    .getAllAssetsGroupedByCategory(getAllAssetsGroupedByCategoryRequest);
    			if (getAllAssetsGroupedByCategoryResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAllAssetsGroupedByCategoryResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getAllAssetsGroupedByCategoryResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAllAssetsGroupedByCategoryResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getAllAssetsGroupedByCategoryResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAllAssetsGroupedByCategoryResponse);
		}
	return getAllAssetsGroupedByCategoryResponse;

	}

	public GetAssetDependenciesResponse getAssetDependencies(
			GetAssetDependenciesRequest getAssetDependenciesRequest) {
		
		GetAssetDependenciesResponse getAssetDependenciesResponse = new GetAssetDependenciesResponse();
    	
		try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAssetDependenciesRequest, errorDataList)) 
    		{
    			getAssetDependenciesResponse = repositoryServiceProvider
    			.getAssetDependencies(getAssetDependenciesRequest);
    			if (getAssetDependenciesResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAssetDependenciesResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getAssetDependenciesResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAssetDependenciesResponse);
    		}
    	}
		catch(ServiceProviderException e)
    	{
    		handleException(e,getAssetDependenciesResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAssetDependenciesResponse);
		}
    	return getAssetDependenciesResponse;
	}

	public GetAssetDependenciesByGraphResponse getAssetDependenciesByGraph(
			GetAssetDependenciesByGraphRequest getAssetDependenciesByGraphRequest) {
		
		GetAssetDependenciesByGraphResponse getAssetDependenciesByGraphResponse = new GetAssetDependenciesByGraphResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAssetDependenciesByGraphRequest, errorDataList)) 
    		{
    			getAssetDependenciesByGraphResponse = repositoryServiceProvider
    			.getAssetDependenciesByGraph(getAssetDependenciesByGraphRequest);
    			if (getAssetDependenciesByGraphResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAssetDependenciesByGraphResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getAssetDependenciesByGraphResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAssetDependenciesByGraphResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getAssetDependenciesByGraphResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAssetDependenciesByGraphResponse);
		}
    	return getAssetDependenciesByGraphResponse;
	}

	public GetAssetInfoResponse getAssetInfo(
			GetAssetInfoRequest getAssetInfoRequest) {
		
		GetAssetInfoResponse getAssetInfoResponse = new GetAssetInfoResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAssetInfoRequest, errorDataList)) 
    		{
    			getAssetInfoResponse = repositoryServiceProvider.getAssetInfo(getAssetInfoRequest);
    			if (getAssetInfoResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAssetInfoResponse);
    			}
    			else
    			{
    				setSuccessResponse(getAssetInfoResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAssetInfoResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getAssetInfoResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAssetInfoResponse);
		}
    	return getAssetInfoResponse;
		
	}

	public GetAssetLifeCycleStatesResponse getAssetLifeCycleStates(
			GetAssetLifeCycleStatesRequest getAssetLifeCycleStatesRequest) {
		GetAssetLifeCycleStatesResponse getAssetLifeCycleStatesResponse = new GetAssetLifeCycleStatesResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAssetLifeCycleStatesRequest, errorDataList)) 
    		{
    			getAssetLifeCycleStatesResponse = repositoryServiceProvider
    			.getAssetLifeCycleStates(getAssetLifeCycleStatesRequest);
    			if (getAssetLifeCycleStatesResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAssetLifeCycleStatesResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getAssetLifeCycleStatesResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAssetLifeCycleStatesResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getAssetLifeCycleStatesResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAssetLifeCycleStatesResponse);
		}
    	return getAssetLifeCycleStatesResponse;
	}

	public GetAssetStatusResponse getAssetStatus(
			GetAssetStatusRequest getAssetStatusRequest) {
		GetAssetStatusResponse getAssetStatusResponse = new GetAssetStatusResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAssetStatusRequest, errorDataList)) 
    		{
    			getAssetStatusResponse = repositoryServiceProvider.getAssetStatus(getAssetStatusRequest);
    			if (getAssetStatusResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAssetStatusResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getAssetStatusResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAssetStatusResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getAssetStatusResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAssetStatusResponse);
		}
    	return getAssetStatusResponse;
	}

	public GetAssetSubmissionPropertiesResponse getAssetSubmissionProperties(
			GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest) {
		GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = new GetAssetSubmissionPropertiesResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAssetSubmissionPropertiesRequest, errorDataList)) 
    		{
    			getAssetSubmissionPropertiesResponse = repositoryServiceProvider
    			.getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
    			if (getAssetSubmissionPropertiesResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAssetSubmissionPropertiesResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getAssetSubmissionPropertiesResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAssetSubmissionPropertiesResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getAssetSubmissionPropertiesResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAssetSubmissionPropertiesResponse);
		}
    	return getAssetSubmissionPropertiesResponse;
	}

	public GetAssetTreeByAttributesResponse getAssetTreeByAttributes(
			GetAssetTreeByAttributesRequest getAssetTreeByAttributesRequest) {
		
		GetAssetTreeByAttributesResponse getAssetTreeByAttributesResponse = new GetAssetTreeByAttributesResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAssetTreeByAttributesRequest, errorDataList)) 
    		{
    			getAssetTreeByAttributesResponse = repositoryServiceProvider
    			.getAssetTreeByAttributes(getAssetTreeByAttributesRequest);
    			if (getAssetTreeByAttributesResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAssetTreeByAttributesResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getAssetTreeByAttributesResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAssetTreeByAttributesResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getAssetTreeByAttributesResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAssetTreeByAttributesResponse);
		}
    	return getAssetTreeByAttributesResponse;
	}

	public GetAssetTypesResponse getAssetTypes(
			GetAssetTypesRequest getAssetTypesRequest) {
		GetAssetTypesResponse getAssetTypesResponse = new GetAssetTypesResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAssetTypesRequest, errorDataList)) 
    		{
    			getAssetTypesResponse = repositoryServiceProvider.getAssetTypes(getAssetTypesRequest);
    			if (getAssetTypesResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAssetTypesResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getAssetTypesResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAssetTypesResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getAssetTypesResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAssetTypesResponse);
		}
    	return getAssetTypesResponse;
	}

	public GetAssetVersionsResponse getAssetVersions(
			GetAssetVersionsRequest getAssetVersionsRequest) {
		
		GetAssetVersionsResponse getAssetVersionsResponse = new GetAssetVersionsResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getAssetVersionsRequest, errorDataList)) 
    		{
    			getAssetVersionsResponse = repositoryServiceProvider.getAssetVersions(getAssetVersionsRequest);
    			if (getAssetVersionsResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getAssetVersionsResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getAssetVersionsResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getAssetVersionsResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getAssetVersionsResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getAssetVersionsResponse);
		}
    	return getAssetVersionsResponse;
		
	}

	public GetBasicAssetInfoResponse getBasicAssetInfo(
			GetBasicAssetInfoRequest getBasicAssetInfoRequest) {
		GetBasicAssetInfoResponse getBasicAssetInfoResponse = new GetBasicAssetInfoResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getBasicAssetInfoRequest, errorDataList)) 
    		{
    			getBasicAssetInfoResponse = repositoryServiceProvider.getBasicAssetInfo(getBasicAssetInfoRequest);
    			if (getBasicAssetInfoResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getBasicAssetInfoResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getBasicAssetInfoResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getBasicAssetInfoResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getBasicAssetInfoResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getBasicAssetInfoResponse);
		}
    	return getBasicAssetInfoResponse;
	}

	public GetCatalogAssetInfoResponse getCatalogAssetInfo(
			GetCatalogAssetInfoRequest getCatalogAssetInfoRequest) {
		GetCatalogAssetInfoResponse getCatalogAssetInfoResponse = new GetCatalogAssetInfoResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
	    	if (RepositoryServiceValidateUtil.validate(getCatalogAssetInfoRequest, errorDataList)) 
	    	{
	    		getCatalogAssetInfoResponse = repositoryServiceProvider
			    .getCatalogAssetInfo(getCatalogAssetInfoRequest);
	    		if (getCatalogAssetInfoResponse.getErrorMessage() != null) 
	    		{
	    			addErrorsToResponse(errorDataList, getCatalogAssetInfoResponse);
	    		} 
	    		else 
	    		{
	    			setSuccessResponse(getCatalogAssetInfoResponse);
	    		}
	    	}
	    	else 
	    	{
	    		addErrorsToResponse(errorDataList, getCatalogAssetInfoResponse);
	    	}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getCatalogAssetInfoResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getCatalogAssetInfoResponse);
		}
    	return getCatalogAssetInfoResponse;
	}

	public GetLibraryListResponse getLibraryList(
			GetLibraryListRequest getLibraryListRequest) {
		
		GetLibraryListResponse getLibraryListResponse = new GetLibraryListResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(getLibraryListRequest, errorDataList)) 
    		{
    			getLibraryListResponse = repositoryServiceProvider.getLibraryList(getLibraryListRequest);
    			if (getLibraryListResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, getLibraryListResponse);
    			}
    			else 
    			{
    				setSuccessResponse(getLibraryListResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, getLibraryListResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,getLibraryListResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, getLibraryListResponse);
		}
    	return getLibraryListResponse;
	}

	public GetSubscriptionResponse getSubscription(
			GetSubscriptionRequest getSubscriptionRequest) {
		// Yet to implement
		return null;
	}

	public LockAssetResponse lockAsset(LockAssetRequest lockAssetRequest) {
		LockAssetResponse lockAssetResponse = new LockAssetResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
	    	if (RepositoryServiceValidateUtil.validate(lockAssetRequest, errorDataList)) 
	    	{
	    		lockAssetResponse = repositoryServiceProvider.lockAsset(lockAssetRequest);
	    		if (lockAssetResponse.getErrorMessage() != null) 
	    		{
	    			addErrorsToResponse(errorDataList, lockAssetResponse);
	    		}
	    		else 
	    		{
	    			setSuccessResponse(lockAssetResponse);
	    		}
	    	}
	    	else 
	    	{
	    		addErrorsToResponse(errorDataList, lockAssetResponse);
	    	}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,lockAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, lockAssetResponse);
		}
    	return lockAssetResponse;
	}

	public RejectAssetResponse rejectAsset(RejectAssetRequest rejectAssetRequest) {
		
		RejectAssetResponse rejectAssetResponse = new RejectAssetResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(rejectAssetRequest, errorDataList)) 
    		{
    			rejectAssetResponse = repositoryServiceProvider.rejectAsset(rejectAssetRequest);
    			if (rejectAssetResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, rejectAssetResponse);
    			}
    			else 
    			{
    				setSuccessResponse(rejectAssetResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, rejectAssetResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,rejectAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, rejectAssetResponse);
		}
    	return rejectAssetResponse;
    	
	}

	public RemoveAssetResponse removeAsset(RemoveAssetRequest removeAssetRequest) {

		RemoveAssetResponse removeAssetResponse = new RemoveAssetResponse();
		
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
			if (RepositoryServiceValidateUtil.validate(removeAssetRequest, errorDataList)) 
			{
			    removeAssetResponse = repositoryServiceProvider.removeAsset(removeAssetRequest);
			    if (removeAssetResponse.getErrorMessage() != null) 
			    {
			    	addErrorsToResponse(errorDataList, removeAssetResponse);
			    } 
			    else 
			    {
			    	setSuccessResponse(removeAssetResponse);
			    }
			}
			else 
			{
			    addErrorsToResponse(errorDataList, removeAssetResponse);
			}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,removeAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, removeAssetResponse);
		}
    	return removeAssetResponse;
		
	}

	public SearchAssetsResponse searchAssets(
			SearchAssetsRequest searchAssetsRequest) {
		
		SearchAssetsResponse searchAssetsResponse = new SearchAssetsResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
			if (RepositoryServiceValidateUtil.validate(searchAssetsRequest, errorDataList)) {
			    searchAssetsResponse = repositoryServiceProvider.searchAssets(searchAssetsRequest);
			    if (searchAssetsResponse.getErrorMessage() != null) {
				// errorDataList.addAll(updateAssetArtifactsResponse.getErrorMessage().getError());
				addErrorsToResponse(errorDataList, searchAssetsResponse);
			    } else {
				setSuccessResponse(searchAssetsResponse);
			    }
			} else {
			    addErrorsToResponse(errorDataList, searchAssetsResponse);
			}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,searchAssetsResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, searchAssetsResponse);
		}
		return searchAssetsResponse;
	}

	public SearchAssetsDetailedResponse searchAssetsDetailed(
			SearchAssetsDetailedRequest searchAssetsDetailedRequest) {
		SearchAssetsDetailedResponse searchAssetsDetailedResponse = new SearchAssetsDetailedResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(searchAssetsDetailedRequest, errorDataList)) 
    		{
    			searchAssetsDetailedResponse = repositoryServiceProvider
    			.searchAssetsDetailed(searchAssetsDetailedRequest);
    			if (searchAssetsDetailedResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, searchAssetsDetailedResponse);
    			}
    			else 
    			{
    				setSuccessResponse(searchAssetsDetailedResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, searchAssetsDetailedResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,searchAssetsDetailedResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, searchAssetsDetailedResponse);
		}
    	return searchAssetsDetailedResponse;
	}

	public SubmitForPublishingResponse submitForPublishing(
			SubmitForPublishingRequest submitForPublishingRequest) {
		SubmitForPublishingResponse submitForPublishingResponse = new SubmitForPublishingResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(submitForPublishingRequest, errorDataList)) 
    		{
    			submitForPublishingResponse = repositoryServiceProvider
    			.submitForPublishing(submitForPublishingRequest);
    			if (submitForPublishingResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, submitForPublishingResponse);
    			} 
    			else 
    			{
    				if(errorDataList.size()>0)
    				{
    					addErrorsToResponse(errorDataList, submitForPublishingResponse);
    					boolean isError = false;
    					boolean isWarning = false;
    					for(CommonErrorData errorData:errorDataList)
    					{
    						if(ErrorSeverity.ERROR.equals(errorData.getSeverity()))
    						{
    							isError = true;
    						}
    						else if(ErrorSeverity.WARNING.equals(errorData.getSeverity()))
    						{
    							isWarning = true;
    						}
    					}
    					
    					if(isError)
    					{
    						submitForPublishingResponse.setAck(AckValue.FAILURE);
    					}
    					else if(isWarning && !AckValue.FAILURE.equals(submitForPublishingResponse.getAck()) && !AckValue.PARTIAL_FAILURE.equals(submitForPublishingResponse.getAck()))
    					{
    						submitForPublishingResponse.setAck(AckValue.WARNING);
    					}
    				}

    				setSuccessResponse(submitForPublishingResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, submitForPublishingResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,submitForPublishingResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, submitForPublishingResponse);
		}
    	return submitForPublishingResponse;
	}

	public SubscribeResponse subscribe(SubscribeRequest subscribeRequest) {
		// Yet to implement
		return null;
	}

	public UnlockAssetResponse unlockAsset(UnlockAssetRequest unlockAssetRequest) {
		
		UnlockAssetResponse unlockAssetResponse = new UnlockAssetResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(unlockAssetRequest, errorDataList)) 
    		{
    			unlockAssetResponse = repositoryServiceProvider.unlockAsset(unlockAssetRequest);
    			if (unlockAssetResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, unlockAssetResponse);
    			}
    			else 
    			{
    				setSuccessResponse(unlockAssetResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, unlockAssetResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,unlockAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, unlockAssetResponse);
		}
    	return unlockAssetResponse;
	}

	public UnsubscribeResponse unsubscribe(UnsubscribeRequest unsubscribeRequest) {
		// Yet to implement
		return null;
	}

	public UpdateAssetResponse updateAsset(UpdateAssetRequest updateAssetRequest) {
		
		UpdateAssetResponse updateAssetResponse = new UpdateAssetResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(updateAssetRequest, errorDataList)) 
    		{
    			updateAssetResponse = repositoryServiceProvider.updateAsset(updateAssetRequest);
    			if (updateAssetResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, updateAssetResponse);
    			}
    			else
    			{
    				setSuccessResponse(updateAssetResponse);
    			}
    		} 
    		else 
    		{
    			addErrorsToResponse(errorDataList, updateAssetResponse);
    		}	
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,updateAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, updateAssetResponse);
		}
    	return updateAssetResponse;
    	
	}

	public UpdateAssetArtifactsResponse updateAssetArtifacts(
			UpdateAssetArtifactsRequest updateAssetArtifactsRequest) {
		
		UpdateAssetArtifactsResponse updateAssetArtifactsResponse = new UpdateAssetArtifactsResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
	    	if (RepositoryServiceValidateUtil.validate(updateAssetArtifactsRequest, errorDataList)) 
	    	{
	    		updateAssetArtifactsResponse = repositoryServiceProvider
			    .updateAssetArtifacts(updateAssetArtifactsRequest);
	    		if (updateAssetArtifactsResponse.getErrorMessage() != null) 
	    		{
	    			addErrorsToResponse(errorDataList, updateAssetArtifactsResponse);
	    		}
	    		else 
	    		{
	    			setSuccessResponse(updateAssetArtifactsResponse);
	    		}
	    	}
	    	else 
	    	{
	    		addErrorsToResponse(errorDataList, updateAssetArtifactsResponse);
	    	}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,updateAssetArtifactsResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, updateAssetArtifactsResponse);
		}
    	return updateAssetArtifactsResponse;
	}

	public UpdateAssetAttributesResponse updateAssetAttributes(
			UpdateAssetAttributesRequest updateAssetAttributesRequest) {
		
		UpdateAssetAttributesResponse updateAssetAttributesResponse = new UpdateAssetAttributesResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(updateAssetAttributesRequest, errorDataList)) 
    		{
    			updateAssetAttributesResponse = repositoryServiceProvider
    			.updateAssetAttributes(updateAssetAttributesRequest);
    			if (updateAssetAttributesResponse.getErrorMessage() != null) 
    			{
    				//List<ErrorData> errorDataList =updateAssetAttributesResponse.getErrorMessage().getError();
    				addErrorsToResponse(errorDataList, updateAssetAttributesResponse);
    			}
    			else
    			{
    				setSuccessResponse(updateAssetAttributesResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, updateAssetAttributesResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,updateAssetAttributesResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, updateAssetAttributesResponse);
		}
    	return updateAssetAttributesResponse;
		
	}

	public UpdateAssetDependenciesResponse updateAssetDependencies(
			UpdateAssetDependenciesRequest updateAssetDependenciesRequest) {

		UpdateAssetDependenciesResponse updateAssetDependenciesResponse = new UpdateAssetDependenciesResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
	    	if (RepositoryServiceValidateUtil.validate(updateAssetDependenciesRequest, errorDataList)) 
	    	{
	    		updateAssetDependenciesResponse = repositoryServiceProvider
			    .updateAssetDependencies(updateAssetDependenciesRequest);
	    		if (updateAssetDependenciesResponse.getErrorMessage() != null) 
	    		{
	    			addErrorsToResponse(errorDataList, updateAssetDependenciesResponse);
	    		}
	    		else 
	    		{
	    			setSuccessResponse(updateAssetDependenciesResponse);
	    		}
	    	}
	    	else 
	    	{
	    		addErrorsToResponse(errorDataList, updateAssetDependenciesResponse);
	    	}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,updateAssetDependenciesResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, updateAssetDependenciesResponse);
		}
    	return updateAssetDependenciesResponse;

	}

	public UpdateAssetDependenciesByGraphResponse updateAssetDependenciesByGraph(
			UpdateAssetDependenciesByGraphRequest updateAssetDependenciesByGraphRequest) {
		
		UpdateAssetDependenciesByGraphResponse updateAssetDependenciesByGraphResponse = new UpdateAssetDependenciesByGraphResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
	    	if (RepositoryServiceValidateUtil.validate(updateAssetDependenciesByGraphRequest, errorDataList)) 
	    	{
	    		updateAssetDependenciesByGraphResponse = repositoryServiceProvider
			    .updateAssetDependenciesByGraph(updateAssetDependenciesByGraphRequest);
	    		if (updateAssetDependenciesByGraphResponse.getErrorMessage() != null) 
	    		{
	    			addErrorsToResponse(errorDataList, updateAssetDependenciesByGraphResponse);
	    		}
	    		else 
	    		{
	    			setSuccessResponse(updateAssetDependenciesByGraphResponse);
	    		}
	    	}
	    	else 
	    	{
	    		addErrorsToResponse(errorDataList, updateAssetDependenciesByGraphResponse);
	    	}
    	}	
    	catch(ServiceProviderException e)
    	{
    		handleException(e,updateAssetDependenciesByGraphResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, updateAssetDependenciesByGraphResponse);
		}
    	return updateAssetDependenciesByGraphResponse;
		
	}

	public UpdateCompleteAssetResponse updateCompleteAsset(
			UpdateCompleteAssetRequest updateCompleteAssetRequest) {
		
		UpdateCompleteAssetResponse updateCompleteAssetResponse = new UpdateCompleteAssetResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(updateCompleteAssetRequest, errorDataList)) 
    		{
    			updateCompleteAssetResponse = repositoryServiceProvider
    			.updateCompleteAsset(updateCompleteAssetRequest);
    			if (updateCompleteAssetResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, updateCompleteAssetResponse);
    			}
    			else 
    			{
    				setSuccessResponse(updateCompleteAssetResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, updateCompleteAssetResponse);
    			updateCompleteAssetResponse.setAck(AckValue.FAILURE);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,updateCompleteAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, updateCompleteAssetResponse);
		}
    	return updateCompleteAssetResponse;
		
	}

	public UpdateSubscriptionResponse updateSubscription(
			UpdateSubscriptionRequest updateSubscriptionRequest) {
		// Yet to implement
		return null;
	}

	public ValidateAssetResponse validateAsset(
			ValidateAssetRequest validateAssetRequest) {
		ValidateAssetResponse validateAssetResponse = new ValidateAssetResponse();
    	try
    	{
    		populateProvider();
    		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
    		if (RepositoryServiceValidateUtil.validate(validateAssetRequest, errorDataList)) 
    		{
    			validateAssetResponse = repositoryServiceProvider.validateAsset(validateAssetRequest);
    			if (validateAssetResponse.getErrorMessage() != null) 
    			{
    				addErrorsToResponse(errorDataList, validateAssetResponse);
    			}
    			else 
    			{
    				setSuccessResponse(validateAssetResponse);
    			}
    		}
    		else 
    		{
    			addErrorsToResponse(errorDataList, validateAssetResponse);
    		}
    	}
    	catch(ServiceProviderException e)
    	{
    		handleException(e,validateAssetResponse,ErrorConstants.SERVICE_PROVIDER_EXCEPTION);
    	}
    	catch (Exception exception) 
    	{
			s_logger.error("Uncaught Exception Occured : ",	exception);
			setExceptionMessageToResponse(exception, validateAssetResponse);
		}
	return validateAssetResponse;
	}
	
    public void addErrorsToResponse(List<CommonErrorData> errors, BaseResponse response) {
    	
    	if(errors != null && !errors.isEmpty() && response != null)
        {
            if(response.getErrorMessage() == null)
            {
                response.setErrorMessage(new ErrorMessage());
            }
            response.getErrorMessage().getError().addAll(errors);
            response.setAck(AckValue.FAILURE);
        }
    }

    public void setSuccessResponse(BaseResponse response) {
	
    	if(response != null)
    		response.setAck(AckValue.SUCCESS);
    }
    
    protected void setExceptionMessageToResponse(Exception exception, BaseResponse response)
	{
		if(response!= null)
		{
			response.setAck(AckValue.FAILURE);
			
			if(response.getErrorMessage() == null)
			{
				ErrorMessage errorMessage = new ErrorMessage();
				response.setErrorMessage(errorMessage);
			}
			response.getErrorMessage().getError().add(parseGeneralExceptionMessage(exception));
		}
	}
    
    /**
	 * @author csubhash
	 * @param exception
	 * @return returns a message parsed error data object
	 */
	public static CommonErrorData parseGeneralExceptionMessage(Exception exception)
	{
		StackTraceElement[] stackTraceElements = exception.getStackTrace();
		int index = 0;
		
		String exceptionClass = exception.getClass().getCanonicalName();
		String fileName = stackTraceElements[index].getFileName();
		String className = stackTraceElements[index].getClassName();
		String methodName = stackTraceElements[index].getMethodName();
		String lineNo = new Integer(stackTraceElements[index].getLineNumber()).toString();
		String message = exception.getMessage();
		
		String[] params = new String[]{exceptionClass,fileName,className,methodName,lineNo,message}; 
		CommonErrorData errorData = ErrorDataFactory.createErrorData(ErrorConstants.UNKNOWN_EXCEPTION, ErrorConstants.ERRORDOMAIN, params);
		return errorData;
	}
	
	private void handleException(Exception exception,BaseResponse baseResponse,
				String errorName) {
		
		baseResponse.setAck(AckValue.FAILURE);
		ErrorParameter errorParameter = new ErrorParameter();
		errorParameter.setName("Error Message");
		errorParameter.setValue(exception.getMessage());
		List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		
		//TODO: Need to use ErrorLib
		errorDataList.add(ErrorDataFactory.createErrorData(errorName, ErrorConstants.ERRORDOMAIN));
		//errorDataList.add(errorDescriptor.newError(errorParameter));
		
		if(baseResponse.getErrorMessage() == null)
        {
			baseResponse.setErrorMessage(new ErrorMessage());
        }
		baseResponse.getErrorMessage().getError().addAll(errorDataList);
	} 

}
