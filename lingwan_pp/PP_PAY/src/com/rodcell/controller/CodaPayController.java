package com.rodcell.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodcell.client.MsgObject;
import com.rodcell.client.UdpClient;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.entity.comm.ReturnObj;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.CodaPayService;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:35:01 
 * 类说明  订单接口
 */
@Controller
public class CodaPayController extends BaseAction{
	
	@Autowired
	CodaPayService codaPayService;
	 
	 
	
	private static long paychannelid=56;
	
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/codaPay/Completion")  //订单创建接口
    public String Completion(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(request);
			map.put("channelId", paychannelid+"");
			log.info("coda.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ReturnObj r =codaPayService.Completion(map, request, response, session);
			
			log.info("coda.class uuid="+s+"  runtime ="+(System.currentTimeMillis()-s));			
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
	
	  
	
	@RequestMapping("/codaPay/Notification")  //订单创建接口
    public String notification(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(paychannelid,request);
			map.put("channelId", paychannelid+"");
			log.info("coda.class codaNotification uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ReturnObj r =codaPayService.notification(map, request, response, session);
			outString(response,"ResultCode="+r.getReturnCode());
			log.info("coda.class codaPayNotification uuid="+s+"  runtime ="+(System.currentTimeMillis()-s)+" return :"+r.getReturnCode());			
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
