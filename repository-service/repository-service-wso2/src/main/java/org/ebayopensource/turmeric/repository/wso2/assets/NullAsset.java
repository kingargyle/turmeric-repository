/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repository.wso2.assets;

import java.util.UUID;

import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;

/**
 * A Null Object implementation for an Asset.
 * 
 * @author dcarver
 *
 */
public class NullAsset implements Asset {

	@Override
	public boolean isNamespaceRequired() {
		return false;
	}

	@Override
	public boolean hasNamespace() {
		return false;
	}

	@Override
	public String getType() {
		return "NULL";
	}

	@Override
	public boolean hasName() {
		return false;
	}

	@Override
	public boolean duplicatesAllowed() {
		return false;
	}

	@Override
	public boolean hasDuplicates() {
		return false;
	}

	@Override
	public boolean createAsset() {
		return true;
	}

	@Override
	public boolean addAsset() {
		return true;
	}

	@Override
	public String getId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Always returns null.
	 */
	@Override
	public GovernanceArtifact addArtifact(ArtifactInfo artifact) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Always returns null.
	 */
	@Override
	public GovernanceArtifact getGovernanceArtifact() {
		return null;
	}

}
