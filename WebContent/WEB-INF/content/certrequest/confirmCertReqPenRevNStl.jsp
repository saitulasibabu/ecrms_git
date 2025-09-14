<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>


<head>
<script type="text/javascript" src="js/certrequest/createCertRequest.js"></script>
</head>

<s:form id="confirmPenRevForm" name="confirmPenRevForm" >
<div id="confirmPenRev">

	<div>
	<s:label key="message.confirmPenRvReq" theme="simple" cssStyle="font-weight:normal;font-size:102%;"></s:label>
	</div>
<div style="text-align:center; margin: 1em 0em 0em 0em;">

	<%-- <sj:a button="true" href="#"
		cssStyle="margin: 0em 2em 1em 0em;" targets="content" onclick="closeAndCreateNew()"><s:property value="getText('button.yes')" /></sj:a> --%>
	<sj:a  button="true" href="certrequest/saveCertificate.action"
	cssStyle="margin: 1em 1em 1em 1em;width:50px;"  targets="content">
	<s:property value="getText('button.yes')" /></sj:a>
	<sj:a button="true" id="noconfirmPenRev" cssStyle="margin: 0em 2em 1em 0em;" targets="createCert"
		onclick="closePenRevnStallDailog()" > <s:property value="getText('button.no')" /></sj:a>

</div>
		
</div>
</s:form>



