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
import org.ebayopensource.turmeric.assetcreation.exception.UpdateCompleteAssetException;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfoForUpdate;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.services.repositoryservice.exception.ProcessingException;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.GetAssetSubmissionPropertiesConsumer;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.RepositoryServiceClientConstants;

import org.ebayopensource.turmeric.services.repository.validation.exception.InvalidInputException;

public class GetAssetSubmissionPropertiesTest {
	public static AssetCreatorIntf assetCreator = AssetCreatorFactory.
	getAssetCreator("resource/FunctionalDomainAsset.xml");
	@BeforeClass
	public static void oneTimeSetUp() throws AssetCreationException {	
		try
		{
		assetCreator.createAsset();
//		Connection connection = ConnectionUtil.getInstance().getDefaultConnection();
//		assetSource = AssetSourceFactory.createAssetSource(connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//private Event event ;
	private static final String s_success ="PASSED";
	//private static AssetSource assetSource = null;
	/*
	 * Method under test: GetAssetSubmissionProperties
	 * Test Type        : Positive
	 * Sub  Type        : assetCurrentlyUnlocked
	 */ 
	@Test
	public void testGetAssetSubmissionProperties_UnlockedAsset()throws  Exception{
	    try{
		//Event event = new Event();
		String user = "_soaRegistry_admin";
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = assetInfo.getBasicAssetInfo().getAssetKey();
		String assetId=assetInfo.getBasicAssetInfo().getAssetKey().getAssetId();
		//Asset asset = assetSource.getAsset(assetId, false);
		AssetInfoForUpdate updateInfo = assetCreator.getAssetInfoForUpdate(assetInfo);
		updateInfo.getBasicAssetInfo().setAssetDescription("Modified");
		assetCreator.updateCompleteAsset(updateInfo);
		//ExtListenerHandlerUtil.setEvent(asset, event, user);			
		String status = GetAssetSubmissionPropertiesConsumer.testGetAssetSubmissionProperties_UnlockedAsset(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/*
	 * Method under test: GetAssetSubmissionProperties
	 * Test Type        : Positive
	 * Sub  Type        : assetCurrentlylocked
	 */
	
	public void testGetAssetSubmissionProperties_LockedAsset()throws Exception {
	    try{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		assetCreator.lockAsset(assetKey);
		String status = GetAssetSubmissionPropertiesConsumer.testGetAssetSubmissionProperties_LockedAsset(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	/*
	 * Method under test: GetAssetSubmissionProperties
	 * Test Type        : Negative
	 * Sub  Type        : EmptyRequest
	 */
	@Test
	public void testGetAssetSubmissionProperties_EmptyRequest(){
		String status =GetAssetSubmissionPropertiesConsumer.testGetAssetSubmissionProperties_EmptyRequest();
		Assert.assertEquals(s_success, status);			
	}
	/*
	 * Method under test: GetAssetSubmissionProperties
	 * Test Type        : Negative
	 * Sub  Type        : EmptyLibrary
	 */
	@Test
	public void testGetAssetSubmissionProperties_EmptyLibrary(){
		String status = GetAssetSubmissionPropertiesConsumer.testGetAssetSubmissionProperties_EmptyLibrary();
		Assert.assertEquals(s_success, status);			
	}
	/*
	 * Method under test: GetAssetSubmissionProperties
	 * Test Type        : Negative
	 * Sub  Type        : EmptyLibrary
	 */
	@Test
	public void testGetAssetSubmissionProperties_WithoutLibraryName(){
		String status = GetAssetSubmissionPropertiesConsumer.testGetAssetSubmissionProperties_WithoutLibraryName();
		Assert.assertEquals(s_success, status);			
	}
	/*
	 * Method under test: GetAssetSubmissionProperties
	 * Test Type        : Negative
	 * Sub  Type        : WithoutAssetKey
	 */
	@Test
	public void testGetAssetSubmissionProperties_WithoutAssetKey(){
		String status = GetAssetSubmissionPropertiesConsumer.testGetAssetSubmissionProperties_WithoutAssetKey();
		Assert.assertEquals(s_success, status);			
	}
	/*
	 * Method under test: GetAssetSubmissionProperties
	 * Test Type        : Negative
	 * Sub  Type        : WithInvalidLibrary
	 */
	@Test
	public void testGetAssetSubmissionProperties_WithInvalidLibrary(){
		String status = GetAssetSubmissionPropertiesConsumer.testGetAssetSubmissionProperties_WithInvalidLibrary();
		Assert.assertEquals(s_success, status);			
	}
	/*
	 * Method under test: GetAssetSubmissionProperties
	 * Test Type        : Negative
	 * Sub  Type        : WithoutAssetId
	 */
	@Test
	public void testGetAssetSubmissionProperties_WithoutAssetId(){
		String status = GetAssetSubmissionPropertiesConsumer.testGetAssetSubmissionProperties_WithoutAssetId();
		Assert.assertEquals(s_success, status);			
	}
	
	
	/*
	 * Method under test: GetAssetSubmissionProperties
	 * Test Type        : Negative
	 * Sub  Type        : WithInvalidAssetId
	 */
	@Test
	public void testGetAssetSubmissionProperties_WithInvalidAssetId(){
		String status = GetAssetSubmissionPropertiesConsumer.testGetAssetSubmissionProperties_WithInvalidAssetId();
		Assert.assertEquals(s_success, status);		
	
	}

}
