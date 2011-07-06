/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.assetcreation.artifacts.AssetCreation;


public class ReadConfigFile {
	
	
	private static Logger s_logger = Logger.getLogger(ReadConfigFile.class);

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static AssetCreation readConfig(String configFilePath) throws 
									JAXBException, FileNotFoundException
	{
		s_logger.debug("Inside ReadConfigFile.readConfig()");
		AssetCreation assetCreation = null;
		
		try {
			File inputXmlFile = new File(configFilePath);
			if(!inputXmlFile.exists())
			{
				s_logger.error("Input XML configuration file ("+inputXmlFile.getName()+") is missing on this path " + inputXmlFile.getAbsolutePath());
				throw new FileNotFoundException("Input XML configuration file ("+inputXmlFile.getName()+") is missing on this path " + inputXmlFile.getAbsolutePath());
			}
			JAXBContext jc = JAXBContext.newInstance("org.ebayopensource.turmeric.assetcreation.artifacts");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			@SuppressWarnings("unchecked")
			JAXBElement<AssetCreation> element = (JAXBElement<AssetCreation>)unmarshaller.unmarshal(inputXmlFile);
			assetCreation = element.getValue();
			s_logger.info(inputXmlFile.getName()+ " is proper ");
		} catch (JAXBException e) {
			s_logger.error("Input XML configuration file is not compatible with XSD");
			e.printStackTrace();
			throw e;
		}
		
		return assetCreation;
		
	}
	
	
}
