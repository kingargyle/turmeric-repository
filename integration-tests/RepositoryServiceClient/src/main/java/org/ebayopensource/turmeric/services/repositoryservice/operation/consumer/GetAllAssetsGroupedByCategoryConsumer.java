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

import org.ebayopensource.turmeric.repository.v1.services.AssetQuery;
import org.ebayopensource.turmeric.repository.v1.services.AssetsGroupedByCategory;
import org.ebayopensource.turmeric.repository.v1.services.Attribute;
import org.ebayopensource.turmeric.repository.v1.services.AttributeCriteria;
import org.ebayopensource.turmeric.repository.v1.services.Conjunction;
import org.ebayopensource.turmeric.repository.v1.services.GetAllAssetsGroupedByCategoryRequest;
import org.ebayopensource.turmeric.repository.v1.services.GetAllAssetsGroupedByCategoryResponse;
import org.ebayopensource.turmeric.repository.v1.services.Library;
import org.ebayopensource.turmeric.repository.v1.services.repositoryservice.impl.AsyncTurmericRSV1;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class GetAllAssetsGroupedByCategoryConsumer extends BaseRepositoryServiceConsumer {

   private AsyncTurmericRSV1 m_proxy = null;

   public static String testGetAllAssetsGroupedByCategory_withCategorizingClassifer() {
      GetAllAssetsGroupedByCategoryConsumer consumer = new GetAllAssetsGroupedByCategoryConsumer();

      Library library = new Library();
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      AssetQuery assetQuery = new AssetQuery();
      AttributeCriteria attributeCriteria = new AttributeCriteria();
      Attribute attribute = new Attribute();

      attribute.setAttributeName("lifecycle_state");
      attribute.setAttributeValue("Exit Approved");
      attributeCriteria.getAttribute().add(attribute);

      Attribute attribute1 = new Attribute();
      attribute1.setAttributeName("asset-type");
      attribute1.setAttributeValue("Service");
      attributeCriteria.getAttribute().add(attribute1);

      Attribute attribute2 = new Attribute();
      attribute2.setAttributeName("asset-type");
      attribute2.setAttributeValue("Data_Types");
      attributeCriteria.getAttribute().add(attribute2);

      attributeCriteria.setConjunction(Conjunction.AND);

      assetQuery.setAttributeCriteria(attributeCriteria);
      assetQuery.setConjunction(Conjunction.valueOf("AND"));
      GetAllAssetsGroupedByCategoryRequest getAllAssetsGroupedByCategoryRequest = new GetAllAssetsGroupedByCategoryRequest();
      getAllAssetsGroupedByCategoryRequest.setLibrary(library);
      getAllAssetsGroupedByCategoryRequest.setAssetQuery(assetQuery);
      getAllAssetsGroupedByCategoryRequest.setCategorizingClassifer("service_layer");

      try {
         GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategoryResponse = consumer.getProxy()
                  .getAllAssetsGroupedByCategory(getAllAssetsGroupedByCategoryRequest);
         if (getAllAssetsGroupedByCategoryResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetAllAssetsGroupedByCategoryResponse(getAllAssetsGroupedByCategoryResponse, "positiveCase")
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

   public static String testGetAllAssetsGroupedByCategory_withoutCategorizingClassifer() {
      GetAllAssetsGroupedByCategoryConsumer consumer = new GetAllAssetsGroupedByCategoryConsumer();

      Library library = new Library();
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      AssetQuery assetQuery = new AssetQuery();
      AttributeCriteria attributeCriteria = new AttributeCriteria();
      Attribute attribute = new Attribute();

      attribute.setAttributeName("lifecycle_state");
      attribute.setAttributeValue("Exit Approved");
      attributeCriteria.getAttribute().add(attribute);

      Attribute attribute1 = new Attribute();
      attribute1.setAttributeName("asset-type");
      attribute1.setAttributeValue("Service");
      attributeCriteria.getAttribute().add(attribute1);

      Attribute attribute2 = new Attribute();
      attribute2.setAttributeName("asset-type");
      attribute2.setAttributeValue("Data_Types");
      attributeCriteria.getAttribute().add(attribute2);

      attributeCriteria.setConjunction(Conjunction.AND);

      assetQuery.setAttributeCriteria(attributeCriteria);
      assetQuery.setConjunction(Conjunction.valueOf("AND"));
      GetAllAssetsGroupedByCategoryRequest getAllAssetsGroupedByCategoryRequest = new GetAllAssetsGroupedByCategoryRequest();
      getAllAssetsGroupedByCategoryRequest.setLibrary(library);
      getAllAssetsGroupedByCategoryRequest.setAssetQuery(assetQuery);

      try {
         GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategoryResponse = consumer.getProxy()
                  .getAllAssetsGroupedByCategory(getAllAssetsGroupedByCategoryRequest);
         if (getAllAssetsGroupedByCategoryResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetAllAssetsGroupedByCategoryResponse(getAllAssetsGroupedByCategoryResponse, "positiveCase")
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

   public static String testGetAllAssetsGroupedByCategory_withInvalidCategorizingClassifer() {
      GetAllAssetsGroupedByCategoryConsumer consumer = new GetAllAssetsGroupedByCategoryConsumer();

      Library library = new Library();
      library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);

      AssetQuery assetQuery = new AssetQuery();
      AttributeCriteria attributeCriteria = new AttributeCriteria();
      Attribute attribute = new Attribute();

      attribute.setAttributeName("lifecycle_state");
      attribute.setAttributeValue("Exit Approved");
      attributeCriteria.getAttribute().add(attribute);

      Attribute attribute1 = new Attribute();
      attribute1.setAttributeName("asset-type");
      attribute1.setAttributeValue("Service");
      attributeCriteria.getAttribute().add(attribute1);

      Attribute attribute2 = new Attribute();
      attribute2.setAttributeName("asset-type");
      attribute2.setAttributeValue("Data_Types");
      attributeCriteria.getAttribute().add(attribute2);

      attributeCriteria.setConjunction(Conjunction.AND);

      assetQuery.setAttributeCriteria(attributeCriteria);
      assetQuery.setConjunction(Conjunction.valueOf("AND"));
      GetAllAssetsGroupedByCategoryRequest getAllAssetsGroupedByCategoryRequest = new GetAllAssetsGroupedByCategoryRequest();
      getAllAssetsGroupedByCategoryRequest.setLibrary(library);
      getAllAssetsGroupedByCategoryRequest.setAssetQuery(assetQuery);
      getAllAssetsGroupedByCategoryRequest.setCategorizingClassifer("Invalid classifier");

      try {
         GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategoryResponse = consumer.getProxy()
                  .getAllAssetsGroupedByCategory(getAllAssetsGroupedByCategoryRequest);
         if (getAllAssetsGroupedByCategoryResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateGetAllAssetsGroupedByCategoryResponse(getAllAssetsGroupedByCategoryResponse, "Warning")
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

   private static String validateGetAllAssetsGroupedByCategoryResponse(
            GetAllAssetsGroupedByCategoryResponse getAllAssetsGroupedByCategoryResponse, String criteria) {

      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (getAllAssetsGroupedByCategoryResponse.getAck().value()
                  .equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            List<AssetsGroupedByCategory> assetsByCategory = getAllAssetsGroupedByCategoryResponse
                     .getAssetsGroupedByCategory();
            if (assetsByCategory != null) {
               for (AssetsGroupedByCategory asset : assetsByCategory) {
                  return RepositoryServiceConsumerUtil.validateAssetsGroupedByCategory(asset);
               }
            }
            return RepositoryServiceClientConstants.SUCCESS;
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("Warning")) {
         if (getAllAssetsGroupedByCategoryResponse.getAck().value().equalsIgnoreCase("Warning")) {
            if (getAllAssetsGroupedByCategoryResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
            return RepositoryServiceClientConstants.SUCCESS;
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (getAllAssetsGroupedByCategoryResponse.getAck().value()
                  .equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            if (getAllAssetsGroupedByCategoryResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
      }

      return RepositoryServiceClientConstants.FAILURE;
   }
}
