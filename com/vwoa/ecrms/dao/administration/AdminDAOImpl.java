/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.administration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vwoa.ecrms.model.administration.DeviceTypeModel;
import com.vwoa.ecrms.model.administration.ExpiryPeriodModel;
import com.vwoa.ecrms.model.administration.NotificationModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author vijay_bhadoria
 * 
 */
@Repository
public class AdminDAOImpl implements IAdminDAO {
	private List<DeviceTypeModel> deviceTypeModelList;
	private static final Logger logger = LoggerFactory
			.getLogger(AdminDAOImpl.class);

	@Autowired
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.administration.IAdminDAO#retrieveDeviceType(com.vwoa
	 * .ecrms.model.administration.DeviceTypeModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceTypeModel> retrieveDeviceType(
			DeviceTypeModel deviceTypeModel) throws ECRMSException {

		logger.info("Start of retrieveDeviceType");

		try {
			
			//Populating the deviceTypeModelList with the list of the device type model from the database
			deviceTypeModelList = sqlSession.selectList(ECRMSConstants.RETRIEVE_DEVICETYPE_CODE,deviceTypeModel);
		} 
		//Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of retrieveDeviceType");
		return deviceTypeModelList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vwoa.ecrms.dao.administration.IAdminDAO#retrieveExpiryPeriod()
	 * This method retrieves expiry period value from database
	 */

	@Override
	public ExpiryPeriodModel retrieveExpiryPeriod() throws ECRMSException {

		logger.info("Start of retrieveExpiryPeriod");
		ExpiryPeriodModel expiryPeriodModel = new ExpiryPeriodModel();
		try {
		//Populating the expiryPeriod model with the values saved in the database 
			expiryPeriodModel = (ExpiryPeriodModel) sqlSession
					.selectOne(ECRMSConstants.RETRIEVE_EXPIRY_PERIOD);
		} 
		//Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}

		logger.info("End of retrieveExpiryPeriod");
		return expiryPeriodModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.administration.IAdminDAO#retrieveExpiryNotification()
	 * This method retrieves the Certiicate expiry notification details from
	 * database
	 */
	@Override
	public NotificationModel retrieveExpiryNotification() throws ECRMSException {

		logger.info("Start of retrieveExpiryNotification");
		NotificationModel notificationModel = new NotificationModel();
		try {
       //Populating the notification model with the values in the database
			notificationModel = (NotificationModel) sqlSession
					.selectOne(ECRMSConstants.RETRIEVE_EXPIRY_NOTIFICATION);
		} 
		//Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}

		logger.info("End of retrieveExpiryNotification");
		return notificationModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.administration.IAdminDAO#retrieveReceivedNotification
	 * () This method retrieves the Certificate create notification details from
	 * database
	 */
	@Override
	public NotificationModel retrieveReceivedNotification()
			throws ECRMSException {

		logger.info("Start of retrieveReceivedNotification");
		NotificationModel notificationModel = new NotificationModel();
		try {
		// Poplating the notification model with the values from the database
			notificationModel = (NotificationModel) sqlSession
					.selectOne(ECRMSConstants.RETRIEVE_RECEIVED_NOTIFICATION);
		} 
		//Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of retrieveReceivedNotification");
		return notificationModel;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.administration.IAdminDAO#saveDeviceType(com.vwoa.ecrms
	 * .model.administration.DeviceTypeModel, java.util.ArrayList,
	 * java.util.ArrayList) This method saves the new device type code and
	 * description and updates active indicator status into the database
	 */

	@SuppressWarnings("unchecked")
	@Override
	public DeviceTypeModel saveDeviceType(DeviceTypeModel deviceTypeModel,
			ArrayList<String> checkedDeviceCode,
			ArrayList<String> uncheckedDeviceCode) throws ECRMSException {

		logger.info("Start of saveDeviceType");
		try {
			/*To check whether user want to add a new device type or not
			 * Save the new device type code and description to the database
			*/
			if(null != deviceTypeModel){
				
				if (StringUtils.isNotBlank(deviceTypeModel.getDeviceTypeId())) {

					sqlSession.insert(ECRMSConstants.ADD_DEVICE_TYPE,deviceTypeModel);
				}
				
			}
			
	        //For updating the status of the device type which has been unchecked by the user			
			for (Iterator iterator = uncheckedDeviceCode.iterator(); iterator
						.hasNext();) {
				
				String uncheckedDeviceId = (String) iterator.next();
				deviceTypeModel.setUncheckedDeviceId(uncheckedDeviceId);
				
				sqlSession.update(ECRMSConstants.UPDATE_UNCHECKED_DEVICETYPESTATUS,deviceTypeModel);

			}
			

			//For updating the status of the device type which has been checked by the user	
			for (Iterator iterator = checkedDeviceCode.iterator(); iterator
					.hasNext();) {
				
				String checkedDeviceId = (String) iterator.next();
				deviceTypeModel.setCheckedDeviceId(checkedDeviceId);
				sqlSession.update(ECRMSConstants.UPDATE_CHECKED_DEVICETYPESTATUS,deviceTypeModel);
				
			}

		} 
		//Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_INSERT_DATA.description(),
					ex);
			throw exception;
		}
		logger.info("End of saveDeviceType");
		return deviceTypeModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.administration.IAdminDAO#saveExpiryPeriod(com.vwoa
	 * .ecrms.model.administration.ExpiryPeriodModel) This method updates the
	 * expiry period value in the database
	 */
	@Override
	public ExpiryPeriodModel saveExpiryPeriod(
			ExpiryPeriodModel expiryPeriodModel) throws ECRMSException {

		logger.info("Start of saveExpiryPeriod");
		try {
			if(null != expiryPeriodModel){
			//Updating the expiry period value in the database with the value entered by user
	      sqlSession.update(ECRMSConstants.UPDATE_EXPIRY_PERIOD,expiryPeriodModel);
		} 
		}
			//Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of saveExpiryPeriod");
		return expiryPeriodModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.administration.IAdminDAO#saveExpiryNotification(com
	 * .vwoa.ecrms.model.administration.NotificationModel) This method saves the
	 * updates details of Create expiry notofication into the database
	 */
	@Override
	public NotificationModel saveExpiryNotification(
			NotificationModel notificationModel) throws ECRMSException {

		logger.info("Start of saveExpiryNotification");
		try {
			if(null != notificationModel){
			//Updating the details of ExpiryNotification in the database with the values entered by the user
			sqlSession.update(ECRMSConstants.UPDATE_EXPIRY_NOTIFICATION,notificationModel);
		} 
		}
			//Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of saveExpiryNotification");
		return notificationModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.administration.IAdminDAO#saveReceivedNotification(
	 * com.vwoa.ecrms.model.administration.NotificationModel) This method
	 * updates Create Received notification in the database
	 */
	@Override
	public NotificationModel saveReceivedNotification(
			NotificationModel notificationModel) throws ECRMSException {

		logger.info("Start of saveReceivedNotification");
		try {
			if(null != notificationModel){
			
			//Update the received notification details in the database with the values enetered by user
			sqlSession.update(ECRMSConstants.UPDATE_RECEIVED_NOTIFICATION,notificationModel);
		} 
		}
		//Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of saveReceivedNotification");
		return notificationModel;
	}

	

	/**
	 * @return device type model list
	 */
	public List<DeviceTypeModel> getDeviceTypeModelList() {
		return deviceTypeModelList;
	}

	/**
	 * @param deviceTypeModelList
	 */
	public void setDeviceTypeModelList(List<DeviceTypeModel> deviceTypeModelList) {
		this.deviceTypeModelList = deviceTypeModelList;
	}
}
