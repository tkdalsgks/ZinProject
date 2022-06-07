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

<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/order.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
	
	$("#totalprice").hide();
	
	$("#item_name").one("click",function(){
		$.ajax(
		{
			type : "POST",
			url : "itemlist",
			dataType : "json",
			success : function(result){
				$("#item_name").html("<option value='' disabled>----선택 ----</option>");
				for(var i=0; i<result.length;i++){
					$("#item_name").append("<option value='"+result[i].item_code+"'>"+ result[i].item_name+"</option>");
				}
			}
		}		
		)
	})	
});

function totalprice(){
	var item_name = $("#item_name").val();
	var item_amount = $("#orders_amount").val();
	if(item_name != '' && item_name != null && item_name != "undefined" && item_amount != '' && item_amount != null && item_amount != "undefined"){
		if(item_amount>1){
			$.ajax({
				type : "POST",
				url :"orderprice",
				async : false,
				data : {"item_code" : item_name},
				success : function(price){
					//alert(price);
					$("#totalprice").show();
					$("#price").html(price + "원 * " + item_amount + "개 = " + (price*item_amount) + "원");
				}
			})
		}
	}else{
		$("#totalprice").hide();
	}
}


</script>

</head>
<body>

<h1 class='pagetitle'>주문하기 페이지입니다.</h1>
<div class='orderdiv'>
<form action="p.orders" method="post">
	<!-- 점포코드 <input type="number" name="shop_code" required><br> -->
	상품명 
	<select class='itemlist' id="item_name" name='item_code' onchange="totalprice()" required>
		<option value='' selected disabled>----선택 ----</option>
	</select>
	<br>
	<!-- <input id='item_code' type="number" name="item_code" onchange="totalprice" required><br> -->
	주문수량 <input class='input' id='orders_amount' type="number" name="orders_amount" min=1 onkeyup="totalprice()" required><br><br>
	<!-- 주문종류 <input type="text" name="orders_sort"><br><br>-->
	<div id='totalprice'>
		<hr class='totalpricehr'>
		<span class='pricetitle'>총 주문금액 > </span>
		<span id='price' class='price'></span>
	</div>
	<input class='orderbtn' type="submit" value="주문하기">
</form>
</div>
<% 
	String ordermsg = (String)request.getAttribute("ordermsg");
	if(ordermsg != null){
		out.println("<script>alert('"+ ordermsg +"');</script>");
	}
%>

</body>
</html>