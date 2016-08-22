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
public class TimweController extends BaseAction{
	
	@Autowired
	PayMessageService originService;
	
	private static long paychannelid=42;
	
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/timweSmsMO.do")  //订单创建接口
    public String thailandSmsMO(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(paychannelid,request);
			map.put("pp_paychannelid", paychannelid);
			log.info("timweSmsMO.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ChannelEntity c = ChannelParser.CHANNEL.get(paychannelid+"");
			String serviceName = c.getMo().getCallServiceName();
			PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
			Map o=service.service(paychannelid, map, request, response, session);

			String status = ChannelParser.toString(paychannelid+Constant.MOresponseStatus, o);
			response.setStatus(Integer.parseInt(status.trim()));
			
			String text = ChannelParser.toString(paychannelid+Constant.MOresponse, o).trim();
			
			log.info("timweSmsMO.class uuid="+s+" return=="+text+" runtime ="+(System.currentTimeMillis()-s));
			
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
	
	
	
	@RequestMapping("/timweSmsNotify.do")  //订单创建接口
    public String thailandSmsDRL(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map =null;
		try {
			long s=System.currentTimeMillis();
			map = getPar(paychannelid,request);
			log.info("timweSmsNotify.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ChannelEntity c = ChannelParser.CHANNEL.get(String.valueOf(paychannelid));
			String serviceName = c.getNotif().getCallServiceName();
			PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
			Map  o=service.service(paychannelid, map, request, response, session);
			
			String status = ChannelParser.toString(paychannelid+Constant.NotifStatus, o);
			response.setStatus(Integer.parseInt(status.trim()));
			String text = ChannelParser.toString(paychannelid+Constant.NotifResponse, o).trim();
			
			log.info("timweSmsNotify.class uuid="+s+" return=="+text+" runtime ="+(System.currentTimeMillis()-s));
			
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
