/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.GetLibraryListRequest;
import org.ebayopensource.turmeric.repository.v2.services.GetLibraryListResponse;
import org.ebayopensource.turmeric.repository.v2.services.Library;

public class GetLibraryListTest extends Wso2Base {
	@BeforeClass
    public static void checkRepository() {
        boolean exists = false;
        try {
            exists = RSProviderUtil.getRegistry().resourceExists("/");
        }
        catch (Exception ex) {
        }

        assumeTrue(exists);
        try {
            createRequiredAssetsInWso2();
        }
        catch (Exception ex) {
            fail("failed creating neccesary assets in wso2 registry");
        }
    }
	
    @Test
    public void checkConfig() {
        // TODO this test is just used to make Maven happy.
        File truststore = new File("src/main/resources/client-truststore.jks");
        assertTrue(truststore.exists());
    }

    @Test
    public void getLibraryList() {
        GetLibraryListRequest getLibraryListRequest = new GetLibraryListRequest();
        RepositoryServiceProviderImpl provider = new RepositoryServiceProviderImpl();
        GetLibraryListResponse response = provider.getLibraryList(getLibraryListRequest);

        assertEquals(AckValue.SUCCESS, response.getAck());
        assertEquals(null, response.getErrorMessage());
        List<Library> libraries = response.getLibrary();
        assertTrue("Null library list", libraries!=null);
        assertTrue("Empty list", libraries.size()>0);//should be at least 1, because of the assets created in @BeforeClass method
        for (Library lib : libraries) {
            System.err.println("lib=" + lib.getLibraryName() + " " + lib.getLibraryId());
        }
    }

}
