
// This method is used to reload Grid of advanced search
function reloadGrid() {
	//Disable search button until grid reloads completely
	$("#search").attr("disabled", true);
	
	
	//publish grid
	$.publish('reloadGridTable');
		return false;
}
//validate date format
function checkdate(inputval){
	var validformat=/^\d{2}\/\d{2}\/\d{4}$/; //Basic check for format validity
	var returnval=false;
	if (!validformat.test(inputval))
		returnval = false;
	else
	{ //Detailed check for valid date ranges
		var monthfield=inputval.split("/")[0];
		var dayfield=inputval.split("/")[1];
		var yearfield=inputval.split("/")[2];
		var dayobj = new Date(yearfield, monthfield-1, dayfield);
		if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield))
		returnval = false;
		else
		returnval=true;
	}
	return returnval;
	}
//validation for pasted from issue date
$("#fromdate1").change(function(e) {
	 
    var value =  $("#fromdate1").val();
   if(checkdate(value)){
    	return true;
    	
    }else{
    	$("#fromdate1").val('');
    } 
});
//validation for pasted from expiring date
$("#fromdate2").change(function(e) {
	 
    var value =  $("#fromdate2").val();
   if(checkdate(value)){
    	return true;
    	
    }else{
    	$("#fromdate2").val('');
    } 
});
//validation for pasted from revocation date
$("#fromdate3").change(function(e) {
	 
    var value =  $("#fromdate3").val();
   if(checkdate(value)){
    	return true;
    	
    }else{
    	$("#fromdate3").val('');
    } 
});
//validation for pasted To issue date
$("#todate1").change(function(e) {
	 
    var value =  $("#todate1").val();
   if(checkdate(value)){
    	return true;
    	
    }else{
    	$("#todate1").val('');
    } 
});
//validation for pasted To expiring date
$("#todate2").change(function(e) {
	 
    var value =  $("#todate2").val();
   if(checkdate(value)){
    	return true;
    	
    }else{
    	$("#todate2").val('');
    } 
});
//validation for pasted To revocation date
$("#todate3").change(function(e) {
	 
    var value =  $("#todate3").val();
   if(checkdate(value)){
    	return true;
    	
    }else{
    	$("#todate3").val('');
    } 
});

//to clear previous rows of result table of advanced search
function beforeValidateAdvancedSearch()
{
	// clear grid data of table
	$("#certificatesList").jqGrid("clearGridData", true);
	// if there is no data clear export to excel Button
	var exportbtn = document.getElementById('exportToExcel');
	exportbtn.style.display='none';
	// call validation function to validate and get result for given search criteria
	validateAdvancedSearch();
}


//This method is used to validate different fields of advanced search page
function validateAdvancedSearch(){
	
	var errStatus = false;
	var errMsg = '';
	var str='';
	
	//  for error messages
	var container = document.getElementById('advancedSearchDiv');
	
	//date fields
	var fromIssueDate=$("#fromdate1").val(); //Date -from expiry date
	var toIssueDate=$("#todate1").val();     //Date -To expiry date
	var fromExpiryDate=$("#fromdate2").val(); //Date -from expiry date
	var toExpiryDate=$("#todate2").val();     //Date -To expiry date
	var fromRevokedDate=$("#fromdate3").val(); //Date -from expiry date
	var toRevokedDate=$("#todate3").val();     //Date -To expiry date
	
	
	
	// for dealer number
	var dealerNumber=$("#dealerNumber").val();
	
	// for hardware id
	var hardwareId=$("#hardwareIdField").val();
	hardwareId=escape($("#hardwareIdField").val());
	
	//validation for hardware id-
	if(hardwareId != '' && hardwareId != null)
	{
		//check hardware id length	
		if(hardwareId.length != 32)
		{
			//if it is not 32 digit then check that if it contains '*' i.e. wild card searching
			if(hardwareId.indexOf("*") == -1)
			{
				errMsg+='<li>'+getMessage('validate.hardwareId') +'</li>';
				errStatus=true;
			}
		}
	}
	//for requestNum
	 var requestnum = $("#requestNumber").val();
	 //validate request number is numeric or not.
	 if(requestnum.indexOf("*") == -1)
	 {
		 if(requestnum !='' && parseInt(requestnum) != requestnum)
		 {
			 errMsg+='<li>'+getMessage('validate.requestNumber') +'</li>';
			 errStatus=true;
		 }
	 }
	 
	// To validate that 'to issue date' is enter, 'from issue date' is required 	
	if(toIssueDate != '')
	{
		if(fromIssueDate == '')
		{
			errMsg+='<li>'+getMessage('required.issuefromdate') +'</li>';
			errStatus=true;
		}
	}
	// To validate that 'to Expiry date' is enter, 'from Expiry date' is required 	
	if(toExpiryDate != '')
	{
		if(fromExpiryDate == '')
		{
			errMsg+='<li>'+getMessage('required.expiryfromdate') +'</li>';
			errStatus=true;
		}
	}
	// To validate that 'to Revoked date' is enter, 'from Revoked date' is required 	
	if(toRevokedDate != '')
	{
		if(fromRevokedDate == '')
		{
			errMsg+='<li>'+getMessage('required.revokedfromdate') +'</li>';
			errStatus=true;
		}
	}
	
	if(fromIssueDate != '')
	{ 
		// To validate if the from issue date is greater than to issue date 
	    if(toIssueDate != '')
		{
			var fromIssueDateFormat = new Date(fromIssueDate);
			var toIssueDateFormat = new Date(toIssueDate);
			
			if(fromIssueDateFormat > toIssueDateFormat)
				{

				errMsg+='<li>'+getMessage('validate.issuedaterange') +'</li>';
				errStatus=true;
				}
		}
		else if(toIssueDate == '')
		{
			// To validate that 'from issue date' is enter, 'to issue date' is required 	
			errMsg+='<li>'+getMessage('required.toIssueDate')+'</li>';
			errStatus=true;
		}	
	}
	
	// To validate if the from Expiration date is greater than to Expiration date 
	if(fromExpiryDate != '' )
	{
		if(toExpiryDate != '' )
		{
			// To validate if the from Expiration date is greater than to Expiration date 
			var fromExpiryDateFormat = new Date(fromExpiryDate);
			var toExpiryDateFormat = new Date(toExpiryDate);
			if(fromExpiryDateFormat > toExpiryDateFormat){
				errMsg+='<li>'+getMessage('validate.expirationdaterange') +'</li>';
				errStatus=true;
			}
		}
		else if(toExpiryDate == '')
		{
			// To validate if the from Expiration date is greater than to Expiration date 
			errMsg+='<li>'+getMessage('required.toExpiryDate')+'</li>';
			errStatus=true;
		}
	}

	if(fromRevokedDate != '')
	{
		if(toRevokedDate != '')
		{
			// To validate if the from Revocation date is greater than to Revocation date 
			var fromRevokedDateFormat = new Date(fromRevokedDate);
			var toRevokedDateFormat = new Date(toRevokedDate);
			
			if(fromRevokedDateFormat > toRevokedDateFormat){
				//errMsg+='<li>To Date should be greater than From Date.</li>';
				errMsg+='<li>'+getMessage('validate.revokeddaterange') +'</li>';
				errStatus=true;
			}
		}else if(toRevokedDate == '')
		{
			// To validate if the from Revocation date is greater than to Revocation date 
			errMsg+='<li>'+getMessage('required.torevokedDate')+'</li>';
			errStatus=true;
		}
	}
	
	if(errStatus)
	{	
		container.innerHTML=errMsg;
		container.style.display='';	
		return true;
	}
	else
	{
		container.innerHTML='';
		container.style.display='none';
		//dummy action call to check session validity
		sessionCheckForAdvanedSearch('dealerNumber');

		reloadGrid();
		$("#search").attr("disabled", false);
        $("#search").focus();
		$("#requestNumber").focus();
	}
}


//This method loads the dealer number and name for non DU 
function sessionCheckForAdvanedSearch(id)
{
	//read dealer number from JSP
	var dealerNum = document.getElementById(id).value;
	var dataStr = "dealerNumber="+dealerNum;
	//ajax call to retrieve dealer detail from action	
	$.ajax({
		type : "POST",
		url : 'retrieveLandingDealerDetails.action',
		data: dataStr,
		dataType : "json",
		success : function(result) {
		},
		error : function(data) {
		   var errorData = data.responseText === null? data.statusText : data.responseText ;
		   
			//handling session time out
			handleSessionExpiry(errorData);
		}
	});
}