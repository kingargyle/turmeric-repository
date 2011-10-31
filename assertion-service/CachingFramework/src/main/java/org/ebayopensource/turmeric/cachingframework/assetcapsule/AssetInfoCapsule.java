/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.cachingframework.assetcapsule;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.cachingframework.assetcapsule.intf.AssetCapsuleInterface;
import org.ebayopensource.turmeric.cachingframework.common.RepositoryServiceClient;
import org.ebayopensource.turmeric.cachingframework.exception.AssertionRuntimeException;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;

public class AssetInfoCapsule implements AssetCapsuleInterface {
   private static final Logger s_logger = Logger.getLogger(AssetInfoCapsule.class);
   private RepositoryServiceClient m_rsClient = null;
   private AssetInfo m_assetInfo = null;
   private String m_assetName = null;
   private String m_assetId = null;
   private String m_libraryName = null;
   private String m_assetVersion = null;
   private String m_assetType = null;

   public static String USER_ID = "user_id";
   public static String PASSWORD = "password";
   public static String RS_LOCATION = "rs_location";

   public AssetInfoCapsule() {

   }

   /**
    * constructor creating a connection to Repository service (RS)
    * 
    * @param properties
    *           --> information required to connect to RS
    * @throws IllegalArgumentException
    * @throws AssertionRuntimeException
    */
   public AssetInfoCapsule(Properties properties) throws IllegalArgumentException, AssertionRuntimeException {
      String userId = properties.getProperty(USER_ID);
      String password = properties.getProperty(PASSWORD);
      String serviceLocation = properties.getProperty(RS_LOCATION);
      if (userId == null) {
         s_logger.error("Property 'user_id' must be provided");
         throw new IllegalArgumentException("Property 'user_id' must be provided");
      }
      if (password == null) {
         s_logger.error("Property 'password' must be provided");
         throw new IllegalArgumentException("Property 'password' must be provided");
      }
      if (serviceLocation == null) {
         s_logger.error("Property 'rs_location' must be provided");
         throw new IllegalArgumentException("Property 'rs_location' must be provided");
      }

      try {
         m_rsClient = new RepositoryServiceClient(userId, password, serviceLocation);
      } catch (ServiceException serviceException) {
         throw new AssertionRuntimeException(
                  "Could not create client to Repository Service from withing AssetInfoCapsule due to following reasons: "
                           + serviceException.getMessage(), serviceException);
      }
   }

   /**
    * Gets the assetInfo object from instance variable m_assetInfo of from RepositoryService
    */
   @Override
   public Object getAsset(String libraryName, String assetName, String assetVersion, String assetType) {
      if (m_assetInfo == null) {
         this.m_assetInfo = m_rsClient.getAssetInfo(libraryName, assetName, assetVersion, assetType);
         this.m_assetId = m_assetInfo.getBasicAssetInfo().getAssetKey().getAssetId();
         this.m_assetName = m_assetInfo.getBasicAssetInfo().getAssetName();
         this.m_assetVersion = m_assetInfo.getBasicAssetInfo().getVersion();
         this.m_libraryName = libraryName;
         this.m_assetType = m_assetInfo.getBasicAssetInfo().getAssetType();
      }

      return m_assetInfo;
   }

   /**
    * Gets the assetInfo object from instance variable m_assetInfo of from RepositoryService
    */
   @Override
   public Object getAsset(String assetId) {

      if (m_assetInfo == null) {
         AssetKey assetKey = new AssetKey();

         assetKey.setAssetId(assetId);

         this.m_assetInfo = m_rsClient.getAssetInfo(assetKey);
         this.m_assetId = m_assetInfo.getBasicAssetInfo().getAssetKey().getAssetId();
         this.m_assetName = m_assetInfo.getBasicAssetInfo().getAssetName();
         this.m_assetVersion = m_assetInfo.getBasicAssetInfo().getVersion();
         this.m_assetType = m_assetInfo.getBasicAssetInfo().getAssetType();
      }

      return m_assetInfo;
   }

   @Override
   public String getAssetId() {
      return m_assetId;
   }

   @Override
   public String getAssetName() {
      return m_assetName;
   }

   @Override
   public String getAssetType() {
      return m_assetType;
   }

   @Override
   public String getLibraryName() {
      return m_libraryName;
   }

   @Override
   public String getAssetVersion() {
      return m_assetVersion;
   }

}
