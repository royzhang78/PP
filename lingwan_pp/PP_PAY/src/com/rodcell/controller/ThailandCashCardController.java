package com.rodcell.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodcell.client.MsgObject;
import com.rodcell.client.UdpClient;
import com.rodcell.comm.ChannelParser;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.JSPParser;
import com.rodcell.dao.sys.SysPayStaticConfigDao;
import com.rodcell.entity.PayMain;
import com.rodcell.entity.comm.ChannelEntity;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.service.PayMessageService;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:35:01 
 * 类说明  订单接口
 */
@Controller
public class ThailandCashCardController extends BaseAction{
	

	private static long paychannelid=41;
	@Autowired
	private ApiService orderService;
	
	@Autowired
	private SysPayStaticConfigDao sysPayStaticConfigDao;
	
	//回调地址http://127.0.0.1/pay/41/o
	
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/thailandCashCard.do")  //订单创建接口
    public String order(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map httpParaMap=null;
		try {
			long s=System.currentTimeMillis();
			httpParaMap=getPar(request);
			httpParaMap.put("channelId", "41");
			httpParaMap.put("isSandboxDB", "thailandCashCard");			
			ReturnObj obj = (ReturnObj)orderService.service(httpParaMap, request, response, session);
			
			ReturnObj robj = obj;
			if(robj.getReturnCode().equals(ErrorCode.NO_ERROR)){
				
				PayMain payMain =(PayMain)obj.getReturnObjs();
				
				String CashCardStoreCode=sysPayStaticConfigDao.findPayStaticConfigbyKey("CashCardStoreCode");
				String ThailandCashCardPayType=sysPayStaticConfigDao.findPayStaticConfigbyKey("CashCardPayType");
				String CashCardResponseUrl=sysPayStaticConfigDao.findPayStaticConfigbyKey("CashCardResponseUrl");
				String CashCardPayChannel=sysPayStaticConfigDao.findPayStaticConfigbyKey("CashCardPayChannel");
				String CashCardUrl= sysPayStaticConfigDao.findPayStaticConfigbyKey("CashCardUrl");
				
				
				String TestCashCardUrl= sysPayStaticConfigDao.findPayStaticConfigbyKey("TestCashCardUrl");
				
				
				if(payMain.getIsSandbox()==Constant.ISSANDBOX_1){
					CashCardUrl=TestCashCardUrl;
				}
				
				httpParaMap.put("mcode", CashCardStoreCode);
				httpParaMap.put("inv_no", payMain.getPay_id());
				httpParaMap.put("product_name", payMain.getProduct_id());
				httpParaMap.put("amount", payMain.getPay_money());
				httpParaMap.put("pay_type", ThailandCashCardPayType);
				httpParaMap.put("response_url", CashCardResponseUrl+payMain.getPay_id() );//+ "?locale=" + MapsUtil.getString(httpParaMap, "locale")
				httpParaMap.put("currency", payMain.getCurrency());
				httpParaMap.put("language", "TH");
				httpParaMap.put("sof", CashCardPayChannel);
				httpParaMap.put("tmcc14digits", "");
				httpParaMap.put("action", CashCardUrl);
				httpParaMap.put("isSandbox", payMain.getIsSandbox()+"");
				
				String str = JSPParser.getJsp("thaiandCashCard.tpl", httpParaMap);
				
				outString(response,str);
			}else{
				
				try{
					 new SException(null, robj.getReturnCode(), httpParaMap);
				}catch(Exception e1){
					
				}
				outString(response,JSONUtil.objectToString(obj));
			}
		} catch (Exception e) {		
			
			MsgObject o=new MsgObject("", ErrorCode.ERROR_500, e.getMessage(), httpParaMap);
			try {
				UdpClient.produce(o);
			} catch (Exception e1) {
			}
			
			
			log.error("", e);
			response.setStatus(500);
			return null;
		}
		return null;
    }
	
	 
	
	@RequestMapping("/thailandCashCardNotify.do")  //订单创建接口
	public String TrueMoneyNotify(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return thailandCashCardNotify(request, response, session);  
	}
	
	
	@RequestMapping("/TrueMoneyNotify.do")  //订单创建接口
    public String thailandCashCardNotify(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map = getPar(paychannelid,request);
			log.info("thailandCashCardNotify.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ChannelEntity c = ChannelParser.CHANNEL.get(String.valueOf(paychannelid));
			String serviceName = c.getNotif().getCallServiceName();
			PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
			Map  o=service.service(paychannelid, map, request, response, session);
			
			String status = ChannelParser.toString(paychannelid+Constant.NotifStatus, o);
			response.setStatus(Integer.parseInt(status.trim()));
			String text = ChannelParser.toString(paychannelid+Constant.NotifResponse, o).trim();
			
			log.info("thailandCashCardNotify.class uuid="+s+" return=="+text+" runtime ="+(System.currentTimeMillis()-s));
			
			try{
				if(!status.contains(ErrorCode.RESPONSE200+"")){
					new SException(null, status, o);
				}
			}catch(Exception e1){
				
			}
			outString(response,text);
		} catch (Exception e) {			
			
			MsgObject o=new MsgObject("", ErrorCode.ERROR_500, e.getMessage(), map);
			try {
				UdpClient.produce(o);
			} catch (Exception e1) {
			}
			
			log.error("", e);
			response.setStatus(500);
			return null;
		}
		return null;
    }
	
}
