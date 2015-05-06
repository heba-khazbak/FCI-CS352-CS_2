<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Create Page Post</title>
<script type="text/javascript">
	function toggleOnWall() {
		var type = document.getElementById("shareOn").value;
		if(type === "your_timeline") {
			document.getElementById("friend_name").style.display = "none";
		}
		else {
			document.getElementById("friend_name").style.display = "block";
		}
	}
</script>
</head>
<body>
<%@ page import="com.FCI.SWE.Controller.*" %>
<%@ page import="java.util.*" %>
	<form action="createSharePost" method="POST">
		Content:
		<textarea name="content"></textarea>
		<br />
		Privacy:
		<select name="privacy">
			<option value="public">Public</option>
			<option value="custom">Custom</option>
			<option value="private">Private</option>
		</select>
		<br />
		Custom:
		<input type="text" name="customUsers" />
		<br />
		Share on:
		<select id="shareOn" name="shareOn" onChange="toggleOnWall()">
			<option value="your_timeline">Your Timeline</option>
			<option value="friend_timeline">Friend's Timeline</option>
		</select>
		<br />
		<div id="friend_name" style="display: none">
			Friend Username: <input type="text" name="onWall" />
		</div>
		<br />
		<input type="hidden" name="postID" value=<%= PostController.originalPostID%> />
		<input type="submit" value="Post" />
	</form>

</body>
</html>