/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.filters;

import javax.xml.namespace.QName;

import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.services.ServiceFilter;
import org.wso2.carbon.governance.api.services.dataobjects.Service;
import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;

/**
 * Provides a WSO2 search filter to locate any duplicates.
 * 
 * @author dcarver
 * 
 */
public class DuplicateServiceFilter implements ServiceFilter {

   private BasicAssetInfo basicInfo = null;

   /**
    * The DuplicateServiceFilter constructor.
    * 
    * @param bi
    *           The BasicAssetInfo used to check for duplicates.
    */
   public DuplicateServiceFilter(BasicAssetInfo bi) {
      basicInfo = bi;
   }

   /**
    * {@inheritDoc}
    * 
    * @return true if the service is a duplicate.
    */
   @Override
   public boolean matches(Service service) throws GovernanceException {
      QName qname = new QName(basicInfo.getNamespace(), basicInfo.getAssetName());
      if (service.getQName().equals(qname)
               && service.getAttribute(AssetConstants.TURMERIC_VERSION).equals(basicInfo.getVersion())) {
         return true;
      }
      return false;
   }

}
