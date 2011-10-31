/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Assert;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.repository.v1.services.*;

/**
 * @author szacharias
 * 
 */
public class TestAssertionGroups extends BaseAssertionsServiceTest {

   public static AssetCreatorIntf assetCreator = AssetCreatorFactory
            .getAssetCreator("resources/assetCreationxmls/Services.xml");

   @BeforeClass
   public static void setUpClass() throws Throwable {
      try {
         assetCreator.createAsset();
      } catch (Throwable exception) {
         exception.printStackTrace();
         throw exception;
      }

   }

   @Test
   public void testAssertionGroupsRequestWithInternalArtifact_Positive() {

      System.out.println("\n***Starting testAssertionGroupsRequestWithInternalArtifact_Positive");

      ApplyAssertionGroupsRequest request = new ApplyAssertionGroupsRequest();

      AssertionGroupAsset reference = createAssertionGroup();
      request.getAssertionGroups().add(reference);

      AssertableArtifact artifact = createInternalArtifactWithPassingContent();
      request.getArtifacts().add(artifact);

      String result = processRequest(request, POSITIVE_CASE);
      Assert.assertEquals(PASS, result);

      System.out.println("testAssertionGroupsRequestWithInternalArtifact_Positive : " + result);
   }

   @Test
   public void testAssertionGroupsRequestWithInternalArtifacts() {
      System.out.println("\n***Starting testAssertionGroupsRequestWithInternalArtifacts");

      ApplyAssertionGroupsRequest request = new ApplyAssertionGroupsRequest();

      AssertionGroupAsset reference = createAssertionGroup();
      request.getAssertionGroups().add(reference);

      List<AssertableArtifact> artifacts = createInternalArtifacts();
      request.getArtifacts().addAll(artifacts);

      String result = processRequest(request, POSITIVE_CASE);
      Assert.assertEquals(PASS, result);

      System.out.println("testAssertionGroupsRequestWithInternalArtifacts : " + result);
   }

   @Test
   public void testAssertionGroupsRequestWithMultipleGroupsAndInternalArtifact() {
      System.out.println("\n***Starting testAssertionGroupsRequestWithMultipleGroupsAndInternalArtifact");

      ApplyAssertionGroupsRequest request = new ApplyAssertionGroupsRequest();

      List<AssertionGroupAsset> groups = createAssertionGroups();
      request.getAssertionGroups().addAll(groups);

      AssertableArtifact artifact = createInternalArtifactWithPassingContent();
      request.getArtifacts().add(artifact);

      String result = processRequest(request, POSITIVE_CASE);
      Assert.assertEquals(PASS, result);

      System.out.println("testAssertionGroupsRequestWithMultipleGroupsAndInternalArtifact : " + result);
   }

   @Test
   public void testAssertionGroupsRequestWithInternalArtifact_Negative() {
      System.out.println("\n***Starting testAssertionGroupsRequestWithInternalArtifact_Negative");

      ApplyAssertionGroupsRequest request = new ApplyAssertionGroupsRequest();

      AssertionGroupAsset reference = createAssertionGroup();
      request.getAssertionGroups().add(reference);

      AssertableArtifact artifact = createInternalArtifactWithFailingContent();
      request.getArtifacts().add(artifact);

      String result = processRequest(request, NEGATIVE_CASE);
      Assert.assertEquals(PASS, result);

      System.out.println("testAssertionGroupsRequestWithInternalArtifact_Negative : " + result);
   }

   @Test
   public void testAssertionGroupsRequestWithExternalArtifact_Positive() {
      System.out.println("\n***Starting testAssertionGroupsRequestWithExternalArtifact_Positive");

      ApplyAssertionGroupsRequest request = new ApplyAssertionGroupsRequest();

      AssertionGroupAsset reference = createAssertionGroup();
      request.getAssertionGroups().add(reference);

      AssertableArtifact artifact = createExternalArtifactWithPassingContent();
      request.getArtifacts().add(artifact);

      String result = processRequest(request, POSITIVE_CASE);
      Assert.assertEquals(PASS, result);

      System.out.println("testAssertionGroupsRequestWithExternalArtifact_Positive : " + result);
   }

   @Test
   public void testAssertionGroupsRequestWithExternalArtifacts() {
      System.out.println("\n***Starting testAssertionGroupsRequestWithExternalArtifacts");

      ApplyAssertionGroupsRequest request = new ApplyAssertionGroupsRequest();

      AssertionGroupAsset reference = createAssertionGroup();
      request.getAssertionGroups().add(reference);

      List<AssertableArtifact> artifacts = createExternalArtifacts();
      request.getArtifacts().addAll(artifacts);

      String result = processRequest(request, POSITIVE_CASE);
      Assert.assertEquals(PASS, result);

      System.out.println("testAssertionGroupsRequestWithExternalArtifacts : " + result);
   }

   @Test
   public void testAssertionGroupsRequestWithMultipleGroupsAndExternalArtifact() {
      System.out.println("\n***Starting testAssertionGroupsRequestWithMultipleGroupsAndExternalArtifact");

      ApplyAssertionGroupsRequest request = new ApplyAssertionGroupsRequest();

      List<AssertionGroupAsset> groups = createAssertionGroups();
      request.getAssertionGroups().addAll(groups);

      AssertableArtifact artifact = createExternalArtifactWithPassingContent();
      request.getArtifacts().add(artifact);

      String result = processRequest(request, POSITIVE_CASE);
      Assert.assertEquals(PASS, result);

      System.out.println("testAssertionGroupsRequestWithMultipleGroupsAndExternalArtifact : " + result);
   }

   @Test
   public void testAssertionGroupsRequestWithExternalArtifact_Negative() {
      System.out.println("\n***Starting testAssertionGroupsRequestWithExternalArtifact_Negative");

      ApplyAssertionGroupsRequest request = new ApplyAssertionGroupsRequest();

      AssertionGroupAsset reference = createAssertionGroup();
      request.getAssertionGroups().add(reference);

      AssertableArtifact artifact = createExternalArtifactWithFailingContent();
      request.getArtifacts().add(artifact);

      String result = processRequest(request, NEGATIVE_CASE);
      Assert.assertEquals(PASS, result);

      System.out.println("testAssertionGroupsRequestWithExternalArtifact_Negative : " + result);
   }

   @Test
   public void testAssertionGroupsRequestWithInvalidGroupDetails() {

      System.out.println("\n***Starting testAssertionGroupsRequestWithInvalidGroupDetails");

      ApplyAssertionGroupsRequest request = new ApplyAssertionGroupsRequest();

      AssertionGroupAsset reference = createAssertionGroup();
      reference.setLibraryName("jhfgj");
      request.getAssertionGroups().add(reference);

      AssertableArtifact artifact = createInternalArtifactWithPassingContent();
      request.getArtifacts().add(artifact);

      String result = processRequest(request, FAILURE_CASE);
      Assert.assertEquals(PASS, result);

      System.out.println("testAssertionGroupsRequestWithInvalidGroupDetails : " + result);
   }

   private AssertionGroupAsset createAssertionGroup() {

      AssertionGroupAsset reference = new AssertionGroupAsset();
      // reference.setAssetName("soa_wsdl_assertionGroup");
      reference.setAssetName("soa_schemaCheckerGeneralRules_assertionGroup");
      reference.setLibraryName("SystemAssets");
      reference.setVersion("3.0.0");
      reference.setAssetType(AssertionGroupAssetTypes.ASSERTION_GROUP);

      return reference;
   }

   private List<AssertionGroupAsset> createAssertionGroups() {

      List<AssertionGroupAsset> assertionGroups = new ArrayList<AssertionGroupAsset>();

      AssertionGroupAsset reference = new AssertionGroupAsset();
      // reference.setAssetName("soa_wsdl_assertionGroup");
      reference.setAssetName("soa_schemaCheckerGeneralRules_assertionGroup");
      reference.setLibraryName("SystemAssets");
      reference.setVersion("3.0.0");
      reference.setAssetType(AssertionGroupAssetTypes.ASSERTION_GROUP);

      AssertionGroupAsset reference1 = new AssertionGroupAsset();
      reference1.setAssetName("soa_wsdlElement_assertionGroup");
      reference1.setLibraryName("SystemAssets");
      reference1.setVersion("3.0.0");
      reference.setAssetType(AssertionGroupAssetTypes.ASSERTION_GROUP);

      assertionGroups.add(reference);
      assertionGroups.add(reference1);

      return assertionGroups;
   }

   private AssertableArtifact createInternalArtifactWithPassingContent() {

      AssetInfo assetInfo = null;
      try {
         assetInfo = assetCreator.getAssetAsAssetInfo("InternalServiceAsset1");
      } catch (AssetInfoNotFoundException e) {
         e.printStackTrace();
      } catch (IdNotFoundException e) {
         e.printStackTrace();
      }

      if (assetInfo == null) {
         return null;
      }

      String assetName = assetInfo.getBasicAssetInfo().getAssetName();
      String assetVersion = assetInfo.getBasicAssetInfo().getVersion();

      AssertableArtifact artifact = new AssertableArtifact();
      ArtifactAsset artifactReference = new ArtifactAsset();
      artifactReference.setArtifactCategory(ArtifactContentTypes.WSDL);
      artifactReference.setAssetName(assetName);
      artifactReference.setAssetType("Service");
      artifactReference.setLibraryName("GovernedAssets");
      artifactReference.setVersion(assetVersion);
      artifact.setArtifactAssetReference(artifactReference);

      return artifact;
   }

   private List<AssertableArtifact> createInternalArtifacts() {

      List<AssertableArtifact> artifacts = new ArrayList<AssertableArtifact>();

      AssetInfo assetInfo = null;
      try {
         assetInfo = assetCreator.getAssetAsAssetInfo("InternalServiceAsset1");
      } catch (AssetInfoNotFoundException e) {
         e.printStackTrace();
      } catch (IdNotFoundException e) {
         e.printStackTrace();
      }

      if (assetInfo == null) {
         return null;
      }

      String assetName1 = assetInfo.getBasicAssetInfo().getAssetName();
      String assetVersion1 = assetInfo.getBasicAssetInfo().getVersion();

      AssertableArtifact artifact = new AssertableArtifact();
      ArtifactAsset artifactReference = new ArtifactAsset();
      artifactReference.setArtifactCategory(ArtifactContentTypes.WSDL);
      artifactReference.setAssetName(assetName1);
      artifactReference.setAssetType("Service");
      artifactReference.setLibraryName("GovernedAssets");
      artifactReference.setVersion(assetVersion1);
      artifact.setArtifactAssetReference(artifactReference);

      try {
         assetInfo = assetCreator.getAssetAsAssetInfo("InternalServiceAsset1");
      } catch (AssetInfoNotFoundException e) {
         e.printStackTrace();
      } catch (IdNotFoundException e) {
         e.printStackTrace();
      }

      if (assetInfo == null) {
         return null;
      }

      String assetName2 = assetInfo.getBasicAssetInfo().getAssetName();
      String assetVersion2 = assetInfo.getBasicAssetInfo().getVersion();

      AssertableArtifact artifact1 = new AssertableArtifact();
      ArtifactAsset artifactReference1 = new ArtifactAsset();
      artifactReference1.setArtifactCategory(ArtifactContentTypes.WSDL);
      artifactReference1.setAssetName(assetName2);
      artifactReference1.setAssetType("Service");
      artifactReference1.setLibraryName("GovernedAssets");
      artifactReference1.setVersion(assetVersion2);
      artifact1.setArtifactAssetReference(artifactReference1);

      artifacts.add(artifact);
      artifacts.add(artifact1);

      return artifacts;
   }

   private AssertableArtifact createInternalArtifactWithFailingContent() {

      AssetInfo assetInfo = null;
      try {
         assetInfo = assetCreator.getAssetAsAssetInfo("InternalServiceAsset2");
      } catch (AssetInfoNotFoundException e) {
         e.printStackTrace();
      } catch (IdNotFoundException e) {
         e.printStackTrace();
      }

      if (assetInfo == null) {
         return null;
      }

      String assetName = assetInfo.getBasicAssetInfo().getAssetName();
      String assetVersion = assetInfo.getBasicAssetInfo().getVersion();

      AssertableArtifact artifact = new AssertableArtifact();
      ArtifactAsset artifactReference = new ArtifactAsset();
      artifactReference.setArtifactCategory(ArtifactContentTypes.WSDL);
      artifactReference.setAssetName(assetName);
      artifactReference.setAssetType("Service");
      artifactReference.setLibraryName("GovernedAssets");
      artifactReference.setVersion(assetVersion);
      artifact.setArtifactAssetReference(artifactReference);

      return artifact;
   }

   private AssertableArtifact createExternalArtifactWithPassingContent() {

      AssertableArtifact artifact = new AssertableArtifact();
      ExternalArtifact artifactExternal = new ExternalArtifact();
      artifactExternal.setContentType(ArtifactContentTypes.WSDL);
      artifactExternal.setBinaryContent(getBinaryContent("TestService.wsdl"));
      artifact.setArtifactExternal(artifactExternal);

      return artifact;
   }

   private List<AssertableArtifact> createExternalArtifacts() {

      List<AssertableArtifact> artifacts = new ArrayList<AssertableArtifact>();

      AssertableArtifact artifact = new AssertableArtifact();
      ExternalArtifact artifactExternal = new ExternalArtifact();
      artifactExternal.setContentType(ArtifactContentTypes.WSDL);
      artifactExternal.setBinaryContent(getBinaryContent("TestService.wsdl"));
      artifact.setArtifactExternal(artifactExternal);

      AssertableArtifact artifact1 = new AssertableArtifact();
      ExternalArtifact artifactExternal1 = new ExternalArtifact();
      artifactExternal1.setContentType(ArtifactContentTypes.WSDL);
      artifactExternal1.setBinaryContent(getBinaryContent("SampleService.wsdl"));
      artifact1.setArtifactExternal(artifactExternal1);

      artifacts.add(artifact);
      artifacts.add(artifact1);

      return artifacts;
   }

   private AssertableArtifact createExternalArtifactWithFailingContent() {

      AssertableArtifact artifact = new AssertableArtifact();
      ExternalArtifact artifactExternal = new ExternalArtifact();
      artifactExternal.setContentType(ArtifactContentTypes.WSDL);
      artifactExternal.setBinaryContent(getBinaryContent("Lowercase TestServiceSency.wsdl"));
      artifact.setArtifactExternal(artifactExternal);

      return artifact;
   }
}
