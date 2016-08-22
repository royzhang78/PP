package com.tp.service.api.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tp.exception.SException;

import com.tp.dao.TUserBindingDao;
import com.tp.entity.ReturnObj;
import com.tp.msg.ErrorCode;
import com.tp.service.ApiService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月18日 下午2:56:30 
 * 类说明 
 */
@Service(value="appBindingService") 
public class APPBindingServiceImpl implements ApiService{
	 

	private static Logger logger = Logger.getLogger(APPBindingServiceImpl.class);
	
	 
	
	@Autowired
	private TUserBindingDao insertbindingDao;
//	@Autowired
//	private PayServerDao payServerDao;

	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED) 
	public synchronized ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		try{
//			#serverName# 传入appid
//			#u_ppid# //u_ppid
//			#serverCode# //serverCode 区服id
//			#createtime#
			map.put("createtime", new Date());
			boolean b = insertBinding(map);//绑定区服
			if(!b){
				return new ReturnObj(ErrorCode.ERROR_500);
			}
		}catch(Exception e){
			return new ReturnObj(ErrorCode.ERROR_500);
		}
		return new ReturnObj(ErrorCode.NO_ERROR);
	}

	
	public boolean insertBinding(Map map) {
		try{
			
			int i =insertbindingDao.insertBinding(map);
			if(i==1){
				return true;
			}
			return false;//"{returnCode:0,returnMsg:\"\"}";	
		}catch (Exception e) {
			 if(e.getMessage().indexOf("Duplicate entry")>-1){
				 return true;// "{returnCode:0,returnMsg:\"\"}";
			 }else{
				logger.error("*** 创建新用户是 绑定区服 ***", e);
				return false;//"{returnCode:-1,returnMsg:'system error'}";
			 }
		}finally{
		}
	}
}
