/**
 * 
 */
package com.vwoa.ecrms.model.webservice;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author prakhar_singhal
 * 
 */
@XmlType(name = "CertificateRequestModel")
public class CertificateRequestModel {

	private String requestId;//Variable to hold the request Id of the request

	private String hardwareId;//Variable to hold the hardware Id of the request

	private String organization;//Variable to hold the organization

	private String dealerNumber;//Variable to hold the dealer number

	private String dealerName;//Variable to hold the dealer name

	private String country;//Variable to hold the country

	private String state;//Variable to hold the state

	private String email;//Variable to hold the Email

	private String pkPassword;

	private String pfxPassword;

	private String expiryPeriod;//Variable to hold the expiry Period

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId
	 *            the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the hardwareId
	 */
	public String getHardwareId() {
		return hardwareId;
	}

	/**
	 * @param hardwareId
	 *            the hardwareId to set
	 */
	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the dealerNumber
	 */
	public String getDealerNumber() {
		return dealerNumber;
	}

	/**
	 * @param dealerNumber
	 *            the dealerNumber to set
	 */
	public void setDealerNumber(String dealerNumber) {
		this.dealerNumber = dealerNumber;
	}

	/**
	 * @return the dealerName
	 */
	public String getDealerName() {
		return dealerName;
	}

	/**
	 * @param dealerName
	 *            the dealerName to set
	 */
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pkPassword
	 */
	public String getPkPassword() {
		return pkPassword;
	}

	/**
	 * @param pkPassword
	 *            the pkPassword to set
	 */
	public void setPkPassword(String pkPassword) {
		this.pkPassword = pkPassword;
	}

	/**
	 * @return the pfxPassword
	 */
	public String getPfxPassword() {
		return pfxPassword;
	}

	/**
	 * @param pfxPassword
	 *            the pfxPassword to set
	 */
	public void setPfxPassword(String pfxPassword) {
		this.pfxPassword = pfxPassword;
	}

	/**
	 * @return the expiryPeriod
	 */
	public String getExpiryPeriod() {
		return expiryPeriod;
	}

	/**
	 * @param expiryPeriod
	 *            the expiryPeriod to set
	 */
	public void setExpiryPeriod(String expiryPeriod) {
		this.expiryPeriod = expiryPeriod;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
