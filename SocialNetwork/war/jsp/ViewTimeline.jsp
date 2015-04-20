<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TimeLine</title>
</head>
<body>

<%@ page import="com.FCI.SWE.Controller.*" %>
<%@ page import="java.util.*" %>

<%
out.println("<b>" + TimelineController.TimeLine + "'s Timeline" + "<b>" );

if (TimelineController.isPage)
	out.println("<form action='likePage' method='POST' style ='display:inline;'><input type='hidden' name='postName' value='"+TimelineController.TimeLine+"'><input type='submit' value='Like'></form><pre style ='display:inline;'>       </pre>");

out.println("<br>");
for (String post : TimelineController.posts)
{
	out.println(post);
}

%>


</body>
</html>