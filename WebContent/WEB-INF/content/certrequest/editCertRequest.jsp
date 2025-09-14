<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<head>

<script type="text/javascript" src="js/certrequest/editCertRequest.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>

<script type="text/javascript">

$.subscribe('handleGridError', function(event, data) {
    $('#commGridLoadingError').html(event.originalEvent.request.responseText);
});

/*
 * This function will perform the logic once the jsp is loaded completely.
 */
jQuery().ready(function () {
	
	//get the status
	var status = $("#statCode").val();

	if(status == 'ACTIV'){
		$("#commText").attr("disabled", true);
		$("#commText").removeAttr("title");
		$("#addButton").attr("disabled", true);
	 	$("#stallButton").attr("disabled", true);
	  	$("#approveButton").attr("disabled", true);
	}else if(status == 'PENRV'){
	 	$("#revokeButton").attr("disabled", true);
	}else if(status == 'STALD'){
	 	$("#revokeButton").attr("disabled", true);
	  	$("#stallButton").attr("disabled", true);
	}
	else if(status == 'EXPRD'){
	 	$("#revokeButton").attr("disabled", true);
	  	$("#commText").attr("disabled", true);
	  	$("#commText").removeAttr("title");
	  	
	 	$("#addButton").attr("disabled", true);
	  	$("#stallButton").attr("disabled", true);
	  	$("#approveButton").attr("disabled", true);
	}
	else if(status == 'REVKD'){
	 	$("#revokeButton").attr("disabled", true);
	 	$("#commText").attr("disabled", true);
	 	$("#commText").removeAttr("title");
	 	$("#addButton").attr("disabled", true);
	  	$("#stallButton").attr("disabled", true);
	  	$("#approveButton").attr("disabled", true);
	}
	//max length validation for comment text
	     $("#commText").keyup(function(){  
         //get the limit from maxlength attribute  
        var limit = 540;  
         //get the current text inside the textarea  
         var text = $(this).val();  
         //count the number of characters in the text  
         var chars = text.length;  
   
         //check if there are more characters then allowed  
         if(chars > limit){  
            //and if there are use substr to get the text before the limit  
             var new_text = text.substr(0, limit);  
   
             //and change the current text with the new text  
             $(this).val(new_text);  
         }  
     });
 
});

$.subscribe('completechecks',function(event,ref)
	{
	$("#revokeButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#addButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#stallButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#approveButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	});
</script>

</head>

<div id="editCertificate" class="span-21 inline"  >
<div class="clear"></div>
<s:form	id="editCertificateForm" name="editCertificateForm" method="post">
<s:if test='#session.userProfile.userLanguage.equals("fr")'>
 	<s:param name="request_locale" >fr</s:param>
 	<s:hidden id="locale" value="fr"></s:hidden>
</s:if>
<s:hidden id="statCode" name="certRequestModel.statusCode" value="%{certRequestModel.statusCode}"></s:hidden>
	
	<div class="span-21">
	<div class="span-17" style="height:42px">
	<div  id="successTopMegDiv" style="color:#0066CC; margin-left: 1px;font-weight: bold;display:none;"></div>
	<div id="errorTopMegDiv" style="color:red ; margin-left: 1px;font-weight:bold;display:none;" ></div>
	<div><s:actionerror cssStyle="font-size: 2px;" /></div>
	<div id="processingImage" style="margin-left:1px;display:none;">
	<img id="indcImg" src="assets/indicator.gif"  style="float:left;margin-right:2px;"/>
	<s:label key="label.processing" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:101%" />
	</div>
    </div>
    <div style="left:972px;position:absolute;">
   
	<sj:a button="true" cssClass="span-2"  id="closeTopEdit"   onclick="closeEditReqDialog()" >
	<s:property value="getText('button.close')" />
	</sj:a>
	
	</div>
	</div>
	<div class="span-18" style="margin-bottom:5px;margin-left: 10px;">
		<s:if test='#session.userProfile.userLanguage.equals("fr")'>
		 <s:label key="label.requestNumber" theme="simple" cssClass="span-4" cssStyle="font-size:101%;"/>
		 </s:if>
		 <s:else>
		  <s:label key="label.requestNumber" theme="simple" cssClass="span-3" cssStyle="font-size:101%;"/>
		 </s:else>
		<s:label value="%{certRequestModel.requestNum}" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:101%;margin-left:-15px;"/>
	</div>
	
	<div class="clear"></div>
	<div class="span-21 inline" style="margin-left:10px;">
	<fieldset><legend>
	<s:label key="legend.dealerInfo" theme="simple"></s:label></legend>
	<div class="span-20">
	
		<div class="span-5">
		<s:textfield cssClass="span-5" cssStyle="border-color:#E6E6FA;" key="label.dealerNumber" name="certRequestModel.dealerNum" disabled="true"/>
		</div>
	
		<div class="span-5 last">
		<s:textfield key="label.dualDealerNumber" name="certRequestModel.dualDealerNum" disabled="true" cssClass="span-5" cssStyle="border-color:#E6E6FA;" title="%{getText('label.dualDealerNumber')}"/>
		</div>
	
	</div>
	<div class="clear span-19">
		<div class="span-5">
		<s:textfield key="label.dealerSiteName" name="certRequestModel.dealerName" disabled="true" cssClass="span-5" cssStyle="border-color:#E6E6FA;" />
		</div>
	
		<div class="span-5 last">
		<s:textfield key="label.dealerPhoneNumber" name="certRequestModel.dealerPhnNum" disabled="true" cssClass="span-5" cssStyle="border-color:#E6E6FA;"/>
		</div>
	</div>
	<div class="clear"></div>

	<div>
	<fieldset>
		<legend>
			<s:label key="legend.dealerAddress" theme="simple"></s:label>
		</legend>

		<div class="span-10">
		<s:textfield key="label.street" cssClass="span-8" cssStyle="border-color:#E6E6FA;" name="certRequestModel.dealerStreetLine" disabled="true" />
		</div>
			
		<div class="clear span-19">
			
			<div class="span-4">
			<s:textfield key="label.city" name="certRequestModel.dealerCity" cssClass="span-4" cssStyle="border-color:#E6E6FA;" disabled="true" />
			</div>
		
			<div class="span-4">
			<s:textfield key="label.state" name="certRequestModel.dealerState" cssClass="span-4" cssStyle="border-color:#E6E6FA;" disabled="true" maxlength="2" />
			</div>
		
			<div class="span-4">
			<s:textfield key="label.zip" name="certRequestModel.dealerZip" disabled="true" cssClass="span-4" cssStyle="border-color:#E6E6FA;"/>
			</div>
		
			<div class="span-4">
			<s:textfield key="label.country" name="certRequestModel.dealerCntry" disabled="true" cssClass="span-4" cssStyle="border-color:#E6E6FA;"/>
			</div>
			
		</div>

	</fieldset>
	
	</div>
	<div class="clear"></div>
	<fieldset>
		<legend>
			<s:label key="legend.contactInfo" theme="simple"></s:label>
		</legend>
		<div class="span-19">
			<div class="span-4">
			<s:textfield key="label.contactName" name="certRequestModel.contactName" disabled="true" cssClass="span-4" cssStyle="border-color:#E6E6FA;"/>
			</div>
		
			<div class="span-4">
			<s:textfield key="label.contactPhoneNumber" name="certRequestModel.contactPhnNum" disabled="true" cssClass="span-4" cssStyle="border-color:#E6E6FA;"/>
			</div>
			
			<div class="span-4">
			<s:textfield key="label.contactEmail" id="contactEmail" name="certRequestModel.contactEmail" disabled="true" cssClass="span-4" cssStyle="border-color:#E6E6FA;" title="%{getText('tooltip.contactemail')}"/>
			</div>
		</div>
	</fieldset>

	</fieldset>
	</div>
	<div class="clear"></div>
	<div class="span-21 inline" style="margin-left:10px;">

		<fieldset>
			<legend>
				<s:label key="legend.deviceInfo" theme="simple"></s:label>
			</legend>
			<div class="span-18">
				<div class="span-5" >
				<s:textfield key="label.deviceType" name="certRequestModel.deviceType" disabled="true" cssStyle="border-color:#E6E6FA;" cssClass="span-5" />
				</div>
			
				<div class="span-5" >
				<s:textfield key="label.hardwareId" cssClass="span-5" cssStyle="border-color:#E6E6FA;" name="certRequestModel.hardwareId" disabled="true" title="%{getText('tooltip.hardwareid')}"/>
				</div>
			
				<div class="span-5">
				<s:textfield key="label.deviceName" name="certRequestModel.deviceName" disabled="true" title="%{getText('tooltip.devicename')}" cssStyle="border-color:#E6E6FA;" cssClass="span-5" />
				</div>
			</div>
	  </fieldset>

	</div>
<div class="clear"></div>
<s:if test='#session.userProfile.userRoleList.contains("ECRMS_SUPER_USER")||#session.userProfile.userRoleList.contains("ECRMS_ADMIN")'> 

	<div class="span-21 inline" style="margin-left:10px;">

	<fieldset style="height: 300px;">
		<legend>
			<s:label key="legend.comments" theme="simple"></s:label>
		</legend>
		<div id="errorAddComment" style="color:red; margin-left:1px;display:none;font-weight: bold;"></div>
		<div id="successAddComment" style="color:#0066CC; margin-left:1px;display:none;font-weight: bold"></div>
	
		<div class="span-15">
			<s:textarea key="label.newComment" cssClass="span-12" name="certRequestModel.commentText" id="commText" cssStyle="height:50px;width:700px;" title="%{getText('tooltip.comment')}" tabindex="1"/>
		</div>
	<s:if test='#session.userProfile.userLanguage.equals("fr")'>
		<div class="span-4">
			<sj:a button="true" id="addButton" cssStyle=" margin-top:45px;margin-left:15px;" onclick="return addReqComment()" tabindex="2">
			<s:property value="getText('button.addComment')" />
			</sj:a>
		</div>
	</s:if>
	<s:else>
		<div class="span-3">
			<sj:a button="true" id="addButton" cssStyle=" margin-top:45px;margin-left:25px;" onclick="return addReqComment()" tabindex="2">
			<s:property value="getText('button.addComment')" />
			</sj:a>
		</div>
	</s:else>
		
		<s:url action="loadCertReqCommHistory" var="reloadUrl"></s:url>
		<div class="span-18" style="height: 90px; margin: 15px 0 15px; ">
		
			<div id="commGridLoadingError"></div>
			<sjg:grid gridModel="reqCommentList" errorElementId="commGridLoadingError"
				reloadTopics="reloadCommentGridTable" onErrorTopics="handleGridError"
				formIds="editCertificateForm" href="%{reloadUrl}" id="reqComments"
				onGridCompleteTopics="completechecks"
				dataType="json" width="950"  height="120" scroll="true">
		
				<sjg:gridColumn tabindex="0" name="userId" index="userId" title="%{getText('label.userId')}" width="70" cssStyle="font-weight:bold;" sortable="true" />
				<sjg:gridColumn tabindex="0" name="addedDate" index="addedDate" title="%{getText('label.commentDate')}" cssStyle="font-weight:bold;" width="90" sortable="true" formatter="date" formatoptions="{newformat : 'm/d/Y H:i:s', srcformat : 'Y-m-d H:i:s'}" key="true"/>
				<sjg:gridColumn tabindex="0" name="commentText" index="commentText" title="%{getText('label.comments')}" cssStyle="font-weight:bold;" sortable="true" />
			</sjg:grid>
		
		</div>
		
		<div class="span-9"></div>
	</fieldset>

	</div>
</s:if>
<s:else>
<s:url action="loadCertReqCommHistory" var="reloadUrl"></s:url>
		<div class="span-18" style="display:none; ">
		
			<sjg:grid gridModel="reqCommList" errorElementId="commGridLoadingError"
				reloadTopics="reloadCommentGridTable"
				formIds="editCertificateForm" href="%{reloadUrl}" id="reqComments" 
				onGridCompleteTopics="completechecks"
				dataType="json" scroll="true">
		
				<sjg:gridColumn hidden="true" name="userId" index="userId" title="%{getText('label.userId')}" width="70" cssStyle="font-weight:bold;" sortable="true" />
				<sjg:gridColumn hidden="true" name="addedDate" index="addedDate" title="%{getText('label.commentDate')}" cssStyle="font-weight:bold;" width="90" sortable="true" formatter="date" formatoptions="{newformat : 'm/d/Y H:i:s', srcformat : 'Y-m-d H:i:s'}" />
				<sjg:gridColumn hidden="true" name="commentText" index="commentText" title="%{getText('label.comments')}" cssStyle="font-weight:bold;" sortable="true" />
			</sjg:grid>
		
		</div>
</s:else>
<s:if test='#session.userProfile.userRoleList.contains("ECRMS_SUPER_USER")||#session.userProfile.userRoleList.contains("ECRMS_ADMIN")'>
	<s:if test='#session.userProfile.userRoleList.contains("ECRMS_ADMIN")'>
	   <s:if test='%{certRequestModel.statusCode.equals("PENRV") || certRequestModel.statusCode.equals("STALD")}'>
		<div class="span-2 last" style="padding:17px;">
			<sj:a button="true" id="approveButton" cssClass="span-2" cssStyle="float: left;width:100px;" onclick="return approveCertRequest()" tabindex="3" >
				<s:property value="getText('button.approve')" />
			</sj:a>
		</div>
	</s:if>	</s:if>		
	<div class="span-2 last" style="padding:17px;">
		<sj:a button="true" id="stallButton" cssClass="span-2" cssStyle="float:left;width:100px" onclick="return stallCertRequest()" tabindex="4">
			<s:property value="getText('button.stall')" />
		</sj:a>
	</div>
	<div class="span-2 last" style="padding:17px;">
		<sj:a button="true" cssClass="span-2" id="revokeButton"  onclick="return revokeCertRequest()" cssStyle="float:left;width:100px" tabindex="5">
			<s:property value="getText('button.revoke')" />
		</sj:a>
	</div>
	<div class="span-2 last push-11" style="padding-top:17px;padding-left:17px;padding-bottom:5px;margin-left:560px">
	<sj:a button="true" cssClass="span-2 last" cssStyle="width:100px;"  id="closeEdit" name="closeEdit" onclick="closeEditReqDialog()" tabindex="6">
	<s:property value="getText('button.close')" /></sj:a></div>
	<div class="clear"></div>

    <div class="span-10" style="padding-left:17px;"><s:label  key="message.help" theme="simple" cssStyle="font-weight:normal;font-size: 13px;"></s:label></div>
</s:if>

<s:if test='#session.userProfile.userRoleList.contains("ECRMS_CORP") || #session.userProfile.userRoleList.contains("ECRMS_DLR")'>		
	<s:if test='#session.userProfile.userRoleList.contains("ECRMS_DLR") && #session.userProfile.userRoleList.contains("ECRMS_DLR_APPROVER")'>
	  <s:if test='%{certRequestModel.statusCode.equals("PENRV") || certRequestModel.statusCode.equals("STALD")}'>
		<div class="span-2 last" style="padding:17px;">
			<sj:a button="true" id="approveButton" cssClass="span-2" cssStyle="float: left;width:100px;" onclick="return approveCertRequest()" tabindex="3" >
				<s:property value="getText('button.approve')" />
			</sj:a>
		</div>		
 		<div id="errorAddComment" hidden="true" > </div>
		<div id="successAddComment" hidden="true" ></div>	 					
 	</s:if>		</s:if>		
	<div class="span-2 last" style="padding-left:17px;padding-right: 17px;padding-top: 17px;padding-bottom: 0px;">
		<sj:a button="true" cssClass="span-2" id="revokeButton"  onclick="return revokeCertRequest()" cssStyle="float:left;width:100px" tabindex="3">
			<s:property value="getText('button.revoke')" />
		</sj:a>
	</div>
	<div class="span-2 last push-15" style="padding-top:17px;padding-left:17px;padding-bottom:0px;margin-left: 758px;">
	<sj:a button="true" cssClass="span-2 last" cssStyle="width:100px;margin-left:48px"  id="closeEdit"  onclick="closeEditReqDialog()" tabindex="4">
	<s:property value="getText('button.close')" /></sj:a></div>
	<div class="clear"></div>

    <div class="span-10" style="padding-left:17px;padding-bottom:8px;"><s:label  key="message.help" theme="simple" cssStyle="font-weight:normal;font-size: 13px;"></s:label></div>
</s:if>

<s:if test='#session.userProfile.userRoleList.contains("ECRMS_VIEW")'>	
	<div class="span-2 push-15" style="padding-top:17px;padding-left:17px;padding-bottom:0px;">
	<sj:a button="true" cssClass="span-2 last" cssStyle="width:100px;margin-left:180px;"  id="closeEdit"  onclick="closeEditReqDialog()" >
	<s:property value="getText('button.close')" /></sj:a></div>
	
	
	<div class="span-10" style="padding-left:17px;margin-left:-113px;padding-top:17px;padding-right:17px;"><s:label  key="message.help" theme="simple" cssStyle="font-weight:normal;font-size: 13px;"></s:label></div>
</s:if>
	
</s:form>
	
	

</div>
<div class="bottom" style="padding-top:35px;">
	<div class="clear span-21"style="margin-bottom:0px;margin-top:0px;">
		<div class="center" style="width: 200px;"> &copy; Volkswagen Group of America </div>
	</div>
</div>



