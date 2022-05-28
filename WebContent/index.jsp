<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String account_id = (String)session.getAttribute("account_id");
	String company_name = (String)session.getAttribute("company_name");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈</title>
</head>
<body>

<% 
	if(account_id == null) {
%>
		<a href="login">로그인</a>
		<a href="join">회원가입</a>
<%	
	} else {
%>
		<table>
			<tr>
				<td>아이디</td>
				<td><%=account_id %></td>
			</tr>
			<tr>
				<td>본사명</td>
				<td><%=company_name %></td>
			</tr>		
		</table>
		<br>
		<a href="logout">로그아웃</a>
		<br>
		<a href="delete">회원탈퇴</a>
		<br>
		<a href="item">점포1</a>
<%	
	}
%>

</body>
</html>