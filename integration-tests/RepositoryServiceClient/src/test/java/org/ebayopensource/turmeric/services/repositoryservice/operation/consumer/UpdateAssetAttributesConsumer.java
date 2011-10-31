/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.util.ArrayList;
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

public class UpdateAssetAttributesConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV2 m_proxy = null;

   public static String testUpdateAssetAttributes_validAsset(AssetInfo assetInfo) {
      UpdateAssetAttributesConsumer updateAssetAttributesConsumer = new UpdateAssetAttributesConsumer();

      int updatedAttributesCount = 3;
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      List<AttributeNameValue> attributeNameValues = new ArrayList<AttributeNameValue>();
      AttributeNameValue attributeNameValue = new AttributeNameValue();
      attributeNameValue.setAttributeName(RepositoryServiceClientConstants.ATTRIBUTE1_NAME);
      attributeNameValue.setAttributeValueString(RepositoryServiceClientConstants.ATTRIBUTE1_VALUE);
      attributeNameValues.add(attributeNameValue);
      attributeNameValue = new AttributeNameValue();
      attributeNameValue.setAttributeName(RepositoryServiceClientConstants.ATTRIBUTE2_NAME);
      attributeNameValue.setAttributeValueLong(RepositoryServiceClientConstants.ATTRIBUTE2_VALUE);
      attributeNameValues.add(attributeNameValue);
      attributeNameValue = new AttributeNameValue();
      attributeNameValue.setAttributeName(RepositoryServiceClientConstants.ATTRIBUTE3_NAME);
      attributeNameValue.setAttributeValueBoolean(RepositoryServiceClientConstants.ATTRIBUTE3_VALUE);
      attributeNameValues.add(attributeNameValue);

      ExtendedAssetInfo extendedAssetInfo = new ExtendedAssetInfo();
      extendedAssetInfo.getAttribute().addAll(attributeNameValues);

      UpdateAssetAttributesRequest updateAssetAttributesRequest = new UpdateAssetAttributesRequest();
      updateAssetAttributesRequest.setAssetKey(assetKey);
      updateAssetAttributesRequest.setExtendedAssetInfo(extendedAssetInfo);
      updateAssetAttributesRequest.setPartialUpdate(true);
      updateAssetAttributesRequest.setReplaceCurrent(true);

      try {
         UpdateAssetAttributesResponse updateAssetAttributesResponse = updateAssetAttributesConsumer.getProxy()
                  .updateAssetAttributes(updateAssetAttributesRequest);
         if (updateAssetAttributesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateUpdateAssetAttributesResponse(updateAssetAttributesResponse, "positiveCase",
                  updatedAttributesCount).equalsIgnoreCase("success")) {
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

   public static String testUpdateAssetAttributes_invalidAsset() {
      UpdateAssetAttributesConsumer updateAssetAttributesConsumer = new UpdateAssetAttributesConsumer();

      CommonUtil.loadPropertyFile("properties/common.properties");

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.INVALID_ASSET_NAME);

      List<AttributeNameValue> attributeNameValues = new ArrayList<AttributeNameValue>();
      AttributeNameValue attributeNameValue = new AttributeNameValue();
      attributeNameValue.setAttributeName(RepositoryServiceClientConstants.ATTRIBUTE1_NAME);
      attributeNameValue.setAttributeValueString(RepositoryServiceClientConstants.ATTRIBUTE1_VALUE);
      attributeNameValues.add(attributeNameValue);

      ExtendedAssetInfo extendedAssetInfo = new ExtendedAssetInfo();
      extendedAssetInfo.getAttribute().addAll(attributeNameValues);

      UpdateAssetAttributesRequest updateAssetAttributesRequest = new UpdateAssetAttributesRequest();
      updateAssetAttributesRequest.setAssetKey(assetKey);
      updateAssetAttributesRequest.setExtendedAssetInfo(extendedAssetInfo);
      updateAssetAttributesRequest.setPartialUpdate(true);
      updateAssetAttributesRequest.setReplaceCurrent(true);

      try {
         UpdateAssetAttributesResponse updateAssetAttributesResponse = updateAssetAttributesConsumer.getProxy()
                  .updateAssetAttributes(updateAssetAttributesRequest);
         if (updateAssetAttributesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateUpdateAssetAttributesResponse(updateAssetAttributesResponse, "negativeCase", 0).equalsIgnoreCase(
                  "success")) {
            List<CommonErrorData> errorDatas = updateAssetAttributesResponse.getErrorMessage().getError();

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

   private static String validateUpdateAssetAttributesResponse(
            UpdateAssetAttributesResponse updateAssetAttributesResponse, String criteria, int updatedAttributesCount) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (updateAssetAttributesResponse.getAck().value().equalsIgnoreCase("success")) {
            if (updateAssetAttributesResponse.getAttributeName().size() == updatedAttributesCount) {
               return "success";
            }
         }
         return "failure";
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (updateAssetAttributesResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (updateAssetAttributesResponse.getErrorMessage().getError().size() > 0) {
               return "success";
            }
         }
      }

      return "failure";
   }

}
