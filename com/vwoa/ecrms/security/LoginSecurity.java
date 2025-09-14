/**
 * 
 */
package com.vwoa.ecrms.security;


import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vw.security.rbs.RBSUser;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.PropertyReader;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;


/**
 * @author guptaan
 *
 */
public class LoginSecurity {

	private static final Logger logger = LoggerFactory
	.getLogger(LoginSecurity.class);

	/**
	 * This method sets the required user details from LDAP to the user profile object.
	 * @param userSec
	 * @return UserProfileModel
	 * @throws Exception 
	 */
	public static UserProfileModel retrieveUserDetailsFromLDAP(RBSUser userSec) throws ECRMSException {


		logger.info("Retrieving user details from LDAP");
		UserProfileModel userProfileModel = new UserProfileModel();
		try {
			// Set userid
			userProfileModel.setUserId(userSec.getUID());
			// Set brand
			userProfileModel.setUserBrand(userSec.getAttrValue("vwprimarybrand"));
			// Set First Name
			userProfileModel.setUserFName(userSec.getAttrValue("givenname"));
			// Set Email
			userProfileModel.setEmail(userSec.getAttrValue("mail"));
			// Set user country
			userProfileModel.setUserCountry(userSec.getAttrValue("CNTY"));
			//set job title
			userProfileModel.setVwJobTitle(userSec.getAttrValue("vwjobtitle"));
			// Get object class attributes
			/*String[] objectClass = userSec.getAttrValues("objectClass");
			List objectList = Arrays.asList(objectClass);*/
			List<String> userRoleList = userSec.getRoles();
			logger.info("LDAP Roles for: "+userSec.getUID()+" : "+userRoleList);
			if(userRoleList.contains(ECRMSConstants.DEALER_USER))
			{
				userProfileModel.setDealer(true);
			}
			userProfileModel.setUserRoleList(userRoleList);

			// Set the user language and the locale
			if(StringUtils.equalsIgnoreCase(userSec.getAttrValue("preferredLanguage"), "EN"))
			{
				userProfileModel.setUserLanguage(userSec.getAttrValue("preferredLanguage"));
				userProfileModel.setUserLocale(Locale.ENGLISH);
			}else if(StringUtils.equalsIgnoreCase(userSec.getAttrValue("preferredLanguage"), "FR"))
			{		
				userProfileModel.setUserLanguage(userSec.getAttrValue("preferredLanguage"));
				userProfileModel.setUserLocale(Locale.FRENCH);
			}else
			{
				userProfileModel.setUserLanguage("EN");
				userProfileModel.setUserLocale(Locale.ENGLISH);
			}
			// Set the contact Email
			userProfileModel.setContactEmail(userSec.getAttrValue("mail"));
			// Set the contact phone number
			userProfileModel.setContactPhnNum(userSec.getAttrValue("telephoneNumber"));

			if(userProfileModel.isDealer())
			{
				userProfileModel.setDealerNum(userSec.getAttrValue("dealercode"));	
				// 1. Search the supervisor email
				//Commenting code -- Supervisor email is not used 
				//searchSupervisorEmail(userProfileModel.getDealerNum());
				// 2. Search the dealer Phone Number
				userProfileModel.setDealerPhnNum(searchDlrPhnNo(userProfileModel.getDealerNum()));
			}
		}catch(Exception ex)
		{		 
			ex.printStackTrace();
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_LOGIN.status(),
					ECRMSErrorMessage.Constants.ERR_LOGIN.description());						
			throw ecrmsException;
		}
		return userProfileModel;
	}


	/**
	 * This method will retrieve the dealership phone number of the logged in user(dealer) from the LDAP directory
	 * using LDAP search
	 * @param dlrNum
	 *            the dealer code against to fetch the dealership phone number.
	 * @return the map which holds the user details.
	 * @throws Exception 
	 */
	public static String searchDlrPhnNo(String dlrNum) throws ECRMSException {


		String queryString = "vwdid="+dlrNum+"*";
//		String queryString = "(&(vwdid="+dlrNum+"*)(dealerstatuscode=active))";
		//queryString = "vwdid=405A11AU";
		//ou=vwoa,dc=vw,dc=com
		try {
			String base = "ou=Dealerships,ou=dealers,ou=vwoa,dc=vw,dc=com";

			//Set a default number
			String phNo="";
			
			if(!retrieveEnvironment().equalsIgnoreCase("OFFSHORE")){

				NamingEnumeration enumeration = prepareLDAPSearchContext(base, queryString);
				
				while (enumeration.hasMore()) {
					javax.naming.directory.SearchResult result = (javax.naming.directory.SearchResult) enumeration.next();
					Attributes attribs = result.getAttributes();
					NamingEnumeration values = ((BasicAttribute) attribs.get("telephoneNumber")).getAll();
					NamingEnumeration status = ((BasicAttribute) attribs.get("dealerstatuscode")).getAll();
					//IM57523980 : Modify the code so that only active dealer will fetch from LDAP
					while (values.hasMore() && status.hasMore()) {
					  if("ACTIVE".equalsIgnoreCase(status.next().toString())){
						phNo = values.next().toString();
					  }
					}
				}  
			}
			
			logger.info("DealershipPh# -> "+phNo);
			return phNo;
		}catch(Exception ex)
		{		 
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_LOGIN.status(),
					ECRMSErrorMessage.Constants.ERR_LOGIN.description());						
			throw ecrmsException;
		}
	}

	/**
	 * This method will search the supervisor e-mail of the logged in user(dealer) from the LDAP directory
	 * using LDAP search
	 * @param dlrNum
	 *            the dealer number against to fetch the supervisor email.
	 * @return the map which holds the user details.
	 * @throws Exception 
	 */
	public static String searchSupervisorEmail(String dlrNum) throws Exception {
		//get jobtitle from properties file
		String jobtitle= PropertyReader.retrievePropertiesValue(ECRMSConstants.LDAP_JOBTITLE);
		
		String queryString = "(&(dealercode="+dlrNum+")(vwjobtitle="+jobtitle+"))";

		//-- Onsite setting
		String base ="ou=vwoa,dc=vw,dc=com";
		String superMail="test@domain.com";
		try
		{
		if(!retrieveEnvironment().equalsIgnoreCase("OFFSHORE")){
			NamingEnumeration enumeration = prepareLDAPSearchContext(base, queryString);	
			
			while (enumeration.hasMore()) {
				javax.naming.directory.SearchResult result = (javax.naming.directory.SearchResult) enumeration.next();
				Attributes attribs = result.getAttributes();
				NamingEnumeration values = ((BasicAttribute) attribs.get("mail")).getAll();
				while (values.hasMore()) {				
					superMail = values.next().toString();
				}
			} 
		}
		}
		catch (NullPointerException  npe) {
			// TODO: handle exception
			npe.printStackTrace();
			logger.info("Null for email , setting Default supervisor mail to test@domain.com");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		logger.info("Supervisor Email -> "+superMail);
		return superMail;
	}

	/**
	 * This method is used to retrieve  the environment value based on the property passed
	 * @param None
	 * @return The environment string set in WAS as environment variable.
	 * @throws Exception 
	 */
	public static String retrieveEnvironment() throws Exception{
		//-- Get environment from WAS
		String RUNTIME_ENV = System.getProperty("env.was.ecrms");
		logger.debug("Environment is :"+RUNTIME_ENV);
		return RUNTIME_ENV;
	}


	/**
	 * This method is used to retrieve the LDAP url string from the properties file
	 * based on the environment value
	 * @return
	 */
	public static String retrieveLDAPUrl(String env)throws Exception{
		ResourceBundle resource;
		String ldapHost="";
		try{
			ldapHost=PropertyReader.retrievePropertiesValue(ECRMSConstants.LDAP_URL);
			logger.debug("LDAP HOST FOUND AND SET "+ldapHost);
		}
		catch(Exception e){
			logger.debug("Error occured while getting the resource bundle.", e);
			ldapHost = "dirsec-readonly-prod.vwoa.na.vwg:636";
		}
		return ldapHost;
	}

	/**
	 * This method is used to prepare the LDAP search context for base search group
	 * and returns the searched result based on the filter passed
	 * @return
	 */

	public static NamingEnumeration prepareLDAPSearchContext(String base, String queryString) throws Exception{	

		String ldapHost = retrieveLDAPUrl(retrieveEnvironment());
		StringBuffer output = new StringBuffer();
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldaps://"+ldapHost);
		DirContext context = new InitialDirContext(env);
		SearchControls ctrl = new SearchControls();
		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);

		//String searchBase = "ou=vwoa,dc=vw,dc=com" + base;
		NamingEnumeration enumeration = context.search(base, queryString, ctrl);

		return enumeration;
	}


	
}
