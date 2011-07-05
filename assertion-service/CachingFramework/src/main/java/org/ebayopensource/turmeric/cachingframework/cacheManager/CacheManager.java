/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.cachingframework.cacheManager;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.cachingframework.assetcapsule.AssetInfoCapsule;
import org.ebayopensource.turmeric.cachingframework.assetcapsule.intf.AssetCapsuleInterface;
import org.ebayopensource.turmeric.cachingframework.exception.AssetRetrivalException;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;



public class CacheManager 
{
    private static final Logger s_logger                       = Logger.getLogger(CacheManager.class);
    private static final String ASSET_TYPE_ASSERTION           = "Assertion";
    private static final String ASSET_TYPE_ASSERTION_GROUP     = "AssertionGroup";
    private static final String ASSET_TYPE_ASSERTION_MODULE    = "AssertionModule";
	private Map<String, AssetCapsuleInterface> m_assetCacheMap = null;
	private Map<String, String> m_assetIdMap                   = null;
	private String m_capsuleClass                              = null;
	private Properties m_properties                            = null;
	public static final String EBAY_SERVICES_FILE_LOCATION     = "META-INF/soa/services/config/TurmericASV1/ebayServices.properties";
	public static final String SYSTEM_ASSETS                   = "SystemAssets";
	/**
	 * Removes all objects stored in the cache
	 */
	public void resetCache() 
	{
		m_assetCacheMap.clear();
		m_assetIdMap.clear();
	}
	
	public CacheManager(Properties properties, String capsuleClassName) throws IllegalAccessException, InstantiationException, ClassNotFoundException, ServiceException 
	{
		if(properties == null) 
		{
			s_logger.error("CacheManager: Argument 'm_properties' can not be null");
			throw new IllegalArgumentException("Argument 'm_properties' can not be null") ;
		}
		if(capsuleClassName == null) 
		{
			s_logger.error("CacheManager: Argument 'capsuleClassName' can not be null");
			throw new IllegalArgumentException("Argument 'capsuleClassName' can not be null") ;
		}
		if(capsuleClassName.indexOf(".")> -1)
		{
			m_capsuleClass = capsuleClassName;
		}
		else
		{
			m_capsuleClass = "org.ebayopensource.turmeric.cachingframework.assetcapsule." + capsuleClassName;
		}
		@SuppressWarnings("unused")
		AssetCapsuleInterface temp = (AssetCapsuleInterface)Class.forName(m_capsuleClass).newInstance();
		this.m_properties = properties;
		m_assetCacheMap = new HashMap<String, AssetCapsuleInterface>();
		m_assetIdMap = new HashMap<String, String>();
	}
	
	/**
	 * Sets the m_properties member variable with the current user credentials
	 * 
	 * @param properties --> stores user credentials
	 * @return void	 
	 */
	public void setProperties(Properties properties)
	{
		m_properties = properties;
	}
	
	/**
	 * Gets an asset object from RM and stores it in the cache for further reference.
	 * 
	 * @param libraryName --> library to wich the asset belongs
	 * @param assetName --> name of the asset to be fetched
	 * @param assetVersion --> version of the asset to be fetched
	 * @param assetType --> assetType of the asset to be fetched
	 * @return
	 * @throws AssetRetrivalException
	 */
	private Object getAssetFromRs(String libraryName, String assetName,
			   String assetVersion,String assetType) throws AssetRetrivalException 
	{
		s_logger.debug("username: " + m_properties.getProperty(AssetInfoCapsule.USER_ID) +
						"password: " + m_properties.getProperty(AssetInfoCapsule.PASSWORD)
				       );
		Constructor<?> constructor = null;
		AssetCapsuleInterface assetCapsule = null;
		try 
		{
			constructor = Class.forName(m_capsuleClass).getDeclaredConstructor(Properties.class);
		} 
		catch (SecurityException securityException) 
		{
			s_logger.error("CacheManager: SecurityException occured while trying to get the constructor object using reflection");
			throw new AssetRetrivalException("SecurityException occured while trying to get the constructor object using reflection. Message: " + securityException.getMessage(),securityException);
		}
		catch (NoSuchMethodException noSuchMethodException) 
		{
			s_logger.error("CacheManager: NoSuchMethodException occured while trying to get the constructor object using reflection");
			throw new AssetRetrivalException("NoSuchMethodException occured while trying to get the constructor object using reflection. Message: " + noSuchMethodException.getMessage(),noSuchMethodException);
		}
		catch (ClassNotFoundException classNotFoundException) 
		{
			s_logger.error("CacheManager: ClassNotFoundException occured while trying to get the constructor object using reflection");
			throw new AssetRetrivalException("ClassNotFoundException occured while trying to get the constructor object using reflection. Message: " + classNotFoundException.getMessage(),classNotFoundException);
		}
		try 
		{
			
			if(assetType.equalsIgnoreCase(ASSET_TYPE_ASSERTION) || assetType.equalsIgnoreCase(ASSET_TYPE_ASSERTION_GROUP) || assetType.equalsIgnoreCase(ASSET_TYPE_ASSERTION_MODULE))
			{
				Properties temp_props = m_properties;
				String password_su = getPropertyValue(EBAY_SERVICES_FILE_LOCATION, "superUserPassword");
				String userid_su   = getPropertyValue(EBAY_SERVICES_FILE_LOCATION, "superUserName");
				temp_props.setProperty(AssetInfoCapsule.USER_ID,  userid_su);
				temp_props.setProperty(AssetInfoCapsule.PASSWORD, password_su);
				assetCapsule =  (AssetCapsuleInterface)constructor.newInstance(temp_props);	
			}
			else
			{
				assetCapsule =  (AssetCapsuleInterface)constructor.newInstance(m_properties);
			}
		}
		catch (IllegalArgumentException illegalArgumentException) 
		{
			s_logger.error("CacheManager: IllegalArgumentException occured while trying to get the assetCapsule object using reflection");
			throw new AssetRetrivalException("IllegalArgumentException occured while trying to get the assetCapsule object using reflection. Message: " + illegalArgumentException.getMessage(),illegalArgumentException);
		}
		catch (InstantiationException instantiationException) 
		{
			s_logger.error("CacheManager: InstantiationException occured while trying to get the assetCapsule object using reflection");
			throw new AssetRetrivalException("InstantiationException occured while trying to get the assetCapsule object using reflection. Message: " + instantiationException.getMessage(),instantiationException);
		}
		catch (IllegalAccessException illegalAccessException) 
		{
			s_logger.error("CacheManager: IllegalAccessException occured while trying to get the assetCapsule object using reflection");
			throw new AssetRetrivalException("IllegalAccessException occured while trying to get the assetCapsule object using reflection. Message: " + illegalAccessException.getMessage(),illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) 
		{
			s_logger.error("CacheManager: InvocationTargetException occured while trying to get the assetCapsule object using reflection");
			throw new AssetRetrivalException("InvocationTargetException occured while trying to get the assetCapsule object using reflection. Message: " + invocationTargetException.getMessage(),invocationTargetException);
		}
		
		Object result = assetCapsule.getAsset(libraryName, assetName, assetVersion, assetType);
		//System.out.println("Method1, AssetType: " + assetType);
		if(assetType.equalsIgnoreCase(ASSET_TYPE_ASSERTION) || assetType.equalsIgnoreCase(ASSET_TYPE_ASSERTION_GROUP) || assetType.equalsIgnoreCase(ASSET_TYPE_ASSERTION_MODULE))
		{
			//System.out.println("Assertion based asset *************************");
			String key 	  = getKey(libraryName, assetName, assetVersion);
			m_assetCacheMap.put(key, assetCapsule);		
			String assetId 	  = assetCapsule.getAssetId();	
			String mappingKey = getKey(libraryName, assetId);
			m_assetIdMap.put(mappingKey, key);
		}
		
		return result;
	}
	/**
	 * Gets an asset from the cache. If asset is not present in the cache it gets from RM
	 * @param libraryName --> library to wich the asset belongs
	 * @param assetName --> name of the asset to be fetched
	 * @param assetVersion --> version of the asset to be fetched
	 * @param assetType --> assetType of the asset to be fetched
	 * @return
	 * @throws AssetRetrivalException
	 */
	public  Object getAsset(String libraryName, String assetName,
     							   String assetVersion,String assetType) throws AssetRetrivalException 
    {
		
		AssetCapsuleInterface cachedCapsule = null;
		Object asset = null;
		
		if(libraryName == null) 
		{
			s_logger.error("CacheManager: Argument 'libraryName' can not be null");
			throw new IllegalArgumentException("Argument 'libraryName' can not be null");
		}
		if(assetName == null) 
		{
			s_logger.error("CacheManager: Argument 'assetName' can not be null");
			throw new IllegalArgumentException("Argument 'assetName' can not be null");
		}
		if(assetVersion == null) 
		{
			s_logger.error("CacheManager: Argument 'assetVersion' can not be null");
			throw new IllegalArgumentException("Argument 'assetVersion' can not be null");
		}
		if(assetType == null) 
		{
			s_logger.error("CacheManager: Argument 'assetType' can not be null");
			throw new IllegalArgumentException("Argument 'assetType' can not be null");
		}
		
		String key = getKey(libraryName, assetName, assetVersion);
		cachedCapsule = m_assetCacheMap.get(key);
		
		if(cachedCapsule == null) 
		{	
			asset = getAssetFromRs(libraryName, assetName, assetVersion, assetType);
			if(asset == null) 
			{
				s_logger.warn("CacheManager: Unable to get AssetCapsule object for the asset: " + assetName + "(" + assetVersion +")belonging to the library: " + libraryName);
			}
		} 
		else 
		{		
			asset = cachedCapsule.getAsset(libraryName, assetName, assetVersion, assetType);
		}
		
		return asset;
	}
	
	/**
	 * 
	 * Gets an asset from the cache. If asset is not present in the cache it gets from RM
	 * @param libraryName --> library to wich the asset belongs
	 * @param assetId --> Id of the asset to be fetched
	 * @return
	 * @throws AssetRetrivalException
	 */
	public  Object getAsset(String libraryName, String assetId) throws AssetRetrivalException 
   {
		
		AssetCapsuleInterface cachedCapsule = null;
		Object asset = null;
		
		if(libraryName == null) 
		{
			s_logger.error("CacheManager: Argument 'libraryName' can not be null");
			throw new IllegalArgumentException("Argument 'libraryName' can not be null");
		}
		if(assetId == null) 
		{
			s_logger.error("CacheManager: Argument 'assetId' can not be null");
			throw new IllegalArgumentException("Argument 'assetId' can not be null");
		}
		
		String assetIdMapingKey = getKey(libraryName, assetId);
		String cachedCapsuleKey = m_assetIdMap.get(assetIdMapingKey);
		if(cachedCapsuleKey != null) 
		{					
			cachedCapsule = m_assetCacheMap.get(cachedCapsuleKey);
			asset = cachedCapsule.getAsset(libraryName, assetId);
		} else {
			asset = getAssetFromRs(libraryName, assetId);
			if(asset == null) 
			{
				s_logger.warn("CacheManager: Unable to get AssetCapsule object from getAssetFromRs");
			}
		}
		
		return asset;
	}
	
	/**
	 * 
	 * Gets an asset from RM and stores it in the cache for future reference
	 * @param libraryName --> library to wich the asset belongs
	 * @param assetId --> Id of the asset to be fetched
	 * @return
	 * @throws AssetRetrivalException
	 */
	private Object getAssetFromRs(String libraryName, String assetId) throws AssetRetrivalException 
	{
		s_logger.debug("username: " + m_properties.getProperty(AssetInfoCapsule.USER_ID) +
				"password: " + m_properties.getProperty(AssetInfoCapsule.PASSWORD)
		       );
		
		Constructor<?> constructor = null;
		AssetCapsuleInterface assetCapsule = null;
		try 
		{
			constructor = Class.forName(m_capsuleClass).getDeclaredConstructor(Properties.class);
		} 
		catch (SecurityException securityException) 
		{
			s_logger.error("CacheManager: SecurityException occured while trying to get the constructor object using reflection");
			throw new AssetRetrivalException("SecurityException occured while trying to get the constructor object using reflection. Message: " + securityException.getMessage(),securityException);
		}
		catch (NoSuchMethodException noSuchMethodException) 
		{
			s_logger.error("CacheManager: NoSuchMethodException occured while trying to get the constructor object using reflection");
			throw new AssetRetrivalException("NoSuchMethodException occured while trying to get the constructor object using reflection. Message: " + noSuchMethodException.getMessage(),noSuchMethodException);
		}
		catch (ClassNotFoundException classNotFoundException) 
		{
			s_logger.error("CacheManager: ClassNotFoundException occured while trying to get the constructor object using reflection");
			throw new AssetRetrivalException("ClassNotFoundException occured while trying to get the constructor object using reflection. Message: " + classNotFoundException.getMessage(),classNotFoundException);
		}
		try 
		{
			if(SYSTEM_ASSETS.equals(libraryName))
			{
				Properties temp_props = m_properties;
				String password_su = getPropertyValue(EBAY_SERVICES_FILE_LOCATION, "superUserPassword");
				String userid_su   = getPropertyValue(EBAY_SERVICES_FILE_LOCATION, "superUserName");
				temp_props.setProperty(AssetInfoCapsule.USER_ID,  userid_su);
				temp_props.setProperty(AssetInfoCapsule.PASSWORD, password_su);
				assetCapsule =  (AssetCapsuleInterface)constructor.newInstance(temp_props);	
			}
			else
			{
				assetCapsule =  (AssetCapsuleInterface)constructor.newInstance(m_properties);
			}						
		}
		catch (IllegalArgumentException illegalArgumentException) 
		{
			s_logger.error("CacheManager: IllegalArgumentException occured while trying to get the assetCapsule object using reflection");
			throw new AssetRetrivalException("IllegalArgumentException occured while trying to get the assetCapsule object using reflection. Message: " + illegalArgumentException.getMessage(),illegalArgumentException);
		}
		catch (InstantiationException instantiationException) 
		{
			s_logger.error("CacheManager: InstantiationException occured while trying to get the assetCapsule object using reflection");
			throw new AssetRetrivalException("InstantiationException occured while trying to get the assetCapsule object using reflection. Message: " + instantiationException.getMessage(),instantiationException);
		}
		catch (IllegalAccessException illegalAccessException) 
		{
			s_logger.error("CacheManager: IllegalAccessException occured while trying to get the assetCapsule object using reflection");
			throw new AssetRetrivalException("IllegalAccessException occured while trying to get the assetCapsule object using reflection. Message: " + illegalAccessException.getMessage(),illegalAccessException);
		}
		catch (InvocationTargetException invocationTargetException) 
		{
			invocationTargetException.printStackTrace();
			s_logger.error("CacheManager: InvocationTargetException occured while trying to get the assetCapsule object using reflection");
			throw new AssetRetrivalException("InvocationTargetException occured while trying to get the assetCapsule object using reflection. Message: " + invocationTargetException.getMessage(),invocationTargetException);
		}
		
		Object result 			= assetCapsule.getAsset(libraryName, assetId);		
		String assetType 		= assetCapsule.getAssetType();
		//System.out.println("Method2, AssetType: " + assetType);
		if(assetType.equalsIgnoreCase(ASSET_TYPE_ASSERTION) || assetType.equalsIgnoreCase(ASSET_TYPE_ASSERTION_GROUP) || assetType.equalsIgnoreCase(ASSET_TYPE_ASSERTION_MODULE))
		{
			//System.out.println("Assertion related asset ***************");
			String assetIdMapingKey = getKey(libraryName, assetId);		
			String assetCachekey 	= getKey(libraryName, assetCapsule.getAssetName(), assetCapsule.getAssetVersion());
			
			m_assetIdMap.put(assetIdMapingKey, assetCachekey);
			m_assetCacheMap.put(assetCachekey, assetCapsule);
		}		
		return result;
	}
	
	/**
	 * Remove the specified asset form Cache. This is done during asset update
	 * @param libraryName --> library to wich the asset belongs
	 * @param assetName --> name of the asset to be fetched
	 * @param assetVersion --> version of the asset to be fetched
	 */
	public void removeAssetFromCache(String libraryName, String assetName, String assetVersion) 
	{
		String key = getKey(libraryName, assetName, assetVersion);
		m_assetCacheMap.remove(key);
	}
	
	/**
	 * Remove the specified asset form Cache. This is done during asset update
	 * @param libraryName --> library to wich the asset belongs
	 * @param assetId --> Id of the asset to be fetched
	 */
	public void removeAssetFromCache(String libraryName, String assetId)
	{
		String mappingKey = getKey(libraryName, assetId);
		String key 		  = m_assetIdMap.remove(mappingKey);
		if(key != null)
		{
			m_assetCacheMap.remove(key);
		}
	}
	
	private String getKey(String libraryName, String assetName, String assetVersion)
	{
		String key = libraryName + ":" + assetName + ":" + assetVersion; 
		return key;
	}

	private String getKey(String libraryName, String assetId)
	{
		String key = libraryName + ":" + assetId; 
		return key;
	}
	
	/**
	 * Fetches a property value from a file
	 * 
	 * @param fileLocation --> location of the property file
	 * @param propName --> Key of the property whose value is to be fetched
	 * @return
	 */
	private String getPropertyValue(String fileLocation, String propName) {
		java.util.Properties props = new java.util.Properties();
		try {
			InputStream propFileStream = CacheManager.class.getClassLoader().getResourceAsStream(EBAY_SERVICES_FILE_LOCATION);
			props.load(propFileStream);
			propFileStream.close();
		} catch (Exception e) {
			return null;
		}
		return props.getProperty(propName);
	}
}

