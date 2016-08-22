package com.rodcell.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:35:01 
 * 类说明  订单接口
 */
@Controller
public class TestController extends BaseAction{
	 
	public static Logger log = Logger.getLogger(TestController.class);
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/heartbeat.do")  //订单创建接口
	 public String order(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception { 
//		Map map=new HashMap();
		
		response.getWriter().write("1");
			return null;
		
		
    }
	
	 
	
  
		
}
