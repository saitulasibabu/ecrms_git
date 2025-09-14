function approveCertRequest(){
	
	$("#processingImage").css('display','inline');
	$("#processingImage").focus();
	$("#approveButton").attr("disabled", true);
	$("#stallButton").attr("disabled", true);
	$("#approveButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	
	var success_container = document.getElementById('successTopMegDiv');
	var error_container = document.getElementById('errorTopMegDiv');
	var error_comment_container = document.getElementById('errorAddComment');
	var success_comment_container=document.getElementById('successAddComment');
	
	success_container.style.display='none';
	error_container.style.display='none';
	error_comment_container.style.display='none';
	success_comment_container.style.display='none';
	
	$("#editCertificateForm").ajaxSubmit({
		type : "POST",
		dataType:'json',
		url : 'certrequest/approveCertRequest.action',
		success: function(data) {
		
		if(data.isError && data.isError == 'true'){
			
			//check whether approve was success and failure in sending email
			if(data.emailError && data.emailError == 'true'){
				success_container.innerHTML=getMessage('success.approve');
				success_container.style.display='';
				
				error_container.innerHTML=data.errorMsg;
				error_container.style.display='';
				 $("#commText").attr("disabled", true);
				 $("#commText").removeAttr("title");
				 $("#addButton").attr("disabled", true);
				 $("#revokeButton").attr("disabled", false);
				 $("#successTopMegDiv").focus();
				 $("#revokeButton").focus();
				 $("#revokeButton").blur();
			}else{
				var errMsg = "<p class=\"error\"/>"+data.errorMsg+"</p>";
				error_container.innerHTML=errMsg;
				error_container.style.display='';
				$("#approveButton").attr("disabled", false);
				$("#commText").attr("disabled", false);
				$("#addButton").attr("disabled", false);
				$("#stallButton").attr("disabled", false);
				$("#errorTopMegDiv").focus();
				
			}
		}else{
			success_container.innerHTML=getMessage('success.approve');
			success_container.style.display='';
			
			 $("#commText").attr("disabled", true);
			 $("#commText").removeAttr("title");
			 $("#addButton").attr("disabled", true);
			 $("#revokeButton").attr("disabled", false);
			 $("#successTopMegDiv").focus();
			 $("#revokeButton").focus();
			 $("#revokeButton").blur();
   		}
		 
		$("#processingImage").css('display','none');	
		$("#commText").val('');
		$.publish('reloadCommentGridTable');
		
	},
	error: function(data) {
		$("#approveButton").attr("disabled", false);
		$("#commText").attr("disabled", false);
		$("#addButton").attr("disabled", false);
		$("#stallButton").attr("disabled", false);
		
		var errorData = data.responseText === null? data.statusText : data.responseText ;
		
		//handling session time out
		handleSessionExpiry(errorData);
		
		error_container.innerHTML=errorData;
		error_container.style.display='';
		
		$("#processingImage").css('display','none');
		$("#errorTopMegDiv").focus();
	}
	
	}); 
	
}

//function to add comments
function addReqComment(){
	$("#addButton").attr("disabled", true);
	$("#addButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	
	var error_comment_container = document.getElementById('errorAddComment');
	var success_comment_container=document.getElementById('successAddComment');
	var error_top_container = document.getElementById('errorTopMegDiv');
	var success_top_container = document.getElementById('successTopMegDiv');
	
	success_comment_container.style.display='none';
	error_comment_container.style.display='none';
	error_top_container.style.display='none';
	success_top_container.style.display='none';
	
	var commText=$("#commText").val();
	commText =commText.replace(/^[\s]+/,'').replace(/[\s]+$/,'');
	
	// Display the formatted  comment name value to the user.
	$('#commText').val(commText);
	commText=escape($("#commText").val());
	
		
	//To validate empty value 
	if(commText =='')
	{
		error_comment_container.innerHTML=getMessage('required.comment');
		error_comment_container.style.display='';	
		$("#addButton").attr("disabled", false);
		 
		return true;
			
	}else{
		error_comment_container.style.display='none';
		$("#editCertificateForm").ajaxSubmit({
			type : "POST",
			dataType:'json',
			url : 'certrequest/addReqComment.action',
			success: function(data) {
			
			success_comment_container.innerHTML=getMessage('success.addComment');
			success_comment_container.style.display='';	
			$("#commText").val('');
			$.publish('reloadCommentGridTable');
			return false;
			error_comment_container.style.display='none';
			
		},
		error: function(data) {
			
			var errorData = data.responseText === null? data.statusText : data.responseText ;
			
			//handling session time out
			handleSessionExpiry(errorData);

			error_top_container.innerHTML=errorData;
			error_top_container.style.display='';
			$("#addButton").attr("disabled", false);
		}
			
		});
		 $("#addButton").attr("disabled", false);
	}
	

}

function stallCertRequest(){
	
	$("#processingImage").css('display','inline');
	$("#processingImage").focus();
	$("#stallButton").attr("disabled", true);
	$("#stallButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	
	var error_comment_container = document.getElementById('errorAddComment');
	var success_top_container = document.getElementById('successTopMegDiv');
	var error_top_container = document.getElementById('errorTopMegDiv');
	var success_comment_container=document.getElementById('successAddComment');
	
	var commText=$("#commText").val();
	commText =commText.replace(/^[\s]+/,'').replace(/[\s]+$/,'');
	
	// Display the formatted  comment name value to the user.
	$('#commText').val(commText);
	
	commText=escape($("#commText").val());
	
	success_top_container.style.display='none';
	error_comment_container.style.display='none';
	error_top_container.style.display='none';
	success_comment_container.style.display='none';
		
	//To validate empty value 
	if(commText =='')
	{
		error_comment_container.innerHTML=getMessage('required.stallComment');
		error_comment_container.style.display='';
		
		$("#stallButton").attr("disabled", false);
		$("#processingImage").css('display','none');
		$("#errorAddComment").focus();
		return true;
			
	}else{
		error_comment_container.style.display='none';
		
		$.alerts.okButton=getMessage('button.yes');
		$.alerts.cancelButton=getMessage('button.no');
		var stallConfirm = jConfirm(getMessage('confirm.stall'), getMessage('title.stall'), function(confirm){
			if(confirm){
				$("#editCertificateForm").ajaxSubmit({
					type : "POST",
					dataType:'json',
					url : 'certrequest/stallCertRequest.action',
					success: function(data) {
					error_container.innerHTML='';
					success_top_container.innerHTML=getMessage('success.stall');
					success_top_container.style.display='';
						
					$("#processingImage").css('display','none');
					$("#successTopMegDiv").focus();
					$("#commText").val('');
					$.publish('reloadCommentGridTable');
					return false;
					
				},
				error: function(data) {
					
					var errorData = data.responseText === null? data.statusText : data.responseText ;
					
					//handling session time out
					handleSessionExpiry(errorData);

					error_top_container.innerHTML=errorData;
					error_top_container.style.display='';
					$("#stallButton").attr("disabled", false);
					$("#processingImage").css('display','none');
					$("#errorTopMegDiv").focus();
				}
						
			});
			}else{
				$("#stallButton").attr("disabled", false);
				$("#processingImage").css('display','none');
				$("#closeEdit").focus();
			}
		});
		}
}

//function to close edit certificate page
function closeEditReqDialog(){
	$("#closeEdit").attr("disabled", true);
	$("#editCertReqDialog").dialog('close');
	$.publish('reloadGridTable');
	return false;
}

//function to revoke certificate from edit certificate page
function revokeCertRequest(){
	
	var success_top_container = document.getElementById('successTopMegDiv');
	var error_top_container = document.getElementById('errorTopMegDiv');
	
	success_top_container.style.display='none';
	error_top_container.style.display='none';
	$("#processingImage").css('display','inline');
	$("#processingImage").focus();
	
	//disable the button to avoid double click
	$("#revokeButton").attr("disabled", true);
	$("#revokeButton").attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	$.alerts.okButton=getMessage('button.yes');
	$.alerts.cancelButton=getMessage('button.no');
	var revokeConfirm = jConfirm(getMessage('confirm.revoke'), getMessage('title.revoke'), function(confirm){
	if(confirm){
		$("#editCertificateForm").ajaxSubmit({
			type : "POST",
			dataType:'json',
			url : 'certrequest/revokeCertificate.action',
			success: function(data) {
			
			if(data.isError && data.isError == 'true'){
				var errMsg = "<p class=\"error\"/>"+data.errorMsg+"</p>";
				error_top_container.innerHTML=errMsg;
				error_top_container.style.display='';
				$("#errorTopMegDiv").focus();
				$("#revokeButton").attr("disabled", false);
			}else{
				success_top_container.innerHTML=getMessage('success.revoke');
				success_top_container.style.display='';
				$("#successTopMegDiv").focus();
			}
				
			$("#processingImage").css('display','none');
			
			
		},
		error: function(data) {
			var errorData = data.responseText === null? data.statusText : data.responseText ;
			
			//handling session time out
			handleSessionExpiry(errorData);

			error_top_container.innerHTML=errorData;
			error_top_container.style.display='';
			$("#processingImage").css('display','none');
			$("#errorTopMegDiv").focus();
			//In case of failure enable the button again
			$("#revokeButton").attr("disabled", false);
		}
		
		}); 
		}else{
			$("#revokeButton").attr("disabled", false);
			$("#processingImage").css('display','none');
			$("#closeEdit").blur();
		}
	});
}

