package com.rodcell.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;

public class BaseAction   {

	 
	public static Logger log = Logger.getLogger(BaseAction.class);
	
	

	protected int start;
	
	protected int limit;
	
	/**数据总记录数**/
	protected int dataCount;
	
	/**总页数**/
	private int pagesLen;

	/**计算总页数**/
	public int pagesLen(int dataCount,int pagesize){
		if(pagesize==0)pagesize=Constant.pagesize;
		return dataCount % pagesize==0?dataCount/pagesize:dataCount/pagesize+1;
	}

	
	protected Map getParameter(HttpServletRequest request) {
		log.info("par:===="+request.getParameter("par"));
		Map p=JSONUtil.JsonToMap(request.getParameter("par"));
		if(p!=null&&p.get("showpage")!=null){
			String showpage =p.get("showpage")==null || "".equals(p.get("showpage"))?"1":String.valueOf(p.get("showpage")) ;
			int page =Integer.parseInt(showpage)-1;
			start=(page)*Constant.pagesize;
//			limit=(start+Constant.pagesize);
			limit=Constant.pagesize;
			p.put("start", start);
			p.put("limit", limit);
			return p;
		}
		return p;
	}
	
 
	public Map getPar(HttpServletRequest request){
		Map par = MapsUtil.newHashMap();
		String reqStr = getInputString(request);
		request.getParameterNames();
		if(reqStr==null||"".equals(reqStr)){
			Map parm1=new HashMap();
			Iterator<String> iterator =request.getParameterMap().keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				parm1.put(key, request.getParameter(key));
			}
			return parm1;
		}else{
			try{
				par =  (Map)JSONUtil.JsonToObject(reqStr, Map.class);
			}catch(Exception e){
				log.error("", e);
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
////	        System.out.println(o.toString());
//	        gout.write(o.toString().getBytes());
//	        gout.close();
//	        
//	        byte b[] = bout.toByteArray();  //得到压缩后的数据
//			response.setHeader("Content-Encoding", "gzip");
//	        response.setHeader("Content-Length", b.length+"");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
//	//		response.getWriter().write(String.valueOf(b));
//			response.getOutputStream().write(b);
			
			response.getWriter().write(String.valueOf(o));
		}
	}


 


 
	
}
