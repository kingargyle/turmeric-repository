/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.Resource;

import org.ebayopensource.turmeric.repository.v2.services.Artifact;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.FlattenedRelationship;
import org.ebayopensource.turmeric.repository.v2.services.Relation;



public class Wso2Base {
    static {
        System.setProperty("javax.net.ssl.trustStore", new File(
                        "src/main/resources/client-truststore.jks").getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");

        System.setProperty("org.ebayopensource.turmeric.repository.wso2.url",
                        "https://127.0.0.1:9443/registry");
        System.setProperty("org.ebayopensource.turmeric.repository.wso2.username", "admin");
        System.setProperty("org.ebayopensource.turmeric.repository.wso2.password", "admin");
        createLifecycleInfoInWso2();
    }

    /**
     * Utility method used to create a necessary asset in the wso2 registry, before a given test
     * runs
     */
    public static void createRequiredAssetsInWso2() {
        createRequiredServiceAssetInWso2();
        createRequiredNonServiceAssetInWso2();
    }

    /**
     * Utility method used to create a necessary asset in the wso2 registry, before a given test
     * runs
     */
    public static void createRequiredServiceAssetInWso2() {
        try {
            RemoteRegistry _registry = new RemoteRegistry(
                            new URL(
                                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.url")),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.username"),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.password"));
            String assetName = "RepositoryMetadataService";
            String assetKey = "/_system/governance/services/http/www/ebay/com/marketplace/services/"
                            + assetName;
            String serviceMetadata = null;
            String libraryName = "http://www.ebay.com/marketplace/services";
            String description = "The service description";
            String wsdlArtifactId = "/_system/governance/wsdls/http/www/ebay/com/marketplace/services/RepositoryMetadataService.wsdl";

            if (!_registry.resourceExists(assetKey)) {
                Resource asset = RSProviderUtil.newAssetResource();
                asset.setMediaType("application/vnd.wso2-service+xml");
                Document serviceMetadataXmlDoc = createXmlDoc(assetName, description, libraryName);
                serviceMetadata = getXmlString(serviceMetadataXmlDoc);
                if (serviceMetadata.length() > 0) {
                    InputStream contentStream = new ByteArrayInputStream(
                                    serviceMetadata.getBytes("UTF-8"));
                    asset.setContentStream(contentStream);
                }
                // asset.setProperty(RSProviderUtil.__artifactVersionPropName, "1.0.0");
                // _registry.put(assetKey, asset);// i put the RepositoryMetadataService in the wso2
                // registry instance
                // ok, now, let's go to the wsdl artifact.
                Resource wsdlArtifact = RSProviderUtil.newAssetResource();
                wsdlArtifact.setMediaType("application/wsdl+xml");
                InputStream wsdlIstream = Wso2Base.class.getClassLoader().getResourceAsStream(
                                "RepositoryMetadataService.wsdl");
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(wsdlIstream));
                StringBuilder wsdlcontent = new StringBuilder();
                String wsdlcontentLine = null;
                while ((wsdlcontentLine = bufReader.readLine()) != null) {
                    wsdlcontent.append(wsdlcontentLine);
                }
                bufReader.close();
                if (wsdlcontent.length() > 0) {
                    InputStream contentStream = new ByteArrayInputStream(wsdlcontent.toString()
                                    .getBytes("UTF-8"));
                    wsdlArtifact.setContentStream(contentStream);
                }
                _registry.put(wsdlArtifactId, wsdlArtifact);
                asset.setProperty(RSProviderUtil.__artifactVersionPropName, "1.0.0");
                _registry.put(assetKey, asset);// i put the RepositoryMetadataService in the wso2

            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void createRequiredNonServiceAssetInWso2() {
        try {
            RemoteRegistry _registry = new RemoteRegistry(
                            new URL(
                                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.url")),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.username"),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.password"));
            String assetName = "NonServiceAsset";
            String assetKey = "/_system/governance/testassets/libraryname/" + assetName;
            String description = "The asset description";
            if (!_registry.resourceExists(assetKey)) {
                Resource asset = RSProviderUtil.newAssetResource();
                asset.setDescription(description);
                asset.setProperty(RSProviderUtil.__artifactVersionPropName, "1.0.0");
                _registry.put(assetKey, asset);
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Utility method used to create a necessary asset in the wso2 registry, before a given test
     * runs
     */
    public static void createLifecycleInfoInWso2() {
        try {
            RemoteRegistry _registry = new RemoteRegistry(
                            new URL(
                                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.url")),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.username"),
                            System.getProperty("org.ebayopensource.turmeric.repository.wso2.password"));
            String assetName = "TurmericLifeCycle";
            String assetKey = "/_system/config/repository/components/org.wso2.carbon.governance/lifecycles/"
                            + assetName;

            if (!_registry.resourceExists(assetKey)) {
                Resource asset = _registry.newResource();
                asset.setMediaType("application/octet-stream");
                // need to read the lifecyle content from a file
                InputStream lifecycleIstrm = Wso2Base.class.getClassLoader().getResourceAsStream(
                                "META-INF/TurmericLifecycle.xml");
                BufferedReader in = new BufferedReader(new InputStreamReader(lifecycleIstrm));
                String str;
                StringBuilder strContent = new StringBuilder();
                while ((str = in.readLine()) != null) {
                    strContent.append(str);
                }
                in.close();
                if (strContent.length() > 0) {
                    InputStream contentStream = new ByteArrayInputStream(strContent.toString()
                                    .getBytes("UTF-8"));
                    asset.setContentStream(contentStream);
                }
                _registry.put(assetKey, asset);// i put the lifecycle in the wso2 registry instance

            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static Document createXmlDoc(String assetName, String assetDescription,
                    String assetNamespace) throws ParserConfigurationException {
        // prepare xml document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation impl = builder.getDOMImplementation();

        Document doc = impl.createDocument(null, null, null);

        Element name = doc.createElement("name");
        name.setTextContent(assetName);
        Element desc = doc.createElement("description");
        desc.setTextContent(assetDescription);
        Element ns = doc.createElement("namespace");
        ns.setTextContent(assetNamespace);

        // <overview> element
        Element overview = doc.createElement("overview");
        overview.appendChild(name);
        overview.appendChild(desc);
        overview.appendChild(ns);

        // <serviceMetaData> element
        Element root = doc.createElement("serviceMetaData");
        root.setAttribute("xmlns", "http://www.wso2.org/governance/metadata");
        root.appendChild(overview);

        doc.appendChild(root);

        return doc;
    }

    public static String getXmlString(Document doc) throws Exception {
        // transform the Document into a String
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        StringWriter sw = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    }

    

    public void validateBasicAssetInfo(BasicAssetInfo basicAssetInfo) {
        boolean result = true;
        if (basicAssetInfo.getAssetType() == null || basicAssetInfo.getAssetName() == null
                        || basicAssetInfo.getAssetDescription() == null
                        || basicAssetInfo.getVersion() == null) {
            result = false;
        }

        assertTrue("Invalid basicAssetInfo", result);
    }

    private static void checkValidArtifactInfo(List<ArtifactInfo> artifactInfos) {
        for (ArtifactInfo artifactInfo : artifactInfos) {
            Artifact artifact = artifactInfo.getArtifact();

            validateArtifact(artifact);
            assertTrue("Null artifact detail", artifactInfo.getArtifactDetail() != null);
        }
    }

    public static void validateArtifact(Artifact artifact) {
        if (artifact == null || artifact.getArtifactCategory() == null
                        || artifact.getArtifactIdentifier() == null
                        || artifact.getArtifactName() == null) {
            fail("Invalid artifact");
        }

    }

    public void validateAssetInfo(AssetInfo assetInfo) {
        List<ArtifactInfo> artifactInfos = assetInfo.getArtifactInfo();
        if (artifactInfos != null) {
            checkValidArtifactInfo(artifactInfos);
        }
        if (assetInfo.getAssetLifeCycleInfo() != null) {
            validateAssetLifeCycleInfo(assetInfo.getAssetLifeCycleInfo());
        }

        validateBasicAssetInfo(assetInfo.getBasicAssetInfo());

        if (assetInfo.getExtendedAssetInfo() != null) {
           validateExtendedAssetInfo(assetInfo.getExtendedAssetInfo());
        }
        if (assetInfo.getFlattenedRelationship() != null) {
           validateFlattenedRelationship(assetInfo.getFlattenedRelationship());
        }

        return;
    }
    
    public void validateExtendedAssetInfo(ExtendedAssetInfo extendedAssetInfo) {
        List<AttributeNameValue> attributeNameValues = extendedAssetInfo.getAttribute();
    
        if(attributeNameValues != null) {
            for (AttributeNameValue attributeNameValue : attributeNameValues) {
                if(attributeNameValue.getAttributeName() == null) {
                    fail("Null attributeName in extendedAssetInfo");               
                }
                if(attributeNameValue.getAttributeValueLong() == null && attributeNameValue.getAttributeValueString() == null && attributeNameValue.isAttributeValueBoolean() == null ) {
                    fail("Null attributeValues");     
                }
            }
        }
    }
    
    public void validateFlattenedRelationship(FlattenedRelationship flattenedRelationship) {
        validateAssetKey(flattenedRelationship.getSourceAsset());
        List<Relation> relations = flattenedRelationship.getRelatedAsset();
        if(relations != null) {
            validateRelations(relations);
        }
                
        return ;
    }
    
    public void validateAssetKey(AssetKey assetKey) {
        if(assetKey != null) {
            if(assetKey.getAssetId() == null || assetKey.getAssetName() == null || assetKey.getLibrary()== null || (assetKey.getLibrary().getLibraryId() == null && assetKey.getLibrary().getLibraryName() == null)) {
                fail("invalid assetKey");
            }
            return;
        }
        return;
    }
    
    public void validateRelations(List<Relation> relations) {
        for (Relation relation : relations) {
            if(relation.getAssetRelationship() == null) {
                fail("Null assetRelationship");
            }
            validateAssetKey(relation.getSourceAsset());
            validateAssetKey(relation.getTargetAsset());
        }       
        
        return;
    }
    
    public void validateAssetLifeCycleInfo(AssetLifeCycleInfo assetLifeCycleInfo) {
        if(assetLifeCycleInfo.getLifeCycleState() == null /*|| assetLifeCycleInfo.getArchitect() == null*/) {
            fail("Null lifecycle state");
        }
        
        return;
    }
}
