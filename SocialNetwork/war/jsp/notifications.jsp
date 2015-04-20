<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Notification Page</title>
</head>
<body>
<%@ page import="com.FCI.SWE.Controller.*" %>
<%@ page import="java.util.*" %>

<%

for (NotificationController.Type T : NotificationController.AllNotifications)
{
%>
<form action="Handelnotifications" method="post">

<input type="hidden" name = "ID" value=<%= T.ID %> />
<input type="hidden" name = "type" value=<%= T.type %> />
<%
if (T.type.equals("1"))
{
	out.println("You've got a personal message from " + T.sender);
%>
<input type="submit" value="Read personal message">	
<% 
}
else  if (T.type.equals("2"))
{
	out.println("You've got a Group message from " + T.sender);
	%>
	<input type="submit" value="Read Group message">	
	<%
	
}
else  if (T.type.equals("3"))
{
	out.println("You've got a FriendRequest from "+ T.sender);
	%>
	<input type="submit" value="acceptFriendRequest">	
	<%
}
else  if (T.type.equals("4"))
{
	out.println( T.sender + " has accepted your FriendRequest");
}
else  if (T.type.equals("5"))
{
	out.println( T.sender + " liked your page ");
}
else  if (T.type.equals("6"))
{
	out.println( T.sender + " liked your post");
}
	

%>



</form>


<% 	
}

%>

 



</body>
</html>