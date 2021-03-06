<%@page import="member.order.orderDTO.OrdersDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>취소 주문</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/admin.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	
	<h1 class='pagetitle'>취소된 주문내역 페이지입니다.</h1>
	<%
		List<OrdersDTO> list = (List<OrdersDTO>)request.getAttribute("orderslist");
		List<String> itemname = (List<String>)request.getAttribute("itemname");
		if(list.size() != 0){
	%>
	<table class='admintable'>
		<tr><th>주문코드</th><th>상품코드</th><th>주문수량</th><th>확정수량</th><th>주문일자</th></tr>
		<%
			for(OrdersDTO dto:list){
		%>
			<tr><td><%=dto.getOrders_code() %></td><td><%=dto.getItem_code() %></td><td><%=dto.getOrders_amount() %></td><td><%=dto.getOrders_camount() %></td><td><%=dto.getOrders_date() %></td></tr>
		<%
			}
		%>
	</table>
	<%
		}else{
	%>
		<h3 class='notable'>승인된 주문이 없습니다.</h3>
	<%
		}
	%>


</body>
</html>