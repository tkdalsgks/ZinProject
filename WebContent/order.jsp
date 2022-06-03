<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<% 
	String item_name = (String) session.getAttribute("item_name");
	Integer item_price = (Integer) session.getAttribute("item_price");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴</title>
<style>
.pagetitle{
	margin-top : 80px;
	text-align:center;
}

.orderdiv{
	width : 60%;
	margin-left : auto;
	margin-right : auto;
	margin-top : 50px;
	text-align :center;
	font-size : 20px;
}

.orderbtn{
	background-color : black;
	color :white;
	width : 30%;
	height : 50px;
	font-size : 17px;
	margin-top : 50px;
}

.orderbtn:hover{
	cursor : pointer;
}

</style>
<%@ include file="header.jsp"%>
<script src="js/jquery-3.6.0.min.js"></script>
<script>
<script>

</script>
</head>
<body>

<h1 class='pagetitle'>주문하기 페이지입니다.</h1>
<div class='orderdiv'>
<form action="p.orders" method="post">
	점포코드 <input type="number" name="shop_code" required><br>
	상품코드 <input id='item_code' type="number" name="item_code" onchange="totalprice" required><br>
	주문수량 <input id='orders_amount' type="number" name="orders_amount" onchange="totalprice" required><br><br>
	<!-- 주문종류 <input type="text" name="orders_sort"><br><br>-->
	<div id='totalprice'></div>
	<input class='orderbtn' type="submit" value="주문하기">
</form>
</div>
<% 
	String ordermsg = (String)session.getAttribute("ordermsg");
	if(ordermsg != null){
		out.println("<script>alert('"+ ordermsg +"');</script>");
	}
%>

</body>
</html>