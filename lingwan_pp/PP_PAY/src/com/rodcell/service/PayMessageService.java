package com.rodcell.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月20日 上午11:03:33 
 * 类说明 
 */
public interface PayMessageService {
	public Map service(long channelid,Map par,HttpServletRequest request, HttpServletResponse response, HttpSession session)throws SException ;

}
