/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.administration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;

import com.vwoa.ecrms.dao.common.CommonDAOImpl;
import com.vwoa.ecrms.dao.searchcertificaterequest.ISearchCertRequestDAO;
import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchResultCertRequestModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author bhagyashri_ajgare
 *
 */
@Repository
 public class RecoverCertDAOImpl implements IRecoverCertDAO
 {
	 private static final Logger logger = LoggerFactory
		.getLogger(CommonDAOImpl.class);

		@Autowired
		private SqlSession sqlSession;

		
		public void setSqlSession(SqlSession sqlSession) {
			this.sqlSession = sqlSession;
		}
		
				
		/* 
		 * This method is used to implement code to retrieve search result for recover certificates
		 * his method will return List of searchResultCertRequestModel(non-Javadoc)
		 * @see com.vwoa.ecrms.dao.administration.IRecoverCertDAO#searchRecoverCerts(com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List<SearchResultCertRequestModel> searchRecoverCerts(
				SearchCertRequestModel searchCertRequestModel) {
			
			logger.info("Start of searchRecoverCerts(searchCertRequestModel):: "+searchCertRequestModel.getDeviceType());
			List<SearchResultCertRequestModel> certList = new ArrayList<SearchResultCertRequestModel>();
			
			try {
				//Populating the list of the certificates which needs to be reactivated	
				certList = sqlSession.selectList(ECRMSConstants.RETRIEVE_RECOVER_SEARCH_CERT, searchCertRequestModel);
			} 
			//Handling the Exception
			catch (Exception ex) {
				ECRMSException exception = new ECRMSException(
						ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
						ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
						ex);

				throw exception;
			}
			return certList;
		}
 }