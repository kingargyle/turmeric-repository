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
import java.util.Properties;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetTypesRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetTypesResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetAssetTypesConsumer extends BaseRepositoryServiceConsumer {
   private static GetAssetTypesConsumer getAssetTypesConsumer = new GetAssetTypesConsumer();
   private static String securityCookie = null;
   private static Service service = null;
   private static Properties prop = CommonUtil.loadPropertyFile("properties/common.properties");

   public static String testGetAssetTypes_invalidJsessionId() {
      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      GetAssetTypesRequest getAssetTypesRequest = new GetAssetTypesRequest();
      getAssetTypesRequest.setLibrary(library);

      try {
         // GetAssetTypesConsumer getAssetTypesConsumer = new GetAssetTypesConsumer();
         GetAssetTypesResponse getAssetTypesResponse = getAssetTypesConsumer.getProxyInvlidSessionId().getAssetTypes(
                  getAssetTypesRequest);
         List<CommonErrorData> errors = getAssetTypesResponse.getErrorMessage().getError();

         if (!AckValue.FAILURE.equals(getAssetTypesResponse.getAck())) {
            return "FAILED";
         }
         if (errors == null || errors.size() <= 0) {
            return "FAILED";
         }
         boolean gotErrorFlag = false;
         for (CommonErrorData errorData : errors) {
            if (errorData.getErrorId() == 81) {
               gotErrorFlag = true;
            }
         }
         if (!gotErrorFlag) {
            return "FAILED";
         }
         return "PASSED";
      } catch (ServiceException e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   public static String testGetAssetTypes_invalidCredentials() {
      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      GetAssetTypesRequest getAssetTypesRequest = new GetAssetTypesRequest();
      getAssetTypesRequest.setLibrary(library);

      try {
         // GetAssetTypesConsumer getAssetTypesConsumer = new GetAssetTypesConsumer();
         GetAssetTypesResponse getAssetTypesResponse = getAssetTypesConsumer.getProxyInvalidCredentials()
                  .getAssetTypes(getAssetTypesRequest);
         List<CommonErrorData> errors = getAssetTypesResponse.getErrorMessage().getError();

         if (!AckValue.FAILURE.equals(getAssetTypesResponse.getAck())) {
            return "FAILED";
         }
         if (errors == null || errors.size() <= 0) {
            return "FAILED";
         }
         boolean gotErrorFlag = false;
         for (CommonErrorData errorData : errors) {
            if (errorData.getErrorId() == 84) {
               gotErrorFlag = true;
            }
         }
         if (!gotErrorFlag) {
            return "FAILED";
         }
         return "PASSED";
      } catch (ServiceException e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   public static String testGetAssetTypes_withLibraryArgument() {
      Library library = new Library();
      String libraryName = prop.getProperty("libraryName", "GovernedAssets");
      library.setLibraryName(libraryName);

      GetAssetTypesRequest getAssetTypesRequest = new GetAssetTypesRequest();
      getAssetTypesRequest.setLibrary(library);

      try {
         GetAssetTypesResponse getAssetTypesResponse = getAssetTypesConsumer.getProxy().getAssetTypes(
                  getAssetTypesRequest);
         List<String> assetTypes = getAssetTypesResponse.getAssetType();

         if (assetTypes.contains("Service")) {
            return "PASSED";
         } else {
            return "FAILED";
         }
      } catch (ServiceException e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   public static String testGetAssetTypes_withoutLibraryArgument() {

      GetAssetTypesRequest getAssetTypesRequest = new GetAssetTypesRequest();

      try {
         GetAssetTypesResponse getAssetTypesResponse = getAssetTypesConsumer.getProxy().getAssetTypes(
                  getAssetTypesRequest);
         List<String> assetTypes = getAssetTypesResponse.getAssetType();

         if (assetTypes.contains("Service")) {
            return "PASSED";
         } else {
            return "FAILED";
         }
      } catch (ServiceException e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   public static String testGetAssetTypes_insufficientPrivilege() {
      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.NO_PRIVILAGE_LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.NO_PRIVILAGE_LIBRARY_NAME);

      GetAssetTypesRequest getAssetTypesRequest = new GetAssetTypesRequest();
      getAssetTypesRequest.setLibrary(library);

      try {
         // GetAssetTypesConsumer getAssetTypesConsumer = new GetAssetTypesConsumer();
         GetAssetTypesResponse getAssetTypesResponse = getAssetTypesConsumer.getProxy().getAssetTypes(
                  getAssetTypesRequest);
         if (getAssetTypesResponse.getAck().value().equalsIgnoreCase("failure")) {
            List<CommonErrorData> errorDatas = getAssetTypesResponse.getErrorMessage().getError();
            if (errorDatas == null) {
               return "FAILED";
            }
            System.out.println("The following list of errors occured");
            for (CommonErrorData errorData : errorDatas) {
               System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());
            }
            return "PASSED";
         }
         return "FAILED";
      } catch (ServiceException e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   public static String testGetAssetTypes_invalidLibrary() {
      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.INVALID_LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.INVALID_LIBRARY_NAME);

      GetAssetTypesRequest getAssetTypesRequest = new GetAssetTypesRequest();
      getAssetTypesRequest.setLibrary(library);

      try {
         GetAssetTypesResponse getAssetTypesResponse = getAssetTypesConsumer.getProxy().getAssetTypes(
                  getAssetTypesRequest);
         if (getAssetTypesResponse.getAck().value().equalsIgnoreCase("failure")) {
            List<CommonErrorData> errorDatas = getAssetTypesResponse.getErrorMessage().getError();
            if (errorDatas == null) {
               return "FAILED";
            }
            System.out.println("The following list of errors occured");
            for (CommonErrorData errorData : errorDatas) {
               System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());
            }
            return "PASSED";
         }
         return "FAILED";
      } catch (ServiceException e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   protected AsyncTurmericRSV1 getProxyInvalidCredentials() throws ServiceException {
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

      requestContext.setTransportHeader("X-TURMERIC-SECURITY-USERID", "invalidUsername");
      requestContext.setTransportHeader("X-TURMERIC-SECURITY-PASSWORD", "invalidpassword");
      return (AsyncTurmericRSV1) service.getProxy();

   }

   protected AsyncTurmericRSV1 getProxyInvlidSessionId() throws ServiceException {
      if (service == null) {
         service = ServiceFactory.create(RepositoryServiceClientConstants.SERVICE_NAME,
                  RepositoryServiceClientConstants.SERVICE_NAME);
      }

      // Use security cookie if present or use userid/password
      RequestContext requestContext = service.getRequestContext();
      requestContext.setTransportHeader("X-TURMERIC-SECURITY-COOKIE", "invalidJsession");
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
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-COOKIE", securityCookie);
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-USERID", "");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-PASSWORD", "");
      } else {
         // logger.debug("Using password header, did not find X-TURMERIC-SECURITY-COOKIE");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-USERID", "csubhash");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-PASSWORD", "Sweetu411&1020");
      }
      return (AsyncTurmericRSV1) service.getProxy();

   }
}
