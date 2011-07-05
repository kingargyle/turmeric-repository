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
import org.ebayopensource.turmeric.repository.v2.services.*;

public abstract class ModifyAsset {
	
	private static Logger s_logger = Logger.getLogger(ModifyAsset.class);
	
	public void modifyAssetName(AssetInfo assetInfo, AssetInput assetInput)
	{
		String prefix = assetInput.getPrefix();
		String suffix = assetInput.getSuffix();
		String currentTime = Long.toString(new Date().getTime());
		String assetName = prefix+currentTime+suffix;
		assetInfo.getBasicAssetInfo().setAssetName(assetName);
		s_logger.debug("Trying to create new asset with name"+assetName+" for ID "+assetInput.getId());
	}
	
	public void modifyAssetId(AssetInfo assetInfo)
	{
		assetInfo.getBasicAssetInfo().getAssetKey().setAssetId(null);
	}
	
	public void modifyComments(AssetInfo assetInfo, String comments)
	{
		assetInfo.getBasicAssetInfo().setAssetDescription(comments);
	}
	
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
	
	public abstract void modify(AssetInfo assetInfo, AssetInput assetInput) throws ProcessingException;
	

}
