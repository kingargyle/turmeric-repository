/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repository.listener.common;

/**
 * a convenient class to hold all the admin related properties
 * 
 * @author xicai
 *
 */
public class AdminProperty {
	
	private String serverURL;
	private String userName;
	private String password;
	private String libraryId;
	private String typeLibraryName;
	public String getTypeLibraryName() {
		return typeLibraryName;
	}
	public void setTypeLibraryName(String typeLibraryName) {
		this.typeLibraryName = typeLibraryName;
	}
	public String getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServerURL() {
		return serverURL;
	}
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
