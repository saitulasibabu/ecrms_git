package com.vwoa.ecrms.action.searchcertrequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.vwoa.ecrms.action.BaseAction;
import com.vwoa.ecrms.model.common.UserProfileModel;
import com.vwoa.ecrms.model.dropdown.DropdownModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchCertRequestModel;
import com.vwoa.ecrms.model.searchcertrequest.SearchResultCertRequestModel;
import com.vwoa.ecrms.service.certrequest.ICertRequestService;
import com.vwoa.ecrms.service.common.ICommonService;
import com.vwoa.ecrms.service.searchcertrequest.ISearchCertRequestService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.PropertyReader;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 * Class to handle all the actions related to Basic Search
 * 
 * @author bhagyashri_ajgare
 * 
 */
@ParentPackage("ecrms-default")
@Namespace("/")
public class BasicSearchAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = -3094958750964676285L;
	private static final Logger logger = LoggerFactory
			.getLogger(BasicSearchAction.class);

	private Map<String, Object> session;

	private SearchCertRequestModel searchCertRequestModel;

	private SearchResultCertRequestModel searchResultCertRequestModel;

	// Dealer Number
	private String dealerNumber;

	// Dealer Name
	private String dealerName;

	// Success message after action completion successfully
	private String successMessage;

	// certificate
	private String multipleReqNum;// list to hold request numbers

	// result List
	private List<SearchResultCertRequestModel> gridModel;

	@Autowired
	private ISearchCertRequestService searchCertRequestService;

	@Autowired
	private ICommonService commonService;

	@Autowired
	private ICertRequestService certRequestService;// service to create new

	// Total pages
	private Integer total = 0;
	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// All Record
	private Integer records = 0;

	// sorting order - asc or desc
	private String sord;

	// get index row - i.e. user click to sort.
	private String sidx;

	private List<DropdownModel> basicStatusList;

	// static map defined to handle sorting in grid.
	private static Map<String, String> sortMap;
	static {

		sortMap = new HashMap<String, String>();
		// device Nick name
		sortMap.put(ECRMSConstants.DEVICE_NICK_NAME,
				ECRMSConstants.DEVICE_NICK_NAME_DBCOL);
		// Hardware Id
		sortMap.put(ECRMSConstants.HARDWARE_ID,
				ECRMSConstants.HARDWARE_ID_DBCOL);
		// Status
		sortMap.put(ECRMSConstants.STATUS, ECRMSConstants.STATUS_DBCOL);
		// Expiry Date
		sortMap.put(ECRMSConstants.EXPIRY_DATE,
				ECRMSConstants.EXPIRY_DATE_DBCOL);
		// Device Type
		sortMap.put(ECRMSConstants.DEVICE_TYPE,
				ECRMSConstants.DEVICE_TYPE_DBCOL);
		// request number
		sortMap.put(ECRMSConstants.REQUEST_NUMBER,
				ECRMSConstants.REQUEST_NUMBER_DBCOL);

	}

	/**
	 * This method load landing page(i.e. Basic search page) This include,
	 * status list data, dealer details (If applicable)
	 * 
	 * @return String the message as success or failure
	 */

	@Action(value = "loadBasicSearch", results = { @Result(name = "success", location = "searchcertrequest/home.jsp", type = "dispatcher") })
	public String loadBasicSearch() {

		logger.info("Start of loadBasicSearch");
		try {

			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());
			// removing session attribute for certificate request if exists.

			if (session.get("certEditRequestModel") != null) {
				session.remove("certEditRequestModel");
			}
			/*
			 * retrieve Status list from database using commonService class.
			 * Status List is filter according to user role
			 */
			basicStatusList = commonService.retrieveStatusData(user);
			logger.info("Status -->", basicStatusList.toString());

			searchCertRequestModel = new SearchCertRequestModel();

			// get dealer number from LDAP
			if (StringUtils.isNotBlank(user.getDealerNum())) {
				// retrieve dealer details
				searchCertRequestModel = searchCertRequestService
						.retrieveDealerDetail(user.getDealerNum());
			}

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error in  loadBasicSearch", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
			throw ecrmsException;
		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		logger.info("End of loadBasicSearch");
		return SUCCESS;
	}

	/**
	 * This method fetch basic search result for Grid on landing page This
	 * method will fetch result at the time of loading as well as if user wants
	 * filter search result.
	 * 
	 * @return String the message as success or failure
	 * @throws Exception
	 */

	@Action(value = "fetchBasicSearchResults", results = { @Result(name = "success", type = "json") })
	public String fetchResults() throws Exception {

		logger.info("Start of fetchResults");
		Integer basicSearchCount = null;
		List<SearchResultCertRequestModel> searchResultCertRequestModelList = null;
		try {
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			// Condition to handle sorting in a grid.
			if (sidx != null && !sidx.equals(ECRMSConstants.EMPTY_STRING)) {
				searchCertRequestModel.setOrderByField(sortMap.get(sidx));
				searchCertRequestModel.setOrderByVal(sord);
			} else {
				// If no sorting is selected, then do the default order by
				// RequestNumber in ascending order.
				searchCertRequestModel.setOrderByField(sortMap
						.get(ECRMSConstants.REQUEST_NUMBER));
				searchCertRequestModel.setOrderByVal(ECRMSConstants.ASC);
			}
			List<String> roleList = user.getUserRoleList();
			// List of SearchResultCertRequestModel retrieve using
			// searchCertRequestService
			int certActivPeriod = Integer
					.parseInt(PropertyReader
							.retrievePropertiesValue(ECRMSConstants.CERT_ACTIVATION_PERIOD));
			int certRenewPeriod = Integer.parseInt(PropertyReader
					.retrievePropertiesValue(ECRMSConstants.CERT_RENEW_PERIOD));

			// Result is depend on User role. if user is dealer User Dealer
			// number is required
			if (roleList.contains(ECRMSConstants.DEALER_USER)) {
				if (StringUtils.isNotBlank(searchCertRequestModel
						.getDealerNum())) {
					logger.info("Basic Search count");

					searchResultCertRequestModelList = searchCertRequestService
							.retrieveCountBasicSearch(searchCertRequestModel,
									roleList, certActivPeriod, certRenewPeriod);
					basicSearchCount = searchResultCertRequestModelList.size();
					logger.info("Basic Search count1" + basicSearchCount);

				}
			} else {
				logger.info("Basic Search count");
				searchResultCertRequestModelList = searchCertRequestService
						.retrieveCountBasicSearch(searchCertRequestModel,
								roleList, certActivPeriod, certRenewPeriod);
				basicSearchCount = searchResultCertRequestModelList.size();
				logger.info("Basic Search count" + basicSearchCount);
			}

			int to = (rows * page);
			int from = to - rows;
			logger.info("offset ->" + from);
			logger.info("limit ->" + (to - from));
			searchCertRequestModel.setOffset(from);
			searchCertRequestModel.setLimit(to - from);

			if (basicSearchCount != null && rows != null && rows != 0) {
				total = (int) Math.ceil((double) basicSearchCount
						/ (double) rows);
				if (getTotal() == 0)
					page = 0;
				logger.info("total ->" + total);
				setRecords(basicSearchCount);
				logger.info("Records are = " + getRecords());
				if (page > getRecords())
					page = getRecords();
				logger.info("page is = " + page);

			}
			// Result is depend on User role. if user is dealer User Dealer
			// number is required
			if (roleList.contains(ECRMSConstants.DEALER_USER)) {
				if (StringUtils.isNotBlank(searchCertRequestModel
						.getDealerNum())) {
					// gridModel =
					// searchCertRequestService.basicSearchCertRequests
					// (searchCertRequestModel,roleList,certActivPeriod,
					// certRenewPeriod);
					if (searchResultCertRequestModelList != null) {
						if (basicSearchCount != 0 && to <= basicSearchCount) {
							logger.info("Total records are :->> "
									+ basicSearchCount
									+ " And To And From are " + to + " , "
									+ from + " resp.");
							gridModel = searchResultCertRequestModelList
									.subList(from, to);
						} else if (to > basicSearchCount) {
							logger.info("Total records are :->> "
									+ basicSearchCount
									+ " And To And From are "
									+ basicSearchCount + " , " + from
									+ " resp.");
							gridModel = searchResultCertRequestModelList
									.subList(from, basicSearchCount);

						}
					}
				}
			} else {
				// gridModel = searchCertRequestService.basicSearchCertRequests(
				// searchCertRequestModel,roleList,certActivPeriod,
				// certRenewPeriod);
				if (searchResultCertRequestModelList != null) {
					if (searchResultCertRequestModelList.size() != 0
							&& to <= basicSearchCount) {
						logger.info("Total records are :->> "
								+ basicSearchCount + " And To And From are "
								+ to + " , " + from + " resp.");
						gridModel = searchResultCertRequestModelList.subList(
								from, to);
					} else if (to > basicSearchCount) {
						logger.info("Total records are :->> "
								+ basicSearchCount + " And To And From are "
								+ basicSearchCount + " , " + from + " resp.");
						gridModel = searchResultCertRequestModelList.subList(
								from, basicSearchCount);
					}
				}
			}

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
			throw ecrmsException;

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		}
		logger.info("End of fetchResults");
		return SUCCESS;
	}

	/**
	 * @return
	 */
	@Action(value = "retrieveLandingDealerDetails", results = { @Result(name = SUCCESS, type = "json") })
	public String retrieveLandingDealerDetails() {

		logger.info("Start of retrieveLandingDealerDetails method");
		try {
			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			if (dealerNumber != null && StringUtils.isNotBlank(dealerNumber)) {
				logger.info("Dealer number entered by corporate user is: "
						+ dealerNumber);

				// details
				SearchCertRequestModel searchModel = searchCertRequestService
						.retrieveDealerDetail(dealerNumber);

				if (null != searchModel) {
					dealerNumber = searchModel.getDealerNum();
					dealerName = searchModel.getDealerName();
				} else {
					dealerNumber = ECRMSConstants.EMPTY_STRING;
					dealerName = ECRMSConstants.EMPTY_STRING;
				}
			}

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
			throw ecrmsException;

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA
							.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		logger.info("End of retrieveLandingDealerDetails method");
		return SUCCESS;
	}

	/**
	 * @return
	 */
	@Action(value = "reactivateCert", results = { @Result(name = SUCCESS, type = "json") })
	public String reactivateCert() {

		logger.info("Start of reactivateCert method ");
		Integer count = 0;
		try {

			UserProfileModel user = (UserProfileModel) session
					.get(ECRMSConstants.USERPROFILE_KEY);

			ActionContext.getContext().setLocale(user.getUserLocale());
			List<String> reqNums = new ArrayList<String>();
			searchCertRequestModel = new SearchCertRequestModel();

			// Tokenize the request id's
			if (StringUtils.isNotBlank(multipleReqNum)) {
				logger.info("mutliple request numbers are  " + multipleReqNum);
				reqNums = Arrays.asList(multipleReqNum.split(","));
			}

			if (StringUtils.isNotBlank(reqNums.toString())) {
				// Prepare List of request Ids'
				for (String reqNumber : reqNums) {
					reqNums.set(reqNums.indexOf(reqNumber), reqNumber.trim());
					logger.info("request numbers is  " + reqNumber);
				}
			}

			List<String> finalReqNums = new ArrayList<String>();
			if (StringUtils.isNotBlank(reqNums.toString())) {
				for (String reqNum : reqNums) {
					// to remove duplicate ids, if any
					if (!finalReqNums.contains(reqNum)) {

						finalReqNums.add(reqNum);

					}
				}

				searchCertRequestModel.setReqNumList(finalReqNums);
				searchCertRequestModel.setUserId(user.getUserId());
				// Reactivate the certificates

				count = searchCertRequestService
						.reactiveCertRequest(searchCertRequestModel);
				if (count > 0) {
					logger.info("certificates got reactivated successfully ");
				}
			}

		} catch (ECRMSException ecrmsEx) {
			logger.error("Error occured is ", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;

		} catch (Exception ex) {
			logger.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_UPDATE_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_UPDATE_DATA
							.description()));
			addActionError(getText(ecrmsException.getDescription()));

			throw ecrmsException;
		}
		logger.info("End of reactiveCertRequestfromLanding method ");

		return SUCCESS;
	}

	/**
	 * @return the multipleReqNum
	 */
	public String getMultipleReqNum() {
		return multipleReqNum;
	}

	/**
	 * @param multipleReqNum
	 *            the multipleReqNum to set
	 */
	public void setMultipleReqNum(String multipleReqNum) {
		this.multipleReqNum = multipleReqNum;
	}

	/**
	 * @return
	 */
	public String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * @param successMessage
	 */
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	/**
	 * @return the gridModel
	 */
	public List<SearchResultCertRequestModel> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel
	 *            the gridModel to set
	 */
	public void setGridModel(List<SearchResultCertRequestModel> gridModel) {
		this.gridModel = gridModel;
	}

	/**
	 * @param searchCertRequestService
	 *            the searchCertRequestService to set
	 */
	public void setSearchCertRequestService(
			ISearchCertRequestService searchCertRequestService) {
		this.searchCertRequestService = searchCertRequestService;
	}

	/**
	 * @return the sord
	 */
	public String getSord() {
		return sord;
	}

	/**
	 * @param sord
	 *            the sord to set
	 */
	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * @return the sidx
	 */
	public String getSidx() {
		return sidx;
	}

	/**
	 * @param sidx
	 *            the sidx to set
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	/**
	 * @return the basicStatusList
	 */
	public List<DropdownModel> getBasicStatusList() {
		return basicStatusList;
	}

	/**
	 * @param basicStatusList
	 *            the basicStatusList to set
	 */
	public void setBasicStatusList(List<DropdownModel> basicStatusList) {
		this.basicStatusList = basicStatusList;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @return the searchCertRequestModel
	 */
	public SearchCertRequestModel getSearchCertRequestModel() {
		return searchCertRequestModel;
	}

	/**
	 * @param searchCertRequestModel
	 *            the searchCertRequestModel to set
	 */
	public void setSearchCertRequestModel(
			SearchCertRequestModel searchCertRequestModel) {
		this.searchCertRequestModel = searchCertRequestModel;
	}

	/**
	 * @return the searchResultCertRequestModel
	 */
	public SearchResultCertRequestModel getSearchResultCertRequestModel() {
		return searchResultCertRequestModel;
	}

	/**
	 * @param searchResultCertRequestModel
	 *            the searchResultCertRequestModel to set
	 */
	public void setSearchResultCertRequestModel(
			SearchResultCertRequestModel searchResultCertRequestModel) {
		this.searchResultCertRequestModel = searchResultCertRequestModel;
	}

	/**
	 * @return the dealerNumber
	 */
	public String getDealerNumber() {
		return dealerNumber;
	}

	/**
	 * @param dealerNumber
	 *            the dealerNumber to set
	 */
	public void setDealerNumber(String dealerNumber) {
		this.dealerNumber = dealerNumber;
	}

	/**
	 * @return the dealerName
	 */
	public String getDealerName() {
		return dealerName;
	}

	/**
	 * @param dealerName
	 *            the dealerName to set
	 */
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	/**
	 * @param certRequestService
	 *            the certRequestService to set
	 */
	public void setCertRequestService(ICertRequestService certRequestService) {
		this.certRequestService = certRequestService;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;

	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the records
	 */
	public Integer getRecords() {
		return records;
	}

	/**
	 * @param records
	 *            the records to set
	 */
	public void setRecords(Integer records) {
		this.records = records;

		if (this.records > 0 && this.rows > 0) {
			this.total = (int) Math.ceil((double) this.records
					/ (double) this.rows);
		} else {
			this.total = 0;
		}
	}

}
