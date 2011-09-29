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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.ValidateAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.ValidateAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

/**
 * @author szacharias
 */
public class ValidateAssetConsumer extends BaseRepositoryServiceConsumer {

   private static final String LIBRARY_NAME = "Services";
   private static final String ASSET_ID = "1.0_1237266411797-1882195359";
   private static final String ASSET_NAME = "Jegan_test_17_03_2009";
   private static final String VERSION = "1.0.0";
   private static final String ASSET_ID_NEGATIVECASE = "1.0_1238676463399-1132652333";
   private static final String ASSET_NAME_NEGATIVECASE = "Jegan_test_asset_0011238676457118";
   private static final String VERSION_NEGATIVECASE = "1.1.0";
   private static final String ASSET_TYPE = "Service";

   private static final String DT_LIBRARY_ID = "352:6";
   private static final String DT_LIBRARY_NAME = "Data_Types";
   private static final String DT_ASSET_ID_NEGATIVECASE = "1.0_1240392066234636589028";
   private static final String DT_ASSET_NAME_NEGATIVECASE = "TestDataType-Sency ";
   private static final String DT_VERSION_NEGATIVECASE = "1.0.0";
   private static final String DT_ASSET_TYPE = "Data Type";

   private AsyncTurmericRSV1 m_proxy = null;

   public static void main(String[] args) {
      Map<String, String> results = new HashMap<String, String>();

      // results.put("testValidateServiceAsset_withValidAsset", testValidateServiceAsset_withValidAsset());
      results.put("testValidateServiceAsset_withInvalidAsset", testValidateServiceAsset_withInvalidAsset());
      // results.put("testValidateDataTypeAsset_withValidAsset", testValidateDataTypeAsset_withValidAsset());
      // results.put("testValidateDataTypeAsset_withInvalidAsset", testValidateDataTypeAsset_withInvalidAsset());

      System.out.println("\nValidateAseet Summary");
      Set<String> methods = results.keySet();
      for (String method : methods) {
         System.out.format("%-60s: %s\n", method, results.get(method));
      }
      System.out.println();
   }

   public static String testValidateServiceAsset_withValidAsset() {

      AssetInfo assetInfo = new AssetInfo();

      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      library.setLibraryName(LIBRARY_NAME);

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(ASSET_ID);
      assetKey.setAssetName(ASSET_NAME);
      assetKey.setLibrary(library);

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setAssetName(ASSET_NAME);
      basicAssetInfo.setAssetType(ASSET_TYPE);
      basicAssetInfo.setVersion(VERSION);
      assetInfo.setBasicAssetInfo(basicAssetInfo);

      ExtendedAssetInfo extendedAssetInfo = new ExtendedAssetInfo();
      AttributeNameValue attributeNameValue = new AttributeNameValue();
      attributeNameValue.setAttributeName("trackerID");
      attributeNameValue.setAttributeValueString("20123");
      extendedAssetInfo.getAttribute().add(attributeNameValue);

      ValidateAssetRequest validateAssetRequest = new ValidateAssetRequest();
      validateAssetRequest.setAssetInfo(assetInfo);

      ValidateAssetConsumer validateAssetConsumer = new ValidateAssetConsumer();
      try {
         ValidateAssetResponse validateAssetResponse = validateAssetConsumer.getProxy().validateAsset(
                  validateAssetRequest);
         if (validateAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateAssetResponse(validateAssetResponse, "positiveCase").equalsIgnoreCase("success")) {
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

   public static String testValidateServiceAsset_withInvalidAsset() {

      AssetInfo assetInfo = new AssetInfo();

      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(ASSET_ID_NEGATIVECASE);
      assetKey.setAssetName(ASSET_NAME_NEGATIVECASE);
      assetKey.setLibrary(library);

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setAssetName(ASSET_NAME_NEGATIVECASE);
      basicAssetInfo.setAssetType(ASSET_TYPE);
      basicAssetInfo.setVersion(VERSION_NEGATIVECASE);
      assetInfo.setBasicAssetInfo(basicAssetInfo);

      ValidateAssetRequest validateAssetRequest = new ValidateAssetRequest();
      validateAssetRequest.setAssetInfo(assetInfo);

      ValidateAssetConsumer validateAssetConsumer = new ValidateAssetConsumer();
      try {
         ValidateAssetResponse validateAssetResponse = validateAssetConsumer.getProxy().validateAsset(
                  validateAssetRequest);
         if (validateAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateAssetResponse(validateAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
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

   public static String testValidateDataTypeAsset_withValidAsset(AssetInfo assetInfo) {

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library);

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
      basicAssetInfo.setAssetType(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      basicAssetInfo.setVersion(assetInfo.getBasicAssetInfo().getVersion());
      assetInfo.setBasicAssetInfo(basicAssetInfo);

      ValidateAssetRequest validateAssetRequest = new ValidateAssetRequest();
      validateAssetRequest.setAssetInfo(assetInfo);

      ValidateAssetConsumer validateAssetConsumer = new ValidateAssetConsumer();
      try {
         ValidateAssetResponse validateAssetResponse = validateAssetConsumer.getProxy().validateAsset(
                  validateAssetRequest);
         if (validateAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateAssetResponse(validateAssetResponse, "positiveCase").equalsIgnoreCase("success")) {
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

   public static String testValidateDataTypeAsset_withInvalidAsset() {

      AssetInfo assetInfo = new AssetInfo();

      Library library = new Library();
      library.setLibraryId(DT_LIBRARY_ID);
      library.setLibraryName(DT_LIBRARY_NAME);

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(DT_ASSET_ID_NEGATIVECASE);
      assetKey.setAssetName(DT_ASSET_NAME_NEGATIVECASE);
      assetKey.setLibrary(library);

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setAssetName(DT_ASSET_NAME_NEGATIVECASE);
      basicAssetInfo.setAssetType(DT_ASSET_TYPE);
      basicAssetInfo.setVersion(DT_VERSION_NEGATIVECASE);
      assetInfo.setBasicAssetInfo(basicAssetInfo);

      ValidateAssetRequest validateAssetRequest = new ValidateAssetRequest();
      validateAssetRequest.setAssetInfo(assetInfo);

      ValidateAssetConsumer validateAssetConsumer = new ValidateAssetConsumer();
      try {
         ValidateAssetResponse validateAssetResponse = validateAssetConsumer.getProxy().validateAsset(
                  validateAssetRequest);
         if (validateAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateAssetResponse(validateAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
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

   private static String validateAssetResponse(ValidateAssetResponse validateAssetResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (validateAssetResponse.getAck().value().equalsIgnoreCase("success")) {
            if (validateAssetResponse.isIsValid()) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
         return "failure";
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (validateAssetResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (validateAssetResponse.getErrorMessage().getError().size() > 0) {
               return "success";
            }
         }
      }

      return "failure";
   }

}
