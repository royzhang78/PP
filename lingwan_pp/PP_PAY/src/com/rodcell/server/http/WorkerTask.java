package com.rodcell.server.http;

import java.util.Date;

import org.apache.log4j.Logger;


public class WorkerTask extends Thread {
	private static Logger logger = Logger.getLogger(WorkerTask.class);
	
	private static boolean WORK_EXCE_TAG=false;
	
	
	/**
	 * 线程执行的频率
	 */
	private int step = 5;
	@Override
	public void run(){
		
		while(true){
			try {
				long s = new Date().getTime();

				boolean sleepFlag = false;
				long sleep=step;
				sleepFlag = true;
//				if(!Constant.GET_TASK_TAG&&!Constant.SERVER_SHUTDOWN){//当发送停服命令后线程将不在工作
//					MQmessage mq = new MQmessage();
//					if(Util.addOfGetTask(null,mq).size()>0){
//						Constant.WORK_EXCE_TAG=true;
//						TaskObject t =Util.addOfGetTask(null,mq).get(0);
//						long runtime = t.getTasktime();
//						
//						long nowtime = new Date().getTime();
//						long i = (runtime-nowtime);
//						if(i<=0){
//							TaskObject o = Util.getTaskObject(t.getKey(),mq);
//							APPService service =  (APPService) Constant.CTX.getBean("servletService");
//							Map par = o.getParMap();
//							par.put("MQmessage", mq);
//							
//							/****/
//							//调用service
//							try{
////								
//								service.exc(par,null);
//								long e = new Date().getTime();
//								long temp = sleep-(e-s);
//								if(temp>=0){
//									sleep=temp;
//								}
//								sleep =sleep-(e-s);
//							}catch (Exception e) {
//								logger.error("", e);
//							}
//							
//						}else{
//							sleepFlag=true;
//						}
//					}else{
//						
//						sleepFlag=true;
//					}
//			
//				}
				if(sleepFlag)
					Thread.sleep(sleep);
			} catch (Exception e) {				 
				logger.error("", e);
			}finally{
//				WORK_EXCE_TAG=false;
			}
			

			
		}
	}
}
