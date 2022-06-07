<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리 점포 수정</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/insert.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>점포관리 수정 페이지입니다.</h1>

<table class='inserttable'>
	<form action="itemmod" method="post">
		<input name="item_code" type="hidden" id="item_code" value="<%=request.getAttribute("item_code")%>">
		<tr><td class='insertname'>물품명</td><td><input type="text" name="item_name" class="insertinput" id="item_name" value="<%=request.getAttribute("item_name")%>" required></td></tr>
		<tr><td class='insertname'>본사코드</td><td class='insertcontext'><%=request.getAttribute("company_code") %><input name="company_code" type="hidden" id="company_code" value="<%=request.getAttribute("company_code")%>"></td></tr>
		<tr><td class='insertname'>물품가격</td><td><input type="number" name="item_price" class="inputinput" id="item_price" min=0 value="<%=request.getAttribute("item_price") %>" required></td></tr>
		<tr><td colspan='2'><input type="submit" class='insertbtn' value="수정하기" id="mod_btn"></td></tr>
	</form>
</table>

</body>
</html>