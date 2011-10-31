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
import org.ebayopensource.turmeric.repository.v2.services.impl.TurmericRSV2;
import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

import org.ebayopensource.turmeric.runtime.sif.service.RequestContext;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.sif.service.Service;
import org.ebayopensource.turmeric.runtime.sif.service.ServiceFactory;

public class SubmitForPublishingConsumer extends BaseRepositoryServiceConsumer {
   private TurmericRSV2 m_proxy = null;
   // private static SearchAssetConsumer searchAssetConsumer = new SearchAssetConsumer();
   private static String securityCookie = null;
   private static Service service1 = null;

   public static String testSubmitForPublishing_validAsset_lockedByUser(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      submitForPublishingRequest.setAssetKey(assetKey);
      Property approverProperty = new Property();
      approverProperty.setPropertyName("RolePlayerProperty");
      approverProperty.setPropertyValue("_regNormalUser");
      submitForPublishingRequest.getRequestProperties().add(approverProperty);
      submitForPublishingRequest.setComment("Test Comment1");
      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
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

   public static String testSubmitForPublishing_incompleteAsset(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());

      submitForPublishingRequest.setAssetKey(assetKey);
      Property approverProperty = new Property();
      approverProperty.setPropertyName("RolePlayerProperty");
      approverProperty.setPropertyValue("_regNormalUser");
      submitForPublishingRequest.getRequestProperties().add(approverProperty);
      submitForPublishingRequest.setComment("Test Comment1");
      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
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

   public static String testSubmitForPublishing_withEmptyAssetKey() {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();

      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
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

   public static String testSubmitForPublishing_withoutLibraryAndAssetId(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();
      AssetKey assetKey = new AssetKey();
      submitForPublishingRequest.setAssetKey(assetKey);

      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
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

   public static String testSubmitForPublishing_withoutLibraryName(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      submitForPublishingRequest.setAssetKey(assetKey);

      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
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

   public static String testSubmitForPublishing_validAsset_unlocked(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      submitForPublishingRequest.setAssetKey(assetKey);
      Property approverProperty = new Property();
      approverProperty.setPropertyName("RolePlayerProperty");
      approverProperty.setPropertyValue("_regNormalUser");
      submitForPublishingRequest.getRequestProperties().add(approverProperty);
      submitForPublishingRequest.setComment("Test Comment1");
      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
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

   public static String testSubmitForPublishing_validAsset_libNotLogged(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      submitForPublishingRequest.setAssetKey(assetKey);
      Property approverProperty = new Property();
      approverProperty.setPropertyName("RolePlayerProperty");
      approverProperty.setPropertyValue("_regNormalUser");
      submitForPublishingRequest.getRequestProperties().add(approverProperty);
      submitForPublishingRequest.setComment("Test Comment1");
      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
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

   public static String testSubmitForPublishing_validAsset_lockedForEdit(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());

      submitForPublishingRequest.setAssetKey(assetKey);
      Property approverProperty = new Property();
      approverProperty.setPropertyName("RolePlayerProperty");
      approverProperty.setPropertyValue("_regNormalUser");
      submitForPublishingRequest.getRequestProperties().add(approverProperty);
      submitForPublishingRequest.setComment("Test Comment1");
      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.SUCCESS)) {
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

   public static String testSubmitForPublishing_invalidLogin(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();

      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      assetKey.setAssetName(assetInfo.getBasicAssetInfo().getAssetKey().getAssetName());
      submitForPublishingRequest.setAssetKey(assetKey);
      Property approverProperty = new Property();
      approverProperty.setPropertyName("RolePlayerProperty");
      approverProperty.setPropertyValue("_regNormalUser");
      submitForPublishingRequest.getRequestProperties().add(approverProperty);
      submitForPublishingRequest.setComment("Test Comment1");
      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getInvalidProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
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

   public static String testSubmitForPublishing_emptyHeader(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      submitForPublishingRequest.setAssetKey(assetKey);
      Property approverProperty = new Property();
      approverProperty.setPropertyName("RolePlayerProperty");
      approverProperty.setPropertyValue("_regNormalUser");
      submitForPublishingRequest.getRequestProperties().add(approverProperty);
      submitForPublishingRequest.setComment("Test Comment1");
      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getEmptyProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
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

   public static String testSubmitForPublishing_inValidSession(AssetInfo assetInfo) {
      SubmitForPublishingConsumer submitForPublishingConsumer = new SubmitForPublishingConsumer();
      SubmitForPublishingRequest submitForPublishingRequest = new SubmitForPublishingRequest();
      AssetKey assetKey = new AssetKey();
      assetKey.setAssetId(assetInfo.getBasicAssetInfo().getAssetKey().getAssetId());
      Property approverProperty = new Property();
      approverProperty.setPropertyName("RolePlayerProperty");
      approverProperty.setPropertyValue("_regNormalUser");
      submitForPublishingRequest.getRequestProperties().add(approverProperty);
      submitForPublishingRequest.setComment("Test Comment1");
      try {
         SubmitForPublishingResponse submitForPublishingResponse = submitForPublishingConsumer.getExpiredProxy()
                  .submitForPublishing(submitForPublishingRequest);
         if (submitForPublishingResponse == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }
         if (submitForPublishingResponse.getAck().value().equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
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
      if (service1 == null) {
         service1 = ServiceFactory.create(RepositoryServiceClientConstants.SERVICE_NAME,
                  RepositoryServiceClientConstants.SERVICE_NAME);
      }

      // get security cookie after first successful login
      if (securityCookie == null) {
         securityCookie = service1.getResponseContext().getTransportHeader("X-TURMERIC-SECURITY-COOKIE");
      }

      // Use security cookie if present or use userid/password
      RequestContext requestContext = service1.getRequestContext();
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
      return (AsyncTurmericRSV2) service1.getProxy();
   }

   protected TurmericRSV2 getInvalidProxy() throws ServiceException {
      if (m_proxy == null) {
         String svcAdminName = RepositoryServiceClientConstants.SERVICE_NAME;
         Service service = ServiceFactory.create(svcAdminName, RepositoryServiceClientConstants.SERVICE_NAME);
         service.setSessionTransportHeader("X-TURMERIC-SECURITY-USERID", "achakrabarty");
         service.setSessionTransportHeader("X-TURMERIC-SECURITY-PASSWORD", "ness@1234");

         m_proxy = service.getProxy();
      }

      return m_proxy;
   }

   protected TurmericRSV2 getEmptyProxy() throws ServiceException {
      if (m_proxy == null) {
         String svcAdminName = RepositoryServiceClientConstants.SERVICE_NAME;
         Service service = ServiceFactory.create(svcAdminName, RepositoryServiceClientConstants.SERVICE_NAME);
         RequestContext requestContext = service.getRequestContext();
         requestContext.setTransportHeader("X-TURMERIC-SECURITY-COOKIE", "invalidCookie");

         m_proxy = service.getProxy();
      }

      return m_proxy;
   }

   protected TurmericRSV2 getExpiredProxy() throws ServiceException {
      if (m_proxy == null) {
         String svcAdminName = RepositoryServiceClientConstants.SERVICE_NAME;
         Service service = ServiceFactory.create(svcAdminName, RepositoryServiceClientConstants.SERVICE_NAME);

         m_proxy = service.getProxy();
      }

      return m_proxy;
   }
}
