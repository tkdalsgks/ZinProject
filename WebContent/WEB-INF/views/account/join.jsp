<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<%@ include file="/WEB-INF/views/header.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/login.css" type="text/css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">

<script type="text/javascript" >
window.onload = function(){
	
	var msg = "<%=request.getAttribute("msg")%>";
	if(msg != '' && msg != "undefined" && msg != "null"){		
		alert(msg);
	}

	
	var forcompany = document.getElementById("forcompany");
	var forshop = document.getElementById("forshop");
	forcompany.style.display = "none";
	forshop.style.display = "none";
	document.getElementById("inputcompany").value = "0";
	document.getElementById("inputteam").value = "0";
	document.getElementById("inputmember").value = "0";
	document.getElementById("inputname").value = "none";
	document.getElementById("inputshop").value = "0";
}

function typeChange(param){
	const typevalue = param.value;
	if(typevalue == "company"){
		forcompany.style.display = "";
		forshop.style.display = "none";
		document.getElementById("inputcompany").value = "";
		document.getElementById("inputteam").value = "";
		document.getElementById("inputmember").value = "";
		document.getElementById("inputname").value = "";
		document.getElementById("inputshop").value = "0";
	}else{
		forcompany.style.display = "none";
		forshop.style.display = "";
		document.getElementById("inputshop").value = "";
		document.getElementById("inputcompany").value = "0";
		document.getElementById("inputteam").value = "0";
		document.getElementById("inputmember").value = "0";
		document.getElementById("inputname").value = "none";
	}
}

function idChange(){
	document.getElementById("checkedid").value = "false";
}

function idcheck(){
	var checked = true;
	var account_id = $("#account_id").val();
	if(account_id == ""){
		alert("아이디를 입력해주세요.");
		checked= false;
	}
	else{
		$.ajax(
			{
				type : "POST",
				url :"idcheck",
				async : false,
				data : {"account_id" : account_id},
				success : function(msg){
					if(msg == "false"){
						alert("이미 사용중인 아이디입니다.");
						$("#account_id").val("");
						document.getElementById("checkedid").value = "false";
						checked = false;
					}else if(msg == "true"){
						alert("사용가능한 아이디입니다.");
						document.getElementById("checkedid").value = "true";
						checked = true;
					}
				}
			}
		)
	}
	
	//alert(checked);
	return checked;
}

function finishidcheck(){
	var result = true;
	var checked = document.getElementById("checkedid").value;
	//alert(checked);
	if(checked == "false"){
		result = false;
		alert("아이디 중복여부를 확인해주세요.");
	}
	
	return result;
}

</script>



</head>
<body>

<form action="join" method="post" onsubmit="return finishidcheck();">
	<table class='signupform' style="text-align: center;">
		<tr>
			<th class='title' colspan="2">회원가입</th>
		</tr>
		<tr>
			<td class='inputtitle'>아이디</td>
			<td>
				<input class='input' id="account_id" type="text" name="account_id" onchange="idChange()" required>
				<input id='idchkbtn' type="button" value="아이디 중복 확인" onclick="idcheck()">
				<input type="hidden" id="checkedid" value="false">
			</td>
			
		</tr>
		<tr>
			<td class='inputtitle'>비밀번호</td>
			<td><input class='input' type="password" name="account_pwd" required></td>
		</tr>
		<tr>
			<td class='inputtitle'>소속</td>
			<td>
				<input type="radio" name="type" id="inputtype" value="company" onchange="typeChange(this)" required><span class='signupradio'> 본사</span> &nbsp;&nbsp;
				<input type="radio" name="type" id="inputtype" value="shop" onchange="typeChange(this)"><span class='signupradio'> 점포</span>
			</td>
		</tr>
		<tbody id='forcompany'>
			<tr>
				<td class='inputtitle'>본사코드</td>
				<td><input class='input' id="inputcompany" type="number" name="company_code" required></td>
			</tr>
			<tr>
				<td class='inputtitle'>팀코드</td>
				<td><input class='input' id="inputteam" type="number" name="team_code" required></td>
			</tr>
			<tr>
				<td class='inputtitle'>사원번호</td>
				<td><input class='input' id="inputmember" type="number" name="member_code" required></td>
			</tr>
			<tr>
				<td class='inputtitle'>팀원명</td>
				<td><input class='input' id="inputname" type="text" name="member_name" required></td>
			</tr>
		</tbody>
		<tbody id="forshop">
			<tr>
				<td class='inputtitle'>점포코드</td>
				<td><input class='input' id="inputshop" type="number" name="shop_code" required></td>
			</tr>
		</tbody>
		<% 
			String errormsg = (String)session.getAttribute("errormsg");
			if(errormsg != null){
				out.println("<tr><td colspan='2'><font color='red' size='2'>"+ errormsg +"</font></td></tr>");
			}
		%>
		<tr>
			<td colspan="2"><input class='btn' type="submit" value="`회원가입"></td>
		</tr>
	</table>
</form>

</body>
</html>