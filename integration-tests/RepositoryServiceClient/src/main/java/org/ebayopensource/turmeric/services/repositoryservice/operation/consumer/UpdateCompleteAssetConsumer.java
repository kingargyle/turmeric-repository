/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.turmeric.repository.v1.services.Artifact;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfoForUpdate;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v1.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationshipForUpdate;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.Relation;
import org.ebayopensource.turmeric.repository.v1.services.RelationForUpdate;
import org.ebayopensource.turmeric.repository.v1.services.UpdateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class UpdateCompleteAssetConsumer extends BaseRepositoryServiceConsumer {
	private AsyncTurmericRSV1 m_proxy = null;
	private static String assetName   = null;


/*	public static void main(String[] args) {		
		Map<String, String> results = new HashMap<String, String>();


    	results.put("testUpdateCompleteAsset_withAllOptionalInputParams",  	testUpdateCompleteAsset_validInput("withAllOptionalInputParams"));
    	
    	results.put("testUpdateCompleteAsset_noRequest",        		    testUpdateCompleteAsset_invalidInput("noRequest")); 
    	results.put("testUpdateCompleteAsset_invalidLibraryName",           testUpdateCompleteAsset_invalidInput("invalidLibraryName"));
    	results.put("testUpdateCompleteAsset_invalidGroupName",      		testUpdateCompleteAsset_invalidInput("invalidGroupName"));
    	results.put("testUpdateCompleteAsset_invalidAssetId",      			testUpdateCompleteAsset_invalidInput("invalidAssetId"));
    	
		System.out.println("\n updateCompleteAsset Summary");	
		Set<String> methods = results.keySet();
		for (String method : methods) {
			System.out.format("%-60s: %s\n", method, results.get(method)); 		
		}
		System.out.println();
	}

	public static Map<String, String> testCreateAsset()	{
		Map<String, String> results = new HashMap<String, String>();

    	results.put("testUpdateCompleteAsset_withAllOptionalInputParams",  	testUpdateCompleteAsset_validInput("withAllOptionalInputParams"));
    	
		return results;		
	}*/
	
	public static String testUpdateCompleteAsset_validInput(String variant, AssetInfo assetInfo, AssetInfo newTargetAssetInfo) {
		UpdateCompleteAssetConsumer updateCompleteAssetConsumer = new UpdateCompleteAssetConsumer();
		
		java.util.Date date = new java.util.Date();
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library = new Library();
		library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library);
		
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION + "_Updated");
		basicAssetInfo.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		basicAssetInfo.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
		basicAssetInfo.setVersion(assetInfo.getBasicAssetInfo().getVersion());
		basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
		basicAssetInfo.setAssetKey(assetKey);
		
		AssetInfoForUpdate assetInfoForUpdate = new AssetInfoForUpdate();
		assetInfoForUpdate.setBasicAssetInfo(basicAssetInfo);
		
		if(variant.equalsIgnoreCase("noLibraryId")) {
			basicAssetInfo.getAssetKey().getLibrary().setLibraryId(null);
		}
		if(variant.equalsIgnoreCase("noAssetLongDescription")) {
			basicAssetInfo.setAssetLongDescription(null);
		}
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		artifact.setArtifactIdentifier(RepositoryServiceClientConstants.ARTIFACT_ID);
		artifact.setArtifactName("test.wsdl");		
		artifact.setArtifactCategory(RepositoryServiceClientConstants.ARTIFACT_CATEGORY);		
		artifact.setArtifactValueType(ArtifactValueType.valueOf(RepositoryServiceClientConstants.ARTIFACT_VALUE_TYPE_FILE));
		artifactInfo.setArtifact(artifact);
		String temp = "Hello world";
		artifactInfo.setArtifactDetail(temp.getBytes());		
		artifactInfo.setContentType("application/xml");
		assetInfoForUpdate.getArtifactInfo().add(artifactInfo);
		
		List<AttributeNameValue> attributeNameValues = new ArrayList<AttributeNameValue>();
		AttributeNameValue attributeNameValue = new AttributeNameValue();
		attributeNameValue.setAttributeName(RepositoryServiceClientConstants.ATTRIBUTE1_NAME);
		attributeNameValue.setAttributeValueString(RepositoryServiceClientConstants.ATTRIBUTE1_VALUE);
		attributeNameValues.add(attributeNameValue);
		
		ExtendedAssetInfo extendedAssetInfo = new ExtendedAssetInfo();
		extendedAssetInfo.getAttribute().addAll(attributeNameValues);
		assetInfoForUpdate.setExtendedAssetInfo(extendedAssetInfo);
		
		FlattenedRelationshipForUpdate flattenedRelationshipForUpdate = new FlattenedRelationshipForUpdate();
		flattenedRelationshipForUpdate.setDepth(1);
		flattenedRelationshipForUpdate.setSourceAsset(assetKey);
		
		RelationForUpdate relationForUpdate = new RelationForUpdate();		
		AssetKey oldTargetAssetKey = new AssetKey();
		//oldTargetAssetKey.setAssetId("1.0_12460233793651151589764");
		//oldTargetAssetKey.setAssetName("JeganTest_june26_14");
		AssetKey targetAssetKey = new AssetKey();
		targetAssetKey.setAssetId(assetInfo.getFlattenedRelationship().getRelatedAsset().get(0).getTargetAsset().getAssetId());
		targetAssetKey.setAssetName(assetInfo.getFlattenedRelationship().getRelatedAsset().get(0).getTargetAsset().getAssetName());
		
		Library library1 = new Library();
		library1.setLibraryId(assetInfo.getFlattenedRelationship().getRelatedAsset().get(0).getTargetAsset().getLibrary().getLibraryId());
		library1.setLibraryName(assetInfo.getFlattenedRelationship().getRelatedAsset().get(0).getTargetAsset().getLibrary().getLibraryName());
		targetAssetKey.setLibrary(library1);
		relationForUpdate.setCurrentSourceAsset(assetKey);
		relationForUpdate.setCurrentTargetAsset(targetAssetKey);
		
		Relation relation = new Relation();
		relation.setSourceAsset(assetKey);
			
		//newTargetAssetKey.setAssetId("1.0_12478079790812076610647");
		//newTargetAssetKey.setAssetName("JeganTest_july17_01");
		
		AssetKey newTargetAssetKey = new AssetKey();
		newTargetAssetKey.setAssetId(newTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		newTargetAssetKey.setAssetName(newTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library2 = new Library();
		library2.setLibraryId(newTargetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library2.setLibraryName(newTargetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		newTargetAssetKey.setLibrary(library2);
		
		relationForUpdate.setCurrentSourceAsset(assetKey);
		relationForUpdate.setCurrentTargetAsset(targetAssetKey);
		
		relation.setTargetAsset(newTargetAssetKey);
		relation.setAssetRelationship("dependsOn");
		relationForUpdate.setNewRelation(relation);
		
		flattenedRelationshipForUpdate.getRelatedAsset().add(relationForUpdate);
		assetInfoForUpdate.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);
		
		AssetLifeCycleInfo assetLifeCycleInfo = new AssetLifeCycleInfo();
		assetLifeCycleInfo.setApprover("jpnadar");
		assetLifeCycleInfo.setLifeCycleState("Approved");
		assetLifeCycleInfo.setProjectManager("jpnadar");		
		assetInfoForUpdate.setAssetLifeCycleInfo(assetLifeCycleInfo);
		
		UpdateCompleteAssetRequest updateCompleteAssetRequest = new UpdateCompleteAssetRequest();
		updateCompleteAssetRequest.setAssetInfoForUpdate(assetInfoForUpdate);
		updateCompleteAssetRequest.setReplaceCurrent(true);
		
		try	{
			UpdateCompleteAssetResponse updateCompleteAssetResponse = updateCompleteAssetConsumer.getProxy().updateCompleteAsset(updateCompleteAssetRequest);		

			if(updateCompleteAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateUpdateCompleteAssetResponse(updateCompleteAssetResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
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
		

	public static String testUpdateCompleteAsset_invalidInput(String variant, AssetInfo assetInfo, AssetInfo newTargetAssetInfo) {
		UpdateCompleteAssetConsumer updateCompleteAssetConsumer = new UpdateCompleteAssetConsumer();
		
		java.util.Date date = new java.util.Date();
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library = new Library();
		library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library);
		
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION + "_Updated");
		basicAssetInfo.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		basicAssetInfo.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
		basicAssetInfo.setVersion(assetInfo.getBasicAssetInfo().getVersion());
		basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
		basicAssetInfo.setAssetKey(assetKey);
		
		AssetInfoForUpdate assetInfoForUpdate = new AssetInfoForUpdate();
		assetInfoForUpdate.setBasicAssetInfo(basicAssetInfo);
		
		if(variant.equalsIgnoreCase("noLibraryId")) {
			basicAssetInfo.getAssetKey().getLibrary().setLibraryId(null);
		}
		if(variant.equalsIgnoreCase("noAssetLongDescription")) {
			basicAssetInfo.setAssetLongDescription(null);
		}
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		artifact.setArtifactIdentifier(RepositoryServiceClientConstants.ARTIFACT_ID);
		artifact.setArtifactName("test.wsdl");		
		artifact.setArtifactCategory(RepositoryServiceClientConstants.ARTIFACT_CATEGORY);		
		artifact.setArtifactValueType(ArtifactValueType.valueOf(RepositoryServiceClientConstants.ARTIFACT_VALUE_TYPE_FILE));
		artifactInfo.setArtifact(artifact);
		String temp = "Hello world";
		artifactInfo.setArtifactDetail(temp.getBytes());		
		artifactInfo.setContentType("application/xml");
		assetInfoForUpdate.getArtifactInfo().add(artifactInfo);
		
		List<AttributeNameValue> attributeNameValues = new ArrayList<AttributeNameValue>();
		AttributeNameValue attributeNameValue = new AttributeNameValue();
		attributeNameValue.setAttributeName(RepositoryServiceClientConstants.ATTRIBUTE1_NAME);
		attributeNameValue.setAttributeValueString(RepositoryServiceClientConstants.ATTRIBUTE1_VALUE);
		attributeNameValues.add(attributeNameValue);
		
		ExtendedAssetInfo extendedAssetInfo = new ExtendedAssetInfo();
		extendedAssetInfo.getAttribute().addAll(attributeNameValues);
		assetInfoForUpdate.setExtendedAssetInfo(extendedAssetInfo);
		
		FlattenedRelationshipForUpdate flattenedRelationshipForUpdate = new FlattenedRelationshipForUpdate();
		flattenedRelationshipForUpdate.setDepth(1);
		flattenedRelationshipForUpdate.setSourceAsset(assetKey);
		
		RelationForUpdate relationForUpdate = new RelationForUpdate();		
		AssetKey oldTargetAssetKey = new AssetKey();
		//oldTargetAssetKey.setAssetId("1.0_12460233793651151589764");
		//oldTargetAssetKey.setAssetName("JeganTest_june26_14");
		AssetKey targetAssetKey = new AssetKey();
		targetAssetKey.setAssetId(assetInfo.getFlattenedRelationship().getRelatedAsset().get(0).getTargetAsset().getAssetId());
		targetAssetKey.setAssetName(assetInfo.getFlattenedRelationship().getRelatedAsset().get(0).getTargetAsset().getAssetName());
		
		Library library1 = new Library();
		library1.setLibraryId(assetInfo.getFlattenedRelationship().getRelatedAsset().get(0).getTargetAsset().getLibrary().getLibraryId());
		library1.setLibraryName(assetInfo.getFlattenedRelationship().getRelatedAsset().get(0).getTargetAsset().getLibrary().getLibraryName());
		targetAssetKey.setLibrary(library1);
		relationForUpdate.setCurrentSourceAsset(assetKey);
		relationForUpdate.setCurrentTargetAsset(targetAssetKey);
		
		
		Relation relation = new Relation();
		relation.setSourceAsset(assetKey);
		
		//newTargetAssetKey.setAssetId("1.0_12478079790812076610647");
		//newTargetAssetKey.setAssetName("JeganTest_july17_01");
		AssetKey newTargetAssetKey = new AssetKey();
		newTargetAssetKey.setAssetId(newTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		newTargetAssetKey.setAssetName(newTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library2 = new Library();
		library2.setLibraryId(newTargetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library2.setLibraryName(newTargetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		newTargetAssetKey.setLibrary(library2);
		
		relationForUpdate.setCurrentSourceAsset(assetKey);
		relationForUpdate.setCurrentTargetAsset(targetAssetKey);
		relation.setTargetAsset(newTargetAssetKey);
		relation.setAssetRelationship("dependsOn");
		relationForUpdate.setNewRelation(relation);
		
		flattenedRelationshipForUpdate.getRelatedAsset().add(relationForUpdate);
		assetInfoForUpdate.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);
		
		AssetLifeCycleInfo assetLifeCycleInfo = new AssetLifeCycleInfo();
		assetLifeCycleInfo.setApprover("jpnadar");
		assetLifeCycleInfo.setLifeCycleState("Approved");
		assetLifeCycleInfo.setProjectManager("jpnadar");		
		assetInfoForUpdate.setAssetLifeCycleInfo(assetLifeCycleInfo);
		
		UpdateCompleteAssetRequest updateCompleteAssetRequest = new UpdateCompleteAssetRequest();
		updateCompleteAssetRequest.setAssetInfoForUpdate(assetInfoForUpdate);
		updateCompleteAssetRequest.setReplaceCurrent(true);
		
		if(variant.equals("noRequest"))
		{
			updateCompleteAssetRequest = null;
		}
		if(variant.equals("invalidLibraryName"))
		{			
			library.setLibraryName(RepositoryServiceClientConstants.INVALID_LIBRARY_NAME);
		}
		if(variant.equals("invalidAssetId"))
		{
			assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
		}
		
		try	{
			UpdateCompleteAssetResponse updateCompleteAssetResponse = updateCompleteAssetConsumer.getProxy().updateCompleteAsset(updateCompleteAssetRequest);		

			if(updateCompleteAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateUpdateCompleteAssetResponse(updateCompleteAssetResponse, "negativeCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
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
	
	private static String validateUpdateCompleteAssetResponse(UpdateCompleteAssetResponse updateCompleteAssetResponse, String criteria) {
    	if(criteria.equalsIgnoreCase("positiveCase")) {
    		if(updateCompleteAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
				return RepositoryServiceConsumerUtil.validateAssetKey(updateCompleteAssetResponse.getAssetKey());
    		}
    		return RepositoryServiceClientConstants.FAILURE;
    	}
    	if(criteria.equalsIgnoreCase("negativeCase")) {
    		if(updateCompleteAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
    			if(updateCompleteAssetResponse.getErrorMessage().getError().size() > 0) {
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

