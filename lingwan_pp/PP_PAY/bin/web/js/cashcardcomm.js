function cashcardreturn(){
	try{		
		droid.toGAME();
	}catch(e){		
		try{
			com_rodcell_droid.com_rodcell_toGAME();
		}catch(e){
			try{
				history.go(-1);
			}catch(e){
				try{
					cashcardclose();
				}catch(e){				
				}
			}		
		}
	}
}


function cashcardclose(){
	try{		
		com_rodcell_droid.com_rodcell_toClose();
		//droid.toClose();
	}catch(e){		
		try{					
			droid.toClose();
		}catch(e){
			try{
				window.opener = null;//为了不出现提示框 
				window.close();//关闭窗口
			}catch(e){				
			}
		}
			
	}
}