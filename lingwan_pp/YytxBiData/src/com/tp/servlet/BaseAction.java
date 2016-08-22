package com.tp.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.tp.comm.util.JSONUtil;
import org.tp.comm.util.MapsUtil;

public class BaseAction   {

	 
	public static Logger log = Logger.getLogger(BaseAction.class);
	
 
	public Map getPar(HttpServletRequest request){
		Map par = MapsUtil.newHashMap();
//		Map parm1=new HashMap();
		
//		System.out.println(request.getQueryString());
		String reqStr = getInputString(request);
		if(reqStr==null||"".equals(reqStr)){
			Iterator<String> iterator =request.getParameterMap().keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				par.put(key, request.getParameter(key));
			}
			return par;
		}else{
			try{
				par =  (Map)JSONUtil.JsonToObject(reqStr, Map.class);
			}catch(Exception e){
				log.error("request :"+reqStr, e);
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
	
	
	public void outString(HttpServletResponse response,Object o) throws Exception{
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
//	        response.setCharacterEncoding("UTF-8");
////	//		response.getWriter().write(String.valueOf(b));
//			response.getOutputStream().write(b);
			response.getWriter().write(String.valueOf(o));
		}
	}


 
	public void out(HttpServletResponse response,byte b[]) throws Exception{
		response.getOutputStream().write(b);
	}

 
	
}
