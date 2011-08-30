
package org.ebayopensource.turmeric.errorlibrary.assertion;

import org.ebayopensource.turmeric.common.v1.types.CommonErrorData;
import org.ebayopensource.turmeric.common.v1.types.ErrorCategory;
import org.ebayopensource.turmeric.common.v1.types.ErrorSeverity;

public class ErrorDataCollection {

    private final static String ORGANIZATION = "ebayopensource";
    public final static CommonErrorData ASSERTION_CONTENT_MISSING = createCommonErrorData(119002L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSERTION_CONTENT_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData ASSET_NAME_MISSING = createCommonErrorData(119004L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSET_NAME_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData ASSERTIONS_MISSING = createCommonErrorData(119006L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSERTIONS_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData ASSERTION_NAME_MISSING = createCommonErrorData(119008L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSERTION_NAME_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData ASSERTION_ERROR_SEVERITY_MISSING = createCommonErrorData(119010L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSERTION_ERROR_SEVERITY_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData UNKNOWN_EXCEPTION = createCommonErrorData(119012L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "UNKNOWN_EXCEPTION", "Assertion", "AssertionsService", "");
    public final static CommonErrorData MULTIPLE_CONTENT_TAG_POPULATION_WARNING = createCommonErrorData(119014L, (ErrorSeverity.WARNING), (ErrorCategory.APPLICATION), "MULTIPLE_CONTENT_TAG_POPULATION_WARNING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData ASSERTION_OBJECT_CONTENT_MISSING = createCommonErrorData(119016L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSERTION_OBJECT_CONTENT_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData OBJECT_NOT_FOUND_EXCEPTION = createCommonErrorData(119018L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "OBJECT_NOT_FOUND_EXCEPTION", "Assertion", "AssertionsService", "");
    public final static CommonErrorData ASSET_TYPE_MISSING = createCommonErrorData(119020L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSET_TYPE_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData MODULE_SCRIPTS_MISSING = createCommonErrorData(119022L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "MODULE_SCRIPTS_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData ASSERTION_PROCESSOR_TYPE = createCommonErrorData(119024L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSERTION_PROCESSOR_TYPE", "Assertion", "AssertionsService", "");
    public final static CommonErrorData REQUEST_EMPTY = createCommonErrorData(119026L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "REQUEST_EMPTY", "Assertion", "AssertionsService", "");
    public final static CommonErrorData ARTIFACT_CATEGORY_MISSING = createCommonErrorData(119028L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ARTIFACT_CATEGORY_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData LIBRARY_NAME_MISSING = createCommonErrorData(119030L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "LIBRARY_NAME_MISSING", "Assertion", "AssertionsService", "");
    public final static CommonErrorData ASSERTION_GROUP_LIST_NULL = createCommonErrorData(119032L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "ASSERTION_GROUP_LIST_NULL", "Assertion", "AssertionsService", "");
    public final static CommonErrorData NOT_ALL_WSDL = createCommonErrorData(119034L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "NOT_ALL_WSDL", "Assertion", "AssertionsService", "");
    public final static CommonErrorData MISSING_ARTIFACT = createCommonErrorData(119036L, (ErrorSeverity.ERROR), (ErrorCategory.APPLICATION), "MISSING_ARTIFACT", "Assertion", "AssertionsService", "");

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
