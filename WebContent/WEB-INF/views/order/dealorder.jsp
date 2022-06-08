<%@page import="member.order.orderDTO.OrdersDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 처리</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/insert.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>주문 처리 페이지입니다.</h1>
<%
	OrdersDTO dto = (OrdersDTO)request.getAttribute("ordersinfo");
%>
<table class='inserttable'>
	<form action="dealorder" method="post">
		<tr>
			<td class='insertname'>주문번호</td>
			<td class='insertcontext'><%=dto.getOrders_code() %><input name="orders_code" type="hidden" id="orders_code" value="<%=dto.getOrders_code() %>"></td>
		</tr>
		<tr><td class='insertname'>점포코드</td><td class='insertcontext'><%=dto.getShop_code() %><input name="shop_code" type="hidden" id="shop_code" value="<%=dto.getShop_code() %>"></td></tr>
		<tr><td class='insertname'>상품코드</td><td class='insertcontext'><%=dto.getItem_code() %><input name="item_code" type="hidden" id="item_code" value="<%=dto.getItem_code() %>"></td></tr>
		<tr><td class='insertname'>주문수량</td><td class='insertcontext'><%=dto.getOrders_amount() %><input name="orders_amount" type="hidden" id="orders_amount" value="<%=dto.getOrders_amount() %>"></td></tr>		
		<tr><td class='insertname'>확정수량</td><td><input class="insertinput" name="orders_camount" type="number" id="orders_camount" min=0 max=<%=dto.getOrders_amount() %> required></td></tr>
		<tr><td class='insertname'>주문일</td><td class='insertcontext'><%=dto.getOrders_date() %><input name="orders_date" type="hidden" id="orders_date" value="<%=dto.getOrders_date() %>"></td></tr>
		<tr><td colspan='2'><input class='insertbtn' type="submit" value="확인" id="deal_btn"></td></tr>
	</form>
</table>

</body>
</html>