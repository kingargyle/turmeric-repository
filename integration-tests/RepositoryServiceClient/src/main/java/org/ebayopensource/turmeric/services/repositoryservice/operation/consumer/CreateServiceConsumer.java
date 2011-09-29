/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v1.services.Artifact;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v1.services.BasicServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.CreateServiceRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateServiceResponse;
import org.ebayopensource.turmeric.repository.v1.services.ExtendedServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationship;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.Relation;
import org.ebayopensource.turmeric.repository.v1.services.ServiceDesignTimeInfo;
import org.ebayopensource.turmeric.repository.v1.services.ServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.ServiceRunTimeInfo;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class CreateServiceConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV1 m_proxy = null;;

   /*
    * public static void main(String[] args) { Map<String, String> results = new HashMap<String, String>();
    * 
    * results.put("testCreateService_withOptionalInputParams", testCreateService_validInput("withOptionalInputParams"));
    * //results.put("testCreateService_withoutOptionalInputParams",
    * testCreateService_validInput("withoutOptionalInputParams")); results.put("testCreateService_noBasicServiceInfo",
    * testCreateService_invalidInput("noBasicServiceInfo")); results.put("testCreateService_invalidServiceVersion",
    * testCreateService_invalidInput("invalidServiceVersion")); results.put("testCreateService_duplicateService",
    * testCreateService_invalidInput("duplicateService"));
    * 
    * 
    * System.out.println("\ncreateService Summary"); Set<String> methods = results.keySet(); for (String method :
    * methods) { System.out.format("%-60s: %s\n", method, results.get(method)); } System.out.println(); }
    * 
    * public static Map<String, String> testCreateService() { Map<String, String> results = new HashMap<String,
    * String>();
    * 
    * results.put("testCreateService_withOptionalInputParams", testCreateService_validInput("withOptionalInputParams"));
    * results.put("testCreateService_withoutOptionalInputParams",
    * testCreateService_validInput("withoutOptionalInputParams")); results.put("testCreateService_noBasicServiceInfo",
    * testCreateService_invalidInput("noBasicServiceInfo")); results.put("testCreateService_invalidServiceVersion",
    * testCreateService_invalidInput("invalidServiceVersion")); results.put("testCreateService_duplicateService",
    * testCreateService_invalidInput("duplicateService"));
    * 
    * return results; }
    */
   public static String testCreateService_validInput(String variant, AssetInfo targetAssetInfo1,
            AssetInfo targetAssetInfo2) {
      CreateServiceConsumer createServiceConsumer = new CreateServiceConsumer();
      CreateServiceRequest createServiceRequest = new CreateServiceRequest();
      ServiceInfo serviceInfo = new ServiceInfo();
      BasicServiceInfo basicServiceInfo = new BasicServiceInfo();
      java.util.Date date = new java.util.Date();
      long currentTime = date.getTime();

      basicServiceInfo.setServiceDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION + currentTime);
      basicServiceInfo.setServiceLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION + currentTime);
      basicServiceInfo.setServiceNamespace(RepositoryServiceClientConstants.ASSET_NAMESPACE);
      basicServiceInfo.setServiceName(RepositoryServiceClientConstants.ASSET_NAME + currentTime);
      basicServiceInfo.setServiceVersion("1.1.0");
      basicServiceInfo.setGroupName("Advertising");
      System.out.println("Service Name : " + basicServiceInfo.getServiceName());
      AssetKey assetKey = new AssetKey();
      Library library = new Library();
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
      assetKey.setLibrary(library);
      basicServiceInfo.setAssetKey(assetKey);
      serviceInfo.setBasicServiceInfo(basicServiceInfo);

      AssetLifeCycleInfo assetLifeCycleInfo = new AssetLifeCycleInfo();
      assetLifeCycleInfo.setApprover("csubhash");
      assetLifeCycleInfo.setArchitect("csubhash");
      assetLifeCycleInfo.setDomainOwner("csubhash");
      assetLifeCycleInfo.setDomainType("API");
      assetLifeCycleInfo.setLifeCycleState("InReview");
      assetLifeCycleInfo.setNextAction("submit");
      assetLifeCycleInfo.setOpsArchitect("csubhash");
      assetLifeCycleInfo.setProductManager("csubhash");
      assetLifeCycleInfo.setProjectManager("csubhash");
      assetLifeCycleInfo.setServiceArchitect("csubhash");
      assetLifeCycleInfo.setTraceTicket("TOICJKSDH");
      assetLifeCycleInfo.setTrackerId("TRACKED ID");
      serviceInfo.setAssetLifeCycleInfo(assetLifeCycleInfo);

      ExtendedServiceInfo extendedServiceInfo = new ExtendedServiceInfo();
      ServiceDesignTimeInfo serviceDesignTimeInfo = new ServiceDesignTimeInfo();
      serviceDesignTimeInfo.setImplementationClass("Com.implementation");
      serviceDesignTimeInfo.setInterfaceClass("com.interface");
      serviceDesignTimeInfo.setIsSecured(true);
      serviceDesignTimeInfo.setPendingVersion(null);
      serviceDesignTimeInfo.setPublishingStatus("Private");
      serviceDesignTimeInfo.setServiceDomain("API");
      serviceDesignTimeInfo.setServiceLayer("Business Service");
      serviceDesignTimeInfo.setServiceType("Production");
      extendedServiceInfo.setServiceDesignTimeInfo(serviceDesignTimeInfo);
      ServiceRunTimeInfo serviceRunTimeInfo = new ServiceRunTimeInfo();
      serviceRunTimeInfo.setDeployedPool("Deployed pool");
      serviceRunTimeInfo.setProductionURL("http://www.ebay.co.in");
      serviceRunTimeInfo.setServiceState(true);
      serviceRunTimeInfo.setServiceStatus("Up");
      serviceRunTimeInfo.setStagingURL("ebay.com");
      extendedServiceInfo.setServiceRunTimeInfo(serviceRunTimeInfo);
      serviceInfo.setExtendedServiceInfo(extendedServiceInfo);

      FlattenedRelationship flattenedRelationship = new FlattenedRelationship();
      AssetKey targetAssetKey1 = new AssetKey();
      targetAssetKey1.setAssetId(targetAssetInfo1.getBasicAssetInfo().getAssetKey().getAssetId());
      targetAssetKey1.setAssetName(targetAssetInfo1.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library1 = new Library();
      library1.setLibraryId(targetAssetInfo1.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library1.setLibraryName(targetAssetInfo1.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library1);
      Relation relation = new Relation();
      relation.setAssetRelationship("functionalDomain");
      relation.setTargetAsset(targetAssetKey1);
      flattenedRelationship.getRelatedAsset().add(relation);

      AssetKey targetAssetKey2 = new AssetKey();
      targetAssetKey2.setAssetId(targetAssetInfo2.getBasicAssetInfo().getAssetKey().getAssetId());
      targetAssetKey2.setAssetName(targetAssetInfo2.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library2 = new Library();
      library2.setLibraryId(targetAssetInfo2.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library2.setLibraryName(targetAssetInfo2.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library2);
      Relation relation1 = new Relation();
      relation1.setAssetRelationship("functionalDomain");
      relation1.setTargetAsset(targetAssetKey2);
      flattenedRelationship.getRelatedAsset().add(relation1);
      // flattenedRelationship.setSourceAsset(targetAssetKey);
      serviceInfo.setFlattenedRelationship(flattenedRelationship);

      ArtifactInfo artifactInfo = new ArtifactInfo();
      Artifact artifact = new Artifact();
      artifact.setArtifactName("FileArtifact.txt");
      artifact.setArtifactValueType(ArtifactValueType.FILE);
      artifact.setArtifactCategory("ext_doc");
      artifactInfo.setArtifact(artifact);
      File file = null;
      OutputStream outputStream = null;
      try {
         file = File.createTempFile("test", ".txt");
         outputStream = new FileOutputStream(file);
         outputStream.write(20);
      } catch (IOException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } finally {
         if (outputStream != null) {
            try {
               outputStream.close();
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }

      FileInputStream fileInputStream = null;
      try {
         fileInputStream = new FileInputStream(file);
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      byte[] fileByte = new byte[(int) file.length()];
      try {
         fileInputStream.read(fileByte);
      } catch (IOException e) {
         e.printStackTrace();
      }
      artifactInfo.setArtifactDetail(fileByte);
      serviceInfo.getArtifactInfo().add(artifactInfo);

      artifactInfo = new ArtifactInfo();
      artifact = new Artifact();
      artifact.setArtifactName("URL Artifact");
      artifact.setArtifactCategory("ext_doc");
      artifactInfo.setArtifact(artifact);
      artifactInfo.setArtifactDetail("http://www.ebay.com".getBytes());
      artifact.setArtifactValueType(ArtifactValueType.URL);
      serviceInfo.getArtifactInfo().add(artifactInfo);

      artifactInfo = new ArtifactInfo();
      artifact = new Artifact();
      artifact.setArtifactName("Description Artifact");
      artifact.setArtifactCategory("ext_doc");
      artifactInfo.setArtifact(artifact);
      artifactInfo.setArtifactDetail("The description".getBytes());
      artifact.setArtifactValueType(ArtifactValueType.DESCRIPTION);
      serviceInfo.getArtifactInfo().add(artifactInfo);

      if (variant.equalsIgnoreCase("withoutOptionalInputParams")) {
         serviceInfo.setAssetLifeCycleInfo(null);
         serviceInfo.setExtendedServiceInfo(null);
         serviceInfo.setFlattenedRelationship(null);
         serviceInfo.getArtifactInfo().clear();

      }
      createServiceRequest.setServiceInfo(serviceInfo);

      try {
         CreateServiceResponse createServiceResponse = createServiceConsumer.getProxy().createService(
                  createServiceRequest);

         if (createServiceResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateCreateServiceResponse(createServiceResponse, "positiveCase").equalsIgnoreCase(
                  RepositoryServiceClientConstants.SUCCESS)) {
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   public static String testCreateService_invalidInput(String variant) {
      CreateServiceConsumer createServiceConsumer = new CreateServiceConsumer();
      CreateServiceRequest createServiceRequest = new CreateServiceRequest();
      ServiceInfo serviceInfo = new ServiceInfo();
      Library library = new Library();
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      BasicServiceInfo basicServiceInfo = new BasicServiceInfo();
      java.util.Date date = new java.util.Date();
      long currentTime = date.getTime();
      basicServiceInfo.setServiceDescription("QE_BSPropService_2_Description_" + currentTime);
      basicServiceInfo.setServiceLongDescription("QE_BSPropService_2_Long_Description");
      basicServiceInfo.setServiceNamespace("QE_BSPropService_2_Namespace");

      basicServiceInfo.setServiceName("QE_BSPropService_16_" + currentTime);
      // basicServiceInfo.setAssetType("Service");
      basicServiceInfo.setServiceVersion("1.1.0");
      AssetKey assetKey = new AssetKey();
      assetKey.setLibrary(library);
      basicServiceInfo.setAssetKey(assetKey);
      serviceInfo.setBasicServiceInfo(basicServiceInfo);

      serviceInfo.setBasicServiceInfo(null);
      AssetLifeCycleInfo assetLifeCycleInfo = new AssetLifeCycleInfo();
      assetLifeCycleInfo.setApprover("csubhash");
      assetLifeCycleInfo.setArchitect("csubhash");
      assetLifeCycleInfo.setDomainOwner("csubhash");
      assetLifeCycleInfo.setDomainType("xxx");
      assetLifeCycleInfo.setLifeCycleState("InReview");
      assetLifeCycleInfo.setNextAction("submit");
      assetLifeCycleInfo.setOpsArchitect("csubhash");
      assetLifeCycleInfo.setProductManager("csubhash");
      assetLifeCycleInfo.setProjectManager("csubhash");
      assetLifeCycleInfo.setServiceArchitect("csubhash");
      assetLifeCycleInfo.setTraceTicket("TOICJKSDH");
      assetLifeCycleInfo.setTrackerId("TRACKED ID");
      serviceInfo.setAssetLifeCycleInfo(assetLifeCycleInfo);

      ExtendedServiceInfo extendedServiceInfo = new ExtendedServiceInfo();
      ServiceDesignTimeInfo serviceDesignTimeInfo = new ServiceDesignTimeInfo();
      serviceDesignTimeInfo.setImplementationClass("Com.implementation");
      serviceDesignTimeInfo.setInterfaceClass("com.interface");
      serviceDesignTimeInfo.setIsSecured(true);
      serviceDesignTimeInfo.setPendingVersion(null);
      serviceDesignTimeInfo.setPublishingStatus("Private");
      serviceDesignTimeInfo.setServiceDomain("API");
      serviceDesignTimeInfo.setServiceLayer("Business Service");
      serviceDesignTimeInfo.setServiceType("Production");
      extendedServiceInfo.setServiceDesignTimeInfo(serviceDesignTimeInfo);
      ServiceRunTimeInfo serviceRunTimeInfo = new ServiceRunTimeInfo();
      serviceRunTimeInfo.setDeployedPool("Deployed pool");
      serviceRunTimeInfo.setProductionURL("http://www.ebay.co.in");
      serviceRunTimeInfo.setServiceState(true);
      serviceRunTimeInfo.setServiceStatus("Up");
      serviceRunTimeInfo.setStagingURL("ebay.com");
      extendedServiceInfo.setServiceRunTimeInfo(serviceRunTimeInfo);
      serviceInfo.setExtendedServiceInfo(extendedServiceInfo);

      FlattenedRelationship flattenedRelationship = new FlattenedRelationship();
      AssetKey targetAssetKey = new AssetKey();
      targetAssetKey.setAssetId("1.0_1228467253126-1486119231");
      targetAssetKey.setLibrary(library);
      Relation relation = new Relation();
      relation.setAssetRelationship("dependent_service");
      relation.setTargetAsset(targetAssetKey);
      flattenedRelationship.getRelatedAsset().add(relation);

      AssetKey targetAssetKey1 = new AssetKey();
      targetAssetKey1.setAssetId("1.0_1228961543884-1208439377");
      targetAssetKey1.setLibrary(library);
      Relation relation1 = new Relation();
      relation1.setAssetRelationship("dependent_service");
      relation1.setTargetAsset(targetAssetKey1);
      flattenedRelationship.getRelatedAsset().add(relation1);
      // flattenedRelationship.setSourceAsset(targetAssetKey);
      serviceInfo.setFlattenedRelationship(flattenedRelationship);

      ArtifactInfo artifactInfo = new ArtifactInfo();
      Artifact artifact = new Artifact();
      artifact.setArtifactName("FileArtifact.txt");
      artifact.setArtifactValueType(ArtifactValueType.FILE);
      artifact.setArtifactCategory("ext_doc");
      artifactInfo.setArtifact(artifact);
      File file = null;
      OutputStream outputStream = null;
      try {
         file = File.createTempFile("test", ".txt");
         outputStream = new FileOutputStream(file);
         outputStream.write(20);
      } catch (IOException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } finally {
         if (outputStream != null) {
            try {
               outputStream.close();
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }

      FileInputStream fileInputStream = null;
      try {
         fileInputStream = new FileInputStream(file);
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      byte[] fileByte = new byte[(int) file.length()];
      try {
         fileInputStream.read(fileByte);
      } catch (IOException e) {
         e.printStackTrace();
      }
      artifactInfo.setArtifactDetail(fileByte);
      serviceInfo.getArtifactInfo().add(artifactInfo);

      artifactInfo = new ArtifactInfo();
      artifact = new Artifact();
      artifact.setArtifactName("URL Artifact");
      artifact.setArtifactCategory("ext_doc");
      artifactInfo.setArtifact(artifact);
      artifactInfo.setArtifactDetail("http://www.ebay.com".getBytes());
      artifact.setArtifactValueType(ArtifactValueType.URL);
      serviceInfo.getArtifactInfo().add(artifactInfo);

      artifactInfo = new ArtifactInfo();
      artifact = new Artifact();
      artifact.setArtifactName("Description Artifact");
      artifact.setArtifactCategory("ext_doc");
      artifactInfo.setArtifact(artifact);
      artifactInfo.setArtifactDetail("The description".getBytes());
      artifact.setArtifactValueType(ArtifactValueType.DESCRIPTION);
      serviceInfo.getArtifactInfo().add(artifactInfo);

      createServiceRequest.setServiceInfo(serviceInfo);

      if (variant.equalsIgnoreCase("noBasicServiceInfo")) {
         serviceInfo.setBasicServiceInfo(null);
      }
      if (variant.equalsIgnoreCase("invalidServiceVersion")) {
         basicServiceInfo.setServiceVersion(RepositoryServiceClientConstants.INVALID_ASSET_VERSION);
      }
      if (variant.equalsIgnoreCase("duplicateService")) {
         basicServiceInfo.setServiceDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION);
         basicServiceInfo.setServiceNamespace(RepositoryServiceClientConstants.ASSET_NAMESPACE);
         basicServiceInfo.setServiceName(RepositoryServiceClientConstants.ASSET_NAME);
         basicServiceInfo.setServiceVersion(RepositoryServiceClientConstants.ASSET_VERSION);

         serviceInfo.setAssetLifeCycleInfo(null);
         serviceInfo.setExtendedServiceInfo(null);
         serviceInfo.setFlattenedRelationship(null);
      }

      try {
         CreateServiceResponse createServiceResponse = createServiceConsumer.getProxy().createService(
                  createServiceRequest);

         if (createServiceResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateCreateServiceResponse(createServiceResponse, "negativeCase").equalsIgnoreCase(
                  RepositoryServiceClientConstants.SUCCESS)) {
            List<CommonErrorData> errorDatas = createServiceResponse.getErrorMessage().getError();

            System.out.println("The following list of errors occured");
            for (CommonErrorData errorData : errorDatas) {
               System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());
            }
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   private static String validateCreateServiceResponse(CreateServiceResponse createServiceResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         String response = createServiceResponse.getAck().value();
         if (response.equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)
                  || response.equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
            return RepositoryServiceConsumerUtil.validateAssetKey(createServiceResponse.getAssetKey());
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (createServiceResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            if (createServiceResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
      }

      return RepositoryServiceClientConstants.FAILURE;
   }

   @Override
   protected AsyncTurmericRSV1 getProxy() throws ServiceException {
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

}
