<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String company_code = (String) session.getAttribute("company_code"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<span><a href="">상품1</a></span><br>
<span><a href="">상품2</a></span><br>
<span><a href="">상품3</a></span>

<form action="item" method="post">
	<table>
		<tr>
			<td>상품코드</td>
			<td><input type="text" name="item_code"></td>
		</tr>
		<tr>
			<td>본사코드</td>
			<td><input type="text" name="company_code" value="<%=company_code %>"></td>
		</tr>
		<tr>
			<td>상품명</td>
			<td><input type="text" name="item_name"></td>
		</tr>
		<tr>
			<td>가격</td>
			<td><input type="text" name="item_price"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="회원가입"></td>
		</tr>
	</table>
</form>

</body>
</html>