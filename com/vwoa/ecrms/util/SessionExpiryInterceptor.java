package com.vwoa.ecrms.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class SessionExpiryInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7384510982611633080L;


	private static final Logger logger = LoggerFactory
			.getLogger(SessionExpiryInterceptor.class);

	/**
	 * Method to check the session expiration period for a logged-in user.
	 * 
	 * @param actionInvocation
	 *            to get the context
	 * @return message the appropriate message to user, if the session got
	 *         expired
	 * @throws Exception
	 */

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		logger.info("Start of intercept method");
		// getting session object from ActionContext here because session object of
		// SessionAware would be always null on initial load, as interceptor
		// gets call before Action class invokation.Needs to confirm

		Map<String, Object> session = ActionContext.getContext().getSession();
		// Logout
		if (session == null || session.isEmpty()
				|| session.get(ECRMSConstants.USERPROFILE_KEY) == null) {

			addActionError(actionInvocation, PropertyReader
					.retrievePropertiesValue(ECRMSConstants.SESSION_EXPIRY_KEY));
			logger.info("session is expired");

			return "SESSION_EXPIRED";
		}
		logger.info("End of intercept method");
		return actionInvocation.invoke();
	}

	/**
	 * Method to set the error message
	 * 
	 * @param invocation
	 * @param message
	 */
	private void addActionError(ActionInvocation invocation, String message) {
		Object action = invocation.getAction();
		if (action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}



}
