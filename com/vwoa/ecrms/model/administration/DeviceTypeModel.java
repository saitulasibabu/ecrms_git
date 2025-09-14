/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.model.administration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aparna_Deshmukh01
 *
 */
public class DeviceTypeModel {
	
	private static final long serialVersionUID = 5140203980677875682L;
	
	private String deviceTypeId;//Variable to hold Device Type Code
	private String deviceTypeDesc;//Variable to hold Device Type Description
	private String deviceActiveInd;//Variable to hold the status of the Device Type
	private String checkedData;//Variable to hold the string which contains all the Codes which has been checked
    private String userId;//Variable to hold the user id
    private String checkedDeviceId;//Variable to hold the list of Device Type Code that has been checked
    private String uncheckedDeviceId;//Variable to hold the list of Device Type Code that has been un-checked
	
	

	/** Fields related to Sorting */
	private String orderByField;
	private String orderByVal;

	/**
	 * @return the orderByField
	 */
	public String getOrderByField() {
		return orderByField;
	}

	/**
	 * @param orderByField
	 *            the orderByField to set
	 */
	public void setOrderByField(String orderByField) {
		this.orderByField = orderByField;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderByVal() {
		return orderByVal;
	}

	/**
	 * @param orderBy
	 *            the orderBy to set
	 */
	public void setOrderByVal(String orderByVal) {
		this.orderByVal = orderByVal;
	}

	
	/**
	 * @return deviceActiveInd
	 */
	public String getDeviceActiveInd() {
		return deviceActiveInd;
	}
	/**
	 * @param deviceActiveInd
	 */
	public void setDeviceActiveInd(String deviceActiveInd) {
		this.deviceActiveInd = deviceActiveInd;
	}
	/**
	 * @return deviceTypeId
	 */
	public String getDeviceTypeId() {
		return deviceTypeId;
	}
	/**
	 * @param deviceTypeId
	 */
	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	/**
	 * @return deviceTypeDesc
	 */
	public String getDeviceTypeDesc() {
		return deviceTypeDesc;
	}
	/**
	 * @param deviceTypeDesc
	 */
	public void setDeviceTypeDesc(String deviceTypeDesc) {
		this.deviceTypeDesc = deviceTypeDesc;
	}
	/**
	 * @return the checkedData
	 */
	public String getCheckedData() {
		return checkedData;
	}
	/**
	 * @param checkedData the checkedData to set
	 */
	public void setCheckedData(String checkedData) {
		this.checkedData = checkedData;
	}
	
	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}
    
	/**
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * @return the checkedDeviceId
	 */
	public String getCheckedDeviceId() {
		return checkedDeviceId;
	}

	/**
	 * @param checkedDeviceId the checkedDeviceId to set
	 */
	public void setCheckedDeviceId(String checkedDeviceId) {
		this.checkedDeviceId = checkedDeviceId;
	}

	/**
	 * @return the uncheckedDeviceId
	 */
	public String getUncheckedDeviceId() {
		return uncheckedDeviceId;
	}

	/**
	 * @param uncheckedDeviceId the uncheckedDeviceId to set
	 */
	public void setUncheckedDeviceId(String uncheckedDeviceId) {
		this.uncheckedDeviceId = uncheckedDeviceId;
	}
}
