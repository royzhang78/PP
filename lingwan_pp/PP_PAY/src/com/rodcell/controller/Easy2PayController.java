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
import com.rodcell.entity.comm.ChannelEntity;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.PayMessageService;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:35:01 
 * 类说明  订单接口
 */
@Controller
public class Easy2PayController extends BaseAction{
	
	@Autowired
	PayMessageService originService;
	
	private static long paychannelid=45;
	
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/easy2PayMO.do")  
    public String thailandSmsMO(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(paychannelid,request);
			log.info("easy2PayMO.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ChannelEntity c = ChannelParser.CHANNEL.get(paychannelid+"");
			String serviceName = c.getMo().getCallServiceName();
			PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
			Map o=service.service(paychannelid, map, request, response, session);

			String status = ChannelParser.toString(paychannelid+Constant.MOresponseStatus, o);
			response.setStatus(Integer.parseInt(status.trim()));
			
			String text = ChannelParser.toString(paychannelid+Constant.MOresponse, o).trim();
			
			log.info("easy2PayMO.class uuid="+s+" return=="+text+" runtime ="+(System.currentTimeMillis()-s));
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
	
	
	
	@RequestMapping("/easy2PayDRL.do") 
    public String thailandSmsDRL(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map =null;
		try {
			long s=System.currentTimeMillis();
			map = getPar(paychannelid,request);
			log.info("easy2PayMO.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ChannelEntity c = ChannelParser.CHANNEL.get(String.valueOf(paychannelid));
			String serviceName = c.getNotif().getCallServiceName();
			PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
			Map  o=service.service(paychannelid, map, request, response, session);
			
			String status = ChannelParser.toString(paychannelid+Constant.NotifStatus, o);
			response.setStatus(Integer.parseInt(status.trim()));
			String text = ChannelParser.toString(paychannelid+Constant.NotifResponse, o).trim();
			
			log.info("easy2PayMO.class uuid="+s+" return=="+text+" runtime ="+(System.currentTimeMillis()-s));
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
