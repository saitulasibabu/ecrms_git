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
import com.vwoa.ecrms.model.certrequest.CertStatusModel;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public interface IEditStatusCertRequestService {

	/**
	 * @param certRequestModel
	 * @return
	 * @throws Exception 
	 */
	Integer approveCertRequest(CertRequestModel certRequestModel) throws Exception;

	/**
	 * @param certRequestModel
	 * @return
	 * @throws Exception 
	 * @throws ECRMSException 
	 */
	Integer stallCertRequest(CertRequestModel certRequestModel) throws ECRMSException, Exception;

	/**
	 * @param certRequestModel
	 * @return
	 * @throws Exception 
	 */
	List<CertStatusModel> revokeCertRequest(
			List<CertRequestModel> certRequestModel) throws Exception;

	/**
	 * @param certRequestModel
	 * @return
	 * @throws Exception 
	 * @throws ECRMSException 
	 */
	Integer updateCertReqStatus(CertRequestModel certRequestModel) throws ECRMSException, Exception;

	/**
	 * @param certRequestModel
	 * @return
	 * @throws ECRMSException
	 * @throws Exception 
	 */
	Integer addReqComment(CertRequestModel certRequestModel)
			throws ECRMSException, Exception;

	

}
