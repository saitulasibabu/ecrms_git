/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */

package com.vwoa.ecrms.action;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.vw.security.rbs.RBSApplication;
import com.vw.security.rbs.RBSUser;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.model.dropdown.DropdownModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.security.LoginSecurity;
import com.vwoa.ecrms.service.common.ICommonService;
import com.vwoa.ecrms.service.searchcertrequest.ISearchCertRequestService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.PropertyReader;
import com.vwoa.ecrms.util.UserDetails;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;


/**
 * This class is main class from where application start to load.
 * @author Aparna_Deshmukh01
 *
 */


public class IndexAction extends BaseAction implements ServletRequestAware,SessionAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId; //variable to hold user name
	private String comingFrom;
	private static RBSApplication theAppl = null;
	private Map globals;
	private static final Logger logger = LoggerFactory
	.getLogger(IndexAction.class);	
	private SearchCertRequestModel searchCertRequestModel;	
	@Autowired
	private ISearchCertRequestService searchCertRequestService;
	@Autowired
	private ICommonService commonService;	
	private List<DropdownModel> basicStatusList;	
	private String eshopLink;	
	private HttpServletRequest requestHeader;	
	private Map<String, Object> session;
	// Total pages
	private Integer total = 0;
	 //get how many rows we want to have into the grid - rowNum attribute in the grid
	private Integer rows = 0;
	//Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// All Record
	private Integer records = 0;
	
	/**
	 * Main method of the application.
	 * It does LDAP authentication and authorization
	 * Checks whether user has access the application from UDE or Vinnie 
	 * and loads the application accordingly.
	 * @return LOGIN, user would be redirected to login page
	 * @return SUCCESS, user would be redirected to application landing page
	 * @return unAuthorizedAccess, user would be redirected to unauthorizedAccess.jsp
	 * @return ERROR/INPUT, user would be redirected to login page
	 * 
	 */
	@Action(value="/index", results={
				@Result(name=INPUT, location="login/login.jsp", type="dispatcher"),
				@Result(name=LOGIN, location="login/login.jsp", type="dispatcher"),
			    @Result(name=SUCCESS, location="index.jsp", type="dispatcher"),
			    @Result(name="RELOAD", location="reLoad.jsp", type="dispatcher"),
			    @Result(name=ERROR, location="login/login.jsp", type="dispatcher"),
			    @Result(name="unAuthorizedAccess", location="login/unauthorizedAccess.jsp", type="dispatcher")
			}, interceptorRefs=@InterceptorRef("defaultStack"))
		public String execute() {	
		logger.info("Start of execute method");
		String ssoUserId = null;
		UserProfileModel userModel = null;		
		String retStr = ECRMSConstants.SUCCESS;
		
		// Capture the source from where the application was called.
		String brand = requestHeader.getHeader("vwbrands");
		
		logger.info("Referrer"+requestHeader.getHeader("vwbrands"));
		
		Enumeration en = requestHeader.getHeaderNames();
	      String headerName = null;

	      while(en.hasMoreElements()) {
	         headerName = (String) en.nextElement();
	         logger.debug(headerName + ": "); 
	         logger.debug(requestHeader.getHeader(headerName) + "\n");
	      }
		
			// Code to retrieve user information from request headers.
			ssoUserId = requestHeader.getHeader("iv-user");
	
		
		//Flag to identify a UDE login
		boolean isUDELogin = false;
		
		if(ssoUserId != null && ssoUserId!= "" && !ssoUserId.equalsIgnoreCase("null"))
		{
			userId = requestHeader.getHeader("iv-user");
			
			isUDELogin = true;
			logger.debug(" I have the userid in request header ->> "+ userId);	
			HttpSession session = requestHeader.getSession();
			session.setAttribute("userId",userId);
			String toCheck = (String) session.getAttribute(ECRMSConstants.TO_CHECK);
			logger.debug("toCheck: " + toCheck);
			if(StringUtils.isEmpty(toCheck)) {
				session.setAttribute(ECRMSConstants.FORWARDED_HOST,
						requestHeader.getHeader(ECRMSConstants.FORWARDED_HOST));
				return "RELOAD";
			}
		}
		// End of code to retrieve user information from request headers.

		if(isUDELogin && (null == userId || userId.equalsIgnoreCase(""))){
			// If the request header does not contain userid information when coming from UDE
			// then return to unauthorized page.
			return ECRMSConstants.UN_AUTHORIZED_ACCESS;				
		}else if (!isUDELogin && !StringUtils.equalsIgnoreCase(comingFrom, "Login")){
			return LOGIN;
		}
		else
		{
			// Exclude the entire check for offshore configuration.
			logger.debug(" Environment is"+ globals.get("environment"));
			if(!globals.get("environment").equals("OFFSHORE"))
			{			
				if((String) globals.get("environment") == null){
					logger.info("environment variable is missing");
					addActionError(getText("err.auth.user"));
					retStr =  ERROR;
					return retStr;
				}	
				theAppl = new RBSApplication();			 			 
				theAppl.load("eCRMS");
				
				RBSUser userSec = new RBSUser();	         	         
				logger.debug("userid: "+ userId);
				logger.debug("coming from: " + comingFrom);
				
				if(StringUtils.equalsIgnoreCase(comingFrom, "Login") && !isUDELogin)
				{		
					// If the details are coming from the login screen, then call the ldap authentication.
					//-- LDAP: Username/Password check
					logger.info("Start Authentication");
					boolean authResult = userSec.loadUser();
					logger.info("RBS User authentication for: "+userSec.getUID()+" is: "+ authResult);
					
					if(!authResult) {
						logger.error("Login Passwd is not correct");
						addActionError(getText("err.incorrect.credntial"));
						return ERROR;
					}
					
					if(!theAppl.isAllowed(userSec))
					{
						logger.info("User not authorized");
						addActionError(getText("err.notauth"));
						return ERROR;
					}
					
				}else
				{
					if (!userSec.isAuthenticated(requestHeader)) {
						logger.error("Request header does not confirm authentication");
						if (!theAppl.isAllowed(userSec)) {
							logger.info("User not allowed through UDE. Checking from LDAP.");
							boolean authResult = userSec.loadUser();
							logger.info("UDE authentication for :"
									+ userSec.getUID() + " is :" + authResult);
							if (!authResult) {
								addActionError(getText("err.notauth"));  
								return ECRMSConstants.UN_AUTHORIZED_ACCESS;
							}
						}
					}
				}			
				// Get the required attributes from the LDAP and set it to user model.
					try {
						userModel = LoginSecurity.retrieveUserDetailsFromLDAP(userSec);
						System.out.println("Job Title VW: " + userModel.getVwJobTitle());
					} catch (Exception e) {
						logger.error("Exception occured is : ", e);
						ECRMSException ecrmsException = new ECRMSException(
								ECRMSErrorMessage.Constants.ERR_LOGIN.status(),
								ECRMSErrorMessage.Constants.ERR_LOGIN.description());						
						addActionError(getText(ecrmsException.getDescription()));
						throw ecrmsException;
					}
				session.put(ECRMSConstants.USERPROFILE_KEY, userModel);			
			}else
			{
				userModel=UserDetails.retriveUserDetailsFromLDAP(userId);
				session.put(ECRMSConstants.USERPROFILE_KEY, userModel);
			}
		}
		
		/**If the Login is via UDE and the user is System_Admin then do not allow the user to access the application */
		if(isUDELogin && (userModel.getUserRoleList().contains(ECRMSConstants.SYSTEM_ADMIN) || userModel.getUserRoleList().contains(ECRMSConstants.SUPER_USER))){
			return ECRMSConstants.UN_AUTHORIZED_ACCESS;
		}
		
		//Set the eshop link
		eshopLink = PropertyReader.retrievePropertiesValue("link.eshop");
		
		logger.info("eshopLink "+eshopLink);
		session.put(ECRMSConstants.ESHOP_LINK, eshopLink);	

		ActionContext.getContext().setLocale(userModel.getUserLocale());

		try
		{
			
			
			basicStatusList = commonService.retrieveStatusData(userModel);
			searchCertRequestModel = new SearchCertRequestModel();
			// get dealer number from userModel (LDAP)
			if (StringUtils.isNotBlank(userModel.getDealerNum())) {
				searchCertRequestModel = searchCertRequestService.retrieveDealerDetail(userModel.getDealerNum());
			}
		}		
		catch(ECRMSException ex)
		{
			logger.error("Error occured is ", ex);
			ECRMSException ecrmsException = new ECRMSException(ex
					.getErrorCode(), getText(ex.getDescription()));
			addActionError(getText(ex.getDescription()));
			throw ex;
		} catch (Exception e) {
			logger.error("Error occured is ", e);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description()));
			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}			
		logger.info("End of execute method");					
		return SUCCESS;
	}
	/**
	 * @return the basicStatusList
	 */
	public List<DropdownModel> getBasicStatusList() {
		return basicStatusList;
	}
	/**
	 * @param basicStatusList the basicStatusList to set
	 */
	public void setBasicStatusList(List<DropdownModel> basicStatusList) {
		this.basicStatusList = basicStatusList;
	}
	/**
	 * @return the searchCertRequestModel
	 */
	public SearchCertRequestModel getSearchCertRequestModel() {
		return searchCertRequestModel;
	}
	/**
	 * @param searchCertRequestModel the searchCertRequestModel to set
	 */
	public void setSearchCertRequestModel(
			SearchCertRequestModel searchCertRequestModel) {
		this.searchCertRequestModel = searchCertRequestModel;
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the comingFrom
	 */
	public String getComingFrom() {
		return comingFrom;
	}
	/**
	 * @param comingFrom the comingFrom to set
	 */
	public void setComingFrom(String comingFrom) {
		this.comingFrom = comingFrom;
	}
	//@Autowired
	public void setGlobals(Map globals) {
		logger.debug("inside setGlobals!!! {}", globals);
		this.globals = globals;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.requestHeader = request;	
	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the records
	 */
	public Integer getRecords() {
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(Integer records) {
		this.records = records;
		
		if(this.records > 0 && this.rows > 0){
			this.total = (int)Math.ceil((double) this.records/(double) this.rows);
		}else{
			this.total = 0;
		}
	}
}
