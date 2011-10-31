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

import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.AsyncTurmericRSV2;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

/**
 * @author jpnadar
 * 
 */
public class LockAssetConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV2 m_proxy = null;

   public static String testLockAsset_validAsset(AssetKey assetKey) {

      LockAssetConsumer lockAssetConsumer = new LockAssetConsumer();

      LockAssetRequest lockAssetRequest = new LockAssetRequest();
      lockAssetRequest.setAssetKey(assetKey);

      LockAssetResponse lockAssetResponse = null;

      try {
         lockAssetResponse = lockAssetConsumer.getProxy().lockAsset(lockAssetRequest);
         if (lockAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateLockAssetResponse(lockAssetResponse, "positiveCase").equalsIgnoreCase("success")) {
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

   public static String testLockAsset_invalidAsset(AssetKey assetKey) {

      String invalidAssetName = "invalidAssetName";
      String invalidAssetId = "invalidAssetId";

      assetKey.setAssetId(invalidAssetId);
      assetKey.setAssetName(invalidAssetName);

      LockAssetRequest lockAssetRequest = new LockAssetRequest();
      lockAssetRequest.setAssetKey(assetKey);

      try {
         LockAssetConsumer lockAssetConsumer = new LockAssetConsumer();
         LockAssetResponse lockAssetResponse = lockAssetConsumer.getProxy().lockAsset(lockAssetRequest);
         if (lockAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateLockAssetResponse(lockAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = lockAssetResponse.getErrorMessage().getError();

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

   public static String testLockAsset_assetCurrentlyLocked(AssetInfo assetInfo) {
      LockAssetConsumer lockAssetConsumer = new LockAssetConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      LockAssetRequest lockAssetRequest = new LockAssetRequest();
      lockAssetRequest.setAssetKey(assetKey);

      LockAssetResponse lockAssetResponse = null;

      try {
         lockAssetResponse = lockAssetConsumer.getProxy().lockAsset(lockAssetRequest);
         if (lockAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateLockAssetResponse(lockAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = lockAssetResponse.getErrorMessage().getError();

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

   public static String testLockAsset_insufficientPrivilege() {
      LockAssetConsumer lockAssetConsumer = new LockAssetConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);

      LockAssetRequest lockAssetRequest = new LockAssetRequest();
      lockAssetRequest.setAssetKey(assetKey);

      LockAssetResponse lockAssetResponse = null;

      try {
         lockAssetResponse = lockAssetConsumer.getProxy().lockAsset(lockAssetRequest);
         if (lockAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateLockAssetResponse(lockAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = lockAssetResponse.getErrorMessage().getError();

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

   public static String validateLockAssetResponse(LockAssetResponse lockAssetResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (lockAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            return RepositoryServiceConsumerUtil.validateAssetInfo(lockAssetResponse.getAssetInfo());
         }
         if (lockAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
            AssetInfo assetInfo = lockAssetResponse.getAssetInfo();
            if (assetInfo == null) {
               return RepositoryServiceClientConstants.FAILURE;
            }
            return RepositoryServiceConsumerUtil.validateAssetInfo(assetInfo, "partialValidation");
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (lockAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            if (lockAssetResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
      }

      return RepositoryServiceClientConstants.FAILURE;
   }
}
