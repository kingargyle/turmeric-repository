/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import org.ebayopensource.turmeric.assetcreation.artifacts.AssetInput;
import org.ebayopensource.turmeric.assetcreation.exception.ProcessingException;
import org.ebayopensource.turmeric.repository.v1.services.*;

/**
 * The Class ModifyAsset.
 */
public abstract class ModifyAsset {
	
	private static Logger s_logger = Logger.getLogger(ModifyAsset.class);
	
	/**
	 * Modify asset name.
	 *
	 * @param assetInfo the asset info
	 * @param assetInput the asset input
	 */
	public void modifyAssetName(AssetInfo assetInfo, AssetInput assetInput)
	{
		String prefix = assetInput.getPrefix();
		String suffix = assetInput.getSuffix();
		String currentTime = Long.toString(new Date().getTime());
		String assetName = prefix+currentTime+suffix;
		assetInfo.getBasicAssetInfo().setAssetName(assetName);
		s_logger.debug("Trying to create new asset with name"+assetName+" for ID "+assetInput.getId());
	}
	
	/**
	 * Modify asset id.
	 *
	 * @param assetInfo the asset info
	 */
	public void modifyAssetId(AssetInfo assetInfo)
	{
		assetInfo.getBasicAssetInfo().getAssetKey().setAssetId(null);
	}
	
	/**
	 * Modify comments.
	 *
	 * @param assetInfo the asset info
	 * @param comments the comments
	 */
	public void modifyComments(AssetInfo assetInfo, String comments)
	{
		assetInfo.getBasicAssetInfo().setAssetDescription(comments);
	}
	
	/**
	 * Sets the classifier.
	 *
	 * @param assetInfo the new classifier
	 */
	public void setClassifier(AssetInfo assetInfo)
	{
		boolean isApprovalRequiredClassifierPresent = false;
		List<AttributeNameValue> attributes = assetInfo.getExtendedAssetInfo().getAttribute();
		for(AttributeNameValue attribute : attributes)
		{
			if(attribute.getAttributeName().equals("isApprovalRequired"))
			{
				attribute.setAttributeValueString("true");
				isApprovalRequiredClassifierPresent = true;
				break;
			}
		}
		
		if(!isApprovalRequiredClassifierPresent)
		{
			AttributeNameValue nVPair = new AttributeNameValue();
			nVPair.setAttributeName("isApprovalRequired");
			nVPair.setAttributeValueString("true");
			assetInfo.getExtendedAssetInfo().getAttribute().add(nVPair);
		}
	}
	
	/**
	 * Modify.
	 *
	 * @param assetInfo the asset info
	 * @param assetInput the asset input
	 * @throws ProcessingException the processing exception
	 */
	public abstract void modify(AssetInfo assetInfo, AssetInput assetInput) throws ProcessingException;
	

}
