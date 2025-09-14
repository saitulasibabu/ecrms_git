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



function printThis(){
 	$("#printable").printArea();
}

</script>
</head>
<body>
<div id="printRequestDetails" class="span-21 inline">

	<s:form	id="createCertificateForm" action="" target="createCert">
	
	<s:hidden id="userType" name="certRequestModel.userType" />
	<s:set var="userType" value="certRequestModel.userType" />
	
 	<s:if test='#session.userProfile.userRoleList.contains("ECRMS_DLR")'>		
		<s:hidden id="userTypeDlrCorp" name="userTypeDlrCorp" value="DLR" />
	</s:if>	
	<s:else>
		<s:hidden id="userTypeDlrCorp" name="userTypeDlrCorp" value="CORP" />
	</s:else>	
	
	<div id=printable class="span-21" style="margin:35px;">
	
	<div class="span-21" style="border:1px solid black;margin:0px;padding-bottom:20px;">
	<div id="header" class="span-21">
		<div class="span-21" style="position: relative; top: 0; left:0px; right:2px; z-index:0;">
			<img src="assets/TopBarWPrintLogo.jpg" width="1040"/>
		</div>
	</div>
	
	<s:if test="%{certRequestModel.userType!='IRF'}">
	
	<div class="span-20" style="margin:20px;">
		<s:label key="message.emailNotf" theme="simple" cssStyle="font-weight:normal;font-size:102%"></s:label>
	</div>
	
	</s:if>
	<s:else>
	<div class="span-20" style="margin:20px;">
		<s:label key="message.IRFstatus" theme="simple" cssStyle="font-weight:normal;font-size:102%"></s:label>
	</div>
	</s:else>
	
	<br/><br/>
	
	<div class="span-20" style="margin:20px;">
		<s:label key="label.requestNumber" theme="simple" cssClass="span-4" cssStyle="font-size:102%"/>
		<s:label value="%{certRequestModel.requestNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
	</div>
	
	<div class="clear"></div>
	
	<div class="span-20" style="margin:20px;">
		<div class="span-9">
			<s:label key="label.dealerNumber" theme="simple" cssClass="span-4" cssStyle="font-size:102%" />
			<s:label value="%{certRequestModel.dealerNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	
		<div class="span-10 last" >
			<s:label key="label.dualDealerNumber" theme="simple" cssClass="span-6" cssStyle="font-size:102%" /> 
			<s:label value="%{certRequestModel.dualDealerNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	</div>
	
	<div class="span-20" style="margin:20px;">
		<div class="span-9">
		<s:label key="label.dealerSiteName" theme="simple" cssClass="span-4" cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerName}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-10 last">
		<s:label key="label.dealerPhoneNumber" theme="simple" cssClass="span-6" cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerPhnNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	</div>

	<div class="clear"></div>
	
	<s:if test="%{certRequestModel.userType!='IRF'}">
	
	<!-- Dealer Information fieldset -->
	<fieldset  class="span-19" style="margin:20px;"><legend>
		<s:label key="legend.dealerInfo" theme="simple"  cssStyle="font-size:102%"/></legend>
		
		
		<div class="span-18">
		<s:label key="label.street" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerStreetLine}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>

		<div class="span-18">
		<s:label key="label.city" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerCity}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>

		<div class="span-18">
		<s:label key="label.state" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerState}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>

		<div class="span-18">
		<s:label key="label.zip" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerZip}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-18">
		<s:label key="label.country" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.dealerCntry}"theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
				

	</fieldset>
	
	</s:if>
	
		
	<div class="clear"></div>
	
	<!-- Contact Information fieldset -->	
	<fieldset class="span-19" style="margin:20px;">
		<legend><s:label key="legend.contactInfo" theme="simple"  cssStyle="font-size:102%" /></legend>
	
		<div class="span-18" style="margin-right:-8px;">
		<s:label key="label.contactName" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.contactName}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-18 last" style="margin-right:-8px;">
		<s:label key="label.contactPhoneNumber" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.contactPhnNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-18"> 
		<s:label key="label.contactEmail" theme="simple" cssClass="span-5"  cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.contactEmail}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	</fieldset>
	
	
	<div class="clear"></div>
	
	<!-- Device Information fieldset -->
	<fieldset class="span-19" style="margin:20px;">
		<legend><s:label key="legend.deviceInfo" theme="simple" cssStyle="font-size:102%" /></legend>
		<div class="span-18">
		<s:label key="label.deviceType" theme="simple" cssClass="span-5"  cssStyle="font-size:102%"/> 
		<s:label value="%{certRequestModel.deviceType}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%"/>
		</div>
		
		<div class="span-18">
		<s:label key="label.hardwareId" theme="simple" cssClass="span-5" cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.hardwareId}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
		
		<div class="span-18">
		<s:label key="label.deviceName" theme="simple" cssClass="span-5" cssStyle="font-size:102%" /> 
		<s:label value="%{certRequestModel.deviceName}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:102%" />
		</div>
	
	</fieldset>
	
	<div class="clear"></div>
	
<%-- 	<s:if test="%{certRequestModel.userType!='IRF'}">	
		<fieldset  class="span-19" style="margin:20px;">
			<legend><s:label theme="simple" /> </legend>
			<div class="span-18" style="height:70px; margin-top:30px;">
			<s:label key="label.name" cssStyle="float:left;font-size:102%" theme="simple"/>
			</div>
			<div class="span-18" style="height: 70px; margin-top:15px;">
			<s:label key="label.signature" cssStyle=" float:left;font-size:102%" theme="simple"/>
			</div>
			<br/>
		</fieldset>
		<br/><br/>
	</s:if>
 --%>	
	
	</div>
	</div>	
	<br/>
	<div class="clear span-21" style="margin:20px;">
	<div class="push-8 span-5 ">
		<sj:a button="true" cssStyle="width:80px;" onclick="printThis(); return false;">
		<s:property value="getText('button.print')" />
		</sj:a>
	</div>
		
	<div class="push-7 span-5 last">
		<sj:a button="true" cssStyle="width:80px;float:left;" onclick="closePrintReqDialog();" >
		<s:property value="getText('button.close')" />
		</sj:a>
	</div>
	</div>
	</s:form>
	<div class="bottom" >
	<div class="clear span-21"style="margin-bottom:10px;margin-top:10px; margin-left: 40px;">
    <div class="center" style="width: 200px;"> &copy; Volkswagen Group of America </div>
    </div>

	</div>
</div>
</body>

