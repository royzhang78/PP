package com.rodcell.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * @version 创建时间：2014年12月3日 下午2:19:18 
 * 类说明 
 */
@Controller
public class MimopayController extends BaseAction{
	private static long paychannelid=55;
	
	@RequestMapping("/mimopaySuccess.do")  //订单创建接口
    public String mimopaySuccess(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		  
				Map map =null;
				try {
					long s=System.currentTimeMillis();
					map = getPar(paychannelid,request);
					log.info("mimopaySuccess.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
					
					ChannelEntity c = ChannelParser.CHANNEL.get(String.valueOf(paychannelid));
					String serviceName = c.getNotif().getCallServiceName();
					PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
					Map  o=service.service(paychannelid, map, request, response, session);
					
					String status = ChannelParser.toString(paychannelid+Constant.NotifStatus, o);
					response.setStatus(Integer.parseInt(status.trim()));
					String text = ChannelParser.toString(paychannelid+Constant.NotifResponse, o).trim();
					
					log.info("mimopaySuccess.class uuid="+s+" return=="+text+" runtime ="+(System.currentTimeMillis()-s));
					
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
	
	
	@RequestMapping("/mimopayRedirectPage.do")  //订单创建接口
    public String mimopayRedirectPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map =null;
		try {
			long s=System.currentTimeMillis();
			map = getPar(request);
			log.info("mimopayRedirectPage:"+JSONUtil.objectToString(map));
		} catch (Exception e) {		
			
		}
		 
		return null;
    }
}
