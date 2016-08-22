package com.rodcell.comm.util;

import java.io.IOException;

import org.apache.log4j.Logger;
 

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月13日 上午10:15:17 
 * 类说明 
 */
public class IDGenerate {


	public static Logger log = Logger.getLogger(IDGenerate.class);
	/**
	 * 当前id
	 */
	public  long Current=0;
	 
	
	public synchronized  long getId(){
		long sid = DateTimeUtil.getCurrentTimeStamp();
		if(sid<=Current){
			Current++;
//			return Current;
		}else{
			Current=sid;
		}		
		return Current;
	}

	
	public static void main(String[] args) throws IOException {
		IDGenerate id = new IDGenerate();
		for (int i = 0; i < 10000; i++) {
			new Test(id).start();
		}
		 System.in.read();
	}
	private static class Test extends Thread {
		IDGenerate id1 ;
		Test(IDGenerate id){
			id1=id;
		}
		@Override
		public void run() {
			while(true){
				
				log.info(id1.getId());
			}
			
			
		}

	}
	
//	public static void main(String[] args) {
//		System.out.println(System.currentTimeMillis());
//		IDGenerate id = new IDGenerate();
//		System.out.println(id.getId());
//		
//		for (int i = 0; i < 100000; i++) {
//			log.info(id.getId());
//		}
//
//	}
	

}
