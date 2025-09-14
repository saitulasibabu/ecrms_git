/**
 * 
 */
package com.vwoa.ecrms.helper.webservice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.vwoa.ecrms.model.certrequest.CertRequestModel;
import com.vwoa.ecrms.model.webservice.CertificateRequestModel;
import com.vwoa.ecrms.model.webservice.CertificateRevokeRequest;
import com.vwoa.ecrms.model.webservice.CertificateRevokeRequestModel;
import com.vwoa.ecrms.model.webservice.CertificateRevokeResponse;
import com.vwoa.ecrms.model.webservice.CreateCertificateRequest;
import com.vwoa.ecrms.model.webservice.CreateCertificateResponse;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.PropertyReader;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
@Repository
public class CertStatusHelper {

	private static final Logger logger = LoggerFactory
			.getLogger(CertStatusHelper.class);
	@Autowired
	private WebServiceTemplate webServiceTemplate;

	public CreateCertificateResponse approveCertRequest(
			CertRequestModel certRequestModel) throws ECRMSException, Exception {

		logger.info("Start of approveCertRequest");

		CreateCertificateRequest createCertificateRequest = new CreateCertificateRequest();
		CertificateRequestModel certificateRequestModel = new CertificateRequestModel();

		CreateCertificateResponse certResposeModel = null;

		/**
		 * Convert and transfer data from CertRequestModel to
		 * CertificateRequestModel
		 * 
		 */

		try {

			if (certRequestModel != null) {
				logger.info("certRequestModel is not null");

				certificateRequestModel.setRequestId(certRequestModel
						.getRequestNum().toString().trim());
				certificateRequestModel.setHardwareId(certRequestModel
						.getHardwareId().trim());
				certificateRequestModel
						.setOrganization(ECRMSConstants.ORGANIZATION);
				certificateRequestModel.setDealerName(certRequestModel
						.getDealerName().trim());
				certificateRequestModel.setDealerNumber(certRequestModel
						.getDealerNum().trim());

				if (StringUtils.equals(
						certRequestModel.getDealerCntry().trim(),
						ECRMSConstants.COUNTRY_USA)) {
					certificateRequestModel
							.setCountry(ECRMSConstants.COUNTRY_US);
				} else if (StringUtils.equals(certRequestModel.getDealerCntry()
						.trim(), ECRMSConstants.COUNTRY_CAN)) {
					certificateRequestModel
							.setCountry(ECRMSConstants.COUNTRY_CA);
				}

				certificateRequestModel.setState(certRequestModel
						.getDealerState().trim());
				certificateRequestModel.setEmail(certRequestModel
						.getContactEmail().trim());
				certificateRequestModel
						.setPkPassword(ECRMSConstants.EMPTY_STRING);
				certificateRequestModel.setPfxPassword(certRequestModel
						.getHardwareId().trim());
				certificateRequestModel.setExpiryPeriod(certRequestModel
						.getExpiryPeriod());

				createCertificateRequest.getCertificateRequestModelList().add(
						certificateRequestModel);
			}

			// Call the web service
			certResposeModel = (CreateCertificateResponse) webServiceTemplate
					.marshalSendAndReceive(createCertificateRequest);

			if (certResposeModel != null) {
				logger.info("response is "
						+ certResposeModel.getCertificateResponseModelList());
			}

		} catch (Exception e) {
			// Throw the exception
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_APPROVE_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_APPROVE_DATA.description(),
					e);
			throw ecrmsException;
		}

		logger.info("End of approveCertRequest");
		return certResposeModel;
	}

	/**
	 * This method makes a call to CA Issuance ca Web service and return the
	 * result. The Response model will return list of Request Id's that were
	 * Revoked successfully.
	 * 
	 * @param certRequestModelList
	 * @return CertificateRevokeResponse
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public CertificateRevokeResponse revokeCertificate(
			List<CertRequestModel> certRequestModelList) throws ECRMSException,
			Exception {

		logger.info("Start of revokeCertificate");

		/** Define the different objects */
		CertificateRevokeResponse certificateRevokeResponse = null;
		CertificateRevokeRequest certificateRevokeRequest = new CertificateRevokeRequest();
		CertificateRevokeRequestModel certificateRevokeRequestModel = null;
		List certificateRevokeRequestModelList = new ArrayList<CertificateRequestModel>();
		CertRequestModel certRequestModel = null;

		/**
		 * Convert and transfer data from CertRequestModelList to
		 * certificateRevokeRequestModelList
		 */
		try {
			if (certRequestModelList != null) {

				for (int i = 0; i < certRequestModelList.size(); i++) {

					certRequestModel = (CertRequestModel) certRequestModelList
							.get(i);

					certificateRevokeRequestModel = new CertificateRevokeRequestModel();
					certificateRevokeRequestModel.setRequestId(certRequestModel
							.getRequestNum().toString());
					certificateRevokeRequestModel
							.setHardwareId(certRequestModel.getHardwareId());
					certificateRevokeRequestModel
							.setOrganization(ECRMSConstants.ORGANIZATION);

					// set the individual revoke request model in the list
					certificateRevokeRequestModelList
							.add(certificateRevokeRequestModel);
				}

				// Set the list in the request model
				certificateRevokeRequest
						.setCertificateRevokeRequestModelList(certificateRevokeRequestModelList);
			}

			// Call the web service
			certificateRevokeResponse = (CertificateRevokeResponse) webServiceTemplate
					.marshalSendAndReceive(certificateRevokeRequest);

		} catch (Exception e) {

			// Throw the exception
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_REVOKE_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_REVOKE_DATA.description(),
					e);
			throw ecrmsException;
		}

		if (certificateRevokeResponse != null) {
			// Logging the response
			logger.info("Revoke Response is "
					+ certificateRevokeResponse
							.getCertificateRevokeResponseModel());
		}

		logger.info("End of revokeCertificate");
		// Return the response model
		return certificateRevokeResponse;

	}

	//This method sets the cerificate activation indicator for the active certificate
	public String prepareCertActivationInd(String activationDate) {
		logger.info("Start of prepareCertActivationInd");
		String activationInd = ECRMSConstants.FALSE;
		Date currentDate = new Date();
		int certActivPeriod = Integer
				.parseInt(PropertyReader
						.retrievePropertiesValue(ECRMSConstants.CERT_ACTIVATION_PERIOD));
		logger.info("activationDate is "+activationDate);
		double diffInHrs = (currentDate.getTime() - convertToDate(activationDate).getTime())/ (60.00 * 60.00 * 1000.00);
		BigDecimal diffVal = new BigDecimal(diffInHrs);
		diffVal = diffVal.setScale(0, RoundingMode.CEILING);
		logger.info("difference between current date and certActivation period is "	+ diffVal);
		logger.info("result is  "	+ diffVal.compareTo(new BigDecimal(certActivPeriod)));
		if (diffVal.compareTo(new BigDecimal(certActivPeriod)) <= 0) {

			// set to true if active Cert Exists and Activated
			// difference hours are less than activation PERIOD
			activationInd = ECRMSConstants.TRUE;
		}
		logger.info("End of prepareCertActivationInd : Activation indicator is "+activationInd);
		return activationInd;
	}

	/**
	 * This method convert String to date.
	 * 
	 * @param strDate
	 * @return
	 */

	public static Date convertToDate(String strDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = new Date();
		try {
			today = df.parse(strDate);
		} catch (ParseException e) {
		}
		return today;
	}

	/**
	 * @return the webServiceTemplate
	 */
	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	/**
	 * @param webServiceTemplate
	 *            the webServiceTemplate to set
	 */
	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}

}
