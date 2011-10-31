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

public class UnlockAssetConsumer extends BaseRepositoryServiceConsumer {

   private AsyncTurmericRSV2 m_proxy = null;

   public static String testUnlockAsset_validAsset(AssetKey assetKey) {
      UnlockAssetConsumer unlockAssetConsumer = new UnlockAssetConsumer();

      UnlockAssetRequest unlockAssetRequest = new UnlockAssetRequest();
      unlockAssetRequest.setAssetKey(assetKey);

      UnlockAssetResponse unlockAssetResponse = null;

      try {
         unlockAssetResponse = unlockAssetConsumer.getProxy().unlockAsset(unlockAssetRequest);
         if (unlockAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateUnlockAssetResponse(unlockAssetResponse, "positiveCase").equalsIgnoreCase("success")) {
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

   public static String testUnlockAsset_invalidAsset() {
      UnlockAssetConsumer unlockAssetConsumer = new UnlockAssetConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);

      UnlockAssetRequest unlockAssetRequest = new UnlockAssetRequest();
      unlockAssetRequest.setAssetKey(assetKey);

      UnlockAssetResponse unlockAssetResponse = null;

      try {
         unlockAssetResponse = unlockAssetConsumer.getProxy().unlockAsset(unlockAssetRequest);
         if (unlockAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateUnlockAssetResponse(unlockAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = unlockAssetResponse.getErrorMessage().getError();

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

   public static String testUnlockAsset_assetCurrentlyNotLocked(AssetInfo assetInfo) {
      UnlockAssetConsumer unlockAssetConsumer = new UnlockAssetConsumer();

      AssetKey assetKey = assetInfo.getBasicAssetInfo().getAssetKey();

      UnlockAssetRequest unlockAssetRequest = new UnlockAssetRequest();
      unlockAssetRequest.setAssetKey(assetKey);

      UnlockAssetResponse unlockAssetResponse = null;

      try {
         unlockAssetResponse = unlockAssetConsumer.getProxy().unlockAsset(unlockAssetRequest);
         if (unlockAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateUnlockAssetResponse(unlockAssetResponse, "positiveCase").equalsIgnoreCase("success")) {
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

   public static String testUnlockAsset_insufficientPrivilege() {
      UnlockAssetConsumer unlockAssetConsumer = new UnlockAssetConsumer();

      AssetKey assetKey = new AssetKey();
      // TODO: the below constants should be updated to refer an underprivileged asset
      assetKey.setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);

      UnlockAssetRequest unlockAssetRequest = new UnlockAssetRequest();
      unlockAssetRequest.setAssetKey(assetKey);

      UnlockAssetResponse unlockAssetResponse = null;

      try {
         unlockAssetResponse = unlockAssetConsumer.getProxy().unlockAsset(unlockAssetRequest);
         if (unlockAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateUnlockAssetResponse(unlockAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = unlockAssetResponse.getErrorMessage().getError();

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

   public static String validateUnlockAssetResponse(UnlockAssetResponse unlockAssetResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (unlockAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            return RepositoryServiceConsumerUtil.validateAssetInfo(unlockAssetResponse.getAssetInfo());
         }
         if (unlockAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
            AssetInfo assetInfo = unlockAssetResponse.getAssetInfo();
            if (assetInfo == null) {
               return RepositoryServiceClientConstants.FAILURE;
            }
            return RepositoryServiceConsumerUtil.validateAssetInfo(assetInfo, "partialValidation");
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (unlockAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            if (unlockAssetResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
      }

      return RepositoryServiceClientConstants.FAILURE;
   }
}
