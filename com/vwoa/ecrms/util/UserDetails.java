/**
 * 
 */
package com.vwoa.ecrms.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vwoa.ecrms.model.common.UserProfileModel;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class UserDetails {
	
	private static final Logger logger = LoggerFactory
	.getLogger(UserDetails.class);

	
	
	/**
	 * Method to retrieve user details from LDAP
	 */
	public static UserProfileModel retriveUserDetailsFromLDAP(String userId) {
		UserProfileModel userProfileModel = new UserProfileModel();
		List<String> userRoleList = new ArrayList<String>();

		if (userId.equalsIgnoreCase(ECRMSConstants.DEALER_USER)) {
			userProfileModel.setUserId(ECRMSConstants.DEALER_USER);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.ENGLISH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.ENGLISH);
			userRoleList.add(ECRMSConstants.DEALER_USER);//dealer user
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setDealerNum("426077");
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}else if (userId.equalsIgnoreCase(ECRMSConstants.CORPORATE_USER)) {
			userProfileModel.setUserId(ECRMSConstants.CORPORATE_USER);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.ENGLISH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.ENGLISH);
			userRoleList.add(ECRMSConstants.CORPORATE_USER);//corporate user
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}
		else if (userId.equalsIgnoreCase(ECRMSConstants.SYSTEM_ADMIN)) {
			userProfileModel.setUserId(ECRMSConstants.SYSTEM_ADMIN);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.ENGLISH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.ENGLISH);
			userRoleList.add(ECRMSConstants.SYSTEM_ADMIN);
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}
		else if (userId.equalsIgnoreCase(ECRMSConstants.SUPER_USER)) {
			userProfileModel.setUserId(ECRMSConstants.SUPER_USER);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.ENGLISH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.ENGLISH);
			userRoleList.add(ECRMSConstants.SUPER_USER);
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}else if (userId.equalsIgnoreCase(ECRMSConstants.REVIEWER_USER)) {
			userProfileModel.setUserId(ECRMSConstants.REVIEWER_USER);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.ENGLISH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.ENGLISH);
			userRoleList.add(ECRMSConstants.REVIEWER_USER);
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}else if (userId.equalsIgnoreCase(ECRMSConstants.DEALER_USER_FR)) {
			userProfileModel.setUserId(ECRMSConstants.DEALER_USER_FR);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.FRENCH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.FRENCH);
			userRoleList.add(ECRMSConstants.DEALER_USER);//dealer user
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setDealerNum("426077");
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}
		else if (userId.equalsIgnoreCase(ECRMSConstants.CORPORATE_USER_FR)) {
			userProfileModel.setUserId(ECRMSConstants.CORPORATE_USER_FR);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.FRENCH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.FRENCH);
			userRoleList.add(ECRMSConstants.CORPORATE_USER);//corporate user
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}else if (userId.equalsIgnoreCase(ECRMSConstants.SYSTEM_ADMIN_FR)) {
			userProfileModel.setUserId(ECRMSConstants.SYSTEM_ADMIN_FR);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.FRENCH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.FRENCH);
			userRoleList.add(ECRMSConstants.SYSTEM_ADMIN);
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}else if (userId.equalsIgnoreCase(ECRMSConstants.SUPER_USER_FR)) {
			userProfileModel.setUserId(ECRMSConstants.SUPER_USER_FR);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.FRENCH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.FRENCH);
			userRoleList.add(ECRMSConstants.SUPER_USER);
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}else if (userId.equalsIgnoreCase(ECRMSConstants.REVIEWER_USER_FR)) {
			userProfileModel.setUserId(ECRMSConstants.REVIEWER_USER_FR);
			userProfileModel.setUserCountry(ECRMSConstants.COUNTRY_USA);
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage(ECRMSConstants.FRENCH_LANGUAGE);
			userProfileModel.setUserLocale(Locale.FRENCH);
			userRoleList.add(ECRMSConstants.REVIEWER_USER);
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}else if (userId.equalsIgnoreCase("PSGTT_VW_USA_ADMIN")) {
			userProfileModel.setUserId(ECRMSConstants.SYSTEM_ADMIN);
			userProfileModel.setUserCountry("USA");
			userProfileModel.setDealerPhnNum("1-24444448888");
			userProfileModel.setUserLanguage("EN");
			userProfileModel.setUserLocale(Locale.ENGLISH);
			userRoleList.add(ECRMSConstants.SYSTEM_ADMIN);
			userRoleList.add(ECRMSConstants.SUPER_USER);
			userProfileModel.setUserRoleList(userRoleList);
			userProfileModel.setContactEmail("ecrms_to@test.com");
			userProfileModel.setContactPhnNum("1-24444444444");

		}
		else{
			userProfileModel.setUserId(null);
		}

		return userProfileModel;
	}
	
	

}
