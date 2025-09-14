/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.util;


/**
 * @author Aparna_Deshmukh01
 *
 */
public class ECRMSConstants {
	
	//Struts return Strings
	public final static String UN_AUTHORIZED_ACCESS = "unAuthorizedAccess";
	
	public final static String LOGIN_TYPE= "LOGIN_TYPE";
	public final static String UDE_LOGIN= "UDE_LOGIN";
	
	public final static String USERPROFILE_KEY= "userProfile";
	public final static String SUCCESS = "success";
	public final static String NEW_PARA = "\n\n";
	public final static String NEW_LINE = "\n";
	public final static String TRUE = "true";
	public final static String FALSE = "false";
	public final static String ORGANIZATION ="Dealer";
	
	// dropdown constants
	public static final String SEARCH_SELECT = "--Select--";
	public static final String SEARCH_ALL = "All";
	public static final String EMPTY_STRING = "";
	public static final String COUNTRY_USA = "USA";
	public static final String COUNTRY_CAN = "CAN";
	public static final String COUNTRY_US = "US";
	public static final String COUNTRY_CA = "CA";
	
	//ECRMS user list
	public static final String SYSTEM_ADMIN = "ECRMS_ADMIN";
	public static final String DEALER_USER = "ECRMS_DLR";
	public static final String CORPORATE_USER = "ECRMS_CORP";
	public static final String SUPER_USER = "ECRMS_SUPER_USER";
	public static final String REVIEWER_USER = "ECRMS_VIEW";
	public static final String DEALER_APPROVER = "ECRMS_DLR_APPROVER";// This is not an LDAP Role
	
	public static final String USER_CAN= "USER_CAN";
	
	public static final String DEALER_USER_FR = "ECRMS_DLR_FR";
	public static final String SYSTEM_ADMIN_FR = "ECRMS_ADMIN_FR";
	public static final String CORPORATE_USER_FR = "ECRMS_CORP_FR";
	public static final String SUPER_USER_FR = "ECRMS_SUPER_USER_FR";
	public static final String REVIEWER_USER_FR = "ECRMS_VIEW_FR";
	
	//messages for the login
	public final static String ENTER_PASSWORD_MESSAGE = "Please enter a Password";
	public static final String FRENCH_LANGUAGE= "fr";
	public static final String ENGLISH_LANGUAGE= "en";
	
	
	//Action constants for Create New Certificate
	public static final String REQUEST_NUM = "requestNum";
	public static final String FROM = "from";
	public static final String CREATE_REQUEST = "createRequest";
	public static final String HOME = "home";
	public static final String DOWNLOAD = "download";
	public static final String ADVANCE_SEARCH = "advanceSearch";
	public static final String ACTIVE_CERT_FOR_EXST_DLR = "ActiveCertForExstDlr";
	public static final String ACTIVE_CERT_FOR_DIFF_DLR = "ActiveCertForDiffDlr";
	public static final String REVOKE_FROM_CERT_MGMT = "revokeFromCertMgmt";
	public static final String REVOKE_FROM_CREATE_REQUEST = "revokeFromCreateRequest";
	public static final String NEW_CERT_REQUEST = "newCertificateRequest";

	
	//Mybatis constants for Create New Certificate
	public static final String RETRIVE_DEALER_DATA = "com.vwoa.ecrms.dao.certrequest.CertRequestDAOImpl.retrieveDealerData";
	public static final String RETRIVE_DEALER_DETAIL_DATA = "com.vwoa.ecrms.dao.searchcertificaterequest.SearchCertRequestDAOImpl.retrieveDealerDetailData";
	public static final String RETRIEVE_DEVICE_DATA = "com.vwoa.ecrms.dao.common.CommonDAOImpl.retrieveDeviceData";
	public static final String RETRIEVE_ALL_DEVICE_DATA = "com.vwoa.ecrms.dao.common.CommonDAOImpl.retrieveAllDeviceData";
	public static final String RETRIEVE_STATUS_DATA = "com.vwoa.ecrms.dao.common.CommonDAOImpl.retrieveStatusData";
	public static final String VERIFY_ACT_CERT_FOR_HWID = "com.vwoa.ecrms.dao.certrequest.CertRequestDAOImpl.verifyActCertForHWId";
	public static final String VERIFY_ACT_CERT_FOR_EXT_DLR = "com.vwoa.ecrms.dao.certrequest.CertRequestDAOImpl.verifyActCertForExtDlr";
	public static final String INSERT_CERT_REQUEST = "com.vwoa.ecrms.dao.certrequest.CertRequestDAOImpl.insertCertRequest";
	public static final String INSERT_CERT_REQUEST_STATUS = "com.vwoa.ecrms.dao.certrequest.CertRequestDAOImpl.insertCertRequestStatus";
	public static final String RETRIVE_CERT_REQ_DATA = "com.vwoa.ecrms.dao.certrequest.CertRequestDAOImpl.retrieveCertReqData";
	public static final String RETRIVE_REQ_COMMENT_DATA = "com.vwoa.ecrms.dao.certrequest.CertRequestDAOImpl.retrieveReqCommentData";
	public static final String INSERT_CERT_DETAIL = "com.vwoa.ecrms.dao.certrequest.EditStatusCertRequestDAOImpl.insertCertDetail";
	public static final String UPDATE_CERT_REQUEST = "com.vwoa.ecrms.dao.certrequest.CertRequestDAOImpl.updateCertRequest";
	public static final String UPDATE_CERT_REQUEST_STATUS = "com.vwoa.ecrms.dao.certrequest.EditStatusCertRequestDAOImpl.updateCertReqStatus";
	public static final String RETRIEVE_EMAIL_DATA = "com.vwoa.ecrms.dao.certrequest.EditStatusCertRequestDAOImpl.retriveEmailDetails";
	public static final String INSERT_CERT_REQUEST_COMMENT ="com.vwoa.ecrms.dao.certrequest.CertRequestDAOImpl.insertCertRequestComment";
	public static final String RETRIEVE_CONFIG_VALUE = "com.vwoa.ecrms.dao.certrequest.EditStatusCertRequestDAOImpl.retriveExpiryPeriod";
	public static final String RETRIEVE_FILE_NAME = "com.vwoa.ecrms.dao.certrequest.EditStatusCertRequestDAOImpl.retriveCertFileName";
	public static final String UPDATE_REVOKE_DATE = "com.vwoa.ecrms.dao.certrequest.EditStatusCertRequestDAOImpl.updateRevokeDate";
	public static final String RETRIEVE_CERT_ACTVN_DATE = "com.vwoa.ecrms.dao.certrequest.EditStatusCertRequestDAOImpl.retriveCertActvnDate";
	
	//Mybatis constants for search certificate request
	public static final String RETRIEVE_SEARCH_CERT_RESULT = "com.vwoa.ecrms.dao.searchcertificaterequest.SearchCertRequestDAOImpl.retrieveSearchCertResult";
	public static final String RETRIEVE_COUNT_SEARCH_CERT_RESULT = "com.vwoa.ecrms.dao.searchcertificaterequest.SearchCertRequestDAOImpl.retrieveCountSearchCertResult";
	public static final String RETRIEVE_COUNT_BASIC_SEARCH_RESULT="com.vwoa.ecrms.dao.searchcertificaterequest.SearchCertRequestDAOImpl.retrieveCountBasicSearchResult";
	public static final String RETRIEVE_BASIC_SEARCH_CERT_RESULT = "com.vwoa.ecrms.dao.searchcertificaterequest.SearchCertRequestDAOImpl.retrieveBasicSearchCertResult";
	public static final String RETRIEVE_COUNT_BASIC_SEARCH_RESULT_ADMIN="com.vwoa.ecrms.dao.searchcertificaterequest.SearchCertRequestDAOImpl.retrieveCountBasicSearchResultAdmin";
	public static final String RETRIEVE_BASIC_SEARCH_CERT_RESULT_ADMIN="com.vwoa.ecrms.dao.searchcertificaterequest.SearchCertRequestDAOImpl.retrieveBasicSearchCertResultAdmin";
	public static final String UPDATE_CERT_ACTIVATION_DTE="com.vwoa.ecrms.dao.searchcertificaterequest.SearchCertRequestDAOImpl.updateCertActivationDte";
	
	//Status codes
	public static final String PENDING_REVIEW = "PENRV";
	public static final String APPROVED = "APRVD";
	public static final String STALLED = "STALD";
	public static final String REVOKED = "REVKD";
	public static final String ACTIVE = "ACTIV";
	public static final String CLOSE = "CLOSE";
	public static final String EXPIRED = "EXPRD";
	
	//BASIC SEARCH GRID Sorting 
	public static final String DEVICE_NICK_NAME = "deviceNickName";
	public static final String HARDWARE_ID = "hardwareId";
	public static final String STATUS = "status";
	public static final String DEVICE_TYPE = "deviceType";
	public static final String REQUEST_NUMBER = "requestNum";
	public static final String DEALER_NUMBER = "dealerNum";
	public static final String ISSUE_DATE= "issueDate";
	public static final String EXPIRY_DATE = "expiryDate";
	public static final String REVOKED_DATE = "revokedDate";
	
	
	public static final String DEVICE_NICK_NAME_DBCOL= "CRT_CERT_RQST.VAS_EQMNT_NAME";
	public static final String HARDWARE_ID_DBCOL= "CRT_CERT_RQST.VAS_EQMNT_ID";
	public static final String STATUS_DBCOL= "RST_RQST_STATUS.RQST_STAT_DESC";
	public static final String DEVICE_TYPE_DBCOL= "CRT_CERT_RQST.DVCE_CDE";
	public static final String REQUEST_NUMBER_DBCOL= "CRT_CERT_RQST.RQST_NUM";
	public static final String DEALER_NUMBER_DBCOL =  "CRT_CERT_RQST.DLR_CDE";
	public static final String ISSUE_DATE_DBCOL= "CDT_CERT.CERT_ISSUE_DTE";
	public static final String EXPIRY_DATE_DBCOL= "CDT_CERT.CERT_EXPR_DTE";
	public static final String REVOKED_DATE_DBCOL= "CDT_CERT.CERT_REVK_DTE";
	
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	//Advanced Search Export
	
	public static final String SEARCH_EXPORT_LIST="SEARCH_EXPORT_LIST";
	public static final String SEARCH_COUNT = "SEARCH_COUNT";
	public static final String SEARCH_MODEL = "SEARCH_MODEL";
	public static final String XLS_FILENAME = "advancedsearch.filename";
	public static final String XLS_FILEPATH = "advancedsearch.filepath";
	
	//DEVICE TYPE LIST
	
	public static final String DEVICE_TYPE_CODE = "deviceTypeId";
	public static final String	DEVICE_TYPE_CODE_DBCOL= "DTP_DVCE_TYP.DVCE_CDE";
	public static final String DEVICE_TYPE_DESC = "deviceTypeDesc";
	public static final String	DEVICE_TYPE_DESC_DBCOL= "DTP_DVCE_TYP.DVCE_DESC";
	
	//Mybatis constant for Administration
	
	public static final String RETRIEVE_DEVICETYPE_CODE = "com.vwoa.ecrms.dao.administration.AdminDAOImpl.retrieveDeviceType";
	public static final String RETRIEVE_EXPIRY_PERIOD = "com.vwoa.ecrms.dao.administration.AdminDAOImpl.retrieveExpiryPeriod";
	public static final String RETRIEVE_EXPIRY_NOTIFICATION = "com.vwoa.ecrms.dao.administration.AdminDAOImpl.retrieveExpNotifDetails";
	public static final String RETRIEVE_RECEIVED_NOTIFICATION = "com.vwoa.ecrms.dao.administration.AdminDAOImpl.retrieveRecNotifDetails";
	public static final String ADD_DEVICE_TYPE = "com.vwoa.ecrms.dao.administration.AdminDAOImpl.saveDeviceType";
	public static final String UPDATE_UNCHECKED_DEVICETYPESTATUS = "com.vwoa.ecrms.dao.administration.AdminDAOImpl.updateActiveIndicatorFalse";
	public static final String UPDATE_CHECKED_DEVICETYPESTATUS = "com.vwoa.ecrms.dao.administration.AdminDAOImpl.updateActiveIndicatorTrue";
	public static final String UPDATE_EXPIRY_PERIOD = "com.vwoa.ecrms.dao.administration.AdminDAOImpl.saveExpPeriod";
	public static final String UPDATE_EXPIRY_NOTIFICATION="com.vwoa.ecrms.dao.administration.AdminDAOImpl.updateExpNotifDetails";
	public static final String UPDATE_RECEIVED_NOTIFICATION="com.vwoa.ecrms.dao.administration.AdminDAOImpl.updateRecNotifDetails";
	
	public static final String RETRIEVE_RECOVER_SEARCH_CERT = "com.vwoa.ecrms.dao.administration.RecoverCertDAOImpl.retrieveRecoverSearchCert";
	
	//Constants for Approval mail
	public static final String AU_UDE="Access Audi - ";
	public static final String VW_UDE="VW Hub - ";
	public static final String VINNIE="VINNIE - ";
	
	public static final String AU_UDE_LINK_KEY="link.ude.au";
	public static final String VW_UDE_LINK_KEY="link.ude.vw";
	public static final String VINNIE_LINK_KEY="link.vinnie";
	
	public static final String DOWNLOAD_INSTRUCT_KEY="download.instruction";
	public static final String DOWNLOAD_SIGNATURE_KEY="download.signature";
	//session expiry key
	public static final String SESSION_EXPIRY_KEY="message.sessionexpired";
	
	//Certificate Activation period
	public static final String CERT_ACTIVATION_PERIOD = "constantval.certActivPeriod";
	
	//Certificate Activation period
	public static final String CERT_RENEW_PERIOD = "constantval.certRenewPeriod";
	
	//error message key for email
	public static final String ERR_MAIL = "err.unable.email";
	
	//Session values
	public static final String ESHOP_LINK="eshopLink"; 
	
	//LDAP property value
	public static final String LDAP_URL="ldap.url";
	public static final String LDAP_JOBTITLE="ldap.vwjobtitle";
	
	public static final String STARTTIMESTAMP=" 00:00:00";
	public static final String ENDTIMESTAMP=" 23:59:59";
	
	public static final String DLR_PRNPL="DLR-PRIN";
	public static final String DLR_PRGM="DLR-PRGM";						
	public static final String GEN_MGR="GEN-MGR";
	public static final String SVC_DIR="SVC-DIR";
	public static final String SV_SM="SV-SM";
	public static final String PTSV_MGR="PTSV-MGR";
	public static final String FORWARDED_HOST = "vwgoa-forwarded-host";
	public static final String TO_CHECK = "toCheck";

}



