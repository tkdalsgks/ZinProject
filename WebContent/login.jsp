<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

<form action="login" method="post">
	<table>
		<tr>
			<td>아이디</td>
			<td><input type="text" name="account_id"></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="account_pwd"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="로그인"></td>
		</tr>
	</table>
</form>


</body>
</html>