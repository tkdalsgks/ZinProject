<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>

body{
	margin : 0;
	font-family: 'Gowun Dodum', sans-serif;
	color : black;
	weight : 500;
}
.header_img{/*로고 이미지*/
	 padding-top:5px; 
	 padding-bottom:5px; 
	 padding-left:20px; 
	 vertical-align: middle; 
	 width: 70px;
	 height:70px;
}

.JoinLogin{/*로그인, 회원가입 부분 개별*/
	text-align: right;
	margin-right:10px;
	margin-top:0;
	margin-bottom:5px;
	padding-top:10px;
	font-size: 13px;
}

.header_j { /*JoinLogin 부분의 a 태그(색 변경)*/
	text-decoration: none;
	font-weight: 500;
	font-size: 15px;
	text-align: left;
	color : black;
}

.header_j:hover{
	cursor : pointer;
}

.header_hr{
	width : 100%;
}

.login_id{
	margin-right : 20px;
	font-weight : 600;
}

.header_li, .header_ul, .bottom_li, .bottom_ul{
	list-style: none;	
	padding : 0;
}

.header_a {
	text-decoration: none;
	font-weight: 500;
	font-size: 20px;
	text-align: center;
    display: inline-block;
    width: 190px;
    border-radius: 10px;
}

.header_a:hover{
	font-weight: 800;
	font-size:20px;
}
nav {/* 메뉴바 */
	display:inline-block;
	margin-top: 15px;
	margin-left: auto;
	margin-right: auto;
	margin-bottom: 0;
	text-align: center;
	width : 80%;
}
nav .header_ul> .header_li {
	display: inline-block;
	vertical-align: top;
	text-align: center;
}
.header_b{
	text-decoration: none;
	font-weight: 400;
	font-size: 15px;
	text-align: center;
    display: inline-block;
    width: 150px;
    border-radius: 10px;
}
.header_b:hover{
	font-weight: 700;
	font-size:16px;
}
a{
	color : black;
}



</style>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
window.onload = function(){
	if($("#sessionid").val() != '' && $("#sessionid").val() != "undefined" && $("#sessionid").val() != null){ 
		//alert("메뉴가져오기");
		//alert($("#sessionid").val());
		$.ajax(
			{
				type : "POST",
				url :"menulist",
				data : {"account_id" : $("#sessionid").val()},
				dataType : "json",
				success : function(result){
					//alert(result.length);
					$("#header_ul").empty();
					var maxindex=0;
					for(var i=0;i<result.length;i++){
						if(result[i].menu_code>maxindex){
							maxindex=result[i].menu_code;
						}
					}
					for(var i=0;i<result.length;i++){
						$("#header_ul").append("<li id='menu_li"+result[i].menu_code+"' class='header_li'><a class='header_a' href='"+result[i].menu_url+"' onmouseover='bottommenu("+result[i].menu_code+","+maxindex+")'>"+result[i].menu_name+"</a><ul id='bottom_ul"+result[i].menu_code+"'></ul></li>");
					}
				}	
			}
		)
	}
	
	
}

function bottommenu(index,length){
	$.ajax(
		{
			type:"POST",
			url : "bottommenu",
			data : {"menu_top" : index},
			dataType : "json",
			success : function(result){
				for(var i=0;i<length;i++){
					$("#bottom_ul"+i).empty();
				}
				for(var i=0;i<result.length;i++){							
					$("#bottom_ul"+index).append("<li id='bottom_li"+result[i].menu_code+"' class='bottom_li'><a class='header_b' href='"+result[i].menu_url+"'>"+result[i].menu_name+"</a></li>");
					
				}
			}
			
			
		}		
	)
}
</script>

</head>
<body>

	<c:if test="${sessionScope.account_id != null }">
		<div class="JoinLogin">
			<input id="sessionid" type="hidden" value='${sessionScope.account_id }'>
			<span id="login_id" class="login_id">안녕하세요 ${sessionScope.account_id}님!</span>
			<a class="header_j" href="mypage">마이페이지</a>&nbsp;/
			<a class="header_j" href="logout" onclick="alert('로그아웃 되었습니다.');">로그아웃</a>
		</div>
	</c:if>
	
	<a href="${pageContext.request.contextPath}/"><img class="header_img" src="${pageContext.request.contextPath}/img/logo.png"></a>
	
	<nav>
		<ul id="header_ul" class="header_ul">
			
		</ul>
	</nav>

	<hr id="header_hr">
</body>
</html>