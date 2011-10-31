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

public class GetAssetVersionsConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV2 m_proxy = null;

   public static String testGetAssetVersions_validInput(String variant, AssetInfo assetInfo) {
      GetAssetVersionsConsumer getAssetVersionsConsumer = new GetAssetVersionsConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      if (variant.equalsIgnoreCase("withoutAssetId")) {
         assetKey.setAssetId(null);
      }
      if (variant.equalsIgnoreCase("withoutAssetName")) {
         assetKey.setAssetName(null);
      }

      GetAssetVersionsRequest getAssetVersionsRequest = new GetAssetVersionsRequest();
      getAssetVersionsRequest.setAssetKey(assetKey);

      try {
         GetAssetVersionsResponse getAssetVersionsResponse = getAssetVersionsConsumer.getProxy().getAssetVersions(
                  getAssetVersionsRequest);
         if (getAssetVersionsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetAssetVersionsResponse(getAssetVersionsResponse, "positiveCase").equalsIgnoreCase("success")) {
            List<BasicAssetInfo> basicAssetInfo_list = getAssetVersionsResponse.getBasicAssetInfo();
            System.out.println("\nVarious versions of the asset: " + assetKey.getAssetName() + " are as follows");
            for (BasicAssetInfo basicAssetInfo : basicAssetInfo_list) {
               System.out.println(basicAssetInfo.getVersion());
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

   public static String testGetAssetVersions_invalidInput(String variant, AssetInfo assetInfo) {
      GetAssetVersionsConsumer getAssetVersionsConsumer = new GetAssetVersionsConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      if (variant.equalsIgnoreCase("invalidAssetId")) {
         assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
         assetKey.setAssetName(null);
      }
      if (variant.equalsIgnoreCase("invalidAssetName")) {
         assetKey.setAssetId(null);
         assetKey.setAssetName(RepositoryServiceClientConstants.INVALID_ASSET_NAME);
      }
      if (variant.equalsIgnoreCase("noAssetIdAndNoAssetName")) {
         assetKey.setAssetName(null);
         assetKey.setAssetId(null);
      }

      GetAssetVersionsRequest getAssetVersionsRequest = new GetAssetVersionsRequest();
      getAssetVersionsRequest.setAssetKey(assetKey);

      try {
         GetAssetVersionsResponse getAssetVersionsResponse = getAssetVersionsConsumer.getProxy().getAssetVersions(
                  getAssetVersionsRequest);

         if (getAssetVersionsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetVersionsResponse(getAssetVersionsResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getAssetVersionsResponse.getErrorMessage().getError();

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

   public static String testGetAssetVersions_insufficientPrivilege() {
      GetAssetVersionsConsumer getAssetVersionsConsumer = new GetAssetVersionsConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);

      GetAssetVersionsRequest getAssetVersionsRequest = new GetAssetVersionsRequest();
      getAssetVersionsRequest.setAssetKey(assetKey);

      try {
         GetAssetVersionsResponse getAssetVersionsResponse = getAssetVersionsConsumer.getProxy().getAssetVersions(
                  getAssetVersionsRequest);

         if (getAssetVersionsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetVersionsResponse(getAssetVersionsResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getAssetVersionsResponse.getErrorMessage().getError();

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

   private static String validateGetAssetVersionsResponse(GetAssetVersionsResponse getAssetVersionsResponse,
            String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getAssetVersionsResponse.getAck().value().equalsIgnoreCase("success")) {
            List<BasicAssetInfo> basicAssetInfo_list = getAssetVersionsResponse.getBasicAssetInfo();
            for (BasicAssetInfo basicAssetInfo : basicAssetInfo_list) {
               if (RepositoryServiceConsumerUtil.validateBasicAssetInfo(basicAssetInfo).equalsIgnoreCase("failure")) {
                  return "failure";
               }
            }
            return "success";
         }
         return "failure";
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getAssetVersionsResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (getAssetVersionsResponse.getErrorMessage().getError().size() > 0) {
               return "success";
            }
         }
      }

      return "failure";
   }
}
