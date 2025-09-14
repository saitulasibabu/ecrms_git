/**
 * 
 */
package com.vwoa.ecrms.dao.certrequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to handle download related data access operations
 * @author Aparna_Deshmukh01
 * 
 */
@Repository
public class DownloadCertDAOImpl implements IDownloadCertDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(DownloadCertDAOImpl.class);
	@Autowired
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vwoa.ecrms.dao.certrequest.IDownloadCertDAO#retrieveCertFileName(
	 * java.lang.Integer)
	 */
	@Override
	public String retrieveCertFileName(Integer requestNum)
			throws ECRMSException {
		logger.info("Start of  retrieveCertFileName");
		String fileName;
		try {
			// retrieve certificate file name
			fileName = (String) sqlSession.selectOne(
					ECRMSConstants.RETRIEVE_FILE_NAME, requestNum);

		}
		// Handling the Exception
		catch (Exception ex) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description(),
					ex);

			throw exception;
		}
		logger.info("End of  retrieveCertFileName");
		return fileName;
	}

}
