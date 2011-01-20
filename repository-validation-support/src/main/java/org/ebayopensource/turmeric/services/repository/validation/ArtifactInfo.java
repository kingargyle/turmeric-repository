/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repository.validation;

/**
 * @deprecated use {@link org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo} instead
 */
@Deprecated
public class ArtifactInfo extends org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo {

	/**
	 * @deprecated
	 */
	@Deprecated
	protected static ArtifactInfo wrap(
			org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo m) {
		ArtifactInfo info = new ArtifactInfo();

		info.setArtifact(m.getArtifact());
		info.setArtifactDetail(m.getArtifactDetail());
		info.setContentType(m.getContentType());

		return info;
	}

}
