<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>}Create Conversation</title>

</head>
<body>

<form action="/social/sendGroupMessage" id="myForm" method="POST" >
Conversation Name: <input type="text" name ="groupName">
<br>
<br>
write your message content here:
<input type="text" name="content">
<br>

<br><br>

<input type="submit" value="Send Message">

</form>

</body>
</html>
