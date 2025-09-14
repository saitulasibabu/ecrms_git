<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<head>
<script type="text/javascript" src="js/certrequest/createCertRequest.js"></script>
<script type="text/javascript" src="js/searchcertrequest/download.jQuery.js"/></script>

</head>

<s:form id="confirmExtDlrForm" name="confirmExtDlrForm" >
<s:hidden id="reqstNum" name="certRequestModel.requestNum" value="%{certRequestModel.requestNum}" />
<s:hidden id="actvnInd" name="certRequestModel.certActvnInd" value="%{certRequestModel.certActvnInd}" />
<div id="confirmExtDlr">

<div>
	<s:label key="message.confirmExstDlr" theme="simple" cssStyle="font-weight:normal;font-size:102%;"></s:label>
	</div>
<div style="text-align:center; margin: 1em 0em 0em 0em;">
	<sj:a button="true" cssStyle="margin: 0em 2em 1em 0em;" id="downloadButton"  onclick="openDwnldInfoForCreate(document.getElementById('reqstNum').value, document.getElementById('actvnInd').value);">
		 <s:property value="getText('button.download')"  /></sj:a>
	<%-- <sj:a button="true"  cssStyle="margin: 0em 2em 1em 0em;"
		href="#" onclick="revokeNCreateForExstDlr()" id="revokeNCreate"
		targets="createCert" ><s:property value="getText('button.create')" /></sj:a> --%>
	<sj:a  button="true" href="certrequest/saveCertificate.action"
	cssStyle="margin: 0em 2em 1em 0em;"  targets="createCert">
	<s:property value="getText('button.create')" /></sj:a>
	</div>
</div>
</s:form>



