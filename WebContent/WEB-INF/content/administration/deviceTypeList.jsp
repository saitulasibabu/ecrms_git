<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<script type="text/javascript" src="js/administration/deviceTypeListValid.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>

<script type="text/javascript">


function formatcheck(cellvalue, options, rowObject) {

if(rowObject.deviceActiveInd == 'Y'){

return "<input type=\"checkbox\" checked=\"checked\" id='" + rowObject.deviceTypeId +"' />";
}
else{
return "<input type=\"checkbox\" id='" + rowObject.deviceTypeId +"' />";
}
}

	jQuery().ready(function (){
		
			$("#saveDeviceTypeButton").attr("disabled", false);
  			$("#resetDeviceTypeButton").attr("disabled", false);
  			
			
		
		});

function checkSubmit(e) 
	{
	    if(e && e.keyCode == 13)    
	    {  
	         saveDeviceType();   
	         return false;
	    }
	 } 

$(document).ready( function() {
    $("#deviceTypeCodeValue").val('');
	$("#deviceTypeDescValue").val('');
});

$("#deviceTypeCodeValue").bind('keyup', function (e) {
    if (e.which >= 97 && e.which <= 122) {
        var newKey = e.which - 32;
        
        e.keyCode = newKey;
        e.charCode = newKey;
    }
    $("#deviceTypeCodeValue").val(($("#deviceTypeCodeValue").val()).toUpperCase());
});

$("#deviceTypeCodeValue").change(function (e) {
    if (e.which >= 97 && e.which <= 122) {
        var newKey = e.which - 32;
        
        e.keyCode = newKey;
        e.charCode = newKey;
    }
    $("#deviceTypeCodeValue").val(($("#deviceTypeCodeValue").val()).toUpperCase());
});


</script>

<s:form id="deviceTypeForm" name="deviceTypeForm">
	<div id="DeviceTypeListErrorDiv" style="color:red;font-weight: bold"></div>
	<div id="DeviceTypeListSuccessDiv" style="color:#0066CC;font-weight: bold" ></div>
	<s:actionerror/>
	<s:actionmessage/>
<s:hidden id="checkedDeviceIdData" name="deviceTypeModel.checkedData"></s:hidden>
<div id="CheckedHiddenDivId">
</div>
<div id="UncheckedHiddenDivId">
</div>
<div id="deviceTypelist" class=" span-15 last">
	<fieldset class="span-14 last">
		<legend><s:label key="legend.deviceTypeList" theme="simple"></s:label></legend>
		
		<s:url action="retrieveDeviceType" var="reloadUrl" ></s:url>
 		
		<sjg:grid gridModel="deviceTypeModelList" 
		         loadingText="%{getText('label.loading')}"
		        reloadTopics="reloadGridTable"
		        rowNum="-1"
		         formIds="deviceTypeForm" href="%{reloadUrl}" id="deviceTypeGrid" dataType="json"  height="300" width="650">
		
		        <sjg:gridColumn tabindex="1" name="deviceTypeId" width="150" index="deviceTypeId"  title="%{getText('label.deviceTypeCode')}" sortable="true"/>
		        <sjg:gridColumn tabindex="2" name="deviceTypeDesc" width="200" index="deviceTypeDesc" title="%{getText('label.deviceTypeDescription')}" sortable="true"/>
				<sjg:gridColumn tabindex="3" name="deviceActiveInd" width="100" align="center" index="deviceActiveInd" title="%{getText('label.active')}" formatter="formatcheck" editable="true" edittype="checkbox" sortable="false"></sjg:gridColumn>
		</sjg:grid>
	</fieldset>
</div>

<div class="span-15 last">
	<fieldset class="span-14 last">
		<legend><s:label key="legend.addDeviceType" theme="simple"></s:label></legend>
		<div class="span-10">
			<s:textfield tabindex="4"  key="label.deviceTypeCode"  name="deviceTypeModel.deviceTypeId" id="deviceTypeCodeValue" maxlength="15" />
		</div>
		<div class="span-10">
			<s:textfield tabindex="5" key="label.deviceTypeDescription"   name="deviceTypeModel.deviceTypeDesc" maxlength="25" id="deviceTypeDescValue" />
		</div>
	</fieldset>
</div>

	<div class="clear"><br/></div>
	
<div class="span-15 last">
	<div class="span-2">
		
			<sj:a button="true" tabindex="6" id="saveDeviceTypeButton" onkeypress="return checkSubmit(event)" onclick="saveDeviceType(); return false;"><s:property value="getText('button.save')" /></sj:a>
		
	</div>
	<div class="span-2" style="margin-left:15px">
			
			<sj:a button="true" tabindex="7" id="resetDeviceTypeButton" href="administration/deviceTypeList.action" targets="adminpart"><s:property value="getText('button.reset')" /></sj:a>
			
	</div>
</div>
</s:form>
