package com.rodcell.filter;

 

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


public class SessionFilter extends OncePerRequestFilter {

	/** 检查不通过时，转发的URL */
	private String forwardUrl; 

	
//	 public void doFilter(ServletRequest request, ServletResponse response)
//	            throws IOException, ServletException {
//		 logger.info("------------");
//	 }
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		//System.out.println();
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

				//String servletPath = request.getServletPath();
				response.setCharacterEncoding("utf-8");
				String url=request.getRequestURI();
//				logger.info("=======>"+url);
				
				if( url.contains(".htm")||url.contains("update")||(url.contains("find")||url.contains("save")||url.contains(".js")||url.contains(".css")||url.contains(".gif")||url.contains(".mp3")|| url.contains(".mp4")|| url.contains(".mid")|| url.contains(".aac")|| url.contains(".wma")|| url.contains(".ogg")
				   || url.contains(".png")|| url.contains(".jpg")|| url.contains(".bmp"))&&!url.contains(".jsp")){
					//待定逻辑   根据url参数，判断是否是接口类
					chain.doFilter(req, res);
				}else{
			        Object sessionObj = request.getSession().getAttribute("loginUser");
			        // 如果Session为空，则跳转到指定页面
			        if (sessionObj == null) {
			        	String contextPath = request.getContextPath();
			        	//String redirect = servletPath + "?" + StringUtils.defaultString(request.getQueryString());
			        	    
				       
			        	if( !url.contains("login")){
					        String urlRedir = contextPath + org.apache.commons.lang.StringUtils.defaultIfEmpty(forwardUrl, "/loginOut.htm") ;//+ "?redirect=" + URLEncoder.encode(redirect, "UTF-8");
					        response.sendRedirect(urlRedir);       
			        	}else{
			        		chain.doFilter(req, res);
			        	}
			        	
			    	} else {
			    		chain.doFilter(req, res);
			    	}
				}
				
	}
	


}
