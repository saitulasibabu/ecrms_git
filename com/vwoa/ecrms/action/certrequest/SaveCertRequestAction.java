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
import com.vwoa.ecrms.model.certrequest.CertStatusModel;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.service.certrequest.ICertRequestService;
import com.vwoa.ecrms.service.certrequest.IEditStatusCertRequestService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to handle all the actions related to save certificate request details.
 * 
 * @author Aparna_Deshmukh01
 * 
 */
@ParentPackage("ecrms-default")
public class SaveCertRequestAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = -5360335506606541012L;

	private static final Logger logger = LoggerFactory
			.getLogger(SaveCertRequestAction.class);

	private Map<String, Object> session;

	private CertRequestModel certRequestModel;// Model to create new certificate

	@Autowired
	private ICertRequestService certRequestService;// service to create new

	private String certRequestStatus;

	@Autowired
	private IEditStatusCertRequestService editStatusCertRequestService;

	List<CertRequestModel> certRequestModelList;

	private String isError;

	private String errorMsg;

	/**
	 * This method verifies whether active certificate exists for hardware id or
	 * existing dealer. If not, then forward the request to appropriate action
	 * to save the details.
	 * 
	 * @return String the message as success or failure
	 * @throws ECRMSException
	 *             , Exception
	 */

	@Action(value = "/certrequest/verifyCertRequest", results = {
			@Result(name = SUCCESS, type = "json"),
			@Result(name = ECRMSConstants.NEW_CERT_REQUEST, type = "dispatcher", location = "detailCertRequest.jsp") })
	public String verifyCertRequest() throws ECRMSException, Exception {
		try {
			logger.info("Start of verifyCertRequest method ");

			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);
			
			ActionContext.getContext().setLocale(user.getUserLocale());
			// verify duplicate certificate

			if (certRequestModel != null) {
				certRequestModel.setUserId(user.getUserId());

				session.put("certRequestModel", certRequestModel);

				//IM50692620- Existing Defect where the IRF certifcate were not being saved if an certifcate is pending approval or approved
				if(certRequestModel.getUserType() != null && certRequestModel.getUserType().equalsIgnoreCase("IRF")) {
					certRequestModel.setDeviceName("IRF_"+certRequestModel.getDealerNum());
					certRequestModel.setDeviceType("6150");
				}

				
				// verify active certificate for hardware id i.e for different
				// dealer

				CertRequestModel certReqResultForDiffDlr = certRequestService
						.verifyActiveCertForHWID(certRequestModel
								.getHardwareId());

				if (certReqResultForDiffDlr != null
						&& certReqResultForDiffDlr.getRequestNum() != null) {

					// verify active certificate for existing dealer
					CertRequestModel certReqResultForExstDlr = certRequestService
							.verifyActiveCertForExstDlr(certRequestModel);

					if (certReqResultForExstDlr != null
							&& certReqResultForExstDlr.getRequestNum() != null) {
						// Active certificate exists for existing dealer
						certRequestModel.setRequestNum(certReqResultForExstDlr
								.getRequestNum());
						certRequestModel.setStatusCode(certReqResultForExstDlr
								.getStatusCode());
						certRequestModel.setCertActvnInd(certReqResultForExstDlr.getCertActvnInd());
						certRequestStatus = ECRMSConstants.ACTIVE_CERT_FOR_EXST_DLR;
						
						logger.info("requestNumForExstDlr is "
								+ certReqResultForExstDlr.getRequestNum());

					} else {
						// Active certificate exists for different dealer i.e
						// for hardware id
						certRequestModel.setRequestNum(certReqResultForDiffDlr
								.getRequestNum());
						certRequestModel.setStatusCode(certReqResultForDiffDlr
								.getStatusCode());
						logger.info("requestNumForHwId is "
								+ certReqResultForDiffDlr.getRequestNum());
						certRequestStatus = ECRMSConstants.ACTIVE_CERT_FOR_DIFF_DLR;

					}
				} else {
					// setting locale for confirmation page
					ActionContext.getContext().setLocale(user.getUserLocale());
					// Its a new form, hence redirect to save action
					certRequestStatus = ECRMSConstants.NEW_CERT_REQUEST;

					certRequestModel
							.setStatusCode(ECRMSConstants.PENDING_REVIEW);
					//IM50692620- Existing Defect where the IRF certifcate were not being saved if an certifcate is pending approval or approved
					//This below comeneted code is moved to top.
/*					if(certRequestModel.getUserType() != null && certRequestModel.getUserType().equalsIgnoreCase("IRF")) {
						certRequestModel.setDeviceName("IRF_"+certRequestModel.getDealerNum());
						certRequestModel.setDeviceType("6150");
					}
*/					
					Integer requestNum = certRequestService
							.saveCertRequest(certRequestModel);
					certRequestModel.setRequestNum(requestNum);

					return ECRMSConstants.NEW_CERT_REQUEST;
				}

			}

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
			errorMsg = getText(ecrmsException.getDescription());
			isError = ECRMSConstants.TRUE;
			return SUCCESS;
			

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);

			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_VERIFY_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_VERIFY_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			errorMsg = getText(ecrmsException.getDescription());
			isError = ECRMSConstants.TRUE;
			return SUCCESS;

		}
		logger.info("End of verifyCertRequest method ");
		return SUCCESS;
	}

	/**
	 * This method saves the details to create new certificate request.
	 * 
	 * @return String the message as success or failure
	 */

	@Action(value = "/certrequest/saveCertificate", results = {
			@Result(name = SUCCESS, location = "detailCertRequest.jsp", type = "dispatcher"),
			@Result(name = "saveError", location = "detailCertRequestError.jsp", type = "dispatcher") })
	public String saveCertRequest() throws ECRMSException, Exception {
		try {
			logger.info("Start of saveCertificate method");

			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);
			// set the locale
			ActionContext.getContext().setLocale(user.getUserLocale());

			certRequestModel = (CertRequestModel) session
					.get("certRequestModel");

			if (certRequestModel != null) {

				if (StringUtils.isNotBlank(certRequestModel.getStatusCode())) {
					// close the request having status as pending review or
					// stalled
					if (StringUtils.equals(certRequestModel.getStatusCode(),
							ECRMSConstants.PENDING_REVIEW)
							|| StringUtils.equals(certRequestModel
									.getStatusCode(), ECRMSConstants.STALLED)) {
						certRequestModel.setStatusCode(ECRMSConstants.CLOSE);
						editStatusCertRequestService
								.updateCertReqStatus(certRequestModel);
					} // revoke the request having status as active
					else if (StringUtils.equals(certRequestModel
							.getStatusCode(), ECRMSConstants.ACTIVE)) {
						logger.info("its a revoke request");

						certRequestModelList = new ArrayList<CertRequestModel>();
						certRequestModelList.add(certRequestModel);

						List<CertStatusModel> certStatusList = editStatusCertRequestService
								.revokeCertRequest(certRequestModelList);

						certRequestModel.setStatusCode(ECRMSConstants.REVOKED);
						editStatusCertRequestService
								.updateCertReqStatus(certRequestModel);
					}
					// save certificate request details
					certRequestModel
							.setStatusCode(ECRMSConstants.PENDING_REVIEW);

					Integer requestNum = certRequestService
							.saveCertRequest(certRequestModel);
					certRequestModel.setRequestNum(requestNum);

				}
				// removing from session
				session.remove("certRequestModel");

			} else {
				logger.info("certRequestModel is  null");
			}

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsException.getDescription()));

			errorMsg = getText(ecrmsEx.getDescription());
			isError = ECRMSConstants.TRUE;
			return "saveError";

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);

			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_INSERT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			errorMsg = getText(ecrmsException.getDescription());
			isError = ECRMSConstants.TRUE;
			return "saveError";

		}
		logger.info("End of saveCertificate method");
		return SUCCESS;
	}

	/**
	 * Method to get details for confirmation dialog for active certificate for
	 * existing dealer
	 * 
	 * @return
	 */
	@Action(value = "/certrequest/confirmCertRequestExtDlr")
	public String confirmCertReqExtDlr() {
		logger.info("Start of confirmExstDlr ");

		UserProfileModel user = (UserProfileModel) session
				.get(ECRMSConstants.USERPROFILE_KEY);
		// set the locale
		ActionContext.getContext().setLocale(user.getUserLocale());

		logger.info("active certificate request number for existing dealer is "
				+ certRequestModel.requestNum);

		logger.info("End of confirmExstDlr ");
		return SUCCESS;
	}

	/**
	 * Method to get details for confirmation dialog for active certificate for
	 * existing dealer
	 * 
	 * @return success or error message
	 */
	@Action(value = "/certrequest/confirmCertRequestHWId")
	public String confirmCertReqDiffDlr() {
		logger.info("Start of confirmCertReqDiffDlr ");

		UserProfileModel user = (UserProfileModel) session
				.get(ECRMSConstants.USERPROFILE_KEY);
		// set the locale
		ActionContext.getContext().setLocale(user.getUserLocale());

		logger.info("End of confirmCertReqDiffDlr ");
		return SUCCESS;
	}

	
	/**
	 * Method to get details for confirmation dialog for certificates in Pending
	 * review and stall status
	 * 
	 * @return success or error message
	 */
	@Action(value = "/certrequest/confirmCertReqPenRevNStl")
	public String confirmPenRevNStallReq() {
		logger.info("Start of confirmPenRevNStallReq ");

		UserProfileModel user = (UserProfileModel) session
				.get(ECRMSConstants.USERPROFILE_KEY);
		// set the locale
		ActionContext.getContext().setLocale(user.getUserLocale());

		logger.info("End of confirmPenRevNStallReq ");
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

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
	 * @return the certRequestStatus
	 */
	public String getCertRequestStatus() {
		return certRequestStatus;
	}

	/**
	 * @param certRequestStatus
	 *            the certRequestStatus to set
	 */
	public void setCertRequestStatus(String certRequestStatus) {
		this.certRequestStatus = certRequestStatus;
	}

	/**
	 * @param certRequestService
	 *            the certRequestService to set
	 */
	public void setCertRequestService(ICertRequestService certRequestService) {
		this.certRequestService = certRequestService;
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
