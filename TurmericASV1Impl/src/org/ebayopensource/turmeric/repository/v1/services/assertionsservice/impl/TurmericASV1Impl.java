/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repository.v1.services.assertionsservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.assertionsservice.operation.impl.ApplyAssertionGroupsOperationImpl;
import org.ebayopensource.turmeric.assertionsservice.operation.impl.ApplyAssertionsOperationImpl;
import org.ebayopensource.turmeric.assertionsservice.operation.impl.ValidateArtifactOperationImpl;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.BaseResponse;
import org.ebayopensource.turmeric.common.v1.types.BaseServiceResponse;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionGroupsRequest;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionGroupsResponse;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionsRequest;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionsResponse;
import org.ebayopensource.turmeric.repository.v1.services.ValidateArtifactRequest;
import org.ebayopensource.turmeric.repository.v1.services.ValidateArtifactResponse;
import org.ebayopensource.turmeric.repository.v1.services.assertionsservice.impl.TurmericASV1;

public class TurmericASV1Impl
    implements TurmericASV1
{


	private static Logger s_logger = Logger.getLogger(TurmericASV1Impl.class);
	 
    public ApplyAssertionGroupsResponse applyAssertionGroups(ApplyAssertionGroupsRequest applyAssertionGroupsRequest) 
    {
    	ApplyAssertionGroupsResponse  applyAssertionGroupsResponse = null;
    	ArrayList<CommonErrorData> errorDataList = new ArrayList<CommonErrorData> (); 
    	ApplyAssertionGroupsOperationImpl applyAssertionGroupsOperationImpl = ApplyAssertionGroupsOperationImpl.getInstance();
    	if (applyAssertionGroupsOperationImpl.validate(applyAssertionGroupsRequest, errorDataList)) 
    	{
    		applyAssertionGroupsResponse = applyAssertionGroupsOperationImpl.applyAssertionGroups(applyAssertionGroupsRequest);
    		if(applyAssertionGroupsResponse.getErrorMessage()!= null)
    		{
    			//errorDataList.addAll(applyAssertionGroupsResponse.getErrorMessage().getError());
    			addErrorsToResponse(errorDataList, applyAssertionGroupsResponse);
    		}
    		else
    		{
    			setSuccessResponse(applyAssertionGroupsResponse);
    		}
    	} 
    	else 
    	{
    		s_logger.error("validation failure for ApplyAssertionGroupsRequest");
    		applyAssertionGroupsResponse = new ApplyAssertionGroupsResponse();
    		addErrorsToResponse(errorDataList, applyAssertionGroupsResponse);
    	}
    	return applyAssertionGroupsResponse;
    }

    public ApplyAssertionsResponse applyAssertions(ApplyAssertionsRequest applyAssertionsRequest) 
    {
    	s_logger.error("applyAssertions: beginning applyAssertions method");
    	ApplyAssertionsResponse  applyAssertionsResponse = null;
    	List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData> (); ;
    	ApplyAssertionsOperationImpl applyAssertionsOperationImpl = ApplyAssertionsOperationImpl.getInstance();
    	s_logger.error("applyAssertions:validating request...");
    	if (applyAssertionsOperationImpl.validate(applyAssertionsRequest, errorDataList)) 
    	{
    		s_logger.error("applyAssertions:request validated, applying assertions");
    		applyAssertionsResponse = applyAssertionsOperationImpl.applyAssertions(applyAssertionsRequest);
    		if(applyAssertionsResponse.getErrorMessage()!= null)
    		{
    			//errorDataList.addAll(applyAssertionsResponse.getErrorMessage().getError());
    			addErrorsToResponse(errorDataList, applyAssertionsResponse);
    		}
    		else
    		{
    			setSuccessResponse(applyAssertionsResponse);
    		}
    	} 
    	else 
    	{
    		s_logger.error("validation failure for ApplyAssertionRequest");
    		applyAssertionsResponse = new ApplyAssertionsResponse();
    		addErrorsToResponse(errorDataList, applyAssertionsResponse);
    	}
    	return applyAssertionsResponse;
    }
    
    public ValidateArtifactResponse validateArtifact(ValidateArtifactRequest validateArtifactRequest) {

    	ValidateArtifactResponse validateArtifactResponse = null;
    	List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData> (); ;
    	
    	ValidateArtifactOperationImpl validateArtifactOperationImpl = ValidateArtifactOperationImpl.getInstance();
    	if (validateArtifactOperationImpl.validate(validateArtifactRequest, errorDataList)) 
    	{
    		validateArtifactResponse = validateArtifactOperationImpl.validateArtifact(validateArtifactRequest);
    		if(validateArtifactResponse.getErrorMessage()!= null)
    		{
    			addErrorsToResponse(errorDataList, validateArtifactResponse);
    		}
    		else
    		{
    			setSuccessResponse(validateArtifactResponse);
    		}
    	} 
    	else 
    	{
    		validateArtifactResponse = new ValidateArtifactResponse();
    		addErrorsToResponse(errorDataList, validateArtifactResponse);
    	}
    	return validateArtifactResponse;
	}

    public ErrorMessage setErrorMessage(BaseServiceResponse response) {
		if (response.getErrorMessage() == null) {
			response.setErrorMessage(new ErrorMessage());
		}
		return response.getErrorMessage();
	}

	
    public void addErrorsToResponse(List<CommonErrorData> errors, BaseResponse response) {
    	
    	if(errors != null && !errors.isEmpty() && response != null)
        {
            if(response.getErrorMessage() == null)
            {
                response.setErrorMessage(new ErrorMessage());
            }
            response.getErrorMessage().getError().addAll(errors);
            response.setAck(AckValue.FAILURE);
        }
    }

    public void setSuccessResponse(BaseResponse response) {
	
    	if(response != null)
    		response.setAck(AckValue.SUCCESS);
    }

}
