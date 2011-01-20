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
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationship;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.Relation;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetAssetDependenciesConsumer extends BaseRepositoryServiceConsumer {
	private AsyncTurmericRSV1 m_proxy = null; 
	public static String testGetAssetDependencies_validAsset(AssetInfo assetInfo)
	{		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library = new Library();
		library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library);

		GetAssetDependenciesRequest getAssetDependenciesRequest = new GetAssetDependenciesRequest();
		getAssetDependenciesRequest.setAssetKey(assetKey);
		
		try {
			GetAssetDependenciesConsumer getAssetDependenciesConsumer = new GetAssetDependenciesConsumer();
			GetAssetDependenciesResponse getAssetDependenciesResponse = getAssetDependenciesConsumer.getProxy().getAssetDependencies(getAssetDependenciesRequest);
			if(getAssetDependenciesResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetDependenciesResponse(getAssetDependenciesResponse, "positiveCase").equalsIgnoreCase("success")) {
				FlattenedRelationship flattenedRelationship = getAssetDependenciesResponse.getFlattenedRelationship();
				if(flattenedRelationship == null) {
					System.out.println("There are no dependent Assets for " + RepositoryServiceClientConstants.ASSET_NAME);
					
					return "PASSED";
				}
				List<Relation> relations = flattenedRelationship.getRelatedAsset();
				
				for (Relation relation : relations) {
					System.out.println(relation.getTargetAsset().getAssetName() + " " + relation.getAssetRelationship());
				}
			
				return "PASSED";
			}
			else {
				return "FAILED";
			}
		}
		catch(ServiceException se) {
			se.getMessage();
			se.printStackTrace();	
			return "FAILED";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "FAILED";
		}						
	}

	public static String testGetAssetDependencies_invalidAsset()
	{
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
		library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
		assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
		assetKey.setLibrary(library);
		
		GetAssetDependenciesRequest getAssetDependenciesRequest = new GetAssetDependenciesRequest();
		getAssetDependenciesRequest.setAssetKey(assetKey);
		getAssetDependenciesRequest.setDepth(1);
		
		try {
			GetAssetDependenciesConsumer getAssetDependenciesConsumer = new GetAssetDependenciesConsumer();
			GetAssetDependenciesResponse getAssetDependenciesResponse = getAssetDependenciesConsumer.getProxy().getAssetDependencies(getAssetDependenciesRequest);
			
			if(getAssetDependenciesResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			
			if(validateGetAssetDependenciesResponse(getAssetDependenciesResponse, "negativeCase").equalsIgnoreCase("success")) {
				List<CommonErrorData> errorDatas = getAssetDependenciesResponse.getErrorMessage().getError();
				
				System.out.println("The following list of errors occured");
				for (CommonErrorData errorData : errorDatas) {
					System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());					
				}				
				return "PASSED";
			}
			else {
				return "FAILED";
			}			
		}
		catch(ServiceException se) {
			se.getMessage();
			se.printStackTrace();	
			return "FAILED";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "FAILED";
		}				
	}
	
	public static String testGetAssetDependencies_untillSpecifiedDepth(AssetInfo assetInfo)
	{
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library = new Library();
		library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library);

		
		GetAssetDependenciesRequest getAssetDependenciesRequest = new GetAssetDependenciesRequest();
		getAssetDependenciesRequest.setAssetKey(assetKey);
		getAssetDependenciesRequest.setDepth(1);
		
		try {
			GetAssetDependenciesConsumer getAssetDependenciesConsumer = new GetAssetDependenciesConsumer();
			GetAssetDependenciesResponse getAssetDependenciesResponse = getAssetDependenciesConsumer.getProxy().getAssetDependencies(getAssetDependenciesRequest);
			
			if(getAssetDependenciesResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetDependenciesResponse(getAssetDependenciesResponse, "positiveCase").equalsIgnoreCase("success")) {
				FlattenedRelationship flattenedRelationship = getAssetDependenciesResponse.getFlattenedRelationship();
				if(flattenedRelationship == null) {
					System.out.println("There are no dependent Assets for " + RepositoryServiceClientConstants.ASSET_NAME);
					
					return "PASSED";
				}
				List<Relation> relations = flattenedRelationship.getRelatedAsset();
				
				for (Relation relation : relations) {
					System.out.println(relation.getTargetAsset().getAssetName() + " " + relation.getAssetRelationship());
				}
			
				return "PASSED";
			}
			else {
				return "FAILED";
			}
		}
		catch(ServiceException se) {
			se.getMessage();
			se.printStackTrace();	
			return "FAILED";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "FAILED";
		}				
	}
	
	public static String testGetAssetDependencies_insufficientPrivilege()
	{
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
		library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
		assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
		assetKey.setLibrary(library);
		
		GetAssetDependenciesRequest getAssetDependenciesRequest = new GetAssetDependenciesRequest();
		getAssetDependenciesRequest.setAssetKey(assetKey);
		
		try {
			GetAssetDependenciesConsumer getAssetDependenciesConsumer = new GetAssetDependenciesConsumer();
			GetAssetDependenciesResponse getAssetDependenciesResponse = getAssetDependenciesConsumer.getProxy().getAssetDependencies(getAssetDependenciesRequest);
			
			if(getAssetDependenciesResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			
			if(validateGetAssetDependenciesResponse(getAssetDependenciesResponse, "negativeCase").equalsIgnoreCase("success")) {
				List<CommonErrorData> errorDatas = getAssetDependenciesResponse.getErrorMessage().getError();
				
				System.out.println("The following list of errors occured");
				for (CommonErrorData errorData : errorDatas) {
					System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());					
				}				
				return "PASSED";
			}
			else {
				return "FAILED";
			}			
		}
		catch(ServiceException se) {
			se.getMessage();
			se.printStackTrace();	
			return "FAILED";
		}
		catch(Exception e) {
			e.printStackTrace();
			return "FAILED";
		}				
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
	
	private static String validateGetAssetDependenciesResponse(GetAssetDependenciesResponse getAssetDependenciesResponse, String criteria) {
    	if(criteria.equalsIgnoreCase("positiveCase")) {
    		if(getAssetDependenciesResponse.getAck().value().equalsIgnoreCase("success")) {
				FlattenedRelationship flattenedRelationship = getAssetDependenciesResponse.getFlattenedRelationship();
				if(flattenedRelationship != null) {
					return RepositoryServiceConsumerUtil.validateFlattenedRelationship(flattenedRelationship);
				}
				return "success";
    		}
    		return "failure";
    	}
    	if(criteria.equalsIgnoreCase("negativeCase")) {
    		if(getAssetDependenciesResponse.getAck().value().equalsIgnoreCase("failure")) {
    			if(getAssetDependenciesResponse.getErrorMessage().getError().size() > 0) {
    				return "success";
    			}
    		}   		
    	}
    	
    	return "failure";
	}
}
