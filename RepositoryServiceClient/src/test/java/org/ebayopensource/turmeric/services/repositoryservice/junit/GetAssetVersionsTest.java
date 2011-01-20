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
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetAssetVersionsConsumer;

public class GetAssetVersionsTest {

	public static AssetCreatorIntf assetCreator = AssetCreatorFactory.
	getAssetCreator("resource/FunctionalDomainAsset.xml");
	@BeforeClass
	public static void oneTimeSetUp() throws AssetCreationException {	
		assetCreator.createAsset();
	}
	private static final String s_success ="PASSED";
	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Positive
	 * Sub  Type        : validAsset without assetid
	 */
	@Test
	public void testGetAssetVersions_withoutAssetId_positive()throws  Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status = GetAssetVersionsConsumer.testGetAssetVersions_validInput("withoutAssetId", assetInfo);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch(Exception e )
		{
		e.printStackTrace();
		throw e;
		}
	}
	
	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Positive
	 * Sub  Type        : validAsset without assetName
	 */
	@Test
	public void testGetAssetVersions_withoutAssetName_positive()throws  Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status = GetAssetVersionsConsumer.testGetAssetVersions_validInput("withoutAssetName", assetInfo);
		Assert.assertEquals(s_success, status);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		}
		catch(Exception e )
		{
			e.printStackTrace();
			throw e;
		}
	}
	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Positive
	 * Sub  Type        : invalidAsset
	 */
	@Test
	public void testGetAssetVersions_invalidAsset_positive()throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status =GetAssetVersionsConsumer.testGetAssetVersions_invalidInput("invalidAssetId", assetInfo);
		Assert.assertEquals(s_success, status);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		}
		catch(Exception e )
		{
			e.printStackTrace();
			throw e;
		}
	}
	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Positive
	 * Sub  Type        : With all input params
	 */
	@Test
	public void testGetAssetVersions_withInputParams_positive()throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status =GetAssetVersionsConsumer.testGetAssetVersions_validInput("withAllInputParams", assetInfo);
		Assert.assertEquals(s_success, status);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		}
		catch(Exception e )
		{
			e.printStackTrace();
			throw e;
		}
	}
	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Positive
	 * Sub  Type        : Without library ID
	 */
	@Test
	public void testGetAssetVersions_withoutLibID_positive()throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status =GetAssetVersionsConsumer.testGetAssetVersions_validInput("withoutLibraryId", assetInfo);
		Assert.assertEquals(s_success, status);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		}
		catch(Exception e )
		{
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Negative
	 * Sub  Type        : invalidAssetName
	 */
	@Test
	public void testGetAssetVersions_invalidAssetName_negative()throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status =GetAssetVersionsConsumer.testGetAssetVersions_validInput("invalidAssetName", assetInfo);
		Assert.assertEquals(s_success, status);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		}
		catch(Exception e )
		{
		e.printStackTrace();
		throw e;
		}
	}
	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Negative
	 * Sub  Type        : No asset Id , no asset name
	 */
	@Test
	public void testGetAssetVersions_noAssetIdAndNoAssetName_negative()throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status =GetAssetVersionsConsumer.testGetAssetVersions_validInput("noAssetIdAndNoAssetName",assetInfo);
		Assert.assertEquals(s_success, status);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		}
		catch(Exception e )
		{
		e.printStackTrace();
		throw e;
		}
	}
	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Negative
	 * Sub  Type        : no library
	 */
	@Test
	public void testGetAssetVersions_noLibrary_negative()throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status = GetAssetVersionsConsumer.testGetAssetVersions_validInput("noLibrary", assetInfo);
		Assert.assertEquals(s_success, status);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		}
		catch(Exception e )
		{
		e.printStackTrace();
		throw e;
		}
	}
	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Negative
	 * Sub  Type        : No library ID, No Library name
	 */
	@Test
	public void testGetAssetVersions_noLibraryIdAndNoLibraryName_negative()throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status =GetAssetVersionsConsumer.testGetAssetVersions_validInput("noLibraryIdAndNoLibraryName",assetInfo);
		Assert.assertEquals(s_success, status);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		}
		catch(Exception e )
		{
		e.printStackTrace();
		throw e;
		}
	}
	/*
	 * Method under test: getAssetVersions
	 * Test Type        : Negative
	 * Sub  Type        : invalidLibrary
	 */
	@Test
	public void testGetAssetVersions_invalidLibrary_negative()throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		String status =GetAssetVersionsConsumer.testGetAssetVersions_validInput("invalidLibrary", assetInfo);
		Assert.assertEquals(s_success, status);
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetCreator.removeAsset(assetKey);
		}
		catch(Exception e )
		{
		e.printStackTrace();
		throw e;
		}
	}
}

