package com.rodcell.service.pay;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.HttpClientUtil;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
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
@Service(value="molGameshineService")
public class MolGameshineImpl implements PayMessageService{

	private static Logger logger = Logger.getLogger(MolGameshineImpl.class);
	
	
	@Autowired
	private ApiService orderService;
	@Autowired
	private SysPayStaticConfigDao sysPayStaticConfigDao;
	
	
	
	@Override
	public Map service(long channelid, Map par, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws SException {
		Map returnMap = MapsUtil.newHashMap();
		
		par.put("isSandboxDB", "molGameshineService");
		ReturnObj obj = (ReturnObj)orderService.service(par, request, response, session);
		
		ReturnObj robj = obj;
		if(robj.getReturnCode().equals(ErrorCode.NO_ERROR)){
			
			PayMain payMain =(PayMain)obj.getReturnObjs();
			
	
			String  molReturnURL=sysPayStaticConfigDao.findPayStaticConfigbyKey("CashCardResponseUrl");
			String molURL=sysPayStaticConfigDao.findPayStaticConfigbyKey("molURL_"+channelid);	
			String  molSecretKey=sysPayStaticConfigDao.findPayStaticConfigbyKey("molSecretKey_"+channelid);
			String  molAppCode=sysPayStaticConfigDao.findPayStaticConfigbyKey("molAppCode_"+channelid);
			String  molVersion=sysPayStaticConfigDao.findPayStaticConfigbyKey("molVersion_"+channelid);
			String  molChannelID=sysPayStaticConfigDao.findPayStaticConfigbyKey("molChannelID_"+channelid);
			String  moltestURL=sysPayStaticConfigDao.findPayStaticConfigbyKey("moltestURL_"+channelid);
			
			if(payMain.getIsSandbox()==Constant.ISSANDBOX_1){
				molURL=moltestURL;
			}
		
//			OrderService order = (OrderService)Constant.CTX.getBean("orderService");
		
			PayMain main = (PayMain)robj.getReturnObjs();
			
			String returnUrl = molReturnURL+payMain.getPay_id();
//			if (returnUrl.indexOf('?') > 0) {
//				returnUrl += "&payId=" + payMain.getPay_id();
//			} else {
//				returnUrl += "?payId=" + payMain.getPay_id();
//			}
			
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("applicationCode", molAppCode);
			paraMap.put("referenceId", payMain.getPay_id());
			paraMap.put("version", molVersion);
			paraMap.put("channelId", molChannelID);
			// MOL amount is integer
			paraMap.put("amount", (Integer.parseInt(payMain.getPay_money())*100)+"");
			
			paraMap.put("currencyCode", payMain.getCurrency().toUpperCase());
			paraMap.put("returnUrl", returnUrl);
			//Parameters {Description} must be more than 5 characters.
			paraMap.put("description","");
			paraMap.put("customerId", main.getUid());
			String md5 = signContent(paraMap, molSecretKey);
			paraMap.put("signature", md5);
			logger.info("params:" + JSONUtil.objectToString(paraMap));
			
			HttpClientUtil http= new HttpClientUtil();
			String res=null;
			try {
				res = http.post(molURL, paraMap, Constant.UTF8);
				logger.info("Mol return response:" + res);
				
//				createResponse="{\"paymentId\":\""+payMain.getPayId()+"\" ,\"paymentUrl\":\"http://www.baidu.com\"}";
				
				if (res == null) {
					returnMap.put(Constant.responseStatus, ErrorCode.ERROR_500);
					return returnMap;
//					return new ReturnObj(ErrorMsgConst.ERROR_CODE_MOL_HTTP_CALL_FAILED);
				}
				Map json = JSONUtil.JsonToMap(res);
				String paymentId = MapsUtil.getString(json, "paymentId");
				if (paymentId == null) {
//					return new ReturnObj(ErrorMsgConst.ERROR_CODE_MOL_PAYMENT_ID_EMPTY);
					returnMap.put(Constant.responseStatus, ErrorCode.ERROR_500);
					return returnMap;
				}
				String paymentUrl = MapsUtil.getString(json, "paymentUrl");
				if (paymentUrl == null) {
//					return new ReturnObj(ErrorMsgConst.ERROR_CODE_MOL_PAYMENT_URL_EMPTY);
					returnMap.put(Constant.responseStatus, ErrorCode.ERROR_500);
					return returnMap;
				}
				returnMap.put(Constant.responseStatus, ErrorCode.RESPONSE200);
				returnMap.put("redirectUrl", paymentUrl);
				response.sendRedirect(paymentUrl);
				return returnMap;
			} catch (Exception e) {
				logger.error("", e);
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
