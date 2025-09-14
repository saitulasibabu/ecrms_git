/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.common;

import java.util.List;

import com.vwoa.ecrms.model.dropdown.DropdownModel;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public interface ICommonDAO {

	/**
	 * This method returns list of device types
	 * @param page
	 * @return List<DropdownModel>
	 * @throws ECRMSException
	 */
	List<DropdownModel> retrieveDeviceTypeData(String page) throws ECRMSException;
	
	/**
	 * This method returns list of statuses 
	 * @return List<DropdownModel>
	 * @throws ECRMSException
	 */
	List<DropdownModel> retrieveStatusData() throws ECRMSException;

}
