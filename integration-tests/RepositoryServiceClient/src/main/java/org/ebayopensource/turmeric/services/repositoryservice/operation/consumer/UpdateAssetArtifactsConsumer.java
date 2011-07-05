/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v1.services.Artifact;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetArtifactsRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetArtifactsResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class UpdateAssetArtifactsConsumer extends BaseRepositoryServiceConsumer {
	private AsyncTurmericRSV1 m_proxy = null;
	public static String testUpdateAssetArtifacts_validAsset(AssetInfo assetInfo) {
		UpdateAssetArtifactsConsumer updateAssetArtifactsConsumer = new UpdateAssetArtifactsConsumer();
			
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library = new Library();
		library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		assetKey.setLibrary(library);
		
		UpdateAssetArtifactsRequest updateAssetArtifactsRequest = new UpdateAssetArtifactsRequest();
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		artifact.setArtifactIdentifier(RepositoryServiceClientConstants.ARTIFACT_ID);
		artifact.setArtifactName("wsdl.txt");		
		artifact.setArtifactCategory(RepositoryServiceClientConstants.ARTIFACT_CATEGORY);		
		artifact.setArtifactValueType(ArtifactValueType.FILE);
		artifactInfo.setArtifact(artifact);
		String temp = "Hello world";
		artifactInfo.setArtifactDetail(temp.getBytes());		
		artifactInfo.setContentType("application/xml");
		updateAssetArtifactsRequest.getArtifactInfo().add(artifactInfo);
		
		try	{
			UpdateAssetArtifactsResponse updateAssetArtifactsResponse = updateAssetArtifactsConsumer.getProxy().updateAssetArtifacts(updateAssetArtifactsRequest);
			if(updateAssetArtifactsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetArtifactsResponse(updateAssetArtifactsResponse, "positiveCase").equalsIgnoreCase("success")) {				
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
	
	public static String testUpdateAssetArtifacts_invalidArtifactId(AssetInfo assetInfo) 
	{
		UpdateAssetArtifactsConsumer updateAssetArtifactsConsumer = new UpdateAssetArtifactsConsumer();
			
		Properties props = CommonUtil.loadPropertyFile("properties/common.properties");
		String libraryId = props.getProperty("libraryId");
		String libraryName = props.getProperty("libraryName", "GovernedAssets");

		Library library = new Library();
		library.setLibraryId(libraryId);
		library.setLibraryName(libraryName);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		assetKey.setLibrary(library);
		
		
		UpdateAssetArtifactsRequest updateAssetArtifactsRequest = new UpdateAssetArtifactsRequest();
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		artifact.setArtifactIdentifier(RepositoryServiceClientConstants.INVALID_ARTIFACT_ID);
		artifact.setArtifactName("wsdl.txt");		
		artifact.setArtifactCategory("ext_doc");
		artifact.setArtifactValueType(ArtifactValueType.FILE);
		artifactInfo.setArtifact(artifact);
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		updateAssetArtifactsRequest.setPartialUpdate(true);
		updateAssetArtifactsRequest.setReplaceCurrent(true);
		
		String temp = "Hello world";
		artifactInfo.setArtifactDetail(temp.getBytes());			
		
		updateAssetArtifactsRequest.getArtifactInfo().add(artifactInfo);				
		
		try	{
			UpdateAssetArtifactsResponse updateAssetArtifactsResponse = updateAssetArtifactsConsumer.getProxy().updateAssetArtifacts(updateAssetArtifactsRequest);
			if(updateAssetArtifactsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetArtifactsResponse(updateAssetArtifactsResponse, "negativeCase").equalsIgnoreCase("success")) {				
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

	public static String testUpdateAssetArtifacts_noArtifactValueType(AssetInfo assetInfo) 
	{
		UpdateAssetArtifactsConsumer updateAssetArtifactsConsumer = new UpdateAssetArtifactsConsumer();
			
		Properties props = CommonUtil.loadPropertyFile("properties/common.properties");
		String libraryId = props.getProperty("libraryId");
		String libraryName = props.getProperty("libraryName", "GovernedAssets");

		Library library = new Library();
		library.setLibraryId(libraryId);
		library.setLibraryName(libraryName);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		assetKey.setLibrary(library);
		
		
		UpdateAssetArtifactsRequest updateAssetArtifactsRequest = new UpdateAssetArtifactsRequest();
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		artifact.setArtifactIdentifier(RepositoryServiceClientConstants.INVALID_ARTIFACT_ID);
		artifact.setArtifactName("wsdl.txt");		
		artifact.setArtifactCategory("ext_doc");
		//artifact.setArtifactValueType(ArtifactValueType.FILE);
		artifactInfo.setArtifact(artifact);
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		updateAssetArtifactsRequest.setPartialUpdate(true);
		updateAssetArtifactsRequest.setReplaceCurrent(true);
		
		try {
			File file = new File("c:\\repsonse.xml");
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] artifactDetail = new byte[1000000];
			fileInputStream.read(artifactDetail);
			artifactInfo.setArtifactDetail(artifactDetail);			
		}
		catch (IOException ioe) {
			ioe.getMessage();
			ioe.getStackTrace();
		}		

		
		updateAssetArtifactsRequest.getArtifactInfo().add(artifactInfo);
		
		
		
		try	{
			UpdateAssetArtifactsResponse updateAssetArtifactsResponse = updateAssetArtifactsConsumer.getProxy().updateAssetArtifacts(updateAssetArtifactsRequest);
			if(updateAssetArtifactsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetArtifactsResponse(updateAssetArtifactsResponse, "negativeCase").equalsIgnoreCase("success")) {				
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

	public static String testUpdateAssetArtifacts_noArtifactName(AssetInfo assetInfo) 
	{
		UpdateAssetArtifactsConsumer updateAssetArtifactsConsumer = new UpdateAssetArtifactsConsumer();
			
		Properties props = CommonUtil.loadPropertyFile("properties/common.properties");
		String libraryId = props.getProperty("libraryId");
		String libraryName = props.getProperty("libraryName", "GovernedAssets");

		Library library = new Library();
		library.setLibraryId(libraryId);
		library.setLibraryName(libraryName);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		assetKey.setLibrary(library);
		
		
		UpdateAssetArtifactsRequest updateAssetArtifactsRequest = new UpdateAssetArtifactsRequest();
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		artifact.setArtifactIdentifier(RepositoryServiceClientConstants.INVALID_ARTIFACT_ID);
		//artifact.setArtifactName("wsdl.txt");		
		artifact.setArtifactCategory("ext_doc");
		artifact.setArtifactValueType(ArtifactValueType.FILE);
		artifactInfo.setArtifact(artifact);
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		updateAssetArtifactsRequest.setPartialUpdate(true);
		updateAssetArtifactsRequest.setReplaceCurrent(true);
		
		try {
			File file = new File("c:\\repsonse.xml");
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] artifactDetail = new byte[1000000];
			fileInputStream.read(artifactDetail);
			artifactInfo.setArtifactDetail(artifactDetail);			
		}
		catch (IOException ioe) {
			ioe.getMessage();
			ioe.getStackTrace();
		}		

		
		updateAssetArtifactsRequest.getArtifactInfo().add(artifactInfo);
		
		
		
		try	{
			UpdateAssetArtifactsResponse updateAssetArtifactsResponse = updateAssetArtifactsConsumer.getProxy().updateAssetArtifacts(updateAssetArtifactsRequest);
			if(updateAssetArtifactsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetArtifactsResponse(updateAssetArtifactsResponse, "negativeCase").equalsIgnoreCase("success")) {				
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

	public static String testUpdateAssetArtifacts_noArtifactCategory(AssetInfo assetInfo) 
	{
		UpdateAssetArtifactsConsumer updateAssetArtifactsConsumer = new UpdateAssetArtifactsConsumer();
			
		Properties props = CommonUtil.loadPropertyFile("properties/common.properties");
		String libraryId = props.getProperty("libraryId");
		String libraryName = props.getProperty("libraryName", "GovernedAssets");

		Library library = new Library();
		library.setLibraryId(libraryId);
		library.setLibraryName(libraryName);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		assetKey.setLibrary(library);
		
		
		UpdateAssetArtifactsRequest updateAssetArtifactsRequest = new UpdateAssetArtifactsRequest();
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		artifact.setArtifactIdentifier(RepositoryServiceClientConstants.INVALID_ARTIFACT_ID);
		artifact.setArtifactName("wsdl.txt");		
		//artifact.setArtifactCategory(ArtifactCategory.EXTERNAL_DOCUMENTATION);
		artifact.setArtifactValueType(ArtifactValueType.FILE);
		artifactInfo.setArtifact(artifact);
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		updateAssetArtifactsRequest.setPartialUpdate(true);
		updateAssetArtifactsRequest.setReplaceCurrent(true);
		
		try {
			File file = new File("c:\\repsonse.xml");
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] artifactDetail = new byte[1000000];
			fileInputStream.read(artifactDetail);
			artifactInfo.setArtifactDetail(artifactDetail);			
		}
		catch (IOException ioe) {
			ioe.getMessage();
			ioe.getStackTrace();
		}		

		
		updateAssetArtifactsRequest.getArtifactInfo().add(artifactInfo);
		
		
		
		try	{
			UpdateAssetArtifactsResponse updateAssetArtifactsResponse = updateAssetArtifactsConsumer.getProxy().updateAssetArtifacts(updateAssetArtifactsRequest);
			if(updateAssetArtifactsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetArtifactsResponse(updateAssetArtifactsResponse, "negativeCase").equalsIgnoreCase("success")) {				
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

	public static String testUpdateAssetArtifacts_noArtifactDetail(AssetInfo assetInfo) 
	{
		UpdateAssetArtifactsConsumer updateAssetArtifactsConsumer = new UpdateAssetArtifactsConsumer();
			
		Properties props = CommonUtil.loadPropertyFile("properties/common.properties");
		String libraryId = props.getProperty("libraryId");
		String libraryName = props.getProperty("libraryName", "GovernedAssets");

		Library library = new Library();
		library.setLibraryId(libraryId);
		library.setLibraryName(libraryName);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		assetKey.setLibrary(library);
		
		
		UpdateAssetArtifactsRequest updateAssetArtifactsRequest = new UpdateAssetArtifactsRequest();
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		artifact.setArtifactIdentifier(RepositoryServiceClientConstants.INVALID_ARTIFACT_ID);
		artifact.setArtifactName("wsdl.txt");		
		//artifact.setArtifactCategory(ArtifactCategory.EXTERNAL_DOCUMENTATION);
		artifact.setArtifactValueType(ArtifactValueType.FILE);
		artifactInfo.setArtifact(artifact);
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		updateAssetArtifactsRequest.setPartialUpdate(true);
		updateAssetArtifactsRequest.setReplaceCurrent(true);
		artifactInfo.setArtifactDetail(null);
				
		updateAssetArtifactsRequest.getArtifactInfo().add(artifactInfo);
		
		
		
		try	{
			UpdateAssetArtifactsResponse updateAssetArtifactsResponse = updateAssetArtifactsConsumer.getProxy().updateAssetArtifacts(updateAssetArtifactsRequest);
			if(updateAssetArtifactsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetArtifactsResponse(updateAssetArtifactsResponse, "negativeCase").equalsIgnoreCase("success")) {				
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

	public static String testUpdateAssetArtifacts_invalidAsset() {
		UpdateAssetArtifactsConsumer updateAssetArtifactsConsumer = new UpdateAssetArtifactsConsumer();
			
		Properties props = CommonUtil.loadPropertyFile("properties/common.properties");
		String libraryId = props.getProperty("libraryId");
		String libraryName = props.getProperty("libraryName", "GovernedAssets");

		Library library = new Library();
		library.setLibraryId(libraryId);
		library.setLibraryName(libraryName);
		
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
		assetKey.setAssetName(RepositoryServiceClientConstants.INVALID_ASSET_NAME);
		assetKey.setLibrary(library);
		
		UpdateAssetArtifactsRequest updateAssetArtifactsRequest = new UpdateAssetArtifactsRequest();
		updateAssetArtifactsRequest.setAssetKey(assetKey);
		
		ArtifactInfo artifactInfo = new ArtifactInfo();
		Artifact artifact = new Artifact();
		artifact.setArtifactIdentifier(RepositoryServiceClientConstants.ARTIFACT_ID);
		artifact.setArtifactName("wsdl.txt");		
		artifact.setArtifactCategory(RepositoryServiceClientConstants.ARTIFACT_CATEGORY);		
		artifactInfo.setArtifact(artifact);
		//DataHandler dataHandler = new DataHandler(new FileDataSource(new File("c:\\repsonse.xml")));
		try {
			File file = new File("c:\\repsonse.xml");
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] artifactDetail = new byte[1000000];
			fileInputStream.read(artifactDetail);
			artifactInfo.setArtifactDetail(artifactDetail);			
		}
		catch (IOException ioe) {
			ioe.getMessage();
			ioe.getStackTrace();
		}		

		artifactInfo.setContentType("application/xml");
		updateAssetArtifactsRequest.getArtifactInfo().add(artifactInfo);		
		
		try	{
			UpdateAssetArtifactsResponse updateAssetArtifactsResponse = updateAssetArtifactsConsumer.getProxy().updateAssetArtifacts(updateAssetArtifactsRequest);
			if(updateAssetArtifactsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}			
			if(validateUpdateAssetArtifactsResponse(updateAssetArtifactsResponse, "negativeCase").equalsIgnoreCase("success")) {				
				List<CommonErrorData> errorDatas = updateAssetArtifactsResponse.getErrorMessage().getError();
				
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
    



    private static String validateUpdateAssetArtifactsResponse(UpdateAssetArtifactsResponse updateAssetArtifactsResponse, String criteria) {
    	if(criteria.equalsIgnoreCase("positiveCase")) {
    		if(updateAssetArtifactsResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
				List<Artifact> artifacts = updateAssetArtifactsResponse.getArtifact();
				
				for (Artifact artifact : artifacts) {
					if(RepositoryServiceConsumerUtil.validateArtifact(artifact).equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
						return RepositoryServiceClientConstants.FAILURE;
					}
				}
				return RepositoryServiceClientConstants.SUCCESS;
    		}
    		return RepositoryServiceClientConstants.FAILURE;
    	}
    	if(criteria.equalsIgnoreCase("negativeCase")) {
    		if(updateAssetArtifactsResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
    			if(updateAssetArtifactsResponse.getErrorMessage().getError().size() > 0) {
    				return RepositoryServiceClientConstants.SUCCESS;
    			}
    		}   	
    		if(updateAssetArtifactsResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
    			if(updateAssetArtifactsResponse.getErrorMessage().getError().size() > 0) {
    				return RepositoryServiceClientConstants.SUCCESS;
    			}
    		}  
    	}
    	
    	return RepositoryServiceClientConstants.FAILURE;
    }

}
