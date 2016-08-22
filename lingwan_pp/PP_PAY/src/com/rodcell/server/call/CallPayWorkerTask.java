package com.rodcell.server.call;

import java.util.List;

import org.apache.log4j.Logger;

import com.rodcell.client.MsgObject;
import com.rodcell.client.UdpClient;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.IDGenerate;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.thread.CallPayService;


public class CallPayWorkerTask extends Thread {
	private static Logger logger = Logger.getLogger(CallPayWorkerTask.class);
	private IDGenerate id=new IDGenerate();
	
	private PayMainRetryDao payMainRetryDao= (PayMainRetryDao)Constant.CTX.getBean("payMainRetryDao");
	
	/**
	 * 线程执行的频率 1秒查询
	 */
	private static final int step = 2000;
	@Override
	public void run(){
		long s=0;
		while(true){
			try {
				s = id.getId();//System.currentTimeMillis();

				int page=0;
				int count = payMainRetryDao.setExePostServerValue(Constant.serverkey, s);
				if(count>0){
					page=count%Constant.CALL_SERVER_THREAD_SIZE;
					if(page!=0){
						page=1;
					}
					page +=count/Constant.CALL_SERVER_THREAD_SIZE-1;
				//	List<PayMainRetry> paylist = payMainRetryDao.getExePostServerValue(Constant.serverkey, s);
					
					for (int i = 0; i <= page; i++) {
						List<PayMainRetry> paylist = payMainRetryDao.getExePostServerValue(Constant.serverkey, s,0+i,Constant.CALL_SERVER_THREAD_SIZE);
						for (PayMainRetry payMainRetry:paylist) {
//							CallPayServer.pool.execute(new CallPayService(payMainRetry));
//							CallPayServer.produce(payMainRetry);
							CallPayService c = new CallPayService(payMainRetry);
							c.exe();
							c=null;
						}
					}
					
					
				}
				long e=System.currentTimeMillis();
				if((e-s)<step)
					Thread.sleep((step-(e-s)));
			} catch (Exception e) {		
				MsgObject o=new MsgObject("", ErrorCode.ERROR_CALL_URL_301, e.getMessage(), "");
				try {
					UdpClient.produce(o);
				} catch (Exception e3) {
				}
				logger.error("", e);
			}finally{
				try {
					payMainRetryDao.closeupdateExePayMainRetry(Constant.serverkey, s);
				} catch (Exception e) {
					logger.error("", e);
				}
//				WORK_EXCE_TAG=false;
			}
			
			
		}
	}
	
	
	public static void main(String[] args) {
		
	}
	
	
	
}
