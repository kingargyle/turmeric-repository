/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.junit.Before;
import org.junit.Test;
import org.wso2.carbon.registry.core.Registry;
import static org.junit.Assert.*;

public class TestAssetFactory extends Wso2Base {
	BasicAssetInfo basicInfo = null;
	Registry registry = null;
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		basicInfo = new BasicAssetInfo();
		basicInfo.setAssetName("TestService");
		basicInfo.setAssetType("Service");
		basicInfo.setGroupName("TestGroup");
		basicInfo.setVersion("1.0.0");
		
		registry = RSProviderUtil.getRegistry();
	}
	
	
	@Test
	public void testServiceAsset() {
		AssetFactory factory = new AssetFactory(basicInfo, registry);
		Asset asset = factory.createAsset();
		assertEquals("Wrong asset created.", "Service", asset.getType());
	}

}
