/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import org.ebayopensource.turmeric.services.repositoryservice.intf.gen.BaseRepositoryServiceConsumer;

public class RepositoryServiceConsumer extends BaseRepositoryServiceConsumer {
	public static void main(String[] args) {/*	
		Map<String, Map<String, String>> testSuitSummary = new HashMap<String, Map<String, String>>();		
		Map<String, String> methodSummary = null;
		
		testSuitSummary.put("lockAsset",                   LockAssetConsumer.testLockAsset());
		testSuitSummary.put("unlockAsset",                 UnlockAssetConsumer.testUnlockAsset());
		testSuitSummary.put("getLibraryList",              GetLibraryListConsumer.testGetLibraryList());
		testSuitSummary.put("getAssetTypes",               GetAssetTypesConsumer.testGetAssetTypes());		
		testSuitSummary.put("getAssetDependencies",        GetAssetDependenciesConsumer.testGetAssetDependencies());
		testSuitSummary.put("getAssetDependenciesByGraph", GetAssetDependenciesByGraphConsumer.testGetAssetDependenciesByGraph());
		testSuitSummary.put("getAssetStatus",              GetAssetStatusConsumer.testGetAssetStatus());
		testSuitSummary.put("getAssetVersions",            GetAssetVersionsConsumer.testGetAssetVersions());
		testSuitSummary.put("getBasicAssetInfo",           GetBasicAssetInfoConsumer.testGetBasicAssetInfo());
		testSuitSummary.put("getAssetInfo",                GetAssetInfoConsumer.testGetAssetInfo());
		testSuitSummary.put("updateAsset",                 UpdateAssetConsumer.testUpdateAsset());
		testSuitSummary.put("updateAssetArtifacts",        UpdateAssetArtifactsConsumer.testUpdateAssetArtifacts());
		testSuitSummary.put("updateAssetAttributes",       UpdateAssetAttributesConsumer.testUpdateAssetAttributes());
		testSuitSummary.put("getService",                  GetServiceConsumer.testGetService());
		testSuitSummary.put("createAsset",                 CreateAssetConsumer.testCreateAsset());

		Set<String> methods = testSuitSummary.keySet();  

		int passCount = 0;
		int failCount = 0;
		
		for (String method : methods) {
			boolean flag = true;
			
			System.out.println("\n" + method + " Summary");
			methodSummary = testSuitSummary.get(method);
			
			Set<String> methodVariants = methodSummary.keySet();
			for (String methodVariant : methodVariants) {
				System.out.format("%-60s: %s\n", methodVariant, methodSummary.get(methodVariant)); 
				if(methodSummary.get(methodVariant).equalsIgnoreCase("FAILED")) {
					flag = false;
				}
			}
			if(flag) {
				passCount ++;
			}
			else {
				failCount ++;
			}						
		}
		System.out.println("\n\nTestsuite summary");
		System.out.println("Number of Methods PASSED: " + passCount);
		System.out.println("Number of Methods FAILED: " + failCount);		
	*/}
}
