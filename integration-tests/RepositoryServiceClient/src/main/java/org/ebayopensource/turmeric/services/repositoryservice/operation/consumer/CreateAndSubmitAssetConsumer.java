/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.turmeric.repository.v1.services.Artifact;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v1.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.CreateAndSubmitAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateAndSubmitAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationship;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.Relation;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class CreateAndSubmitAssetConsumer extends BaseRepositoryServiceConsumer {
	
	private static CreateAndSubmitAssetConsumer createAndSubmitAssetConsumer = new CreateAndSubmitAssetConsumer();
	
	private AsyncTurmericRSV1 m_proxy = null;
	private static String assetName=null;
	public static String testCreateAndSubmitAsset_validInput(String variant, AssetInfo assetInfo1) {
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
		library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
		
		Library invalidlibrary = new Library();
		invalidlibrary.setLibraryId(RepositoryServiceClientConstants.INVALID_LIBRARY_ID);
		invalidlibrary.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
		java.util.Date date = new java.util.Date();
		assetName = "Test" + date.getTime();// RepositoryServiceClientConstants.ASSET_NAME + date.getTime();
		System.out.println("assetName :" + assetName);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetName(assetName);
		assetKey.setLibrary(library);
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription("Test"/*RepositoryServiceClientConstants.ASSET_DESCRIPTION*/);
		basicAssetInfo.setAssetName(assetName);
		basicAssetInfo.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
		basicAssetInfo.setVersion(RepositoryServiceClientConstants.ASSET_VERSION);
		basicAssetInfo.setAssetLongDescription("Test"/*RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION*/);
		basicAssetInfo.setAssetKey(assetKey);
		basicAssetInfo.setGroupName("Enterprise Group");
		AssetInfo assetInfo = new AssetInfo();
		assetInfo.setBasicAssetInfo(basicAssetInfo);
		
		if(variant.equalsIgnoreCase("noLibraryId")) {
			basicAssetInfo.getAssetKey().getLibrary().setLibraryId(null);
		}else if(variant.equalsIgnoreCase("invalidLibrary")) {
					basicAssetInfo.getAssetKey().setLibrary(invalidlibrary);
		}		
		else if(variant.equalsIgnoreCase("noAssetLongDescription")) {
			basicAssetInfo.setAssetLongDescription(null);
		}
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		//artifact.setArtifactIdentifier(RepositoryServiceClientConstants.ARTIFACT_ID);
		artifact.setArtifactName("test.wsdl");		
		artifact.setArtifactCategory(RepositoryServiceClientConstants.ARTIFACT_CATEGORY);		
		artifact.setArtifactValueType(ArtifactValueType.valueOf(RepositoryServiceClientConstants.ARTIFACT_VALUE_TYPE_FILE));
		artifactInfo.setArtifact(artifact);
		String temp = "";
		StringBuilder stringBuilder = new StringBuilder();
		File file = new File("resource/test.wsdl");
		try 
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			temp = bufferedReader.readLine();
			while(temp!=null)
			{
				
				stringBuilder.append(temp);
				temp = bufferedReader.readLine();
			}
			
			//BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		artifactInfo.setArtifactDetail(stringBuilder.toString().getBytes());		
		artifactInfo.setContentType("application/xml");
		assetInfo.getArtifactInfo().add(artifactInfo);
		
		List<AttributeNameValue> attributeNameValues = new ArrayList<AttributeNameValue>();
		AttributeNameValue attributeNameValue = new AttributeNameValue();
		attributeNameValue.setAttributeName(RepositoryServiceClientConstants.ATTRIBUTE1_NAME);
		attributeNameValue.setAttributeValueString(RepositoryServiceClientConstants.ATTRIBUTE1_VALUE);
		attributeNameValues.add(attributeNameValue);
		
		//if(variant.equals("withMandatoryClassifiers")){
			AttributeNameValue attributeNameValue1 = new AttributeNameValue();
			attributeNameValue1.setAttributeName("manager-owner");
			attributeNameValue1.setAttributeValueString("acarnell");
			attributeNameValues.add(attributeNameValue1);
		//}
			
			AttributeNameValue attributeNameValue2 = new AttributeNameValue();
			attributeNameValue2.setAttributeName("lifecycle_state");
			attributeNameValue2.setAttributeValueString("Proposed");
			attributeNameValues.add(attributeNameValue2);
			
			AttributeNameValue attributeNameValue3 = new AttributeNameValue();
			attributeNameValue3.setAttributeName("source_control_system");
			attributeNameValue3.setAttributeValueString("Others");
			attributeNameValues.add(attributeNameValue3);
			
			AttributeNameValue attributeNameValue4 = new AttributeNameValue();
			attributeNameValue4.setAttributeName("service_visibility");
			attributeNameValue4.setAttributeValueString("Internal");
			attributeNameValues.add(attributeNameValue4);
			
			AttributeNameValue attributeNameValue5 = new AttributeNameValue();
			attributeNameValue5.setAttributeName("service_layer");
			attributeNameValue5.setAttributeValueString("BUSINESS");
			attributeNameValues.add(attributeNameValue5);
		
		ExtendedAssetInfo extendedAssetInfo = new ExtendedAssetInfo();
		extendedAssetInfo.getAttribute().addAll(attributeNameValues);
		assetInfo.setExtendedAssetInfo(extendedAssetInfo);
		
		FlattenedRelationship flattenedRelationship = new FlattenedRelationship();
		flattenedRelationship.setDepth(1);
		flattenedRelationship.setPartial(false);
		flattenedRelationship.setSourceAsset(assetKey);
		Relation relation = new Relation();
		relation.setSourceAsset(assetKey);
		AssetKey targetAsset = new AssetKey();
		targetAsset.setLibrary(library);
		targetAsset.setAssetId(assetInfo1.getBasicAssetInfo().getAssetKey().getAssetId());
		targetAsset.setAssetName(assetInfo1.getBasicAssetInfo().getAssetKey().getAssetName());
		//targetAsset.setAssetName("catalog");
		relation.setTargetAsset(targetAsset);
		relation.setAssetRelationship("functionalDomain");
		flattenedRelationship.getRelatedAsset().add(relation);
		assetInfo.setFlattenedRelationship(flattenedRelationship);
		
		AssetLifeCycleInfo assetLifeCycleInfo = new AssetLifeCycleInfo();
		assetLifeCycleInfo.setApprover("szacharias");
		assetLifeCycleInfo.setLifeCycleState("Proposed");
		assetLifeCycleInfo.setProjectManager("szacharias");		
		assetInfo.setAssetLifeCycleInfo(assetLifeCycleInfo);
		
		CreateAndSubmitAssetRequest createAndSubmitAssetRequest = new CreateAndSubmitAssetRequest();
		createAndSubmitAssetRequest.setAssetInfo(assetInfo);
		createAndSubmitAssetRequest.setCaptureTemplateName(RepositoryServiceClientConstants.VALID_SERVICE_CAPTURE_TEMPLATE);
			
		try	{
			CreateAndSubmitAssetResponse createAndSubmitAssetResponse = createAndSubmitAssetConsumer.getProxy().createAndSubmitAsset(createAndSubmitAssetRequest);		

			if(createAndSubmitAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateCreateAndSubmitAssetResponse(createAndSubmitAssetResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.getMessage();
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
		}			
	}
	
	public static String testCreateAndSubmitAsset_FDAssetType() {
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
		library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
		java.util.Date date = new java.util.Date();
		assetName = "FunctionalDomain_" + date.getTime();
		System.out.println("assetName" + assetName);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetName(assetName);
		assetKey.setLibrary(library);
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION);
		basicAssetInfo.setAssetName(assetName);
		basicAssetInfo.setAssetType("Functional Domain");
		basicAssetInfo.setGroupName("Advertising");
		basicAssetInfo.setVersion(RepositoryServiceClientConstants.ASSET_VERSION);
		basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
		basicAssetInfo.setAssetKey(assetKey);
		
		AssetInfo assetInfo = new AssetInfo();
		assetInfo.setBasicAssetInfo(basicAssetInfo);

		List<AttributeNameValue> attributeNameValues = new ArrayList<AttributeNameValue>();
		AttributeNameValue attributeNameValue = new AttributeNameValue();
		attributeNameValue.setAttributeName(RepositoryServiceClientConstants.ATTRIBUTE1_NAME);
		attributeNameValue.setAttributeValueString(RepositoryServiceClientConstants.ATTRIBUTE1_VALUE);
		attributeNameValues.add(attributeNameValue);
		
		AttributeNameValue attributeNameValue1 = new AttributeNameValue();
		attributeNameValue1.setAttributeName("manager-owner");
		attributeNameValue1.setAttributeValueString("abkumar");
		attributeNameValues.add(attributeNameValue1);
		AttributeNameValue attributeNameValue2 = new AttributeNameValue();
		attributeNameValue2.setAttributeName("domain_string");
		attributeNameValue2.setAttributeValueString("marketplace");
		attributeNameValues.add(attributeNameValue2);
		AttributeNameValue attributeNameValue3 = new AttributeNameValue();
		attributeNameValue3.setAttributeName("architect");
		attributeNameValue3.setAttributeValueString("ssooriyan");
		attributeNameValues.add(attributeNameValue3);
		
		ExtendedAssetInfo extendedAssetInfo = new ExtendedAssetInfo();
		extendedAssetInfo.getAttribute().addAll(attributeNameValues);
		assetInfo.setExtendedAssetInfo(extendedAssetInfo);
		
		CreateAndSubmitAssetRequest createAndSubmitAssetRequest = new CreateAndSubmitAssetRequest();
		createAndSubmitAssetRequest.setAssetInfo(assetInfo);
		createAndSubmitAssetRequest.setCaptureTemplateName("Functional Domain Template");
		createAndSubmitAssetRequest.setComment("Creation Comments");
		
		try	{
			CreateAndSubmitAssetResponse createAndSubmitAssetResponse = createAndSubmitAssetConsumer.getProxy().createAndSubmitAsset(createAndSubmitAssetRequest);		

			if(createAndSubmitAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateCreateAndSubmitAssetResponse(createAndSubmitAssetResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.getMessage();
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
		}			
	}
	
	public static String testCreateAndSubmitAsset_nullRequest() {
		CreateAndSubmitAssetRequest createAndSubmitAssetRequest = null;
		
		try	{
			CreateAndSubmitAssetResponse createAndSubmitAssetResponse = createAndSubmitAssetConsumer.getProxy().createAndSubmitAsset(createAndSubmitAssetRequest);		

			if(createAndSubmitAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateCreateAndSubmitAssetResponse(createAndSubmitAssetResponse, "negativeCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.getMessage();
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
		}			
	}
	
	private static String validateCreateAndSubmitAssetResponse(CreateAndSubmitAssetResponse createAndSubmitAssetResponse, String criteria) {
    	if(criteria.equalsIgnoreCase("positiveCase")) {
    		if(createAndSubmitAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
				return RepositoryServiceConsumerUtil.validateAssetKey(createAndSubmitAssetResponse.getAssetKey());
    		}
    		return RepositoryServiceClientConstants.FAILURE;
    	}
    	if(criteria.equalsIgnoreCase("negativeCase")) {
    		if(createAndSubmitAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
    			if(createAndSubmitAssetResponse.getErrorMessage().getError().size() > 0) {
    				return RepositoryServiceClientConstants.SUCCESS;
    			}
    		}   		
    	}
    	
    	return RepositoryServiceClientConstants.FAILURE;
	}
	
    protected AsyncTurmericRSV1 getProxy() throws ServiceException {
    	if(m_proxy == null) {
	        String svcAdminName = RepositoryServiceClientConstants.SERVICE_NAME;
	        Service service = ServiceFactory.create(svcAdminName, RepositoryServiceClientConstants.SERVICE_NAME);
	        service.setSessionTransportHeader("X-TURMERIC-SECURITY-USERID", RepositoryServiceClientConstants.USER_ID);
	        service.setSessionTransportHeader("X-TURMERIC-SECURITY-PASSWORD", RepositoryServiceClientConstants.USER_PASSWORD);
	        
	        m_proxy = service.getProxy();
    	} 	
        
	    return m_proxy;
    }
}
