/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

// TODO: Auto-generated Javadoc
/**
 * An AssetReference references a Repository Manager Asset.
 * 
 * @author pcopeland
 */
public class AssetReference
{
    
    /** The library. */
    private String library;
    
    /** The asset name. */
    private String assetName;
    
    /** The version. */
    private String version;
    
    /** The asset type. */
    private String assetType;
    
    /** The hash code. */
    private int hashCode;

    /**
     * Constructs an AssetReference.
     *
     * @param library the Repository Manager library.
     * @param assetName the asset name.
     * @param version the asset version.
     * @param assetType the asset type
     */
    public AssetReference(String library, String assetName, String version, String assetType)
    {
        this.library = library;
        this.assetName = assetName;
        this.version = version;
        this.assetType = assetType;
        this.hashCode = (library+"#"+assetName+"#"+version).hashCode();
    }

    /**
     * Returns the Repository Manager library.
     * 
     * @return the Repository Manager library.
     */
    public String getLibrary() { return library; }

    /**
     * Returns the asset name.
     * 
     * @return the asset name.
     */
    public String getAssetName() { return assetName; }

    /**
     * Returns the asset version.
     * 
     * @return the asset version.
     */
    public String getVersion() { return version; }

    /**
     * Gets the asset type.
     *
     * @return the asset type
     */
    public String getAssetType() { return assetType; }

    /**
     * Returns a reference to the asset in the form of Library:AssetName:Version.
     * 
     * @return a reference to the asset in the form of Library:AssetName:Version.
     */
    public String getAssetReference()
    {
        return (library+":"+assetName+":"+version).replace(' ', '_');
    }

    /**
     * Returns a reference to the asset in the form of Library/AssetName/Version.
     * 
     * @return a reference to the asset in the form of Library/AssetName/Version.
     */
    @Override
	public String toString()
    {
        return "AssetReference["+getAssetReference()+"]";
    }

    /**
     * Returns true if the argument is a AssetReference with equal
     * library, name and version.
     *
     * @param o the o
     * @return true if the argument is an AssetReference with equal
     * library, name and version.
     */
    @Override
	public boolean equals(Object o)
    {
        if (o == null || !(o instanceof AssetReference))
            return false;

        AssetReference oaa = (AssetReference)o;
        String oaaLibrary = oaa.getLibrary();
        String oaaName = oaa.getAssetName();
        String oaaVersion = oaa.getVersion();
        return
            ((library == null && oaaLibrary == null)
                    || (library != null && library.equals(oaaLibrary)))
            &&
            ((assetName == null && oaaName == null)
                    || (assetName != null && assetName.equals(oaaName)))
            &&
            ((version == null && oaaVersion == null)
                    || (version != null && version.equals(oaaVersion)));
    }

    /**
     * Returns the hashCode for this AssetReference.
     * 
     * @return the hashCode for this AssetReference.
     */
    @Override
	public int hashCode() { return hashCode; }
}
