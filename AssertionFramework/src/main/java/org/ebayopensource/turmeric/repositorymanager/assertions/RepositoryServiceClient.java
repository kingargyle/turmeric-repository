/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.turmeric.common.v1.types.ErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.intf.TurmericRSV2;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionIllegalArgumentException;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionRuntimeException;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceInvocationRuntimeException;
import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * RepositoryServiceClient is a client for the RepositoryService
 * web service.  The Assertions framework uses RepositoryService
 * to obtain Asset artifacts from the repository.
 * 
 * @author pcopeland
 */
public class RepositoryServiceClient
{
    static private final Logger logger = LoggerFactory.getLogger(RepositoryServiceClient.class);

    private String userId;
    private String password;
    private String securityCookie = null;
    //private Service service;
    private URL serviceLocationURL;
    //private RepositoryService serviceProxy = null;
    private Map<String, Library> libraryCache = new HashMap<String, Library>();

    public RepositoryServiceClient(
            String userId,
            String password,
            String serviceLocation)
        throws ServiceException
    {
        this.userId = userId;
        this.password = password;
        try {
            URL serviceLocationURL = new URL(serviceLocation);
            this.serviceLocationURL = serviceLocationURL;
            //service = ServiceFactory.create("RepositoryService", "RepositoryService", serviceLocationURL);
            //serviceProxy = service.getProxy();
        } catch (MalformedURLException mux) {
            throw new IllegalArgumentException(mux);
        }
    }

    /**
     * Returns the service proxy object.
     * 
     * @return the service proxy object.
     * @throws ServiceException 
     */
    private TurmericRSV2 getServiceProxy() throws ServiceException
    {
    	Service service = ServiceFactory.create("TurmericRSV2", "TurmericRSV2", serviceLocationURL);
    	
        // get security cookie after first successful login
        if (securityCookie == null) {
            securityCookie = service.getResponseContext().getTransportHeader(
                "X-TURMERIC-SECURITY-COOKIE");
        }

        // Use security cookie if present or use userid/password
        RequestContext requestContext = service.getRequestContext();
        if (securityCookie != null) {
            logger.debug("Found X-TURMERIC-SECURITY-COOKIE="+securityCookie);
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-COOKIE", securityCookie);
        } else {
            logger.debug("Using password header, did not find X-TURMERIC-SECURITY-COOKIE");
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-USERID", userId);
            requestContext.setTransportHeader(
                    "X-TURMERIC-SECURITY-PASSWORD", password);
        }
        return (TurmericRSV2) service.getProxy();
    }

    /**
     * Returns a repository Asset.
     * 
     * @param assetKey AssetKey
     * @return a repository Asset.
     */
    public AssetInfo getAssetInfo(AssetKey assetKey) throws AssertionIllegalArgumentException
    {
        GetAssetInfoRequest assetRequest = new GetAssetInfoRequest();
        assetRequest.setAssetKey(assetKey);
        return getAssetInfo(assetRequest);
    }
 
    /**
     * Returns a repository Asset.
     * 
     * @param libraryName the library that contains the Asset
     * @param assetName the name of the Asset.
     * @param assetVersion the Asset version.
     * @return a repository Asset.
     */
    public AssetInfo getAssetInfo(
            String libraryName,
            String assetName,
            String assetVersion,
            String assetType) throws AssertionIllegalArgumentException
    {
        GetAssetInfoRequest assetRequest = new GetAssetInfoRequest();
        AssetKey assetKey = new AssetKey();

        Library library = getNamedLibrary(libraryName);
        if (library == null)
            throw new IllegalArgumentException(
                    "Could not find Library named '"+libraryName+"'");
        assetKey.setLibrary(library);
        assetKey.setAssetName(assetName);
        assetRequest.setAssetKey(assetKey);
        assetRequest.setVersion(assetVersion);
        assetRequest.setAssetType(assetType);
        //TODO: find what is this line doing here 
        //assetKey.setAssetId("1.0_1232749942227-390605607");

        return getAssetInfo(assetRequest);
    }
 
    private AssetInfo getAssetInfo(GetAssetInfoRequest assetRequest) throws AssertionIllegalArgumentException
    {
        String assetName = assetRequest.getAssetKey().getAssetName();
        String assetVersion = assetRequest.getVersion();
        String libraryName = assetRequest.getAssetKey().getLibrary().getLibraryName();
        try {
            GetAssetInfoResponse rsp = getServiceProxy().getAssetInfo(assetRequest);
            if(rsp.getAck() != null && rsp.getAck().value()!= null && rsp.getAck().value().equalsIgnoreCase("success")) 
            {
            	if(rsp.getAssetInfo() == null)
            	{
            		throw new AssertionIllegalArgumentException(rsp.getErrorMessage());
            	}
                return rsp.getAssetInfo();
            } else {
                assetRequest.getAssetKey().getLibrary().getLibraryName();
                StringBuffer errbuf =
                    new StringBuffer("RepositoryService.getAssetinfo() failed");
                errbuf.append(" asset=").append(assetName)
                .append(",version=").append(assetVersion)
                .append(",library=").append(libraryName)
                .append(",errors=");
                throw new AssertionRuntimeException(rsp.getErrorMessage());
                /*throw new RuntimeException(formatError(
                        errbuf, rsp.getErrorMessage()));*/
            }
        } catch (ServiceInvocationRuntimeException sifex) {
            throw new RuntimeException(
                    "RepositoryService.getAssetinfo() failed for"
                    +" asset="+assetName
                    +",version="+assetVersion
                    +",library="+libraryName
                    , sifex);
        } catch (ServiceException serviceException) 
        {
        	throw new RuntimeException(
                    "RepositoryService.getAssetinfo() failed for"
                    +" asset="+assetName
                    +",version="+assetVersion
                    +",library="+libraryName
                    , serviceException);
		}
    }

    /**
     * Returns a repository Library that has a given name.
     * 
     * @param searchName the name of the Library to search for.
     * @return a repository Library that has a given name.
     */
    public Library getNamedLibrary(String searchName)
    {
        Library namedLibrary = libraryCache.get(searchName);
        if (namedLibrary == null) {
            List<Library> libraryList = getLibraryList();
            if (libraryList != null) {
                for (Library library : libraryList) {
                    String libraryName = library.getLibraryName();
                    libraryCache.put(libraryName, library);
                    if (libraryName.equals(searchName))
                        namedLibrary = library;
                }
            }
        }
        return namedLibrary;
    }

    /**
     * Returns a list of the Libraries in the repository.
     * 
     * @return a list of the Libraries in the repository.
     */
    public List<Library> getLibraryList()
    {
        List<Library> libraryList = null;
        GetLibraryListResponse rsp = null;
		try 
		{
			rsp = getServiceProxy().getLibraryList(new GetLibraryListRequest());
		} 
		catch (ServiceException serviceException) 
		{
			 throw new RuntimeException(
	                    "RepositoryService.getLibraryList() failed"
	                    , serviceException);
		}
        if (rsp.getAck().value().equalsIgnoreCase("success")) 
        {
            libraryList = rsp.getLibrary();
        } 
        else 
        {
            throw new RuntimeException(formatError(
                    new StringBuffer("RepositoryService.getLibraryList() failed"),
                    rsp.getErrorMessage()));
        }
        return libraryList;
    }

    private String formatError(StringBuffer errbuf, ErrorMessage errorMessage)
    {
        for (ErrorData error: errorMessage.getError()) {
            errbuf.append("[Id=").append(error.getErrorId())
            .append(",category=").append(error.getCategory().value())
            .append(',').append(error.getMessage()).append(']');
        }
        return errbuf.toString();
    }
}
