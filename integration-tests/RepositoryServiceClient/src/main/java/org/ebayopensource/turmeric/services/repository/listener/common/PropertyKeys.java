/*******************************************************************************
 * Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.services.repository.listener.common;

public enum PropertyKeys {
   LIBRARY_URL("libraryURL"), SERVICE_LIBRARY_NAME("ServiceLibraryName"), ADMIN_DL_WO_EBAY_COM("admin_dl_wo_ebaycom"), ADMIN_DL(
            "admin_Dl"), JOBS_XML("jobs_xml"), PUBLIC_LIFECYCLE_STAGES("public_lifecycle_stages"), MAIL_TO_FILE(
            "mail_to_file"), ALL_MAIL_TO_FILE("all_mail_to_file"), MAIL_TO_FILEPATH("mail_to_filepath"), WSDL_DOC_SERVER_URL(
            "wsdl_doc_server_url"), WSDL_DOC_BASE_LOC("wsdl_doc_base_loc"), WSDL_DOC_LOG_BASE_LOC(
            "wsdl_doc_log_base_loc"), SMC_SERVICE_URL("smc_service_url"), SUPER_USER_NAME("admin"), SUPER_USER_PASSWORD(
            "admin"), DL_EBAY_SOA_ADMIN("dl_ebay_soa_admin"), WIKI_BASE_URL_SERVICE("wiki_base_url_service"), WIKI_BASE_URL_FUNCTIONAL_DOMAIN(
            "wiki_base_url_functional_domain"), AR_TEMP_DIR("ar_temp_dir"), DELETE_TEMP_FILES("delete_temp_files"), JRE_PATH(
            "jre_path"), DOC_GEN_PATH_URL("doc_gen_path_url"), CONSUMER_TYPE("consumer_type"), COMPANY("company"), ADDRESS(
            "address"), CITY("city"), STATE("state"), ZIP("zip"), SERVICE_ASSET_DL("service_asset_Dl"), COUNTRY_ID(
            "country_id"), LDAP_HOST("ldap_host"), LDAP_PORT("ldap_port"), IS_SSL_REQUIRED("is_ssl_required"), JAVA_HOME(
            "java_home");

   private final String value;

   PropertyKeys(String v) {
      value = v;
   }

   public String value() {
      return value;
   }
}
