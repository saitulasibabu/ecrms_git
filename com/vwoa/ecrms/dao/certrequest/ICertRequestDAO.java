/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.certrequest;

import java.util.List;

import com.vwoa.ecrms.model.certrequest.CertRequestModel;
import com.vwoa.ecrms.model.certrequest.CommentHistoryModel;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author aparna_deshmukh01
 * 
 */
public interface ICertRequestDAO {

	/**
	 * This method retrieves dealer details required to create to new
	 * certificate request.
	 * 
	 * @param dealerNum
	 *            the dealer number against which to fetch the details
	 * @return CertRequestModel the model having all the dealer details'
	 * @throws ECRMSException
	 */

	CertRequestModel retriveDealerDetails(String dealerNum)
			throws ECRMSException;

	/**
	 * This method inserts new certificate request into database
	 * 
	 * @param certRequestModel
	 *            the model which holds the details to be inserted
	 * 
	 * @return Integer the count as 1 if success and 0 if not
	 * @throws ECRMSException
	 */
	Integer saveCertRequest(CertRequestModel certRequestModel)
			throws ECRMSException;

	/**
	 * This method verifies whether active certificate exists for existing
	 * dealer
	 * 
	 * @param certRequestModel
	 *            the model which holds the dealer number and hardware Id
	 *            against which to verify active certificate
	 * @return Integer the count as 1 if exists and 0 if does not exist
	 * @throws ECRMSException
	 */
	CertRequestModel verifyActiveCertForExstDlr(
			CertRequestModel certRequestModel) throws ECRMSException;

	/**
	 * This method verifies whether active certificate exists for hardware Id
	 * 
	 * @param hardwareId
	 *            the hardwareId against which to verify active certificate
	 * @return Integer the count as 1 if exists and 0 if does not exist
	 * @throws ECRMSException
	 */
	CertRequestModel verifyActiveCertForHWID(String hardwareId)
			throws ECRMSException;

	Integer updateCertRequest(CertRequestModel certRequestModel)
			throws ECRMSException;

	/**
	 * This method retrieves certificate request details
	 * 
	 * @param requestNum
	 *            the request number against which to fetch the details
	 * @return CertRequestModel the model having all the request details'
	 * @throws ECRMSException
	 */

	CertRequestModel retriveCertReqDetails(Integer requestNum)
			throws ECRMSException;

	/**
	 * This method retrieves certificate request comment details
	 * 
	 * @param requestNum
	 *            the request number against which to fetch the details
	 * @return List<CommentHistoryModel> the list of models having all the
	 *         dealer details'
	 * @throws ECRMSException
	 */

	List<CommentHistoryModel> retriveReqCommentDetails(Integer requestNum)
			throws ECRMSException;

	/**
	 * This method retrieves expiry configuration period
	 * 
	 * @return String expiry period
	 * @throws ECRMSException
	 */

	String retrieveExpiryPeriod() throws ECRMSException;

	/**
	 * This method retrieves certificate activation date for active certificates
	 * 
	 * @param requestNum
	 *            the request number against which to fetch the details
	 * @return String certActivation date
	 * @throws ECRMSException
	 */

	String retrieveCertActvnDate(Integer requestNum) throws ECRMSException;

	/**
	 * This method retrieves certificate request user details
	 * 
	 * @param requestNum
	 *            the request number against which to fetch the details
	 * @return CertRequestModel the model having all the request details'
	 * @throws ECRMSException
	 */
	CertRequestModel retriveCertRequestUserData(Integer requestNum)	throws ECRMSException;
}
