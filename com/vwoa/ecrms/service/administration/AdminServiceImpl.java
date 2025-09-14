/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.service.administration;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.vwoa.ecrms.dao.administration.ArrayList;
import com.vwoa.ecrms.dao.administration.IAdminDAO;
import com.vwoa.ecrms.model.administration.DeviceTypeModel;
import com.vwoa.ecrms.model.administration.ExpiryPeriodModel;
import com.vwoa.ecrms.model.administration.NotificationModel;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Vijay_Bhadoria
 *
 */
@Service
public class AdminServiceImpl implements IAdminService{
	
	private static final Logger logger = LoggerFactory
	.getLogger(AdminServiceImpl.class);
	@Autowired
	private IAdminDAO adminDAO;
	
	

	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.administration.IAdminService#retrieveDeviceType(com.vwoa.ecrms.model.administration.DeviceTypeModel)
	   This method retrieves the device type model from DAO 
	 */
	@Override
	public List<DeviceTypeModel> retrieveDeviceType(DeviceTypeModel deviceTypeModel) throws ECRMSException {

		logger.info("Start of retrieveDeviceType");
		
		return adminDAO.retrieveDeviceType(deviceTypeModel);
		
	}
	

	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.administration.IAdminService#retrieveExpiryPeriod()
	   This method retrieves the expiry period details from dao
	 */
	@Override
	public ExpiryPeriodModel retrieveExpiryPeriod() throws ECRMSException{

		logger.info("Start of retrieveExpiryPeriod");
		return adminDAO.retrieveExpiryPeriod();
	    
	}
	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.administration.IAdminService#retrieveExpiryNotification()
	   This method retrieves expiry notification details from dao
	 */
	@Override
	public NotificationModel retrieveExpiryNotification()throws ECRMSException {

		logger.info("Start of retrieveExpiryNotification ");
		return adminDAO.retrieveExpiryNotification();
	}
	

	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.administration.IAdminService#retrieveReceivedNotification()
	   This method retrieves the received notification from the dao
	 */
	@Override
	public NotificationModel retrieveReceivedNotification() throws ECRMSException {
		logger.info("Start of retrieveReceivedNotification");
		return adminDAO.retrieveReceivedNotification();
	}
	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.administration.IAdminService#saveDeviceType(com.vwoa.ecrms.model.administration.DeviceTypeModel, java.util.ArrayList, java.util.ArrayList)
	   This Method sends the DeviceType Code and Description to DAO
	 */
	@Override
	@Transactional
	public DeviceTypeModel saveDeviceType(DeviceTypeModel deviceTypeModel,ArrayList<String> checkedDeviceCode,ArrayList<String> uncheckedDeviceCode)throws ECRMSException {

		logger.info("Start of saveDeviceType");
		return adminDAO.saveDeviceType(deviceTypeModel,checkedDeviceCode,uncheckedDeviceCode);
	}

	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.administration.IAdminService#saveExpiryPeriod(com.vwoa.ecrms.model.administration.ExpiryPeriodModel)
	   This Method sends the updated Expiry Period to DAO
	 */
	@Override
	@Transactional
	public ExpiryPeriodModel saveExpiryPeriod(ExpiryPeriodModel expiryPeriodModel) throws ECRMSException {

		logger.info("Start of saveExpiryPeriod");
		return adminDAO.saveExpiryPeriod(expiryPeriodModel);
	}
	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.administration.IAdminService#saveExpiryNotification(com.vwoa.ecrms.model.administration.NotificationModel)
	   This Method sends the updated Expired Notification details to DAO
	 */
	@Override
	@Transactional
	public NotificationModel saveExpiryNotification(NotificationModel notificationModel)throws ECRMSException {

		logger.info("Start of saveExpiryNotification");
		return adminDAO.saveExpiryNotification(notificationModel);
	}
	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.administration.IAdminService#saveReceivedNotification(com.vwoa.ecrms.model.administration.NotificationModel)
	   This Method sends the updated Received Notification details to DAO
	 */
	@Override
	@Transactional
	public NotificationModel saveReceivedNotification(NotificationModel notificationModel) throws ECRMSException {

		logger.info("Start of saveReceivedNotification");
		return adminDAO.saveReceivedNotification(notificationModel);
	}
	
	
	
}
