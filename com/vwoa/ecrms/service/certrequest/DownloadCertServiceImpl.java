/**
 * 
 */
package com.vwoa.ecrms.service.certrequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vwoa.ecrms.dao.certrequest.IDownloadCertDAO;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Service class to handle request related to download certificate file name
 * @author Aparna_Deshmukh01
 * 
 */
@Service
public class DownloadCertServiceImpl implements IDownloadCertService {

	private static final Logger logger = LoggerFactory
			.getLogger(DownloadCertServiceImpl.class);

	@Autowired
	private IDownloadCertDAO downloadCertDAO;

	@Override
	public String retrieveCertFileName(Integer requestNum)
			throws ECRMSException {
		// retrieve certificate file name
		return downloadCertDAO.retrieveCertFileName(requestNum);
	}

	/**
	 * @return the downloadCertDAO
	 */
	public IDownloadCertDAO getDownloadCertDAO() {
		return downloadCertDAO;
	}

	/**
	 * @param downloadCertDAO
	 *            the downloadCertDAO to set
	 */
	public void setDownloadCertDAO(IDownloadCertDAO downloadCertDAO) {
		this.downloadCertDAO = downloadCertDAO;
	}

}
