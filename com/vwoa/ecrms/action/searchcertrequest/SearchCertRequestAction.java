
package com.vwoa.ecrms.action.searchcertrequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.vwoa.ecrms.service.common.ICommonService;
import com.vwoa.ecrms.service.searchcertrequest.ISearchCertRequestService;
import com.vwoa.ecrms.util.ECRMSConstants;
import com.vwoa.ecrms.util.exception.ECRMSErrorMessage;
import com.vwoa.ecrms.util.exception.ECRMSException;

/**
 *Class searchCertRequestAction is used to handle all the actions related to advanced search
 *Class to handle all the actions related to Advanced Search 
 * 
 * @author bhagyashri_ajgare
 *
 */
@ParentPackage("ecrms-default")
@Namespace("/")
public class SearchCertRequestAction extends BaseAction implements SessionAware{

	private static final long serialVersionUID = -3094958750964676285L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchCertRequestAction.class);

	private Map<String, Object> session;
	
	private SearchCertRequestModel searchCertRequestModel;
	
	// result List
	private List<SearchResultCertRequestModel> gridModel;

	@Autowired
	private ISearchCertRequestService searchCertRequestService;

	@Autowired
	private ICommonService commonService;
	// Total pages
	private Integer total = 0;
	 //get how many rows we want to have into the grid - rowNum attribute in the grid
	private Integer rows = 0;
	//Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// All Record
	private Integer records = 0;
	// sorting order ascending or descending
	// sorting order - asc or desc
	private String sord;

	// get index row - i.e. user click to sort.
	private String sidx;

	private List<DropdownModel> statusList;
	private List<DropdownModel> deviceTypeList;

	// static map defined to handle sorting in grid.
	private static Map<String, String> sortMap; 
	static{
		
		sortMap = new HashMap<String, String>();
		//Device nick name
		sortMap.put(ECRMSConstants.DEVICE_NICK_NAME, ECRMSConstants.DEVICE_NICK_NAME_DBCOL);
		// hardware id
		sortMap.put(ECRMSConstants.HARDWARE_ID, ECRMSConstants.HARDWARE_ID_DBCOL);
		//Status
		sortMap.put(ECRMSConstants.STATUS, ECRMSConstants.STATUS_DBCOL);
		//Expired date
		sortMap.put(ECRMSConstants.EXPIRY_DATE, ECRMSConstants.EXPIRY_DATE_DBCOL);
		//Device type
		sortMap.put(ECRMSConstants.DEVICE_TYPE, ECRMSConstants.DEVICE_TYPE_DBCOL);
		//dealer number
		sortMap.put(ECRMSConstants.DEALER_NUMBER, ECRMSConstants.DEALER_NUMBER_DBCOL);
		// request number
		sortMap.put(ECRMSConstants.REQUEST_NUMBER, ECRMSConstants.REQUEST_NUMBER_DBCOL);
		//issue date
		sortMap.put(ECRMSConstants.ISSUE_DATE, ECRMSConstants.ISSUE_DATE_DBCOL);
		//revoked date
		sortMap.put(ECRMSConstants.REVOKED_DATE, ECRMSConstants.REVOKED_DATE_DBCOL);
	}
	/**
	 *  This method load Advanced search page
	 * This include, status list data, dealer details (If applicable), device type list
	 * 
	 * 
	 * @return String SUCCESS / ERROR
	 */
	@Action(value="advanceSearchRequest", results={@Result(name="success", location="searchcertrequest/advancedSearch.jsp" , type="dispatcher")})
	public String advanceSearchCertRequest() {
		LOGGER.info("Start of advanceSearchCertRequest method");

		try {
		
			UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			
			//Invoke common service to set the status list
			statusList = commonService.retrieveStatusData();
			
			//Invoke common service to set the device type list
			deviceTypeList = commonService.retrieveDeviceTypeData("");
			
			
		}
		catch (ECRMSException ecrmsEx) {
			LOGGER.error("Error in  advanceSearchCertRequest", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
			throw ecrmsException;
		}
		catch (Exception ex) {
			LOGGER.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		
		LOGGER.info("End of advanceSearchCertRequest");
		return SUCCESS;
	}
	
	/**
	 * This method used to retrieve or fetch advanced search result
	 * This method will fetch result at the time of loading as well as if user wants filter search result. 
	 * 
	 * @param searchCertRequestModel
	 * @return String SUCCESS / ERROR
	 */
	@Action(value="advanceSearchRequestResult",results={@Result(name="success", type="json")})
	public String advanceSearchRequestMethod()
	{
		LOGGER.info("Start of advanceSearchRequestResult");
		Integer searchCount = null;
		try {
			UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			if(sidx != null &&  ! sidx.equals(ECRMSConstants.EMPTY_STRING))
			{				
				searchCertRequestModel.setOrderByField(sortMap.get(sidx));
				searchCertRequestModel.setOrderByVal(sord);				
			}
			else
			{
				// If no sorting is selected, then do the default order by if any.
				searchCertRequestModel.setOrderByField(sortMap.get(ECRMSConstants.REQUEST_NUMBER));
				searchCertRequestModel.setOrderByVal(ECRMSConstants.ASC);
			}
			searchCount = searchCertRequestService.retrieveCountAdvancedSearch(searchCertRequestModel);
			session.put( ECRMSConstants.SEARCH_COUNT,searchCount );
			session.put(ECRMSConstants.SEARCH_MODEL, searchCertRequestModel);
			int to = (rows * page);    
			int from = to - rows; 
			
			searchCertRequestModel.setOffset(from);
			searchCertRequestModel.setLimit(to-from);
			
			gridModel = searchCertRequestService.searchCertRequests(searchCertRequestModel);
			if(searchCount != null && rows != null && rows !=0)
			{
			total =(int) Math.ceil((double)searchCount / (double)rows);
			if(getTotal() == 0) page =0;
			setRecords(searchCount);
			}
		} 
		catch (ECRMSException ecrmsEx) {
			LOGGER.error("Error in  advanceSearchRequestMethod", ecrmsEx);
			ECRMSException ecrmsException = new ECRMSException(ecrmsEx
					.getErrorCode(), getText(ecrmsEx.getDescription()));
			addActionError(getText(ecrmsEx.getDescription()));
			throw ecrmsException;
		}
		catch (Exception ex) {
			LOGGER.error("Exception occured is : ", ex);
			ECRMSException ecrmsException = new ECRMSException(
					ECRMSErrorMessage.Constants.ERR_SELECT_DATA.status(),
					getText(ECRMSErrorMessage.Constants.ERR_SELECT_DATA.description()));

			addActionError(getText(ecrmsException.getDescription()));
			throw ecrmsException;
		}
		
		LOGGER.info("End of advanceSearchRequestResult");
		return SUCCESS;
		
	}	
	
	/**
	 * This method used to retrieve or fetch advanced search result
	 * @param searchCertRequestModel
	 * @return String SUCCESS / ERROR
	 */
	@SuppressWarnings("unchecked")
	@Action(value="exportSearchResult")
	public String exportSearchResults()
	{
		LOGGER.info("Start of exportSearchResults");
		try {
			UserProfileModel user = (UserProfileModel) session.get(ECRMSConstants.USERPROFILE_KEY);
			ActionContext.getContext().setLocale(user.getUserLocale());
			
//			List<SearchResultCertRequestModel> resultList = (ArrayList)session.get(ECRMSConstants.SEARCH_EXPORT_LIST);
			Integer resultCount = (Integer)session.get(ECRMSConstants.SEARCH_COUNT);
			SearchCertRequestModel searchCertRequestModel = (SearchCertRequestModel)session.get(ECRMSConstants.SEARCH_MODEL);
			searchCertRequestModel.setLimit(null);
			searchCertRequestModel.setOffset(null);
			
			List<SearchResultCertRequestModel> resultList = null;	
			resultList=searchCertRequestService.searchCertRequests(searchCertRequestModel);
			List<String> headerList = new ArrayList<String>();
			
			headerList.add(getText("label.workSheetName"));
			headerList.add(getText("label.requestNumber"));
			headerList.add(getText("label.dealerNumber"));
			headerList.add(getText("label.deviceName"));
			headerList.add(getText("label.hardwareId"));
			headerList.add(getText("label.deviceType"));
			headerList.add(getText("label.status"));
			headerList.add(getText("label.issueDate"));
			headerList.add(getText("label.expirationDate"));
			headerList.add(getText("label.revokedDate"));
			
			searchCertRequestService.exportCertRequestToExcel(resultList,headerList);
			
		} 
		catch (ECRMSException ecrmsEx) {
			LOGGER.error("Error in  exportSearchResults", ecrmsEx);
			
		}
		catch (Exception ex) {
			LOGGER.error("Exception occured is : ", ex);
					
		}
		LOGGER.info("End of exportSearchResults");
		return NONE;
		
	}	
	
	
	/**
	 * @return the gridModel
	 */
	public List<SearchResultCertRequestModel> getGridModel() {
		return gridModel;
	}
	/**
	 * @param gridModel the gridModel to set
	 */
	public void setGridModel(List<SearchResultCertRequestModel> gridModel) {
		this.gridModel = gridModel;
	}
	
	/**
	 * @param searchCertRequestService the searchCertRequestService to set
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
	 * @param sord the sord to set
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
	 * @param sidx the sidx to set
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
			
	public List<DropdownModel> getStatusList() {
		return statusList;
	}

	/**
	 * @param statusList
	 *            the statusList to set
	 */
	public void setStatusList(List<DropdownModel> statusList) {
		this.statusList = statusList;
		
	}
	/**
	 * @return the deviceTypeList
	 */
	public List<DropdownModel> getDeviceTypeList() {
		return deviceTypeList;
	}
	/**
	 * @param deviceTypeList the deviceTypeList to set
	 */
	public void setDeviceTypeList(List<DropdownModel> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}
	
	/**
	 * @param commonService the commonService to set
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
	 * @param searchCertRequestModel the searchCertRequestModel to set
	 */
	public void setSearchCertRequestModel(
			SearchCertRequestModel searchCertRequestModel) {
		this.searchCertRequestModel = searchCertRequestModel;
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
	 * @param total the total to set
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
	 * @param rows the rows to set
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
	 * @param page the page to set
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
	 * @param records the records to set
	 */
	public void setRecords(Integer records) {
		this.records = records;
		
		if(this.records > 0 && this.rows > 0){
			this.total = (int)Math.ceil((double) this.records/(double) this.rows);
		}else{
			this.total = 0;
		}
	}

}

