/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationshipForUpdate;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.Relation;
import org.ebayopensource.turmeric.repository.v1.services.RelationForUpdate;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesRequest;
import org.ebayopensource.turmeric.repository.v1.services.UpdateAssetDependenciesResponse;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class UpdateAssetDependenciesConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV1 m_proxy = null;

   public static String testUpdateAssetDependencies_validInput(AssetInfo sourceAssetInfo, AssetInfo targetAssetInfo,
            AssetInfo currentTargetAssetInfo) {
      UpdateAssetDependenciesConsumer updateAssetDependenciesConsumer = new UpdateAssetDependenciesConsumer();
      UpdateAssetDependenciesRequest updateAssetDependenciesRequest = new UpdateAssetDependenciesRequest();

      updateAssetDependenciesRequest.setReplaceCurrent(true);
      FlattenedRelationshipForUpdate flattenedRelationshipForUpdate = new FlattenedRelationshipForUpdate();

      AssetKey sourceAssetKey = new AssetKey();
      sourceAssetKey.setAssetId(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      sourceAssetKey.setAssetName(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library1 = new Library();
      library1.setLibraryId(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library1.setLibraryName(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      sourceAssetKey.setLibrary(library1);

      flattenedRelationshipForUpdate.setSourceAsset(sourceAssetKey);

      updateAssetDependenciesRequest.setAssetKey(sourceAssetKey);
      /*
       * RelationForUpdate relationForUpdate = new RelationForUpdate(); relationForUpdate.setCurrentSourceAsset(null);
       * relationForUpdate.setCurrentTargetAsset(null); Relation relation = new Relation();
       * relation.setAssetRelationship("dependent_service"); AssetKey targetAssetKey = new AssetKey();
       * targetAssetKey.setAssetId("1.0_1228437745125312141751"); targetAssetKey.setLibrary(library);
       * relation.setTargetAsset(targetAssetKey); relationForUpdate.setNewRelation(relation);
       * flattenedRelationshipForUpdate.getRelatedAsset().add(relationForUpdate);
       */

      RelationForUpdate relationForUpdate1 = new RelationForUpdate();

      relationForUpdate1.setCurrentSourceAsset(sourceAssetKey);

      AssetKey currentTargetAssetKey = new AssetKey();
      currentTargetAssetKey.setAssetId(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      currentTargetAssetKey.setAssetName(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library3 = new Library();
      library3.setLibraryId(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library3.setLibraryName(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      currentTargetAssetKey.setLibrary(library3);

      relationForUpdate1.setCurrentTargetAsset(currentTargetAssetKey);

      Relation relation1 = new Relation();
      relation1.setAssetRelationship("dependsOn");

      AssetKey targetAssetKey = new AssetKey();
      targetAssetKey.setAssetId(targetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      targetAssetKey.setAssetName(targetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library4 = new Library();
      library4.setLibraryId(targetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library4.setLibraryName(targetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      targetAssetKey.setLibrary(library4);

      relation1.setTargetAsset(targetAssetKey);
      relationForUpdate1.setNewRelation(relation1);
      flattenedRelationshipForUpdate.getRelatedAsset().add(relationForUpdate1);

      updateAssetDependenciesRequest.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);

      try {
         UpdateAssetDependenciesResponse updateAssetDependenciesResponse = updateAssetDependenciesConsumer.getProxy()
                  .updateAssetDependencies(updateAssetDependenciesRequest);
         if (updateAssetDependenciesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (updateAssetDependenciesResponse.getAck().value()
                  .equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      }
   }

   public static String testUpdateAssetDependencies_deleteRelationship(AssetInfo sourceAssetInfo,
            AssetInfo currentTargetAssetInfo) {
      UpdateAssetDependenciesConsumer updateAssetDependenciesConsumer = new UpdateAssetDependenciesConsumer();
      UpdateAssetDependenciesRequest updateAssetDependenciesRequest = new UpdateAssetDependenciesRequest();

      updateAssetDependenciesRequest.setReplaceCurrent(true);
      FlattenedRelationshipForUpdate flattenedRelationshipForUpdate = new FlattenedRelationshipForUpdate();

      AssetKey sourceAssetKey = new AssetKey();
      sourceAssetKey.setAssetId(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      sourceAssetKey.setAssetName(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library1 = new Library();
      library1.setLibraryId(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library1.setLibraryName(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      sourceAssetKey.setLibrary(library1);

      flattenedRelationshipForUpdate.setSourceAsset(sourceAssetKey);

      updateAssetDependenciesRequest.setAssetKey(sourceAssetKey);
      /*
       * RelationForUpdate relationForUpdate = new RelationForUpdate(); relationForUpdate.setCurrentSourceAsset(null);
       * relationForUpdate.setCurrentTargetAsset(null); Relation relation = new Relation();
       * relation.setAssetRelationship("dependent_service"); AssetKey targetAssetKey = new AssetKey();
       * targetAssetKey.setAssetId("1.0_1228437745125312141751"); targetAssetKey.setLibrary(library);
       * relation.setTargetAsset(targetAssetKey); relationForUpdate.setNewRelation(relation);
       * flattenedRelationshipForUpdate.getRelatedAsset().add(relationForUpdate);
       */

      RelationForUpdate relationForUpdate1 = new RelationForUpdate();

      relationForUpdate1.setCurrentSourceAsset(sourceAssetKey);

      AssetKey currentTargetAssetKey = new AssetKey();
      currentTargetAssetKey.setAssetId(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      currentTargetAssetKey.setAssetName(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library3 = new Library();
      library3.setLibraryId(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library3.setLibraryName(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      currentTargetAssetKey.setLibrary(library3);

      relationForUpdate1.setCurrentTargetAsset(currentTargetAssetKey);
      relationForUpdate1.setDeleteRelation(new Boolean("true"));
      flattenedRelationshipForUpdate.getRelatedAsset().add(relationForUpdate1);

      updateAssetDependenciesRequest.setFlattenedRelationshipForUpdate(flattenedRelationshipForUpdate);

      try {
         UpdateAssetDependenciesResponse updateAssetDependenciesResponse = updateAssetDependenciesConsumer.getProxy()
                  .updateAssetDependencies(updateAssetDependenciesRequest);
         if (updateAssetDependenciesResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (updateAssetDependenciesResponse.getAck().value()
                  .equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            return RepositoryServiceClientConstants.PASS;
         } else {
            return RepositoryServiceClientConstants.FAIL;
         }
      } catch (ServiceException se) {
         se.getMessage();
         se.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
      } catch (Exception e) {
         e.printStackTrace();
         return RepositoryServiceClientConstants.FAIL;
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
