package com.rodcell.comm.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jetbrick.template.JetContext;
import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.runtime.JetPageContext;
import jetbrick.template.utils.UnsafeCharArrayWriter;
import jetbrick.template.web.JetWebContext;


//http://subchen.github.io/jetbrick-template/userguide.html
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年4月29日 下午4:46:00 
 * 类说明 
 */

public class jetTemplateUtil {

	public static void main(String[] args) {
		// 创建一个默认的 JetEngine
		JetEngine engine = JetEngine.create();
		Map<String, Object> context = new HashMap<String, Object>();
		UnsafeCharArrayWriter out=null;
		// 获取一个模板对象
//		JetTemplate template = engine.getTemplate("/sample.jetx");
		JetTemplate template = engine.createTemplate("#if (user != null && user != 1) $!{user.asString()} #1# #else #2#  #end");
		long s = System.currentTimeMillis();
//		for (int i = 0; i < 100000; i++) {
			context.put("user", "<img src=\"\"/>");
			out = new UnsafeCharArrayWriter();
			template.render(context, out);
//		}
//		Assert.assertEquals("7", out.toString());
		System.out.println((System.currentTimeMillis()-s)+out.toString());
	}

	public static String render(JetTemplate template,Map mode,HttpServletRequest request, HttpServletResponse response, HttpSession session){
		UnsafeCharArrayWriter out = new UnsafeCharArrayWriter();
		JetPageContext j =new JetPageContext(template, null, null);
		JetContext context=new JetContext(mode);
//		 context.get(JetWebContext.REQUEST);
		 context.put(JetWebContext.REQUEST, request);
		 context.put(JetWebContext.RESPONSE, response);
		 context.put(JetWebContext.SESSION, session);
		 
		template.render(context, out);
		return out.toString();
	}
	
}
