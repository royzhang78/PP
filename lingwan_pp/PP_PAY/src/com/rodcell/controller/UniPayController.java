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
import com.rodcell.message.ErrorCode;
import com.rodcell.service.PayMessageService;
import com.rodcell.service.UniPayService;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:35:01 
 * 类说明  订单接口
 */
@Controller
public class UniPayController extends BaseAction{
	
	@Autowired
	PayMessageService originService;
	
	@Autowired
	UniPayService uniPayService;
	 
	
	private static long paychannelid=54;
	
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/UniPayPage.do")  //订单创建接口
    public String UniPayPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(request);
			map.put("channelId", paychannelid+"");
			log.info("UniPay.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			uniPayService.page(map, request, response, session);
			
			log.info("UniPay.class uuid="+s+"  runtime ="+(System.currentTimeMillis()-s));			
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
	
	  
	
	@RequestMapping("/UniPayNotification/{orderId}")  //订单创建接口
    public String notification(@PathVariable("orderId") Long orderId,HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(paychannelid,request);
			map.put("channelId", paychannelid+"");
			log.info("UniPay.class UniPayNotification uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			String text = uniPayService.notification(orderId, map, request, response, session);
			if(text==null){
				response.setStatus(500);
				text="";
			}
			outString(response,text);
			log.info("UniPay.class UniPayNotification uuid="+s+"  runtime ="+(System.currentTimeMillis()-s));			
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
