<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<head>
<title>eCRMS Login</title>
<s:head />
<link rel="stylesheet" href="css/blueprint/screen.css" type="text/css"
	media="screen, projection">



<s:url var="cp" value="/" />
<sj:head debug="true" jqueryui="true" jquerytheme="vw" customBasepath="%{cp}css"/>

<link rel="stylesheet" href="css/customcss/ecrms.css" type="text/css"
	media="screen, projection">
<script type="text/javascript" src="js/jqueryjs/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript">

function pageSubmit()

{
	document.forms["loginfrm"].submit();
}
</script>

</head>
<body onload="pageSubmit();">	
	<s:if test='#session.userProfile.userLanguage.equals("fr")'>
	 	<s:param name="request_locale" >fr</s:param>
	</s:if>
	<s:form action="index" method="POST" id="loginfrm" name="loginfrm">		   	    
	    <s:hidden name="comingFrom" value="Login"/>
	</s:form>
</body>