/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.service.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vwoa.ecrms.dao.common.ICommonDAO;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.model.dropdown.DropdownModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
@Service
public class CommonServiceImpl implements ICommonService {

	private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Autowired
	private ICommonDAO commonDAO;

	DropdownModel emptyDropdownModel;
	
	public CommonServiceImpl()
	{
		emptyDropdownModel = new DropdownModel("~", ECRMSConstants.SEARCH_SELECT);
	}
	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.common.ICommonService#retrieveDeviceTypeData(java.lang.String)
	 */
	@Override
	public List<DropdownModel> retrieveDeviceTypeData(String page) throws ECRMSException {
        logger.info("Start of retrieveDeviceTypeData");
		return commonDAO.retrieveDeviceTypeData(page);
	}

	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.common.ICommonService#retrieveStatusData(com.vwoa.ecrms.model.common.UserProfileModel)
	 */
	@Override
	public List<DropdownModel> retrieveStatusData(UserProfileModel user) throws ECRMSException , Exception{
		
		logger.info("Start of retrieveStatusData");
		List<DropdownModel> statusList= null;
		statusList = commonDAO.retrieveStatusData();
		List<String> userRoleList =user.getUserRoleList();
		List<DropdownModel> updatedStatusList = new ArrayList<DropdownModel>();
		if(!userRoleList.contains(ECRMSConstants.SYSTEM_ADMIN))
		{
			
			DropdownModel toberemain= null;
			if((userRoleList.contains(ECRMSConstants.CORPORATE_USER))|| (userRoleList.contains(ECRMSConstants.REVIEWER_USER)) || (userRoleList.contains(ECRMSConstants.SUPER_USER)))
			{
				if(! updatedStatusList.contains(emptyDropdownModel)){
					updatedStatusList.add(0,emptyDropdownModel);
				}
			}
			
			for(DropdownModel dropdownModel: statusList)
			{
				toberemain = dropdownModel;
				if(!StringUtils.equals(dropdownModel.getKey(), ECRMSConstants.CLOSE) && !StringUtils.equals(dropdownModel.getKey(), ECRMSConstants.REVOKED) )
				{
					updatedStatusList.add(toberemain);
				}
			}
		}
		else
		{
				DropdownModel toberemain= null;
				for(DropdownModel dropdownModel: statusList)
				{
					toberemain = dropdownModel;
					if(!StringUtils.equals(dropdownModel.getKey(), ECRMSConstants.CLOSE))
					{
						updatedStatusList.add(toberemain);
					}
				}
				if(! updatedStatusList.contains(emptyDropdownModel)){
					updatedStatusList.add(0,emptyDropdownModel);
				}
		}
		logger.info("End of retrieveStatusData");
		return updatedStatusList;
	}
	
	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.service.common.ICommonService#retrieveStatusData()
	 */
	@Override
	public List<DropdownModel> retrieveStatusData() throws ECRMSException {
		logger.info("Start of retrieveStatusData");
		
		List<DropdownModel> statusList= null;
		statusList = commonDAO.retrieveStatusData();
		
		logger.info("End of retrieveStatusData");
		return statusList;
	}

	/**
	 * @return the commonDAO
	 */
	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	/**
	 * @param commonDAO
	 *            the commonDAO to set
	 */
	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
}
