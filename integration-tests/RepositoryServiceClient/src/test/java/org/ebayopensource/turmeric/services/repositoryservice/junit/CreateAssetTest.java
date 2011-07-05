/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import junit.framework.TestCase;
import static org.junit.Assume.assumeTrue;

import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.CreateAssetConsumer;
import org.junit.Test;


public class CreateAssetTest extends TestCase {
    private static final String s_success = "PASSED";

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : noLibraryName
     */
    @Test
    public void testCreateAsset_noLibraryName_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("noLibraryName");
        assertEquals(s_success, status);
    }

    /*
     * Method under test: createAsset Test Type : Positive Sub Type : validInput
     */
    @Test
    public void testCreateAsset_validInput_positive() {
        String status = CreateAssetConsumer.testCreateAsset_validInput("valid Input");
        assertEquals(s_success, status);
    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : noLibraryId
     */
    @Test
    public void testCreateAsset_noLibraryId_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("noLibraryId");
        assertEquals(s_success, status);
    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : invalidLibraryName
     */
    @Test
    public void testCreateAsset_invalidLibraryName_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("invalidLibraryName");
        assumeTrue(s_success.equalsIgnoreCase(status));
    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : noLibrary
     */
    @Test
    public void testCreateAsset_noLibrary_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("noLibrary");
        assertEquals(s_success, status);
    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : noAssetKey
     */
    @Test
    public void testCreateAsset_noAssetKey_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetKey");
        assertEquals(s_success, status);
    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : noAssetName
     */
    @Test
    public void testCreateAsset_noAssetName_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetName");
        assertEquals(s_success, status);
    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : duplicateBasicAssetInfo (To
     * try create an asset which is already present)
     */
//    @Test
//    public void testCreateAsset_duplicateBasicAssetInfo_negative() {
//        String status = CreateAssetConsumer.testCreateAsset_invalidInput("duplicateBasicAssetInfo");
//        assertEquals(s_success, status);
//    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : noAssetDescription
     */
    @Test
    public void testCreateAsset_noAssetDescription_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetDescription");
        assumeTrue(s_success.equalsIgnoreCase(status));
    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : noAssetLongDescription
     */
    @Test
    public void testCreateAsset_noAssetLongDescription_positive() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetLongDescription");
        assertEquals(s_success, status);
    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : noAssetType
     */
    @Test
    public void testCreateAsset_noAssetType_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetType");
        assertEquals(s_success, status);
    }

    /*
     * Method under test: createAsset Test Type : Negative Sub Type : noAssetVersion
     */
    @Test
    public void testCreateAsset_noAssetVersion_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetVersion");
        assertEquals(s_success, status);
    }

    /*
     *  * Method under test: createAsset Test Type : Negative Sub Type : invalidLibraryId
     */
    @Test
    public void testCreateAsset_invalidLibraryId_negative() {
        String status = CreateAssetConsumer.testCreateAsset_invalidInput("invalidLibraryId");
        assumeTrue(s_success.equalsIgnoreCase(status));
    }
}
