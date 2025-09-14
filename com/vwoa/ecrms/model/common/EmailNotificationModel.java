/**
 * 
 */
package com.vwoa.ecrms.model.common;

/**
 * @author aparna_deshmukh01
 * 
 */
public class EmailNotificationModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5052416882636396792L;

	private String contactEmail;// To email

	private String serviceManagerEmail; // CC email

	private String securityAdminEmail; // From mail VWGoA Security Admin

	private String subject;

	private String body;

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
	 * @return the serviceManagerEmail
	 */
	public String getServiceManagerEmail() {
		return serviceManagerEmail;
	}

	/**
	 * @param serviceManagerEmail
	 *            the serviceManagerEmail to set
	 */
	public void setServiceManagerEmail(String serviceManagerEmail) {
		this.serviceManagerEmail = serviceManagerEmail;
	}

	/**
	 * @return the securityAdminEmail
	 */
	public String getSecurityAdminEmail() {
		return securityAdminEmail;
	}

	/**
	 * @param securityAdminEmail
	 *            the securityAdminEmail to set
	 */
	public void setSecurityAdminEmail(String securityAdminEmail) {
		this.securityAdminEmail = securityAdminEmail;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

}
