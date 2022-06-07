<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/css/header.css" type="text/css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script>
$(document).ready(function(){	
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
});


function bottommenu(index,length){
	$.ajax(
		{
			type:"POST",
			url : "bottommenu",
			data : {"menu_top" : index},
			dataType : "json",
			success : function(result){
				for(var i=0;i<=length;i++){
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
	
	<a href="${pageContext.request.contextPath}/"><img class="header_img" src="${pageContext.request.contextPath}/resource/img/logo.png"></a>
	
	<nav>
		<ul id="header_ul" class="header_ul">
			
		</ul>
	</nav>

	<hr id="header_hr">
</body>
</html>