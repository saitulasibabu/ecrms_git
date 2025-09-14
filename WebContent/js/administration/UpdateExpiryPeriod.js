//This function is called on click for save button
function saveExpiryPeriod()
{
	var error_container = document.getElementById('ExpiryPeriodErrorDiv');
	var success_container = document.getElementById('ExpiryPeriodSuccessDiv');		
    var test = $("#test").val();
    $("#saveExpiryPeriodButton").attr("disabled", true);
	$("#resetExpiryPeriodButton").attr("disabled", true);
	$("#saveExpiryPeriodButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#resetExpiryPeriodButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	// Empty the already filled container.
	success_container.innerHTML='';
	error_container.innerHTML='';
	
	
	if(validate()){
		
		return;
	}else{
		$("#adminExpiryPeriod").ajaxSubmit({
			type : "POST",
			url : 'administration/saveExpiryPeriod.action',
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
					var successData = getMessage('success.expiryPeriod');		
					success_container.innerHTML=successData;
					success_container.style.display='';
				}
			}
		
			 $("#saveExpiryPeriodButton").attr("disabled", false);
			$("#resetExpiryPeriodButton").attr("disabled", false);
			$("#saveExpiryPeriodButton").focus();
			$("#resetExpiryPeriodButton").focus();
			$("#expiryPeriodValue").focus();
			
		},
		error: function(data) {
			
			success_container.style.display='none';
			
			var errorMsg = "<p class=\"error\"/>"+data.exceptionMessage+"</p>";
			error_container.innerHTML=errorMsg;
			error_container.style.display='';
			 $("#saveExpiryPeriodButton").attr("disabled", false);
			$("#resetExpiryPeriodButton").attr("disabled", false);
			$("#saveExpiryPeriodButton").focus();
			$("#resetExpiryPeriodButton").focus();
			$("#expiryPeriodValue").focus();
			
		}
			
		}); 
		
	}
	
}
//To validate empty value and 0
function validate(){
	var error_container = document.getElementById('ExpiryPeriodErrorDiv');
	var success_container = document.getElementById('ExpiryPeriodSuccessDiv');		
	var container = document.getElementById('ExpiryPeriodErrorDiv');
	var errStatus = false;
	var errMsg = '';
     
	var expiryPeriodValue = $("#expiryPeriodValue").val();
	if(expiryPeriodValue==''){
		errStatus = true;
		errMsg = getMessage('required.expiryPeriod');			
       
		
	
	}
	if(expiryPeriodValue<=0 && expiryPeriodValue!=''){
		errStatus = true;

		errMsg =  getMessage('required.expiryPeriodNonZero');	
	}
	
	if(errStatus)
	{	
	
	container.innerHTML=errMsg;
	container.style.display='';	
	success_container.style.display='none';
	$("#saveExpiryPeriodButton").attr("disabled", false);
	$("#resetExpiryPeriodButton").attr("disabled", false);
	
	return true;
	
	}else{
	
		return false;
	
	}
	}

 

