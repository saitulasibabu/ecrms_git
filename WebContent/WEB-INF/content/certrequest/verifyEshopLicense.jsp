<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ page language="java" contentType="text/html" import="com.vwoa.ecrms.model.common.UserProfileModel"%>
<%@ page language="java" contentType="text/html" import="com.opensymphony.xwork2.ActionContext"%>
<%@ page language="java" contentType="text/html" import="com.vwoa.ecrms.util.ECRMSConstants"%>

<head>
<script type="text/javascript" src="js/eshop/verifyEshopLicense.js"></script>
</head>
<s:form name="verifyEshop" var="verifyEshop">
<s:hidden id="eshopLink" value="%{#session.eshopLink}" />

<div id="eshoplicense">

	<div>
	<s:label key="message.verifyEshop" theme="simple" cssStyle="font-weight:normal;font-size:102%;"></s:label>
	</div>
	
	
<div style="text-align:center; margin: 0.5em 0.5em 0.5em 0.5em;">
<sj:a  button="true" href="certrequest/createCertRequest.action"
	cssStyle="margin: 1em 1em 1em 1em;width:50px;"  targets="content">
	<s:property value="getText('button.yes')" /></sj:a>
<sj:a button="true" href="#" cssStyle="margin: 1em 1em 1em 1em;width:50px;" onclick="openWindow(document.getElementById('eshopLink').value)" ><s:property value="getText('button.no')" /></sj:a>


</div>

</div>
</s:form>