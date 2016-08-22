package com.tp.service.task;

import org.apache.log4j.Logger; 

public class UserLogoutTask extends Thread {

	private static final int step = 5000;
	private static  int count=1; 
	private static Logger logger = Logger.getLogger(UserLogoutTask.class);
	
	@Override
	public void run(){
		
		long s=System.currentTimeMillis();
		while(true){
			int i =0;
			try {
				
				
			} catch (Exception e) {		
				
			}finally{
				
			}
			
			long e=System.currentTimeMillis();
			if(i==0){
				try {
					Thread.sleep(step*count);
					count+=1;
					if(count>6){
						count=6;
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				count=1;
			}
		}
	}
	 
			 
}
