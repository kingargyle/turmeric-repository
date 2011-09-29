/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repository.listener.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.ebayopensource.turmeric.repository.v1.services.Artifact;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v1.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.CreateAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.CreateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationship;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.LockAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.LockAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.Relation;
import org.ebayopensource.turmeric.repository.v1.services.RemoveAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.RemoveAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.UnlockAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.UnlockAssetResponse;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.RepositoryServiceClientConstants;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;

public class CommonUtil {

   public static AssetInfo createBasicAsset(String assetType, String captureTemplateName) {

      Properties prop = loadPropertyFile("properties/common.properties");

      String assetName = "testasset";
      String libraryName = prop.getProperty("libraryName", "GovernedAssets");
      String groupName = prop.getProperty("groupName", "GroupName");
      String assetDescription = "Test asset created using junit";
      String versionOne = "1.0.0";
      String appendTimeStamp = new Long(System.currentTimeMillis()).toString();
      assetName = assetName.concat(appendTimeStamp);
      AssetKey responseAssetKey = null;

      CreateAssetRequest createAssetRequest = new CreateAssetRequest();
      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      AssetKey assetKey = new AssetKey();
      Library library = new Library();
      AssetInfo responseAssetInfo = new AssetInfo();
      responseAssetInfo.setBasicAssetInfo(basicAssetInfo);

      library.setLibraryName(libraryName);
      assetKey.setAssetName(assetName);
      assetKey.setLibrary(library);

      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setAssetDescription(assetDescription);
      basicAssetInfo.setAssetLongDescription(assetDescription);
      basicAssetInfo.setAssetName(assetName);
      basicAssetInfo.setAssetType(assetType);
      basicAssetInfo.setVersion(versionOne);
      basicAssetInfo.setGroupName(groupName);// required according to RepositoryService wsdl on createAsset
      createAssetRequest.setBasicAssetInfo(basicAssetInfo);
      createAssetRequest.setCaptureTemplateName(captureTemplateName);

      RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer();

      try {
         CreateAssetResponse createAssetResponse = rsConsumer.getProxy().createAsset(createAssetRequest);
         if (createAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            responseAssetKey = createAssetResponse.getAssetKey();
            responseAssetInfo.getBasicAssetInfo().setAssetKey(responseAssetKey);
         }
      } catch (ServiceException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return responseAssetInfo;

   }

   public static AssetInfo getAssetInfo(AssetKey assetKey) {
      GetAssetInfoRequest getAssetRequest = new GetAssetInfoRequest();
      getAssetRequest.setAssetKey(assetKey);
      RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer();
      GetAssetInfoResponse getAssetInforesponse = rsConsumer.getAssetInfo(getAssetRequest);
      return getAssetInforesponse.getAssetInfo();
   }

   public static AssetKey createCompleteServiceAsset() {

      Properties prop = loadPropertyFile("properties/common.properties");
      AssetKey serviceAssetKey = null;

      String assetName = "testasset";
      String libraryName = prop.getProperty("libraryName", "GovernedAssets");
      String groupName = prop.getProperty("groupName", "GroupName");
      String assetDescription = "Test asset created using junit";
      String versionOne = "1.0.0";
      String appendTimeStamp = new Long(System.currentTimeMillis()).toString();
      assetName = assetName.concat(appendTimeStamp);

      Library library = new Library();
      library.setLibraryName(libraryName);

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetName(assetName);
      assetKey.setLibrary(library);

      BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
      basicAssetInfo.setAssetDescription(assetDescription);
      basicAssetInfo.setAssetKey(assetKey);
      basicAssetInfo.setAssetName(assetName);
      basicAssetInfo.setAssetType("Service");
      basicAssetInfo.setGroupName(groupName);
      basicAssetInfo.setVersion(versionOne);

      ArtifactInfo artifactInfo = new ArtifactInfo();
      Artifact artifact = new Artifact();
      artifact.setArtifactName("assetName.wsdl");
      artifact.setArtifactCategory(RepositoryServiceClientConstants.ARTIFACT_CATEGORY);
      artifact.setArtifactValueType(ArtifactValueType
               .valueOf(RepositoryServiceClientConstants.ARTIFACT_VALUE_TYPE_FILE));
      artifactInfo.setArtifact(artifact);

      InputStream is = ClassLoader.getSystemResourceAsStream("wsdls/SoaSchemaValidateTestServiceV1.wsdl");

      byte[] bytes = null;
      try {
         bytes = IOUtils.toByteArray(is);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      artifactInfo.setArtifactDetail(bytes);
      artifactInfo.setContentType("application/xml");

      List<AttributeNameValue> attributeNameValues = new ArrayList<AttributeNameValue>();
      AttributeNameValue attributeNameValue = new AttributeNameValue();
      attributeNameValue.setAttributeName(RepositoryServiceClientConstants.ATTRIBUTE1_NAME);
      attributeNameValue.setAttributeValueString(RepositoryServiceClientConstants.ATTRIBUTE1_VALUE);
      attributeNameValues.add(attributeNameValue);

      ExtendedAssetInfo extendedAssetInfo = new ExtendedAssetInfo();
      extendedAssetInfo.getAttribute().addAll(attributeNameValues);

      AssetLifeCycleInfo assetLifeCycleInfo = new AssetLifeCycleInfo();
      assetLifeCycleInfo.setApprover("jpnadar");
      assetLifeCycleInfo.setLifeCycleState("Approved");
      assetLifeCycleInfo.setProjectManager("arajmony");

      AssetInfo fdAssetInfo = createBasicAsset("Functional Domain", "Functional Domain Template");

      FlattenedRelationship flattenedRelationship = new FlattenedRelationship();
      flattenedRelationship.setDepth(1);
      flattenedRelationship.setPartial(false);
      flattenedRelationship.setSourceAsset(assetKey);
      Relation relation = new Relation();
      relation.setSourceAsset(assetKey);

      relation.setTargetAsset(fdAssetInfo.getBasicAssetInfo().getAssetKey());
      relation.setAssetRelationship("Predecessor");
      flattenedRelationship.getRelatedAsset().add(relation);

      AssetInfo assetInfo = new AssetInfo();
      assetInfo.setBasicAssetInfo(basicAssetInfo);
      assetInfo.getArtifactInfo().add(artifactInfo);
      assetInfo.setExtendedAssetInfo(extendedAssetInfo);
      assetInfo.setAssetLifeCycleInfo(assetLifeCycleInfo);
      assetInfo.setFlattenedRelationship(flattenedRelationship);

      CreateCompleteAssetRequest createCompleteAssetRequest = new CreateCompleteAssetRequest();
      createCompleteAssetRequest.setAssetInfo(assetInfo);
      createCompleteAssetRequest
               .setCaptureTemplateName(RepositoryServiceClientConstants.VALID_SERVICE_CAPTURE_TEMPLATE);

      RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer();

      try {
         CreateCompleteAssetResponse createCompleteAssetResponse = rsConsumer.getProxy().createCompleteAsset(
                  createCompleteAssetRequest);
         if (createCompleteAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            serviceAssetKey = createCompleteAssetResponse.getAssetKey();
         }
      } catch (ServiceException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return serviceAssetKey;
   }

   public static Properties loadPropertyFile(String filePath) {
      Properties prop = new Properties();
      InputStream in = ClassLoader.getSystemResourceAsStream(filePath);

      try {
         prop.load(in);
      } catch (IOException e) {
         e.printStackTrace();
      }

      return prop;
   }

   public static void removeAsset(AssetKey assetKey) {
      RemoveAssetRequest removeAssetRequest = new RemoveAssetRequest();
      removeAssetRequest.setAssetKey(assetKey);

      RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer();

      try {
         rsConsumer.getProxy().removeAsset(removeAssetRequest);
      } catch (ServiceException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void unLockAsset(AssetKey assetKey) {
      UnlockAssetRequest unlockAssetRequest = new UnlockAssetRequest();
      unlockAssetRequest.setAssetKey(assetKey);

      RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer();

      try {
         rsConsumer.getProxy().unlockAsset(unlockAssetRequest);
      } catch (ServiceException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   public static void lockAsset(AssetKey assetKey) {
      LockAssetRequest lockAssetRequest = new LockAssetRequest();
      lockAssetRequest.setAssetKey(assetKey);

      RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer();

      try {
         rsConsumer.getProxy().lockAsset(lockAssetRequest);
      } catch (ServiceException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   public static void lockAsset(AssetKey assetKey, String userId, String password) {
      LockAssetRequest lockAssetRequest = new LockAssetRequest();
      lockAssetRequest.setAssetKey(assetKey);

      RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer();

      try {
         rsConsumer.getProxy(userId, password).lockAsset(lockAssetRequest);
      } catch (ServiceException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
