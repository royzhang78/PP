package com.rodcell.filter;

 

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;

import org.springframework.web.filter.OncePerRequestFilter;

import com.rodcell.server.http.MyHttpServletResponse;


public class SessionFilter extends OncePerRequestFilter {

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
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
				String url=request.getRequestURI();
				filterChain.doFilter(request, response);
			
	}
	


}
