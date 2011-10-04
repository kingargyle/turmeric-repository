/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;

import org.ebayopensource.turmeric.common.v1.types.BaseResponse;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.assets.ServiceAsset;
import org.ebayopensource.turmeric.repository.wso2.utils.AbstractCarbonIntegrationTestCase;
import org.junit.Before;

public class Wso2Base extends AbstractCarbonIntegrationTestCase {

   static {
      System.setProperty("javax.net.ssl.trustStore",
               new File("src/main/resources/client-truststore.jks").getAbsolutePath());
      System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
      System.setProperty("javax.net.ssl.trustStoreType", "JKS");

      System.setProperty("org.ebayopensource.turmeric.repository.wso2.url", "https://127.0.0.1:9443/registry");
      System.setProperty("org.ebayopensource.turmeric.repository.wso2.username", "admin");
      System.setProperty("org.ebayopensource.turmeric.repository.wso2.password", "admin");
   }

   protected static String preloaded_asset_id = null;

   public static final String BASE = "/_system/governance/trunk";

   @Override
   @Before
   public void setUp() throws Exception {
      super.setUp();

      createLifecycleInfoInWso2();

   }

   /**
    * Utility method used to create a necessary asset in the wso2 registry, before a given test runs
    */
   public static void createRequiredAssetsInWso2() throws Exception {
      createRequiredServiceAssetInWso2();
      // createRequiredNonServiceAssetInWso2();
   }

   /**
    * Utility method used to create a necessary asset in the wso2 registry, before a given test runs
    */
   public static void createRequiredServiceAssetInWso2() throws Exception {
      Registry registry = RSProviderUtil.getRegistry();
      BasicAssetInfo basicInfo = new BasicAssetInfo();
      AssetKey assetKey = new AssetKey();
      assetKey.setType("Service");
      assetKey.setAssetName("RepositoryMetadataService");
      assetKey.setVersion("2.0.0");

      basicInfo.setAssetKey(assetKey);
      basicInfo.setNamespace("http://www.ebay.com/marketplace/services");
      basicInfo.setAssetName("RepositoryMetadataService");
      basicInfo.setAssetDescription("The service description");
      basicInfo.setVersion("2.0.0");
      basicInfo.setAssetType("Service");

      ServiceAsset serviceAsset = new ServiceAsset(basicInfo, registry);
      serviceAsset.createAsset();
      serviceAsset.addAsset();
      preloaded_asset_id = serviceAsset.getId();

      // String wsdlArtifactId =
      // "/_system/governance/trunk/wsdls/http/com/ebay/www/marketplace/services/RepositoryMetadataService.wsdl";
      //
      //
      //
      // RemoteRegistry _registry = new RemoteRegistry(
      // new URL(
      // System.getProperty("org.ebayopensource.turmeric.repository.wso2.url")),
      // System.getProperty("org.ebayopensource.turmeric.repository.wso2.username"),
      // System.getProperty("org.ebayopensource.turmeric.repository.wso2.password"));

      // if (!_registry.resourceExists(assetKey)) {
      // Resource asset = RSProviderUtil.newAssetResource();
      // asset.setMediaType("application/vnd.wso2-service+xml");
      // Document serviceMetadataXmlDoc = createXmlDoc(assetName, description, libraryName);
      // serviceMetadata = getXmlString(serviceMetadataXmlDoc);
      // if (serviceMetadata.length() > 0) {
      // InputStream contentStream = new ByteArrayInputStream(
      // serviceMetadata.getBytes("UTF-8"));
      // asset.setContentStream(contentStream);
      // }

      // registry instance
      // ok, now, let's go to the wsdl artifact.
      // Resource wsdlArtifact = RSProviderUtil.newAssetResource();
      // wsdlArtifact.setMediaType("application/wsdl+xml");
      // InputStream wsdlIstream = Wso2Base.class.getClassLoader().getResourceAsStream(
      // "RepositoryMetadataService.wsdl");
      // BufferedReader bufReader = new BufferedReader(new InputStreamReader(wsdlIstream));
      // StringBuilder wsdlcontent = new StringBuilder();
      // String wsdlcontentLine = null;
      // while ((wsdlcontentLine = bufReader.readLine()) != null) {
      // wsdlcontent.append(wsdlcontentLine);
      // }
      // bufReader.close();
      // if (wsdlcontent.length() > 0) {
      // InputStream contentStream = new ByteArrayInputStream(wsdlcontent.toString()
      // .getBytes("UTF-8"));
      // wsdlArtifact.setContentStream(contentStream);
      // }
      // _registry.put(wsdlArtifactId, wsdlArtifact);
      // asset.setProperty(RSProviderUtil.__artifactVersionPropName, "1.0.0");
      // _registry.put(assetKey, asset);// i put the RepositoryMetadataService in the wso2

      // }
   }

   // public static void createRequiredNonServiceAssetInWso2() {
   // try {
   // RemoteRegistry _registry = new RemoteRegistry(
   // new URL(
   // System.getProperty("org.ebayopensource.turmeric.repository.wso2.url")),
   // System.getProperty("org.ebayopensource.turmeric.repository.wso2.username"),
   // System.getProperty("org.ebayopensource.turmeric.repository.wso2.password"));
   // String assetName = "NonServiceAsset";
   // String assetKey = "/_system/governance/trunk/testassets/libraryname/" + assetName;
   // String description = "The asset description";
   // if (!_registry.resourceExists(assetKey)) {
   // Resource asset = RSProviderUtil.newAssetResource();
   // asset.setDescription(description);
   // asset.setProperty(RSProviderUtil.__artifactVersionPropName, "1.0.0");
   // _registry.put(assetKey, asset);
   // }
   // }
   // catch (Exception e) {
   // e.printStackTrace();
   // }
   // }

   /**
    * Utility method used to create a necessary asset in the wso2 registry, before a given test runs
    */
   public static void createLifecycleInfoInWso2() {
      try {
         RemoteRegistry _registry = new RemoteRegistry(new URL(
                  System.getProperty("org.ebayopensource.turmeric.repository.wso2.url")),
                  System.getProperty("org.ebayopensource.turmeric.repository.wso2.username"),
                  System.getProperty("org.ebayopensource.turmeric.repository.wso2.password"));
         String assetName = "TurmericLifeCycle";
         String assetKey = "/_system/config/repository/components/org.wso2.carbon.governance/lifecycles/" + assetName;

         if (!_registry.resourceExists(assetKey)) {
            Resource asset = _registry.newResource();
            asset.setMediaType("application/octet-stream");
            // need to read the lifecyle content from a file
            InputStream lifecycleIstrm = Wso2Base.class.getClassLoader().getResourceAsStream(
                     "META-INF/TurmericLifecycle.xml");
            BufferedReader in = new BufferedReader(new InputStreamReader(lifecycleIstrm));
            String str;
            StringBuilder strContent = new StringBuilder();
            while ((str = in.readLine()) != null) {
               strContent.append(str);
            }
            in.close();
            if (strContent.length() > 0) {
               InputStream contentStream = new ByteArrayInputStream(strContent.toString().getBytes("UTF-8"));
               asset.setContentStream(contentStream);
            }
            _registry.put(assetKey, asset);// i put the lifecycle in the wso2 registry instance

         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void validateBasicAssetInfo(BasicAssetInfo basicAssetInfo) {
      boolean result = true;
      if (basicAssetInfo.getAssetType() == null || basicAssetInfo.getAssetName() == null
               || basicAssetInfo.getAssetDescription() == null || basicAssetInfo.getVersion() == null) {
         result = false;
      }

      assertTrue("Invalid basicAssetInfo", result);
   }

   private static void checkValidArtifactInfo(List<ArtifactInfo> artifactInfos) {
      for (ArtifactInfo artifactInfo : artifactInfos) {
         Artifact artifact = artifactInfo.getArtifact();

         validateArtifact(artifact);
         assertTrue("Null artifact detail", artifactInfo.getArtifactDetail() != null);
      }
   }

   public static void validateArtifact(Artifact artifact) {
      if (artifact == null || artifact.getArtifactCategory() == null || artifact.getArtifactIdentifier() == null
               || artifact.getArtifactName() == null) {
         fail("Invalid artifact");
      }

   }

   public void validateAssetInfo(AssetInfo assetInfo) {
      List<ArtifactInfo> artifactInfos = assetInfo.getArtifactInfo();
      if (artifactInfos != null) {
         checkValidArtifactInfo(artifactInfos);
      }
      if (assetInfo.getAssetLifeCycleInfo() != null) {
         validateAssetLifeCycleInfo(assetInfo.getAssetLifeCycleInfo());
      }

      validateBasicAssetInfo(assetInfo.getBasicAssetInfo());

      if (assetInfo.getExtendedAssetInfo() != null) {
         validateExtendedAssetInfo(assetInfo.getExtendedAssetInfo());
      }
      if (assetInfo.getFlattenedRelationship() != null) {
         validateFlattenedRelationship(assetInfo.getFlattenedRelationship());
      }

      return;
   }

   public void validateExtendedAssetInfo(ExtendedAssetInfo extendedAssetInfo) {
      List<AttributeNameValue> attributeNameValues = extendedAssetInfo.getAttribute();

      if (attributeNameValues != null) {
         for (AttributeNameValue attributeNameValue : attributeNameValues) {
            if (attributeNameValue.getAttributeName() == null) {
               fail("Null attributeName in extendedAssetInfo");
            }
            if (attributeNameValue.getAttributeValueLong() == null
                     && attributeNameValue.getAttributeValueString() == null
                     && attributeNameValue.isAttributeValueBoolean() == null) {
               fail("Null attributeValues");
            }
         }
      }
   }

   public void validateFlattenedRelationship(FlattenedRelationship flattenedRelationship) {
      validateAssetKey(flattenedRelationship.getSourceAsset());
      List<Relation> relations = flattenedRelationship.getRelatedAsset();
      if (relations != null) {
         validateRelations(relations);
      }

      return;
   }

   public void validateAssetKey(AssetKey assetKey) {
      if (assetKey != null) {
         if (assetKey.getAssetId() == null || assetKey.getAssetName() == null) {
            fail("invalid assetKey");
         }
         return;
      }
      return;
   }

   public void validateRelations(List<Relation> relations) {
      for (Relation relation : relations) {
         if (relation.getAssetRelationship() == null) {
            fail("Null assetRelationship");
         }
         validateAssetKey(relation.getSourceAsset());
         validateAssetKey(relation.getTargetAsset());
      }

      return;
   }

   public void validateAssetLifeCycleInfo(AssetLifeCycleInfo assetLifeCycleInfo) {
      if (assetLifeCycleInfo.getLifeCycleState() == null /* || assetLifeCycleInfo.getArchitect() == null */) {
         fail("Null lifecycle state");
      }

      return;
   }

   @Override
   protected void copyArtifacts() throws IOException {

   }

   /**
    * Given a path to a file, load it's content into a byte array.
    * 
    * @param pathname
    *           the fullpath to the file.
    * @return a byte array with the contents of the file
    * @throws Exception
    *            an exception if any occurs
    */
   protected byte[] loadFile(String pathname) throws Exception {
      File file = new File(pathname);
      int size = (int) file.length();
      byte[] content = new byte[size];

      FileInputStream fin = new FileInputStream(file);
      fin.read(content, 0, size);
      fin.close();
      return content;
   }

   protected <T extends BaseResponse> String getErrorMessage(T response) {
      if (response.getErrorMessage() != null && response.getErrorMessage().getError().size() > 0) {
         return response.getErrorMessage().getError().get(0).getMessage();
      }
      return null;
   }
}
