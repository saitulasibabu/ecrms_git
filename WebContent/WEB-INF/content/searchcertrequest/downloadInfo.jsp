<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<head>
<script type="text/javascript" src="js/common/common.js"/></script>

</head>
<s:form id="downloadInfo" name="downloadInfo">

<div class="span-10">
<s:label key="tooltip.download" theme="simple" cssStyle="font-weight:normal"></s:label>
<div class="push-4 span-4 ">

<s:if test='fileName != null'>
<s:hidden id="cfileName" name="fileName" value="%{fileName}"/>
<sj:a button="true" onclick="return downloadCertByFileName(document.getElementById('cfileName').value)"
	cssStyle="width:40%; margin-right:60px; float:center" targets="content">
	<s:property value="getText('button.ok')" /></sj:a>

</s:if>
<s:else>
<s:hidden id="reqstNumber" value="%{certRequestModel.requestNum}" />

<sj:a button="true" onclick="return downloadCertByRequest(document.getElementById('reqstNumber').value)"
	cssStyle="width:40%; margin-right:60px; float:center" targets="content">
	<s:property value="getText('button.ok')" /></sj:a>

</s:else>
</div>
</div>
</s:form>