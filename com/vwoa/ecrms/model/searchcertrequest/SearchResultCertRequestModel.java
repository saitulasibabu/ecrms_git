/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */
package com.vwoa.ecrms.model.searchcertrequest;

import java.io.Serializable;

import com.vwoa.ecrms.model.common.BaseModel;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class SearchResultCertRequestModel extends BaseModel implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6873694651843551417L;

	private String dealerNum;// variable to hold Dealer Number
	private String dealerName; // variable to hold Dealer Name
	private String deviceNickName; // variable to hold Device Nick name
	private String fileName; // Variable to hold Certificate Serial Name
	private String certActivationDate; //variable to hold Certificate Activation Date
	private String hardwareId; // variable to hold 32 bit hardware ID
	private String status; // variable to hold request status
	private String statuscde; // variable to hold request status
	private String expiryDate; // variable to hold certificate expiration date
	private String deviceType; // variable to hold device type for the hardware
	private Integer requestNum; // variable to hold unique request number
	private String issueDate; // variable to hold certificate Issue date
	private String revokedDate; // variable to hold certificate revoked Date
	private String historyStatus; // variable to hold Certificate Status in history
	private String addDate; // variable to hold Certificate add date
	private String multipleCreateInd; // Flag to determine, whether eligible for multiple create
	private String certActivationInd; //Flag to determine, whether eligible for download
	
	
	/**
	 * @return the addDate
	 */
	public String getAddDate() {
		return addDate;
	}

	/**
	 * @param addDate
	 *            the addDate to set
	 */
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	/**
	 * @return the historyStatus
	 */
	public String getHistoryStatus() {
		return historyStatus;
	}

	/**
	 * @param historyStatus
	 *            the historyStatus to Set
	 */
	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus;
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
		this.dealerName = dealerName.replace("/", "-");
	}

	/**
	 * @return the deviceNickName
	 */
	public String getDeviceNickName() {
		return deviceNickName;
	}

	/**
	 * @param deviceNickName
	 *            the deviceNickName to set
	 */
	public void setDeviceNickName(String deviceNickName) {
		this.deviceNickName = deviceNickName;
	}

	/*
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/*
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the Certificate Activation Date
	 */
	public String getCertActivationDate()
	{
		return certActivationDate;
	}
	
	/**
	 * @param certActivationDate to set
	 */
	public void setCertActivationDate(String certActivationDate){
		this.certActivationDate=certActivationDate;
	}
	
	/**
	 * @return certActivationInd
	 */
	public String getCertActivationInd()
	{
		return certActivationInd;
	}
	
	/**
	 * @param certActivationInd to set
	 */
	public void setCertActivationInd(String certActivationInd)
	{
		this.certActivationInd = certActivationInd;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatuscde() {
		return statuscde;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatuscde(String statuscde) {
		this.statuscde = statuscde;
	}

	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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
	 * @return the requestNum
	 */
	public Integer getRequestNum() {
		return requestNum;
	}

	/**
	 * @param requestNum
	 *            the requestNum to set
	 */
	public void setRequestNum(Integer requestNum) {
		this.requestNum = requestNum;
	}

	/**
	 * @return the issueDate
	 */
	public String getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate
	 *            the issueDate to set
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the revokeDate
	 */
	public String getRevokedDate() {
		return revokedDate;
	}

	/**
	 * @param revokeDate
	 *            the revokeDate to set
	 */
	public void setRevokedDate(String revokedDate) {
		this.revokedDate = revokedDate;
	}

	/**
	 * @return the multipleCreateInd
	 */
	public String getMultipleCreateInd() {
		return multipleCreateInd;
	}

	/**
	 * @param multipleCreateInd
	 *            the multipleCreateInd to set
	 */
	public void setMultipleCreateInd(String multipleCreateInd) {
		this.multipleCreateInd = multipleCreateInd;
	}

}
