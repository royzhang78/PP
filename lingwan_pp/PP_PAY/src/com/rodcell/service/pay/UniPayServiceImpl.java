package com.rodcell.service.pay;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MD5Util;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.dao.PayServerChannelDao;
import com.rodcell.dao.PayServerDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.service.UniPayService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * 适用客户端充值完毕后通知pp 支付系统
 * @version 创建时间：2014年6月6日 下午5:00:15 
 * 类说明 
 */
@Service(value="uniPayService")
public class UniPayServiceImpl  implements UniPayService{

	private static Logger logger = Logger.getLogger(UniPayServiceImpl.class);
	
	
	@Autowired
	private ApiService orderService;
	@Autowired
	private SysPayStaticConfigDao sysPayStaticConfigDao;
	
	@Autowired
	private PayServerChannelDao payServerChannelDao;
	

	@Autowired
	private PayServerDao payServerDao;
	

	@Autowired
	private PayMainDao payMainDao;
	

	@Autowired
	private PayMainRetryDao payMainRetryDao;
	
 
	
	
//	public Map service(long channelid, Map par, HttpServletRequest request,
//			HttpServletResponse response, HttpSession session) throws SException {
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED)
	public void page(Map par ,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SException{
		 
		par.put("channelId", "54");
		par.put("currency", "Chips");
		ReturnObj obj = (ReturnObj)orderService.service(par, request, response, session);//创建订单
		PayMain payMain =(PayMain)obj.getReturnObjs();
		if(obj!=null && obj.getReturnCode().equals(ErrorCode.NO_ERROR)){	
			String md5String ="";
			String UniPayGuid = sysPayStaticConfigDao.findPayStaticConfigbyKey("UniPay_Guid");
			String reference=payMain.getPay_id();
			String message= sysPayStaticConfigDao.findPayStaticConfigbyKey("UniPay_message");
			String urlAck= sysPayStaticConfigDao.findPayStaticConfigbyKey("UniPay_urlAck")+reference;
			String urlReturn= sysPayStaticConfigDao.findPayStaticConfigbyKey("CashCardResponseUrl")+reference;
			String szSecretKey = sysPayStaticConfigDao.findPayStaticConfigbyKey("UniPay_szSecretKey");
			
			String unipay_url = sysPayStaticConfigDao.findPayStaticConfigbyKey("unipay_url");
			String unipay_url_test = sysPayStaticConfigDao.findPayStaticConfigbyKey("unipay_url_test");
			
			
			String amount = payMain.getPay_money();
			String description = amount+" "+payMain.getCurrency();
			
			md5String+=UniPayGuid+reference+message+urlAck+urlReturn+amount+description+szSecretKey;
			md5String =MD5Util.getMD5String(md5String);
			String url="";
			if(payMain.getIsSandbox()==Constant.ISSANDBOX_0){
				url = unipay_url;
			}else{
				url = unipay_url_test;
			}
//			String s = HttpClientUtil.doPostWithObject(url, obj);
			Map m1=new HashMap();
			m1.put("guid", UniPayGuid);
			m1.put("reference", reference);
			m1.put("message", message);
			m1.put("urlAck", urlAck);
			m1.put("urlReturn", urlReturn);
			
			
			 
			
			
			String json="{"
					+ "\"merchant\":{"
						+"\"guid\":\""+UniPayGuid+"\","
						+"\"reference\":\""+reference+"\","
						+"\"message\":\""+message+"\","
						+"\"urlAck\":\""+urlAck+"\","
						+"\"urlReturn\":\""+urlReturn+"\""
					+ "},"
						
					+ "\"denominations\":[{"
						+"\"amount\":"+amount+","
						+"\"description\":\""+description+"\""
					+ "}],"
						
					+ "\"signature\":\""+md5String+"\""
					+ "}";
			
			logger.info("send json:"+json);
			HttpResponse response1 = HttpRequest
			        .post(url)
			        .body(json)
//			        .basicAuthentication("test", "test")
			        .send();
			String s =response1.bodyText();
			
			logger.info("response1:"+s);
			payMain.setStep1(s);
			payMainDao.updatePayMain(payMain);
			
			if(!StringUtil.isNullOrEmpty(s)){
				Map map = JSONUtil.JsonToMap(s);
				int status = MapUtils.getIntValue(map, "status");
				if(status==0){
					String callurl=MapUtils.getString(map, "url");
					try {
						logger.info("callurl:"+callurl);
						response.sendRedirect(callurl);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			try {
				response.sendRedirect(urlReturn+"?resp_desc=UniPay Server error!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}




	@Override
	public String notification(long orderId, Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {

		
		PayMain payMain = payMainDao.findpayMainById(orderId+"");
		 
		if(payMain!=null && payMain.getPay_status()<=Constant.PAY_STATUS_1 && payMain.getPay_channel_type()==54){//订单存在并且状态为创建状态
			payMain.setStep2(JSONUtil.objectToString(par));
			
			Map transaction = (Map)par.get("transaction");
			if(orderId==MapUtils.getLongValue(transaction, "reference")){
				if(ErrorCode.NO_ERROR.equals(MapUtils.getString(transaction, "status"))){
					payMain.setUnique_key(MapUtils.getString(transaction, "trxNo")); 
					payMain.setPay_status(Constant.PAY_STATUS_4);
					
					payMainDao.updatePayMain(payMain);
					
					Date date = new Date();
					PayMainRetry retry=new PayMainRetry(payMain.getPay_id(), Constant.serverkey+"", date, 
								0,date, 0, null, null, null, 0);
					payMainRetryDao.inserPayMainRetry(retry);
					
					String s="{\"status\":0,\"message\"	:\"Reload Successful.\"}";
					return s;
				}
			}
			
		}

		payMainDao.updatePayMain(payMain);
		return null;
	}
	
	

}
