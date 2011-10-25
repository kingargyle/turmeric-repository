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
import org.ebayopensource.turmeric.repository.v2.services.ApprovalInfo;
import org.ebayopensource.turmeric.repository.v2.services.ApproveAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.ApproveAssetResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Registry;

public class ApproveAsset extends AbstractRepositoryProvider {

   public ApproveAssetResponse process(ApproveAssetRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      ApproveAssetResponse response = new ApproveAssetResponse();

      try {
         ApprovalInfo approvalInfo = request.getApprovalInfo();
         String assetId = approvalInfo.getAssetId();
         GovernanceArtifact gart = GovernanceUtils.retrieveGovernanceArtifactById(wso2, assetId);

         if (gart == null) {
            return createAssetNotFoundError(errorDataList, response);
         }

         // TODO Re-implement approveAsset

         response.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
         response.setVersion(gart.getAttribute(AssetConstants.TURMERIC_VERSION));
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }
   }

}
