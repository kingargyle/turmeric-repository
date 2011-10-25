/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.operations;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.RejectAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.RejectAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.RejectionInfo;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.RSLifeCycle;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;

public class RejectAsset extends AbstractRepositoryProvider {

   public RejectAssetResponse process(RejectAssetRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      RejectAssetResponse response = new RejectAssetResponse();

      try {
         RejectionInfo rejectionInfo = request.getRejectionInfo();
         String assetId = rejectionInfo.getAssetId();
         GovernanceArtifact gart = GovernanceUtils.retrieveGovernanceArtifactById(wso2, assetId);

         if (gart == null) {
            return createAssetNotFoundError(errorDataList, response);
         }

         Resource asset = wso2.get(assetId);
         RSLifeCycle.reject(asset, rejectionInfo.getComments());

         wso2.put(assetId, asset);

         response.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
         response.setVersion(asset.getProperty(RSProviderUtil.__artifactVersionPropName));
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

}
