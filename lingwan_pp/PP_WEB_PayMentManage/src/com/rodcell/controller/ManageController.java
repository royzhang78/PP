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
public class ManageController extends BaseAction{  // extends BaseDao {
	
 
	
	@Autowired
	private UserService userService;

	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		  
//		String username = request.getParameter("username");
		Map par = MapsUtil.newHashMap();// getPar(request);
		par.put("username", request.getParameter("username"));
		par.put("password", request.getParameter("password"));
		try {
			Map m = userService.login(par);
			if(m!=null){
				session.setAttribute("loginUser", m);
				return "main";
			}
		} catch (Exception e) {
			log.error("", e);
		}
		
		return "redirect:/login.htm";     
	}
	
	
}
