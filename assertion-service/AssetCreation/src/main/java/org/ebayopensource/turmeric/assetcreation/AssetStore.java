/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.ebayopensource.turmeric.assetcreation.exception.*;
import org.ebayopensource.turmeric.repository.v2.services.*;

/**
 * The Class AssetStore.
 */
public class AssetStore {

   private static Logger s_logger = Logger.getLogger(AssetStore.class);

   private final Map<String, List<AssetIdWrapper>> assetIds = new HashMap<String, List<AssetIdWrapper>>();
   private final Map<String, List<AssetInfoWrapper>> assetInfos = new HashMap<String, List<AssetInfoWrapper>>();

   /** The asset id index. */
   int assetIdIndex = 0;

   /** The asset info index. */
   int assetInfoIndex = 0;

   /**
    * Adds the asset id.
    * 
    * @param id
    *           the id
    * @param assetId
    *           the asset id
    */
   public void addAssetId(String id, AssetIdWrapper assetId) {
      s_logger.debug("Caching assetId for ID = " + id + "assetId = " + assetId);
      List<AssetIdWrapper> listOfAssetId = assetIds.get(id);
      if (listOfAssetId == null) {
         listOfAssetId = new ArrayList<AssetIdWrapper>();
         listOfAssetId.add(assetId);
         assetIds.put(id, listOfAssetId);
      } else {
         listOfAssetId.add(assetId);
      }
   }

   /**
    * Adds the asset info.
    * 
    * @param id
    *           the id
    * @param assetInfo
    *           the asset info
    */
   public void addAssetInfo(String id, AssetInfoWrapper assetInfo) {
      s_logger.debug("Caching assetInfo for ID = " + id + "assetId = "
               + assetInfo.getAssetInfo().getBasicAssetInfo().getAssetKey().getAssetId());
      List<AssetInfoWrapper> listOfAssetInfo = assetInfos.get(id);
      if (listOfAssetInfo == null) {
         listOfAssetInfo = new ArrayList<AssetInfoWrapper>();
         listOfAssetInfo.add(assetInfo);
         assetInfos.put(id, listOfAssetInfo);
      } else {
         listOfAssetInfo.add(assetInfo);
      }
   }

   /**
    * Gets the asset id.
    * 
    * @param id
    *           the id
    * @return the asset id
    * @throws AssetIdNotFoundException
    *            the asset id not found exception
    * @throws IdNotFoundException
    *            the id not found exception
    */
   public String getAssetId(String id) throws AssetIdNotFoundException, IdNotFoundException {
      List<AssetIdWrapper> listOfAssetId = assetIds.get(id);
      if (listOfAssetId == null) {
         s_logger.error("ID " + id + " is not configured in the input xml file");
         throw new IdNotFoundException("ID " + id + " is not configured in the input xml file");
      }
      if (assetIdIndex >= listOfAssetId.size()) {
         s_logger.error("All the generated assetIds got consumed, Increase number of assets in the "
                  + "input xml configuration file for this ID number " + id);
         throw new AssetIdNotFoundException(
                  "All the generated assetIds got consumed, Increase number of assets in the "
                           + "input xml configuration file for this ID number " + id);
      }
      listOfAssetId.get(assetIdIndex).setConsumed(true);
      return listOfAssetId.get(assetIdIndex++).getAssetId();
   }

   /**
    * Gets the asset info.
    * 
    * @param id
    *           the id
    * @return the asset info
    * @throws AssetInfoNotFoundException
    *            the asset info not found exception
    * @throws IdNotFoundException
    *            the id not found exception
    */
   public AssetInfo getAssetInfo(String id) throws AssetInfoNotFoundException, IdNotFoundException {
      List<AssetInfoWrapper> listOfAssetInfo = assetInfos.get(id);
      if (listOfAssetInfo == null) {
         s_logger.error("ID " + id + " is not configured in the input xml file");
         throw new IdNotFoundException("ID " + id + " is not configured in the input xml file");
      }
      if (assetInfoIndex >= listOfAssetInfo.size()) {
         s_logger.error("All the generated AssetInfo got consumed, Increase number of assets in the "
                  + "input xml configuration file for this ID number " + id);
         throw new AssetInfoNotFoundException(
                  "All the generated AssetInfo got consumed, Increase number of assets in the "
                           + "input xml configuration file for this ID number " + id);
      }
      listOfAssetInfo.get(assetInfoIndex).setConsumed(true);
      return listOfAssetInfo.get(assetInfoIndex++).getAssetInfo();
   }

   /**
    * Gets the asset ids.
    * 
    * @return the asset ids
    */
   public Map<String, List<AssetIdWrapper>> getAssetIds() {
      return assetIds;
   }

   /**
    * Gets the asset infos.
    * 
    * @return the asset infos
    */
   public Map<String, List<AssetInfoWrapper>> getAssetInfos() {
      return assetInfos;
   }

}
