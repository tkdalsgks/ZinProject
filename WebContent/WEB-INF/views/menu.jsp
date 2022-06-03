<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String company_name = (String) session.getAttribute("company_name");
	String shop_name = (String) session.getAttribute("shop_name");
	String menu = (String) session.getAttribute("menu");
	Integer menu_code = (Integer) session.getAttribute("menu_code");
	Integer company_code = (Integer) session.getAttribute("company_code");
	Integer cnt = (Integer) session.getAttribute("cnt");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴</title>
</head>
<body>

		<table style="text-align:center;">
<%
	if(company_name != null){
%>
			<tr>
				<th>본사</th>
			</tr>
			<tr>
				<td><a href="">점포관리</a></td>
			</tr>
			<tr>
				<td><a href="">주문관리</a></td>
			</tr>
			<tr>
				<td><a href="">매출관리</a></td>
			</tr>
<%
	} else if(shop_name != null){
%>
			<tr>
				<th>점포</th>
			</tr>
			<tr>
				<td><a href="">상품주문</a></td>
			</tr>
			<tr>
				<td><a href="">상품판매</a></td>
			</tr>
<%
	}
%>
			<tr>
				<td><a href="/">홈</a></td>
			</tr>
		</table>

</body>
</html>