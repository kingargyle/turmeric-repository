/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.cachingframework.common;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

import org.ebayopensource.turmeric.cachingframework.exception.AssertionIllegalArgumentException;
import org.ebayopensource.turmeric.cachingframework.exception.AssertionRuntimeException;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.TurmericRSV2;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceInvocationRuntimeException;
import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

/**
 * RepositoryServiceClient is a client for the RepositoryService web service. The Assertions framework uses
 * RepositoryService to obtain Asset artifacts from the repository.
 * 
 * @author pcopeland
 */
public class RepositoryServiceClient {
   static private final Logger logger = Logger.getLogger(RepositoryServiceClient.class);

   private final String userId;
   private final String password;
   private String securityCookie = null;
   // private Service service;
   private URL serviceLocationURL;

   public RepositoryServiceClient(String userId, String password, String serviceLocation) throws ServiceException {
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
      logger.error("[JEGAN]Inside CachingFramework RepositoryServiceClient.getServiceProxy Username: " + userId
               + " password: " + password);
      logger.error("RepositoryServiceClient.getServiceProxy serviceLocationURL=" + serviceLocationURL);
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
    * @return a repository Asset.
    */
   public AssetInfo getAssetInfo(String libraryName, String assetName, String assetVersion, String assetType)
            throws AssertionIllegalArgumentException {
      GetAssetInfoRequest assetRequest = new GetAssetInfoRequest();
      AssetKey assetKey = new AssetKey();

      assetKey.setAssetName(assetName);
      assetRequest.setAssetKey(assetKey);
      assetRequest.setVersion(assetVersion);
      assetRequest.setAssetType(assetType);

      return getAssetInfo(assetRequest);
   }

   private AssetInfo getAssetInfo(GetAssetInfoRequest assetRequest) throws AssertionIllegalArgumentException {
      String assetName = assetRequest.getAssetKey().getAssetName();
      String assetVersion = assetRequest.getVersion();
      String assetId = assetRequest.getAssetKey().getAssetId();
      try {
         logger.error("RepositoryServiceClient.getAssetInfo: getting the Asset info for assetName=" + assetName
                  + ", assetId=" + assetId + ",assetVersion=" + assetVersion);
         GetAssetInfoResponse rsp = getServiceProxy().getAssetInfo(assetRequest);
         logger.error("RepositoryServiceClient.getAssetInfo: rsp=" + rsp + ", rsp.getAck=" + rsp.getAck()
                  + ",rsp.getErrorMessage()=" + rsp.getErrorMessage());

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