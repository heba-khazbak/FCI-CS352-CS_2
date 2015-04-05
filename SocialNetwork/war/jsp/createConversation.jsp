<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>}Create Conversation</title>

</head>
<body>

<form action="/social/createGroupMessage" id="myForm" method="POST" >
Conversation Name: <input type="text" name ="groupName">
<br>
<br>
Send to: 
<input type="text" name="receiver">
<br>

<br><br>

<input type="submit" value="create Conversation">

</form>

</body>
</html>
