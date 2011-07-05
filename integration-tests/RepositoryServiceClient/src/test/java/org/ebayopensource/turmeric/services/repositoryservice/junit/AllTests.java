/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.Resource;

@RunWith(Suite.class)
@Suite.SuiteClasses({
// ApproveAssetTest.class,
// CreateAndSubmitTest.class,
		CreateAssetTest.class,
 		CreateCompleteAssetTest.class,
// CreativeServiceTest.class,
// GetAllAssetsGroupedByCategoryTest.class,
// GetAllProjectsAndGroupsTest.class,
        GetAssetBasicAssetInfoTest.class,
        // GetAssetDependenciesByGraphTest.class,
        // GetAssetDependenciesByGraphTest.class,
        // GetAssetDependencyTest.class,
        GetAssetInfoTest.class,
        // GetAssetLifeCycleStatesTest.class,
        // GetAssetStatusTest.class,
        // GetAssetSubmissionPropertiesTest.class,
        //GetAssetTypesTest.class,
        // GetAssetVersionsTest.class,
        // GetCatalogAssetInfoTest.class,
        GetLibraryListTest.class,
        // GetServiceTest.class,
        // GetUsersProjectsAndGroupsTest.class,
        LockAssetTest.class,
        // RejectAssetTest.class,
        RemoveAssetTest.class,
        // SearchAssetsDetailedTest.class,
        // SearchAssetTest.class,
        // SubmitForPublishingTest.class,
        UnlockAssetTest.class,
        UpdateAssetArtifactTest.class,
        UpdateAssetAttributesTest.class,
// UpdateAssetDependenciesByGraphTest.class,
// UpdateAssetDependenciesTest.class,
        UpdateAssetTest.class,
        UpdateCompleteAssetTest.class
// UpdateServiceTest.class,
// ValidateAssetTest.class
})
public class AllTests {
    static {
        System.setProperty("javax.net.ssl.trustStore",
                        new File("client-truststore.jks").getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");

        System.setProperty("org.ebayopensource.turmeric.repository.wso2.url",
                        "https://127.0.0.1:9443/registry");
        System.setProperty("org.ebayopensource.turmeric.repository.wso2.username", "admin");
        System.setProperty("org.ebayopensource.turmeric.repository.wso2.password", "admin");
    }

    /**
     * Utility method used to create a necessary asset in the wso2 registry, before a given test
     * runs
     */
    @BeforeClass
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
                InputStream lifecycleIstrm = AllTests.class.getClassLoader().getResourceAsStream(
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
            throw new RuntimeException(e);
        }
    }

}
