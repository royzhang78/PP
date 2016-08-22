package com.rodcell.service.thread;

import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.client.MsgObject;
import com.rodcell.client.UdpClient;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.HttpClientUtil;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.dao.PayProductDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.entity.PayProduct;
import com.rodcell.entity.PayServer;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月23日 下午3:03:01 
 * 类说明 
 */
public class CallPayService {

	private static Logger logger = Logger.getLogger(CallPayService.class);
	
	private String threadName;

	private PayMainRetry payMainRetry;
	
	public static final String UTF8="utf-8";
	
	  
	public CallPayService(){
		
	}
	public CallPayService(PayMainRetry payMainRetry) {
		super();
		this.payMainRetry = payMainRetry;
	}

	/**
	 * 线程执行的频率 1秒查询
	 */
	private static final int step = 10;
//	@Override
//	public void run(){
//		
//		while(true){
//			long s=System.currentTimeMillis();
//			try {
//				payMainRetry=CallPayServer.consume();
//				exe();
//				logger.info("run name="+threadName);
//			} catch (Exception e) {				 
//				logger.error("", e);
//			}finally{
////				try {
////					this.wait();
////				} catch (InterruptedException e) {
////					logger.error("", e);
////				}
//			}
//			
//			long e=System.currentTimeMillis();
//			if((e-s)<step)
//				try {
//					Thread.sleep((step-(e-s)));
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//		}
//	}
	
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED) 
	public void exe() throws SException{

		PayMainDao dao =(PayMainDao)Constant.CTX.getBean("payMainDao");
		
		PayServerDao payServerDao =(PayServerDao)Constant.CTX.getBean("payServerDao");
		PayProductDao payProductDao =(PayProductDao)Constant.CTX.getBean("payProductDao");
		
		PayMain main = dao.findpayMainById(payMainRetry.getPay_id());
		PayServer  ps = payServerDao.findPayServerById(main.getS_id()+"");
		if(ps.getS_call_payment_type()==Constant.CALL_SERVER_TYPE0){
			Map map = JSONUtil.objToMap(main);
			
			Map rmap = MapsUtil.newHashMap();
			
			long timestamp = System.currentTimeMillis();
			String md5 = DigestUtils.md5Hex(main.getPay_id()+timestamp
					+ main.getUid()
					+ (ps.getS_key()==null?"":ps.getS_key())).toUpperCase();
		
			PayProduct p =payProductDao.findPayProductById(main.getProduct_id()+"");
			
			rmap.put("orderId", main.getPay_id());
			rmap.put("amount", main.getServer_order_info());
			rmap.put("productType", main.getProductType());
			rmap.put("payType", main.getPay_channel_type());
			rmap.put("gameRoleId", main.getUid());
			rmap.put("timestamp", timestamp);
			rmap.put("verify", md5);
			rmap.put("currency", main.getCurrency());
			rmap.put("payMoney", main.getPay_money());
			rmap.put("cparam", main.getCparam());
			if(p!=null)
				rmap.put("productName", p.getProduct_name());
			rmap.put("ServerCode", main.getServerCode());
			rmap.put("adType", main.getAdType());
			
			Map value = MapsUtil.newHashMap();
			value.put("serviceName", "payGem");
			value.put("callPara", rmap);
			payMainRetry.setStatus(0);
			String call_url=ps.getS_call_payment_url();
			if(main.getServerCode()!=null&&!"".equals(main.getServerCode())){
				call_url=payServerDao.findPayServerByDetailUrl(main.getS_id()+"", main.getServerCode(),main.getIsSandbox()+"");
			}
			if(call_url==null || "".equals(call_url)){
				call_url=ps.getS_call_payment_url();
			}
			
			String rs = HttpClientUtil.doPostWithObject(call_url, JSONUtil.objectToString(value));
			main.setStep4(rs);
			int retCode = 0;
			if(rs==null || "".equals(rs)){
				
				rmap.put("url", ps.getS_call_payment_url());
				rmap.put("rs", rs);
				MsgObject o=new MsgObject("", ErrorCode.ERROR_CALL_URL_300, "", rmap);
				try {
					UdpClient.produce(o);
				} catch (Exception e1) {
				}
				
				retCode=-1;
			}else{
				try{
					Map m =JSONUtil.JsonToMap(rs);
					logger.info("rs="+m);
					retCode = MapsUtil.getInteger(m, "result");
				}catch(Exception e){
					
					try{
						rs=rs.substring(1, rs.length()-1);
						rs= StringUtil.replaceAll(rs, "\\\\", "");
						logger.info("rs1="+rs);
						Map m =JSONUtil.JsonToMap(rs);
						retCode = MapsUtil.getInteger(m, "result");
					}catch(Exception e1){
						
						try{
							if (rs != null) {
								rs = rs.replaceAll("\\n", "");
//								JSONObject retJson = JSONObject.fromObject(rs.trim());
								Map m =JSONUtil.JsonToMap(rs);
								if (m != null && m.containsKey("result")) {
									retCode = Integer.parseInt(String.valueOf(m.get("result")));
								}
							}
						}catch(Exception e2){
							
							rmap.put("url", ps.getS_call_payment_url());
							rmap.put("rs", rs);
							MsgObject o=new MsgObject("", ErrorCode.ERROR_CALL_URL_300, e2.getMessage(), rmap);
							try {
								UdpClient.produce(o);
							} catch (Exception e3) {
							}
							retCode=-99999;
						}
						
					}
				}
				
			}
			int n=0;
			if(retCode==0){//成功
				main.setSucccess_pay_date(new Date());
				main.setPay_status(Constant.PAY_STATUS_5);
				dao.updatePayMain(main);
				n=1;
				
				PayMainRetryDao payMainRetryDao = (PayMainRetryDao)Constant.CTX.getBean("payMainRetryDao");
				payMainRetryDao.deletePayMainRetry(payMainRetry);
				return;
			}else if(retCode>0){//失败，不需要重试，返回码由GS自定义。
				main.setPay_status(Constant.PAY_STATUS_6);
				main.setFail_code(retCode+"");
				n=1;
			}else if(retCode<0){//失败，需要重试，返回码由GS自定义。
				if(payMainRetry.getCall_server_count()<=5){//回调不超过5次
					Date date=null;
					if(payMainRetry.getCall_server_count()==1){
						date = new Date(System.currentTimeMillis()+1000*60*2*payMainRetry.getCall_server_count());
					}
					if(payMainRetry.getCall_server_count()==2){
						date = new Date(System.currentTimeMillis()+1000*60*30*payMainRetry.getCall_server_count());
					}
					if(payMainRetry.getCall_server_count()==3){
						date = new Date(System.currentTimeMillis()+1000*60*120*payMainRetry.getCall_server_count());
					}
					if(payMainRetry.getCall_server_count()==4){
						date = new Date(System.currentTimeMillis()+1000*60*60*6*payMainRetry.getCall_server_count());
					}
					if(payMainRetry.getCall_server_count()==5){
						date = new Date(System.currentTimeMillis()+1000*60*60*12*payMainRetry.getCall_server_count());
					}
					payMainRetry.setNext_time(date);
	//				payMainRetry.setCall_server_count(payMainRetry.getCall_server_count()+1);
					main.setCall_server_count(payMainRetry.getCall_server_count());
					PayMainRetryDao payMainRetryDao = (PayMainRetryDao)Constant.CTX.getBean("payMainRetryDao");
					payMainRetryDao.updatePayMainRetry(payMainRetry);
				}else{
					main.setPay_status(Constant.PAY_STATUS_6);
					main.setFail_code(retCode+"");
					main.setCall_server_count(payMainRetry.getCall_server_count());	
					n=1;
				}
			}
			PayMainDao payMainDao = (PayMainDao)Constant.CTX.getBean("payMainDao"); 
			payMainDao.updatePayMain(main);
			if(n==1){
				PayMainRetryDao payMainRetryDao = (PayMainRetryDao)Constant.CTX.getBean("payMainRetryDao");
				payMainRetryDao.deletePayMainRetry(payMainRetry);
			}
		}else if(ps.getS_call_payment_type()==Constant.CALL_SERVER_TYPE1){
			main.setPay_status(Constant.PAY_STATUS_5);
			PayMainDao payMainDao = (PayMainDao)Constant.CTX.getBean("payMainDao"); 
			payMainDao.updatePayMain(main);
			PayMainRetryDao payMainRetryDao = (PayMainRetryDao)Constant.CTX.getBean("payMainRetryDao");
			payMainRetryDao.deletePayMainRetry(payMainRetry);
		}
	}
	
	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}


	public PayMainRetry getPayMainRetry() {
		return payMainRetry;
	}


	public void setPayMainRetry(PayMainRetry payMainRetry) {
		this.payMainRetry = payMainRetry;
	}
	
	
	
	
	
}
