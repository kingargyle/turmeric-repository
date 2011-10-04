/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.wso2.carbon.governance.api.services.ServiceManager;
import org.wso2.carbon.governance.api.services.dataobjects.Service;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.LockAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.LockAssetResponse;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

/**
 * @author dcarver
 * 
 */
public class LockAssetTest extends Wso2Base {
   // First resource path must be the primary resource created by the test
   // in order for the assumption checks to work correctly.

   private static final String assetName = "UpdateAssetAttributesTest";
   private static final String assetDesc = "UpdateAssetAttributesTest description";
   private static final String libraryName = "http://www.ebay.com/marketplace/services";
   private static final Long longProperty = new Long(100000l);
   private static final Boolean booleanProperty = Boolean.FALSE;

   @Before
   @Override
   public void setUp() throws Exception {
      super.setUp();

   }

   private CreateCompleteAssetResponse createAsset() throws Exception {
      AssetKey key = new AssetKey();
      key.setAssetName(assetName);
      key.setType("Service");
      key.setVersion("1.0.0");

      BasicAssetInfo basicInfo = new BasicAssetInfo();
      basicInfo.setAssetKey(key);
      basicInfo.setAssetName(assetName);
      basicInfo.setAssetDescription(assetDesc);
      basicInfo.setAssetType("Service");
      basicInfo.setVersion("1.0.0");
      basicInfo.setNamespace(libraryName);

      ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
      List<AttributeNameValue> attrs = extendedInfo.getAttribute();
      attrs.add(RSProviderUtil.newAttribute("namespace", libraryName));
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

   private LockAssetResponse lockAsset(AssetKey assetKey) throws Exception {
      AssetKey key = assetKey;
      key.setType("Service");

      LockAssetRequest lockReq = new LockAssetRequest();
      lockReq.setAssetKey(key);

      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      LockAssetResponse lockRes = provider.lockAsset(lockReq);
      return lockRes;
   }

   @Test
   public void lockAssetTest() throws Exception {

      // first, create the complete asset
      CreateCompleteAssetResponse response = createAsset();
      assertEquals("Error: " + getErrorMessage(response), AckValue.SUCCESS, response.getAck());
      assertEquals(null, response.getErrorMessage());

      // then, update the complete asset, replacing all its related objects
      LockAssetResponse responseLock = lockAsset(response.getAssetKey());
      assertEquals("Lock Error: " + getErrorMessage(responseLock), AckValue.SUCCESS, responseLock.getAck());
      assertEquals(null, responseLock.getErrorMessage());

      ServiceManager manager = new ServiceManager(RSProviderUtil.getRegistry());
      String assetId = responseLock.getAssetInfo().getBasicAssetInfo().getAssetKey().getAssetId();
      Service service = manager.getService(assetId);
      String lock = service.getAttribute(AssetConstants.TURMERIC_LOCK);
      assertEquals("Missing expected lock value", "true", lock);
   }

}
