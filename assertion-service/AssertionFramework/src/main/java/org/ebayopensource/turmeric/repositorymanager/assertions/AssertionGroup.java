/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

import java.util.Collection;


// TODO: Auto-generated Javadoc
/**
 * An AssertionGroup contains a collection of Assertables.
 * Two AssertionGroups with the same name and version are "equal"
 * (group1.equals(group2) is true).
 * 
 * @author pcopeland
 */
public interface AssertionGroup
    extends Assertable
{
    /**
     * Returns the Assertablea in this AssertionGroup.
     * 
     * @return the Assertablea in this AssertionGroup.
     */
    public Collection<? extends Assertable> getGroupMembers();
}
