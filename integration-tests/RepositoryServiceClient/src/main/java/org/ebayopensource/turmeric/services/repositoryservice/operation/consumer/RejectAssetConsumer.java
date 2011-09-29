/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import org.ebayopensource.turmeric.repository.v1.services.AssetDetail;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.RejectAssetRequest;
import org.ebayopensource.turmeric.repository.v1.services.RejectAssetResponse;
import org.ebayopensource.turmeric.repository.v1.services.RejectionInfo;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class RejectAssetConsumer extends BaseRepositoryServiceConsumer {
   private AsyncTurmericRSV1 m_proxy = null;

   public static String testGetAssetStatus_validAsset_WithName(AssetInfo assetInfo) {
      ApproveAssetConsumer approveAssetConsumer = new ApproveAssetConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library);

      AssetDetail assetDetail = new AssetDetail();
      assetDetail.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
      assetDetail.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
      assetDetail.setVersion(assetInfo.getBasicAssetInfo().getVersion());

      RejectAssetRequest rejectAssetRequest = new RejectAssetRequest();
      RejectionInfo rejectionInfo = new RejectionInfo();
      rejectionInfo.setLibrary(library);
      rejectionInfo.setAssetDetail(assetDetail);
      rejectionInfo.setRejectionRole(RepositoryServiceClientConstants.VALID_APPROVAL_ROLE);
      rejectAssetRequest.setRejectionInfo(rejectionInfo);

      try {
         RejectAssetResponse rejectAssetResponse = approveAssetConsumer.getProxy().rejectAsset(rejectAssetRequest);
         if (rejectAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateRejectAssetResponse(rejectAssetResponse, "positive").equalsIgnoreCase("success")) {
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

   public static String testGetAssetStatus_validAsset_WithId(AssetInfo assetInfo) {
      ApproveAssetConsumer approveAssetConsumer = new ApproveAssetConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library);

      AssetDetail assetDetail = new AssetDetail();
      assetDetail.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
      assetDetail.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
      assetDetail.setVersion(assetInfo.getBasicAssetInfo().getVersion());

      RejectAssetRequest rejectAssetRequest = new RejectAssetRequest();
      RejectionInfo rejectionInfo = new RejectionInfo();
      rejectionInfo.setLibrary(library);
      rejectionInfo.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      // rejectionInfo.setAssetDetail(assetDetail);
      rejectionInfo.setRejectionRole(RepositoryServiceClientConstants.VALID_APPROVAL_ROLE);
      rejectAssetRequest.setRejectionInfo(rejectionInfo);

      try {
         RejectAssetResponse rejectAssetResponse = approveAssetConsumer.getProxy().rejectAsset(rejectAssetRequest);
         if (rejectAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateRejectAssetResponse(rejectAssetResponse, "positive").equalsIgnoreCase("success")) {
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

   public static String testGetAssetStatus_invalidRole(AssetInfo assetInfo) {
      ApproveAssetConsumer approveAssetConsumer = new ApproveAssetConsumer();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      Library library = new Library();
      library.setLibraryId(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryId());
      library.setLibraryName(assetInfo.getBasicAssetInfo().getAssetKey().getLibrary().getLibraryName());
      assetKey.setLibrary(library);

      AssetDetail assetDetail = new AssetDetail();
      assetDetail.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
      assetDetail.setAssetType(assetInfo.getBasicAssetInfo().getAssetType());
      assetDetail.setVersion(assetInfo.getBasicAssetInfo().getVersion());
      RejectAssetRequest rejectAssetRequest = new RejectAssetRequest();
      RejectionInfo rejectionInfo = new RejectionInfo();
      rejectionInfo.setLibrary(library);
      rejectionInfo.setAssetDetail(assetDetail);
      rejectionInfo.setRejectionRole(RepositoryServiceClientConstants.INVALID_APPROVAL_ROLE);
      rejectAssetRequest.setRejectionInfo(rejectionInfo);

      try {
         RejectAssetResponse rejectAssetResponse = approveAssetConsumer.getProxy().rejectAsset(rejectAssetRequest);
         if (rejectAssetResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateRejectAssetResponse(rejectAssetResponse, "negative").equalsIgnoreCase("success")) {
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

   public static String validateRejectAssetResponse(RejectAssetResponse rejectAssetResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (rejectAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            return RepositoryServiceClientConstants.SUCCESS;
         }
         if (rejectAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (rejectAssetResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            if (rejectAssetResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
      }

      return RepositoryServiceClientConstants.FAILURE;
   }

}
