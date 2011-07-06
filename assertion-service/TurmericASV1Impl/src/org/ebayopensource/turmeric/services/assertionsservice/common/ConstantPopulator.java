/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConstantPopulator {
	private static ConstantPopulator instance = null;
	private Properties properties;

	private static Logger s_logger = Logger.getLogger(ConstantPopulator.class);

	public static Logger getLogger() {
		return s_logger;
	}

	protected ConstantPopulator() throws IOException {
		String defaultSvcLayerFilePath = "META-INF/soa/services/config/TurmericASV1/ebayServices.properties";
		InputStream propFileStream = ConstantPopulator.class.getClassLoader()
				.getResourceAsStream(defaultSvcLayerFilePath);
		s_logger.error("propFileStream="+propFileStream);
		properties = new Properties();
		properties.load(propFileStream);
		s_logger.error("propFileStream loaded in proeprties object="+properties);
		try {
			propFileStream.close();
		} catch (Exception e) {
			s_logger.error("Error closing ebayServices.properties input stream", e);
		}
	}

	public static ConstantPopulator getInstance() {
		if (instance == null) {
			try {
				instance = new ConstantPopulator();
			} catch (IOException e) {
				getLogger().error(
						"Unable to get ConstantPopulator class instance");
			}
		}

		return instance;
	}

	public String getValueOf(PropertyKeys propertyKey) {
		return properties.getProperty(propertyKey.value());
	}

	// public static void main(String[] args) {
	// ConstantPopulator populator = ConstantPopulator.getInstance();
	// String result = populator.getValueOf(PropertyKeys."libraryURL");
	// System.out.println("libraryURL: " + result);
	//
	// System.out.println("libraryURL: " +
	// ConstantPopulator.getInstance().getValueOf(PropertyKeys.LIBRARY_URL));
	//
	// }
}
