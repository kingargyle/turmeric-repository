/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.operations;

import java.util.Arrays;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetTypesRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetAssetTypesResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;

/**
 * Handles the request for retrieving the asset types supported by the provider
 * 
 * @author dcarver
 * 
 */
public class GetAssetTypes extends AbstractRepositoryProvider {

   /**
    * Process the request to return the types of assets supported by the provider.
    * 
    * @param request
    *           The request
    * @return the response. this should always be successful.
    */
   public GetAssetTypesResponse process(GetAssetTypesRequest request) {
      GetAssetTypesResponse response = new GetAssetTypesResponse();

      String[] assetTypes = { "Service", "Endpoint", "WSDL", "Schema" };
      response.getAssetType().addAll(Arrays.asList(assetTypes));
      response.setAck(AckValue.SUCCESS);
      return response;
   }
}
