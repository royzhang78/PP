package com.rodcell.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodcell.client.MsgObject;
import com.rodcell.client.UdpClient;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.entity.comm.ReturnObj;
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
public class ApiController extends BaseAction{
	 
	
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/api")  //订单创建接口
    public synchronized String order(HttpServletRequest request, HttpServletResponse response, HttpSession session) { 
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(request);
			

//			log.info("apiController.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			Object o =null;//orderService.service(map, request, response, session);
			
			String serviceName = MapsUtil.getString(map, "serviceName");
			String callPara = MapsUtil.getString(map, "callPara");//json.optString("callPara");
			String callback = MapsUtil.getString(map, "callback");
//			if("inSohaPurchaseJson".equals(serviceName)){
//				serviceName="orderService";
//			}
			boolean b =Constant.CTX.containsBean(serviceName);
			if(b){
				ApiService service =(ApiService)Constant.CTX.getBean(serviceName);
				o=service.service((Map)JSONUtil.JsonToObject(callPara, Map.class), request, response, session);
//				ReturnObj obj = new ReturnObj(ErrorCode.NO_ERROR);
//				obj.setReturnValues(o);
				String str = JSONUtil.objectToString(o);
				if(!StringUtil.isNullOrEmpty(callback)){
					str=callback+"("+str+")";
				}
				log.info("apiController.class uuid="+s+" parameter=="+JSONUtil.objectToString(map)+" return=="+str+" runtime ="+(System.currentTimeMillis()-s));
//				if (o instanceof ReturnObj ) {
//					if(((ReturnObj)o).getReturnCode()!=ErrorCode.NO_ERROR){
//						try {
//							MsgObject o1=new MsgObject("", ((ReturnObj)o).getReturnCode(), "", map);
//							UdpClient.produce(o1); 
//						} catch (Exception e1) {
//							
//						}
//					}
//				}
				outString(response,str);
			}else if("inGooglePurchaseJson".equals(serviceName) ||"inGoogleService".equals(serviceName)){//google支付
				serviceName="inGoogleService";
				PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
				Map o1=service.service(6, (Map)JSONUtil.JsonToObject(callPara, Map.class), request, response, session);
				
				if(o1.get("ReturnObj")!=null){//订单创建错误
					String str = JSONUtil.objectToString(o1.get("ReturnObj"));
					log.info("apiController.class uuid="+s+" parameter=="+JSONUtil.objectToString(map)+" return=="+str+" runtime ="+(System.currentTimeMillis()-s));
					map.put("return", o1.get("ReturnObj"));
//					try{
//						if (o1.get("ReturnObj") instanceof ReturnObj ) {
//							if(!((ReturnObj)o1.get("ReturnObj")).getReturnCode().equals(ErrorCode.NO_ERROR)){
//								try {
//									MsgObject o3=new MsgObject("", ((ReturnObj)o).getReturnCode(), "", map);
//									UdpClient.produce(o3); 
//								} catch (Exception e1) {
//									
//								}
//							}
//						}
//					}catch(Exception e){
//						
//					}
					this.outString(response, str);
				}else{
					log.info("apiController.class uuid="+s+" parameter=="+JSONUtil.objectToString(map)+" return=="+null+" runtime ="+(System.currentTimeMillis()-s));
					map.put("serviceName", serviceName);
//					try {
//						MsgObject o2=new MsgObject("","404", "", map);
//						UdpClient.produce(o2); 
//					} catch (Exception e1) {
//						
//					}
					
				}  
				o1.clear();
				o1=null;
			}else{
//				try {
//					MsgObject o1=new MsgObject("", ErrorCode.ERROR_400, "", map);
//					UdpClient.produce(o1); 
//				} catch (Exception e1) {
//					
//				}
				response.setStatus(400);
				this.outString(response, JSONUtil.objectToString(new ReturnObj(ErrorCode.ERROR_400+"")));
			}
//			else if("orderService".equals(serviceName) ){//订单创建
//				log.info("OrderController.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
//				OrderService orderService=(OrderService)Constant.CTX.getBean("orderService");
//				Object obj =orderService.service(map, request, response, session);				
//				String str = JSONUtil.objectToString(obj);
//				outString(response,str);
//				log.info("OrderController.class uuid="+s+" return=="+str+" runtime ="+(System.currentTimeMillis()-s));
//			}
			map.clear();
			map=null;
			
			return null;
		} catch (Exception e) {		
			
//			try {
//				MsgObject o=new MsgObject("", ErrorCode.ERROR_500, e.getMessage(), map);
//				UdpClient.produce(o);
//				this.outString(response, JSONUtil.objectToString(new ReturnObj(ErrorCode.ERROR_500+"")));
//			} catch (Exception e1) {
//				
//			}
			log.error("", e);
			response.setStatus(500);
			return null;
		}
		
    }
	
	 
	
	
}
