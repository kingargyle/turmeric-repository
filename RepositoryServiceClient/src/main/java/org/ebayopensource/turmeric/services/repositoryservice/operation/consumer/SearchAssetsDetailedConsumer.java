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

import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetQuery;
import org.ebayopensource.turmeric.repository.v1.services.Attribute;
import org.ebayopensource.turmeric.repository.v1.services.AttributeCriteria;
import org.ebayopensource.turmeric.repository.v1.services.Conjunction;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.SearchAssetsDetailedRequest;
import org.ebayopensource.turmeric.repository.v1.services.SearchAssetsDetailedResponse;
import org.ebayopensource.turmeric.repository.v1.services.SearchLevel;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class SearchAssetsDetailedConsumer extends BaseRepositoryServiceConsumer {
	public static final String  LIBRARY_NAME = "GovernedAssets";
	
	private static SearchAssetsDetailedConsumer searchAssetsDetailedConsumer = new SearchAssetsDetailedConsumer();
	private static String securityCookie = null;
	private static Service service = null;
	public static String testSearchAssetsDetailed_withoutSearchLevel() {
		SearchAssetsDetailedRequest searchAssetsRequest = getSearchAssetsDetailedRequest();
		searchAssetsRequest.getSearchLevel().add(SearchLevel.BASIC);
		searchAssetsRequest.getSearchLevel().add(SearchLevel.ALL);
		searchAssetsRequest.getSearchLevel().add(SearchLevel.LIFECYCLE);
		
		try	{
			SearchAssetsDetailedResponse searchAssetsResponse = searchAssetsDetailedConsumer.getProxy().searchAssetsDetailed(searchAssetsRequest);
			System.out.println("SIZE: " + searchAssetsResponse.getAssetInfo().size());
			if(searchAssetsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateSearchAssetsDetailedResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
		}	
	}
	
	public static String testSearchAssetsDetailed_withSearchLevel_basic() {
		SearchAssetsDetailedRequest searchAssetsRequest = getSearchAssetsDetailedRequest();
		searchAssetsRequest.getSearchLevel().add(SearchLevel.BASIC);
		
		try	{
			SearchAssetsDetailedResponse searchAssetsResponse = searchAssetsDetailedConsumer.getProxy().searchAssetsDetailed(searchAssetsRequest);
			System.out.println("SIZE: " + searchAssetsResponse.getAssetInfo().size());
			if(searchAssetsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateSearchAssetsDetailedResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
		}	
	}
	
	public static String testSearchAssetsDetailed_withSearchLevel_extended() {
		SearchAssetsDetailedRequest searchAssetsRequest = getSearchAssetsDetailedRequest();
		searchAssetsRequest.getSearchLevel().add(SearchLevel.EXTENDED);
		
		try	{
			SearchAssetsDetailedResponse searchAssetsResponse = searchAssetsDetailedConsumer.getProxy().searchAssetsDetailed(searchAssetsRequest);
			System.out.println("SIZE: " + searchAssetsResponse.getAssetInfo().size());
			if(searchAssetsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateSearchAssetsDetailedResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
		}	
	}
	
	public static String testSearchAssetsDetailed_withSearchLevel_artifacts() {
		SearchAssetsDetailedRequest searchAssetsRequest = getSearchAssetsDetailedRequest();
		searchAssetsRequest.getSearchLevel().add(SearchLevel.ARTIFACTS);
		
		try	{
			SearchAssetsDetailedResponse searchAssetsResponse = searchAssetsDetailedConsumer.getProxy().searchAssetsDetailed(searchAssetsRequest);
			System.out.println("SIZE: " + searchAssetsResponse.getAssetInfo().size());
			if(searchAssetsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateSearchAssetsDetailedResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
		}	
	}
	
	public static String testSearchAssetsDetailed_withSearchLevel_lifecycle() {
		SearchAssetsDetailedRequest searchAssetsRequest = getSearchAssetsDetailedRequest();
		searchAssetsRequest.getSearchLevel().add(SearchLevel.LIFECYCLE);
		
		try	{
			SearchAssetsDetailedResponse searchAssetsResponse = searchAssetsDetailedConsumer.getProxy().searchAssetsDetailed(searchAssetsRequest);
			System.out.println("SIZE: " + searchAssetsResponse.getAssetInfo().size());
			if(searchAssetsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateSearchAssetsDetailedResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
		}	
	}
	
	public static String testSearchAssetsDetailed_withSearchLevel_relationships() {
		SearchAssetsDetailedRequest searchAssetsRequest = getSearchAssetsDetailedRequest();
		searchAssetsRequest.getSearchLevel().add(SearchLevel.RELATIONSHIPS);
		
		try	{
			SearchAssetsDetailedResponse searchAssetsResponse = searchAssetsDetailedConsumer.getProxy().searchAssetsDetailed(searchAssetsRequest);
			System.out.println("SIZE: " + searchAssetsResponse.getAssetInfo().size());
			if(searchAssetsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateSearchAssetsDetailedResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
		}	
	}
	
	public static String testSearchAssetsDetailed_withSearchLevel_all() {
		SearchAssetsDetailedRequest searchAssetsRequest = getSearchAssetsDetailedRequest();
		searchAssetsRequest.getSearchLevel().add(SearchLevel.ALL);
		
		try	{
			SearchAssetsDetailedResponse searchAssetsResponse = searchAssetsDetailedConsumer.getProxy().searchAssetsDetailed(searchAssetsRequest);
			System.out.println("SIZE: " + searchAssetsResponse.getAssetInfo().size());
			if(searchAssetsResponse == null) {
				throw new ServiceException(null, "Response object can not be null", null);
			}	
			if(validateSearchAssetsDetailedResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {							
				return RepositoryServiceClientConstants.PASS;
			}
			else {
				return RepositoryServiceClientConstants.FAIL;
			}
		}
		catch(ServiceException se) {
			se.printStackTrace();	
			return RepositoryServiceClientConstants.FAIL;
		}
		catch(Exception e) {
			e.printStackTrace();
			return RepositoryServiceClientConstants.FAIL;
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
        } else {
          //  logger.debug("Using password header, did not find X-TURMERIC-SECURITY-COOKIE");
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-USERID", RepositoryServiceClientConstants.USER_ID);
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-PASSWORD", RepositoryServiceClientConstants.USER_PASSWORD);
        }
        return (AsyncTurmericRSV1) service.getProxy();
    }

    private static String validateSearchAssetsDetailedResponse(SearchAssetsDetailedResponse searchAssetsDetailedResponse, String criteria) {
    	if(criteria.equalsIgnoreCase("positiveCase")) {
    		if(searchAssetsDetailedResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
				List<AssetInfo> assetInfos = searchAssetsDetailedResponse.getAssetInfo();
				if(assetInfos != null) {
					for (AssetInfo assetInfo : assetInfos) {
						return RepositoryServiceConsumerUtil.validateAssetInfo(assetInfo);
					}
				}
				return RepositoryServiceClientConstants.SUCCESS;
    		}
    		return RepositoryServiceClientConstants.FAILURE;
    	}
    	if(criteria.equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_CASE))
    	{
    		if(searchAssetsDetailedResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
				List<AssetInfo> assetInfos = searchAssetsDetailedResponse.getAssetInfo();
				if(assetInfos != null) {
					for (AssetInfo assetInfo : assetInfos) {
						return RepositoryServiceConsumerUtil.validateAssetInfo(assetInfo);
					}
				}
				return RepositoryServiceClientConstants.SUCCESS;
    		}
    		return RepositoryServiceClientConstants.FAILURE;
    	}
    	if(criteria.equalsIgnoreCase("negativeCase")) {
    		if(searchAssetsDetailedResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
    			if(searchAssetsDetailedResponse.getErrorMessage().getError().size() > 0) {
    				return RepositoryServiceClientConstants.SUCCESS;
    			}
    		}   		
    	}
    	
    	return RepositoryServiceClientConstants.FAILURE;
    }
    
    private static SearchAssetsDetailedRequest getSearchAssetsDetailedRequest(){
    	Library library = new Library();
		library.setLibraryName(LIBRARY_NAME);
		
		AssetQuery assetQuery = new AssetQuery();
		AttributeCriteria attributeCriteria = new AttributeCriteria();		

		Attribute attribute = new Attribute();
		attribute.setAttributeName("lifecycle_state");
		attribute.setAttributeValue("Proposed");
		attributeCriteria.getAttribute().add(attribute);
		
		/*Attribute attribute1 = new Attribute();
		attribute1.setAttributeName("asset-name");
		attribute1.setAttributeValue("TestInternalService1");
		attributeCriteria.getAttribute().add(attribute1);*/
		
		attributeCriteria.setConjunction(Conjunction.AND);
		assetQuery.setAttributeCriteria(attributeCriteria);
		assetQuery.setConjunction(Conjunction.valueOf("AND"));
		SearchAssetsDetailedRequest searchAssetsRequest = new SearchAssetsDetailedRequest();
		searchAssetsRequest.setLibrary(library);
		searchAssetsRequest.setAssetQuery(assetQuery);
		
		return searchAssetsRequest;
    }
}
