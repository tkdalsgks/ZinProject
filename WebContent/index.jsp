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
		<table>
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
			<% } %>
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