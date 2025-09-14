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


import com.vwoa.ecrms.model.administration.DeviceTypeModel;
import com.vwoa.ecrms.model.administration.ExpiryPeriodModel;
import com.vwoa.ecrms.model.administration.NotificationModel;


/**
 * @author Vijay_Bhadoria
 *
 */
public interface IAdminService {
	
	/**
	 * This method retrieves the device type from the database.
	 * 
	 * @param deviceTypeModel The model is sent to the method for sorting
 	 * @return deviceTypeModel	The list of device type models
	 * @throws ECRMS Exception
	 */
	List<DeviceTypeModel> retrieveDeviceType(DeviceTypeModel deviceTypeModel);
	
	/**
	 * This method retrieves the expiry Period from the database.
	 * @return expiryPeriodModel	The model for expiry period details
	 * @throws ECRMS Exception
	 */
	ExpiryPeriodModel retrieveExpiryPeriod();
	
	/**
	 * This method retrieves the details of Certificate Expiry Notification from the database.
	 * @return NotificationModel   The model for notification details
	 * @throws ECRMS Exception
	 */
	NotificationModel retrieveExpiryNotification();
	
	/**
	 * This method retrieves the details of Certificate Create Notification from the database.
	 * @return NotificationModel   The model for notification details
	 * @throws ECRMS Exception
	 */
	NotificationModel retrieveReceivedNotification();
	
	/**
	 * This method saves the new device type and update the active indicator status of device type.	 * @return NotificationModel   The model for notification details
	 * @param deviceTypeModel The model for saving the new device type
	 * @param checkedDeviceCode The list of all device codes checked by the user
	 * @param uncheckedDeviceCode The list of all device codes unchecked by the user
	 * @return deviceTypeModel The model for device type list
	 * @throws ECRMS Exception
	 * 
	 */
	DeviceTypeModel saveDeviceType(DeviceTypeModel deviceTypeModel,ArrayList<String> checkedDeviceCode,ArrayList<String> uncheckedDeviceCode);
	
	/**
	 * This method retrieves the updates the value of expiry period into the database.
	 * @return expiryPeriodModel   The model for Expiry period details
	 * @throws ECRMS Exception
	 */
	ExpiryPeriodModel saveExpiryPeriod(ExpiryPeriodModel expiryPeriodModel);
	

	/**
	 * This method retrieves the updates the Create expiry notification details into the database.
	 * @return NotificationModel   The model for Notification  details
	 * @throws ECRMS Exception
	 */
	NotificationModel saveExpiryNotification(NotificationModel notificationModel);
	
	/**
	 * This method retrieves the updates the Create create notification details into the database.
	 * @return NotificationModel   The model for Notification  details
	 * @throws ECRMS Exception
	 */
	NotificationModel saveReceivedNotification(NotificationModel notificationModel);
	
	
	
}
