<%@page import="dto.AccountDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<%@ include file="header.jsp"%>
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
		<td>이름</td>
		<td><%=account.getMember_name() %>
	<%
		}
	%>
	<%
		if(account.getShop_name() != null){
	%>
		<td>점포명</td>
		<td><%=account.getShop_name() %>
	<%
		}
	%>
	
	<tr>
		<td colspan='2'><button>수정하기</button></td>
	</tr>
</table>

</body>
</html>