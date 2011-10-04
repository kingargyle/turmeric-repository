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

import org.junit.Before;
import org.junit.Test;
import org.wso2.carbon.governance.api.services.ServiceManager;
import org.wso2.carbon.governance.api.services.dataobjects.Service;
import org.wso2.carbon.registry.core.Registry;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.wso2.filters.FindServiceByNameVersionFilter;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

public class GetAssetInfoTest extends Wso2Base {
   @Override
   @Before
   public void setUp() throws Exception {
      super.setUp();
      try {
         createRequiredAssetsInWso2();
      } catch (Exception ex) {
         fail("failed creating neccesary assets in wso2 registry");
      }
   }

   @Test
   public void getAssetByAssetNameVersion() throws Exception {
      AssetKey key = new AssetKey();
      key.setAssetName("RepositoryMetadataService");
      key.setType("Service");
      key.setVersion("2.0.0");

      GetAssetInfoRequest request = new GetAssetInfoRequest();
      request.setAssetKey(key);
      request.setAssetType("Service");

      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      GetAssetInfoResponse response = provider.getAssetInfo(request);

      assertEquals("Error: " + getErrorMessage(response), AckValue.SUCCESS, response.getAck());
      assertEquals(null, response.getErrorMessage());
   }

   private String findId() throws Exception {
      Registry registry = RSProviderUtil.getRegistry();
      ServiceManager manager = new ServiceManager(registry);
      BasicAssetInfo bi = new BasicAssetInfo();
      AssetKey key = new AssetKey();
      key.setAssetName("RepositoryMetadataService");
      key.setType("Service");
      key.setVersion("2.0.0");

      bi.setAssetKey(key);
      bi.setVersion(key.getVersion());
      bi.setAssetName(key.getAssetName());
      bi.setAssetType(key.getType());
      Service assets[] = manager.findServices(new FindServiceByNameVersionFilter(bi));

      if (assets.length == 0) {
         fail("Unable to locate asset, make sure it exists before the test runs.");
      }
      return assets[0].getId();
   }

   @Test
   public void getAssetByAssetId() throws Exception {

      AssetKey key = new AssetKey();
      key.setAssetId(findId());
      key.setType("Service");
      GetAssetInfoRequest GetAssetInfoRequest = new GetAssetInfoRequest();
      GetAssetInfoRequest.setAssetKey(key);

      GetAssetInfoRequest request = new GetAssetInfoRequest();
      request.setAssetKey(key);
      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      GetAssetInfoResponse response = provider.getAssetInfo(request);

      assertEquals("Error:  " + getErrorMessage(response), AckValue.SUCCESS, response.getAck());
      assertEquals(null, response.getErrorMessage());
   }
}
