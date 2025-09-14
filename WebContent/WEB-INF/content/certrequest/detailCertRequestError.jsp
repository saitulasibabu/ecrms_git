<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<head>
<script type="text/javascript" src="js/certrequest/createCertRequest.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.printArea.js"></script>

<script>


$(document).ready( function() {
	
	closeConfirmExstDlrForm();
	closeConfirmHwIdForm();
	//openInfoDialogForRequest();
	
	
});


</script>
</head>
<body>


<div id="createCertificate" class="span-22 inline error">

	 <!-- <div class="span-20" style="border:1px solid black;margin:0px;padding-bottom:20px;margin-left:60px">  -->
	<div id="header" class="span-20">
	<s:label value="%{errorMsg}"  cssStyle="font-weight:normal;font-size:102%" />
	</div>
	<!-- </div> --> 
</div>
<div class="clear span-19"></div>
</body>

