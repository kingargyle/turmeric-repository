/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.common;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.BaseServiceResponse;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;
import org.ebayopensource.turmeric.common.v1.types.ErrorParameter;
import org.ebayopensource.turmeric.runtime.common.errors.ErrorSubcategory;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.common.pipeline.MessageContext;
import org.ebayopensource.turmeric.runtime.common.pipeline.MessageContextAccessor;

import org.ebayopensource.turmeric.services.common.error.ServiceBaseErrorDescriptor;

/**
 * This is the Base Impl class for all Marketplace service Impl
 *
 * @author vajoshi
 */
public abstract class BaseServiceImpl {

	 /**
     * This method will return the schema validation errors. These validation
     * error are currently stored as warning in MessageContext. This method will
     * convert these errors ( SOA framework erros to ) MarketPlaceErros.
     *
     * @return ErrorMessage - if there are any data validation errors present. Null if there are no errors
     */
    public List<ErrorData> getInboundDataValidationErrors(){
 
        MessageContext ctx = MessageContextAccessor.getContext();
        List<Throwable> warnings = ctx.getWarningList();
        List<ErrorData> convertedErrors = null;
        List<ErrorData> jaxbValidationErrorList = null;
 
        //get all errors from exception
        if(warnings != null && !warnings.isEmpty()){
            jaxbValidationErrorList = new ArrayList<ErrorData>();
            for(Throwable warning: warnings){
                if (warning instanceof ServiceException) {
                    ServiceException error = (ServiceException) warning;
                    if(error != null && error.getSubcategory() == ErrorSubcategory.INBOUND_DATA){
                        jaxbValidationErrorList.addAll(error.getErrorMessage().getError());
                    }
                }
            }
        }
 
        if(jaxbValidationErrorList != null && !jaxbValidationErrorList.isEmpty()){
            convertedErrors = new ArrayList<ErrorData>();
            for(ErrorData errorData : jaxbValidationErrorList){
                ErrorParameter param = new ErrorParameter();
                param.setName(ServiceBaseErrorDescriptor.PARAM_ORIGINAL_MESSAGE.getName());
                param.setValue(errorData.getMessage());
                //create a new ErrorData from marketplacecommontypes
                ErrorData error = ServiceBaseErrorDescriptor.INVALID_INPUT_DATA.newError(param);
                convertedErrors.add(error);
            }
        }
 
        return convertedErrors;
    }
 
    public void addErrorsToResponse(List<CommonErrorData> errors,
            BaseServiceResponse response) {
        if (errors != null && !errors.isEmpty() && response != null) {
            if (response.getErrorMessage() == null) {
                response.setErrorMessage(new ErrorMessage());
            }
            response.getErrorMessage().getError().addAll(errors);
            response.setAck(AckValue.FAILURE);
        }
    }
 
    public void setSuccessResponse(BaseServiceResponse response){
        response.setAck(AckValue.SUCCESS);
    }
}
