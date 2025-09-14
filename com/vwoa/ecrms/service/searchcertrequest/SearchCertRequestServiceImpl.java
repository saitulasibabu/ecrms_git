/**
 * 
 */
package com.vwoa.ecrms.service.searchcertrequest;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vwoa.ecrms.dao.searchcertificaterequest.ISearchCertRequestDAO;
import com.vwoa.ecrms.helper.searchcertrequest.SearchCertRequestHelper;
import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchResultCertRequestModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
@Service
public class SearchCertRequestServiceImpl implements ISearchCertRequestService {
	private static final Logger logger = LoggerFactory.getLogger(SearchCertRequestServiceImpl.class);
	@Autowired
	private ISearchCertRequestDAO searchCertRequestDAO;

	private SearchCertRequestHelper searchCertRequestHelper;

	/* 
	 * This method is used implement code to download Certificate for request Number
	 * (which is used as a parameter)
	 * (non-Javadoc)
	 * @see com.vwoa.ecrms.service.searchcertrequest.ISearchCertRequestService#downloadCertificate(java.lang.Integer)
	 */
	@Override
	public String downloadCertificate(Integer requestNum) {

		logger.info("Start of downloadCertificate");
		return searchCertRequestDAO.downloadCertificate(requestNum);
	}

	/* 
	 * This method is used implement code to export list of searched records in Excel sheet 
	 * (where List of searchResultCertRequestModel is Parameter)
	 * Also in this implementation exportCertRequestToExcel method of SearchCertRequestHelper class is used to do actual excelsheet 
	 * write operation.
	 *  
	 * (non-Javadoc)
	 * @see com.vwoa.ecrms.service.searchcertrequest.ISearchCertRequestService#exportCertRequestToExcel(java.util.List)
	 */
	@Override
	public void exportCertRequestToExcel(List<SearchResultCertRequestModel> searchResultCertRequestModelList, List<String> headerList) throws ECRMSException, Exception {
		logger.info("Start of exportCertRequestToExcel");
		searchCertRequestHelper = new SearchCertRequestHelper();
		searchCertRequestHelper.exportCertRequestToExcel(searchResultCertRequestModelList,headerList);
		
	}
	/*
	 * This method is used to implement code to retrieve dealer Detail of logged in user(if applicable)
	 * this method will return SearchCertRequestModel for given Dealer Number(which is parameter) 
	 * (non-Javadoc)
	 * @see
	 * com.vwoa.ecrms.service.searchcertrequest.ISearchCertRequestService#retriveDealerDetail
	 * (java.lang.String)
	 */
	@Override
	public SearchCertRequestModel retrieveDealerDetail(String dealerNum)
			throws Exception {
		logger.info("Start of retriveDealerDetail");
		return searchCertRequestDAO.retrieveDealerDetail(dealerNum);
		
	}
	
	public Integer retrieveCountAdvancedSearch(SearchCertRequestModel searchCertModel) throws ECRMSException,Exception
	{
		logger.info("Start of retrieveCountAdvancedSearch method");
		
		Integer searchCount = null;
		searchCertRequestHelper = new SearchCertRequestHelper();
		SearchCertRequestModel searchCertificateModel = searchCertRequestHelper.formatCertRequest(searchCertModel);
		if(StringUtils.isNotBlank(searchCertificateModel.getFromIssueDate()) && StringUtils.isNotBlank(searchCertificateModel.getToIssueDate()))
		{
			if(!(searchCertificateModel.getFromIssueDate().contains(ECRMSConstants.STARTTIMESTAMP) && searchCertificateModel.getToIssueDate().contains(ECRMSConstants.ENDTIMESTAMP)))
			{
				logger.info("from Issue before adding 00:00:00 "+searchCertificateModel.getFromIssueDate());
				logger.info("To Issue before adding 23:59:59 "+searchCertificateModel.getToIssueDate());
				searchCertificateModel.setFromIssueDate(searchCertificateModel.getFromIssueDate()+ECRMSConstants.STARTTIMESTAMP);
				searchCertificateModel.setToIssueDate(searchCertificateModel.getToIssueDate()+ECRMSConstants.ENDTIMESTAMP);
			}
		}
		if(StringUtils.isNotBlank(searchCertificateModel.getFromExpiryDate()) && StringUtils.isNotBlank(searchCertificateModel.getToExpiryDate()))
		{
			if(!(searchCertificateModel.getFromExpiryDate().contains(ECRMSConstants.STARTTIMESTAMP) && searchCertificateModel.getToExpiryDate().contains(ECRMSConstants.ENDTIMESTAMP)))
			{
				logger.info("from expiry before adding 00:00:00 "+searchCertificateModel.getFromExpiryDate());
				logger.info("to expiry before adding 23:59:59 "+searchCertificateModel.getToExpiryDate());
				searchCertificateModel.setFromExpiryDate(searchCertificateModel.getFromExpiryDate()+ECRMSConstants.STARTTIMESTAMP);
				searchCertificateModel.setToExpiryDate(searchCertificateModel.getToExpiryDate()+ECRMSConstants.ENDTIMESTAMP);
			}
		}
		if(StringUtils.isNotBlank(searchCertificateModel.getFromRevokedDate()) && StringUtils.isNotBlank(searchCertificateModel.getToRevokedDate()))
		{
			if(!(searchCertificateModel.getFromRevokedDate().contains(ECRMSConstants.STARTTIMESTAMP) && searchCertificateModel.getToRevokedDate().contains(ECRMSConstants.ENDTIMESTAMP)))
			{
				logger.info("from revoke before adding 00:00:00 "+searchCertificateModel.getFromRevokedDate());
				logger.info("to revoke before adding 23:59:59 "+searchCertificateModel.getToRevokedDate());
				searchCertificateModel.setFromRevokedDate(searchCertificateModel.getFromRevokedDate()+ECRMSConstants.STARTTIMESTAMP);
				searchCertificateModel.setToRevokedDate(searchCertificateModel.getToRevokedDate()+ECRMSConstants.ENDTIMESTAMP);
			}
		}
		searchCount=searchCertRequestDAO.retrieveCountAdvancedSearch(searchCertificateModel);
		
		logger.info("End of retrieveCountAdvancedSearch method");
		
		return searchCount;
		
	}
	/* 
	 * This method is used to implement code to retrieve search result for advanced search
	 * formatsearchCertRequests method of searchCertRequestHelper class is used to format retrieve data (e.g.change Date format to desired format)
	 * This method will return List of searchResultCertRequestModel
	 * (non-Javadoc)
	 * @see com.vwoa.ecrms.service.searchcertrequest.ISearchCertRequestService#searchCertRequests(com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel)
	 */
	public List<SearchResultCertRequestModel> searchCertRequests(
			SearchCertRequestModel searchCertModel) throws ECRMSException, Exception {
		logger.info("Start of searchCertRequests");
		List<SearchResultCertRequestModel> searchResultCertRequestModelList = null;
		searchCertRequestHelper = new SearchCertRequestHelper();
		SearchCertRequestModel searchCertificateModel = searchCertRequestHelper.formatCertRequest(searchCertModel);
		//Add timestamp for advanced search
		if(StringUtils.isNotBlank(searchCertificateModel.getFromIssueDate()) && StringUtils.isNotBlank(searchCertificateModel.getToIssueDate()))
		{
			if(!(searchCertificateModel.getFromIssueDate().contains("00:00:00") && searchCertificateModel.getToIssueDate().contains("23:59:59")))
			{
				searchCertificateModel.setFromIssueDate(searchCertificateModel.getFromIssueDate()+" 00:00:00");
				searchCertificateModel.setToIssueDate(searchCertificateModel.getToIssueDate()+" 23:59:59");
			}
		}
		if(StringUtils.isNotBlank(searchCertificateModel.getFromExpiryDate()) && StringUtils.isNotBlank(searchCertificateModel.getToExpiryDate()))
		{
			if(!(searchCertificateModel.getFromExpiryDate().contains("00:00:00") && searchCertificateModel.getToExpiryDate().contains("23:59:59")))
			{
			searchCertificateModel.setFromExpiryDate(searchCertificateModel.getFromExpiryDate()+" 00:00:00");
			searchCertificateModel.setToExpiryDate(searchCertificateModel.getToExpiryDate()+" 23:59:59");
			}
		}
		if(StringUtils.isNotBlank(searchCertificateModel.getFromRevokedDate()) && StringUtils.isNotBlank(searchCertificateModel.getToRevokedDate()))
		{
			if(!(searchCertificateModel.getFromRevokedDate().contains("00:00:00") && searchCertificateModel.getToRevokedDate().contains("23:59:59")))
			{searchCertificateModel.setFromRevokedDate(searchCertificateModel.getFromRevokedDate()+" 00:00:00");
			searchCertificateModel.setToRevokedDate(searchCertificateModel.getToRevokedDate()+" 23:59:59");
			}
		}
				
		searchResultCertRequestModelList = searchCertRequestDAO.searchCertRequests(searchCertificateModel);
		searchResultCertRequestModelList = searchCertRequestHelper.formatsearchCertRequests(searchResultCertRequestModelList);
		logger.info("End of searchCertRequests");
		return searchResultCertRequestModelList;
	}
	
	public List<SearchResultCertRequestModel> retrieveCountBasicSearch(SearchCertRequestModel searchCertRequestModel, List<String> roleList,int certActivPeriod, int certRenewPeriod) throws ECRMSException,Exception
	{
			logger.info("Start of retrieveCountBasicSearch method");
			Integer basicSearchCount = null;
			List<SearchResultCertRequestModel> searchResultCertRequestModelList = null;
			searchCertRequestHelper = new SearchCertRequestHelper();
			searchResultCertRequestModelList = searchCertRequestDAO.retrieveCountBasicSearch(searchCertRequestModel,roleList);
			searchResultCertRequestModelList = searchCertRequestHelper.formatsearchCertRequests(searchResultCertRequestModelList,certActivPeriod, certRenewPeriod);
			basicSearchCount = searchResultCertRequestModelList.size();
			logger.info("Total records of Basic search "+basicSearchCount);
			logger.info("End of retrieveCountBasicSearch method");
			
			return searchResultCertRequestModelList;
	}
	
	/* 
	 * This method is used to implement code to retrieve search result for basic search(or landing page)
	 * formatsearchCertRequests method of searchCertRequestHelper class is used to format retrieve data (e.g.change Date format to desired format)
	 * This method will return List of searchResultCertRequestModel
	 * (non-Javadoc)
	 * @see com.vwoa.ecrms.service.searchcertrequest.ISearchCertRequestService#basicSearchCertRequests(com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel)
	 */
	public List<SearchResultCertRequestModel> basicSearchCertRequests(SearchCertRequestModel searchCertificateModel,List<String> roleList,int certActivPeriod, int certRenewPeriod) throws ECRMSException, Exception {
		logger.info("Start of basicSearchCertRequests");
		List<SearchResultCertRequestModel> searchResultCertRequestModelList = null;
		searchCertRequestHelper = new SearchCertRequestHelper();
		searchResultCertRequestModelList = searchCertRequestDAO.basicSearchCertRequests(searchCertificateModel,roleList);
		searchResultCertRequestModelList = searchCertRequestHelper.formatsearchCertRequests(searchResultCertRequestModelList,certActivPeriod, certRenewPeriod);
		logger.info("End of basicSearchCertRequests");
		return searchResultCertRequestModelList;
	}
	
	public Integer reactiveCertRequest(SearchCertRequestModel searchCertRequest) throws ECRMSException, Exception
	{
		logger.info("Start of reactiveCertRequest");
		
		
		return searchCertRequestDAO.reactiveCertRequest(searchCertRequest);
	}
	/**
	 * @return the searchCertRequestDAO
	 */
	public ISearchCertRequestDAO getSearchCertRequestDAO() {
		return searchCertRequestDAO;
	}

	/**
	 * @param searchCertRequestDAO
	 *            the searchCertRequestDAO to set
	 */
	public void setSearchCertRequestDAO(
			ISearchCertRequestDAO searchCertRequestDAO) {
		this.searchCertRequestDAO = searchCertRequestDAO;
	}

	/**
	 * @return the searchCertRequestHelper
	 */
	public SearchCertRequestHelper getSearchCertRequestHelper() {
		return searchCertRequestHelper;
	}

	/**
	 * @param searchCertRequestHelper
	 *            the searchCertRequestHelper to set
	 */
	public void setSearchCertRequestHelper(
			SearchCertRequestHelper searchCertRequestHelper) {
		this.searchCertRequestHelper = searchCertRequestHelper;
	}

}
