/**
 * 
 */
package com.vwoa.ecrms.model.certrequest;

import java.math.BigInteger;
import java.util.Date;

import java.io.Serializable;
import com.vwoa.ecrms.model.common.BaseModel;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class CertDetailModel extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7754029422597505026L;
	private Integer certNum;
	private BigInteger certSerialNum;
	private Date certIssueDate;
	private Date certExpiryDate;
	private String certFilePath;

	/**
	 * @return the certNum
	 */
	public Integer getCertNum() {
		return certNum;
	}

	/**
	 * @param certNum
	 *            the certNum to set
	 */
	public void setCertNum(Integer certNum) {
		this.certNum = certNum;
	}

	/**
	 * @return the certSerialNum
	 */
	public BigInteger getCertSerialNum() {
		return certSerialNum;
	}

	/**
	 * @param certSerialNum
	 *            the certSerialNum to set
	 */
	public void setCertSerialNum(BigInteger certSerialNum) {
		this.certSerialNum = certSerialNum;
	}

	/**
	 * @return the certIssueDate
	 */
	public Date getCertIssueDate() {
		return certIssueDate;
	}

	/**
	 * @param certIssueDate
	 *            the certIssueDate to set
	 */
	public void setCertIssueDate(Date certIssueDate) {
		this.certIssueDate = certIssueDate;
	}

	/**
	 * @return the certExpiryDate
	 */
	public Date getCertExpiryDate() {
		return certExpiryDate;
	}

	/**
	 * @param certExpiryDate
	 *            the certExpiryDate to set
	 */
	public void setCertExpiryDate(Date certExpiryDate) {
		this.certExpiryDate = certExpiryDate;
	}

	/**
	 * @return the certFilePath
	 */
	public String getCertFilePath() {
		return certFilePath;
	}

	/**
	 * @param certFilePath
	 *            the certFilePath to set
	 */
	public void setCertFilePath(String certFilePath) {
		this.certFilePath = certFilePath;
	}

}
