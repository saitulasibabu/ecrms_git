<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>

<head>
<script type="text/javascript" src="js/common/common.js"></script>
</head>

<div>
	<s:label key="err.session.expired.msg" theme="simple" cssStyle="font-weight:normal;font-size:102%;"></s:label>
</div>
<br>
<div>
	<sj:a button="true" href="#" onclick="closeOnSessionExpiry()"><s:property value="getText('button.close')" /></sj:a>
</div>
		



