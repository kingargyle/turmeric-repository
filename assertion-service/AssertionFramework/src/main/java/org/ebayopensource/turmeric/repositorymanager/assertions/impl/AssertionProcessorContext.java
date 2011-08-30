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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.ebayopensource.turmeric.cachingframework.assetcapsule.AssetInfoCapsule;
import org.ebayopensource.turmeric.cachingframework.cacheManager.CacheManager;
import org.ebayopensource.turmeric.cachingframework.exception.AssetRetrivalException;
import org.ebayopensource.turmeric.repository.v1.services.*;
import org.ebayopensource.turmeric.repositorymanager.assertions.Assertable;
import org.ebayopensource.turmeric.repositorymanager.assertions.Assertion;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionGroup;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionModule;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssetContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference;
import org.ebayopensource.turmeric.repositorymanager.assertions.BinaryContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.StringContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.URLContent;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionIllegalArgumentException;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;



/**
 * AssertionProcessorContext manages content for one or more
 * AssertionProcessors.
 * <p>
 * The context cache consolidates multiple references to the same content and
 * allows different AssertionProcessors to share the same content. For instance
 * the same Assertion may be in multiple groups. In that case the content source
 * for the assertion script is shared by those groups. Also many assertions may
 * reference the same AssertionModules. The module script contents need to be
 * retrieved only once.
 * <p>
 * The context optimizes access to repository Assets. The artifact contents of
 * an Asset are retrieved once the first time an artifact for the Asset.
 * 
 * @author pcopeland
 */
public class AssertionProcessorContext {
	// private RepositoryServiceClient rsClient;
	private static final Logger logger = Logger
			.getLogger(AssertionProcessorContext.class);
	private int uniqueId = 0; // used for keys to String and byte content

	/*
	 * The cache objects aim to duplicate trips to the Repository for the same
	 * Asset. The assumption is that Assertions and Module scripts are
	 * relatively small. Input content that assertions are applied against might
	 * be large but there will be only a a few in each call to the service
	 * (usually only one).
	 * 
	 * contentCache holds artifact content as well as assertion scripts and
	 * module scripts. Input content is dereferenced by AssetContentSource.
	 * Modules are usually dereferenced when their containing Assertion is
	 * loaded (lookup in assetIdCache).
	 * 
	 * Assertions and AssertionGroups are input as AssetReferences (lookup in
	 * assetRefCache) or can be found an members of AssertionGroups (lookup in
	 * assetIdCache).
	 */
	private Map<String, AssertionContentSource> contentCache = new HashMap<String, AssertionContentSource>();
	private Map<AssetReference, AssetInfo> assetRefCache = new HashMap<AssetReference, AssetInfo>();
	private Map<String, AssetInfo> assetIdCache = new HashMap<String, AssetInfo>();
	private Map<String, AssertionModule> moduleCache = new HashMap<String, AssertionModule>();
	private Map<String, Assertable> assertableCache = new HashMap<String, Assertable>();

	public static String CAPSULE_NAME = "AssetInfoCapsule";
	private static CacheManager s_cacheManager = null;

	/**
	 * Constructs a AssertionProcessorContext without credentials for the
	 * RepositoryService.
	 */
	public AssertionProcessorContext() {
	}

	/**
	 * Constructs a AssertionProcessorContext with credentials for the
	 * RepositoryService.
	 * 
	 * @param userid
	 *            userid for the RepositoryService.
	 * @param password
	 *            password for the RepositoryService.
	 * @parm serviceLocation URL for the RepositoryService.
	 */
	public AssertionProcessorContext(String userid, String password,
			String serviceLocation) {
		logger.debug("AssertionProcessorContext: userid=" + userid);
		logger.debug("AssertionProcessorContext: password=" + password);
		logger.debug("AssertionProcessorContext: serviceLocation="
				+ serviceLocation);
		logger.debug("AssertionProcessorContext 3 param constructor: s_cacheManager ="
				+ s_cacheManager);
		Properties properties = new Properties();

		if (serviceLocation != null) {
			properties.setProperty(AssetInfoCapsule.RS_LOCATION,
					serviceLocation);
		}
		if (userid != null) {
			properties.setProperty(AssetInfoCapsule.USER_ID, userid);
		}
		if (password != null) {
			properties.setProperty(AssetInfoCapsule.PASSWORD, password);
		}

		logger.debug("[JEGAN] AssertionFramework AssertionProcessorContext constructor username: "
				+ userid + " password: " + password);
		if (s_cacheManager == null) {
			try {
				logger.debug("AssertionProcessorContext: instanciating  s_cacheManager ");
				s_cacheManager = new CacheManager(properties, CAPSULE_NAME);
				logger.debug("AssertionProcessorContext: instanciated s_cacheManager="
						+ s_cacheManager);
			} catch (IllegalAccessException illegalAccessException) {
				throw new RuntimeException(
						"Could not create cacheManager due to the following reasons: "
								+ illegalAccessException.getMessage(),
						illegalAccessException);
			} catch (InstantiationException instantiationException) {
				throw new RuntimeException(
						"Could not create cacheManager due to the following reasons: "
								+ instantiationException.getMessage(),
						instantiationException);
			} catch (ClassNotFoundException classNotFoundException) {
				throw new RuntimeException(
						"Could not create cacheManager due to the following reasons: "
								+ classNotFoundException.getMessage(),
						classNotFoundException);
			} catch (ServiceException serviceException) {
				throw new RuntimeException(
						"Could not create cacheManager due to the following reasons: "
								+ serviceException.getMessage(),
						serviceException);
			}
		} else {
			s_cacheManager.setProperties(properties);
		}
		/*
		 * try { rsClient = new RepositoryServiceClient(userid, password,
		 * serviceLocation); } catch (ServiceException se) { throw new
		 * RuntimeException("Could not construct RepositoryServiceClient", se);
		 * }
		 */
	}

	/**
	 * Returns an AssertionContentSource for a given AssertionContent. The
	 * returned AssertionContentSource is cached for subsequent calls with the
	 * same AssertionContent.
	 * 
	 * @param assertionContent
	 * @return an AssertionContentSource for a given AssertionContent.
	 */
	public AssertionContentSource getAssertionContentSource(
			AssertionContent assertionContent) {
		AssertionContentSource acs = contentCache.get(assertionContent
				.getSourceName());
		if (acs == null) {
			acs = newAssertionContentSource(assertionContent);
			contentCache.put(assertionContent.getSourceName(), acs);
		}
		return acs;
	}

	/**
	 * Caches an AssertionContent and the associated AssertionContentSource. If
	 * an AssertionContent with the same name is already cached it is replaced
	 * with the given AssertionContent.
	 * 
	 * @param assertionContent
	 *            the AssertionContent to be cached.
	 * @return the cached AssertionContent.
	 */
	public void replaceAssertionContent(AssertionContent assertionContent) {
		AssertionContentSource acs = newAssertionContentSource(assertionContent);
		contentCache.put(assertionContent.getSourceName(), acs);
	}

	/**
	 * Finds the cached Repository Asset or gets it from the Repository.
	 * 
	 * @param referent
	 *            the referent to populate
	 * @throws AssetRetrivalException
	 */
	public AssetInfo getAssetInfo(AssetReferent referent)
			throws AssertionIllegalArgumentException {
		AssetInfo assetInfo = null;

		AssetReference assetRef = referent.getAssetReference();
		assetInfo = assetRefCache.get(assetRef);
		if (assetInfo == null) {
			assetInfo = getRespositoryAssetInfo(assetRef);
			if (assetInfo == null)
				throw new IllegalArgumentException("Could not get Asset for "
						+ assetRef);
			assetRefCache.put(referent.getAssetReference(), assetInfo);
			assetIdCache.put(getPseudoAssetId(assetInfo.getBasicAssetInfo()
					.getAssetKey()), assetInfo);
		}
		return assetInfo;
	}

	public AssertionModule getAssertionModule(AssetKey assetKey) {
		String assetId = getPseudoAssetId(assetKey);
		AssertionModule assertionModule = moduleCache.get(assetId);
		if (assertionModule == null) {
			AssetInfo assetInfo = getAssetInfo(assetKey);
			assertionModule = newAssetAssertionModule(assetInfo);
			moduleCache.put(assetId, assertionModule);
		}
		return assertionModule;
	}

	public void addAssertion(AssetKey assetKey, AssetAssertion assertion) {
		String assetId = getPseudoAssetId(assetKey);
		assertableCache.put(assetId, assertion);
	}

	public Assertion getAssertion(AssetKey assetKey)
			throws AssertionIllegalArgumentException {
		String assetId = getPseudoAssetId(assetKey);
		AssetAssertion assertion = (AssetAssertion) assertableCache
				.get(assetId);
		if (assertion == null) {
			AssetInfo assetInfo = getAssetInfo(assetKey);
			assertion = new AssetAssertion(getAssetReference(assetInfo));
			assertion.dereference(this); // also called in AssertionManager
		}
		return assertion;
	}

	public void addAssertionGroup(AssetKey assetKey, AssetAssertionGroup group) {
		String assetId = getPseudoAssetId(assetKey);
		assertableCache.put(assetId, group);
	}

	public AssertionGroup getAssertionGroup(AssetKey assetKey) {
		String assetId = getPseudoAssetId(assetKey);
		AssetAssertionGroup group = (AssetAssertionGroup) assertableCache
				.get(assetId);
		if (group == null) {
			AssetInfo assetInfo = getAssetInfo(assetKey);
			group = new AssetAssertionGroup(getAssetReference(assetInfo));
			group.dereference(this); // also called in AssertionManager
		}
		return group;
	}

	synchronized private int getUniqueId() {
		return ++uniqueId;
	}

	private String getPseudoAssetId(AssetKey assetKey) {
		return assetKey.getAssetId() + "#"
				+ assetKey.getLibrary().getLibraryId();
	}

	// Finds the cached Repository Asset or gets it from the Repository.
	private AssetInfo getAssetInfo(AssetKey assetKey) {
		String assetId = getPseudoAssetId(assetKey);
		AssetInfo assetInfo = assetIdCache.get(assetId);
		if (assetInfo == null) {
			assetInfo = getRespositoryAssetInfo(assetKey);
			if (assetInfo == null)
				throw new IllegalArgumentException(
						"Could not get Asset for AssetId="
								+ assetKey.getAssetId() + " library="
								+ assetKey.getLibrary().getLibraryName());
			assetIdCache.put(assetId, assetInfo);
			assetRefCache.put(getAssetReference(assetInfo), assetInfo);
		}
		return assetInfo;
	}

	// Finds the cached Repository Asset or gets it from the Repository.
	private AssetReference getAssetReference(AssetInfo assetInfo) {
		BasicAssetInfo basicAssetInfo = assetInfo.getBasicAssetInfo();
		AssetKey assetKey = basicAssetInfo.getAssetKey();
		String assetName = assetKey.getAssetName();
		if (assetName == null) {
			assetName = basicAssetInfo.getAssetName();
		}
		return new AssetReference(assetKey.getLibrary().getLibraryName(),
				assetName, basicAssetInfo.getVersion(),
				basicAssetInfo.getAssetType());
	}

	private AssertionContentSource newAssertionContentSource(
			AssertionContent assertionContent) {
		AssertionContentSource acs = null;
		if (assertionContent instanceof AssetContent) {
			acs = new AssetContentSource((AssetContent) assertionContent, this);
		} else if (assertionContent instanceof BinaryContent) {
			BinaryContent binaryContent = (BinaryContent) assertionContent;
			binaryContent.setSourceId(getUniqueId());
			acs = new BinaryContentSource(binaryContent);
		} else if (assertionContent instanceof StringContent) {
			StringContent stringContent = (StringContent) assertionContent;
			stringContent.setSourceId(getUniqueId());
			acs = new StringContentSource((StringContent) assertionContent);
		} else if (assertionContent instanceof URLContent) {
			acs = new URLContentSource((URLContent) assertionContent);
		} else if (assertionContent instanceof ClassResourceContent) {
			acs = new ClassResourceContentSource(
					(ClassResourceContent) assertionContent);
		} else
			throw new IllegalArgumentException("Unknown AssertionContent type="
					+ assertionContent.getClass().getName());
		return acs;
	}

	private AssertionModule newAssetAssertionModule(AssetInfo assetInfo) {
		BasicAssetInfo basicAssetInfo = assetInfo.getBasicAssetInfo();
		String processorType = null;
		List<AttributeNameValue> attributes = assetInfo.getExtendedAssetInfo()
				.getAttribute();
		for (AttributeNameValue attribute : attributes) {
			String attributeName = attribute.getAttributeName();
			if (attributeName.equals("assertion-processor"))
				processorType = attribute.getAttributeValueString();
		}
		if (processorType == null)
			throw new IllegalArgumentException(
					"Cannot find 'assertion-processor' for Asset="
							+ basicAssetInfo.getAssetName());

		// get module artifacts
		Collection<AssertionContent> scriptContents = new ArrayList<AssertionContent>();
		List<ArtifactInfo> artifacts = assetInfo.getArtifactInfo();
		for (ArtifactInfo artifactInfo : artifacts) {
			scriptContents.add(new AssetContent(artifactInfo.getArtifact()
					.getArtifactName(), getAssetReference(assetInfo)));
		}
		return new BasicAssertionModule(basicAssetInfo.getAssetName(),
				basicAssetInfo.getVersion(),
				basicAssetInfo.getAssetDescription(), scriptContents,
				processorType);
	}

	private AssetInfo getRespositoryAssetInfo(AssetReference assetRef) {
		/*
		 * return rsClient.getAssetInfo( assetRef.getLibrary(),
		 * assetRef.getAssetName(), assetRef.getVersion(),
		 * assetRef.getAssetType());
		 */

		try {
			logger.debug("AssertionProcessorContext: s_cacheManager="+s_cacheManager);
			return (AssetInfo) s_cacheManager.getAsset(assetRef.getLibrary(),
					assetRef.getAssetName(), assetRef.getVersion(),
					assetRef.getAssetType());
		} catch (AssetRetrivalException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	private AssetInfo getRespositoryAssetInfo(AssetKey assetKey) {
		if (assetKey.getLibrary().getLibraryName() == null) {
			logger.error("LibraryName should be provided **************************");
		}
		try {
			return (AssetInfo) s_cacheManager.getAsset(assetKey.getLibrary()
					.getLibraryName(), assetKey.getAssetId());
		} catch (AssetRetrivalException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public CacheManager getCacheManager() {
		return s_cacheManager;
	}

	public void resetCacheManager() {
		if (s_cacheManager != null) {
			s_cacheManager.resetCache();
		}
	}

	public void removeAssetFromCache(String libraryName, String assetName,
			String assetVersion) {
		if (s_cacheManager != null) {
			s_cacheManager.removeAssetFromCache(libraryName, assetName,
					assetVersion);
		}
	}

	public void removeAssetFromCache(String libraryName, String assetId) {
		if (s_cacheManager != null) {
			s_cacheManager.removeAssetFromCache(libraryName, assetId);
		}
	}
}
