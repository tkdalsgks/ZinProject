<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.item.itemDTO.ItemDTO" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	List<ItemDTO> list = (List<ItemDTO>)request.getAttribute("itemlist");
	List<ItemDTO> list2 = (List<ItemDTO>)request.getAttribute("psaleslist");
	int sales_sum = ((Integer)request.getAttribute("sales_sum")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매출등록</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/sales.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#div_order').css('display', 'none');
		$('#div_pay').on('click', function() {
			$.ajax({
				type : "POST",
				url :"paylist",
				dataType : "json",
				success : function(result){
					//alert(result.length);
					$("#pay_sort").empty();
					for(var i=0;i<result.length;i++){
						$("#pay_sort").append("<input type='button' value='"+result[i].pay_name+"' onclick='paysort("+result[i].pay_code+")'>");
					}
				}	
			})
			
			
			$('#div_order').css('display', '');
		})
	})
	
	function paysort(code){
		$("#pay_code").val(code);
	}
</script>

<input type="hidden" id="account_id" value="${account_id}">

<h1 class='pagetitle'>매출등록 페이지입니다.</h1>
<div style="display: flex; width: 1400px; margin: 0 auto; text-align: center;">
	<div class='sales_one'>
			<table class='salestable'>
				<tr><th></th><th>상품명</th><th>가격</th><th>재고량</th><th>등록수량</th><th></th></tr>
				<c:forEach var="list" items="${itemlist}" varStatus="status">
					<tr>
						<form action="psales" method="post">
							<td><input type="hidden" name="sitem_code" value="${list.sitem_code}"></td>
							<td><c:out value="${list.item_name}" /></td>
							<td><input type="text" class="sales_price" id="item_price${status.count}" value="${list.item_price}"></td>
							<td><input type="text" class="sales_amount" value="${list.sitem_amount}"></td>
							<td><input type="number" class="sales_number" name="item_amount" min="1" max="${list.sitem_amount}" required></td>
							<td><button type="submit" id="item_input${status.count}" style="margin: 5px 0 5px 0; width: 80px; height: 30px;">담기</button></td>
						</form>
					</tr>
				</c:forEach>
			</table>
	</div>
	<div class="sales_two">
		<div>
			<div style="border-bottom: 1px solid black; width: 200px; margin: 0 auto 15px auto;">
				<table>
					<tr><th>상품명</th><th>가격</th><th>수량</th></tr>
					<c:forEach var="list2" items="${psaleslist}">
						<tr>
							<td><c:out value="${list2.item_name}" /></td>
							<td><c:out value="${list2.item_price}" /></td>
							<td><c:out value="${list2.sales_amount}" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div style="border-bottom: 1px solid black; width: 200px; margin: 0 auto 15px auto;">
				<span style="font-size: 20px;">총 금액</span><br>
				<span class="money_comma" style="margin-top: 10px; font-size: 18px; font-weight: bold;">
					<%=sales_sum %><span style="font-size: 18px;"> 원</span>
				</span>
			</div>
			<div>
				<button id="div_pay" style="margin-top: 10px;">
					<span style="font-size: 20px;">결제</span>
				</button>
			</div>
		</div>
	</div>
</div>
<div id="div_order" class="sales_three">
	<div style="border: 1px solid black; width: 1400px; height: 250px; margin: 0 auto;">
		<form action="ppsales" method="post">
			<input type="hidden" name="pay_code" id="pay_code">
			<div id="pay_sort">
	
			</div>
			<button type="submit">확인</button>
		</form>
	</div>
</div>

<script type="text/javascript">
	/* 천단위 콤마 */
	var money = $('.money_comma').text();
	var moneycomma = money.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	$('.money_comma').text(moneycomma);
</script>

</body>
</html>