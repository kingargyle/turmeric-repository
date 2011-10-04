/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.ebayopensource.turmeric.common.v1.types.AckValue;
import org.ebayopensource.turmeric.repository.v2.services.Artifact;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v2.services.ArtifactValueType;
import org.ebayopensource.turmeric.repository.v2.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.AssetKey;
import org.ebayopensource.turmeric.repository.v2.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v2.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v2.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.CreateAndSubmitAssetRequest;
import org.ebayopensource.turmeric.repository.v2.services.CreateAndSubmitAssetResponse;
import org.ebayopensource.turmeric.repository.v2.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v2.services.FlattenedRelationship;
import org.ebayopensource.turmeric.services.repositoryservice.impl.RepositoryServiceProvider;

/**
 * @author mgorovoy
 * 
 */
public class CreateSubmitAssetTest extends Wso2Base {

   private static final String assetName = "CreateSubmitAssetTest";
   private static final String assetDesc = "CreateSubmitAssetTest description";
   private static final String libraryName = "http://www.domain.com/assets";
   private static final String baseUrl = "http://www.domain.com/assets/";
   private static final String stringProperty = "a test value";
   private static final Long longProperty = new Long(1234567l);
   private static final Boolean booleanProperty = Boolean.FALSE;

   private CreateAndSubmitAssetResponse createAndSubmitAsset() throws Exception {
      AssetKey key = new AssetKey();
      key.setAssetName(assetName);

      BasicAssetInfo basicInfo = new BasicAssetInfo();
      basicInfo.setAssetKey(key);
      basicInfo.setAssetName(assetName);
      basicInfo.setAssetDescription(assetDesc);
      basicInfo.setAssetType("Service");

      ExtendedAssetInfo extendedInfo = new ExtendedAssetInfo();
      List<AttributeNameValue> attrs = extendedInfo.getAttribute();
      attrs.add(RSProviderUtil.newAttribute("namespace", libraryName));
      attrs.add(RSProviderUtil.newAttribute("stringProperty", stringProperty));
      attrs.add(RSProviderUtil.newAttribute("longProperty", longProperty.longValue()));
      attrs.add(RSProviderUtil.newAttribute("booleanProperty", booleanProperty.booleanValue()));

      AssetLifeCycleInfo lifeCycleInfo = new AssetLifeCycleInfo();
      lifeCycleInfo.setDomainOwner("John Doe");
      lifeCycleInfo.setDomainType("Technical Owner");

      AssetInfo assetInfo = new AssetInfo();
      assetInfo.setBasicAssetInfo(basicInfo);
      assetInfo.setExtendedAssetInfo(extendedInfo);
      assetInfo.setAssetLifeCycleInfo(lifeCycleInfo);

      Artifact endpoint = new Artifact();
      endpoint.setArtifactName("ep-" + assetName);
      endpoint.setArtifactCategory("endpoint");
      endpoint.setArtifactValueType(ArtifactValueType.URL);

      String endpointUrl = baseUrl + assetName;
      ArtifactInfo endpointInfo = new ArtifactInfo();
      endpointInfo.setArtifact(endpoint);
      endpointInfo.setArtifactDetail(endpointUrl.getBytes("UTF-8"));
      endpointInfo.setContentType("application/vnd.wso2.endpoint");

      Artifact wsdl = new Artifact();
      wsdl.setArtifactName(assetName + ".wsdl");
      wsdl.setArtifactCategory("wsdl");
      wsdl.setArtifactValueType(ArtifactValueType.FILE);

      ArtifactInfo wsdlInfo = new ArtifactInfo();
      wsdlInfo.setArtifact(wsdl);
      wsdlInfo.setArtifactDetail(loadFile("src/main/resources/META-INF/soa/services/wsdl/CreateServiceTest/CreateServiceTest.wsdl"));
      wsdlInfo.setContentType("application/wsdl+xml");

      List<ArtifactInfo> artifactList = assetInfo.getArtifactInfo();
      artifactList.add(endpointInfo);
      artifactList.add(wsdlInfo);

      FlattenedRelationship rel = new FlattenedRelationship();
      rel.getRelatedAsset();
      // relationList.add(
      // new Relation() {
      // {
      // this.setSourceAsset(RSProviderUtil.completeAssetKey(
      // new AssetKey() {
      // {
      // this.setAssetId(resources[0]);
      // }
      // }, null, null));
      // this.setTargetAsset(RSProviderUtil.completeAssetKey(
      // new AssetKey() {
      // {
      // this.setAssetId(dstAsset);
      // }
      // }, null, null));
      // this.setAssetRelationship("DependsOn");
      // }
      // });
      assetInfo.setFlattenedRelationship(rel);

      CreateAndSubmitAssetRequest request = new CreateAndSubmitAssetRequest();
      request.setAssetInfo(assetInfo);

      RepositoryServiceProvider provider = new RepositoryServiceProviderImpl();
      return provider.createAndSubmitAsset(request);
   }

   @Test
   @Ignore
   public void createTest() throws Exception {
      CreateAndSubmitAssetResponse response = createAndSubmitAsset();

      assertEquals("Error: " + getErrorMessage(response), AckValue.SUCCESS, response.getAck());
      assertEquals(null, response.getErrorMessage());

      // assertEquals("New", lc.getState());
      // assertEquals(true, lc.getItem(0).isValue());
   }
}
