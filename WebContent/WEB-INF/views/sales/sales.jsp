<%@page import="member.sales.payDTO.PayDTO"%>
<%@page import="member.sales.salesDTO.SalesDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>총매출</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/sales_view.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
	
	$("#shop_name").one("click",function(){
		$.ajax(
		{
			type : "POST",
			url : "shoplist",
			dataType : "json",
			success : function(result){
				$("#shop_name").html("<option value='' disabled>----선택 ----</option>");
				$("#shop_name").append("<option value='all'>본사점포 전체</option>");
				if(result.length>0){					
					$("#shop_name").append("<option value='memall'>내 관리점포 전체</option>");
				}
				for(var i=0; i<result.length;i++){
					$("#shop_name").append("<option value='"+result[i].shop_code+"'>"+ result[i].shop_name+"</option>");
				}
			}
		}		
		)
	})	
});


</script>
</head>
<body>

<h1 class='pagetitle'>총매출 페이지입니다.</h1>

<div class='searchshop'>
	<form action="sales" method="post">
	<span class='searchtitle'>매장선택</span>
	<select class='shoplist' id="shop_name" name='shop_code' required>
		<option value='' selected disabled>----선택 ----</option>
	</select>
	<input class='searchbtn' type="submit" value="매출확인">
	</form>
</div>
<div class='sales'>
	
	<%
		List<SalesDTO> list = (List<SalesDTO>)request.getAttribute("saleslist");
		int sales_amount=0;
		int sales_price=0;
		if(list != null){
	%>
		<div class='totalsales'>		
	<%
			if(list.size() != 0){
				for(SalesDTO dto:list){
					if(dto.getSales_sort() != 1){
						sales_amount += dto.getSales_amount();
						sales_price += dto.getSales_price();
					}
				}
	%>
			총 판매량 : <%=sales_amount %>개 <br>
			총 매출액 : <%=sales_price %>원
	<%
			}else{
	%>
				아직 매출이 없습니다.
	<%
			}
	%>
		</div>
	<%
		}
	%>
	<div class='saleslist'>
	<%
		List<PayDTO> paylist = (List<PayDTO>)request.getAttribute("paylist");
		if(list != null){
			if(list.size() != 0){
	%>
				<table class='saleslisttable'>
				<tr><th>매출코드</th><th>매출 번호</th><th>결제물품</th><th>결제수단</th><th>수량</th><th>금액</th><th>결제일</th><th></th></tr>
	<%
				for(SalesDTO dto:list){
					if(dto.getSales_sort() == 1){
	%>
					<tr>
						<td><%=dto.getSales_code() %></td>
						<td><%=dto.getSales_number() %></td>
						<td><%=dto.getItem_name() %></td>
	
						<td><%=(paylist.get(dto.getPay_code()-1)).getPay_name() %></td>
						<td><%=dto.getSales_amount() %></td>
						<td><%=dto.getSales_price() %></td>
						<td><%=dto.getSales_date() %></td>
						<td><a href="${pageContext.request.contextPath}/salesdelete?salescode=<%=dto.getSales_code()%>&salesnumber=<%=dto.getSales_number()%>">매출취소</a></td>
					</tr>
	<%
					}else{
	%>
					<tr class='notsales'>
						<td><%=dto.getSales_code() %></td>
						<td><%=dto.getSales_number() %></td>
						<td><%=dto.getItem_name() %></td>
	
						<td><%=(paylist.get(dto.getPay_code()-1)).getPay_name() %></td>
						<td><%=dto.getSales_amount() %></td>
						<td><%=dto.getSales_price() %></td>
						<td><%=dto.getSales_date() %></td>
						<td> - </td>
					</tr>
	<%
					}
				}
	%>
				</table>
	<%		
				}
		}
	%>
	</div>
</div>
<br>
</body>
</html>