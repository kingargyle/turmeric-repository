/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.v2.services.impl.AsyncTurmericRSV2;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class SearchAssetConsumer extends BaseRepositoryServiceConsumer {
   private static SearchAssetConsumer searchAssetConsumer = new SearchAssetConsumer();
   private static String securityCookie = null;
   private static Service service = null;

   public static void main(String[] args) {
      Map<String, String> results = new HashMap<String, String>();

      results.put("testSearchAsset_withLibrary_andConjunction_relationCriteria",
               testSearchAsset_withLibrary_andConjunction_relationCriteria());

      System.out.println("\nsearchAsset Summary");
      Set<String> methods = results.keySet();
      for (String method : methods) {
         System.out.format("%-60s: %s\n", method, results.get(method));
      }
      System.out.println();
   }

   public static String testSearchAsset_withLibrary_andConjunction_relationCriteria() {

      AssetQuery assetQuery = new AssetQuery();
      AttributeCriteria attributeCriteria = new AttributeCriteria();
      Attribute attribute = new Attribute();

      attribute.setAttributeName("asset-type");
      attribute.setAttributeValue("Service");
      attributeCriteria.getAttribute().add(attribute);

      Attribute attribute1 = new Attribute();
      attribute1.setAttributeName("service_layer");
      attribute1.setAttributeValue("BUSINESS");
      attributeCriteria.getAttribute().add(attribute1);

      attributeCriteria.setConjunction(Conjunction.AND);

      assetQuery.setAttributeCriteria(attributeCriteria);

      RelationshipCriteria relationshipCriteria = new RelationshipCriteria();
      RelationshipCriterion relationshipCriterion = new RelationshipCriterion();
      relationshipCriterion.setRelatedAssetName("TempFunctionalDomain");
      relationshipCriterion.setRelationShipType("functionalDomain");
      relationshipCriteria.getRelationshipCriterion().add(relationshipCriterion);
      relationshipCriteria.setConjunction(Conjunction.AND);
      assetQuery.setRelationshipCriteria(relationshipCriteria);

      assetQuery.setConjunction(Conjunction.valueOf("AND"));
      SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
      searchAssetsRequest.setAssetQuery(assetQuery);

      try {
         SearchAssetsResponse searchAssetsResponse = searchAssetConsumer.getProxy().searchAssets(searchAssetsRequest);
         // System.out.println("SIZE: " + searchAssetsResponse.getAssetKey().size());
         if (searchAssetsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateSearchAssetsResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(
                  RepositoryServiceClientConstants.SUCCESS)) {
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

   public static String testSearchAsset_Without_AttributeCriteria_withLibrary() {

      AssetQuery assetQuery = new AssetQuery();
      SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
      searchAssetsRequest.setAssetQuery(assetQuery);

      try {
         SearchAssetsResponse searchAssetsResponse = searchAssetConsumer.getProxy().searchAssets(searchAssetsRequest);
         // System.out.println("SIZE: " + searchAssetsResponse.getAssetKey().size());
         if (searchAssetsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateSearchAssetsResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(
                  RepositoryServiceClientConstants.SUCCESS)) {
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

   public static String testSearchAsset_Without_AttributeCriteria_withoutLibrary() {

      AssetQuery assetQuery = new AssetQuery();

      SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
      searchAssetsRequest.setAssetQuery(assetQuery);

      try {
         SearchAssetsResponse searchAssetsResponse = searchAssetConsumer.getProxy().searchAssets(searchAssetsRequest);
         // System.out.println("SIZE: " + searchAssetsResponse.getAssetKey().size());
         if (searchAssetsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateSearchAssetsResponse(searchAssetsResponse, RepositoryServiceClientConstants.PARTIAL_CASE)
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

   public static String testSearchAsset_with_SingleAttribute_Criteria() {

      AssetQuery assetQuery = new AssetQuery();
      AttributeCriteria attributeCriteria = new AttributeCriteria();
      Attribute attribute = new Attribute();

      attribute.setAttributeName("lifecycle_state");
      attribute.setAttributeValue("Exit Approved");
      attributeCriteria.getAttribute().add(attribute);

      assetQuery.setAttributeCriteria(attributeCriteria);
      assetQuery.setConjunction(Conjunction.valueOf("AND"));
      SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
      searchAssetsRequest.setAssetQuery(assetQuery);

      try {
         SearchAssetsResponse searchAssetsResponse = searchAssetConsumer.getProxy().searchAssets(searchAssetsRequest);
         // System.out.println("SIZE: " + searchAssetsResponse.getAssetKey().size());
         if (searchAssetsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateSearchAssetsResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(
                  RepositoryServiceClientConstants.SUCCESS)) {
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

   public static String testSearchAsset_withLibrary_andConjunction() {

      AssetQuery assetQuery = new AssetQuery();
      AttributeCriteria attributeCriteria = new AttributeCriteria();
      Attribute attribute = new Attribute();

      attribute.setAttributeName("layer");
      attribute.setAttributeValue("Common Service");
      attribute.setAttributeValue("Proposed");
      attributeCriteria.getAttribute().add(attribute);

      Attribute attribute1 = new Attribute();
      attribute1.setAttributeName("owner");
      attribute1.setAttributeValue("ramurthy");
      attributeCriteria.getAttribute().add(attribute1);

      attributeCriteria.setConjunction(Conjunction.OR);

      assetQuery.setAttributeCriteria(attributeCriteria);
      assetQuery.setConjunction(Conjunction.valueOf("AND"));
      SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
      searchAssetsRequest.setAssetQuery(assetQuery);

      try {
         SearchAssetsResponse searchAssetsResponse = searchAssetConsumer.getProxy().searchAssets(searchAssetsRequest);
         // System.out.println("SIZE: " + searchAssetsResponse.getAssetKey().size());
         if (searchAssetsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateSearchAssetsResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(
                  RepositoryServiceClientConstants.SUCCESS)) {
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

   public static String testSearchAsset_withLibrary_orConjunction() {

      AssetQuery assetQuery = new AssetQuery();
      AttributeCriteria attributeCriteria = new AttributeCriteria();
      Attribute attribute = new Attribute();

      attribute.setAttributeName("lifecycle_state");
      attribute.setAttributeValue("Approved");
      attributeCriteria.getAttribute().add(attribute);

      Attribute attribute1 = new Attribute();
      attribute1.setAttributeName("asset-type");
      attribute1.setAttributeValue("Service");
      attributeCriteria.getAttribute().add(attribute1);

      attributeCriteria.setConjunction(Conjunction.OR);
      assetQuery.setAttributeCriteria(attributeCriteria);
      assetQuery.setConjunction(Conjunction.valueOf("OR"));
      SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
      searchAssetsRequest.setAssetQuery(assetQuery);

      try {
         SearchAssetsResponse searchAssetsResponse = searchAssetConsumer.getProxy().searchAssets(searchAssetsRequest);
         if (searchAssetsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateSearchAssetsResponse(searchAssetsResponse, "positiveCase").equalsIgnoreCase(
                  RepositoryServiceClientConstants.SUCCESS)) {
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

   public static String testSearchAsset_withoutLibrary_andConjunction() {
      // SearchAssetConsumer searchAssetConsumer = new SearchAssetConsumer();

      AssetQuery assetQuery = new AssetQuery();
      AttributeCriteria attributeCriteria = new AttributeCriteria();
      Attribute attribute = new Attribute();

      attribute.setAttributeName("lifecycle_state");
      attribute.setAttributeValue("Approved");
      attributeCriteria.getAttribute().add(attribute);

      Attribute attribute1 = new Attribute();
      attribute1.setAttributeName("asset-type");
      attribute1.setAttributeValue("Service");
      attributeCriteria.getAttribute().add(attribute1);

      attributeCriteria.setConjunction(Conjunction.AND);
      assetQuery.setAttributeCriteria(attributeCriteria);
      assetQuery.setConjunction(Conjunction.valueOf("AND"));
      SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
      searchAssetsRequest.setAssetQuery(assetQuery);

      /*
       * Library library = new Library(); library.setLibraryName(RepositoryServiceClientConstants.LIBRARY_NAME);
       * searchAssetsRequest.setLibrary(library);
       */

      try {
         SearchAssetsResponse searchAssetsResponse = searchAssetConsumer.getProxy().searchAssets(searchAssetsRequest);
         if (searchAssetsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateSearchAssetsResponse(searchAssetsResponse, RepositoryServiceClientConstants.PARTIAL_CASE)
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

   public static String testSearchAsset_withoutLibrary_orConjunction() {
      // SearchAssetConsumer searchAssetConsumer = new SearchAssetConsumer();

      AssetQuery assetQuery = new AssetQuery();
      AttributeCriteria attributeCriteria = new AttributeCriteria();
      Attribute attribute = new Attribute();

      attribute.setAttributeName("lifecycle_state");
      attribute.setAttributeValue("InReview");
      attributeCriteria.getAttribute().add(attribute);

      Attribute attribute1 = new Attribute();
      attribute1.setAttributeName("lifecycle_state");
      attribute1.setAttributeValue("Approved");
      attributeCriteria.getAttribute().add(attribute1);

      Attribute attribute2 = new Attribute();
      attribute2.setAttributeName("lifecycle_state");
      attribute2.setAttributeValue("Proposed");
      // attributeCriteria.getAttribute().add(attribute2);

      attributeCriteria.setConjunction(Conjunction.OR);
      assetQuery.setAttributeCriteria(attributeCriteria);
      assetQuery.setConjunction(Conjunction.valueOf("OR"));
      SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
      searchAssetsRequest.setAssetQuery(assetQuery);

      try {
         SearchAssetsResponse searchAssetsResponse = searchAssetConsumer.getProxy().searchAssets(searchAssetsRequest);
         if (searchAssetsResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (validateSearchAssetsResponse(searchAssetsResponse, RepositoryServiceClientConstants.PARTIAL_CASE)
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
      if (service == null) {
         service = ServiceFactory.create(RepositoryServiceClientConstants.SERVICE_NAME,
                  RepositoryServiceClientConstants.SERVICE_NAME);
      }

      // get security cookie after first successful login
      if (securityCookie == null) {
         securityCookie = service.getResponseContext().getTransportHeader("X-TURMERIC-SECURITY-COOKIE");
      }

      // Use security cookie if present or use userid/password
      RequestContext requestContext = service.getRequestContext();
      if (securityCookie != null) {
         // logger.debug("Found X-TURMERIC-SECURITY-COOKIE="+securityCookie);
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-COOKIE", securityCookie);
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-USERID", "");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-PASSWORD", "");
      } else {
         // logger.debug("Using password header, did not find X-TURMERIC-SECURITY-COOKIE");
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-USERID", RepositoryServiceClientConstants.USER_ID);
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-PASSWORD",
                  RepositoryServiceClientConstants.USER_PASSWORD);
      }
      return (AsyncTurmericRSV2) service.getProxy();
   }

   private static String validateSearchAssetsResponse(SearchAssetsResponse searchAssetsResponse, String criteria) {
      if (criteria.equalsIgnoreCase("positiveCase")) {
         if (searchAssetsResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
            List<AssetBaseInfo> assetBaseInfoList = searchAssetsResponse.getAssetBaseInfo();
            if (assetBaseInfoList != null) {
               for (AssetBaseInfo assetBaseInfo : assetBaseInfoList) {
                  return RepositoryServiceConsumerUtil.validateAssetBaseInfo(assetBaseInfo);
               }
            }
            return RepositoryServiceClientConstants.SUCCESS;
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_CASE)) {
         if (searchAssetsResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.PARTIAL_FAILURE)) {
            List<AssetBaseInfo> assetBaseInfoList = searchAssetsResponse.getAssetBaseInfo();
            if (assetBaseInfoList != null) {
               for (AssetBaseInfo assetBaseInfo : assetBaseInfoList) {
                  return RepositoryServiceConsumerUtil.validateAssetBaseInfo(assetBaseInfo);
               }
            }
            return RepositoryServiceClientConstants.SUCCESS;
         }
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (criteria.equalsIgnoreCase("negativeCase")) {
         if (searchAssetsResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            if (searchAssetsResponse.getErrorMessage().getError().size() > 0) {
               return RepositoryServiceClientConstants.SUCCESS;
            }
         }
      }

      return RepositoryServiceClientConstants.FAILURE;
   }
}
