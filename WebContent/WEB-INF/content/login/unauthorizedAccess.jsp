<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<head>
	<link rel="stylesheet" href="css/blueprint/screen.css" type="text/css" media="screen, projection">
	
	<link rel="stylesheet" href="css/customcss/ecrms.css" type="text/css" media="screen, projection">
</head>

<body>
	
<div id="page" class="container"> 
	<s:include value="./../header.jsp"/>
	

	<div class="span-24">
		<s:label cssClass="span-24" theme="simple" cssStyle="font-weight:normal"><s:text name="message.unauthorized.ude"/></s:label>
	</div>
	<div class="span-24">
		<s:label cssClass="span-24" theme="simple" cssStyle="font-weight:normal"><s:text name="message.help"/></s:label>
	</div>
	
	<s:include value="./../footer.jsp"/>
	
</div>


</body>