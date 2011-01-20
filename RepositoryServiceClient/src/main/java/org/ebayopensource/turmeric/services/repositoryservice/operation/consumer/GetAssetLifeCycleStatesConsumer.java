/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetLifeCycleStatesRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetLifeCycleStatesResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.LibraryLifeCycleStates;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetAssetLifeCycleStatesConsumer extends BaseRepositoryServiceConsumer {
	private AsyncTurmericRSV1 m_proxy = null;
	
	public static String testGetAssetLifeCycleStates_validInput(String varient) {
		GetAssetLifeCycleStatesConsumer getAssetLifeCycleStatesConsumer = new GetAssetLifeCycleStatesConsumer();
		GetAssetLifeCycleStatesRequest  getAssetLifeCycleStatesRequest  = new GetAssetLifeCycleStatesRequest();
		
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
		library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
		
		getAssetLifeCycleStatesRequest.setLibrary(library);
		getAssetLifeCycleStatesRequest.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
		
		if(varient.equalsIgnoreCase("withoutLibraryId")) {
			library.setLibraryId(null);
		}
		if(varient.equalsIgnoreCase("withoutLibraryName")) {
			library.setLibraryName(null);
		}
		if(varient.equalsIgnoreCase("noLibrary")) {
			getAssetLifeCycleStatesRequest.setLibrary(null);
		}
		
		try	{
			GetAssetLifeCycleStatesResponse getAssetLifeCycleStatesResponse = getAssetLifeCycleStatesConsumer.getProxy().getAssetLifeCycleStates(getAssetLifeCycleStatesRequest);			
			if(getAssetLifeCycleStatesResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateGetAssetLifeCycleStatesResponse(getAssetLifeCycleStatesResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
    			List<LibraryLifeCycleStates> libraryLifeCycleStates_list = getAssetLifeCycleStatesResponse.getLibraryLifeCycleStates();
    			
    			for (LibraryLifeCycleStates libraryLifeCycleStates : libraryLifeCycleStates_list) {
    				List<String> states = libraryLifeCycleStates.getLifeCycleState();
    				System.out.println("Library Name: " + libraryLifeCycleStates.getLibraryName());
    				System.out.println("States      : " + states);
				}
    			
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
	
	public static String testGetAssetLifeCycleStates_invalidInput(String varient) {
		GetAssetLifeCycleStatesConsumer getAssetLifeCycleStatesConsumer = new GetAssetLifeCycleStatesConsumer();
		GetAssetLifeCycleStatesRequest  getAssetLifeCycleStatesRequest  = new GetAssetLifeCycleStatesRequest();
		
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
		library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
		
		getAssetLifeCycleStatesRequest.setLibrary(library);
		getAssetLifeCycleStatesRequest.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
		
		if(varient.equalsIgnoreCase("invalidAssetType")) {
			getAssetLifeCycleStatesRequest.setAssetType(RepositoryServiceClientConstants.INVALID_ASSET_TYPE);
		}
		if(varient.equalsIgnoreCase("withoutAssetType")) {
			getAssetLifeCycleStatesRequest.setAssetType(null);
		}
		if(varient.equalsIgnoreCase("invalidLibraryId")) {
			library.setLibraryId(RepositoryServiceClientConstants.INVALID_LIBRARY_ID);
			library.setLibraryName(null);
		}
		if(varient.equalsIgnoreCase("invalidLibraryName")) {
			library.setLibraryName(RepositoryServiceClientConstants.INVALID_LIBRARY_NAME);
			library.setLibraryId(null);
		}
		if(varient.equalsIgnoreCase("noLibraryNameAndId")) {
			library.setLibraryName(null);
			library.setLibraryId(null);
		}
		
		try	{
			GetAssetLifeCycleStatesResponse getAssetLifeCycleStatesResponse = getAssetLifeCycleStatesConsumer.getProxy().getAssetLifeCycleStates(getAssetLifeCycleStatesRequest);			
			if(getAssetLifeCycleStatesResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateGetAssetLifeCycleStatesResponse(getAssetLifeCycleStatesResponse, "negativeCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							   			
				List<CommonErrorData> errorDatas = getAssetLifeCycleStatesResponse.getErrorMessage().getError();
				
				System.out.println("The following list of errors occured");
				for (CommonErrorData errorData : errorDatas) {
					System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());					
				}
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

	private static String validateGetAssetLifeCycleStatesResponse(GetAssetLifeCycleStatesResponse getAssetLifeCycleStatesResponse, String criteria) {
    	if(criteria.equalsIgnoreCase("positiveCase")) {
    		if(getAssetLifeCycleStatesResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS) || getAssetLifeCycleStatesResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
    			List<LibraryLifeCycleStates> libraryLifeCycleStates_list = getAssetLifeCycleStatesResponse.getLibraryLifeCycleStates();
    			
    			for (LibraryLifeCycleStates libraryLifeCycleStates : libraryLifeCycleStates_list) {
					if(libraryLifeCycleStates.getLibraryName() == null) {
						return RepositoryServiceClientConstants.FAILURE;
					}
				}
    			return RepositoryServiceClientConstants.SUCCESS;
    		}
    		return RepositoryServiceClientConstants.FAILURE;
    	}
    	if(criteria.equalsIgnoreCase("negativeCase")) {
    		if(getAssetLifeCycleStatesResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
    			if(getAssetLifeCycleStatesResponse.getErrorMessage().getError().size() > 0) {
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
