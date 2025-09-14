<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:url var="eshopURL" action="certrequest/verifyEshopLicense" />
<head>
<link href="css/dropdown/dropdown.css" media="screen" rel="stylesheet" type="text/css" />
<link href="css/dropdown/themes/default/default.css" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/eshop/verifyEshopLicense.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript">
function openEshopDialogForCreate(){
	$("#eshopDialog").dialog({
		closeOnEscape: true,    open: function(event, ui) { $(".ui-dialog-titlebar-close").show(); } 
		}); 
    $("#eshopDialog").load("<s:property value="eshopURL" />", 
		function(response, status, xhr) {
			if (status == "error") {
				var msg = getMessage('err.unable.retrieve');
				$("#confirmCertRequForExstDlr").html(msg + xhr.status + " " + xhr.responseText);
			}
		});
    
	$("#eshopDialog").dialog('open');
}

</script>
<!--[if lte IE 7]>
<script type="text/javascript" src="struts/js/plugins/jquery-1.4.3.min.js"></script>
<script type="text/javascript" src="struts/jquery.dropdown.js"></script>
<![endif]-->

<script type="text/javascript">
	$(document).ready(function(){
		// for every link in the menu item attach a click event
		$('.iet_main_menu ul li a').click(function(){
			// first reset all the menu background to default
			$('.iet_main_menu ul li').css('background-color','#F6F6F6');
			// set the currently clicked anchor background to #dadada one.
			$(this).parent().css('background-color','#dadada');
		});
		
		// by default, first menu item should be in #dadada background
		$('.iet_main_menu ul li a:first').parent().css('background-color','#dadada');
	});
	
	
</script>

</head>
<body>

<div class="iet_main_menu span-24" style="margin-top: -0.3%;">
<ul id="nav" class="dropdown dropdown-horizontal">

<li><sj:a href="loadBasicSearch.action"  targets="content" indicator="loadingInd"><s:property value="getText('menu.home')"/></sj:a></li>

<s:if test='#session.userProfile.userRoleList.contains("ECRMS_CORP")
			||#session.userProfile.userRoleList.contains("ECRMS_SUPER_USER")
			||#session.userProfile.userRoleList.contains("ECRMS_DLR")
			||#session.userProfile.userRoleList.contains("ECRMS_ADMIN")'>
<li><sj:a  href="#" onclick="openEshopDialogForCreate()" indicator="loadingInd"><s:property value="getText('menu.createCertificate')"/></sj:a></li>
</s:if>

<%-- <s:if test='#session.userProfile.userRoleList.contains("ECRMS_SUPER_USER")||#session.userProfile.userRoleList.contains("ECRMS_ADMIN")'> --%>
<s:if test='#session.userProfile.userRoleList.contains("ECRMS_ADMIN")'>
<li><sj:a href="certrequest/createCertRequest.action?IRFUser=TRUE" cssClass="menubar" targets="content" indicator="loadingInd"><s:property value="getText('menu.IRFCertificate')"/></sj:a></li>
</s:if>

<s:if test='#session.userProfile.userRoleList.contains("ECRMS_CORP")
			||#session.userProfile.userRoleList.contains("ECRMS_SUPER_USER")
			||#session.userProfile.userRoleList.contains("ECRMS_VIEW")
			||#session.userProfile.userRoleList.contains("ECRMS_ADMIN")'>
<li><sj:a href="advanceSearchRequest.action" cssClass="menubar" targets="content" indicator="loadingInd"><s:property value="getText('menu.advancedSearch')"/></sj:a></li>
</s:if>


<%-- <li><sj:a href="#" onclick="window.open('%{#session.eshopLink}')" targets="content" indicator="loadingInd"><s:property value="getText('menu.goToEShop')"/></sj:a></li> --%>
<li><sj:a href="#" onclick="window.open('%{#session.eshopLink}', 'myWindow')" indicator="loadingInd"><s:property value="getText('menu.goToEShop')"/></sj:a></li>

<s:if test='#session.userProfile.userRoleList.contains("ECRMS_ADMIN")'>
<li><sj:a href="administration/loadIndex.action" cssClass="menubar" targets="content" indicator="loadingInd"><s:property value="getText('menu.administration')"/></sj:a></li>
</s:if>
<s:if test='#session.userProfile.userLocale.toString().equals("fr")'>
<li><sj:a href="#" cssClass="menubar" onclick="window.open('assets/Help_Document_eCRMS-FRENCH.pdf', 'myWindow')" indicator="loadingInd"><s:property value="getText('menu.help')"/></sj:a></li>
</s:if>
<s:else>
<li><sj:a href="#" onclick="window.open('assets/Help_Document_eCRMS-ENG.pdf', 'myWindow')" indicator="loadingInd"><s:property value="getText('menu.help')"/></sj:a></li>
</s:else>

<li style="left:1070px;position:absolute;width:100px;text-align: center;"> <sj:a href="#" onclick="closeWindow()" ><s:property value="getText('menu.closeECRMS')"/></sj:a>  </li> 

</ul>

</div>
<div id="loadingInd" style="margin-left:1px;display:none;" >
<img id="indctImg" src="assets/indicator.gif" style="float:left;"/>
<s:label key="label.loading" theme="simple" cssClass="span-4 last" cssStyle="font-weight:normal;font-size:101%" />
</div>
<div class="bottom">
<s:include value="footer.jsp"/>
</div>
<sj:dialog id="eshopDialog" title="%{getText('tooltip.verifyeshop')}"
		autoOpen="false" modal="true" onCloseTopics="eshopDialogClosed"
		width="380" height="155" cssStyle="overflow:hidden;"
		/>
</body>