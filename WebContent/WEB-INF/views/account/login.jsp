<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<%@ include file="/WEB-INF/views/header.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/login.css" type="text/css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
</head>
<body>

<form action="login" method="post">
	<table class='loginform'>
		<tr>
			<th class='title' colspan='2'>로그인</th>
		</tr>
		<tr>
			<td class='inputtitle'>아이디</td>
			<td><input class='input' type="text" name="account_id"></td>
		</tr>
		<tr>
			<td class='inputtitle'>비밀번호</td>
			<td><input class='input' type="password" name="account_pwd"></td>
		</tr>
		<tr>
			<td colspan="2"><input class='btn' type="submit" value="로그인"></td>
		</tr>
	</table>
</form>


</body>
</html>