package com.vwoa.ecrms.action.searchcertrequest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.service.certrequest.IDownloadCertService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Sanman Ganthe
 * 
 */
@Namespace("/")
public class DownloadFileAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8549515353194080191L;

	private static final Logger logger = LoggerFactory
			.getLogger(DownloadFileAction.class);

	private String fileName;

	@Autowired
	private String certificateFileExtension;

	@Autowired
	private String pfxFileLocation;

	@Autowired
	private IDownloadCertService downloadCertService;// service to get filename
	// for download

	private Map<String, Object> session;

	private Integer requestNum;

	/**
	 * This method is called to download the PFX certificate file
	 * 
	 * @return Success Code
	 */
	@Action(value = "downloadCertificate", results = { @Result(name = SUCCESS, location = "searchcertrequest/home.jsp", type = "dispatcher") })
	public String downloadCertificateFile(){

		logger.info("Start of downloadCertificateFile");

		try {
			
			UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			
			javax.servlet.http.HttpServletResponse response = ServletActionContext
					.getResponse();
			if (StringUtils.isBlank(fileName)) {
				logger.info("downloading from create certificate request");

					fileName = downloadCertService
							.retrieveCertFileName(requestNum);
					logger
							.info("file to download from create certificate request "
									+ fileName);
				} else {
					logger.info("Nothing to download");
				}

			

			String fileSeperator = System.getProperty("file.separator");
			String completefileName = pfxFileLocation + fileSeperator
					+ fileName;

			logger.info(completefileName);

			int len = fileName.length();

			String fileExtension = fileName.substring(len - 3, len);

			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/" + fileExtension);
			response.addHeader("Content-Disposition", "attachment; filename="
					+ fileName);

			File file = new File(completefileName);

			BufferedInputStream is = new BufferedInputStream(
					new FileInputStream(file));
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int bytesRead;
			while ((bytesRead = is.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}

			is.close();
			out.close();

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
						

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			
		}

		logger.info("End of downloadCertificateFile");
		return null;
	}
	
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the pfxFileLocation
	 */
	public String getPfxFileLocation() {
		return pfxFileLocation;
	}

	/**
	 * @param pfxFileLocation
	 *            the pfxFileLocation to set
	 */
	public void setPfxFileLocation(String pfxFileLocation) {
		this.pfxFileLocation = pfxFileLocation;
	}

	/**
	 * @return the certificateFileExtension
	 */
	public String getCertificateFileExtension() {
		return certificateFileExtension;
	}

	/**
	 * @param certificateFileExtension
	 *            the certificateFileExtension to set
	 */
	public void setCertificateFileExtension(String certificateFileExtension) {
		this.certificateFileExtension = certificateFileExtension;
	}

	/**
	 * @param downloadCertService
	 *            the downloadCertService to set
	 */
	public void setDownloadCertService(IDownloadCertService downloadCertService) {
		this.downloadCertService = downloadCertService;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

	}

	/**
	 * @return the requestNum
	 */
	public Integer getRequestNum() {
		return requestNum;
	}

	/**
	 * @param requestNum
	 *            the requestNum to set
	 */
	public void setRequestNum(Integer requestNum) {
		this.requestNum = requestNum;
	}

}
