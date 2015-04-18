<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trending</title>
</head>
<body>

<%@ page import="com.FCI.SWE.Controller.*" %>
<%@ page import="java.util.*" %>

<%

for (String hashtagName : HashtagController.hashtags)
{
%>
<h1><%=hashtagName %></h1>

<% 	
}

%>


</body>
</html>