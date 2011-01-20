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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v1.services.Artifact;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v1.services.BasicServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.ExtendedServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationshipForUpdate;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.Relation;
import org.ebayopensource.turmeric.repository.v1.services.RelationForUpdate;
import org.ebayopensource.turmeric.repository.v1.services.ServiceDesignTimeInfo;
import org.ebayopensource.turmeric.repository.v1.services.ServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.ServiceInfoForUpdate;
import org.ebayopensource.turmeric.repository.v1.services.UpdateServiceRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateServiceResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class UpdateServiceConsumer extends BaseRepositoryServiceConsumer {
	private AsyncTurmericRSV1 m_proxy = null;
	public static String testUpdateService_validInput(String variant) {
		UpdateServiceConsumer updateServiceConsumer = new UpdateServiceConsumer();
		
		AssetKey assetkey = new AssetKey();
		Library lib = new Library();
		lib.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
		lib.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
		assetkey.setAssetId(RepositoryServiceClientConstants.ASSET__SERVICE_UPDATE_ID);
		assetkey.setLibrary(lib);
		assetkey.setAssetName("AnavAsset1");
		ServiceInfoForUpdate serviceInfo = new ServiceInfoForUpdate();
		BasicServiceInfo basicServiceInfo = new BasicServiceInfo();
		basicServiceInfo.setAssetKey(assetkey);
		basicServiceInfo.setServiceDescription("new desc 123");
		basicServiceInfo.setServiceNamespace("www.123.com");
		AssetLifeCycleInfo assetLifeCycleInfo = new AssetLifeCycleInfo(); 
		assetLifeCycleInfo.setProductManager("pm123");
		assetLifeCycleInfo.setDomainOwner("do123");
		ServiceDesignTimeInfo serviceDesignTimeInfo = new ServiceDesignTimeInfo();
		serviceDesignTimeInfo.setInterfaceClass("interface123");
		ExtendedServiceInfo extendedServiceInfo = new ExtendedServiceInfo();
		extendedServiceInfo.setServiceDesignTimeInfo(serviceDesignTimeInfo);
		serviceInfo.setExtendedServiceInfo(extendedServiceInfo);
		serviceInfo.setBasicServiceInfo(basicServiceInfo);
		serviceInfo.setAssetLifeCycleInfo(assetLifeCycleInfo);				
		
		FlattenedRelationshipForUpdate flattenedRelationshipForUpdate = new FlattenedRelationshipForUpdate();
		
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
										
		RelationForUpdate relationForUpdate1 = new RelationForUpdate();
		AssetKey currentSourceAssetKey = new AssetKey();
		currentSourceAssetKey.setAssetId("1.0_1232509159309-1849001722");
		currentSourceAssetKey.setLibrary(library);
		relationForUpdate1.setCurrentSourceAsset(assetkey);
		AssetKey currentTargetAssetKey = new AssetKey();
		currentTargetAssetKey.setAssetId("1.0_12325091615121940406949");
		currentTargetAssetKey.setLibrary(library);
		relationForUpdate1.setCurrentTargetAsset(currentTargetAssetKey);
		Relation relation1  = new Relation();
		relation1.setAssetRelationship("dependent_service");
		AssetKey targetAssetKey1 = new AssetKey();
		targetAssetKey1.setAssetId("1.0_1232509163418-16249845");
		targetAssetKey1.setLibrary(library);
		relation1.setTargetAsset(targetAssetKey1);
		relationForUpdate1.setNewRelation(relation1);
		flattenedRelationshipForUpdate.getRelatedAsset().add(relationForUpdate1);
		
		//updateAssetDependenciesRequest.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);
		serviceInfo.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);
		
		
		 ArtifactInfo artifactInfo = new ArtifactInfo();
		  
		  Artifact artifact = new Artifact();
		  artifact.setArtifactName("FileArtifact1.txt");
		  artifact.setArtifactValueType(ArtifactValueType.FILE);
		  artifact.setArtifactCategory("ext_doc");
		  
		  artifactInfo.setArtifact(artifact);
			File file = null;
			OutputStream outputStream = null;
			try {
				file = File.createTempFile("test", ".txt");
				 outputStream = new FileOutputStream(file);
				outputStream.write(20);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				if(outputStream != null)
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  byte[] fileByte = new byte[(int)file.length()];
		  try {
		   fileInputStream.read(fileByte);
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  artifactInfo.setArtifactDetail(fileByte);
		  
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  
		  artifactInfo = new ArtifactInfo();
		  artifact = new Artifact();
		  artifact.setArtifactIdentifier("LL:artifact:wsdl:1");
		  artifact.setArtifactName("TESTINPUT2.txt");
		  artifact.setArtifactCategory("wsdl");
		  artifactInfo.setArtifact(artifact);  
		  artifact.setArtifactValueType(ArtifactValueType.FILE);
		  file = null;
		  file = new File("C:\\testing.txt");
		  try {
		   fileInputStream = new FileInputStream(file);
		  } catch (FileNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  fileByte = new byte[(int)file.length()];
		  try {
		   fileInputStream.read(fileByte);
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  artifactInfo.setArtifactDetail(fileByte);
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  
		  
		  artifactInfo = new ArtifactInfo();
		  artifact = new Artifact();
		  
		  artifact.setArtifactName("URL Artifact");
		  artifact.setArtifactCategory("ext_doc");
		  artifactInfo.setArtifact(artifact);  
		  artifactInfo.setArtifactDetail("http://www.google.com".getBytes());
		  artifact.setArtifactValueType(ArtifactValueType.URL);
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  
		  artifactInfo = new ArtifactInfo();
		  artifact = new Artifact();
		  artifact.setArtifactName("Description Artifact new");
		  artifact.setArtifactCategory("ext_doc");
		  artifactInfo.setArtifact(artifact);  
		  artifactInfo.setArtifactDetail("The description".getBytes());
		  artifact.setArtifactValueType(ArtifactValueType.DESCRIPTION);
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  		 			
		UpdateServiceRequest updateServiceRequest = new UpdateServiceRequest();
		updateServiceRequest.setServiceInfo(serviceInfo);
		updateServiceRequest.setPartialUpdate(false);
		updateServiceRequest.setReplaceCurrent(true);
				
		if(variant.equalsIgnoreCase("withBasicServiceInfoOnly")) {
			updateServiceRequest.getServiceInfo().setAssetLifeCycleInfo(null);
			updateServiceRequest.getServiceInfo().setExtendedServiceInfo(null);
			updateServiceRequest.getServiceInfo().setFlattenedRelationshipForUpdate(null);
		}
		
		try	{
			UpdateServiceResponse updateServiceResponse = updateServiceConsumer.getProxy().updateService(updateServiceRequest);
			if(updateServiceResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateUpdateServiceResponse(updateServiceResponse, "positiveCase").equalsIgnoreCase("success")) {							
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
	
	public static String testUpdateService() {
		UpdateServiceConsumer updateServiceConsumer = new UpdateServiceConsumer();
		
		AssetKey assetkey = new AssetKey();
		Library lib = new Library();
		lib.setLibraryId("352:9");
		lib.setLibraryName("Services");
		assetkey.setAssetId("1.0_1228472500860599041937");
		assetkey.setLibrary(lib);
		assetkey.setAssetName("AnavAsset1");
		ServiceInfoForUpdate serviceInfo = new ServiceInfoForUpdate();
		BasicServiceInfo basicServiceInfo = new BasicServiceInfo();
		basicServiceInfo.setAssetKey(assetkey);
		basicServiceInfo.setServiceDescription("new desc 123");
		basicServiceInfo.setServiceNamespace("www.123.com");
		AssetLifeCycleInfo assetLifeCycleInfo = new AssetLifeCycleInfo(); 
		assetLifeCycleInfo.setProductManager("pm123");
		assetLifeCycleInfo.setDomainOwner("do123");
		ServiceDesignTimeInfo serviceDesignTimeInfo = new ServiceDesignTimeInfo();
		serviceDesignTimeInfo.setInterfaceClass("interface123");
		ExtendedServiceInfo extendedServiceInfo = new ExtendedServiceInfo();
		extendedServiceInfo.setServiceDesignTimeInfo(serviceDesignTimeInfo);
		serviceInfo.setExtendedServiceInfo(extendedServiceInfo);
		serviceInfo.setBasicServiceInfo(basicServiceInfo);
		serviceInfo.setAssetLifeCycleInfo(assetLifeCycleInfo);				
		
		FlattenedRelationshipForUpdate flattenedRelationshipForUpdate = new FlattenedRelationshipForUpdate();
		
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
										
		RelationForUpdate relationForUpdate1 = new RelationForUpdate();
		AssetKey currentSourceAssetKey = new AssetKey();
		currentSourceAssetKey.setAssetId("1.0_1228472109329-65177213");
		currentSourceAssetKey.setLibrary(library);
		relationForUpdate1.setCurrentSourceAsset(assetkey);
		AssetKey currentTargetAssetKey = new AssetKey();
		currentTargetAssetKey.setAssetId("1.0_1228961543884-1208439377");
		currentTargetAssetKey.setLibrary(library);
		relationForUpdate1.setCurrentTargetAsset(currentTargetAssetKey);
		Relation relation1  = new Relation();
		relation1.setAssetRelationship("dependent_service");
		AssetKey targetAssetKey1 = new AssetKey();
		targetAssetKey1.setAssetId("1.0_1228903563540808459586");
		targetAssetKey1.setLibrary(library);
		relation1.setTargetAsset(targetAssetKey1);
		relationForUpdate1.setNewRelation(relation1);
		flattenedRelationshipForUpdate.getRelatedAsset().add(relationForUpdate1);
		
		//updateAssetDependenciesRequest.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);
		serviceInfo.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);
		
		
		 ArtifactInfo artifactInfo = new ArtifactInfo();
		  
		  Artifact artifact = new Artifact();
		  artifact.setArtifactName("FileArtifact1.txt");
		  artifact.setArtifactValueType(ArtifactValueType.FILE);
		  artifact.setArtifactCategory("ext_doc");
		  
		  artifactInfo.setArtifact(artifact);
			File file = null;
			OutputStream outputStream = null;
			try {
				file = File.createTempFile("test", ".txt");
				 outputStream = new FileOutputStream(file);
				outputStream.write(20);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				if(outputStream != null)
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  byte[] fileByte = new byte[(int)file.length()];
		  try {
		   fileInputStream.read(fileByte);
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  artifactInfo.setArtifactDetail(fileByte);
		  
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  
		  artifactInfo = new ArtifactInfo();
		  artifact = new Artifact();
		  artifact.setArtifactIdentifier("LL:artifact:wsdl:1");
		  artifact.setArtifactName("TESTINPUT2.txt");
		  artifact.setArtifactCategory("wsdl");
		  artifactInfo.setArtifact(artifact);  
		  artifact.setArtifactValueType(ArtifactValueType.FILE);
		  file = null;
		  file = new File("C:\\testing.txt");
		  try {
		   fileInputStream = new FileInputStream(file);
		  } catch (FileNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  fileByte = new byte[(int)file.length()];
		  try {
		   fileInputStream.read(fileByte);
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  artifactInfo.setArtifactDetail(fileByte);
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  
		  
		  artifactInfo = new ArtifactInfo();
		  artifact = new Artifact();
		  
		  artifact.setArtifactName("URL Artifact");
		  artifact.setArtifactCategory("ext_doc");
		  artifactInfo.setArtifact(artifact);  
		  artifactInfo.setArtifactDetail("http://www.google.com".getBytes());
		  artifact.setArtifactValueType(ArtifactValueType.URL);
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  
		  artifactInfo = new ArtifactInfo();
		  artifact = new Artifact();
		  artifact.setArtifactName("Description Artifact new");
		  artifact.setArtifactCategory("ext_doc");
		  artifactInfo.setArtifact(artifact);  
		  artifactInfo.setArtifactDetail("The description".getBytes());
		  artifact.setArtifactValueType(ArtifactValueType.DESCRIPTION);
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  		 			
		UpdateServiceRequest updateServiceRequest = new UpdateServiceRequest();
		updateServiceRequest.setServiceInfo(serviceInfo);
		updateServiceRequest.setPartialUpdate(false);
		updateServiceRequest.setReplaceCurrent(true);
				
		
		try	{
			UpdateServiceResponse updateServiceResponse = updateServiceConsumer.getProxy().updateService(updateServiceRequest);
			if(updateServiceResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(updateServiceResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
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
	
	public static String testUpdateService_invalidInput(String variant) {
		UpdateServiceConsumer updateServiceConsumer = new UpdateServiceConsumer();
		
		AssetKey assetkey = new AssetKey();
		Library lib = new Library();
		lib.setLibraryId("352:9");
		lib.setLibraryName("Services");
		assetkey.setAssetId("1.0_1228472500860599041937");
		assetkey.setLibrary(lib);
		assetkey.setAssetName("AnavAsset1");
		ServiceInfoForUpdate serviceInfo = new ServiceInfoForUpdate();
		BasicServiceInfo basicServiceInfo = new BasicServiceInfo();
		basicServiceInfo.setAssetKey(assetkey);
		basicServiceInfo.setServiceDescription("new desc 123");
		basicServiceInfo.setServiceNamespace("www.123.com");
		AssetLifeCycleInfo assetLifeCycleInfo = new AssetLifeCycleInfo(); 
		assetLifeCycleInfo.setProductManager("pm123");
		assetLifeCycleInfo.setDomainOwner("do123");
		ServiceDesignTimeInfo serviceDesignTimeInfo = new ServiceDesignTimeInfo();
		serviceDesignTimeInfo.setInterfaceClass("interface123");
		ExtendedServiceInfo extendedServiceInfo = new ExtendedServiceInfo();
		extendedServiceInfo.setServiceDesignTimeInfo(serviceDesignTimeInfo);
		serviceInfo.setExtendedServiceInfo(extendedServiceInfo);
		serviceInfo.setBasicServiceInfo(basicServiceInfo);
		serviceInfo.setAssetLifeCycleInfo(assetLifeCycleInfo);				
		
		FlattenedRelationshipForUpdate flattenedRelationshipForUpdate = new FlattenedRelationshipForUpdate();
		
		Library library = new Library();
		library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);		
		
		RelationForUpdate relationForUpdate1 = new RelationForUpdate();
		AssetKey currentSourceAssetKey = new AssetKey();
		currentSourceAssetKey.setAssetId("1.0_1228472109329-65177213");
		currentSourceAssetKey.setLibrary(library);
		relationForUpdate1.setCurrentSourceAsset(assetkey);
		AssetKey currentTargetAssetKey = new AssetKey();
		currentTargetAssetKey.setAssetId("1.0_1228961543884-1208439377");
		currentTargetAssetKey.setLibrary(library);
		relationForUpdate1.setCurrentTargetAsset(currentTargetAssetKey);
		Relation relation1  = new Relation();
		relation1.setAssetRelationship("dependent_service");
		AssetKey targetAssetKey1 = new AssetKey();
		targetAssetKey1.setAssetId("1.0_1228903563540808459586");
		targetAssetKey1.setLibrary(library);
		relation1.setTargetAsset(targetAssetKey1);
		relationForUpdate1.setNewRelation(relation1);
		flattenedRelationshipForUpdate.getRelatedAsset().add(relationForUpdate1);
		
		//updateAssetDependenciesRequest.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);
		serviceInfo.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);
			
		 ArtifactInfo artifactInfo = new ArtifactInfo();
		  
		  Artifact artifact = new Artifact();
		  artifact.setArtifactName("FileArtifact1.txt");
		  artifact.setArtifactValueType(ArtifactValueType.FILE);
		  artifact.setArtifactCategory("ext_doc");
		  
		  artifactInfo.setArtifact(artifact);
			File file = null;
			OutputStream outputStream = null;
			try {
				file = File.createTempFile("test", ".txt");
				 outputStream = new FileOutputStream(file);
				outputStream.write(20);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				if(outputStream != null)
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  byte[] fileByte = new byte[(int)file.length()];
		  try {
		   fileInputStream.read(fileByte);
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  artifactInfo.setArtifactDetail(fileByte);
		  
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  
		  artifactInfo = new ArtifactInfo();
		  artifact = new Artifact();
		  artifact.setArtifactIdentifier("LL:artifact:wsdl:1");
		  artifact.setArtifactName("TESTINPUT2.txt");
		  artifact.setArtifactCategory("wsdl");
		  artifactInfo.setArtifact(artifact);  
		  artifact.setArtifactValueType(ArtifactValueType.FILE);
		  file = null;
		  file = new File("C:\\testing.txt");
		  try {
		   fileInputStream = new FileInputStream(file);
		  } catch (FileNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  fileByte = new byte[(int)file.length()];
		  try {
		   fileInputStream.read(fileByte);
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  artifactInfo.setArtifactDetail(fileByte);
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  
		  artifactInfo = new ArtifactInfo();
		  artifact = new Artifact();
		  
		  artifact.setArtifactName("URL Artifact");
		  artifact.setArtifactCategory("ext_doc");
		  artifactInfo.setArtifact(artifact);  
		  artifactInfo.setArtifactDetail("http://www.google.com".getBytes());
		  artifact.setArtifactValueType(ArtifactValueType.URL);
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  
		  artifactInfo = new ArtifactInfo();
		  artifact = new Artifact();
		  artifact.setArtifactName("Description Artifact new");
		  artifact.setArtifactCategory("ext_doc");
		  artifactInfo.setArtifact(artifact);  
		  artifactInfo.setArtifactDetail("The description".getBytes());
		  artifact.setArtifactValueType(ArtifactValueType.DESCRIPTION);
		  serviceInfo.getArtifactInfo().add(artifactInfo);
		  		 			
		UpdateServiceRequest updateServiceRequest = new UpdateServiceRequest();
		updateServiceRequest.setServiceInfo(serviceInfo);
		updateServiceRequest.setPartialUpdate(false);
		updateServiceRequest.setReplaceCurrent(true);
				
		if(variant.equalsIgnoreCase("noServiceInfo")) {
			updateServiceRequest.setServiceInfo(null);
		}
		if(variant.equalsIgnoreCase("noBasicServiceInfo")) {
			updateServiceRequest.getServiceInfo().setBasicServiceInfo(null);
		}
		
		try	{
			UpdateServiceResponse updateServiceResponse = updateServiceConsumer.getProxy().updateService(updateServiceRequest);
			if(updateServiceResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateUpdateServiceResponse(updateServiceResponse, "negativeCase").equalsIgnoreCase("success")) {
				List<CommonErrorData> errorDatas = updateServiceResponse.getErrorMessage().getError();
				
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
	
	private static String validateUpdateServiceResponse(UpdateServiceResponse updateServiceResponse, String criteria) {
    	if(criteria.equalsIgnoreCase("positiveCase")) {
    		if(updateServiceResponse.getAck().value().equalsIgnoreCase("success")) {
				ServiceInfo serviceInfo = updateServiceResponse.getServiceInfo();
				if(serviceInfo == null) {
					return "failure";
				}	
				//TODO: add validation api specific to update methods, which will validate the user provided fields alone
				//TODO: confirm it with Arun
				return "success";
    		}
    		if(updateServiceResponse.getAck().value().equalsIgnoreCase("partialFailure")) {
    			if(updateServiceResponse.getErrorMessage().getError().size() > 0) {  				
    				List<CommonErrorData> errorDatas = updateServiceResponse.getErrorMessage().getError();    				
    				System.out.println("The following list of errors occured");
    				for (CommonErrorData errorData : errorDatas) {
    					System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());					
    				}
    				
    				ServiceInfo serviceInfo = updateServiceResponse.getServiceInfo();
    				if(serviceInfo == null) {
    					return "failure";
    				}	
    				//TODO: add validation api specific to update methods, which will validate the user provided fields alone
    				//TODO: confirm it with Arun
    				return "success";
    			}
    		}
    		return "failure";
    	}
    	if(criteria.equalsIgnoreCase("negativeCase")) {
    		if(updateServiceResponse.getAck().value().equalsIgnoreCase("failure")) {
    			if(updateServiceResponse.getErrorMessage().getError().size() > 0) {
    				return "success";
    			}
    		}   		
    	}
    	
    	return "failure";
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
