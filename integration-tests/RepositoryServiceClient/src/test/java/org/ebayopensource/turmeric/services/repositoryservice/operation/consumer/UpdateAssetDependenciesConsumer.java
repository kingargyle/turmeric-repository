/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.AsyncTurmericRSV2;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class UpdateAssetDependenciesConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV2 m_proxy = null;

   public static String testUpdateAssetDependencies_validInput(AssetInfo sourceAssetInfo, AssetInfo targetAssetInfo,
            AssetInfo currentTargetAssetInfo) {
      UpdateAssetDependenciesConsumer updateAssetDependenciesConsumer = new UpdateAssetDependenciesConsumer();
      UpdateAssetDependenciesRequest updateAssetDependenciesRequest = new UpdateAssetDependenciesRequest();

      updateAssetDependenciesRequest.setReplaceCurrent(true);
      FlattenedRelationshipForUpdate flattenedRelationshipForUpdate = new FlattenedRelationshipForUpdate();

      AssetKey sourceAssetKey = new AssetKey();
      sourceAssetKey.setAssetId(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      sourceAssetKey.setAssetName(sourceAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      flattenedRelationshipForUpdate.setSourceAsset(sourceAssetKey);

      updateAssetDependenciesRequest.setAssetKey(sourceAssetKey);

      RelationForUpdate relationForUpdate1 = new RelationForUpdate();

      relationForUpdate1.setCurrentSourceAsset(sourceAssetKey);

      AssetKey currentTargetAssetKey = new AssetKey();
      currentTargetAssetKey.setAssetId(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      currentTargetAssetKey.setAssetName(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      relationForUpdate1.setCurrentTargetAsset(currentTargetAssetKey);

      Relation relation1 = new Relation();
      relation1.setAssetRelationship("dependsOn");

      AssetKey targetAssetKey = new AssetKey();
      targetAssetKey.setAssetId(targetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      targetAssetKey.setAssetName(targetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

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

      flattenedRelationshipForUpdate.setSourceAsset(sourceAssetKey);

      updateAssetDependenciesRequest.setAssetKey(sourceAssetKey);

      RelationForUpdate relationForUpdate1 = new RelationForUpdate();

      relationForUpdate1.setCurrentSourceAsset(sourceAssetKey);

      AssetKey currentTargetAssetKey = new AssetKey();
      currentTargetAssetKey.setAssetId(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      currentTargetAssetKey.setAssetName(currentTargetAssetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

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
   protected AsyncTurmericRSV2 getProxy() throws ServiceException {
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
