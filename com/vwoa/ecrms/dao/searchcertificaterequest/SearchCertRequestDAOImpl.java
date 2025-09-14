/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.searchcertificaterequest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.vwoa.ecrms.dao.common.CommonDAOImpl;
import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchResultCertRequestModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author aparna_deshmukh01
 * 
 */
@Repository
public class SearchCertRequestDAOImpl implements ISearchCertRequestDAO {
	
	private static final Logger logger = LoggerFactory
	.getLogger(CommonDAOImpl.class);

	@Autowired
	private SqlSession sqlSession;

	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/* 
	 * This method is used to implement code to retrieve search result for advanced search
	 * formatsearchCertRequests method of searchCertRequestHelper class is used to format retrieve data (e.g.change Date format to desired format)
	 * This method will return List of searchResultCertRequestModel
	 * (non-Javadoc)
	 * @see com.vwoa.ecrms.dao.searchcertificaterequest.ISearchCertRequestDAO#searchCertRequests(com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SearchResultCertRequestModel> searchCertRequests(
			SearchCertRequestModel searchCertRequestModel) throws ECRMSException, Exception {
		
		logger.info("Start of searchCerReuests(searchCertRequestModel):: "+searchCertRequestModel.getDeviceType());
		List<SearchResultCertRequestModel> certList = new ArrayList<SearchResultCertRequestModel>();
		
		try {
			Integer offset = searchCertRequestModel.getOffset();
			Integer limit = searchCertRequestModel.getLimit();
			logger.info(offset+" ->offset and "+limit+" -> limit");
			if(offset != null && limit != null)
			{
				RowBounds rowBound= new RowBounds(offset,limit);
				certList = sqlSession.selectList(ECRMSConstants.RETRIEVE_SEARCH_CERT_RESULT, searchCertRequestModel,rowBound);	
			}
			else
			{	
				certList = sqlSession.selectList(ECRMSConstants.RETRIEVE_SEARCH_CERT_RESULT, searchCertRequestModel);
			}
			logger.info("total certlist fetched for advanced search are : "+certList.size());
		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		return certList;
	}
	
	public Integer retrieveCountAdvancedSearch(SearchCertRequestModel searchCertRequestModel) throws ECRMSException, Exception
	{
		logger.info("Start of retrieveCountAdvancedSearch");
		Integer searchCount = null;
		try
		{
			searchCount = (Integer)sqlSession.selectOne(ECRMSConstants.RETRIEVE_COUNT_SEARCH_CERT_RESULT, searchCertRequestModel);
		}
		catch(Exception ex)
		{
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		return searchCount;
	}
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.dao.searchcertificaterequest.ISearchCertRequestDAO#retrieveCountBasicSearch(com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<SearchResultCertRequestModel> retrieveCountBasicSearch(SearchCertRequestModel searchCertRequestModel, List<String> roleList) throws ECRMSException, Exception
	{
		logger.info("Start of retrieveCountBasicSearch");
		List<SearchResultCertRequestModel> certList = new ArrayList<SearchResultCertRequestModel>();
		
		try {
				if(!(roleList.contains(ECRMSConstants.SYSTEM_ADMIN)) )
				{
					certList = sqlSession.selectList(ECRMSConstants.RETRIEVE_BASIC_SEARCH_CERT_RESULT,searchCertRequestModel);
				}
				else
				{
					certList = sqlSession.selectList(ECRMSConstants.RETRIEVE_BASIC_SEARCH_CERT_RESULT_ADMIN,searchCertRequestModel);
				}
			} catch (Exception ex) {
				ECRMSException exception = new ECRMSException(
						ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
						ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
						ex);

				throw exception;
			}
			logger.info("Total records of Basic search "+certList.size());
			
		return certList;
	}
	/* 
	 * This method is used to implement code to retrieve search result for basic search(or landing page)
	 * formatsearchCertRequests method of searchCertRequestHelper class is used to format retrieve data (e.g.change Date format to desired format)
	 * This method will return List of searchResultCertRequestModel
	 * 
	 * (non-Javadoc)
	 * @see com.vwoa.ecrms.dao.searchcertificaterequest.ISearchCertRequestDAO#basicSearchCertRequests(com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SearchResultCertRequestModel> basicSearchCertRequests(
			SearchCertRequestModel searchCertRequestModel, List<String> roleList) throws ECRMSException, Exception {
		
		logger.info("Start of basicSearchCertRequests(searchCertRequestModel):: "+searchCertRequestModel.getDeviceType());
		List<SearchResultCertRequestModel> certList = new ArrayList<SearchResultCertRequestModel>();
		
		try {
			Integer offset = searchCertRequestModel.getOffset();
			Integer limit = searchCertRequestModel.getLimit();
			logger.info(offset+" ->offset and "+limit+" -> limit");
			
			if(!(roleList.contains(ECRMSConstants.SYSTEM_ADMIN)))
			{
				if(offset != null && limit != null)
				{
					RowBounds rowBound= new RowBounds(offset,limit);
					certList= sqlSession.selectList(ECRMSConstants.RETRIEVE_BASIC_SEARCH_CERT_RESULT, searchCertRequestModel,rowBound);	
				}
			}	
			else
			{
				if(offset != null && limit != null)
				{
					RowBounds rowBound= new RowBounds(offset,limit);
					certList=sqlSession.selectList(ECRMSConstants.RETRIEVE_BASIC_SEARCH_CERT_RESULT_ADMIN,searchCertRequestModel,rowBound);
				}
			}
			
		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		return certList;
	}
	
	/* (non-Javadoc)
	 * @see com.vwoa.ecrms.dao.searchcertificaterequest.ISearchCertRequestDAO#reactiveCertRequest(java.util.List)
	 */
	public Integer reactiveCertRequest(SearchCertRequestModel searchCertRequest)
	throws ECRMSException, Exception {
		logger.info("Start of reactiveCertRequest");
		Integer count=0;
		try{
			count = sqlSession.update(ECRMSConstants.UPDATE_CERT_ACTIVATION_DTE, searchCertRequest);
		}
		catch(Exception ex)
		{
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.description(),
					ex);
			throw exception;
		}
		logger.info("End of reactiveCertRequest");
		return count;
	}
	/* 
	 * This method is used implement code to download Certificate for request Number
	 * (which is used as a parameter)
	 * (non-Javadoc)
	 * @see com.vwoa.ecrms.dao.searchcertificaterequest.ISearchCertRequestDAO#downloadCertificate(java.lang.Integer)
	 */
	@Override
	public String downloadCertificate(Integer requestNum) {

		return "";
	}
	/*
	 * This method is used to implement code to retrieve dealer Detail of logged in user(if applicable)
	 * this method will return SearchCertRequestModel for given Dealer Number(which is parameter)
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.searchcertificaterequest.ISearchCertRequestDAO#retriveDealerDetail(java.lang.String)
	 */
	@Override
	public SearchCertRequestModel retrieveDealerDetail(String dealerNumber)
	throws ECRMSException, Exception {
		logger.info("Start of  retrieveDealerDetails()");
		SearchCertRequestModel searchCertRequestModel = new SearchCertRequestModel();
		try {

			searchCertRequestModel = (SearchCertRequestModel) sqlSession.selectOne(
					ECRMSConstants.RETRIVE_DEALER_DETAIL_DATA, dealerNumber);

		} catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  retrieveDealerDetails()");
		return searchCertRequestModel;
	}

}
