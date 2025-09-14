/**
 * 
 */
package com.vwoa.ecrms.model.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author prakhar_singhal
 *
 */
@XmlRootElement(name = "CertificateRevokeResponse" , namespace = "http://certrequest.vwoa.na.vwg/cert/schemas")
public class CertificateRevokeResponse {
	
	private List<CertificateRevokeResponseModel> certificateRevokeResponseModel;

	public CertificateRevokeResponse(){
		certificateRevokeResponseModel = new ArrayList<CertificateRevokeResponseModel>();
	}
	/**
	 * @return the certificateRevokeResponseModel
	 */
	public List<CertificateRevokeResponseModel> getCertificateRevokeResponseModel() {
		return certificateRevokeResponseModel;
	}

	/**
	 * @param certificateRevokeResponseModel the certificateRevokeResponseModel to set
	 */
	public void setCertificateRevokeResponseModel(
			List<CertificateRevokeResponseModel> certificateRevokeResponseModel) {
		this.certificateRevokeResponseModel = certificateRevokeResponseModel;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
