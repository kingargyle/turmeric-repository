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
 * @deprecated use {@link org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo} instead
 */
@Deprecated
public class ExtendedAssetInfo extends
		org.ebayopensource.turmeric.repository.v2.services.ExtendedAssetInfo {

	/**
	 * @deprecated use {@link org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo#getAttribute()} instead
	 */
	@Deprecated
	public List<AttributeNameValue> getAttribute_m() {
		List<AttributeNameValue> attribs = new ArrayList<AttributeNameValue>();
		List<org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue> attribs_m = super
				.getAttribute();

		for (org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue anv : attribs_m) {
			attribs.add(AttributeNameValue.wrap(anv));
		}

		return attribs;
	}

}
