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
@XmlRootElement(name = "CreateCertificateResponse", namespace="http://certrequest.vwoa.na.vwg/cert/schemas")
public class CreateCertificateResponse {
	
	
	public CreateCertificateResponse(){
		
		certificateResponseModelList = new ArrayList<CertificateResponseModel>();
	}
	
	private List<CertificateResponseModel> certificateResponseModelList;

	/**
	 * @return the certificateResponseModelList
	 */
	public List<CertificateResponseModel> getCertificateResponseModelList() {
		return certificateResponseModelList;
	}

	/**
	 * @param certificateResponseModelList the certificateResponseModelList to set
	 */
	public void setCertificateResponseModelList(
			List<CertificateResponseModel> certificateResponseModelList) {
		this.certificateResponseModelList = certificateResponseModelList;
	}

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
