<%@page import="account.accountDTO.AccountDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>마이페이지입니다.</h1>
<% 
	AccountDTO account = (AccountDTO)request.getAttribute("myinfo");

%>

<table class='mypagetable'>
	<tr>
		<td>아이디</td>
		<td><%=account.getAccount_id() %>
	</tr>
	<%
		if(account.getMember_name() != null){
	%>
	<tr>
		<td>이름</td>
		<td><%=account.getMember_name() %></td>
	</tr>
	<%
		}
	%>
	<%
		if(account.getShop_name() != null){
	%>
	<tr>
		<td>점포명</td>
		<td><%=account.getShop_name() %></td>
	</tr>
	<%
		}
	%>
	
	<tr>
		<td colspan='2'><button>수정하기</button></td>
	</tr>
	<tr>
		<td colspan='2'><a href="delete">회원탈퇴</a></td>
	</tr>
</table>

</body>
</html>