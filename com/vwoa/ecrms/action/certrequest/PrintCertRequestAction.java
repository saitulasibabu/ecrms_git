/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */
package com.vwoa.ecrms.action.certrequest;

import java.util.Map;

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
import com.vwoa.ecrms.security.LoginSecurity;
import com.vwoa.ecrms.service.certrequest.ICertRequestService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to handle the action related to print certificate request.
 * 
 * @author Aparna_Deshmukh01
 * 
 */
@ParentPackage("ecrms-default")
public class PrintCertRequestAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = -8018962386349701802L;

	private static final Logger logger = LoggerFactory
			.getLogger(PrintCertRequestAction.class);
	private Map<String, Object> session;
	private CertRequestModel certRequestModel;// Model to print certificate
	// request
	@Autowired
	private ICertRequestService certRequestService;// service to get details to

	// print certificate

	/**
	 * Method to retrieve data for print certificate request
	 * 
	 * @return success or error message.
	 */
	@Action(value = "/certrequest/printCertRequest", results = { @Result(name = SUCCESS, location = "printCertDetails.jsp", type = "dispatcher") })
	public String printCertRequest() throws ECRMSException, Exception {

		logger.info("Start of printCertRequest method");
		try {
			// get user object from session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);
			// set the user locale
			ActionContext.getContext().setLocale(user.getUserLocale());

			if (certRequestModel != null) {

				Integer reqNum = certRequestModel.getRequestNum();

				logger.info("View certificate for the request no: " + reqNum);

				session.put("reqNum", reqNum);

				// get certificate request details to print
				certRequestModel = certRequestService
						.retrieveCertRequestDetails(reqNum);

				// get dealer phone number from LDAP

				certRequestModel.setDealerPhnNum(LoginSecurity.searchDlrPhnNo(certRequestModel.getDealerNum().trim()));
				
				certRequestModel.setUserTypeDlrOrCorp("DEALER");
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
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_UPDATE_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		logger.info("End of printCertRequest method");
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

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

	}
}
