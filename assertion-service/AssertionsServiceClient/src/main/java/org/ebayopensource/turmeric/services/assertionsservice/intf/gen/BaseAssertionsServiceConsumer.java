/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.intf.gen;

import java.net.MalformedURLException;
import java.net.URL;

import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionGroupsRequest;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionGroupsResponse;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionsRequest;
import org.ebayopensource.turmeric.repository.v1.services.ApplyAssertionsResponse;
import org.ebayopensource.turmeric.repository.v1.services.assertionsservice.impl.TurmericASV1;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceRuntimeException;
import org.ebayopensource.turmeric.runtime.common.types.Cookie;
import org.ebayopensource.turmeric.runtime.common.types.SOAHeaders;
import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;



/**
 * Note : Generated file, any changes will be lost upon regeneration.
 * 
 */
public class BaseAssertionsServiceConsumer {

    private URL m_serviceLocation = null;
    private TurmericASV1 m_proxy = null;
    private String m_authToken = null;
    private Cookie[] m_cookies;

    public BaseAssertionsServiceConsumer() {
    }

    protected void setServiceLocation(String serviceLocation)
        throws MalformedURLException
    {
        m_serviceLocation = new URL(serviceLocation);
    }

    private void setUserProvidedSecurityCredentials(Service service) {
        RequestContext requestContext = service.getRequestContext();
        if (m_authToken!= null) {
            requestContext.setTransportHeader(SOAHeaders.AUTH_TOKEN, m_authToken);
        }
        if (m_cookies!= null) {
            for (int i = 0; (i<m_cookies.length); i ++) {
                requestContext.setCookie(m_cookies[i]);
            }
        }
    }

    /**
     * Use this method to set User Credentials (Token) 
     * 
     */
    protected void setAuthToken(String authToken) {
        m_authToken = authToken;
    }

    /**
     * Use this method to set User Credentials (Cookie)
     * 
     */
    protected void setCookies(Cookie[] cookies) {
        m_cookies = cookies;
    }

    protected TurmericASV1 getProxy()
        throws ServiceException
    {
        if (m_proxy == null) {
            String svcAdminName = "TurmericASV1";
            Service service = ServiceFactory.create(svcAdminName, "TurmericASV1", m_serviceLocation);
            m_proxy = service.getProxy();
            setUserProvidedSecurityCredentials(service);
        }
        return m_proxy;
    }

    public ApplyAssertionGroupsResponse applyAssertionGroups(ApplyAssertionGroupsRequest param0) {
        ApplyAssertionGroupsResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.applyAssertionGroups(param0);
        return result;
    }

    public ApplyAssertionsResponse applyAssertions(ApplyAssertionsRequest param0) {
        ApplyAssertionsResponse result = null;
        try {
            m_proxy = getProxy();
        } catch (ServiceException serviceException) {
            throw ServiceRuntimeException.wrap(serviceException);
        }
        result = m_proxy.applyAssertions(param0);
        return result;
    }

}
