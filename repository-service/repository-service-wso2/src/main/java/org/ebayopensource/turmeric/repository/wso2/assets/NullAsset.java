package org.ebayopensource.turmeric.repository.wso2.assets;

import java.util.UUID;

import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;

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
