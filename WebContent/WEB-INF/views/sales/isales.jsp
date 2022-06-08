<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.item.itemDTO.ItemDTO" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% List<ItemDTO> list = (List<ItemDTO>)request.getAttribute("itemlist"); %>
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

<script type="text/javascript">
	$(document).ready(function() {
		$('#div_order').css('display', 'none');
		$('#div_pay').on('click', function() {
			$('#div_order').css('display', '');
		})
	})
</script>

<h1 class='pagetitle'>매출등록 페이지입니다.</h1>
<div style="display: flex; width: 1400px; margin: 0 auto; text-align: center;">
	<div class='sales_one'>
		<form action="psales" method="post">
			<table class='salestable'>
				<tr><th></th><th>상품명</th><th>가격</th><th>재고량</th><th>등록수량</th><th></th></tr>
				<c:forEach var="list" items="${itemlist}" varStatus="status">
					<tr>
						<td><input type="hidden" name="item_code${status.count}" value="${list.item_code}"></td>
						<td><c:out value="${list.item_name}" /></td>
						<td><input type="text" class="sales_price" id="item_price${status.count}" value="${list.item_price}"></td>
						<td><input type="text" class="sales_amount" value="${list.sitem_amount}"></td>
						<td><input type="number" class="sales_number" name="item_amount${status.count}" min="0" max="${list.sitem_amount}"></td>
					</tr>
				</c:forEach>
			</table>
			<button type="submit" id="item_input${status.count}" style="margin: 20px 0 20px 0; width: 100px; height: 30px;">담기</button>
		</form>
	</div>
	<div class="sales_two">
		<div>
			<div style="border-bottom: 1px solid black; width: 200px; margin: 0 auto 15px auto;">
				<span style="font-size: 20px;">상품/가격</span>
			</div>
			<div style="border-bottom: 1px solid black; width: 200px; margin: 0 auto 15px auto;">
				<span style="font-size: 20px;">총금액</span><br>
				<span><input type="text" class="total_amount" id="total_amount" readonly></span> 원
			</div>
			<div>
				<button id="div_pay"><span style="font-size: 20px;">결제</span></button>
			</div>
		</div>
	</div>
</div>
<div id="div_order" class="sales_three">
	<div style="border: 1px solid black; width: 1400px; height: 250px; margin: 0 auto;">
		<button>신용카드</button>
		<button>현금</button>
		<button>확인</button>
	</div>
</div>

<script type="text/javascript">
	/* $(document).ready(function() {
		for(let i = 1; i <= 100; i++) {
			var total = 0;
			var sum = 0;
			$('#item_amount' + i).blur(function () {
				$('#item_amount' + i).each(function() {
					total = $('#item_price' + i).val() * $('#item_amount' + i).val();
					sum += total;
				});
				$("#total_amount").val(sum);
			})
		}
	}) */
</script>

</body>
</html>