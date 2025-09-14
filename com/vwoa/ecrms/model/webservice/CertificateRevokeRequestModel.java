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
@XmlType(name = "CertificateRevokeRequestModel")
public class CertificateRevokeRequestModel {
	
	private String requestId;
	
	private String requestType;

	private String hardwareId;
	
	private String organization;

	/**
	 * @return the hardwareId
	 */
	public String getHardwareId() {
		return hardwareId;
	}

	/**
	 * @param hardwareId the hardwareId to set
	 */
	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}


	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
