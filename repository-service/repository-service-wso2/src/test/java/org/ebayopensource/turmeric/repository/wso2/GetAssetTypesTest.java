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

import org.junit.Before;
import org.junit.Test;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetTypesRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetTypesResponse;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

public class GetAssetTypesTest {

   GetAssetTypesRequest request = null;
   RepositoryServiceProvider provider = null;

   @Before
   public void setUp() throws Exception {
      request = new GetAssetTypesRequest();
      provider = new RepositoryServiceProviderImpl();

   }

   @Test
   public void getAssetTypes() throws Exception {
      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      GetAssetTypesResponse response = provider.getAssetTypes(request);
      assertEquals(AckValue.SUCCESS, response.getAck());
      assertEquals(null, response.getErrorMessage());
   }

   @Test
   public void getAssetTypesNotEmpty() throws Exception {
      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      GetAssetTypesResponse response = provider.getAssetTypes(request);
      assertFalse(response.getAssetType().isEmpty());
   }

   @Test
   public void getAssetTypesHasService() throws Exception {
      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      GetAssetTypesResponse response = provider.getAssetTypes(request);
      assertTrue(response.getAssetType().contains("Service"));
   }

   @Test
   public void getAssetTypesHasEndpoint() throws Exception {
      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      GetAssetTypesResponse response = provider.getAssetTypes(request);
      assertTrue(response.getAssetType().contains("Endpoint"));
   }

   @Test
   public void getAssetTypesHasWSDL() throws Exception {
      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      GetAssetTypesResponse response = provider.getAssetTypes(request);
      assertTrue(response.getAssetType().contains("WSDL"));
   }

   @Test
   public void getAssetTypesHasSchema() {
      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      GetAssetTypesResponse response = provider.getAssetTypes(request);
      assertTrue(response.getAssetType().contains("Schema"));
   }
}
