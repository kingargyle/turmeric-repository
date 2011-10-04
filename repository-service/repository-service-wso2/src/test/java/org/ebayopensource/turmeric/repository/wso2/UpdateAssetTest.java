/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetResponse;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

/**
 * @author mgorovoy
 * 
 */
public class UpdateAssetTest extends Wso2Base {
   // First resource path must be the primary resource created by the test
   // in order for the assumption checks to work correctly.

   private static final String assetName = "UpdateAssetTest";
   private static final String assetDesc = "UpdateAssetTest description";
   private String assetId = null;
   private BasicAssetInfo basicInfo = null;

   @Override
   @Before
   public void setUp() throws Exception {
      super.setUp();
      AssetKey key = new AssetKey();
      key.setAssetName(assetName);
      key.setType("Service");
      key.setVersion("1.0.0");

      basicInfo = new BasicAssetInfo();
      basicInfo.setAssetKey(key);
      basicInfo.setAssetName(assetName);
      basicInfo.setAssetDescription(assetDesc);
      basicInfo.setAssetType("Service");
      basicInfo.setVersion("1.0.0");
      basicInfo.setNamespace("http://www.example.org");

      assetId = createAsset(basicInfo);
   };

   @After
   public void cleanUp() throws Exception {
      GovernanceUtils.removeArtifact(RSProviderUtil.getRegistry(), assetId);
   }

   private String createAsset(BasicAssetInfo basicInfo) {
      CreateAssetRequest request = new CreateAssetRequest();
      request.setBasicAssetInfo(basicInfo);

      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      CreateAssetResponse response = provider.createAsset(request);

      assertEquals("error creating asset" + getErrorMessage(response), AckValue.SUCCESS, response.getAck());
      assertEquals(null, response.getErrorMessage());
      return response.getAssetKey().getAssetId();
   }

   public void updateAsset(String newName, String newDesc) throws Exception {
      basicInfo.getAssetKey().setAssetId(assetId);

      basicInfo.setAssetName(newName);
      basicInfo.setAssetDescription(newDesc);

      UpdateAssetRequest request = new UpdateAssetRequest();
      request.setBasicAssetInfo(basicInfo);

      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      UpdateAssetResponse response = provider.updateAsset(request);

      assertEquals("error updating asset: " + getErrorMessage(response), AckValue.SUCCESS, response.getAck());
      assertEquals(null, response.getErrorMessage());
   }

   @Test
   public void updateTest() throws Exception {
      updateAsset(assetName, "Updated " + assetDesc);
      GovernanceArtifact artifact = GovernanceUtils.retrieveGovernanceArtifactById(RSProviderUtil.getRegistry(),
               assetId);
      assertEquals("Updated " + assetDesc, artifact.getAttribute(AssetConstants.TURMERIC_DESCRIPTION));
   }

   @Test
   public void renameTest() throws Exception {
      updateAsset("Updated" + assetName, assetDesc);
      GovernanceArtifact artifact = GovernanceUtils.retrieveGovernanceArtifactById(RSProviderUtil.getRegistry(),
               assetId);
      assertEquals("Updated" + assetName, artifact.getAttribute(AssetConstants.TURMERIC_NAME));
   }

}
