<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<script type="text/javascript" src="js/administration/UpdateExpiryPeriod.js"></script>
<script type="text/javascript" src="js/jqueryjs/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>

<script type="text/javascript">
function numbersonly(){
 $('input').keypress(function(e) {     
 var a = [];     
 var k = e.which;      
 for (i = 48; i < 58; i++)         
 a.push(i);      
 if (!($.inArray(k,a)>=0))         
 e.preventDefault(); 
 });
}

function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}


//By deafault, at the time of page load all the buttons are enabled
	jQuery().ready(function (){
		$("#saveExpiryPeriodButton").attr("disabled", false);
		$("#resetExpiryPeriodButton").attr("disabled", false);
		$("#saveExpiryPeriodButton").focus();
		$("#resetExpiryPeriodButton").focus();
		$("#expiryPeriodValue").focus();
		
		
	});

 //To check whethr value entered in expiry period field is numeric.
 $("#expiryPeriodValue").change(function(e) {
 
     var value =  $("#expiryPeriodValue").val();
     
     if(isNumber(value)){
     	return true;
     }else{
     	$("#expiryPeriodValue").val('');
     } 
 });
 
//To submit the form when user hits enter
 function checkSubmit(e) 
	{
	    if(e && e.keyCode == 13)    
	    {  
	         saveExpiryPeriod();   
	         return false;
	    }
	 } 


</script>


<s:form id="adminExpiryPeriod" name="adminExpiryPeriod">
	<div  id="ExpiryPeriodErrorDiv" style="color:red;font-weight: bold"></div>
	<div id="ExpiryPeriodSuccessDiv" style="color:#0066CC;font-weight: bold"></div>
	<s:actionerror/>
	<s:actionmessage/>
	
	
	<div id="expiryPeriod" class="clear span-15 last" onkeypress="return checkSubmit(event)">
		<fieldset>
			
			<legend><s:label theme="simple" key="legend.expiryPeriod" ></s:label></legend>
			
			<div class="span-5" style="line-height: 5;">
				<s:textfield onkeypress="numbersonly()" maxlength="6" tabindex="1" cssClass="span-2"   
					title="%{getText('tooltip.expiryPeriod')}"  name="expiryPeriodModel.expiryPeriod" id="expiryPeriodValue" />
				<s:label theme="simple" cssClass="span-2" key="label.days"></s:label> 
			</div>
		</fieldset>
	</div>
	
	<div class="span-15 last">    
	     <div class="span-2">
	     <sj:a button="true" tabindex="2" onkeypress="return checkSubmit(event)" id="saveExpiryPeriodButton" onclick="saveExpiryPeriod(); return false;"><s:property value="getText('button.save')" /></sj:a>
		 </div>
		 <div class="span-2" style="margin-left:15px">
		 
		<sj:a button="true" tabindex="3" id="resetExpiryPeriodButton" href="administration/expiryPeriodDefault.action" targets="adminpart"><s:property value="getText('button.reset')" /></sj:a>
		
		
		</div>
	</div>
	
</s:form>
