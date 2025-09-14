<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>


<script type="text/javascript" src="js/administration/expiryNotif.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript">

function checkSubmit(e) 
	{
	    if(e && e.keyCode == 13)    
	    {  
	         saveExpiryNotification();   
	         return false;
	    }
	 } 

	jQuery().ready(function (){
		
			    $("#saveExpiryNotificationButton").attr("disabled", false);
				$("#resetExpiryNotificationButton").attr("disabled", false);
				$("#saveExpiryNotificationButton").focus();
				$("#resetExpiryNotificationButton").focus();
				$("#fromExpNotification").focus();
		
		});

 $(document).ready(function() {  
   
     $("#expiryBody").keyup(function(){  
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


<s:form id="expiryNotificationForm" name="expiryNotificationForm">
<div id="ExpiryNotifErrorDiv" style="color:red;font-weight: bold" ></div>
<div id="ExpiryNotifSuccessDiv" style="color:#0066CC;font-weight: bold" ></div>
<s:actionerror/>
<s:actionmessage/>
<div id="expiryNotification" class=" span-15 last">

<fieldset>




	<legend ><s:label theme="simple" key="legend.expiryPeriodNotification"></s:label></legend>
	
	<div class="span-10">
	
	<s:textfield tabindex="1" maxlength="100"  id="fromExpNotification" key="label.from" name="notificationModel.expiryFrom" cssStyle="width:710px;"/>
	
	</div>
	
	<div class="span-10">
	
	<s:textfield tabindex="2" maxlength="200" key="label.subject"  name="notificationModel.expirySubj" cssStyle="width:710px;"/>
	
	</div>
	
	<div class="span-10">
	
	<s:textarea tabindex="3" id="expiryNotifTextArea" key="label.body" id="expiryBody" name="notificationModel.expiryBody" cssStyle="width:700px;height:200px;" />
	
	</div>

</fieldset>



<div class="span-15 last">
 
    <div class="span-2" >
           <sj:a button="true" tabindex="4" onkeypress="return checkSubmit(event)" id="saveExpiryNotificationButton" onclick="saveExpiryNotification(); return false;"><s:property value="getText('button.save')" /></sj:a>
    </div>

    <div class="span-2" style="margin-left:15px">
         
             <sj:a tabindex="5" button="true" id="resetExpiryNotificationButton" href="administration/expiryNotifications.action" targets="adminpart"><s:property value="getText('button.reset')" /></sj:a>
             
        
    </div>

</div>
</div>
</s:form>


    		 


