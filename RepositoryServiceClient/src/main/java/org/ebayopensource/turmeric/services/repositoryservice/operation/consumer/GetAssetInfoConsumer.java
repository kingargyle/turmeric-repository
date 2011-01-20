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
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.TurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetAssetInfoConsumer extends BaseRepositoryServiceConsumer {
	private TurmericRSV1 m_proxy = null;
	private static GetAssetInfoConsumer getAssetInfoConsumer = new GetAssetInfoConsumer();
	private static String securityCookie = null;
	private static Service service = null;

	/*
	public static void main(String[] args) {		
		Map<String, String> results = new HashMap<String, String>();

		results.put("testGetAssetInfo_validAsset_forpublished",            testGetAssetInfo_validAsset_forpublished());
//		results.put("testGetAssetInfo_validAsset_byName_forpublished",     testGetAssetInfo_validAsset_byName_forpublished());
//		results.put("testGetAssetInfo_invalidAssetKey_forpublished",       testGetAssetInfo_forpublished("invalidAssetKey"));
//		results.put("testGetAssetInfo_invalidAssetVersion_forpublished",   testGetAssetInfo_forpublished("invalidAssetVersion"));
//		results.put("testGetAssetInfo_invalidAssetType_forpublished",      testGetAssetInfo_forpublished("invalidAssetType"));
//		results.put("testGetAssetInfo_insufficientPrivilege_forpublished", testGetAssetInfo_forpublished("insufficientPrivilege"));
//		results.put("testGetAssetInfo_fromDifferentlibraries_validAsset_forpublished", testGetAssetInfo_fromDifferentlibraries_validAsset_forpublished());
//	    results.put("testGetAssetInfo_validAsset",            testGetAssetInfo_validAsset());
//	    results.put("testGetAssetInfo_validAsset_byName",     testGetAssetInfo_validAsset_byName());
//		results.put("testGetAssetInfo_invalidAssetKey",       testGetAssetInfo("invalidAssetKey"));
//		results.put("testGetAssetInfo_invalidAssetVersion",   testGetAssetInfo("invalidAssetVersion"));
//		results.put("testGetAssetInfo_invalidAssetType",      testGetAssetInfo("invalidAssetType"));
//		results.put("testGetAssetInfo_insufficientPrivilege", testGetAssetInfo("insufficientPrivilege"));
//		results.put("testGetAssetInfo_fromDifferentlibraries_validAsset", testGetAssetInfo_fromDifferentlibraries_validAsset());
			
		
		
		
		
		System.out.println("\ngetAssetInfo Summary");		
		Set<String> methods = results.keySet();
		int i = 1;
		for (String method : methods) 
		{
			System.out.format("%d %-60s: %s\n",i, method, results.get(method));
			i++;
		}
		System.out.println(); 
	}

	public static Map<String, String> testGetAssetInfo()
	{
		Map<String, String> results = new HashMap<String, String>();

		results.put("testGetAssetInfo_validAsset",            testGetAssetInfo_validAsset());
	    results.put("testGetAssetInfo_validAsset_byName",     testGetAssetInfo_validAsset_byName());
		results.put("testGetAssetInfo_invalidAssetKey",       testGetAssetInfo("invalidAssetKey"));
		results.put("testGetAssetInfo_invalidAssetVersion",   testGetAssetInfo("invalidAssetVersion"));
		results.put("testGetAssetInfo_invalidAssetType",      testGetAssetInfo("invalidAssetType"));
		results.put("testGetAssetInfo_insufficientPrivilege", testGetAssetInfo("insufficientPrivilege"));
		results.put("testGetAssetInfo_fromDifferentlibraries_validAsset", testGetAssetInfo_fromDifferentlibraries_validAsset());
		return results;
	}
	*/
	public static String testGetAssetInfo_fromDifferentlibraries_validAsset(AssetInfo governedAssetInfo,AssetInfo systemAssetInfo) 
	{
		String returnString = null;
		//GetAssetInfoConsumer getAssetInfoConsumer = new GetAssetInfoConsumer();
		AssetKey assetKey1 = new AssetKey();
		assetKey1.setAssetId(governedAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey1.setAssetName(governedAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library1 = new Library();
		library1.setLibraryId(governedAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library1.setLibraryName(governedAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey1.setLibrary(library1);
		
		
		
		AssetKey assetKey2 = new AssetKey();
		assetKey2.setAssetId(systemAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey2.setAssetName(systemAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library2 = new Library();
		library2.setLibraryId(systemAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library2.setLibraryName(systemAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey2.setLibrary(library2);

		/*AssetKey assetKey2 = new AssetKey();
		//assetKey2.setAssetId(RepositoryServiceClientConstants.ASSET_ID2);
		assetKey2.setAssetName(RepositoryServiceClientConstants.ASSET_NAME2);
		assetKey2.setLibrary(library2);*/
		
		GetAssetInfoRequest getAssetInfoRequest = new GetAssetInfoRequest();
		getAssetInfoRequest.setAssetKey(assetKey1);
		getAssetInfoRequest.setAssetType(governedAssetInfo.getBasicAssetInfo().getAssetType());
		getAssetInfoRequest.setVersion(governedAssetInfo.getBasicAssetInfo().getVersion());
		
		GetAssetInfoRequest getAssetInfoRequest1 = new GetAssetInfoRequest();
		getAssetInfoRequest1.setAssetKey(assetKey2);
		getAssetInfoRequest1.setAssetType(systemAssetInfo.getBasicAssetInfo().getAssetType());
		getAssetInfoRequest1.setVersion(systemAssetInfo.getBasicAssetInfo().getVersion());
				
		try	{
			GetAssetInfoResponse getAssetInfoResponse = getAssetInfoConsumer.getProxy().getAssetInfo(getAssetInfoRequest);
			

			if(getAssetInfoResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetInfoResponse(getAssetInfoResponse, "positiveCase").equalsIgnoreCase("success")) {
				AssetInfo assetInfoResponse = getAssetInfoResponse.getAssetInfo();				
				BasicAssetInfo basicAssetInfo = assetInfoResponse.getBasicAssetInfo();
				System.out.println();
				System.out.println("Basic Asset information of the asset '" + assetKey1.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + basicAssetInfo.getVersion());
				System.out.println();				
				
				returnString = "PASSED";
			}
			else {
				returnString = "FAILED";
			}
			
			GetAssetInfoResponse getAssetInfoResponse1 = getAssetInfoConsumer.getFreshProxy().getAssetInfo(getAssetInfoRequest1);
			

			if(getAssetInfoResponse1 == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetInfoResponse(getAssetInfoResponse1, "positiveCase").equalsIgnoreCase("success")) {
				AssetInfo assetInfoResponse = getAssetInfoResponse1.getAssetInfo();				
				BasicAssetInfo basicAssetInfo = assetInfoResponse.getBasicAssetInfo();
				System.out.println();
				System.out.println("Basic Asset information of the asset '" + assetKey2.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + basicAssetInfo.getVersion());
				System.out.println();				
				if(!returnString.equals("FAILED"))
				{
					returnString = "PASSED";
				}
			}
			else {
				returnString = "FAILED";
			}
			
			/*GetAssetInfoResponse getAssetInfoResponse2 = getAssetInfoConsumer.getProxy().getAssetInfo(getAssetInfoRequest2);
			

			if(getAssetInfoResponse2 == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(getAssetInfoResponse2!= null&& getAssetInfoResponse2.getAssetInfo()!= null) {
				AssetInfo assetInfo = getAssetInfoResponse2.getAssetInfo();				
				BasicAssetInfo basicAssetInfo = assetInfo.getBasicAssetInfo();
				System.out.println();
				System.out.println("Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + basicAssetInfo.getVersion());
				System.out.println();				
				
				if(!returnString.equals("FAILED"))
				{
					returnString = "PASSED";
				}
			}
			else {
				returnString = "FAILED";
			}*/
		}
		catch(ServiceException se) {
			se.getMessage();
			se.printStackTrace();	
			returnString = "FAILED";
		}
		catch(Exception e) {
			e.printStackTrace();
			returnString = "FAILED";
		}			
		return returnString;
	}
	
	
	public static String testGetAssetInfo_fromDifferentlibraries_validAsset_forpublished(AssetInfo systemAssetInfo,AssetInfo governedAssetInfo) 
	{
		String returnString = null;
			
		AssetKey assetKey1 = new AssetKey();
		assetKey1.setAssetId(governedAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey1.setAssetName(governedAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library1 = new Library();
		library1.setLibraryId(governedAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library1.setLibraryName(governedAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey1.setLibrary(library1);
		
		
		
		AssetKey assetKey2 = new AssetKey();
		assetKey2.setAssetId(systemAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey2.setAssetName(systemAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library2 = new Library();
		library2.setLibraryId(systemAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library2.setLibraryName(systemAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey2.setLibrary(library2);
		
		GetAssetInfoRequest getAssetInfoRequest = new GetAssetInfoRequest();
		getAssetInfoRequest.setAssetKey(assetKey1);
		getAssetInfoRequest.setAssetType(governedAssetInfo.getBasicAssetInfo().getAssetType());
		getAssetInfoRequest.setVersion(governedAssetInfo.getBasicAssetInfo().getVersion());
		
		GetAssetInfoRequest getAssetInfoRequest1 = new GetAssetInfoRequest();
		getAssetInfoRequest1.setAssetKey(assetKey2);
		getAssetInfoRequest1.setAssetType(systemAssetInfo.getBasicAssetInfo().getAssetType());
		getAssetInfoRequest1.setVersion(systemAssetInfo.getBasicAssetInfo().getVersion());
			
		try	{
			GetAssetInfoResponse getAssetInfoResponse = getAssetInfoConsumer.getProxy().getAssetInfo(getAssetInfoRequest);
			

			if(getAssetInfoResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetInfoResponse(getAssetInfoResponse, "positiveCase").equalsIgnoreCase("success")) {
				AssetInfo assetInfo = getAssetInfoResponse.getAssetInfo();				
				BasicAssetInfo basicAssetInfo = assetInfo.getBasicAssetInfo();
				System.out.println();
				System.out.println("Basic Asset information of the asset '" + assetKey1.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + basicAssetInfo.getVersion());
				System.out.println();				
				
				returnString = "PASSED";
			}
			else {
				returnString = "FAILED";
			}
			
			GetAssetInfoResponse getAssetInfoResponse1 = getAssetInfoConsumer.getFreshProxy().getAssetInfo(getAssetInfoRequest1);
			

			if(getAssetInfoResponse1 == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetInfoResponse(getAssetInfoResponse1, "positiveCase").equalsIgnoreCase("success")) {
				AssetInfo assetInfo = getAssetInfoResponse1.getAssetInfo();				
				BasicAssetInfo basicAssetInfo = assetInfo.getBasicAssetInfo();
				System.out.println();
				System.out.println("Basic Asset information of the asset '" + assetKey2.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + basicAssetInfo.getVersion());
				System.out.println();				
				if(!returnString.equals("FAILED"))
				{
					returnString = "PASSED";
				}
			}
			else {
				returnString = "FAILED";
			}
		}
		catch(ServiceException se) {
			se.getMessage();
			se.printStackTrace();	
			returnString = "FAILED";
		}
		catch(Exception e) {
			e.printStackTrace();
			returnString = "FAILED";
		}			
		return returnString;
	}
	
	
	public static String testGetAssetInfo_validAsset_byName(AssetKey assetKey) {
			
		GetAssetInfoRequest getAssetInfoRequest = new GetAssetInfoRequest();
		getAssetInfoRequest.setAssetKey(assetKey);
		getAssetInfoRequest.setAssetType("Service");
		getAssetInfoRequest.setVersion("1.0.0");
		
		try	{
			GetAssetInfoResponse getAssetInfoResponse = getAssetInfoConsumer.getProxy().getAssetInfo(getAssetInfoRequest);
			

			if(getAssetInfoResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetInfoResponse(getAssetInfoResponse, "positiveCase").equalsIgnoreCase("success")) {
				AssetInfo assetInfoResponse = getAssetInfoResponse.getAssetInfo();				
				BasicAssetInfo basicAssetInfo = assetInfoResponse.getBasicAssetInfo();
				System.out.println();
				System.out.println("Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + basicAssetInfo.getVersion());
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
			e.printStackTrace();
			return "FAILED";
		}			

	}
	
	public static String testGetAssetInfo_validAsset_byName_forpublished(AssetInfo assetInfo) {
		//GetAssetInfoConsumer getAssetInfoConsumer = new GetAssetInfoConsumer();
			
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		
		Library library = new Library();
		library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library);
		
		
		GetAssetInfoRequest getAssetInfoRequest = new GetAssetInfoRequest();
		getAssetInfoRequest.setAssetKey(assetKey);
		getAssetInfoRequest.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
		getAssetInfoRequest.setVersion(assetInfo.getBasicAssetInfo().getVersion());
		getAssetInfoRequest.setPublished(new Boolean(true));
		try	{
			GetAssetInfoResponse getAssetInfoResponse = getAssetInfoConsumer.getProxy().getAssetInfo(getAssetInfoRequest);
			

			if(getAssetInfoResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetInfoResponse(getAssetInfoResponse, "positiveCase").equalsIgnoreCase("success")) {
				AssetInfo responseAssetInfo = getAssetInfoResponse.getAssetInfo();				
				BasicAssetInfo basicAssetInfo = responseAssetInfo.getBasicAssetInfo();
				System.out.println();
				System.out.println("Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + basicAssetInfo.getVersion());
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
			e.printStackTrace();
			return "FAILED";
		}			

	}
	
	public static String testGetAssetInfo_validAsset(AssetKey assetKey) {
		//GetAssetInfoConsumer getAssetInfoConsumer = new GetAssetInfoConsumer();
			
		
		GetAssetInfoRequest getAssetInfoRequest = new GetAssetInfoRequest();
		getAssetInfoRequest.setAssetKey(assetKey);
		/*getAssetInfoRequest.setAssetType("Assertion");
		getAssetInfoRequest.setVersion("1.0.0");*/
		try	{
			GetAssetInfoResponse getAssetInfoResponse = getAssetInfoConsumer.getProxy().getAssetInfo(getAssetInfoRequest);
			

			if(getAssetInfoResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetInfoResponse(getAssetInfoResponse, "positiveCase").equalsIgnoreCase("success")) {
				AssetInfo assetInfoResponse = getAssetInfoResponse.getAssetInfo();				
				BasicAssetInfo basicAssetInfo = assetInfoResponse.getBasicAssetInfo();
				System.out.println();
				System.out.println("Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + basicAssetInfo.getVersion());
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
			e.printStackTrace();
			return "FAILED";
		}			

	}	
	
	public static String testGetAssetInfo_validAsset_forpublished(AssetInfo assetInfo) {
		//GetAssetInfoConsumer getAssetInfoConsumer = new GetAssetInfoConsumer();
			
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library = new Library();
		library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library);
		
		
		GetAssetInfoRequest getAssetInfoRequest = new GetAssetInfoRequest();
		getAssetInfoRequest.setAssetKey(assetKey);
		getAssetInfoRequest.setPublished(new Boolean(false));
		/*getAssetInfoRequest.setAssetType("Assertion");
		getAssetInfoRequest.setVersion("1.0.0");*/
		try	{
			GetAssetInfoResponse getAssetInfoResponse = getAssetInfoConsumer.getProxy().getAssetInfo(getAssetInfoRequest);
			

			if(getAssetInfoResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateGetAssetInfoResponse(getAssetInfoResponse, "positiveCase").equalsIgnoreCase("success")) {
				AssetInfo assetInfoResponse = getAssetInfoResponse.getAssetInfo();				
				BasicAssetInfo basicAssetInfo = assetInfoResponse.getBasicAssetInfo();
				System.out.println();
				System.out.println("Basic Asset information of the asset '" + assetKey.getAssetName() + "' is as follows");
				System.out.println("AssetType             : " + basicAssetInfo.getAssetType());
				System.out.println("Asset Description     : " + basicAssetInfo.getAssetDescription());
				System.out.println("Asset long description: " + basicAssetInfo.getAssetLongDescription());
				System.out.println("Asset version         : " + basicAssetInfo.getVersion());
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
			e.printStackTrace();
			return "FAILED";
		}			

	}	

	public static String testGetAssetInfo(String variant,AssetInfo assetInfo) {
		//GetAssetInfoConsumer getAssetInfoConsumer = new GetAssetInfoConsumer();
			
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library = new Library();
		library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library);
		
		GetAssetInfoRequest getAssetInfoRequest = new GetAssetInfoRequest();
		getAssetInfoRequest.setAssetKey(assetKey);
		
		if(variant.equalsIgnoreCase("invalidAssetKey")) {
			getAssetInfoRequest.getAssetKey().setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
		}
		if(variant.equalsIgnoreCase("invalidAssetVersion")) 
		{
			getAssetInfoRequest.getAssetKey().setAssetId(null);
			getAssetInfoRequest.setVersion(RepositoryServiceClientConstants.INVALID_ASSET_VERSION);
		}
		if(variant.equalsIgnoreCase("invalidAssetType")) {
			getAssetInfoRequest.getAssetKey().setAssetId(null);
			getAssetInfoRequest.setAssetType(RepositoryServiceClientConstants.INVALID_ASSET_TYPE);
		}
		if(variant.equalsIgnoreCase("insufficientPrivilege")) {
			getAssetInfoRequest.getAssetKey().setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
		}
		
		try	{
			GetAssetInfoResponse getAssetInfoResponse = getAssetInfoConsumer.getProxy().getAssetInfo(getAssetInfoRequest);
			

			if(getAssetInfoResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			
			if(validateGetAssetInfoResponse(getAssetInfoResponse, "negativeCase").equalsIgnoreCase("success")) {
				List<CommonErrorData> errorDatas = getAssetInfoResponse.getErrorMessage().getError();
				
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
	
	public static String testGetAssetInfo_forpublished(String variant,AssetInfo assetInfo) {
		//GetAssetInfoConsumer getAssetInfoConsumer = new GetAssetInfoConsumer();
			
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library = new Library();
		library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library);
		
		GetAssetInfoRequest getAssetInfoRequest = new GetAssetInfoRequest();
		getAssetInfoRequest.setAssetKey(assetKey);
		getAssetInfoRequest.setPublished(new Boolean(true));
		if(variant.equalsIgnoreCase("invalidAssetKey")) {
			getAssetInfoRequest.getAssetKey().setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
		}
		if(variant.equalsIgnoreCase("invalidAssetVersion")) 
		{
			getAssetInfoRequest.getAssetKey().setAssetId(null);
			getAssetInfoRequest.setVersion(RepositoryServiceClientConstants.INVALID_ASSET_VERSION);
		}
		if(variant.equalsIgnoreCase("invalidAssetType")) {
			getAssetInfoRequest.getAssetKey().setAssetId(null);
			getAssetInfoRequest.setAssetType(RepositoryServiceClientConstants.INVALID_ASSET_TYPE);
		}
		if(variant.equalsIgnoreCase("insufficientPrivilege")) {
			getAssetInfoRequest.getAssetKey().setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
		}
		
		try	{
			GetAssetInfoResponse getAssetInfoResponse = getAssetInfoConsumer.getProxy().getAssetInfo(getAssetInfoRequest);
			

			if(getAssetInfoResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			
			if(validateGetAssetInfoResponse(getAssetInfoResponse, "negativeCase").equalsIgnoreCase("success")) {
				List<CommonErrorData> errorDatas = getAssetInfoResponse.getErrorMessage().getError();
				
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
    	if(service==null)
    	 	service = ServiceFactory.create(RepositoryServiceClientConstants.SERVICE_NAME, RepositoryServiceClientConstants.SERVICE_NAME);
    	
        // get security cookie after first successful login
        if (securityCookie == null) {
            securityCookie = service.getResponseContext().getTransportHeader(
                "X-TURMERIC-SECURITY-COOKIE");
        }

        // Use security cookie if present or use userid/password
        RequestContext requestContext = service.getRequestContext();
        if (securityCookie != null) {
           // logger.debug("Found X-TURMERIC-SECURITY-COOKIE="+securityCookie);
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-COOKIE", securityCookie);
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-USERID", "");
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-PASSWORD", "");
        } else {
          //  logger.debug("Using password header, did not find X-TURMERIC-SECURITY-COOKIE");
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-USERID", "_regNormalUser");
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-PASSWORD", "rm@ebay01");
        }
        return (AsyncTurmericRSV1) service.getProxy();
    
    }
    protected TurmericRSV1 getFreshProxy() throws ServiceException 
    {
    	if(service==null)
    	 	service = ServiceFactory.create(RepositoryServiceClientConstants.SERVICE_NAME, RepositoryServiceClientConstants.SERVICE_NAME);
    	
        // get security cookie after first successful login
        if (securityCookie == null) {
            securityCookie = service.getResponseContext().getTransportHeader(
                "X-TURMERIC-SECURITY-COOKIE");
        }

        // Use security cookie if present or use userid/password
        RequestContext requestContext = service.getRequestContext();
        requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-USERID", "csubhash");
         requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-PASSWORD", "Chandu411&1020");
        
        return (TurmericRSV1) service.getProxy();
    
    }
    
    
    private static String validateGetAssetInfoResponse(GetAssetInfoResponse getAssetInfoResponse, String criteria) {
    	if(criteria.equalsIgnoreCase("positiveCase")) {
    		if(getAssetInfoResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
				AssetInfo assetInfo = getAssetInfoResponse.getAssetInfo();
				if(assetInfo == null) {
					return RepositoryServiceClientConstants.FAILURE;
				}	
				return RepositoryServiceConsumerUtil.validateAssetInfo(assetInfo);
    		}
    		if(getAssetInfoResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
				AssetInfo assetInfo = getAssetInfoResponse.getAssetInfo();
				if(assetInfo == null) {
					return RepositoryServiceClientConstants.FAILURE;
				}	
				return RepositoryServiceConsumerUtil.validateAssetInfo(assetInfo, "partialValidation");
    		}
    		return RepositoryServiceClientConstants.FAILURE;
    	}
    	if(criteria.equalsIgnoreCase("negativeCase")) {
    		if(getAssetInfoResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
    			if(getAssetInfoResponse.getErrorMessage().getError().size() > 0) {
    				return RepositoryServiceClientConstants.SUCCESS;
    			}
    		}   		
    	}
    	
    	return RepositoryServiceClientConstants.FAILURE;
    }
}
