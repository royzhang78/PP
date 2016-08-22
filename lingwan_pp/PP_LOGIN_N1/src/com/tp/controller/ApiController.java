package com.tp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tp.comm.util.JSONUtil;
import org.tp.comm.util.MapsUtil;

import com.sun.swing.internal.plaf.synth.resources.synth;
import com.tp.comm.Constant;
import com.tp.entity.ReturnObj;
import com.tp.msg.ErrorCode;
import com.tp.service.ApiService;
import com.tp.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:35:01 
 * 类说明  订单接口
 */
@Controller
public class ApiController extends BaseAction{
	 
	public static Logger log = Logger.getLogger(ApiController.class);
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/api")  //订单创建接口
    public synchronized  String order(HttpServletRequest request, HttpServletResponse response, HttpSession session) { 
		Map map=getPar(request);
		try {
			long s=System.currentTimeMillis();
			
			

//			log.info("apiController.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			Object o =null;//orderService.service(map, request, response, session);
			
			String serviceName = MapsUtil.getString(map, "serviceName");
			String callPara = MapsUtil.getString(map, "callPara");//json.optString("callPara");
			String par = JSONUtil.objectToString(map);
			log.info("apiController.class uuid="+s+" parameter=="+par);
			boolean b =Constant.CTX.containsBean(serviceName);
			if(b){
				
				
				
				ApiService service =(ApiService)Constant.CTX.getBean(serviceName);
				Map m = (Map)JSONUtil.JsonToObject(callPara, Map.class);
				o=service.service(m, request, response, session);
				m.clear();
				m=null;
//				ReturnObj obj = new ReturnObj(ErrorCode.NO_ERROR);
//				obj.setReturnValues(o);
				String str = JSONUtil.objectToString(o);
				o=null;
				log.info("apiController.class uuid="+s+" runtime ="+(System.currentTimeMillis()-s)+" parameter=="+par+" return=="+str);
				 
//				if(o instanceof ReturnObj ){
//					if(!((ReturnObj)o).getReturnCode().equals(ErrorCode.NO_ERROR)){
//						try {
//							map.put("return", o);
//							MsgObject o1=new MsgObject("", ((ReturnObj)o).getReturnCode(), "", map);
//							UdpClient.produce(o1); 
//						} catch (Exception e1) {
//							
//						}
//					}
//				}
				outString(response,str);
			}
			map.clear();
			map=null;
			return null;
		} catch (Exception e) {		
			
			log.info("apiController.class  parameter=="+JSONUtil.objectToString(map));
			map.clear();
			map=null;			
			log.error("", e);
			try {
				try {
//					MsgObject o=new MsgObject("", ErrorCode.ERROR_500, e.getMessage(), map);
//					UdpClient.produce(o); 
				} catch (Exception e1) {
					
				}
				
				this.outString(response, JSONUtil.objectToString(new ReturnObj(ErrorCode.ERROR_500+"")));
			} catch (Exception e1) {
				
			}
			response.setStatus(500);
			return null;
		}
		
    } 
	
	 
	
	
}
