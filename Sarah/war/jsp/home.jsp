
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.io.DataInputStream"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Home Page</title>
</head>
<body>
<p> Welcome <%=session.getAttribute("name") %> to your homepage </p>



<a href="/social/sendFriend">Send Friend Request</a> <br>
<a href="/social/notifications">Notifications</a> <br>
<a href="/social/logout">Logout</a> <br>

</body>
</html>