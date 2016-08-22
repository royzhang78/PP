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
import com.rodcell.comm.ChannelParser;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.entity.FilterObject;
import com.rodcell.entity.comm.ChannelEntity;
import com.rodcell.exception.SException;
import com.rodcell.message.ErrorCode;
import com.rodcell.service.MessageFilterService;
import com.rodcell.service.PayMessageService;
import com.rodcell.service.impl.FilterServer;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 时间：2014年5月20日 上午10:29:38 
 * 类说明 用于接受结束消息(例如mo消息)
 */
@Controller
public class TerminatedController extends BaseAction {

	@Autowired
	PayMessageService terminatedService;
	
 
	
	@RequestMapping("/drl/{paychannelid}/")  //订单创建接口
	public String service(@PathVariable("paychannelid") Long paychannelid,HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Map map=null;
		try {
			 map = getPar(paychannelid,request);
			long s=System.currentTimeMillis();
			log.info("TerminatedController.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			log.info("TerminatedController.class parameter=="+JSONUtil.objectToString(map));
			//参数验证过滤
			FilterObject  fo =FilterServer.payfilterPar(paychannelid, Constant.COMM_TEMPLATE_SOURCE_1+"", map);
			
			if(fo.isStatus()){
				Map o =null;
				
//				o = terminatedService.service(paychannelid, map, request, response, session);
				
				ChannelEntity c = ChannelParser.CHANNEL.get(String.valueOf(paychannelid));
				String serviceName = c.getNotif().getCallServiceName();
				PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
				o=service.service(paychannelid, map, request, response, session);
				
				if(o.get("ReturnObj")!=null){//订单创建错误
					String str = JSONUtil.objectToString(o.get("ReturnObj"));
					this.outString(response, str);
					return null;
				}
				
				String status = ChannelParser.toString(paychannelid+Constant.NotifStatus, o);
				response.setStatus(Integer.parseInt(status.trim()));
				
				String text = ChannelParser.toString(paychannelid+Constant.NotifResponse, o).trim();
				
				try{
					if(!status.contains(ErrorCode.RESPONSE200+"")){
						new SException(null, status, o);
					}
				}catch(Exception e1){
					
				}
				log.info("TerminatedController.class uuid="+s+" return=="+text+" runtime ="+(System.currentTimeMillis()-s));
				this.outString(response, text);
			}else{
				log.info("OriginController.class uuid="+s+" return=="+JSONUtil.objectToString(fo)+" runtime ="+(System.currentTimeMillis()-s));
				
				response.setStatus(fo.getResponseStatus());
				
				try{ 
					new SException(null, fo.getResponseStatus()+"", map);
					 
				}catch(Exception e1){
					
				}
				this.outString(response, fo.getReturnTxt());
			}
		} catch (Exception e) {
			MsgObject o=new MsgObject("", ErrorCode.ERROR_500, e.getMessage(), map);
			try {
				UdpClient.produce(o);
			} catch (Exception e1) {
			}
			
			log.error("", e);
			return null;
		}
		return null;
	}


	public PayMessageService getTerminatedService() {
		return terminatedService;
	}


	public void setTerminatedService(PayMessageService terminatedService) {
		this.terminatedService = terminatedService;
	}


	 
	
	
	
}
