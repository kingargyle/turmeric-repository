/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import java.net.URL;
import java.util.*;

import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.ebayopensource.turmeric.services.common.error.RepositoryServiceErrorDescriptor;

import org.ebayopensource.turmeric.common.v1.types.*;
import org.ebayopensource.turmeric.repository.v2.services.*;

public class RSProviderUtil {
   public static String __artifactVersionPropName = "org.ebayopensource.turmeric.artifactVersion";

   private static final RSProviderUtil __instance = new RSProviderUtil();

   private Registry _registry;

   /**
    * @throws IllegalStateException
    */
   public RSProviderUtil() throws IllegalStateException {
      try {
         String username = System.getProperty("org.ebayopensource.turmeric.repository.wso2.username");
         String password = System.getProperty("org.ebayopensource.turmeric.repository.wso2.password");
         String url = System.getProperty("org.ebayopensource.turmeric.repository.wso2.url");
         Registry rootRegistry = new RemoteRegistry(new URL(url), username, password);
         _registry = GovernanceUtils.getGovernanceUserRegistry(rootRegistry, username);

      } catch (Exception ex) {
         ex.printStackTrace();
         throw new IllegalStateException(ex);
      }
   }

   /**
    * @return
    */
   public static final Registry getRegistry() {
      return __instance._registry;
   }

   /**
    * @param response
    */
   public static <T extends BaseResponse> T setSuccessResponse(T response) {

      if (response != null) {
         response.setAck(AckValue.SUCCESS);
      }
      return response;
   }

   /**
    * @param errors
    * @param response
    */
   public static <T extends BaseResponse> T addErrorsToResponse(List<CommonErrorData> errors, T response) {
      if (errors != null && !errors.isEmpty() && response != null) {
         if (response.getErrorMessage() == null) {
            response.setErrorMessage(new ErrorMessage());
         }
         response.getErrorMessage().getError().addAll(errors);
         response.setAck(AckValue.FAILURE);
      }
      return response;
   }

   /**
    * @param exception
    * @param response
    */
   public static <T extends BaseResponse> T setExceptionMessageToResponse(Exception exception, T response) {
      if (response != null) {
         response.setAck(AckValue.FAILURE);

         if (response.getErrorMessage() == null) {
            ErrorMessage errorMessage = new ErrorMessage();
            response.setErrorMessage(errorMessage);
         }
         response.getErrorMessage().getError().add(parseGeneralExceptionMessage(exception));
      }
      return response;
   }

   /**
    * @author csubhash
    * @param exception
    * @return returns a message parsed error data object
    */
   public static CommonErrorData parseGeneralExceptionMessage(Exception exception) {
      List<ErrorParameter> errorDataList = new ArrayList<ErrorParameter>();
      StackTraceElement[] stackTraceElements = exception.getStackTrace();
      int index = 0;

      ErrorParameter exceptionClassErrorParameter = new ErrorParameter();
      exceptionClassErrorParameter.setName("exceptionClass");
      exceptionClassErrorParameter.setValue(exception.getClass().getCanonicalName());
      errorDataList.add(exceptionClassErrorParameter);

      ErrorParameter fileNameErrorParameter = new ErrorParameter();
      fileNameErrorParameter.setName("fileName");
      fileNameErrorParameter.setValue(stackTraceElements[index].getFileName());
      errorDataList.add(fileNameErrorParameter);

      ErrorParameter classNameErrorParameter = new ErrorParameter();
      classNameErrorParameter.setName("className");
      classNameErrorParameter.setValue(stackTraceElements[index].getClassName());
      errorDataList.add(classNameErrorParameter);

      ErrorParameter methodNameErrorParameter = new ErrorParameter();
      methodNameErrorParameter.setName("methodName");
      methodNameErrorParameter.setValue(stackTraceElements[index].getMethodName());
      errorDataList.add(methodNameErrorParameter);

      ErrorParameter lineNoErrorParameter = new ErrorParameter();
      lineNoErrorParameter.setName("lineNo");
      int linenum = stackTraceElements[index].getLineNumber();
      lineNoErrorParameter.setValue(Integer.valueOf(linenum).toString());
      errorDataList.add(lineNoErrorParameter);

      ErrorParameter messageErrorParameter = new ErrorParameter();
      messageErrorParameter.setName("errorMessage");
      messageErrorParameter.setValue(exception.getMessage());
      errorDataList.add(messageErrorParameter);

      CommonErrorData errorData = RepositoryServiceErrorDescriptor.UNKNOWN_EXCEPTION.newError(errorDataList);
      return errorData;
   }

   /**
    * @param exception
    * @param response
    * @param errorDescriptor
    */
   public static <T extends BaseResponse> T handleException(Exception exception, T response,
            RepositoryServiceErrorDescriptor errorDescriptor) {
      exception.printStackTrace();

      response.setAck(AckValue.FAILURE);
      ErrorParameter errorParameter = new ErrorParameter();
      errorParameter.setName("Error Message");
      errorParameter.setValue(exception.getMessage());
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      errorDataList.add(errorDescriptor.newError(errorParameter));

      if (response.getErrorMessage() == null) {
         response.setErrorMessage(new ErrorMessage());
      }
      response.getErrorMessage().getError().addAll(errorDataList);

      return response;
   }

   /**
    * @param info
    * @param service
    * @param doc
    * @return
    * @throws XPathExpressionException
    */
   public static AssetLifeCycleInfo completeAssetLifeCycleInfo(AssetLifeCycleInfo info, Resource asset, Document doc)
            throws XPathExpressionException {
      if (doc != null) {
         XPath xpath = XPathFactory.newInstance().newXPath();
         info.setDomainOwner(xpath.evaluate("/*/contacts/textContact", doc));
         info.setDomainType(xpath.evaluate("/*/contacts/contact", doc));
      }
      RSLifeCycle lifeCycle = RSLifeCycle.get(asset);
      info.setLifeCycleState(lifeCycle.getState());
      return info;
   }

   public static AttributeNameValue newAttribute(String name, String value) {
      AttributeNameValue attr = new AttributeNameValue();
      attr.setAttributeName(name);
      attr.setAttributeValueString(value);
      return attr;
   }

   public static AttributeNameValue newAttribute(String name, long value) {
      AttributeNameValue attr = new AttributeNameValue();
      attr.setAttributeName(name);
      attr.setAttributeValueLong(value);
      return attr;
   }

   public static AttributeNameValue newAttribute(String name, boolean value) {
      AttributeNameValue attr = new AttributeNameValue();
      attr.setAttributeName(name);
      attr.setAttributeValueBoolean(value);
      return attr;
   }

   public static String getAttribute(ExtendedAssetInfo extendedInfo, String name, String dflt) {
      List<AttributeNameValue> attrs = extendedInfo.getAttribute();
      for (AttributeNameValue attr : attrs) {
         if (attr.getAttributeName().equalsIgnoreCase(name)) {
            return attr.getAttributeValueString();
         }
      }
      return dflt;
   }

   public static long getAttribute(ExtendedAssetInfo extendedInfo, String name, long dflt) {
      List<AttributeNameValue> attrs = extendedInfo.getAttribute();
      for (AttributeNameValue attr : attrs) {
         if (attr.getAttributeName().equalsIgnoreCase(name)) {
            return attr.getAttributeValueLong();
         }
      }
      return dflt;
   }

   public static void updateLifeCycleInfo(AssetLifeCycleInfo lifeCycleInfo, AssetLifeCycleInfo newLifeCycleInfo) {
      if (newLifeCycleInfo.getApprover() != null) {
         lifeCycleInfo.setApprover(newLifeCycleInfo.getApprover());
      }

      if (newLifeCycleInfo.getArchitect() != null) {
         lifeCycleInfo.setArchitect(newLifeCycleInfo.getArchitect());
      }

      if (newLifeCycleInfo.getDomainOwner() != null) {
         lifeCycleInfo.setDomainOwner(newLifeCycleInfo.getDomainOwner());
      }

      if (newLifeCycleInfo.getDomainType() != null) {
         lifeCycleInfo.setDomainType(newLifeCycleInfo.getDomainType());
      }

      if (newLifeCycleInfo.getLifeCycleState() != null) {
         lifeCycleInfo.setLifeCycleState(newLifeCycleInfo.getLifeCycleState());
      }

      if (newLifeCycleInfo.getNextAction() != null) {
         lifeCycleInfo.setNextAction(newLifeCycleInfo.getNextAction());
      }

      if (newLifeCycleInfo.getOpsArchitect() != null) {
         lifeCycleInfo.setOpsArchitect(newLifeCycleInfo.getOpsArchitect());
      }

      if (newLifeCycleInfo.getProductManager() != null) {
         lifeCycleInfo.setProductManager(newLifeCycleInfo.getProductManager());
      }

      if (newLifeCycleInfo.getProjectManager() != null) {
         lifeCycleInfo.setProjectManager(newLifeCycleInfo.getProjectManager());
      }

      if (newLifeCycleInfo.getServiceArchitect() != null) {
         lifeCycleInfo.setServiceArchitect(newLifeCycleInfo.getServiceArchitect());
      }

      if (newLifeCycleInfo.getTraceTicket() != null) {
         lifeCycleInfo.setTraceTicket(newLifeCycleInfo.getTraceTicket());
      }

      if (newLifeCycleInfo.getTrackerId() != null) {
         lifeCycleInfo.setTrackerId(newLifeCycleInfo.getTrackerId());
      }
   }
}
