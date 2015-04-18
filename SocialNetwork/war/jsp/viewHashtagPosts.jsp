<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hashtag Posts</title>
</head>
<body>

<%@ page import="com.FCI.SWE.Controller.*" %>
<%@ page import="java.util.*" %>

<%

for (HashtagController.Type T : HashtagController.posts)
{
	if(T.owner.equals(T.onWall))T.onWall="posted";
	else T.onWall="posted to "+T.onWall+"'s wall";
	out.println(T.owner+" "+T.onWall+"<br>"+T.content+"<br><br>");
}

%>


</body>
</html>