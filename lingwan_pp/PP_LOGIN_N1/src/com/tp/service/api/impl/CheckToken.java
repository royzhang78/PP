package com.tp.service.api.impl;

import java.util.Date;
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
import org.tp.comm.util.IDGenerate;
import org.tp.comm.util.JSONUtil;
import org.tp.comm.util.MapsUtil;
import org.tp.comm.util.StringUtil;
import org.tp.exception.SException;

import com.tp.dao.PayServerDao;
import com.tp.dao.TUserBaseDao;
import com.tp.dao.TUserLoginChannelDao;
import com.tp.dao.TUserServerDao;
import com.tp.entity.ReturnObj;
import com.tp.entity.TUserLoginChannel;
import com.tp.msg.ErrorCode;
import com.tp.service.ApiService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月18日 下午2:56:30 
 * 类说明 三方帐号绑定
 */
@Service(value="CheckToken") 
public class CheckToken implements ApiService{
	 

	private static Logger logger = Logger.getLogger(CheckToken.class);
	
	@Autowired
	private PayServerDao payServerDao;
	@Autowired
	private TUserLoginChannelDao tUserLoginChannelDao;
	@Autowired
	private TUserBaseDao tUserBaseDao;
	
	@Autowired
	private TUserServerDao tUserServerDao;
	
	@Autowired
	private IDGenerate idGenerate;

	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED) 
	public   ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		
		String token=MapsUtil.getString(map, "token");
		 
		String u_ppid=MapsUtil.getString(map, "u_ppid");//手机唯一标识
		
	 
		if(StringUtil.isNullOrEmpty(token+"")){
			return new ReturnObj(ErrorCode.ERROR_20002,ErrorCode.ERROR_20002_MSG);
		} 
		if(StringUtil.isNullOrEmpty(u_ppid)){
			return new ReturnObj(ErrorCode.ERROR_20008,ErrorCode.ERROR_20008_MSG);
		}
				
		Map user = tUserBaseDao.findTUserBaseByToken(map);
		if(user==null || user.size()==0){
			return new ReturnObj(ErrorCode.ERROR_20007,"",user);
		}
		
		return new ReturnObj(ErrorCode.NO_ERROR,"",user);
	}

	
	
	
	
	
}
