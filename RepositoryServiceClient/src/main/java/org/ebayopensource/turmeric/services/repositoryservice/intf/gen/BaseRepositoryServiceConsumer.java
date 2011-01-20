/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.intf.gen;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Future;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

import org.ebayopensource.turmeric.repository.v1.services.ApproveAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.ApproveAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.CreateAndSubmitAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateAndSubmitAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.CreateAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.CreateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.CreateServiceRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateServiceResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAllAssetsGroupedByCategoryRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAllAssetsGroupedByCategoryResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAllProjectsAndGroupsRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAllProjectsAndGroupsResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesByGraphRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesByGraphResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetLifeCycleStatesRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetLifeCycleStatesResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetStatusRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetStatusResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetSubmissionPropertiesRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetSubmissionPropertiesResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetTreeByAttributesRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetTreeByAttributesResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetTypesRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetTypesResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetVersionsRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetVersionsResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetBasicAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetBasicAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetCatalogAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetCatalogAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetLibraryListRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetLibraryListResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetServiceRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetServiceResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetSubscriptionRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetSubscriptionResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetUsersProjectsAndGroupsRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetUsersProjectsAndGroupsResponse;
import org.ebayopensource.turmeric.repository.v1.services.LockAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.LockAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.RejectAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.RejectAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.RemoveAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.RemoveAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.SearchAssetsDetailedRequest;
import org.ebayopensource.turmeric.repository.v1.services.SearchAssetsDetailedResponse;
import org.ebayopensource.turmeric.repository.v1.services.SearchAssetsRequest;
import org.ebayopensource.turmeric.repository.v1.services.SearchAssetsResponse;
import org.ebayopensource.turmeric.repository.v1.services.SubmitForPublishingRequest;
import org.ebayopensource.turmeric.repository.v1.services.SubmitForPublishingResponse;
import org.ebayopensource.turmeric.repository.v1.services.SubscribeRequest;
import org.ebayopensource.turmeric.repository.v1.services.SubscribeResponse;
import org.ebayopensource.turmeric.repository.v1.services.UnlockAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.UnlockAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.UnsubscribeRequest;
import org.ebayopensource.turmeric.repository.v1.services.UnsubscribeResponse;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetArtifactsRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetArtifactsResponse;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetAttributesRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetAttributesResponse;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesByGraphRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesByGraphResponse;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesResponse;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.UpdateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.UpdateServiceRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateServiceResponse;
import org.ebayopensource.turmeric.repository.v1.services.UpdateSubscriptionRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateSubscriptionResponse;
import org.ebayopensource.turmeric.repository.v1.services.ValidateAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.ValidateAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceRuntimeException;
import org.ebayopensource.turmeric.runtime.common.types.Cookie;
import org.ebayopensource.turmeric.runtime.common.types.SOAHeaders;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceInvokerOptions;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.RepositoryServiceClientConstants;


/**
 * Note : Generated file, any changes will be lost upon regeneration.
 * 
 */
public class BaseRepositoryServiceConsumer {

    private URL m_serviceLocation = null;
    private AsyncTurmericRSV1 m_proxy = null;
    private String m_authToken = null;
    private Cookie[] m_cookies;
    private Service m_service = null;

    public BaseRepositoryServiceConsumer() {
    }

    protected void setServiceLocation(String serviceLocation)
        throws MalformedURLException
    {
        m_serviceLocation = new URL(serviceLocation);
    }

    private void setUserProvidedSecurityCredentials(Service service) {
        if (m_authToken!= null) {
            service.setSessionTransportHeader(SOAHeaders.AUTH_TOKEN, m_authToken);
        }
        if (m_cookies!= null) {
            for (int i = 0; (i<m_cookies.length); i ++) {
                service.setCookie(m_cookies[i]);
            }
        }
    }

    /**
     * Use this method to set User Credentials (Token) 
     * 
     */
    protected void setAuthToken(String authToken) {
        m_authToken = authToken;
    }

    /**
     * Use this method to set User Credentials (Cookie)
     * 
     */
    protected void setCookies(Cookie[] cookies) {
        m_cookies = cookies;
    }

    /**
     * Use this method to get the Invoker Options on the Service and set them to user-preferences
     * 
     */
    public ServiceInvokerOptions getServiceInvokerOptions()
        throws ServiceException
    {
        m_service = getService();
        return m_service.getInvokerOptions();
    }

    protected AsyncTurmericRSV1 getProxy()
        throws ServiceException
    {
        m_service = getService();
        m_proxy = m_service.getProxy();
        return m_proxy;
    }

    /**
     * Method returns an instance of Service which has been initilized for this Consumer
     * 
     */
    public Service getService()
        throws ServiceException
    {
        String svcAdminName = RepositoryServiceClientConstants.SERVICE_NAME;
        if (m_service == null) {
            m_service = ServiceFactory.create(svcAdminName, RepositoryServiceClientConstants.SERVICE_NAME, m_serviceLocation);
        }
        setUserProvidedSecurityCredentials(m_service);
        return m_service;
    }

    public List<Response<?>> poll(boolean param0, boolean param1)
        throws InterruptedException
    {
        List<Response<?>> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.poll(param0, param1);
        return result;
    }

    public Response<GetServiceResponse> getServiceAsync(GetServiceRequest param0) {
        Response<GetServiceResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getServiceAsync(param0);
        return result;
    }

    public Future<?> getServiceAsync(GetServiceRequest param0, AsyncHandler<GetServiceResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getServiceAsync(param0, param1);
        return result;
    }

    public Response<LockAssetResponse> lockAssetAsync(LockAssetRequest param0) {
        Response<LockAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.lockAssetAsync(param0);
        return result;
    }

    public Future<?> lockAssetAsync(LockAssetRequest param0, AsyncHandler<LockAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.lockAssetAsync(param0, param1);
        return result;
    }

    public Response<UnsubscribeResponse> unsubscribeAsync(UnsubscribeRequest param0) {
        Response<UnsubscribeResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.unsubscribeAsync(param0);
        return result;
    }

    public Future<?> unsubscribeAsync(UnsubscribeRequest param0, AsyncHandler<UnsubscribeResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.unsubscribeAsync(param0, param1);
        return result;
    }

    public Future<?> getUsersProjectsAndGroupsAsync(GetUsersProjectsAndGroupsRequest param0, AsyncHandler<GetUsersProjectsAndGroupsResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getUsersProjectsAndGroupsAsync(param0, param1);
        return result;
    }

    public Response<GetUsersProjectsAndGroupsResponse> getUsersProjectsAndGroupsAsync(GetUsersProjectsAndGroupsRequest param0) {
        Response<GetUsersProjectsAndGroupsResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getUsersProjectsAndGroupsAsync(param0);
        return result;
    }

    public Response<SearchAssetsResponse> searchAssetsAsync(SearchAssetsRequest param0) {
        Response<SearchAssetsResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.searchAssetsAsync(param0);
        return result;
    }

    public Future<?> searchAssetsAsync(SearchAssetsRequest param0, AsyncHandler<SearchAssetsResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.searchAssetsAsync(param0, param1);
        return result;
    }

    public Response<UpdateAssetArtifactsResponse> updateAssetArtifactsAsync(UpdateAssetArtifactsRequest param0) {
        Response<UpdateAssetArtifactsResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetArtifactsAsync(param0);
        return result;
    }

    public Future<?> updateAssetArtifactsAsync(UpdateAssetArtifactsRequest param0, AsyncHandler<UpdateAssetArtifactsResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetArtifactsAsync(param0, param1);
        return result;
    }

    public Response<UpdateAssetDependenciesByGraphResponse> updateAssetDependenciesByGraphAsync(UpdateAssetDependenciesByGraphRequest param0) {
        Response<UpdateAssetDependenciesByGraphResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetDependenciesByGraphAsync(param0);
        return result;
    }

    public Future<?> updateAssetDependenciesByGraphAsync(UpdateAssetDependenciesByGraphRequest param0, AsyncHandler<UpdateAssetDependenciesByGraphResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetDependenciesByGraphAsync(param0, param1);
        return result;
    }

    public Response<ApproveAssetResponse> approveAssetAsync(ApproveAssetRequest param0) {
        Response<ApproveAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.approveAssetAsync(param0);
        return result;
    }

    public Future<?> approveAssetAsync(ApproveAssetRequest param0, AsyncHandler<ApproveAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.approveAssetAsync(param0, param1);
        return result;
    }

    public Response<CreateAndSubmitAssetResponse> createAndSubmitAssetAsync(CreateAndSubmitAssetRequest param0) {
        Response<CreateAndSubmitAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createAndSubmitAssetAsync(param0);
        return result;
    }

    public Future<?> createAndSubmitAssetAsync(CreateAndSubmitAssetRequest param0, AsyncHandler<CreateAndSubmitAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createAndSubmitAssetAsync(param0, param1);
        return result;
    }

    public Response<GetAssetTypesResponse> getAssetTypesAsync(GetAssetTypesRequest param0) {
        Response<GetAssetTypesResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetTypesAsync(param0);
        return result;
    }

    public Future<?> getAssetTypesAsync(GetAssetTypesRequest param0, AsyncHandler<GetAssetTypesResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetTypesAsync(param0, param1);
        return result;
    }

    public Response<GetAssetTreeByAttributesResponse> getAssetTreeByAttributesAsync(GetAssetTreeByAttributesRequest param0) {
        Response<GetAssetTreeByAttributesResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetTreeByAttributesAsync(param0);
        return result;
    }

    public Future<?> getAssetTreeByAttributesAsync(GetAssetTreeByAttributesRequest param0, AsyncHandler<GetAssetTreeByAttributesResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetTreeByAttributesAsync(param0, param1);
        return result;
    }

    public Response<RemoveAssetResponse> removeAssetAsync(RemoveAssetRequest param0) {
        Response<RemoveAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.removeAssetAsync(param0);
        return result;
    }

    public Future<?> removeAssetAsync(RemoveAssetRequest param0, AsyncHandler<RemoveAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.removeAssetAsync(param0, param1);
        return result;
    }

    public Response<CreateServiceResponse> createServiceAsync(CreateServiceRequest param0) {
        Response<CreateServiceResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createServiceAsync(param0);
        return result;
    }

    public Future<?> createServiceAsync(CreateServiceRequest param0, AsyncHandler<CreateServiceResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createServiceAsync(param0, param1);
        return result;
    }

    public Future<?> getAssetSubmissionPropertiesAsync(GetAssetSubmissionPropertiesRequest param0, AsyncHandler<GetAssetSubmissionPropertiesResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetSubmissionPropertiesAsync(param0, param1);
        return result;
    }

    public Response<GetAssetSubmissionPropertiesResponse> getAssetSubmissionPropertiesAsync(GetAssetSubmissionPropertiesRequest param0) {
        Response<GetAssetSubmissionPropertiesResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetSubmissionPropertiesAsync(param0);
        return result;
    }

    public Response<UpdateAssetResponse> updateAssetAsync(UpdateAssetRequest param0) {
        Response<UpdateAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetAsync(param0);
        return result;
    }

    public Future<?> updateAssetAsync(UpdateAssetRequest param0, AsyncHandler<UpdateAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetAsync(param0, param1);
        return result;
    }

    public Response<RejectAssetResponse> rejectAssetAsync(RejectAssetRequest param0) {
        Response<RejectAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.rejectAssetAsync(param0);
        return result;
    }

    public Future<?> rejectAssetAsync(RejectAssetRequest param0, AsyncHandler<RejectAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.rejectAssetAsync(param0, param1);
        return result;
    }

    public Response<GetAllProjectsAndGroupsResponse> getAllProjectsAndGroupsAsync(GetAllProjectsAndGroupsRequest param0) {
        Response<GetAllProjectsAndGroupsResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAllProjectsAndGroupsAsync(param0);
        return result;
    }

    public Future<?> getAllProjectsAndGroupsAsync(GetAllProjectsAndGroupsRequest param0, AsyncHandler<GetAllProjectsAndGroupsResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAllProjectsAndGroupsAsync(param0, param1);
        return result;
    }

    public Future<?> searchAssetsDetailedAsync(SearchAssetsDetailedRequest param0, AsyncHandler<SearchAssetsDetailedResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.searchAssetsDetailedAsync(param0, param1);
        return result;
    }

    public Response<SearchAssetsDetailedResponse> searchAssetsDetailedAsync(SearchAssetsDetailedRequest param0) {
        Response<SearchAssetsDetailedResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.searchAssetsDetailedAsync(param0);
        return result;
    }

    public Response<GetSubscriptionResponse> getSubscriptionAsync(GetSubscriptionRequest param0) {
        Response<GetSubscriptionResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getSubscriptionAsync(param0);
        return result;
    }

    public Future<?> getSubscriptionAsync(GetSubscriptionRequest param0, AsyncHandler<GetSubscriptionResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getSubscriptionAsync(param0, param1);
        return result;
    }

    public Response<GetAssetDependenciesByGraphResponse> getAssetDependenciesByGraphAsync(GetAssetDependenciesByGraphRequest param0) {
        Response<GetAssetDependenciesByGraphResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetDependenciesByGraphAsync(param0);
        return result;
    }

    public Future<?> getAssetDependenciesByGraphAsync(GetAssetDependenciesByGraphRequest param0, AsyncHandler<GetAssetDependenciesByGraphResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetDependenciesByGraphAsync(param0, param1);
        return result;
    }

    public Response<SubmitForPublishingResponse> submitForPublishingAsync(SubmitForPublishingRequest param0) {
        Response<SubmitForPublishingResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.submitForPublishingAsync(param0);
        return result;
    }

    public Future<?> submitForPublishingAsync(SubmitForPublishingRequest param0, AsyncHandler<SubmitForPublishingResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.submitForPublishingAsync(param0, param1);
        return result;
    }

    public Response<GetAssetStatusResponse> getAssetStatusAsync(GetAssetStatusRequest param0) {
        Response<GetAssetStatusResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetStatusAsync(param0);
        return result;
    }

    public Future<?> getAssetStatusAsync(GetAssetStatusRequest param0, AsyncHandler<GetAssetStatusResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetStatusAsync(param0, param1);
        return result;
    }

    public Response<CreateCompleteAssetResponse> createCompleteAssetAsync(CreateCompleteAssetRequest param0) {
        Response<CreateCompleteAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createCompleteAssetAsync(param0);
        return result;
    }

    public Future<?> createCompleteAssetAsync(CreateCompleteAssetRequest param0, AsyncHandler<CreateCompleteAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createCompleteAssetAsync(param0, param1);
        return result;
    }

    public Future<?> subscribeAsync(SubscribeRequest param0, AsyncHandler<SubscribeResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.subscribeAsync(param0, param1);
        return result;
    }

    public Response<SubscribeResponse> subscribeAsync(SubscribeRequest param0) {
        Response<SubscribeResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.subscribeAsync(param0);
        return result;
    }

    public Response<ValidateAssetResponse> validateAssetAsync(ValidateAssetRequest param0) {
        Response<ValidateAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.validateAssetAsync(param0);
        return result;
    }

    public Future<?> validateAssetAsync(ValidateAssetRequest param0, AsyncHandler<ValidateAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.validateAssetAsync(param0, param1);
        return result;
    }

    public Response<UnlockAssetResponse> unlockAssetAsync(UnlockAssetRequest param0) {
        Response<UnlockAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.unlockAssetAsync(param0);
        return result;
    }

    public Future<?> unlockAssetAsync(UnlockAssetRequest param0, AsyncHandler<UnlockAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.unlockAssetAsync(param0, param1);
        return result;
    }

    public Response<GetAssetDependenciesResponse> getAssetDependenciesAsync(GetAssetDependenciesRequest param0) {
        Response<GetAssetDependenciesResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetDependenciesAsync(param0);
        return result;
    }

    public Future<?> getAssetDependenciesAsync(GetAssetDependenciesRequest param0, AsyncHandler<GetAssetDependenciesResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetDependenciesAsync(param0, param1);
        return result;
    }

    public Response<GetAssetInfoResponse> getAssetInfoAsync(GetAssetInfoRequest param0) {
        Response<GetAssetInfoResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetInfoAsync(param0);
        return result;
    }

    public Future<?> getAssetInfoAsync(GetAssetInfoRequest param0, AsyncHandler<GetAssetInfoResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetInfoAsync(param0, param1);
        return result;
    }

    public Response<GetAssetLifeCycleStatesResponse> getAssetLifeCycleStatesAsync(GetAssetLifeCycleStatesRequest param0) {
        Response<GetAssetLifeCycleStatesResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetLifeCycleStatesAsync(param0);
        return result;
    }

    public Future<?> getAssetLifeCycleStatesAsync(GetAssetLifeCycleStatesRequest param0, AsyncHandler<GetAssetLifeCycleStatesResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetLifeCycleStatesAsync(param0, param1);
        return result;
    }

    public Response<GetCatalogAssetInfoResponse> getCatalogAssetInfoAsync(GetCatalogAssetInfoRequest param0) {
        Response<GetCatalogAssetInfoResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getCatalogAssetInfoAsync(param0);
        return result;
    }

    public Future<?> getCatalogAssetInfoAsync(GetCatalogAssetInfoRequest param0, AsyncHandler<GetCatalogAssetInfoResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getCatalogAssetInfoAsync(param0, param1);
        return result;
    }

    public Response<GetLibraryListResponse> getLibraryListAsync(GetLibraryListRequest param0) {
        Response<GetLibraryListResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getLibraryListAsync(param0);
        return result;
    }

    public Future<?> getLibraryListAsync(GetLibraryListRequest param0, AsyncHandler<GetLibraryListResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getLibraryListAsync(param0, param1);
        return result;
    }

    public Response<GetAllAssetsGroupedByCategoryResponse> getAllAssetsGroupedByCategoryAsync(GetAllAssetsGroupedByCategoryRequest param0) {
        Response<GetAllAssetsGroupedByCategoryResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAllAssetsGroupedByCategoryAsync(param0);
        return result;
    }

    public Future<?> getAllAssetsGroupedByCategoryAsync(GetAllAssetsGroupedByCategoryRequest param0, AsyncHandler<GetAllAssetsGroupedByCategoryResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAllAssetsGroupedByCategoryAsync(param0, param1);
        return result;
    }

    public Response<UpdateServiceResponse> updateServiceAsync(UpdateServiceRequest param0) {
        Response<UpdateServiceResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateServiceAsync(param0);
        return result;
    }

    public Future<?> updateServiceAsync(UpdateServiceRequest param0, AsyncHandler<UpdateServiceResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateServiceAsync(param0, param1);
        return result;
    }

    public Future<?> updateAssetAttributesAsync(UpdateAssetAttributesRequest param0, AsyncHandler<UpdateAssetAttributesResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetAttributesAsync(param0, param1);
        return result;
    }

    public Response<UpdateAssetAttributesResponse> updateAssetAttributesAsync(UpdateAssetAttributesRequest param0) {
        Response<UpdateAssetAttributesResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetAttributesAsync(param0);
        return result;
    }

    public Response<GetAssetVersionsResponse> getAssetVersionsAsync(GetAssetVersionsRequest param0) {
        Response<GetAssetVersionsResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetVersionsAsync(param0);
        return result;
    }

    public Future<?> getAssetVersionsAsync(GetAssetVersionsRequest param0, AsyncHandler<GetAssetVersionsResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetVersionsAsync(param0, param1);
        return result;
    }

    public Response<UpdateCompleteAssetResponse> updateCompleteAssetAsync(UpdateCompleteAssetRequest param0) {
        Response<UpdateCompleteAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateCompleteAssetAsync(param0);
        return result;
    }

    public Future<?> updateCompleteAssetAsync(UpdateCompleteAssetRequest param0, AsyncHandler<UpdateCompleteAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateCompleteAssetAsync(param0, param1);
        return result;
    }

    public Response<CreateAssetResponse> createAssetAsync(CreateAssetRequest param0) {
        Response<CreateAssetResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createAssetAsync(param0);
        return result;
    }

    public Future<?> createAssetAsync(CreateAssetRequest param0, AsyncHandler<CreateAssetResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createAssetAsync(param0, param1);
        return result;
    }

    public Future<?> getBasicAssetInfoAsync(GetBasicAssetInfoRequest param0, AsyncHandler<GetBasicAssetInfoResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getBasicAssetInfoAsync(param0, param1);
        return result;
    }

    public Response<GetBasicAssetInfoResponse> getBasicAssetInfoAsync(GetBasicAssetInfoRequest param0) {
        Response<GetBasicAssetInfoResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getBasicAssetInfoAsync(param0);
        return result;
    }

    public Response<UpdateAssetDependenciesResponse> updateAssetDependenciesAsync(UpdateAssetDependenciesRequest param0) {
        Response<UpdateAssetDependenciesResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetDependenciesAsync(param0);
        return result;
    }

    public Future<?> updateAssetDependenciesAsync(UpdateAssetDependenciesRequest param0, AsyncHandler<UpdateAssetDependenciesResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetDependenciesAsync(param0, param1);
        return result;
    }

    public Response<UpdateSubscriptionResponse> updateSubscriptionAsync(UpdateSubscriptionRequest param0) {
        Response<UpdateSubscriptionResponse> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateSubscriptionAsync(param0);
        return result;
    }

    public Future<?> updateSubscriptionAsync(UpdateSubscriptionRequest param0, AsyncHandler<UpdateSubscriptionResponse> param1) {
        Future<?> result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateSubscriptionAsync(param0, param1);
        return result;
    }

    public GetServiceResponse getService(GetServiceRequest param0) {
        GetServiceResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getService(param0);
        return result;
    }

    public CreateServiceResponse createService(CreateServiceRequest param0) {
        CreateServiceResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createService(param0);
        return result;
    }

    public SubscribeResponse subscribe(SubscribeRequest param0) {
        SubscribeResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.subscribe(param0);
        return result;
    }

    public UnsubscribeResponse unsubscribe(UnsubscribeRequest param0) {
        UnsubscribeResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.unsubscribe(param0);
        return result;
    }

    public LockAssetResponse lockAsset(LockAssetRequest param0) {
        LockAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.lockAsset(param0);
        return result;
    }

    public GetUsersProjectsAndGroupsResponse getUsersProjectsAndGroups(GetUsersProjectsAndGroupsRequest param0) {
        GetUsersProjectsAndGroupsResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getUsersProjectsAndGroups(param0);
        return result;
    }

    public SearchAssetsResponse searchAssets(SearchAssetsRequest param0) {
        SearchAssetsResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.searchAssets(param0);
        return result;
    }

    public UpdateAssetArtifactsResponse updateAssetArtifacts(UpdateAssetArtifactsRequest param0) {
        UpdateAssetArtifactsResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetArtifacts(param0);
        return result;
    }

    public UpdateAssetDependenciesByGraphResponse updateAssetDependenciesByGraph(UpdateAssetDependenciesByGraphRequest param0) {
        UpdateAssetDependenciesByGraphResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetDependenciesByGraph(param0);
        return result;
    }

    public ApproveAssetResponse approveAsset(ApproveAssetRequest param0) {
        ApproveAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.approveAsset(param0);
        return result;
    }

    public CreateAndSubmitAssetResponse createAndSubmitAsset(CreateAndSubmitAssetRequest param0) {
        CreateAndSubmitAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createAndSubmitAsset(param0);
        return result;
    }

    public GetAssetTypesResponse getAssetTypes(GetAssetTypesRequest param0) {
        GetAssetTypesResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetTypes(param0);
        return result;
    }

    public GetAssetTreeByAttributesResponse getAssetTreeByAttributes(GetAssetTreeByAttributesRequest param0) {
        GetAssetTreeByAttributesResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetTreeByAttributes(param0);
        return result;
    }

    public RemoveAssetResponse removeAsset(RemoveAssetRequest param0) {
        RemoveAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.removeAsset(param0);
        return result;
    }

    public GetAssetSubmissionPropertiesResponse getAssetSubmissionProperties(GetAssetSubmissionPropertiesRequest param0) {
        GetAssetSubmissionPropertiesResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetSubmissionProperties(param0);
        return result;
    }

    public UpdateAssetResponse updateAsset(UpdateAssetRequest param0) {
        UpdateAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAsset(param0);
        return result;
    }

    public RejectAssetResponse rejectAsset(RejectAssetRequest param0) {
        RejectAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.rejectAsset(param0);
        return result;
    }

    public GetAllProjectsAndGroupsResponse getAllProjectsAndGroups(GetAllProjectsAndGroupsRequest param0) {
        GetAllProjectsAndGroupsResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAllProjectsAndGroups(param0);
        return result;
    }

    public SearchAssetsDetailedResponse searchAssetsDetailed(SearchAssetsDetailedRequest param0) {
        SearchAssetsDetailedResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.searchAssetsDetailed(param0);
        return result;
    }

    public GetSubscriptionResponse getSubscription(GetSubscriptionRequest param0) {
        GetSubscriptionResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getSubscription(param0);
        return result;
    }

    public GetAssetDependenciesByGraphResponse getAssetDependenciesByGraph(GetAssetDependenciesByGraphRequest param0) {
        GetAssetDependenciesByGraphResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetDependenciesByGraph(param0);
        return result;
    }

    public SubmitForPublishingResponse submitForPublishing(SubmitForPublishingRequest param0) {
        SubmitForPublishingResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.submitForPublishing(param0);
        return result;
    }

    public GetAssetStatusResponse getAssetStatus(GetAssetStatusRequest param0) {
        GetAssetStatusResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetStatus(param0);
        return result;
    }

    public CreateCompleteAssetResponse createCompleteAsset(CreateCompleteAssetRequest param0) {
        CreateCompleteAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createCompleteAsset(param0);
        return result;
    }

    public ValidateAssetResponse validateAsset(ValidateAssetRequest param0) {
        ValidateAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.validateAsset(param0);
        return result;
    }

    public UnlockAssetResponse unlockAsset(UnlockAssetRequest param0) {
        UnlockAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.unlockAsset(param0);
        return result;
    }

    public GetAssetDependenciesResponse getAssetDependencies(GetAssetDependenciesRequest param0) {
        GetAssetDependenciesResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetDependencies(param0);
        return result;
    }

    public GetAssetInfoResponse getAssetInfo(GetAssetInfoRequest param0) {
        GetAssetInfoResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetInfo(param0);
        return result;
    }

    public GetAssetLifeCycleStatesResponse getAssetLifeCycleStates(GetAssetLifeCycleStatesRequest param0) {
        GetAssetLifeCycleStatesResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetLifeCycleStates(param0);
        return result;
    }

    public GetCatalogAssetInfoResponse getCatalogAssetInfo(GetCatalogAssetInfoRequest param0) {
        GetCatalogAssetInfoResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getCatalogAssetInfo(param0);
        return result;
    }

    public GetLibraryListResponse getLibraryList(GetLibraryListRequest param0) {
        GetLibraryListResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getLibraryList(param0);
        return result;
    }

    public GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategory(GetAllAssetsGroupedByCategoryRequest param0) {
        GetAllAssetsGroupedByCategoryResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAllAssetsGroupedByCategory(param0);
        return result;
    }

    public UpdateServiceResponse updateService(UpdateServiceRequest param0) {
        UpdateServiceResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateService(param0);
        return result;
    }

    public UpdateAssetAttributesResponse updateAssetAttributes(UpdateAssetAttributesRequest param0) {
        UpdateAssetAttributesResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetAttributes(param0);
        return result;
    }

    public GetAssetVersionsResponse getAssetVersions(GetAssetVersionsRequest param0) {
        GetAssetVersionsResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getAssetVersions(param0);
        return result;
    }

    public UpdateCompleteAssetResponse updateCompleteAsset(UpdateCompleteAssetRequest param0) {
        UpdateCompleteAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateCompleteAsset(param0);
        return result;
    }

    public CreateAssetResponse createAsset(CreateAssetRequest param0) {
        CreateAssetResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.createAsset(param0);
        return result;
    }

    public GetBasicAssetInfoResponse getBasicAssetInfo(GetBasicAssetInfoRequest param0) {
        GetBasicAssetInfoResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.getBasicAssetInfo(param0);
        return result;
    }

    public UpdateAssetDependenciesResponse updateAssetDependencies(UpdateAssetDependenciesRequest param0) {
        UpdateAssetDependenciesResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateAssetDependencies(param0);
        return result;
    }

    public UpdateSubscriptionResponse updateSubscription(UpdateSubscriptionRequest param0) {
        UpdateSubscriptionResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.updateSubscription(param0);
        return result;
    }

}
