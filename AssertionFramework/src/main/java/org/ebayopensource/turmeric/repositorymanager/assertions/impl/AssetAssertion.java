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
import java.util.List;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repositorymanager.assertions.Assertion;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionModule;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssetContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference;
import org.ebayopensource.turmeric.repositorymanager.assertions.ValidationResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionIllegalArgumentException;


/**
 * AssetAssertion implements Assertion.  
 * 
 * @author pcopeland
 */
public class AssetAssertion
    extends BasicAssertion
    implements Assertion, AssetReferent
{
    private AssetReference assetRef;
    private boolean isDereferenced = false;

    /**
     * Constructs an AssetAssertion. The object is initially "hollow".
     * The description, assertion script, assertion modules, error severity
     * and processor type are not valid until the Ssset is dereferenced.
     * 
     * @param assetRef the AssetReference for this referent.
     */
    public AssetAssertion(AssetReference assetRef)
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

        // Attributes
        this.description = basicAssetInfo.getAssetDescription();
        List<AttributeNameValue> attributes = assetInfo.getExtendedAssetInfo().getAttribute();
        for (AttributeNameValue attribute: attributes) {
            String attributeName = attribute.getAttributeName();
            if (attributeName.equals("assertion-processor"))
                this.assertionProcessorType = attribute.getAttributeValueString();
            else if (attributeName.equals("assertion-error-severity"))
                this.errorSeverity = Enum.valueOf(
                        ValidationResult.class, attribute.getAttributeValueString().toUpperCase());
        }

        // Script artifact
        List<ArtifactInfo> artifacts = assetInfo.getArtifactInfo();
        if (artifacts.size() == 1) {
            String artifactName = artifacts.get(0).getArtifact().getArtifactCategory();
            this.assertionScript = new AssetContent(artifactName, assetRef);
        } else
            throw new IllegalStateException(
                    "Assertion asset can only have one artifact: "+this);

        // Modules
        assertionModules = new ArrayList<AssertionModule>();
        if(assetInfo != null && assetInfo.getFlattenedRelationship() != null){
	        for (Relation relation : assetInfo.getFlattenedRelationship().getRelatedAsset()) {
	            String relationship = relation.getAssetRelationship();
	            if (relationship.equals("assertion-module")) {
	                AssetKey moduleKey = relation.getTargetAsset();
	                assertionModules.add(context.getAssertionModule(moduleKey));
	            }
	        }
        }
        context.addAssertion(assetKey, this);
        isDereferenced = true;
    }

	@Override
	public boolean equals(Object o) 
	{
		return super.equals(o);
	}
    
    
}
