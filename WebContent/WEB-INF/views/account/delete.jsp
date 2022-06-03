<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String account_id = (String) session.getAttribute("account_id"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

<form action="delete" method="post">
	아이디 : <input type="text" name="account_id" value="<%=account_id %>" style="border:none" readonly><br>
	<button type="submit">회원탈퇴</button>
</form>


</body>
</html>