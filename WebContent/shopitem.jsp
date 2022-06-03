<%@page import="java.util.List"%>
<%@page import="dto.SitemDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>점포물품확인</title>
<style>
.pagetitle{
	margin-top : 80px;
	text-align:center;
}

.sitemtable{
	text-align : center;
	width : 70%;
	margin-right:auto;
	margin-left:auto;
	border: 1px solid gray;
	border-collapse: collapse;
	margin-bottom : 100px;
}
.sitemtable th{
	font-size : 20px;
	border : 1px solid gray;
}
.sitemtable tr,td{
	border : 1px solid gray;
}
.noitem{
	text-align:center;
}
</style>
<%@ include file="header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>점포물품확인 페이지입니다.</h1><br><br><br>
<%
	List<SitemDTO> list = (List<SitemDTO>)request.getAttribute("sitemlist");
	if(list.size() != 0){
%>
<table class='sitemtable'>
	<tr><th>매장내 물품코드</th><th>물품코드</th><th>수량</th></tr>
	<%
		for(SitemDTO dto:list){
	%>
		<tr>
			<td><%=dto.getSitem_code() %></td>
			<td><%=dto.getItem_code() %></td>
			<td><%=dto.getSitem_amount() %></td>
		</tr>
	<%
		}
	%>
</table>
<%
	}else{
%>
	<h3 class='noitem'>점포에 물품이 없습니다.</h3>
<%
	}
%>

</body>
</html>