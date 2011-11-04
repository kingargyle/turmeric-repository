/*
 * Copyright 2005-2007 WSO2, Inc. (http://wso2.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ebayopensource.turmeric.repository.wso2.utils;

import org.wso2.carbon.base.ServerConfigurationException;
import org.wso2.carbon.utils.ArchiveManipulator;
import org.wso2.carbon.utils.FileManipulator;
import org.wso2.carbon.utils.ServerConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A set of utility methods such as starting & stopping a Carbon server.
 */
public class ServerUtils {

   private static Process process;
   private static Thread consoleLogPrinter;
   private static String originalUserDir = null;

   private final static String SERVER_STARTUP_MESSAGE = "WSO2 Carbon started in";
   private final static String SERVER_SHUTDOWN_MESSAGE = "Halting JVM";
   private final static long DEFAULT_START_STOP_WAIT_MS = 1000 * 60 * 4;

   public synchronized static void startServerUsingCarbonHome(String carbonHome) throws ServerConfigurationException {
      if (process != null) { // An instance of the server is running
         return;
      }
      Process tempProcess;
      try {
         System.setProperty(ServerConstants.CARBON_HOME, carbonHome);
         originalUserDir = System.getProperty("user.dir");
         System.setProperty("user.dir", carbonHome);
         String temp;
         if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            tempProcess = Runtime.getRuntime().exec(new String[] { "bat", "bin/wso2server.bat" }, null,
                     new File(carbonHome));
         } else {
            tempProcess = Runtime.getRuntime().exec(new String[] { "sh", "bin/wso2server.sh", "test" }, null,
                     new File(carbonHome));
         }
         Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
               try {
                  System.out.println("Shutting down server...");
                  ServerUtils.shutdown();
               } catch (Exception e) {
                  e.printStackTrace();
               }
            }
         });
         final BufferedReader reader = new BufferedReader(new InputStreamReader(tempProcess.getInputStream()));
         long time = System.currentTimeMillis() + DEFAULT_START_STOP_WAIT_MS;
         while ((temp = reader.readLine()) != null && System.currentTimeMillis() < time) {
            System.out.println(temp);
            if (temp.contains(SERVER_STARTUP_MESSAGE)) {
               consoleLogPrinter = new Thread() {
                  @Override
                  public void run() {
                     try {
                        String temp;
                        while ((temp = reader.readLine()) != null) {
                           System.out.println(temp);
                        }
                     } catch (Exception ignore) {

                     }
                  }
               };
               consoleLogPrinter.start();
               break;
            }
         }
      } catch (IOException e) {
         throw new RuntimeException("Unable to start server", e);
      }
      process = tempProcess;
      System.out.println("Successfully started Carbon server. Returning...");
   }

   public synchronized static void startServerUsingCarbonZip(String carbonServerZipFile)
            throws ServerConfigurationException, IOException {
      if (process != null) { // An instance of the server is running
         return;
      }
      String carbonHome = setUpCarbonHome(carbonServerZipFile);
      carbonHome = carbonHome.replace("/carbon", "/wso2greg-4.1.0");
      // instrumentJarsForEmma(carbonHome);
      startServerUsingCarbonHome(carbonHome);
   }

   public synchronized static String setUpCarbonHome(String carbonServerZipFile) throws IOException {
      if (process != null) { // An instance of the server is running
         return null;
      }
      int indexOfZip = carbonServerZipFile.lastIndexOf(".zip");
      if (indexOfZip == -1) {
         throw new IllegalArgumentException(carbonServerZipFile + " is not a zip file");
      }
      String fileSeparator = (File.separator.equals("\\")) ? "\\" : "/";
      String extractedCarbonDir = carbonServerZipFile.substring(carbonServerZipFile.lastIndexOf(fileSeparator) + 1,
               indexOfZip);
      FileManipulator.deleteDir(extractedCarbonDir);
      new ArchiveManipulator().extract(carbonServerZipFile, "wso2temp");
      return new File(".").getAbsolutePath() + File.separator + "wso2temp" + File.separator + extractedCarbonDir;
   }

   public synchronized static void shutdown() throws Exception {
      if (process != null) {
         process.destroy();
         InputStream inputstream = null;
         try {
            String temp;
            process.destroy();
            inputstream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
            long time = System.currentTimeMillis() + DEFAULT_START_STOP_WAIT_MS;
            while ((temp = reader.readLine()) != null && System.currentTimeMillis() < time) {
               if (temp.contains(SERVER_SHUTDOWN_MESSAGE)) {
                  break;
               }
            }

         } catch (IOException ignored) {
         } finally {
            if (inputstream != null) {
               inputstream.close();
            }
         }

         try {
            consoleLogPrinter.interrupt();
         } catch (Exception e) {
            e.printStackTrace();
         }

         consoleLogPrinter = null;
         process = null;
         System.clearProperty(ServerConstants.CARBON_HOME);
         System.setProperty("user.dir", originalUserDir);
      }
   }

}