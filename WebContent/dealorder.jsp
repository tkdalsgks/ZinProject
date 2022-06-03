<%@page import="member.order.orderDTO.OrdersDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 처리</title>
<%@ include file="header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>주문 처리 페이지입니다.</h1>
<%
	OrdersDTO dto = (OrdersDTO)request.getAttribute("ordersinfo");
%>
<table class='orderstable'>
	<form action="dealorder" method="post">
		<tr>
			<td>주문번호</td>
			<td><%=dto.getOrders_code() %><input name="orders_code" type="hidden" id="orders_code" value="<%=dto.getOrders_code() %>"></td>
		</tr>
		<tr><td>점포코드</td><td><%=dto.getShop_code() %><input name="shop_code" type="hidden" id="shop_code" value="<%=dto.getShop_code() %>"></td></tr>
		<tr><td>상품코드</td><td><%=dto.getItem_code() %><input name="item_code" type="hidden" id="item_code" value="<%=dto.getItem_code() %>"></td></tr>
		<tr><td>주문수량</td><td><%=dto.getOrders_amount() %><input name="orders_amount" type="hidden" id="orders_amount" value="<%=dto.getOrders_amount() %>"></td></tr>		
		<tr><td>확정수량</td><td><input name="orders_camount" type="number" id="orders_camount" min=0 max=<%=dto.getOrders_amount() %> required></td></tr>
		<tr><td>주문일</td><td><%=dto.getOrders_date() %><input name="orders_date" type="hidden" id="orders_date" value="<%=dto.getOrders_date() %>"></td></tr>
		<tr><td colspan='2'><input type="submit" value="확인" id="deal_btn"></td></tr>
	</form>
</table>

</body>
</html>