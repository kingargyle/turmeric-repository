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
 * @deprecated use {@link org.ebayopensource.turmeric.repository.v1.services.AttributeNameValue} instead
 */
@Deprecated
public class AttributeNameValue extends
		org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue {

	/**
	 * @deprecated
	 */
	@Deprecated
	public static AttributeNameValue wrap(
			org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue anv) {

		AttributeNameValue nv = new AttributeNameValue();

		nv.setAttributeName(anv.getAttributeName());
		nv.setAttributeValueLong(anv.getAttributeValueLong());
		nv.setAttributeValueString(anv.getAttributeValueString());
		nv.setAttributeValueBoolean(anv.isAttributeValueBoolean());

		return nv;
	}

}
