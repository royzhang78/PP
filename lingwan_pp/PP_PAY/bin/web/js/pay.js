
function getPayMain(payid,serverurl){
	
	if(serverurl==''){
//		serverurl='http://58.96.176.236/pp1/api';
		serverurl='http://127.0.0.1:8080/pp/api';
	}
		
	var json = '{"payid": "'+payid+'"}';
	
	$.ajax({
		type: 'get', 
		url: serverurl,
		data: {
			serviceName: 'GetPayMain',
			callPara:json
		},
		dataType: 'jsonp',
		jsonp: "callback",
		success: function(data, textStatus, jqXHR) {
			try{
				if(data.returnCode==0){
					if(data.returnObjs.pay_status==5){
//						$("#showimg").attr("src","http://site.rodcell.com/pp/image/yes.png");
						$("#showimg").hide()
						$("#desc").html('success');
						window.clearInterval(timeID);
					}else if(data.returnObjs.pay_status==6){
//						$("#showimg").attr("src","http://site.rodcell.com/pp/image/no.jpg");
						$("#showimg").hide()
						$("#desc").html('error');
					}else{
					//	setTimeout(getPayMain(payid,serverurl),4000)
					}
					 
				}
			}catch(e){
				//setTimeout(getPayMain(payid,serverurl),4000)
			}
		}
	});
}