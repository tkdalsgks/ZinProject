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