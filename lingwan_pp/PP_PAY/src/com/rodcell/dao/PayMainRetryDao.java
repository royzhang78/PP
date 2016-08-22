package com.rodcell.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="payMainRetryDao") 
public class PayMainRetryDao extends BaseDao{
	public static final String tablename="PayMainRetry";
	
	public static final String exe_time="exe_time";
	
	public static final String server_id="server_id";
	
	
	/**
	 * 设置当前服务器执行时间服务器信息
	 * @return 返回需要回调的总记录数
	 * @throws SException 
	 */
	public int setExePostServerValue(int serverid,long exe_time) throws SException{
		Map m = MapsUtil.newHashMap();
		m.put(PayMainRetryDao.server_id, serverid);
		m.put(PayMainRetryDao.exe_time, serverid+""+exe_time);
		SqlXML sqlxml = getSql("updateExePayMainRetry",m );
		return this.getDbManagement().update(sqlxml.getSearchsql(),sqlxml.getPar());
	}
	
	
	/**
	 * 设置当前服务器执行时间服务器信息
	 * @return 返回需要回调的总记录数
	 * @throws SException 
	 */
	public int initupdateExePayMainRetry(int serverid) throws SException{
		Map m = MapsUtil.newHashMap();
		m.put(PayMainRetryDao.server_id, serverid); 
		SqlXML sqlxml = getSql("initupdateExePayMainRetry",m );
		return this.getDbManagement().update(sqlxml.getSearchsql(),sqlxml.getPar());
	}
	 
	
	
	
	public int closeupdateExePayMainRetry(int serverid,long exe_time) throws SException{
		Map m = MapsUtil.newHashMap();
		m.put(PayMainRetryDao.server_id, serverid);
		m.put(PayMainRetryDao.exe_time, serverid+""+exe_time);
		SqlXML sqlxml = getSql("closeupdateExePayMainRetry",m );
		return this.getDbManagement().update(sqlxml.getSearchsql(),sqlxml.getPar());
	}
	/**
	 * 查询当前执行数据
	 * @param serverid
	 * @param exe_time
	 * @return
	 * @throws SException
	 */
	public List<PayMainRetry> getExePostServerValue(int serverid,long exe_time,int start,int end) throws SException{
		Map m = MapsUtil.newHashMap();
		m.put(PayMainRetryDao.exe_time, serverid+""+exe_time);
		m.put("end", end);
		m.put("start", start);
		SqlXML sqlxml = getSql("findExePayMainRetryList",m );
		return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(),PayMainRetry.class,sqlxml.getPar());
	}

	/**
	 * 插入回调服务器
	 * @param retry
	 * @return
	 * @throws SException
	 */
	public boolean inserPayMainRetry(PayMainRetry retry) throws SException{
		if(retry!=null){
			Map m = JSONUtil.objToMap(retry);
			SqlXML sqlxml = getSql("inserPayMainRetry",m );
			
				int i = this.getDbManagement().update(sqlxml.getSearchsql(),sqlxml.getPar());
				if(i>0){
//					this.getMemcachedManager().cacheObject(tablename+retry.getPay_id(), JSONUtil.objectToString(retry), this.getMemcachedManager().CACHE_EXP_DAY_2);
					return true;
				}
			
		}
		return false;
	}
	
	
	
	public int updatePayMainRetry(PayMainRetry retry) throws SException{
		int i = 0 ;
		if(retry!=null){
			Map m = JSONUtil.objToMap(retry);
			SqlXML sqlxml = getSql("updatePayMainRetry",m );
			
				 i = this.getDbManagement().update(sqlxml.getSearchsql(),sqlxml.getPar());
				if(i>0){
					this.getMemcachedManager().cacheObject(tablename+retry.getPay_id(), JSONUtil.objectToString(retry), this.getMemcachedManager().CACHE_EXP_DAY_2);
					return i;
				}
			
		}
		return i;
	}
	
	
	public boolean deletePayMainRetry(PayMainRetry retry) throws SException{
		if(retry!=null){
			Map m = JSONUtil.objToMap(retry);
			SqlXML sqlxml = getSql("deletePayMainRetry",m );
			
				int i = this.getDbManagement().update(sqlxml.getSearchsql(),sqlxml.getPar());
				if(i>0){
					this.getMemcachedManager().flushObject(tablename+retry.getPay_id());
					return true;
				}
			
		}
		return false;
	}
	
}
