/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repository.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated use {@link org.ebayopensource.turmeric.repository.v1.services.AssetInfo} instead
 */
@Deprecated
public class AssetInfo extends org.ebayopensource.turmeric.repository.v1.services.AssetInfo {
	/**
	 * @deprecated use
	 *             {@link org.ebayopensource.turmeric.repository.v1.services.AssetInfo#setExtendedAssetInfo(org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo)}
	 *             instead
	 */
	@Deprecated
	public void setExtendedAssetInfo(ExtendedAssetInfo info) {
		super.setExtendedAssetInfo(info);
	}

	/**
	 * @deprecated use {@link org.ebayopensource.turmeric.repository.v1.services.AssetInfo#getArtifactInfo()} instead
	 */
	public List<ArtifactInfo> getArtifactInfo_m() {
		List<ArtifactInfo> info = new ArrayList<ArtifactInfo>();
		List<org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo> info_m = super
				.getArtifactInfo();

		for (org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo i : info_m) {
			info.add(ArtifactInfo.wrap(i));
		}

		return info;
	}
}
