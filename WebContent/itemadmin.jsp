<%@page import="dto.ItemDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>물품관리</title>
<style>
.pagetitle{
	text-align:center;
}

.itemtable{
	text-align : center;
	width : 70%;
	margin-right:auto;
	margin-left:auto;
	border: 1px solid gray;
	border-collapse: collapse;
}
.itemtable th{
	font-size : 20px;
	border : 1px solid gray;
}
.itemtable tr,td{
	border : 1px solid gray;
}
</style>
<%@ include file="header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>물품관리 페이지입니다.</h1>

<table class='itemtable'>
	<tr><th>물품코드</th><th>물품명</th><th>가격</th><th></th><th></th></tr>
	<%
		List<ItemDTO> list = (List<ItemDTO>)request.getAttribute("itemlist");
		for(ItemDTO dto:list){
	%>
		<tr><td><%=dto.getItem_code() %></td><td><%=dto.getItem_name() %></td><td><%=dto.getItem_price() %></td><td><a href=''>수정</a></td><td><a href=''>삭제</a></td></tr>
	<%
		}
	%>
</table>


</body>
</html>