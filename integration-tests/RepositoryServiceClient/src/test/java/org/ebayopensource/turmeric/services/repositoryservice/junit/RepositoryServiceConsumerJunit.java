/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.junit;

import org.ebayopensource.turmeric.services.repositoryservice.operation.consumer.*;
import org.junit.Test;


import junit.framework.TestCase;

public class RepositoryServiceConsumerJunit extends TestCase {
/*(	private static final String s_success ="PASSED";

	
	 * Method under test: createAsset
	 * Test Type        : Positive
	 * Sub  Type        : validInput

	@Test
	public void testCreateAsset_validInput_positive() {
		String status = CreateAssetConsumer.testCreateAsset_validInput("nolibraryid");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : invalidLibraryId
	 
	@Test
	public void testCreateAsset_invalidLibraryId_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("invalidLibraryId");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noLibraryId
	 
	@Test
	public void testCreateAsset_noLibraryId_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noLibraryId");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : invalidLibraryName
	 
	@Test
	public void testCreateAsset_invalidLibraryName_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("invalidLibraryName");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noLibraryName
	 
	@Test
	public void testCreateAsset_noLibraryName_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noLibraryName");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noLibrary
	 
	@Test
	public void testCreateAsset_noLibrary_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noLibrary");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noAssetKey
	 
	@Test
	public void testCreateAsset_noAssetKey_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetKey");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noAssetName
	 
	@Test
	public void testCreateAsset_noAssetName_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetName");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : duplicateBasicAssetInfo (To try create an asset which is already present)
	 
	@Test
	public void testCreateAsset_duplicateBasicAssetInfo_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("duplicateBasicAssetInfo");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noAssetDescription
	 
	@Test
	public void testCreateAsset_noAssetDescription_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetDescription");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noAssetLongDescription
	 
	@Test
	public void testCreateAsset_noAssetLongDescription_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetLongDescription");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noAssetNamespase
	 
	@Test
	public void testCreateAsset_noAssetNamespase_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetNamespase");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noAssetType
	 
	@Test
	public void testCreateAsset_noAssetType_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetType");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createAsset
	 * Test Type        : Negative
	 * Sub  Type        : noAssetVersion
	 
	@Test
	public void testCreateAsset_noAssetVersion_negative() {
		String status = CreateAssetConsumer.testCreateAsset_invalidInput("noAssetVersion");
		assertEquals(s_success, status);
	}
		
	
	 * Method under test: getAssetDependenciesByGraph
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 
	@Test
	public void testGetAssetDependenciesByGraph_positive() {
		String status = GetAssetDependenciesByGraphConsumer.testGetAssetDependenciesByGraph_validAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetDependenciesByGraph
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testGetAssetDependenciesByGraph_invalidAsset_negative() {
		String status = GetAssetDependenciesByGraphConsumer.testGetAssetDependenciesByGraph_invalidAsset();
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetDependencies
	 * Test Type        : Positive
	 * Sub  Type        : withoutSpecifyingDepth 
	 
	@Test
	public void testGetAssetDependencies_withoutSpecifyingDepth_positive() {
		String status = GetAssetDependenciesConsumer.testGetAssetDependencies_validAsset();
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetDependencies
	 * Test Type        : Positive
	 * Sub  Type        : untillSpecifiedDepth 
	 
	@Test
	public void testGetAssetDependencies_untillSpecifiedDepth_positive() {
		String status = GetAssetDependenciesConsumer.testGetAssetDependencies_untillSpecifiedDepth();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetDependencies
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testGetAssetDependencies_invalidAsset_negative() {
		String status = GetAssetDependenciesConsumer.testGetAssetDependencies_invalidAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetInfo
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 
	@Test
	public void testGetAssetInfo_validAsset_positive() {
		String status = GetAssetInfoConsumer.testGetAssetInfo_validAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetInfo
	 * Test Type        : Negative
	 * Sub  Type        : invalidAssetKey
	 
	@Test
	public void testGetAssetInfo_invalidAssetKey_negative() {
		String status = GetAssetInfoConsumer.testGetAssetInfo("invalidAssetKey");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetInfo
	 * Test Type        : Negative
	 * Sub  Type        : invalidAssetVersion
	 
	@Test
	public void testGetAssetInfo_invalidAssetVersion_negative() {
		String status = GetAssetInfoConsumer.testGetAssetInfo("invalidAssetVersion");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetInfo
	 * Test Type        : Negative
	 * Sub  Type        : invalidAssetType
	 
	@Test
	public void testGetAssetInfo_invalidAssetType_negative() {
		String status = GetAssetInfoConsumer.testGetAssetInfo("invalidAssetType");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetStatus
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 
	@Test
	public void testGetAssetStatus_validAsset_positive() {
		String status = GetAssetStatusConsumer.testGetAssetStatus_validAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetStatus
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testGetAssetStatus_invalidAsset_negative() {
		String status = GetAssetStatusConsumer.testGetAssetStatus_invalidAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetTypes
	 * Test Type        : Positive
	 * Sub  Type        : withoutLibraryArgument
	 
	@Test
	public void testGetAssetTypes_withoutLibraryArgument_positive() {
		String status = GetAssetTypesConsumer.testGetAssetTypes_withoutLibraryArgument();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetTypes
	 * Test Type        : Positive
	 * Sub  Type        : withLibraryArgument
	 
	@Test
	public void testGetAssetTypes_withLibraryArgument_positive() {
		String status = GetAssetTypesConsumer.testGetAssetTypes_withLibraryArgument();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetTypes
	 * Test Type        : Negative
	 * Sub  Type        : invalidLibrary
	 
	@Test
	public void testGetAssetTypes_invalidLibrary_negative() {
		String status = GetAssetTypesConsumer.testGetAssetTypes_invalidLibrary();
		assertEquals(s_success, status);
	}
		
	
	 * Method under test: getAssetVersions
	 * Test Type        : Positive
	 * Sub  Type        : validAsset without assetid
	 
	@Test
	public void testGetAssetVersions_withoutAssetId_positive() {
		String status = GetAssetVersionsConsumer.testGetAssetVersions_validAsset("withoutAssetId");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetVersions
	 * Test Type        : Positive
	 * Sub  Type        : validAsset without assetName
	 
	@Test
	public void testGetAssetVersions_withoutAssetName_positive() {
		String status = GetAssetVersionsConsumer.testGetAssetVersions_validAsset("withoutAssetName");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getAssetVersions
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testGetAssetVersions_invalidAsset_negative() {
		String status = GetAssetVersionsConsumer.testGetAssetVersions_invalidAsset();
		assertEquals(s_success, status);
	}
	 
	
	 * Method under test: getBasicAssetInfo
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 
	@Test
	public void testGetBasicAssetInfo_validAsset_positive() {
		String status = GetBasicAssetInfoConsumer.testGetBasicAssetInfo_validAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getBasicAssetInfo
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testGetBasicAssetInfo_invalidAsset_negative() {
		String status = GetBasicAssetInfoConsumer.testGetBasicAssetInfo_invalidAsset();
		assertEquals(s_success, status);
	}
	 
	
	 * Method under test: getLibraryList
	 * Test Type        : Positive
	 * Sub  Type        : not applicable
	 
	@Test
	public void testGetLibraryList_positive() {
		String status = GetLibraryListConsumer.testGetLibraryList_positiveScenario();
		assertEquals(s_success, status);
	}

	
	 * Method under test: getService
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 
	@Test
	public void testGetService_validAsset_positive() {
		String status = GetServiceConsumer.testGetService_validAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: getService
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testGetService_invalidAsset_negative() {
		String status = GetServiceConsumer.testGetService_invalidAsset();
		assertEquals(s_success, status);
	}
	  
	
	 * Method under test: lockAsset
	 * Test Type        : Positive
	 * Sub  Type        : assetCurrentlyUnlocked
	 
	@Test
	public void testLockAsset_assetCurrentlyUnlocked_positive() {
//		String status = LockAssetConsumer.testLockAsset_validAsset();
//		assertEquals(s_success, status);
	}
	
	
	 * Method under test: lockAsset
	 * Test Type        : Negative
	 * Sub  Type        : assetCurrentlyLocked
	 
	@Test
	public void testLockAsset_assetCurrentlyLocked_negative() {
		String status = LockAssetConsumer.testLockAsset_assetCurrentlyLocked();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: lockAsset
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testLockAsset_invalidAsset_negative() {
		String status = LockAssetConsumer.testLockAsset_invalidAsset();
		assertEquals(s_success, status);
	}
	  
	
	 * Method under test: unlockAsset
	 * Test Type        : Positive
	 * Sub  Type        : assetCurrentlyLocked
	 
	@Test
	public void testUnlockAsset_assetCurrentlyLocked_positive() {
		String status = UnlockAssetConsumer.testUnlockAsset_validAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: unlockAsset
	 * Test Type        : Negative
	 * Sub  Type        : assetCurrentlyUnlocked
	 
	@Test
	public void testUnlockAsset_assetCurrentlyUnlocked_negative() {
		String status = UnlockAssetConsumer.testUnlockAsset_assetCurrentlyNotLocked();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: unlockAsset
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testUnlockAsset_invalidAsset_negative() {
		String status = UnlockAssetConsumer.testUnlockAsset_invalidAsset();
		assertEquals(s_success, status);
	}

	
	 * Method under test: updateAssetArtifacts
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 
	@Test
	public void testUpdateAssetArtifacts_validAsset_positive() {
		String status = UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts_validAsset();
		assertEquals(s_success, status);
	}

	
	 * Method under test: updateAssetArtifacts
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testUpdateAssetArtifacts_invalidAsset_negative() {
		String status = UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts_invalidAsset();
		assertEquals(s_success, status);
	}

	
	 * Method under test: updateAssetAttributes
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 
	@Test
	public void testUpdateAssetAttributes_validAsset_positive() {
		String status = UpdateAssetAttributesConsumer.testUpdateAssetAttributes_validAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: updateAssetAttributes
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testUpdateAssetAttributes_invalidAsset_negative() {
		String status = UpdateAssetAttributesConsumer.testUpdateAssetAttributes_invalidAsset();
		assertEquals(s_success, status);
	}

	
	 * Method under test: updateAsset
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 
	@Test
	public void testUpdateAsset_validAsset_positive() {
		String status = UpdateAssetConsumer.testUpdateAsset_validAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: updateAsset
	 * Test Type        : Negative
	 * Sub  Type        : invalidAsset
	 
	@Test
	public void testUpdateAsset_invalidAsset_negative() {
		String status = UpdateAssetConsumer.testUpdateAsset_invalidAsset();
		assertEquals(s_success, status);
	}		
	
	
	 * Method under test: createService
	 * Test Type        : Positive
	 * Sub  Type        : validInput, with all optional input parameters
	 
	@Test
	public void testCreateService_withOptionalInputParams_positive() {
		String status = CreateServiceConsumer.testCreateService_validInput("withOptionalInputParams");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createService
	 * Test Type        : Positive
	 * Sub  Type        : validInput, with the mandatory input fields alone
	 
	@Test
	public void testCreateService_withoutOptionalParams_positive() {
		String status = CreateServiceConsumer.testCreateService_validInput("withoutOptionalInputParams");
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createService
	 * Test Type        : Negative
	 * Sub  Type        : invalidInput, with basicServiceInfo set to null
	 
	@Test
	public void testCreateService_withoutBasicServiceInfo_negative() {
		String status = CreateServiceConsumer.testCreateService_invalidInput();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: createService
	 * Test Type        : Negative
	 * Sub  Type        : invalidInput, with duplicate service info
	 
	@Test
	public void testCreateService_duplicateServiceInfo_negative() {
		String status = CreateServiceConsumer.testCreateService_duplicateService();
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetLifeCycleStates
	 * Test Type        : Positive
	 * Sub  Type        : with Library object as argument
	 
	@Test
	public void testGetAssetLifeCycleStates_withLibraryArgument_positive() {
		String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_validInput("withLibrary");
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetLifeCycleStates
	 * Test Type        : Positive
	 * Sub  Type        : without Library object as argument
	 
	@Test
	public void testGetAssetLifeCycleStates_withoutLibraryArgument_positive() {
		String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_validInput("withoutLibrary");
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetLifeCycleStates
	 * Test Type        : Positive
	 * Sub  Type        : without LibraryId in argument
	 
	@Test
	public void testGetAssetLifeCycleStates_withoutLibraryId_positive() {
		String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_validInput("withoutLibraryId");
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetLifeCycleStates
	 * Test Type        : Positive
	 * Sub  Type        :without LibraryName in argument
	 
	@Test
	public void testGetAssetLifeCycleStates_withoutLibraryName_positive() {
		String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_validInput("withoutLibraryName");
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetLifeCycleStates
	 * Test Type        : Negative
	 * Sub  Type        :invalid AssetType in argument
	 
	@Test
	public void testGetAssetLifeCycleStates_invalidAssetType_negative() {
		String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_invalidInput("invalidAssetType");
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetLifeCycleStates
	 * Test Type        : Negative
	 * Sub  Type        :without AssetType in argument
	 
	@Test
	public void testGetAssetLifeCycleStates_withoutAssetType_negative() {
		String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_invalidInput("withoutAssetType");
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetLifeCycleStates
	 * Test Type        : Negative
	 * Sub  Type        :invalid LibraryId in argument
	 
	@Test
	public void testGetAssetLifeCycleStates_invalidLibraryId_negative() {
		String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_invalidInput("invalidLibraryId");
		assertEquals(s_success, status);
	}

	
	 * Method under test: getAssetLifeCycleStates
	 * Test Type        : Negative
	 * Sub  Type        :invalid LibraryName in argument
	 
	@Test
	public void testGetAssetLifeCycleStates_invalidLibraryName_negative() {
		String status = GetAssetLifeCycleStatesConsumer.testGetAssetLifeCycleStates_invalidInput("invalidLibraryName");
		assertEquals(s_success, status);
	}

	
	 * Method under test: submitForPublishing
	 * Test Type        : Positive
	 * Sub  Type        : validAsset
	 
	@Test
	public void testSubmitForPublishing_positive() {
		String status = SubmitForPublishingConsumer.testSubmitForPublishing_validAsset();
		assertEquals(s_success, status);
	}
	
	
	 * Method under test: updateAssetDependenciesByGraph
	 * Test Type        : Positive
	 * Sub  Type        : validInput
	 
	@Test
	public void testUpdateAssetDependenciesByGraph_positive() {
		String status = UpdateAssetDependenciesByGraphConsumer.testUpdateAssetDependenciesByGraph_validInput();
		assertEquals(s_success, status);
	}

	
	 * Method under test: updateAssetDependencies
	 * Test Type        : Positive
	 * Sub  Type        : validInput
	 
	@Test
	public void testUpdateAssetDependencies_positive() {
		String status = UpdateAssetDependenciesConsumer.testUpdateAssetDependencies_validInput();
		assertEquals(s_success, status);
	}

	
	 * Method under test: updateService
	 * Test Type        : Positive
	 * Sub  Type        : with All Input Arguments
	 
	@Test
	public void testUpdateService_withAllInputArguments_positive() {
		String status = UpdateServiceConsumer.testUpdateService_validInput("withAllInputArguments"); 
		assertEquals(s_success, status);
	}	

	
	 * Method under test: updateService
	 * Test Type        : Positive
	 * Sub  Type        : with BasicServiceInfo as the Only element within ServiceInfo object
	 
	@Test
	public void testUpdateService_withBasicServiceInfoOnly_positive() {
		String status = UpdateServiceConsumer.testUpdateService_validInput("withBasicServiceInfoOnly"); 
		assertEquals(s_success, status);
	}	

	
	 * Method under test: updateService
	 * Test Type        : Negative
	 * Sub  Type        : without ServiceInfo
	 
	@Test
	public void testUpdateService_noServiceInfo_negative() {
		String status = UpdateServiceConsumer.testUpdateService_invalidInput("noServiceInfo"); 
		assertEquals(s_success, status);
	}	

	
	 * Method under test: updateService
	 * Test Type        : Negative
	 * Sub  Type        : without BasicServiceInfo
	 
	@Test
	public void testUpdateService_noBasicServiceInfo_negative() {
		String status = UpdateServiceConsumer.testUpdateService_invalidInput("noBasicServiceInfo"); 
		assertEquals(s_success, status);
	}	

	
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : with Library argument , using the AND Conjunction
	 
	@Test
	public void testSearchAsset_withLibrary_andConjunction_positive() {
		String status = SearchAssetConsumer.testSearchAsset_withLibrary_andConjunction(); 
		assertEquals(s_success, status);
	}

	
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : with Library argument , using the OR Conjunction
	 
	@Test
	public void testSearchAsset_withLibrary_orConjunction_positive() {
		String status = SearchAssetConsumer.testSearchAsset_withLibrary_orConjunction(); 
		assertEquals(s_success, status);
	}	

	
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : without Library argument , using the AND Conjunction
	 
	@Test
	public void testSearchAsset_withoutLibrary_andConjunction_positive() {
		String status = SearchAssetConsumer.testSearchAsset_withoutLibrary_andConjunction(); 
		assertEquals(s_success, status);
	}

	
	 * Method under test: SearchAsset
	 * Test Type        : Positive
	 * Sub  Type        : without Library argument , using the OR Conjunction
	 
	@Test
	public void testSearchAsset_withoutLibrary_orConjunction_positive() {
		String status = SearchAssetConsumer.testSearchAsset_withoutLibrary_orConjunction(); 
		assertEquals(s_success, status);
	}	
	*/
} 
