<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리 물품 등록</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/insert.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>물품관리 등록 페이지입니다.</h1>

<table class='inserttable'>
	<form action="iteminsert" method="post">
	<tr><td class='insertname'>물품명</td><td><input type="text" name="item_name" class="insertinput" id="shop_name" required></td></tr>
	<tr><td class='insertname'>본사코드</td><td class='insertcontext'><%=request.getAttribute("company_code") %><input type="hidden" name="company_code" value="<%=request.getAttribute("company_code") %>"></td></tr>
	<tr><td class='insertname'>등록자명</td><td class='insertcontext'><%=request.getAttribute("member_name") %></td></tr>
	<tr><td class='insertname'>물품가격</td><td><input class="insertinput" type="number" name="item_price" min=0 required></td></tr>
	<tr><td colspan='2'><input class='insertbtn' type="submit" value="등록하기" id="insert_btn"></td></tr>
	</form>
</table>

</body>
</html>