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

import org.junit.Ignore;
import org.junit.Test;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

/**
 * @author mgorovoy
 * 
 */
public class UpdateAssetDependenciesTest extends Wso2Base {
    // First resource path must be the primary resource created by the test
    // in order for the assumption checks to work correctly.    
    private static final String assetName = "UpdateAssetDependenciesTest";
    private static final String assetDesc = "UpdateAssetDependenciesTest description";
    private static final String libraryName = "http://www.ebay.com/marketplace/services";
    private static final String baseUrl = "http://www.domain.com/services/";

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

        FlattenedRelationship relationship = new FlattenedRelationship();
        List<Relation> relationList = relationship.getRelatedAsset();
//        relationList.add(new Relation() {
//            {
//                this.setSourceAsset(RSProviderUtil.completeAssetKey(new AssetKey() {
//                    {
//                        this.setAssetId(resources[0]);
//                    }
//                }, null, null));
//                this.setTargetAsset(RSProviderUtil.completeAssetKey(new AssetKey() {
//                    {
//                        this.setAssetId(dstAssets[0]);
//                    }
//                }, null, null));
//                this.setAssetRelationship("DependsOn");
//            }
//        });
        assetInfo.setFlattenedRelationship(relationship);

        CreateCompleteAssetRequest request = new CreateCompleteAssetRequest();
        request.setAssetInfo(assetInfo);

        RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
        return provider.createCompleteAsset(request);
    }

    private UpdateAssetDependenciesResponse updateDependencies(String assetId) throws Exception {
        AssetKey key = new AssetKey();
        key.setAssetId(assetId);

        LockAssetRequest lockReq = new LockAssetRequest();
        lockReq.setAssetKey(key);

        RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
        LockAssetResponse lockRes = provider.lockAsset(lockReq);
        assertEquals(AckValue.SUCCESS, lockRes.getAck());

        FlattenedRelationshipForUpdate relationship = new FlattenedRelationshipForUpdate();
        relationship.setDepth(1);
        relationship.setSourceAsset(key);
        List<RelationForUpdate> relationList = relationship.getRelatedAsset();
//        relationList.add(new RelationForUpdate() {
//            {
//                this.setCurrentSourceAsset(RSProviderUtil.completeAssetKey(new AssetKey() {
//                    {
//                        this.setAssetId(resources[0]);
//                    }
//                }, null, null));
//                this.setCurrentTargetAsset(RSProviderUtil.completeAssetKey(new AssetKey() {
//                    {
//                        this.setAssetId(dstAssets[0]);
//                    }
//                }, null, null));
//                this.setNewRelation(new Relation() {
//                    {
//                        this.setSourceAsset(RSProviderUtil.completeAssetKey(new AssetKey() {
//                            {
//                                this.setAssetId(resources[0]);
//                            }
//                        }, null, null));
//                        this.setTargetAsset(RSProviderUtil.completeAssetKey(new AssetKey() {
//                            {
//                                this.setAssetId(dstAssets[1]);
//                            }
//                        }, null, null));
//                        this.setAssetRelationship("DependsOn");
//                    }
//                });
//            }
//        });

        UpdateAssetDependenciesRequest request = new UpdateAssetDependenciesRequest();
        request.setAssetKey(key);
        request.setFlattenedRelationshipForUpdate(relationship);
        request.setReplaceCurrent(true);

        return provider.updateAssetDependencies(request);
    }

    /**
     * Helper method to validate the asset basic and extended info
     * 
     * @return
     */
    private AssetInfo validateAsset(String assetId) {
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
    public void updateTest() throws Exception {
        // first, create the complete asset
        CreateCompleteAssetResponse response = createAsset();
        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());

        // then, update the complete asset, replacing all its related objects
        UpdateAssetDependenciesResponse responseUpdate = updateDependencies(response.getAssetKey().getAssetId());
        assertEquals(AckValue.SUCCESS, responseUpdate.getAck());
        assertEquals(null, responseUpdate.getErrorMessage());

        validateAsset(response.getAssetKey().getAssetId());
    }
}
