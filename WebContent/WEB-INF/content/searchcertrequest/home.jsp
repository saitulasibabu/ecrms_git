<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<s:url var="editCertReqURL" action="certrequest/viewCertRequest" />

<s:url var="printCertReqURL" action="certrequest/printCertRequest" />


<script type="text/javascript" src="js/jqueryjs/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/searchcertrequest/basicSearch.js"/></script>
<script type="text/javascript" src="js/searchcertrequest/download.jQuery.js"/></script>

<script type="text/javascript">

//setting pagination default values
var jgrid=$("#certificates");
$.extend($.jgrid.defaults, {
	emptyrecords: getMessage('text.emptyrecords'),
    recordtext: getMessage('text.recordtext'), // Overriding value in grid.locale-en.js
    pgtext: getMessage('text.pgtext')
      
});
	
//Variable to count the number of clicks on Search Button
var countButtonClick = 0;
var revkBtnClicked = false;	  // Flag to check whether revoke button is clicked or not	

	// Check Enter Button Functionality for Basic search page
function checkSubmit(event) 
{
   
       //Check Enter button ASCII code and allow to submit  
	if(event && event.keyCode == 13)    
    {  
       validateSearchCertRequest();   
       
       return false;
    }
    
 }
	//Subscribers
	//subscriber to check functionality of select all checkbox and prepare data for action
	var lastRowsState=null;
	$.subscribe('selectAllCheck', function (event,ref) { 
		
	 	selectedIds = jQuery("#certificates").jqGrid('getGridParam','selarrrow');
	 	
	 	if(lastRowsState == null)
	 	{
	 	initcount();
	 	lastRowState=null;
	 	}
		var idChk;
		var isChecked;
		
		//filter duplicate call for same event 
		if(event.originalEvent.status != lastRowsState ){
		
		reqIds2 = new Array();
		multipleCreateArray= new Array();
		error_container = document.getElementById('basicSearchDiv');
		error_container.innerHTML='';
		error_container.style.display='none';
		//success msg div
		success_container = document.getElementById('basicSearchSuccessDiv');
		success_container.innerHTML='';
		success_container.style.display='none';
		for (i = 0; i < selectedIds.length; i++)
		{
		var data = jQuery("#certificates").jqGrid('getRowData',selectedIds[i]);
			idChk = "#jqg_certificates_"+data.requestNum;
			isChecked = $(idChk).is(':checked');
			
			activateButton(isChecked, data.statuscde, data.requestNum, data.fileName, data.multipleCreateInd, data.expiryDate,data.certActivationInd);
			
		
		}
			var isUnchecked = event.originalEvent.status;
			if(!isUnchecked){
				
				lastRowsState = null;
				initcount();
			}
			lastRowsState = event.originalEvent.status;
		}
		
	});
	
	
	var lastRowState=null;
 	// subscriber to check single row select
 	$.subscribe('selectRowCheck', function (event,ref) { 
 		  
		
		$("#cb_certificates").removeAttr('checked');
		lastRowsState = null;
		error_container = document.getElementById('basicSearchDiv');
	error_container.innerHTML='';
	error_container.style.display='none';
	//success msg div
	success_container = document.getElementById('basicSearchSuccessDiv');
	success_container.innerHTML='';
	success_container.style.display='none';
		var data = jQuery("#certificates").jqGrid('getRowData',event.originalEvent.id);
		
		//filter duplicate call for same event
		if(event.originalEvent.id+event.originalEvent.status != lastRowState ){
				activateButton(event.originalEvent.status, data.statuscde, data.requestNum, data.fileName,data.multipleCreateInd,data.expiryDate,data.certActivationInd);
				lastRowState = event.originalEvent.id+event.originalEvent.status;
			
		}
		
		
	});
	
	
	// subscriber to do actions on completion of grid load
	$.subscribe('completecheck',function(event,ref)
	{
	
	var gridrowcount = jQuery("#certificates").jqGrid('getGridParam', 'records');
	
	$("#search").attr("disabled", false);
	$("#print").attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#createNewCert").attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#dwnldCert").attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#revokeCert").attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#reactivCert").attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	
	// Added by Sanjana: IE project changes
	// need to check
	/* $('#print1').attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$('#createNewCert1').attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$('#dwnldCert1').attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$('#revokeCert1').attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$('#reactivCert1').attr("class","ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	 */
	if(!checkResultCriteria()&& (countButtonClick!=0) &&(!revkBtnClicked)){
               if(gridrowcount == 0)
				{
				error_container = document.getElementById('basicSearchDiv');
				error_container.innerHTML="<li>"+getMessage('message.resulterror') +"</li>";
				error_container.style.display='';
					
					$("#status").focus();
					$("#status").blur();
					
				}	
				
	}
	if(gridrowcount == 0){
	$("#cb_certificates").attr("disabled", true);
	}
	else{
				$("#cb_certificates").attr("disabled", false);
		}
		
	});
	
	//subscriber to do actions before grid loading
	$.subscribe('beforeloadcheck',function(event,ref)
	{
	
	initcount();	
	lastRowsState=null;					// 
	lastRowState=null;
	
	});

	

// to open Print page for given Request number's certificate
function openPrintCertReqDialog(requestNum){
	
	var error_container = document.getElementById('basicSearchDiv');
	var success_container = document.getElementById('basicSearchSuccessDiv');
		
	error_container.style.dispaly='none';
	success_container.style.dispaly='none';
	
	$("#printCertReqDialog").dialog({
		closeOnEscape: true,    open: function(event, ui) { $(".ui-dialog-titlebar-close").show(); } 
		}); 
		
    $("#printCertReqDialog").load("<s:property value="printCertReqURL" />?certRequestModel.requestNum="+requestNum,
    
    	function(response, status, xhr) {
				if (status == "error") {
					var msg = getMessage('err.unable.retrieve');
					$("#printCertReqDialog").html(msg + xhr.status + " " + xhr.responseText);
				}
			});
	$("#printCertReqDialog").dialog('open');
	
}

// to open Edit given Request number's certificate by clicking on Device Name Link
function openEditCertReqDialog(requestNum){
	
		
		//clearing error messages
		var error_container = document.getElementById('basicSearchDiv');
		var success_container = document.getElementById('basicSearchSuccessDiv');
		
		error_container.style.display='none';
		success_container.style.display='none';
		$("#nav").hide();
		
		//$("#editCertReqDialog").dialog(); 
		$("#editCertReqDialog").dialog({
		closeOnEscape: false,    open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); } 
		});
	    $("#editCertReqDialog").load("<s:property value="editCertReqURL" />?certRequestModel.requestNum="+requestNum,
	    
	    	function(response, status, xhr) {
					if (status == "error") {
						var msg = getMessage('err.unable.retrieve');
						$("#editCertReqDialog").html(msg + xhr.status + " " + xhr.responseText);
					}
				});
		$("#editCertReqDialog").dialog('open');
		 	
	}

	// to handle grid level error
	$.subscribe('handleError', function(event, data) {
	 	    $('#gridLoadingError').html(event.originalEvent.request.responseText);
	 
 	});

	//By deafault, at the time of page load all the buttons are disabled
	jQuery().ready(function (){
		 
		 revkBtnClicked = false;
		 initcount();	
		 
		 var loadingText=getMessage('text.loading');
					
		$("#print").attr("disabled", true);
		$("#createNewCert").attr("disabled", true);
		$("#dwnldCert").attr("disabled", true);
		$("#revokeCert").attr("disabled", true);
		$("#reactivCert").attr("disabled", true);
		
		// Added by Sanjana: IE project changes
		$('#print1').attr('disabled', 'disabled');
		$('#createNewCert1').attr('disabled', 'disabled');
		$('#dwnldCert1').attr('disabled', 'disabled');
		$('#revokeCert1').attr('disabled', 'disabled');
		$('#reactivCert1').attr('disabled', 'disabled');
		
		$.subscribe('editCertReqDialogClosed', function(event,data) {
		$("#nav").show();
		$('#editCertReqDialog').html("<img src='assets/indicator.gif' align='middle'/>"+loadingText);
		});
		
		$.subscribe('printCertReqDialogClosed', function(event,data) {
		$('#printCertReqDialog').html("<img src='assets/indicator.gif' align='middle'/>"+loadingText);
		});
		$("#search").click(function(){
             
             countButtonClick++; 
             revkBtnClicked = false;		
		});
		$("#revokeCert").click(function(){
             
             revkBtnClicked = true;
            	
		});	
		
		// Added by Sanjana: IE project changes
		// Check with surabhi and add for firefox
		/* $('#revokeCert1').click(function(){
             
             revkBtnClicked = true;
            	
		}); */
			
			$("#search").attr("disabled", false);
			$("#search").focus();
			$("#dealerNumber").focus();
			$("#status").focus();
			$("#status").blur();
	});
	

</script>



<div id="mainHome" class="span-24" >
	<div id="home" class="span-24" onkeypress="return checkSubmit(event)">
		<s:form id="homeForm" name="homeForm" tabindex="1">
			<s:hidden id="multipleReqNum" name="multipleReqNum"></s:hidden>
			<div id="basicSearchDiv" style="color:red; margin-left:30px;font-weight: bold;"></div>
			<div id="basicSearchSuccessDiv" style="color:#0066CC; margin-left:30px;font-weight: bold;"></div>
			<s:actionerror cssStyle="font-size: 2px;"/>
			<s:actionmessage cssStyle="font-size: 2px;"/>
			<div id="loadingimg" style="margin-left:1px;display:none;" >
			<img id="indctImg" src="assets/indicator.gif" style="float:left;"/>
			<s:label key="label.processing" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:101%" />
			</div>
			<s:if test='#session.userProfile.userLanguage.equals("fr")'>
 				<s:param name="request_locale" >fr</s:param>
 				<s:hidden id="locale" value="fr"></s:hidden>
			</s:if>
	<div class="clear"></div>
			<!-- To set Search criteria -->
			<fieldset>
				<legend><s:label key="legend.search" theme="simple" /></legend>

				<s:if test='#session.userProfile.userRoleList.contains("ECRMS_DLR")'>
					<div class="span-4">
						<s:textfield key="label.dealerNumber" cssStyle="width:120px;" id="dealerNumber" value="%{searchCertRequestModel.dealerNum}" name="searchCertRequestModel.dealerNum" disabled="true" />
						<s:hidden id="dealerNumber" name="searchCertRequestModel.dealerNum" />
					</div>
				</s:if>
				
				<s:else>
					<div class="span-4">
						<s:textfield key="label.dealerNumber" id="dealerNumber" cssStyle="width:120px;" tabindex="2" name="searchCertRequestModel.dealerNum" title="%{getText('tooltip.dealerNumber')}" maxlength="7"/>
					</div>
				</s:else>
	
				<div class="span-4">
					<s:select id="status" key="label.status" cssStyle="width:120px;" value="" tabindex="3" title="%{getText('tooltip.status')}" list="basicStatusList" name="searchCertRequestModel.status" listKey="key" listValue="value"/>
				</div> 

				<div class="span-3 last" style="margin-top:18px;">
					<sj:a button="true" id="search" tabindex="4" 
						onclick="javascript:beforeValidate(); return false;">
						<s:property value="getText('button.search')" />
					</sj:a>
				</div>
			</fieldset>
		</s:form>
		<img id="indicator" src="assets/indicator.gif" style="display:none;"/>
	</div> <!-- closing div id=home -->
<!-- Search Result  -->
	<div class="span-24">
		<div id="searchResult" class="span-24 last">
			<fieldset>
				<legend><s:label key="legend.certMgmt" theme="simple" /></legend>
				<br style="line-height:0.7"/>
				<div class="span-8 ">
					<s:label key="label.dealerNumber" cssClass="span-3" theme="simple"></s:label>
					<s:if test='#session.userProfile.userRoleList.contains("ECRMS_DLR")'>
						<s:label value="%{searchCertRequestModel.dealerNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:101%" /> 
					</s:if>
					<s:else>
						<div id="dealerNumLbl" class="span-4" style="font-weight:normal;margin-top:-3px;"></div>
					</s:else>					
				</div>

				<div class="span-8 last">
					<s:label key="label.dealerName"  cssClass="span-3" theme="simple"> </s:label>
					<s:if test='#session.userProfile.userRoleList.contains("ECRMS_DLR")'>
						<s:label value="%{searchCertRequestModel.dealerName}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:101% " /> 
					</s:if>
					<s:else>
						<div id="dealerNameLbl" class="span-4 last" style="font-weight:normal;margin-top:-3px;"></div>
					</s:else>					
				</div>			
				<div class="span-22"  style="height:10px;"></div>
				<div class="clear"></div>

				<s:url action="fetchBasicSearchResults" var="reloadUrl"></s:url>

				<div id="gridLoadingError"></div>
				
				<sjg:grid gridModel="gridModel" 
						errorElementId="gridLoadingError"
				        reloadTopics="reloadGridTable" 
				        onErrorTopics="handleError"
				        formIds="homeForm"  
				        loadingText="%{getText('label.loading')}"
				        pagerPosition="center"
				        href="%{reloadUrl}" 
				        id="certificates" 
				        dataType="json" 
				        onSelectAllTopics="selectAllCheck"
				        onSelectRowTopics="selectRowCheck"
				        onGridCompleteTopics="completecheck"
				        onBeforeTopics="beforeloadcheck"
				        multiselect="true"
				        width="1150"  height="230"
				       	rowList="50,75,100"	rowNum="50" 
        				pager="true" viewrecords="true"
				        tabindex="5">
						<sjg:gridColumn tabindex="6" name="requestNum" id="requestNum" index="requestNum" title="%{getText('label.requestNumber')}" align="left" cssStyle="font-weight:bold;" width="150"  key="true"/>
				        <sjg:gridColumn tabindex="7" name="deviceNickName" id="deviceNickName" index="deviceNickName" title="%{getText('label.deviceName')}" align="left" formatter="formatLink" width="170" cssStyle="font-weight:bold;" sortable="true" />
				        <sjg:gridColumn tabindex="8" name="hardwareId" id="hardwareId" index="hardwareId" title="%{getText('label.hardwareId')}" align="left" cssStyle="font-weight:bold;" width="300" sortable="true"/>
				        <sjg:gridColumn tabindex="9" name="status" id="status" index="status" title="%{getText('label.status')}" align="left" cssStyle="font-weight:bold;" width="200" sortable="true"/>
				        <sjg:gridColumn tabindex="10" name="expiryDate" id="expiryDate" index="expiryDate" title="%{getText('label.expirationDate')}" align="left" width="170" cssStyle="font-weight:bold;" sortable="true"/>
				        <sjg:gridColumn tabindex="11" name="deviceType" id="deviceType" index="deviceType" title="%{getText('label.deviceType')}" align="left" width="200" cssStyle="font-weight:bold;" sortable="true"/>
				        <sjg:gridColumn tabindex="12" name="hardwareId" id="hardwareId" index="hardwareId" title=""  hidden="true"/>
						<sjg:gridColumn tabindex="13" name="fileName" id="fileName" index="fileName" title="" hidden="true"/>
				 		<sjg:gridColumn tabindex="14" name="statuscde" id="statuscde" index="statuscde" title="" hidden="true"/>
				 		<sjg:gridColumn tabindex="15" name="multipleCreateInd" id="multipleCreateInd" index="multipleCreateInd" title="" hidden="true"/>	
						<sjg:gridColumn name="certActivationInd" id="certActivationInd" index="certActivationInd" title="" hidden="true"/>	
						</sjg:grid>						

			</fieldset>
		</div> <!-- closing divSearchResult -->
	</div> 

	<!--  Added by Sanjana: IE project changes -->
     <% 
     String browserDetails = request.getHeader("User-Agent");
     String userAgent = browserDetails;
     %>
     
   
     
	<s:if test='#session.userProfile.userRoleList.contains("ECRMS_ADMIN") || #session.userProfile.userRoleList.contains("ECRMS_SUPER_USER")'>
	<!-- Changes : 2016/01/30 - Reactivate button is made visbile for SUPER USERS -->
	<!-- Reactivate button is visbile to Admin User only -->	
	<div class="span-23" style="margin-top:20px;margin-left:20px;">
		
		<!--  Added by Sanjana: IE project changes -->
	    <%
           if(userAgent.toLowerCase().contains("msie")) {
        %>           
           <div class="span-4" id="printsj">
				<sj:a button="true" id="print" tabindex="16" onclick="printCert();" cssStyle="margin-left:45px;width:185px;">
				<s:property value="getText('button.print')" /></sj:a>
			</div>
		<%
          } else {                                                                 
		%>
			<input class=buttonType type=button tabindex="16" value="Print" onClick="printCert()" style="margin-left:45px;width:155px;" id="print1" name="print">
		<%
          }                                                                  
		%>
		
		<!--  Added by Sanjana: IE project changes -->
	    <%
           if(userAgent.toLowerCase().contains("msie")) {
        %>           
           <div class="span-4" id="downldsj" >
				<sj:a button="true" id="dwnldCert" tabindex="17" onclick="return verifyDownload();" cssStyle="margin-left:55px;width:185px;">
				<s:property value="getText('button.dwnldCert')" /></sj:a>
			</div>
        <%
          } else {                                                                 
		%>
			<input class=buttonType type=button tabindex="17" value="Download Certificate" onClick="return verifyDownload()" style="margin-left:55px;width:155px;" id="dwnldCert1" name="dwnldCert">
		<%
          }                                                                 
		%>
		
		<!--  Added by Sanjana: IE project changes -->
	    <%
           if(userAgent.toLowerCase().contains("msie")) {
        %>           
            <div class="span-4 last" id="createsj" >
				<sj:a button="true" id="createNewCert" tabindex="18" onclick="multipleCreateRequest();" cssStyle="margin-left:65px;width:185px;">
				<s:property value="getText('button.createNewCert')" /></sj:a>
			</div>
        <%
          } else {                                                                 
		%>
			<input class=buttonType type=button tabindex="18" value="Create Certificate" onClick="multipleCreateRequest()" style="margin-left:65px;width:155px;" id="createNewCert1" name="createNewCert">
		<%
          }                                                                 
		%>
		
		<!--  Added by Sanjana: IE project changes -->
	    <%
           if(userAgent.toLowerCase().contains("msie")) {
        %>           
           <div class="span-4 last" id="revokedsj" >
				<sj:a button="true" id="revokeCert" tabindex="19" onclick="multipleRevokeRequest();" cssStyle="margin-left:85px;width:185px;">
				<s:property value="getText('button.revokeCert')" /></sj:a>
			</div>
        <%
          } else {                                                                 
		%>
			 <input class=buttonType type=button tabindex="19" value="Revoke Certificate" onClick="multipleRevokeRequest()" style="margin-left:75px;width:155px;" id="revokeCert1" name="revokeCert">
		<%
          }                                                                 
		%>
		
		<!--  Added by Sanjana: IE project changes -->
	    <%
           if(userAgent.toLowerCase().contains("msie")) {
        %>           
           <div class="span-4 last" id="reactivdsj" >
				<sj:a button="true" id="reactivCert" tabindex="20" onclick="reactiveCert()" cssStyle="margin-left:105px;width:185px;">
				<s:property value="getText('button.reactiveCert')" /></sj:a>
			</div>
        <%
          } else {                                                                 
		%>
			 <input class=buttonType type=button tabindex="20" value="Reactivate Certificate" onClick="reactiveCert()" style="margin-left:85px;width:155px;" id="reactivCert1" name="reactivCert">
		<%
          }                                                                 
		%>
			 
	</div>
	</s:if>
	<s:elseif test='#session.userProfile.userRoleList.contains("ECRMS_VIEW")'>
	<!-- Reviewer can only Print and download certificates -->
	<div class="span-18"  style="margin-top:20px;">
			
			<!--  Added by Sanjana: IE project changes -->
		    <%
	          if(userAgent.toLowerCase().contains("msie")) {
	        %>           
           		<div class="push-6 span-5" id="printsj">
					<sj:a button="true" id="print" tabindex="16" onclick="printCert();" cssStyle="margin-left:25px;width:200px;">
					<s:property value="getText('button.print')" /></sj:a>
				</div>
           	<%
	          } else {                                                                 
			%>
				<input class=buttonType type=button tabindex="16" value="Print" onClick="printCert()" style="margin-left:25px;width:170px;" id="print1" name="print">
			<%
	          }                                                                  
			%>
			
			
			
			<!--  Added by Sanjana: IE project changes -->
		    <%
	           if(userAgent.toLowerCase().contains("msie")) {
	        %>           
           		<div class="push-6 span-5" id="downldsj" >
					<sj:a button="true" id="dwnldCert" tabindex="17" onclick="return verifyDownload();" cssStyle="margin-left:25px;width:200px;">
					<s:property value="getText('button.dwnldCert')" /></sj:a>
				</div>
           		
			<%
	          } else {                                                                 
			%>
				<input class=buttonType type=button tabindex="17" value="Download Certificate" onClick="return verifyDownload()" style="margin-left:25px;width:170px;" id="dwnldCert1" name="dwnldCert">
			<%
	          }                                                                  
			%>	
	</div>
	</s:elseif>
	<s:else>
		<!-- Corporate user, Super User, Dealer user Can Print, Download, Renew and Revoke certificate(s) -->
		<div class="span-24" style="margin-top:20px;">
			
			<!--  Added by Sanjana: IE project changes -->
		    <%
	           if(userAgent.toLowerCase().contains("msie")) {
	        %>           
           		<div class="push-2 span-5" id="printsj">
					<sj:a button="true" id="print" tabindex="16" onclick="printCert();" cssStyle="margin-left:25px;width:200px;">
					<s:property value="getText('button.print')" /></sj:a>
				</div>
           		
			<%
	          }  else {                                                                
			%>
				<input class=buttonType type=button tabindex="16" value="Print" onClick="printCert()" style="margin-left:25px;width:170px;" id="print1" name="print">
			<%
	          }                                                                  
			%>
			
			
			<!--  Added by Sanjana: IE project changes -->
		    <%
	           if(userAgent.toLowerCase().contains("msie")) {
	        %>           
           		<div class="push-2 span-5" id="downldsj" >
					<sj:a button="true" id="dwnldCert" tabindex="17" onclick="return verifyDownload();" cssStyle="margin-left:25px;width:200px;">
					<s:property value="getText('button.dwnldCert')" /></sj:a>
				</div>
           	<%
	          } else {                                                                 
			%>
				<input class=buttonType type=button tabindex="17" value="Download Certificate" onClick="return verifyDownload()" style="margin-left:25px;width:170px;" id="dwnldCert1" name="dwnldCert">
			<%
	          }                                                                   
			%>
			
			<!--  Added by Sanjana: IE project changes -->
		    <%
	           if(userAgent.toLowerCase().contains("msie")) {
	        %>           
           		<div class="push-2 span-5 last" id="createsj" >
					<sj:a button="true" id="createNewCert" tabindex="18" onclick="multipleCreateRequest();" cssStyle="margin-left:25px;width:200px;">
					<s:property value="getText('button.createNewCert')" /></sj:a>
				</div>
           	<%
	          } else {                                                                  
			%>	
				<input class=buttonType type=button tabindex="18" value="Create Certificate" onClick="multipleCreateRequest()" style="margin-left:25px;width:170px;" id="createNewCert1" name="createNewCert">
			<%
	          }                                                                  
			%>
			
			<!--  Added by Sanjana: IE project changes -->
		    <%
	           if(userAgent.toLowerCase().contains("msie")) {
	        %>           
           		<div class="push-2 span-5 last" id="revokedsj" >
					<sj:a button="true" id="revokeCert" tabindex="19" onclick="multipleRevokeRequest();" cssStyle="margin-left:25px;width:200px;">
					<s:property value="getText('button.revokeCert')" /></sj:a>
				</div>
           	<%
	          } else {                                                                 
			%>
				  <input class=buttonType type=button tabindex="19" value="Revoke Certificate" onClick="multipleRevokeRequest()" style="margin-left:25px;width:170px;" id="revokeCert1" name="revokeCert">
			<%
	          }                                                                  
			%>	
				 
		</div>
	</s:else>

<!-- Edit Certificate Request Dialog open when user click on device nick name -->
<s:if test='#session.userProfile.userRoleList.contains("ECRMS_SUPER_USER")||#session.userProfile.userRoleList.contains("ECRMS_ADMIN")'>	
	<sj:dialog
		id="editCertReqDialog"  
		title="%{getText('menu.editRequest')}"
		closeOnEscape="true"                  
		autoOpen="false"
		modal="true"
		position="center"
		width="1100"
		height="700"
		onCloseTopics="editCertReqDialogClosed" 
		/>
</s:if>

<s:elseif test='#session.userProfile.userRoleList.contains("ECRMS_CORP")
			||#session.userProfile.userRoleList.contains("ECRMS_DLR")'>
	<sj:dialog
		id="editCertReqDialog"  
		title="%{getText('menu.editRequest')}"
		closeOnEscape="true"                  
		autoOpen="false"
		modal="true"
		position="center"
		width="1100"
		height="700"
		onCloseTopics="editCertReqDialogClosed" 
		
		/>
</s:elseif>

<s:elseif test='#session.userProfile.userRoleList.contains("ECRMS_VIEW")'>
	<sj:dialog
		id="editCertReqDialog"  
		title="%{getText('menu.editRequest')}"
		closeOnEscape="true"                  
		autoOpen="false"
		modal="true"
		position="center"
		width="1100"
		height="660"
		onCloseTopics="editCertReqDialogClosed" 
		
		/>
</s:elseif>
<!-- Print Certificate dialog open when user select any single Pending Review , Stalled or Approved request -->
 	<sj:dialog
            id="printCertReqDialog"  
            title="%{getText('menu.printTitle')}" 
            closeOnEscape="true"                
            autoOpen="false"
            modal="true"
            position="center"
            width="1150"
            height="700"
            onCloseTopics="printCertReqDialogClosed" />
 <!-- Download instruction dialog for user -->           
   <sj:dialog id="downloadInstrctnDialog" title="%{getText('title.download')}"
		closeOnEscape="true" autoOpen="false" modal="true" onCloseTopics="downloadInstrctnDialogClosed"
		 width="400" height="100">
	</sj:dialog>


