/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation.common;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.TurmericRSV2;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

/**
 * RepositoryServiceClient is a client for the RepositoryService web service. The AssetCreation utility uses
 * RepositoryService to create and submit Asset and to retrieve from the repository.
 * 
 * @author sanarayanan
 */
public class RepositoryServiceConsumer {
   static private final Logger logger = Logger.getLogger(RepositoryServiceConsumer.class);

   private final String userId;
   private final String password;
   private String securityCookie = null;
   // private Service service;
   private URL serviceLocationURL;

   // private RepositoryService serviceProxy = null;

   /**
    * Instantiates a new repository service consumer.
    * 
    * @param userId
    *           the user id
    * @param password
    *           the password
    * @param serviceLocation
    *           the service location
    * @throws ServiceException
    *            the service exception
    */
   public RepositoryServiceConsumer(String userId, String password, String serviceLocation) throws ServiceException {
      this.userId = userId;
      this.password = password;
      try {
         URL serviceLocationURL = new URL(serviceLocation);
         this.serviceLocationURL = serviceLocationURL;
      } catch (MalformedURLException mux) {
         throw new IllegalArgumentException(mux);
      }
   }

   /**
    * Returns the service proxy object.
    * 
    * @return the service proxy object.
    * @throws ServiceException
    */
   private TurmericRSV2 getServiceProxy() throws ServiceException {
      Service service = ServiceFactory.create("TurmericRSV2", "TurmericRSV2", serviceLocationURL);

      // get security cookie after first successful login
      if (securityCookie == null) {
         securityCookie = service.getResponseContext().getTransportHeader("X-TURMERIC-SECURITY-COOKIE");
      }

      // Use security cookie if present or use userid/password
      RequestContext requestContext = service.getRequestContext();
      if (securityCookie != null) {
         logger.debug("Found X-TURMERIC-SECURITY-COOKIE=" + securityCookie);
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-COOKIE", securityCookie);
      } else {
         logger.debug("Using password header, did not find X-TURMERIC-SECURITY-COOKIE");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-USERID", userId);
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-PASSWORD", password);
      }
      return (TurmericRSV2) service.getProxy();
   }

   /**
    * Creates the complete asset.
    * 
    * @param createCompAssetReq
    *           the create comp asset req
    * @return the creates the complete asset response
    * @throws ServiceException
    *            the service exception
    */
   public CreateCompleteAssetResponse createCompleteAsset(CreateCompleteAssetRequest createCompAssetReq)
            throws ServiceException {
      CreateCompleteAssetResponse createCompAssetRes = getServiceProxy().createCompleteAsset(createCompAssetReq);
      return createCompAssetRes;
   }

   /**
    * Gets the asset info.
    * 
    * @param assetRequest
    *           the asset request
    * @return the asset info
    * @throws ServiceException
    *            the service exception
    */
   public GetAssetInfoResponse getAssetInfo(GetAssetInfoRequest assetRequest) throws ServiceException {
      GetAssetInfoResponse assetResponse = getServiceProxy().getAssetInfo(assetRequest);
      return assetResponse;
   }

   /**
    * Gets the basic asset info.
    * 
    * @param basicAssetInfoReq
    *           the basic asset info req
    * @return the basic asset info
    * @throws ServiceException
    *            the service exception
    */
   public GetBasicAssetInfoResponse getBasicAssetInfo(GetBasicAssetInfoRequest basicAssetInfoReq)
            throws ServiceException {
      GetBasicAssetInfoResponse basicAssetInfoRes = getServiceProxy().getBasicAssetInfo(basicAssetInfoReq);
      return basicAssetInfoRes;
   }

   /**
    * Update asset attributes.
    * 
    * @param updateAssetAttrReq
    *           the update asset attr req
    * @return the update asset attributes response
    * @throws ServiceException
    *            the service exception
    */
   public UpdateAssetAttributesResponse updateAssetAttributes(UpdateAssetAttributesRequest updateAssetAttrReq)
            throws ServiceException {
      UpdateAssetAttributesResponse updateAssetAttrRes = getServiceProxy().updateAssetAttributes(updateAssetAttrReq);
      return updateAssetAttrRes;
   }

   /**
    * Creates the and submit asset.
    * 
    * @param submitAssetRequest
    *           the submit asset request
    * @return the creates the and submit asset response
    * @throws ServiceException
    *            the service exception
    */
   public CreateAndSubmitAssetResponse createAndSubmitAsset(CreateAndSubmitAssetRequest submitAssetRequest)
            throws ServiceException {
      CreateAndSubmitAssetResponse submitAssetResponse = getServiceProxy().createAndSubmitAsset(submitAssetRequest);
      return submitAssetResponse;
   }

   /**
    * Update complete asset.
    * 
    * @param updateAssetRequest
    *           the update asset request
    * @return the update complete asset response
    * @throws ServiceException
    *            the service exception
    */
   public UpdateCompleteAssetResponse updateCompleteAsset(UpdateCompleteAssetRequest updateAssetRequest)
            throws ServiceException {
      UpdateCompleteAssetResponse updateResponse = getServiceProxy().updateCompleteAsset(updateAssetRequest);
      return updateResponse;
   }

   /**
    * Lock asset.
    * 
    * @param lockAssetRequest
    *           the lock asset request
    * @return the lock asset response
    * @throws ServiceException
    *            the service exception
    */
   public LockAssetResponse lockAsset(LockAssetRequest lockAssetRequest) throws ServiceException {
      LockAssetResponse lockAssetResponse = getServiceProxy().lockAsset(lockAssetRequest);
      return lockAssetResponse;
   }

   /**
    * Unlock asset.
    * 
    * @param unlockAssetRequest
    *           the unlock asset request
    * @return the unlock asset response
    * @throws ServiceException
    *            the service exception
    */
   public UnlockAssetResponse unlockAsset(UnlockAssetRequest unlockAssetRequest) throws ServiceException {
      UnlockAssetResponse unlockAssetResponse = getServiceProxy().unlockAsset(unlockAssetRequest);
      return unlockAssetResponse;
   }

   /**
    * Submit for publishing.
    * 
    * @param submitForPubReq
    *           the submit for pub req
    * @return the submit for publishing response
    * @throws ServiceException
    *            the service exception
    */
   public SubmitForPublishingResponse submitForPublishing(SubmitForPublishingRequest submitForPubReq)
            throws ServiceException {
      SubmitForPublishingResponse submitForPubRes = getServiceProxy().submitForPublishing(submitForPubReq);
      return submitForPubRes;
   }

   /**
    * Removes the asset.
    * 
    * @param removeAssetReq
    *           the remove asset req
    * @return the removes the asset response
    * @throws ServiceException
    *            the service exception
    */
   public RemoveAssetResponse removeAsset(RemoveAssetRequest removeAssetReq) throws ServiceException {
      RemoveAssetResponse removeAssetRes = getServiceProxy().removeAsset(removeAssetReq);
      return removeAssetRes;
   }

}
