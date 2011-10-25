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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v2.services.Artifact;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactCriteria;
import org.ebayopensource.turmeric.repository.v2.services.SearchAssetsRequest;
import org.ebayopensource.turmeric.repository.v2.services.SearchAssetsResponse;
import org.ebayopensource.turmeric.repository.wso2.AbstractRepositoryProvider;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.RegistryConstants;
import org.wso2.carbon.registry.core.Resource;

public class SearchAssets extends AbstractRepositoryProvider {

   public SearchAssetsResponse process(SearchAssetsRequest request) {
      RSProviderUtil.getRegistry();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      SearchAssetsResponse response = new SearchAssetsResponse();

      if (request.getAssetQuery() == null) {
         return createErrorInvalidInput(errorDataList, response);
      }

      ArtifactCriteria artifactCriteria = request.getAssetQuery().getArtifactCriteria();
      List<Artifact> artifacts = artifactCriteria.getArtifact();

      Registry registry = RSProviderUtil.getRegistry();

      String searchByID = "SELECT REG_PATH_ID, REG_NAME FROM REG_RESOURCE WHERE REG_PATH_ID = ?";
      try {
         Resource queryResource = registry.newResource();
         queryResource.setContent(searchByID);
         queryResource.setMediaType(RegistryConstants.SQL_QUERY_MEDIA_TYPE);
         queryResource
                  .addProperty(RegistryConstants.RESULT_TYPE_PROPERTY_NAME, RegistryConstants.RESOURCES_RESULT_TYPE);
         registry.put(RegistryConstants.CONFIG_REGISTRY_BASE_PATH + RegistryConstants.QUERIES_COLLECTION_PATH
                  + "/turmeric-queries-findbyid", queryResource);

         Map<String, String> parameters = new ConcurrentHashMap<String, String>();
         for (Artifact art : artifacts) {
            parameters.put(art.getArtifactIdentifier(), "%services%");
         }
         registry.executeQuery(RegistryConstants.CONFIG_REGISTRY_BASE_PATH + RegistryConstants.QUERIES_COLLECTION_PATH
                  + "/turmeric-queries-findbyid", parameters);

         return RSProviderUtil.setSuccessResponse(response);
      } catch (Exception ex) {
         return RSProviderUtil.handleException(ex, response,
                  RepositoryServiceErrorDescriptor.SERVICE_PROVIDER_EXCEPTION);
      }

   }

}
