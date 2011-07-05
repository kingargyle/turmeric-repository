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

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;
import org.ebayopensource.turmeric.errorlibrary.assertion.ErrorConstants;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionGroupsRequest;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionGroupsResponse;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactContentTypes;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactReport;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactValidationResult;
import org.ebayopensource.turmeric.repository.v1.services.AssertableArtifact;
import org.ebayopensource.turmeric.repository.v1.services.AssertionGroupAsset;
import org.ebayopensource.turmeric.repository.v1.services.AssertionGroupAssetTypes;
import org.ebayopensource.turmeric.repository.v1.services.AssertionGroupReport;
import org.ebayopensource.turmeric.repository.v1.services.AssertionResult;
import org.ebayopensource.turmeric.repository.v1.services.AssertionResultItem;
import org.ebayopensource.turmeric.repository.v1.services.AssertionSeverity;
import org.ebayopensource.turmeric.repository.v1.services.ValidateArtifactRequest;
import org.ebayopensource.turmeric.repository.v1.services.ValidateArtifactResponse;
import org.ebayopensource.turmeric.repository.v1.services.ValidationResultItem;
import org.ebayopensource.turmeric.runtime.common.exceptions.ErrorDataFactory;
import org.ebayopensource.turmeric.services.assertionsservice.common.ConstantPopulator;
import org.ebayopensource.turmeric.services.assertionsservice.common.PropertyKeys;
import org.ebayopensource.turmeric.services.assertionsservice.exception.InvalidInputException;



/**
 * @author szacharias
 */
public class ValidateArtifactOperationImpl {

	private static ValidateArtifactOperationImpl s_ValidateArtifactOperationImpl = null;
	private static ApplyAssertionGroupsOperationImpl s_ApplyAssertionGroupsOperationImpl = null;
	
	public static ValidateArtifactOperationImpl getInstance() {
		if (s_ValidateArtifactOperationImpl == null) {
			s_ValidateArtifactOperationImpl = new ValidateArtifactOperationImpl();
			s_ApplyAssertionGroupsOperationImpl = new ApplyAssertionGroupsOperationImpl();
		}
		return s_ValidateArtifactOperationImpl;
	}
	
	/**
	 * This validates the request object for the operation to see whether any required parameters are missing.  
	 * 
	 * @param validateArtifactRequest request to be validated
	 * @param errorDataList this will hold any errors during validation
	 * @return boolean indicating success or failure or request object validation
	 */
	public boolean validate(ValidateArtifactRequest validateArtifactRequest, List<CommonErrorData> errorDataList) {
		
		boolean isValid = true;
		
		if(validateArtifactRequest== null)
		{
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.REQUEST_EMPTY, ErrorConstants.ERRORDOMAIN));
		}else if(validateArtifactRequest.getArtifactContent() == null){
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ARTIFACT_CATEGORY_MISSING, ErrorConstants.ERRORDOMAIN));
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MISSING_ARTIFACT, ErrorConstants.ERRORDOMAIN));
		}else if(validateArtifactRequest.getArtifactContent().getContentType() == null){
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ARTIFACT_CATEGORY_MISSING, ErrorConstants.ERRORDOMAIN));
		}else if(validateArtifactRequest.getArtifactContent().getBinaryContent() == null && validateArtifactRequest.getArtifactContent().getTextContent() == null){
			//Currently only BinaryContent and TextContent is supported to specify the artifact. UrlContent is not supported.
			isValid = false;
			errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.MISSING_ARTIFACT, ErrorConstants.ERRORDOMAIN));
		}
		
		return isValid;
	}
	
	public ValidateArtifactResponse validateArtifact(ValidateArtifactRequest validateArtifactRequest)
	{
		boolean isError = false;
		boolean isWarning = false;
		boolean isPartial = false;
		ArrayList<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
		ValidateArtifactResponse validateArtifactResponse = new ValidateArtifactResponse();
			
		try {
			ApplyAssertionGroupsRequest applyAssertionGroupsRequest = getApplyAssertionGroupsRequest(validateArtifactRequest);
			ApplyAssertionGroupsResponse applyAssertionGroupsResponse = s_ApplyAssertionGroupsOperationImpl.applyAssertionGroups(applyAssertionGroupsRequest);
			setResponseWithValidationResult(applyAssertionGroupsResponse, validateArtifactResponse);
			propagateErrors(applyAssertionGroupsResponse, validateArtifactResponse);
			
			if(AckValue.FAILURE.equals(applyAssertionGroupsResponse.getAck()))
			{
				isError = true;
			}
			else if(AckValue.PARTIAL_FAILURE.equals(applyAssertionGroupsResponse.getAck()))
			{
				isPartial = true;
			}
			else if(AckValue.WARNING.equals(applyAssertionGroupsResponse.getAck()))
			{
				isWarning = true;
			}
			
		} catch (InvalidInputException invalidInputException) {
			isError = true;
			String message = invalidInputException.getMessage();
			String[] params = new String[]{message};
			
		    errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.OBJECT_NOT_FOUND_EXCEPTION, 
		    								ErrorConstants.ERRORDOMAIN, params));
		}

		if(errorDataList!= null &&!errorDataList.isEmpty())
		{
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.getError().addAll(errorDataList);
			validateArtifactResponse.setErrorMessage(errorMessage);
		}
		
		if(isError)
			validateArtifactResponse.setAck(AckValue.FAILURE);
		else if(isPartial)
			validateArtifactResponse.setAck(AckValue.PARTIAL_FAILURE);
		else if (isWarning)
			validateArtifactResponse.setAck(AckValue.WARNING);
		else
			validateArtifactResponse.setAck(AckValue.SUCCESS);
		
		return validateArtifactResponse;
	}
	
	private ApplyAssertionGroupsRequest getApplyAssertionGroupsRequest(ValidateArtifactRequest validateArtifactRequest) throws InvalidInputException{
		
		ApplyAssertionGroupsRequest applyAssertionGroupsRequest = new ApplyAssertionGroupsRequest();
		AssertionGroupAsset assertionGroupAsset = new AssertionGroupAsset();
		applyAssertionGroupsRequest.getAssertionGroups().add(assertionGroupAsset);
		assertionGroupAsset.setLibraryName(ConstantPopulator.getInstance().getValueOf(PropertyKeys.DEFAULT_ASSERTION_LIBRARY_NAME));
		assertionGroupAsset.setAssetType(AssertionGroupAssetTypes.ASSERTION_GROUP);
		if(!setAssertionGroupAndVersion(validateArtifactRequest, assertionGroupAsset)){
			throw new InvalidInputException("Could not find ArtifactType or assertionGroup to be applied for the ArtifactType");
		}
		
		AssertableArtifact assertableArtifact = new AssertableArtifact();
		applyAssertionGroupsRequest.getArtifacts().add(assertableArtifact);
		assertableArtifact.setArtifactExternal(validateArtifactRequest.getArtifactContent());
		
		return applyAssertionGroupsRequest;
	}
	
	private boolean setAssertionGroupAndVersion(ValidateArtifactRequest validateArtifactRequest, AssertionGroupAsset assertionGroupAsset){
		
		boolean isSuccess = false;
		
		ArtifactContentTypes artifactType = validateArtifactRequest.getArtifactContent().getContentType();
		switch (artifactType) {
		case WSDL:
			assertionGroupAsset.setAssetName(ConstantPopulator.getInstance().getValueOf(PropertyKeys.WSDL_ASSERTION_GROUP)); 
			assertionGroupAsset.setVersion(ConstantPopulator.getInstance().getValueOf(PropertyKeys.WSDL_ASSERTION_GROUP_VERSION));
			isSuccess = true;
			break;
			
		case XSD:
			assertionGroupAsset.setAssetName(ConstantPopulator.getInstance().getValueOf(PropertyKeys.XSD_ASSERTION_GROUP));
			assertionGroupAsset.setVersion(ConstantPopulator.getInstance().getValueOf(PropertyKeys.XSD_ASSERTION_GROUP_VERSION));
			isSuccess = true;
			break;
		
		default:
			break;
		}
		
		return isSuccess;
	}
	
	private void setResponseWithValidationResult(ApplyAssertionGroupsResponse applyAssertionGroupsResponse, ValidateArtifactResponse validateArtifactResponse) {
		
		boolean atleastOneWarning = false;
		boolean atleastOneError = false;
		List<ValidationResultItem> validationResultItems = new ArrayList<ValidationResultItem>();
		
		if (applyAssertionGroupsResponse != null && applyAssertionGroupsResponse.getAck() != AckValue.FAILURE) {
			List<AssertionGroupReport> assertionGroupReports = applyAssertionGroupsResponse.getAssertionGroupReport();

			for (AssertionGroupReport assertionGroupReport : assertionGroupReports) {

				List<ArtifactReport> artifactReports = assertionGroupReport.getArtifactReport();
				if (artifactReports != null) {

					for (ArtifactReport artifactReport : artifactReports) {
						
						List<AssertionResult> assertionResults = artifactReport.getAssertionResults();
						
						//Give the validation message to response only in case of failure
						for(AssertionResult assertionResult  :assertionResults){
							
							List<AssertionResultItem> resultItems = assertionResult.getResultItems();
							for(AssertionResultItem assertionResultItem : resultItems){
								ValidationResultItem validationResultItem = new ValidationResultItem();
								validationResultItem.setMessage(assertionResultItem.getMessageText());
								validationResultItem.setLineNo(assertionResultItem.getLineNumber());
								
								if(assertionResult.getAssertionSeverity() == AssertionSeverity.ERROR){
									atleastOneError = true;
									validationResultItem.setValidationSeverity(AssertionSeverity.ERROR);
								} else if(assertionResult.getAssertionSeverity() == AssertionSeverity.MUST){
									atleastOneError = true;
									validationResultItem.setValidationSeverity(AssertionSeverity.MUST);
								} else if(assertionResult.getAssertionSeverity() == AssertionSeverity.WARNING){
									atleastOneWarning = true;
									validationResultItem.setValidationSeverity(AssertionSeverity.WARNING);
								} else if(assertionResult.getAssertionSeverity() == AssertionSeverity.SHOULD){
									atleastOneWarning = true;
									validationResultItem.setValidationSeverity(AssertionSeverity.SHOULD);
								} else if(assertionResult.getAssertionSeverity() == AssertionSeverity.MAY){
									atleastOneWarning = true;
									validationResultItem.setValidationSeverity(AssertionSeverity.MAY);
								}
								validationResultItems.add(validationResultItem);
							}
						}
					}
				}
			}
			
			validateArtifactResponse.getValidationResultItem().addAll(validationResultItems);
			
			if(atleastOneError && atleastOneWarning){
				validateArtifactResponse.setArtifactValidationResult(ArtifactValidationResult.ERRORS_AND_WARNINGS);
			}else if(atleastOneError){
				validateArtifactResponse.setArtifactValidationResult(ArtifactValidationResult.ERRORS);
			}else if(atleastOneWarning){
				validateArtifactResponse.setArtifactValidationResult(ArtifactValidationResult.WARNINGS);
			}else{
				validateArtifactResponse.setArtifactValidationResult(ArtifactValidationResult.VALID);
			}
		}
	}
	
	private void propagateErrors(ApplyAssertionGroupsResponse applyAssertionGroupsResponse, ValidateArtifactResponse validateArtifactResponse){
		if(applyAssertionGroupsResponse != null && applyAssertionGroupsResponse.getErrorMessage() != null){
			List<ErrorData> errors = applyAssertionGroupsResponse.getErrorMessage().getError();
			
			if(errors != null && !errors.isEmpty()){
				if(validateArtifactResponse.getErrorMessage() == null){
					ErrorMessage errorMessage = new ErrorMessage();
					validateArtifactResponse.setErrorMessage(errorMessage);
				}
				validateArtifactResponse.getErrorMessage().getError().addAll(errors);	
			}
		}
	}
}
