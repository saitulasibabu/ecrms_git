/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.service.certrequest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vwoa.ecrms.dao.certrequest.ICertRequestDAO;
import com.vwoa.ecrms.dao.certrequest.IEditStatusCertRequestDAO;
import com.vwoa.ecrms.helper.common.EmailHelper;
import com.vwoa.ecrms.helper.webservice.CertStatusHelper;
import com.vwoa.ecrms.model.certrequest.CertDetailModel;
import com.vwoa.ecrms.model.certrequest.CertRequestModel;
import com.vwoa.ecrms.model.certrequest.CertStatusModel;
import com.vwoa.ecrms.model.common.EmailNotificationModel;
import com.vwoa.ecrms.model.webservice.CertificateResponseModel;
import com.vwoa.ecrms.model.webservice.CertificateRevokeResponse;
import com.vwoa.ecrms.model.webservice.CertificateRevokeResponseModel;
import com.vwoa.ecrms.model.webservice.CreateCertificateResponse;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.PropertyReader;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
@Service
public class EditStatusCertRequestServiceImpl implements
		IEditStatusCertRequestService {

	private static final Logger logger = LoggerFactory
			.getLogger(EditStatusCertRequestServiceImpl.class);

	@Autowired
	private IEditStatusCertRequestDAO editStatusCertRequestDAO;

	@Autowired
	private ICertRequestDAO certRequestDAO;

	@Autowired
	private CertStatusHelper certStatusHelper;

	@Autowired
	private EmailHelper emailHelper;
    
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.certrequest.IEditStatusCertRequestService#approveCertRequest(com.vwoa.ecrms.model.certrequest.CertRequestModel)
	 */
	@Transactional
	@Override
	public Integer approveCertRequest(CertRequestModel certRequestModel) throws ECRMSException, Exception {
		
		logger.info("Start of approveCertRequest method");
		List<CertificateResponseModel> certResponseModelList = null;
		Integer count = null;
		
		//get the expiry period
		certRequestModel.setExpiryPeriod(certRequestDAO.retrieveExpiryPeriod());
		
		//IM50692620- Change the expiry of IRF certificate only from 3 years to 1 year
		String expiryDefaultIRF = PropertyReader.retrievePropertiesValue("default.IRF.expiryPeriod");
		if(certRequestModel.getDeviceName().equalsIgnoreCase("IRF_"+certRequestModel.getDealerNum().trim())){
			certRequestModel.setExpiryPeriod(expiryDefaultIRF);
		}else {
			certRequestModel.setExpiryPeriod(certRequestDAO.retrieveExpiryPeriod());
		}
		
		// send the request to web-service
		 CreateCertificateResponse certResponseList = certStatusHelper.approveCertRequest(certRequestModel);
		
		CertDetailModel certDetailModel = new CertDetailModel();
		if (certResponseList != null
				&& certResponseList.getCertificateResponseModelList() != null) {

			certResponseModelList = certResponseList
					.getCertificateResponseModelList();
			for (CertificateResponseModel certRespModel : certResponseModelList) {

				if (StringUtils.isNotBlank(certRespModel
						.getCertificateSerialId().toString())) {

					logger.info("getPfxFilePath "
							+ certRespModel.getPfxFilePath());
					logger.info("getCertificateSerialId "
							+ certRespModel.getCertificateSerialId());
					logger.info("getExpiryDate "
							+ certRespModel.getExpiryDate());
					logger.info("getIssueDate " + certRespModel.getIssueDate());

					certDetailModel.setCertExpiryDate(certRespModel
							.getExpiryDate());
					certDetailModel.setCertFilePath(certRespModel
							.getPfxFilePath());
					certDetailModel.setCertIssueDate(certRespModel
							.getIssueDate());
					
					 certDetailModel.setCertSerialNum(certRespModel
					  .getCertificateSerialId());
					 
					
					certDetailModel.setUserId(certRequestModel.getUserId());
					// update certificate details
					Integer certNum = editStatusCertRequestDAO.updateCertDetails(certDetailModel);

					certRequestModel.setCertNum(certNum);
					certRequestModel.setStatusCode(ECRMSConstants.ACTIVE);
					// Update certificate request status
					count = certRequestDAO.updateCertRequest(certRequestModel);
					if(StringUtils.isNotBlank(certRequestModel.getCommentText()))
					{
						//add request comment
						editStatusCertRequestDAO.addReqComment(certRequestModel);
					}
					// send mail
					EmailNotificationModel emailNotificationModel = editStatusCertRequestDAO
							.retrieveEmailNotificationData();
					StringBuffer sb = new StringBuffer();

					sb = sb
							.append(emailNotificationModel.getBody())
							.append(ECRMSConstants.NEW_PARA)
							.append(ECRMSConstants.AU_UDE)
							.append(PropertyReader.retrievePropertiesValue(ECRMSConstants.AU_UDE_LINK_KEY))
							.append(ECRMSConstants.NEW_PARA)
							.append(ECRMSConstants.VW_UDE)
							.append(PropertyReader.retrievePropertiesValue(ECRMSConstants.VW_UDE_LINK_KEY))
							.append(ECRMSConstants.NEW_PARA)
							.append(ECRMSConstants.VINNIE)
							.append(PropertyReader.retrievePropertiesValue(ECRMSConstants.VINNIE_LINK_KEY))
							.append(ECRMSConstants.NEW_PARA)
							.append(PropertyReader.retrievePropertiesValue(ECRMSConstants.DOWNLOAD_INSTRUCT_KEY))
							.append(ECRMSConstants.NEW_PARA)
							.append(PropertyReader.retrievePropertiesValue(ECRMSConstants.DOWNLOAD_SIGNATURE_KEY));
					emailNotificationModel.setBody(sb.toString());
					emailNotificationModel.setContactEmail(certRequestModel
							.getContactEmail());

					
					if(certRequestModel.getDeviceName() != null && !certRequestModel.getDeviceName().equalsIgnoreCase("IRF_"+certRequestModel.getDealerNum().trim())) {
						emailHelper.sendEmail(emailNotificationModel);
					} else {
						//IRFs should not receive emails since they don't have access to eCRMS application.
					}
					
				}
			}
		}
		
		logger.info("End of approveCertRequest");
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.vwoa.ecrms.service.certrequest.IEditStatusCertRequestService#
	 * revokeCertRequest(java.util.List)
	 */
	@Transactional
	@Override
	public List<CertStatusModel> revokeCertRequest(List<CertRequestModel> certRequestModelList) throws ECRMSException, Exception {
		
		logger.info("Start of revokeCertRequest");
		// Call the Helper class to revoke the certificates
				
		CertificateRevokeResponse certificateRevokeResponse = certStatusHelper.revokeCertificate(certRequestModelList);
		
		String userid = null;
		if (null != certRequestModelList && certRequestModelList.size() > 0) {
			userid = ((CertRequestModel) certRequestModelList.get(0))
					.getUserId();
		}

		CertRequestModel certRequestModel = null;
		List<CertRequestModel> certRequestModelSuccessList = new ArrayList<CertRequestModel>();
		CertificateRevokeResponseModel certificateRevokeResponseModel = null;
		// prepare the updated cert request list to update the status in DB
		if (null != certificateRevokeResponse
				&& null != certificateRevokeResponse
						.getCertificateRevokeResponseModel()) {

			String reqNum = "";

			for (int i = 0; i < certificateRevokeResponse
					.getCertificateRevokeResponseModel().size(); i++) {

				certificateRevokeResponseModel = certificateRevokeResponse.getCertificateRevokeResponseModel().get(i);
				certRequestModel = new CertRequestModel();
				certRequestModel.setUserId(userid);
				reqNum = certificateRevokeResponseModel.getRequestId();

				certRequestModel.setRequestNum(Integer.parseInt(reqNum));
				
				logger.info("Request Num is -->" +certRequestModel.getRequestNum());

				certRequestModel.setStatusCode(ECRMSConstants.REVOKED);
				certRequestModelSuccessList.add(certRequestModel);
			}
		}
		logger.info("End of revokeCertRequest");
		// Call DB to update the
		return editStatusCertRequestDAO.revokeCertRequest(certRequestModelSuccessList);
	}
    
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.certrequest.IEditStatusCertRequestService#stallCertRequest(com.vwoa.ecrms.model.certrequest.CertRequestModel)
	 */
	@Transactional
	@Override
	public Integer stallCertRequest(CertRequestModel certRequestModel) throws ECRMSException, Exception {
		logger.info("Start of stallCertRequest");
		
		Integer count= 0;
		count = editStatusCertRequestDAO.updateCertReqStatus(certRequestModel);
		//add request comment
		editStatusCertRequestDAO.addReqComment(certRequestModel);
		return count;
	}

	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.certrequest.IEditStatusCertRequestService#updateCertReqStatus(com.vwoa.ecrms.model.certrequest.CertRequestModel)
	 */
	@Transactional
	@Override
	public Integer updateCertReqStatus(CertRequestModel certRequestModel) throws ECRMSException, Exception {
		logger.info("Start of updateCertReqStatus");
		return editStatusCertRequestDAO.updateCertReqStatus(certRequestModel);
	}
	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.certrequest.IEditStatusCertRequestService#addReqComment(com.vwoa.ecrms.model.certrequest.CertRequestModel)
	 */
	@Override
	public Integer addReqComment(CertRequestModel certRequestModel)
	throws ECRMSException, Exception {
		logger.info("Start of addReqComment");
		return editStatusCertRequestDAO.addReqComment(certRequestModel);
	}
	
	/**
	 * @return the editStatusCertRequestDAO
	 */
	public IEditStatusCertRequestDAO getEditStatusCertRequestDAO() {
		return editStatusCertRequestDAO;
	}

	/**
	 * @param editStatusCertRequestDAO
	 *            the editStatusCertRequestDAO to set
	 */
	public void setEditStatusCertRequestDAO(
			IEditStatusCertRequestDAO editStatusCertRequestDAO) {
		this.editStatusCertRequestDAO = editStatusCertRequestDAO;
	}

	/**
	 * @return the certStatusHelper
	 */
	public CertStatusHelper getCertStatusHelper() {
		return certStatusHelper;
	}

	/**
	 * @param certStatusHelper
	 *            the certStatusHelper to set
	 */
	public void setCertStatusHelper(CertStatusHelper certStatusHelper) {
		this.certStatusHelper = certStatusHelper;
	}

	/**
	 * @return the certRequestDAO
	 */
	public ICertRequestDAO getCertRequestDAO() {
		return certRequestDAO;
	}

	/**
	 * @param certRequestDAO
	 *            the certRequestDAO to set
	 */
	public void setCertRequestDAO(ICertRequestDAO certRequestDAO) {
		this.certRequestDAO = certRequestDAO;
	}

	/**
	 * @return the emailHelper
	 */
	public EmailHelper getEmailHelper() {
		return emailHelper;
	}

	/**
	 * @param emailHelper
	 *            the emailHelper to set
	 */
	public void setEmailHelper(EmailHelper emailHelper) {
		this.emailHelper = emailHelper;
	}

	

}
