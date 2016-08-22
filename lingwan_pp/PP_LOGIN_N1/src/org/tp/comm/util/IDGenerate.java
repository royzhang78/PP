package org.tp.comm.util;

import java.io.IOException;

import jodd.log.Logger;
import jodd.log.LoggerFactory;
 

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月13日 上午10:15:17 
 * 类说明 
 */
public class IDGenerate {


	private static Logger log = LoggerFactory.getLogger(IDGenerate.class);
	/**
	 * 当前id
	 */
	public  static long Current=0;
	 
	
	public synchronized static  long getId(){
		long sid = DateTimeUtil.getCurrentTimeStamp();
//		long sid=Long.valueOf(DateTimeUtil.getTimeId());
		if(sid<=Current){
			Current++;
//			return Current;
		}else{
			Current=sid;
		}		
		return Current;
	}

	
	public static void main(String[] args) throws IOException {
		long s = System.currentTimeMillis();
		IDGenerate id = new IDGenerate();
		for (int i = 0; i < 2000000; i++) {
//			new Test(id).start();
			id.getId();
//			System.currentTimeMillis();
		}
		System.out.println((System.currentTimeMillis()-s));
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
				id1.getId();
//				log.info(id1.getId());
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
