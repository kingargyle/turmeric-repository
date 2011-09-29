/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.util.List;

import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.CatalogSearchLevel;
import org.ebayopensource.turmeric.repository.v1.services.GetCatalogAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetCatalogAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetCatalogAssetInfoConsumer extends BaseRepositoryServiceConsumer {
   private static String securityCookie = null;
   private static Service service = null;
   private static final String FAILED = "FAILED";
   private static final String PASSED = "PASSED";
   private static GetCatalogAssetInfoConsumer getAssetInfoConsumer = new GetCatalogAssetInfoConsumer();

   public static String testGetAllCatalogAsset_invalidSessionId() {
      GetCatalogAssetInfoRequest getCatalogAssetInfoRequest = new GetCatalogAssetInfoRequest();
      CatalogSearchLevel searchLevel = CatalogSearchLevel.ALL;
      getCatalogAssetInfoRequest.setCatalogSearchLevel(searchLevel);
      getCatalogAssetInfoRequest.setLibraryName("GovernedAssets");
      GetCatalogAssetInfoResponse response = null;
      try {
         response = getAssetInfoConsumer.getProxyInvalidSession().getCatalogAssetInfo(getCatalogAssetInfoRequest);
         displayCatalogResponse(response);
      } catch (ServiceException e) {
         return FAILED;
      }
      return PASSED;
   }

   public static String testGetAllCatalogAsset() {
      GetCatalogAssetInfoRequest getCatalogAssetInfoRequest = new GetCatalogAssetInfoRequest();
      CatalogSearchLevel searchLevel = CatalogSearchLevel.ALL;
      getCatalogAssetInfoRequest.setCatalogSearchLevel(searchLevel);
      getCatalogAssetInfoRequest.setLibraryName("GovernedAssets");
      GetCatalogAssetInfoResponse response = null;
      try {
         response = getAssetInfoConsumer.getProxy().getCatalogAssetInfo(getCatalogAssetInfoRequest);
         displayCatalogResponse(response);
      } catch (ServiceException e) {
         return FAILED;
      }
      return PASSED;
   }

   public static String testGetEditedCatalogAsset() {
      GetCatalogAssetInfoRequest getCatalogAssetInfoRequest = new GetCatalogAssetInfoRequest();
      CatalogSearchLevel searchLevel = CatalogSearchLevel.EDITED;
      getCatalogAssetInfoRequest.setCatalogSearchLevel(searchLevel);
      getCatalogAssetInfoRequest.setLibraryName("GovernedAssets");
      GetCatalogAssetInfoResponse response = null;
      try {
         response = getAssetInfoConsumer.getProxy().getCatalogAssetInfo(getCatalogAssetInfoRequest);
         displayCatalogResponse(response);
      } catch (ServiceException e) {
         return FAILED;
      }
      return PASSED;
   }

   public static String testGetLockedCatalogAsset() {
      GetCatalogAssetInfoRequest getCatalogAssetInfoRequest = new GetCatalogAssetInfoRequest();
      CatalogSearchLevel searchLevel = CatalogSearchLevel.LOCKED;
      getCatalogAssetInfoRequest.setCatalogSearchLevel(searchLevel);
      getCatalogAssetInfoRequest.setLibraryName("GovernedAssets");
      GetCatalogAssetInfoResponse response = null;
      try {
         response = getAssetInfoConsumer.getProxy().getCatalogAssetInfo(getCatalogAssetInfoRequest);
         displayCatalogResponse(response);
      } catch (ServiceException e) {
         return FAILED;
      }
      return PASSED;
   }

   public static String testGetCatalogAssetWhenSearchLevelProvided() {
      GetCatalogAssetInfoRequest getCatalogAssetInfoRequest = new GetCatalogAssetInfoRequest();
      getCatalogAssetInfoRequest.setLibraryName("GovernedAssets");
      GetCatalogAssetInfoResponse response = null;
      try {
         response = getAssetInfoConsumer.getProxy().getCatalogAssetInfo(getCatalogAssetInfoRequest);
         displayCatalogResponse(response);
      } catch (ServiceException e) {
         return FAILED;
      }
      return PASSED;
   }

   protected AsyncTurmericRSV1 getProxyInvalidSession() throws ServiceException {
      if (service == null) {
         service = ServiceFactory.create(RepositoryServiceClientConstants.SERVICE_NAME,
                  RepositoryServiceClientConstants.SERVICE_NAME);
      }

      // Use security cookie if present or use userid/password
      RequestContext requestContext = service.getRequestContext();
      // logger.debug("Found X-TURMERIC-SECURITY-COOKIE="+securityCookie);
      requestContext.setTransportHeader("X-TURMERIC-SECURITY-COOKIE", "improperJSESSIONID");
      return (AsyncTurmericRSV1) service.getProxy();
   }

   @Override
   protected AsyncTurmericRSV1 getProxy() throws ServiceException {
      if (service == null) {
         service = ServiceFactory.create(RepositoryServiceClientConstants.SERVICE_NAME,
                  RepositoryServiceClientConstants.SERVICE_NAME);
      }

      // get security cookie after first successful login
      if (securityCookie == null) {
         securityCookie = service.getResponseContext().getTransportHeader("X-TURMERIC-SECURITY-COOKIE");
      }

      // Use security cookie if present or use userid/password
      RequestContext requestContext = service.getRequestContext();
      if (securityCookie != null) {
         // logger.debug("Found X-TURMERIC-SECURITY-COOKIE="+securityCookie);
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-COOKIE", "improperJSESSIONID");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-USERID", "");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-PASSWORD", "");
      } else {
         // logger.debug("Using password header, did not find X-TURMERIC-SECURITY-COOKIE");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-USERID", "csubhash");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-PASSWORD", "Chandu411&1020");
      }
      return (AsyncTurmericRSV1) service.getProxy();
   }

   private static void displayCatalogResponse(GetCatalogAssetInfoResponse catalogAssetInfoResponse) {
      System.out.println("***** Displaying CatalogAssetInfoResponse ******");
      System.out.println("Ack: " + catalogAssetInfoResponse.getAck());
      System.out.println("***** Basic Asset Info ****** No: " + catalogAssetInfoResponse.getCatalogAssets().size());
      displayAllBasicAssetInfoList(catalogAssetInfoResponse.getCatalogAssets());
   }

   public static void displayAllBasicAssetInfoList(List<BasicAssetInfo> basicAssetInfoList) {
      BasicAssetInfo basicInfo = null;
      for (int index = 0; index < basicAssetInfoList.size(); index++) {
         basicInfo = basicAssetInfoList.get(index);
         System.out.println("***** Basic Asset Info :" + (index + 1));
         System.out.println("basicAssetInfo.getAssetName: " + basicInfo.getAssetName());
         System.out.println("basicAssetInfo.getVersion: " + basicInfo.getVersion());
         System.out.println("basicAssetInfo.getAssetType: " + basicInfo.getAssetType());
         System.out.println("basicAssetInfo.getAssetDescription: " + basicInfo.getAssetDescription());
         System.out.println("basicAssetInfo.getAssetLongDescription: " + basicInfo.getAssetLongDescription());
         System.out.println("basicAssetInfo.getAssetKey.getAssetId: " + basicInfo.getAssetKey().getAssetId());
         System.out.println("basicAssetInfo.getAssetKey.getAssetName: " + basicInfo.getAssetKey().getAssetName());
         System.out.println("basicAssetInfo.getAssetKey.getLibrary.getLibraryName: "
                  + basicInfo.getAssetKey().getLibrary().getLibraryName());
         System.out.println("basicAssetInfo.getAssetKey.getLibrary.getLibraryName: "
                  + basicInfo.getAssetKey().getLibrary().getLibraryName());
      }
   }
}
