package com.vwoa.ecrms.helper.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

import com.vwoa.ecrms.model.common.EmailNotificationModel;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author aparna_deshmukh01
 * 
 */
@Repository
public class EmailHelper {

	private static final Logger logger = LoggerFactory
			.getLogger(EmailHelper.class);

	@Autowired
	private MailSender mailSender;

	@Autowired
	private SimpleMailMessage mailTemplate;

	/**
	 * This method sends file by email
	 * 
	 * @param emailModel
	 *            CertificateExpiryNotificationModel
	 * @throws Exception
	 * @throws ECRMSException
	 */
	public void sendEmail(EmailNotificationModel emailModel) throws ECRMSException{

		logger.info("Start of  sendEmail()");

		

		try {

			mailTemplate.setTo(emailModel.getContactEmail());
			
			mailTemplate.setFrom(emailModel.getSecurityAdminEmail());
			mailTemplate.setSubject(emailModel.getSubject());
			mailTemplate.setText(emailModel.getBody());

			mailSender.send(mailTemplate);

		} 
		//Handling the Mail Exception
		catch (MailException mex) {

			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_EMAIL_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_EMAIL_DATA.description(),mex);
			throw exception;

		} 
		//Handling the Exception
		catch (Exception ex) {

			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_EMAIL_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_EMAIL_DATA.description(),ex);
			throw exception;
		}

		logger.info("End of  sendEmail()");
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public SimpleMailMessage getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(SimpleMailMessage mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

}
