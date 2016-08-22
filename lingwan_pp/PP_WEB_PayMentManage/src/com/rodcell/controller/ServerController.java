package com.rodcell.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodcell.comm.SqlManagement;
import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.dao.BaseDao;
import com.rodcell.entity.TableObject;
import com.rodcell.service.TestDB;
import com.rodcell.service.UserService;
import com.rodcell.servlet.BaseAction;


@Controller
//@RestController
public class ServerController extends BaseAction{  // extends BaseDao {
	
 
	
	@Autowired
	private UserService userService;

	
	
	
	@RequestMapping("/findServer")
	public void findProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		try {
//		String username = request.getParameter("username");
		Map par = MapsUtil.newHashMap();// getPar(request);
		par = getParameter(request);
		int pagesize=0;
		String tmp = MapsUtil.getString(par, "pagesize");
		if(!StringUtil.isNullOrEmpty(tmp)){
			pagesize=Integer.parseInt("tmp");
		}
		
			Object o = userService.findServerByPage(par);
			
			int count = userService.findServerByPageCount(par);
			
			int pagecount = pagesLen(count,pagesize);
			
			String str= JSONUtil.objectToString(new TableObject(pagecount, o));
			outString(response, str);
		} catch (Exception e) {
			log.error("", e);
		}
		
		
	}
	 
	
	
	
	@RequestMapping("/saveServer")
	public void insertServer(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		try {
//		String username = request.getParameter("username");
		Map par = MapsUtil.newHashMap();// getPar(request);
		par = getParameter(request);
		
			
			long i =0;
			if(StringUtil.isNullOrEmpty(MapUtils.getString(par, "s_id"))){
				i=userService.insertServer(par);
			}else{
				i=userService.updateServer(par);
			}
			
			//String str= JSONUtil.objectToString(new TableObject(pagecount, o));
			outString(response, i);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	
	@RequestMapping("/findServerChannel")
	public void findServerChannel(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		try {
		Map par = MapsUtil.newHashMap();// getPar(request);
		par = getParameter(request);
		

			Object o = userService.findServerChannel(MapUtils.getString(par,
					"s_id"));

			 String str= JSONUtil.objectToString(o);
			outString(response, str);
		} catch (Exception e) {
			log.error("", e);
		}
	}
		
	
	
	@RequestMapping("/updateServerChannel")
	public void updateServerChannel(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		try {

		Map par = MapsUtil.newHashMap();// getPar(request);
		par = getParameter(request);
		
			Object o = userService.updateServerChannel(par);

			 String str= JSONUtil.objectToString(o);
			outString(response, str);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	
	
	@RequestMapping("/findconfigChannelPrice")
	public void findconfigChannelPrice(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		try {
		Map par = MapsUtil.newHashMap();// getPar(request);
		par = getParameter(request);
		int pagesize=0;
		String tmp = MapsUtil.getString(par, "pagesize");
		if(!StringUtil.isNullOrEmpty(tmp)){
			pagesize=Integer.parseInt("tmp");
		}

			Object o = userService.findconfigChannelPrice(par);
			
			int i =userService.findconfigChannelPriceCount(par);

			int pagecount = pagesLen(i,pagesize);
			
			String str= JSONUtil.objectToString(new TableObject(pagecount, o));
			outString(response, str);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	
	@RequestMapping("/saveChannelPrice")
	public void saveChannelPrice(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		try {
//		String username = request.getParameter("username");
		Map par = MapsUtil.newHashMap();// getPar(request);
		par = getParameter(request);
		
			
			long i =0;
			if(StringUtil.isNullOrEmpty(MapUtils.getString(par, "id"))){
				i=userService.insertChannelPrice(par);
			}else{
				i=userService.updateChannelPrice(par);
			}
			
			//String str= JSONUtil.objectToString(new TableObject(pagecount, o));
			outString(response, i);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	
	
	
	@RequestMapping("/findChannelPriceAll")//对外接口查询所有设定的支付渠道价格
	public void findChannelPriceAll(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		try {
//		String username = request.getParameter("username");
		Map par = MapsUtil.newHashMap();// getPar(request);
		par = getParameter(request);
		
		outString(response, JSONUtil.objectToString(userService.findChannelPriceAll(par)));
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
