<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Send Message</title>
</head>
<body>

<form action="sendPersonalMessage" method="POST">
Send to: <input type="text" name="receiver">
<br>
Message: <input type="text" name="content" >
<input type="submit" value="Send Message">

</form>



</body>
</html>