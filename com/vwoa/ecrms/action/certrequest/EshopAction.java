/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */
package com.vwoa.ecrms.action.certrequest;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.vwoa.ecrms.action.BaseAction;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to render dialog to verify Eshop license
 * 
 * @author Aparna_Deshmukh01
 * 
 */

@ParentPackage("ecrms-default")
public class EshopAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = 838190685756150584L;
	private static final Logger logger = LoggerFactory
			.getLogger(EshopAction.class);
	private Map<String, Object> session;

	/**
	 * Method to render dialog to verify Eshop license login.
	 * 
	 * @return success.
	 */
	@Action(value = "/certrequest/verifyEshopLicense")
	public String loadEshopDialog() throws ECRMSException, Exception {
		logger.info("start of loadEshopDialog");

		UserProfileModel user = (UserProfileModel) session
				.get(ECRMSConstants.USERPROFILE_KEY);

		// setting locale
		ActionContext.getContext().setLocale(user.getUserLocale());

		logger.info("End of loadEshopDialog");
		return SUCCESS;

	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

	}
}
