/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.init;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: CacheManagerServlet
 *
 */
 public class CacheManagerServlet extends javax.servlet.http.HttpServlet 
 {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7520023558398755394L;

	@Override
	public void init() throws ServletException
	  {	  		  
		  System.out.println("************ BEGIN CacheManagerServlet.init() ************");
		  Timer timer = new Timer();
		  CacheManagerTimerTask cacheManagerTimerTask = new CacheManagerTimerTask();
		  timer.schedule(cacheManagerTimerTask, new Date(), 1800000);
		  System.out.println("************ END CacheManagerServletinit() ************");
	  }

	  @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {
	    PrintWriter out = response.getWriter();

	    out.println("<title>" + "Cache Manager" + "</title>");
	    out.println("<h1>" + "Hey buddy, Have a nice day :-)" + "</h1>");
	    CacheManagerInitializer cacheManagerInitializer = new CacheManagerInitializer();
	    cacheManagerInitializer.initializeCacheManager();
	  }
}
