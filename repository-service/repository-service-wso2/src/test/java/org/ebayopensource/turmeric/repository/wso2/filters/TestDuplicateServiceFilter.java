/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.filters;

import javax.xml.namespace.QName;

import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.services.dataobjects.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDuplicateServiceFilter {
	private BasicAssetInfo basicInfo = null;
	private Service service = null;

	@Before
	public void setUp() throws Exception {
		service = new Service("1234", new QName("http://www.example.org", "TestService"));
		basicInfo = new BasicAssetInfo();
		
		basicInfo.setAssetName("TestService");
		basicInfo.setAssetDescription("A description.");
		basicInfo.setAssetLongDescription("A really long description.");
		basicInfo.setVersion("1.0.0");
		basicInfo.setNamespace("http://www.example.org");
		basicInfo.setGroupName("TestGroup");
		
		service.setAttribute("name", basicInfo.getAssetName());
		service.setAttribute("description",
				basicInfo.getAssetDescription());
		service.setAttribute("version", basicInfo.getVersion());
		service.setAttribute("namespace", basicInfo.getNamespace());
		service.setAttribute("Owner", basicInfo.getGroupName());
		
		
	}
	
	@After
	public void tearDown() {
		service = null;
		basicInfo = null;
	}
	
	@Test
	public void testDuplicateFound() throws Exception {
		DuplicateServiceFilter dsf = new DuplicateServiceFilter(basicInfo);
		assertTrue("Did not find duplicate service", dsf.matches(service));
	}
	
	@Test
	public void testDuplicateNotFoundNamespace() throws Exception {
		basicInfo.setNamespace("http://www.example.org/invalid");
		DuplicateServiceFilter dsf = new DuplicateServiceFilter(basicInfo);
		assertFalse(dsf.matches(service));
	}
	
	@Test
	public void testDuplicateNotFoundName() throws Exception {
		basicInfo.setAssetName("InvalidService");
		DuplicateServiceFilter dsf = new DuplicateServiceFilter(basicInfo);
		assertFalse(dsf.matches(service));
	}
	
	@Test
	public void testDuplicateNotFoundVersion() throws Exception {
		basicInfo.setVersion("1.1.1");
		DuplicateServiceFilter dsf = new DuplicateServiceFilter(basicInfo);
		assertFalse(dsf.matches(service));
	}
	


}
