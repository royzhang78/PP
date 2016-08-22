var showpage=1;
var jsondata={};
var PayChannelList={}
$(document).ready(function() {
	pageshow(showpage);
	
});

function pageshow(showpagenum){
	
	ShowDiv('hidAllDiv','fade');
	var json={
			showpage:showpagenum
	}
	
	var channel_type=$("#find_channel_type").val();
	var currency=$("#find_currency_type").val();
	if(channel_type!=null && ""!=channel_type){
		json.channel_type=channel_type;
	}
	if(currency!=null && ""!=currency){
		json.currency=currency;
	}
	
	mypage(0,1,"pageDiv");
	 
	$.ajax({
		type: 'post',
		url: '../m/findconfigChannelPrice',
		data: {
			par: JSON.stringify(json)
		},
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {			
			$("#channelPricelist").empty();
			if(data.value.ChannelPrice.length>0){
				PayChannelList=data.value.PayChannelList;
				defchannelselect()
				
				$("#channelPriceTmpl").tmpl(data.value.ChannelPrice).appendTo("#channelPricelist");
				jsondata=data.value.ChannelPrice;
				
			}
			mypage(showpage,data.pagecount,"pageDiv")		
			CloseDiv('hidAllDiv','fade');
		}
	});
}

function addkey(obj){
	var o=obj;
	if(o==null){
		o={
				key:'',
				val:''
		}
	}
	$("#addKEYTmpl").tmpl(o).appendTo("#addKEY");
	
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


function updateshow(id){
	rt();
	for (var a in jsondata) { 
		if(jsondata[a].id==id){
			
			$("#id").val(jsondata[a].id);
			$("#pricepoin").val(jsondata[a].pricepoin);
			$("#channel_type").val(jsondata[a].channel_type);
			$("#currency").val(jsondata[a].currency);
			$("#type").val(jsondata[a].type);
			$("#status").val(jsondata[a].status);
			
			var dataset = $.parseJSON(jsondata[a].other_param);
			for (int in dataset) {
				
				$("#addKEYTmpl").tmpl(dataset[int]).appendTo("#addKEY");
			}
			
			break;
		}
	}
	ShowDiv('MyDiv','fade');
}



function save(){
	if($("#pricepoin").val()==null || ""==$("#pricepoin").val()){
		alert('请设定价格');
		return;
	}
	if($("#channel_type").val()==null || ""==$("#channel_type").val()){
		alert('请设定支付渠道');
		return;
	}
	CloseDiv('MyDiv','fade');
	ShowDiv('hidAllDiv','fade');
	var parjson=[];
	var i=0;
	//,returnValue:document.getElementsByName("returnValue")[i].value
	//alert(document.getElementsByName("returnValue")[0].value)
	$('input[name="returnKey"]').each(function(){    
		   var s=$(this).val(); 
		  if(s!=null && ""!=s){
			  var obj={key:s,val:document.getElementsByName("returnValue")[i].value};
			  
			  parjson[i]=obj;
		  }
		   i++;
	});
	//alert( JSON.stringify(parjson))
	var json={
			id:$("#id").val(),
			pricepoin:$("#pricepoin").val(),
			channel_type:$("#channel_type").val(),
			currency:$("#currency").val(),
			type:$("#type").val(),
			other_param:JSON.stringify(parjson),
			status:$("#status").val()
	}
	//alert( JSON.stringify(json))
	$.ajax({
		type: 'post',
		url: '../m/saveChannelPrice',
		data: {
			par: JSON.stringify(json)
		},
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			var b=false;
			if(data>0){
				b=true;
				alert('操作成功');
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
	$("#id").val('');
	$("#addKEY").empty();
	document.getElementById('saveForm').reset(); 
}


function priceType(type){
	if(type==0)
		return '完全匹配'
	else if(type==1)
		return '正则匹配'
	else
		return '其他'
}


function defchannelselect(){
	$("#channel_type").empty();
	$("#find_channel_type").empty();
	$("#find_channel_type").append("<option value=''>all</option>"); 
	for (int in PayChannelList) {
		var str="<option value='"+PayChannelList[int].channel_id+"'>"+PayChannelList[int].channel_name+"("+PayChannelList[int].channel_id+")" +"</option>";
		$("#channel_type").append(str); 
		$("#find_channel_type").append(str); 
	}
	
}

function channelType(type){
	
	for (int in PayChannelList) {
		
		if(PayChannelList[int].channel_id==type){
			return PayChannelList[int].channel_name+'('+type+') '
		}
	}
}