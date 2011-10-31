/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.repositorymanager.assertions;

/**
 * An AssetReference references a Repository Manager Asset.
 * 
 * @author pcopeland
 */
public class AssetReference {

   /**
    * The asset ID
    */
   private final String assetId;

   /** The asset name. */
   private final String assetName;

   /** The version. */
   private final String version;

   /** The asset type. */
   private final String assetType;

   /** The hash code. */
   private final int hashCode;

   /**
    * Constructs an AssetReference.
    * 
    * @param id
    *           the Asset Id
    * @param assetName
    *           the asset name.
    * @param version
    *           the asset version.
    * @param assetType
    *           the asset type
    */
   public AssetReference(String id, String assetName, String version, String assetType) {
      this.assetId = id;
      this.assetName = assetName;
      this.version = version;
      this.assetType = assetType;
      this.hashCode = (id + "#" + assetName + "#" + version).hashCode();
   }

   /**
    * Returns the asset id.
    * 
    * @return the asset id.
    */
   public String getAssetId() {
      return this.assetId;
   }

   /**
    * Returns the asset name.
    * 
    * @return the asset name.
    */
   public String getAssetName() {
      return assetName;
   }

   /**
    * Returns the asset version.
    * 
    * @return the asset version.
    */
   public String getVersion() {
      return version;
   }

   /**
    * Gets the asset type.
    * 
    * @return the asset type
    */
   public String getAssetType() {
      return assetType;
   }

   /**
    * Returns a reference to the asset in the form of Library:AssetName:Version.
    * 
    * @return a reference to the asset in the form of Library:AssetName:Version.
    */
   public String getAssetReference() {
      return (assetId + ":" + assetName + ":" + version).replace(' ', '_');
   }

   /**
    * Returns a reference to the asset in the form of Library/AssetName/Version.
    * 
    * @return a reference to the asset in the form of Library/AssetName/Version.
    */
   @Override
   public String toString() {
      return "AssetReference[" + getAssetReference() + "]";
   }

   /**
    * Returns true if the argument is a AssetReference with equal library, name and version.
    * 
    * @param o
    *           the o
    * @return true if the argument is an AssetReference with equal library, name and version.
    */
   @Override
   public boolean equals(Object o) {
      if (o == null || !(o instanceof AssetReference)) {
         return false;
      }

      AssetReference oaa = (AssetReference) o;
      String oaaLibrary = oaa.getAssetId();
      String oaaName = oaa.getAssetName();
      String oaaVersion = oaa.getVersion();
      return ((assetId == null && oaaLibrary == null) || (assetId != null && assetId.equals(oaaLibrary)))
               && ((assetName == null && oaaName == null) || (assetName != null && assetName.equals(oaaName)))
               && ((version == null && oaaVersion == null) || (version != null && version.equals(oaaVersion)));
   }

   /**
    * Returns the hashCode for this AssetReference.
    * 
    * @return the hashCode for this AssetReference.
    */
   @Override
   public int hashCode() {
      return hashCode;
   }
}
