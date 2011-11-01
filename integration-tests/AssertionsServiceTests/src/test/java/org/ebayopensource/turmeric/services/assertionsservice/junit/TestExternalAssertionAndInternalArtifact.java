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

import junit.framework.Assert;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.assertion.v1.services.*;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;

/**
 * @author szacharias
 * 
 */
public class TestExternalAssertionAndInternalArtifact extends BaseAssertionsServiceTest {

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
   public void testAssertionsRequestWithErraneousAssertionScript() {
      System.out.println("\n***Starting testAssertionsRequestWithErraneousAssertionScript");

      ApplyAssertionsRequest request = new ApplyAssertionsRequest();

      Assertion assertion = createErraneousAssertion();
      request.getAssertions().add(assertion);

      AssertableArtifact artifact = createArtifactWithPassingContent();
      if (artifact == null) {
         System.out.println("Unable to get the wsdl artifact");
         Assert.fail();
      }
      request.getArtifacts().add(artifact);

      String result = processRequest(request, FAILURE_CASE);
      System.out.println("testAssertionsRequestWithErraneousAssertionScript : " + result);

      Assert.assertEquals(PASS, result);
   }

   @Test
   public void testAssertionsRequest_Positive() {
      System.out.println("\n***Starting testAssertionsRequest_Positive");

      ApplyAssertionsRequest request = new ApplyAssertionsRequest();

      Assertion assertion = createAssertion();
      request.getAssertions().add(assertion);

      AssertableArtifact artifact = createArtifactWithPassingContent();
      if (artifact == null) {
         System.out.println("Unable to get the wsdl artifact");
         Assert.fail();
      }
      request.getArtifacts().add(artifact);

      String result = processRequest(request, POSITIVE_CASE);
      System.out.println("testAssertionsRequest_Positive : " + result);

      Assert.assertEquals(PASS, result);
   }

   @Test
   public void testAssertionsRequest_WithInternalModule() {
      System.out.println("\n***Starting testAssertionsRequest_WithInternalModule");

      ApplyAssertionsRequest request = new ApplyAssertionsRequest();

      Assertion assertion = createAssertionWithInternalModule();
      request.getAssertions().add(assertion);

      AssertableArtifact artifact = createArtifactWithPassingContent();
      if (artifact == null) {
         System.out.println("Unable to get the wsdl artifact");
         Assert.fail();
      }
      request.getArtifacts().add(artifact);

      String result = processRequest(request, POSITIVE_CASE);
      System.out.println("testAssertionsRequest_WithInternalModule : " + result);

      Assert.assertEquals(PASS, result);
   }

   @Test
   public void testAssertionsRequest_WithExternalModule() {
      System.out.println("\n***Starting testAssertionsRequest_WithExternalModule");

      ApplyAssertionsRequest request = new ApplyAssertionsRequest();

      Assertion assertion = createAssertionWithExternalModule();
      request.getAssertions().add(assertion);

      AssertableArtifact artifact = createArtifactWithPassingContent();
      if (artifact == null) {
         System.out.println("Unable to get the wsdl artifact");
         Assert.fail();
      }
      request.getArtifacts().add(artifact);

      String result = processRequest(request, POSITIVE_CASE);
      System.out.println("testAssertionsRequest_WithExternalModule : " + result);

      Assert.assertEquals(PASS, result);
   }

   @Test
   public void testAssertionsRequest_Negative() {
      System.out.println("\n***Starting testAssertionsRequest_Negative");

      ApplyAssertionsRequest request = new ApplyAssertionsRequest();

      Assertion assertion = createAssertion();
      request.getAssertions().add(assertion);

      AssertableArtifact artifact = createArtifactWithFailingContent();
      request.getArtifacts().add(artifact);

      String result = processRequest(request, NEGATIVE_CASE);
      System.out.println("testAssertionsRequest_Negative : " + result);

      Assert.assertEquals(PASS, result);
   }

   @Test
   public void testAssertionsRequestWithMultipleArtifacts() {
      System.out.println("\n***Starting testAssertionsRequestWithMultipleArtifacts");

      ApplyAssertionsRequest request = new ApplyAssertionsRequest();

      Assertion assertion = createAssertion();
      request.getAssertions().add(assertion);

      List<AssertableArtifact> artifacts = createArtifacts();
      request.getArtifacts().addAll(artifacts);

      String result = processRequest(request, POSITIVE_CASE);
      System.out.println("testAssertionsRequestWithMultipleArtifacts : " + result);

      Assert.assertEquals(PASS, result);
   }

   private Assertion createAssertion() {

      Assertion assertion = new Assertion();
      ExternalAssertion reference = new ExternalAssertion();
      reference.setAssertionProcessorType("XQuery");
      reference.setDescription("Testing");
      reference.setErrorSeverity(AssertionSeverity.ERROR);
      reference.setName("ComplexTypeNameUppercase Assertion");
      reference.setVersion("1.0");
      reference.setContentBindingName(ContentBindingName.FILE_PATH);

      ExternalContent contentExternal = new ExternalContent();
      contentExternal.setBinaryContent(getBinaryContent("ComplexTypeNameUppercase.xq"));
      reference.setAssertionScript(contentExternal);
      assertion.setExternalAssertion(reference);

      return assertion;
   }

   private Assertion createErraneousAssertion() {

      Assertion assertion = new Assertion();
      ExternalAssertion reference = new ExternalAssertion();
      reference.setAssertionProcessorType("XQuery");
      reference.setDescription("Testing");
      reference.setErrorSeverity(AssertionSeverity.ERROR);
      reference.setName("ErraneousAssertion Assertion");
      reference.setVersion("1.0");
      reference.setContentBindingName(ContentBindingName.FILE_PATH);

      ExternalContent contentExternal = new ExternalContent();
      contentExternal.setBinaryContent(getBinaryContent("ErraneousAssertion.xq"));
      reference.setAssertionScript(contentExternal);
      assertion.setExternalAssertion(reference);

      return assertion;
   }

   private Assertion createAssertionWithInternalModule() {

      Assertion assertion = new Assertion();
      ExternalAssertion reference = new ExternalAssertion();
      reference.setAssertionProcessorType("XQuery");
      reference.setDescription("Testing");
      reference.setErrorSeverity(AssertionSeverity.ERROR);
      reference.setName("ComplexTypeNameUppercase Assertion");
      reference.setVersion("1.0");
      reference.setContentBindingName(ContentBindingName.FILE_PATH);

      ExternalContent contentExternal = new ExternalContent();
      contentExternal.setBinaryContent(getBinaryContent("ComplexTypeNameUppercaseWithIntModule.xq"));
      reference.setAssertionScript(contentExternal);
      assertion.setExternalAssertion(reference);

      AssertionModuleAsset assetReference = new AssertionModuleAsset();
      assetReference.setAssetName("soa_assertionFrameworkUtil_assertionModule");
      assetReference.setAssetType(AssertionModuleAssetTypes.ASSERTION_MODULE);
      assetReference.setLibraryName("SystemAssets");
      assetReference.setVersion("1.0.0");

      AssertionModule module = new AssertionModule();
      module.setModuleAssetReference(assetReference);
      reference.getAssertionModules().add(module);

      return assertion;
   }

   private Assertion createAssertionWithExternalModule() {

      Assertion assertion = new Assertion();
      ExternalAssertion reference = new ExternalAssertion();
      reference.setAssertionProcessorType("XQuery");
      reference.setDescription("Testing");
      reference.setErrorSeverity(AssertionSeverity.ERROR);
      reference.setName("ElementNameEndingWithWordCount Assertion");
      reference.setVersion("1.0");
      reference.setContentBindingName(ContentBindingName.FILE_PATH);

      ExternalContent contentExternal = new ExternalContent();
      contentExternal.setBinaryContent(getBinaryContent("ElementNameEndingWithWordCount.xq"));
      reference.setAssertionScript(contentExternal);
      assertion.setExternalAssertion(reference);

      ExternalAssertionModule externalModule = new ExternalAssertionModule();
      externalModule.setAssertionProcessorType(AssertionProcessorType.XQUERY);
      externalModule.setDescription("Testing");
      externalModule.setName("TestModule");
      externalModule.setVersion("1.0");

      ExternalContent contentExternal1 = new ExternalContent();
      contentExternal1.setBinaryContent(getBinaryContent("AssertionFrameworkUtil.xq"));
      externalModule.getModuleScripts().add(contentExternal1);

      AssertionModule module = new AssertionModule();
      module.setAssertionModuleExternal(externalModule);
      reference.getAssertionModules().add(module);

      return assertion;
   }

   private AssertableArtifact createArtifactWithPassingContent() {

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

   private List<AssertableArtifact> createArtifacts() {

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

   private AssertableArtifact createArtifactWithFailingContent() {

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

}
