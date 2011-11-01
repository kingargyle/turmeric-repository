/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.assertionsservice.junit;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorMessage;
import org.ebayopensource.turmeric.assertion.v1.services.*;
import org.ebayopensource.turmeric.runtime.common.exceptions.ServiceException;
import org.ebayopensource.turmeric.services.assertionsservice.consumer.AssertionsServiceConsumer;

/**
 * @author szacharias
 */
public class BaseAssertionsServiceTest {

   private final AssertionsServiceConsumer consumer = new AssertionsServiceConsumer();

   public static final String PASS = "PASSED";
   public static final String FAIL = "FAILED";

   public static final String SUCCESS = "success";
   public static final String FAILURE = "failure";

   public static final String POSITIVE_CASE = "positiveCase";
   public static final String NEGATIVE_CASE = "negativeCase";
   public static final String FAILURE_CASE = "failureCase";

   protected String processRequest(ApplyAssertionsRequest request, String criteria) {

      String result = FAIL;

      try {
         ApplyAssertionsResponse response = consumer.applyAssertions(request);

         if (response == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateServiceResponse(response, criteria).equalsIgnoreCase(SUCCESS)) {
            result = PASS;
         }
         if (result != PASS || !criteria.equals(POSITIVE_CASE)) {
            displayResponse(response);
         }
      } catch (Exception e) {
         e.printStackTrace();
         result = FAIL;
      }
      return result;
   }

   protected String processRequest(ApplyAssertionGroupsRequest request, String criteria) {

      String result = FAIL;

      try {
         ApplyAssertionGroupsResponse response = consumer.applyAssertionGroups(request);

         if (response == null) {
            throw new ServiceException(null, "Response object can not be null", null);
         }

         if (validateServiceResponse(response, criteria).equalsIgnoreCase(SUCCESS)) {
            result = PASS;
         }

         if (result != PASS || !criteria.equals(POSITIVE_CASE)) {
            displayResponse(response);
         }
      } catch (Exception e) {
         e.printStackTrace();
         result = FAIL;
      }
      return result;
   }

   protected static String validateServiceResponse(ApplyAssertionsResponse response, String criteria) {

      // Verify the response structure.
      if (!criteria.equalsIgnoreCase(FAILURE_CASE)) {
         boolean isValid = AssertionsTestUtil.validateStructure(response);
         if (!isValid) {
            return FAILURE;
         }
      }

      // Verify whether the response is in the expected way. ie. whether the assertions pass for POSITIVE_CASE and fail
      // for NEGATIVE_CASE
      if (criteria.equalsIgnoreCase(POSITIVE_CASE)) {
         if (response.getAck().value().equalsIgnoreCase(SUCCESS)) {
            if (response.getAssertionReport() != null) {
               for (ArtifactReport artifactReport : response.getAssertionReport().getArtifactReport()) {
                  for (AssertionResult assertionResult : artifactReport.getAssertionResults()) {
                     if (assertionResult.getValidationResult() != ValidationResult.PASSED) {
                        return FAILURE;
                     }
                  }
               }
            }

            return SUCCESS;
         }
      }

      if (criteria.equalsIgnoreCase(NEGATIVE_CASE)) {
         if (response.getAck().value().equalsIgnoreCase(SUCCESS)) {

            if (response.getAssertionReport() != null) {
               for (ArtifactReport artifactReport : response.getAssertionReport().getArtifactReport()) {
                  for (AssertionResult assertionResult : artifactReport.getAssertionResults()) {
                     if (assertionResult.getValidationResult() != ValidationResult.FAILED) {
                        return FAILURE;
                     }
                  }
               }
            }

            return SUCCESS;
         }
      }

      if (criteria.equalsIgnoreCase(FAILURE_CASE)) {
         if (response.getAck().value().equalsIgnoreCase(FAILURE)) {
            return SUCCESS;
         }
      }

      return FAILURE;
   }

   protected static String validateServiceResponse(ApplyAssertionGroupsResponse response, String criteria) {

      // Verify the response structure.
      if (!criteria.equalsIgnoreCase(FAILURE_CASE)) {
         boolean isValid = AssertionsTestUtil.validateStructure(response);
         if (!isValid) {
            return FAILURE;
         }
      }

      // Verify whether the response is in the expected way. ie. whether the assertionGroups pass for POSITIVE_CASE and
      // fail for NEGATIVE_CASE
      if (criteria.equalsIgnoreCase(POSITIVE_CASE)) {
         if (response.getAck().value().equalsIgnoreCase(SUCCESS)) {
            if (response.getAssertionGroupReport() != null) {
               for (AssertionGroupReport assertionGroupReport : response.getAssertionGroupReport()) {

                  for (ArtifactReport artifactReport : assertionGroupReport.getArtifactReport()) {
                     for (AssertionResult assertionResult : artifactReport.getAssertionResults()) {

                        // WARNING assertions are not considered
                        if (assertionResult.getAssertionSeverity() == AssertionSeverity.ERROR
                                 && assertionResult.getValidationResult() != ValidationResult.PASSED) {
                           return FAILURE;
                        }
                     }
                  }
               }
            }
            return SUCCESS;
         }

      }

      if (criteria.equalsIgnoreCase(NEGATIVE_CASE)) {
         if (response.getAck().value().equalsIgnoreCase(SUCCESS)) {
            if (response.getAssertionGroupReport() != null) {
               for (AssertionGroupReport assertionGroupReport : response.getAssertionGroupReport()) {

                  for (ArtifactReport artifactReport : assertionGroupReport.getArtifactReport()) {
                     for (AssertionResult assertionResult : artifactReport.getAssertionResults()) {

                        // WARNING assertions are not considered
                        if (assertionResult.getAssertionSeverity() == AssertionSeverity.ERROR
                                 && assertionResult.getValidationResult() == ValidationResult.FAILED) {
                           return SUCCESS;
                        }
                     }
                  }
               }
            }
            return FAILURE;
         }
      }

      if (criteria.equalsIgnoreCase(FAILURE_CASE)) {
         if (response.getAck().value().equalsIgnoreCase(FAILURE)) {
            return SUCCESS;
         }
      }

      return FAILURE;
   }

   protected void displayResponse(final ApplyAssertionsResponse response) {

      if (response != null && response.getAck().value().equalsIgnoreCase(SUCCESS)) {
         AssertionReport assertionReport = response.getAssertionReport();

         displayArtifactReport(assertionReport.getArtifactReport());

      } else {
         displayErrorMessage(response.getErrorMessage());
      }
   }

   protected void displayResponse(final ApplyAssertionGroupsResponse response) {

      if (response != null && response.getAck().value().equalsIgnoreCase(SUCCESS)) {

         for (AssertionGroupReport assertionGroupReport : response.getAssertionGroupReport()) {
            System.out.println("Assertion Group : " + assertionGroupReport.getAssertionGroup().getName() + " : "
                     + assertionGroupReport.getAssertionGroup().getVersion());

            displayArtifactReport(assertionGroupReport.getArtifactReport());
         }

      } else {
         displayErrorMessage(response.getErrorMessage());
      }
   }

   private void displayArtifactReport(List<ArtifactReport> artifactReports) {

      for (ArtifactReport artifactReport : artifactReports) {
         AssertableArtifactReference assertableArtifactReference = artifactReport.getArtifact();
         if (assertableArtifactReference.getArtifactAsset() != null) {
            System.out.println("Artifact : " + assertableArtifactReference.getArtifactAsset().getAssetName());
         } else if (assertableArtifactReference.getArtifactExternal() != null) {
            System.out.println("Artifact : External Artifact");
         }

         for (AssertionResult assertionResult : artifactReport.getAssertionResults()) {

            System.out.println("Assertion : " + assertionResult.getAssertionName());
            for (AssertionResultItem resultItem : assertionResult.getResultItems()) {
               System.out.println("Message : " + resultItem.getMessageText());
            }
         }
      }
   }

   protected void displayErrorMessage(ErrorMessage errorMessage) {
      for (CommonErrorData errorData : errorMessage.getError()) {
         System.err.println("Error");
         System.err.println("Error severity :" + errorData.getSeverity());
         System.err.println("Error Message :" + errorData.getMessage());
      }
   }

   protected BinaryContent getBinaryContent(String resourceName) {

      BinaryContent content = new BinaryContent();

      InputStream in = this.getClass().getClassLoader().getResourceAsStream("WSDL/" + resourceName);

      int count;
      int chunkSize = 10000;
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      byte[] b = new byte[chunkSize];
      try {
         while ((count = in.read(b, 0, chunkSize)) > 0) {
            stream.write(b, 0, count);
         }
         byte[] thebytes = stream.toByteArray();

         content.setContent(thebytes);
      } catch (IOException ioe) {
         System.out.println("Error while reading" + resourceName);
         // TODO : Handle correctly
      } finally {
         try {
            in.close();
            // stream.close();
         } catch (IOException ioe) {
            System.out.println("Could not close stream properly" + ioe.getMessage());
         }
      }

      return content;
   }

   protected URLContent getURLContent(String url) {

      URLContent urlContent = new URLContent();

      urlContent.setUrl(url);

      return urlContent;
   }

   public TextContent getTextContent(String resourceName) {

      TextContent textContent = new TextContent();

      InputStream is = this.getClass().getClassLoader().getResourceAsStream(resourceName);

      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();

      String line = null;
      try {
         while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            is.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      textContent.setContent(sb.toString());
      return textContent;
   }

}
