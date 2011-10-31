/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProviderFactory;
import org.junit.Test;

public class RepositoryServiceImplLoadTest {

   @Test
   public void testServiceProvider() {
      // TODO Auto-generated method stub
      try {

         System.setProperty("org.ebayopensource.turmeric.repository.wso2.url", "https://127.0.0.1:9443/registry");
         System.setProperty("org.ebayopensource.turmeric.repository.wso2.username", "admin");
         System.setProperty("org.ebayopensource.turmeric.repository.wso2.password", "admin");
         System.setProperty("jaxb.debug", "true");

         RepositoryServiceProviderFactory.getInstance();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

}
