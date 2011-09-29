/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import org.ebayopensource.turmeric.repository.v1.services.GetUsersProjectsAndGroupsRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetUsersProjectsAndGroupsResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.TurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

/**
 * @author szacharias
 * 
 */
public class GetUsersProjectsAndGroupsConsumer extends BaseRepositoryServiceConsumer {

   private static String securityCookie = null;
   private static Service service = null;

   public static String testGetUsersProjectsAndGroupsWithValidLibrary() {

      GetUsersProjectsAndGroupsRequest getUsersProjectsAndGroupsRequest = new GetUsersProjectsAndGroupsRequest();
      getUsersProjectsAndGroupsRequest.setUsername("csubhash");

      Library library = new Library();
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      getUsersProjectsAndGroupsRequest.setLibrary(library);

      GetUsersProjectsAndGroupsConsumer getUsersProjectsAndGroupsConsumer = new GetUsersProjectsAndGroupsConsumer();
      try {
         GetUsersProjectsAndGroupsResponse getUsersProjectsAndGroupsResponse = getUsersProjectsAndGroupsConsumer
                  .getProxy().getUsersProjectsAndGroups(getUsersProjectsAndGroupsRequest);
         if (getUsersProjectsAndGroupsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetUsersProjectsAndGroupsResponse(getUsersProjectsAndGroupsResponse, "positiveCase")
                  .equalsIgnoreCase("success")) {
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.getMessage();
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   public static String testGetUsersProjectsAndGroupsWithInvalidLibrary() {

      GetUsersProjectsAndGroupsRequest getUsersProjectsAndGroupsRequest = new GetUsersProjectsAndGroupsRequest();
      Library library = new Library();
      library.setLibraryName("GovernedAssetsss");
      library.setLibraryId("320:9");
      getUsersProjectsAndGroupsRequest.setLibrary(library);

      GetUsersProjectsAndGroupsConsumer getProjectsAndGroupsConsumer = new GetUsersProjectsAndGroupsConsumer();
      try {
         GetUsersProjectsAndGroupsResponse getUsersProjectsAndGroupsResponse = getProjectsAndGroupsConsumer.getProxy()
                  .getUsersProjectsAndGroups(getUsersProjectsAndGroupsRequest);
         if (getUsersProjectsAndGroupsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetUsersProjectsAndGroupsResponse(getUsersProjectsAndGroupsResponse, "negativeCase")
                  .equalsIgnoreCase("success")) {
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.getMessage();
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   public static String testGetUsersProjectsAndGroupsWithoutLibrary() {

      GetUsersProjectsAndGroupsRequest getUsersProjectsAndGroupsRequest = new GetUsersProjectsAndGroupsRequest();

      GetUsersProjectsAndGroupsConsumer getProjectsAndGroupsConsumer = new GetUsersProjectsAndGroupsConsumer();
      try {
         GetUsersProjectsAndGroupsResponse getUsersProjectsAndGroupsResponse = getProjectsAndGroupsConsumer.getProxy()
                  .getUsersProjectsAndGroups(getUsersProjectsAndGroupsRequest);
         if (getUsersProjectsAndGroupsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetUsersProjectsAndGroupsResponse(getUsersProjectsAndGroupsResponse, "positiveCase")
                  .equalsIgnoreCase("success")) {
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.getMessage();
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   public static String testGetUsersProjectsAndGroupsWithEmptyLibrary() {

      GetUsersProjectsAndGroupsRequest getUsersProjectsAndGroupsRequest = new GetUsersProjectsAndGroupsRequest();
      Library library = new Library();
      getUsersProjectsAndGroupsRequest.setLibrary(library);

      GetUsersProjectsAndGroupsConsumer getProjectsAndGroupsConsumer = new GetUsersProjectsAndGroupsConsumer();
      try {
         GetUsersProjectsAndGroupsResponse getUsersProjectsAndGroupsResponse = getProjectsAndGroupsConsumer.getProxy()
                  .getUsersProjectsAndGroups(getUsersProjectsAndGroupsRequest);
         if (getUsersProjectsAndGroupsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetUsersProjectsAndGroupsResponse(getUsersProjectsAndGroupsResponse, "negativeCase")
                  .equalsIgnoreCase("success")) {
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.getMessage();
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   public static String testGetUsersProjectsAndGroupsWithEmptyRequest() {

      GetUsersProjectsAndGroupsRequest getUsersProjectsAndGroupsRequest = null;

      GetUsersProjectsAndGroupsConsumer getProjectsAndGroupsConsumer = new GetUsersProjectsAndGroupsConsumer();
      try {
         GetUsersProjectsAndGroupsResponse getUsersProjectsAndGroupsResponse = getProjectsAndGroupsConsumer.getProxy()
                  .getUsersProjectsAndGroups(getUsersProjectsAndGroupsRequest);
         if (getUsersProjectsAndGroupsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetUsersProjectsAndGroupsResponse(getUsersProjectsAndGroupsResponse, "negativeCase")
                  .equalsIgnoreCase("success")) {
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.getMessage();
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   public static String testGetUsersProjectsAndGroupsWithInvalidUser() {

      GetUsersProjectsAndGroupsRequest getUsersProjectsAndGroupsRequest = new GetUsersProjectsAndGroupsRequest();
      Library library = new Library();
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      getUsersProjectsAndGroupsRequest.setLibrary(library);
      getUsersProjectsAndGroupsRequest.setUsername("InvalidUser");

      GetUsersProjectsAndGroupsConsumer getProjectsAndGroupsConsumer = new GetUsersProjectsAndGroupsConsumer();
      try {
         GetUsersProjectsAndGroupsResponse getUsersProjectsAndGroupsResponse = getProjectsAndGroupsConsumer.getProxy()
                  .getUsersProjectsAndGroups(getUsersProjectsAndGroupsRequest);
         if (getUsersProjectsAndGroupsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetUsersProjectsAndGroupsResponse(getUsersProjectsAndGroupsResponse, "negativeCase")
                  .equalsIgnoreCase("success")) {
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.getMessage();
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   @Override
   protected AsyncTurmericRSV1 getProxy() throws ServiceException {
      /*
       * if(m_proxy == null) { String svcAdminName = RepositoryServiceClientConstants.SERVICE_NAME; Service service =
       * ServiceFactory.create(svcAdminName, RepositoryServiceClientConstants.SERVICE_NAME);
       * service.setSessionTransportHeader("X-TURMERIC-SECURITY-USERID", RepositoryServiceClientConstants.USER_ID);
       * service.setSessionTransportHeader("X-TURMERIC-SECURITY-PASSWORD",
       * RepositoryServiceClientConstants.USER_PASSWORD);
       * 
       * m_proxy = service.getProxy(); }
       * 
       * return m_proxy;
       */

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
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-USERID", RepositoryServiceClientConstants.USER_ID);
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-PASSWORD",
                  RepositoryServiceClientConstants.USER_PASSWORD);
      }
      return (AsyncTurmericRSV1) service.getProxy();
   }

   private static String validateGetUsersProjectsAndGroupsResponse(
            GetUsersProjectsAndGroupsResponse getUsersProjectsAndGroupsResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getUsersProjectsAndGroupsResponse.getAck().value().equalsIgnoreCase("success")) {
            return RepositoryServiceClientConstants.SUCCESS;
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getUsersProjectsAndGroupsResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (getUsersProjectsAndGroupsResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
      }

      return RepositoryServiceClientConstants.FAILURE;
   }

}
