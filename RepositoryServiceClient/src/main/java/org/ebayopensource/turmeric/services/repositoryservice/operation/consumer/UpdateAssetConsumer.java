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
import java.util.Properties;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class UpdateAssetConsumer extends BaseRepositoryServiceConsumer 
{
	private AsyncTurmericRSV1 m_proxy = null;
	
	public static String testUpdateAsset_validAsset_duplicatename(AssetInfo assetInfo,AssetInfo assetInfoDuplicateName) {	
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library1 = new Library();
		library1.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library1.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library1);
		
		AssetKey assetKeyDuplicate = new AssetKey();
		assetKeyDuplicate.setAssetId(assetInfoDuplicateName.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKeyDuplicate.setAssetName(assetInfoDuplicateName.getBasicAssetInfo().getAssetKey().getAssetName());
		
		
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.UPDATED_ASSET_DESCRIPTION);
		basicAssetInfo.setAssetKey(assetKey);
		basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.UPDATED_ASSET_LONG_DESCRIPTION);
		basicAssetInfo.setAssetName(assetInfoDuplicateName.getBasicAssetInfo().getAssetKey().getAssetName());
		basicAssetInfo.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
		basicAssetInfo.setVersion(assetInfo.getBasicAssetInfo().getVersion());
		
		UpdateAssetRequest updateAssetRequest = new UpdateAssetRequest();
		updateAssetRequest.setBasicAssetInfo(basicAssetInfo);
		
		UpdateAssetConsumer updateAssetConsumer = new UpdateAssetConsumer();
		try {
			UpdateAssetResponse updateAssetResponse = updateAssetConsumer.getProxy().updateAsset(updateAssetRequest);
			if(updateAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetResponse(updateAssetResponse, "positiveCase").equalsIgnoreCase("success")) {	
				BasicAssetInfo updatedBasicAssetInfo = updateAssetResponse.getAssetInfo().getBasicAssetInfo();
				System.out.println();
				System.out.println("Updated Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + updatedBasicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + updatedBasicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + updatedBasicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + updatedBasicAssetInfo.getVersion());
				System.out.println();
								
				return resetAsset(assetInfo);
				
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
			e.getMessage();
			e.printStackTrace();
			return "FAILED";
		}
		
	}
	
	public static String testUpdateAsset_validAsset(AssetInfo assetInfo) {	
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library1 = new Library();
		library1.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library1.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library1);
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.UPDATED_ASSET_DESCRIPTION);
		basicAssetInfo.setAssetKey(assetKey);
		basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.UPDATED_ASSET_LONG_DESCRIPTION);
		basicAssetInfo.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		basicAssetInfo.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
		basicAssetInfo.setVersion(assetInfo.getBasicAssetInfo().getVersion());
		
		UpdateAssetRequest updateAssetRequest = new UpdateAssetRequest();
		updateAssetRequest.setBasicAssetInfo(basicAssetInfo);
		
		UpdateAssetConsumer updateAssetConsumer = new UpdateAssetConsumer();
		try {
			UpdateAssetResponse updateAssetResponse = updateAssetConsumer.getProxy().updateAsset(updateAssetRequest);
			if(updateAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetResponse(updateAssetResponse, "positiveCase").equalsIgnoreCase("success")) {	
				BasicAssetInfo updatedBasicAssetInfo = updateAssetResponse.getAssetInfo().getBasicAssetInfo();
				System.out.println();
				System.out.println("Updated Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + updatedBasicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + updatedBasicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + updatedBasicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + updatedBasicAssetInfo.getVersion());
				System.out.println();
								
				return resetAsset(assetInfo);
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
			e.getMessage();
			e.printStackTrace();
			return "FAILED";
		}
		
	}
	
	public static String testUpdateAsset_invalidAsset() {
		
		Properties props = CommonUtil.loadPropertyFile("properties/common.properties");
		Library library = new Library();
		String libraryId = props.getProperty("libraryId");
		String libraryName = props.getProperty("libraryName", "GovernedAssets");
		library.setLibraryId(libraryId);
		library.setLibraryName(libraryName);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
		assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
		assetKey.setLibrary(library);
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.UPDATED_ASSET_DESCRIPTION);
		basicAssetInfo.setAssetKey(assetKey);
		basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.UPDATED_ASSET_LONG_DESCRIPTION);
		basicAssetInfo.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
		basicAssetInfo.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
		basicAssetInfo.setVersion(RepositoryServiceClientConstants.ASSET_VERSION);
		
		UpdateAssetRequest updateAssetRequest = new UpdateAssetRequest();
		updateAssetRequest.setBasicAssetInfo(basicAssetInfo);
		
		UpdateAssetConsumer updateAssetConsumer = new UpdateAssetConsumer();
		try {
			UpdateAssetResponse updateAssetResponse = updateAssetConsumer.getProxy().updateAsset(updateAssetRequest);
			if(updateAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			
			if(validateUpdateAssetResponse(updateAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
				List<CommonErrorData> errorDatas = updateAssetResponse.getErrorMessage().getError();
				
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

	public static String testUpdateAsset_insufficientPrivilege() {		
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
		library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
		assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
		assetKey.setLibrary(library);
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.UPDATED_ASSET_DESCRIPTION);
		basicAssetInfo.setAssetKey(assetKey);
		basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.UPDATED_ASSET_LONG_DESCRIPTION);
		basicAssetInfo.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
		basicAssetInfo.setAssetType(RepositoryServiceClientConstants.ASSET_TYPE);
		basicAssetInfo.setVersion(RepositoryServiceClientConstants.ASSET_VERSION);
		
		UpdateAssetRequest updateAssetRequest = new UpdateAssetRequest();
		updateAssetRequest.setBasicAssetInfo(basicAssetInfo);
		
		UpdateAssetConsumer updateAssetConsumer = new UpdateAssetConsumer();
		try {
			UpdateAssetResponse updateAssetResponse = updateAssetConsumer.getProxy().updateAsset(updateAssetRequest);
			if(updateAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			
			if(validateUpdateAssetResponse(updateAssetResponse, "negativeCase").equalsIgnoreCase("success")) {
				List<CommonErrorData> errorDatas = updateAssetResponse.getErrorMessage().getError();
				
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
	
	/**
	 * @author csubhash
	 * @return
	 */
	public static String testUpdateAsset_invalidVersion(AssetInfo assetInfo) {
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.UPDATED_ASSET_DESCRIPTION);
		basicAssetInfo.setAssetKey(assetInfo.getBasicAssetInfo().getAssetKey());
		basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.UPDATED_ASSET_LONG_DESCRIPTION);
		basicAssetInfo.setAssetName(assetInfo.getBasicAssetInfo().getAssetName());
		basicAssetInfo.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
		basicAssetInfo.setVersion(RepositoryServiceClientConstants.INVALID_ASSET_VERSION);
		
		UpdateAssetRequest updateAssetRequest = new UpdateAssetRequest();
		updateAssetRequest.setBasicAssetInfo(basicAssetInfo);
		
		UpdateAssetConsumer updateAssetConsumer = new UpdateAssetConsumer();
		try {
			UpdateAssetResponse updateAssetResponse = updateAssetConsumer.getProxy().updateAsset(updateAssetRequest);
			if(updateAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetResponse(updateAssetResponse, "negativeCase").equalsIgnoreCase("success")) {	
				
								
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
			e.getMessage();
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

    private static String validateUpdateAssetResponse(UpdateAssetResponse updateAssetResponse, String criteria) {
    	if(criteria.equalsIgnoreCase("positiveCase")) {
    		if(updateAssetResponse.getAck().value().equalsIgnoreCase("success")) {
    			if(updateAssetResponse.getAssetInfo() != null) {
    				//TODO: add validation api specific to update methods, which will validate the user provided fields alone
    				//TODO: confirm it with Arun
    				return RepositoryServiceClientConstants.SUCCESS;
    			}
    		}
    		return "failure";
    	}
    	if(criteria.equalsIgnoreCase("negativeCase")) {
    		if(updateAssetResponse.getAck().value().equalsIgnoreCase("failure")) {
    			if(updateAssetResponse.getErrorMessage().getError().size() > 0) {
    				return "success";
    			}
    		}   		
    	}
    	
    	return "failure";
    }

	public static String resetAsset(AssetInfo assetInfo) {	
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library1 = new Library();
		library1.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library1.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library1);
		
		BasicAssetInfo basicAssetInfo = new BasicAssetInfo();
		basicAssetInfo.setAssetDescription(RepositoryServiceClientConstants.ASSET_DESCRIPTION);
		basicAssetInfo.setAssetKey(assetKey);
		basicAssetInfo.setAssetLongDescription(RepositoryServiceClientConstants.ASSET_LONG_DESCRIPTION);
		basicAssetInfo.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		basicAssetInfo.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
		basicAssetInfo.setVersion(assetInfo.getBasicAssetInfo().getVersion());
		
		UpdateAssetRequest updateAssetRequest = new UpdateAssetRequest();
		updateAssetRequest.setBasicAssetInfo(basicAssetInfo);
		
		UpdateAssetConsumer updateAssetConsumer = new UpdateAssetConsumer();
		try {
			UpdateAssetResponse updateAssetResponse = updateAssetConsumer.getProxy().updateAsset(updateAssetRequest);
			if(updateAssetResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetResponse(updateAssetResponse, "positiveCase").equalsIgnoreCase("success")) {	
				BasicAssetInfo updatedBasicAssetInfo = updateAssetResponse.getAssetInfo().getBasicAssetInfo();
				System.out.println();
				System.out.println("Resetted Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + updatedBasicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + updatedBasicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + updatedBasicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + updatedBasicAssetInfo.getVersion());
				System.out.println();
				
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
			e.getMessage();
			e.printStackTrace();
			return "FAILED";
		}
		
	}
	
}
