/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repository.listener.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class ConstantPopulator {
   private static ConstantPopulator instance = null;
   private Properties properties;

   private static Logger s_logger = Logger.getLogger(ConstantPopulator.class);

   public static Logger getLogger() {
      return s_logger;
   }

   protected ConstantPopulator() throws IOException {
      String defaultSvcLayerFilePath = "C:/RM/ebayconf/ebayServices.properties";
      File file = new File(defaultSvcLayerFilePath);
      FileInputStream inputStream = new FileInputStream(file);
      properties = new Properties();
      properties.load(inputStream);
   }

   public static ConstantPopulator getInstance() {
      if (instance == null) {
         try {
            instance = new ConstantPopulator();
         } catch (IOException e) {
            getLogger().error("Unable to get ConstantPopulator class instance");
         }
      }

      return instance;
   }

   public String getValueOf(PropertyKeys propertyKey) {
      return properties.getProperty(propertyKey.value());
   }

   public String getValueOf(String propertyKey) {
      return properties.getProperty(propertyKey);
   }

   public List<String> getPossibleSuffixes(String prefix) {
      List<String> listOfSuffixes = new ArrayList<String>();

      Set<Object> keys = properties.keySet();
      if (keys != null) {
         for (Object key : keys) {
            if (key instanceof String) {
               String keyString = (String) key;
               if (keyString.startsWith(prefix)) {
                  String suffix = keyString.substring(prefix.length() + 1, keyString.length());
                  if (keyString.charAt(prefix.length()) == '.') {
                     listOfSuffixes.add(suffix);
                  }
               }
            }
         }
      }

      return listOfSuffixes;
   }

   public Map<String, String> getCommonPrefixValues(String prefix) {
      List<String> availableSuffixes = getPossibleSuffixes(prefix);
      Map<String, String> commonPrefixValues = new HashMap<String, String>();
      if (availableSuffixes != null) {
         for (String suffix : availableSuffixes) {
            String value = getValueOf(prefix + "." + suffix);
            commonPrefixValues.put(suffix, value);
         }
      }
      return commonPrefixValues;
   }

   /*
    * public static void main(String[] args) { //ConstantPopulator populator = ConstantPopulator.getInstance(); //String
    * result = populator.getValueOf("libraryURL"); //System.out.println("libraryURL: " + result);
    * 
    * System.out.println("libraryURL: " + ConstantPopulator.getInstance().getValueOf(PropertyKeys.LIBRARY_URL));
    * 
    * }
    */
}
