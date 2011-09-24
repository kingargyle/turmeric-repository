/*******************************************************************************
 * Copyright (c) 2006-2011 eBay Inc. All Rights Reserved.
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

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.wso2.carbon.governance.api.util.GovernanceConstants;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.Artifact;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateCompleteAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v2.services.LockAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.LockAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetArtifactsRequest;
import org.ebayopensource.turmeric.repository.v2.services.UpdateAssetArtifactsResponse;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

/**
 * @author mgorovoy
 * 
 */
public class UpdateAssetArtifactsTest extends Wso2Base {
    private static final String assetName = "UpdateAssetArtifactsTest";
    private static final String assetDesc = "UpdateAssetArtifactsTest description";
    private static final String baseUrl = "http://www.domain.com/assets/";
  
    private CreateCompleteAssetResponse createAsset() throws Exception {
        AssetKey key = new AssetKey();
        key.setAssetName(assetName);
        key.setType("Service");
        key.setVersion("1.0.0");

        BasicAssetInfo basicInfo = new BasicAssetInfo();
        basicInfo.setAssetKey(key);
        basicInfo.setAssetName(assetName);
        basicInfo.setAssetDescription(assetDesc);
        basicInfo.setAssetType("Service");
        basicInfo.setVersion(key.getVersion());

        ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();

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
        endpointInfo.setContentType(GovernanceConstants.ENDPOINT_MEDIA_TYPE);

        List<ArtifactInfo> artifactList = assetInfo.getArtifactInfo();
        artifactList.add(endpointInfo);

        CreateCompleteAssetRequest request = new CreateCompleteAssetRequest();
        request.setAssetInfo(assetInfo);

        RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
        return provider.createCompleteAsset(request);
    }

    private UpdateAssetArtifactsResponse replaceAsset(String assetId) throws Exception {
        AssetKey key = new AssetKey();
        key.setAssetId(assetId);

        LockAssetRequest lockReq = new LockAssetRequest();
        lockReq.setAssetKey(key);

        RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
        LockAssetResponse lockRes = provider.lockAsset(lockReq);
        assertEquals(AckValue.SUCCESS, lockRes.getAck());

        UpdateAssetArtifactsRequest request = new UpdateAssetArtifactsRequest();
        request.setPartialUpdate(false);
        request.setReplaceCurrent(true);
        request.setAssetKey(key);
        List<ArtifactInfo> artifactList = request.getArtifactInfo();

        Artifact endpoint = new Artifact();
        endpoint.setArtifactName("ep-" + assetName + "-updated");
        endpoint.setArtifactCategory("Endpoint");
        endpoint.setArtifactValueType(ArtifactValueType.URL);

        String endpointUrl = baseUrl + assetName + "-updated";
        ArtifactInfo endpointInfo = new ArtifactInfo();
        endpointInfo.setArtifact(endpoint);
        endpointInfo.setArtifactDetail(endpointUrl.getBytes("UTF-8"));
        endpointInfo.setContentType("application/vnd.wso2.endpoint");
        artifactList.add(endpointInfo);

        return provider.updateAssetArtifacts(request);
    }

    private UpdateAssetArtifactsResponse mergeAsset(String assetId) throws Exception {
        AssetKey key = new AssetKey();
        key.setAssetId(assetId);

        LockAssetRequest lockReq = new LockAssetRequest();
        lockReq.setAssetKey(key);

        RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
        LockAssetResponse lockRes = provider.lockAsset(lockReq);
        assertEquals(AckValue.SUCCESS, lockRes.getAck());

        UpdateAssetArtifactsRequest request = new UpdateAssetArtifactsRequest();
        request.setPartialUpdate(false);
        request.setReplaceCurrent(false);
        request.setAssetKey(key);

        List<ArtifactInfo> artifactList = request.getArtifactInfo();

        Artifact endpoint = new Artifact();
        endpoint.setArtifactName("ep-" + assetName + "-updated-merge");
        endpoint.setArtifactCategory("Endpoint");
        endpoint.setArtifactValueType(ArtifactValueType.URL);

        String endpointUrl = baseUrl + assetName + "-updated-merge";
        ArtifactInfo endpointInfo = new ArtifactInfo();
        endpointInfo.setArtifact(endpoint);
        endpointInfo.setArtifactDetail(endpointUrl.getBytes("UTF-8"));
        endpointInfo.setContentType("application/vnd.wso2.endpoint");
        artifactList.add(endpointInfo);

        return provider.updateAssetArtifacts(request);
    }

    /**
     * Helper method to retrieve the asset basic and extended info
     * 
     * @return
     */
    private AssetInfo getAsset(String assetId) {
        // now, i search the service to get all its related objects
        RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();

        GetAssetInfoRequest request = new GetAssetInfoRequest();
        AssetKey key = new AssetKey();
        key.setAssetId(assetId);
        request.setAssetKey(key);
        request.setAssetType("Service");

        GetAssetInfoResponse assetInfoResponse = provider.getAssetInfo(request);
        assertEquals(AckValue.SUCCESS, assetInfoResponse.getAck());
        assertEquals(null, assetInfoResponse.getErrorMessage());

        return assetInfoResponse.getAssetInfo();
    }

    @Test
    @Ignore
    public void updateReplaceTest() throws Exception {
        // first, create the complete asset
        CreateCompleteAssetResponse response = createAsset();
        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        // then, update the complete asset, replacing all its related objects
        UpdateAssetArtifactsResponse responseUpdate = replaceAsset(response.getAssetKey().getAssetId());
        assertEquals(AckValue.SUCCESS, responseUpdate.getAck());
        assertEquals(null, responseUpdate.getErrorMessage());

        AssetInfo assetInfo = getAsset(response.getAssetKey().getAssetId());
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
    @Ignore
    public void mergeCompleteAssetTest() throws Exception {
        // first, create the complete asset
        CreateCompleteAssetResponse response = createAsset();
        String errorMessage = null;
        if (!response.getAck().equals(AckValue.SUCCESS)) {
        	for (CommonErrorData error : response.getErrorMessage().getError()) {
        		errorMessage = error.getMessage();
        		fail("Unable to complete asset creation. " + errorMessage);
        	}
        }

        // then, update the complete asset, replacing all its related objects
        UpdateAssetArtifactsResponse responseUpdate = mergeAsset(response.getAssetKey().getAssetId());
        assertEquals(AckValue.SUCCESS, responseUpdate.getAck());
        assertEquals(null, responseUpdate.getErrorMessage());

        AssetInfo assetInfo = this.getAsset(response.getAssetKey().getAssetId());
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
