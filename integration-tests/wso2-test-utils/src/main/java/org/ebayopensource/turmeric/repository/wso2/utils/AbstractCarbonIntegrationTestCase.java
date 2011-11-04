/*
 *  Copyright (c) 2005-2011, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *    
 *    Portions of the code were originally based on WSO2's CarbonIntegrationTestCase
 *    these have been updated to work with a more general environment.
 *******************************************************************************/
package org.ebayopensource.turmeric.repository.wso2.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.Before;

/**
 * This is the base class of all JUnit TestCases which require starting of a WSO2 Governance server instance
 * <p/>
 * The governance server is downloaded from the ebayopensource hudson instance.
 * <p/>
 */
public abstract class AbstractCarbonIntegrationTestCase {

   protected String carbonHome;

   @Before
   public void setUp() throws Exception {

      String carbonZip = downloadZip();
      copyArtifacts();

      ServerUtils.startServerUsingCarbonZip(carbonZip);
   }

   /**
    * Copy any artifacts that may be needed before starting the server
    * 
    * @throws java.io.IOException
    *            If an error occurs while copying artifacts
    */
   abstract protected void copyArtifacts() throws IOException;

   @AfterClass
   public static void tearDown() throws Exception {
      ServerUtils.shutdown();
      Thread.sleep(15000L);
   }

   public String downloadZip() throws Exception {
      BufferedInputStream in = new BufferedInputStream(new URL(
               "http://dist.wso2.org/products/registry/4.1.0/wso2greg-4.1.0.zip").openStream());
      String location = System.getProperty("java.io.tmpdir") + "/carbon.zip";

      if (!new File(location).exists()) {
         System.out.println("Downloading WSO2 Governance Registry.");
         FileOutputStream fos = new FileOutputStream(location);
         BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
         byte[] data = new byte[1024];
         int x = 0;
         while ((x = in.read(data, 0, 1024)) >= 0) {
            bout.write(data, 0, x);
         }
         bout.close();
         in.close();
         System.out.println("Finished download.");
      }

      return location;
   }

}