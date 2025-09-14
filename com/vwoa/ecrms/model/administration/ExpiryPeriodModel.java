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
 *
 */

public class ExpiryPeriodModel {

	private static final long serialVersionUID = 5140203980677875682L;
	
	private String expiryPeriod;//Variable to hold the Expiry Period
   
	

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
     * @return expiryPeriod
     */
	public String getExpiryPeriod() {
		return expiryPeriod;
	}
    
	/**
	 * @param expiryPeriod
	 */
	public void setExpiryPeriod(String expiryPeriod) {
		this.expiryPeriod = expiryPeriod;
	}
	
	
	
}
