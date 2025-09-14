<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<fieldset class="span-5">
	<legend><s:label theme="simple" key="legend.administration"></s:label></legend>
	
	<div id="adminIndex">
		<div id="adminmenu">
	    	<ul>
		    	<li><sj:a href="administration/expiryPeriod.action" cssClass="Adminlink" targets="adminpart"><s:label key="legend.expiryPeriod" theme="simple"></s:label></sj:a></li>
		    	<li><sj:a href="administration/expiryNotifications.action" cssClass="Adminlink" targets="adminpart"><s:label key="legend.expiryPeriodNotification" theme="simple"></s:label></sj:a></li>
		    	<li><sj:a href="administration/receivedNotifications.action" cssClass="Adminlink" targets="adminpart"><s:label key="legend.certificateCreateNotification" theme="simple"></s:label></sj:a></li>
		    	<li><sj:a href="administration/deviceTypeList.action" cssClass="Adminlink" targets="adminpart"><s:label key="legend.deviceTypeList" theme="simple"></s:label></sj:a></li>
	    	</ul>
	 	</div>
	</div>
</fieldset>
<s:actionerror/>
<!-- This the place where we will get all the children pages -->
<div id="adminpart" class="span-15 last">
</div>
	