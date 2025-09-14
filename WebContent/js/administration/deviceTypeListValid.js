//This function  is called on click of Submit button


var checkData;
var allDeviceTypeCode;
function reloadGrid() {
	$.publish('reloadGridTable');
	$("#deviceTypeCodeValue").val('');
	$("#deviceTypeDescValue").val('');
	$("#saveDeviceTypeButton").focus();
	
	$("#resetDeviceTypeButton").focus();
	
	$("#deviceTypeCodeValue").focus();
	return false;
}


function saveDeviceType()
{    
	var error_container = document.getElementById('DeviceTypeListErrorDiv');
	var success_container = document.getElementById('DeviceTypeListSuccessDiv');
	$("#saveDeviceTypeButton").attr("disabled", true);
	$("#resetDeviceTypeButton").attr("disabled", true);
	$("#saveDeviceTypeButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$("#resetDeviceTypeButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	// To empty the container
	error_container.innerHTML='';
	success_container.innerHTML='';
	checkData = $("input[type=checkbox]").map( function () {return this.id + '|' + this.checked;}).get().join(",");
    allDeviceTypeCode = $("input[type=checkbox]").map( function () {return this.id;}).get().join("~");
    
    
    
	        if(validate()){
                return;	
	        } else{
	        	$("#checkedDeviceIdData").val(checkData);
                $("#deviceTypeForm").ajaxSubmit({
			type : "POST",
			url : 'administration/saveDeviceType.action',
			success: function(data) {
              		
            		//handling session time out
                	//Pass the 'data' to the method where the return is forced to success
                	var sessionTimeOut = handleComplexSessionExpiry(data);
            		
                	if(sessionTimeOut == 'false'){	
                    	if(data.isError == 'true'){
                    		var errorMesg = "<p class=\"error\"/>"+data.errorMsg+"</p>";
                    		success_container.style.display='none';
                    		error_container.innerHTML=errorMesg;
                			error_container.style.display='';
                			
                  		}else{
                  			error_container.style.display='none';
                  			var successData =''; 
                  			if($("#deviceTypeCodeValue").val() != ''){
                  				successData = getMessage('success.deviceType');
                        	}else{
                 				successData = getMessage('success.deviceTypeUpdate');
                        	}
                  			
                      		success_container.innerHTML=successData;
                      		success_container.style.display='';
                      		
                  		}
                    	
              			reloadGrid();  
                	}

          			$("#saveDeviceTypeButton").attr("disabled", false);
          			$("#resetDeviceTypeButton").attr("disabled", false);
          			$("#saveDeviceTypeButton").focus();
          			$("#resetDeviceTypeButton").focus();
          			$("#deviceTypeCodeValue").focus();
                               
                },
		error: function(data) {
			
			var errorData = data.responseText === null? data.statusText : data.responseText ;
			
			error_container.innerHTML=errorData;
			error_container.style.display='';
			$("#saveDeviceTypeButton").attr("disabled", false);
  			$("#resetDeviceTypeButton").attr("disabled", false);
  			$("#saveDeviceTypeButton").focus();
  			$("#resetDeviceTypeButton").focus();
  			$("#deviceTypeCodeValue").focus();
		}
			
		}); 
		
	        }
}


//To validate empty value




function validate(){
	var container = document.getElementById('DeviceTypeListErrorDiv');
	var errStatus = false;
	var errMsg = '';
	var success_container = document.getElementById('DeviceTypeListSuccessDiv');
	var deviceTypeCodeValue = $("#deviceTypeCodeValue").val();
	var deviceTypeDescValue = $("#deviceTypeDescValue").val();
	var splitDevice = allDeviceTypeCode.split('~');
	
    
	if(deviceTypeCodeValue != '' && deviceTypeDescValue == ''){
		errStatus = true;
		errMsg = getMessage('required.deviceTypeDesc');
		
		
	}
	if(deviceTypeDescValue != '' && deviceTypeCodeValue =='' ){
		errStatus = true;
		errMsg = getMessage('required.deviceTypeCode');
		}
	
   	

    if(deviceTypeDescValue != '' && deviceTypeCodeValue !='' )
     {

	    for(i=0;i<splitDevice.length;i++)
            { 
                if(deviceTypeCodeValue == splitDevice[i])
                    {
	                   
	                var errMessage = getMessage('error.duplicate.deviceTypeCode');
	                success_container.style.display='none';
	               	container.innerHTML=errMessage;
	               	container.style.display='';	
	               	$("#saveDeviceTypeButton").attr("disabled", false);
	           		$("#resetDeviceTypeButton").attr("disabled", false);
                    return true;
                    }
                
  	         }
	}
    
	
	
    if(errStatus)
	  {	
	
    	success_container.style.display='none';
    	container.innerHTML=errMsg;
    	container.style.display='';	
    	$("#saveDeviceTypeButton").attr("disabled", false);
		$("#resetDeviceTypeButton").attr("disabled", false);
		return true;
	
	  }
		
		
    else
      {
		
    	return false;
		
	  }
	
}


