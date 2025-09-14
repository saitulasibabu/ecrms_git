/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.model.dropdown;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class DropdownModel {

	private String key;
	private String value;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * 
	 */
	public DropdownModel() {
		
	}

	/**
	 * @param key
	 * @param value
	 */
	public DropdownModel(String key, String value) {
		this.key = key;
		this.value = value;
	}

	

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
