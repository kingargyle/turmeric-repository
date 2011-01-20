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
 * @deprecated use {@link org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationship} instead
 */
@Deprecated
public class FlattenedRelationship extends
		org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationship {

	/**
	 * @deprecated use use {@link org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationship#getRelatedAsset()} instead
	 */
	@Deprecated
	public List<Relation> getRelatedAsset_m() {
		List<Relation> related = new ArrayList<Relation>();
		List<org.ebayopensource.turmeric.repository.v1.services.Relation> related_m = super
				.getRelatedAsset();

		for (org.ebayopensource.turmeric.repository.v1.services.Relation relation : related_m) {
			related.add(Relation.wrap(relation));
		}
		return related;
	}

}
