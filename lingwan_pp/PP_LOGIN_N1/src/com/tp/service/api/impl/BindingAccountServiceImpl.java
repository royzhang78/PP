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
@Service(value="bindingAccount") 
public class BindingAccountServiceImpl implements ApiService{
	 

	private static Logger logger = Logger.getLogger(BindingAccountServiceImpl.class);
	
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
		
		String login_name=MapsUtil.getString(map, "login_name");//登录帐号(若三方登录则为三方帐号  直登+三方都是设备号)
		String login_type=MapsUtil.getString(map, "type");//0pp帐号系统,1GS帐号直登，2pp udid直等 
		String ppid=MapsUtil.getString(map, "ppid");//手机唯一标识
		
		if(StringUtil.isNullOrEmpty(login_name)){
			return new ReturnObj(ErrorCode.ERROR_20001,ErrorCode.ERROR_20001_MSG);
		}
		if(StringUtil.isNullOrEmpty(login_type+"")){
			return new ReturnObj(ErrorCode.ERROR_20002,ErrorCode.ERROR_20002_MSG);
		} 
		if(StringUtil.isNullOrEmpty(ppid)){
			return new ReturnObj(ErrorCode.ERROR_20008,ErrorCode.ERROR_20008_MSG);
		}
				
		
		Map tmp =map;
		TUserLoginChannel user=null;
		tmp = JSONUtil.objToMap(map);
		tmp.put("login_name", login_name);
		tmp.put("login_type", login_type);
		user = tUserLoginChannelDao.findTUserLoginChannelDaoById(tmp);
		
		 
		
		
		if(user==null){//如果三方登录不存在 则进行绑定
			
			long sid=System.currentTimeMillis();
			String delid="0";
			String old_login_name="";
			List<Map> data =tUserLoginChannelDao.findLoginUserByPPid(ppid);
			for (Map usermap:data) {
				logger.info("TUserLoginChannel==="+JSONUtil.objectToString(usermap));
				if(MapsUtil.getString(usermap, "login_type").equals("2")){//获取直登方式id绑定后删除
					delid=MapsUtil.getString(usermap, "id");
					sid=MapsUtil.getLong(usermap, "s_id");//获取第一次绑定游戏
					old_login_name=MapsUtil.getString(usermap, "login_name");
				}
				if(MapsUtil.getString(usermap, "login_type").equals(login_type)){//如果相同类型进行绑定则无效
					return new ReturnObj(ErrorCode.ERROR_20007,ErrorCode.ERROR_20007_MSG);
				}
				 
			}
			int status = tUserLoginChannelDao.deleteLoginUserByPPid(delid,old_login_name,2,sid);
//			if(status==1){
				user = tUserLoginChannelDao.findTUserLoginChannelDaoById(map);
				TUserLoginChannel user1=new TUserLoginChannel(Long.valueOf(0), Long.valueOf(ppid), login_name, Integer.parseInt(login_type), "", new Date(),"",sid);			
				tUserLoginChannelDao.insertTUserLoginChannelDaoById(user1);
//			}
		
		}else{
			
			
			return new ReturnObj(ErrorCode.ERROR_20007,ErrorCode.ERROR_20007_MSG);
			
		}
		
		
		return new ReturnObj(ErrorCode.NO_ERROR,"",user);
	}

	
	
	
	
	
}
