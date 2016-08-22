package com.tp.service.impl;

import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.tp.comm.util.HttpClientUtil;
import org.tp.comm.util.JSONUtil;
import org.tp.comm.util.MapsUtil;

import com.tp.comm.Constant;
import com.tp.dao.LoginLogDao;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.net.SocketHttpConnection;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月18日 下午6:52:08 
 * 类说明 
 */
public class LogServiceQueueImpl extends Thread{

	private static Logger logger = Logger.getLogger(LogServiceQueueImpl.class);
	
	private LoginLogDao loginLogDao = (LoginLogDao)Constant.CTX.getBean("loginLogDao");
	
	
//	public LogServiceQueueImpl(Map data) {
//		super();
//		this.data = data;
//	}



	private Map data;
	
	

	public Map getData() {
		return data;
	}



	public void setData(Map data) {
		this.data = data;
	}



	@Override
	public void run(){
		
		while(true){
			try {
				
				data = Constant.queue.take();
				
				if(data!=null){
					String login_name=MapsUtil.getString(data, "login_name");//登录帐号
					String login_type=MapsUtil.getString(data, "login_type");//0pp帐号系统,1GS帐号直登，2pp udid直等
					String u_deviceid=MapsUtil.getString(data, "u_deviceid");//手机唯一标识
					String u_releaseChannel=MapsUtil.getString(data, "u_releaseChannel");//发布渠道
					String serverName=MapsUtil.getString(data, "serverName");//登录服务器名称
					String locale=MapsUtil.getString(data, "locale");//客户端语种
					String clientType=MapsUtil.getString(data, "clientType");//客户端类型
					String login_password=MapsUtil.getString(data, "login_password");//帐号密码（登录方式为pp系统时使用）
					
					String accessToken=MapsUtil.getString(data, "accessToken");//第三方登录成功后获取的访问token
					String userId=MapsUtil.getString(data, "userId");//第三方登录帐号
					String userloginType=MapsUtil.getString(data, "userloginType");//第三方登录类型 3为facebook
					
					Map facebookMap = new HashMap();
					Object o[]=null;
					if ("3".equals(userloginType)) {//3为facebook
						loginLogDao.inserttuserfacebookdata(data);
					}
					
//					if ("3".equals(userloginType)) {//3为facebook
//						o=verifyFBLogin(accessToken, userId);		
//						if(Boolean.valueOf(String.valueOf(o[0]))==true){
//	//						data.put("metadata", o[1]);
//							facebookMap=JSONUtil.JsonToMap(String.valueOf(o[1]));
//	//						facebookMap.put(login_name, MapsUtil.getString(data, "login_name"));
//							Iterator<String> ii = data.keySet().iterator();
//							while(ii.hasNext()){
//								String key = ii.next();
//								facebookMap.put(key, MapsUtil.getString(data, key));
//							}
//							if(o!=null && o[1]!=null)
//								facebookMap.put("metadata", String.valueOf(o[1]));
//							
//							facebookMap.put("facebookid", MapsUtil.getString(data, "id"));
//						}
//					}
					
//					logger.info(JSONUtil.objectToString(facebookMap));
					
					loginLogDao.createloginlogDao(data);
					loginLogDao.insertTUserBaseDao(data); 
//					if(o!=null && Boolean.valueOf(String.valueOf(o[0]))==true){
//						if ("3".equals(userloginType)) {
////							try{
////								loginLogDao.insertTUserFaceBook(facebookMap); 
////							}catch (Exception e1) {		
////								
////								loginLogDao.updateTUserFacebook(facebookMap); 
////								
////							}
//						}
//					}
					facebookMap.clear();
					facebookMap=null;
				}
			} catch (Exception e) {				 
				logger.error("", e);
			}finally{
//				try {
//					this.wait();
//				} catch (InterruptedException e) {
//					logger.error("", e);
//				}
			}
			
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			
		}
			
	}
	
	
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
		
		HttpClientUtil httpClient = new HttpClientUtil();
		String ret="";
		try {
//			 httpClient.post("https://graph.facebook.com/" + userId, para,"UTF-8");
			 
			 para.put("access_token", accessToken);
//			 para.put("fields", "id");
			ret = httpClient.get("https://graph.facebook.com/me?metadata=1&access_token="+accessToken+"&fields=age_range,id,first_name,gender,last_name,link,name,about,address,bio,birthday,currency,devices,education,email,favorite_athletes,favorite_teams,hometown,inspirational_people,install_type,installed,interested_in,is_verified,languages,location,locale,meeting_for,middle_name,name_format,payment_pricepoints,test_group,political,relationship_status,religion,security_settings,sports,significant_other,quotes,third_party_id,timezone,updated_time,verified,video_upload_limits,viewer_can_send_gift,website,work,cover" , false);
//			ret=get("https://graph.facebook.com/me?metadata=1&access_token="+accessToken+"&fields=age_range,id,first_name,gender,last_name,link,name,about,address,bio,birthday,currency,devices,education,email,favorite_athletes,favorite_teams,hometown,inspirational_people,install_type,installed,interested_in,is_verified,languages,location,locale,meeting_for,middle_name,name_format,payment_pricepoints,test_group,political,relationship_status,religion,security_settings,sports,significant_other,quotes,third_party_id,timezone,updated_time,verified,video_upload_limits,viewer_can_send_gift,website,work,cover" );
			o[1]=ret;
			o[0]=true;
			if(ret!=null && !"".equals(ret)){
				logger.info("facebook url");
			}
			if ("true".equals(ret)||ret != null && ret.indexOf(userId)>0) {
//				String url = "https://graph.facebook.com/me";
//				try {
//					String r = httpClient.post(url, para, "utf-8");
//				} catch (Exception e) {
//					
//				} 
				o[0]=true;
				
			}
		} catch (Exception e) {
			o[1]="";
			o[0]=false;
			logger.error("", e);
		}

		return o;
	}
	
	
	public static  String get(String url){
		HttpRequest q =HttpRequest
		        .get(url);
		
		SocketHttpConnection httpConnection =
		        (SocketHttpConnection) q.httpConnection();
		    Socket socket = httpConnection.getSocket();
		    try {
				socket.setSoTimeout(3000);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		HttpResponse response =q.send();
		String s = response.bodyText();
		response.close();
		return s;
	}
	
}
