<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<head>
<script type="text/javascript" src="js/certrequest/createCertRequest.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.printArea.js"></script>

<script>


$(document).ready( function() {
	
	closeConfirmExstDlrForm();
	closeConfirmHwIdForm();
	openInfoDialogForRequest();
	
	
});


</script>
</head>
<body>


<div id="createCertificate" class="span-22 inline">

<s:form	id="createCertificateForm" action="" target="createCert">

<s:hidden id="userType" name="certRequestModel.userType" />
<s:set var="userType" value="certRequestModel.userType" />
<s:if test='#session.userProfile.userRoleList.contains("ECRMS_DLR")'>		
	<s:hidden id="userTypeDlrCorp" name="userTypeDlrCorp" value="DLR" />
</s:if>	
<s:else>
	<s:hidden id="userTypeDlrCorp" name="userTypeDlrCorp" value="CORP" />
</s:else>
	
	<div id=printable class="span-21" style="margin:20px;">
	
	
	<div class="span-20" style="border:1px solid black;margin:0px;padding-bottom:20px;margin-left:60px">
	<div id="header" class="span-20">
		<div class="span-20" style="position: relative; top: 0; left:0px; right:2px; z-index:0;">
			<img src="assets/TopBarWPrintLogo.jpg" style="width:990px;"/>
		</div>
	</div>
	
	<s:if test="%{certRequestModel.userType!='IRF'}">
	 
	<div class="span-19" style="margin:20px;">
		<s:label key="message.status" theme="simple" cssStyle="font-weight:normal;font-size:102%;"></s:label>
		<s:label key="message.emailNotf" theme="simple" cssStyle="font-weight:normal;font-size:102%;"></s:label>
	</div>
	
	</s:if>
	<s:else>
	<div class="span-19" style="margin:20px;">
		<s:label key="message.IRFstatus" theme="simple" cssStyle="font-weight:normal;font-size:102%;"></s:label>
	</div>
	</s:else>
	
	<br/><br/>
	
	<div class="span-19" style="margin:20px;">
		<s:label key="label.requestNumber" theme="simple" cssClass="span-4" cssStyle="font-size:102%;"/>
		<s:label value="%{certRequestModel.requestNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%;" />
	</div>
	
	<div class="clear"></div>
	
	
	<div class="span-19" style="margin:20px;">
		<div class="span-9">
			<s:label key="label.dealerNumber" theme="simple" cssClass="span-4" cssStyle="font-size:102%" />
			<s:label value="%{certRequestModel.dealerNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	
		<div class="span-10 last" >
			<s:label key="label.dualDealerNumber" theme="simple" cssClass="span-5" cssStyle="font-size:102%" /> 
			<s:label value="%{certRequestModel.dualDealerNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	</div>

	<div class="span-19" style="margin:20px;">
		<div class="span-9">
		<s:label key="label.dealerSiteName" theme="simple" cssClass="span-4" cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerName}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-10 last">
		<s:label key="label.dealerPhoneNumber" theme="simple" cssClass="span-5" cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerPhnNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	</div>
	
	<div class="clear"></div>
	
	<s:if test="%{certRequestModel.userType!='IRF'}">
	
	
<div class="clear"></div>
	<!-- Dealer Information fieldset -->
	<fieldset  class="span-18" style="margin:20px;"><legend>
		<s:label key="legend.dealerInfo" theme="simple"  cssStyle="font-size:102%"/></legend>
		
		
		<div class="span-17">
		<s:label key="label.street" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerStreetLine}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>

		<div class="span-17">
		<s:label key="label.city" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerCity}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>

		<div class="span-17">
		<s:label key="label.state" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerState}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>

		<div class="span-17">
		<s:label key="label.zip" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerZip}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-17">
		<s:label key="label.country" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerCntry}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
				

	</fieldset>
	
	</s:if>
<div class="clear"></div>
	
	<!-- Contact Information fieldset -->	
	<fieldset class="span-18" style="margin:20px;">
		<legend><s:label key="legend.contactInfo" theme="simple"  cssStyle="font-size:102%" /></legend>
	
		<div class="span-17" style="margin-right:-8px;">
		<s:label key="label.contactName" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.contactName}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-17 last" style="margin-right:-8px;">
		<s:label key="label.contactPhoneNumber" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.contactPhnNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-17"> 
		<s:label key="label.contactEmail" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.contactEmail}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	</fieldset>
	
	
	<div class="clear"></div>
	
	
	<!-- Device Information fieldset -->
	<fieldset class="span-18" style="margin:20px;">
		<legend><s:label key="legend.deviceInfo" theme="simple" cssStyle="font-size:102%" /></legend>
		<div class="span-17">
		<s:label key="label.deviceType" theme="simple" cssClass="span-5"  cssStyle="font-size:102%"/> 
		<s:label value="%{certRequestModel.deviceType}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%"/>
		</div>
		
		<div class="span-17">
		<s:label key="label.hardwareId" theme="simple" cssClass="span-5" cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.hardwareId}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-17">
		<s:label key="label.deviceName" theme="simple" cssClass="span-5" cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.deviceName}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	
	</fieldset>
	
<%-- 	<s:if test="%{certRequestModel.userType!='IRF'}">
	
	<div class="clear"></div>
	<fieldset  class="span-18" style="margin:20px;">
		<legend><s:label theme="simple" /> </legend>
		<div class="span-17" style="height:70px; margin-top:30px;">
		<s:label key="label.name" cssStyle="float:left;font-size:102%" theme="simple"/>
		</div>
		<div class="span-17" style="height: 70px; margin-top:15px;">
		<s:label key="label.signature" cssStyle=" float:left;font-size:102%" theme="simple"/>
		</div>
		<br/>
	</fieldset>
	<br/><br/>
	</s:if> --%>
	
	</div><br/>
	
</div>


	<div class="clear span-19"> 
	<s:if test='(#session.reqNumList != null && #session.reqNumList.size() > 1)'>
	
		<div class="push-5 span-5 ">
		<sj:a button="true" id="printButton" cssStyle="width:80px;" onclick="printThis(); return false;"><s:property value="getText('button.print')" /></sj:a>
		</div>
		
		<div class="push-5 span-5 last">
			<sj:a button="true" href="loadBasicSearch.action" cssStyle="width:80px;float:left;" targets="content" onclick="this.disabled=true">
			<s:property value="getText('button.close')" /></sj:a>
		</div>
		<s:if test="%{certRequestModel.userType!='IRF'}"> 
	
				<div class="push-5 span-5 last">
					<sj:a button="true" href="certrequest/multipleCertReqCreate.action"  cssStyle="width:80px;;float:left;" targets="content" onclick="this.disabled=true">
					<s:property value="getText('button.next')" /></sj:a>
				</div>
		</s:if>
	
	</s:if>
	<s:else>
			<div class="push-9 span-5 ">
				<sj:a button="true" id="printButton" cssStyle="width:80px;" onclick="printThis(); return false;"><s:property value="getText('button.print')" /></sj:a>
			</div>
			
				<s:if test="%{certRequestModel.userType!='IRF'}"> 
					
						<s:if test='(#session.reqNumList != null && #session.reqNumList.size() > 0)'>
							<div class="push-8 span-5 last">
								<sj:a button="true" href="loadBasicSearch.action" cssStyle="width:80px;float:left;" targets="content" onclick="this.disabled=true">
								<s:property value="getText('button.close')" /></sj:a>
							</div>
						</s:if>
						<s:else>
							<div class="push-8 span-5 last">
								<sj:a button="true" href="certrequest/createCertRequest.action" cssStyle="width:80px;float:left;" targets="content" onclick="this.disabled=true">
								<s:property value="getText('button.close')" /></sj:a>
							</div>
						</s:else>
				</s:if>
				<s:else>
					<div class="push-8 span-5 last">
						<sj:a button="true" href="loadBasicSearch.action" cssStyle="width:80px;float:left;" targets="content" onclick="this.disabled=true">
						<s:property value="getText('button.close')" /></sj:a>
					</div>
				</s:else>
	
	</s:else>
	
	</div>
</s:form>	
</div>
<div class="clear span-19"></div>
</body>

