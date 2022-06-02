<%@page import="dto.ShopDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>점포관리</title>
<%@ include file="header.jsp"%>
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
	border-collapse: collapse;
}
.itemtable tr,td{
	border : 1px solid gray;
	border-collapse: collapse;
}
.insertbtn{
	display : block;
	background-color : black;
	color : white;
	width : 100px;
	height : 35px;
	font-size : 15px;
	margin-top : 10px;
	margin-bottom : 20px;
	float : right;
	margin-right : 100px;
}
.insertbtn:hover{
	cursor : pointer;
}
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script type="text/javascript">
window.onload = function(){
	$("#insert_btn").on("click",function(){
		location.href = "/"
	})	
}
</script>
</head>
<body>

<h1 class='pagetitle'>점포관리 페이지입니다.</h1>

<input id='insert_btn' class='insertbtn' type="button" value="등록하기">
<%
	List<ShopDTO> list = (List<ShopDTO>)request.getAttribute("shoplist");
	if(list.size() != 0){
%>
<table class='itemtable'>
	<tr><th>점포코드</th><th>점포명</th><th></th><th></th></tr>
	<%
		for(ShopDTO dto:list){
	%>
		<tr><td><%=dto.getShop_code() %></td><td><%=dto.getShop_name() %></td><td><a href=''>수정</a></td><td><a href=''>삭제</a></td></tr>
	<%
		}
	%>
</table>
<%
	}else{
%>
	<h3>관리하고 있는 점포가 없습니다.</h3>
<% 
	}
%>


</body>
</html>