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

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.AsyncTurmericRSV2;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetAssetLifeCycleStatesConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV2 m_proxy = null;

   public static String testGetAssetLifeCycleStates_validInput(String varient) {
      GetAssetLifeCycleStatesConsumer getAssetLifeCycleStatesConsumer = new GetAssetLifeCycleStatesConsumer();
      GetAssetLifeCycleStatesRequest getAssetLifeCycleStatesRequest = new GetAssetLifeCycleStatesRequest();

      getAssetLifeCycleStatesRequest.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);

      try {
         GetAssetLifeCycleStatesResponse getAssetLifeCycleStatesResponse = getAssetLifeCycleStatesConsumer.getProxy()
                  .getAssetLifeCycleStates(getAssetLifeCycleStatesRequest);
         if (getAssetLifeCycleStatesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetAssetLifeCycleStatesResponse(getAssetLifeCycleStatesResponse, "positiveCase").equalsIgnoreCase(
                  RepositoryServiceClientConstants.SUCCESS)) {

            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   public static String testGetAssetLifeCycleStates_invalidInput(String varient) {
      GetAssetLifeCycleStatesConsumer getAssetLifeCycleStatesConsumer = new GetAssetLifeCycleStatesConsumer();
      GetAssetLifeCycleStatesRequest getAssetLifeCycleStatesRequest = new GetAssetLifeCycleStatesRequest();

      getAssetLifeCycleStatesRequest.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);

      if (varient.equalsIgnoreCase("invalidAssetType")) {
         getAssetLifeCycleStatesRequest.setAssetType(RepositoryServiceClientConstants.INVALID_ASSET_TYPE);
      }
      if (varient.equalsIgnoreCase("withoutAssetType")) {
         getAssetLifeCycleStatesRequest.setAssetType(null);
      }

      try {
         GetAssetLifeCycleStatesResponse getAssetLifeCycleStatesResponse = getAssetLifeCycleStatesConsumer.getProxy()
                  .getAssetLifeCycleStates(getAssetLifeCycleStatesRequest);
         if (getAssetLifeCycleStatesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetAssetLifeCycleStatesResponse(getAssetLifeCycleStatesResponse, "negativeCase").equalsIgnoreCase(
                  RepositoryServiceClientConstants.SUCCESS)) {
            List<CommonErrorData> errorDatas = getAssetLifeCycleStatesResponse.getErrorMessage().getError();

            System.out.println("The following list of errors occured");
            for (CommonErrorData errorData : errorDatas) {
               System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());
            }
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   private static String validateGetAssetLifeCycleStatesResponse(
            GetAssetLifeCycleStatesResponse getAssetLifeCycleStatesResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getAssetLifeCycleStatesResponse.getAck().value()
                  .equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)
                  || getAssetLifeCycleStatesResponse.getAck().value()
                           .equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
            return RepositoryServiceClientConstants.SUCCESS;
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getAssetLifeCycleStatesResponse.getAck().value()
                  .equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            if (getAssetLifeCycleStatesResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
      }

      return RepositoryServiceClientConstants.FAILURE;
   }

   @Override
   protected AsyncTurmericRSV2 getProxy() throws ServiceException {
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

}
