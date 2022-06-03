<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<%@ include file="header.jsp"%>
<style>
body{
	font-family: 'Gowun Dodum', sans-serif;
	color : black;
	weight : 500;
}
.loginform{
	margin-top : 200px;
	margin-bottom : 200px;
	border : 1px solid black;
	width : 50%;
	height : 400px;
	margin-right : auto;
	margin-left : auto;
	text-align : center;
	padding-top : 50px;
	vertical-align : center;
}

.inputtitle{
	font-size : 25px;
	margin-right : 50px;
}
.loginbtn{
	margin-top : 30px;
	background-color : black;
	color : white;
	width : 100px;
	height : 40px;
	font-size : 17px;
}

.loginbtn:hover{
	cursor : pointer;
}

.logininput{
	font-size : 20px;
	height : 25px;
}

.title{
	font-size : 30px;
}

</style>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
</head>
<body>

<form action="login" method="post">
	<table class='loginform'>
		<tr>
			<th class='title' colspan='2'>로그인</th>
		</tr>
		<tr>
			<td class='inputtitle'>아이디</td>
			<td><input class='logininput' type="text" name="account_id"></td>
		</tr>
		<tr>
			<td class='inputtitle'>비밀번호</td>
			<td><input class='logininput' type="password" name="account_pwd"></td>
		</tr>
		<tr>
			<td colspan="2"><input class='loginbtn' type="submit" value="로그인"></td>
		</tr>
	</table>
</form>


</body>
</html>