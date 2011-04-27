/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.init;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import org.ebayopensource.turmeric.cachingframework.exception.AssetRetrivalException;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssertionProcessorContext;
import org.ebayopensource.turmeric.services.assertionsservice.common.AssertionsServiceConstants;

public class CacheManagerInitializer {
	private static final Logger logger = Logger
			.getLogger(CacheManagerInitializer.class);
	static int count = 0;

	/**
	 * Initializes the CacheManager, by reseting the cache and repopulating the
	 * cache with assertion specific assets
	 */
	public void initializeCacheManager() {
		String password = getPropertyValue(
				AssertionsServiceConstants.EBAY_RM_FILE_LOCATION,
				AssertionsServiceConstants.LOGIDEX_ADMIN_PASSWORD);
		String userId = getPropertyValue(
				AssertionsServiceConstants.EBAY_RM_FILE_LOCATION,
				AssertionsServiceConstants.LOGIDEEX_ADMIN_USERNAME);
		String libraryName = getPropertyValue(
				AssertionsServiceConstants.EBAY_SERVICES_FILE_LOCATION,
				"assertion_library_name"); // "SystemAssets";
		String assetName = getPropertyValue(
				AssertionsServiceConstants.EBAY_SERVICES_FILE_LOCATION,
				"wsdl_assertion_group");
		String assetVersion = getPropertyValue(
				AssertionsServiceConstants.EBAY_SERVICES_FILE_LOCATION,
				"wsdl_assertion_group_version");
		String assetType = "AssertionGroup";
		String repositoryServiceUrl = getPropertyValue(
				AssertionsServiceConstants.EBAY_SERVICES_FILE_LOCATION,
				"repositoryServiceUrl");
		Date time1 = null;
		time1 = new Date();
		logger.info("BEGIN CacheManagerInitializer.initializeCacheManager() *************************************"
				+ time1.toString() + " (" + time1.getTime() + ")");
		logger.info("userId: " + userId + " password: " + password
				+ " libraryName: " + libraryName + " assetName: " + assetName
				+ " assetVersion" + assetVersion + " repositoryServiceUrl: "
				+ repositoryServiceUrl);
		AssertionProcessorContext context = new AssertionProcessorContext(
				userId, password, repositoryServiceUrl);
		// context.resetCacheManager();
		try {
			context.removeAssetFromCache(libraryName, assetName, assetVersion);
			logger.info("context: " + context);
			AssetInfo assetInfo = (AssetInfo) context.getCacheManager()
					.getAsset(libraryName, assetName, assetVersion, assetType);
			logger.info("assetInfo: " + assetInfo);
			if (assetInfo != null
					&& assetInfo.getFlattenedRelationship() != null) {
				List<Relation> relations = assetInfo.getFlattenedRelationship()
						.getRelatedAsset();
				for (Relation relation : relations) {
					logger.info(count + ": "
							+ assetInfo.getBasicAssetInfo().getAssetName()
							+ "*********************");
					context.removeAssetFromCache(relation.getTargetAsset()
							.getLibrary().getLibraryName(), relation
							.getTargetAsset().getAssetId());
					getAssetInfo(context, relation.getTargetAsset()
							.getLibrary().getLibraryName(), relation
							.getTargetAsset().getAssetId());
				}

				count = 0;
			}
		} catch (AssetRetrivalException e) {
			logger.error("Unable to initialize the cache Mangaer due to the following reasons: "
					+ e.getMessage());
		}
		logger.info("END CacheManagerInitializer.initializeCacheManager() *************************************"
				+ time1.toString() + " (" + time1.getTime() + ")");
	}

	/**
	 * gets the AssetInfo oject using cacheManager, either from the cache or
	 * from RM if the asset is not present in the cache
	 * 
	 * @param context
	 *            --> processor context
	 * @param libraryName
	 *            --> library name to which the asset belongs to
	 * @param assetId
	 *            --> RM Asset id for the corresponding asset
	 */
	private void getAssetInfo(AssertionProcessorContext context,
			String libraryName, String assetId) {
		AssetInfo assetInfo = null;
		List<Relation> relations = null;

		try {
			assetInfo = (AssetInfo) context.getCacheManager().getAsset(
					libraryName, assetId);
			logger.info(count + ": "
					+ assetInfo.getBasicAssetInfo().getAssetName()
					+ "*********************");
		} catch (AssetRetrivalException e) {
			logger.error("Unable to initialize the cache Mangaer due to the following reasons: "
					+ e.getMessage());
		}
		if (assetInfo != null && assetInfo.getFlattenedRelationship() != null) {
			relations = assetInfo.getFlattenedRelationship().getRelatedAsset();
		}
		if (relations != null) {
			for (Relation relation : relations) {
				context.removeAssetFromCache(relation.getTargetAsset()
						.getLibrary().getLibraryName(), relation
						.getTargetAsset().getAssetId());
				getAssetInfo(context, relation.getTargetAsset().getLibrary()
						.getLibraryName(), relation.getTargetAsset()
						.getAssetId());
			}
		}
	}

	/**
	 * Fetches a property value from a file
	 * 
	 * @param fileLocation
	 *            --> location of the property file
	 * @param propName
	 *            --> Key of the property whose value is to be fetched
	 * @return
	 */
	private String getPropertyValue(String fileLocation, String propName) {
		java.util.Properties props = new java.util.Properties();
		try {
			File file = new File(fileLocation);
			FileInputStream fi = new FileInputStream(file);
			props.load(fi);
		} catch (Exception e) {
			return null;
		}
		return props.getProperty(propName);
	}

	public static void main(String[] args) {
		CacheManagerInitializer cacheManagerInitializer = new CacheManagerInitializer();
		cacheManagerInitializer.initializeCacheManager();
	}
}
