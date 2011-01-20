/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference;

/**
 * As AssetReferent has an AssetReference.
 * 
 * @author pcopeland
 */
public interface AssetReferent
{
    /**
     * Returns the AssetReference for this referent.
     * 
     * @return the AssetReference for this referent.
     */
    public AssetReference getAssetReference();

    /**
     * Initializes the state of this referent and any referents
     * referred to by the referent.
     * 
     * @param context the context for accessing the Repository.
     */
    public void dereference(AssertionProcessorContext context);
}
