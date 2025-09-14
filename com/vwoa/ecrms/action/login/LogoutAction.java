/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */
package com.vwoa.ecrms.action.login;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * Action class for the logout functionality
 * 
 * @author Aparna_Deshmukh01
 * 
 */
public class LogoutAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = -7500376342370341269L;
	private static Log log = LogFactory.getLog(LogoutAction.class);
	private Map<String, Object> session;

	/**
	 * Method for clearing the session
	 * 
	 * @return=SUCCESS
	 */

	@Action(value = "/login/logout", results = { @Result(name = SUCCESS, location = "logout.jsp", type = "dispatcher") })
	
	public String execute() throws Exception {
		log.info("Start of execute method");
		log.info("session got cleared for " + session.get("userProfile"));

		if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
			try {
				((org.apache.struts2.dispatcher.SessionMap) session)
						.invalidate();
			} catch (IllegalStateException e) {
				log.error("Invalid attempt to kill session", e);
			}
		}

		log.info("session got cleared for " + session.get("userProfile"));

		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

	}
}
