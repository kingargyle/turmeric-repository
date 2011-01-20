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
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.RepositoryServiceClientConstants;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.UpdateAssetAttributesConsumer;
import org.junit.BeforeClass;
import org.junit.Test;


public class UpdateAssetAttributesTest {
	
	private static final String s_success = "PASSED";
    private static AssetInfo assetInfo;

    @BeforeClass
    public static void oneTimeSetUp() {
        assetInfo = CommonUtil.createBasicAsset("Service", RepositoryServiceClientConstants.VALID_SERVICE_CAPTURE_TEMPLATE);
    }
    
	/*
	 * Method under test: updateAssetAttributes
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 */
	@Test
	public void testUpdateAssetAttributes_validAsset_positive()throws  Exception  {
		try
		{
			String status = UpdateAssetAttributesConsumer.testUpdateAssetAttributes_validAsset(assetInfo);
			Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	/*
	 * Method under test: updateAssetAttributes
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 */
	@Test
	public void testUpdateAssetAttributes_invalidAsset_negative(){ 
		String status = UpdateAssetAttributesConsumer.testUpdateAssetAttributes_invalidAsset();
		Assert.assertEquals(s_success, status);
	}

}
