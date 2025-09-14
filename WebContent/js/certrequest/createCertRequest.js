function closeInfoDialog(){
	$("#infoCertRequestDetail").dialog('close');	
}

//This function  is called on click of Submit button
function saveCertRequest()
{
	var error_container = document.getElementById('errorCreateReqDiv');
	$("#saveCertRequestButton").attr("disabled", true);	
	if(validateRequestDetails()){
		$("#saveCertRequestButton").attr("disabled", false);	
		return;
	}else{
		error_container.style.display='none';
		$("#createCertificateForm").ajaxSubmit({
			type : "POST",
			url : 'certrequest/verifyCertRequest.action',
			success: function(data) {
			
			//alert(data);
			if(data.isError && data.isError == 'true'){
					
				var errMsg = "<p class=\"error\"/>"+data.errorMsg+"</p>";
				//alert(errMsg);
				error_container.innerHTML=errMsg;
				error_container.style.display='';
    			
      		}else{			
      			if(data.certRequestStatus &&  data.certRequestStatus == 'ActiveCertForExstDlr'){
    				if(data.certRequestModel.statusCode == 'ACTIV'){
    					openConfirmDialogForExstDlr(data.certRequestModel.requestNum, data.certRequestModel.certActvnInd);
    				}else{
    					openConfirmDialogForPenRev();
    				}
    			}else if(data.certRequestStatus &&  data.certRequestStatus == 'ActiveCertForDiffDlr'){
    				if(data.certRequestModel.statusCode == 'ACTIV'){
    					openConfirmDialogForHWId();
    				}else{
    					openConfirmDialogForPenRev();
    				}
    				
    			}else{
    				$('#content').html(data);
    			}
      		}
      			
		},
		error: function(data) {
			//close ConfirmHwId dialog, if error occurred from this dialog
			closeConfirmHwIdForm();
			////close ConfirmExstDlr dialog, if error occurred from this dialog
			closeConfirmExstDlrForm();
		
			var errorData = data.responseText === null? data.statusText : data.responseText ;
			error_container.innerHTML=errorData;
			error_container.style.display='';
		}
			
		}); 
		$("#saveCertRequestButton").attr("disabled", false);	
	}
	
}
//to validate UI fields
function validateRequestDetails(){
	
	var errStatus = false;
	var errMsg = '';
	//  for error messages
	var container = document.getElementById('errorCreateReqDiv');
	
	var userType=$("#userType").val();
	//alert(userType);
	var deviceType = "";
	var deviceName = "";
	
	
	
	// for Contact Name
	var contactName=$("#contactName").val();
	//trimming the leading and trailing white spaces from the contactName entered
	contactName =contactName.replace(/^[\s]+/,'').replace(/[\s]+$/,'');
	// Display the formatted contactName value to the user.
	$('#contactName').val(contactName);
	contactName=escape($("#contactName").val());
	
	// for Contact Phone Number
	var contactPhoneNumber=$("#contactPhoneNumber").val();
	//trimming the leading and trailing white spaces from the  Contact Phone Number entered
	contactPhoneNumber =contactPhoneNumber.replace(/^[\s]+/,'').replace(/[\s]+$/,'');
	// Display the formatted  Contact Phone Number value to the user.
	$('#contactPhoneNumber').val(contactPhoneNumber);
	contactPhoneNumber=escape($("#contactPhoneNumber").val());
	
	
	// for hardware id
	var hardwareId=$("#hardwareId").val();
	//trimming the leading and trailing white spaces from the hardware id entered
	hardwareId =hardwareId.replace(/^[\s]+/,'').replace(/[\s]+$/,'');
	// Display the formatted hardware id value to the user.
	$('#hardwareId').val(hardwareId);
	hardwareId=escape($("#hardwareId").val());
		
	
	if(userType != "IRF") {
		
	// for device name
	 deviceName=$("#deviceName").val();
	//trimming the leading and trailing white spaces from the  device name entered
	deviceName =deviceName.replace(/^[\s]+/,'').replace(/[\s]+$/,'');
	// Display the formatted  device name value to the user.
	$('#deviceName').val(deviceName);
	deviceName=escape($("#deviceName").val());
	
	}
	
	var confirmEmail=$("#confirmEmail").val();
	var contactEmail=$("#contactEmail").val();
	
	if(userType != "IRF") {
		
	 deviceType=$("#deviceType").val();
	
	}
	
	
	
	// Dealer id for corporate and system admin
	var dlrNum=$("#dlrNum").val();
	//trimming the leading and trailing white spaces from the contactName entered
	dlrNum =dlrNum.replace(/^[\s]+/,'').replace(/[\s]+$/,'');
	// Display the formatted contactName value to the user.
	$('#dlrNum').val(dlrNum);
	dlrNum=escape($("#dlrNum").val());
	
	if(userType != "IRF") {
		
		
	//for dealer name
	var dealerName=$("#dealerName").val();
	var dealerState=$("#dealerState").val();
	var dealerCntry=$("#dealerCntry").val();
	
	// Dealer id for Dealer user
	var dealerNum=$("#dealerNum").val();
	
	//flag for valid user
	var validDlrFlag=$("#validDlrNum").val();
	
	//To validate Dealer number
	if(dealerNum == undefined && validDlrFlag == "false" && dlrNum != ''){
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('validate.dealerNum') +'</li>';
	}
	//To validate empty value for dealer in case of Admin/Corporate user
	if(dlrNum =='')
	{
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('required.dealerNum') +'</li>';
		
	}

	//validate dealer retrieved data
	if(dlrNum != '' &&(dealerName === '' || dealerState == '' || dealerCntry == '')){
		
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('required.dealerData') +'</li>';
		
	}
	
} //if not IRF
else { // For IRF, validate only DealerCode, not dealership name and address.
		
		//To validate empty value for dealer in case of Admin/Corporate user
		if(dlrNum =='')
		{
			errStatus = true;
			errMsg = errMsg+ '<li>'+getMessage('required.dealerNum') +'</li>';
			
		}
		
	}	
	//To validate empty value 
	if(contactName =='')
	{
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('required.contactName') +'</li>';
		
	}
	if(contactPhoneNumber =='')
	{
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('required.contactPhnNum') +'</li>';
		
	}
	if(contactEmail =='')
	{
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('required.contactEmail') +'</li>';
		
	}
	if(confirmEmail =='')
	{
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('required.confirmEmail') +'</li>';
		
	}		
	if(hardwareId =='')
	{
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('required.hardwareId') +'</li>';
		
	}
	
	//validation to allow only alphanumeric characters 
	var regHWId=/^[0-9a-zA-Z]+$/;
	if(hardwareId != '' && (regHWId.test(hardwareId) == false || hardwareId.length != 32))
	{
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('validate.hardwareId') +'</li>';
		
	}
	
	if(userType != "IRF") {
		
		if(deviceName =='')
		{
			errStatus = true;
			errMsg = errMsg+ '<li>'+getMessage('required.deviceName') +'</li>';
			
		}
			
		if(deviceType == '')
		{
			errStatus = true;
			errMsg = errMsg+ '<li>'+getMessage('required.deviceType') +'</li>';
			
		}
	
	}
	
	//verify contact email id
	
	if(contactEmail != ''){
		if(validateEmail(contactEmail)){
			
			errStatus = true;
			errMsg = errMsg+ '<li>'+getMessage('validate.contactEmail') +'</li>';
		}
	}
		
	
	//verify confirm email id
	if(confirmEmail != '' && confirmEmail != contactEmail){
		errStatus = true;
		errMsg = errMsg+ '<li>'+getMessage('validate.confirmEmail') +'</li>';
	}
	
	//alert("submitting... "+errStatus);
	
	if(errStatus)
	{	
	
	container.innerHTML=errMsg;
	container.style.display='';	
	return true;
	
	}else{
	return false;
	}
	
	
}

function closeConfirmHwIdForm(){
	$("#noConfirmDiffDlr").attr("disabled", true);	
	$("#confirmCertRequForHwId").dialog('close');
}

function closeConfirmExstDlrForm(){
	$("#noConfirmExstDlr").attr("disabled", true);	
	$("#confirmCertRequForExstDlr").dialog('close');
}


function retrieveDlrData(id)
{
	$("#saveCertRequestButton").attr("disabled", true);	
	var dealerNum = document.getElementById(id).value;
	var dlrContainer = document.getElementById('errorCreateReqDiv');
	
	//alert(dealerNum);
	var userType=$("#userType").val();
	//alert(userType);
	
	
	var space = /^[ ]{0,10}$/;
	
	if(dealerNum === null || dealerNum === '' || space.test(dealerNum))
	{
		dlrContainer.innerHTML="<li>"+getMessage('required.dealerNum')+"</li>";
		dlrContainer.style.display='';
		$("#saveCertRequestButton").attr("disabled", false);	
		
	}else
	{
		$("#indImg").css('display','inline');
		var dataStr = "certRequestModel.dealerNum="+dealerNum;
		//alert(dataStr);
		
		if(userType != "IRF") {
		
		$.ajax({
			type : "POST",
			url : 'certrequest/retrieveDealerDetails.action',
			data: dataStr,
			dataType : "json",
			success : function(result) {
			if(result && result.validDealerNum === "false")
			{
				$('#validDlrNum').val("false");
				dlrContainer.innerHTML="<li>"+getMessage('validate.dealerNum')+"</li>";
				dlrContainer.style.display='';
				$("#indImg").css('display','none');
				
				$('#dlrName').val('');
				$("#dualDlrNum").val('');
				$("#dlrStreetLine").val('');
				$("#dlrCity").val('');
				$("#dlrState").val('');
				$("#dlrZip").val('');
				$("#dlrCountry").val('');
				$("#contactName").val('');
				$("#dlrPhnNum").val('');
				
				 
				
				//setting values in hidden fields
				$('#dealerName').val('');
				$("#dualDealerNum").val('');
				$("#dealerStreetLine").val('');
				$("#dealerCity").val('');
				$("#dealerState").val('');
				$("#dealerZip").val('');
				$("#dealerCntry").val('');
				$("#dealerPhnNum").val('');
				
				
			}else
			{
				$('#validDlrNum').val("true");
				dlrContainer.style.display='none';
				$('#dlrName').val(result.certRequestModel.dealerName);
				$("#dualDlrNum").val(result.certRequestModel.dualDealerNum);
				$("#dlrStreetLine").val(result.certRequestModel.dealerStreetLine);
				$("#dlrCity").val(result.certRequestModel.dealerCity);
				$("#dlrState").val(result.certRequestModel.dealerState);
				$("#dlrZip").val(result.certRequestModel.dealerZip);
				$("#dlrCountry").val(result.certRequestModel.dealerCntry);
				$("#contactName").val(result.certRequestModel.contactName);
				$("#dlrPhnNum").val(result.certRequestModel.dealerPhnNum);
				
			
				//setting values in hidden fields
				$('#dealerName').val(result.certRequestModel.dealerName);
				$("#dualDealerNum").val(result.certRequestModel.dualDealerNum);
				$("#dealerStreetLine").val(result.certRequestModel.dealerStreetLine);
				$("#dealerCity").val(result.certRequestModel.dealerCity);
				$("#dealerState").val(result.certRequestModel.dealerState);
				$("#dealerZip").val(result.certRequestModel.dealerZip);
				$("#dealerCntry").val(result.certRequestModel.dealerCntry);
				$("#dealerPhnNum").val(result.certRequestModel.dealerPhnNum);		
				
			}
		},
		error : function(data) {
			   var errorData = data.responseText === null? data.statusText : data.responseText ;
			   dlrContainer.innerHTML=errorData;
			   dlrContainer.style.display='';
			  
			}
		});
		
		} // if not IRF
		
		 $("#indImg").css('display','none');
		$("#saveCertRequestButton").attr("disabled", false);	
	}
}


function closePrintReqDialog(){
	$("#printCertReqDialog").dialog('close');
	
	return false;
}

function checkEvent(evt)
{
        if (!evt)
        evt = event;

        if (evt.ctrlKey && (evt.keyCode==86 || evt.keyCode==67))
        {
                return true;
        }
        else
        {
                return false;
        }
}

function downloadCertificate(reqstNum){
	
	$("#downloadButton").attr("disabled", true);
	$.download('downloadCertificate.action?requestNum='+reqstNum, reqstNum );
	$("#downloadButton").attr("disabled", false);
	
}

//function to print certificate
function printThis(){
	$("#printButton").attr("disabled", true);	
 	$("#printable").printArea();
 	$("#printButton").attr("disabled", false);	
}

function resetCreateForm(){
	
	$("#resetButton").attr("disabled", true);	
	
		$("#createCertificateForm").ajaxSubmit({
			type : "POST",
			url : 'certrequest/createCertRequest.action',
			success: function(data) {
						
			$('#content').html(data);
			
		},
		error: function(data) {
			
			var errorData = data.responseText === null? data.statusText : data.responseText ;
			error_container.innerHTML=errorData;
			error_container.style.display='';
		}
			
		}); 
	$("#resetButton").attr("disabled", false);	
	
}
//function to open print info dialog
function openInfoDialogForRequest(){
	$("#confirmPenRevNStall").dialog('close');
	var userType=$("#userType").val();
	//alert(userType);
	var dealerOrCorp = $("#userTypeDlrCorp").val();
	//alert(dealerOrCorp);	
	
	$.alerts.okButton=getMessage('button.ok');
	
	if(userType != "IRF") {
		
		if(dealerOrCorp == "DLR") {
			var popUpMsg=getMessage('message.instructionPrintAlertDealerMsg');			
		}else{
			var popUpMsg=getMessage('message.instructionPrintAlertCorpMsg');
		}
		var info = jAlert(popUpMsg, getMessage('title.information'), function(confirm){
			if(confirm){
		
			}
		});
	} 
	
}

//download certificate from create certificate page respective to  request number
function openDwnldInfoForCreate(requestNumber, certActivationInd){
	requestNumber= parseInt(requestNumber);
	
	if(certActivationInd == 'true'){
		$("#confirmCertRequForExstDlr").dialog('close');
		$.alerts.okButton=getMessage('button.ok');
		var info = jAlert(getMessage('message.download'), getMessage('title.downloadInstr'), function(confirm){
			if(confirm){
				
				$.download('downloadCertificate.action?requestNum='+requestNumber, requestNumber );
			}
		});
	}else{
	
		$.alerts.okButton=getMessage('button.ok');
		var info = jAlert(getMessage('required.downloadcert'), getMessage('title.downloadInstr'), function(confirm){});
	}
}

function revokeNCreateForExstDlr(){
	
	var container = document.getElementById('errorCreateReqDiv');
	
		$("#confirmExtDlrForm").ajaxSubmit({
			type : "POST",
			url : 'certrequest/saveCertificate.action',
			success: function(data) {
				if(data.isError && data.isError == 'true'){
				
				$("#confirmCertRequForExstDlr").dialog('close');
				var errMsg = "<p class=\"error\"/>"+data.errorMsg+"</p>";
				container.innerHTML=errMsg;
				container.style.display='';
    			
      		}else{
      			$("#confirmCertRequForExstDlr").dialog('close');
      			$('#content').html(data);
			}
		},
		error: function(data) {
			$("#confirmCertRequForExstDlr").dialog('close');
			var errorData = data.responseText === null? data.statusText : data.responseText ;
			container.innerHTML=errorData;
			container.style.display='';
		}
			
		}); 
			
	
}

function revokeNCreateForHwId(){
	
	var container = document.getElementById('errorCreateReqDiv');
	
		$("#confirmHwId").ajaxSubmit({
			type : "POST",
			url : 'certrequest/saveCertificate.action',
			success: function(data) {
			
			if(data.isError && data.isError == 'true'){
				
				$("#confirmCertRequForHwId").dialog('close');
				var errMsg = "<p class=\"error\"/>"+data.errorMsg+"</p>";
				container.innerHTML=errMsg;
				container.style.display='';
    			
      		}else{
      			$("#confirmCertRequForHwId").dialog('close');
      			$('#content').html(data);
			}
		},
		error: function(data) {
			
			$("#confirmCertRequForHwId").dialog('close');
			var errorData = data.responseText === null? data.statusText : data.responseText ;
			container.innerHTML=errorData;
			container.style.display='';
		}
			
		}); 
			
	
}

function closeAndCreateNew(){
	
	var container = document.getElementById('errorCreateReqDiv');
	
		$("#confirmPenRev").ajaxSubmit({
			type : "POST",
			url : 'certrequest/saveCertificate.action',
			success: function(data) {
			
			if(data.isError && data.isError == 'true'){
				
				$("#confirmPenRevNStall").dialog('close');
				var errorMsg = "<p class=\"error\"/>"+data.errorMsg+"</p>";
				container.innerHTML=errorMsg;
				container.style.display='';
    			
      		}else{
      			    			
      			$("#confirmPenRevNStall").dialog('close');
      			$('#content').html(data);
			}
		},
		error: function(data) {
			
			$("#confirmPenRevNStall").dialog('close');
			var errorData = data.responseText === null? data.statusText : data.responseText ;
			container.innerHTML=errorData;
			container.style.display='';
		}
			
		}); 
			
	
}

function closePenRevnStallDailog(){
	$("#confirmPenRevNStall").dialog('close');
}


function checkButtonSubmit(e) 
{
    if(e && e.keyCode == 13)    
    {  
    	saveCertRequest();   
         return false;
    }
 } 