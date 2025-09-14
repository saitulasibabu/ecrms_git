/**
 * Copyright (c) 2011 Infosys Technologies Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are not permitted.
 */
package com.vwoa.ecrms.helper.searchcertrequest;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchResultCertRequestModel;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.PropertyReader;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * @author Aparna_Deshmukh01
 * 
 */
public class SearchCertRequestHelper {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SearchCertRequestHelper.class);

	/**
	 * This method exports the Advanced search records to an excel
	 * 
	 * @param sarchResultCertRequestModelList
	 *            - List of search result model
	 * @param headerList
	 */

	public void exportCertRequestToExcel(

	List<SearchResultCertRequestModel> sarchResultCertRequestModelList,
			List<String> headerList) throws ECRMSException, Exception {
		LOGGER.info("Start of exportCertRequestToExcel");
		// filename to export report
		String filename = PropertyReader
				.retrievePropertiesValue(ECRMSConstants.XLS_FILENAME);
		
		String filePath = PropertyReader
				.retrievePropertiesValue(ECRMSConstants.XLS_FILEPATH);
		
		ServletOutputStream resOut = null;
		String worksheetName = null;
		String requestNumber = null;
		String dealerNumber = null;
		String deviceName = null;
		String hwdId = null;
		String deviceType = null;
		String status = null;
		String issueDate = null;
		String expirationDate = null;
		String revokeDate = null;

		try {
			if (headerList != null && headerList.size() == 10) {
				// Labels or header of excel
				worksheetName = headerList.get(0); // Work sheet name
				requestNumber = headerList.get(1); // Request Number column
				dealerNumber = headerList.get(2); // dealer Number Column
				deviceName = headerList.get(3); // device name column
				hwdId = headerList.get(4); // hardware id column
				deviceType = headerList.get(5); // device type column
				status = headerList.get(6); // status column
				issueDate = headerList.get(7); // Issue date column
				expirationDate = headerList.get(8); // expiration date column
				revokeDate = headerList.get(9); // revoke date column
			}
			
			
			File fTemp = new File(filePath);
			
			if(!fTemp.exists())
			{
				fTemp.mkdirs();
			}
			
			
			Workbook workbook = new HSSFWorkbook();
			Map<String, CellStyle> styles = createStyles(workbook);
			Sheet sheet = workbook.createSheet(worksheetName);
			Row row1 = sheet.createRow(0);
			// columns of worksheet
			String[] columns = { requestNumber, dealerNumber, deviceName,
					hwdId, deviceType, status, issueDate, expirationDate,
					revokeDate };
			for (int i = 0; i < columns.length; i++) {
				Cell Cell1 = row1.createCell(i);
				Cell1.setCellValue(columns[i]);
				// sheet.autoSizeColumn(i);
				sheet.setDefaultColumnWidth(20);
				sheet.setDefaultRowHeight((short) 20);
				Cell1.setCellStyle(styles.get("header"));
			}
			int rowCountSheet = 1;
			// add data rows
			for (SearchResultCertRequestModel resultModel : sarchResultCertRequestModelList) {
				Cell cell;
				Row row = sheet.createRow(rowCountSheet);
				cell = row.createCell(0);
				cell
						.setCellValue(resultModel.getRequestNum() != null ? resultModel
								.getRequestNum().toString()
								: "");
				cell = row.createCell(1);
				cell
						.setCellValue(resultModel.getDealerNum() != null ? resultModel
								.getDealerNum()
								: "");
				cell = row.createCell(2);
				cell
						.setCellValue(resultModel.getDeviceNickName() != null ? resultModel
								.getDeviceNickName()
								: "");
				cell = row.createCell(3);
				cell
						.setCellValue(resultModel.getHardwareId() != null ? resultModel
								.getHardwareId()
								: "");
				cell = row.createCell(4);
				cell
						.setCellValue(resultModel.getDeviceType() != null ? resultModel
								.getDeviceType()
								: "");
				cell = row.createCell(5);
				cell.setCellValue(resultModel.getStatus() != null ? resultModel
						.getStatus() : "");
				cell = row.createCell(6);
				cell
						.setCellValue(resultModel.getIssueDate() != null ? resultModel
								.getIssueDate().toString()
								: "");
				cell = row.createCell(7);
				cell
						.setCellValue(resultModel.getExpiryDate() != null ? resultModel
								.getExpiryDate().toString()
								: "");
				cell = row.createCell(8);
				cell
						.setCellValue(resultModel.getRevokedDate() != null ? resultModel
								.getRevokedDate().toString()
								: "");
				rowCountSheet++;
			}
			FileOutputStream fileOut = new FileOutputStream(fTemp.getPath()+File.separator+filename);
			workbook.write(fileOut);
			// close file
			fileOut.close();
			javax.servlet.http.HttpServletResponse response = ServletActionContext
					.getResponse();
			response.setContentType("text/plain");
			
			response.addHeader("Content-Disposition", "attachment; filename="
					+ filename);
			
			int bufferSize = 2048;
			response.setBufferSize(bufferSize);
			resOut = response.getOutputStream();
			workbook.write(resOut);
			
		} catch (Exception e) {
			ECRMSException exception = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_EXPORT_DATA.status(),
					ECRMSErrorMessage.Constants.ERR_EXPORT_DATA.description(),
					e);
			throw exception;
		} finally {
			try {
				resOut.close();
			} catch (Exception e) {
				ECRMSException exception = new ECRMSException(
						ECRMSErrorMessage.Constants.ERR_EXPORT_DATA.status(),
						ECRMSErrorMessage.Constants.ERR_EXPORT_DATA
								.description(), e);
				throw exception;
			}
		}
		LOGGER.info("End of exportCertRequestToExcel");
	}

	/**
	 * This method create or customize style for workbook.
	 * 
	 * @param wb
	 * @return Map<String, CellStyle>
	 */
	private static Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style = wb.createCellStyle();
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(HSSFColor.BLUE.index);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setBorderTop((short) 6); // double lines border
		style.setBorderBottom((short) 1);
		style.setBorderRight((short) 1);
		style.setBorderLeft((short) 1);
		style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFont(headerFont);
		styles.put("header", style);
		return styles;
	}

	/**
	 * 1.This method helps to format result dates into proper date format, which
	 * is require to display in Advanced Search and Basic search 2.Filter basic
	 * search result list
	 * 
	 * @param searchResultCertRequestModelList
	 * @return List<SearchResultCertRequestModel>
	 */
	public List<SearchResultCertRequestModel> formatsearchCertRequests(
			List<SearchResultCertRequestModel> searchResultCertRequestModelList,
			int certActivPeriod, int certRenewPeriod) throws ECRMSException,
			Exception {
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// to get current date minus 29 days
		cal.add(Calendar.DATE, -29);
		String currentDateminus29 = sdf.format(cal.getTime());
		List<SearchResultCertRequestModel> updatedResultList = new ArrayList<SearchResultCertRequestModel>();
		Date currentDate = new Date();
		
		//filter search result based on Expiry date of Expired Certificates.
		for (SearchResultCertRequestModel resultModel : searchResultCertRequestModelList) {
			if (StringUtils.isNotBlank(resultModel.getHistoryStatus())) 
			{
				if (StringUtils.isNotBlank(resultModel.getStatus())) 
				{
					if (!resultModel.getStatuscde().equals(ECRMSConstants.EXPIRED)) {
						updatedResultList.add(resultModel);
					}
					else if (resultModel.getExpiryDate() != null) 
					{
						LOGGER.info(resultModel.getExpiryDate()+" --> Expiry date : "+convertToDate(resultModel.getExpiryDate()));
						LOGGER.info(currentDateminus29+" --> current date minus 29 : "+convertToDate(currentDateminus29));
						if (convertToDate(resultModel.getExpiryDate()).after(
								convertToDate(currentDateminus29))) {
							//if record is expired and expired within last 29 days.
							updatedResultList.add(resultModel);
							LOGGER.info("Expiry date is after current minus 29: ");
						}
					}
				}
			} else {
				updatedResultList.add(resultModel);
			}
		}
			for (SearchResultCertRequestModel resultModel : updatedResultList) {
			// set multiple create indicator
			// default- false
			resultModel.setMultipleCreateInd(ECRMSConstants.FALSE);
			if (StringUtils.isNotBlank(resultModel.getExpiryDate())
					&& resultModel.getExpiryDate().length() > 0) {
				if (StringUtils.isNotBlank(resultModel.getHistoryStatus())) {
					if (StringUtils.equals(resultModel.getHistoryStatus(),ECRMSConstants.ACTIVE)) {
						double diffInDays = (convertToDate(resultModel.getExpiryDate()).getTime() - currentDate
								.getTime())/ (24.00 * 60.00 * 60.00 * 1000.00);
						BigDecimal diffVal = new BigDecimal(diffInDays);
						diffVal = diffVal.setScale(0, RoundingMode.CEILING);
						if (diffVal.compareTo(new BigDecimal(certRenewPeriod)) <= 0) {
							// set to true if active Cert Exists and Expiration
							// Date is Greater than 60 Days
							resultModel.setMultipleCreateInd(ECRMSConstants.TRUE);
						}
					} else if (StringUtils.equals(resultModel.getHistoryStatus(), ECRMSConstants.EXPIRED)) {
						// set to true if Certificate is already expired
						resultModel.setMultipleCreateInd(ECRMSConstants.TRUE);
					}
				}
				// format expiry date
				if(resultModel.getExpiryDate().contains("/"))
				{
					resultModel.setExpiryDate(formatDate4Advanced(resultModel.getExpiryDate()));
					LOGGER.info("Date format contain / "+resultModel.getExpiryDate());
				}
				else if(resultModel.getExpiryDate().contains("-"))
				{
					resultModel.setExpiryDate(formatDate(resultModel.getExpiryDate()));
					LOGGER.info("Date format contain - "+resultModel.getExpiryDate());
				}
				
			}
		

			// to set CertActivationInd
			// by default
			if (StringUtils.isNotBlank(resultModel.getCertActivationDate())) {
				if (StringUtils.equals(resultModel.getStatuscde(),
						ECRMSConstants.ACTIVE)) {
					LOGGER.info("CertActivationIndicator");
					LOGGER.info("RequestNum :->>"+resultModel.getRequestNum());
					double diffInHrs = (currentDate.getTime() - convertToDate(
							resultModel.getCertActivationDate()).getTime())
							/ (60.00 * 60.00 * 1000.00);
					BigDecimal diffVal = new BigDecimal(diffInHrs);
					diffVal = diffVal.setScale(0, RoundingMode.CEILING);
					LOGGER.info("diffVal :->>"+diffVal);
					LOGGER.info("currentDate :->>"+currentDate);
					//if diffInHrs <= certActivPeriod 
					if (diffVal.compareTo(new BigDecimal(certActivPeriod)) <= 0){
						// set to true if active Cert Exists and Activated
						// difference hours are less than activation PERIOD
						resultModel.setCertActivationInd(ECRMSConstants.TRUE);
						
					} else {
						resultModel.setCertActivationInd(ECRMSConstants.FALSE);
						
					}
				} else {
					// set to true if Certificate Status is not active
					resultModel.setCertActivationInd(ECRMSConstants.FALSE);
				}
			}
		}
		return updatedResultList;
	}
	/**
	 * 1.This method helps to format result dates into proper date format, which
	 * is require to display in Advanced Search and Basic search 2.Filter basic
	 * search result list
	 * 
	 * @param searchResultCertRequestModelList
	 * @return List<SearchResultCertRequestModel>
	 */
	public List<SearchResultCertRequestModel> formatsearchCertRequests(
			List<SearchResultCertRequestModel> searchResultCertRequestModelList) throws ECRMSException,
			Exception {
				
		List<SearchResultCertRequestModel> updatedResultList = new ArrayList<SearchResultCertRequestModel>();
		
		
		for (SearchResultCertRequestModel resultModel : searchResultCertRequestModelList) {
			
			if (StringUtils.isNotBlank(resultModel.getExpiryDate())
					&& resultModel.getExpiryDate().length() > 0) {
				// format expiry date
				if(resultModel.getExpiryDate().contains("/"))
				{
					resultModel.setExpiryDate(formatDate4Advanced(resultModel.getExpiryDate()));
					LOGGER.info("Expirydate/-  "+resultModel.getExpiryDate());
				}
				else if(resultModel.getExpiryDate().contains("-"))
				{
					resultModel.setExpiryDate(formatDate(resultModel.getExpiryDate()));
					LOGGER.info("Expirydate --  "+resultModel.getExpiryDate());
				}
				
			}
			// format issue date
			if (StringUtils.isNotBlank(resultModel.getIssueDate())
					&& resultModel.getIssueDate().length() > 0) {
				if(resultModel.getIssueDate().contains("/"))
				{
					resultModel.setIssueDate(formatDate4Advanced(resultModel.getIssueDate()));
					LOGGER.info("Issue date/- "+resultModel.getIssueDate());
				}
				else if(resultModel.getIssueDate().contains("-"))
				{
					resultModel.setIssueDate(formatDate(resultModel.getIssueDate()));
					LOGGER.info("Issuedate - - "+resultModel.getIssueDate());
				}
				
			}
			// format revoke date
			if (StringUtils.isNotBlank(resultModel.getRevokedDate())
					&& resultModel.getRevokedDate().length() > 0) {
				
				if(resultModel.getRevokedDate().contains("/"))
				{
					resultModel.setRevokedDate(formatDate4Advanced(resultModel.getRevokedDate()));
					LOGGER.info("REvoked date/- "+resultModel.getRevokedDate());
				}
				else if(resultModel.getRevokedDate().contains("-"))
				{
					resultModel.setRevokedDate(formatDate(resultModel.getRevokedDate()));
					LOGGER.info("RevokedDate  -- "+resultModel.getRevokedDate());
				}
			}
			updatedResultList.add(resultModel);
		}
		
		return updatedResultList;
	}
	/**
	 * This method convert String to date.
	 * 
	 * @param strDate
	 * @return
	 */

	public static Date convertToDate(String strDate) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date today = new Date();
		try {
			LOGGER.info("ConverToDate Before conversion :->>"+strDate);
			today = df.parse(strDate);
			LOGGER.info("ConverToDate after conversion :->>"+today);
			
		} catch (ParseException e) {
			LOGGER.error("Error occured while parsing date");
		}
		return today;
	}

	/**
	 * This method helps to actually change the format of source date to
	 * destination date format
	 * 
	 * @param dateStr
	 * @return
	 */

	public static String formatDate(String dateStr) {

		String retStr = dateStr;
		if (dateStr != null && dateStr.length() > 0) {
			try {
				SimpleDateFormat sdfSource = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdfDestination = new SimpleDateFormat(
						"MM/dd/yyyy");
				retStr = sdfDestination.format(sdfSource.parse(dateStr));
				
				
			} catch (ParseException ex) {
				LOGGER.info("formatDate: Error in date Conversion: "+ex.toString());
			}
		}
		return retStr;
	}
	/**
	 * This method helps to actually change the format of source date to
	 * destination date format
	 * 
	 * @param dateStr
	 * @return
	 */

	public static String formatDate4Advanced(String dateStr) {

		String retStr = dateStr;
		if (dateStr != null && dateStr.length() > 0) {
			try {
				SimpleDateFormat sdfSource = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				SimpleDateFormat sdfDestination = new SimpleDateFormat(
						"MM/dd/yyyy");
				retStr = sdfDestination.format(sdfSource.parse(dateStr));
				
			} catch (ParseException ex) {
				LOGGER.info("formatDate4Advanced:Error in date Conversion: "+ex.toString());
			}
		}
		return retStr;
	}
	/**
	 * @param searchCertificateModel
	 * @return
	 */

	public SearchCertRequestModel formatCertRequest(SearchCertRequestModel searchCertificateModel) {
		String dealerNumber = "";
		dealerNumber = searchCertificateModel.getDealerNum();
		String hardwareId = "";
		hardwareId = searchCertificateModel.getHardwareId();
		LOGGER.debug("Dealer Number :- "+dealerNumber+" HardwareId :- "+hardwareId);
		String requestNumberStr="";
		if(searchCertificateModel.getRequestNumStr()!= null)
		{
		requestNumberStr = searchCertificateModel.getRequestNumStr();
		LOGGER.debug(" RequestNumber:-"+requestNumberStr);
		}
		LOGGER.debug(" RequestNumber :->"+requestNumberStr);
		
		if (StringUtils.isNotBlank(dealerNumber)) {
			dealerNumber = formatString(dealerNumber);
			searchCertificateModel.setDealerNum(dealerNumber);
			LOGGER.debug(" Dealer Number:-"+dealerNumber);
			
		}
		if (StringUtils.isNotBlank(hardwareId)) {
			hardwareId = formatString(hardwareId);
			searchCertificateModel.setHardwareId(hardwareId);
			LOGGER.debug(" HardwareId :-"+hardwareId);
			
		}
		if (StringUtils.isNotBlank(requestNumberStr)) {
			requestNumberStr = formatString(requestNumberStr);
			searchCertificateModel.setRequestNumStr(requestNumberStr);
			LOGGER.debug(" RequestNumber:-"+requestNumberStr);
			
		}

		return searchCertificateModel;
	}

	/**
	 * Format string for wild card searching. Replace * with %
	 * 
	 * @param str
	 * @return
	 */

	public String formatString(String paramstr) {
		StringBuffer strbuffer = new StringBuffer();
		String str = paramstr;
		// add first char
		char lastChar = str.charAt(0);
		strbuffer.append(lastChar);
		int strlen = str.length();
		for (int index = 1; index < strlen; index++) {
			char c = str.charAt(index);
			if (c != lastChar) {
				strbuffer.append(c);
				lastChar = c;
			} else {
				if (c == '*') {
					lastChar = c;
				} else {
					strbuffer.append(c);
					lastChar = c;
				}
			}
		}
		str = strbuffer.toString().replace('*', '%');
		return str;
	}
}
