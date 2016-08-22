package com.rodcell.controller;

import java.util.HashMap;
import java.util.Iterator;
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
import com.rodcell.comm.util.JSPParser;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.PayServerDao;
import com.rodcell.entity.PayServer;
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
public class MolController extends BaseAction{
	
	@Autowired
	PayMessageService originService;
	
	@Autowired
	PayMessageService molCommService;
	
	@Autowired
	PayMessageService molCommSuccessService;
	
	
	@Autowired
	private PayServerDao payServerDao;
	
	private static long paychannelid=33;
	
	//http://127.0.0.1/pay/order?gName=s1&uID=1&productId=1&cparam=test&payid=42&source=0
	@RequestMapping("/mol.do")  //订单创建接口
    public String mol(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long s=System.currentTimeMillis();
			map=getPar(paychannelid,request);
			map.put("channelId", paychannelid+"");
			log.info("mol.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ChannelEntity c = ChannelParser.CHANNEL.get(paychannelid+"");
			String serviceName = c.getMo().getCallServiceName();
			PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
			Map o=service.service(paychannelid, map, request, response, session);

			String status = ChannelParser.toString(paychannelid+Constant.MOresponseStatus, o);
			response.setStatus(Integer.parseInt(status.trim()));
			
			String text = ChannelParser.toString(paychannelid+Constant.MOresponse, o).trim();
			
			log.info("mol.class uuid="+s+" return=="+text+" runtime ="+(System.currentTimeMillis()-s));
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
	
	
	
	
	@RequestMapping("/molSuccess.do")  //订单创建接口
    public String molSuccess(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map =null;
		try {
			long s=System.currentTimeMillis();
			map = getPar(paychannelid,request);
			log.info("molSuccess.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			ChannelEntity c = ChannelParser.CHANNEL.get(String.valueOf(paychannelid));
			String serviceName = c.getNotif().getCallServiceName();
			PayMessageService service = (PayMessageService)Constant.CTX.getBean(serviceName);
			Map  o=service.service(paychannelid, map, request, response, session);
			
			String status = ChannelParser.toString(paychannelid+Constant.NotifStatus, o);
			response.setStatus(Integer.parseInt(status.trim()));
			String text = ChannelParser.toString(paychannelid+Constant.NotifResponse, o).trim();
			
			log.info("molSuccess.class uuid="+s+" return=="+text+" runtime ="+(System.currentTimeMillis()-s));
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
	
	
	
	@RequestMapping("/{channelid}/molPage.do") //
	public String molPage(@PathVariable("channelid") String channelid,HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map par=null;
		try {
			long s=System.currentTimeMillis();
			par = getPar(request);
			log.info("molPage.class uuid="+s+" parameter=="+JSONUtil.objectToString(par));
			
			Iterator<String>iterator= par.keySet().iterator();
			String str="";
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value=String.valueOf(par.get(key));
				str+=key+"="+value+"&";				
			}
			str+="channe="+channelid;
			
			Map httpParaMap = new HashMap();
			httpParaMap.put("urlpar", str);
			httpParaMap.put("channelid", channelid);
			String serverName = MapsUtil.getString(par, new String[]{"gName","serverName"});
			PayServer ps = payServerDao.findPayServerByName(serverName);
			log.info("ps==="+ps);
			log.info("isSandbox==="+ps.getIsSandbox());
			if(ps!=null){
				httpParaMap.put("isSandbox", ps.getIsSandbox()+"");
				
			}
			String strValue = JSPParser.getJsp("molcommCashCard.tpl", httpParaMap);
			try {
				BaseAction.outString(response,strValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("molPage.class uuid="+s+" return=="+strValue+" runtime ="+(System.currentTimeMillis()-s));
			
		}catch (Exception e) {		
			MsgObject o=new MsgObject("", ErrorCode.ERROR_500, e.getMessage(), par);
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
	
	
	
 
	@RequestMapping("/{channelid}/molService.do") //mol 统一支付
	public String molService(@PathVariable("channelid") String channelid,HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long id=48;
			long s=System.currentTimeMillis(); 
			
			map = getPar(id,request);
			log.info("molService.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			
			Map data=molCommService.service(id, map, request, response, session);
			
			String strValue = JSPParser.getJsp("CallCashCard.tpl", data);
			try {
				BaseAction.outString(response,strValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("molService.class uuid="+s+" return=="+strValue+" runtime ="+(System.currentTimeMillis()-s));
			
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
	
	
	
	@RequestMapping("/molCommSuccessService.do") //mol 确认
	public String molCommSuccessService(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
		Map map=null;
		try {
			long id=48;
			long s=System.currentTimeMillis(); 
			
			map = getPar(id,request);
			log.info("molCommSuccessService.class uuid="+s+" parameter=="+JSONUtil.objectToString(map));
			Map data=molCommSuccessService.service(id, map, request, response, session);
			
			if(data==null){
				response.setStatus(500);
			}
//			String strValue = JSPParser.getJsp("CallCashCard.tpl", data);
			try {
				BaseAction.outString(response,"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("molService.class uuid="+s+" return=="+JSONUtil.objectToString(data)+""+" runtime ="+(System.currentTimeMillis()-s));
			
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
