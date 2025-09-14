<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<head>
<script type="text/javascript" src="js/certrequest/createCertRequest.js"></script>
</head>

<div id="confirmHwId">
<div>
	<s:label  key="message.confirmCreate" theme="simple" cssStyle="font-weight:normal;font-size:102%;"></s:label>
</div>

<div style="text-align:center; margin: 1em 0em 0em 0em;">
<sj:a button="true" href="#" cssStyle="margin: 0em 2em 1em 0em;" targets="createCert" onclick="revokeNCreateForHwId()"><s:property value="getText('button.yes')" /></sj:a>
<sj:a button="true" id="noConfirmDiffDlr" cssStyle="margin: 0em 2em 1em 0em;" onclick="closeConfirmHwIdForm()" ><s:property value="getText('button.no')" /></sj:a>
</div>

</div>
