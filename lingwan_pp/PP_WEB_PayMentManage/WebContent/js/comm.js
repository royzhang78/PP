function resetform(formname){
	 document.getElementById(formname).reset();
}

function showstatus(status){
	if(status==1){
		return '创建'
	}else if(status==1){
		return '审核中'
	}else if(status==2){
		return '审核通过'
	}else if(status==3){
		return '下架'
	}
}