/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.action.certrequest;

import java.util.ArrayList;
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
import com.vwoa.ecrms.service.certrequest.IEditStatusCertRequestService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to handle all the actions related to edit status for a particular
 * certificate request
 * 
 * @author Aparna_Deshmukh01
 * 
 */

@ParentPackage("ecrms-default")
public class EditStatusCertRequestAction extends BaseAction implements
		SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1120784873346529157L;

	private static final Logger logger = LoggerFactory
			.getLogger(EditStatusCertRequestAction.class);

	private Map<String, Object> session;

	@Autowired
	private IEditStatusCertRequestService editStatusCertRequestService;

	private CertRequestModel certRequestModel;

	private List<CertRequestModel> certRequestModelList = null;

	private String emailError;

	private String isError;

	private String errorMsg;

	/**
	 * Method to approve certificate request
	 * 
	 * @return String the message as success or failure
	 */

	@Action(value = "/certrequest/approveCertRequest", results = { @Result(name = SUCCESS, type = "json") })
	public String approveCertRequest() throws ECRMSException, Exception {
		logger.info("Start of approveCertRequest");
		System.out.println("start of approveCertRequest..");
		Integer count = null;
		try {
			UserProfileModel user = (UserProfileModel) session
			.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());
			if (certRequestModel != null) {
				// get the certificate request model from session
				if (session.get("certEditRequestModel") != null) {

					CertRequestModel sessionCertRequestModel = (CertRequestModel) session
							.get("certEditRequestModel");

					sessionCertRequestModel.setCommentText(certRequestModel
							.getCommentText());
					logger.info(sessionCertRequestModel.getCommentText()
							+ " -> is comment");
					// get the count, 1 if success and 0 if fail
					count = editStatusCertRequestService
							.approveCertRequest(sessionCertRequestModel);
					if (count != null && count > 0) {
						
						logger.info("Request Approved successfully");
					}
				}
			}
		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {

			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsException.getDescription()));
			if (StringUtils.equals(ecrmsEx.getDescription(),
					ECRMSConstants.ERR_MAIL)) {
				emailError = ECRMSConstants.TRUE;

			}
			errorMsg = getText(ecrmsEx.getDescription());
			isError = ECRMSConstants.TRUE;
			return SUCCESS;
		}
		// Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_APPROVE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_APPROVE_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			errorMsg = getText(ecrmsException.getDescription());
			isError = ECRMSConstants.TRUE;
			return SUCCESS;
		}
		logger.info("End of approveCertRequest");
		return SUCCESS;
	}

	/**
	 * Method to stall certificate request
	 */
	@Action(value = "/certrequest/stallCertRequest")
	public String stallCertRequest() throws ECRMSException, Exception {
		logger.info("Start of stallCertRequest method");
		try {
			UserProfileModel user = (UserProfileModel) session
			.get(ECRMSConstants.USERPROFILE_KEY);
	ActionContext.getContext().setLocale(user.getUserLocale());
			if (certRequestModel != null
					&& StringUtils
							.isNotBlank(certRequestModel.getCommentText())) {
				logger.info("user added comments are "
						+ certRequestModel.getCommentText());

				if (session.get("certEditRequestModel") != null) {
					CertRequestModel certReqModelFromSession = (CertRequestModel) session
							.get("certEditRequestModel");
					certReqModelFromSession.setCommentText(certRequestModel
							.getCommentText());
					certReqModelFromSession
							.setStatusCode(ECRMSConstants.STALLED);
					editStatusCertRequestService
							.stallCertRequest(certReqModelFromSession);
				}

			}

		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		}
		// Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_STALL_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_STALL_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		logger.info("End of stallCertRequest method ");
		return NONE;
	}

	/**
	 * Method to add comments for certificate request
	 */
	@Action(value = "/certrequest/addReqComment")
	public String addReqComment() throws ECRMSException, Exception {
		logger.info("Start of addReqComment method");
		try {
			UserProfileModel user = (UserProfileModel) session
			.get(ECRMSConstants.USERPROFILE_KEY);
		ActionContext.getContext().setLocale(user.getUserLocale());
			
			Integer count = 0;

			if (certRequestModel != null
					&& StringUtils
							.isNotBlank(certRequestModel.getCommentText())) {
				logger.info("user added comments are "
						+ certRequestModel.getCommentText());

				if (session.get("certEditRequestModel") != null) {
					CertRequestModel certReqtModelFromSession = (CertRequestModel) session
							.get("certEditRequestModel");
					certReqtModelFromSession.setCommentText(certRequestModel
							.getCommentText());
					count = editStatusCertRequestService
							.addReqComment(certReqtModelFromSession);
					if (count > 0) {
						logger.info("comments added successfully");
					}
				}

			}
		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(ecrmsEx.getDescription());
			throw ecrmsException;

		}
		// Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_INSERT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		logger.info("End of addReqComment method ");
		return NONE;
	}

	/**
	 * Method to revoke certificate request
	 */
	@Action(value = "/certrequest/revokeCertificate", results = { @Result(name = SUCCESS, type = "json") })
	public String revokeCertRequest() throws ECRMSException, Exception {
		logger.info("Start of revokeCertRequest method ");

		try {

			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			certRequestModelList = new ArrayList<CertRequestModel>();
			certRequestModel = (CertRequestModel) session
					.get("certEditRequestModel");
			certRequestModel.setUserId(user.getUserId());

			certRequestModelList.add(certRequestModel);

			editStatusCertRequestService
					.revokeCertRequest(certRequestModelList);

			logger.info("THE REVOKE WAS A SUCCESS");

		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(ecrmsEx.getDescription());
			errorMsg = getText(ecrmsEx.getDescription());
			isError = ECRMSConstants.TRUE;
			return SUCCESS;

		}
		// Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_REVOKE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_REVOKE_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			errorMsg = getText(ecrmsException.getDescription());
			isError = ECRMSConstants.TRUE;
			return SUCCESS;
		}
		logger.info("End of revokeCertRequest method ");

		return SUCCESS;
	}

	/**
	 * @param editStatusCertRequestService
	 *            the editStatusCertRequestService to set
	 */
	public void setEditStatusCertRequestService(
			IEditStatusCertRequestService editStatusCertRequestService) {
		this.editStatusCertRequestService = editStatusCertRequestService;
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

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

	}

	/**
	 * @return the emailError
	 */
	public String getEmailError() {
		return emailError;
	}

	/**
	 * @param emailError
	 *            the emailError to set
	 */
	public void setEmailError(String emailError) {
		this.emailError = emailError;
	}

	/**
	 * @return the isError
	 */
	public String getIsError() {
		return isError;
	}

	/**
	 * @param isError
	 *            the isError to set
	 */
	public void setIsError(String isError) {
		this.isError = isError;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
