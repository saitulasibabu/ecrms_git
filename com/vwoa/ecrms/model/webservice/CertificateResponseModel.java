/**
 * 
 */
package com.vwoa.ecrms.model.webservice;

import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author prakhar_singhal
 * 
 */
@XmlType(name = "CertificateResponseModel")
public class CertificateResponseModel {

	// for audit trail
	private String requestId;

	// required from the ws request
	private Date expiryDate;

	private Date issueDate;

	private BigInteger certificateSerialId;

	private String pfxFilePath;

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
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the issueDate
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate
	 *            the issueDate to set
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the certificateSerialId
	 */
	public BigInteger getCertificateSerialId() {
		return certificateSerialId;
	}

	/**
	 * @param certificateSerialId
	 *            the certificateSerialId to set
	 */
	public void setCertificateSerialId(BigInteger certificateSerialId) {
		this.certificateSerialId = certificateSerialId;
	}

	/**
	 * @return the pfxFilePath
	 */
	public String getPfxFilePath() {
		return pfxFilePath;
	}

	/**
	 * @param pfxFilePath
	 *            the pfxFilePath to set
	 */
	public void setPfxFilePath(String pfxFilePath) {
		this.pfxFilePath = pfxFilePath;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
