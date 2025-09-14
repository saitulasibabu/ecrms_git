/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.certrequest;

import java.util.List;

import com.vwoa.ecrms.model.certrequest.CertDetailModel;
import com.vwoa.ecrms.model.certrequest.CertRequestModel;
import com.vwoa.ecrms.model.certrequest.CertStatusModel;
import com.vwoa.ecrms.model.common.EmailNotificationModel;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public interface IEditStatusCertRequestDAO {

	/**
	 * @param certDetailModel
	 * @return
	 * @throws ECRMSException
	 */
	Integer updateCertDetails(CertDetailModel certDetailModel)
			throws ECRMSException;

	/**
	 * @param certRequestModel
	 * @return
	 * @throws ECRMSException
	 */
	List<CertStatusModel> revokeCertRequest(
			List<CertRequestModel> certRequestModel) throws ECRMSException;

	/**
	 * Method to update certificate request status
	 * 
	 * @param certRequestModel
	 * @return
	 */
	Integer updateCertReqStatus(CertRequestModel certRequestModel)
			throws ECRMSException;

	/**
	 * @return
	 * @throws ECRMSException
	 */
	EmailNotificationModel retrieveEmailNotificationData()
			throws ECRMSException;

	/**
	 * @param certRequestModel
	 * @return
	 * @throws ECRMSException
	 */
	Integer addReqComment(CertRequestModel certRequestModel)
			throws ECRMSException;
	
	

}
