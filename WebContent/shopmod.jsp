<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리 점포 수정</title>
<%@ include file="header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>점포관리 수정 페이지입니다.</h1>

<table>
	<form action="shopmod" method="post">
	<input name="shop_code" type="hidden" id="shop_code" value="<%=request.getAttribute("shop_code")%>">
	<tr><td>점포명</td><td><input type="text" name="shop_name" class="inputtd" id="shop_name" value="<%=request.getAttribute("shop_name")%>" required></td></tr>
	<tr><td>관리자명</td><td><%=request.getAttribute("member_name") %></td></tr>
	<tr><td colspan='2'><input type="submit" value="수정하기" id="mod_btn"></td></tr>
	</form>
</table>

</body>
</html>