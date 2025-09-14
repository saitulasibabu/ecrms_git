/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.service.common;

import java.util.List;

import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.model.dropdown.DropdownModel;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public interface ICommonService {

	/**
	 * This method returns list of device types
	 * @return
	 * @throws ECRMSException
	 */
	List<DropdownModel> retrieveDeviceTypeData(String page) throws ECRMSException;
	
	/**
	 * This method returns list of statuses based on user profile
	 * @param user
	 * @return
	 * @throws ECRMSException
	 * @throws Exception 
	 */
	List<DropdownModel> retrieveStatusData(UserProfileModel user) throws ECRMSException, Exception;
	
	/**
	 * This method returns list of statuses 
	 * @return
	 * @throws ECRMSException
	 */
	public List<DropdownModel> retrieveStatusData() throws ECRMSException;
}
