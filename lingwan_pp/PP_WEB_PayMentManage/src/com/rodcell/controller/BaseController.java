package com.rodcell.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rodcell.servlet.BaseAction;


@Controller
public class BaseController extends BaseAction{  // extends BaseDao {
	 
	 
	
//	@RequestMapping("**")  
//    public String isnull(@PathVariable("*") String id,HttpServletRequest request, HttpServletResponse response, HttpSession session) {  
//		try {
//			response.setStatus(404);
////			response.getWriter().write("404 error !"+id);
//			outString(response, "404 error !"+id);
//		} catch (Exception e) {
//			log.error("", e);
//		}
//		return null;
//    } 
//	
	
}
