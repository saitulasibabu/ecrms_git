/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.service.searchcertrequest;

import java.util.List;

import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchResultCertRequestModel;
import com.vwoa.ecrms.util.exception.ECRMSException;


/**
 * @author bhagyashri_ajgare
 *
 */
public interface ISearchCertRequestService {

	
	/**
	 * This Interface is used to retrieve search result (i.e. List of searchResultCertRequestModel)
	 * of Advanced search, which is using searchCertRequest Model as a parameter.  
	 * 
	 * @param searchCertificateModel - 
	 * @return List<SearchResultCertRequestModel> List of certificates (which is SearchResultCertRequestModel) 
	 * @throws Exception 
	 * @throws ECRMSException 
	 */
	List<SearchResultCertRequestModel> searchCertRequests(SearchCertRequestModel searchCertificateModel) throws ECRMSException, Exception;
	
	/**
	 * This Interface is used to retrieve search result (i.e. List of searchResultCertRequestModel)
	 * of Basic search(or landing page), which is using searchCertRequest Model as a parameter. 
	 * 
	 * @param searchCertificateModel
	 * @return List<SearchResultCertRequestModel> List of certificates (which is SearchResultCertRequestModel)
	 * @throws Exception 
	 * @throws ECRMSException 
	 */
	List<SearchResultCertRequestModel> basicSearchCertRequests(SearchCertRequestModel searchCertificateModel, List<String> roleList, int certActivPeriod, int certRenewPeriod) throws ECRMSException, Exception;
	
	/**
	 * This interface is used to retrieve total count of Advanced SearchResult
	 * @param searchCertRequestModel
	 * @return 
	 * @throws ECRMSException
	 * @throws Exception
	 */
	Integer retrieveCountAdvancedSearch(SearchCertRequestModel searchCertRequestModel) throws ECRMSException,Exception;
	/**
	 * This interface is used to retrieve total count of basic SearchResult
	 * @param searchCertRequestModel
	 * @return 
	 * @throws ECRMSException
	 * @throws Exception
	 */
	List<SearchResultCertRequestModel> retrieveCountBasicSearch(SearchCertRequestModel searchCertRequestModel, List<String> roleList,int certActivPeriod, int certRenewPeriod) throws ECRMSException,Exception;
	
	/**
	 * This interface is used to reactivate list of List
	 * @param searchCertReuestList
	 * @return
	 * @throws Exception 
	 * @throws ECRMSException 
	 */
	Integer reactiveCertRequest(SearchCertRequestModel searchCertRequest) throws ECRMSException, Exception;
	/**
	 * This interface is used to download Certificate for request Number (which is parameter)
	 * @param requestNum - the Request number which is used to download certificate.
	 * @return String
	 */
	String downloadCertificate(Integer requestNum);

	/**
	 * This interface is used to export List of Certificate Search result into excel sheet  
	 * 
	 * @param searchResultCertRequestModel
	 * @param headerList
	 * @return void
	 * @throws Exception 
	 * @throws ECRMSException 
	 */
	void exportCertRequestToExcel(List<SearchResultCertRequestModel> searchResultCertRequestModel, List<String> headerList) throws ECRMSException, Exception;
	
	
	/**
	 * This interface is used to retrieve Dealer Details to display on landing page(Basic Search page) 
	 * if applicable.
	 * @param dealerNum - the Dealer Number against which to fetch Dealer Detail
	 * @return
	 * @throws Exception 
	 */
	public SearchCertRequestModel retrieveDealerDetail(String dealerNum) throws Exception;
}
