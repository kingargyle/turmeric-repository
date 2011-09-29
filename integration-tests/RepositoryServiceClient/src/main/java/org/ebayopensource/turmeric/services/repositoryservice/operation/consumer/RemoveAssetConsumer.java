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
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.RemoveAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.RemoveAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.TurmericRSV1;

import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class RemoveAssetConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV1 m_proxy = null;

   public static String testRemoveAsset_positiveScenario(AssetInfo assetInfo) {
      RemoveAssetRequest removeAssetRequest = new RemoveAssetRequest();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());

      assetKey.setLibrary(library);
      assetKey.setLibrary(library);
      removeAssetRequest.setAssetKey(assetKey);

      try {
         RemoveAssetConsumer removeAssetConsumer = new RemoveAssetConsumer();
         RemoveAssetResponse removeAssetResponse = removeAssetConsumer.getProxy().removeAsset(removeAssetRequest);
         if (removeAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateRemoveAssetResponse(removeAssetResponse, "positiveCase").equalsIgnoreCase("success")) {
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

   public static String testRemoveAsset_assetIsLockedByAnotherUser(AssetInfo assetInfo) {
      RemoveAssetRequest removeAssetRequest = new RemoveAssetRequest();
      RemoveAssetConsumer removeAssetConsumer = new RemoveAssetConsumer();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());

      assetKey.setLibrary(library);
      removeAssetRequest.setAssetKey(assetKey);
      try {

         RemoveAssetResponse removeAssetResponse = removeAssetConsumer.getProxy().removeAsset(removeAssetRequest);
         if (removeAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateRemoveAssetResponse(removeAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = removeAssetResponse.getErrorMessage().getError();

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

   private static String validateRemoveAssetResponse(RemoveAssetResponse removeAssetResponse, String criteria) {
      String returnStatus = "faliure";
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (removeAssetResponse.getAck().value().equalsIgnoreCase("success")) {
            returnStatus = "success";
         }
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (removeAssetResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (removeAssetResponse.getErrorMessage().getError().size() > 0) {
               returnStatus = "success";
            }
         }
      }

      return returnStatus;
   }
}
