/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetPersistException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;

public class ServiceAssetWithWsdl {
	
	public static AssetCreatorIntf assetCreator = AssetCreatorFactory.
				getAssetCreator("src/test/resources/xml/ServiceAssetWithWsdl.xml");
	
	@BeforeClass
    public static void oneTimeSetUp() throws AssetCreationException {
		assetCreator.createAsset();
    }
	
	@Test
	public void testAssetInfo() throws  AssetInfoNotFoundException, IdNotFoundException {
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("104");
		System.out.println("AssetName " + assetInfo.getBasicAssetInfo().getAssetName());
		System.out.println("Asset ID" + assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
	}
	
	@AfterClass
    public static void oneTimeTearDown() throws AssetPersistException {
        assetCreator.persist();
    }

}
