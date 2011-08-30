
package org.ebayopensource.turmeric.errorlibrary.repository;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorCategory;
import org.ebayopensource.turmeric.common.v1.types.ErrorSeverity;

public class ErrorDataCollection {

    private final static String ORGANIZATION = "ebayopensource";
    public final static CommonErrorData BASE_ASSETINFO_MISSING = createCommonErrorData(118004L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "BASE_ASSETINFO_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ASSET_NAME_MISSING = createCommonErrorData(118006L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSET_NAME_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ASSET_VERSION_MISSING = createCommonErrorData(118008L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSET_VERSION_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ASSET_ID_MISSING = createCommonErrorData(118010L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSET_ID_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData LIBRARY_ID_MISSING = createCommonErrorData(118012L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "LIBRARY_ID_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData DEPTH_MISSING = createCommonErrorData(118014L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "DEPTH_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData UNKNOWN_EXCEPTION = createCommonErrorData(118016L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "UNKNOWN_EXCEPTION", "Repository", "RepositoryService", "");
    public final static CommonErrorData NO_ARTIFACTS_FOR_ADDING = createCommonErrorData(118018L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "NO_ARTIFACTS_FOR_ADDING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ASSET_NAME_AND_ID_MISSING = createCommonErrorData(118020L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSET_NAME_AND_ID_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ASSET_TYPE_MISSING = createCommonErrorData(118022L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSET_TYPE_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData SOURCE_ASSET_NOT_ENTERED = createCommonErrorData(118024L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "SOURCE_ASSET_NOT_ENTERED", "Repository", "RepositoryService", "");
    public final static CommonErrorData RELATIONS_NOT_ENTERED = createCommonErrorData(118026L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "RELATIONS_NOT_ENTERED", "Repository", "RepositoryService", "");
    public final static CommonErrorData INVALID_VERSION = createCommonErrorData(118028L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "INVALID_VERSION", "Repository", "RepositoryService", "");
    public final static CommonErrorData ARTIFACT_EMPTY_EXCEPTION = createCommonErrorData(118030L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ARTIFACT_EMPTY_EXCEPTION", "Repository", "RepositoryService", "");
    public final static CommonErrorData RELATIONSHIP_NAME_NOT_ENTERED = createCommonErrorData(118032L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "RELATIONSHIP_NAME_NOT_ENTERED", "Repository", "RepositoryService", "");
    public final static CommonErrorData BASIC_SERVICE_INFO_MISSING = createCommonErrorData(118034L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "BASIC_SERVICE_INFO_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData REQUEST_EMPTY = createCommonErrorData(118036L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "REQUEST_EMPTY", "Repository", "RepositoryService", "");
    public final static CommonErrorData ARTIFACT_NAME_MISSING = createCommonErrorData(118038L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ARTIFACT_NAME_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ARTIFACT_VALUE_TYPE_MISSING = createCommonErrorData(118040L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ARTIFACT_VALUE_TYPE_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ARTIFACT_CATEGORY_MISSING = createCommonErrorData(118042L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ARTIFACT_CATEGORY_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ASSET_ID_NOT_NEEDED = createCommonErrorData(118044L, (ErrorSeverity.WARNING), (ErrorCategory.APPLICATION), "ASSET_ID_NOT_NEEDED", "Repository", "RepositoryService", "");
    public final static CommonErrorData ASSET_KEY_MISMATCH_ERROR = createCommonErrorData(118046L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSET_KEY_MISMATCH_ERROR", "Repository", "RepositoryService", "");
    public final static CommonErrorData CAPTURE_TEMPLATE_NOT_RESOLVED = createCommonErrorData(118048L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "CAPTURE_TEMPLATE_NOT_RESOLVED", "Repository", "RepositoryService", "");
    public final static CommonErrorData LIBRARY_NAME_MISSING = createCommonErrorData(118050L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "LIBRARY_NAME_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData MISSING_ASSETINFO = createCommonErrorData(118052L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "MISSING_ASSETINFO", "Repository", "RepositoryService", "");
    public final static CommonErrorData APPROVAL_ROLE_MISSING = createCommonErrorData(118054L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "APPROVAL_ROLE_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData REJECTION_ROLE_MISSING = createCommonErrorData(118056L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "REJECTION_ROLE_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData APPROVAL_INFO_MISSING = createCommonErrorData(118058L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "APPROVAL_INFO_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData REJECTION_INFO_MISSING = createCommonErrorData(118060L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "REJECTION_INFO_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData NEXT_LEVEL_ATTRIBUTE_MISSING = createCommonErrorData(118062L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "NEXT_LEVEL_ATTRIBUTE_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData TARGET_ASSETKEY_MISSING = createCommonErrorData(118064L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "TARGET_ASSETKEY_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ASSETKEY_MISSING = createCommonErrorData(118066L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSETKEY_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData LIBRARY_MISSING = createCommonErrorData(118068L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "LIBRARY_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData FLATTENED_RELATIONSHIP_MISSING = createCommonErrorData(118070L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "FLATTENED_RELATIONSHIP_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData ARTIFACT_MISSING = createCommonErrorData(118072L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ARTIFACT_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData NEW_RELATION_MISSING = createCommonErrorData(118074L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "NEW_RELATION_MISSING", "Repository", "RepositoryService", "");
    public final static CommonErrorData NO_REQUEST_PARAM = createCommonErrorData(118076L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "NO_REQUEST_PARAM", "Repository", "RepositoryService", "");
    public final static CommonErrorData GROUP_NOT_PROVIDED = createCommonErrorData(118078L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "GROUP_NOT_PROVIDED", "Repository", "RepositoryService", "");
    public final static CommonErrorData COMMENT_TOO_LONG = createCommonErrorData(118080L, (ErrorSeverity.WARNING), (ErrorCategory.APPLICATION), "COMMENT_TOO_LONG", "Repository", "RepositoryService", "");
    public final static CommonErrorData SERVICE_PROVIDER_EXCEPTION = createCommonErrorData(118082L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "SERVICE_PROVIDER_EXCEPTION", "Repository", "RepositoryService", "");

    private static CommonErrorData createCommonErrorData(long errorId, ErrorSeverity severity, ErrorCategory category, String errorName, String domain, String subDomain, String errorGroup) {
        CommonErrorData errorData = new CommonErrorData();
        errorData.setErrorId(errorId);
        errorData.setSeverity(severity);
        errorData.setCategory(category);
        errorData.setSubdomain(subDomain);
        errorData.setDomain(domain);
        errorData.setErrorGroups(errorGroup);
        errorData.setErrorName(errorName);
        errorData.setOrganization(ORGANIZATION);
        return errorData;
    }

}
