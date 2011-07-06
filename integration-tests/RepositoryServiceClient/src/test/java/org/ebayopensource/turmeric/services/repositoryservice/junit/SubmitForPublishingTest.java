/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.Assert;

import org.ebayopensource.turmeric.assetcreation.AssetCreatorFactory;
import org.ebayopensource.turmeric.assetcreation.AssetCreatorIntf;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.SubmitForPublishingConsumer;
import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.UnlockAssetConsumer;

public class SubmitForPublishingTest {
	public static AssetCreatorIntf assetCreator = AssetCreatorFactory.
	getAssetCreator("resource/FunctionalDomainAsset.xml");
	@BeforeClass
	public static void oneTimeSetUp() throws AssetCreationException {	
		assetCreator.createAsset();
	}
	private static final String s_success ="PASSED";
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : validAsset locked
	 */
	@Test
	public void testSubmitForPublishing_validAsset_lockedByUser() throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		assetCreator.lockAsset(assetKey);
		String status = SubmitForPublishingConsumer.testSubmitForPublishing_validAsset_lockedByUser(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : validAsset unlocked
	 */	@Test
	public void testSubmitForPublishing_validAsset_unlocked() throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		String status = SubmitForPublishingConsumer.testSubmitForPublishing_validAsset_unlocked(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : validAsset_libNotLogged()
	 */
	@Test
	public void testSubmitForPublishing_validAsset_libNotLogged() throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("SystemAsset");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		assetCreator.lockAsset(assetKey);
		String status = SubmitForPublishingConsumer.testSubmitForPublishing_validAsset_libNotLogged(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Negative
	 * Sub  Type        : invalidLogin
	 */
	@Test
	public void testSubmitForPublishing_invalidLogin() throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		String status = SubmitForPublishingConsumer.testSubmitForPublishing_invalidLogin(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : validAsset_lockedForEdit
	 */
	@Test
	public void testSubmitForPublishing_validAsset_lockedForEdit() throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		String status =SubmitForPublishingConsumer.testSubmitForPublishing_validAsset_lockedForEdit(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : emptyHeader
	 */
	@Test
	public void testSubmitForPublishing_emptyHeader() throws Exception {
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		String status =SubmitForPublishingConsumer.testSubmitForPublishing_emptyHeader(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : inValidSession
	 */
	@Test
	public void testSubmitForPublishing_inValidSession() throws Exception{
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		String status = SubmitForPublishingConsumer.testSubmitForPublishing_inValidSession(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	
	

	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : incompleteAsset
	 */
	@Test
	public void testSubmitForPublishing_incompleteAsset() throws Exception{
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		

assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		

assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		String status = 

SubmitForPublishingConsumer.testSubmitForPublishing_incompleteAsset(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : incompleteAsset
	 */
	@Test
	public void testSubmitForPublishing_withoutLibraryAndAssetId() throws Exception{
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		String status =SubmitForPublishingConsumer.testSubmitForPublishing_withoutLibraryAndAssetId(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : EmptyAsset
	 */
	@Test
	public void testSubmitForPublishing_withEmptyAssetKey(){
		
		String status =SubmitForPublishingConsumer.testSubmitForPublishing_withEmptyAssetKey();
		Assert.assertEquals(s_success, status);
}
	/*
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : withoutLibraryName
	 */
	@Test
	public void testSubmitForPublishing_withoutLibraryName() throws Exception{
		try
		{
		AssetInfo assetInfo = assetCreator.getAssetAsAssetInfo("Common");
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
		assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
		String status =SubmitForPublishingConsumer.testSubmitForPublishing_withoutLibraryName(assetInfo);
		assetCreator.removeAsset(assetKey);
		Assert.assertEquals(s_success, status);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
}
	

}
