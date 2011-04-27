/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.consumer;


import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionGroupsRequest;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionGroupsResponse;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionsRequest;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionsResponse;
import org.ebayopensource.turmeric.repository.v1.services.assertionservice.impl.TurmericASV1;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;
import org.ebayopensource.turmeric.services.assertionsservice.intf.gen.BaseAssertionsServiceConsumer;



/**
 * @author szacharias
 *
 */
public class AssertionsServiceConsumer extends BaseAssertionsServiceConsumer {

	private TurmericASV1 m_proxy; 

	private TurmericASV1 service;

	@Override
	public ApplyAssertionsResponse applyAssertions(ApplyAssertionsRequest request) {

		return getService().applyAssertions(request);
	}

	@Override
	public ApplyAssertionGroupsResponse applyAssertionGroups(ApplyAssertionGroupsRequest request) {

		return getService().applyAssertionGroups(request);
	}

	/**
	 * @return the service
	 */
	public TurmericASV1 getService() {
		try {
			if (service == null) {
				AssertionsServiceConsumer consumer = new AssertionsServiceConsumer();
				service = consumer.getProxy();
			}
		} catch (ServiceException se) {
			se.printStackTrace();
		}
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(TurmericASV1 service) {
		this.service = service;
	}
	
	@Override
	protected TurmericASV1 getProxy() throws ServiceException {
		  
	     if(m_proxy == null) {
	         Service service = ServiceFactory.create("TurmericASV1", "TurmericASV1");
	         service.setSessionTransportHeader("X-TURMERIC-SECURITY-USERID", "_regNormalUser");
	         service.setSessionTransportHeader("X-TURMERIC-SECURITY-PASSWORD", "rm@ebay01");
	         
	         m_proxy = service.getProxy();
	     }  
	        
	     return m_proxy;
    }

}
