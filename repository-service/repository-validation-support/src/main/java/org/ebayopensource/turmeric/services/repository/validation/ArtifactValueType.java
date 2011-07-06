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
 * @deprecated use {@link org.ebayopensource.turmeric.repository.v2.services.ArtifactValueType} instead
 */
@Deprecated
public enum ArtifactValueType {
	URL("url"), DESCRIPTION("description"), FILE("file");
	private final String value;

	ArtifactValueType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	/**
	 * @deprecated use {@link org.ebayopensource.turmeric.repository.v2.services.ArtifactValueType#fromValue(String)} instead
	 */
	@Deprecated
	public static org.ebayopensource.turmeric.repository.v2.services.ArtifactValueType fromValue(String v) {
		for (org.ebayopensource.turmeric.repository.v2.services.ArtifactValueType c : org.ebayopensource.turmeric.repository.v2.services.ArtifactValueType.values()) {
			if (c.value().equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
