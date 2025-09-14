<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>

<head>
<script type="text/javascript" src="js/common/common.js"></script>
<link rel="stylesheet" href="css/blueprint/screen.css" type="text/css"
	media="screen, projection">
</head>

<script>
$(document).ready( function() {
	
	$.alerts.okButton=getMessage('button.Close');
	var info = jAlert(getMessage('err.session.expired.dialog'), getMessage('title.information'), function(confirm){
		if(confirm){
			closeOnSessionExpiry();
		}
	});
});

</script>

<!-- ECRMSSessionTimeOut -->


