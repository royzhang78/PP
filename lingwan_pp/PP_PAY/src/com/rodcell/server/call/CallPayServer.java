package com.rodcell.server.call;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.rodcell.client.UdpClient;
import com.rodcell.comm.Constant;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.server.http.Server;
import com.rodcell.service.thread.CallPayService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月23日 下午2:43:53 
 * 类说明  回调支付服务器
 */
public class CallPayServer {
	

	public static Logger log = Logger.getLogger(CallPayServer.class);
 
	public static ThreadPoolExecutor pool=null;
	
	public static BlockingQueue<PayMainRetry> basket = new LinkedBlockingQueue<PayMainRetry>();
	
	
	
    public static void produce(PayMainRetry retry) throws InterruptedException {
       
        basket.put(retry);
    }

   
    public static PayMainRetry consume() throws InterruptedException {
        // take方法取出一个，若basket为空，等到basket有苹果为止(获取并移除此队列的头部)
        return basket.take();
    }
    
	   
	/**
	 * 加载spring文件
	 */
	private static void initSpring() {
		Constant.CTX = new FileSystemXmlApplicationContext(
				Constant.APP_ROOT_PATH + "spring/spring.xml");
	}
	
	
	public static void main(String[] args) throws Exception {
		Constant.APP_ROOT_PATH = Server.class.getProtectionDomain()
				.getCodeSource().getLocation().getFile();
		if (Constant.APP_ROOT_PATH.startsWith("/"))
			Constant.APP_ROOT_PATH = "/" + Constant.APP_ROOT_PATH;
		
		Server.loadMainParm();		
		CallPayServer.initSpring();
		
		PayMainRetryDao payMainRetryDao= (PayMainRetryDao)Constant.CTX.getBean("payMainRetryDao");
		payMainRetryDao.initupdateExePayMainRetry(Constant.serverkey);
		
		
		new UdpClient().start();
		
		for (int i = 0; i < 3; i++) {
			CallPayWorkerTask t = new CallPayWorkerTask();
			t.start();
		}
		
		
		int min=10;
		pool = new  ThreadPoolExecutor(min ,  Constant.CALL_SERVER_THREAD_SIZE<min?min:Constant.CALL_SERVER_THREAD_SIZE ,  
				5000 , TimeUnit.MILLISECONDS,   
	            new  ArrayBlockingQueue<Runnable>(3000));
		
		
		log.info("The CallPayServer is running. Press enter to stop...");
		System.in.read();
		while (true) {
			Thread.sleep(100000);
		}
	}

}
