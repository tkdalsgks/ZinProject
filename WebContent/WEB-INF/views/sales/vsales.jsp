<%@page import="member.sales.salesDTO.SalesDTO"%>
<%@page import="member.sales.payDTO.PayDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매출확인</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/sales_view.css" type="text/css">
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>

<h1 class='pagetitle'>매출확인 페이지입니다.</h1>

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
					if(dto.getSales_sort() == 1){
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
						<td><a href="${pageContext.request.contextPath}/vsalesdelete?salescode=<%=dto.getSales_code()%>&salesnumber=<%=dto.getSales_number()%>">매출취소</a></td>
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

</body>
</html>