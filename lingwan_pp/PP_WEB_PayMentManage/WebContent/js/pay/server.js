var  showpage=1;
var jsondata={}

$(document).ready(function() {
	pageshow(showpage);
	
});
/**
 * 查询所有物品信息
 * @param showpagenum
 */
function pageshow(showpagenum){
	
	ShowDiv('hidAllDiv','fade');
	var json = '{"showpage": "'+showpagenum+'"';
	if($("#find_server_name").val()!=null && $("#find_server_name").val()!=""){
		json+=', "s_name": "'+$("#find_server_name").val()+'"}';
	}else{
		json +='}';
	}

	
	mypage(0,1,"pageDiv");
	 
	$.ajax({
		type: 'post',
		url: '../m/findServer',
		data: {
			par: json
		},
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			
		
			$("#serverlist").empty();
			if(data.value.length>0){
				$("#serverTmpl").tmpl(data.value).appendTo("#serverlist");
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

function updateshow(s_id){
	
	for (var a in jsondata) { 
		if(jsondata[a].s_id==s_id){
			$("#s_id").val(jsondata[a].s_id);
			$("#s_name").val(jsondata[a].s_name);
			$("#s_ip").val(jsondata[a].s_ip);
			$("#s_port").val(jsondata[a].s_port);
			$("#s_call_payment_url").val(jsondata[a].s_call_payment_url);
			$("s_call_payment_type").val(jsondata[a].s_call_payment_type);
			$("#s_status").val(jsondata[a].s_status);
			$("#s_key").val(jsondata[a].s_key);
			$("#isSandbox").val(jsondata[a].isSandbox);
		}
	}
	
	ShowDiv('MyDiv','fade');
}

var channelSid="";
function updateChannelshow(sid){
	channelSid=sid;
	ShowDiv('hidAllDiv','fade');
	var json={
			s_id:sid
	}
	$.ajax({
		type: 'post',
		url: '../m/findServerChannel',
		data: {
			par: JSON.stringify(json)
		},
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			$("#serverChannelList").empty();
			if(data.PayChannelList.length>0){
				$("#serverChannelTmpl").tmpl(data.PayChannelList).appendTo("#serverChannelList");
				jsondata=data.value;
			}
			
			$('input[name="channel_id"]').each(function(){  
				var str = document.getElementById("range"+$(this).val()).value;
				if(str==null || ""==str)
					document.getElementById("range"+$(this).val()).value=1;
			});
			
			if(data.PayServerChannelList.length>0){
				$('input[name="channel_id"]').each(function(){  
					for(obj in data.PayServerChannelList){ 
						
					   if($(this).val()==data.PayServerChannelList[obj].c_id){
						   $(this).attr("checked",'true');//全选  
						   if(data.PayServerChannelList[obj].range!=null){
							   document.getElementById("range"+$(this).val()).value=data.PayServerChannelList[obj].range
						   }else{
							   document.getElementById("range"+$(this).val()).value=1
						   }
						   if(data.PayServerChannelList[obj].key!=null){
							   document.getElementById("key"+$(this).val()).value=data.PayServerChannelList[obj].key
						   }
					   }   
					   
					}
				});
				
				
				
			}
			
			
			CloseDiv('hidAllDiv','fade');
			ShowDiv('channelDiv','fade');
		}
	});
		
}


function saveServerChannel(){
	CloseDiv('channelDiv','fade');
	ShowDiv('hidAllDiv','fade'); 
	var chk_value =[];   
	var i=0;
	$('input[name="channel_id"]:checked').each(function(){  
		var o={
				cid:$(this).val(),
				range:document.getElementById("range"+$(this).val()).value,
				key:document.getElementById("key"+$(this).val()).value
		   };
		   chk_value[i]=o;
		   i++;
	});
	
	var json={
			s_id:channelSid,
			c_id:chk_value
	}
	//alert(JSON.stringify(json))
	$.ajax({
		type: 'post',
		url: '../m/updateServerChannel',
		data: {
			par: JSON.stringify(json)
		},
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			 if(data==0){
				 alert('操作成功')
			 }else{
				 alert('操作失败')
			 }
			CloseDiv('hidAllDiv','fade');
			
		}
	});
}

function save(){
	CloseDiv('MyDiv','fade');
	ShowDiv('hidAllDiv','fade');
	var json={
			s_id:$("#s_id").val(),
			s_name:$("#s_name").val(),
			s_ip:$("#s_ip").val(),
			s_port:$("#s_port").val(),
			s_call_payment_url:$("#s_call_payment_url").val(),
			s_call_payment_type:$("#s_call_payment_type").val(),
			s_status:$("#s_status").val(),
			s_key:$("#s_key").val(),
			isSandbox:$("#isSandbox").val()
	}
	//alert( JSON.stringify(json))
	$.ajax({
		type: 'post',
		url: '../m/saveServer',
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
				alert('服务器名已存在！')
			}else if (data==-400){
				alert('服务器名不允许修改！')
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
	$("#s_id").val('');
	document.getElementById('saveForm').reset(); 
}



function Sandboxshow(isSandbox){
	if(isSandbox==0)
		return '正式模式'
	else
		return '<font color="#FF0000">沙箱模式</font>'
		//return '沙箱模式'
}





function channelType(type){
	if(type==0)
		return 'SMS'
	else if(type==1)
		return 'HTTP'
	else if(type==2)
		return 'CASHCARD'
	else if(type==3)
		return '其他'
}


function updateProductshow(sid){
	parent.document.getElementById("home").src="../jsp/product.jsp?sid="+sid;
}


function rangeType(type){
	return type+"1"
}

