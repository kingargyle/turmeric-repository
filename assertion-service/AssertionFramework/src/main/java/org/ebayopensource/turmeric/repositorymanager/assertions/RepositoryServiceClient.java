/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

import java.net.MalformedURLException;
import java.net.URL;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.TurmericRSV2;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionIllegalArgumentException;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionRuntimeException;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceInvocationRuntimeException;
import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RepositoryServiceClient is a client for the RepositoryService web service. The Assertions framework uses
 * RepositoryService to obtain Asset artifacts from the repository.
 * 
 * @author pcopeland
 */
public class RepositoryServiceClient {

   /** The Constant logger. */
   static private final Logger logger = LoggerFactory.getLogger(RepositoryServiceClient.class);

   /** The user id. */
   private final String userId;

   /** The password. */
   private final String password;

   /** The security cookie. */
   private String securityCookie = null;
   // private Service service;
   /** The service location url. */
   private URL serviceLocationURL;

   /**
    * Instantiates a new repository service client.
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
   public RepositoryServiceClient(String userId, String password, String serviceLocation) throws ServiceException {
      this.userId = userId;
      this.password = password;
      try {
         URL serviceLocationURL = new URL(serviceLocation);
         this.serviceLocationURL = serviceLocationURL;
         // service = ServiceFactory.create("RepositoryService", "RepositoryService", serviceLocationURL);
         // serviceProxy = service.getProxy();
      } catch (MalformedURLException mux) {
         throw new IllegalArgumentException(mux);
      }
   }

   /**
    * Returns the service proxy object.
    * 
    * @return the service proxy object.
    * @throws ServiceException
    *            the service exception
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
    * Returns a repository Asset.
    * 
    * @param assetKey
    *           AssetKey
    * @return a repository Asset.
    * @throws AssertionIllegalArgumentException
    *            the assertion illegal argument exception
    */
   public AssetInfo getAssetInfo(AssetKey assetKey) throws AssertionIllegalArgumentException {
      GetAssetInfoRequest assetRequest = new GetAssetInfoRequest();
      assetRequest.setAssetKey(assetKey);
      return getAssetInfo(assetRequest);
   }

   /**
    * Returns a repository Asset.
    * 
    * @param libraryName
    *           the library that contains the Asset
    * @param assetName
    *           the name of the Asset.
    * @param assetVersion
    *           the Asset version.
    * @param assetType
    *           the asset type
    * @return a repository Asset.
    * @throws AssertionIllegalArgumentException
    *            the assertion illegal argument exception
    */
   public AssetInfo getAssetInfo(String id, String assetName, String assetVersion, String assetType)
            throws AssertionIllegalArgumentException {
      GetAssetInfoRequest assetRequest = new GetAssetInfoRequest();
      AssetKey assetKey = new AssetKey();

      assetKey.setAssetId(id);
      assetKey.setAssetName(assetName);
      assetRequest.setAssetKey(assetKey);
      assetRequest.setVersion(assetVersion);
      assetRequest.setAssetType(assetType);

      return getAssetInfo(assetRequest);
   }

   /**
    * Gets the asset info.
    * 
    * @param assetRequest
    *           the asset request
    * @return the asset info
    * @throws AssertionIllegalArgumentException
    *            the assertion illegal argument exception
    */
   private AssetInfo getAssetInfo(GetAssetInfoRequest assetRequest) throws AssertionIllegalArgumentException {
      String assetName = assetRequest.getAssetKey().getAssetName();
      String assetVersion = assetRequest.getVersion();
      String assetId = assetRequest.getAssetKey().getAssetId();

      try {
         GetAssetInfoResponse rsp = getServiceProxy().getAssetInfo(assetRequest);
         if (rsp.getAck() != null && rsp.getAck().value() != null && rsp.getAck().value().equalsIgnoreCase("success")) {
            if (rsp.getAssetInfo() == null) {
               throw new AssertionIllegalArgumentException(rsp.getErrorMessage());
            }
            return rsp.getAssetInfo();
         } else {
            StringBuffer errbuf = new StringBuffer("RepositoryService.getAssetinfo() failed");
            errbuf.append(" asset=").append(assetName).append(",version=").append(assetVersion).append(",assetId=")
                     .append(assetId).append(",errors=");
            throw new AssertionRuntimeException(rsp.getErrorMessage());
            /*
             * throw new RuntimeException(formatError( errbuf, rsp.getErrorMessage()));
             */
         }
      } catch (ServiceInvocationRuntimeException sifex) {
         throw new RuntimeException("RepositoryService.getAssetinfo() failed for" + " asset=" + assetName + ",version="
                  + assetVersion + ",assetId=" + assetId, sifex);
      } catch (ServiceException serviceException) {
         throw new RuntimeException("RepositoryService.getAssetinfo() failed for" + " asset=" + assetName + ",version="
                  + assetVersion + ",assetId=" + assetId, serviceException);
      }
   }
}
