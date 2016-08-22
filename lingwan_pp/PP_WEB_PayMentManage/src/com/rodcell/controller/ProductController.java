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
import com.rodcell.message.ErrorCode;
import com.rodcell.service.TestDB;
import com.rodcell.service.UserService;
import com.rodcell.servlet.BaseAction;


@Controller
//@RestController
public class ProductController extends BaseAction{  // extends BaseDao {
	
 
	
	@Autowired
	private UserService userService;

	
	
	@RequestMapping("/findProduct")
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
			Object o = userService.findProduct(par);
			
			
			int count = userService.findProductCount(par);
			int pagecount = pagesLen(count,pagesize);
			
			String str= JSONUtil.objectToString(new TableObject(pagecount, o));
			outString(response, str);
		} catch (Exception e) {
			log.error("", e);
		}
		
		
	}
	 
	@RequestMapping("/findProductById")
	public void findProductById(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		try {
//		String username = request.getParameter("username");
		Map par = MapsUtil.newHashMap();// getPar(request);
		par = getParameter(request);
		
			Object o = userService.findProductById(String.valueOf(par.get("id")));
			
			String str= JSONUtil.objectToString(o);
			outString(response, str);
		} catch (Exception e) {
			log.error("", e);
		}
		
		
	}
	
	
	@RequestMapping("/saveProduct")
	public void insertProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		try {
//		String username = request.getParameter("username");
		Map par = MapsUtil.newHashMap();// getPar(request);
		par = getParameter(request);
		
			
			long i =0;
			String tmp =  MapsUtil.getString(par, "product_price");
			Map product_parameter = JSONUtil.JsonToMap(MapsUtil.getString(par, "product_parameter"));
			String pay_channel_type = MapsUtil.getString(product_parameter, "type");
			par.put("pay_channel_type", pay_channel_type);
//			par.put("product_price", product_price);//解析key
//			if(StringUtil.isNullOrEmpty(tmp)||!tmp.equals(product_price)){//价格不一致
//				i=Long.valueOf(ErrorCode.PAY_PRODUCT_PRICE_ERROR);
//			}else{
//				
//			}
			
			if(StringUtil.isNullOrEmpty(MapUtils.getString(par, "product_id"))){
				i=userService.insertProduct(par);
			}else{
				i=userService.updateProduct(par);
			}
			
			//String str= JSONUtil.objectToString(new TableObject(pagecount, o));
			outString(response, i);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	public static void main(String[] args) {
		Map product_parameter = (Map)JSONUtil.JsonToObject("{'key':'5','value':[40],'type':'0'}", Map.class);
		String product_price = MapsUtil.getString(product_parameter, "value");
		System.out.println(product_price);;
	}
}
