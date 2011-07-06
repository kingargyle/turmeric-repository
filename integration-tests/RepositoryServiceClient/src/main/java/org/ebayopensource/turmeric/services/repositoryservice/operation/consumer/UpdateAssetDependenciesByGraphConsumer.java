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
import org.ebayopensource.turmeric.repository.v1.services.GraphRelationship;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.TypedRelationNode;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesByGraphRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesByGraphResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class UpdateAssetDependenciesByGraphConsumer extends
		BaseRepositoryServiceConsumer {
	private AsyncTurmericRSV1 m_proxy = null;
	public static String testUpdateAssetDependenciesByGraph_validInput(AssetInfo sourceAssetInfo,AssetInfo targetAsset1Level1Info,AssetInfo targetAsset2Level1Info,AssetInfo targetAsset1Level2Info) {
		UpdateAssetDependenciesByGraphConsumer updateAssetDependenciesByGraphConsumer = new UpdateAssetDependenciesByGraphConsumer();
		UpdateAssetDependenciesByGraphRequest updateAssetDependenciesByGraphRequest = new UpdateAssetDependenciesByGraphRequest();
		
		/*Library library = new Library();
		library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);*/
		
		GraphRelationship graphRelationshipRoot = new GraphRelationship();
		
		AssetKey sourceAssetKey = new AssetKey();
		sourceAssetKey.setAssetId(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		sourceAssetKey.setAssetName(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library = new Library();
		library.setLibraryId(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		sourceAssetKey.setLibrary(library);
		graphRelationshipRoot.setSourceAsset(sourceAssetKey);
		
		TypedRelationNode typedRelationNode1Level1  = new TypedRelationNode(); 
		typedRelationNode1Level1.setAssetRelationship("dependsOn");
		
		AssetKey  targetAsset1Level1 = new AssetKey();
		/*sourceAssetKey.setAssetId(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getAssetId());
		targetAsset1Level1.setLibrary(library);
		*/
		targetAsset1Level1.setAssetId(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getAssetId());
		targetAsset1Level1.setAssetName(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library1 = new Library();
		library1.setLibraryId(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library1.setLibraryName(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		sourceAssetKey.setLibrary(library1);
		
		GraphRelationship graphRelationship1Level1 = new GraphRelationship();
		graphRelationship1Level1.setSourceAsset(targetAsset1Level1);
		typedRelationNode1Level1.setTarget(graphRelationship1Level1);
		
		
		//graphRelationshipRoot.getTargetAsset().add(typedRelationNode1Level1);
		
		TypedRelationNode typedRelationNode2Level1  = new TypedRelationNode();
		typedRelationNode2Level1.setAssetRelationship("dependsOn");
		
		
		
		AssetKey  targetAsset2Level1 = new AssetKey();
		targetAsset2Level1.setAssetId(targetAsset2Level1Info.getBasicAssetInfo().getAssetKey().getAssetId());
		targetAsset2Level1.setAssetName(targetAsset2Level1Info.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library2 = new Library();
		library2.setLibraryId(targetAsset2Level1Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library2.setLibraryName(targetAsset2Level1Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		sourceAssetKey.setLibrary(library2);
		
		
		
		
		
		GraphRelationship graphRelationship2Level1 = new GraphRelationship();
		graphRelationship2Level1.setSourceAsset(targetAsset2Level1);
		TypedRelationNode typedRelationNode2Level2 = new TypedRelationNode();
		typedRelationNode2Level2.setAssetRelationship("dependent_service");
		GraphRelationship graphRelationship1Level2 = new GraphRelationship();
				
				
				AssetKey targetAsset1Level2 = new AssetKey();
				targetAsset1Level2.setAssetId(targetAsset1Level2Info.getBasicAssetInfo().getAssetKey().getAssetId());
				targetAsset1Level2.setAssetName(targetAsset1Level2Info.getBasicAssetInfo().getAssetKey().getAssetName());
				Library library3 = new Library();
				library3.setLibraryId(targetAsset1Level2Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
				library3.setLibraryName(targetAsset1Level2Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
				sourceAssetKey.setLibrary(library3);
				
				
				graphRelationship1Level2.setSourceAsset(targetAsset1Level2);
				
			typedRelationNode2Level2.setTarget(graphRelationship1Level2);
			graphRelationship2Level1.getTargetAsset().add(typedRelationNode2Level2);
		typedRelationNode2Level1.setTarget(graphRelationship2Level1);
		graphRelationshipRoot.getTargetAsset().add(typedRelationNode2Level1);
		
		updateAssetDependenciesByGraphRequest.setGraphRelationship(graphRelationshipRoot);
		updateAssetDependenciesByGraphRequest.setAssetKey(sourceAssetKey);
		
		try	{
			UpdateAssetDependenciesByGraphResponse updateAssetDependenciesByGraphResponse = updateAssetDependenciesByGraphConsumer.getProxy().updateAssetDependenciesByGraph(updateAssetDependenciesByGraphRequest);
			if(updateAssetDependenciesByGraphResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(updateAssetDependenciesByGraphResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else if(updateAssetDependenciesByGraphResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {							
				return RepositoryServiceClientConstants.PARTIAL_FAIL;
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
	
	public static String testUpdateAssetDependenciesByGraph_invalidInput(String variant,AssetInfo sourceAssetInfo,AssetInfo targetAsset1Level1Info,AssetInfo targetAsset2Level1Info,AssetInfo targetAsset1Level2Info) {
		UpdateAssetDependenciesByGraphConsumer updateAssetDependenciesByGraphConsumer = new UpdateAssetDependenciesByGraphConsumer();
		UpdateAssetDependenciesByGraphRequest updateAssetDependenciesByGraphRequest = new UpdateAssetDependenciesByGraphRequest();
		
		GraphRelationship graphRelationshipRoot = new GraphRelationship();
		
		AssetKey sourceAssetKey = new AssetKey();
		sourceAssetKey.setAssetId(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		sourceAssetKey.setAssetName(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library = new Library();
		library.setLibraryId(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library.setLibraryName(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		sourceAssetKey.setLibrary(library);
		graphRelationshipRoot.setSourceAsset(sourceAssetKey);
		
		TypedRelationNode typedRelationNode1Level1  = new TypedRelationNode(); 
		typedRelationNode1Level1.setAssetRelationship("consumers");
		
		AssetKey  targetAsset1Level1 = new AssetKey();
		/*sourceAssetKey.setAssetId(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getAssetId());
		targetAsset1Level1.setLibrary(library);
		*/
		targetAsset1Level1.setAssetId(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getAssetId());
		targetAsset1Level1.setAssetName(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library1 = new Library();
		library1.setLibraryId(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library1.setLibraryName(targetAsset1Level1Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		sourceAssetKey.setLibrary(library1);
		
		GraphRelationship graphRelationship1Level1 = new GraphRelationship();
		graphRelationship1Level1.setSourceAsset(targetAsset1Level1);
		typedRelationNode1Level1.setTarget(graphRelationship1Level1);
				
		//graphRelationshipRoot.getTargetAsset().add(typedRelationNode1Level1);
		
		TypedRelationNode typedRelationNode2Level1  = new TypedRelationNode();
		typedRelationNode2Level1.setAssetRelationship("consumers");
		
		AssetKey  targetAsset2Level1 = new AssetKey();
		targetAsset2Level1.setAssetId(targetAsset2Level1Info.getBasicAssetInfo().getAssetKey().getAssetId());
		targetAsset2Level1.setAssetName(targetAsset2Level1Info.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library2 = new Library();
		library2.setLibraryId(targetAsset2Level1Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library2.setLibraryName(targetAsset2Level1Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		sourceAssetKey.setLibrary(library2);
				
		GraphRelationship graphRelationship2Level1 = new GraphRelationship();
		graphRelationship2Level1.setSourceAsset(targetAsset2Level1);
		TypedRelationNode typedRelationNode2Level2 = new TypedRelationNode();
		typedRelationNode2Level2.setAssetRelationship("dependent_service");
		GraphRelationship graphRelationship1Level2 = new GraphRelationship();
				
				
		AssetKey targetAsset1Level2 = new AssetKey();
		targetAsset1Level2.setAssetId(targetAsset1Level2Info.getBasicAssetInfo().getAssetKey().getAssetId());
		targetAsset1Level2.setAssetName(targetAsset1Level2Info.getBasicAssetInfo().getAssetKey().getAssetName());
		Library library3 = new Library();
		library3.setLibraryId(targetAsset1Level2Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
		library3.setLibraryName(targetAsset1Level2Info.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
		sourceAssetKey.setLibrary(library3);
				
				
		graphRelationship1Level2.setSourceAsset(targetAsset1Level2);
			
		typedRelationNode2Level2.setTarget(graphRelationship1Level2);
		graphRelationship2Level1.getTargetAsset().add(typedRelationNode2Level2);
		typedRelationNode2Level1.setTarget(graphRelationship2Level1);
		graphRelationshipRoot.getTargetAsset().add(typedRelationNode2Level1);
		
		updateAssetDependenciesByGraphRequest.setGraphRelationship(graphRelationshipRoot);
		if(variant.equalsIgnoreCase("invalidAssetId"))
		{
			sourceAssetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
		}
		updateAssetDependenciesByGraphRequest.setAssetKey(sourceAssetKey);
		
		
		try	{
			UpdateAssetDependenciesByGraphResponse updateAssetDependenciesByGraphResponse = updateAssetDependenciesByGraphConsumer.getProxy().updateAssetDependenciesByGraph(updateAssetDependenciesByGraphRequest);
			if(updateAssetDependenciesByGraphResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(updateAssetDependenciesByGraphResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {							
				List<CommonErrorData> errorDatas = updateAssetDependenciesByGraphResponse.getErrorMessage().getError();
				
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
