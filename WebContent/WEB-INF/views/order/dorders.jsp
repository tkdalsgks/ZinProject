<%@page import="member.order.orderDTO.OrdersDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>처리된 주문</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/admin.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1;
	var yyyy = today.getFullYear();
	
	if(dd < 10){
		dd = '0' + dd;
	}
	if(mm < 10){
		mm = '0' + mm;
	}
	
	today = yyyy + '-' + mm + '-' + dd;
	document.getElementById("start_date").setAttribute("max",today);
	document.getElementById("end_date").setAttribute("max",today);
});

function startdate_change(){
	var startdate = document.getElementById("start_date").value;
	document.getElementById("end_date").setAttribute("min",startdate);
}
</script>
</head>
<body>

<h1 class='pagetitle'>처리된 주문 페이지입니다.</h1>
<div class="date_search" id="date_search">
	<form action='dorders_date' method='post'>
	<input id="start_date" name="start_date" type="date" onchange="startdate_change()" required>
	 ~ 
	<input id="end_date" name="end_date" type="date" required>
	<input type="image" class='searchimg' src="${pageContext.request.contextPath}/resource/img/search.png">
	</form>
</div>
<br>
<br>
<br>
<div id='pagecontext'>
<%
	List<OrdersDTO> list = (List<OrdersDTO>)request.getAttribute("orderslist");
	if(list.size() != 0){
%>


<table id="admintable" class='admintable'>
	<tr><th>주문코드</th><th>점포코드</th><th>상품코드</th><th>주문수량</th><th>확정수량</th><th>주문일자</th></tr>
	<%
		for(OrdersDTO dto:list){
	%>
		<tr><td><%=dto.getOrders_code() %></td><td><%=dto.getShop_code() %></td><td><%=dto.getItem_code() %></td><td><%=dto.getOrders_amount() %></td><td><%=dto.getOrders_camount() %></td><td><%=dto.getOrders_date() %></td></tr>
	<%
		}
	%>
</table>
<%
	}else{
%>
	<h3 class='notable'>처리된 주문이 없습니다.</h3>
<%
	}
%>
</div>
</body>
</html>