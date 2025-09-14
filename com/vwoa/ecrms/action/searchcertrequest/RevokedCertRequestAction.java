/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.action.searchcertrequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
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
import com.vwoa.ecrms.model.dropdown.DropdownModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.service.certrequest.ICertRequestService;
import com.vwoa.ecrms.service.certrequest.IEditStatusCertRequestService;
import com.vwoa.ecrms.service.common.ICommonService;
import com.vwoa.ecrms.service.searchcertrequest.ISearchCertRequestService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */

@ParentPackage("ecrms-default")
@Namespace("/")
public class RevokedCertRequestAction extends BaseAction implements SessionAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1120784873346529157L;
	private static final Logger logger = LoggerFactory.getLogger(RevokedCertRequestAction.class);
	@Autowired
	private IEditStatusCertRequestService editStatusCertRequestService;
	private CertRequestModel certRequestModel;
	List<CertRequestModel> certRequestModelList = null;

	private Map<String, Object> session;

	private String successMessage;

	@Autowired
	private ICertRequestService certRequestService;// service
	// to
	// create
	// new

	// certificate
	private String multipleReqNum;// list to hold request
	// numbers

	@Autowired
	private ICommonService commonService;
	private List<DropdownModel> basicStatusList;
	private SearchCertRequestModel searchCertRequestModel;
	@Autowired
	private ISearchCertRequestService searchCertRequestService;

	/**
	 * This method is used to revoke certificate from
	 * landing page This uses request numbers ,retrieve cert
	 * request details and revoke certificates
	 * 
	 * @return
	 */
	/**
	 * @return
	 */
	@Action(value = "revokeCertificatefromLanding", results = { @Result(name = ECRMSConstants.REVOKE_FROM_CERT_MGMT, type = "json") })
	public String revokeCertRequestfromLanding()
	{
		logger.info("Start of revokeCertRequestfromLanding method ");

		try {

			UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);
			// Setting the Locale
			ActionContext.getContext().setLocale(user.getUserLocale());
			List<String> reqNums = null;
			List<CertStatusModel> certStatusList = null;
			certRequestModelList = new ArrayList<CertRequestModel>();

			// for home page
			basicStatusList = commonService.retrieveStatusData(user);
			searchCertRequestModel = new SearchCertRequestModel();
			// get dealer number from userModel (LDAP)

			if (StringUtils.isNotBlank(user.getDealerNum())) {
				searchCertRequestModel = searchCertRequestService.retrieveDealerDetail(user.getDealerNum());
			}

			// Tokenize the request id's
			if (StringUtils.isNotBlank(multipleReqNum)) {
				logger.info("mutliple request numbers are  " + multipleReqNum);
				reqNums = Arrays.asList(multipleReqNum.split(","));

				// Preapare List of request Ids'
				for (String reqNumber : reqNums) {
					reqNums.set(reqNums.indexOf(reqNumber), reqNumber.trim());
					logger.info("request numbers is  " + reqNumber);

				}

				// Fetch the required details for every
				// request
				// id
				Integer reqNo = 0;
				List<String> finalReqNums = new ArrayList<String>();
				for (String reqNum : reqNums) {
					if (!finalReqNums.contains(reqNum)) {

						reqNo = Integer.parseInt(reqNum);
						logger.info("request numbers in integer is  " + reqNo);
						certRequestModel = certRequestService.retrieveCertRequestDetails(reqNo);
						certRequestModel.setUserId(user.getUserId());
						logger.info("Hardware Id is  " + certRequestModel.getHardwareId());
						finalReqNums.add(reqNum);
						certRequestModelList.add(certRequestModel);
					}
				}
				// Revoke the certificates
				certStatusList = new ArrayList<CertStatusModel>();

				certStatusList = editStatusCertRequestService.revokeCertRequest(certRequestModelList);

				// Prepare the success/failure message
				int certStatusListcount = 0;
				int reqNumscount = 0;
				String[] counts = new String[2];
				if (null != certStatusList) {
					certStatusListcount = certStatusList.size();
				}
				if (null != finalReqNums) {
					reqNumscount = finalReqNums.size();
				}

				// Count 0 is for the success id's
				counts[0] = Integer.toString(certStatusListcount);

				// Count 1 is for the total id's
				counts[1] = Integer.toString(reqNumscount);

				// success message
				successMessage = getText("success.multipleRevoke", counts);
			}

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ECRMSErrorMessage.Constants.ERR_MULTIPLE_REVOKE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_MULTIPLE_REVOKE_DATA.description()));
			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(ECRMSErrorMessage.Constants.ERR_MULTIPLE_REVOKE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_MULTIPLE_REVOKE_DATA.description()));
			addActionError(getText(ecrmsException.getDescription()));

			throw ecrmsException;
		}
		logger.info("End of revokeCertRequestfromLanding method ");

		return ECRMSConstants.REVOKE_FROM_CERT_MGMT;
	}

	/**
	 * @param editStatusCertRequestService
	 *            the editStatusCertRequestService to set
	 */
	public void setEditStatusCertRequestService(IEditStatusCertRequestService editStatusCertRequestService)
	{
		this.editStatusCertRequestService = editStatusCertRequestService;
	}

	/**
	 * @return the certRequestModel
	 */
	public CertRequestModel getCertRequestModel()
	{
		return certRequestModel;
	}

	/**
	 * @param certRequestModel
	 *            the certRequestModel to set
	 */
	public void setCertRequestModel(CertRequestModel certRequestModel)
	{
		this.certRequestModel = certRequestModel;
	}

	/**
	 * @param certRequestService
	 *            the certRequestService to set
	 */
	public void setCertRequestService(ICertRequestService certRequestService)
	{
		this.certRequestService = certRequestService;
	}

	/**
	 * @return the multipleReqNum
	 */
	public String getMultipleReqNum()
	{
		return multipleReqNum;
	}

	/**
	 * @param multipleReqNum
	 *            the multipleReqNum to set
	 */
	public void setMultipleReqNum(String multipleReqNum)
	{
		this.multipleReqNum = multipleReqNum;
	}

	public String getSuccessMessage()
	{
		return successMessage;
	}

	public void setSuccessMessage(String successMessage)
	{
		this.successMessage = successMessage;
	}

	@Override
	public void setSession(Map<String, Object> arg0)
	{

		session = arg0;

	}

	/**
	 * @return the basicStatusList
	 */
	public List<DropdownModel> getBasicStatusList()
	{
		return basicStatusList;
	}

	/**
	 * @param basicStatusList
	 *            the basicStatusList to set
	 */
	public void setBasicStatusList(List<DropdownModel> basicStatusList)
	{
		this.basicStatusList = basicStatusList;
	}

	/**
	 * @return the searchCertRequestModel
	 */
	public SearchCertRequestModel getSearchCertRequestModel()
	{
		return searchCertRequestModel;
	}

	/**
	 * @param searchCertRequestModel
	 *            the searchCertRequestModel to set
	 */
	public void setSearchCertRequestModel(SearchCertRequestModel searchCertRequestModel)
	{
		this.searchCertRequestModel = searchCertRequestModel;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	public void setCommonService(ICommonService commonService)
	{
		this.commonService = commonService;
	}

	/**
	 * @param searchCertRequestService
	 *            the searchCertRequestService to set
	 */
	public void setSearchCertRequestService(ISearchCertRequestService searchCertRequestService)
	{
		this.searchCertRequestService = searchCertRequestService;
	}

}
