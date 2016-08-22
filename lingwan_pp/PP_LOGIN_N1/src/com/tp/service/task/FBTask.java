package com.tp.service.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.tp.comm.util.HttpClientUtil;
import org.tp.comm.util.JSONUtil;
import org.tp.comm.util.MapsUtil;
import org.tp.exception.SException;

import com.tp.comm.Constant;
import com.tp.dao.LoginLogDao;

public class FBTask extends Thread {

	private static final int step = 5000;
	private static  int count=1;
	private LoginLogDao loginLogDao = (LoginLogDao)Constant.CTX.getBean("loginLogDao");
	private static Logger logger = Logger.getLogger(FBTask.class);
	
	@Override
	public void run(){
		
		long s=System.currentTimeMillis();
		while(true){
			int i =0;
			try {
				
				List<Map> data = loginLogDao.findtuserfacebookdata();
				i=data.size();
				logger.info(data.size());
				for (Map map:data) {
					checkfb(MapsUtil.getString(map, "id"), MapsUtil.getString(map, "accesstoken"), MapsUtil.getString(map, "userid"), MapsUtil.getString(map, "u_ppid"));
				}
			} catch (Exception e) {		
				
			}finally{
				
			}
			
			long e=System.currentTimeMillis();
			if(i==0){
				try {
					Thread.sleep(step*count);
					count+=1;
					if(count>6){
						count=6;
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				count=1;
			}
		}
	}
	
	public void checkfb(String id,String accessToken,String userId,String ppid){
		logger.info("id:"+id+" accessToken:"+accessToken+" userId:"+userId);
		Map data=new HashMap();
		Object o[] =verifyFBLogin(accessToken, userId);		
		logger.info("url return:"+JSONUtil.objectToString(o));
		if(Boolean.valueOf(String.valueOf(o[0]))==true){
//						data.put("metadata", o[1]);
			Map facebookMap=JSONUtil.JsonToMap(String.valueOf(o[1]));
//						facebookMap.put(login_name, MapsUtil.getString(data, "login_name"));
			Iterator<String> ii = data.keySet().iterator();
			while(ii.hasNext()){
				String key = ii.next();
				facebookMap.put(key, MapsUtil.getString(data, key));
			}
			if(o!=null && o[1]!=null)
				facebookMap.put("metadata", String.valueOf(o[1]));
			
			facebookMap.put("facebookid", MapsUtil.getString(facebookMap, "id"));
			facebookMap.put("u_ppid", ppid);
			try{
				loginLogDao.insertTUserFaceBook(facebookMap); 
			}catch (Exception e1) {
				try {
					loginLogDao.updateTUserFacebook(facebookMap);
				} catch (SException e) {
					
				} 
				
			}
			
			try {
				data.put("id", id);
				loginLogDao.deltuserfacebookdata(data);
			} catch (SException e) {
				 
			}
			facebookMap.clear();
			facebookMap=null;
			data.clear();
			data=null;
			
			
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
//				logger.info("facebook url");
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
//			logger.error("", e);
		}

		return o;
	}
			 
}
