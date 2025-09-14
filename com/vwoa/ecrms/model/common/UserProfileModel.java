/**
 * 
 */
package com.vwoa.ecrms.model.common;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class UserProfileModel extends BaseModel {

	/**
		 * 
		 */
	private static final long serialVersionUID = 900231169112727060L;

	private String userCountry="";//Variable to hold the user Country
	private String userBrand="";//Variable to hold the user Brand
	private Date userloginDate;//Variable to hold the user Login Date
	private Timestamp userLoginTime;//Variable to hold the user Login time
	private String userLanguage="";//Variable to hold the user Language
	private String dealerNum="";//Variable to hold the Dealer Number
	private String contactPhnNum="";//Variable to hold the contact phone number
	private String dealerPhnNum="";//Variable to hold the dealer phone number
	private String contactEmail="";//Variable to hold the contact Email of the user
	private Locale userLocale;//Variable to hold the Locale of the user
	private List<String> userRoleList;//Variable to hold the user Role List
	private String userFName="";//Variable to hold the userF name
	private String email="";//Variable to hold the user E-mail
	private boolean dealer;//Flag for the dealer
	private String vwJobTitle="";//Variable to hold the vw job title

	

	/**
	 * @return the userCountry
	 */
	public String getUserCountry() {
		return userCountry;
	}

	/**
	 * @param userCountry
	 *            the userCountry to set
	 */
	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	/**
	 * @return the userBrand
	 */
	public String getUserBrand() {
		return userBrand;
	}

	/**
	 * @param userBrand
	 *            the userBrand to set
	 */
	public void setUserBrand(String userBrand) {
		this.userBrand = userBrand;
	}

	/**
	 * @return the userloginDate
	 */
	public Date getUserloginDate() {
		return userloginDate;
	}

	/**
	 * @param userloginDate
	 *            the userloginDate to set
	 */
	public void setUserloginDate(Date userloginDate) {
		this.userloginDate = userloginDate;
	}

	/**
	 * @return the userLoginTime
	 */
	public Timestamp getUserLoginTime() {
		return userLoginTime;
	}

	/**
	 * @param userLoginTime
	 *            the userLoginTime to set
	 */
	public void setUserLoginTime(Timestamp userLoginTime) {
		this.userLoginTime = userLoginTime;
	}

	/**
	 * @return the userLanguage
	 */
	public String getUserLanguage() {
		return userLanguage;
	}

	/**
	 * @param userLanguage
	 *            the userLanguage to set
	 */
	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	/**
	 * @return the dealerNum
	 */
	public String getDealerNum() {
		return dealerNum;
	}

	/**
	 * @param dealerNum
	 *            the dealerNum to set
	 */
	public void setDealerNum(String dealerNum) {
		this.dealerNum = dealerNum;
	}

	/**
	 * @return the contactPhnNum
	 */
	public String getContactPhnNum() {
		return contactPhnNum;
	}

	/**
	 * @param contactPhnNum
	 *            the contactPhnNum to set
	 */
	public void setContactPhnNum(String contactPhnNum) {
		this.contactPhnNum = contactPhnNum;
	}

	/**
	 * @return the dealerPhnNum
	 */
	public String getDealerPhnNum() {
		return dealerPhnNum;
	}

	/**
	 * @param dealerPhnNum
	 *            the dealerPhnNum to set
	 */
	public void setDealerPhnNum(String dealerPhnNum) {
		this.dealerPhnNum = dealerPhnNum;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail
	 *            the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the userLocale
	 */
	public Locale getUserLocale() {
		return userLocale;
	}

	/**
	 * @param userLocale
	 *            the userLocale to set
	 */
	public void setUserLocale(Locale userLocale) {
		this.userLocale = userLocale;
	}

	/**
	 * @return the userRoleList
	 */
	public List<String> getUserRoleList() {
		return userRoleList;
	}

	/**
	 * @param userRoleList
	 *            the userRoleList to set
	 */
	public void setUserRoleList(List<String> userRoleList) {
		this.userRoleList = userRoleList;
	}

	/**
	 * @return the userFName
	 */
	public String getUserFName() {
		return userFName;
	}

	/**
	 * @param userFName the userFName to set
	 */
	public void setUserFName(String userFName) {
		this.userFName = userFName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the dealer
	 */
	public boolean isDealer() {
		return dealer;
	}

	/**
	 * @param dealer the dealer to set
	 */
	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}

	public String getVwJobTitle() {
		return vwJobTitle;
	}

	public void setVwJobTitle(String vwJobTitle) {
		this.vwJobTitle = vwJobTitle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
