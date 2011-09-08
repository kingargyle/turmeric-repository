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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
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
public class UpdateAssetAttributesTest extends Wso2Base {
    // First resource path must be the primary resource created by the test
    // in order for the assumption checks to work correctly.
    private static final String[] resources = {
            "/_system/governance/trunk/services/http/www/ebay/com/marketplace/services/UpdateAssetAttributesTest",
            "/_system/governance/trunk/endpoints/http/localhost:8080/ep-UpdateAssetAttributesTest",
            "/_system/governance/trunk/endpoints/http/localhost:8080/ep-UpdateAssetAttributesTest-updated",
            "/_system/governance/trunk/endpoints/http/localhost:8080/ep-UpdateAssetAttributesTest-updated-merge" };

    private static final String assetName = "UpdateAssetAttributesTest";
    private static final String assetDesc = "UpdateAssetAttributesTest description";
    private static final String libraryName = "http://www.ebay.com/marketplace/services";
    private static final String stringProperty = "test value";
    private static final Long longProperty = new Long(100000l);
    private static final Boolean booleanProperty = Boolean.FALSE;

    @Before
    @Override
    public void setUp() throws Exception {
    	super.setUp();
    	
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

        assertTrue(exists);
    }
    
    @After
    public void cleanUp() throws Exception {
        Registry wso2 = RSProviderUtil.getRegistry();

        for (String resource : resources) {
            if (wso2.resourceExists(resource)) {
                wso2.delete(resource);
            }
        }
    	
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

        CreateCompleteAssetRequest request = new CreateCompleteAssetRequest();
        request.setAssetInfo(assetInfo);

        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        return provider.createCompleteAsset(request);
    }

    private UpdateCompleteAssetResponse replaceAsset(AssetKey assetKey) throws Exception {
        AssetKey key = assetKey;
        key.setType("Service");

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
        UpdateCompleteAssetResponse responseUpdate = replaceAsset(response.getAssetKey());
        assertEquals(AckValue.SUCCESS, responseUpdate.getAck());
        assertEquals(null, responseUpdate.getErrorMessage());

        validateAsset(responseUpdate.getAssetKey().getAssetId());
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

        validateAsset(responseUpdate.getAssetKey().getAssetId());
    }
}
