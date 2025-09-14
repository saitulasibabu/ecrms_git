/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.service.certrequest;

import java.util.List;

import com.vwoa.ecrms.model.certrequest.CertRequestModel;
import com.vwoa.ecrms.model.certrequest.CommentHistoryModel;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public interface ICertRequestService {
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

	Integer saveCertRequest(CertRequestModel certRequestModel)
			throws ECRMSException;

	/**
	 * This method verifies whether active certificate exists for existing
	 * dealer
	 * 
	 * @param certRequestModel
	 *            the model which holds the dealer number and hardware Id
	 *            against which to verify active certificate
	 * @return CertRequestModel which holds request number and request status
	 * @throws ECRMSException
	 */
	CertRequestModel verifyActiveCertForExstDlr(
			CertRequestModel certRequestModel) throws ECRMSException;

	/**
	 * This method verifies whether active certificate exists for hardware Id
	 * 
	 * @param hardwareId
	 *            the hardwareId against which to verify active certificate
	 * @return CertRequestModel which holds request number and request status
	 * @throws ECRMSException
	 */
	CertRequestModel verifyActiveCertForHWID(String hardwareId)
			throws ECRMSException;

	/**
	 * This method retrieves certificate request details to view
	 * 
	 * @param requestNum
	 *            the request number against which to fetch the details
	 * @return CertRequestModel the model having all the request details'
	 * @throws ECRMSException
	 */
	CertRequestModel retrieveCertRequestDetails(Integer requestNum)
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
}
