<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Create User Post</title>
</head>
<body>

	<form action="createUserPost" method="POST">
		Content:
		<textarea name="content"></textarea>
		<br /> Privacy: <select name="privacy">
			<option value="public">Public</option>
			<option value="custom">Custom</option>
			<option value="private">Private</option>
		</select> <br /> Custom: <input type="text" name="customUsers" /> <br /> <input
			type="hidden" name="onWall" value="" />
		<!-- waiting for value (which will be the user name in timeline) -->
		<input type="submit" value="Post" />

	</form>



</body>
</html>