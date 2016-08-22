package com.rodcell.service.pay;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.rodcell.dao.PayServerChannelDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.service.PayMessageService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月6日 下午5:00:15 
 * 类说明 
 */
@Service(value="molCommService")
public class MolCommServiceImpl implements PayMessageService{

	private static Logger logger = Logger.getLogger(MolCommServiceImpl.class);
	
	public static String reason_code_String="reason_code";
	
	public static Map reasondesc=new HashMap();
	{
		reasondesc.put("000", "Success");
		reasondesc.put("101", "Card or Serial was used");
		
		reasondesc.put("100", "Invalid PIN");
		reasondesc.put("105", "Invalid Input");
		reasondesc.put("104", "Invalid Merchant ID");
		reasondesc.put("107", "Serial not found");
		reasondesc.put("108", "Payment channel not found");
		reasondesc.put("900", "System under maintenance");
		reasondesc.put("999", "Internal error");
	}
	
	
	@Autowired
	private ApiService orderService;
	@Autowired
	private SysPayStaticConfigDao sysPayStaticConfigDao;
	
	@Autowired
	private PayServerChannelDao payServerChannelDao;
	
	
	@Autowired
	private PayMainDao payMainDao;
	
	@Autowired
	private PayMainRetryDao payMainRetryDao;
	
	@Override
//	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED)
	public Map service(long channelid, Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SException {
		Map returnMap = MapsUtil.newHashMap();
		
		par.put("channelId", channelid);
		par.put("isSandboxDB", "molCommService");//设定isSandboxDB 避免非正常沙箱模式
		ReturnObj obj = (ReturnObj)orderService.service(par, request, response, session);
		
		ReturnObj robj = obj;
		if(robj.getReturnCode().equals(ErrorCode.NO_ERROR)){
			
			PayMain payMain =(PayMain)obj.getReturnObjs();
			returnMap.put("pay_id", payMain.getPay_id());
	
			String  mol_merchantid=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_merchantid");
			String mol_secretpin=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_secretpin");	
			String  mol_verify_url=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_verify_url");
			String  mol_verify_url_test=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_verify_url_test");
			String  mol_check_url=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_check_url");
			String  mol_check_url_test=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_check_url_test"); 
			String  mol_back_url=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_back_url"); 
			 
			if(payMain.getIsSandbox()==Constant.ISSANDBOX_1){
				mol_check_url=mol_check_url_test;
				mol_verify_url=mol_verify_url_test;
			}
		
//			OrderService order = (OrderService)Constant.CTX.getBean("orderService");
		
			PayMain main = (PayMain)robj.getReturnObjs();
			
			
			
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("merchantid", mol_merchantid);
			Map datachannel = payServerChannelDao.fingServerChannel(channelid+"", main.getS_id()+"");	
			 
			if(datachannel==null|| StringUtil.isNullOrEmpty(MapsUtil.getString(datachannel, "key"))){
				returnMap.put("resp_desc", "gameid config is null");
				return returnMap;
			}
			paraMap.put("gameid", MapsUtil.getString(datachannel, "key"));
			paraMap.put("cus_id", main.getSource()+"");
//			paraMap.put("email", "");
			// MOL amount is integer
			paraMap.put("mref_id", payMain.getPay_id());
			
			paraMap.put("signature", DigestUtils.md5Hex(mol_merchantid+payMain.getPay_id()+mol_secretpin));
			paraMap.put("pin_no", (String)par.get("pin_no"));
			paraMap.put("serial_no", (String)par.get("serial_no"));
			paraMap.put("channel", (String)par.get("channe"));
			
			//Parameters {Description} must be more than 5 characters.
//			paraMap.put("customer_ip",RequestUtils.getRemoteAddr(request));
			paraMap.put("back_url", mol_back_url);
			logger.info("params:" + JSONUtil.objectToString(paraMap));
			
			HttpClientUtil http= new HttpClientUtil();
			String res=null;
			try {
//				res = http.post(mol_verify_url, paraMap, Constant.UTF8);
				Iterator<String> iterator =paraMap.keySet().iterator();
				while(iterator.hasNext()){
					String key = iterator.next();
					mol_verify_url+="&"+key+"="+paraMap.get(key);
				}
				res = http.get(mol_verify_url,  Constant.UTF8);
				logger.info("Mol mol_check_url url="+mol_verify_url+" response:" + res);				 
				
				 
				par = JSONUtil.JsonToMap(res);
				if(res==null){
					main.setStep1(res);
					main.setPay_status(Constant.PAY_STATUS_6);
					if(par!=null){
						main.setFail_code((String)par.get(reason_code_String));
					}
					returnMap.put("resp_desc", "time out");
				}else if("000".equals(par.get(reason_code_String))){//成功
					returnMap.put("resp_desc", reasondesc.get(par.get(reason_code_String)));
					main.setStep1(res);
					
					int amount =MapsUtil.getInteger(par, "amount");
					boolean b=false;
					if(amount>=Integer.parseInt(main.getPay_money())){
						b=true;
					}
					
					if(b){
						
							returnMap.put("resp_desc", reasondesc.get(par.get(reason_code_String)));
							main.setStep2(res);						
							main.setPay_status(Constant.PAY_STATUS_2);
							
//							Date date = new Date();
//							PayMainRetry retry=new PayMainRetry(payMain.getPay_id(), Constant.serverkey+"", date, 
//										0,date, 0, null, null, null, 0);
//							payMainRetryDao.inserPayMainRetry(retry);
							
						
					}else{
						main.setStep1(res);
						main.setPay_status(Constant.PAY_STATUS_6);
						if(par!=null){
							main.setFail_code("amount Insufficient");
						}
					}
				}else {
					main.setStep1(res);
					main.setPay_status(Constant.PAY_STATUS_6);
					if(par!=null){
						main.setFail_code((String)par.get(reason_code_String));
					}
					returnMap.put("resp_desc", reasondesc.get(par.get(reason_code_String)));
				}
				
//				payMainDao.updatePayMain(main);
//				if(main.getIsSandbox()==Constant.ISSANDBOX_0){//非沙箱模式不提供描述信息
//					returnMap.put("resp_desc", "");
//				}
//				return returnMap;
			} catch (Exception e) {
				returnMap.put("resp_desc", "Payment failed ! "+e.getMessage());
				
				MsgObject o=new MsgObject("", ErrorCode.ERROR_500, e.getMessage(), par);
				try {
					UdpClient.produce(o);
				} catch (Exception e1) {
				}
				logger.error("", e);
				try{
					main =payMainDao.findpayMainById(main.getPay_id());
					if(res.length()>2000){
						res=res.substring(0, 1999);
					}
					main.setStep1(res);
					main.setPay_status(Constant.PAY_STATUS_2);
					
					payMainDao.updatePayMain(main);
					
//					if(main.getIsSandbox()==Constant.ISSANDBOX_0){//非沙箱模式不提供描述信息
//						returnMap.put("resp_desc", "");
//					}
				} catch (Exception e1) {
				}
			}
		}
		
		
		returnMap.put("ReturnObj", obj);
		return returnMap;
	}
	
	
	
	public static String signContent(Map<String, String> params, String privateKey) {
		List<String> keys = new ArrayList<String>(params.keySet());
		// sort is requiered by mol
		Collections.sort(keys);
		String prestr = "";
		for (String key : keys) {
			String value = (String) params.get(key);
			if (value == null || value.trim().isEmpty()) {
				continue;
			}
			prestr += value;
		}
		logger.info("prestr:" + prestr);
		return DigestUtils.md5Hex(getContentBytes(prestr + privateKey));
	}
	
	
	public static byte[] getContentBytes(String content) {
		try {
			return content.getBytes(Constant.UTF8);
		} catch (UnsupportedEncodingException e) {
			logger.error(e, e);
		}
		return content.getBytes();
	}

	
	public static void main(String[] args) {
		Map json = JSONUtil.JsonToMap("{\"paymentId\":\"MPO118552\",\"referenceId\":\"101407814231224\",\"paymentUrl\":\"http://molv4.molsolutions.com/PaymentchannelSandbox/PayoutAPICall.aspx?token=qipDtsNLDSKKu7DDx%2f9y2EzgTky%2bB6mvyO3LgBRlct4%2fNq0bNxHN4Icn2brPVwQMKnqgGheXqsHDv4ggydjKDs0XJi7ZRmbZW67OI2tdw1xSabvbke00y2bdsS1rywSSjrDiUgM4PxEKqS0J9LvHEiUiV9TzCiMO40QDTyi2s%2bYwww0pVzya6oQh4hob%2fYRy2wHqjGdhp4BX8%2fOJFkzPqs2N42hqKS6RMbMB1WsmbgeFBaU%2bL263Vt7%2fe6MdYJv%2b\",\"amount\":5,\"currencyCode\":\"THB\",\"version\":\"v1\",\"signature\":\"9de45bdb9d7a5b448c098c5ebc49eef5\",\"applicationCode\":\"RhUUiXzP7mO2040plTsXlCZPC6eEOm6b\"}");
	}
}
