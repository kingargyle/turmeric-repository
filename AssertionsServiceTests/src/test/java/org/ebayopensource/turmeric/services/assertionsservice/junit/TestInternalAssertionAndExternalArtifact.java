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

import org.junit.Test;

import junit.framework.TestCase;

import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionsRequest;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactContentTypes;
import org.ebayopensource.turmeric.repository.v1.services.AssertableArtifact;
import org.ebayopensource.turmeric.repository.v1.services.Assertion;
import org.ebayopensource.turmeric.repository.v1.services.AssertionAsset;
import org.ebayopensource.turmeric.repository.v1.services.AssertionAssetTypes;
import org.ebayopensource.turmeric.repository.v1.services.ExternalArtifact;

/**
 * @author szacharias
 * 
 */
public class TestInternalAssertionAndExternalArtifact extends BaseAssertionsServiceTest {
	
	@Test
	public void testAssertionsRequestWithSingleAssertionAndSingleArtifact() {
		System.out.println("\n***Starting testAssertionsRequestWithSingleAssertionAndSingleArtifact");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		AssertableArtifact artifact = createArtifactWithPassingContent();
		request.getArtifacts().add(artifact);
		request.getAssertions().add(assertion);
		
		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequestWithSingleAssertionAndSingleArtifact : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequestWithSingleAssertionAndMultipleArtifact() {
		System.out.println("\n***Starting testAssertionsRequestWithSingleAssertionAndMultipleArtifact");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		List<AssertableArtifact> artifacts = createArtifacts();
		request.getArtifacts().addAll(artifacts);
		
		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequestWithSingleAssertionAndMultipleArtifact : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequestWithMultipleAssertionAndSingleArtifact() {
		System.out.println("\n***Starting testAssertionsRequestWithMultipleAssertionAndSingleArtifact");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		List<Assertion> assertions = createAssertions();
		request.getAssertions().addAll(assertions);
		
		AssertableArtifact artifact = createArtifactWithPassingContent();
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequestWithMultipleAssertionAndSingleArtifact : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequestWithMultipleAssertionAndMultipleArtifact() {
		System.out.println("\n***Starting testAssertionsRequestWithMultipleAssertionAndMultipleArtifact");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();
		
		List<Assertion> assertions = createAssertions();
		request.getAssertions().addAll(assertions);

		List<AssertableArtifact> artifacts = createArtifacts();
		request.getArtifacts().addAll(artifacts);
		
		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequestWithMultipleAssertionAndMultipleArtifact : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequest_Positive() {
		System.out.println("\n***Starting testAssertionsRequest_Positive");
		
		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithPassingContent();
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequest_Positive : "  + result);
		
		TestCase.assertEquals(PASS, result);
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
		System.out.println("testAssertionsRequest_Negative : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	private Assertion createAssertion(){
		
		Assertion assertion = new Assertion();
		AssertionAsset reference = new AssertionAsset();
		reference.setAssetName("soa_complexTypeNameUppercase_assertion");
		reference.setAssetType(AssertionAssetTypes.ASSERTION);
		reference.setLibraryName("SystemAssets");
		reference.setVersion("1.0.0");
		assertion.setAssertionAsset(reference);
		
		return assertion;
	}
	
	private List<Assertion> createAssertions(){
		
		List<Assertion> assertions = new ArrayList<Assertion>();
		
		Assertion assertion = new Assertion();
		AssertionAsset reference = new AssertionAsset();
		reference.setAssetName("soa_complexTypeNameUppercase_assertion");
		reference.setAssetType(AssertionAssetTypes.ASSERTION);
		reference.setLibraryName("SystemAssets");
		reference.setVersion("1.0.0");
		assertion.setAssertionAsset(reference);
		
		Assertion assertion1 = new Assertion();
		AssertionAsset reference1 = new AssertionAsset();
		reference1.setAssetName("soa_serviceNameUppercase_assertion"); 
		reference1.setAssetType(AssertionAssetTypes.ASSERTION);
		reference1.setLibraryName("SystemAssets");
		reference1.setVersion("1.0.0");
		assertion1.setAssertionAsset(reference1);

		assertions.add(assertion);
		assertions.add(assertion1);
		
		return assertions;
	}
	
	private AssertableArtifact createArtifactWithPassingContent(){
		
		AssertableArtifact artifact = new AssertableArtifact();
		ExternalArtifact artifactExternal = new ExternalArtifact();
		artifactExternal.setContentType(ArtifactContentTypes.WSDL);
		//artifactExternal.setBinaryContent(getBinaryContent("TestService.wsdl"));
		artifactExternal.setTextContent(getTextContent("TestService.wsdl"));
		//artifactExternal.setUrlContent(getURLContent("//rhv-vepts-270/Chanchal/Project/XQuery/WSDLValid18.wsdl"));
		artifact.setArtifactExternal(artifactExternal);
		
		return artifact;
	}
	
	private List<AssertableArtifact> createArtifacts(){
		
		List<AssertableArtifact> artifacts  = new ArrayList<AssertableArtifact>();
		
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
	
	private AssertableArtifact createArtifactWithFailingContent(){
		
		AssertableArtifact artifact = new AssertableArtifact();
		ExternalArtifact artifactExternal = new ExternalArtifact();
		artifactExternal.setContentType(ArtifactContentTypes.WSDL);
		artifactExternal.setBinaryContent(getBinaryContent("Lowercase TestServiceSency.wsdl"));
		artifact.setArtifactExternal(artifactExternal);
		
		return artifact;
	}
	
}
