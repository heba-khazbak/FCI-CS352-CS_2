
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.io.DataInputStream"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Home Page</title>
</head>
<body>
<p> Welcome <%=session.getAttribute("name") %> to your homepage </p>

<form action="viewTimeline" method="POST">
<input type="hidden" name="onWall" value=<%=session.getAttribute("name").toString() %>>
<input type="submit" value="Timeline">
</form>

<br><br>

<a href="/social/sendPMessage">Send personal message</a> <br><br>

<a href="/social/createGMessage">create group message</a> <br>
<a href="/social/sendGMessage">Send group message</a> <br><br>

<a href="/social/sendFriend">Send Friend Request</a> <br>
<a href="/social/notifications">Notifications</a> <br>
<a href="/social/searchTimeline">Search for timeline</a> <br>
<a href="/social/viewHashtag">View Hashtag</a> <br>
<a href="/social/showTrends">Trending</a> <br>

<a href="/social/createPage_Info">Create Page</a> <br>

<a href="/social/logout">Logout</a> <br>

</body>
</html>