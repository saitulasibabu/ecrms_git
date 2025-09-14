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
@XmlRootElement(name = "CreateCertificateRequest" , namespace = "http://certrequest.vwoa.na.vwg/cert/schemas")
public class CreateCertificateRequest {
	
	public CreateCertificateRequest(){
		certificateRequestModelList =  new ArrayList<CertificateRequestModel>();
	}
	
	private List<CertificateRequestModel> certificateRequestModelList;

	/**
	 * @return the certificateRequestModelList
	 */
	public List<CertificateRequestModel> getCertificateRequestModelList() {
		return certificateRequestModelList;
	}

	/**
	 * @param certificateRequestModelList the certificateRequestModelList to set
	 */
	public void setCertificateRequestModelList(
			List<CertificateRequestModel> certificateRequestModelList) {
		this.certificateRequestModelList = certificateRequestModelList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	
	
}
