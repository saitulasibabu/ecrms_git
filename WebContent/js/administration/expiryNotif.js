//This function  is called on click of Submit button
function saveExpiryNotification()
{
	var error_container = document.getElementById('ExpiryNotifErrorDiv');
	var success_container = document.getElementById('ExpiryNotifSuccessDiv');	
	
	$("#saveExpiryNotificationButton").attr("disabled", true);
	$("#resetExpiryNotificationButton").attr("disabled", true);
	$("#saveExpiryNotificationButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#resetExpiryNotificationButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	//To empty the container
	error_container.innerHTML='';
	success_container.innerHTML='';
	

	if(validate()){
		return;
	}else{
		$("#expiryNotificationForm").ajaxSubmit({
			type : "POST",
			url : 'administration/saveExpiry.action',
			success: function(data) {
			
				var sessionTimeOut = handleComplexSessionExpiry(data);

				if(sessionTimeOut == 'false'){		
					if(data.exceptionMessage != ''){
		
						var errorMsg = "<p class=\"error\"/>"+data.exceptionMessage+"</p>";
								
						error_container.style.display=''				
						error_container.innerHTML=errorMsg;
						
					}else{
						error_container.style.display='none';
						var successData = getMessage('success.expiryNotification');		
						success_container.innerHTML=successData;
						success_container.style.display='';
					}
				}
				
				$("#saveExpiryNotificationButton").attr("disabled", false);
				$("#resetExpiryNotificationButton").attr("disabled", false);
				$("#saveExpiryNotificationButton").focus();
				$("#resetExpiryNotificationButton").focus();
				$("#fromExpNotification").focus();
			
			},
			error: function(data) {
				
				success_container.style.display='none';
				var errorMsg = "<p class=\"error\"/>"+data.exceptionMessage+"</p>";
				error_container.innerHTML=errorMsg;
				error_container.style.display='';
				
				$("#saveExpiryNotificationButton").attr("disabled", false);
				$("#resetExpiryNotificationButton").attr("disabled", false);
				$("#saveExpiryNotificationButton").focus();
				$("#resetExpiryNotificationButton").focus();
				$("#fromExpNotification").focus();
				
			}
			
		}); 
		
	}
	
}



//To validate empty value
function validate(){
	var container = document.getElementById('ExpiryNotifErrorDiv');
	var errStatus = false;
	var errMsg = '';
	var expiryFromValue = $("#fromExpNotification").val();
	
	var success_container = document.getElementById('ExpiryNotifSuccessDiv');
	if(expiryFromValue == ''){
		
		errStatus = true;
		errMsg = getMessage('required.fromEmail');
	
	}
	
	if(expiryFromValue != ''){
		
		if(validateEmail(expiryFromValue)){
			
			errStatus = true;
			errMsg = getMessage('validate.fromEmail');
		}
	}

	
	if(errStatus)
	{	
		container.innerHTML=errMsg;
		container.style.display='';	
		 $("#saveExpiryNotificationButton").attr("disabled", false);
		$("#resetExpiryNotificationButton").attr("disabled", false);
		success_container.style.display='none';
		return true;
	}else{
		return false;
	}
}

