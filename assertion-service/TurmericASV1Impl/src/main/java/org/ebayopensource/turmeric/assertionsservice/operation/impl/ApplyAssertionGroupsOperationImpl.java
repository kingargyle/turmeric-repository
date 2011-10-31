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
import java.util.List;

import org.apache.log4j.Logger;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;
import org.ebayopensource.turmeric.errorlibrary.assertion.ErrorConstants;
import org.ebayopensource.turmeric.assertion.v1.services.*;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssertionManager;
import org.ebayopensource.turmeric.repositorymanager.assertions.AssetReference;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionIllegalArgumentException;
import org.ebayopensource.turmeric.repositorymanager.assertions.exception.AssertionRuntimeException;
import org.ebayopensource.turmeric.repositorymanager.assertions.impl.AssetAssertionGroup;
import org.ebayopensource.turmeric.runtime.common.exceptions.ErrorDataFactory;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.runtime.common.pipeline.MessageContext;
import org.ebayopensource.turmeric.runtime.common.pipeline.MessageContextAccessor;
import org.ebayopensource.turmeric.runtime.common.types.SOAHeaders;
import org.ebayopensource.turmeric.services.assertionsservice.common.ConstantPopulator;
import org.ebayopensource.turmeric.services.assertionsservice.common.PropertyKeys;
import org.ebayopensource.turmeric.services.assertionsservice.exception.ObjectNotFoundException;
import org.ebayopensource.turmeric.services.common.util.AssertionsServiceOperationUtil;

public class ApplyAssertionGroupsOperationImpl extends BaseOperationImpl {
   private static Logger s_logger = Logger.getLogger(ApplyAssertionGroupsOperationImpl.class);
   private static ApplyAssertionGroupsOperationImpl s_ApplyAssertionGroupsOperationImpl;

   public static ApplyAssertionGroupsOperationImpl getInstance() {
      if (s_ApplyAssertionGroupsOperationImpl == null) {
         s_ApplyAssertionGroupsOperationImpl = new ApplyAssertionGroupsOperationImpl();
      }
      return s_ApplyAssertionGroupsOperationImpl;
   }

   public boolean validate(ApplyAssertionGroupsRequest applyAssertionGroupsRequest, List<CommonErrorData> errorDataList) {
      boolean isValid = true;
      boolean isAllWSDL = true;
      // System.out.println("ENtered APPLY ASERTION GROUP validate");
      if (applyAssertionGroupsRequest != null) {
         if (applyAssertionGroupsRequest.getArtifacts() != null
                  && applyAssertionGroupsRequest.getArtifacts().size() > 0) {
            for (AssertableArtifact assertableArtifact : applyAssertionGroupsRequest.getArtifacts()) {
               if (!validateAssertableArtifact(assertableArtifact, errorDataList)) {
                  isValid = false;
               }
               if (assertableArtifact.getArtifactAssetReference() != null
                        && !ArtifactContentTypes.WSDL.equals(assertableArtifact.getArtifactAssetReference()
                                 .getArtifactCategory())) {
                  isAllWSDL = false;
               } else if (assertableArtifact.getArtifactExternal() != null
                        && !ArtifactContentTypes.WSDL.equals(assertableArtifact.getArtifactExternal().getContentType())) {
                  isAllWSDL = false;
               }
            }
         } else {
            isValid = false;
            errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_CONTENT_MISSING,
                     ErrorConstants.ERRORDOMAIN));
         }
         if (applyAssertionGroupsRequest.getAssertionGroups() != null
                  && applyAssertionGroupsRequest.getAssertionGroups().size() > 0) {
            for (AssertionGroupAsset assertionGroupAsset : applyAssertionGroupsRequest.getAssertionGroups()) {
               if (!validateAssertionGroupAsset(assertionGroupAsset, errorDataList)) {
                  isValid = false;
               }
            }
         } else {
            if (applyAssertionGroupsRequest.getAssertionGroups() == null) {
               isValid = false;
               errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSERTION_GROUP_LIST_NULL,
                        ErrorConstants.ERRORDOMAIN));
            } else if (!isAllWSDL) {
               isValid = false;
               errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.NOT_ALL_WSDL,
                        ErrorConstants.ERRORDOMAIN));
            } else {
               AssertionGroupAsset assertionGroupAsset = new AssertionGroupAsset();
               assertionGroupAsset.setAssetName(ConstantPopulator.getInstance().getValueOf(
                        PropertyKeys.DEFAULT_WSDL_ASSERTION_GROUP_ASSET_NAME));
               assertionGroupAsset.setAssetType(AssertionGroupAssetTypes.ASSERTION_GROUP);
               assertionGroupAsset.setLibraryName(ConstantPopulator.getInstance().getValueOf(
                        PropertyKeys.DEFAULT_ASSERTION_LIBRARY_NAME));
               assertionGroupAsset.setVersion(ConstantPopulator.getInstance().getValueOf(
                        PropertyKeys.DEFAULT_WSDL_ASSERTION_GROUP_ASSET_VERSION));
               applyAssertionGroupsRequest.getAssertionGroups().add(assertionGroupAsset);
            }
         }
      } else {
         isValid = false;
         errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.REQUEST_EMPTY, ErrorConstants.ERRORDOMAIN));
      }
      // System.out.println("Exited APPLY ASERTION GROUP validate");
      return isValid;
   }

   private boolean validateAssertionGroupAsset(AssertionGroupAsset assertionGroupAssetReference,
            List<CommonErrorData> errorDataList) {
      boolean isValid = true;
      if (assertionGroupAssetReference.getAssetName() == null) {
         isValid = false;
         errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.ASSET_NAME_MISSING,
                  ErrorConstants.ERRORDOMAIN));
      }
      if (assertionGroupAssetReference.getLibraryName() == null) {
         isValid = false;
         errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.LIBRARY_NAME_MISSING,
                  ErrorConstants.ERRORDOMAIN));
      }
      return isValid;
   }

   public ApplyAssertionGroupsResponse applyAssertionGroups(ApplyAssertionGroupsRequest applyAssertionGroupsRequest) {
      boolean isError = false;
      boolean isWarning = false;
      boolean isPartial = false;
      // System.out.println("ENtered APPLY ASERTION GROUP");
      MessageContext messageContext = MessageContextAccessor.getContext();
      List<CommonErrorData> errorDataList = new ArrayList<CommonErrorData>();
      ApplyAssertionGroupsResponse applyAssertionGroupsResponse = new ApplyAssertionGroupsResponse();

      List<AssertionGroupAsset> wsdlAssertionGroupAssetList = applyAssertionGroupsRequest.getAssertionGroups();

      AssertionManager assertionManager = new AssertionManager();
      assertionManager.setRepositoryServiceLocation(ConstantPopulator.getInstance().getValueOf(
               PropertyKeys.REPOSITORY_SERVICE_URL));
      org.ebayopensource.turmeric.repositorymanager.assertions.AssertionReport apiAssertionReport = null;
      // System.out.println("Looping assertion Groups");
      s_logger.info("Begin ApplyAssertionGroups ************************");
      for (AssertionGroupAsset wsdlAssertionGroupAsset : wsdlAssertionGroupAssetList) {
         // System.out.println("GROUP NAME " + wsdlAssertionGroupAsset.getAssetName());
         AssertionGroupReport wsdlAssertionGroupReport = new AssertionGroupReport();

         List<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionGroup> apiAssertionGroupList = new ArrayList<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionGroup>();
         AssetReference groupAssetReference = new AssetReference(wsdlAssertionGroupAsset.getLibraryName(),
                  wsdlAssertionGroupAsset.getAssetName(), wsdlAssertionGroupAsset.getVersion(),
                  AssertionGroupAssetTypes.ASSERTION_GROUP.value());
         org.ebayopensource.turmeric.repositorymanager.assertions.AssertionGroup apiAssertionGroup = new AssetAssertionGroup(
                  groupAssetReference);

         AssertionGroup assertionGroup = new AssertionGroup();
         // TODO: code to get description to be added
         assertionGroup.setDescription("TO BE IMPLEMENTED");
         assertionGroup.setName(wsdlAssertionGroupAsset.getAssetName());
         assertionGroup.setVersion(wsdlAssertionGroupAsset.getVersion());

         wsdlAssertionGroupReport.setAssertionGroup(assertionGroup);
         apiAssertionGroupList.add(apiAssertionGroup);
         List<AssertableArtifact> assertableArtifactList = applyAssertionGroupsRequest.getArtifacts();
         // System.out.println("LOOPING ASSERTABEL ARTIFACTS " );
         for (AssertableArtifact assertableArtifact : assertableArtifactList) {
            // System.out.println("ASSETABLE ARTIFACT " +
            // assertableArtifact.getArtifactAssetReference().getAssetName());
            try {
               List<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent> singleContentList = new ArrayList<org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent>();
               singleContentList
                        .add((org.ebayopensource.turmeric.repositorymanager.assertions.AssertionContent) convert(assertableArtifact));

               String userId = null;
               userId = messageContext.getRequestMessage().getTransportHeader(SOAHeaders.AUTH_USERID);

               String password = null;
               password = messageContext.getRequestMessage().getTransportHeader(SOAHeaders.AUTH_PASSWORD);
               // System.out.println("CALLING FRAMEWORK FUNCTIONS");
               apiAssertionReport = assertionManager.applyAssertionGroups(userId, password, apiAssertionGroupList,
                        singleContentList);
               // System.out.println("FRAMEWORK FUNCTIONS RETURNED");
               ArtifactReport wsdlArtifactReport = null;
               if (apiAssertionReport != null) {
                  wsdlArtifactReport = new ArtifactReport();
                  convert(apiAssertionReport, wsdlArtifactReport);
               }

               AssertableArtifactReference assertableArtifactReference = null;
               if (assertableArtifact != null) {
                  assertableArtifactReference = new AssertableArtifactReference();
                  convert(assertableArtifact, assertableArtifactReference);
               }
               wsdlArtifactReport.setArtifact(assertableArtifactReference);

               wsdlAssertionGroupReport.getArtifactReport().add(wsdlArtifactReport);
            } catch (AssertionRuntimeException assertionRuntimeException) {
               s_logger.error(assertionRuntimeException.getMessage());
               if (assertionRuntimeException.getErrorMessage() != null) {
                  isError = true;
                  errorDataList.addAll(assertionRuntimeException.getErrorMessage().getError());
               }
               if (assertionRuntimeException.getThrowable() != null) {
                  errorDataList.add(AssertionsServiceOperationUtil.parseGeneralExceptionMessage(new Exception(
                           assertionRuntimeException.getThrowable())));
               }
            } catch (AssertionIllegalArgumentException assertionIllegalArgumentException) {
               s_logger.error(assertionIllegalArgumentException.getMessage());
               if (assertionIllegalArgumentException.getErrorMessage() != null) {
                  isError = true;
                  errorDataList.addAll(assertionIllegalArgumentException.getErrorMessage().getError());
               }
               if (assertionIllegalArgumentException.getThrowable() != null) {
                  errorDataList.add(AssertionsServiceOperationUtil.parseGeneralExceptionMessage(new Exception(
                           assertionIllegalArgumentException.getThrowable())));
               }
            } catch (ObjectNotFoundException objectNotFoundException) {
               s_logger.error(objectNotFoundException.getMessage());
               isPartial = true;
               String message = objectNotFoundException.getMessage();
               errorDataList.add(ErrorDataFactory.createErrorData(ErrorConstants.OBJECT_NOT_FOUND_EXCEPTION,
                        ErrorConstants.ERRORDOMAIN, new String[] { message }));
            }

            catch (ServiceException serviceException) {
               s_logger.error(serviceException.getMessage());
               isError = true;
               errorDataList.add(AssertionsServiceOperationUtil.parseGeneralExceptionMessage(serviceException));
            } catch (IOException ioException) {
               s_logger.error(ioException.getMessage());
               isError = true;
               errorDataList.add(AssertionsServiceOperationUtil.parseGeneralExceptionMessage(ioException));
            }

            catch (RuntimeException runtimeException) {
               s_logger.error(runtimeException.getMessage());
               isError = true;
               errorDataList.add(AssertionsServiceOperationUtil.parseGeneralExceptionMessage(runtimeException));
            }
         }

         applyAssertionGroupsResponse.getAssertionGroupReport().add(wsdlAssertionGroupReport);
      }

      if (isError) {
         applyAssertionGroupsResponse.setAck(AckValue.FAILURE);
      } else if (isPartial) {
         applyAssertionGroupsResponse.setAck(AckValue.PARTIAL_FAILURE);
      } else if (isWarning) {
         applyAssertionGroupsResponse.setAck(AckValue.WARNING);
      } else {
         applyAssertionGroupsResponse.setAck(AckValue.SUCCESS);
      }
      if (errorDataList != null && errorDataList.size() > 0) {
         ErrorMessage errorMessage = new ErrorMessage();
         errorMessage.getError().addAll(errorDataList);
         applyAssertionGroupsResponse.setErrorMessage(errorMessage);
      }
      // System.out.println("EXITING APPLYA ASSERTION GROUPS");
      return applyAssertionGroupsResponse;
   }

}
