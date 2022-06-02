<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String account_id = (String)session.getAttribute("account_id");
	String company_name = (String)session.getAttribute("company_name");
	String team_name = (String)session.getAttribute("team_name");
	String member_name = (String)session.getAttribute("member_name");
	String shop_name = (String)session.getAttribute("shop_name");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈</title>
<%@ include file="header.jsp"%>
<style>

body{
	font-family: 'Gowun Dodum', sans-serif;
	color : black;
	weight : 500;
}

.btn{
	text-align : center;
	vertical-align :middle;
	padding-top : 30px;
	height : 70px;
	width : 40%;
	display : block;
	margin-top : 150px;
	margin-bottom : 100px;
	margin-left : auto;
	margin-right: auto;
	font-size : 35px;
	background-color : gray;
	color : white;
	text-decoration: none;
}

.login_info{
	margin-top : 100px;
	margin-right :auto;
	margin-left : auto;
	width : 50%;
	height : 300px;
	font-size : 30px;
	font-weight : 500;
	
}
</style>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
</head>
<body>

<% 
	if(account_id == null) {
%>
		<a class='btn' href="login">로그인</a>
		<a class='btn' href="join">회원가입</a>
<%	
	} else {
%>
		<table class='login_info'>
			<tr>
				<td>아이디</td>
				<td><%=account_id %></td>
			</tr>
			<%
				if(company_name != null){
			%>
			<tr>
				<td>본사명</td>
				<td><%=company_name %></td>
			</tr>
			<tr>
				<td>팀명</td>
				<td><%=team_name %></td>
			</tr>	
			<tr>
				<td>팀원명</td>
				<td><%=member_name %></td>
			</tr>	
			<%
				}
			%>
			<% if(shop_name != null){ %>
			<tr>
				<td>점포명</td>
				<td><%=shop_name %></td>
			</tr>
			<tr>
				<td>관리팀원명</td>
				<td><%=member_name %></td>
			</tr>
			<% } %>
		</table>
		<br>
		<a href="logout">로그아웃</a>
		<br>
		<a href="delete">회원탈퇴</a>
		<br>
		<a href="menu">메뉴</a>
<%	
	}
%>

</body>
</html>