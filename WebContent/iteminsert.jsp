<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리 물품 등록</title>
<%@ include file="header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>물품관리 등록 페이지입니다.</h1>

<table>
	<form action="iteminsert" method="post">
	<tr><td>물품명</td><td><input type="text" name="item_name" class="inputtd" id="shop_name" required></td></tr>
	<tr><td>본사코드</td><td><%=request.getAttribute("company_code") %><input type="hidden" name="company_code" value="<%=request.getAttribute("company_code") %>"></td></tr>
	<tr><td>등록자명</td><td><%=request.getAttribute("member_name") %></td></tr>
	<tr><td>물품가격</td><td><input type="number" name="item_price" min=0 required></td></tr>
	<tr><td colspan='2'><input type="submit" value="등록하기" id="insert_btn"></td></tr>
	</form>
</table>

</body>
</html>