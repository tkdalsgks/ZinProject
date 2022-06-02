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
<%@ include file="header.jsp"%>
<script src="js/jquery-3.6.0.min.js"></script>
</head>
<body>

<%-- <%
	Class.forName("oracle.jdbc.driver.OracleDriver");
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";
	
	Connection conn = DriverManager.getConnection(url, id, pw);
	
	String SQL = "select item_name, item_price from item";
	PreparedStatement ps = null;
	ResultSet rs = null;
	String item_name = null;
	int item_price = 0;
	ps = conn.prepareStatement(SQL);
	rs = ps.executeQuery();
	if(rs.next()) {
		item_name = rs.getString("item_name");
		item_price = rs.getInt("item_price");
	}
	
	String ordersql = "insert into ";
%> --%>

<form action="p.orders" method="post">
	점포코드 <input type="text" name="shop_code"><br>
	상품코드 <input type="text" name="item_code"><br>
	주문수량 <input type="text" name="orders_amount"><br>
	주문종류 <input type="text" name="orders_sort"><br><br>
	<input type="submit" value="주문하기">
</form>
		

</body>
</html>