<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.submitLink {
background-color: transparent;
text-decoration: underline;
border: none;
color: blue;
cursor: pointer;
font-size: 40px;
}
.buttonHolder{ text-align: center; }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trending</title>
</head>
<body>

<%@ page import="com.FCI.SWE.Controller.*" %>
<%@ page import="java.util.*" %>

<%

for (String hashtagLink : HashtagController.hashtags)
{


out.println(hashtagLink);


}

%>


</body>
</html>