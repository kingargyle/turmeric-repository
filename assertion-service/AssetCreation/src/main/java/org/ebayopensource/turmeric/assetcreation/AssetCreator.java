/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.assetcreation.artifacts.AssetCreation;
import org.ebayopensource.turmeric.assetcreation.artifacts.AssetInput;
import org.ebayopensource.turmeric.assetcreation.common.RepositoryServiceConsumer;
import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetIdNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetInfoNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.AssetPersistException;
import org.ebayopensource.turmeric.assetcreation.exception.IdNotFoundException;
import org.ebayopensource.turmeric.assetcreation.exception.ProcessingException;
import org.ebayopensource.turmeric.assetcreation.exception.UpdateCompleteAssetException;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v1.services.*;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;


public class AssetCreator implements AssetCreatorIntf {
	
	String inputFilePath;
	AssetStore assetStore;
	
	private static Logger s_logger = Logger.getLogger(AssetCreator.class);
	
	public AssetCreator(String inputFilePath)
	{
		this.inputFilePath = inputFilePath;
	}

	@Override
	public void createAsset() throws AssetCreationException {
		s_logger.debug("Inside AssetCreator.createAsset()");
		assetStore = AssetGenerator.createAsset(inputFilePath); 
	}

	@Override
	public String getAssetAsAssetId(String id) throws AssetIdNotFoundException, IdNotFoundException {
		s_logger.debug("Inside AssetCreator.getAssetAsAssetId()");
		return assetStore.getAssetId(id);
	}

	@Override
	public AssetInfo getAssetAsAssetInfo(String id) throws AssetInfoNotFoundException, IdNotFoundException {
		s_logger.debug("Inside AssetCreator.getAssetAsAssetInfo()");
		return assetStore.getAssetInfo(id);
	}

	@Override
	public void persist() throws AssetPersistException {
		s_logger.debug("Inside AssetCreator.persist()");
		AssetPersist.persist(assetStore,inputFilePath);
	}
	
	@Override
	public AssetInfoForUpdate getAssetInfoForUpdate(AssetInfo assetInfo) {
		s_logger.debug("Inside AssetCreator.getAssetInfoForUpdate()");
		AssetInfoForUpdate assetInfoForUpdate = new AssetInfoForUpdate();
		assetInfoForUpdate.setAssetLifeCycleInfo(assetInfo.getAssetLifeCycleInfo());
		assetInfoForUpdate.setBasicAssetInfo(assetInfo.getBasicAssetInfo());
		return assetInfoForUpdate;
	}
	
	@Override
	public UpdateCompleteAssetResponse updateCompleteAsset(AssetInfoForUpdate assetInfoForUpdate) throws UpdateCompleteAssetException {
		
		UpdateCompleteAssetResponse updateAssetResponse = null;
		
		try
		{
			AssetCreation assetCreation = ReadConfigFile.readConfig(inputFilePath);
			RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer(AssetCreationConstants.userId,
					AssetCreationConstants.password,assetCreation.getRepositoryServiceEndPoint());
			UpdateCompleteAssetRequest updateAssetRequest = new UpdateCompleteAssetRequest();
			updateAssetRequest.setAssetInfoForUpdate(assetInfoForUpdate);
			updateAssetResponse = rsConsumer.updateCompleteAsset(updateAssetRequest);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new UpdateCompleteAssetException(e.getMessage());
		}
		return updateAssetResponse;
	}
	
	@Override
	public LockAssetResponse lockAsset(AssetKey assetKey) throws ProcessingException {
		
		LockAssetResponse lockAssetResponse = null;
		try
		{
			AssetCreation assetCreation = ReadConfigFile.readConfig(inputFilePath);
			RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer(AssetCreationConstants.userId,
					AssetCreationConstants.password,assetCreation.getRepositoryServiceEndPoint());
			LockAssetRequest lockAssetRequest = new LockAssetRequest();
			lockAssetRequest.setAssetKey(assetKey);
			lockAssetResponse = rsConsumer.lockAsset(lockAssetRequest);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ProcessingException(e.getMessage());
		}
		return lockAssetResponse;
	}
	
	
	@Override
	public UnlockAssetResponse unlockAsset(AssetKey assetKey) throws ProcessingException {
		
		UnlockAssetResponse unlockAssetResponse = null;
		try
		{
			AssetCreation assetCreation = ReadConfigFile.readConfig(inputFilePath);
			RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer(AssetCreationConstants.userId,
					AssetCreationConstants.password,assetCreation.getRepositoryServiceEndPoint());
			UnlockAssetRequest unlockAssetRequest = new UnlockAssetRequest();
			unlockAssetRequest.setAssetKey(assetKey);
			unlockAssetResponse = rsConsumer.unlockAsset(unlockAssetRequest);
		}
		catch(FileNotFoundException e)
		{
			throw new ProcessingException(e);
		}
		catch(JAXBException e)
		{
			throw new ProcessingException(e);
		}
		catch(ServiceException e)
		{
			e.printStackTrace();
			throw new ProcessingException(e);
		}
		return unlockAssetResponse;
	}
	
	@Override
	public RemoveAssetResponse removeAsset(AssetKey assetKey) throws ProcessingException {
		
		s_logger.debug("Inside AssetCreator.removeAsset()");
		RemoveAssetResponse removeAssetRes = null;
		try
		{
			AssetCreation assetCreation = ReadConfigFile.readConfig(inputFilePath);
			RepositoryServiceConsumer rsConsumer = new RepositoryServiceConsumer(AssetCreationConstants.userId,
					AssetCreationConstants.password,assetCreation.getRepositoryServiceEndPoint());
			RemoveAssetRequest removeAssetReq = new RemoveAssetRequest();
			removeAssetReq.setAssetKey(assetKey);
			removeAssetRes = rsConsumer.removeAsset(removeAssetReq);
		}
		catch(FileNotFoundException e)
		{
			throw new ProcessingException(e);
		}
		catch(JAXBException e)
		{
			throw new ProcessingException(e);
		}
		catch(ServiceException e)
		{
			e.printStackTrace();
			throw new ProcessingException(e);
		}
		return removeAssetRes;
	}
	
	@Override
	public void deleteConsumedAssets() throws ProcessingException
	{
		s_logger.debug("Inside deleteConsumedAssets()");
		AssetCreation assetCreation = null;
		RepositoryServiceConsumer rsConsumer = null;
		
		try {
			assetCreation = ReadConfigFile.readConfig(inputFilePath);
		} catch (FileNotFoundException e) {
			String message = inputFilePath+" file is not present ";
			s_logger.error(message, e);
			throw new ProcessingException(message,e);
		} catch (JAXBException e) {
			String message = inputFilePath+" file is not proper. Exception occured while trying to unmarshall it ";
			s_logger.error(message, e);
			throw new ProcessingException(message,e);
		}
		
		try {
			rsConsumer = new RepositoryServiceConsumer(AssetCreationConstants.superUserId,
					AssetCreationConstants.superPassword,assetCreation.getRepositoryServiceEndPoint());
		} catch (ServiceException e) {
			s_logger.error(e.getMessage(), e);
			throw new ProcessingException(e.getMessage(),e);
		}
		
		ExtendedAssetInfo extendedAssetInfo = new ExtendedAssetInfo();
		AttributeNameValue nvPair = new AttributeNameValue();
		nvPair.setAttributeName(AssetCreationConstants.IS_APPROVAL_REQUIRED);
		nvPair.setAttributeValueString(AssetCreationConstants.TRUE);
		extendedAssetInfo.getAttribute().add(nvPair);
		
		Map<String, List<AssetIdWrapper>> assetIdMap = assetStore.getAssetIds();
		Set<String> keySet = assetIdMap.keySet();
		
		for (String key : keySet) {
			
			if(!isDeleteFlagOn(assetCreation,key))
				continue;
			
			List<AssetIdWrapper> listOfAssetIdWrapper = assetIdMap.get(key);
			
			for (AssetIdWrapper assetIdWrapper : listOfAssetIdWrapper) {
				
				if(assetIdWrapper.isConsumed())
				{
					UnlockAssetRequest unlockAssetRequest = new UnlockAssetRequest();
					Library library = new Library();
					library.setLibraryName(getLibraryNameFromId(key));
					AssetKey assetKey = new AssetKey();
					assetKey.setAssetId(assetIdWrapper.getAssetId());
					assetKey.setLibrary(library);
					unlockAssetRequest.setAssetKey(assetKey);
					
					UnlockAssetResponse unlockAssetResp = null;
					
					try {
						unlockAssetResp = rsConsumer.unlockAsset(unlockAssetRequest);
						if(!unlockAssetResp.getAck().value().equals(AssetCreationConstants.SUCCESS))
						{
							s_logger.error("Following errors occured while unlocking the asset: "+assetIdWrapper.getAssetId());
							List<CommonErrorData> error = unlockAssetResp.getErrorMessage().getError();
							for (CommonErrorData errorData : error) {
								s_logger.error("ErrorId: "+errorData.getErrorId());
								s_logger.error("Error Message: "+ errorData.getMessage());
							}
							continue;
						}
					} catch (ServiceException e) {
						s_logger.error(e.getMessage(),e);
						throw new ProcessingException(e.getMessage(),e);
					}
					
					String isApprovalRequired = getApprovalRequiredStatus(unlockAssetResp.getAssetInfo());
					
					if(isApprovalRequired==null || isApprovalRequired.equals(AssetCreationConstants.FALSE))
					{
						UpdateAssetAttributesRequest updateAssetAttrReq = new UpdateAssetAttributesRequest();
						updateAssetAttrReq.setAssetKey(assetKey);
						updateAssetAttrReq.setExtendedAssetInfo(extendedAssetInfo);
						updateAssetAttrReq.setReplaceCurrent(true);
						updateAssetAttrReq.setPartialUpdate(true);
						try {
							UpdateAssetAttributesResponse updateAssetAttrRes = rsConsumer.updateAssetAttributes(updateAssetAttrReq);
							if(!updateAssetAttrRes.getAck().value().equals(AssetCreationConstants.SUCCESS))
							{
								s_logger.error("Following errors occured while updating the asset for isApprovedClassifier: "+assetIdWrapper.getAssetId());
								List<CommonErrorData> error = updateAssetAttrRes.getErrorMessage().getError();
								for (CommonErrorData errorData : error) {
									s_logger.error("ErrorId: "+errorData.getErrorId());
									s_logger.error("Error Message: "+ errorData.getMessage());
								}
								continue;
							}
						} catch (ServiceException e) {
							s_logger.error(e.getMessage(),e);
							throw new ProcessingException(e.getMessage(),e);
						}
					}
					
					LockAssetRequest lockAssetReq = new LockAssetRequest();
					lockAssetReq.setAssetKey(assetKey);
					try {
						LockAssetResponse lockAssetResp = rsConsumer.lockAsset(lockAssetReq);
						if(!lockAssetResp.getAck().value().equals(AssetCreationConstants.SUCCESS))
						{
							s_logger.error("Following errors occured while locking the asset under logidex_adm  : "+assetIdWrapper.getAssetId());
							List<CommonErrorData> error = lockAssetResp.getErrorMessage().getError();
							for (CommonErrorData errorData : error) {
								s_logger.error("ErrorId: "+errorData.getErrorId());
								s_logger.error("Error Message: "+ errorData.getMessage());
							}
							continue;
						}
					} catch (ServiceException e) {
						s_logger.error(e.getMessage(),e);
						throw new ProcessingException(e.getMessage(),e);
					}
					
					RemoveAssetRequest removeAssetReq = new RemoveAssetRequest();
					removeAssetReq.setAssetKey(assetKey);
					
					try {
						RemoveAssetResponse removeAssetResp = rsConsumer.removeAsset(removeAssetReq);
						if(!removeAssetResp.getAck().value().equals(AssetCreationConstants.SUCCESS))
						{
							s_logger.error("Following errors occured while deleting the asset: "+assetIdWrapper.getAssetId());
							List<CommonErrorData> error = removeAssetResp.getErrorMessage().getError();
							for (CommonErrorData errorData : error) {
								s_logger.error("ErrorId: "+errorData.getErrorId());
								s_logger.error("Error Message: "+ errorData.getMessage());
							}
							continue;
						}
					} catch (ServiceException e) {
						s_logger.error(e.getMessage(),e);
						throw new ProcessingException(e.getMessage(),e);
					}
					
				}
				
			}
		}
		
		Map<String, List<AssetInfoWrapper>> assetInfoMap = assetStore.getAssetInfos();
		keySet = assetInfoMap.keySet();
		
			for (String key : keySet) {
				
				if(!isDeleteFlagOn(assetCreation,key))
					continue;
			
				List<AssetInfoWrapper> listOfAssetInfoWrapper = assetInfoMap.get(key);
			
				for (AssetInfoWrapper assetInfoWrapper : listOfAssetInfoWrapper) {
					
					if(assetInfoWrapper.isConsumed())
					{
						UnlockAssetRequest unlockAssetRequest = new UnlockAssetRequest();
						Library library = new Library();
						library.setLibraryName(getLibraryNameFromId(key));
						AssetKey assetKey = new AssetKey();
						assetKey.setAssetId(assetInfoWrapper.getAssetInfo().getBasicAssetInfo().getAssetKey().getAssetId());
						assetKey.setLibrary(library);
						unlockAssetRequest.setAssetKey(assetKey);
						
						UnlockAssetResponse unlockAssetResp = null;
						
						try {
							unlockAssetResp = rsConsumer.unlockAsset(unlockAssetRequest);
							if(!unlockAssetResp.getAck().value().equals(AssetCreationConstants.SUCCESS))
							{
								s_logger.error("Following errors occured while unlocking the asset: "+assetInfoWrapper.
										getAssetInfo().getBasicAssetInfo().getAssetKey().getAssetId());
								List<CommonErrorData> error = unlockAssetResp.getErrorMessage().getError();
								for (CommonErrorData errorData : error) {
									s_logger.error("ErrorId: "+errorData.getErrorId());
									s_logger.error("Error Message: "+ errorData.getMessage());
								}
								continue;
							}
						} catch (ServiceException e) {
							s_logger.error(e.getMessage(),e);
							throw new ProcessingException(e.getMessage(),e);
						}
						
						String isApprovalRequired = getApprovalRequiredStatus(unlockAssetResp.getAssetInfo());
						
						if(isApprovalRequired==null || isApprovalRequired.equals(AssetCreationConstants.FALSE))
						{
							UpdateAssetAttributesRequest updateAssetAttrReq = new UpdateAssetAttributesRequest();
							updateAssetAttrReq.setAssetKey(assetKey);
							updateAssetAttrReq.setExtendedAssetInfo(extendedAssetInfo);
							updateAssetAttrReq.setReplaceCurrent(true);
							updateAssetAttrReq.setPartialUpdate(true);
							try {
								UpdateAssetAttributesResponse updateAssetAttrRes = rsConsumer.updateAssetAttributes(updateAssetAttrReq);
								if(!updateAssetAttrRes.getAck().value().equals(AssetCreationConstants.SUCCESS))
								{
									s_logger.error("Following errors occured while updating the asset for isApprovedClassifier: "+assetInfoWrapper.
											getAssetInfo().getBasicAssetInfo().getAssetKey().getAssetId());
									List<CommonErrorData> error = updateAssetAttrRes.getErrorMessage().getError();
									for (CommonErrorData errorData : error) {
										s_logger.error("ErrorId: "+errorData.getErrorId());
										s_logger.error("Error Message: "+ errorData.getMessage());
									}
									continue;
								}
							} catch (ServiceException e) {
								s_logger.error(e.getMessage(),e);
								throw new ProcessingException(e.getMessage(),e);
							}
						}
						
						LockAssetRequest lockAssetReq = new LockAssetRequest();
						lockAssetReq.setAssetKey(assetKey);
						try {
							LockAssetResponse lockAssetResp = rsConsumer.lockAsset(lockAssetReq);
							if(!lockAssetResp.getAck().value().equals(AssetCreationConstants.SUCCESS))
							{
								s_logger.error("Following errors occured while locking the asset under logidex_adm  : "+assetInfoWrapper.
										getAssetInfo().getBasicAssetInfo().getAssetKey().getAssetId());
								List<CommonErrorData> error = lockAssetResp.getErrorMessage().getError();
								for (CommonErrorData errorData : error) {
									s_logger.error("ErrorId: "+errorData.getErrorId());
									s_logger.error("Error Message: "+ errorData.getMessage());
								}
								continue;
							}
						} catch (ServiceException e) {
							s_logger.error(e.getMessage(),e);
							throw new ProcessingException(e.getMessage(),e);
						}
						
						RemoveAssetRequest removeAssetReq = new RemoveAssetRequest();
						removeAssetReq.setAssetKey(assetKey);
						
						try {
							RemoveAssetResponse removeAssetResp = rsConsumer.removeAsset(removeAssetReq);
							if(!removeAssetResp.getAck().value().equals(AssetCreationConstants.SUCCESS))
							{
								s_logger.error("Following errors occured while deleting the asset: "+assetInfoWrapper.
										getAssetInfo().getBasicAssetInfo().getAssetKey().getAssetId());
								List<CommonErrorData> error = removeAssetResp.getErrorMessage().getError();
								for (CommonErrorData errorData : error) {
									s_logger.error("ErrorId: "+errorData.getErrorId());
									s_logger.error("Error Message: "+ errorData.getMessage());
								}
								continue;
							}
						} catch (ServiceException e) {
							s_logger.error(e.getMessage(),e);
							throw new ProcessingException(e.getMessage(),e);
						}
						
					}
					
				}
		}
		
	}
	
	private String getLibraryNameFromId(String key) throws ProcessingException
	{
		AssetCreation assetCreation = null;
		
		try {
			assetCreation = ReadConfigFile.readConfig(inputFilePath);
		} catch (FileNotFoundException e) {
			s_logger.error(e.getMessage(), e);
			throw new ProcessingException(e.getMessage(),e);
		} catch (JAXBException e) {
			s_logger.error(e.getMessage(), e);
			throw new ProcessingException(e.getMessage(),e);
		}
		
		List<AssetInput> assetInputs = assetCreation.getAssetInput();
		
		for (AssetInput assetInput : assetInputs) {
			
			if(assetInput.getId().equals(key))
				return assetInput.getLibraryName();
		}
		
		return null;
	}
	
	private String getApprovalRequiredStatus(AssetInfo assetInfo)
	{
		List<AttributeNameValue> attribute = assetInfo.getExtendedAssetInfo().getAttribute();
		for (AttributeNameValue attributeNameValue : attribute) {
			if(attributeNameValue.getAttributeName().equals(AssetCreationConstants.IS_APPROVAL_REQUIRED))
			{
				return attributeNameValue.getAttributeValueString();
			}
		}
		return null;
	}
	
	private boolean isDeleteFlagOn(AssetCreation assetCreation, String key)
	{
		List<AssetInput> assetInputs = assetCreation.getAssetInput();
		
		for (AssetInput assetInput : assetInputs) {
			if(assetInput.getId().equals(key))
			{
				if(assetInput.isDeleteConsumedAssets()==null)
					return false;
				else
					return assetInput.isDeleteConsumedAssets();
			}
		}
		return false;
	}
	
}
