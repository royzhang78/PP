package com.tp.service.api.impl;

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

import com.tp.dao.PayServerDao;
import com.tp.dao.TChannelTrackingDao;
import com.tp.entity.PayServer;
import com.tp.entity.ReturnObj;
import com.tp.msg.ErrorCode;
import com.tp.service.ApiService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月18日 下午2:56:30 
 * 类说明 
 */
@Service(value="ChannelTrackService") 
public class ChannelTrackServiceImpl implements ApiService{
	 

	private static Logger logger = Logger.getLogger(ChannelTrackServiceImpl.class);
	
	 
	
	@Autowired
	private TChannelTrackingDao tchannelTrackingDao;
	@Autowired
	private PayServerDao payServerDao;

	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED) 
	public synchronized ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		try{
			PayServer  p = payServerDao.findPayServerByName(String.valueOf(map.get("g_id")));
			map.put("g_id", p.getS_id()+"");
			boolean b = userChannelTracking(map);
			if(!b){
				return new ReturnObj(ErrorCode.ERROR_500);
			}
		}catch(Exception e){
			return new ReturnObj(ErrorCode.ERROR_500);
		}
		return new ReturnObj(ErrorCode.NO_ERROR);
	}

	
	public boolean userChannelTracking(Map map) {
		try{
			
			int i =tchannelTrackingDao.insertTChannelTracking(map);
			if(i==1){
				return true;
			}
			return false;//"{returnCode:0,returnMsg:\"\"}";	
		}catch (Exception e) {
			 if(e.getMessage().indexOf("Duplicate entry")>-1){
				 return true;// "{returnCode:0,returnMsg:\"\"}";
			 }else{
				logger.error("*** 创建新用户是 渠道跟踪信息 ***", e);
				return false;//"{returnCode:-1,returnMsg:'system error'}";
			 }
		}finally{
		}
	}
}
