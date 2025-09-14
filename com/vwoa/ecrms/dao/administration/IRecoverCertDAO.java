/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.dao.administration;

import java.util.List;

import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchResultCertRequestModel;

/**
 * @author bhagyashri_ajgare
 *
 */
public interface IRecoverCertDAO{
	
	/**
	 *  This Interface is used to retrieve search result (i.e. List of searchResultCertRequestModel)
	 * for recoverCert.jsp, which is using searchCertRequest Model as a parameter.  
	 * 
	 * @param searchCertificateModel - 
	 * @return List<SearchResultCertRequestModel> List of certificates (which is SearchResultCertRequestModel)
	 */
	List<SearchResultCertRequestModel> searchRecoverCerts(SearchCertRequestModel searchCertRequestModel);
	
	
}