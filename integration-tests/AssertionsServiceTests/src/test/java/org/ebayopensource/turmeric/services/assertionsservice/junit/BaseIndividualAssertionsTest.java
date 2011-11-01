/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import org.ebayopensource.turmeric.assertion.v1.services.*;

/**
 * @author szacharias
 * 
 */
public class BaseIndividualAssertionsTest extends BaseAssertionsServiceTest {

   /**
    * For an assertion "soa_sampleAssertionXXX_assertion", the sample WSDL to test the positive scenario need to be
    * present as "/WSDL/SampleAssertionXXX/Sample_Pass.wsdl". Similarly, the sample WSDL to test the negative scenario
    * need to be present as "/WSDL/SampleAssertionXXX/Sample_Fail.wsdl"
    */

   protected Assertion createAssertion(String assertionAssetName) {

      Assertion assertion = new Assertion();
      AssertionAsset reference = new AssertionAsset();
      reference.setAssetName(assertionAssetName);
      reference.setAssetType(AssertionAssetTypes.ASSERTION);
      reference.setLibraryName("SystemAssets");
      reference.setVersion("1.0.0");
      assertion.setAssertionAsset(reference);

      return assertion;
   }

   protected AssertableArtifact createArtifactForPositiveScenario(String assertionAssetName) {

      AssertableArtifact artifact = new AssertableArtifact();
      ExternalArtifact artifactExternal = new ExternalArtifact();
      artifactExternal.setContentType(ArtifactContentTypes.WSDL);
      artifactExternal.setBinaryContent(getBinaryContent(getTestArtifactWSDLFilepath(assertionAssetName)
               + "/Sample_Pass.wsdl"));
      artifact.setArtifactExternal(artifactExternal);

      return artifact;
   }

   protected AssertableArtifact createArtifactForNegativeScenario(String assertionAssetName) {

      AssertableArtifact artifact = new AssertableArtifact();
      ExternalArtifact artifactExternal = new ExternalArtifact();
      artifactExternal.setContentType(ArtifactContentTypes.WSDL);
      artifactExternal.setBinaryContent(getBinaryContent(getTestArtifactWSDLFilepath(assertionAssetName)
               + "/Sample_Fail.wsdl"));
      artifact.setArtifactExternal(artifactExternal);

      return artifact;
   }

   protected AssertableArtifact createArtifact(String assertionAssetName, String artifactFilename) {

      AssertableArtifact artifact = new AssertableArtifact();
      ExternalArtifact artifactExternal = new ExternalArtifact();
      artifactExternal.setContentType(ArtifactContentTypes.WSDL);
      artifactExternal.setBinaryContent(getBinaryContent(getTestArtifactWSDLFilepath(assertionAssetName) + "/"
               + artifactFilename));
      artifact.setArtifactExternal(artifactExternal);

      return artifact;
   }

   protected String getTestArtifactWSDLFilepath(String assertionAssetName) {

      String filepath = null;
      String temp = assertionAssetName;

      if (assertionAssetName != null) {
         if (temp.startsWith("soa_")) {
            temp = temp.replaceFirst("soa_", "");
         }
         if (temp.endsWith("_assertion")) {
            temp = temp.replace("_assertion", "");
         }
         char firstLetter = temp.charAt(0);
         temp = temp.replaceFirst(String.valueOf(firstLetter), String.valueOf(firstLetter).toUpperCase());

         filepath = "WSDL/" + temp;
      }
      return filepath;
   }

   protected String testAssertion(String assertionAssetName, String artifactFilename, String criteria) {

      ApplyAssertionsRequest request = new ApplyAssertionsRequest();

      Assertion assertion = createAssertion(assertionAssetName);
      request.getAssertions().add(assertion);

      AssertableArtifact artifact = createArtifact(assertionAssetName, artifactFilename);
      request.getArtifacts().add(artifact);

      return processRequest(request, criteria);
   }

   protected String testAssertionForPositiveScenario(String assertionAssetName) {

      ApplyAssertionsRequest request = new ApplyAssertionsRequest();

      Assertion assertion = createAssertion(assertionAssetName);
      request.getAssertions().add(assertion);

      AssertableArtifact artifact = createArtifactForPositiveScenario(assertionAssetName);
      request.getArtifacts().add(artifact);

      return processRequest(request, POSITIVE_CASE);
   }

   protected String testAssertionForNegativeScenario(String assertionAssetName) {

      ApplyAssertionsRequest request = new ApplyAssertionsRequest();

      Assertion assertion = createAssertion(assertionAssetName);
      request.getAssertions().add(assertion);

      AssertableArtifact artifact = createArtifactForNegativeScenario(assertionAssetName);
      request.getArtifacts().add(artifact);

      return processRequest(request, NEGATIVE_CASE);
   }
}
