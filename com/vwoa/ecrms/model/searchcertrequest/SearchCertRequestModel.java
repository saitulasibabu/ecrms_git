/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.model.searchcertrequest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.vwoa.ecrms.model.common.BaseModel;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class SearchCertRequestModel extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2804568130439118992L;

	private String dealerNum;// variable to hold Dealer Number
	private String dealerName; //variable to hold Dealer Name
	private Map<String, String> statusMap; // map to hold status list
	private List<String> deviceTypeList;// list For Device Type (Drop down) List
	private String status; // variable to hold request status code
	private String deviceType; // variable to hold device type for the hardware
	private String hardwareId; // variable to hold 32 bit hardware ID
	private String certSerialNum; // variable to hold certificate Serial Number
	private String fromExpiryDate; // variable to hold From certificate expiration
									// date
	private String toExpiryDate; // variable to hold To certificate expiration
								// date
	private String fromIssueDate; // variable to hold From certificate Issue date
	private String toIssueDate; // variable to hold To certificate Issue date
	private String fromRevokedDate; // variable to hold From certificate
									// revocation date
	private String toRevokedDate; // variable to hold To certificate revocation
								// date

	/** Fields related to pagination and Sorting */
	private String orderByField;
	private String orderByVal;
	private String userRole; // variable to hold user role
	private Integer basicSearchCount;
	private Integer advancedSearchCount;
	private Integer offset; // variable to hold offset for pagination
	private Integer limit;	// variable to hold limit for pagination
	
	private String requestNumStr; // variable to hold request number as a string for advanced search
	

	private List<String> reqNumList; //list to hold request numbers to reactivate the certificates

	/**
	 * @return the requestNumStr
	 */
	public String getRequestNumStr()
	{
		return requestNumStr;
	}

	/**
	 * @param requestNumStr the requestNumStr to set
	 */
	public void setRequestNumStr(String requestNumStr)
	{
		this.requestNumStr = requestNumStr;
	}
	
	/**
	 * @return
	 */
	public String getUserRole()
	{
		return userRole;
	}
	
	public void setUserRole(String userRole)
	{
		this.userRole=userRole;
	}
	
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
	 * @return integer-total count for basic search result 
	 */
	public Integer getBasicSearchCount()
	{
		return basicSearchCount;
	}
	
	/**
	 * @param basicSearchCount
	 */
	public void setBasicSearchCount(Integer basicSearchCount)
	{
		this.basicSearchCount=basicSearchCount;
	}
	/**
	 * @return integer-total count for advanced search result 
	 */
	public Integer getAdvancedSearchCount()
	{
		return advancedSearchCount;
	}
	
	/**
	 * @param advancedSearchCount
	 */
	public void setAdvancedSearchCount(Integer advancedSearchCount)
	{
		this.advancedSearchCount=advancedSearchCount;
	}
	
	
	
	/**
	 * @return the offset
	 */
	public Integer getOffset()
	{
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Integer offset)
	{
		this.offset = offset;
	}

	/**
	 * @return the limit
	 */
	public Integer getLimit()
	{
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit)
	{
		this.limit = limit;
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
	 * @return the statusMap
	 */
	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	/**
	 * @param statusMap
	 *            the statusMap to set
	 */
	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
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
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceTypeList(List<String> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

	/**
	 * @return the deviceType
	 */
	public List<String> getDeviceTypeList() {
		return deviceTypeList;
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
	 * @return the serialNum
	 */
	public String getCertSerialNum() {
		return certSerialNum;
	}

	/**
	 * @param serialNum
	 *            the serialNum to set
	 */
	public void setCertSerialNum(String certSerialNum) {
		this.certSerialNum = certSerialNum;
	}

	/**
	 * @return the fromExpiryDate
	 */
	public String getFromExpiryDate() {
		return fromExpiryDate;
	}

	/**
	 * @param fromExpiryDate
	 *            the fromExpiryDate to set
	 */
	public void setFromExpiryDate(String fromExpiryDate) {
		this.fromExpiryDate = fromExpiryDate;
	}

	/**
	 * @return the toExpiryDate
	 */
	public String getToExpiryDate() {
		return toExpiryDate;
	}

	/**
	 * @param toExpiryDate
	 *            the toExpiryDate to set
	 */
	public void setToExpiryDate(String toExpiryDate) {
		this.toExpiryDate = toExpiryDate;
	}

	/**
	 * @return the fromIssueDate
	 */
	public String getFromIssueDate() {
		return fromIssueDate;
	}

	/**
	 * @param fromIssueDate
	 *            the fromIssueDate to set
	 */
	public void setFromIssueDate(String fromIssueDate) {
		this.fromIssueDate = fromIssueDate;
	}

	/**
	 * @return the toIssueDate
	 */
	public String getToIssueDate() {
		return toIssueDate;
	}

	/**
	 * @param toIssueDate
	 *            the toIssueDate to set
	 */
	public void setToIssueDate(String toIssueDate) {
		this.toIssueDate = toIssueDate;
	}

	/**
	 * @return the fromRevokedDate
	 */
	public String getFromRevokedDate() {
		return fromRevokedDate;
	}

	/**
	 * @param fromRevokedDate
	 *            the fromRevokedDate to set
	 */
	public void setFromRevokedDate(String fromRevokedDate) {
		this.fromRevokedDate = fromRevokedDate;
	}

	/**
	 * @return the toRevokedDate
	 */
	public String getToRevokedDate() {
		return toRevokedDate;
	}

	/**
	 * @param toRevokedDate
	 *            the toRevokedDate to set
	 */
	public void setToRevokedDate(String toRevokedDate) {
		this.toRevokedDate = toRevokedDate;
	}

	/**
	 * @return the reqNumList
	 */
	public List<String> getReqNumList() {
		return reqNumList;
	}

	/**
	 * @param reqNumList the reqNumList to set
	 */
	public void setReqNumList(List<String> reqNumList) {
		this.reqNumList = reqNumList;
	}
	
	

}
