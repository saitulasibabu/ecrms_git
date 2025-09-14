/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.action.certrequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.vwoa.ecrms.action.BaseAction;
import com.vwoa.ecrms.model.certrequest.CertRequestModel;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.model.dropdown.DropdownModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.security.LoginSecurity;
import com.vwoa.ecrms.service.certrequest.ICertRequestService;
import com.vwoa.ecrms.service.common.ICommonService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to handle all the actions related to create new certificate request.
 * 
 * @author Aparna_Deshmukh01
 * 
 */
@ParentPackage("ecrms-default")
public class CreateCertRequestAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = -852637752157077735L;

	private static final Logger logger = LoggerFactory
			.getLogger(CreateCertRequestAction.class);

	private Map<String, Object> session;

	private CertRequestModel certRequestModel;// Model to create new certificate

	private List<DropdownModel> deviceTypeList;// list to hold device type data

	@Autowired
	private ICertRequestService certRequestService;// service to create new

	@Autowired
	private ICommonService commonService;// service to get drop-down details

	private String validDealerNum = ECRMSConstants.FALSE;

	private String multipleReqNum;// list to hold request number
	
	private SearchCertRequestModel searchCertRequestModel;	
	
	private List<DropdownModel> basicStatusList;
	
	private boolean IRFUser = false;

	/**
	 * This method retrieves details required to create new certificate request
	 * 
	 * @return String the message as success or failure
	 */

	@Action(value = "/certrequest/createCertRequest")
	public String loadCertRequestDetails() throws ECRMSException, Exception {
		logger.info("Start of createCertRequest method ");
		try {
			
			if(isIRFUser()) {
				System.out.println("This certificate request is for an IRF User.");
			}
			UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());
			// Start- for multiple create functionality
			if(session.get("CertModelForRenew") != null){
				logger.info("removing from Session");
				session.remove("CertModelForRenew");
			}
			if (session.get("reqNumList") != null) {
				session.remove("reqNumList");
			}
			// End- for multiple create functionality
			
			if (certRequestModel == null) {
				logger.info("certRequestModel is null hence initializing");
				certRequestModel = new CertRequestModel();
			}

			/****** IRF User******/
			if(isIRFUser()) {
				certRequestModel.setUserType("IRF");
			}
			/****** End IRF User******/
			
			
			// get dealer number from LDAP
			if (StringUtils.isNotBlank(user.getDealerNum())) {

				// retrieve dealer details
				certRequestModel = certRequestService.retriveDealerDetails(user
						.getDealerNum());

				if(isIRFUser()) {
					certRequestModel.setDealerName("IRFUser");
					certRequestModel.setDealerCntry(ECRMSConstants.COUNTRY_USA);
					certRequestModel.setDealerState("ST");
				}
				
				// setting dealer LDAP details
				if (StringUtils.isNotBlank(user.getDealerPhnNum())) {
					certRequestModel.setDealerPhnNum(user.getDealerPhnNum());
				}
				
			}
			//setting logged-in user's LDAP details
			if (StringUtils.isNotBlank(user.getContactPhnNum())) {
				certRequestModel.setContactPhnNum(user.getContactPhnNum());
			}
			if (StringUtils.isNotBlank(user.getContactEmail())) {
				certRequestModel.setContactEmail(user.getContactEmail());
			}
			//setting brand
			certRequestModel.setDealerBrand(user.getDealerBrand());
			// retrieve the device type list
			deviceTypeList = commonService
					.retrieveDeviceTypeData(ECRMSConstants.CREATE_REQUEST);

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(ecrmsEx.getDescription());
			throw ecrmsException;

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		}
		logger.info("End of createCertRequest method ");
		return SUCCESS;
	}

	/**
	 * Method to retrieve dealer details in case Corporate/System Admin user
	 * login.
	 * 
	 * @return success or error message.
	 */
	@Action(value = "/certrequest/retrieveDealerDetails", results = { @Result(name = SUCCESS, type = "json") })
	public String retrieveDealerDetails() throws ECRMSException, Exception {
		
		logger.info("Start of retrieveDealerDetails method");
		
		UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);
		ActionContext.getContext().setLocale(user.getUserLocale());
		
		try {

			if (certRequestModel != null
					&& StringUtils.isNotBlank(certRequestModel.getDealerNum())) {
				logger.info("Dealer number entered by corporate user is: "
						+ certRequestModel.getDealerNum()); // retrieve dealer
				// details
				certRequestModel = certRequestService
						.retriveDealerDetails(certRequestModel.getDealerNum());

				if (certRequestModel != null) {
					validDealerNum = ECRMSConstants.TRUE;

					// setting fields from LDAP
					certRequestModel.setDealerPhnNum(LoginSecurity.searchDlrPhnNo(certRequestModel.getDealerNum().trim()));
					
				}

			}

		} 
		//Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		} 
		//Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		logger.info("End of retrieveDealerDetails method" + certRequestModel);
		return SUCCESS;
	}

	/**
	 * Method to load multiple create certificate form.
	 * 
	 * @return @return success or error message.
	 */
	@Action(value = "/certrequest/multipleCertReqCreate", results = { @Result(name = SUCCESS, location = "createCertRequest.jsp", type = "dispatcher") })
	public String multipleCertReqCreate() throws ECRMSException, Exception {
		logger.info("Start of multipleCertReqCreate method ");
		try {

			UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			List<String> reqNumList = null;
			//for basic search
			
			basicStatusList = commonService.retrieveStatusData(user);
			searchCertRequestModel = new SearchCertRequestModel();
			
			
			// removing request id from session for multiple request
			// setting valid dealer flag as true
			validDealerNum = ECRMSConstants.TRUE;
			if (session.get("reqNumList") != null) {
				List<String> sessionReqList = (ArrayList<String>) session.get("reqNumList");
				if (sessionReqList.size() > 0) {
					logger.info("removing from session "+ sessionReqList.get(0));
					sessionReqList.remove(0);
				} else {
					session.remove(ECRMSConstants.FROM);
				}
				session.put("reqNumList", sessionReqList);
			}
			
			//for reset functionality
			if(session.get("CertModelForRenew") != null){
				logger.info("removing from Session");
				session.remove("CertModelForRenew");
			}

			// Adding request number in list for first time for multiple create
			// request
			if (StringUtils.isNotBlank(multipleReqNum)) {
				// keeping in session to identify source for create request
				session.put(ECRMSConstants.FROM, ECRMSConstants.HOME);
				logger.info("session value "+ session.get(ECRMSConstants.FROM));
				logger.info("mutliple request numbers are  " + multipleReqNum);
				reqNumList = new ArrayList<String>(Arrays.asList(multipleReqNum.split(",")));

				// modify reqNumList to remove trailing spaces

				for (String reqNumber : reqNumList) {
					reqNumList.set(reqNumList.indexOf(reqNumber), reqNumber.trim());
				}

				// keeping in session
				session.put("reqNumList", reqNumList);
			}

			// retrieving from session for each request
			if (session.get("reqNumList") != null) {
				List<String> requestList = (ArrayList<String>) session.get("reqNumList");

				if (requestList.size() > 0) {

					Integer reqNum = Integer.parseInt(requestList.get(0));

					logger.info("View certificate for the request no: "
							+ reqNum);
					certRequestModel = certRequestService.retrieveCertRequestDetails(reqNum);
					certRequestModel.setUserId(user.getUserId());

					// retrieve the device type list
					deviceTypeList = commonService
							.retrieveDeviceTypeData(ECRMSConstants.CREATE_REQUEST);
					
					// setting fields from LDAP
					certRequestModel.setDealerPhnNum(LoginSecurity.searchDlrPhnNo(certRequestModel.getDealerNum().trim()));
					
					//setting brand
					certRequestModel.setDealerBrand(user.getDealerBrand());
					//for landing page
					searchCertRequestModel.setDealerNum(certRequestModel.getDealerNum());
					
					session.put("CertModelForRenew", certRequestModel);
				}
				
			}

		} 
		//Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		} 
		//Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		}
		logger.info("End of multipleCertReqCreate method ");
		return SUCCESS;
	}
	/**
	 * method to reset form for renewal of create certificate
	 * @return
	 */
	@Action(value = "/certrequest/resetRenewCertRequest", results = { @Result(name = SUCCESS, location = "createCertRequest.jsp", type = "dispatcher") })
	public String resetRenewCertRequest() throws ECRMSException, Exception{
		logger.info("Start of resetRenewCertRequest method ");
		try{
			UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			if(session.get("CertModelForRenew") != null){
				validDealerNum = ECRMSConstants.TRUE;
				certRequestModel=(CertRequestModel) session.get("CertModelForRenew");
				deviceTypeList = commonService
				.retrieveDeviceTypeData(ECRMSConstants.CREATE_REQUEST);
			}
		}
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		} 
		//Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		}
		logger.info("End of resetRenewCertRequest method ");
		return SUCCESS;
	}

	/**
	 * @return the certRequestModel
	 */
	public CertRequestModel getCertRequestModel() {
		return certRequestModel;
	}

	/**
	 * @param certRequestModel
	 *            the certRequestModel to set
	 */
	public void setCertRequestModel(CertRequestModel certRequestModel) {
		this.certRequestModel = certRequestModel;
	}

	/**
	 * @param certRequestService
	 *            the certRequestService to set
	 */
	public void setCertRequestService(ICertRequestService certRequestService) {
		this.certRequestService = certRequestService;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @return the deviceTypeList
	 */
	public List<DropdownModel> getDeviceTypeList() {
		return deviceTypeList;
	}

	/**
	 * @param deviceTypeList
	 *            the deviceTypeList to set
	 */
	public void setDeviceTypeList(List<DropdownModel> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

	/**
	 * @return the validDealerNum
	 */
	public String getValidDealerNum() {
		return validDealerNum;
	}

	/**
	 * @param validDealerNum
	 *            the validDealerNum to set
	 */
	public void setValidDealerNum(String validDealerNum) {
		this.validDealerNum = validDealerNum;
	}

	/**
	 * @return the multipleReqNum
	 */
	public String getMultipleReqNum() {
		return multipleReqNum;
	}

	/**
	 * @param multipleReqNum
	 *            the multipleReqNum to set
	 */
	public void setMultipleReqNum(String multipleReqNum) {
		this.multipleReqNum = multipleReqNum;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

	}

	/**
	 * @return the searchCertRequestModel
	 */
	public SearchCertRequestModel getSearchCertRequestModel() {
		return searchCertRequestModel;
	}

	/**
	 * @param searchCertRequestModel the searchCertRequestModel to set
	 */
	public void setSearchCertRequestModel(
			SearchCertRequestModel searchCertRequestModel) {
		this.searchCertRequestModel = searchCertRequestModel;
	}

	/**
	 * @return the basicStatusList
	 */
	public List<DropdownModel> getBasicStatusList() {
		return basicStatusList;
	}

	/**
	 * @param basicStatusList the basicStatusList to set
	 */
	public void setBasicStatusList(List<DropdownModel> basicStatusList) {
		this.basicStatusList = basicStatusList;
	}

	public boolean isIRFUser() {
		return IRFUser;
	}

	public void setIRFUser(boolean user) {
		IRFUser = user;
	}


	

}
