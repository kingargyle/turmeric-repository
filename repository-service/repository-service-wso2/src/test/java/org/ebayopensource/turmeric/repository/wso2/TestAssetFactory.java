/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import java.util.UUID;

import org.ebayopensource.turmeric.repository.v2.services.Artifact;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.junit.Before;
import org.junit.Test;
import org.wso2.carbon.registry.core.Registry;
import static org.junit.Assert.*;

public class TestAssetFactory extends Wso2Base {
   BasicAssetInfo basicInfo = null;
   Registry registry = null;

   @Before
   @Override
   public void setUp() throws Exception {
      super.setUp();

      registry = RSProviderUtil.getRegistry();
   }

   @Test
   public void testServiceAsset() {
      basicInfo = new BasicAssetInfo();
      basicInfo.setAssetName("TestService");
      basicInfo.setAssetType("Service");
      basicInfo.setGroupName("TestGroup");
      basicInfo.setVersion("1.0.0");
      AssetFactory factory = new AssetFactory(basicInfo, registry);
      Asset asset = factory.createAsset();
      assertEquals("Wrong asset created.", "Service", asset.getType());
   }

   @Test
   public void testEndpointAsset() {
      basicInfo = new BasicAssetInfo();
      basicInfo.setAssetName("TestService");
      basicInfo.setAssetType("Endpoint");
      basicInfo.setGroupName("TestGroup");
      basicInfo.setNamespace("http://www.example.org");
      basicInfo.setVersion("1.0.0");

      AssetFactory factory = new AssetFactory(basicInfo, registry);
      Asset asset = factory.createAsset();
      assertEquals("Wrong asset created.", "Endpoint", asset.getType());
   }

   @Test
   public void testNullAsset() {
      basicInfo = new BasicAssetInfo();
      basicInfo.setAssetName("TestService");
      basicInfo.setAssetType("WSDL");
      basicInfo.setGroupName("TestGroup");
      basicInfo.setNamespace("http://www.example.org");
      basicInfo.setVersion("1.0.0");
      AssetFactory factory = new AssetFactory(basicInfo, registry);
      Asset asset = factory.createAsset();
      assertNull("Unexpected Asset was created.", asset);
   }

   @Test
   public void testCreateWSDLArtifact() {
      ArtifactInfo artifactInfo = new ArtifactInfo();
      Artifact artifact = new Artifact();
      artifact.setArtifactCategory("WSDL");
      artifact.setArtifactValueType(ArtifactValueType.FILE);
      artifact.setArtifactName("TestWSDL.wsdl");
      artifact.setArtifactDisplayName("TestWSDL");
      artifact.setArtifactIdentifier(UUID.randomUUID().toString());
      artifactInfo.setArtifact(artifact);

      AssetFactory factory = new AssetFactory(artifactInfo, registry);
      Asset asset = factory.createArtifactAsset();
      assertEquals("Wrong artifact asset created", "WSDL", asset.getType());
   }

   @Test
   public void testCreateSchemaArtifact() {
      ArtifactInfo artifactInfo = new ArtifactInfo();
      Artifact artifact = new Artifact();
      artifact.setArtifactCategory("Schema");
      artifact.setArtifactValueType(ArtifactValueType.FILE);
      artifact.setArtifactName("TestXSD.xsd");
      artifact.setArtifactDisplayName("TestSchema");
      artifact.setArtifactIdentifier(UUID.randomUUID().toString());
      artifactInfo.setArtifact(artifact);

      AssetFactory factory = new AssetFactory(artifactInfo, registry);
      Asset asset = factory.createArtifactAsset();
      assertEquals("Wrong artifact asset created", "Schema", asset.getType());
   }

   @Test
   public void testCreateNullArtifactAsset() {
      ArtifactInfo artifactInfo = new ArtifactInfo();
      Artifact artifact = new Artifact();
      artifact.setArtifactCategory("RelaxNG");
      artifact.setArtifactValueType(ArtifactValueType.FILE);
      artifact.setArtifactName("TestXSD.xsd");
      artifact.setArtifactDisplayName("TestSchema");
      artifact.setArtifactIdentifier(UUID.randomUUID().toString());
      artifactInfo.setArtifact(artifact);

      AssetFactory factory = new AssetFactory(artifactInfo, registry);
      Asset asset = factory.createArtifactAsset();
      assertEquals("Wrong artifact asset created", "NULL", asset.getType());
   }

}
