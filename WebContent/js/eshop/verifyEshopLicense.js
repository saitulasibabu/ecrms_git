
function closeEshopDialog(){
	$("#eshopDialog").dialog('close');
}

function openWindow(eshopLink){
	closeEshopDialog();
		
	window.open(eshopLink);
}
