/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.certrequest;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vwoa.ecrms.model.certrequest.CertRequestModel;
import com.vwoa.ecrms.model.certrequest.CommentHistoryModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author aparna_deshmukh01
 * 
 */
@Repository
public class CertRequestDAOImpl implements ICertRequestDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(CertRequestDAOImpl.class);
	@Autowired
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.ICertRequestDAO#retriveDealerDetails(java
	 * .lang.String)
	 */
	@Override
	public CertRequestModel retriveDealerDetails(String dealerNumber)
			throws ECRMSException {
		logger.info("Start of  retriveDealerDetails()");
		CertRequestModel certRequestModel = new CertRequestModel();
		try {

			certRequestModel = (CertRequestModel) sqlSession.selectOne(
					ECRMSConstants.RETRIVE_DEALER_DATA, dealerNumber);

		} catch (Exception ex) {

			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  retriveDealerDetails()");
		return certRequestModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.ICertRequestDAO#saveCertRequest(com.vwoa
	 * .ecrms.model.certrequest.CertRequestModel)
	 */
	@Override
	public Integer saveCertRequest(CertRequestModel certRequestModel)
			throws ECRMSException {

		logger.info("Start of  saveCertRequest");
		try {
			// insert request details
			sqlSession.insert(ECRMSConstants.INSERT_CERT_REQUEST,
					certRequestModel);

			// insert request status details
			sqlSession.insert(ECRMSConstants.INSERT_CERT_REQUEST_STATUS,
					certRequestModel);

			logger.info("request num is " + certRequestModel.getRequestNum());

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  saveCertRequest");

		// return generated request number.
		return certRequestModel.getRequestNum();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.ICertRequestDAO#verifyActiveCertForExstDlr
	 * (com.vwoa.ecrms.model.certrequest.CertRequestModel)
	 */
	@Override
	public CertRequestModel verifyActiveCertForExstDlr(
			CertRequestModel certRequestModel) throws ECRMSException {

		logger.info("Start of  verifyActiveCertForExstDlr()");
		CertRequestModel certRequestResultModel = new CertRequestModel();
		try {

			certRequestResultModel = (CertRequestModel) sqlSession.selectOne(
					ECRMSConstants.VERIFY_ACT_CERT_FOR_EXT_DLR,
					certRequestModel);

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_VERIFY_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_VERIFY_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  verifyActiveCertForExstDlr()");
		return certRequestResultModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.ICertRequestDAO#verifyActiveCertForHWID
	 * (java.lang.String)
	 */
	@Override
	public CertRequestModel verifyActiveCertForHWID(String hardwareId)
			throws ECRMSException {
		logger.info("Start of  verifyActiveCertForHWID()");
		CertRequestModel certRequestResultModel = new CertRequestModel();
		try {

			certRequestResultModel = (CertRequestModel) sqlSession.selectOne(
					ECRMSConstants.VERIFY_ACT_CERT_FOR_HWID, hardwareId);

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_VERIFY_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_VERIFY_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  verifyActiveCertForHWID()");
		return certRequestResultModel;
	}

	@Override
	public Integer updateCertRequest(CertRequestModel certRequestModel)
			throws ECRMSException {

		logger.info("Start of  updateCertRequest");
		Integer count = 0;
		try {
			// update certificate request details
			count = sqlSession.update(ECRMSConstants.UPDATE_CERT_REQUEST,
					certRequestModel);

			// insert request status details
			sqlSession.insert(ECRMSConstants.INSERT_CERT_REQUEST_STATUS,
					certRequestModel);

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  updateCertRequest");
		return count;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.ICertRequestDAO#retriveCertReqDetails(
	 * java.lang.String)
	 */
	@Override
	public CertRequestModel retriveCertRequestUserData(Integer requestNum) throws ECRMSException {
		logger.info("Start of  retriveCertReqUserDetails()");
		CertRequestModel certRequestModel = new CertRequestModel();
		try {

			certRequestModel = (CertRequestModel) sqlSession.selectOne(ECRMSConstants.RETRIVE_CERT_REQ_DATA, requestNum);

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  retriveCertReqUserDetails()");
		return certRequestModel;
	}	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.ICertRequestDAO#retriveCertReqDetails(
	 * java.lang.String)
	 */
	@Override
	public CertRequestModel retriveCertReqDetails(Integer requestNum)
			throws ECRMSException {
		logger.info("Start of  retriveCertReqDetails()");
		CertRequestModel certRequestModel = new CertRequestModel();
		try {

			certRequestModel = (CertRequestModel) sqlSession.selectOne(ECRMSConstants.RETRIVE_CERT_REQ_DATA, requestNum);

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  retriveCertReqDetails()");
		return certRequestModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.ICertRequestDAO#retriveReqCommentDetails
	 * (java.lang.String)
	 */
	@Override
	public List<CommentHistoryModel> retriveReqCommentDetails(Integer requestNum)
			throws ECRMSException {
		logger.info("Start of  retriveReqCommentDetails()");
		List<CommentHistoryModel> reqCommentList = new ArrayList<CommentHistoryModel>();
		try {

			reqCommentList = sqlSession.selectList(
					ECRMSConstants.RETRIVE_REQ_COMMENT_DATA, requestNum);

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  retriveReqCommentDetails()");
		return reqCommentList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.ICertRequestDAO#retrieveExpiryPeriod()
	 */
	@Override
	public String retrieveExpiryPeriod() throws ECRMSException {
		logger.info("Start of  retrieveExpiryPeriod");
		String expValue = "";
		try {

			expValue = (String) sqlSession
					.selectOne(ECRMSConstants.RETRIEVE_CONFIG_VALUE);

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  retrieveExpiryPeriod");
		return expValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.ICertRequestDAO#retrieveCertActvnDate(
	 * java.lang.Integer)
	 */
	@Override
	public String retrieveCertActvnDate(Integer requestNum)
			throws ECRMSException {
		logger.info("Start of  retrieveCertActvnDate");
		String activationDate = null;
		try {

			activationDate = (String) sqlSession.selectOne(
					ECRMSConstants.RETRIEVE_CERT_ACTVN_DATE, requestNum);

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  retrieveCertActvnDate");
		return activationDate;
	}

}
