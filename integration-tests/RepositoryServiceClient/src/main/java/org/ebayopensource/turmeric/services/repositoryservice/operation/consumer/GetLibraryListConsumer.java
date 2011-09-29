/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v1.services.GetLibraryListRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetLibraryListResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.TurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetLibraryListConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV1 m_proxy = null;

   public static String testGetLibraryList_positiveScenario() {
      GetLibraryListRequest getLibraryListRequest = new GetLibraryListRequest();

      try {
         GetLibraryListConsumer getLibraryListConsumer = new GetLibraryListConsumer();
         AsyncTurmericRSV1 proxy = getLibraryListConsumer.getProxy();
         GetLibraryListResponse getLibraryListResponse = proxy.getLibraryList(getLibraryListRequest);
         if (getLibraryListResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetLibraryListResponse(getLibraryListResponse, "positiveCase").equalsIgnoreCase("success")) {
            return "PASSED";
         } else {
            return "FAILED";
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return "FAILED";
      } catch (Exception e) {
         e.printStackTrace();
         return "FAILED";
      }
      /*
       * List<Library> libraries = getLibraryListResponse.getLibrary();
       * 
       * System.out.println("testGetLibraryList resulted in : " + getLibraryListResponse.getAck().value()); for (Library
       * library : libraries) { System.out.println("Library name: " + library.getLibraryName() + " Library Id: " +
       * library.getLibraryId()); }
       */
   }

   public static String testGetLibraryList_insufficientPrivilege() {
      GetLibraryListRequest getLibraryListRequest = new GetLibraryListRequest();
      GetLibraryListConsumer getLibraryListConsumer = new GetLibraryListConsumer();

      HashMap<String, String> userConfig = new HashMap<String, String>();
      userConfig.put("X-TURMERIC-SECURITY-USERID", RepositoryServiceClientConstants.NO_PRIVILAGE_USER_ID);
      userConfig.put("X-TURMERIC-SECURITY-PASSWORD", RepositoryServiceClientConstants.NO_PRIVILAGE_USER_PASSWORD);

      try {

         GetLibraryListResponse getLibraryListResponse = getLibraryListConsumer.getProxy().getLibraryList(
                  getLibraryListRequest);
         if (getLibraryListResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetLibraryListResponse(getLibraryListResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getLibraryListResponse.getErrorMessage().getError();

            System.out.println("The following list of errors occured");
            for (CommonErrorData errorData : errorDatas) {
               System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());
            }
            return "PASSED";
         } else {
            return "FAILED";
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return "FAILED";
      } catch (Exception e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   @Override
   protected AsyncTurmericRSV1 getProxy() throws ServiceException {
      if (m_proxy == null) {
         String svcAdminName = RepositoryServiceClientConstants.SERVICE_NAME;
         Service service = ServiceFactory.create(svcAdminName, RepositoryServiceClientConstants.SERVICE_NAME);
         service.setSessionTransportHeader("X-TURMERIC-SECURITY-USERID", RepositoryServiceClientConstants.USER_ID);
         service.setSessionTransportHeader("X-TURMERIC-SECURITY-PASSWORD",
                  RepositoryServiceClientConstants.USER_PASSWORD);

         m_proxy = service.getProxy();
      }

      return m_proxy;
   }

   protected TurmericRSV1 getProxy(HashMap<String, String> userConfig) throws ServiceException {
      if (userConfig == null) {
         userConfig.put("X-TURMERIC-SECURITY-USERID", RepositoryServiceClientConstants.USER_ID);
         userConfig.put("X-TURMERIC-SECURITY-PASSWORD", RepositoryServiceClientConstants.USER_PASSWORD);
      }

      String svcAdminName = RepositoryServiceClientConstants.SERVICE_NAME;
      Service service = ServiceFactory.create(svcAdminName, RepositoryServiceClientConstants.SERVICE_NAME);
      Set<String> keys = userConfig.keySet();

      for (String key : keys) {
         service.setSessionTransportHeader(key, userConfig.get(key));
      }
      m_proxy = service.getProxy();

      return m_proxy;
   }

   private static String validateGetLibraryListResponse(GetLibraryListResponse getLibraryListResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getLibraryListResponse.getAck().value().equalsIgnoreCase("success")) {
            List<Library> libraries = getLibraryListResponse.getLibrary();
            if (libraries != null) {
               for (Library library : libraries) {
                  if (library.getLibraryId() == null || library.getLibraryName() == null) {
                     return "failure";
                  }
               }
            }
            return "success";
         }
         return "failure";
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getLibraryListResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (getLibraryListResponse.getErrorMessage().getError().size() > 0) {
               return "success";
            }
         }
      }

      return "failure";
   }
}
