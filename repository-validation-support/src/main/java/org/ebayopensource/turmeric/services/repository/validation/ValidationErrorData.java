/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repository.validation;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.ErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorSeverity;




/**
 * @deprecated use {@link com.ebay.marketplace.services.ErrorData} instead
 */
@Deprecated
public class ValidationErrorData extends
		ErrorData {

	/**
	 * @deprecated use {@link com.ebay.marketplace.services.ErrorData#getSeverity()} instead
	 */
	@Deprecated
	public AckValue getErrorSeverity() {

		ErrorSeverity sev = super.getSeverity();
		if(sev == ErrorSeverity.ERROR) {
			return AckValue.FAILURE;
		}
		
		if(sev == ErrorSeverity.WARNING) {
			return AckValue.WARNING;
		}

		return AckValue.SUCCESS;
	}

	/**
	 * @deprecated use {@link com.ebay.marketplace.services.ErrorData#getMessage()} instead
	 */
	@Deprecated
	public String getErrorMessage() {
		return super.getMessage();
	}
}
