/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;

public interface Asset {

   /**
    * Does the asset require a namespace.
    * 
    * @return true or false
    */
   public boolean isNamespaceRequired();

   /**
    * Does the asset have a namespace;
    * 
    * @return true or false;
    */
   public boolean hasNamespace();

   /**
    * The type of Asset that has been created.
    * 
    * @return The asset Type.
    */
   public String getType();

   /**
    * Does the asset have a name?
    * 
    * @return true or false
    */
   public boolean hasName();

   /**
    * Whether duplicates are allowed or now. This is typically used to check to see if an asset needs to call
    * hasDuplicates to check for a duplicate asset in a repository.
    * 
    * @return
    */
   public boolean duplicatesAllowed();

   /**
    * Checks the Registry implementation to search for duplicates for this Asset. How this is done depends on the Asset
    * that is being searched.
    * 
    * @return true or false
    */
   public boolean hasDuplicates();

   /**
    * This creates an asset. This does not add it to the Registry.
    * 
    * @return true or false.
    */
   public boolean createAsset();

   /**
    * Add asset. Adds an asset to the Registry.
    * 
    * @return true or false.
    */
   public boolean addAsset();

   /**
    * Returns the asset id. This can be null if the asset id has not be created yet.
    * 
    * @return
    */
   public String getId();

   /**
    * Given a Repository service artifact, add a relationship to asset and the artifact.
    * 
    * @param artifact
    *           A repository service artifact
    * @return The GovernenanceArtifact that was added
    */
   public GovernanceArtifact addArtifact(ArtifactInfo artifact);

   public GovernanceArtifact getGovernanceArtifact();

   /**
    * Whether the asset exists or not in the Registry
    * 
    * @return true or false;
    */
   public boolean exists();

   /**
    * This method finds the asset within the registry.
    * 
    * It is up to the individual asset to know how best to find itself, adopters should call exists to make sure the
    * assset exists in the registry. After finding the asset, a GovernanceArtifact can be returned.
    */
   public void findAsset();

   /**
    * Lock the asset. The asset should be saved for this to take affect.
    */
   public void lockAsset();

   /**
    * Unlock the asset. The asset should be saved for this to take affect.
    */

   public void unlock();

   /**
    * Is the asset Locked.
    * 
    * @return true or false.
    */
   public boolean isLocked();

   /**
    * Saves the service after it has been added. This is equivalant to an update.
    * 
    * @return true or false if it was saved successfully.
    */
   public boolean save();

}
