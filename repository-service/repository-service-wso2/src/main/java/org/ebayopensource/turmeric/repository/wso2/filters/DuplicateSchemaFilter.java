/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.filters;

import javax.xml.namespace.QName;

import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.schema.SchemaFilter;
import org.wso2.carbon.governance.api.schema.dataobjects.Schema;
import org.ebayopensource.turmeric.repository.v2.services.*;

/**
 * Provides a WSO2 search filter to locate any duplicates.
 * 
 * @author dcarver
 * 
 */
public class DuplicateSchemaFilter implements SchemaFilter {

	private ArtifactInfo artifactInfo = null;

	/**
	 * The DuplicateServiceFilter constructor.
	 * 
	 * @param bi
	 *            The BasicAssetInfo used to check for duplicates.
	 */
	public DuplicateSchemaFilter(ArtifactInfo ai) {
		artifactInfo = ai;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the service is a duplicate.
	 */
	@Override
	public boolean matches(Schema schema) throws GovernanceException {
		QName qname = new QName(artifactInfo.getArtifact().getTargetNamespace(), artifactInfo.getArtifact().getArtifactName());
		if (schema.getQName().equals(qname)) {
			return true;
		}
		return false;
	}

}
