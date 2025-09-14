
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<head>
<link rel="stylesheet" href="css/blueprint/screen.css" type="text/css"
	media="screen, projection">
</head>
<body>
	
<div id="page" class="container"> 
	<s:include value="./../header.jsp"/>
	
	
		<s:form name="logout" method="POST">
		<div class=" span-12" style="width:350px; float:center">
			<s:if test="hasActionErrors()">
				<div class="error">
					<s:actionerror/>
				</div>
			</s:if>
			
			<s:label cssClass="span-12" theme="simple" cssStyle="font-weight:normal">You have successfully logged out from the application.</s:label>
			<s:label cssClass="span-12" theme="simple" cssStyle="font-weight:normal">Please click on the Login button to login to the application again.</s:label>
			
		    <div class="span-6 center"><sj:a  href="index.action" button="true" value="Login" targets="page">Login</sj:a>
		    </div>
		</div>
		</s:form>
	
	</div>

	

	
</body>

<s:include value="./../footer.jsp"/>
