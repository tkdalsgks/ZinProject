<%@page import="member.item.itemDTO.ItemDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>물품정보</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/admin.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>

<script type="text/javascript">

</script>
</head>
<body>

<h1 class='pagetitle'>물품정보 페이지입니다.</h1><br><br><br>

<table class='admintable'>
	<tr><th>물품명</th><th>가격</th></tr>
	<%
		List<ItemDTO> list = (List<ItemDTO>)request.getAttribute("itemlist");
		for(ItemDTO dto:list){
	%>
		<tr>
			<td><%=dto.getItem_name() %></td>
			<td><%=dto.getItem_price() %></td>
		</tr>
	<%
		}
	%>
</table>


</body>
</html>