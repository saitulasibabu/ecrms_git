	
var reqIds2 = new Array();             		// Request Number Array for different Actions
var certfilenamearr = new Array();
var downloadflag=false;               // Boolean value, which used to check action on download  
var revokedFlag=false;                // Boolean value, which used to check action on Revoke
var printFlag=false;                  // Boolean value, which used to check action on Print
var createFlag=false;                 // Boolean value, which used to check action on Create
var reactiveFlag = false;             // Boolean value, which used to check action on Reactive
var certfilename='';                  // certfilename to hold Certificate file name for download 
var certActivationIndc='false';       // certActivationIndc to hold true or false for Download
var activecount=0;                    // activecount hold count of selected certificates which are active
var printcount=0;                     // printcount hold count of selected certificates which are stalled / pending review / approved
var createcount=0;                    // createcount hold count of selected certificates which are active/expired
var selectedrowcount=0;               // selectedrowcount hold count of total selected certificates irrespective of status
var revokecount = 0;                  // revokecount hold count of selected certificates which are active/expired
var printreqNum='';                   // Request Number for print certificate Action
var multipleCreateArray= new Array(); // Array use to hold multiple create flags



//This method loads the dealer number and name for non DU 
function retrieveDlrDataForHome(id)
{
	
	//read dealer number from JSP
	var dealerNum = document.getElementById(id).value;
	//div or container for dealer Number and dealer Name
	var dealerNumContainer = document.getElementById('dealerNumLbl');
	var dealerNameContainer = document.getElementById('dealerNameLbl');
	//check if User is NON DU and dealerNumContainer is not null
	if(dealerNumContainer != null )
	{
	var dataStr = "dealerNumber="+dealerNum;
	//ajax call to retrieve dealer detail from action	
	$.ajax({
		type : "POST",
		url : 'retrieveLandingDealerDetails.action',
		data: dataStr,
		dataType : "json",
		success : function(result) {
			if(dealerNum != '' && dealerNum != null){
				//if dealer number is valid ajax call return Dealer Number and dealer Name
				dealerNumContainer.innerHTML=result.dealerNumber;
				dealerNameContainer.innerHTML=result.dealerName;
			}else{
				// if dealer number is not valid, ajax call will not return values for dealer number and dealer name
				dealerNumContainer.innerHTML='';
				dealerNameContainer.innerHTML='';
			}
		},
		error : function(data) {
		   var errorData = data.responseText === null? data.statusText : data.responseText ;
		   
			//handling session time out
			handleSessionExpiry(errorData);

		// if dealer number is not valid or there is any error while retrieving data, 
		//ajax call will not return values for dealer number and dealer name
		   dealerNumContainer.innerHTML='';
		   dealerNameContainer.innerHTML='';

		}
	});
	}

	
}

/*
 * This method is used to clear previous rows of result table of Landing page( Basic search)
 */
function beforeValidate()
{
	// clear grid data of table
	$("#certificates").jqGrid("clearGridData", true);
	// call validation function to validate and get result for given search criteria
	validateSearchCertRequest();
	
}

/* This method reload grid for basic search, also initialize all counts and flag. 
This methods disable all the buttons at the time of grid reload 
*/ 
function reloadGridbasic()
{
	//to avoid multiple hits, disable search button after first hit
	$("#search").attr("disabled", true);
	$("#search").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	//on Grid load all buttons are disabled, 
	$("#print").attr("disabled", true);
	$("#createNewCert").attr("disabled", true);
	$("#dwnldCert").attr("disabled", true);
	$("#revokeCert").attr("disabled", true);
	
	// Added by Sanjana: IE project changes
	$('#print1').attr('disabled', 'disabled');
	$('#createNewCert1').attr('disabled', 'disabled');
	$('#dwnldCert1').attr('disabled', 'disabled');
	$('#revokeCert1').attr('disabled', 'disabled');
		
	// reload grid data
	$.publish('reloadGridTable');
	
	//initialize all counts and flags
	initcount();
	return false;
}

//to validate UI fields. This function  is called on click of Search button
function validateSearchCertRequest(){
	$("#cb_certificates").attr("disabled", false);
	//clear previous dealer information fields for new search if no dealer number is entered
	if(document.getElementById('dealerNumber').value == '' || document.getElementById('dealerNumber').value == null )
	{
		document.getElementById('dealerNumLbl').innerHTML='';
		document.getElementById('dealerNameLbl').innerHTML='';
	}
	var errStatus = false;
	var errMsg = '';
	//  for error messages
	var container = document.getElementById('basicSearchDiv');
	// for status
	var status =$("#status").val();
	// for dealer number
	var dealerNumber=$("#dealerNumber").val(); 
	//trimming the leading and trailing white spaces from the dealerNumber entered
	dealerNumber =dealerNumber.replace(/^[\s]+/,'').replace(/[\s]+$/,'');
	// Display the formatted DealerNumber value to the user.
	$('#dealerNumber').val(dealerNumber);
	dealerNumber=escape($("#dealerNumber").val());
	if(status == '~'){
		errStatus= true;	
		errMsg = "<li>"+getMessage('errormessage.status')+"</li>";
	}
	
	if(errStatus)
	{	
		//if there are(or is an) error, display error messages in proper container
		container.innerHTML=errMsg;
		container.style.display='';	
		return true;
	}
	else
	{
		// If there is no error on form or page, proceed further
		container.innerHTML='';
		container.style.display='none';
		
		//retrieve dealer Data and display On page
		retrieveDlrDataForHome('dealerNumber');
		reloadGridbasic();
		$("#search").attr("disabled", false);
		$("#search").focus();
		$("#dealerNumber").focus();
		$("#status").focus();
		$("#status").blur();
		
	}
	
}

//To do multiple create from landing page
function multipleCreateRequest()
{
	error_container = document.getElementById('basicSearchDiv');
	success_container = document.getElementById('basicSearchSuccessDiv');
	success_container.innerHTML='';
	success_container.style.display='none';
	if(createFlag)
	{		
	$("#multipleReqNum").val(reqIds2);
	$("#homeForm").ajaxSubmit({
		type : "POST",
		url : 'certrequest/multipleCertReqCreate.action',
		success: function(data) {
			$('#content').html(data);
	},
	error: function(data) {
		
		var errorData = data.responseText === null? data.statusText : data.responseText ;
		error_container.innerHTML=errorData;
		error_container.style.display='';
	}
	}); 
	}
	else
	{
			error_container.innerHTML="<li>"+getMessage('validate.multicreatenew')+"</li>";
			error_container.style.display='';
	}
	
}

function prepareDataForMultipleCreate(rowId, reqNum, multipleCreateInd) 
{
	if(rowId){
		reqIds2.push(reqNum);
		multipleCreateArray.push(multipleCreateInd);
	}else{
		var idx = verifyDataForCreate(reqIds2,reqNum);
		if(idx || idx ===0)
		{
			reqIds2.splice(idx, 1); 
			multipleCreateArray.splice(multipleCreateInd, 1); 
		}
	}
	
}

function verifyDataForCreate(arr, obj) 
{   
	for(var i=0; i<arr.length; i++) 
	{    
		if (arr[i] === obj) 
		{
			return i;   
		}
	}
	 
	return false;
}

function verifyDownload()
{
	error_container = document.getElementById('basicSearchDiv');
	error_container.style.display='none';
	success_container = document.getElementById('basicSearchSuccessDiv');
	success_container.innerHTML='';
	success_container.style.display='none';
	
	if(downloadflag){
	
		if(certfilenamearr[0] != '')
		{
			
			if(certActivationIndc == 'true')
			{
				openDwnldInfo(certfilenamearr[0]);
			}
			else
			{
				//if certificate file name is not valid or is empty
				$.alerts.okButton=getMessage('button.ok');
				var info = jAlert(getMessage('required.downloadcert'), getMessage('title.downloadInstr'), function(confirm){});
				
			}
		}
		else 
		{
			//if certificate file name is not valid or is empty
			error_container.innerHTML='<li>'+getMessage('errmessage.downloadFile')+'</li>';
			error_container.style.display='';
		}
		
	}
	else
	{
		success_container.innerHTML='';
		success_container.style.display='none';
		if(activecount == 0){
		error_container.innerHTML='<li>'+getMessage('message.downld')+'</li>';
		error_container.style.display='';
		}
		if(activecount > 1)
		{
			error_container.innerHTML='<li>'+getMessage('message.downldone')+'</li>';
			error_container.style.display='';
		}
		if(activecount == 1 && selectedrowcount > 1)
		{
			error_container.innerHTML='<li>'+getMessage('message.downldone')+'</li>';
			error_container.style.display='';
		}
	}
	
}
function printCert()
{
	error_container = document.getElementById('basicSearchDiv');
	error_container.style.display='none';
	success_container = document.getElementById('basicSearchSuccessDiv');
	success_container.innerHTML='';
	success_container.style.display='none';
	if(printFlag)
	{
		if(reqIds2.length == 1)
		{
		openPrintCertReqDialog(reqIds2[0]);
		}
	}
	else
	{
		success_container.style.display='none';
		if(printcount == 0)
		{
			
			error_container.innerHTML="<li>"+getMessage('message.printatleastone') +"</li>";
			error_container.style.display='';
		}
		else if(printcount > 1)
		{
			
			error_container.innerHTML="<li>"+ getMessage('message.printonlyone') +"</li>";
			error_container.style.display='';
		}
		else if(printcount == 1 && selectedrowcount > 1 )
		{	
			error_container.innerHTML="<li>"+getMessage('message.printonlyone')+"</li>";
			error_container.style.display='';
		}
	}
	
}

function multipleRevokeRequest()
{
	error_container = document.getElementById('basicSearchDiv');
	error_container.innerHTML='';
	error_container.style.display='none';
	success_container = document.getElementById('basicSearchSuccessDiv');
	success_container.innerHTML='';
	success_container.style.display='none';
	if(revokedFlag)
	{
		$.alerts.okButton=getMessage('button.yes');
		$.alerts.cancelButton=getMessage('button.no');
		var revokeConfirm = jConfirm(getMessage('confirm.multipleRevoke'), getMessage('title.revoke'), function(confirm){
			if(confirm){
				$("#loadingimg").css('display','inline');
				error_container = document.getElementById('basicSearchDiv');
			$("#multipleReqNum").val(reqIds2);
			
			$("#homeForm").ajaxSubmit({
				type : "POST",
				dataType : "json",
				url : 'revokeCertificatefromLanding.action',
				success: function(data) {
				reloadGridbasic(); // To display changed status(i.e. Revoked) of certificate
				success_container.innerHTML=data.successMessage;
				success_container.style.display='';
				$("#loadingimg").css('display','none');
			},
			error: function(data) {
				var errorData = data.responseText === null? data.statusText : data.responseText ;
				
				//handling session time out
				handleSessionExpiry(errorData);

				error_container.innerHTML=errorData;
				error_container.style.display='';
				$("#loadingimg").css('display','none');
				
			}});
		}else{
			
		}
		});
	}else{
		success_container.innerHTML='';
		success_container.style.display='none';
		error_container = document.getElementById('basicSearchDiv');
			
			error_container.innerHTML="<li>"+ getMessage('message.revokedall') +"</li>";
			error_container.style.display='';
			
	}
	
}
// To reactivate Certificate to current time stamp, so that user can download that cerificate
function reactiveCert()
{

	error_container = document.getElementById('basicSearchDiv');
	error_container.innerHTML='';
	error_container.style.display='none';
	success_container = document.getElementById('basicSearchSuccessDiv');
	success_container.innerHTML='';
	success_container.style.display='none';
	if(reactiveFlag)
	{
		//Confirmation for reactivation of certificate
		$.alerts.okButton=getMessage('button.yes');
		$.alerts.cancelButton=getMessage('button.no');
		var reactivConfirm = jConfirm(getMessage('confirm.reactiv'), getMessage('title.reactiv'), function(confirm){
			if(confirm){
				// if user confirm the reactivation, ajax call to 'reactivateCert' Action
				error_container = document.getElementById('basicSearchDiv');
				$("#multipleReqNum").val(reqIds2);
				$("#homeForm").ajaxSubmit({
					type : "POST",
					dataType : "json",
					url : 'reactivateCert.action',
					success: function(data) {
					reloadGridbasic();//reload grid to make certificate available to download.
					// Show Success message in success div
					success_container.innerHTML="<li>"+ getMessage('message.reactiv')+"</li>";
					success_container.style.display='';
				},
				error: function(data) {
					var errorData = data.responseText === null? data.statusText : data.responseText ;
					
					//handling session time out
					handleSessionExpiry(errorData);

					// Show error message in error div
					error_container.innerHTML=errorData;
					error_container.style.display='';
				}});
		}
		});
	}
	else
	{
		error_container = document.getElementById('basicSearchDiv');
		success_container.innerHTML='';
		success_container.style.display='none';
		if(activecount == 0){
		//If no active certificate is selected;
		error_container.innerHTML="<li>"+ getMessage('errmessage.reactiv') +"</li>";
			error_container.style.display='';
		}else if(selectedrowcount != activecount){
			//If selected certificate(s) is(are) other than Active
			error_container.innerHTML="<li>"+ getMessage('message.reactiveAll') +"</li>";
			error_container.style.display='';
		}
	}
	
}

//this method returns current date in mm/dd/yyyy format
function currentDate()
{
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;//January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd;}// if date is between 1 to 9
	if(mm<10){mm='0'+mm;}// if month is between 1 to 9
	today= mm+'/'+dd+'/'+yyyy 
	return today;
}



//prepare data for button functionality. Activate/deactivate buttons and set or reset flags 
function prepareDataForActions(rowId, statusCde, fileName,reqNum, expiryDate, certActivationInd,multipleCreateInd)
{
	
	if(rowId){
		//if row is selected, then check for next conditions to activate or deactivate buttons
		if(statusCde === "ACTIV" || statusCde === "EXPRD"){
			if(statusCde === "ACTIV"){
				//if status of selected certificate is active, then we can revoke and download certificate depend on   
				// some other criteria. Also certfilename is set to download functionality.
				certfilename=fileName;
				certfilenamearr.push(certfilename);
				certActivationIndc=certActivationInd;
				activecount++;
				revokecount++;
				
			}else{
				//if selected certificate is Expired  
					revokecount++;
			}
			if(multipleCreateInd == "true")	{
				createcount++;
			}
			selectedrowcount++;
			
		}
		else if(statusCde == 'STALD' || statusCde == 'PENRV' || statusCde == 'APRVD')
		{
			//if selected certificate is either Stalled, pending review or Approved
			printreqNum=reqNum; // request number to print certificate
			printcount++; 		// to check multiple or single selection of certificates
			selectedrowcount++; // to check number of rows are selected

			}
		else
		{
		//if selected row is other than active,expired,stalled, pending review or approved
			selectedrowcount++;
		}
	}else
	{
		//if row is unselected, then check for next conditions to activate or deactivate buttons
		if(statusCde === "ACTIV" || statusCde === "EXPRD"){
			if(statusCde === "ACTIV"){
				activecount--;
				revokecount--;
				var idx = verifyDataForCreate(certfilenamearr,fileName);
				if(idx || idx ===0)
				{
					certfilenamearr.splice(idx, 1); 
				}
			}else{
				revokecount--;			
			}
			if(multipleCreateInd == "true"){
				createcount--;
			}
			selectedrowcount--;
		}else if(statusCde == 'STALD' || statusCde == 'PENRV' || statusCde == 'APRVD'){
			printcount--;
			selectedrowcount--;
			
		}else{
			selectedrowcount--;
		}
	}
	// only one row is selected
	if(selectedrowcount == 1){
		if(revokecount == 0){
			 	//if no active and revoke certificate is selected
			 	$("#revokeCert").attr("disabled", true);
			 	$("#createNewCert").attr("disabled", true);
			 	
			 	// Added by Sanjana: IE project changes
			 	$('#revokeCert1').attr('disabled', 'disabled');
			 	$('#createNewCert1').attr('disabled', 'disabled');
				
			 	revokedFlag = false;
		}
		 if(createcount == 1){
			 $("#createNewCert").attr("disabled", false);
			 
			 // Added by Sanjana: IE project changes
			 $('#createNewCert1').removeAttr('disabled');
			 
			 createFlag = true;
		}
		 else if(createcount == 0){	
			
			 createFlag = false;
		}
		if(activecount == 0){
			//if no active certificate is selected
			$("#dwnldCert").attr("disabled", true);
			$("#reactivCert").attr("disabled",true);
			
			// Added by Sanjana: IE project changes
			$('#dwnldCert1').attr('disabled', 'disabled');
			$('#reactivCert1').attr('disabled', 'disabled');
			
			downloadflag=false;
			reactiveFlag=false;
		}
		else if(activecount == 1){
			//if single active certificate is selected
			$("#dwnldCert").attr("disabled", false);
			$("#reactivCert").attr("disabled",false);
			
			// Added by Sanjana: IE project changes
			$('#dwnldCert1').removeAttr('disabled');
			$('#reactivCert1').removeAttr('disabled');
			
			downloadflag = true;
			reactiveFlag=true;
		}
		if(revokecount == 1){
			//if no active or expired certificate is selected
			$("#revokeCert").attr("disabled", false);
			
			// Added by Sanjana: IE project changes
			$('#revokeCert1').removeAttr('disabled');
			
			revokedFlag = true;
		}
		if(printcount==0){
			//if no stalled, pending review or approved is selected
			$("#print").attr("disabled", true);
			
			// Added by Sanjana: IE project changes
			$('#print1').attr('disabled', 'disabled');
			
			printFlag=false;

			
		}
		else if(printcount == 1){
			//if single stalled, pending review or approved is selected
			$("#print").attr("disabled", false);
			
			// Added by Sanjana: IE project changes
			$('#print1').removeAttr('disabled');
			
			printFlag=true;
			revokedFlag=false;
			reactiveFlag=false;

			
		}
	}else if(selectedrowcount > 1){
		// selected rows are more than one
		if(activecount==0){
			//if no active or expired certificate is selected
			$("#dwnldCert").attr("disabled", true);
			$("#reactivCert").attr("disabled",true);
			
			// Added by Sanjana: IE project changes
			$('#dwnldCert1').attr('disabled', 'disabled');
			$('#reactivCert1').attr('disabled', 'disabled');
			
			reactiveFlag = false;
		}
		else if(activecount >= 1 ){
			//if more than one active certificates are selected
			if(activecount == selectedrowcount){	
				reactivateFlag=true;
			}
			else{
				reactiveFlag=false;
			}
		}
		if(createcount==0){
			
			createFlag=false;
		}
		else if(createcount >= 1){
			// only active expiring within next 60 days or expired can certificates can create new certificate 
			if(createcount == selectedrowcount){
				$("#createNewCert").attr("disabled", false);
				
				// Added by Sanjana: IE project changes
				$('#createNewCert1').removeAttr('disabled');
				 
				createFlag=true;
			}	
			else{
				createFlag=false;
			}	
		}
		if(printcount >= 1){
		//only one certificate can be print at a time
			revokedFlag=false;
			downloadFlag = false;
			printFlag=false;
			
		}
		if(revokecount >= 1){
			// if multiple active or expired row to revoke
			// but if all selected row are active or expired then only we can revoke certificate
			if(revokecount == selectedrowcount){
				revokedFlag = true;
			}
			else{
			revokedFlag = false;
			}
		}
		if(revokecount == 0){
			$("#revokeCert").attr("disabled", true);
			$("#createNewCert").attr("disabled", true);
			
			// Added by Sanjana: IE project changes
			$('#revokeCert1').attr('disabled', 'disabled');
		 	$('#createNewCert1').attr('disabled', 'disabled');
			
			revokedFlag=false;
		}
			downloadflag=false;		// can download single certificate
			printFlag=false;		// can print single certificate

			
	}
	else if(selectedrowcount == 0){
		//if no certificate is selected
		$("#print").attr("disabled", true);
		$("#createNewCert").attr("disabled", true);
		$("#dwnldCert").attr("disabled", true);
		$("#revokeCert").attr("disabled", true);
		$("#reactivCert").attr("disabled", true);
		
		// Added by Sanjana: IE project changes
		$('#print1').attr('disabled', 'disabled');
		$('#createNewCert1').attr('disabled', 'disabled');
		$('#dwnldCert1').attr('disabled', 'disabled');
		$('#revokeCert1').attr('disabled', 'disabled');
		$('#reactivCert1').attr('disabled', 'disabled');
		
		createFlag=false;
		revokedFlag=false;
		downloadflag=false;  
		printFlag=false;	
		reactiveFlag=false;	

		
	}
	
	
}
//Link on device nick name to Edit request page/dialog.
function formatLink(cellvalue, options, rowObject) {
	
	var link = "<a href=\"javascript:openEditCertReqDialog(" + rowObject.requestNum +")\" >" + rowObject.deviceNickName + "</a>";
	return link;

	
	
}
//functions to enable or disable buttons based on status of the row
function activateButton(obj,statuscde, reqNum, fileName, multipleCreateInd,expiryDate,certActivationInd)
{
	if(obj){	
//if row is selected or checked
		if(statuscde == 'EXPRD' || statuscde=='ACTIV'){
			if(statuscde=='ACTIV'){
				$("#dwnldCert").attr("disabled", false);
				$("#reactivCert").attr("disabled",false);
				
				// Added by Sanjana: IE project changes
				$('#dwnldCert1').removeAttr('disabled');
				$('#reactivCert1').removeAttr('disabled');
			}
			$("#revokeCert").attr("disabled",false);
			$("#createNewCert").attr("disabled", false);
			
			// Added by Sanjana: IE project changes
			$('#revokeCert1').removeAttr('disabled');
			$('#createNewCert1').removeAttr('disabled');
		}
		else if(statuscde == 'PENRV' || statuscde == 'STALD' || statuscde == 'APRVD'){
			$("#print").attr("disabled", false);
			
			// Added by Sanjana: IE project changes
			$('#print1').removeAttr('disabled');
		}		
		
	}
	//call function to prepare data for multiple create
	prepareDataForMultipleCreate(obj, reqNum, multipleCreateInd);
	
	//call function to prepare data for all actions(like download, print etc)
	prepareDataForActions(obj,statuscde,fileName,reqNum,expiryDate,certActivationInd,multipleCreateInd);
	
}
//This function reset or reinitialize all counts and flags or divs 
function initcount()
{
	// reset All Counts and flags
	//error msg div
	error_container = document.getElementById('basicSearchDiv');
	error_container.innerHTML='';
	error_container.style.display='none';
	//success msg div
	success_container = document.getElementById('basicSearchSuccessDiv');
	success_container.innerHTML='';
	success_container.style.display='none';
	reqIds2 = new Array();           //request ids for different actions like create, reactivate, revoke
	certfilenamearr = new Array();
	multipleCreateArray= new Array();  // array used in multiple create
	downloadflag=false;				   // download flag check while downloading request
	revokedFlag=false;					// revoked flag check while revoking request		
	createFlag=false;					//createflag check while creating request
	printFlag=false;					// Print flag while printing request
	reactiveFlag = false;				// reactivate flag while reactivation
	certfilename='';					// certificate file name for download
	activecount=0;						// active count for download, reactivate
	createcount=0;						// create count for create certificate
	selectedrowcount=0;					// selected row count for total number of selected rows
	printcount=0;						// print count for print certificate
	revokecount = 0;					// revoke count for revocation of certificate
	$("#dwnldCert").attr("disabled", true);		//disable download button 
	$("#revokeCert").attr("disabled", true);	//disable revoke button 
	$("#createNewCert").attr("disabled", true);	//disable create New button 
	$("#print").attr("disabled", true);			//disable print button 
	$("#reactivCert").attr("disabled",true);	//disable reactivate button 
	
	// Added by Sanjana: IE project changes
	$('#dwnldCert1').attr('disabled', 'disabled');
	$('#revokeCert1').attr('disabled', 'disabled');
	$('#createNewCert1').attr('disabled', 'disabled');
	$('#print1').attr('disabled', 'disabled');
	$('#reactivCert1').attr('disabled', 'disabled');
		
}
//download certificate from create certificate page respective to  request number
function openDwnldInfo(cFileName){
	
	//this alert window show Instructions to download certificate 
	$.alerts.okButton=getMessage('button.ok');
	var info = jAlert(getMessage('message.download'), getMessage('title.downloadInstr'), function(confirm){
		if(confirm){
				$.download('downloadCertificate.action?fileName='+cFileName, cFileName );
		}});
	
}

//check to show result error
function checkResultCriteria(){
	// for dealer number
	var dealerNumber=$("#dealerNumber").val(); 
	//trimming the leading and trailing white spaces from the dealer number entered
	dealerNumber =dealerNumber.replace(/^[\s]+/,'').replace(/[\s]+$/,'');
	// Display the formatted Dealer Number value to the user.
	$('#dealerNumber').val(dealerNumber);
	dealerNumber=escape($("#dealerNumber").val());
	var status =$("#status").val();
	if(status == '~'){//if no status is selected
		return true;}
	else{	return false;}
	
	}
