/** This method is used to retrieve the Message based upon the key passed using browser i18 feature.
 * This method should not be replicated in each js file. Should be used from common js file.
 * @param msgKey
 * @return msgValue
 */

var userLocale = $("#locale").val();
if(userLocale != 'fr'){
	userLocale='en';
}

function getMessage(msgKey){
	
	var msg;
	
	jQuery.i18n.properties({   
		name: 'assets/ecrms_web_message',
		mode: 'map',
		language: userLocale,
		callback: function(){ 
		msg = jQuery.i18n.prop(msgKey);
	} 
	});
	return msg;
}



//to close the browser
function closeWindow(){

	// Added by Sanjana: IE project changes
	
	/*window.opener='X';
	  window.open("","_self");
	  window.close();*/
	
	var answer = confirm("Do you want to close this window?");
	  if (answer){
		  window.open("../ecrms/Close.html","_self");
		  window.close();
	  }else{
		  stop;
	  }
}

// function to validate email address for create form and Administration
function validateEmail(emailAddress){
	var regEmail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\.+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{1,}))$/;  
	if(regEmail.test(emailAddress) == false) {
		
		return true;	
	}else{
		return false;
	}
}

//close the browser on session expiry
function closeOnSessionExpiry(){
	
	//window.opener='X';
	
	// Added by Sanjana: IE project changes
	
	/*window.open("","_self");
	window.close();*/
	
	var answer = confirm("Do you want to close this window?");
	  if (answer){
		  window.open("../ecrms/Close.html","_self");
		  window.close();
	  }else{
		  stop;
	  }
}


function handleSessionExpiry(cType){
	
	if(cType.indexOf("ECRMSSessionTimeOut") != -1){		
		$.alerts.okButton=getMessage('button.Close');
		var info = jAlert(getMessage('err.session.expired.dialog'), getMessage('title.information'), function(confirm){
			if(confirm){
				closeOnSessionExpiry();
			}
		});
		
	}
}

function handleComplexSessionExpiry(data){
	
	var sessionTimeOut = 'false';
	try
	{
		if(data.indexOf("ECRMSSessionTimeOut") != -1){		

			//handling session time out
			handleSessionExpiry(data);

			sessionTimeOut = 'true';
		}
	}catch(e){}
	
	return sessionTimeOut;
}

