/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import org.junit.Test;
import org.junit.BeforeClass;
import junit.framework.Assert;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.ProcessingException;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetAssetDependenciesByGraphConsumer;

public class GetAssetDependenciesByGraphTest {
	public static AssetCreatorIntf assetCreator = AssetCreatorFactory.
	getAssetCreator("resource/FunctionalDomainAsset.xml");
	@BeforeClass
	public static void oneTimeSetUp() throws AssetCreationException {	
		assetCreator.createAsset();
	}
	private static final String s_success ="PASSED";

	/*
	 * Method under test: getAssetDependenciesByGraph
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 */
	@Test
	public void testGetAssetDependenciesByGraph_positive() throws  AssetInfoNotFoundException, IdNotFoundException, ProcessingException {
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status = GetAssetDependenciesByGraphConsumer.testGetAssetDependenciesByGraph_validAsset(assetInfo);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
	}
	
	/*
	 * Method under test: getAssetDependenciesByGraph
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 */
	@Test
	public void testGetAssetDependenciesByGraph_invalidAsset_negative() {
		String status = GetAssetDependenciesByGraphConsumer.testGetAssetDependenciesByGraph_invalidAsset();
		Assert.assertEquals(s_success, status);
	}



}
