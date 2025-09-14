<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url action="advanceSearchRequestResult" var="reloadUrl"></s:url>
<script type="text/javascript">

//setting pagination default values

var jgrid=$("#certificatesList");
$.extend($.jgrid.defaults, {
	
	emptyrecords: getMessage('text.emptyrecords'),
    recordtext: getMessage('text.recordtext'), // Overriding value in grid.locale-en.js
    pgtext: getMessage('text.pgtext')
   
});

var countSearchBtnClick = 0;
jQuery().ready(function (){
		
			$("#search").attr("disabled", false);
	        $("#search").focus();
			$("#requestNumber").focus();
			
		$("#search").click(function(){
             
             countSearchBtnClick++; 
             		
		});
		});	
//prevent manual entry for different dates textfields
function removeTxt(cObj) 
{
	cObj.value='';
}

//Check Enter Button Functionality for Advanced search page
function checkAdvancedSubmit(e) 
{
	//Check Enter button ASCII code and allow to submit  
	if(e && e.keyCode == 13)    
    {  
         validateAdvancedSearch();  
         return false;
    }
 } 
 //check pasted value for request number field
function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}
//check pasted value for request number field, if it is other than numeric value flush the field
$("#requestNumber").change(function(e) {
 
     var value =  $("#requestNumber").val();
     
     //if request number contain *
     while(value.indexOf("*")!= -1)
		{
		value = value.replace("*","");
		}


     //if request number not contains any *
	 if(value.indexOf("*") == -1)
	  {   if(isNumber(value)){
	     	return true;
	     }else{
	     	$("#requestNumber").val('');
	     }
     }
 });
 //prevent entering alphabets and special chars
function numbersonly(){
 $("#requestNumber").keypress(function(e) {     
 var a = [];     
 var k = e.which;  
 for (i = 48; i < 58; i++)         
 a.push(i);      
 if (!($.inArray(k,a)>=0) && (k != 42))         
 e.preventDefault(); 
 });
}
//prevent special chars (except * for wildcard search) for Hardware ID
function noSpecialChar(){
 $("#hardwareIdField").keypress(function(e) {  
 
 var k = e.which;     
 if((k >= 32 && k <= 41) || (k >= 43 && k <= 47)|| (k >= 58 && k <= 64)|| (k >= 91 && k <= 96)|| (k >= 123 && k <= 126))
 {
 e.preventDefault();
 }
 });
}
//prevent special chars (except * for wildcard search) for Dealer number
function noSpecialChars4dlr(){
 $("#dealerNumber").keypress(function(e) {  
 
 var k = e.which;     
 if((k >= 32 && k <= 41) || (k >= 43 && k <= 47)|| (k >= 58 && k <= 64)|| (k >= 91 && k <= 96)|| (k >= 123 && k <= 126))
 {
 e.preventDefault();
 }
 });
}
//prevent special chars for Request number
function isSpecialChar(val)
{
	var strValidChars = "<>@!#$%^&( )_+[]{}?:;|'\"\\,./~`-=";
    var strChar;
    var blnResult = false;

    if (val.length == 0) return false;

    //  test strString consists of any invalid characters listed above
    for (var i = 0; i < val.length && blnResult == false; i++) {
        strChar = val.charAt(i);
        if (strValidChars.indexOf(strChar) == -1) {
           blnResult = false;
        }
        else if (strValidChars.indexOf(strChar) != -1)
        {
        blnResult = true;
        }
    }
    return blnResult;

}
//remove pasted special chars (except * for wildcard search) from Hardware ID
$("#hardwareIdField").change(function(e) {
 
     var value =  $("#hardwareIdField").val();
    if(isSpecialChar(value)){
     	$("#hardwareIdField").val('');
     }else{
     	return true;
     } 
 });
//remove pasted special chars (except * for wildcard search) from Dealer NUmber
 $("#dealerNumber").change(function(e) {
 
     var value =  $("#dealerNumber").val();
    if(isSpecialChar(value)){
     	$("#dealerNumber").val('');
     }else{
     	return true;
     } 
 });
 


 
	//subscriber for actions to do after reload grid completion
	$.subscribe('completecheck',function(event,ref)
	{
	 
		var gridrowcount = jQuery("#certificatesList").jqGrid('getGridParam', 'records');
		//prevent double hit
		$("#search").attr("disabled", false);
		var exportbtn = document.getElementById('exportToExcel');
		if(countSearchBtnClick!=0)
		{if(gridrowcount == 0)
		{
			//if no data found display error msg and hide export to excel button
			error_container = document.getElementById('advancedSearchDiv');
			error_container.innerHTML="<li>"+getMessage('message.resulterror') +"</li>";
			error_container.style.display='';
			exportbtn.style.display='none';
		}
		else
		{
			$('#exportToExcel').hide();
		}}
	});
	
</script>

<script type="text/javascript" src="js/searchcertrequest/advancedSearch.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<div id="advancesearch" class="span-24" onkeypress="return checkAdvancedSubmit(event)">
	
<s:form id="advancedSearchForm" tabindex="1">
	<div id="advancedSearchDiv" style="color:red; margin-left:30px;font-weight:bold" ></div>
	<s:actionerror cssStyle="font-size: 2px; margin-left:30px;"/>
	<s:actionmessage cssStyle="font-size: 2px; margin-left:30px;"/>
	
	<fieldset>
      	<legend><s:label key="legend.advancedSearch" theme="simple" /></legend>
<div class="span-14">
   		
   		<div class="span-4">
	   		<s:textfield cssClass="span-3" onkeypress="numbersonly()" key="label.requestNumber" tabindex="2" id="requestNumber" maxlength="10" name="searchCertRequestModel.requestNumStr" />
   		</div>   
   		
   		<div class="span-4 last">
	   		<s:textfield cssClass="span-3" onkeypress="noSpecialChars4dlr()" key="label.dealerNumber" tabindex="3" maxlength="7" id="dealerNumber"  name="searchCertRequestModel.dealerNum" />
   		</div>
   		
   		<div class="span-5 last">
	   		<s:textfield cssClass="span-5" onkeypress="noSpecialChar()" key="label.hardwareId" tabindex="4" maxlength="32" id="hardwareIdField" name="searchCertRequestModel.hardwareId"/>
        </div>
</div>
<div class="clear span-10">
        <div class="span-4">
	        <s:select key="label.status" cssClass="span-3" id="status" tabindex="5" list="statusList" name="searchCertRequestModel.status"  listValue="value" listKey="key"/>
        </div>
        
        <div class="span-4 last">
	        <s:select key="label.deviceType" cssClass="span-3" id="deviceType" tabindex="6" list="deviceTypeList" name="searchCertRequestModel.deviceType"  listValue="value" listKey="key" />
        </div>
</div>
<div class="clear span-12">  
        <div class="span-4" style="margin-top:25px;">
        	<s:label key="label.certIssueDateRange" theme="simple" />
        </div>
        
        <div class="span-4 last">
	        <sj:datepicker id="fromdate1" key="label.from" cssClass="span-3"  
		        name="searchCertRequestModel.fromIssueDate" showOn="focus"  tabindex="7"
		        onkeyup="return removeTxt(this);" changeMonth="true" displayFormat="mm/dd/yy" 
		        changeYear="true" />
        </div>
        
        <div class="span-3 last">
	        <sj:datepicker id="todate1" key="label.to" cssClass="span-3" 
	        	name="searchCertRequestModel.toIssueDate" showOn="focus" tabindex="8"
	        	onkeyup="return removeTxt(this);" changeMonth="true" displayFormat="mm/dd/yy" changeYear="true" />
        </div>
</div>
<div class="clear span-12">
        <div class="span-4" style="margin-top:25px;">
	        <s:label key="label.certExpiringDateRange" theme="simple"/>
        </div>
        
        <div class="span-4 last">
	        <sj:datepicker id="fromdate2" key="label.from" cssClass="span-3" 
	        	name="searchCertRequestModel.fromExpiryDate"  showOn="focus"  tabindex="9"
	        	onkeyup="return removeTxt(this);" changeMonth="true" displayFormat="mm/dd/yy" changeYear="true"/>
        </div>
        
        <div class="span-3 last">
	        <sj:datepicker id="todate2" key="label.to" showOn="focus"  cssClass="span-3 last" tabindex="10" name="searchCertRequestModel.toExpiryDate" onkeyup="return removeTxt(this);" changeMonth="true" displayFormat="mm/dd/yy" changeYear="true"/>
        </div>
</div>
<div class="clear span-12">
 		<div class="span-4" style="margin-top:25px;">
        	<s:label key="label.revocatDateRange" theme="simple" />
        </div>
        
        <div class="span-4 last">
	        <sj:datepicker id="fromdate3" key="label.from" showOn="focus" cssClass="span-3" tabindex="11"
		        name="searchCertRequestModel.fromRevokedDate" onkeyup="return removeTxt(this);" 
		        changeMonth="true" displayFormat="mm/dd/yy" changeYear="true" />
        </div>
        
        <div class="span-3 last">
	        <sj:datepicker id="todate3" key="label.to" showOn="focus" cssClass="span-3 last" tabindex="12"
	        name="searchCertRequestModel.toRevokedDate" onkeyup="return removeTxt(this);" 
	        changeMonth="true" displayFormat="mm/dd/yy" changeYear="true" />
        </div>
  </div>    
 		<div class="clear span-14">
 		<div class="clear span-2" style="margin-right:0.75em;margin-top:2em;">
			 <sj:a button="true" id="search" cssClass="span-2" tabindex="13" onclick="javascript:beforeValidateAdvancedSearch();">
			 	<s:property value="getText('button.search')" /></sj:a>
		</div>
	    
	    <div class="span-2 last" style="margin-left:0.75em;margin-top:2em;">
			 <sj:a button="true" id="reset" cssClass="span-2" tabindex="13" href="advanceSearchRequest.action" targets="content" >
			<s:property value="getText('button.reset')" /></sj:a>
		</div>
		 </div>   
	    </fieldset>
	    <s:hidden id="buttonClicked" name="buttonClicked"
				value="false" />
	</s:form>

</div>
<!-- Grid model For search Result -->
<div id="searchResult" class="span-24 last">
<fieldset>
<legend><s:label key="legend.searchResult" theme="simple"></s:label></legend>
<br style="line-height:0.9"/>
<img id="indicator" src="assets/indicator.gif" alt="Loading..." style="display:none"/>				
<div class="span-22">
<sjg:grid 
			gridModel="gridModel" 
			errorElementId="gridLoadingError"
        	reloadTopics="reloadGridTable" 
        	onErrorTopics="handleError" 
        	onGridCompleteTopics="completecheck"
        	loadingText="%{getText('label.loading')}"
        	formIds="advancedSearchForm" 
        	href="%{reloadUrl}" 
        	id="certificatesList" 
        	dataType="json" 
        	width="1150" 
        	height="180" rowList="50,75,100"
			rowNum="50" tabindex="13"
        	pager="true" viewrecords="true"
        	>
     <sjg:gridColumn tabindex="14" name="requestNum" id="requestNum" align="left" index="requestNum" width="85" title="%{getText('label.requestNumber')}" cssStyle="font-weight:bold;" sortable="true" key="true"/>
     <sjg:gridColumn tabindex="15" name="dealerNum" id="dealerNum" align="left" index="dealerNum" width="80" title="%{getText('label.dealerNumber')}" cssStyle="font-weight:bold;" sortable="true"/>
     <sjg:gridColumn tabindex="16" name="deviceNickName" id="deviceNickName" align="left" index="deviceNickName" width="100" title="%{getText('label.deviceName')}" cssStyle="font-weight:bold;" sortable="true" />
     <sjg:gridColumn tabindex="17" name="hardwareId" id="hardwareId" align="left" index="hardwareId" width="200" title="%{getText('label.hardwareId')}" cssStyle="font-weight:bold;" sortable="true"/>
	 <sjg:gridColumn tabindex="18" name="deviceType" id="deviceType" align="left" index="deviceType" width="100" title="%{getText('label.deviceType')}" cssStyle="font-weight:bold;" sortable="true"/>
     <sjg:gridColumn tabindex="19" name="status" id="status" align="left" index="status" title="%{getText('label.status')}" width="75" cssStyle="font-weight:bold;" sortable="true"/>
     <sjg:gridColumn tabindex="20" name="issueDate" id="issueDate" align="left" index="issueDate"  width="100" title="%{getText('label.issueDate')}" cssStyle="font-weight:bold;" sortable="true"/>
     <sjg:gridColumn tabindex="21" name="expiryDate" id="expiryDate" align="left" index="expiryDate"  width="100" title="%{getText('label.expirationDate')}" cssStyle="font-weight:bold;" sortable="true"/>
     <sjg:gridColumn tabindex="22" name="revokedDate" id="revokedDate" index="revokedDate" width="100" title="%{getText('label.revokedDate')}" cssStyle="font-weight:bold;" sortable="true"/>
        

</sjg:grid>

</div>
</fieldset>
</div>
<!-- Provide button to Export result to excel sheet -->
<div class="push-1 span-15">

<div class="span-15" align="left" id="exportToExcel">
<a  tabindex="22" href="<s:url action='exportSearchResult'/>" >
<img style="float: left" src='assets/icon_xl.gif' height="32px" width="34px" />
</a>
<div class="push-1" style="margin-left: 30px;margin-top:13px;">
<s:if test='#session.userProfile.userLanguage.equals("fr")'>
<s:label key="label.exportToExcel" theme="simple" cssClass="span-7" cssStyle="margin-left:-20px; float:center;"/>
</s:if>
<s:else>
<s:label key="label.exportToExcel" theme="simple" cssClass="span-7" cssStyle="margin-left:-20px; float:center; "/>
</s:else>
</div>



</div>


</div>
               