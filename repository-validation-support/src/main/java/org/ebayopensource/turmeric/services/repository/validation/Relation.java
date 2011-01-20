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
 * @deprecated use {@link org.ebayopensource.turmeric.repository.v1.services.Relation} instead
 */
@Deprecated
public class Relation extends org.ebayopensource.turmeric.repository.v1.services.Relation {

	/**
	 * @deprecated
	 */
	@Deprecated
	protected static Relation wrap(org.ebayopensource.turmeric.repository.v1.services.Relation m) {
		Relation relation = new Relation();

		relation.setAssetRelationship(m.getAssetRelationship());
		relation.setSourceAsset(m.getSourceAsset());
		relation.setTargetAsset(m.getTargetAsset());

		return relation;
	}

}
