/*******************************************************************************
 * Copyright (c) 2006-2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

/**
 * @author mgorovoy
 * 
 */
public class UpdateAssetAttributesTest extends Wso2Base {
   // First resource path must be the primary resource created by the test
   // in order for the assumption checks to work correctly.

   private static final String assetName = "UpdateAssetAttributesTest";
   private static final String assetDesc = "UpdateAssetAttributesTest description";
   private static final String namespace = "http://www.ebay.com/marketplace/services";
   private static final String stringProperty = "test value";
   private static final Long longProperty = new Long(100000l);
   private static final Boolean booleanProperty = Boolean.FALSE;

   private CreateCompleteAssetResponse createAsset() throws Exception {
      AssetKey key = new AssetKey();
      key.setAssetName(assetName);
      key.setType("Service");
      key.setVersion("1.0.0");

      BasicAssetInfo basicInfo = new BasicAssetInfo();
      basicInfo.setAssetKey(key);
      basicInfo.setAssetName(assetName + Math.random());
      basicInfo.setAssetDescription(assetDesc);
      basicInfo.setAssetType("Service");
      basicInfo.setVersion("1.0.0");

      ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
      List<AttributeNameValue> attrs = extendedInfo.getAttribute();
      attrs.add(RSProviderUtil.newAttribute("namespace", namespace));
      attrs.add(RSProviderUtil.newAttribute("longProperty", longProperty.longValue()));
      attrs.add(RSProviderUtil.newAttribute("booleanProperty", booleanProperty.booleanValue()));

      AssetLifeCycleInfo lifeCycleInfo = new AssetLifeCycleInfo();
      lifeCycleInfo.setDomainOwner("John Doe");
      lifeCycleInfo.setDomainType("Technical Owner");

      AssetInfo assetInfo = new AssetInfo();
      assetInfo.setBasicAssetInfo(basicInfo);
      assetInfo.setExtendedAssetInfo(extendedInfo);
      assetInfo.setAssetLifeCycleInfo(lifeCycleInfo);

      CreateCompleteAssetRequest request = new CreateCompleteAssetRequest();
      request.setAssetInfo(assetInfo);

      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      return provider.createCompleteAsset(request);
   }

   private UpdateAssetAttributesResponse replaceAsset(AssetKey assetKey) throws Exception {
      AssetKey key = assetKey;
      key.setType("Service");
      key.setVersion("1.0.0");
      key.setAssetName(assetName);

      LockAssetRequest lockReq = new LockAssetRequest();
      lockReq.setAssetKey(key);

      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      LockAssetResponse lockRes = provider.lockAsset(lockReq);
      assertEquals("Lock Asset Error: " + getErrorMessage(lockRes), AckValue.SUCCESS, lockRes.getAck());

      BasicAssetInfo basicInfo = new BasicAssetInfo();
      basicInfo.setAssetKey(key);
      basicInfo.setAssetName(assetName);
      basicInfo.setAssetDescription(assetDesc);
      basicInfo.setAssetType("Service");

      ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
      List<AttributeNameValue> attrs = extendedInfo.getAttribute();
      attrs.add(RSProviderUtil.newAttribute("namespace", namespace));
      attrs.add(RSProviderUtil.newAttribute("stringProperty", stringProperty + " updated"));
      attrs.add(RSProviderUtil.newAttribute("longProperty", longProperty.longValue() * 10));
      attrs.add(RSProviderUtil.newAttribute("booleanProperty", !booleanProperty.booleanValue()));

      AssetLifeCycleInfo lifeCycleInfo = new AssetLifeCycleInfo();
      lifeCycleInfo.setDomainOwner("John Doe Junior");
      lifeCycleInfo.setDomainType("Business Owner");

      AssetInfoForUpdate assetInfo = new AssetInfoForUpdate();
      assetInfo.setBasicAssetInfo(basicInfo);
      assetInfo.setExtendedAssetInfo(extendedInfo);
      assetInfo.setAssetLifeCycleInfo(lifeCycleInfo);

      UpdateAssetAttributesRequest request = new UpdateAssetAttributesRequest();
      request.setPartialUpdate(false);
      request.setAssetKey(key);
      request.setExtendedAssetInfo(extendedInfo);
      request.setReplaceCurrent(true);

      return provider.updateAssetAttributes(request);
   }

   private UpdateAssetAttributesResponse mergeAsset(String assetId) throws Exception {
      AssetKey key = new AssetKey();
      key.setAssetId(assetId);
      key.setAssetName(assetName);
      key.setType("Service");
      key.setVersion("1.0.0");

      ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
      List<AttributeNameValue> attrs = extendedInfo.getAttribute();
      attrs.add(RSProviderUtil.newAttribute("namespace", namespace));
      attrs.add(RSProviderUtil.newAttribute("stringProperty", stringProperty + " updated"));
      attrs.add(RSProviderUtil.newAttribute("longProperty", longProperty.longValue() * 10));
      attrs.add(RSProviderUtil.newAttribute("booleanProperty", !booleanProperty.booleanValue()));

      UpdateAssetAttributesRequest request = new UpdateAssetAttributesRequest();
      request.setPartialUpdate(false);
      request.setReplaceCurrent(false);
      request.setExtendedAssetInfo(extendedInfo);
      request.setAssetKey(key);

      RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
      return provider.updateAssetAttributes(request);
   }

   /**
    * Helper method to validate the asset basic and extended info
    * 
    * @return
    */
   private AssetInfo validateAsset(String assetId) {
      // now, i search the service to get all its related objects
      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();

      GetAssetInfoRequest request = new GetAssetInfoRequest();
      AssetKey key = new AssetKey();
      key.setAssetId(assetId);
      request.setAssetKey(key);

      GetAssetInfoResponse assetInfoResponse = provider.getAssetInfo(request);
      String errorMsg = getErrorMessage(assetInfoResponse);
      assertEquals("Unexpected error: " + errorMsg, AckValue.SUCCESS, assetInfoResponse.getAck());
      assertEquals(null, assetInfoResponse.getErrorMessage());

      // //now, validating the basic info of the updated asset
      BasicAssetInfo assetInfo = assetInfoResponse.getAssetInfo().getBasicAssetInfo();
      assertEquals("Service", assetInfo.getAssetType());
      assertEquals(assetDesc, assetInfo.getAssetDescription());
      // now, validating extended info
      ExtendedAssetInfo extededAssetInfo = assetInfoResponse.getAssetInfo().getExtendedAssetInfo();
      List<AttributeNameValue> attrValues = extededAssetInfo.getAttribute();
      for (AttributeNameValue attributeNameValue : attrValues) {
         if ("stringProperty".equals(attributeNameValue.getAttributeName())) {
            assertEquals(stringProperty + " updated", attributeNameValue.getAttributeValueString());
         } else if ("longProperty".equals(attributeNameValue.getAttributeName())) {
            assertEquals(Long.toString(longProperty * 10), attributeNameValue.getAttributeValueString());
         } else if ("booleanProperty".equals(attributeNameValue.getAttributeName())) {
            assertEquals(Boolean.toString(!booleanProperty), attributeNameValue.getAttributeValueString());
         }
      }

      return assetInfoResponse.getAssetInfo();
   }

   @Test
   public void updateReplaceTest() throws Exception {

      // first, create the complete asset
      CreateCompleteAssetResponse response = createAsset();
      assertEquals("Create error: " + getErrorMessage(response), AckValue.SUCCESS, response.getAck());
      assertEquals(null, response.getErrorMessage());

      // then, update the complete asset, replacing all its related objects
      UpdateAssetAttributesResponse responseUpdate = replaceAsset(response.getAssetKey());

      assertEquals("Unexpected error: " + getErrorMessage(responseUpdate), AckValue.SUCCESS, responseUpdate.getAck());
      assertEquals(null, responseUpdate.getErrorMessage());

      validateAsset(response.getAssetKey().getAssetId());
   }

   @Test
   public void mergeCompleteAssetTest() throws Exception {

      // first, create the complete asset
      CreateCompleteAssetResponse response = createAsset();

      // then, update the complete asset, replacing all its related objects
      UpdateAssetAttributesResponse responseUpdate = mergeAsset(response.getAssetKey().getAssetId());
      assertEquals("Unexpected Error:" + getErrorMessage(responseUpdate), AckValue.SUCCESS, responseUpdate.getAck());
      assertEquals(null, responseUpdate.getErrorMessage());

      validateAsset(response.getAssetKey().getAssetId());
   }
}
