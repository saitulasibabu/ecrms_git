<html>
<head>
<% 
session.setAttribute("toCheck", "toCheck");
String host = (String)session.getAttribute("vwgoa-forwarded-host");
String url = "https://"+host+"/ecrmsjct/";
System.out.println("Reloading Page: "+url);
%>

</head>

<body onload="javascript:location.href='<%= url %>'">

</body>

</html>
