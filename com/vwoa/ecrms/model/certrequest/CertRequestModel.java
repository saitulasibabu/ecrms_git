/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.model.certrequest;

import java.util.Map;

import com.vwoa.ecrms.model.common.BaseModel;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class CertRequestModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7386577290181294665L;

	private Integer certNum; // variable to hold certificate no for the
	// particular request
	private String dealerNum; // variable to hold Dealer Number
	private String dealerName; // variable to hold Dealer Name
	private String dualDealerNum; // variable to hold Dual Dealer Number
	private String dealerPhnNum; // variable to hold Dealer Phone Number
	private String dealerStreetLine; // variable to hold complete Dealer Street
	private String dealerCity; // variable to hold Dealer City
	private String dealerState; // variable to hold Dealer State
	private String dealerZip; // variable to hold Dealer ZIP code
	private String dealerCntry; // variable to hold Dealer Country Code
	private String contactName; // variable to hold Contact Name
	private String contactPhnNum; // variable to hold Contact Phone Number
	private String contactEmail; // variable to hold Contact Email ID
	private String confirmEmail; // variable to hold confirmed Email ID
	private String deviceType; // variable to hold device type for the hardware
	private String hardwareId; // variable to hold 32 bit hardware ID
	private String deviceName; // variable to hold Device Nick name
	private String commentText; // variable to hold Request Comment text
	private String statusCode; // variable to hold Request status code
	private Map<String, String> deviceTypeMap; // Map to hold device type list
	private String expiryPeriod;// variable to hold expiry period config value
	private String certActvnInd; // variable to hold certificate activation
									// indicator

	private String userType;
	private String certRequestedByUsr;
	private String userTypeDlrOrCorp;

	public String getUserTypeDlrOrCorp() {
		return userTypeDlrOrCorp;
	}

	public void setUserTypeDlrOrCorp(String userTypeDlrOrCorp) {
		this.userTypeDlrOrCorp = userTypeDlrOrCorp;
	}

	/**
	 * @return the certNum
	 */
	public Integer getCertNum() {
		return certNum;
	}

	/**
	 * @param certNum
	 *            the certNum to set
	 */
	public void setCertNum(Integer certNum) {
		this.certNum = certNum;
	}

	/**
	 * @return the dealerNum
	 */
	public String getDealerNum() {
		return dealerNum;
	}

	/**
	 * @param dealerNum
	 *            the dealerNum to set
	 */
	public void setDealerNum(String dealerNum) {
		this.dealerNum = dealerNum;
	}

	/**
	 * @return the dealerName
	 */
	public String getDealerName() {
		return dealerName;
	}

	/**
	 * @param dealerName
	 *            the dealerName to set
	 */
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	/**
	 * @return the dualDealerNum
	 */
	public String getDualDealerNum() {
		return dualDealerNum;
	}

	/**
	 * @param dualDealerNum
	 *            the dualDealerNum to set
	 */
	public void setDualDealerNum(String dualDealerNum) {
		this.dualDealerNum = dualDealerNum;
	}

	/**
	 * @return the dealerPhnNum
	 */
	public String getDealerPhnNum() {
		return dealerPhnNum;
	}

	/**
	 * @param dealerPhnNum
	 *            the dealerPhnNum to set
	 */
	public void setDealerPhnNum(String dealerPhnNum) {
		this.dealerPhnNum = dealerPhnNum;
	}

	/**
	 * @return the dealerStreetLine
	 */
	public String getDealerStreetLine() {
		return dealerStreetLine;
	}

	/**
	 * @param dealerStreetLine
	 *            the dealerStreetLine to set
	 */
	public void setDealerStreetLine(String dealerStreetLine) {
		this.dealerStreetLine = dealerStreetLine;
	}

	/**
	 * @return the dealerCity
	 */
	public String getDealerCity() {
		return dealerCity;
	}

	/**
	 * @param dealerCity
	 *            the dealerCity to set
	 */
	public void setDealerCity(String dealerCity) {
		this.dealerCity = dealerCity;
	}

	/**
	 * @return the dealerState
	 */
	public String getDealerState() {
		return dealerState;
	}

	/**
	 * @param dealerState
	 *            the dealerState to set
	 */
	public void setDealerState(String dealerState) {
		this.dealerState = dealerState;
	}

	/**
	 * @return the dealerZip
	 */
	public String getDealerZip() {
		return dealerZip;
	}

	/**
	 * @param dealerZip
	 *            the dealerZip to set
	 */
	public void setDealerZip(String dealerZip) {
		this.dealerZip = dealerZip;
	}

	/**
	 * @return the dealerCntry
	 */
	public String getDealerCntry() {
		return dealerCntry;
	}

	/**
	 * @param dealerCntry
	 *            the dealerCntry to set
	 */
	public void setDealerCntry(String dealerCntry) {
		this.dealerCntry = dealerCntry;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName
	 *            the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactPhnNum
	 */
	public String getContactPhnNum() {
		return contactPhnNum;
	}

	/**
	 * @param contactPhnNum
	 *            the contactPhnNum to set
	 */
	public void setContactPhnNum(String contactPhnNum) {
		this.contactPhnNum = contactPhnNum;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail
	 *            the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the confirmEmail
	 */
	public String getConfirmEmail() {
		return confirmEmail;
	}

	/**
	 * @param confirmEmail
	 *            the confirmEmail to set
	 */
	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the hardwareId
	 */
	public String getHardwareId() {
		return hardwareId;
	}

	/**
	 * @param hardwareId
	 *            the hardwareId to set
	 */
	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the commentText
	 */
	public String getCommentText() {
		return commentText;
	}

	/**
	 * @param commentText
	 *            the commentText to set
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the deviceTypeMap
	 */
	public Map<String, String> getDeviceTypeMap() {
		return deviceTypeMap;
	}

	/**
	 * @param deviceTypeMap
	 *            the deviceTypeMap to set
	 */
	public void setDeviceTypeMap(Map<String, String> deviceTypeMap) {
		this.deviceTypeMap = deviceTypeMap;
	}

	/**
	 * @return the expiryPeriod
	 */
	public String getExpiryPeriod() {
		return expiryPeriod;
	}

	/**
	 * @param expiryPeriod
	 *            the expiryPeriod to set
	 */
	public void setExpiryPeriod(String expiryPeriod) {
		this.expiryPeriod = expiryPeriod;
	}

	/**
	 * @return the certActvnInd
	 */
	public String getCertActvnInd() {
		return certActvnInd;
	}

	/**
	 * @param certActvnInd
	 *            the certActvnInd to set
	 */
	public void setCertActvnInd(String certActvnInd) {
		this.certActvnInd = certActvnInd;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCertRequestedByUsr() {
		return certRequestedByUsr;
	}

	public void setCertRequestedByUsr(String certRequestedByUsr) {
		this.certRequestedByUsr = certRequestedByUsr;
	}
}
