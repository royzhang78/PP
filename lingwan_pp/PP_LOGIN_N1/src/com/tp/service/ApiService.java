package com.tp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tp.exception.SException;

import com.tp.entity.ReturnObj;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月11日 下午2:55:06 
 * 类说明 
 */
public interface ApiService {
	
	public  ReturnObj service(Map map ,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SException;


}
