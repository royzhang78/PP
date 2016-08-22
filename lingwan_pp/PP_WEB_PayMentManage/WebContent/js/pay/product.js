var  showpage=1;
var jsondata={};

$(document).ready(function() {
	pageshow(showpage);
	
});
/**
 * 查询所有物品信息
 * @param showpagenum
 */
function pageshow(showpagenum){
	
	ShowDiv('hidAllDiv','fade');
	var json = '{"sid":"'+sid+'","showpage": "'+showpagenum+'"';
	if($("#find_product_name").val()!=null && $("#find_product_name").val()!=""){
		json+=', "product_name": "'+$("#find_product_name").val()+'"}';
	}else{
		json +='}';
	}

	
	mypage(0,1,"pageDiv");
	 
	$.ajax({
		type: 'post',
		url: '../m/findProduct',
		data: {
			par: json
		},
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			
		
			$("#productlist").empty();
			if(data.value.length>0){
				$("#prodectTmpl").tmpl(data.value).appendTo("#productlist");
				jsondata=data.value;
			}
			mypage(showpage,data.pagecount,"pageDiv")		
			CloseDiv('hidAllDiv','fade');
		}
	});
}



function mypage(num,nummax,page1) {
	//选择器调用方式指定样式 style 可选big  和 small，可自定义首页尾页按钮的文本
	$("#pageDiv").mypage({
		now : num,
		max : nummax,
		callback : function(page) {
			showpage=page;
			pageshow(showpage);
		}
//		style : "small",
//		first : "First",
//		last : "Last"
	});
}

function updateshow(product_id){
	for (var a in jsondata) { 
		if(jsondata[a].product_id==product_id){
			$("#product_id").val(jsondata[a].product_id);
			$("#product_name").val(jsondata[a].product_name);
			$("#product_status").val(jsondata[a].product_status);
			$("#product_type").val(jsondata[a].product_type);
			$("#product_point").val(jsondata[a].product_point);
			$("#product_price").val(jsondata[a].product_price);
			$("#product_currency").val(jsondata[a].product_currency);
			$("#product_parameter").val(jsondata[a].product_parameter);
			$("#product_showname").val(jsondata[a].product_showname);
			
		}
	}
	ShowDiv('MyDiv','fade');
}

function save(){
	CloseDiv('MyDiv','fade');
	ShowDiv('hidAllDiv','fade');
	var json={
			product_id:$("#product_id").val(),
			product_name:$("#product_name").val(),
			product_status:$("#product_status").val(),
			product_type:$("#product_type").val(),
			product_point:$("#product_point").val(),
			product_price:$("#product_price").val(),
			product_currency:$("#product_currency").val(),
			product_parameter:$("#product_parameter").val(),
			product_showname:$("#product_showname").val(),
			sid:sid
	}
	//alert( JSON.stringify(json))
	$.ajax({
		type: 'post',
		url: '../m/saveProduct',
		data: {
			par: JSON.stringify(json)
		},
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			var b=false;
			if(data>0){
				b=true;
				alert('操作成功');
			}else if (data==-500){
				alert('物品名已存在！')
			}else if (data==-400){
				alert('物品名不允许修改！')
			}else{
				alert('操作失败')
			}
			
			
			if(b){
				mypage(showpage,data.pagecount,"pageDiv")		
				CloseDiv('hidAllDiv','fade');
				pageshow(showpage);
			}else{
				CloseDiv('hidAllDiv','fade');
				ShowDiv('MyDiv','fade');
			}
			
			
		}
	});
	
	
	
}



function rt(){
	$("#product_id").val('');
	document.getElementById('saveForm').reset(); 
}