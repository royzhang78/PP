package com.rodcell.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodcell.client.MsgObject;
import com.rodcell.client.UdpClient;
import com.rodcell.comm.util.JSPParser;
import com.rodcell.message.ErrorCode;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:35:01 
 * 类说明  订单接口
 */
@Controller
public class CashCardCallURLController extends BaseAction{
	 
	
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/CALLCashCard/{pay_id}")  //订单创建接口
    public String CALLCashCard(@PathVariable("pay_id") String pay_id,HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map httpParaMap=null;
		try {
		
			httpParaMap=getPostPar(request);
			httpParaMap.put("pay_id", pay_id);
			String str = JSPParser.getJsp("CallCashCard.tpl", httpParaMap);
			
			outString(response,str);
			
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
	
	 
	
	
}
