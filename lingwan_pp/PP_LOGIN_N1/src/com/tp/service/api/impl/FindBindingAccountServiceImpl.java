package com.tp.service.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tp.comm.util.MapsUtil;
import org.tp.comm.util.StringUtil;
import org.tp.exception.SException;

import com.tp.dao.TUserLoginChannelDao;
import com.tp.entity.ReturnObj;
import com.tp.msg.ErrorCode;
import com.tp.service.ApiService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月18日 下午2:56:30 
 * 类说明 根据ppid查询绑定帐号
 */
@Service(value="findBindingAccount") 
public class FindBindingAccountServiceImpl implements ApiService{
	 

	private static Logger logger = Logger.getLogger(FindBindingAccountServiceImpl.class);
	

	@Autowired
	private TUserLoginChannelDao tUserLoginChannelDao;


	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED) 
	public   ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		
		String ppid=MapsUtil.getString(map, "ppid");//登录ppid
		int newUserType=0;//0表示新用户，1为老用户
		
		if(StringUtil.isNullOrEmpty(ppid)){
			return new ReturnObj(ErrorCode.ERROR_20008,ErrorCode.ERROR_20001_MSG);
		}
		
		List l = tUserLoginChannelDao.findLoginUserByPPid(ppid);
		Map data = new HashMap();
		data.put("data", l);
		
		return new ReturnObj(ErrorCode.NO_ERROR,"",data);
	}

	
	
	
	
}
