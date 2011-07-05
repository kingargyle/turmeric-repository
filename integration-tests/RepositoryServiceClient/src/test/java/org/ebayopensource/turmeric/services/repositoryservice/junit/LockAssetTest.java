/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;
import junit.framework.Assert;

import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.services.repository.listener.util.CommonUtil;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.LockAssetConsumer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



public class LockAssetTest {	

	
	
	private static final String s_success ="PASSED";
	private static AssetInfo assetInfo;

    @BeforeClass
    public static void oneTimeSetUp() {
        assetInfo = CommonUtil.createBasicAsset("Service", "Propose Service Template");
        
    }
	
	
	/*
	 * Method under test: lockAsset
	 * Test Type        : Positive
	 * Sub  Type        : assetCurrentlyUnlocked
	 */ 
	
	@Test
	public void testLockAsset_assetCurrentlyUnlocked_positive()
	{
		String status = LockAssetConsumer.testLockAsset_validAsset(assetInfo.getBasicAssetInfo().getAssetKey());
		CommonUtil.unLockAsset(assetInfo.getBasicAssetInfo().getAssetKey());
		Assert.assertEquals(s_success, status);
	}
	
	/*
	 * Method under test: lockAsset
	 * Test Type        : Negative
	 * Sub  Type        : assetCurrentlyLocked
	 */
	
//	@Test
//	public void testLockAsset_assetCurrentlyLocked_negative()throws  Exception  {
//	try
//		{
//		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
//		AssetKey assetKey = new AssetKey();
//		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
//		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
//		assetCreator.lockAsset(assetKey);
//		String status = LockAssetConsumer.testLockAsset_assetCurrentlyLocked(assetInfo);
//		assetCreator.removeAsset(assetKey);
//		Assert.assertEquals(s_success, status);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			throw e;
//		}
//	}
	
	/*
	 * Method under test: lockAsset
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 */
	@Test
	public void testLockAsset_invalidAsset_negative() {
		String status = LockAssetConsumer.testLockAsset_invalidAsset(assetInfo.getBasicAssetInfo().getAssetKey());
		Assert.assertEquals(s_success, status);
	}
	
	
	@AfterClass
	public static void oneTimeTearDown()
	{
		CommonUtil.removeAsset(assetInfo.getBasicAssetInfo().getAssetKey());
	}

}
