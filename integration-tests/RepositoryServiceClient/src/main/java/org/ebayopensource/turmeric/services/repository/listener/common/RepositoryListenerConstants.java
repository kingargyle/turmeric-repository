/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repository.listener.common;

public class RepositoryListenerConstants {
   public static final String SUPER_USER = "logidex_adm";
   public static final String SUPER_USER_PASSWORD = "rm@ebay01";
   public static final String LIBRARYAPI_ADAPTER_CLASS = "com.logiclibrary.library.client.LibraryAPISOAPAdapter";
   public static String RM_FOLDER = "C:/RM/";
   public final static String SERVER_URL_PROPERTY = "library_url";
   public final static String ADMIN_USER_PROPERTY = "auth_userid";
   public final static String ADMIN_PASSEORD_PROPERTY = "auth_password";

   public final static String PROPERTY_TYPE_LIBRAY = "type_library";
   public final static String PROPERTY_ACT = "asset_capture_template";

   public final static String PROPERTY_ACTION = "action";
   // read the default reviewer when the specified reviewer NT login is not valid
   public final static String PROPERTY_DEFAULT_REVIEWER = "default_reviewer";
   // validate wsdl, data type check, send notification when errors/type missing
   public final static String ACTION_SUBMIT = "validate_wsdl_and_check_datatype";
   public final static String ACTION_LAST_SUBMIT = "mail_asset_reviewer_and_datatype_check";
   public final static String ACTION_SUBMIT_ASSERTION = "validate_xquery";
   public final static String WSDL_UPLOAD_INDICATOR = "com.ebay.services.repository.listener.handler.ServiceValidationHandler";

   // assignee assigns a reviewer and approve the request
   public final static String ACTION_ASSIGN_REVIEWER = "review_assignee_approved";
   // the assignee reject the request
   public final static String ACTION_ASSIGN_REJECT = "review_assignee_rejected";
   // the reviwerer rejects the request
   public final static String ACTION_REVIEWER_REJECT = "reviewer_rejected";
   // mail the asset revierwer to review the request
   public final static String ACTION_MAIL_REVIEWER = "mail_asset_reviewer";
   public final static String ACTION_UPDATE_ACT = "update_act";
   public final static String ACTION_VALIDATE_SCHEMA = "validate_schema";
   public final static String ACTION_MAIL_SUBMITEER_ENTRY_APPROVAL = "notify_all_entry_approved";
   public final static String ACTION_MAIL_SUBMITEER_EXIT_APPROVAL = "notify_all_exit_approved";

   public final static int ACTION_REJECT_ASSIGN = 0;
   public final static int ACTION_REJECT_OTHERS = 1;

   public final static String ACTION_MAIL_EXIT = "exit";
   public final static String ACTION_MAIL_ENTRY = "entry";

   public final static String ASSET_TYPE_ASSET = "Asset";
   public final static String ASSET_TYPE_SERVICE = "Service";
   public final static String ASSET_TYPE_TYPE = "Type";
   public static final String ASSET_TYPE_CONSUMER = "Consumer";

   public final static String FILTER_ASSET_NAME = "asset-name";
   public final static String FILTER_ASSET_TYPE = "asset-type";

   public final static String RELATIONSHIP_DEPENDED_TYPE = "dependent_datatypes";
   public final static String CLASSIFIER_ASSET_TYPE = "asset-type";
   public final static String CLASSIFIER_LIFECYCLE_STATE = "lifecycle_state";
   public final static String CLASSIFIER_PREV_LIFECYCLE_STATE = "prev_lifecycle_state";
   public final static String CLASSIFIER_MAIN_REVIEWER = "main_reviewer";
   public final static String CLASSIFIER_TRACKER_ID = "trackerID";
   public final static String CLASSIFIER_PM = "pm";
   public final static String CLASSIFIER_PJM = "pjm";
   public final static String CLASSIFIER_ARCHITECT = "architect";
   public final static String CLASSIFIER_APPROVER = "service_approver";
   public final static String CLASSIFIER_DOMAINOWNER = "domain_owner";
   public final static String CLASSIFIER_DOMAIN = "domain";
   public final static String CLASSIFIER_QE = "qe";
   public final static String CLASSIFIER_NAMESPACE = "namespace";
   public final static String CLASSIFIER_STAGINGURL = "staging_endpoint_url";
   public final static String CLASSIFIER_PRODURL = "production_endpoint_url";
   public final static String CLASSIFIER_CURRENT_DEPLOYED = "current_deployed";
   public final static String CLASSIFIER_LAST_APPROVER = "last_approver";
   public final static String CLASSIFIER_IS_APPROVAL_REQUIRED = "isApprovalRequired";

   public final static String CLASSIFIER_NEED_MORE_INFO = "Needs More Info";
   public final static String CLASSIFIER_PUBLISH_STATUS = "publishing_status";
   public final static String CLASSIFIER_ASSET_LAYER = "asset_layer";
   public final static String CLASSIFIER_SERVICE_LAYER = "service_layer";
   public final static String CLASSIFIER_SERVICE_TYPE = "service_type";
   public final static String CLASSIFIER_SOA_DASHBOARD_LINK = "soa_dashboard_link";

   public final static String CLASSIFIER_VALUE_PUBLISH_STATUS_PRI = "Private";
   public final static String CLASSIFIER_VALUE_PUBLISH_STATUS_PUB = "Public";

   public final static String CLASSIFIER_VALUE_TRUE = "true";
   public final static String CLASSIFIER_VALUE_FALSE = "false";
   public final static String CLASSIFIER_VALUE_ASSET_LAYER_BIZ = "Business Service";
   public final static String CLASSIFIER_VALUE_ASSET_LAYER_COM = "Common Service";
   public final static String CLASSIFIER_VALUE_ASSET_LAYER_INT = "Intermediate Service";
   public final static String CLASSIFIER_VALUE_ASSET_LAYER_DATA = "Data Type";
   public final static String CLASSIFIER_REQUESTOR_USER_ID = "requester_user_id";
   public final static String CLASSIFIER_VALUE_EXIT_REVIEW = "Exit Review";
   public final static String CLASSIFIER_VALUE_APPROVED = "Approved";
   public final static String CLASSIFIER_VALUE_RETIRED = "Retired";

   public final static String CLASSIFIER_VALUE_SEVICE_TYPE_PRO = "Production";
   public final static String CLASSIFIER_VALUE_SERVICE_TYPE_EXP = "Experimental";

   public final static String SERVICE_FLOW_BUSINESS = "business";
   public final static String SERVICE_FLOW_INTERMEDIATE = "intermediate";
   public final static String SERVICE_FLOW_COMMON = "common";
   public final static String SERVICE_FLOW_EXPERIMENTAL = "experimental";
   public final static String SERVICE_FLOW_INTERNAL = "internal";

   // public final static String libraryURL = "https://crp-soa-04.corp.ebay.com/rm";
   // public final static String ServiceLibraryName = "GovernedAssets";
   public final static String LOGIDEEX_ADMIN = "logidex.adminuser";
   public final static String LOGIDEX_ADMIN_PASSWORD = "logidex.password";
   public static int RETURN_CODE_SUCCESS = 0;
   public static int RETURN_CODE_CONFIGURATION_ERROR = 1;
   public static int RETURN_CODE_SYSTEM_ERROR = -1;
   public static final int RETURN_CODE_APPROVAL_REJECTED = 2;

   // The authenticator class for the connection
   public static final String AUTHENTICATOR = "com.logiclibrary.authentication.LDAPWithPropertiesAuthenticator";

   public final static String LDAP_USER = "ldap.user";
   public final static String LDAP_PASSWORD = "ldap.password";

   // public final static String ADMIN_DL = "do-not-reply_governed-assets@ebay.com";
   // public final static String ADMIN_DL_WO_EBAYCOM = "DL-eBay-SOA-Registry-Admin";

   public final static String ARTIFACT_WSDL = "artifact:wsdl:";
   public final static String ARTIFACT_XQUERY_ASSET_ID = "artifact:assertion-script:";
   public final static String ARTIFACT_MODULE_XQUERY_ASSET_ID = "artifact:assertion-module-script:";
   public final static String ARTIFACT_SCHEMA = "artifact:schema";
   public final static String ARTIFACT_CATEGORY_WSDL = "wsdl";
   public final static String ARTIFACT_CATEGORY_SCHEMA = "schema";
   public final static String ARTIFACT_DISPLAY_WSDL = "WSDL";
   public final static String ARTIFACT_DISPLAY_SCHEMA = "Schema";
   public final static String ARTIFACT_DISPLAY_OVERVIEW = "Overview";
   public final static String ARTIFACT_CATEGORY_OVERVIEW = "overview";
   public final static String ARTIFACT_DISPLAY_DASHBOARD = "SOA Dashboard Monitoring Link";
   public final static String ARTIFACT_CATEGORY_DASHBOARD = "soa_dashboard_mon_link";
   public final static String ARTIFACT_CATEGORY_CREDENTIAL = "credentials";

   public final static String BOOTSTRAP_LIBRARY = "BootstrapLibrary";

   public final static String WSDL_ERROR = "Error";
   public final static String WSDL_WARNING = "Warning";

   public final static String EMAIL_ERROR_SECTION = "\n--------------------------------------------";
   public final static String EMAIL_DOMAIN = "@ebay.com";
   public final static String EMAIL_DOMAIN_PAYPAL = "@paypal.com";
   public final static String RM_PATH_EDIT_ASSET = "/application/cataloger/showEditPage.do?assetId=";
   public final static String RM_PATH_APPROVE_ASSET = "/application/general/assetRequest.do?role=Asset%20Reviewer&requestId=";
   public final static String RM_PATH_APPROVE_ASSET_FORSUBMITTER = "/application/general/assetRequest.do?requestId=";

   public final static String FILE_EXTENSION_WSDL = ".wsdl";
   public final static String FILE_EXTENSION_XQUERY = ".xq";
   public final static String FILE_EXTENSION_SCHEMA = ".xsd";

   public final static String SCHEMA_ROOT_ELEMENT = "xs:schema";
   public final static String CONTENT_TYPE_XML = "text/xml";

   public final static String EBAYRM_FILE_LOCATION = "C:\\RM\\ebayconf\\ebayrm.properties";
   public final static String EBAY_DOMAIN_FILE_LOCATION = "C:\\RM\\ebayconf\\ebaydomain.properties";

   public final static String EBAYRM_FILE_DIR = "C:\\RM\\ebayconf";
   public final static String VALUE_DEFAULT_VERSION = "1.0.0";
   public final static String VALUE_DEFAULT_TYPE_CAPTURE_TEMPLATE = "Data Type - Proposed";

   public final static String CLASSIFIER_SOA_DASHBOARD_LINK_VALUE = "http://blade3-13.arch.ebay.com:8080/GraphEngine/Dashboard/Dashboard.html#app=d0ed&4719-selectedIndex=1&f33-selectedIndex=1";

   public final static int CATALOG_ASSET_ALL = 0;
   public final static int CATALOG_ASSET_PUBLISHED = 1;
   public final static int CATALOG_ASSET_NONPUBISHED = 2;

   public final static String WSDL_VALIDATOR_ASSERTION_GROUP_NAME = "soa_wsdl_assertionGroup";
   public final static String WSDL_VALIDATOR_ASSERTION_LIBRARY_NAME = "AssertionsDevelopment";
   public final static String WSDL_VALIDATOR_ASSERTION_GROUP_VERSION = "1.0.0";
   // public static final String WSDL_VALIDATOR_ASSERTION_SERVICE_LOCATION =
   // "http://crp-soa-04.corp.ebay.com:8080/AssertionsService/AssertionsService";
   public static final String WSDL_VALIDATOR_ASSERTION_SERVICE_ARTIFACT_CATEGORY = "WSDL";
   public static final String WSDL_VALIDATOR_ASSERTION_SERVICE_ASSET_TYPE = "Service";

   public static final String XQUERY_VALIDATOR_BINDING_VARIABLE = "FILE_PATH";
   public static final String XQUERY_VALIDATOR_BINDING_FILE_PATH = "wsdl_file_path";
   public static final String XQUERY_VALIDATOR_MODULE_PATH = "/assertion/soa_assertionFrameworkUtil_assertionModule/1-0-0/Assertion-Modelue-Script";
   public static final String XQUERY_VALIDATOR_RM_PASSWORD = "rm@ebay01";
   public static final String XQUERY_TEMPORARY_FILE_PATH = "c:\\temp\\AssertionValidator_xquery.xq";
   public static final String XQUERY_VALIDATOR_ASSERTION_GROUP_TYPE = "AssertionGroup";
   public static final String XQUERY_VALIDATOR_ASSERTION_MODULE_TYPE = "AssertionModule";
   public static final Object CLASSIFIER_VALUE_PROPOSED = "Proposed";
   public static final String CLASSIFIER_IS_GENERATED = "is_autogenerated";
   public static final String CLASSIFIER_VISIBILITY = "visibility";
   public static final Object CLASSIFIER_VISIBILITY_INTERNAL = "Internal";
   public static final String CLASSIFIER_SOA_SME = "soasme";
   public static final String CLASSIFIER_TECHNICAL_WRITER = "technical_writer";
   public static final String CLASSIFIER_BUSINESS_PRODUCT_MANAGER = "business_pm";
   public static final String CLASSIFIER_API_PRODUCT_MANAGER = "api_pm";
   public static final String CLASSIFIER_DEVELOPER_TECHNICAL_SUPPORT = "developer_technical_support";
   public static final String CLASSIFIER_REVIEWER = "reviewer";
   public static final String CLASSIFIER_SERVICE_OWNER = "owner";
   public static final String EMAIL_PCR_LISTENERS = "DL-eBay-SOAPCR-Listeners@ebay.com";
   public static final String NEW_SERVICE_VALIDATION = "new_service_validation";
   public static final String NEW_FUNCTIONAL_DOMAIN_VALIDATION = "validate_functional_domain_name";
   public static final String FUNCTIONAL_DOMAIN_APPROVED_HANDLER = "com.ebay.services.repository.listener.handler.FunctionalDomainApprovedHandler";
   public static final String FUNCTIONAL_DOMAIN_REJECTED_HANDLER = "com.ebay.services.repository.listener.handler.FunctionalDomainRejectedHandler";

   public static final String RELATED_ASSET_NAME_FUNCTIONAL_DOMAIN = "functionalDomain";
   public static final String CAPTURE_TEMPLATE_NAME_FUNCTIONAL_DOMAIN_TEMPLATE = "Functional Domain Template";
   public static final String CLASSIFIER_ESCALATION_MANAGER = "escalation_manager";
   public static final String CLASSIFIER_MANAGER_OWNER = "manager-owner";
   public static final String CLASSIFIER_VALUE_DEPRECATED = "Deprecated";
   public static final String ASSET_TYPE_FUNCTIONAL_DOMAIN = "Functional Domain";
   public static final String CLASSIFIER_DOMAIN_STRING = "domain_string";

   public static final String CLASSIFIER_VALUE_DEPLOYED = "Deployed";

   public static final String EMAIL_SUBJECT_DEPLOYED_SUBMISSION = "Request to move {0} version {1} to DEPLOYED";
   public static final String EMAIL_SUBJECT_DEPLOYED_REJECTED = "{0} {1} has been rejected for Deployment";
   public static final String EMAIL_SUBJECT_DEPLOYED_APPROVED = "{0} has been moved to DEPLOYED";
   public static final String EMAIL_SUBJECT_COMMENT_ADDED = "{0} has added a comment for service {1}({2})";
   public static final String ROLE_SERVICE_APPROVER = "Service Approver";
   public static final String CLASSIFIER_VALIDATION_STATUS = "validation_status";
   public static final String PROPERTY_NAME_ROLE_PLAYER = "RolePlayerProperty";
   public static final String ARTIFACT_NAME_CALL_REF_DOC = "call_ref_document";
   public static final String ARTIFACT_CATEGORY_WIKI_URL = "wiki_link";
   public static final String CONSUMER_SERVICE_URL_PREFIX = "consumerservice.url";
   public static final String CONSUMER_TYPE_SECRET = "Secret";
   public static final String CONSUMER_TYPE_IAF_TOKEN = "IAFToken";
   public static final String CONSUMER_RESPONSE_ACK_SUCCESS = "SUCCESS";
   public static final String CONSUMER_RESPONSE_ACK_FAILURE = "FAILURE";
   public static final String CONSUMER_RESPONSE_ACK_PARTIAL_FAILURE = "PARTIAL_FAILURE";
   public static final String CONSUMER_RESPONSE_ACK_WARNING = "WARNING";
   public static final String CLASSIFIER_CONSUMER_ID = "consumerID";
   public static final String CONSUMER_ID_PREFIX = "ebay-marketplace-consumerid:";
   public static final String DEV_ID_PREFIX = "ebay-marketplace-devid:";
   public static final String CLASSIFIER_DEV_ID = "devID";

   public static enum CREDENTIAL_STATUS {
      SUCCESS, PARTIAL_FAILURE, NOCHANGE
   };
}
