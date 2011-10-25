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
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.SubmitForPublishingRequest;
import org.ebayopensource.turmeric.repository.v2.services.SubmitForPublishingResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.Asset;
import org.ebayopensource.turmeric.repository.wso2.AssetFactory;
import org.ebayopensource.turmeric.repository.wso2.RSLifeCycle;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.assets.AssetConstants;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;

public class SubmitForPublishing extends AbstractRepositoryProvider {

   public SubmitForPublishingResponse process(SubmitForPublishingRequest request) {
      Registry wso2 = RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      SubmitForPublishingResponse response = new SubmitForPublishingResponse();

      try {

         AssetFactory factory = new AssetFactory(request.getAssetKey(), wso2);
         Asset gasset = factory.createAsset();
         AssetKey assetKey = getAssetKey(gasset);
         String assetId = assetKey.getAssetId();

         if (!gasset.exists()) {
            return createAssetNotFoundError(errorDataList, response);
         }

         Resource asset = wso2.get(assetId);
         RSLifeCycle.submit(asset, request.getComment());

         wso2.put(assetId, asset);

         response.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
         response.setVersion(asset.getProperty(AssetConstants.TURMERIC_VERSION));
         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }

   }

}
