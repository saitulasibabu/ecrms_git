<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><s:property value="getText('label.headerTitle')"/></title>
<s:head />
<link rel="stylesheet" href="css/blueprint/screen.css" type="text/css"
	media="screen, projection">
<link rel="stylesheet" href="css/blueprint/print.css" type="text/css"
	media="print">



<s:url var="cp" value="/" />

<sj:head debug="true" jqueryui="true" jquerytheme="vw" customBasepath="%{cp}css"/>

<link rel="stylesheet" href="css/customcss/ecrms.css" type="text/css"
	media="screen, projection">
<link rel="stylesheet" href="css/vw/jquery.alerts.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="css/vw/jquery-ui.css">
<script>
jQuery.browser = {};
(function () {
    jQuery.browser.msie = false;
    jQuery.browser.version = 0;
    if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
        jQuery.browser.msie = true;
        jQuery.browser.version = RegExp.$1;
    }
})();



</script>
<script type="text/javascript" src="js/jqueryjs/grid.locale-en.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.jqGrid.js"></script>	
<script type="text/javascript" src="js/jqueryjs/jquery-ui.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.form.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.alerts.js"></script>

</head>

<body oncontextmenu="return false;">

<div id="page" class="container"> <!-- can include "showgrid" to class to show the grid -->
<s:if test='#session.userProfile.userLanguage.equals("fr")'>
 	<s:param name="request_locale" >fr</s:param>
 	<s:hidden id="locale" value="fr"></s:hidden>
</s:if>
<s:include value="header.jsp"/>

<s:include value="menu.jsp"/>
<hr/>

<img id="indicator" src="assets/indicator.gif" alt="Loading..." align="left" style="display:none"/>

<div id="content" class="span-24" >
<s:include value="searchcertrequest/home.jsp"/>


</div>
</div>

</body>
</html>

