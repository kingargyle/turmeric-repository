/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.common;

public enum PropertyKeys {
	REPOSITORY_SERVICE_URL("repositoryServiceUrl"),
	DEFAULT_ASSERTION_LIBRARY_NAME("assertion_library_name"),
	DEFAULT_WSDL_ASSERTION_GROUP_ASSET_NAME("default_assertion_group_name"),
	DEFAULT_WSDL_ASSERTION_GROUP_ASSET_VERSION("default_assertion_group_version"),
	WSDL_ASSERTION_GROUP("wsdl_assertion_group"),
	WSDL_ASSERTION_GROUP_VERSION("wsdl_assertion_group_version"),
	XSD_ASSERTION_GROUP("xsd_assertion_group"),
	XSD_ASSERTION_GROUP_VERSION("xsd_assertion_group_version");
	
	private final String value;

	PropertyKeys(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
}
