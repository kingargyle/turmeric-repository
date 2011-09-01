/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

public interface Asset {

	/**
	 * Does the asset require a namespace.
	 * 
	 * @return true or false
	 */
	public boolean isNamespaceRequired();
	
	/**
	 * Does the asset have a namespace;
	 * @return true or false;
	 */
	public boolean hasNamespace();
	
	/**
	 * The type of Asset that has been created.
	 * @return The asset Type.
	 */
	public String getType();
	
	/**
	 * Does the asset have a name?
	 * @return true or false
	 */
	public boolean hasName();

	/**
	 * Whether duplicates are allowed or now.  This is typically used
	 * to check to see if an asset needs to call hasDuplicates to check
	 * for a duplicate asset in a repository.
	 * @return
	 */
	public boolean duplicatesAllowed();
	
	/**
	 * Checks the Registry implementation to search for duplicates for this Asset.
	 * How this is done depends on the Asset that is being searched.
	 * @return true or false
	 */
	public boolean hasDuplicates();
	
	/**
	 * This creates an asset.  This does not add it to the Registry.
	 * @return true or false.
	 */
	public boolean createAsset();
	
	/**
	 * Add asset.  Adds an asset to the Registry.
	 * @return true or false.
	 */
	public boolean addAsset();
	
	/**
	 * Returns the asset id.  This can be null if the asset id has not be created yet.
	 * @return
	 */
	public String getId();
		
	
	
}
