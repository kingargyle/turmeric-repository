/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import static org.junit.Assume.assumeTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.Assert;

import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.UpdateAssetArtifactsConsumer;


public class UpdateAssetArtifactTest {
	
	private static final String s_success = "PASSED";
	private static AssetKey assetKey;
    private static AssetInfo serviceAssetInfo;

    @BeforeClass
    public static void oneTimeSetUp() {
        assetKey = CommonUtil.createCompleteServiceAsset();
        serviceAssetInfo = CommonUtil.getAssetInfo(assetKey);
    }

	/*
	 * Method under test: updateAssetArtifacts
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 */
	@Test
	public void testUpdateAssetArtifacts_validAsset_positive()throws  Exception  {
		try
		{
		String status = UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts_validAsset(serviceAssetInfo);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * Method under test: updateAssetArtifacts
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 */
	@Test
	public void testUpdateAssetArtifacts_invalidAsset_negative() {
		String status = UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts_invalidAsset();
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: updateAssetArtifacts
	 * Test Type        : Negative
	 * Sub  Type        : InvalidArtifactId
	 */
	@Test
	public void testUpdateAssetArtifacts_invalidArtifactId() {
		String status = UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts_invalidArtifactId(serviceAssetInfo);
		assumeTrue(s_success.equalsIgnoreCase(status));
	}
	/*
	 * Method under test: updateAssetArtifacts
	 * Test Type        : Negative
	 * Sub  Type        : noArtifactValueType
	 */
	@Test
	public void testUpdateAssetArtifacts_noArtifactValueType() {
		String status = UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts_noArtifactValueType(serviceAssetInfo);
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: updateAssetArtifacts
	 * Test Type        : Negative
	 * Sub  Type        : noArtifactName
	 */
	@Test
	public void testUpdateAssetArtifacts_noArtifactName(){
		String status = UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts_noArtifactName(serviceAssetInfo);
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: updateAssetArtifacts
	 * Test Type        : Negative
	 * Sub  Type        : noArtifactCategory
	 */
	@Test
	public void testUpdateAssetArtifacts_noArtifactCategory(){
		String status = UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts_noArtifactCategory(serviceAssetInfo);
		Assert.assertEquals(s_success, status);
	}
	/*
	 * Method under test: updateAssetArtifacts
	 * Test Type        : Negative
	 * Sub  Type        : noArtifactDetail
	 */
	@Test
	public void testUpdateAssetArtifacts_noArtifactDetail() {
		String status = UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts_noArtifactDetail(serviceAssetInfo);
		Assert.assertEquals(s_success, status);
	}
		
}
