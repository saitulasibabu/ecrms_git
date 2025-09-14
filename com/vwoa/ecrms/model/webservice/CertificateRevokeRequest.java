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
@XmlRootElement(name = "CertificateRevokeRequest" , namespace = "http://certrequest.vwoa.na.vwg/cert/schemas")
public class CertificateRevokeRequest {
	
	private List<CertificateRevokeRequestModel> certificateRevokeRequestModelList;

	public CertificateRevokeRequest(){
		certificateRevokeRequestModelList = new ArrayList<CertificateRevokeRequestModel>();
	}
	
	/**
	 * @return the certificateRevokeRequestModelList
	 */
	public List<CertificateRevokeRequestModel> getCertificateRevokeRequestModelList() {
		return certificateRevokeRequestModelList;
	}

	/**
	 * @param certificateRevokeRequestModelList the certificateRevokeRequestModelList to set
	 */
	public void setCertificateRevokeRequestModelList(
			List<CertificateRevokeRequestModel> certificateRevokeRequestModelList) {
		this.certificateRevokeRequestModelList = certificateRevokeRequestModelList;
	}
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
