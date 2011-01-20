/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import java.util.List;

import org.apache.log4j.Logger;

import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionGroupsResponse;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionsResponse;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactAsset;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactReport;
import org.ebayopensource.turmeric.repository.v1.services.AssertableArtifactReference;
import org.ebayopensource.turmeric.repository.v1.services.AssertionGroup;
import org.ebayopensource.turmeric.repository.v1.services.AssertionGroupMember;
import org.ebayopensource.turmeric.repository.v1.services.AssertionGroupReport;
import org.ebayopensource.turmeric.repository.v1.services.AssertionResult;
import org.ebayopensource.turmeric.repository.v1.services.AssertionResultItem;
import org.ebayopensource.turmeric.repository.v1.services.AssetReference;
import org.ebayopensource.turmeric.repository.v1.services.ExternalArtifactReference;
import org.ebayopensource.turmeric.repository.v1.services.ValidationResult;

/**
 * @author szacharias
 */
public class AssertionsTestUtil {
	
	private static Logger logger = Logger.getLogger("AssertionsTestUtil"); 
	
	public static boolean validateStructure(ApplyAssertionsResponse response){
		
		boolean isValidApplyAssertionsResponse = true;
		
		if(response != null){
			if(response.getAssertionReport() != null){
				
				List<ArtifactReport> artifactReports = response.getAssertionReport().getArtifactReport();
				
				if(artifactReports == null || artifactReports.isEmpty()){
					logger.info("ArttifactReports is empty");
					isValidApplyAssertionsResponse = false;
				
				}else{
					for(ArtifactReport artifactReport: artifactReports){
						if(!validateArtifactReport(artifactReport)){
							isValidApplyAssertionsResponse = false;
						}
					}
				}
			}else{
				logger.info("AssertionReport is null");
				isValidApplyAssertionsResponse = false;
			}
			
		}else{
			logger.info("ApplyAssertionsResponse is null");
			isValidApplyAssertionsResponse = false;
		}
		
		return isValidApplyAssertionsResponse;
	}
	
	public static boolean validateStructure(ApplyAssertionGroupsResponse response){
		
		boolean isValidApplyAssertionGroupsResponse = true;
		
		if(response != null){
			if(response.getAssertionGroupReport() != null){
				List<AssertionGroupReport> assertionGroupReports = response.getAssertionGroupReport();
				if(assertionGroupReports == null || assertionGroupReports.isEmpty()){
					logger.info("AssertionGroupReport list is not present");
					isValidApplyAssertionGroupsResponse = false;
				}else{
					for(AssertionGroupReport assertionGroupReport :assertionGroupReports){
						if(!validateAssertionGroupReport(assertionGroupReport)){
							isValidApplyAssertionGroupsResponse = false;
						}
					}
				}
			}else{
				logger.info("AssertionGroupReport is null");
				isValidApplyAssertionGroupsResponse = false;
			}
			
		}else{
			logger.info("ApplyAssertionGroupsResponse is null");
			isValidApplyAssertionGroupsResponse = false;
		}
		
		return isValidApplyAssertionGroupsResponse;
	}
	
	public static boolean validateAssertionGroupReport(AssertionGroupReport assertionGroupReport){
		
		boolean isValidAssertionGroupReport = true;
		
		if(assertionGroupReport != null){
			//TOTO : Not implemented currently
			/*if(assertionGroupReport.getGroupLevelSeverity() == null){
				logger.info("AssertionGroupLevelSeverity is null");
				isValidAssertionGroupReport = false;
			}*/
			
			List<ArtifactReport> artifactReports = assertionGroupReport.getArtifactReport();
			if(artifactReports== null || artifactReports.isEmpty()){
				logger.info("ArttifactReports is empty");
				isValidAssertionGroupReport = false;
			}else{
				for(ArtifactReport artifactReport: artifactReports){
					if(!validateArtifactReport(artifactReport)){
						isValidAssertionGroupReport = false;
					}
				}
			}
			
			if(!validateAssertionGroup(assertionGroupReport.getAssertionGroup())){
				isValidAssertionGroupReport = false;
			}
			
		}else{
			logger.info("AssertionGroupReport name is null");
			isValidAssertionGroupReport = false;
		}
		
		return isValidAssertionGroupReport;
	}
	
	public static boolean validateArtifactReport(ArtifactReport artifactReport){

		boolean isValidArtifactReport = true;
		
		if(artifactReport != null){
			if(!validateAssertableArtifactReference(artifactReport.getArtifact())){
				isValidArtifactReport = false;
			}
			 List<AssertionResult> assertionResults = artifactReport.getAssertionResults();
			 if(assertionResults == null || assertionResults.isEmpty()){
				 logger.info("AssertionResults is not present");
			 	 isValidArtifactReport = false;
			 }else{
				 for(AssertionResult assertionResult  :assertionResults){
					 if(!validateAssertionResult(assertionResult)){
						 isValidArtifactReport = false;
					 }
				 }
			 }
			
		}else{
			logger.info("ArtifactReport name is null");
			isValidArtifactReport = false;
		}
		
		return isValidArtifactReport;
		
	}
	
	public static boolean validateAssertionGroup(AssertionGroup assertionGroup){
		boolean isValidAssertionGroup = true;
		
		if(assertionGroup != null){
			if(assertionGroup.getName() == null){
				logger.info("AssertionGroup name is null");
				isValidAssertionGroup = false;
			}
			if(assertionGroup.getDescription() == null){
				logger.info("AssertionGroup description is null");
				isValidAssertionGroup = false;
			}
			if(assertionGroup.getVersion() == null){
				logger.info("AssertionGroup version is null");
				isValidAssertionGroup = false;
			}
			
			for(AssertionGroupMember assertionGroupMember :assertionGroup.getGroupMembers()) {
				if(!validateAssertionGroupMember(assertionGroupMember)){
					isValidAssertionGroup = false;
				}
			}
			
		}else{
			logger.info("assertionGroup is null");
			isValidAssertionGroup = false;
		}
		
		return isValidAssertionGroup;
		
		
	}
	
	public static boolean validateAssertionGroupMember(AssertionGroupMember assertionGroupMember){
		
		boolean isValidAssertionGroupMember = true;
		
		if(assertionGroupMember != null){
			AssetReference assetReference = assertionGroupMember.getAssertionGroupAssetReference();
			if(assetReference != null){
				if(assetReference.getAssetName() == null){
					logger.info("AssetName is null");
					isValidAssertionGroupMember = false;
				}
				if(assetReference.getAssetType() == null){
					logger.info("AssetType is null");
					isValidAssertionGroupMember = false;
				}
				if(assetReference.getLibraryName() == null){
					logger.info("LibraryName is null");
					isValidAssertionGroupMember = false;
				}
				if(assetReference.getVersion() == null){
					logger.info("Version is null");
					isValidAssertionGroupMember = false;
				}
				
			}else{
				logger.info("AssetReference is null");
				isValidAssertionGroupMember = false;
			}
			
		}else{
			logger.info("AssertionGroupMember is null");
			isValidAssertionGroupMember = false;
		}
		
		return isValidAssertionGroupMember;
	}
	
	public static boolean validateAssertableArtifactReference(AssertableArtifactReference assertableArtifactReference){
		
		boolean isValidAssertableArtifactReference = true;
		if(assertableArtifactReference != null){
			if(assertableArtifactReference.getArtifactAsset() == null && assertableArtifactReference.getArtifactExternal() ==null){
				logger.info("AssertableArtifactReference does not contain any internal/external artifact details");
				isValidAssertableArtifactReference = false;
			}
			
			if(assertableArtifactReference.getArtifactAsset() != null){
				ArtifactAsset artifactAsset =assertableArtifactReference.getArtifactAsset(); 
				if(artifactAsset.getAssetName() == null){
					logger.info("ArtifactAsset's AssetName is null");
					isValidAssertableArtifactReference = false;
				}
				if(artifactAsset.getAssetType() == null){
					logger.info("ArtifactAsset's AssetType is null");
					isValidAssertableArtifactReference = false;
				}
				if(artifactAsset.getArtifactCategory() == null){
					logger.info("ArtifactAsset's ArtifactCategory is null");
					isValidAssertableArtifactReference = false;
				}
				if(artifactAsset.getLibraryName() == null){
					logger.info("ArtifactAsset's LibraryName is null");
					isValidAssertableArtifactReference = false;
				}
				//Artifact version can be null
				/*if(artifactAsset.getVersion() == null){
					logger.info("ArtifactAsset's Version is null");
					isValidAssertableArtifactReference = false;
				}*/
			}
			if(assertableArtifactReference.getArtifactExternal() != null){
				ExternalArtifactReference externalArtifactReference = assertableArtifactReference.getArtifactExternal();
				if(externalArtifactReference.getContentName() == null){
					logger.info("ExternalArtifactReference's ContentName is null");
					isValidAssertableArtifactReference = false;
				}
			}
			
		}else{
			logger.info("AssertableArtifactReference is null");
			isValidAssertableArtifactReference = false;
		}
		
		return isValidAssertableArtifactReference;
	}
	
	public static boolean validateAssertionResult(AssertionResult assertionResult){
		
		boolean isValidAssertionResult = true;
		
		if(assertionResult != null){
			if(assertionResult.getAssertionName() == null ){
				logger.info("AssertionName is null");
				isValidAssertionResult = false;
			}
			if(assertionResult.getAssertionSeverity() == null ){
				logger.info("AssertionSeverity is null");
				isValidAssertionResult = false;
			}
			if(assertionResult.getAssertionDescription() == null ){
				logger.info("AssertionDescription is null");
				isValidAssertionResult = false;
			}
			if(assertionResult.getAssertionVersion() == null ){
				logger.info("AssertionVersion is null");
				isValidAssertionResult = false;
			}
			if(assertionResult.getValidationResult() == null ){
				logger.info("ValidationResult is null");
				isValidAssertionResult = false;
			}
			if(assertionResult.getValidationResult() != null && assertionResult.getValidationResult() == ValidationResult.FAILED){
				List<AssertionResultItem> resultItems = assertionResult.getResultItems();
				if(resultItems == null || resultItems.isEmpty()){
					logger.info("ResultItems is null/empty");
					isValidAssertionResult = false;
				}else{
					for(AssertionResultItem assertionResultItem : resultItems){
						if(!validateAssertionResultItem(assertionResultItem)){
							isValidAssertionResult = false;
						}
					}
				}
			}
			
		}else{
			logger.info("AssertionResult is null");
			isValidAssertionResult = false;
		}
		
		return isValidAssertionResult;
	}

	public static boolean validateAssertionResultItem(AssertionResultItem assertionResultItem){
		
		boolean isValidAssertionResultItem = true;
		
		if(assertionResultItem != null){
			if(assertionResultItem.getMessageText() == null){
				logger.info("AssertionResultItem MessageText is null");
				isValidAssertionResultItem = false;
			}
		}else{
			logger.info("AssertionResultItem is null");
			isValidAssertionResultItem = false;
		}
		
		return isValidAssertionResultItem;
	}
}