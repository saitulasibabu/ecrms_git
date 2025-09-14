/**
 * 
 */
package com.vwoa.ecrms.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.vwoa.ecrms.util.ECRMSConstants;

/**
 * @author Aparna_Deshmukh01
 * 
 */
@ParentPackage("ecrms-default")
public class SessionInvalidateAction extends ActionSupport implements
		SessionAware {

	private static final long serialVersionUID = -7500376342370341269L;
	private static Log log = LogFactory.getLog(SessionInvalidateAction.class);
	private Map<String, Object> session;
	private String sessionValue;

	/**
	 * Method for clearing the session
	 * 
	 * @return=SUCCESS
	 */

	@Action(value = "/invalidateSession", results = { @Result(name = SUCCESS, type = "json") })
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

	/**
	 * @return the sessionValue
	 */
	public String getSessionValue() {
		return sessionValue;
	}

	/**
	 * @param sessionValue
	 *            the sessionValue to set
	 */
	public void setSessionValue(String sessionValue) {
		this.sessionValue = sessionValue;
	}

}
