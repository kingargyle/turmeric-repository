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

import org.junit.BeforeClass;
import org.junit.Test;

import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.CreateServiceConsumer;
public class CreativeServiceTest {
	public static AssetCreatorIntf assetCreator = AssetCreatorFactory.
	getAssetCreator("resource/ServiceAsset.xml");
	@BeforeClass
	public static void oneTimeSetUp() throws AssetCreationException {	
		assetCreator.createAsset();
	}
	private static final String s_success ="PASSED";
	/*
	 * Method under test: updateCompleteAsset
	 * Test Type        : Positive
	 * Sub  Type        : validInput
	 */
	@Test
	public void testUpdateCompleteAsset_validInput() {
		try
		{
			AssetInfo assetInfo1 = assetCreator.getAssetAsAssetInfo("Common");
			AssetInfo assetInfo2 = assetCreator.getAssetAsAssetInfo("Common1");
			String status = CreateServiceConsumer.testCreateService_validInput("withAllOptionalInputParams",assetInfo1,assetInfo2);
			AssetKey assetKey = new AssetKey();
			assetKey.setAssetId(assetInfo1.getBasicAssetInfo().getAssetKey().getAssetId());
			assetCreator.removeAsset(assetKey);
			assetKey.setAssetId(assetInfo2.getBasicAssetInfo().getAssetKey().getAssetId());
			assetCreator.removeAsset(assetKey);
			Assert.assertEquals(s_success, status);
		}
		catch(Exception e )
		{
		e.printStackTrace();
		}

}
}
