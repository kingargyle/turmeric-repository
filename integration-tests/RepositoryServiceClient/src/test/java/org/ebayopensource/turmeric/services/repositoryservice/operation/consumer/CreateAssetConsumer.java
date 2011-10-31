/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.AsyncTurmericRSV2;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class CreateAssetConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV2 m_proxy = null;
   private static String assetName = null;

   public static String testCreateAsset_validInput(String variant) {
      CreateAssetConsumer createAssetConsumer = new CreateAssetConsumer();
      CommonUtil.loadPropertyFile("properties/common.properties");
      java.util.Date date = new java.util.Date();
      assetName = RepositoryServiceClientConstants.ASSET_NAME + date.getTime();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetName(assetName);

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION);
      basicAssetInfo.setAssetName(assetName);
      basicAssetInfo.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
      basicAssetInfo.setVersion(RepositoryServiceClientConstants.ASSET_VERSION);
      basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setGroupName("catalogs");

      CreateAssetRequest createAssetRequest = new CreateAssetRequest();
      createAssetRequest.setBasicAssetInfo(basicAssetInfo);

      if (variant.equalsIgnoreCase("noAssetLongDescription")) {
         basicAssetInfo.setAssetLongDescription(null);
      }

      try {
         CreateAssetResponse createAssetResponse = createAssetConsumer.getProxy().createAsset(createAssetRequest);

         if (createAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateCreateAssetResponse(createAssetResponse, "positiveCase").equalsIgnoreCase(
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

   public static String testCreateAsset_noCaptureTemaplteName() {
      CreateAssetConsumer createAssetConsumer = new CreateAssetConsumer();

      java.util.Date date = new java.util.Date();
      assetName = RepositoryServiceClientConstants.ASSET_NAME + date.getTime();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetName(assetName);

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION);
      basicAssetInfo.setAssetName(assetName);
      basicAssetInfo.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
      basicAssetInfo.setVersion(RepositoryServiceClientConstants.ASSET_VERSION);
      basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setGroupName("catalogs");
      CreateAssetRequest createAssetRequest = new CreateAssetRequest();
      createAssetRequest.setBasicAssetInfo(basicAssetInfo);

      try {
         CreateAssetResponse createAssetResponse = createAssetConsumer.getProxy().createAsset(createAssetRequest);

         if (createAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateCreateAssetResponse(createAssetResponse, "positiveCase").equalsIgnoreCase(
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

   public static String testCreateAsset_invalidInput(String reason) {
      CreateAssetConsumer createAssetConsumer = new CreateAssetConsumer();

      Properties props = CommonUtil.loadPropertyFile("properties/common.properties");
      props.getProperty("libraryId");
      props.getProperty("libraryName", "GovernedAssets");

      java.util.Date date = new java.util.Date();
      String assetName = RepositoryServiceClientConstants.ASSET_NAME + date.getTime();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetName(assetName);

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION);
      basicAssetInfo.setAssetName(assetName);
      basicAssetInfo.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
      basicAssetInfo.setVersion(RepositoryServiceClientConstants.ASSET_VERSION);
      basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setGroupName("catalogs");

      if (reason.equalsIgnoreCase("noAssetName")) {
         basicAssetInfo.getAssetKey().setAssetName(null);
         basicAssetInfo.setAssetName(null);
      }
      if (reason.equalsIgnoreCase("noAssetKey")) {
         basicAssetInfo.setAssetKey(null);
      }
      if (reason.equalsIgnoreCase("duplicateBasicAssetInfo")) {
         basicAssetInfo.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
         basicAssetInfo.getAssetKey().setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
      }
      if (reason.equalsIgnoreCase("noAssetDescription")) {
         basicAssetInfo.setAssetDescription(null);
      }
      if (reason.equalsIgnoreCase("noAssetType")) {
         basicAssetInfo.setAssetType(null);
      }
      if (reason.equalsIgnoreCase("noAssetVersion")) {
         basicAssetInfo.setVersion(null);
      }

      CreateAssetRequest createAssetRequest = new CreateAssetRequest();
      createAssetRequest.setBasicAssetInfo(basicAssetInfo);
      try {
         CreateAssetResponse createAssetResponse = createAssetConsumer.getProxy().createAsset(createAssetRequest);
         if (reason.equalsIgnoreCase("noAssetLongDescription") || reason.equalsIgnoreCase("noAssetDescription")) {
            if (validateCreateAssetResponse(createAssetResponse, "positiveCase").equalsIgnoreCase(
                     RepositoryServiceClientConstants.SUCCESS)) {

               return RepositoryServiceClientConstants.PASS;
            } else {
               return RepositoryServiceClientConstants.FAIL;
            }
         } else {
            if (validateCreateAssetResponse(createAssetResponse, "negativeCase").equalsIgnoreCase(
                     RepositoryServiceClientConstants.SUCCESS)) {
               List<CommonErrorData> errorDatas = createAssetResponse.getErrorMessage().getError();

               System.out.println("The following list of errors occured");
               for (CommonErrorData errorData : errorDatas) {
                  System.out.println("Error id: " + errorData.getErrorId() + " Error message: "
                           + errorData.getMessage());
               }
               return RepositoryServiceClientConstants.PASS;
            } else {
               return RepositoryServiceClientConstants.FAIL;
            }
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

   /**
    * @author csubhash
    * @return
    */
   public static String testCreateAsset_invalidVersion() {
      CreateAssetConsumer createAssetConsumer = new CreateAssetConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME + "_new5");

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION);
      basicAssetInfo.setAssetName(RepositoryServiceClientConstants.ASSET_NAME + "_new5");
      basicAssetInfo.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
      basicAssetInfo.setVersion(RepositoryServiceClientConstants.INVALID_ASSET_VERSION);
      basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setGroupName("catalogs");

      CreateAssetRequest createAssetRequest = new CreateAssetRequest();
      createAssetRequest.setBasicAssetInfo(basicAssetInfo);
      try {
         CreateAssetResponse createAssetResponse = createAssetConsumer.getProxy().createAsset(createAssetRequest);

         if (createAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateCreateAssetResponse(createAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
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

   /**
    * @author csubhash
    * @return
    */
   public static String testCreateAsset_emptyBasicInfo() {
      CreateAssetConsumer createAssetConsumer = new CreateAssetConsumer();

      CreateAssetRequest createAssetRequest = new CreateAssetRequest();
      createAssetRequest.setBasicAssetInfo(null);

      try {
         CreateAssetResponse createAssetResponse = createAssetConsumer.getProxy().createAsset(createAssetRequest);

         if (createAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateCreateAssetResponse(createAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
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

   /**
    * @author csubhash
    * @return
    */
   public static String testCreateAsset_sameNameDifferentversion() {
      CreateAssetConsumer createAssetConsumer = new CreateAssetConsumer();

      AssetKey assetKey = new AssetKey();
      /* assetName = RepositoryServiceClientConstants.ASSET_NAME + "_new_" + (new Date()).getTime(); */
      assetKey.setAssetName(assetName);

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION);
      basicAssetInfo.setAssetName(assetName);
      basicAssetInfo.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
      basicAssetInfo.setVersion(RepositoryServiceClientConstants.ASSET_NEW_VERSION);
      basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setGroupName("catalogs");

      CreateAssetRequest createAssetRequest = new CreateAssetRequest();
      createAssetRequest.setBasicAssetInfo(basicAssetInfo);
      try {
         CreateAssetResponse createAssetResponse = createAssetConsumer.getProxy().createAsset(createAssetRequest);

         if (createAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateCreateAssetResponse(createAssetResponse, "positiveCase").equalsIgnoreCase("success")) {
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

   /**
    * @author csubhash
    * @return
    */
   public static String testCreateAsset_invalidAssetType() {
      CreateAssetConsumer createAssetConsumer = new CreateAssetConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME + "_new_" + (new Date()).getTime());

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION);
      basicAssetInfo.setAssetName(assetName);
      basicAssetInfo.setAssetType(RepositoryServiceClientConstants.INVALID_ASSET_TYPE);
      basicAssetInfo.setVersion(RepositoryServiceClientConstants.ASSET_VERSION);
      basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setGroupName("catalogs");

      CreateAssetRequest createAssetRequest = new CreateAssetRequest();
      createAssetRequest.setBasicAssetInfo(basicAssetInfo);
      try {
         CreateAssetResponse createAssetResponse = createAssetConsumer.getProxy().createAsset(createAssetRequest);

         if (createAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateCreateAssetResponse(createAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
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

   private static String validateCreateAssetResponse(CreateAssetResponse createAssetResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (createAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            return RepositoryServiceConsumerUtil.validateAssetKey(createAssetResponse.getAssetKey());
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (createAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            if (createAssetResponse.getErrorMessage().getError().size() > 0) {
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
