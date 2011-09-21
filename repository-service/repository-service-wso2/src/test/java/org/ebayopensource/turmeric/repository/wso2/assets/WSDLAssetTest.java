/*******************************************************************************
 * Copyright (c) 2011 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2.assets;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.wso2.carbon.governance.api.util.GovernanceConstants;
import org.wso2.carbon.registry.core.Registry;

import org.ebayopensource.turmeric.repository.v2.services.*;
import org.ebayopensource.turmeric.repository.wso2.RSProviderUtil;
import org.ebayopensource.turmeric.repository.wso2.Wso2Base;

public class WSDLAssetTest extends Wso2Base {
	
	private static final String ASSETNAME = "testwsdl";

    @Test
    public void testCreateWSDL() throws Exception {
        Registry wso2Registry = RSProviderUtil.getRegistry();        
        Artifact wsdl = new Artifact();
        wsdl.setArtifactName(ASSETNAME + ".wsdl");
        wsdl.setArtifactCategory(AssetConstants.TURMERIC_TYPE);
        wsdl.setArtifactValueType(ArtifactValueType.FILE);
        wsdl.setTargetNamespace("http://www.example.org/" + ASSETNAME);

        ArtifactInfo wsdlInfo = new ArtifactInfo();
        wsdlInfo.setArtifact(wsdl);
        wsdlInfo.setArtifactDetail(loadFile("src/main/resources/META-INF/soa/services/wsdl/CreateServiceTest/CreateServiceTest.wsdl"));
        wsdlInfo.setContentType(GovernanceConstants.WSDL_MEDIA_TYPE);
        
        WSDLAsset wsdlAsset = new WSDLAsset(wsdlInfo, wso2Registry);
        assertTrue("WSDL was not created.", wsdlAsset.createAsset());
        assertTrue("WSDL was not added.", wsdlAsset.addAsset());
    }
    
    private byte[] loadFile(String pathname) throws Exception {
        File file = new File(pathname);
        int size = (int) file.length();
        byte[] content = new byte[size];

        FileInputStream fin = new FileInputStream(file);
        fin.read(content, 0, size);
        fin.close();
        return content;
    }
}
