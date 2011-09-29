/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repositoryservice.operation.consumer;

import java.util.List;

import org.ebayopensource.turmeric.repository.v1.services.Artifact;
import org.ebayopensource.turmeric.repository.v1.services.ArtifactInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetBaseInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetKey;
import org.ebayopensource.turmeric.repository.v1.services.AssetLifeCycleInfo;
import org.ebayopensource.turmeric.repository.v1.services.AssetStatus;
import org.ebayopensource.turmeric.repository.v1.services.AssetsGroupedByCategory;
import org.ebayopensource.turmeric.repository.v1.services.AssetsOfACategory;
import org.ebayopensource.turmeric.repository.v1.services.AttributeNameValue;
import org.ebayopensource.turmeric.repository.v1.services.BasicAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.BasicServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.ExtendedAssetInfo;
import org.ebayopensource.turmeric.repository.v1.services.FlattenedRelationship;
import org.ebayopensource.turmeric.repository.v1.services.GraphRelationship;
import org.ebayopensource.turmeric.repository.v1.services.Relation;
import org.ebayopensource.turmeric.repository.v1.services.ServiceInfo;
import org.ebayopensource.turmeric.repository.v1.services.TypedRelationNode;

public class RepositoryServiceConsumerUtil {
   public static String validateAssetInfo(AssetInfo assetInfo) {
      List<ArtifactInfo> artifactInfos = assetInfo.getArtifactInfo();
      if (artifactInfos != null) {
         if (validateArtifactInfo(artifactInfos).equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (assetInfo.getAssetLifeCycleInfo() != null) {
         if (validateAssetLifeCycleInfo(assetInfo.getAssetLifeCycleInfo()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (validateBasicAssetInfo(assetInfo.getBasicAssetInfo()).equalsIgnoreCase(
               RepositoryServiceClientConstants.FAILURE)) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (assetInfo.getExtendedAssetInfo() != null) {
         if (validateExtendedAssetInfo(assetInfo.getExtendedAssetInfo()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (assetInfo.getFlattenedRelationship() != null) {
         if (validateFlattenedRelationship(assetInfo.getFlattenedRelationship()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateAssetInfo(AssetInfo assetInfo, String validationType) {
      if (assetInfo.getAssetLifeCycleInfo() != null) {
         if (validateAssetLifeCycleInfo(assetInfo.getAssetLifeCycleInfo()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (validateBasicAssetInfo(assetInfo.getBasicAssetInfo()).equalsIgnoreCase(
               RepositoryServiceClientConstants.FAILURE)) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (assetInfo.getExtendedAssetInfo() != null) {
         if (validateExtendedAssetInfo(assetInfo.getExtendedAssetInfo()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (assetInfo.getFlattenedRelationship() != null) {
         if (validateFlattenedRelationship(assetInfo.getFlattenedRelationship()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateServiceInfo(ServiceInfo serviceInfo) {
      if (serviceInfo == null) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (validateBasicServiceInfo(serviceInfo.getBasicServiceInfo()).equalsIgnoreCase(
               RepositoryServiceClientConstants.FAILURE)) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      List<ArtifactInfo> artifactInfos = serviceInfo.getArtifactInfo();
      if (artifactInfos != null) {
         if (validateArtifactInfo(artifactInfos).equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (serviceInfo.getAssetLifeCycleInfo() != null) {
         if (validateAssetLifeCycleInfo(serviceInfo.getAssetLifeCycleInfo()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (serviceInfo.getFlattenedRelationship() != null) {
         if (validateFlattenedRelationship(serviceInfo.getFlattenedRelationship()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateServiceInfo(ServiceInfo serviceInfo, String validationType) {
      if (serviceInfo == null) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (validateBasicServiceInfo(serviceInfo.getBasicServiceInfo()).equalsIgnoreCase(
               RepositoryServiceClientConstants.FAILURE)) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (serviceInfo.getAssetLifeCycleInfo() != null) {
         if (validateAssetLifeCycleInfo(serviceInfo.getAssetLifeCycleInfo()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (serviceInfo.getFlattenedRelationship() != null) {
         if (validateFlattenedRelationship(serviceInfo.getFlattenedRelationship()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateBasicServiceInfo(BasicServiceInfo basicServiceInfo) {
      if (basicServiceInfo == null) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (basicServiceInfo.getAssetKey() != null) {
         if (validateAssetKey(basicServiceInfo.getAssetKey())
                  .equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      if (basicServiceInfo.getServiceDescription() == null || basicServiceInfo.getServiceName() == null
               || basicServiceInfo.getServiceVersion() == null) {
         return RepositoryServiceClientConstants.FAILURE;
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateArtifactInfo(List<ArtifactInfo> artifactInfos) {
      for (ArtifactInfo artifactInfo : artifactInfos) {
         Artifact artifact = artifactInfo.getArtifact();

         if (validateArtifact(artifact).equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
         if (artifactInfo.getArtifactDetail() == null) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateArtifact(Artifact artifact) {
      if (artifact == null || artifact.getArtifactCategory() == null || artifact.getArtifactIdentifier() == null
               || artifact.getArtifactName() == null) {
         return RepositoryServiceClientConstants.FAILURE;
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateAssetLifeCycleInfo(AssetLifeCycleInfo assetLifeCycleInfo) {
      if (assetLifeCycleInfo.getLifeCycleState() == null /* || assetLifeCycleInfo.getArchitect() == null */) {
         return RepositoryServiceClientConstants.FAILURE;
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateBasicAssetInfo(BasicAssetInfo basicAssetInfo) {
      if (basicAssetInfo.getAssetType() == null || basicAssetInfo.getAssetName() == null
               || basicAssetInfo.getAssetDescription() == null || basicAssetInfo.getVersion() == null) {
         return RepositoryServiceClientConstants.FAILURE;
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateExtendedAssetInfo(ExtendedAssetInfo extendedAssetInfo) {
      List<AttributeNameValue> attributeNameValues = extendedAssetInfo.getAttribute();

      if (attributeNameValues != null) {
         for (AttributeNameValue attributeNameValue : attributeNameValues) {
            if (attributeNameValue.getAttributeName() == null) {
               return RepositoryServiceClientConstants.FAILURE;
            }
            if (attributeNameValue.getAttributeValueLong() == null
                     && attributeNameValue.getAttributeValueString() == null
                     && attributeNameValue.isAttributeValueBoolean() == null) {
               return RepositoryServiceClientConstants.FAILURE;
            }
         }
      }
      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateFlattenedRelationship(FlattenedRelationship flattenedRelationship) {
      if (validateAssetKey(flattenedRelationship.getSourceAsset()).equalsIgnoreCase(
               RepositoryServiceClientConstants.FAILURE)) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      List<Relation> relations = flattenedRelationship.getRelatedAsset();
      if (relations != null) {
         if (validateRelations(relations).equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateRelations(List<Relation> relations) {
      for (Relation relation : relations) {
         if (relation.getAssetRelationship() == null) {
            return RepositoryServiceClientConstants.FAILURE;
         }
         if (validateAssetKey(relation.getSourceAsset()).equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
         if (validateAssetKey(relation.getTargetAsset()).equalsIgnoreCase(RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }

      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateAssetStatus(AssetStatus assetStatus) {
      if (assetStatus.getAssetSubmissionTime() == null || assetStatus.getLastActivity() == null
               || assetStatus.getLastUpdateTime() == null || assetStatus.getState() == null /*
                                                                                             * ||
                                                                                             * assetStatus.getReviewer()
                                                                                             * == null ||
                                                                                             * assetStatus.getReviewer
                                                                                             * ().getRole() == null
                                                                                             */) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      return RepositoryServiceClientConstants.SUCCESS;
   }

   public static String validateAssetKey(AssetKey assetKey) {
      if (assetKey != null) {
         if (assetKey.getAssetId() == null || assetKey.getAssetName() == null || assetKey.getLibrary() == null
                  || (assetKey.getLibrary().getLibraryId() == null && assetKey.getLibrary().getLibraryName() == null)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
         return RepositoryServiceClientConstants.SUCCESS;
      }
      return RepositoryServiceClientConstants.FAILURE;
   }

   public static String validateAssetBaseInfo(AssetBaseInfo assetBaseInfo) {
      if (assetBaseInfo != null) {
         if (assetBaseInfo.getAssetVersion() == null || assetBaseInfo.getAssetDescription() == null
                  || assetBaseInfo.getAssetKey() == null) {
            return RepositoryServiceClientConstants.FAILURE;
         } else {
            return validateAssetKey(assetBaseInfo.getAssetKey());
         }
      }
      return RepositoryServiceClientConstants.FAILURE;
   }

   public static String validateAssetsGroupedByCategory(AssetsGroupedByCategory assetsGroupedByCategory) {

      if (assetsGroupedByCategory != null) {

         if (assetsGroupedByCategory.getAssetsOfACategory() == null
                  || assetsGroupedByCategory.getAssetsOfACategory().isEmpty()) {
            return RepositoryServiceClientConstants.FAILURE;
         } else {
            for (AssetsOfACategory assetsOfACategory : assetsGroupedByCategory.getAssetsOfACategory()) {

               if (assetsOfACategory.getBasicAssetinfo() != null) {
                  // return validateBasicAssetInfo(assetsOfACategory.getBasicAssetinfo());
               }

               if (assetsOfACategory.getExtendedAssetInfo() != null) {
                  // return validateExtendedAssetInfo(assetsOfACategory.getExtendedAssetInfo());
               }
            }
         }
         return RepositoryServiceClientConstants.SUCCESS;
      }
      return RepositoryServiceClientConstants.FAILURE;
   }

   public static String validateGraphRelationship(GraphRelationship graphRelationship) {
      if (graphRelationship == null) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      if (RepositoryServiceConsumerUtil.validateAssetKey(graphRelationship.getSourceAsset()).equalsIgnoreCase(
               RepositoryServiceClientConstants.FAILURE)) {
         return RepositoryServiceClientConstants.FAILURE;
      }
      List<TypedRelationNode> typedRelationNodes = graphRelationship.getTargetAsset();
      for (TypedRelationNode typedRelationNode : typedRelationNodes) {
         if (typedRelationNode.getAssetRelationship() == null) {
            return RepositoryServiceClientConstants.FAILURE;
         }

         if (validateGraphRelationship(typedRelationNode.getTarget()).equalsIgnoreCase(
                  RepositoryServiceClientConstants.FAILURE)) {
            return RepositoryServiceClientConstants.FAILURE;
         }
      }
      return RepositoryServiceClientConstants.SUCCESS;
   }
}
