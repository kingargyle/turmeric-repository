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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;


import org.ebayopensource.turmeric.assetcreation.artifacts.AssetCreation;
import org.ebayopensource.turmeric.assetcreation.artifacts.AssetInput;
import org.ebayopensource.turmeric.assetcreation.exception.AssetPersistException;
import org.ebayopensource.turmeric.assetcreation.util.CommonUtil;
import org.ebayopensource.turmeric.assetcreation.util.FileUtil;
import org.ebayopensource.turmeric.assetdata.artifacts.AssetData;
import org.ebayopensource.turmeric.assetdata.artifacts.Assets;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;

public class AssetPersist {
	
	public static void persist(AssetStore assetStore, String inputFilePath) throws AssetPersistException
	{
		try
		{
			AssetCreation assetCreation = ReadConfigFile.readConfig(inputFilePath);
			List<AssetInput> assetInputs = assetCreation.getAssetInput();
			String filePath = assetCreation.getAssetDestinationLocation();
			String assetFilePath = null;
			AssetData assetData = new AssetData();
			List<Assets> listOfAssets = assetData.getAssets();
			Assets assets = null;
			String assetIdXmlPath = filePath+"/"+"AssetData.xml";
			
			File assetIdXml = new File(assetIdXmlPath);
			if(assetIdXml.exists() && assetIdXml.isFile())
				assetIdXml.delete();
			
			for(AssetInput assetInput : assetInputs)
			{
				if(assetInput.isPersist())
				{
					if(assetInput.isNeedFullAsset())
					{
						List<AssetInfoWrapper> listOfAssetInfoWrapper = assetStore.getAssetInfos().get(assetInput.getId());
						for(AssetInfoWrapper assetInfoWrapper : listOfAssetInfoWrapper)
						{
							if(!assetInfoWrapper.isConsumed())
							{
								assetFilePath = filePath+"/"+assetInput.getId()+"/"
										+assetInfoWrapper.getAssetInfo().getBasicAssetInfo().getAssetKey().getAssetId()+".xml";
								CommonUtil.marshalAssetInfo(assetInfoWrapper.getAssetInfo(), assetFilePath);
							}
						}
					}
					else
					{
						List<AssetIdWrapper> listOfAssetIdWrapper = assetStore.getAssetIds().get(assetInput.getId());
						assets = new Assets();
						assets.setId(assetInput.getId());
						for(AssetIdWrapper assetIdWrapper : listOfAssetIdWrapper)
						{
							if(!assetIdWrapper.isConsumed())
								assets.getAssetid().add(assetIdWrapper.getAssetId());
						}
						if(assets.getAssetid().size() > 0)
							listOfAssets.add(assets);
					}
				}
			}
			if(assetData.getAssets().size()> 0)
				CommonUtil.marshalAssetData(assetData, assetIdXmlPath);
			
			cleanUp(assetCreation);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new AssetPersistException();
		}
	}
	
	private static void cleanUp(AssetCreation assetCreation)
	{
		List<AssetInput> assetInputs = assetCreation.getAssetInput();
		List<String> id = new ArrayList<String>();
		String assetDestinationPath = assetCreation.getAssetDestinationLocation();
		
		for(AssetInput assetInput : assetInputs)
		{
			id.add(assetInput.getId());
		}
		
		File[] files = new File(assetDestinationPath).listFiles();
		for(File file : files)
		{
			if(!id.contains(file.getName()) && !file.getName().equals("AssetData.xml"))
				FileUtil.deleteDir(file);
		}
	}

}
