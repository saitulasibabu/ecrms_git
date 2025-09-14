/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vwoa.ecrms.model.dropdown.DropdownModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
@Repository
public class CommonDAOImpl implements ICommonDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(CommonDAOImpl.class);

	@Autowired
	private SqlSession sqlSession;
	
	DropdownModel selectDropdownModel;
	
	DropdownModel allDropdownModel;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public CommonDAOImpl(){
		allDropdownModel = new DropdownModel("", ECRMSConstants.SEARCH_ALL);
		selectDropdownModel= new DropdownModel("", ECRMSConstants.SEARCH_SELECT);
	}

	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.dao.common.ICommonDAO#retrieveDeviceTypeData(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DropdownModel> retrieveDeviceTypeData(String page) throws ECRMSException {
		logger.info("Start of retriveDeviceTypeData()");
		List<DropdownModel> deviceTypeList= new ArrayList<DropdownModel>();
		
		try {
			
		
			
			if(StringUtils.isNotBlank(page)){
				deviceTypeList= sqlSession.selectList(ECRMSConstants.RETRIEVE_DEVICE_DATA);
				
				if(! deviceTypeList.contains(selectDropdownModel)){
					deviceTypeList.add(0,selectDropdownModel);
				}
			}else{
				deviceTypeList= sqlSession.selectList(ECRMSConstants.RETRIEVE_ALL_DEVICE_DATA);
				
				if(! deviceTypeList.contains(allDropdownModel)){
					deviceTypeList.add(0,allDropdownModel);
				}
			}
			
			
		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
        logger.info("End of retriveDeviceTypeData()");
		return deviceTypeList;

	}

	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.dao.common.ICommonDAO#retrieveStatusData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DropdownModel> retrieveStatusData() throws ECRMSException {
		logger.info("Start of retrieveStatusData()");
		List<DropdownModel> statusList= new ArrayList<DropdownModel>();
	
		try {
			statusList= sqlSession.selectList(ECRMSConstants.RETRIEVE_STATUS_DATA);
			
			
			if(! statusList.contains(allDropdownModel)){
				statusList.add(0,allDropdownModel);
			}
			
			
		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of retrieveStatusData()");
		return statusList;

	}

}
