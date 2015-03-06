<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Notification Page</title>
</head>
<body>
<%@ page import= "com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.FCI.SWE.Models.*" %>
<%@ page import="java.util.*" %>

<%
DatastoreService datastore = DatastoreServiceFactory
.getDatastoreService();

Query gaeQuery = new Query("notifications");
PreparedQuery pq = datastore.prepare(gaeQuery);
%>

<form action="/social/acceptFriendRequest" method="post">

<select name="dropNotifications">
<%
List<String> list=new ArrayList<String>();
for (Entity e : pq.asIterable()){
	String s=e.getProperty("pending").toString();
	if(s.equals("0")){
		if(e.getProperty("currentUser").equals(session.getAttribute("name"))){
			String t=e.getProperty("toUser").toString()+" accepted your friend request";
			list.add(t);
		}
		continue;
	}
if(!e.getProperty("toUser").equals(session.getAttribute("name")))continue;
%>

<option value=<%=e.getProperty("currentUser").toString() %>><%=e.getProperty("currentUser")%> sent you a friend request</option>

<%
}
%>
</select>
<input type="submit" value="Accept">
</form>




<% 
for(String s:list){
	%>
	<p><%=s %></p>
	<% 
}
%>

</body>
</html>