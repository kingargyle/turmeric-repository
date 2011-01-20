/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;
import java.util.List;

import org.ebayopensource.turmeric.repository.v1.services.Artifact;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v1.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.CreateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProviderFactory;
import org.junit.Test;



public class RepositoryServiceImplLoadTest {

    @Test
    public void testServiceProvider() {
        // TODO Auto-generated method stub
        try {
            
            System.setProperty("org.ebayopensource.turmeric.repository.wso2.url",
                            "https://127.0.0.1:9443/registry");
            System.setProperty("org.ebayopensource.turmeric.repository.wso2.username", "admin");
            System.setProperty("org.ebayopensource.turmeric.repository.wso2.password", "admin");
            System.setProperty("jaxb.debug", "true");
            
            RepositoryServiceProvider serviceProvider = RepositoryServiceProviderFactory.getInstance();
//            createCompleteAsset(serviceProvider);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private static CreateCompleteAssetResponse createCompleteAsset(RepositoryServiceProvider serviceProvider)
    throws Exception
{
    AssetKey key = new AssetKey();
    Library lib = new Library();
    lib.setLibraryName("my-library");
    key.setLibrary(lib);
    key.setAssetName("my-asset");
    
    BasicAssetInfo basicInfo = new BasicAssetInfo();
    basicInfo.setAssetKey(key);
    basicInfo.setAssetName("my-asset");
    basicInfo.setAssetDescription("my-asset description");
    basicInfo.setAssetType("Service");
    
    ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
    List<AttributeNameValue> attrs = extendedInfo.getAttribute();
    attrs.add(RSProviderUtil.newAttribute("namespace","my-library"));
     
    AssetLifeCycleInfo lifeCycleInfo = new AssetLifeCycleInfo();
    lifeCycleInfo.setDomainOwner("Manuel Chinea");
    lifeCycleInfo.setDomainType("Technical Owner");
    
    AssetInfo assetInfo = new AssetInfo();
    assetInfo.setBasicAssetInfo(basicInfo);
    assetInfo.setExtendedAssetInfo(extendedInfo);
    assetInfo.setAssetLifeCycleInfo(lifeCycleInfo);
    
    Artifact endpoint = new Artifact();
    endpoint.setArtifactName("ep-");
    endpoint.setArtifactCategory("Endpoint");
    endpoint.setArtifactValueType(ArtifactValueType.URL);
    
    String endpointUrl = "http://localhost:8080/" + "my-asset";
    ArtifactInfo endpointInfo = new ArtifactInfo();
    endpointInfo.setArtifact(endpoint);
    endpointInfo.setArtifactDetail(endpointUrl.getBytes("UTF-8"));
    endpointInfo.setContentType("application/vnd.wso2.endpoint");
    
    List<ArtifactInfo> artifactList = assetInfo.getArtifactInfo();
    artifactList.add(endpointInfo);
    
    CreateCompleteAssetRequest request = new CreateCompleteAssetRequest();
    request.setAssetInfo(assetInfo);
    
    RepositoryServiceProvider provider = serviceProvider;
    return provider.createCompleteAsset(request);
}

}
