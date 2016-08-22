package com.rodcell.service.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rodcell.client.MsgObject;
import com.rodcell.client.UdpClient;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.HttpClientUtil;
import com.rodcell.comm.util.IPUtil;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.RequestUtils;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.comm.util.XmlUtils;
import com.rodcell.dao.PayMainDao;
import com.rodcell.dao.PayMainRetryDao;
import com.rodcell.dao.PayServerChannelDao;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.PayMainRetry;
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
@Service(value="molCommSuccessService")
public class MolCommSuccessServiceImpl implements PayMessageService{

	private static Logger logger = Logger.getLogger(MolCommSuccessServiceImpl.class);
	
	 
	
	
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
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED)
	public Map service(long channelid, Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SException {
		Map returnMap = MapsUtil.newHashMap();
		
		par.put("channelId", channelid);
		par.put("isSandboxDB", "molCommService");//设定isSandboxDB 避免非正常沙箱模式
		  
		String  mol_merchantid=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_merchantid");
		String mol_secretpin=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_secretpin");	
		String  mol_verify_url=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_verify_url");
		String  mol_verify_url_test=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_verify_url_test");
		String  mol_check_url=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_check_url");
		String  mol_check_url_test=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_check_url_test"); 
		String  mol_back_url=sysPayStaticConfigDao.findPayStaticConfigbyKey("mol_back_url"); 
		
		String pay_id=MapsUtil.getString(par, "mref_id");
		String signature=MapsUtil.getString(par, "signature");
		String md5=DigestUtils.md5Hex(mol_merchantid+pay_id+MapsUtil.getString(par, "orderid")+MapsUtil.getString(par, "amount")+mol_secretpin);
		int amount = MapUtils.getIntValue(par, "amount");
		
		int returnstatus=0;
			
			PayMain main = payMainDao.findpayMainById(pay_id);
			if(main==null){
				try {
					Thread.sleep(150);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			logger.info("main==========="+main);
			if(main!=null&&main.getPay_status()<Constant.PAY_STATUS_4){
				
				if(main.getIsSandbox()==Constant.ISSANDBOX_1){
					mol_check_url=mol_check_url_test;
					mol_verify_url=mol_verify_url_test;
				}
				if(md5.toLowerCase().equals(signature.toLowerCase())){
				HttpClientUtil http= new HttpClientUtil();
				Map paraMap = new HashMap();
				String res=null;
				main.setStep2(JSONUtil.objectToString(par));
				
					try {
						logger.info("if 1"+(amount>=Integer.parseInt(main.getPay_money())));
						if(amount>=Integer.parseInt(main.getPay_money())){
							
							
							Map data = par;
							 
							logger.info("if 2"+data.get(MolCommServiceImpl.reason_code_String));
							if(data!=null && "000".equals(data.get(MolCommServiceImpl.reason_code_String))){//成功{
								main.setPay_status(Constant.PAY_STATUS_4);
								Date date = new Date();
								PayMainRetry retry=new PayMainRetry(pay_id, Constant.serverkey+"", date, 
											0,date, 0, null, null, null, 0);
								boolean b = payMainRetryDao.inserPayMainRetry(retry);
								logger.info("insert into retry"+b);
								returnMap.put("retry", retry);
								
							}else{
								main.setPay_status(Constant.PAY_STATUS_6);
								returnstatus=1;
							}
							paraMap.put("merchantid", mol_merchantid);
							
							paraMap.put("mref_id", pay_id);
							paraMap.put("signature", DigestUtils.md5Hex(mol_merchantid+pay_id+mol_secretpin));
							res = http.post(mol_check_url, paraMap, Constant.UTF8);
							main.setStep3(res);
						}else{
							returnstatus=1;
						}
	
					}  catch (Exception e) {
						logger.error("",e);
					}
				}else{
					main.setStep2(JSONUtil.objectToString(par));
				}
				returnMap.put("main", main);
					payMainDao.updatePayMain(main);
					
				
			}
			
//		}
			if(returnstatus==1){
				return null;
			}
		
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
