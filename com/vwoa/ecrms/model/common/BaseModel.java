/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.model.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1055028725350864204L;

	public String id;//Variable to hold the id of the request

	public Integer requestNum;//Variable to hold the request number

	public String userId;//Variable to hold the user Id 

	public Date updatedDate;//Variable to hold the updated date by the user

	public Date addedDate;//Variable to hold the added date by the user
	
	public String dealerBrand; // Variable to hold the dealer brand name

	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the addedDate
	 */
	public Date getAddedDate() {
		return addedDate;
	}

	/**
	 * @param addedDate
	 *            the addedDate to set
	 */
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	/**
	 * @return the dealerBrand
	 */
	public String getDealerBrand() {
		return dealerBrand;
	}

	/**
	 * @param dealerBrand
	 *            the dealerBrand to set
	 */
	public void setDealerBrand(String dealerBrand) {
		this.dealerBrand = dealerBrand;
	}


}
