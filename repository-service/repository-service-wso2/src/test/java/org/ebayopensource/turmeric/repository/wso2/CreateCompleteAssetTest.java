/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.wso2.carbon.governance.api.services.ServiceManager;
import org.wso2.carbon.governance.api.services.dataobjects.Service;
import org.wso2.carbon.governance.api.util.GovernanceConstants;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.*;

/**
 * @author mgorovoy
 * 
 */
public class CreateCompleteAssetTest extends Wso2Base {
    // First resource path must be the primary resource created by the test
    // in order for the assumption checks to work correctly.
    private static final String[] resources = {
            "/_system/governance/trunk/services/com/domain/www/assets/CreateCompleteAssetTest",
            "/_system/governance/trunk/endpoints/com/domain/www/ep-CreateCompleteAssetTest", };

    private static final String assetName = "CreateCompleteAssetTest";
    private static final String assetDesc = "CreateCompleteAssetTest description";
    private static final String namespace = "http://www.domain.com/assets";
    private static final String baseUrl = "http://www.domain.com/assets/";
    private static final String stringProperty = "a test value";
    private static final Long longProperty = new Long(1234567l);
    private static final Boolean booleanProperty = Boolean.FALSE;

    private CreateCompleteAssetResponse createCompleteAsset() throws Exception {
        AssetKey key = new AssetKey();
        key.setAssetName(assetName);

        AssetInfo assetInfo = new AssetInfo();
        BasicAssetInfo basicInfo = new BasicAssetInfo();
        basicInfo.setAssetKey(key);
        basicInfo.setAssetName(assetName);
        basicInfo.setAssetDescription(assetDesc);
        basicInfo.setAssetType("Service");
        basicInfo.setVersion("1.0.0");
        basicInfo.setNamespace(namespace);
        
        assetInfo.setBasicAssetInfo(basicInfo);

        ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
        List<AttributeNameValue> attrs = extendedInfo.getAttribute();
        attrs.add(RSProviderUtil.newAttribute("stringProperty", stringProperty));
        attrs.add(RSProviderUtil.newAttribute("longProperty", longProperty.longValue()));
        attrs.add(RSProviderUtil.newAttribute("booleanProperty", booleanProperty.booleanValue()));
        assetInfo.setExtendedAssetInfo(extendedInfo);
        
        AssetLifeCycleInfo lifeCycleInfo = new AssetLifeCycleInfo();
        lifeCycleInfo.setDomainOwner("John Doe");
        lifeCycleInfo.setDomainType("Technical Owner");

        assetInfo.setAssetLifeCycleInfo(lifeCycleInfo);

        Artifact endpoint = new Artifact();
        endpoint.setArtifactName("ep-" + assetName);
        endpoint.setArtifactCategory("endpoint");
        endpoint.setArtifactValueType(ArtifactValueType.URL);

        String endpointUrl = baseUrl + assetName;
        ArtifactInfo endpointInfo = new ArtifactInfo();
        endpointInfo.setArtifact(endpoint);
        endpointInfo.setArtifactDetail(endpointUrl.getBytes("UTF-8"));
        endpointInfo.setContentType(GovernanceConstants.ENDPOINT_MEDIA_TYPE);

        Artifact wsdl = new Artifact();
        wsdl.setArtifactName(assetName + ".wsdl");
        wsdl.setArtifactCategory("wsdl");
        wsdl.setArtifactValueType(ArtifactValueType.FILE);

        ArtifactInfo wsdlInfo = new ArtifactInfo();
        wsdlInfo.setArtifact(wsdl);
        wsdlInfo.setArtifactDetail(loadFile("src/main/resources/META-INF/soa/services/wsdl/CreateServiceTest/CreateServiceTest.wsdl"));
        wsdlInfo.setContentType(GovernanceConstants.WSDL_MEDIA_TYPE);

        List<ArtifactInfo> artifactList = assetInfo.getArtifactInfo();
        artifactList.add(endpointInfo);
        artifactList.add(wsdlInfo);
        
 /*       FlattenedRelationship rel = new FlattenedRelationship();
        List<Relation> relationList = rel.getRelatedAsset();
        relationList.add(
                        new Relation() {
                            {
                               this.setSourceAsset(RSProviderUtil.completeAssetKey(
                                               new AssetKey() {
                                                    {
                                                        this.setAssetId(resources[0]);
                                                    }
                                                }, null, null));
                                this.setTargetAsset(RSProviderUtil.completeAssetKey(
                                                new AssetKey() {
                                                    {
                                                        this.setAssetId(dstAsset);
                                                    }
                                                }, null, null));
                                this.setAssetRelationship("DependsOn");
                            }
                        });
        assetInfo.setFlattenedRelationship(rel);
 */
        
        CreateCompleteAssetRequest request = new CreateCompleteAssetRequest();
        request.setAssetInfo(assetInfo);

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        return provider.createCompleteAsset(request);
    }

    @Test
    public void createTest() throws Exception {
        boolean clean = false;
        Registry wso2 = RSProviderUtil.getRegistry();
        try {
            clean = !wso2.resourceExists(resources[0]);
        }
        catch (RegistryException e) {
        }
        assertTrue(clean);

        CreateCompleteAssetResponse response = createCompleteAsset();

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());
        
        assertNotNull(response.getAssetKey().getAssetId());
        String id = response.getAssetKey().getAssetId();
        ServiceManager serviceManager = new ServiceManager(wso2);
        Service service = serviceManager.getService(id);
        assertNotNull(service);
        assertTrue("No WSDLs were found as attachments", service.getAttachedWsdls().length > 0);
    }

    @Test
    public void createDuplicateTest() throws Exception {
        CreateCompleteAssetResponse response = createCompleteAsset();
        assertEquals(AckValue.FAILURE, response.getAck());
    }
}
