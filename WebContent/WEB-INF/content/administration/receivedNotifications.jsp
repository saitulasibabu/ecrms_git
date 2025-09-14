<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>


<script type="text/javascript" src="js/administration/receivedNotif.js"> </script>
<script type="text/javascript" src="js/jqueryjs/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript">
function checkSubmit(e) 
	{
	    if(e && e.keyCode == 13)    
	    {  
	         saveReceivedNotification();   
	         return false;
	    }
	 } 

 	jQuery().ready(function (){
		
			$("#saveReceivedNotificationButton").attr("disabled", false);
			$("#resetReceivedNotificationButton").attr("disabled", false);
			$("#saveReceivedNotificationButton").focus();
			$("#resetReceivedNotificationButton").focus();
			$("#receivedFromValue").focus();
			
		
		});
 $(document).ready(function() {  
   
     
     
     $("#receivedBody").keyup(function(){  
         //get the limit from maxlength attribute  
        var limit = 1000;  
         //get the current text inside the textarea  
         var text = $(this).val();  
         //count the number of characters in the text  
         var chars = text.length;  
   
         //check if there are more characters then allowed  
         if(chars > limit){  
            //and if there are use substr to get the text before the limit  
             var new_text = text.substr(0, limit);  
   
             //and change the current text with the new text  
             $(this).val(new_text);  
         }  
     });  
   
 });

</script>
<s:form id="receivednotificationForm" name="receivednotificationForm">
<div id="ReceivedNotifErrorDiv" style="color:red;font-weight: bold"></div>
<div id="ReceivedNotifSuccessDiv" style="color:#0066CC;font-weight: bold"></div>
<s:actionerror/>
<s:actionmessage/>

<div id="receiveNotofication" class="span-15 last">

<fieldset>

<legend><s:label theme="simple" key="legend.certificateCreateNotification"></s:label></legend>

<div class="span-10">

<s:textfield tabindex="1" maxlength="100" id="receivedFromValue"  key="label.from" name="notificationModel.expiryFrom" cssStyle="width:710px;"/>

</div>

<div class="span-10">

<s:textfield tabindex="2" key="label.subject" maxlength="200" name="notificationModel.expirySubj" cssStyle="width:710px;"/>

</div>

<div class="span-10">

<s:textarea tabindex="3" id="receivedBody" key="label.body"  name="notificationModel.expiryBody" cssStyle="width:700px;height:200px;"/>

</div>

</fieldset>


</div>


<div class="span-15 last">

   <div class="span-2">
       
       <sj:a tabindex="4" button="true" onkeypress="return checkSubmit(event)" id="saveReceivedNotificationButton" onclick="saveReceivedNotification(); return false;"><s:property value="getText('button.save')" /></sj:a>
   </div>

   <div class="span-2" style="margin-left:15px">
        
             <sj:a tabindex="5" button="true" id="resetReceivedNotificationButton" href="administration/receivedNotifications.action" targets="adminpart"><s:property value="getText('button.reset')" /></sj:a>
             
        
   </div>
    		 
</div>

</s:form>
