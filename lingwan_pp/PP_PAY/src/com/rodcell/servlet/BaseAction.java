package com.rodcell.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.glassfish.grizzly.http.server.Request;

import com.rodcell.comm.ChannelParser;
import com.rodcell.comm.util.IPUtil;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.RequestUtils;
import com.rodcell.comm.util.ResponseUtil;
import com.rodcell.comm.util.XmlUtils;
import com.rodcell.entity.comm.ChannelEntity;

public class BaseAction   {

	 
	public static Logger log = Logger.getLogger(BaseAction.class);
	
 
	public Map getPostPar(HttpServletRequest request){
		
		Map par = MapsUtil.newHashMap();
		Iterator<String> iterator =request.getParameterMap().keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			par.put(key, request.getParameter(key));
		}
		log.info("ip:"+RequestUtils.getRemoteAddr(request)+" val="+par); 
		return par;
	}
	public Map getPar(HttpServletRequest request){
		Map par = MapsUtil.newHashMap();
		String reqStr = getInputString(request);
		if(reqStr==null||"".equals(reqStr)){
			Map parm1=new HashMap();
			Iterator<String> iterator =request.getParameterMap().keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				parm1.put(key, request.getParameter(key));
			}
			log.info("ip:"+RequestUtils.getRemoteAddr(request)+" val="+parm1); 
			return parm1;
		}else{
			
			try{
				par =  (Map)JSONUtil.JsonToObject(reqStr, Map.class);
			}catch(Exception e){
				try {
					par = XmlUtils.Dom2Map(DocumentHelper.parseText(reqStr));
				} catch (Exception e1) {
					log.info("Par===="+ reqStr);
					try{
//						StringEntity entity = new StringEntity(reqStr);
//						 HttpPost httpost = new HttpPost(LOCATIONS_URL);
//				            httpost.setEntity(entity);
//				            entity.g
						Map parm1=new HashMap();
				            jodd.http.HttpResponse response= new jodd.http.HttpResponse();
				            response.body(reqStr);
				            parm1=response.getHttpRequest().query();
				            log.info("Par parm1===="+ parm1.keySet().size());
						
//						Iterator<String> iterator =request.getParameterMap().keySet().iterator();
//						while(iterator.hasNext()){
//							String key = iterator.next();
//							parm1.put(key, request.getParameter(key));
//						}
				            log.info("ip:"+RequestUtils.getRemoteAddr(request)+" val="+parm1); 
						return parm1;
					} catch (Exception e2) {
						log.error("", e2);
					}
				}
			}
		}
		log.info("ip:"+RequestUtils.getRemoteAddr(request)+" val="+par); 
		return par;
	}
	
	
	
	
	public Map getPar(Long channel ,HttpServletRequest request){
		Map par = MapsUtil.newHashMap();
		ChannelEntity c = ChannelParser.CHANNEL.get(String.valueOf(channel));
		if(c!=null){
			if("http".equals(c.getRequest_type())){ 
				Iterator<String> iterator =request.getParameterMap().keySet().iterator();
				while(iterator.hasNext()){
					String key = iterator.next();
					par.put(key, request.getParameter(key));
				}
			}else if("json".equals(c.getRequest_type())){
				String reqStr = getInputString(request);
				par =  (Map)JSONUtil.JsonToObject(reqStr, Map.class);
			}else if("xml".equals(c.getRequest_type())){
				String reqStr = getInputString(request);
				try {
					par = XmlUtils.Dom2Map(DocumentHelper.parseText(reqStr));
				} catch (DocumentException e) {
					log.error("", e);
				}
			}
		}
		return par;
	}
	
	protected String getInputString(HttpServletRequest request) {
		BufferedReader in = null;
		try {
			String aLine;
			StringBuilder s = new StringBuilder();
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			while ((aLine = in.readLine()) != null) {
				s.append(aLine);
			}
			return s.toString();
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				log.error(e, e);
			}
		}
		return null;
	}
	
	
	public static void outString(HttpServletResponse response,Object o) throws Exception{
		if(o!=null){
//			ByteArrayOutputStream bout = new ByteArrayOutputStream();
//	        
//	        GZIPOutputStream gout = new GZIPOutputStream(bout);
//	        gout.write(o.toString().getBytes());
//	        gout.close();
//	        
//	        byte b[] = bout.toByteArray();  //得到压缩后的数据
//			response.setHeader("Content-Encoding", "gzip");
//	        response.setHeader("Content-Length", b.length+"");
//	        response.setHeader("Pragma", "no-cache");
//	        response.setHeader("Cache-Control", "no-cache");
//	        response.setCharacterEncoding("GB2312");
			response.getWriter().write(String.valueOf(o));
//			response.getOutputStream().write(b);
		}
	}


 


 
	
}
