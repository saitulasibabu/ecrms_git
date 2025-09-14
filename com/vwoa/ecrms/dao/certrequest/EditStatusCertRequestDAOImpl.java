/**
 * 
 */
package com.vwoa.ecrms.dao.certrequest;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vwoa.ecrms.model.certrequest.CertDetailModel;
import com.vwoa.ecrms.model.certrequest.CertRequestModel;
import com.vwoa.ecrms.model.certrequest.CertStatusModel;
import com.vwoa.ecrms.model.common.EmailNotificationModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
@Repository
public class EditStatusCertRequestDAOImpl implements IEditStatusCertRequestDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(EditStatusCertRequestDAOImpl.class);
	@Autowired
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.IEditStatusCertRequestDAO#updateCertDetails
	 * (com.vwoa.ecrms.model.certrequest.CertDetailModel)
	 */
	@Override
	public Integer updateCertDetails(CertDetailModel certDetailModel)
			throws ECRMSException {

		logger.info("Start of  updateCertDetails");
		try {
			// insert request details
			sqlSession.insert(ECRMSConstants.INSERT_CERT_DETAIL,
					certDetailModel);

			logger.info("certificate num is " + certDetailModel.getCertNum());

		}
		// Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  updateCertDetails");
		return certDetailModel.getCertNum();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.IEditStatusCertRequestDAO#revokeCertRequest
	 * (java.util.List)
	 */
	@Override
	public List<CertStatusModel> revokeCertRequest(
			List<CertRequestModel> certRequestModelList) throws ECRMSException {

		logger.info("Start of  revokeCertRequest");

		CertRequestModel certRequestModel = null;
		CertStatusModel certStatusModel = null;
		List<CertStatusModel> successList = new ArrayList<CertStatusModel>();

		try {

			for (int i = 0; i < certRequestModelList.size(); i++) {

				certRequestModel = certRequestModelList.get(i);

				// insert request details
				sqlSession.update(ECRMSConstants.UPDATE_CERT_REQUEST_STATUS,
						certRequestModel);

				// insert request status details
				sqlSession.insert(ECRMSConstants.INSERT_CERT_REQUEST_STATUS,
						certRequestModel);
				
				
				// update the Revoke Date
				sqlSession.update(ECRMSConstants.UPDATE_REVOKE_DATE, certRequestModel);
				
				// Prepare the return status list
				certStatusModel = new CertStatusModel();
				certStatusModel.setRequestNum(certRequestModel.getRequestNum());
				certStatusModel.setStatus(ECRMSConstants.SUCCESS);
				successList.add(certStatusModel);

			}

		}
		// Handling the Exception
		catch (Exception ex) {

			logger.debug("Error in revokeCertRequest");
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.description(),
					ex);

			throw exception;
		}

		logger.info("End of  revokeCertRequest");
		return successList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.IEditStatusCertRequestDAO#updateCertReqStatus
	 * (com.vwoa.ecrms.model.certrequest.CertRequestModel)
	 */
	@Override
	public Integer updateCertReqStatus(CertRequestModel certRequestModel)
			throws ECRMSException {
		logger.info("Start of  updateCertReqStatus");
		Integer count = 0;
		try {
			// insert request details
			count = sqlSession
					.update(ECRMSConstants.UPDATE_CERT_REQUEST_STATUS,
							certRequestModel);

			// insert request status details
			sqlSession.insert(ECRMSConstants.INSERT_CERT_REQUEST_STATUS,
					certRequestModel);

		}
		// Handling the exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  updateCertReqStatus");

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.vwoa.ecrms.dao.certrequest.IEditStatusCertRequestDAO#
	 * retrieveEmailNotificationData()
	 */
	@Override
	public EmailNotificationModel retrieveEmailNotificationData()
			throws ECRMSException {
		logger.info("Start of  retrieveEmailNotificationData()");

		EmailNotificationModel emailNotificationModel = null;

		try {

			emailNotificationModel = (EmailNotificationModel) sqlSession
					.selectOne(ECRMSConstants.RETRIEVE_EMAIL_DATA);

		}
		// Handling the exception
		catch (Exception ex) {

			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;

		}

		logger.info("End of  retrieveEmailNotificationData()");
		return emailNotificationModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.IEditStatusCertRequestDAO#addReqComment
	 * (com.vwoa.ecrms.model.certrequest.CertRequestModel)
	 */
	@Override
	public Integer addReqComment(CertRequestModel certRequestModel)
			throws ECRMSException {
		logger.info("Start of  addReqComment");
		Integer count = 0;
		try {
			// update certificate request details
			count = sqlSession.insert(
					ECRMSConstants.INSERT_CERT_REQUEST_COMMENT,
					certRequestModel);

		}
		// Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  addReqComment");
		return count;
	}

}
