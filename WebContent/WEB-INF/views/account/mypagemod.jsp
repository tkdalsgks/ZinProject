<%@page import="account.accountDTO.AccountDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<%@ include file="/WEB-INF/views/header.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/mypagemod.css" type="text/css">
</head>

<script>

function back(){
	location.href = "mypage";
}

function pwdrequired(){
	var result = true;
	var cur_pwd = $("#cur_pwd").val();
	var new_pwd = $("#new_pwd").val();
	var check_pwd = $("#check_pwd").val();
	
	if(cur_pwd != null && cur_pwd != "undefined" && cur_pwd != '' && new_pwd != null && new_pwd != "undefined" && new_pwd != '' && check_pwd != null && check_pwd!="undefined" && check_pwd!=''){
		result = true;
	}else if((cur_pwd == null || cur_pwd == "undefined" || cur_pwd == '') && (new_pwd == null || new_pwd == "undefined" || new_pwd == '') && (check_pwd == null || check_pwd == "undefined" || check_pwd == '')){
		result = true;
	}else{
		result = false;
	}
	return result;
}
function checkinput(){
	var result = true;
	var pwdrequiredresult = pwdrequired();
	if(pwdrequiredresult == false){
		result = false;
		alert("비밀번호 변경시 3가지 입력값을 모두 입력해야합니다.");
	}else{
		var cur_pwd = $("#cur_pwd").val();
		var pwd = $("#pwd").val();
		var new_pwd = $("#new_pwd").val();
		var check_pwd = $("#check_pwd").val();
		
		if(cur_pwd != pwd){
			result = false;
			alert("비밀번호 입력을 다시 확인해주세요.");
		}else if(new_pwd != check_pwd){
			result = false;
			alert("비밀번호 입력을 다시 확인해주세요.");
		}else if(cur_pwd == new_pwd){
			result = false;
			alert("새로운 비밀번호를 입력해주세요.");
		}
		
	}
	
	return result;
}
</script>
<body>

<h1 class='pagetitle'>마이페이지 수정 페이지입니다.</h1>
<% 
	AccountDTO account = (AccountDTO)request.getAttribute("myinfo");

%>

<table class='admintable'>
	<form action="mypagemod" method="post" onsubmit="return checkinput();">
	<tr>
		<td>아이디</td>
		<td><%=account.getAccount_id() %>
			<input type="hidden" name="account_id" value="<%=account.getAccount_id() %>">
		</td>
	</tr>
	<tr>
		<td>현재 비밀번호</td>
		<td><input type="password" id="cur_pwd"><input type="hidden" id="pwd" value="<%=account.getAccount_pwd() %>"></td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><input type="password" id="new_pwd" name="account_pwd"></td>
	</tr>
	<tr>
		<td>비밀번호 확인</td>
		<td><input type="password" id="check_pwd"></td>_
	</tr>
	<%
		if(account.getMember_name() != null){
	%>
	<tr>
		<td>이름</td>
		<td><input type="text" id="member_name" name="member_name" value="<%=account.getMember_name() %>" required></td>
	</tr>
	<%
		}
	%>
	<%
		if(account.getShop_name() != null){
	%>
	<tr>
		<td>점포명</td>
		<td><input type="text" id="shop_name" name="shop_name" value="<%=account.getShop_name() %>" required></td>
	</tr>
	<%
		}
	%>
	
	<tr>
		<td><input type="button" class="mypagebtn" value="취소" onclick='back()'></td>
		<td><input type="submit" class='mypagebtn' value="수정완료"></td>
	</tr>
	</form>
</table>

</body>
</html>