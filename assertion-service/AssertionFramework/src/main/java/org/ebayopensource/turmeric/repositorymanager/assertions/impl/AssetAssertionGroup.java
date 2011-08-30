/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import java.util.ArrayList;

import org.ebayopensource.turmeric.repository.v1.services.*;
import org.ebayopensource.turmeric.repositorymanager.assertions.Assertable;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionIllegalArgumentException;


/**
 * AssetAssertion implements Assertion.  
 * 
 * @author pcopeland
 */
public class AssetAssertionGroup
    extends BasicAssertionGroup
    implements AssetReferent
{
    private AssetReference assetRef;
    private boolean isDereferenced = false;

    /**
     * Constructs an AssetAssertionGroup. The object is initially "hollow".
     * The description and list of Assertables are not valid until
     * the Asset is dereferenced.
     * 
     * @param assetRef the AssetReference for this referent.
     */
    public AssetAssertionGroup(AssetReference assetRef)
    {
        super(assetRef.getAssetName(), assetRef.getVersion());
        this.assetRef = assetRef;
    }

    /**
     * Returns the AssetReference for this AssetAssertion.
     * 
     * @return the AssetReference for this AssetAssertion.
     */
    @Override
	public AssetReference getAssetReference() { return assetRef; }

    /**
     * Initializes the state of this AssetAssertion.
     *
     * @param context the context for accessing the Repository.
     * @throws AssertionIllegalArgumentException the assertion illegal argument exception
     */
    @Override
	public void dereference(AssertionProcessorContext context) throws AssertionIllegalArgumentException
    {
        if (isDereferenced) return;

        // Sanity check
        AssetInfo assetInfo = context.getAssetInfo(this);
        BasicAssetInfo basicAssetInfo = assetInfo.getBasicAssetInfo();
        AssetKey assetKey = basicAssetInfo.getAssetKey();
        if (!getName().equals(basicAssetInfo.getAssetName())
                || !getVersion().equals(basicAssetInfo.getVersion()))
            throw new IllegalStateException(
                    this +" does not match Repository asset ["
                    + basicAssetInfo.getAssetName()
                    + ",version="+ basicAssetInfo.getVersion());

        this.description = basicAssetInfo.getAssetDescription();

        // Group members
        groupMembers = new ArrayList<Assertable>();
        if(assetInfo != null && assetInfo.getFlattenedRelationship() != null){
	        for (Relation relation : assetInfo.getFlattenedRelationship().getRelatedAsset()) {
	            String relationship = relation.getAssetRelationship();
	            if (relationship.equals("assertion-groupmember-assertion")) {
	                AssetKey memberKey = relation.getTargetAsset();
	                groupMembers.add(context.getAssertion(memberKey));
	            } else if (relationship.equals("assertion-groupmember-group")) {
	                AssetKey memberKey = relation.getTargetAsset();
	                groupMembers.add(context.getAssertionGroup(memberKey));
	            }
	        }
        }
        context.addAssertionGroup(assetKey, this);
        isDereferenced = true;
    }

	/* (non-Javadoc)
	 * @see org.ebayopensource.turmeric.repositorymanager.assertions.impl.BasicAssertionGroup#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) 
	{
		return super.equals(o);
	}
    
}
