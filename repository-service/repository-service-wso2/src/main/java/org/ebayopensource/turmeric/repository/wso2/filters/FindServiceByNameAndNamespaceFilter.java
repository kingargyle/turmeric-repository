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

import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.services.ServiceFilter;
import org.wso2.carbon.governance.api.services.dataobjects.Service;

public class FindServiceByNameAndNamespaceFilter implements ServiceFilter {

   private BasicAssetInfo basicInfo = null;

   public FindServiceByNameAndNamespaceFilter(BasicAssetInfo info) {
      this.basicInfo = info;
   }

   @Override
   public boolean matches(Service service) throws GovernanceException {
      QName qname = new QName(basicInfo.getNamespace(), basicInfo.getAssetName());
      if (service.getQName().equals(qname)) {
         return true;
      }
      return false;
   }

}
