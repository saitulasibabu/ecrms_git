<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<s:url var="confirmCertRequestForExstDlr" action="confirmCertRequestExtDlr" />
<s:url var="confirmCertRequestForHWId" action="confirmCertRequestHWId" />
<s:url var="infoCertReqDtlURL" action="infoCertDetail" />
<s:url var="confirmPenRevNStallReq" action="confirmCertReqPenRevNStl" />

<script type="text/javascript" src="js/certrequest/createCertRequest.js"></script>
<script type="text/javascript" src="js/eshop/verifyEshopLicense.js"></script>
<script type="text/javascript" src="js/searchcertrequest/download.jQuery.js"/></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript"> 
function openConfirmDialogForExstDlr(requestNum, certActvnInd){
	
	$("#confirmCertRequForExstDlr").dialog({
		closeOnEscape: true,    open: function(event, ui) { $(".ui-dialog-titlebar-close").show(); } 
		});  
	
	 $("#confirmCertRequForExstDlr").load("<s:property value="confirmCertRequestForExstDlr" />?certRequestModel.requestNum="+requestNum+"&certRequestModel.certActvnInd="+certActvnInd
      , 
		function(response, status, xhr) {
			if (status == "error") {
				var msg = getMessage('err.unable.retrieve');
				$("#confirmCertRequForExstDlr").html(msg + xhr.status + " " + xhr.responseText);
			}
		});
    
	$("#confirmCertRequForExstDlr").dialog('open');
}


function openConfirmDialogForHWId(){
	
	$("#confirmCertRequForHwId").dialog({
		closeOnEscape: true,    open: function(event, ui) { $(".ui-dialog-titlebar-close").show(); } 
		}); 
    $("#confirmCertRequForHwId").load('<s:property value="confirmCertRequestForHWId" />', 
		function(response, status, xhr) {
			if (status == "error") {
				var msg = getMessage('err.unable.retrieve');
				$("#confirmCertRequForHwId").html(msg + xhr.status + " " + xhr.responseText);
			}
		});
	$("#confirmCertRequForHwId").dialog('open');
}

function openConfirmDialogForPenRev(){
	
	$("#confirmPenRevNStall").dialog({
		closeOnEscape: true,    open: function(event, ui) { $(".ui-dialog-titlebar-close").show(); } 
		}); 
    $("#confirmPenRevNStall").load('<s:property value="confirmPenRevNStallReq" />', 
		function(response, status, xhr) {
			if (status == "error") {
				var msg = getMessage('err.unable.retrieve');
				$("#confirmPenRevNStall").html(msg + xhr.status + " " + xhr.responseText);
			}
		});
	$("#confirmPenRevNStall").dialog('open'); 
}

$(document).ready( function() {
	
	$.subscribe('confirmCertRequForExstDlrClosed', function(event,data) {
		$('#confirmCertRequForExstDlr').html("<img src='assets/indicator.gif' />");
	});
	$.subscribe('confirmCertRequForHwIdClosed', function(event,data) {
		$('#confirmCertRequForHwId').html("<img src='assets/indicator.gif' />");
	});
	$.subscribe('confirmPenRevNStallClosed', function(event,data) {
		$('#confirmPenRevNStall').html("<img src='assets/indicator.gif' />");
	});
	closeEshopDialog();
	
		
});  
</script>
<div id="createCert" class="span-24 inline">
<s:form	id="createCertificateForm" name="createCertificateForm" method="post" >
<s:hidden id="invHidendlrNum" value="" /> 
<div id="invMsgdlrNum" align="left" style="display:none;float:left;color:red"></div>
<div id="successCreateReqDiv" style="margin-left:30px;color:#0066CC;font-weight: bold"></div>
<div id="errorCreateReqDiv" style="margin-left:30px;color:red;font-weight: bold"></div>
<s:actionerror cssStyle="font-size: 2px;" />
	

<s:hidden id="dualDealerNum" name="certRequestModel.dualDealerNum" />
<s:hidden id="dealerName" name="certRequestModel.dealerName" />
<s:hidden id="dealerPhnNum" name="certRequestModel.dealerPhnNum" />
<s:hidden id="dealerStreetLine" name="certRequestModel.dealerStreetLine" />
<s:hidden id="dealerCity" name="certRequestModel.dealerCity" />
<s:hidden id="dealerState" name="certRequestModel.dealerState" />
<s:hidden id="dealerZip" name="certRequestModel.dealerZip" />
<s:hidden id="dealerCntry" name="certRequestModel.dealerCntry" />
<s:hidden id="validDlrNum" name="validDealerNum" />
<s:hidden id="userType" name="certRequestModel.userType" />
<s:set var="userType" value="certRequestModel.userType" />

<s:if test='#session.userProfile.userRoleList.contains("ECRMS_DLR")'>		
	<s:hidden id="userTypeDlrCorp" name="userTypeDlrCorp" value="DLR" />
</s:if>	
<s:else>
	<s:hidden id="userTypeDlrCorp" name="userTypeDlrCorp" value="CORP" />
</s:else>
 
	<div id="createCertificate" class="span-24">
	<s:if test="%{#userType!='IRF'}">
	<fieldset>
		<legend>
			<s:label key="legend.instructions" theme="simple"></s:label>
		</legend>
		<s:text name="message.instructionReq"></s:text><br>
		<s:if test='#session.userProfile.userRoleList.contains("ECRMS_DLR")'>
			<s:text name="message.instructionPrintDealerMsg"></s:text>
		</s:if>
		<s:else>
			<s:text name="message.instructionPrintCorpMsg"></s:text>
		</s:else>	
	</fieldset>
	</s:if>

	<fieldset>
		<legend><s:label key="legend.dealerInfo" theme="simple"></s:label></legend>
		
	<s:if test="%{#userType!='IRF'}">
			
<div class="span-15"> 
		<div class="span-5">
				<s:if test='#session.userProfile.userRoleList.contains("ECRMS_CORP")||#session.userProfile.userRoleList.contains("ECRMS_ADMIN")||#session.userProfile.userRoleList.contains("ECRMS_SUPER_USER")'>
					<s:textfield key="label.dealerNumber" cssClass="span-5" id="dlrNum" name="certRequestModel.dealerNum" 
						maxlength="7" title="%{getText('tooltip.dealernumber')}" onchange="return retrieveDlrData(this.id)" tabindex="1" required="true"/>
					<img id="indImg" src="assets/indicator.gif"  style="margin-left:0px;display:none;"/>
				</s:if>
				<s:else>
					<s:textfield key="label.dealerNumber" id="dlrNum" cssClass="span-5"  name="certRequestModel.dealerNum" maxlength="7" disabled="true" 
						title="%{getText('tooltip.dealernumber')}" tabindex="1"/>
					<s:hidden id="dealerNum" name="certRequestModel.dealerNum" />
				</s:else>
		</div> 
		<div class="span-5 last">	
			<s:textfield key="label.dualDealerNumber" cssClass="span-5" name="certRequestModel.dualDealerNum" id="dualDlrNum"
				disabled="true" maxlength="7" title="%{getText('label.dualDealerNumber')}"/>
		</div>
</div>
<div class="clear span-15">
		<div class="clear span-5">
			<s:textfield key="label.dealerSiteName" cssClass="span-5" name="certRequestModel.dealerName" maxlength="35" disabled="true"
			id="dlrName" />
		</div>
		
		<div class="span-5 last">
			<s:textfield key="label.dealerPhoneNumber" cssClass="span-5" name="certRequestModel.dealerPhnNum" id="dlrPhnNum"
				disabled="true" maxlength="35" />
		</div>
</div>
<div class="clear">
	 <fieldset class="span-22"><legend><s:label key="legend.dealerAddress" theme="simple"></s:label></legend>

		<div class="clear span-12">
		<s:textfield key="label.street" cssClass="span-10" name="certRequestModel.dealerStreetLine" disabled="true" id="dlrStreetLine"
			maxlength="196" />
		</div>
<div class="clear span-22">
		<div class="clear span-5">
		<s:textfield key="label.city" cssClass="span-5" name="certRequestModel.dealerCity" id="dlrCity" disabled="true" maxlength="35" />
		</div>

		<div class="span-5">
			<s:textfield key="label.state" cssClass="span-5" name="certRequestModel.dealerState" id="dlrState"
				disabled="true" maxlength="2" />
		</div>

		<div class="span-5">
		<s:textfield key="label.zip" cssClass="span-5" name="certRequestModel.dealerZip" disabled="true" id="dlrZip" maxlength="6" />
		</div>

		<div class="span-5">
		<s:textfield key="label.country" cssClass="span-5" name="certRequestModel.dealerCntry" disabled="true" id="dlrCountry" />
		</div>
</div>
	</fieldset>
	</div>
	
	</s:if>
	
	<!--  IRF User's Dealership info is not available in PRD1 tables, so SecurityAdmin will enter only the dealercode -->
	
	<s:if test="%{#userType=='IRF'}">
		<s:textfield key="label.dealerNumber" cssClass="span-5" id="dlrNum" name="certRequestModel.dealerNum" 
		maxlength="7" title="%{getText('tooltip.dealernumber')}" tabindex="1" required="true" onchange="return retrieveDlrData(this.id)" />
	</s:if>	
	
	
	<div class="clear">
	<fieldset class="span-22">
		<legend><s:label key="legend.contactInfo" theme="simple"></s:label></legend>
		<div class="span-22">
			<div class="span-5">
			<s:textfield  cssClass="span-5" key="label.contactName" id="contactName" name="certRequestModel.contactName" required="true" maxlength="50"
				tabindex="2"/>
			</div>
			
			<div class="span-5">
			<s:textfield cssClass="span-5 " id="contactPhoneNumber" key="label.contactPhoneNumber" name="certRequestModel.contactPhnNum"
				maxlength="20" required="true" tabindex="3"/>
			</div>
			
			<div class="span-5">
			<s:textfield cssClass="span-5" key="label.contactEmail" id="contactEmail" name="certRequestModel.contactEmail" required="true" maxlength="50"
				title="%{getText('tooltip.contactemail')}" tabindex="4"/>
			</div>
			
			<div class="span-5">
			<s:textfield cssClass="span-5" key="label.confirmEmail" name="certRequestModel.confirmEmail"
				id="confirmEmail" required="true" maxlength="50" tabindex="5"/>
			</div>
		</div>
	</fieldset>
	</div>
</fieldset>


<div class="clear">
	
	<fieldset>
	
		<s:if test="%{#userType!='IRF'}">
		
			<legend><s:label key="legend.deviceInfo" theme="simple"></s:label></legend>
			<div class="span-5">
				<s:select cssClass="span-5" id="deviceType" key="label.deviceType" list="deviceTypeList" name="certRequestModel.deviceType" listValue="value" listKey="key" required="true" tabindex="6"/>
			</div>
	
		</s:if>
	
		<div class="span-5">
			<s:textfield cssClass="span-5" id="hardwareId" key="label.hardwareId" name="certRequestModel.hardwareId" required="true" maxlength="32" title="%{getText('tooltip.hardwareid')}" onkeydown="return checkEvent(event)" tabindex="0"/>
		</div>
	
		<s:if test="%{#userType!='IRF'}">
	
					<div class="span-5">
						<s:textfield cssClass="span-5" key="label.deviceName" name="certRequestModel.deviceName" id="deviceName" required="true" maxlength="20"
						title="%{getText('tooltip.devicename')}" tabindex="7"/>
					</div>
		</s:if>
	</fieldset>
</div>
	</div>

	<div class="span-22" style="align:center;padding-left:17px;padding-top:17px;">
		<div class="span-3">
		<sj:a button="true"  id="saveCertRequestButton" onkeypress="return checkButtonSubmit(event)"
		 	 onclick="return saveCertRequest()" tabindex="8"><s:property value="getText('button.submit')" /></sj:a>
		</div>
		<div class="span-3">
		<s:if test='#session.CertModelForRenew != null'>
		<sj:a button="true" href="certrequest/resetRenewCertRequest.action" targets="content" id="resetButton" tabindex="9">
			<s:property value="getText('button.reset')" /></sj:a>
		</s:if>
		<s:else>
		<sj:a button="true" href="certrequest/createCertRequest.action" targets="content" id="resetButton" tabindex="9">
			<s:property value="getText('button.reset')" /></sj:a>
		</s:else>
	</div>
	
		<s:if test='(#session.reqNumList != null && #session.reqNumList.size() > 1)'>
			<div class="span-3"><sj:a button="true" id="nextButton" href="certrequest/multipleCertReqCreate.action"  cssStyle="float:left;" targets="content" onclick="this.disabled=true" tabindex="10">
				<s:property value="getText('button.next')" /></sj:a>
				</div>
		</s:if>
	</div>
	
	<div class="span-9" style="padding:17px;">
		<s:text name="message.help"></s:text>
	</div>
	
	<sj:dialog id="confirmCertRequForExstDlr" title="%{getText('tooltip.confirm_dialog')}" 
		closeOnEscape="true" autoOpen="false" modal="true" onCloseTopics="confirmCertRequForExstDlrClosed"
		width="600" height="180" cssStyle="overflow:hidden;">
	</sj:dialog>
	
	<sj:dialog id="confirmCertRequForHwId" title="%{getText('tooltip.confirm_dialog')}"
		closeOnEscape="true" autoOpen="false" modal="true" onCloseTopics="confirmCertRequForHwIdClosed"
		 width="550" height="160" cssStyle="overflow:hidden;"
		>
	</sj:dialog>
	
	<sj:dialog id="confirmPenRevNStall" title="%{getText('tooltip.confirm_dialog')}"
		closeOnEscape="true" autoOpen="false" modal="true" onCloseTopics="confirmPenRevNStallClosed"
		 width="480" height="160" cssStyle="overflow:hidden;"
		>
	</sj:dialog>
	
</s:form>
</div>