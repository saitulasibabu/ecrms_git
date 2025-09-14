/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.searchcertificaterequest;

import java.util.List;

import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchResultCertRequestModel;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author aparna_deshmukh01
 * 
 */
public interface ISearchCertRequestDAO {

	/**
	 *  This Interface is used to retrieve search result (i.e. List of searchResultCertRequestModel)
	 * of Advanced search, which is using searchCertRequest Model as a parameter.  
	 * 
	 * @param searchCertificateModel - 
	 * @return List<SearchResultCertRequestModel> List of certificates (which is SearchResultCertRequestModel)
	 * @throws Exception 
	 * @throws ECRMSException 
	 */
	List<SearchResultCertRequestModel> searchCertRequests(SearchCertRequestModel searchCertRequestModel) throws ECRMSException, Exception;
	
	/**
	 * 
	 * This interface is used to retrieve Count for advanced search result
	 * @param searchCertRequestModel
	 * @return
	 * @throws ECRMSException
	 * @throws Exception
	 */
	Integer retrieveCountAdvancedSearch(SearchCertRequestModel searchCertRequestModel) throws ECRMSException, Exception;
	
	/**
	 * 
	 * This interface is used to retrieve Count for basic search result
	 * @param searchCertRequestModel
	 * @return
	 * @throws ECRMSException
	 * @throws Exception
	 */
	List<SearchResultCertRequestModel> retrieveCountBasicSearch(SearchCertRequestModel searchCertRequestModel, List<String> roleList) throws ECRMSException, Exception;
	
	/**
	 *	This Interface is used to retrieve search result (i.e. List of searchResultCertRequestModel)
	 * of Basic search(or landing page), which is using searchCertRequest Model as a parameter. 
	 * 
	 * @param searchCertificateModel
	 * @return List<SearchResultCertRequestModel> List of certificates (which is SearchResultCertRequestModel)
	 * @throws Exception 
	 * @throws ECRMSException 
	 */
	List<SearchResultCertRequestModel> basicSearchCertRequests(SearchCertRequestModel searchCertRequestModel,List<String> roleList) throws ECRMSException, Exception;
	
	/**
	 * @param searchCertRequestList
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
	 * This interface is used to retrieve Dealer Details to display on landing page(Basic Search page) 
	 * if applicable.
	 * @param dealerNum - the Dealer Number against which to fetch Dealer Detail
	 * @return SearchCertRequestModel the model having dealer details'
	 * @throws ECRMSException
	 * @throws Exception 
	 */

	SearchCertRequestModel retrieveDealerDetail(String dealerNum)
			throws ECRMSException, Exception;
}
