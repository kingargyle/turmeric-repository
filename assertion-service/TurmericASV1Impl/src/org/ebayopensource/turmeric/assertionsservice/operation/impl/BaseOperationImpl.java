/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assertionsservice.operation.impl;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.errorlibrary.assertion.ErrorConstants;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactAsset;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactReport;
import org.ebayopensource.turmeric.repository.v1.services.AssertableArtifact;
import org.ebayopensource.turmeric.repository.v1.services.AssertableArtifactReference;
import org.ebayopensource.turmeric.repository.v1.services.Assertion;
import org.ebayopensource.turmeric.repository.v1.services.AssertionModule;
import org.ebayopensource.turmeric.repository.v1.services.AssertionModuleAsset;
import org.ebayopensource.turmeric.repository.v1.services.AssertionSeverity;
import org.ebayopensource.turmeric.repository.v1.services.AssetReference;
import org.ebayopensource.turmeric.repository.v1.services.ContentBindingName;
import org.ebayopensource.turmeric.repository.v1.services.ExternalArtifact;
import org.ebayopensource.turmeric.repository.v1.services.ExternalArtifactReference;
import org.ebayopensource.turmeric.repository.v1.services.ExternalAssertion;
import org.ebayopensource.turmeric.repository.v1.services.ExternalAssertionModule;
import org.ebayopensource.turmeric.repository.v1.services.ExternalContent;
import org.ebayopensource.turmeric.services.assertionsservice.exception.ObjectNotFoundException;


import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionProcessor;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionResultItem;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssetContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.BinaryContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.StringContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.URLContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.ValidationResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.BasicAssertionModule;
import org.ebayopensource.turmeric.runtime.common.exceptions.ErrorDataFactory;


public class BaseOperationImpl 
{
    
    protected void convert(Object inputObject, Object outputObject) throws ObjectNotFoundException
    {
	if (outputObject != null && inputObject != null)
	{
	    if (inputObject instanceof AssertableArtifact)
	    {
		AssertableArtifact assertableArtifact = (AssertableArtifact) inputObject;
		if (outputObject instanceof AssertableArtifactReference)
		{
		    AssertableArtifactReference assertableArtifactReference = (AssertableArtifactReference) outputObject;
		    if (assertableArtifact.getArtifactAssetReference() != null)
		    {
			assertableArtifactReference.setArtifactAsset(assertableArtifact.getArtifactAssetReference());
		    } else if (assertableArtifact.getArtifactExternal() != null)
		    {
			ExternalArtifactReference externalArtifactReference = new ExternalArtifactReference();
			convert(assertableArtifact.getArtifactExternal(), externalArtifactReference);
			assertableArtifactReference.setArtifactExternal(externalArtifactReference);
		    }
		}
	    } else if (inputObject instanceof ExternalArtifact)
	    {
		ExternalArtifact externalArtifact = (ExternalArtifact) inputObject;
		if (outputObject instanceof ExternalArtifactReference)
		{
		    ExternalArtifactReference externalArtifactReference = (ExternalArtifactReference) outputObject;
		    externalArtifactReference.setContentName(ContentBindingName.FILE_PATH.value());
		}
	    } else if (inputObject instanceof org.ebayopensource.turmeric.repositorymanager.assertions.AssertionReport)
	    {
		org.ebayopensource.turmeric.repositorymanager.assertions.AssertionReport apiAssertionReport = (org.ebayopensource.turmeric.repositorymanager.assertions.AssertionReport) inputObject;
		if (outputObject instanceof ArtifactReport)
		{
		    ArtifactReport wsdlArtifactReport = (ArtifactReport) outputObject;
		    for (AssertionResult assertionResult : apiAssertionReport.getAssertionResults())
		    {
		    org.ebayopensource.turmeric.repository.v1.services.AssertionResult wsdlAssertionResult = (org.ebayopensource.turmeric.repository.v1.services.AssertionResult) convert(assertionResult);
			if(assertionResult.getValidationResult().equals(ValidationResult.ERROR))
			{
				wsdlAssertionResult.setAssertionSeverity(AssertionSeverity.ERROR);
			}
			if(assertionResult.getValidationResult().equals(ValidationResult.MUST))
			{
				wsdlAssertionResult.setAssertionSeverity(AssertionSeverity.MUST);
			}			
			else if(assertionResult.getValidationResult().equals(ValidationResult.WARNING))
			{
				wsdlAssertionResult.setAssertionSeverity(AssertionSeverity.WARNING);
			}
			if(assertionResult.getValidationResult().equals(ValidationResult.SHOULD))
			{
				wsdlAssertionResult.setAssertionSeverity(AssertionSeverity.SHOULD);
			}
			if(assertionResult.getValidationResult().equals(ValidationResult.MAY))
			{
				wsdlAssertionResult.setAssertionSeverity(AssertionSeverity.MAY);
			}
			else if(assertionResult.getValidationResult().equals(ValidationResult.PASSED))
			{
				wsdlAssertionResult.setAssertionSeverity(AssertionSeverity.WARNING);
			}
			wsdlArtifactReport.getAssertionResults().add(wsdlAssertionResult);
		    }
		}
	    }
	}
    }
	
	protected boolean validateAssertion(Assertion assertionInput,List<CommonErrorData> errorDataList)
	{
		boolean isValid = true;
		//boolean isWarning = false;
		boolean foundContent = false;
		if(assertionInput.getExternalAssertion() != null)
		{
			foundContent = true;
			ExternalAssertion externalAssertion = assertionInput.getExternalAssertion();
			if(externalAssertion.getName() == null)
			{
				isValid = false;
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_NAME_MISSING, ErrorConstants.ERRORDOMAIN));
			}
			if(externalAssertion.getErrorSeverity() == null)
			{
				isValid = false;
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_ERROR_SEVERITY_MISSING, 
																				ErrorConstants.ERRORDOMAIN));
			}
			if(!validateExternalContent(externalAssertion.getAssertionScript(), errorDataList))
			{
				isValid = false;
			}
			if(externalAssertion.getAssertionProcessorType() == null)
			{
				isValid = false;
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_ERROR_SEVERITY_MISSING, 
																				ErrorConstants.ERRORDOMAIN));
			}
			if(externalAssertion.getAssertionModules() != null)
			{
				for(AssertionModule assertionModule : externalAssertion.getAssertionModules())
				{
					if(!validateAssertionModule(assertionModule,errorDataList))
					{
						isValid = false;
					}
				}
			}
		}
		if(assertionInput.getAssertionAsset() != null)
		{
			if(foundContent)
			{
				//isWarning = true;
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MULTIPLE_CONTENT_TAG_POPULATION_WARNING, 
																			ErrorConstants.ERRORDOMAIN));
			}
			foundContent = true;
			
		}
		if(!foundContent)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_CONTENT_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		return isValid;
	}
	
	protected boolean validateExternalContent(ExternalContent contentExternal,List<CommonErrorData> errorDataList)
	{
		boolean isValid = true;
		boolean foundContent = false;
		
		if(contentExternal.getBinaryContent()!= null)
		{
			if(foundContent)
			{
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MULTIPLE_CONTENT_TAG_POPULATION_WARNING, 
																				ErrorConstants.ERRORDOMAIN));
			}
			foundContent = true;

			if(contentExternal.getBinaryContent().getContent() == null || contentExternal.getBinaryContent().getContent().length<=0)
			{
				isValid = false;
				String[] params = new String[]{"a BinaryContent"};
				
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_OBJECT_CONTENT_MISSING, 
										ErrorConstants.ERRORDOMAIN, params));
			}
		}
		if(contentExternal.getStringContent()!= null)
		{
			if(foundContent)
			{
				//isWarning = true;
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MULTIPLE_CONTENT_TAG_POPULATION_WARNING, 
																			ErrorConstants.ERRORDOMAIN));
			}
			foundContent = true;
			/*if(contentExternal.getStringContent().getContentName() == null)
			{
				isValid = false;
				errorDataList.add(AssertionsServiceErrorDescriptor.ASSERTION_CONTENT_NAME_MISSING.newError());
			}*/
			if(contentExternal.getStringContent().getContent() == null)
			{
				isValid = false;
				String[] params = new String[]{"a StringContent"};
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_OBJECT_CONTENT_MISSING, 
						ErrorConstants.ERRORDOMAIN,params));
			}
		}
		if(contentExternal.getUrlContent()!= null)
		{
			if(foundContent)
			{
				//isWarning = true;
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MULTIPLE_CONTENT_TAG_POPULATION_WARNING, 
																			ErrorConstants.ERRORDOMAIN));
			}
			foundContent = true;
			/*if(contentExternal.getUrlContent().getContentName() == null)
			{
				isValid = false;
				errorDataList.add(AssertionsServiceErrorDescriptor.ASSERTION_CONTENT_NAME_MISSING.newError());
			}*/
			if(contentExternal.getUrlContent().getUrl() == null)
			{
				isValid = false;
				String[] params = new String[]{"a URLContent"};
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_OBJECT_CONTENT_MISSING, 
								ErrorConstants.ERRORDOMAIN,params));
			}
		}
		if(!foundContent)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_CONTENT_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		
		return isValid;
	}
	
	protected boolean validateAssertionModule(AssertionModule assertionModule,List<CommonErrorData> errorDataList) 
	{
		boolean isValid = true;
		
		if(assertionModule.getAssertionModuleExternal()!= null)
		{
			if(!validateExternalAssertionModule(assertionModule.getAssertionModuleExternal(), errorDataList))
			{
				isValid = false;
			}
		}
		else if(assertionModule.getModuleAssetReference()!= null)
		{
			if(!validateAssertionModuleAsset(assertionModule.getModuleAssetReference(), errorDataList))
			{
				isValid = false;
			}
		}
		else
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MODULE_SCRIPTS_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		return isValid;
	}

	protected boolean validateAssertionModuleAsset(AssertionModuleAsset assetReference,List<CommonErrorData> errorDataList)
	{
		boolean isValid = true;
		if(assetReference.getAssetName()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSET_NAME_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		if(assetReference.getAssetType()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSET_TYPE_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		if(assetReference.getLibraryName()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.LIBRARY_NAME_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		return isValid;	
	}
	
	protected boolean validateAssetReference(AssetReference assetReference,List<CommonErrorData> errorDataList)
	{
		boolean isValid = true;
		if(assetReference.getAssetName()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSET_NAME_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		if(assetReference.getAssetType()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSET_TYPE_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		if(assetReference.getLibraryName()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.LIBRARY_NAME_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		return isValid;	
	}
	
	protected boolean validateArtifactAsset(ArtifactAsset artifactAsset,List<CommonErrorData> errorDataList)
	{
		boolean isValid = true;
		if(artifactAsset.getAssetName()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSET_NAME_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		if(artifactAsset.getAssetType()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSET_TYPE_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		if(artifactAsset.getLibraryName()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.LIBRARY_NAME_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		if(artifactAsset.getArtifactCategory()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ARTIFACT_CATEGORY_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		return isValid;	
	}
	
	protected boolean validateExternalAssertionModule(ExternalAssertionModule externalAssertionModule,List<CommonErrorData> errorDataList)
	{
		boolean isValid = true;
		if(externalAssertionModule.getName()==null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_NAME_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		if(externalAssertionModule.getAssertionProcessorType()== null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_PROCESSOR_TYPE, ErrorConstants.ERRORDOMAIN));
		}
		if(externalAssertionModule.getModuleScripts()!= null && externalAssertionModule.getModuleScripts().size()>0)
		{
			for(ExternalContent contentExternal : externalAssertionModule.getModuleScripts())
			{
				if(!validateExternalContent(contentExternal,errorDataList))
				{
					isValid = false;
				}
			}
		}
		return isValid;
	}
	
	 protected boolean validateExternalArtifact(ExternalArtifact externalArtifact,
				List<CommonErrorData> errorDataList) 
		 {
			 boolean isValid = true;
				//boolean isWarning = false;
				boolean foundContent = false;
				
				if(externalArtifact.getBinaryContent()!= null)
				{
					if(foundContent)
					{
						//isWarning = true;
						errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MULTIPLE_CONTENT_TAG_POPULATION_WARNING, 
																					ErrorConstants.ERRORDOMAIN));
					}
					foundContent = true;
					if(externalArtifact.getBinaryContent().getContent() == null || externalArtifact.getBinaryContent().getContent().length<=0)
					{
						isValid = false;
						String[] params = new String[]{"a BinaryContent"};
						errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_OBJECT_CONTENT_MISSING, 
								ErrorConstants.ERRORDOMAIN, params));
					}
				}
				if(externalArtifact.getTextContent()!= null)
				{
					if(foundContent)
					{
						//isWarning = true;
						errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MULTIPLE_CONTENT_TAG_POPULATION_WARNING, 
																				ErrorConstants.ERRORDOMAIN));
					}
					foundContent = true;
					/*if(artifactExternal.getTextContent().getContentName() == null)
					{
						isValid = false;
						errorDataList.add(AssertionsServiceErrorDescriptor.ASSERTION_CONTENT_NAME_MISSING.newError());
					}*/
					if(externalArtifact.getTextContent().getContent() == null)
					{
						isValid = false;
						String[] params = new String[]{"a StringContent"};
						errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_OBJECT_CONTENT_MISSING, 
																ErrorConstants.ERRORDOMAIN));
					}
				}
				if(externalArtifact.getUrlContent()!= null)
				{
					if(foundContent)
					{
						//isWarning = true;
						errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MULTIPLE_CONTENT_TAG_POPULATION_WARNING, 
																					ErrorConstants.ERRORDOMAIN));
					}
					foundContent = true;
					/*if(artifactExternal.getUrlContent().getContentName() == null)
					{
						isValid = false;
						errorDataList.add(AssertionsServiceErrorDescriptor.ASSERTION_CONTENT_NAME_MISSING.newError());
					}*/
					if(externalArtifact.getUrlContent().getUrl() == null)
					{
						isValid = false;
						String[] params = new String[]{"a URLContent"};
						
						errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_OBJECT_CONTENT_MISSING, 
												ErrorConstants.ERRORDOMAIN,params));
					}
				}
				if(!foundContent)
				{
					isValid = false;
					errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_CONTENT_MISSING, ErrorConstants.ERRORDOMAIN));
				}
				
				return isValid;
		}
	
	protected boolean validateAssertableArtifact(AssertableArtifact assertableArtifact,List<CommonErrorData> errorDataList)
	{
		boolean isValid = true;
		//boolean isWarning = false;
		boolean foundContent = false;
		
		if(assertableArtifact.getArtifactAssetReference()!=null)
		{
			foundContent = true;
			if(!validateArtifactAsset(assertableArtifact.getArtifactAssetReference(), errorDataList))
			{
				isValid = false;
			}
		}
		if(assertableArtifact.getArtifactExternal()!= null)
		{
			if(foundContent)
			{
				//isWarning = true;
				errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MULTIPLE_CONTENT_TAG_POPULATION_WARNING, 
																		ErrorConstants.ERRORDOMAIN));
			}
			foundContent = true;
			
			if(!validateExternalArtifact(assertableArtifact.getArtifactExternal(), errorDataList))
			{
				isValid = false;
			}
		}
		if(!foundContent)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_CONTENT_MISSING, ErrorConstants.ERRORDOMAIN));
		}
		return isValid;
	}
	
	protected Object convert(Object inputObject) throws ObjectNotFoundException
	{
		Object outputObject = null;
		if(inputObject.getClass().equals(AssertableArtifact.class))
		{
			AssertableArtifact wsdlAssertableArtifact = (AssertableArtifact) inputObject;
			org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent returnApiAssertionContent = null;
			if(wsdlAssertableArtifact.getArtifactAssetReference()!= null)
			{
				ArtifactAsset wsdlArtifactAssetReference = wsdlAssertableArtifact.getArtifactAssetReference();
				org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference apiAssetReference = null;
				if(wsdlArtifactAssetReference!= null)
				{
					apiAssetReference = new org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference(wsdlArtifactAssetReference.getLibraryName(),wsdlArtifactAssetReference.getAssetName(),wsdlArtifactAssetReference.getVersion(),wsdlArtifactAssetReference.getAssetType());
				}
				else
				{
					new ObjectNotFoundException("Reference to requeried Assertion Asset was not found");
				}
				
				AssetContent apiAssetContent = null;
				if(apiAssetReference!= null)
				{
					apiAssetContent = new AssetContent("FILE_PATH",apiAssetReference,wsdlAssertableArtifact.getArtifactAssetReference().getArtifactCategory().value());
				}
				else
				{
					new ObjectNotFoundException("Reference to requeried Assertion Asset was not found");
				}
				returnApiAssertionContent = apiAssetContent;
			}
			if(wsdlAssertableArtifact.getArtifactExternal()!= null)
			{
				returnApiAssertionContent = (org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent) convert(wsdlAssertableArtifact.getArtifactExternal()); 
			}
			outputObject = (Object) returnApiAssertionContent;
		}
		else if(inputObject instanceof AssertionModuleAsset)
		{
			AssertionModuleAsset assertionModuleAsset = (AssertionModuleAsset) inputObject;
			org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference apiAssetReference = new org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference(assertionModuleAsset.getLibraryName(),assertionModuleAsset.getAssetName(),assertionModuleAsset.getVersion(),assertionModuleAsset.getAssetType().value());
			outputObject = apiAssetReference;
		}
		else if(inputObject instanceof AssetReference)
		{
			AssetReference wsdlAssetReference = (AssetReference) inputObject;
			org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference apiAssetReference = new org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference(wsdlAssetReference.getLibraryName(),wsdlAssetReference.getAssetName(),wsdlAssetReference.getVersion(),wsdlAssetReference.getAssetType().value());
			outputObject = apiAssetReference;
		}
		else if(inputObject instanceof AssertionResultItem)
		{
		    AssertionResultItem apiAssertionResultItem = (AssertionResultItem) inputObject;
		    org.ebayopensource.turmeric.repository.v1.services.AssertionResultItem wsdlAssertionResultItem = new org.ebayopensource.turmeric.repository.v1.services.AssertionResultItem();
		    wsdlAssertionResultItem.setContentName(apiAssertionResultItem.getContentName());
		    wsdlAssertionResultItem.setLineNumber(apiAssertionResultItem.getLineNumber());
		    wsdlAssertionResultItem.setMessageText(apiAssertionResultItem.getMessageText());
		    outputObject = wsdlAssertionResultItem;
		}
		else if(inputObject instanceof AssertionResult)
		{
			AssertionResult apiAssertionResult = (AssertionResult) inputObject;
			org.ebayopensource.turmeric.repository.v1.services.AssertionResult wsdlAssertionResult = new org.ebayopensource.turmeric.repository.v1.services.AssertionResult();
			
			wsdlAssertionResult.setAssertionDescription(apiAssertionResult.getAssertionDescription());
			wsdlAssertionResult.setAssertionName(apiAssertionResult.getAssertionName());
			wsdlAssertionResult.setAssertionVersion(apiAssertionResult.getAssertionVersion());
			
			if(!apiAssertionResult.getValidationResult().equals(ValidationResult.PASSED))
			{
				wsdlAssertionResult.setValidationResult(org.ebayopensource.turmeric.repository.v1.services.ValidationResult.FAILED);
			}
			else
			{
				wsdlAssertionResult.setValidationResult(org.ebayopensource.turmeric.repository.v1.services.ValidationResult.PASSED);
			}
			
			if(apiAssertionResult.getResultItems()!= null)
			{
    			for(AssertionResultItem apiAssertionResultItem : apiAssertionResult.getResultItems())
    			{
    				org.ebayopensource.turmeric.repository.v1.services.AssertionResultItem wsdlAssertionResultItem = (org.ebayopensource.turmeric.repository.v1.services.AssertionResultItem) convert(apiAssertionResultItem);
    			    wsdlAssertionResult.getResultItems().add(wsdlAssertionResultItem);
    			}
			}
			outputObject = wsdlAssertionResult;
		}
		else if(inputObject instanceof AssertionModule)
		{
			AssertionModule wsdlAssertionModule = (AssertionModule) inputObject;
			org.ebayopensource.turmeric.repositorymanager.assertions.AssertionModule assertionModule = null;
			List<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent> assertionContentList = new ArrayList<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent>();
			AssetContent assetContent = null;
			if(wsdlAssertionModule.getModuleAssetReference()!= null)
			{
				//TODO: implementation pending. THis is just a makeshift implementation
				
				org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference apiAssetReference = (org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference) convert(wsdlAssertionModule.getModuleAssetReference());
				assetContent = new AssetContent(apiAssetReference.getAssetName(),apiAssetReference,"Assertion Modelue Script");
				assertionContentList.add(assetContent);
				assertionModule = new BasicAssertionModule(apiAssetReference.getAssetName(),apiAssetReference.getVersion(),apiAssetReference.getAssetName(),assertionContentList,AssertionProcessor.XQUERY_TYPE);
			}
			else
			{
				ExternalAssertionModule assertionModuleExternal = wsdlAssertionModule.getAssertionModuleExternal();
				for(ExternalContent contentExternal : assertionModuleExternal.getModuleScripts())
				{
					org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent assertionContent = (org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent) convert(contentExternal);
					assertionContentList.add(assertionContent);
				}
				assertionModule = new BasicAssertionModule(assertionModuleExternal.getName(),assertionModuleExternal.getVersion(),assertionModuleExternal.getDescription(),assertionContentList,assertionModuleExternal.getAssertionProcessorType().value());
			}
			
			outputObject = assertionModule;
		}
		else if(inputObject instanceof ExternalContent)
		{
		    ExternalContent contentExternal = (ExternalContent) inputObject;
			org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent assertionContent = null;
			if(contentExternal.getBinaryContent()!= null)
			{
				BinaryContent apiBinaryContent = new BinaryContent(ContentBindingName.FILE_PATH.value()/*contentExternal.getBinaryContent().getContentName()*/,contentExternal.getBinaryContent().getContent());
				
				assertionContent = apiBinaryContent;
			}
			if(contentExternal.getStringContent()!= null)
			{
				StringContent apiStringContent = new StringContent(ContentBindingName.FILE_PATH.value()/*contentExternal.getStringContent().getContentName()*/,contentExternal.getStringContent().getContent());
				
				assertionContent = apiStringContent;
			}
			if(contentExternal.getUrlContent()!= null)
			{
				assertionContent = new URLContent(ContentBindingName.FILE_PATH.value()/*contentExternal.getUrlContent().getContentName()*/,contentExternal.getUrlContent().getUrl());
				//TODO: find how to use the proxy value
			}
			outputObject = assertionContent;
		}
		else if(inputObject instanceof ExternalArtifact)
		{
			ExternalArtifact artifactExternal = (ExternalArtifact) inputObject;
			org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent assertionContent = null;
			if(artifactExternal.getBinaryContent()!= null)
			{
				BinaryContent apiBinaryContent = new BinaryContent(ContentBindingName.FILE_PATH.value()/*artifactExternal.getBinaryContent().getContentName()*/,artifactExternal.getBinaryContent().getContent());
				
				assertionContent = apiBinaryContent;
			}
			if(artifactExternal.getTextContent()!= null)
			{
				StringContent apiStringContent = new StringContent(ContentBindingName.FILE_PATH.value()/*artifactExternal.getTextContent().getContentName()*/,artifactExternal.getTextContent().getContent());
				
				assertionContent = apiStringContent;
			}
			if(artifactExternal.getUrlContent()!= null)
			{
				assertionContent = new URLContent(ContentBindingName.FILE_PATH.value()/*artifactExternal.getUrlContent().getContentName()*/,artifactExternal.getUrlContent().getUrl());
				//TODO: find how to use the proxy value
			}
			outputObject = assertionContent;
		}
		return outputObject;
	}

}
