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
import org.ebayopensource.turmeric.repository.v1.services.AssetStatus;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetStatusRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetStatusResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetAssetStatusConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV1 m_proxy = null;

   public static String testGetAssetStatus_validAsset(AssetInfo assetInfo) {
      GetAssetStatusConsumer getAssetStatusConsumer = new GetAssetStatusConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library);

      GetAssetStatusRequest getAssetStatusRequest = new GetAssetStatusRequest();
      getAssetStatusRequest.setAssetKey(assetKey);

      try {
         GetAssetStatusResponse getAssetStatusResponse = getAssetStatusConsumer.getProxy().getAssetStatus(
                  getAssetStatusRequest);
         if (getAssetStatusResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetAssetStatusResponse(getAssetStatusResponse, "positiveCase").equalsIgnoreCase("success")) {
            AssetStatus assetStatus = getAssetStatusResponse.getAssetStatus();

            System.out
                     .println("testGetAssetStatus_validAsset resulted in : " + getAssetStatusResponse.getAck().value());
            System.out.println("Status info of the asset" + assetKey.getAssetName() + " is as follows");
            System.out.println("CURRENT STATE       : " + assetStatus.getState());
            System.out.println("NEXT STATE          : " + assetStatus.getNextState());
            // System.out.println("REVIEWER NAME(S)    : " + assetStatus.getReviewer().getUsername());
            // System.out.println("REVIEWER ROLE       : " + assetStatus.getReviewer().getRole());
            System.out.println("LAST ACTIVITY       : " + assetStatus.getLastActivity());
            System.out.println("LAST UPDATE TIME    : " + assetStatus.getLastUpdateTime());
            System.out.println("ASSET SUBMISION TIME: " + assetStatus.getAssetSubmissionTime());

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

   public static String testGetAssetStatus_invalidAsset() {
      GetAssetStatusConsumer getAssetStatusConsumer = new GetAssetStatusConsumer();

      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.INVALID_ASSET_NAME);
      assetKey.setLibrary(library);

      GetAssetStatusRequest getAssetStatusRequest = new GetAssetStatusRequest();
      getAssetStatusRequest.setAssetKey(assetKey);

      try {
         GetAssetStatusResponse getAssetStatusResponse = getAssetStatusConsumer.getProxy().getAssetStatus(
                  getAssetStatusRequest);

         if (getAssetStatusResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetStatusResponse(getAssetStatusResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getAssetStatusResponse.getErrorMessage().getError();

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

   public static String testGetAssetStatus_insufficientPrivilege() {
      GetAssetStatusConsumer getAssetStatusConsumer = new GetAssetStatusConsumer();

      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
      assetKey.setLibrary(library);

      GetAssetStatusRequest getAssetStatusRequest = new GetAssetStatusRequest();
      getAssetStatusRequest.setAssetKey(assetKey);

      try {
         GetAssetStatusResponse getAssetStatusResponse = getAssetStatusConsumer.getProxy().getAssetStatus(
                  getAssetStatusRequest);

         if (getAssetStatusResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetStatusResponse(getAssetStatusResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getAssetStatusResponse.getErrorMessage().getError();

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

   public static String testGetAssetStatus_assetwithnoassetRequests(AssetInfo assetInfo) {
      GetAssetStatusConsumer getAssetStatusConsumer = new GetAssetStatusConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library);

      GetAssetStatusRequest getAssetStatusRequest = new GetAssetStatusRequest();
      getAssetStatusRequest.setAssetKey(assetKey);

      try {
         GetAssetStatusResponse getAssetStatusResponse = getAssetStatusConsumer.getProxy().getAssetStatus(
                  getAssetStatusRequest);

         if (getAssetStatusResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetStatusResponse(getAssetStatusResponse, "partial").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getAssetStatusResponse.getErrorMessage().getError();

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

   private static String validateGetAssetStatusResponse(GetAssetStatusResponse getAssetStatusResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getAssetStatusResponse.getAck().value().equalsIgnoreCase("success")) {
            AssetStatus assetStatus = getAssetStatusResponse.getAssetStatus();
            if (assetStatus == null) {
               return "failure";
            }
            return RepositoryServiceConsumerUtil.validateAssetStatus(assetStatus);
         }
         return "failure";
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getAssetStatusResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (getAssetStatusResponse.getErrorMessage().getError().size() > 0) {
               return "success";
            }
         }
      }
      if (criteria.equalsIgnoreCase("partial")) {
         if (getAssetStatusResponse.getAck().value().equalsIgnoreCase("partialfailure")) {
            if (getAssetStatusResponse.getErrorMessage().getError().size() > 0) {
               return "success";
            }
         }
      }
      return "failure";
   }
}
