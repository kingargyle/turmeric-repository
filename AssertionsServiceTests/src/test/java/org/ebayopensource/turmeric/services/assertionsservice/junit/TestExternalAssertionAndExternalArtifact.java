/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import org.junit.Test;

import junit.framework.Assert;
import org.ebayopensource.turmeric.repository.v1.services.*;

/**
 * @author szacharias
 * 
 */
public class TestExternalAssertionAndExternalArtifact extends BaseAssertionsServiceTest {
	
	@Test
	public void testAssertionsRequestWithNullRequest() {
		System.out.println("\n***Starting testAssertionsRequestWithNullRequest");

		ApplyAssertionsRequest request = null;

		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequestWithNullRequest : "  +  result);
		
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequestWithEmptyRequest() {
		System.out.println("\n***Starting testAssertionsRequestWithEmptyRequest");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequestWithEmptyRequest : "  +  result);
		
		Assert.assertEquals(PASS, result);
	}
	
	//@Test
	public void testAssertionsRequestWithInternalAndExternalAssertions() {
		System.out.println("\n***Starting testAssertionsRequestWithInternalAndExternalAssertions");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		
		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		request.getAssertions().add(createInternalAssertion());
		
		AssertableArtifact artifact = createArtifactWithPassingContent();
		request.getArtifacts().add(artifact);

		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequestWithInternalAndExternalAssertions : "  +  result);
		
		Assert.assertEquals(PASS, result);
	}
	
	//@Test
	public void testAssertionsRequestWithInternalAndExternalArtifacts() {
		System.out.println("\n***Starting testAssertionsRequestWithInternalAndExternalArtifacts");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();
		
		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithPassingContent();
		request.getArtifacts().add(artifact);
		request.getArtifacts().add(createInternalArtifactWithPassingContent());

		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequestWithInternalAndExternalArtifacts : "  +  result);
		
		Assert.assertEquals(PASS, result);
	}
	
	private AssertableArtifact createInternalArtifactWithPassingContent(){
		
		AssertableArtifact artifact = new AssertableArtifact();
		ArtifactAsset artifactReference = new ArtifactAsset();
		artifactReference.setArtifactCategory(ArtifactContentTypes.WSDL);
		artifactReference.setAssetName("TestServiceSency"); 
		artifactReference.setAssetType("Service");
		artifactReference.setLibraryName("Services");
		artifactReference.setVersion("1.0");
		artifact.setArtifactAssetReference(artifactReference);
		
		return artifact;
	}
	
	private Assertion createInternalAssertion(){
		
		Assertion assertion = new Assertion();
		AssertionAsset reference = new AssertionAsset();
		reference.setAssetName("soa_complexTypeNameUppercase_assertion");
		reference.setAssetType(AssertionAssetTypes.ASSERTION);
		reference.setLibraryName("SystemAssets");
		reference.setVersion("1.0.0");
		assertion.setAssertionAsset(reference);
		
		return assertion;
	}
	
	@Test
	public void testAssertionsRequestWithNonWSDLArtifact() {
		System.out.println("\n***Starting testAssertionsRequestWithNonWSDLArtifact");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createNonWSDLArtifact();
		request.getArtifacts().add(artifact);

		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequestWithNonWSDLArtifact : "  +  result);
		
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequestWithInvalidArtifact() {
		System.out.println("\n***Starting testAssertionsRequestWithInvalidArtifact");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithBadContent();
		request.getArtifacts().add(artifact);

		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequestWithInvalidArtifact : "  +  result);
		
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequestWithSimpleExternalAssertion_Positive() {
		System.out.println("\n***Starting testAssertionsRequestWithSimpleExternalAssertion_Positive");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithPassingContent();
		request.getArtifacts().add(artifact);

		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequestWithSimpleExternalAssertion_Positive : "  +  result);
		
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequestWithSimpleExternalAssertion_Negative() {
		System.out.println("\n***Starting testAssertionsRequestWithSimpleExternalAssertion_Negative");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithFailingContent();
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, NEGATIVE_CASE);
		System.out.println("testAssertionsRequestWithSimpleExternalAssertion_Negative : "  + result);
		
		Assert.assertEquals(PASS, result);
	}
	
	//@Test
	public void testAssertionsRequestWithSimpleExternalAssertionWithIntModule() {
		System.out.println("\n***Starting testAssertionsRequestWithSimpleExternalAssertionWithIntModule");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertionWithInternalModule();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithPassingContent();
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequestWithSimpleExternalAssertionWithIntModule : "  + result);
		
		Assert.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequestWithSimpleExternalAssertionWithExtModule() {
		System.out.println("\n***Starting testAssertionsRequestWithSimpleExternalAssertionWithExtModule");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertionWithExternalModule();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithPassingContent();
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequestWithSimpleExternalAssertionWithExtModule : "  + result);
		
		Assert.assertEquals(PASS, result);
	}
	
	private Assertion createAssertion(){
		
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
	
	private Assertion createAssertionWithInternalModule(){
		
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
	
	private Assertion createAssertionWithExternalModule(){
		
		Assertion assertion = new Assertion();
		ExternalAssertion reference = new ExternalAssertion();
		reference.setAssertionProcessorType("XQuery");
		reference.setDescription("Testing");
		reference.setErrorSeverity(AssertionSeverity.ERROR);
		reference.setName("ElementNameEndingWithWordCount Assertion");
		reference.setVersion("1.0");
		
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
	
	private AssertableArtifact createArtifactWithPassingContent(){
	
		AssertableArtifact artifact = new AssertableArtifact();
		ExternalArtifact artifactExternal = new ExternalArtifact();
		artifactExternal.setContentType(ArtifactContentTypes.WSDL);
		artifactExternal.setBinaryContent(getBinaryContent("TestService.wsdl"));
		artifact.setArtifactExternal(artifactExternal);
		
		return artifact;
	}
	
	private AssertableArtifact createArtifactWithBadContent(){
		
		AssertableArtifact artifact = new AssertableArtifact();
		ExternalArtifact artifactExternal = new ExternalArtifact();
		artifactExternal.setContentType(ArtifactContentTypes.WSDL);
		artifactExternal.setBinaryContent(getBinaryContent("WrongSample.wsdl"));
		artifact.setArtifactExternal(artifactExternal);
		
		return artifact;
	}
	
	private AssertableArtifact createNonWSDLArtifact(){
		
		AssertableArtifact artifact = new AssertableArtifact();
		ExternalArtifact artifactExternal = new ExternalArtifact();
		artifactExternal.setContentType(ArtifactContentTypes.WSDL);
		artifactExternal.setBinaryContent(getBinaryContent("test.xq"));
		artifact.setArtifactExternal(artifactExternal);
		
		return artifact;
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
