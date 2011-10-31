/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assertionsservice.operation.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;
import org.ebayopensource.turmeric.errorlibrary.assertion.ErrorConstants;
import org.ebayopensource.turmeric.assertion.v1.services.*;
import org.ebayopensource.turmeric.services.assertionsservice.common.ConstantPopulator;
import org.ebayopensource.turmeric.services.assertionsservice.common.PropertyKeys;
import org.ebayopensource.turmeric.services.assertionsservice.exception.ObjectNotFoundException;
import org.ebayopensource.turmeric.services.common.util.AssertionsServiceOperationUtil;

import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionManager;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionReport;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.ValidationResult;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionIllegalArgumentException;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssetAssertion;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.BasicAssertion;
import org.ebayopensource.turmeric.runtime.common.exceptions.ErrorDataFactory;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.common.pipeline.MessageContext;
import org.ebayopensource.turmeric.runtime.common.pipeline.MessageContextAccessor;
import org.ebayopensource.turmeric.runtime.common.types.SOAHeaders;

public class ApplyAssertionsOperationImpl extends BaseOperationImpl {
   private static Logger s_logger = Logger.getLogger(ApplyAssertionsOperationImpl.class);
   private static ApplyAssertionsOperationImpl s_ApplyAssertionsOperationImpl;

   public static ApplyAssertionsOperationImpl getInstance() {
      if (s_ApplyAssertionsOperationImpl == null) {
         s_ApplyAssertionsOperationImpl = new ApplyAssertionsOperationImpl();
      }
      return s_ApplyAssertionsOperationImpl;
   }

   public boolean validate(ApplyAssertionsRequest applyAssertionsRequest, List<CommonErrorData> errorDataList) {
      boolean isValid = true;
      if (applyAssertionsRequest != null) {
         if (applyAssertionsRequest.getArtifacts() == null || applyAssertionsRequest.getArtifacts().size() <= 0) {
            isValid = false;
            errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_CONTENT_MISSING,
                     ErrorConstants.ERRORDOMAIN));
         } else {
            List<AssertableArtifact> assertableArtifactList = applyAssertionsRequest.getArtifacts();
            for (AssertableArtifact assertableArtifact : assertableArtifactList) {
               if (!validateAssertableArtifact(assertableArtifact, errorDataList)) {
                  isValid = false;
               }
            }
         }
         if (applyAssertionsRequest.getAssertions() == null || applyAssertionsRequest.getAssertions().size() <= 0) {
            isValid = false;
            errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTIONS_MISSING,
                     ErrorConstants.ERRORDOMAIN));
         } else {
            List<Assertion> assertionList = applyAssertionsRequest.getAssertions();
            for (Assertion assertion : assertionList) {
               if (!validateAssertion(assertion, errorDataList)) {
                  isValid = false;
               }
            }
         }
      } else {
         isValid = false;
         errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.REQUEST_EMPTY, ErrorConstants.ERRORDOMAIN));
      }
      return isValid;
   }

   public ApplyAssertionsResponse applyAssertions(ApplyAssertionsRequest applyAssertionsRequest) {
      boolean isError = false;
      boolean isWarning = false;
      boolean isPartial = false;
      MessageContext messageContext = MessageContextAccessor.getContext();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      List<AssertableArtifact> assertableArtifactList = applyAssertionsRequest.getArtifacts();
      // Create AssertionContent
      List<Assertion> assertionList = applyAssertionsRequest.getAssertions();
      List<org.ebayopensource.turmeric.repositorymanager.assertions.Assertion> apiAssertionList = new ArrayList<org.ebayopensource.turmeric.repositorymanager.assertions.Assertion>();
      s_logger.info("Begin ApplyAssertions ************************");
      for (Assertion singleAssertion : assertionList) {
         org.ebayopensource.turmeric.repositorymanager.assertions.Assertion apiAssertion = null;
         if (singleAssertion.getAssertionAsset() != null) {
            if (singleAssertion.getAssertionAsset().getAssetType() == null) {
               singleAssertion.getAssertionAsset().setAssetType(AssertionAssetTypes.ASSERTION);
            }
            org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference apiAssetReference = new org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference(
                     singleAssertion.getAssertionAsset().getLibraryName(), singleAssertion.getAssertionAsset()
                              .getAssetName(), singleAssertion.getAssertionAsset().getVersion(), singleAssertion
                              .getAssertionAsset().getAssetType().value());
            apiAssertion = new AssetAssertion(apiAssetReference);
         } else if (singleAssertion.getExternalAssertion() != null) {
            org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent apiAssertionContent = null;
            Collection<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionModule> apiAssertionModuleList = new ArrayList<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionModule>();
            try {
               apiAssertionContent = (org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent) convert(singleAssertion
                        .getExternalAssertion().getAssertionScript());
            } catch (ObjectNotFoundException objectNotFoundException) {
               isPartial = true;
               String message = objectNotFoundException.getMessage();
               errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.OBJECT_NOT_FOUND_EXCEPTION,
                        ErrorConstants.ERRORDOMAIN, new String[] { message }));
            }
            if (singleAssertion.getExternalAssertion().getAssertionModules() != null) {
               List<AssertionModule> wsdlAssertionModuleList = singleAssertion.getExternalAssertion()
                        .getAssertionModules();

               for (AssertionModule assertionModule : wsdlAssertionModuleList) {
                  try {
                     apiAssertionModuleList
                              .add((org.ebayopensource.turmeric.repositorymanager.assertions.AssertionModule) convert(assertionModule));
                  } catch (ObjectNotFoundException objectNotFoundException) {
                     isPartial = true;
                     String message = objectNotFoundException.getMessage();
                     errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.OBJECT_NOT_FOUND_EXCEPTION,
                              ErrorConstants.ERRORDOMAIN, new String[] { message }));
                  }
               }
            }
            apiAssertion = new BasicAssertion(singleAssertion.getExternalAssertion().getName(), singleAssertion
                     .getExternalAssertion().getVersion(), singleAssertion.getExternalAssertion().getDescription(),
                     ValidationResult.valueOf(singleAssertion.getExternalAssertion().getErrorSeverity().value()),
                     apiAssertionContent, singleAssertion.getExternalAssertion().getAssertionProcessorType(),
                     apiAssertionModuleList);

         }
         apiAssertionList.add(apiAssertion);
      }

      AssertionManager assertionManager = new AssertionManager();
      assertionManager.setRepositoryServiceLocation(ConstantPopulator.getInstance().getValueOf(
               PropertyKeys.REPOSITORY_SERVICE_URL));
      AssertionReport apiAssertionReport = null;
      ApplyAssertionsResponse applyAssertionsResponse = new ApplyAssertionsResponse();
      org.ebayopensource.turmeric.assertion.v1.services.AssertionReport wsdlAssertionReport = new org.ebayopensource.turmeric.assertion.v1.services.AssertionReport();
      for (AssertableArtifact assertableArtifact : assertableArtifactList) {

         try {
            List<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent> singleContentList = new ArrayList<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent>();
            singleContentList
                     .add((org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent) convert(assertableArtifact));

            String userId = null;
            userId = messageContext.getRequestMessage().getTransportHeader(SOAHeaders.AUTH_USERID);

            String password = null;
            password = messageContext.getRequestMessage().getTransportHeader(SOAHeaders.AUTH_PASSWORD);
            apiAssertionReport = assertionManager
                     .applyAssertions(userId, password, apiAssertionList, singleContentList);

            ArtifactReport artifactReport = new ArtifactReport();
            AssertableArtifactReference assertableArtifactReference = null;
            if (assertableArtifact != null) {
               assertableArtifactReference = new AssertableArtifactReference();
               convert(assertableArtifact, assertableArtifactReference);
            }

            artifactReport.setArtifact(assertableArtifactReference);

            // artifactReport.setArtif
            // ValidationResult(AssertionErrorSeverity.valueOf(apiAssertionReport.getValidationResult().toString()));
            for (AssertionResult assertionResult : apiAssertionReport.getAssertionResults()) {

               org.ebayopensource.turmeric.assertion.v1.services.AssertionResult wsdlAssertionResult = (org.ebayopensource.turmeric.assertion.v1.services.AssertionResult) convert(assertionResult);
               for (org.ebayopensource.turmeric.repositorymanager.assertions.Assertion apiAssertion : apiAssertionList) {
                  if (apiAssertion.getName().equals(wsdlAssertionResult.getAssertionName())) {
                     wsdlAssertionResult.setAssertionSeverity(AssertionSeverity.valueOf(apiAssertion.getErrorSeverity()
                              .name()));
                  }
               }
               artifactReport.getAssertionResults().add(wsdlAssertionResult);

            }

            wsdlAssertionReport.getArtifactReport().add(artifactReport);
         } catch (AssertionIllegalArgumentException assertionIllegalArgumentException) {
            if (assertionIllegalArgumentException.getErrorMessage() != null) {
               isError = true;
               errorDataList.addAll(assertionIllegalArgumentException.getErrorMessage().getError());
            }
            if (assertionIllegalArgumentException.getThrowable() != null) {
               errorDataList.add(AssertionsServiceOperationUtil.parseGeneralExceptionMessage(new Exception(
                        assertionIllegalArgumentException.getThrowable())));
            }
         } catch (ObjectNotFoundException objectNotFoundException) {
            isPartial = true;
            String message = objectNotFoundException.getMessage();
            errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.OBJECT_NOT_FOUND_EXCEPTION,
                     ErrorConstants.ERRORDOMAIN, new String[] { message }));
         } catch (ServiceException serviceException) {
            isError = true;
            errorDataList.add(AssertionsServiceOperationUtil.parseGeneralExceptionMessage(serviceException));
         } catch (IOException ioException) {
            isError = true;
            errorDataList.add(AssertionsServiceOperationUtil.parseGeneralExceptionMessage(ioException));
         } catch (RuntimeException runtimeException) {
            isError = true;
            errorDataList.add(AssertionsServiceOperationUtil.parseGeneralExceptionMessage(runtimeException));
         }
      }

      applyAssertionsResponse.setAssertionReport(wsdlAssertionReport);

      if (isError) {
         applyAssertionsResponse.setAck(AckValue.FAILURE);
      } else if (isPartial) {
         applyAssertionsResponse.setAck(AckValue.PARTIAL_FAILURE);
      } else if (isWarning) {
         applyAssertionsResponse.setAck(AckValue.WARNING);
      } else {
         applyAssertionsResponse.setAck(AckValue.SUCCESS);
      }
      if (errorDataList != null && errorDataList.size() > 0) {
         ErrorMessage errorMessage = new ErrorMessage();
         errorMessage.getError().addAll(errorDataList);
         applyAssertionsResponse.setErrorMessage(errorMessage);
      }

      return applyAssertionsResponse;
   }
}
