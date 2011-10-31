/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import java.util.List;

import org.apache.log4j.Logger;

import org.ebayopensource.turmeric.assetcreation.exception.AssetCreationException;
import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;
import org.ebayopensource.turmeric.repository.v2.services.*;

/**
 * The Class Validator.
 */
public class Validator {

   private static Logger s_logger = Logger.getLogger(Validator.class);

   /**
    * Validate create and submit asset response.
    * 
    * @param submitAssetResponse
    *           the submit asset response
    * @throws AssetCreationException
    *            the asset creation exception
    */
   public static void validateCreateAndSubmitAssetResponse(CreateAndSubmitAssetResponse submitAssetResponse)
            throws AssetCreationException {
      s_logger.debug("Inside Validator.validateCreateAndSubmitAssetResponse()");
      ErrorMessage errorMessages = null;
      List<CommonErrorData> errors = null;

      if (submitAssetResponse == null) {
         s_logger.error("CreateAndSubmitResponse retrieved from RS is null");
         throw new AssetCreationException("CreateAndSubmitResponse retrieved from RS is null");
      }

      if (!submitAssetResponse.getAck().value().equalsIgnoreCase("success")) {
         if ((submitAssetResponse.getErrorMessage().getError().size() == 1 && submitAssetResponse.getErrorMessage()
                  .getError().get(0).getErrorId() == 109)) {
            s_logger.warn("Creation got success but submission got " + "failed for assetName = "
                     + submitAssetResponse.getAssetKey().getAssetName() + " for assetId = "
                     + submitAssetResponse.getAssetKey().getAssetId());
            return;
         }

         else {
            s_logger.error("CreateAndSubmitAssetResponse failed");
            s_logger.error("Asset Response ack status " + submitAssetResponse.getAck().value());
            errorMessages = submitAssetResponse.getErrorMessage();
            errors = errorMessages.getError();
            for (ErrorData error : errors) {
               s_logger.error("ERROR ID : " + error.getErrorId());
               s_logger.error("ERROR EXCEPTION ID : " + error.getExceptionId());
               s_logger.error("ERROR MESSAGE : " + error.getMessage());
               s_logger.error("ERROR SEVERITY : " + error.getSeverity().value());
            }
            throw new AssetCreationException("CreateAndSubmitAssetResponse ACK from RS is failure");
         }
      }

      if (submitAssetResponse.getAssetKey() == null) {
         s_logger.error("In CreateAndSubmitAssetResponse, AssetKey object is null");
         throw new AssetCreationException("In CreateAndSubmitAssetResponse, AssetKey object is null");
      }
   }

   /**
    * Validate submit for publishing response.
    * 
    * @param submitForPubRes
    *           the submit for pub res
    * @throws AssetCreationException
    *            the asset creation exception
    */
   public static void validateSubmitForPublishingResponse(SubmitForPublishingResponse submitForPubRes)
            throws AssetCreationException {
      s_logger.debug("Validator.validateSubmitForPublishingResponse()");
      ErrorMessage errorMessages = null;
      List<CommonErrorData> errors = null;

      if (submitForPubRes == null) {
         s_logger.error("CreateAndSubmitResponse retrieved from RS is null");
         throw new AssetCreationException("CreateAndSubmitResponse retrieved from RS is null");
      }

      if (submitForPubRes.getAck().value().equalsIgnoreCase("success")) {
         if (submitForPubRes.getErrorMessages() != null) {
            s_logger.error("Submission got failured with error message " + submitForPubRes.getErrorMessages());
            throw new AssetCreationException("Submission got failured with error message "
                     + submitForPubRes.getErrorMessages());
         } else {
            s_logger.info("SubmitForPublishing operation got success");
         }
      } else {
         s_logger.error("submitForPublishingResponse failed");
         s_logger.error("Asset Response ack status " + submitForPubRes.getAck().value());
         errorMessages = submitForPubRes.getErrorMessage();
         errors = errorMessages.getError();
         for (ErrorData error : errors) {
            s_logger.error("ERROR ID : " + error.getErrorId());
            s_logger.error("ERROR EXCEPTION ID : " + error.getExceptionId());
            s_logger.error("ERROR MESSAGE : " + error.getMessage());
            s_logger.error("ERROR SEVERITY : " + error.getSeverity().value());
         }
         throw new AssetCreationException("submitForPublishingResponse ACK from RS is failure");
      }
   }

   /**
    * Validate get asset info response.
    * 
    * @param assetResponse
    *           the asset response
    * @throws AssetCreationException
    *            the asset creation exception
    */
   public static void validateGetAssetInfoResponse(GetAssetInfoResponse assetResponse) throws AssetCreationException {
      s_logger.debug("Inside Validator.validateGetAssetInfoResponse()");
      ErrorMessage errorMessages = null;
      List<CommonErrorData> errors = null;

      if (assetResponse == null) {
         s_logger.error("GetAssetInfoResponse retrieved from RS is null");
         throw new AssetCreationException("GetAssetInfoResponse retrieved from RS is null");
      }
      if (!assetResponse.getAck().value().equalsIgnoreCase("success")) {
         s_logger.error("GetAssetInfoResponse failed");
         s_logger.error("Asset Response ack status " + assetResponse.getAck().value());
         errorMessages = assetResponse.getErrorMessage();
         errors = errorMessages.getError();
         for (ErrorData error : errors) {
            s_logger.error("ERROR ID : " + error.getErrorId());
            s_logger.error("ERROR EXCEPTION ID : " + error.getExceptionId());
            s_logger.error("ERROR MESSAGE : " + error.getMessage());
            s_logger.error("ERROR SEVERITY : " + error.getSeverity().value());
         }
         throw new AssetCreationException("GetAssetInfoResponse ACK from RS is failure");
      }

      AssetInfo assetInfo = assetResponse.getAssetInfo();

      if (assetInfo == null) {
         s_logger.error("In GetAssetInfoResponse, AssetInfo object is null");
         throw new AssetCreationException("In GetAssetInfoResponse, AssetInfo object is null");
      }
   }

}
