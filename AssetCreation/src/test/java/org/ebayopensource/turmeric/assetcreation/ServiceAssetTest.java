/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import org.junit.*;

import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetIdNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetPersistException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;

import static org.junit.Assert.*;

public class ServiceAssetTest {
	
	public static AssetCreatorIntf assetCreator = AssetCreatorFactory.
				getAssetCreator("src/test/resources/xml/ServiceAsset.xml");
	
	@BeforeClass
    public static void oneTimeSetUp() throws AssetCreationException {
		assetCreator.createAsset();
    }
	
	@Test
	public void testAssetInfo() throws  AssetInfoNotFoundException, IdNotFoundException {
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("100");
		System.out.println("AssetName " + assetInfo.getBasicAssetInfo().getAssetName());
		System.out.println("Asset ID" + assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
	}
	
	@Test
	public void testAssetId() throws AssetIdNotFoundException, IdNotFoundException {
		String assetId = assetCreator.getAssetAsAssetId("101");
		System.out.println("NewAssetId = " + assetId);
	}
	
	@AfterClass
    public static void oneTimeTearDown() throws AssetPersistException {
        assetCreator.persist();
    }

}
