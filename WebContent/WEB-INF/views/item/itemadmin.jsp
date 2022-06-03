<%@page import="member.item.itemDTO.ItemDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>물품관리</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/admin.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	//alert("페이지 onload");
	$("#insert_btn").on("click",function(){
		//alert("클릭");
		location.href = "${pageContext.request.contextPath}/iteminsert";
	})	
});

</script>
</head>
<body>

<h1 class='pagetitle'>물품관리 페이지입니다.</h1>

<input id='insert_btn' class='insertbtn' type="button" value="등록하기"><br><br><br>

<table class='admintable'>
	<tr><th>물품코드</th><th>물품명</th><th>가격</th><th></th><th></th></tr>
	<%
		List<ItemDTO> list = (List<ItemDTO>)request.getAttribute("itemlist");
		for(ItemDTO dto:list){
	%>
		<tr>
			<td><%=dto.getItem_code() %></td>
			<td><%=dto.getItem_name() %></td>
			<td><%=dto.getItem_price() %></td>
			<td><a href='${pageContext.request.contextPath}/itemmod?code=<%=dto.getItem_code()%>'>수정</a></td>
			<td><a href='${pageContext.request.contextPath}/itemdelete?code=<%=dto.getItem_code()%>'>삭제</a></td>
		</tr>
	<%
		}
	%>
</table>


</body>
</html>