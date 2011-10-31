/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.assetcreation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.ebayopensource.turmeric.assetcreation.artifacts.AssetInput;
import org.ebayopensource.turmeric.assetcreation.exception.ProcessingException;
import org.ebayopensource.turmeric.assetcreation.exception.WSDLNotFoundException;
import org.ebayopensource.turmeric.repository.v2.services.*;

/**
 * The Class ModifyServiceAsset.
 */
public class ModifyServiceAsset extends ModifyAsset {

   private static Logger s_logger = Logger.getLogger(ModifyServiceAsset.class);

   /*
    * (non-Javadoc)
    * 
    * @see
    * org.ebayopensource.turmeric.assetcreation.ModifyAsset#modify(org.ebayopensource.turmeric.repository.v1.services
    * .AssetInfo, org.ebayopensource.turmeric.assetcreation.artifacts.AssetInput)
    */
   @Override
   public void modify(AssetInfo assetInfo, AssetInput assetInput) throws ProcessingException {
      modifyAssetName(assetInfo, assetInput);
      try {
         modifyWsdl(assetInfo, assetInput);
      } catch (WSDLNotFoundException e) {
         s_logger.error(e.getMessage());
         throw new ProcessingException(e.getMessage(), e);
      }
      modifyAssetId(assetInfo);
      modifyArtifact(assetInfo);
      modifySourceRelationship(assetInfo);
      setClassifier(assetInfo);
   }

   /**
    * Modify source relationship.
    * 
    * @param assetInfo
    *           the asset info
    */
   public void modifySourceRelationship(AssetInfo assetInfo) {
      if (assetInfo.getFlattenedRelationship() != null) {
         assetInfo.getFlattenedRelationship().setSourceAsset(null);
      }
   }

   /**
    * Modify artifact.
    * 
    * @param assetInfo
    *           the asset info
    */
   public void modifyArtifact(AssetInfo assetInfo) {
      List<ArtifactInfo> artifactInfos = assetInfo.getArtifactInfo();

      for (ArtifactInfo artifactInfo : artifactInfos) {
         artifactInfo.getArtifact().setArtifactIdentifier(null);
      }
   }

   /**
    * Modify wsdl.
    * 
    * @param assetInfo
    *           the asset info
    * @param assetInput
    *           the asset input
    * @throws WSDLNotFoundException
    *            the wSDL not found exception
    */
   public void modifyWsdl(AssetInfo assetInfo, AssetInput assetInput) throws WSDLNotFoundException {

      byte[] wsdlDataInBytes = null;
      ByteArrayInputStream byteArrayInputStream = null;
      Document doc = null;
      String serviceAssetName = assetInfo.getBasicAssetInfo().getAssetName();
      List<ArtifactInfo> artifactInfos = assetInfo.getArtifactInfo();
      TransformerFactory transfactory = TransformerFactory.newInstance();
      StringWriter sw = new StringWriter();
      StreamResult result = new StreamResult(sw);
      String wsdlFileLocation = null;
      File wsdlFile = null;
      boolean wsdlFileFound = false;
      ArtifactInfo temp_artifactInfo = null;

      if (assetInput.getWsdlDocumentLocation() != null) {
         wsdlFileLocation = assetInput.getWsdlDocumentLocation();
         wsdlFile = new File(wsdlFileLocation);
         if (!wsdlFile.isFile()) {
            s_logger.error("WSDL is not found in the path " + wsdlFileLocation);
            throw new WSDLNotFoundException("WSDL is not found in the path " + wsdlFileLocation);
         } else {
            s_logger.info("WSDL file found on the location" + wsdlFileLocation);
            wsdlFileFound = true;
         }
      }

      for (ArtifactInfo artifactInfo : artifactInfos) {
         if (artifactInfo.getArtifact().getArtifactCategory().toString().equalsIgnoreCase("WSDL")) {
            wsdlDataInBytes = artifactInfo.getArtifactDetail();
            byteArrayInputStream = new ByteArrayInputStream(wsdlDataInBytes);
            temp_artifactInfo = artifactInfo;
            break;
         }
      }

      try {
         DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         Transformer transformer = transfactory.newTransformer();

         if (wsdlFileFound) {
            doc = documentBuilder.parse(wsdlFile);
         } else {
            doc = documentBuilder.parse(byteArrayInputStream);
         }

         NodeList nodeList = doc.getElementsByTagName("wsdl:service");
         Node item = nodeList.item(0);
         NamedNodeMap attributes = item.getAttributes();
         Node namedItem = attributes.getNamedItem("name");
         namedItem.setTextContent(serviceAssetName);
         DOMSource source = new DOMSource(doc);
         transformer.transform(source, result);
         wsdlDataInBytes = sw.toString().getBytes("UTF-8");
         temp_artifactInfo.setArtifactDetail(wsdlDataInBytes);
         temp_artifactInfo.getArtifact().setArtifactDisplayName(serviceAssetName + ".wsdl");

      } catch (Exception e) {
         s_logger.warn("WSDL not properly populated for assetName = " + assetInfo.getBasicAssetInfo().getAssetName()
                  + " for ID = " + assetInput.getId());
      }
   }

}
