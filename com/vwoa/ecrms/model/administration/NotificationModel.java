/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.model.administration;

/**
  * @author Vijay_Bhadoria
  */
public class NotificationModel {

	private static final long serialVersionUID = 5140203980677875682L;
	
	private String expiryFrom;//Variable to hold the emailId id for the expiry notification
	private String expirySubj;//Variable to hold the subject for the expiry notification
	private String expiryBody;//Variable to hold the body for the expiry notification
	
	private String receivedFrom;//Variable to hold the emailId id for the received notification
	private String receivedSubj;//Variable to hold the subject for the received notification
	private String receivedBody;//Variable to hold the body for the received notification
	
	private String userId;
	
	
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
	 * @return expiryFrom
	 */
	public String getExpiryFrom() {
		return expiryFrom;
	}
	/**
	 * @param expiryFrom
	 */
	public void setExpiryFrom(String expiryFrom) {
		this.expiryFrom = expiryFrom;
	}
	/**
	 * @return expirySubj
	 */
	public String getExpirySubj() {
		return expirySubj;
	}
	/**
	 * @param expirySubj
	 */
	public void setExpirySubj(String expirySubj) {
		this.expirySubj = expirySubj;
	}
	/**
	 * @return expiryBody
	 */
	public String getExpiryBody() {
		return expiryBody;
	}
	/**
	 * @param expiryBody
	 */
	public void setExpiryBody(String expiryBody) {
		this.expiryBody = expiryBody;
	}
	/**
	 * @return receivedFrom
	 */
	public String getReceivedFrom() {
		return receivedFrom;
	}
	/**
	 * @param receivedFrom
	 */
	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}
	
	/**
	 * @return receivedSubj
	 */
	public String getReceivedSubj() {
		return receivedSubj;
	}
	/**
	 * @param receivedSubj
	 */
	public void setReceivedSubj(String receivedSubj) {
		this.receivedSubj = receivedSubj;
	}
	
	/**
	 * @return receivedBody
	 */
	public String getReceivedBody() {
		return receivedBody;
	}
	
	/**
	 * @param receivedBody
	 */
	public void setReceivedBody(String receivedBody) {
		this.receivedBody = receivedBody;
	}
	
	
	
}
