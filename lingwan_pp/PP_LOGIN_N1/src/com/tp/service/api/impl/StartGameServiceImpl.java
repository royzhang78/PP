package com.tp.service.api.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tp.comm.util.HttpClientUtil;
import org.tp.comm.util.IDGenerate;
import org.tp.comm.util.IPUtil;
import org.tp.comm.util.JSONUtil;
import org.tp.comm.util.MD5Util;
import org.tp.comm.util.MapsUtil;
import org.tp.comm.util.StringUtil;
import org.tp.exception.SException;

import com.tp.comm.Constant;
import com.tp.dao.LoginLogDao;
import com.tp.dao.PayServerDao;
import com.tp.dao.TUserBaseDao;
import com.tp.dao.TUserLoginChannelDao;
import com.tp.dao.TUserServerDao;
import com.tp.entity.PayServer;
import com.tp.entity.ReturnObj;
import com.tp.entity.TUserLoginChannel;
import com.tp.msg.ErrorCode;
import com.tp.service.ApiService;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月18日 下午2:56:30 
 * 类说明 
 */
@Service(value="startGameJson") 
public class StartGameServiceImpl implements ApiService{
	 

	private static Logger logger = Logger.getLogger(StartGameServiceImpl.class);
	
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
	@Autowired
	private LoginLogDao loginLogDao;
	
	
	private static Map<String, String> SID_LOG_MAP=new Hashtable();

	@Override
	@Transactional(rollbackFor=SException.class,propagation=Propagation.REQUIRED) 
	public   ReturnObj service(Map map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws SException {
		
		String login_name=MapsUtil.getString(map, "login_name");//登录帐号(若三方登录则为三方帐号  直登+三方都是设备号)
		String login_type=MapsUtil.getString(map, "login_type");//0pp帐号系统,1GS帐号直登，2pp udid直等 
		String u_deviceid=MapsUtil.getString(map, "u_deviceid");//手机唯一标识
		String u_releaseChannel=MapsUtil.getString(map, "u_releaseChannel");//发布渠道
		String serverName=MapsUtil.getString(map, new String[]{"gameId","serverName","gameName"});//登录服务器名称
		String locale=MapsUtil.getString(map, "locale");//客户端语种
		String clientType=MapsUtil.getString(map, "clientType");//客户端类型
		String login_password=MapsUtil.getString(map, "login_password");//帐号密码（登录方式为pp系统时使用）
		
		String accessToken=MapsUtil.getString(map, "accessToken");//第三方登录成功后获取的访问token
		String userId=MapsUtil.getString(map, "userId");//第三方登录帐号
		String userloginType=MapsUtil.getString(map, "userloginType");//第三方登录类型 3为facebook  ,Google4,ios5
		String ppid="";
		String login_ip=IPUtil.getIpAddr(request);
		map.put("login_ip", login_ip);
		int newUserType=0;//0表示新用户，1为老用户
		
		if(StringUtil.isNullOrEmpty(login_name)){
			return new ReturnObj(ErrorCode.ERROR_20001,ErrorCode.ERROR_20001_MSG);
		}else if(StringUtil.isNullOrEmpty(login_type+"")){
			return new ReturnObj(ErrorCode.ERROR_20002,ErrorCode.ERROR_20002_MSG);
		}else if(StringUtil.isNullOrEmpty(u_deviceid)){
			return new ReturnObj(ErrorCode.ERROR_20003,ErrorCode.ERROR_20003_MSG);
		}else if(StringUtil.isNullOrEmpty(serverName)){
			return new ReturnObj(ErrorCode.ERROR_20004,ErrorCode.ERROR_20004_MSG);
		}


				
		PayServer server =payServerDao.findPayServerByName(serverName);

		if(server==null || server.getS_status()!=2){//登录服务器不存在
			return new ReturnObj(ErrorCode.ERROR_20005,ErrorCode.ERROR_20005_MSG);
		}
		map.put("s_id", server.getS_id()+"");
		
		Map tmp =JSONUtil.objToMap(map);
		TUserLoginChannel user=null;
		String u_info="";
		if(!StringUtil.isNullOrEmpty(userId)){//判断是否使用三方登录帐号
			login_name+="."+userId;
			map.put("login_name", login_name);
			if ("3".equals(userloginType)) {
				Object o1[]=verifyFBLogin(accessToken, userId);
				map.put("u_info", o1[1]);
				boolean bb = (Boolean)(o1[0]);
				if(!bb){
					return new ReturnObj(ErrorCode.ERROR_20006,ErrorCode.ERROR_20006_MSG);
				}
			}
			tmp = JSONUtil.objToMap(map);
			tmp.put("login_name", userId);
			tmp.put("login_type", userloginType);
			user = tUserLoginChannelDao.findTUserLoginChannelDaoById(tmp);
			tmp.clear();
			tmp=null;
		}
		


		
		if(user==null)//如果三方登录不存在 查询原接口协议登录
			user = tUserLoginChannelDao.findTUserLoginChannelDaoById(map);


		if(user ==null){//如果用户不存在则创建
			
			Long u_ppid=Long.valueOf(Constant.serverkey+""+idGenerate.getId());//创建ppid
			map.put("u_ppid", u_ppid);
			
			ppid=u_ppid+"";
			user=new TUserLoginChannel(Long.valueOf(0), u_ppid, login_name, Integer.parseInt(login_type), login_password, new Date(),u_info,server.getS_id());	
			
			tUserLoginChannelDao.insertTUserLoginChannelDaoById(user);
			
			
			tUserBaseDao.insertTUserBaseDao(map);
			tUserServerDao.insertTUserServerDao(map);
			
		 
			
			if(!StringUtil.isNullOrEmpty(userId)){//判断是否使用三方登录帐号 则绑定三方登录
				TUserLoginChannel user1=new TUserLoginChannel(Long.valueOf(0), u_ppid, userId, Integer.parseInt(userloginType), login_password, new Date(),u_info,server.getS_id());			
				tUserLoginChannelDao.insertTUserLoginChannelDaoById(user1);
				user=user1;
			}
				
			
		}else{
			newUserType=1;
			map.put("u_ppid", user.getU_ppid());
			ppid=user.getU_ppid()+"";
			int i =tUserServerDao.findUserServerByPPidAndSid(map);
			if(i==0){
				tUserServerDao.insertTUserServerDao(map);
			}else{
				tUserServerDao.updateTUserServer(map);
			}
			
			tUserBaseDao.updateTUserBase(map);
			i =tUserBaseDao.findTUserBaseByPPidAndStatus(map);
			
//			if(!StringUtil.isNullOrEmpty(userId)){//判断是否使用三方登录帐号 则绑定三方登录
//				
//				tmp = JSONUtil.objToMap(map);
//				tmp.put("login_name", userId);
//				tmp.put("login_type", userloginType);
//				TUserLoginChannel user1 = tUserLoginChannelDao.findTUserLoginChannelDaoById(tmp);
//				
//				if(user1==null){
//					user1=new TUserLoginChannel(Long.valueOf(0), user.getU_ppid(), userId, Integer.parseInt(userloginType), login_password, new Date(),u_info,server.getS_id());			
//					tUserLoginChannelDao.insertTUserLoginChannelDaoById(user1);
//				}
//			}
			
			
			if(i!=1){//用户帐号状态不正确
				
//				logger.info("=================11111111111111========");
				return new ReturnObj(ErrorCode.ERROR_20007,ErrorCode.ERROR_20007_MSG);
			}
			
			
		}
		
		if(user==null ){		
//			logger.info("=================2222222========");
			return new ReturnObj(ErrorCode.ERROR_20007,ErrorCode.ERROR_20007_MSG);
		}
		
		
		Map userMap = JSONUtil.objToMap(user);		
		userMap.put("newUserType", newUserType);//返回新老用户标识
		try{
//			Constant.queue.offer((ConcurrentHashMap) JSONUtil.mapToObject(map, ConcurrentHashMap.class));
//			Server.pool.execute(new LogServiceImpl(map));//进行日志操作
			
			if ("3".equals(userloginType)) {//3为facebook
				if(accessToken!=null &&!"".equals(accessToken))
				{
					map.put("u_ppid", ppid);
					loginLogDao.inserttuserfacebookdata(map);
				}
			}
			String tmplog=SID_LOG_MAP.get(server.getS_id()+"");
			if(tmplog==null || "".equals(tmplog)){
				SID_LOG_MAP.put(server.getS_id()+"", server.getS_id()+"");
				loginLogDao.createloginlogDao(map);
				
			}
			loginLogDao.insertTUserBaseDao(map); 
			
		}catch(Exception e){
			logger.error("insert into log error ", e);
		}
		userMap.remove("login_password");
		if ("3".equals(userloginType)) {//3为facebook
			userMap.put("login_type", userloginType);
		}
		
		long timestamp=System.currentTimeMillis();
		
		userMap.put("timestamp", timestamp);
		String md5= timestamp+""+user.getU_ppid()+""+server.getS_key();
		md5=MD5Util.getMD5String(md5);
		userMap.put("md5", md5);
		userMap.put("token", md5);
		try{
			map.put("token", md5);
			map.put("u_ppid", user.getU_ppid());
			int i = tUserBaseDao.updatet_user_token(map);
			if(i==0){
				tUserBaseDao.insertt_user_token(map);
			}
		}catch(Exception e){
			logger.error("insert into log error ", e);
		}
		return new ReturnObj(ErrorCode.NO_ERROR,"",userMap);
	}

	
	
	
	
	
	/**
	 * 验证新浪AccessToken是否正确
	 * 
	 * @return
	 */
//	protected static boolean validateSinaToken(String accessToken, String userId) {
//		
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("access_token", accessToken);
//		HttpClientUtil httpClient = new HttpClientUtil();
//		String createResponse=null;
//		try {
//			createResponse = httpClient.post("https://api.weibo.com/oauth2/get_token_info", params,"UTF-8");
//		} catch (Exception e) {
//			logger.error("", e);
//		}
//		logger.info("Sina return response:" + createResponse);
//		if (null != createResponse && !createResponse.isEmpty()) {
//			JSONObject json = JSONObject.fromObject(createResponse);
//			if (json != null) {
//				String result = json.optString("uid");
//				if (null != result && result.equals(userId)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	
	/**
	 * 腾讯QQaccessToken验证
	 * 
	 * @param userId
	 * @param accessToken
	 * @param u
	 * @return
	 */
//	protected static boolean validateQQToken(String accessToken, String userId) {
//	
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("access_token", accessToken);
//		HttpClientUtil httpClient = new HttpClientUtil();
//		String createResponse=null;
//		try {
//			createResponse = httpClient.post("https://graph.qq.com/oauth2.0/me", params,"UTF-8");
//		} catch (Exception e) {
//			logger.error("", e);
//		}
//		logger.info("QQ return response:" + createResponse);
//		if (null != createResponse && !createResponse.isEmpty() && createResponse.contains("{")) {
//			createResponse = createResponse.substring(createResponse.indexOf("{"), createResponse.lastIndexOf("}") + 1);
//			JSONObject json = JSONObject.fromObject(createResponse);
//			if (json != null) {
//				String result = json.optString("openid");
//				if (null != result && result.equals(userId)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

	/**
	 * vk token验证
	 * 
	 * @param userId
	 * @param accessToken
	 * @param u
	 * @return
	 */
//	protected static boolean validateVkToken(String accessToken, String userId) {
//		
//		HttpClientUtil httpClient = new HttpClientUtil();
//		StringBuffer str = new StringBuffer();
//		str.append("https://api.vk.com/method/getProfiles");
//		str.append("?uid=" + userId);
//		str.append("&access_token=" + accessToken);
//		String ret=null;
//		try {
//			ret = httpClient.get(str.toString(),"UTF-8");
//		} catch (Exception e) {
//			logger.error("", e);
//		}
//		logger.info("vk token response:" + ret);
//		if (null != ret && !ret.isEmpty() && !ret.contains("error_code")) {
//			return true;
//		}
//		return false;
//	}
	
	/**
	 * weixin验证
	 * @param accessToken
	 * @param userId
	 * @param u
	 * @return
	 */
//	protected static boolean validateWXToken(String accessToken, String userId) {
//		
//		HttpClientUtil httpClient = new HttpClientUtil();
//		StringBuffer str = new StringBuffer();
//		str.append("https://api.weixin.qq.com/sns/userinfo");
//		str.append("?access_token=" + accessToken );
//		str.append("&openid=" + userId);
//		String ret=null;
//		try {
//			ret = httpClient.get(str.toString(),"UTF-8");
//		} catch (Exception e) {
//			logger.error("", e);
//		}
//		logger.info("weixin token response:" + ret);
//		if (null != ret && !ret.isEmpty()){
//			JSONObject json = JSONObject.fromObject(ret);
//			String result = json.optString("openid");
//			if (null != result && result.equals(userId)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	
	/**
	 * 验证facebook用户token是否正确
	 * 
	 * @param accessToken
	 * @param userId
	 * @param u
	 * @return
	 */
	protected static Object[] verifyFBLogin(String accessToken, String userId) {
		Object[] o = new Object [2];
		o[0]=false;
		Map<String, String> para = new HashMap<String, String>();
		
//		HttpClientUtil httpClient = new HttpClientUtil();
		String ret="";
		try {
//			 httpClient.post("https://graph.facebook.com/" + userId, para,"UTF-8");
			 
			 para.put("access_token", accessToken);
//			 para.put("fields", "id");
//			ret = httpClient.get("https://graph.facebook.com/me?metadata=1&access_token="+accessToken+"&fields=age_range,id,first_name,gender,last_name,link,name,about,address,bio,birthday,currency,devices,education,email,favorite_athletes,favorite_teams,hometown,inspirational_people,install_type,installed,interested_in,is_verified,languages,location,locale,meeting_for,middle_name,name_format,payment_pricepoints,test_group,political,relationship_status,religion,security_settings,sports,significant_other,quotes,third_party_id,timezone,updated_time,verified,video_upload_limits,viewer_can_send_gift,website,work,cover" , "UTF-8");
			o[1]=ret;
			o[0]=true;
		} catch (Exception e) {
			o[1]="";
			o[0]=false;
			logger.error("", e);
		}
		if ("true".equals(ret)||ret != null && ret.indexOf(userId)>0) {
//			String url = "https://graph.facebook.com/me";
//			try {
//				String r = httpClient.post(url, para, "utf-8");
//			} catch (Exception e) {
//				
//			} 
			o[0]=true;
			
		}
		return o;
	}
}
