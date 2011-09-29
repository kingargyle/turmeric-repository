/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.assets;

import static org.junit.Assert.*;

import org.junit.Test;
import org.wso2.carbon.governance.api.util.GovernanceConstants;
import org.wso2.carbon.registry.core.Registry;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.Wso2Base;

public class SchemaAssetTest extends Wso2Base {

   private static final String ASSETNAME = "RepositoryServiceProviderConfig";

   @Test
   public void testCreateWSDL() throws Exception {
      Registry wso2Registry = RSProviderUtil.getRegistry();
      Artifact schema = new Artifact();
      schema.setArtifactName(ASSETNAME + ".xsd");
      schema.setArtifactCategory(AssetConstants.TURMERIC_TYPE);
      schema.setArtifactValueType(ArtifactValueType.FILE);
      schema.setTargetNamespace("http://www.example.org/" + ASSETNAME);

      ArtifactInfo schemaInfo = new ArtifactInfo();
      schemaInfo.setArtifact(schema);
      schemaInfo.setArtifactDetail(loadFile("src/main/resources/META-INF/schema/RepositoryServiceProviderConfig.xsd"));
      schemaInfo.setContentType(GovernanceConstants.SCHEMA_MEDIA_TYPE);

      SchemaAsset schemaAsset = new SchemaAsset(schemaInfo, wso2Registry);
      assertTrue("Schema was not created.", schemaAsset.createAsset());
      assertTrue("Schema was not added.", schemaAsset.addAsset());
      assertTrue("Schema doesn't exist.", schemaAsset.exists());
   }
}
