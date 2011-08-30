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

/**
 * The Class ServiceAssetTest.
 */
public class ServiceAssetTest {
	
	/** The asset creator. */
	public static AssetCreatorIntf assetCreator = AssetCreatorFactory.
				getAssetCreator("src/test/resources/xml/ServiceAsset.xml");
	
	/**
	 * One time set up.
	 *
	 * @throws AssetCreationException the asset creation exception
	 */
	@BeforeClass
    public static void oneTimeSetUp() throws AssetCreationException {
		assetCreator.createAsset();
    }
	
	/**
	 * Test asset info.
	 *
	 * @throws AssetInfoNotFoundException the asset info not found exception
	 * @throws IdNotFoundException the id not found exception
	 */
	@Test
	public void testAssetInfo() throws  AssetInfoNotFoundException, IdNotFoundException {
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("100");
		System.out.println("AssetName " + assetInfo.getBasicAssetInfo().getAssetName());
		System.out.println("Asset ID" + assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
	}
	
	/**
	 * Test asset id.
	 *
	 * @throws AssetIdNotFoundException the asset id not found exception
	 * @throws IdNotFoundException the id not found exception
	 */
	@Test
	public void testAssetId() throws AssetIdNotFoundException, IdNotFoundException {
		String assetId = assetCreator.getAssetAsAssetId("101");
		System.out.println("NewAssetId = " + assetId);
	}
	
	/**
	 * One time tear down.
	 *
	 * @throws AssetPersistException the asset persist exception
	 */
	@AfterClass
    public static void oneTimeTearDown() throws AssetPersistException {
        assetCreator.persist();
    }

}
