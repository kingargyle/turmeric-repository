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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;


import org.ebayopensource.turmeric.assetcreation.artifacts.AssetCreation;
import org.ebayopensource.turmeric.assetcreation.artifacts.AssetInput;
import org.ebayopensource.turmeric.assetcreation.common.RepositoryServiceConsumer;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetValidationFailedException;
import org.ebayopensource.turmeric.assetcreation.exception.IdDuplicateException;
import org.ebayopensource.turmeric.assetcreation.exception.ProcessingException;
import org.ebayopensource.turmeric.assetcreation.exception.SourceFolderNotFoundException;
import org.ebayopensource.turmeric.assetcreation.util.CommonUtil;
import org.ebayopensource.turmeric.assetcreation.util.FileUtil;
import org.ebayopensource.turmeric.assetdata.artifacts.AssetData;
import org.ebayopensource.turmeric.assetdata.artifacts.Assets;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v1.services.CreateAndSubmitAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.CreateAndSubmitAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetInfoResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.Property;
import org.ebayopensource.turmeric.repository.v1.services.SubmitForPublishingRequest;
import org.ebayopensource.turmeric.repository.v1.services.SubmitForPublishingResponse;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;


public class AssetGenerator {
	
	private static Logger s_logger = Logger.getLogger(AssetGenerator.class);
	private static Properties properties = null;
	
	public static AssetStore createAsset(String configFilePath) 
				throws AssetCreationException 
	{
		s_logger.debug("Inside AssetGenerator.createAsset()");
		AssetStore assetStore = null;
		
		try
		{
			AssetCreation assetCreation = ReadConfigFile.readConfig(configFilePath);
			
			if(!validateId(assetCreation))
			{
				s_logger.error("Input XML Configuration file must have unique <ID> element");
				throw new IdDuplicateException("Data between the <ID> element is not unique ");
			}
			
			if(!validPathLocation(assetCreation))
			{
				s_logger.error("Not able to create folder " + assetCreation.getAssetDestinationLocation());
				throw new SourceFolderNotFoundException("Unable to create a folder mentioned in configuration file");
			}
			
			s_logger.debug("XML Validation Passed");

			Map<String,AssetInfo> sourceAssetInfo = getSourceAsset(assetCreation);
			
			if(!validateSourceAsset(sourceAssetInfo,assetCreation))
			{
				s_logger.error("Source Asset validation failed");
				throw new AssetValidationFailedException("Source Asset validation Failed");
			}
			
			s_logger.debug("Source Asset Validation Passed");
			
			assetStore = createAsset(sourceAssetInfo,assetCreation);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			throw new AssetCreationException(e);
		}
		catch(JAXBException e)
		{
			throw new AssetCreationException(e);
		}
		catch(IdDuplicateException e)
		{	
			e.printStackTrace();
			throw e;
		}
		catch(SourceFolderNotFoundException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch(ServiceException e)
		{
			e.printStackTrace();
			throw new AssetCreationException(e);
		}
		catch(AssetValidationFailedException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch(ProcessingException e)
		{
			e.printStackTrace();
			throw new AssetCreationException(e);
		}
		
		//displayCachedInfo(assetStore);
		
		return assetStore;
	}
	
	private static void displayCachedInfo(AssetStore assetStore)
	{
		Map<String, List<AssetIdWrapper>> dispAssetIds = assetStore.getAssetIds();
		Map<String, List<AssetInfoWrapper>> dispAssetInfos = assetStore.getAssetInfos();
		
		//TODO: Display cached Info
		
	}
	
	private static void loadProperties() throws AssetCreationException
	{
		properties = new Properties();
		try
		{
			properties.load(new FileInputStream("src/main/resources/properties/assetcreation.properties"));
		}
		catch(IOException e)
		{
			throw new AssetCreationException("Unable to load assetcreation.properties",e);
		}
	}
	
	private static AssetStore createAsset(Map<String,AssetInfo> sourceAssetInfo, AssetCreation assetCreation)
														throws ServiceException, ProcessingException, AssetCreationException
	{
		s_logger.debug("Inside AssetGenerator.createAsset()");
		List<AssetInput> assetInputs = assetCreation.getAssetInput();
		String id = null;
		String sourceAssetId = null;
		String filePath = null;
		String assetDestinationLocation = assetCreation.getAssetDestinationLocation();
		AssetInfo tempAssetInfo, newAssetInfo = null;
		String dirPath = null;
		String assetIdXmlPath = assetDestinationLocation+"/"+"AssetData.xml"; 
		CreateAndSubmitAssetResponse submitAssetResponse = null;
		GetAssetInfoResponse assetResponse = null;
		SubmitForPublishingResponse submitForPubRes = null;
		AssetKey assetKey = null;
		AssetData assetData = null;
		boolean isFilePresent = true;
		String serviceLocation = assetCreation.getRepositoryServiceEndPoint();
		AssetStore assetStore = new AssetStore();
		List<Assets> assets = null;
		
		RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer(AssetCreationConstants.userId,
				AssetCreationConstants.password,serviceLocation);
		
		CreateAndSubmitAssetRequest submitAssetRequest = new CreateAndSubmitAssetRequest();
		GetAssetInfoRequest assetRequest = new GetAssetInfoRequest();
		SubmitForPublishingRequest submitForPubReq = new SubmitForPublishingRequest();
		
		//loadProperties(); 
		
		Property subProperty = new Property();
		subProperty.setPropertyName("RolePlayerProperty");
		subProperty.setPropertyValue("_soaRegistry");
		submitForPubReq.getRequestProperties().add(subProperty);
		submitForPubReq.setComment("Asset Created by Asset Creation tool");
		
		if(new File(assetIdXmlPath).isFile())
		{
			s_logger.debug("File "+assetIdXmlPath+ "is present");
			assetData = CommonUtil.unMarshalAssetData(assetIdXmlPath);
		}
		else
		{
			s_logger.debug("File "+assetIdXmlPath+ "is not present");
			isFilePresent = false;
		}
		
		for(AssetInput assetInput : assetInputs)
		{
			int numOfAssetsPresent = 0;
			int numOfAssetsNeeded = 0;
			
			id = assetInput.getId();
			sourceAssetId = assetInput.getSourceAssetID();
			if(!FileUtil.isFileExist(assetDestinationLocation, id, sourceAssetId))
			{
				filePath = assetDestinationLocation+"/"+id+"/"+sourceAssetId+".xml";
				s_logger.debug("Source asset not persisted, Hence Persisting in "+filePath);
				CommonUtil.marshalAssetInfo(sourceAssetInfo.get(id),filePath);
			}
			tempAssetInfo = sourceAssetInfo.get(id);
			if(assetInput.isNeedFullAsset())
			{
				s_logger.info("Need AssetInfo object for ID "+ id);
				dirPath = assetDestinationLocation+"/"+id;
				numOfAssetsPresent = FileUtil.findNumberOfAssets(dirPath);
				numOfAssetsNeeded = assetInput.getNumberOfAssets()-numOfAssetsPresent;
				s_logger.info("Number of Assets Configured in XML for ID "+ id +" = "+ assetInput.getNumberOfAssets());
				s_logger.info("Number of assets Present in system for ID "+ id +" = "+ numOfAssetsPresent);
				s_logger.info("Number of assets to be created for ID "+ id +" = "+ numOfAssetsNeeded);
			}
			else
			{
				s_logger.info("Need AssetId for ID "+ id);
				if(!isFilePresent)
				{
					numOfAssetsNeeded = assetInput.getNumberOfAssets();
				}
				else
				{
					assets = assetData.getAssets();
					for(Assets asset : assets)
					{
						if(asset.getId().equals(id))
						{
							numOfAssetsPresent = asset.getAssetid().size();
							break;
						}
					}
					numOfAssetsNeeded = assetInput.getNumberOfAssets()-numOfAssetsPresent;
					s_logger.info("Number of Assets Configured in XML for ID "+ id +" = "+ assetInput.getNumberOfAssets());
					s_logger.info("Number of assets Present in system for ID "+ id +" = "+ numOfAssetsPresent);
					s_logger.info("Number of assets to be created for ID "+ id +" = "+ numOfAssetsNeeded);
				}
			}
			
			// Loading already existing asset details
			
			if(numOfAssetsPresent > 0)
			{
				if(assetInput.isNeedFullAsset())
				{
					File[] existingAssets = new File(dirPath).listFiles();
					for(File existingAsset : existingAssets)
					{
						if(!existingAsset.getName().equals(sourceAssetId+".xml") && existingAsset.getName().endsWith(".xml"))
						{
							s_logger.debug("Unmarshalling persisted assetInfo for "+id+"from"+existingAsset.getAbsolutePath());
							AssetInfo existingAssetInfo = CommonUtil.unMarshalAssetInfo(existingAsset.getPath());
							AssetInfoWrapper assetInfoWrapper = new AssetInfoWrapper(existingAssetInfo);
							assetStore.addAssetInfo(id, assetInfoWrapper);
							s_logger.debug("Cached existing AssetInfo, Cached Detail follows");
							s_logger.debug("Cached assetId = "+ existingAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
							s_logger.debug("Cached assetName = "+ existingAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
							existingAsset.delete();
						}
					}
				}
				else
				{
					for(Assets asset : assets)
					{
						if(asset.getId().equals(id))
						{
							List<String> assetIds = asset.getAssetid();
							s_logger.debug("Caching persisted assetId for "+id);
							for(String assetId : assetIds)
							{
								AssetIdWrapper assetIdWrapper = new AssetIdWrapper(assetId);
								assetStore.addAssetId(id, assetIdWrapper);
								s_logger.debug("Cached existing AssetId, Cached Detail follows");
								s_logger.debug("Cached assetId = "+ assetId);
							}
						}
					}
				}
			}
			
			for(int index=1; index<=numOfAssetsNeeded; index++)
			{
				try
				{
					ModifyAsset modifyAsset = ModifyAssetFactory.getModifyAsset(assetInput);
					modifyAsset.modify(tempAssetInfo, assetInput);
					submitAssetRequest.setAssetInfo(tempAssetInfo);
					submitAssetRequest.setCaptureTemplateName(assetInput.getCaptureTemplateName().value());
					submitAssetRequest.setGroupName(tempAssetInfo.getBasicAssetInfo().getGroupName());
					submitAssetResponse = rsConsumer.createAndSubmitAsset(submitAssetRequest);
					Validator.validateCreateAndSubmitAssetResponse(submitAssetResponse);
					assetKey = submitAssetResponse.getAssetKey();
					s_logger.debug("Asset Created... AssetId = "+assetKey.getAssetId()+" AssetName = "+assetKey.getAssetName()+ " for ID "+id);
					if(assetInput.isPublished()==null || assetInput.isPublished())
					{
						assetKey = submitAssetResponse.getAssetKey();
						submitForPubReq.setAssetKey(assetKey);
						submitForPubRes = rsConsumer.submitForPublishing(submitForPubReq);
						Validator.validateSubmitForPublishingResponse(submitForPubRes);
						s_logger.debug("Asset Published... AssetId = "+assetKey.getAssetId()+" AssetName = "+assetKey.getAssetName()+ " for ID "+id);
					}
					if(assetInput.isNeedFullAsset())
					{
						s_logger.debug("Retriving AssetInfo for assetId = "+assetKey.getAssetId());
						assetRequest.setAssetKey(assetKey);
						assetResponse = rsConsumer.getAssetInfo(assetRequest);
						Validator.validateGetAssetInfoResponse(assetResponse);
						newAssetInfo = assetResponse.getAssetInfo();
						AssetInfoWrapper assetInfoWrapper = new AssetInfoWrapper(newAssetInfo);
						assetStore.addAssetInfo(id, assetInfoWrapper);
					}
					else
					{
						String newAssetId = assetKey.getAssetId();
						AssetIdWrapper assetIdWrapper = new AssetIdWrapper(newAssetId);
						assetStore.addAssetId(id, assetIdWrapper);
					}
				}
				catch(ProcessingException e)
				{
					s_logger.error("Proceesing exception occured for ID "+ id);
					throw e;
				}
				catch(ServiceException e)
				{
					s_logger.error("ServiceException occured while calling RS Operation for ID "+ id);
					e.printStackTrace();
					throw e;
				}
				catch(AssetCreationException e)
				{
					s_logger.error(e.getMessage()+" for ID "+ id);
					e.printStackTrace();
					throw e;
				}
			}	
			
		}
		return assetStore;
	}
	
	private static boolean validateSourceAsset(Map<String,AssetInfo> sourceAssetInfo,AssetCreation assetCreation)
	{
		s_logger.debug("Inside AssetGenerator.validateSourceAsset()");
		boolean valid = true;
		String assetType = null;
		String lifeCycleState = null;
		String id = null;
		List<AssetInput> assetInputs = assetCreation.getAssetInput();
		List<AttributeNameValue> attributes = null;
		String sourceLifeCycleState = null;
		
		for(AssetInput assetInput : assetInputs)
		{
			assetType = assetInput.getAssetType().value();
			lifeCycleState = assetInput.getLifeCycleState().value();
			id = assetInput.getId();
			
			s_logger.debug("Validating source assset for XML Unique Id " + id);
			attributes = sourceAssetInfo.get(id).getExtendedAssetInfo().getAttribute();
			
			for(AttributeNameValue attribute : attributes)
			{
				if(attribute.getAttributeName().equals("lifecycle_state"))
				{
					sourceLifeCycleState = attribute.getAttributeValueString();
					break;
				}
			}
			
			if(assetType.equals(sourceAssetInfo.get(id).getBasicAssetInfo().getAssetType()) && 
					lifeCycleState.equals(sourceLifeCycleState))
			{
				valid = true;
			}
			else
			{
				s_logger.error("Source Asset Validation failed for ID "+id+" Check the assetType and lifeCycleState");
				valid = false;
				break;
			}
		}
		
		return valid;
		
	}
	
	/**
	 * 
	 * @param assetCreation
	 * @return
	 */
	
	public static boolean validateId(AssetCreation assetCreation)
	{
		s_logger.debug("Inside AssetGenerator.validateId()");
		List<AssetInput> assetInputs = assetCreation.getAssetInput();
		Set<String> idSet = new HashSet<String>();
		boolean validate = true;
		for(AssetInput assetInput : assetInputs)
		{
			validate = idSet.add(assetInput.getId());
			if(!validate)
				break;
		}
		return validate;
	}
	
	public static boolean validPathLocation(AssetCreation assetCreation)
	{
		s_logger.debug("Inside AssetGenerator.validPathLocation()");
		String assetDestinationLocation = assetCreation.getAssetDestinationLocation();
		File file = new File(assetDestinationLocation);
		if(file.isDirectory())
		{
			s_logger.info(file.getAbsolutePath() + " folder is present");
			return true;
		}
		else
		{
			s_logger.info(file.getAbsolutePath() + "folder is missing, trying to create");
			return file.mkdirs();
		}
	}
	
	private static GetAssetInfoRequest getAssetInfoRequest(AssetInput assetInput)
	{
		s_logger.debug("Inside AssetGenerator.getAssetInfoRequest()");
		GetAssetInfoRequest assetInfoRequest = new GetAssetInfoRequest();
		Library library = new Library();
		library.setLibraryName(assetInput.getLibraryName());
		AssetKey assetKey = new AssetKey();
		assetKey.setAssetId(assetInput.getSourceAssetID());
		assetKey.setLibrary(library);
		assetInfoRequest.setAssetKey(assetKey);
		return assetInfoRequest;
	}
	
	public static Map<String,AssetInfo> getSourceAsset(AssetCreation assetCreation) throws ServiceException, AssetCreationException
	{
		s_logger.debug("Inside AssetGenerator.getSourceAsset()");
		String serviceLocation = assetCreation.getRepositoryServiceEndPoint();
		String assetDestinationLocation = assetCreation.getAssetDestinationLocation();
		s_logger.debug("Repository Service End Point " + serviceLocation);
		s_logger.debug("Asset Persisting Location" + assetDestinationLocation);
		List<AssetInput> assetInputs = assetCreation.getAssetInput();
		Map<String,AssetInfo> sourceAssetInfos = new HashMap<String, AssetInfo>();
		AssetInfo sourceAssetInfo = null;
		String id = null;
		
		try
		{
			RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer(AssetCreationConstants.userId,
				AssetCreationConstants.password,serviceLocation);
			for(AssetInput assetInput : assetInputs)
			{
				String assetId = assetInput.getSourceAssetID();
				id = assetInput.getId();
				s_logger.debug("Source Asset Id " + assetId);
				s_logger.debug("Unique Id " + id);
				if(FileUtil.isFileExist(assetDestinationLocation, id, assetId))
				{
					s_logger.info("Source asset present in " + assetDestinationLocation);
					sourceAssetInfo = CommonUtil.unMarshalAssetInfo(assetDestinationLocation+"/"+id+"/"+assetId+".xml");
				}
				else
				{
					s_logger.info("Source asset not persisted " + assetDestinationLocation + "Retriving using RS operation");
					GetAssetInfoRequest getAssetInfoRequest = getAssetInfoRequest(assetInput);
					GetAssetInfoResponse assetResponse = rsConsumer.getAssetInfo(getAssetInfoRequest);
					Validator.validateGetAssetInfoResponse(assetResponse);
					sourceAssetInfo = assetResponse.getAssetInfo();
				}
				if(validateAssetInfo(sourceAssetInfo,assetInput)) {
					sourceAssetInfos.put(assetInput.getId(), sourceAssetInfo);
				}
			}
			s_logger.debug("Source Asset Info Map Size " + sourceAssetInfos.size());
			return sourceAssetInfos;
		}
		catch(ServiceException e)
		{
			s_logger.error("ServiceException occured while calling RS getAssetInfo Operation for ID "+ id);
			e.printStackTrace();
			throw e;
		}
		catch(AssetCreationException e)
		{
			s_logger.error("Problem occured while retriving source asset, Make sure that source asset for ID "+ id + 
					"is present in particular environment and RS is up");
			s_logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	private static boolean validateAssetInfo(AssetInfo assetInfo,AssetInput assetInput)
	{
		//TODO - Copy it from RS Consumer
		return true;
	}

}
