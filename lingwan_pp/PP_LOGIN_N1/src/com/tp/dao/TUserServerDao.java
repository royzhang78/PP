package com.tp.dao;

import java.util.Map;
import org.tp.comm.util.MapsUtil;
import org.springframework.stereotype.Service;
import org.tp.comm.SqlXML;
import org.tp.comm.util.DateTimeUtil;
import org.tp.exception.SException;
import org.tp.memcached.MemcachedManager;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="tUserServerDao") 
public class TUserServerDao extends BaseDao{
	public static String tablename="TUserServer";
	
	
	public void insertTUserServerDao(Map data) throws SException{
		
		String time =DateTimeUtil.getDateTime();
		
		data.put("createtime", time);
		data.put("logintime", time);
		SqlXML sqlxml = getSql("insertTUserServer", data);	
		
		getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		
		String u_ppid=MapsUtil.getString(data, "u_ppid");
		String s_id=MapsUtil.getString(data, "s_id");
		String key=tablename+s_id+"_"+u_ppid;
		this.getMemcachedManager().cacheObject(key, s_id+"_"+u_ppid, MemcachedManager.CACHE_EXP_DAY);
	}
	
	
	public int updateTUserServer(Map data) throws SException{
		
		String time =DateTimeUtil.getDateTime();		
		data.put("logintime", time);
		
		SqlXML sqlxml = getSql("updateTUserServer", data);	
		
		return getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
	}
	
	
	public int findUserServerByPPidAndSid(Map data) throws SException{
		String u_ppid=MapsUtil.getString(data, "u_ppid");
		String s_id=MapsUtil.getString(data, "s_id");
		String key=tablename+s_id+"_"+u_ppid;
		Object obj = this.getMemcachedManager().loadObject(key);
		if(obj!=null){
			return 1;
		}
		SqlXML sqlxml = getSql("findUserServerByPPidAndSid", data);		
		int i = getDbManagement().queryForInt(sqlxml.getSearchsql(), sqlxml.getPar());
		if(i>0){
			this.getMemcachedManager().cacheObject(key, s_id+"_"+u_ppid, MemcachedManager.CACHE_EXP_DAY);
		}
		return i;
		
	}
	
}
