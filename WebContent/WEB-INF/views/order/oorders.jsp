<%@page import="member.order.orderDTO.OrdersDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문내역</title>

<%@ include file="/WEB-INF/views/header.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/admin.css" type="text/css">
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

<h1 class='pagetitle'>주문내역 페이지입니다.</h1>
<div class="date_search" id="date_search">
	<form action='oorders_date' method='post'>
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
	List<String> itemname = (List<String>)request.getAttribute("itemname");
	if(list.size() != 0){
%>
<table class='admintable'>
	<tr><th>상품명</th><th>주문수량</th><th>확정수량</th><th>주문일자</th><th>처리여부</th><th>승인여부</th></tr>
	<%
		int i=0;
		for(OrdersDTO dto:list){
	%>
		<tr>
			<td><%=itemname.get(i++) %></td>
			<td><%=dto.getOrders_amount() %></td>
			<td><%=dto.getOrders_camount() %></td>
			<td><%=dto.getOrders_date() %></td>
			<%
				if(dto.getOrders_sort() == 0){
			%>
				<td style="color:red;font-weight:600;"> X </td>
			<%
				}else{
			%>
				<td style="font-weight:600;"> O </td>
			<%
				}
				if(dto.getOrders_sort() == 0){
			%>
				<td style="font-weight:600;"> - </td>
			<%
				}else if(dto.getOrders_amount()!=dto.getOrders_camount()){
			%>
				<td style="color:red;font-weight:600;"> X </td>
			<%
				}else{
			%>
				<td style="font-weight:600;"> O </td>
			<%
				}
			%>
		</tr>
	<%
		}
	%>
</table>
<%
	}else{
%>
	<h3 class='notable'>주문한 내역이 없습니다.</h3>
<%
	}
%>
</div>



</body>
</html>