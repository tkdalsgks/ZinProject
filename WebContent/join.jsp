<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

<script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>

</head>
<body>

<form action="join" method="post">
	<table style="text-align: center;">
		<tr>
			<td colspan="2">회원가입</td>
		</tr>
		<tr>
			<td>아이디</td>
			<td><input type="text" name="account_id"></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="account_pwd"></td>
		</tr>
		<tr>
			<td colspan="2">본사</td>
		</tr>
		<tr>
			<td>본사코드</td>
			<td><input type="text" name="company_code"></td>
		</tr>
		<tr>
			<td>본사명</td>
			<td><input type="text" name="company_name"></td>
		</tr>
		<tr>
			<td>팀코드</td>
			<td><input type="text" name="team_code"></td>
		</tr>
		<tr>
			<td>팀명</td>
			<td><input type="text" name="team_name"></td>
		</tr>
		<tr>
			<td>팀원코드</td>
			<td><input type="text" name="member_code"></td>
		</tr>
		<tr>
			<td>팀원명</td>
			<td><input type="text" name="member_name"></td>
		</tr>
		<tr>
			<td colspan="2">점포</td>
		</tr>
		<tr>
			<td>점포코드</td>
			<td><input type="text" name="shop_code"></td>
		</tr>
		<tr>
			<td>점포명</td>
			<td><input type="text" name="shop_name"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="회원가입"></td>
		</tr>
	</table>
</form>

</body>
</html>