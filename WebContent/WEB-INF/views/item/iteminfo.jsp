<%@page import="member.item.itemDTO.ItemDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>물품정보</title>
<style>
.pagetitle{
	margin-top : 80px;
	text-align:center;
}

.itemtable{
	text-align : center;
	width : 70%;
	margin-right:auto;
	margin-left:auto;
	border: 1px solid gray;
	border-collapse: collapse;
	margin-bottom : 100px;
}
.itemtable th{
	font-size : 20px;
	border : 1px solid gray;
}
.itemtable tr,td{
	border : 1px solid gray;
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
<%@ include file="/WEB-INF/views/header.jsp"%>

<script type="text/javascript">

</script>
</head>
<body>

<h1 class='pagetitle'>물품정보 페이지입니다.</h1><br><br><br>

<table class='itemtable'>
	<tr><th>물품코드</th><th>물품명</th><th>가격</th></tr>
	<%
		List<ItemDTO> list = (List<ItemDTO>)request.getAttribute("itemlist");
		for(ItemDTO dto:list){
	%>
		<tr>
			<td><%=dto.getItem_code() %></td>
			<td><%=dto.getItem_name() %></td>
			<td><%=dto.getItem_price() %></td>
		</tr>
	<%
		}
	%>
</table>


</body>
</html>