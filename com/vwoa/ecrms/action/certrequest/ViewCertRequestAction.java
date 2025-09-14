/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */
package com.vwoa.ecrms.action.certrequest;

import java.util.List;
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
import com.vwoa.ecrms.model.certrequest.CommentHistoryModel;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.security.LoginSecurity;
import com.vwoa.ecrms.service.certrequest.ICertRequestService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to handle action related to view certificate request.
 * 
 * @author Aparna_Deshmukh01
 * 
 */
@ParentPackage("ecrms-default")
public class ViewCertRequestAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = -1430740449220802911L;

	private static final Logger logger = LoggerFactory
			.getLogger(ViewCertRequestAction.class);

	private Map<String, Object> session; // session variable

	private CertRequestModel certRequestModel;// Model to create new certificate

	@Autowired
	private ICertRequestService certRequestService;// service get certificate

	List<CommentHistoryModel> reqCommentList;

	// details

	/**
	 * Method to get certificate request details to view.
	 * 
	 * @return success or error message
	 * @throws ECRMSException
	 * @throws Exception
	 */
	@Action(value = "/certrequest/viewCertRequest", results = { @Result(name = SUCCESS, location = "editCertRequest.jsp", type = "dispatcher") })
	public String viewCertRequest() throws ECRMSException, Exception {
		logger.info("Start of viewCertRequest method");
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
				// fetch request details for the selected request number
				certRequestModel = certRequestService
						.retrieveCertRequestDetails(reqNum);

				// get dealer phone number from LDAP
				certRequestModel
						.setDealerPhnNum(LoginSecurity
								.searchDlrPhnNo(certRequestModel.getDealerNum()
										.trim()));
				
				// Changes related to Dealer Approval process for the certificate request. Now dealers with some specific
				// job titles can approve the certificate requests.
				logger.info("Certificate Requested By User:: " + certRequestModel.getCertRequestedByUsr());
				List<String> userRoleList = user.getUserRoleList();
				if(userRoleList.contains(ECRMSConstants.DEALER_APPROVER)){
					userRoleList.remove(ECRMSConstants.DEALER_APPROVER);					
				}
				if(user!=null && certRequestModel.getCertRequestedByUsr()!=null && (!certRequestModel.getCertRequestedByUsr().equalsIgnoreCase(user.getUserId()))){					
					String jobTitle=user.getVwJobTitle();
					logger.info("CertRequestedUser:: " + certRequestModel.getCertRequestedByUsr() +
							    " Login User:: " + user.getUserId()+
							    " Login User Job Title:: " + jobTitle);
					if(jobTitle!=null && (jobTitle.equalsIgnoreCase(ECRMSConstants.DLR_PRNPL)|| 
							              jobTitle.equalsIgnoreCase(ECRMSConstants.DLR_PRGM) || 
							              jobTitle.equalsIgnoreCase(ECRMSConstants.GEN_MGR) ||
							              jobTitle.equalsIgnoreCase(ECRMSConstants.SVC_DIR) ||
							              jobTitle.equalsIgnoreCase(ECRMSConstants.SV_SM) ||
							              jobTitle.equalsIgnoreCase(ECRMSConstants.PTSV_MGR) )){
						userRoleList.add(ECRMSConstants.DEALER_APPROVER);						
						logger.info("Dealer Approver Role is added ");
					}													
				}								
				// set logged in user in the model
				certRequestModel.setUserId(user.getUserId());
				session.put("certEditRequestModel", certRequestModel);
			}

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_UPDATE_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		logger.info("End of viewCertRequest method");
		return SUCCESS;
	}

	/**
	 * Method to get certificate request comment list
	 * 
	 * @return success or error message
	 * @throws ECRMSException
	 * @throws Exception
	 */
	@Action(value = "/certrequest/loadCertReqCommHistory", results = { @Result(name = "success", type = "json") })
	public String loadCertReqCommHistory() throws ECRMSException, Exception {

		logger.info("Start of loadCertReqCommHistory");
		try {
			UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			// Populating the comment list
			if (session.get("reqNum") != null) {
				logger.info("reqNum is " + session.get("reqNum"));
				reqCommentList = certRequestService
						.retriveReqCommentDetails((Integer) session
								.get("reqNum"));
			}
			// Handling the ECRMS Exception
		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(ecrmsEx.getDescription());
			throw ecrmsException;
			// Handling the Exception
		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(ecrmsException.getDescription());
			throw ecrmsException;
		}
		logger.info("End of loadCertReqCommHistory");
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
	 * @return the reqCommentList
	 */
	public List<CommentHistoryModel> getReqCommentList() {
		return reqCommentList;
	}

	/**
	 * @param reqCommentList
	 *            the reqCommentList to set
	 */
	public void setReqCommentList(List<CommentHistoryModel> reqCommentList) {
		this.reqCommentList = reqCommentList;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

	}
}
