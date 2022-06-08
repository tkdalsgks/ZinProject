<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리 점포 등록</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/insert.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>점포관리 등록 페이지입니다.</h1>

<table class='inserttable'>
	<form action="shopinsert" method="post">
	<tr><td class='insertname'>점포명</td><td><input type="text" name="shop_name" class="insertinput" id="shop_name" maxlength=20 required></td></tr>
	<tr><td class='insertname'>관리자명</td><td class='insertcontext'><%=request.getAttribute("member_name") %></td></tr>
	<tr><td colspan='2'><input type="submit" class='insertbtn' value="등록하기" id="insert_btn"></td></tr>
	</form>
</table>

</body>
</html>