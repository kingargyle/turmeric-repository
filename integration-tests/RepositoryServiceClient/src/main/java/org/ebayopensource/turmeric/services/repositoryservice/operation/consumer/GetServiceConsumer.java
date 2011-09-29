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
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.GetServiceRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetServiceResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.ServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetServiceConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV1 m_proxy = null;

   public static String testGetService_validAsset(AssetInfo assetInfo) {
      GetServiceConsumer getServiceConsumer = new GetServiceConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library);

      GetServiceRequest getServiceRequest = new GetServiceRequest();
      getServiceRequest.setAssetKey(assetKey);

      try {
         GetServiceResponse getServiceResponse = getServiceConsumer.getProxy().getService(getServiceRequest);

         if (getServiceResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetServiceResponse(getServiceResponse, "positiveCase").equalsIgnoreCase("success")) {
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

   public static String testGetService_invalidAsset() {
      GetServiceConsumer getServiceConsumer = new GetServiceConsumer();

      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
      assetKey.setLibrary(library);

      GetServiceRequest getServiceRequest = new GetServiceRequest();
      getServiceRequest.setAssetKey(assetKey);

      try {
         GetServiceResponse getServiceResponse = getServiceConsumer.getProxy().getService(getServiceRequest);
         if (getServiceResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetServiceResponse(getServiceResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getServiceResponse.getErrorMessage().getError();

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

   private static String validateGetServiceResponse(GetServiceResponse getServiceResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getServiceResponse.getAck().value().equalsIgnoreCase("success")) {
            ServiceInfo serviceInfo = getServiceResponse.getServiceInfo();
            if (serviceInfo == null) {
               return "failure";
            }
            return RepositoryServiceConsumerUtil.validateServiceInfo(serviceInfo);
         }
         if (getServiceResponse.getAck().value().equalsIgnoreCase("partialFailure")) {
            if (getServiceResponse.getErrorMessage().getError().size() > 0) {
               List<CommonErrorData> errorDatas = getServiceResponse.getErrorMessage().getError();
               System.out.println("The following list of errors occured");
               for (CommonErrorData errorData : errorDatas) {
                  System.out.println("Error id: " + errorData.getErrorId() + " Error message: "
                           + errorData.getMessage());
               }

               ServiceInfo serviceInfo = getServiceResponse.getServiceInfo();
               if (serviceInfo == null) {
                  return "failure";
               }
               return RepositoryServiceConsumerUtil.validateServiceInfo(serviceInfo, "partialValidation");
            }
         }
         return "failure";
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getServiceResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (getServiceResponse.getErrorMessage().getError().size() > 0) {
               return "success";
            }
         }
      }

      return "failure";
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

}
