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

import junit.framework.TestCase;


import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionsRequest;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactAsset;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactContentTypes;
import org.ebayopensource.turmeric.repository.v1.services.AssertableArtifact;
import org.ebayopensource.turmeric.repository.v1.services.Assertion;
import org.ebayopensource.turmeric.repository.v1.services.AssertionAsset;
import org.ebayopensource.turmeric.repository.v1.services.AssertionAssetTypes;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;

/**
 * @author szacharias
 * 
 */
public class TestInternalAssertionAndInternalArtifact extends BaseAssertionsServiceTest {


	public static AssetCreatorIntf assetCreator = AssetCreatorFactory.
	getAssetCreator("resources/assetCreationxmls/Services.xml");
	
	@BeforeClass
	public static void setUpClass() throws Throwable
	{
		try
		{
			assetCreator.createAsset();
		}
		catch (Throwable exception) 
		{
			exception.printStackTrace();
			throw exception;
		}
		
	}
	
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
	
	@Test
	public void testAssertionsRequest_InvalidAssertionVersion() {
		System.out.println("\n***Starting testAssertionsRequest_InvalidAssertionVersion");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		assertion.getAssertionAsset().setVersion(";k&*");
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithFailingContent();
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequest_InvalidAssertionVersion : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequest_WithoutArtifactCategory() {
		System.out.println("\n***Starting testAssertionsRequest_WithoutArtifactCategory");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithFailingContent();
		artifact.getArtifactAssetReference().setArtifactCategory(null);
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequest_WithoutArtifactCategory : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequest_InvalidArtifactCategory() {
		System.out.println("\n***Starting testAssertionsRequest_InvalidArtifactName");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithFailingContent();
		artifact.getArtifactAssetReference().setArtifactCategory(ArtifactContentTypes.XSD);
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequest_InvalidArtifactCategory : "  + result);

		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequest_WithoutArtifactVersion() {
		System.out.println("\n***Starting testAssertionsRequest_WithoutArtifactVersion");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithPassingContent();
		artifact.getArtifactAssetReference().setVersion(null);
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, POSITIVE_CASE);
		System.out.println("testAssertionsRequest_WithoutArtifactVersion : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequest_WithoutArtifactAssetName() {
		System.out.println("\n***Starting testAssertionsRequest_WithoutArtifactAssetName");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithFailingContent();
		artifact.getArtifactAssetReference().setAssetName(null);
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequest_WithoutArtifactAssetName : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequest_WithoutArtifactAssetType() {
		System.out.println("\n***Starting testAssertionsRequest_WithoutArtifactAssetType");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithFailingContent();
		artifact.getArtifactAssetReference().setAssetType(null);
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequest_WithoutArtifactAssetType : "  + result);
		
		TestCase.assertEquals(PASS, result);
	}
	
	@Test
	public void testAssertionsRequest_WithoutArtifactLibraryName() {
		System.out.println("\n***Starting testAssertionsRequest_WithoutArtifactLibraryName");

		ApplyAssertionsRequest request = new ApplyAssertionsRequest();

		Assertion assertion = createAssertion();
		request.getAssertions().add(assertion);
		
		AssertableArtifact artifact = createArtifactWithFailingContent();
		artifact.getArtifactAssetReference().setLibraryName(null);
		request.getArtifacts().add(artifact);
		
		String result = processRequest(request, FAILURE_CASE);
		System.out.println("testAssertionsRequest_WithoutArtifactLibraryName : "  + result);
		
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
		
		AssetInfo assetInfo = null;
		try {
			assetInfo = assetCreator.getAssetAsAssetInfo("InternalServiceAsset1");			
		} catch (AssetInfoNotFoundException e) {
			e.printStackTrace();
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}
		
		if(assetInfo == null)
		{
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
	
	
	private List<AssertableArtifact> createArtifacts(){
		
		List<AssertableArtifact> artifacts  = new ArrayList<AssertableArtifact>();
		
		AssetInfo assetInfo = null;
		try {
			assetInfo = assetCreator.getAssetAsAssetInfo("InternalServiceAsset1");
		} catch (AssetInfoNotFoundException e) {
			e.printStackTrace();
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}
		
		if(assetInfo == null)
		{
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
		
		if(assetInfo == null)
		{
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
	
	private AssertableArtifact createArtifactWithFailingContent(){
		
		AssetInfo assetInfo = null;
		try {
			assetInfo = assetCreator.getAssetAsAssetInfo("InternalServiceAsset2");
		} catch (AssetInfoNotFoundException e) {
			e.printStackTrace();
		} catch (IdNotFoundException e) {
			e.printStackTrace();
		}
		
		if(assetInfo == null)
		{
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
