/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.Artifact;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfoForUpdate;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v2.services.LockAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.LockAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateCompleteAssetResponse;

/**
 * @author mgorovoy
 * 
 */
public class UpdateCompleteAssetTest extends Wso2Base {
    // First resource path must be the primary resource created by the test
    // in order for the assumption checks to work correctly.
    private static final String[] resources = {
            "/_system/governance/trunk/services/http/www/domain/com/assets/UpdateCompleteAssetTest",
            "/_system/governance/trunk/endpoints/http/www/domain/com/ep-UpdateCompleteAssetTest",
            "/_system/governance/trunk/endpoints/http/www/domain/com/ep-UpdateCompleteAssetTest-updated",
            "/_system/governance/trunk/endpoints/http/www/domain/com/ep-UpdateCompleteAssetTest-updated-merge" };

    private static final String assetName = "UpdateCompleteAssetTest";
    private static final String assetDesc = "UpdateCompleteAssetTest description";
    private static final String libraryName = "http://www.domain.com/assets";
    private static final String baseUrl = "http://www.domain.com/assets/";
    private static final String stringProperty = "test value";
    private static final Long longProperty = new Long(100000l);
    private static final Boolean booleanProperty = Boolean.FALSE;

    @Before
    public void checkRepository() {
        boolean exists = false;
        try {
            Registry wso2 = RSProviderUtil.getRegistry();
            exists = wso2.resourceExists("/");

            for (String resource : resources) {
                if (wso2.resourceExists(resource)) {
                    wso2.delete(resource);
                }
            }
        }
        catch (Exception ex) {
        }

        assumeTrue(exists);
    }

    private CreateCompleteAssetResponse createAsset() throws Exception {
        AssetKey key = new AssetKey();
        key.setAssetName(assetName);

        BasicAssetInfo basicInfo = new BasicAssetInfo();
        basicInfo.setAssetKey(key);
        basicInfo.setAssetName(assetName);
        basicInfo.setAssetDescription(assetDesc);
        basicInfo.setAssetType("Service");

        ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
        List<AttributeNameValue> attrs = extendedInfo.getAttribute();
        attrs.add(RSProviderUtil.newAttribute("namespace", libraryName));
        attrs.add(RSProviderUtil.newAttribute("longProperty", longProperty.longValue()));
        attrs.add(RSProviderUtil.newAttribute("booleanProperty", booleanProperty.booleanValue()));

        AssetLifeCycleInfo lifeCycleInfo = new AssetLifeCycleInfo();
        lifeCycleInfo.setDomainOwner("John Doe");
        lifeCycleInfo.setDomainType("Technical Owner");

        AssetInfo assetInfo = new AssetInfo();
        assetInfo.setBasicAssetInfo(basicInfo);
        assetInfo.setExtendedAssetInfo(extendedInfo);
        assetInfo.setAssetLifeCycleInfo(lifeCycleInfo);

        Artifact endpoint = new Artifact();
        endpoint.setArtifactName("ep-" + assetName);
        endpoint.setArtifactCategory("Endpoint");
        endpoint.setArtifactValueType(ArtifactValueType.URL);

        String endpointUrl = baseUrl + assetName;
        ArtifactInfo endpointInfo = new ArtifactInfo();
        endpointInfo.setArtifact(endpoint);
        endpointInfo.setArtifactDetail(endpointUrl.getBytes("UTF-8"));
        endpointInfo.setContentType("application/vnd.wso2.endpoint");

        List<ArtifactInfo> artifactList = assetInfo.getArtifactInfo();
        artifactList.add(endpointInfo);

        CreateCompleteAssetRequest request = new CreateCompleteAssetRequest();
        request.setAssetInfo(assetInfo);

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        return provider.createCompleteAsset(request);
    }

    private UpdateCompleteAssetResponse replaceAsset(String assetId) throws Exception {
        AssetKey key = new AssetKey();
        key.setAssetId(assetId);

        LockAssetRequest lockReq = new LockAssetRequest();
        lockReq.setAssetKey(key);

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        LockAssetResponse lockRes = provider.lockAsset(lockReq);
        assertEquals(AckValue.SUCCESS, lockRes.getAck());

        BasicAssetInfo basicInfo = new BasicAssetInfo();
        basicInfo.setAssetKey(key);
        basicInfo.setAssetName(assetName);
        basicInfo.setAssetDescription(assetDesc + " updated");
        basicInfo.setAssetType("Service");

        ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
        List<AttributeNameValue> attrs = extendedInfo.getAttribute();
        attrs.add(RSProviderUtil.newAttribute("namespace", libraryName));
        attrs.add(RSProviderUtil.newAttribute("stringProperty", stringProperty + " updated"));
        attrs.add(RSProviderUtil.newAttribute("longProperty", longProperty.longValue() * 10));
        attrs.add(RSProviderUtil.newAttribute("booleanProperty", !booleanProperty.booleanValue()));

        AssetLifeCycleInfo lifeCycleInfo = new AssetLifeCycleInfo();
        lifeCycleInfo.setDomainOwner("John Doe Junior");
        lifeCycleInfo.setDomainType("Business Owner");

        AssetInfoForUpdate assetInfo = new AssetInfoForUpdate();
        assetInfo.setBasicAssetInfo(basicInfo);
        assetInfo.setExtendedAssetInfo(extendedInfo);
        assetInfo.setAssetLifeCycleInfo(lifeCycleInfo);

        Artifact endpoint = new Artifact();
        endpoint.setArtifactName("ep-" + assetName + "-updated");
        endpoint.setArtifactCategory("Endpoint");
        endpoint.setArtifactValueType(ArtifactValueType.URL);

        String endpointUrl = baseUrl + assetName + "-updated";
        ArtifactInfo endpointInfo = new ArtifactInfo();
        endpointInfo.setArtifact(endpoint);
        endpointInfo.setArtifactDetail(endpointUrl.getBytes("UTF-8"));
        endpointInfo.setContentType("application/vnd.wso2.endpoint");

        List<ArtifactInfo> artifactList = assetInfo.getArtifactInfo();
        artifactList.add(endpointInfo);

        UpdateCompleteAssetRequest request = new UpdateCompleteAssetRequest();
        request.setPartialUpdate(false);
        request.setAssetInfoForUpdate(assetInfo);
        request.setReplaceCurrent(true);

        return provider.updateCompleteAsset(request);
    }

    private UpdateCompleteAssetResponse mergeAsset(String assetId) throws Exception {
        AssetKey key = new AssetKey();
        key.setAssetId(assetId);

        LockAssetRequest lockReq = new LockAssetRequest();
        lockReq.setAssetKey(key);

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        LockAssetResponse lockRes = provider.lockAsset(lockReq);
        assertEquals(AckValue.SUCCESS, lockRes.getAck());

        BasicAssetInfo basicInfo = new BasicAssetInfo();
        basicInfo.setAssetKey(key);
        basicInfo.setAssetName(assetName);
        basicInfo.setAssetDescription(assetDesc + " updated");
        basicInfo.setAssetType("Service");

        ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
        List<AttributeNameValue> attrs = extendedInfo.getAttribute();
        attrs.add(RSProviderUtil.newAttribute("namespace", libraryName));
        attrs.add(RSProviderUtil.newAttribute("stringProperty", stringProperty + " updated"));
        attrs.add(RSProviderUtil.newAttribute("longProperty", longProperty.longValue() * 10));
        attrs.add(RSProviderUtil.newAttribute("booleanProperty", !booleanProperty.booleanValue()));

        AssetLifeCycleInfo lifeCycleInfo = new AssetLifeCycleInfo();
        lifeCycleInfo.setDomainOwner("John Doe Junior");
        lifeCycleInfo.setDomainType("Business Owner");

        AssetInfoForUpdate assetInfo = new AssetInfoForUpdate();
        assetInfo.setBasicAssetInfo(basicInfo);
        assetInfo.setExtendedAssetInfo(extendedInfo);
        assetInfo.setAssetLifeCycleInfo(lifeCycleInfo);

        Artifact endpoint = new Artifact();
        endpoint.setArtifactName("ep-" + assetName + "-updated-merge");
        endpoint.setArtifactCategory("endpoint");
        endpoint.setArtifactValueType(ArtifactValueType.URL);

        String endpointUrl = baseUrl + assetName + "-updated-merge";
        ArtifactInfo endpointInfo = new ArtifactInfo();
        endpointInfo.setArtifact(endpoint);
        endpointInfo.setArtifactDetail(endpointUrl.getBytes("UTF-8"));
        endpointInfo.setContentType("application/vnd.wso2.endpoint");

        List<ArtifactInfo> artifactList = assetInfo.getArtifactInfo();
        artifactList.add(endpointInfo);

        UpdateCompleteAssetRequest request = new UpdateCompleteAssetRequest();
        request.setPartialUpdate(false);
        request.setAssetInfoForUpdate(assetInfo);
        request.setReplaceCurrent(false);

        return provider.updateCompleteAsset(request);
    }

    /**
     * Helper method to validate the asset basic and extended info
     * 
     * @return
     */
    private AssetInfo validateAsset(String assetId) {
        // now, i search the service to get all its related objects
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();

        GetAssetInfoRequest request = new GetAssetInfoRequest();
        AssetKey key = new AssetKey();
        key.setAssetId(assetId);
        request.setAssetKey(key);
        request.setAssetType("Service");

        GetAssetInfoResponse assetInfoResponse = provider.getAssetInfo(request);
        assertEquals(AckValue.SUCCESS, assetInfoResponse.getAck());
        assertEquals(null, assetInfoResponse.getErrorMessage());

        // //now, validating the basic info of the updated asset
        BasicAssetInfo assetInfo = assetInfoResponse.getAssetInfo().getBasicAssetInfo();
        assertEquals(assetName, assetInfo.getAssetName());
        assertEquals("Service", assetInfo.getAssetType());
        assertEquals(assetDesc + " updated", assetInfo.getAssetDescription());
        // now, validating extended info
        ExtendedAssetInfo extededAssetInfo = assetInfoResponse.getAssetInfo()
                        .getExtendedAssetInfo();
        List<AttributeNameValue> attrValues = extededAssetInfo.getAttribute();
        for (AttributeNameValue attributeNameValue : attrValues) {
            if ("stringProperty".equals(attributeNameValue.getAttributeName())) {
                assertEquals(stringProperty + " updated",
                                attributeNameValue.getAttributeValueString());
            }
            else if ("longProperty".equals(attributeNameValue.getAttributeName())) {
                assertEquals(Long.toString(longProperty * 10),
                                attributeNameValue.getAttributeValueString());
            }
            else if ("booleanProperty".equals(attributeNameValue.getAttributeName())) {
                assertEquals(Boolean.toString(!booleanProperty),
                                attributeNameValue.getAttributeValueString());
            }
        }
        // now, validating lifecycle info
        AssetLifeCycleInfo lifeCycleInfo = assetInfoResponse.getAssetInfo().getAssetLifeCycleInfo();
        assertEquals("John Doe Junior", lifeCycleInfo.getDomainOwner());
        assertEquals("Business Owner", lifeCycleInfo.getDomainType());

        return assetInfoResponse.getAssetInfo();
    }

    @Test
    public void updateReplaceTest() throws Exception {
        boolean clean = false;
        try {
            Registry wso2 = RSProviderUtil.getRegistry();
            clean = !wso2.resourceExists(resources[0]);
        }
        catch (RegistryException e) {
        }
        assertTrue(clean);

        // first, create the complete asset
        CreateCompleteAssetResponse response = createAsset();
        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        // then, update the complete asset, replacing all its related objects
        UpdateCompleteAssetResponse responseUpdate = replaceAsset(response.getAssetKey().getAssetId());
        assertEquals(AckValue.SUCCESS, responseUpdate.getAck());
        assertEquals(null, responseUpdate.getErrorMessage());

        AssetInfo assetInfo = validateAsset(responseUpdate.getAssetKey().getAssetId());
        // now, validating artifacts
        List<ArtifactInfo> artifacts = assetInfo.getArtifactInfo();
        assertTrue(artifacts != null);
        assertEquals(1, artifacts.size());
        ArtifactInfo artifactInfo = artifacts.get(0);
        assertEquals("ep-" + assetName + "-updated", artifactInfo.getArtifact().getArtifactName());
        assertEquals("Endpoint", artifactInfo.getArtifact().getArtifactCategory());
        assertEquals(ArtifactValueType.URL, artifactInfo.getArtifact().getArtifactValueType());
        assertEquals(new String(
                        (baseUrl + assetName + "-updated").getBytes("UTF-8")),
                        new String(artifactInfo.getArtifactDetail()));
        assertEquals("application/vnd.wso2.endpoint", artifactInfo.getContentType());

    }

    @Test
    public void mergeCompleteAssetTest() throws Exception {
        boolean clean = false;
        try {
            Registry wso2 = RSProviderUtil.getRegistry();
            clean = !wso2.resourceExists(resources[0]);
        }
        catch (RegistryException e) {
        }
        assertTrue(clean);

        // first, create the complete asset
        CreateCompleteAssetResponse response = createAsset();
        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        // then, update the complete asset, replacing all its related objects
        UpdateCompleteAssetResponse responseUpdate = mergeAsset(response.getAssetKey().getAssetId());
        assertEquals(AckValue.SUCCESS, responseUpdate.getAck());
        assertEquals(null, responseUpdate.getErrorMessage());

        AssetInfo assetInfo = validateAsset(responseUpdate.getAssetKey().getAssetId());
        // now, validating artifacts
        List<ArtifactInfo> artifacts = assetInfo.getArtifactInfo();
        assertTrue(artifacts != null);
        assertEquals(2, artifacts.size());
        for (ArtifactInfo artifactInfo : artifacts) {
            if (("ep-" + assetName).equals(artifactInfo.getArtifact().getArtifactName())) {
                assertEquals(new String((baseUrl + assetName).getBytes("UTF-8")),
                                new String(artifactInfo.getArtifactDetail()));
            }
            else if (("ep-" + assetName + "-updated-merge").equals(artifactInfo.getArtifact()
                            .getArtifactName())) {
                assertEquals(new String(
                                (baseUrl + assetName + "-updated-merge")
                                                .getBytes("UTF-8")),
                                new String(artifactInfo.getArtifactDetail()));

            }
            else {
                // test should fail, there should be only two artifacts here
                fail("there should be only two artifacts");
            }

            assertEquals("Endpoint", artifactInfo.getArtifact().getArtifactCategory());
            assertEquals(ArtifactValueType.URL, artifactInfo.getArtifact().getArtifactValueType());
            assertEquals("application/vnd.wso2.endpoint", artifactInfo.getContentType());
        }
    }
}
