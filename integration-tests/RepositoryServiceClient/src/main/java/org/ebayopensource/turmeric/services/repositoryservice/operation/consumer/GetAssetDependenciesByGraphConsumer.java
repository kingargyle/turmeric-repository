/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesByGraphRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAssetDependenciesByGraphResponse;
import org.ebayopensource.turmeric.repository.v1.services.GraphRelationship;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.TypedRelationNode;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetAssetDependenciesByGraphConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV1 m_proxy = null;

   public static String testGetAssetDependenciesByGraph_validAsset(AssetInfo assetInfo) {

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library);

      GetAssetDependenciesByGraphRequest getAssetDependenciesByGraphRequest = new GetAssetDependenciesByGraphRequest();
      getAssetDependenciesByGraphRequest.setAssetKey(assetKey);

      try {
         GetAssetDependenciesByGraphConsumer getAssetDependenciesByGraphConsumer = new GetAssetDependenciesByGraphConsumer();
         GetAssetDependenciesByGraphResponse getAssetDependenciesByGraphResponse = getAssetDependenciesByGraphConsumer
                  .getProxy().getAssetDependenciesByGraph(getAssetDependenciesByGraphRequest);
         if (getAssetDependenciesByGraphResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetAssetDependenciesByGraphResponse(getAssetDependenciesByGraphResponse, "positiveCase")
                  .equalsIgnoreCase("success")) {
            GraphRelationship graphRelationship = getAssetDependenciesByGraphResponse.getGraphRelationship();
            if (graphRelationship == null) {
               System.out.println("There are no dependent Assets for " + RepositoryServiceClientConstants.ASSET_NAME);

               return "PASSED";
            }
            printRelationship(graphRelationship, 1);

            return "PASSED";
         } else {
            return "FAILED";
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return "FAILED";
      } catch (Exception e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   public static String testGetAssetDependenciesByGraph_invalidAsset() {
      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.INVALID_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
      assetKey.setLibrary(library);

      GetAssetDependenciesByGraphRequest getAssetDependenciesByGraphRequest = new GetAssetDependenciesByGraphRequest();
      getAssetDependenciesByGraphRequest.setAssetKey(assetKey);
      getAssetDependenciesByGraphRequest.setDepth(1);

      try {
         GetAssetDependenciesByGraphConsumer getAssetDependenciesByGraphConsumer = new GetAssetDependenciesByGraphConsumer();
         GetAssetDependenciesByGraphResponse getAssetDependenciesByGraphResponse = getAssetDependenciesByGraphConsumer
                  .getProxy().getAssetDependenciesByGraph(getAssetDependenciesByGraphRequest);

         if (getAssetDependenciesByGraphResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetDependenciesByGraphResponse(getAssetDependenciesByGraphResponse, "negativeCase")
                  .equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getAssetDependenciesByGraphResponse.getErrorMessage().getError();

            System.out.println("The following list of errors occured");
            for (CommonErrorData errorData : errorDatas) {
               System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());
            }
            return "PASSED";
         } else {
            return "FAILED";
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return "FAILED";
      } catch (Exception e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   public static String testGetAssetDependenciesByGraph_insufficientPrivilege() {
      Library library = new Library();
      library.setLibraryId(RepositoryServiceClientConstants.LIBRARY_ID);
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(RepositoryServiceClientConstants.NO_PRIVILAGE_ASSET_ID);
      assetKey.setAssetName(RepositoryServiceClientConstants.ASSET_NAME);
      assetKey.setLibrary(library);

      GetAssetDependenciesByGraphRequest getAssetDependenciesByGraphRequest = new GetAssetDependenciesByGraphRequest();
      getAssetDependenciesByGraphRequest.setAssetKey(assetKey);

      try {
         GetAssetDependenciesByGraphConsumer getAssetDependenciesByGraphConsumer = new GetAssetDependenciesByGraphConsumer();
         GetAssetDependenciesByGraphResponse getAssetDependenciesByGraphResponse = getAssetDependenciesByGraphConsumer
                  .getProxy().getAssetDependenciesByGraph(getAssetDependenciesByGraphRequest);

         if (getAssetDependenciesByGraphResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateGetAssetDependenciesByGraphResponse(getAssetDependenciesByGraphResponse, "negativeCase")
                  .equalsIgnoreCase("success")) {
            List<CommonErrorData> errorDatas = getAssetDependenciesByGraphResponse.getErrorMessage().getError();

            System.out.println("The following list of errors occured");
            for (CommonErrorData errorData : errorDatas) {
               System.out.println("Error id: " + errorData.getErrorId() + " Error message: " + errorData.getMessage());
            }
            return "PASSED";
         } else {
            return "FAILED";
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return "FAILED";
      } catch (Exception e) {
         e.printStackTrace();
         return "FAILED";
      }
   }

   private static String validateGetAssetDependenciesByGraphResponse(
            GetAssetDependenciesByGraphResponse getAssetDependenciesByGraphResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getAssetDependenciesByGraphResponse.getAck().value().equalsIgnoreCase("success")) {
            GraphRelationship graphRelationship = getAssetDependenciesByGraphResponse.getGraphRelationship();
            if (graphRelationship != null) {
               return RepositoryServiceConsumerUtil.validateGraphRelationship(graphRelationship);
            }
            return "success";
         }
         return "failure";
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getAssetDependenciesByGraphResponse.getAck().value().equalsIgnoreCase("failure")) {
            if (getAssetDependenciesByGraphResponse.getErrorMessage().getError().size() > 0) {
               return "success";
            }
         }
      }

      return "failure";
   }

   private static void printRelationship(GraphRelationship graphRelationship, int treeDepth) {
      List<TypedRelationNode> typedRelationNodes = graphRelationship.getTargetAsset();

      for (TypedRelationNode typedRelationNode : typedRelationNodes) {
         for (int i = 0; i < treeDepth; i++) {
            System.out.print("    ");
         }

         System.out.println(typedRelationNode.getAssetRelationship());
         printRelationship(typedRelationNode.getTarget(), treeDepth + 1);
      }
   }

   @Override
   protected AsyncTurmericRSV1 getProxy() throws ServiceException {
      if (m_proxy == null) {
         String svcAdminName = RepositoryServiceClientConstants.SERVICE_NAME;
         Service service = ServiceFactory.create(svcAdminName, RepositoryServiceClientConstants.SERVICE_NAME);
         service.setSessionTransportHeader("X-TURMERIC-SECURITY-USERID", RepositoryServiceClientConstants.USER_ID);
         service.setSessionTransportHeader("X-TURMERIC-SECURITY-PASSWORD",
                  RepositoryServiceClientConstants.USER_PASSWORD);

         m_proxy = service.getProxy();
      }

      return m_proxy;
   }
}
