package com.tp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.tp.comm.SqlXML;
import org.tp.comm.util.JSONUtil;
import org.tp.comm.util.MapsUtil;
import org.tp.exception.SException;
import org.tp.memcached.MemcachedManager;

import com.tp.entity.TUserLoginChannel;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="tUserLoginChannelDao") 
public class TUserLoginChannelDao extends BaseDao{
	public static String tablename="TUserLoginChannel";
	
	 
	public TUserLoginChannel findTUserLoginChannelDaoById(Map data) throws SException{
		TUserLoginChannel tuserloginchannel = null;
		String key=tablename;
//		try {
//			String login_name=MapsUtil.getString(data, "login_name");//登录帐号
//			int login_type=MapsUtil.getInteger(data, "login_type");//0pp帐号系统,1GS帐号直登，2pp udid直等，3facebook
//			key+=login_type+"_"+login_name;
//			if(login_type==1||login_type==2){//直登方式需要绑定服务器id
//				key+=MapsUtil.getString(data, "s_id");
//			}
//			
//			tuserloginchannel =(TUserLoginChannel)JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(key),TUserLoginChannel.class);
//		} catch (Exception e) {
////			log.error("",e);
//		}
		if(tuserloginchannel==null){
			
			SqlXML sqlxml = getSql("findLoginUser", data);
			
			tuserloginchannel = getDbManagement().queryForBean(sqlxml.getSearchsql(), TUserLoginChannel.class, sqlxml.getPar());
//			if(tuserloginchannel!=null){
//				this.getMemcachedManager().cacheObject(key, JSONUtil.objectToString(tuserloginchannel), MemcachedManager.CACHE_EXP_DAY);
//			}
			
		}
		return tuserloginchannel;
	}
	
	public void insertTUserLoginChannelDaoById(TUserLoginChannel tuserloginchannel) throws SException{
		Map data = JSONUtil.objToMap(tuserloginchannel);
		SqlXML sqlxml = getSql("insertLoginUserChannel", data);		
		getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
		String key=tablename;
		try {
			String login_name=MapsUtil.getString(data, "login_name");//登录帐号
			int login_type=MapsUtil.getInteger(data, "login_type");//0pp帐号系统,1GS帐号直登，2pp udid直等，3facebook
			key+=login_type+"_"+login_name;
			if(login_type==1||login_type==2){//直登方式需要绑定服务器id
				key+=MapsUtil.getString(data, "s_id");
			}
			this.getMemcachedManager().cacheObject(key, JSONUtil.objectToString(tuserloginchannel), MemcachedManager.CACHE_EXP_DAY);
		} catch (Exception e) {
			log.error("",e);
		}
		
	}
	

	  public int deleteLoginUserByPPid(String id, String login_name, int login_type, long s_id) throws SException
	  {
	    Map data = new HashMap();
	    data.put("id", id);
	    SqlXML sqlxml = getSql("deleteLoginUserByPPid", data);
	    int i = getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
	    if (i > 0) {
	      String key = tablename;

	      key = key + login_type + "_" + login_name;
	      if ((login_type == 1) || (login_type == 2)) {
	        key = key + s_id;
	      }
	      getMemcachedManager().flushObject(key);
	    }

	    return i;
	  }

	  public List findLoginUserByPPid(String ppid) throws SException {
	    Map data = new HashMap();
	    data.put("u_ppid", ppid);
	    SqlXML sqlxml = getSql("findLoginUserByPPid", data);
	    List l = getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class, sqlxml.getPar());
	    return l;
	  }
	
}
