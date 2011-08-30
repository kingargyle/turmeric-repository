/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import org.apache.log4j.Logger;

import org.ebayopensource.turmeric.repository.v1.services.*;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssetContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionIllegalArgumentException;


/**
 * The Class AssetContentSource.
 *
 * @author pcopeland
 */
public class AssetContentSource
    extends AbstractContentSource
    implements AssetReferent
{
    private AssetContent assetContent;
    private AssertionProcessorContext context;
    private byte[] content;
    private ByteArrayInputStream inputStream = null;
    static private final Logger logger = Logger.getLogger(AssetContentSource.class); 

    /**
     * Constructs an AssertionContentSource for an AssetContent.
     *
     * @param assetContent the underlying AssertionContent.
     * @param context the context
     */
    public AssetContentSource(
            AssetContent assetContent,
            AssertionProcessorContext context)
    {
        super(assetContent);
        this.assetContent = assetContent;
        this.context = context;
    }

    /**
     * Returns an InputStream for this AssertionContent or null
     * if the content should be read with a character Reader.
     *
     * @return an InputStream for this AssertionContent.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
	public InputStream getContentStream()
        throws IOException
    {
        if (inputStream == null) {
            dereference(context);
            if (content == null)
                throw new IllegalStateException(
                        "Could not find artifact content for "+ this);
            inputStream = new ByteArrayInputStream(content);
        }
        inputStream.reset(); // ByteArrayInputStream can be reused after closed
        return inputStream;
    }

    /**
     * Returns a character stream reader for this AssertionContent or null
     * if the content is not character data.
     *
     * @return a character stream reader for this AssertionContent or null.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
	public Reader getContentReader()
        throws IOException
    {
        return null;
    }

    /**
     * Returns the AssetReference for this referent.
     * 
     * @return the AssetReference for this referent.
     */
    @Override
	public AssetReference getAssetReference()
    {
        return assetContent.getAssetReference();
    }

    /**
     * Initializes the content of this AssetContentSource.
     *
     * @param ctx the ctx
     * @throws AssertionIllegalArgumentException the assertion illegal argument exception
     */
    @Override
	public void dereference(AssertionProcessorContext ctx) throws AssertionIllegalArgumentException
    {
    	logger.debug("Entered dereference method of AssetContentSource");
        if (ctx != context)
        {
        	logger.error("Attempt to access content from another context");
            throw new IllegalStateException("Attempt to access content from another context");
        }
        AssetInfo assetInfo = context.getAssetInfo(this);
        String artifactName = assetContent.getArtifactName();
        List<ArtifactInfo> artifacts = assetInfo.getArtifactInfo();
        for (ArtifactInfo artifactInfo: artifacts) {
            Artifact artifact = artifactInfo.getArtifact();
            if(!assetInfo.getBasicAssetInfo().getAssetType().equals("AssertionModule"))
            {            	
            	if (artifact!= null && artifact.getArtifactCategory()!= null && artifactName.equalsIgnoreCase(artifact.getArtifactCategory()))
            	{            		
            		content = artifactInfo.getArtifactDetail();
            		assetContent.setArtifactDisplayName(artifactInfo.getArtifact().getArtifactName());
            	}
        		if(content == null)
        		{
        			if(artifact == null)
        			{
        				logger.error("CASE1: Content is NULL for asset '" + assetInfo.getBasicAssetInfo().getAssetName() + "' as the artifact object is null");
        			}
        			else if(artifact.getArtifactCategory()== null)
        			{
        				logger.error("CASE1: Content is NULL for asset '" + assetInfo.getBasicAssetInfo().getAssetName() + "' as the artifact category is null");
        			}
        			else if(!artifactName.equalsIgnoreCase(artifact.getArtifactCategory()))
        			{
        				logger.error("CASE1: Content is NULL for asset '" + assetInfo.getBasicAssetInfo().getAssetName() + "' as artifact name " + artifactName + " != " + artifact.getArtifactCategory());
        			}
        			else
        			{
        				logger.error("CASE1: Content is NULL for asset '" + assetInfo.getBasicAssetInfo().getAssetName() + "' artifact " + artifactInfo.getArtifact().getArtifactName() + " with artifact detail: " + artifactInfo.getArtifactDetail() + " *********");
        			}
        		}
            }
            else
            {
            	if (artifactName.equals(artifact.getArtifactName()))
            	{
            		content = artifactInfo.getArtifactDetail();
            		assetContent.setArtifactDisplayName(artifactInfo.getArtifact().getArtifactName());            		
            	}
        		if(content == null)
        		{
        			if(!artifactName.equals(artifact.getArtifactName()))
        			{
        				logger.error("CASE2: Content is NULL for asset '" + assetInfo.getBasicAssetInfo().getAssetName() + "' as artifact name " + artifactName + " != " + artifact.getArtifactName());
        			}
        			else
        			{
        				logger.error("CASE2: Content is NULL for asset '" + assetInfo.getBasicAssetInfo().getAssetName() + "' artifact " + artifactInfo.getArtifact().getArtifactName() + " with artifact detail: " + artifactInfo.getArtifactDetail() + " *********");
        			}
        		}
            }
        }
    }
}
