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

public class GetBasicAssetInfoConsumer extends BaseRepositoryServiceConsumer {

   private AsyncTurmericRSV2 m_proxy = null;

   public static String testGetBasicAssetInfo_validAsset(AssetInfo assetInfo) {
      AssetKey assetKey = assetInfo.getBasicAssetInfo().getAssetKey();
      String assetName = assetKey.getAssetName();
      GetBasicAssetInfoConsumer getBasicAssetInfoConsumer = new GetBasicAssetInfoConsumer();
      AssetKey assetKey1 = new AssetKey();
      assetKey1.setAssetName(assetName);

      GetBasicAssetInfoRequest getBasicAssetInfoRequest = new GetBasicAssetInfoRequest();
      getBasicAssetInfoRequest.setAssetKey(assetKey1);
      getBasicAssetInfoRequest.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());

      try {
         GetBasicAssetInfoResponse getBasicAssetInfoResponse = getBasicAssetInfoConsumer.getProxy().getBasicAssetInfo(
                  getBasicAssetInfoRequest);

         if (getBasicAssetInfoResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetBasicAssetInfoResponse(getBasicAssetInfoResponse, "positiveCase").equalsIgnoreCase("success")) {
            BasicAssetInfo basicAssetInfo = getBasicAssetInfoResponse.getBasicAssetInfo();
            System.out.println();
            System.out.println("Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
            System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
            System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
            System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
            System.out.println("Asset version         : " + basicAssetInfo.getVersion());
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

   public static String testGetBasicAssetInfo_validAsset_Long_Description(AssetKey assetKey) {
      String assetId = assetKey.getAssetId();

      GetBasicAssetInfoConsumer getBasicAssetInfoConsumer = new GetBasicAssetInfoConsumer();

      AssetKey assetKey1 = new AssetKey();
      assetKey1.setAssetId(assetId);

      GetBasicAssetInfoRequest getBasicAssetInfoRequest = new GetBasicAssetInfoRequest();
      getBasicAssetInfoRequest.setAssetKey(assetKey1);

      try {
         GetBasicAssetInfoResponse getBasicAssetInfoResponse = getBasicAssetInfoConsumer.getProxy().getBasicAssetInfo(
                  getBasicAssetInfoRequest);

         if (getBasicAssetInfoResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetBasicAssetInfoResponse(getBasicAssetInfoResponse, "positiveCase").equalsIgnoreCase("success")) {
            BasicAssetInfo basicAssetInfo = getBasicAssetInfoResponse.getBasicAssetInfo();
            System.out.println("Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
            System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
            System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
            System.out.println("Asset long description test: " + basicAssetInfo.getAssetLongDescription());
            System.out.println("Asset version         : " + basicAssetInfo.getVersion());

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

   public static String testGetBasicAssetInfo_invalidAsset(AssetKey assetKey) {

      GetBasicAssetInfoConsumer getBasicAssetInfoConsumer = new GetBasicAssetInfoConsumer();

      String invalidAssetId = "invalidId";
      String invalidAssetName = "invalidName";

      AssetKey assetKey1 = new AssetKey();
      assetKey1.setAssetId(invalidAssetId);
      assetKey1.setAssetName(invalidAssetName);

      GetBasicAssetInfoRequest getBasicAssetInfoRequest = new GetBasicAssetInfoRequest();
      getBasicAssetInfoRequest.setAssetKey(assetKey1);

      try {
         GetBasicAssetInfoResponse getBasicAssetInfoResponse = getBasicAssetInfoConsumer.getProxy().getBasicAssetInfo(
                  getBasicAssetInfoRequest);

         if (getBasicAssetInfoResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetBasicAssetInfoResponse(getBasicAssetInfoResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getBasicAssetInfoResponse.getErrorMessage().getError();

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

   public static String testGetBasicAssetInfo_insufficientPrivilege() {
      GetBasicAssetInfoConsumer getBasicAssetInfoConsumer = new GetBasicAssetInfoConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);

      GetBasicAssetInfoRequest getBasicAssetInfoRequest = new GetBasicAssetInfoRequest();
      getBasicAssetInfoRequest.setAssetKey(assetKey);

      try {
         GetBasicAssetInfoResponse getBasicAssetInfoResponse = getBasicAssetInfoConsumer.getProxy().getBasicAssetInfo(
                  getBasicAssetInfoRequest);

         if (getBasicAssetInfoResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetBasicAssetInfoResponse(getBasicAssetInfoResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getBasicAssetInfoResponse.getErrorMessage().getError();

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

   private static String validateGetBasicAssetInfoResponse(GetBasicAssetInfoResponse getBasicAssetInfoResponse,
            String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getBasicAssetInfoResponse.getAck().value().equalsIgnoreCase("success")) {
            BasicAssetInfo basicAssetInfo = getBasicAssetInfoResponse.getBasicAssetInfo();
            if (basicAssetInfo == null) {
               return "failure";
            }
            return RepositoryServiceConsumerUtil.validateBasicAssetInfo(basicAssetInfo);
         }
         return "failure";
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getBasicAssetInfoResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (getBasicAssetInfoResponse.getErrorMessage().getError().size() > 0) {
               return "success";
            }
         }
      }

      return "failure";
   }
}
