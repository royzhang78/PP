package com.rodcell.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodcell.comm.ChannelParser;
import com.rodcell.comm.Constant;
import com.rodcell.comm.SqlManagement;
import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.PayChannelDao;
import com.rodcell.db.DBManagement;
import com.rodcell.server.http.AppInit;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月13日 上午11:00:10 
 * 类说明 
 */
@Controller
public class UpdateAppController extends BaseAction{
	 
	@Autowired
	private DBManagement dbManagement;
	
	private boolean b =true;
	
//	@RequestMapping("/update")  //订单创建接口
//   public String order(HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
//		try {
//			if(b){
//				b=false;
//				Map par = MapsUtil.newHashMap();// getPar(request);
//				par.put("username", request.getParameter("username"));
//				par.put("password", request.getParameter("password"));
//				
//				SqlXML sqlobj =SqlManagement.getpropByXml("user_login", par); 
//				Map m = dbManagement.queryForBean(sqlobj.getSearchsql(),Map.class ,sqlobj.getPar());
//				if(m!=null){
//					ApplicationContext tmp =   new FileSystemXmlApplicationContext(Constant.APP_ROOT_PATH+"\\spring\\spring.xml"); 
//	//				PayChannelDao payChannelDao = (PayChannelDao)tmp.getBean("payChannelDao");
//		//			Resource r=new FileSystemResource(Constant.APP_ROOT_PATH+"\\channel\\"+request.getParameter("channel_id")+".xml");
//		//			ChannelParser.newBundle(r, payChannelDao.findPayChannel());
//					Constant.CTX=null;
//					Constant.CTX =tmp;
//					new AppInit().init();
//				}
//			
//			}
//		
//		} catch (Exception e) {			
//			log.error("", e);
//			response.setStatus(500);
//			return null;
//		}finally{
//			b=true;
//		}
//		return null;
//		
//   }
}
