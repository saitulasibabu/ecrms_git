package com.vwoa.ecrms.action.administration;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.vwoa.ecrms.action.BaseAction;
import com.vwoa.ecrms.model.administration.NotificationModel;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.service.administration.IAdminService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to handle all the actions related to Certificate Create Notifications
 * and Certificate Expiry Notification
 * @author Vijay_Bhadoria
 * 
 */
/**
 * @author sanman_ganthe
 * 
 */
@ParentPackage("ecrms-default")
public class NotificationAdminAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = -3094958750964676285L;
	private static final Logger logger = LoggerFactory
			.getLogger(NotificationAdminAction.class);

	private String exceptionMessage;

	private Map<String, Object> session;

	private NotificationModel notificationModel;
	@Autowired
	private IAdminService adminService;

	/**
	 * This method retrieves details required of Expired Notifications
	 * 
	 * @return String the message as success or failure
	 */
	@Action(value = "/administration/expiryNotifications")
	public String retrieveExpiryNotification() throws ECRMSException {
		logger.info("Start of retrieveExpiryNotification");
		try {
			
			// Fetching the user model from the session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());
			// Retrieving the notificationModel from the service
			
			notificationModel = adminService.retrieveExpiryNotification();

		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		}
		// Handling the exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		}

		logger.info("End of retrieveExpiryNotification");

		return SUCCESS;

	}

	/**
	 * This method saves the updated data for Expired Notification
	 * 
	 * @return String the message as success or failure
	 */
	@Action(value = "/administration/saveExpiry", results = { @Result(name = SUCCESS, type = "json") })
	public String saveExpiryNotification() throws ECRMSException {
		logger.info("Start of saveExpiryNotification");
		try {

			// Fetching the user details from the session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());

			// Setting the user Id into the notification model
			notificationModel.setUserId(user.getUserId());

			// Sending the updated notification model to service
			adminService.saveExpiryNotification(notificationModel);

			// No exception
			exceptionMessage = "";
		}
		// Handling the ECRM Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);

			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_UPDATE_DATA
							.description()));

			exceptionMessage = getText(ecrmsException.getDescription());

		}
		// Handling the Exception
		catch (Exception ex) {

			logger.error("Exception occured is : ", ex);

			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_UPDATE_DATA
							.description()));

			exceptionMessage = getText(ecrmsException.getDescription());
		}

		logger.info("End of saveExpiryNotification");

		return SUCCESS;
	}

	/**
	 * This method retrieves details of Received Notification
	 * 
	 * @return String the message as success or failure
	 */

	@Action(value = "/administration/receivedNotifications")
	public String retrieveReceivedNotification() throws ECRMSException {
		logger.info("Start of retrieveReceivedNotification");
		try {

			// Fetching the user model from the session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());
			// Fetching the details from service into the notification model
			notificationModel = adminService.retrieveReceivedNotification();

		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		// Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}

		logger.info("End of retrieveReceivedNotification");
		return SUCCESS;

	}

	/**
	 * This method saves the updated data for Received Notification
	 * 
	 * @return String the message as success or failure
	 */

	@Action(value = "/administration/saveRecieved", results = { @Result(name = SUCCESS, type = "json") })
	public String saveReceivedNotification() throws ECRMSException {

		logger.info("Start of saveReceivedNotification");
		try {

			// Fetching the user details from the session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());

			// Setting the user Id into the model
			notificationModel.setUserId(user.getUserId());

			// Sending the updated notification model to the service
			adminService.saveReceivedNotification(notificationModel);

			// No exception
			exceptionMessage = "";

		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_UPDATE_DATA
							.description()));

			exceptionMessage = getText(ecrmsException.getDescription());
		}
		// Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);

			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_UPDATE_DATA
							.description()));

			exceptionMessage = getText(ecrmsException.getDescription());
			logger.info(exceptionMessage);
		}

		logger.info("End of saveReceivedNotification");

		return SUCCESS;
	}

	/**
	 * @return the notificationModel
	 */
	public NotificationModel getNotificationModel() {
		return notificationModel;
	}

	/**
	 * @param notificationModel
	 *            the notificationModel to set
	 */

	public void setNotificationModel(NotificationModel notificationModel) {
		this.notificationModel = notificationModel;
	}

	/**
	 * @param adminService
	 *            the adminService to set
	 */

	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
