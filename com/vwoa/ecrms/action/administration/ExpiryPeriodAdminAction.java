package com.vwoa.ecrms.action.administration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.vwoa.ecrms.action.BaseAction;
import com.vwoa.ecrms.model.administration.DeviceTypeModel;
import com.vwoa.ecrms.model.administration.ExpiryPeriodModel;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.service.administration.IAdminService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.PropertyReader;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to handle all the actions related to expiry period
 * 
 * @author Vijay_Bhadoria
 * 
 */

@ParentPackage("ecrms-default")
public class ExpiryPeriodAdminAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = -3094958750964676285L;
	private static final Logger logger = LoggerFactory
			.getLogger(ExpiryPeriodAdminAction.class);

	private Map<String, Object> session;

	private ExpiryPeriodModel expiryPeriodModel;
	@Autowired
	private IAdminService adminService;

	private List<DeviceTypeModel> deviceTypeModelList;

	// sorting order - asc or desc
	private String sord;

	// get index row - i.e. user click to sort.
	private String sidx;

	private DeviceTypeModel deviceTypeModel;
	// String to populate the error message
	private String errorMsg;
	// String to populate the exception message
	private String exceptionMessage;
	// Flag used for error message
	private String isError;

	private static Map<String, String> sortMap;
	static {

		sortMap = new HashMap<String, String>();
		sortMap.put(ECRMSConstants.DEVICE_TYPE_CODE,
				ECRMSConstants.DEVICE_TYPE_CODE_DBCOL);
		sortMap.put(ECRMSConstants.DEVICE_TYPE_DESC,
				ECRMSConstants.DEVICE_TYPE_DESC_DBCOL);

	}

	/**
	 * This method loads the adminIndex page
	 * 
	 * @return String the message as success or failure
	 */
	@Action(value = "/administration/loadIndex", results = { @Result(name = SUCCESS, location = "adminIndex.jsp", type = "dispatcher") })
	public String loadIndex() {
		logger.info("Start of loadIndex");

		try {
			// Fetching user model from session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));

			addActionError(getText(ecrmsEx.getDescription()));

			throw ecrmsException;

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		}
		logger.info("End of loadIndex");
		return SUCCESS;
	}

	/**
	 * This method retrieves the Expiry Period value from the service.
	 * 
	 * @return String the message as success or failure
	 */
	@Action(value = "/administration/expiryPeriod", results = { @Result(name = SUCCESS, location = "expiryPeriod.jsp", type = "dispatcher") })
	public String retrieveExpiryPeriod() {
		logger.info("Start of retrieveExpiryPeriod");

		try {

			// Fetching the user model from session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());
			// Retrieving the expiry period model from service
			expiryPeriodModel = adminService.retrieveExpiryPeriod();
		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsEx.getDescription()));
			throw ecrmsException;

		}
		// Handling the Excpetion
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);

			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));
			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		logger.info("End of retrieveExpiryPeriod");
		return SUCCESS;
	}

	/**
	 * This method retrieves the default Expiry Period
	 * 
	 * @return String the message as success or failure
	 */
	@Action(value = "/administration/expiryPeriodDefault", results = { @Result(name = SUCCESS, location = "expiryPeriod.jsp", type = "dispatcher") })
	public String retrieveExpiryPeriodDefault() {
		logger.info("Start of retrieveExpiryPeriodDefault");
		try {

			// Fetching the user model from session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());

			if (expiryPeriodModel == null) {

				expiryPeriodModel = new ExpiryPeriodModel();
			}
			
			// Retrieving the default value of expiry period and setting it in the expiry period model
			String expiryDefault = PropertyReader.retrievePropertiesValue("default.expiryPeriod");
			expiryPeriodModel.setExpiryPeriod(expiryDefault);
			// Handling the ECRMS Exception
		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
			throw ecrmsException;
			// Handling the Exception
		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));

			throw ecrmsException;

		}
		logger.info("End of retrieveExpiryPeriodDefault");
		return SUCCESS;
	}

	/**
	 * This method saves the updated value of Expiry Period
	 * 
	 * @return String the message as success or failure
	 */

	@Action(value = "/administration/saveExpiryPeriod", results = { @Result(name = SUCCESS, type = "json") })
	public String saveExpiryPeriod() {
		logger.info("Start of saveExpiryPeriod");
		try {
			// Fetching the user model from session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());
			// Setting the user Id into the model
			expiryPeriodModel.setUserId(user.getUserId());
			// Sending the updated model to the service
			adminService.saveExpiryPeriod(expiryPeriodModel);
			// No exception
			exceptionMessage = "";
		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
			exceptionMessage = getText(ecrmsException.getDescription());

		}
		// Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);

			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_UPDATE_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			exceptionMessage = getText(ecrmsException.getDescription());
		}
		logger.info("End of saveExpiryPeriod");

		return SUCCESS;
	}

	/**
	 * This method load the device type data
	 * 
	 * @return String the message as success or failure
	 */
	@Action(value = "/administration/deviceTypeList", results = { @Result(name = SUCCESS, location = "deviceTypeList.jsp") })
	public String loadDeviceData() throws ECRMSException {
		logger.info("Start of loadDeviceData");
		try {
			// Fetching the usermodel from session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());

		}
		// Handling the ECRM Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
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

		logger.info("End of loadDeviceData");
		return SUCCESS;
	}

	/**
	 * This method retrieves the device type List
	 * 
	 * @return String the message as success or failure
	 */
	@Action(value = "/administration/retrieveDeviceType", results = { @Result(name = SUCCESS, type = "json") })
	public String retrieveDeviceType() throws ECRMSException {
		logger.info("Start of retrieveDeviceType");
		try {
			// Fetching the user model from the session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());

			// Condition to handle sorting in a grid.
			if (sidx != null && !sidx.equals(ECRMSConstants.EMPTY_STRING)) {
				deviceTypeModel.setOrderByField(sortMap.get(sidx));
				deviceTypeModel.setOrderByVal(sord);
			} else {

				// If no sorting is selected, then do the default order by if
				// any.
				deviceTypeModel.setOrderByField(sortMap
						.get(ECRMSConstants.DEVICE_TYPE_CODE));
				deviceTypeModel.setOrderByVal(ECRMSConstants.ASC);
			}
			// Populating the deviceTypeModel List from the service into the
			// Model List
			deviceTypeModelList = adminService
					.retrieveDeviceType(deviceTypeModel);
		}
		// Handling the ECRMSException
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
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
		logger.info("End of retrieveDeviceType");
		return SUCCESS;
	}

	/**
	 * This method saves the device type code and description
	 * 
	 * @return String the message as success or failure
	 */
	@Action(value = "/administration/saveDeviceType", results = { @Result(name = SUCCESS, type = "json") })
	public String saveDeviceType() throws ECRMSException {
		logger.info("Start of saveDeviceType ");
		try {

			// Fetching the usermodel from session
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			isError = "false";
			// retrieving the list of checked device type code

			String checkedData = deviceTypeModel.getCheckedData();

			String[] split = checkedData.split(",");
			String[] checkedDeviceTypeList;
			String deviceTypeCheckedCode;
			// List for populating device code which has been checked by the user
			ArrayList<String> checkedDeviceCode = new ArrayList<String>();
			// List for populating device code which has been unchecked by the user
			ArrayList<String> uncheckedDeviceCode = new ArrayList<String>();
			List<String> checkedList = Arrays.asList(split);
			// Checking for the checked and unchecked deviceTypeCode
			for (Iterator iterator = checkedList.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				if (string.indexOf("|true") != -1) {

					checkedDeviceTypeList = StringUtils.split(string, '|');
					deviceTypeCheckedCode = checkedDeviceTypeList[0];

					checkedDeviceCode.add(deviceTypeCheckedCode);

				} else if (string.indexOf("|false") != -1) {

					checkedDeviceTypeList = StringUtils.split(string, '|');
					deviceTypeCheckedCode = checkedDeviceTypeList[0];

					uncheckedDeviceCode.add(deviceTypeCheckedCode);

				}
			}
			// Setting the userId into the device Type Model
			deviceTypeModel.setUserId(user.getUserId());
			// Passing the updated model and the device type codes which has
			// been updated by user to the service
			adminService.saveDeviceType(deviceTypeModel, checkedDeviceCode,
					uncheckedDeviceCode);

		}
		// Handling the ECRMS Exception
		catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));

			errorMsg = getText(ecrmsException.getDescription());
			isError = "true";

			return SUCCESS;

		}
		// Handling the Exception
		catch (Exception ex) {
			logger.error("Exception occured is : ", ex);

			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_UPDATE_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			errorMsg = getText(ecrmsException.getDescription());
			isError = "true";
			return SUCCESS;
		}

		logger.info("End of saveDeviceType");
		return SUCCESS;
	}

	/**
	 * @return the sortMap
	 */
	public static Map<String, String> getSortMap() {
		return sortMap;
	}

	/**
	 * @param sortMap
	 */
	public static void setSortMap(Map<String, String> sortMap) {
		ExpiryPeriodAdminAction.sortMap = sortMap;
	}

	/**
	 * @return the expiryPeriodModel
	 */

	public ExpiryPeriodModel getExpiryPeriodModel() {
		return expiryPeriodModel;
	}

	/**
	 * @param expiryPeriodModel
	 *            the expiryPeriodModel to set
	 */

	public void setExpiryPeriodModel(ExpiryPeriodModel expiryPeriodModel) {
		this.expiryPeriodModel = expiryPeriodModel;
	}

	/**
	 * @param adminService
	 *            the adminService to set
	 */
	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * @return the deviceTypeModelList
	 */
	public List<DeviceTypeModel> getDeviceTypeModelList() {
		return deviceTypeModelList;
	}

	/**
	 * @param deviceTypeModelList
	 *            the deviceTypeModelList to set
	 */
	public void setDeviceTypeModelList(List<DeviceTypeModel> deviceTypeModelList) {
		this.deviceTypeModelList = deviceTypeModelList;
	}

	/**
	 * @return the sord
	 */
	public String getSord() {
		return sord;
	}

	/**
	 * @param sord
	 *            the sord to set
	 */
	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * @return the sidx
	 */
	public String getSidx() {
		return sidx;
	}

	/**
	 * @param sidx
	 *            the sidx to set
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	/**
	 * @return the deviceTypeModel
	 */
	public DeviceTypeModel getDeviceTypeModel() {
		return deviceTypeModel;
	}

	/**
	 * @param deviceTypeModel
	 *            the deviceTypeModel to set
	 */
	public void setDeviceTypeModel(DeviceTypeModel deviceTypeModel) {
		this.deviceTypeModel = deviceTypeModel;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the isError
	 */
	public String getIsError() {
		return isError;
	}

	/**
	 * @param isError
	 *            the isError to set
	 */
	public void setIsError(String isError) {
		this.isError = isError;
	}

	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * @param exceptionMessage
	 *            the exceptionMessage to set
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}
