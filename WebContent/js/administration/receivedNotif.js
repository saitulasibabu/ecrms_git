//This function  is called on click of Submit button
function saveReceivedNotification()
{
	var error_container = document.getElementById('ReceivedNotifErrorDiv');
	var success_container = document.getElementById('ReceivedNotifSuccessDiv');		
	
	//Disable the buttons to prevent double click
	$("#saveReceivedNotificationButton").attr("disabled", true);
	$("#resetReceivedNotificationButton").attr("disabled", true);
	$("#saveReceivedNotificationButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#resetReceivedNotificationButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	// Empty the already filled container.
	success_container.innerHTML='';
	error_container.innerHTML='';

	if(validate()){
		return;
	}else{
		$("#receivednotificationForm").ajaxSubmit({
			type : "POST",
			url : 'administration/saveRecieved.action',
			success: function(data) {

				var sessionTimeOut = handleComplexSessionExpiry(data);

				if(sessionTimeOut == 'false'){
					if(data.exceptionMessage != ''){
						success_container.style.display='none';
						var errorMsg = "<p class=\"error\"/>"+data.exceptionMessage+"</p>";
								
						error_container.style.display=''				
						error_container.innerHTML=errorMsg;
						
					}else{
						error_container.style.display='none';
						var successData = getMessage('success.receivedNotification');		
						success_container.innerHTML=successData;
						success_container.style.display='';
					}
				}
	
				$("#saveReceivedNotificationButton").attr("disabled", false);
				$("#resetReceivedNotificationButton").attr("disabled", false);
				$("#saveReceivedNotificationButton").focus();
				$("#resetReceivedNotificationButton").focus();
				
				$("#receivedFromValue").focus();
			},
			error: function(data) {
				
				success_container.style.display='none';
				var errorMsg = "<p class=\"error\"/>"+data.exceptionMessage+"</p>";
				error_container.innerHTML=errorMsg;
				error_container.style.display='';
				$("#saveReceivedNotificationButton").attr("disabled", false);
				$("#resetReceivedNotificationButton").attr("disabled", false);
				$("#saveReceivedNotificationButton").focus();
				$("#resetReceivedNotificationButton").focus();
				
				$("#receivedFromValue").focus();
			}
		
			
		}); 
		
	}
	
}



//To validate empty value
function validate(){
	var container = document.getElementById('ReceivedNotifErrorDiv');
	var errStatus = false;
	var errMsg = '';
	var success_container = document.getElementById('ReceivedNotifSuccessDiv');
	var receivedFromValue = $("#receivedFromValue").val();
	
	if(receivedFromValue == ''){
		errStatus = true;
		errMsg = getMessage('required.fromEmail');
	
	}
	if(receivedFromValue != ''){
		
		if(validateEmail(receivedFromValue)){
			
			errStatus = true;
			errMsg = getMessage('validate.fromEmail');
		}
	}
		
	
	if(errStatus)
	{	
		success_container.style.display='none';
		container.innerHTML=errMsg;
		container.style.display='';	
		$("#saveReceivedNotificationButton").attr("disabled", false);
		$("#resetReceivedNotificationButton").attr("disabled", false);
		return true;
	
	}else{
		return false;
	}
}

