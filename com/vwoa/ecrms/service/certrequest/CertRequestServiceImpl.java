/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.service.certrequest;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vwoa.ecrms.dao.certrequest.ICertRequestDAO;
import com.vwoa.ecrms.helper.webservice.CertStatusHelper;
import com.vwoa.ecrms.model.certrequest.CertRequestModel;
import com.vwoa.ecrms.model.certrequest.CommentHistoryModel;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
@Service
public class CertRequestServiceImpl implements ICertRequestService {
	private static final Logger logger = LoggerFactory
			.getLogger(CertRequestServiceImpl.class);
	@Autowired
	private ICertRequestDAO certRequestDAO;

	@Autowired
	private CertStatusHelper certStatusHelper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.service.certrequest.ICertRequestService#retriveDealerDetails
	 * (java.lang.String)
	 */
	@Override
	public CertRequestModel retriveDealerDetails(String dealerNum)
			throws ECRMSException {
		logger.info("Start of retriveDealerDetails");
		return certRequestDAO.retriveDealerDetails(dealerNum);
	}

	@Transactional
	@Override
	public Integer saveCertRequest(CertRequestModel certRequestModel)
			throws ECRMSException {
		logger.info("Start of saveCertRequest");
		return certRequestDAO.saveCertRequest(certRequestModel);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.vwoa.ecrms.service.certrequest.ICertRequestService#
	 * verifyActiveCertForHWID(java.lang.String)
	 */
	@Override
	public CertRequestModel verifyActiveCertForHWID(String hardwareId)
			throws ECRMSException {
		logger.info("Start of verifyActiveCertForHWID");
		return certRequestDAO.verifyActiveCertForHWID(hardwareId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.vwoa.ecrms.service.certrequest.ICertRequestService#
	 * verifyActiveCertForExstDlr
	 * (com.vwoa.ecrms.model.certrequest.CertRequestModel)
	 */
	@Override
	public CertRequestModel verifyActiveCertForExstDlr(
			CertRequestModel certRequestModel) throws ECRMSException {
		logger.info("Start of verifyActiveCertForExstDlr");
		CertRequestModel requestModel = null;

		requestModel = certRequestDAO
				.verifyActiveCertForExstDlr(certRequestModel);
		
		// set certificate activation indicator

		if (requestModel != null) {
			String certActivationDate = certRequestDAO
					.retrieveCertActvnDate(requestModel.getRequestNum());

			if (StringUtils.isNotBlank(certActivationDate)) {
				String certActivationInd = certStatusHelper
						.prepareCertActivationInd(certActivationDate);
				requestModel.setCertActvnInd(certActivationInd);
			}
		}
		return requestModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.vwoa.ecrms.service.certrequest.ICertRequestService#
	 * retrieveCertRequestDetails(java.lang.String)
	 */
	@Override
	public CertRequestModel retrieveCertRequestDetails(Integer requestNum)
			throws ECRMSException {
		logger.info("Start of retrieveCertRequestDetails");
		CertRequestModel certRequestModel = null;
		List<CommentHistoryModel> reqCommentList = null;

		certRequestModel = certRequestDAO.retriveCertReqDetails(requestNum);

		CertRequestModel certRequestDlrDetails =  new CertRequestModel();
		
		
		//IRF site information is hardcoded, since dealer/site info is not stored in the db. So no need to capture it. 
		//Device name will be in format IRF_DealerCode
		if(certRequestModel.getDeviceName() != null && certRequestModel.getDeviceName().equalsIgnoreCase("IRF_"+certRequestModel.getDealerNum().trim())) {
			certRequestModel.setDealerName("IRF User");
			certRequestModel.setDealerStreetLine("Street1");
			certRequestModel.setDealerCity("City");
			certRequestModel.setDealerState("ST");
			certRequestModel.setDealerZip("11111");
			certRequestModel.setDealerCntry("USA");
			certRequestModel.setUserType("IRF");
		}
		else{ 
			certRequestDlrDetails = certRequestDAO.retriveDealerDetails(certRequestModel.getDealerNum().trim());

			// setting dealer details in certRequestModel model
			if (certRequestDlrDetails != null) {
	
				if (StringUtils.isNotBlank(certRequestDlrDetails.getDealerName())) {
					certRequestModel.setDealerName(certRequestDlrDetails
							.getDealerName());
				}
				if (StringUtils
						.isNotBlank(certRequestDlrDetails.getDualDealerNum())) {
					certRequestModel.setDualDealerNum(certRequestDlrDetails
							.getDualDealerNum());
				}
				if (StringUtils.isNotBlank(certRequestDlrDetails.getDealerCity())) {
					certRequestModel.setDealerCity(certRequestDlrDetails
							.getDealerCity());
				}
				if (StringUtils.isNotBlank(certRequestDlrDetails.getDealerState())) {
					certRequestModel.setDealerState(certRequestDlrDetails
							.getDealerState());
				}
				if (StringUtils.isNotBlank(certRequestDlrDetails
						.getDealerStreetLine())) {
					certRequestModel.setDealerStreetLine(certRequestDlrDetails
							.getDealerStreetLine());
				}
				if (StringUtils.isNotBlank(certRequestDlrDetails.getDealerZip())) {
					certRequestModel.setDealerZip(certRequestDlrDetails
							.getDealerZip());
				}
				if (StringUtils.isNotBlank(certRequestDlrDetails.getDealerCntry())) {
					certRequestModel.setDealerCntry(certRequestDlrDetails
							.getDealerCntry());
				}
			}

		}
		logger.info("End of retrieveCertRequestDetails");
		return certRequestModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.vwoa.ecrms.service.certrequest.ICertRequestService#
	 * retriveReqCommentDetails(java.lang.Integer)
	 */
	@Override
	public List<CommentHistoryModel> retriveReqCommentDetails(Integer requestNum)
			throws ECRMSException {

		logger.info("Start of retriveReqCommentDetails");
		return certRequestDAO.retriveReqCommentDetails(requestNum);
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

}
