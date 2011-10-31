/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation.util;

import java.io.File;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.assetdata.artifacts.AssetData;
import org.ebayopensource.turmeric.repository.v2.services.*;

/**
 * The Class CommonUtil.
 */
public class CommonUtil {

   private static Logger s_logger = Logger.getLogger(CommonUtil.class);

   /**
    * Un marshal asset info.
    * 
    * @param filePath
    *           the file path
    * @return the asset info
    */
   public static AssetInfo unMarshalAssetInfo(String filePath) {
      s_logger.debug("Inside CommonUtil.unMarshalAssetInfo()");
      File inputXmlFile = new File(filePath);
      AssetInfo assetInfo = JAXB.unmarshal(inputXmlFile, AssetInfo.class);
      return assetInfo;
   }

   /**
    * Un marshal asset data.
    * 
    * @param filePath
    *           the file path
    * @return the asset data
    */
   public static AssetData unMarshalAssetData(String filePath) {
      s_logger.debug("Inside CommonUtil.unMarshalAssetData()");
      File inputXmlFile = new File(filePath);
      AssetData assetData = JAXB.unmarshal(inputXmlFile, AssetData.class);
      return assetData;
   }

   /**
    * Marshal asset info.
    * 
    * @param assetInfo
    *           the asset info
    * @param filePath
    *           the file path
    */
   public static void marshalAssetInfo(AssetInfo assetInfo, String filePath) {
      s_logger.debug("Inside CommonUtil.marshalAssetInfo()");
      int index = filePath.lastIndexOf("/");
      File folder = new File(filePath.substring(0, index));
      if (!folder.isDirectory()) {
         folder.mkdir();
      }
      File outputXmlFile = new File(filePath);
      if (outputXmlFile.exists() && outputXmlFile.isFile()) {
         return;
      }
      JAXB.marshal(assetInfo, outputXmlFile);
   }

   /**
    * Marshal asset data.
    * 
    * @param assetData
    *           the asset data
    * @param filePath
    *           the file path
    */
   public static void marshalAssetData(AssetData assetData, String filePath) {
      s_logger.debug("Inside CommonUtil.marshalAssetData()");
      File outputXmlFile = new File(filePath);
      JAXB.marshal(assetData, outputXmlFile);
   }

}
