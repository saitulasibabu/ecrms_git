/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.certrequest;

import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 *
 */
public interface IDownloadCertDAO {
	
	/**
	 * Method to certificate file name respective to request number
	 * @param certRequestModel
	 * @return
	 * @throws ECRMSException
	 */
	String retrieveCertFileName(Integer requestNum)
			throws ECRMSException;

}
