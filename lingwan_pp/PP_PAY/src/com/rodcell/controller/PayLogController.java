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
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.ApiService;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:35:01 
 * 类说明  订单接口
 */
@Controller
public class PayLogController extends BaseAction{
	 
	@Autowired
	ApiService payLogService;
	/**
	 * 订单日志接口
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/payLog.do")  //订单创建接口
    public String PayLog(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(request);
			

//			log.info("apiController.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			Object o =null;//orderService.service(map, request, response, session);
			
			String serviceName = MapsUtil.getString(map, "serviceName");
			String callPara = MapsUtil.getString(map, "callPara");//json.optString("callPara");
			String callback = MapsUtil.getString(map, "callback");
			
			log.info("payLog.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ReturnObj  r = payLogService.service((Map)JSONUtil.JsonToObject(callPara, Map.class), request, response, session);

			String str = JSONUtil.objectToString(r);
			if(!StringUtil.isNullOrEmpty(callback)){
				str=callback+"("+str+")";
			}
			log.info("payLog.class uuid="+s+"  runtime ="+(System.currentTimeMillis()-s));	
			outString(response,str);
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
