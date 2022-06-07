<%@page import="java.util.List"%>
<%@page import="shop.sitem.sitemDTO.SitemDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>점포물품확인</title>

<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/admin.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>점포물품확인 페이지입니다.</h1><br><br><br>
<%
	List<SitemDTO> list = (List<SitemDTO>)request.getAttribute("sitemlist");
	if(list.size() != 0){
%>
<table class='admintable'>
	<tr><th>물품명</th><th>수량</th></tr>
	<%
		for(SitemDTO dto:list){
	%>
		<tr>
			<td><%=dto.getItem_name() %></td>
			<td><%=dto.getSitem_amount() %></td>
		</tr>
	<%
		}
	%>
</table>
<%
	}else{
%>
	<h3 class='notable'>점포에 물품이 없습니다.</h3>
<%
	}
%>

</body>
</html>