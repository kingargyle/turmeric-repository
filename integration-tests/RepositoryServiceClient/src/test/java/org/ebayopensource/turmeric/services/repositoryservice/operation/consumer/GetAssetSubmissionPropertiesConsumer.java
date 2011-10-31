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

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.AsyncTurmericRSV2;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

/**
 * @author szacharias
 */
public class GetAssetSubmissionPropertiesConsumer extends BaseRepositoryServiceConsumer {

   private AsyncTurmericRSV2 m_proxy = null;

   public static String testGetAssetSubmissionProperties_UnlockedAsset(AssetInfo assetInfo) {

      GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest = new GetAssetSubmissionPropertiesRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      getAssetSubmissionPropertiesRequest.setAssetKey(assetKey);

      GetAssetSubmissionPropertiesConsumer getAssetSubmissionPropertiesConsumer = new GetAssetSubmissionPropertiesConsumer();
      try {
         GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = getAssetSubmissionPropertiesConsumer
                  .getProxy().getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
         if (getAssetSubmissionPropertiesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetSubmissionPropertiesResponse(getAssetSubmissionPropertiesResponse, "positiveCase")
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

   public static String testGetAssetSubmissionProperties_LockedAsset(AssetInfo assetInfo) {

      GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest = new GetAssetSubmissionPropertiesRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
      getAssetSubmissionPropertiesRequest.setAssetKey(assetKey);

      GetAssetSubmissionPropertiesConsumer getAssetSubmissionPropertiesConsumer = new GetAssetSubmissionPropertiesConsumer();
      try {
         GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = getAssetSubmissionPropertiesConsumer
                  .getProxy().getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
         if (getAssetSubmissionPropertiesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetSubmissionPropertiesResponse(getAssetSubmissionPropertiesResponse, "positiveCase")
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

   public static String testGetAssetSubmissionProperties_WithoutAssetId() {

      GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest = new GetAssetSubmissionPropertiesRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetName("Reg_CreateAsset11249280044835");

      getAssetSubmissionPropertiesRequest.setAssetKey(assetKey);

      GetAssetSubmissionPropertiesConsumer getAssetSubmissionPropertiesConsumer = new GetAssetSubmissionPropertiesConsumer();
      try {
         GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = getAssetSubmissionPropertiesConsumer
                  .getProxy().getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
         if (getAssetSubmissionPropertiesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetSubmissionPropertiesResponse(getAssetSubmissionPropertiesResponse, "negativeCase")
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

   public static String testGetAssetSubmissionProperties_WithInvalidLibrary() {

      GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest = new GetAssetSubmissionPropertiesRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId("1.0_12492800530588078296");
      assetKey.setAssetName("Reg_CreateAsset11249280044835");

      getAssetSubmissionPropertiesRequest.setAssetKey(assetKey);

      GetAssetSubmissionPropertiesConsumer getAssetSubmissionPropertiesConsumer = new GetAssetSubmissionPropertiesConsumer();
      try {
         GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = getAssetSubmissionPropertiesConsumer
                  .getProxy().getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
         if (getAssetSubmissionPropertiesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetSubmissionPropertiesResponse(getAssetSubmissionPropertiesResponse, "negativeCase")
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

   public static String testGetAssetSubmissionProperties_WithInvalidAssetId() {

      GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest = new GetAssetSubmissionPropertiesRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId("1.0_1249280053058807896");
      assetKey.setAssetName("Reg_CreateAsset11249280044835");

      getAssetSubmissionPropertiesRequest.setAssetKey(assetKey);

      GetAssetSubmissionPropertiesConsumer getAssetSubmissionPropertiesConsumer = new GetAssetSubmissionPropertiesConsumer();
      try {
         GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = getAssetSubmissionPropertiesConsumer
                  .getProxy().getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
         if (getAssetSubmissionPropertiesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetSubmissionPropertiesResponse(getAssetSubmissionPropertiesResponse, "negativeCase")
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

   public static String testGetAssetSubmissionProperties_EmptyRequest() {

      GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest = null;

      GetAssetSubmissionPropertiesConsumer getAssetSubmissionPropertiesConsumer = new GetAssetSubmissionPropertiesConsumer();
      try {
         GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = getAssetSubmissionPropertiesConsumer
                  .getProxy().getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
         if (getAssetSubmissionPropertiesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetSubmissionPropertiesResponse(getAssetSubmissionPropertiesResponse, "negativeCase")
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

   public static String testGetAssetSubmissionProperties_EmptyLibrary() {

      GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest = new GetAssetSubmissionPropertiesRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId("1.0_12492800530588078296");
      assetKey.setAssetName("Reg_CreateAsset11249280044835");

      getAssetSubmissionPropertiesRequest.setAssetKey(assetKey);

      GetAssetSubmissionPropertiesConsumer getAssetSubmissionPropertiesConsumer = new GetAssetSubmissionPropertiesConsumer();
      try {
         GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = getAssetSubmissionPropertiesConsumer
                  .getProxy().getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
         if (getAssetSubmissionPropertiesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetSubmissionPropertiesResponse(getAssetSubmissionPropertiesResponse, "negativeCase")
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

   public static String testGetAssetSubmissionProperties_WithoutLibraryName() {

      GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest = new GetAssetSubmissionPropertiesRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId("1.0_12492800530588078296");
      assetKey.setAssetName("Reg_CreateAsset11249280044835");

      getAssetSubmissionPropertiesRequest.setAssetKey(assetKey);

      GetAssetSubmissionPropertiesConsumer getAssetSubmissionPropertiesConsumer = new GetAssetSubmissionPropertiesConsumer();
      try {
         GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = getAssetSubmissionPropertiesConsumer
                  .getProxy().getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
         if (getAssetSubmissionPropertiesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetSubmissionPropertiesResponse(getAssetSubmissionPropertiesResponse, "negativeCase")
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

   public static String testGetAssetSubmissionProperties_WithoutAssetKey() {

      GetAssetSubmissionPropertiesRequest getAssetSubmissionPropertiesRequest = new GetAssetSubmissionPropertiesRequest();

      GetAssetSubmissionPropertiesConsumer getAssetSubmissionPropertiesConsumer = new GetAssetSubmissionPropertiesConsumer();
      try {
         GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse = getAssetSubmissionPropertiesConsumer
                  .getProxy().getAssetSubmissionProperties(getAssetSubmissionPropertiesRequest);
         if (getAssetSubmissionPropertiesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetSubmissionPropertiesResponse(getAssetSubmissionPropertiesResponse, "negativeCase")
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

   private static String validateGetAssetSubmissionPropertiesResponse(
            GetAssetSubmissionPropertiesResponse getAssetSubmissionPropertiesResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getAssetSubmissionPropertiesResponse.getAck().value().equalsIgnoreCase("success")) {
            return RepositoryServiceClientConstants.SUCCESS;
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getAssetSubmissionPropertiesResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (getAssetSubmissionPropertiesResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
      }

      return RepositoryServiceClientConstants.FAILURE;
   }

}
