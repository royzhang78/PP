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
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
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
public class OrderController extends BaseAction{
	
	@Autowired
	private ApiService orderService;
	
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/order")  //订单创建接口
    public synchronized String order(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(request);
			map.put(Constant.NotifStatus, "");//驱除关键词
			log.info("OrderController.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			Object o =orderService.service(map, request, response, session);
			
			String str = JSONUtil.objectToString(o);
			
			
			log.info("OrderController.class uuid="+s+" return=="+str+" runtime ="+(System.currentTimeMillis()-s));
			
			outString(response,str);
			map.clear();
			map=null;
		} catch (Exception e) {	
//			MsgObject o=new MsgObject("", ErrorCode.ERROR_500, e.getMessage(), map);
//			try {
//				UdpClient.produce(o);
//			} catch (Exception e1) {
//			}
//			log.error("", e);
			response.setStatus(500);
			return null;
		}
		return null;
    }
	
	 
}
