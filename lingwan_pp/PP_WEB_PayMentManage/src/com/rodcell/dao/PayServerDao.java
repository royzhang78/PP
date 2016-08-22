package com.rodcell.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.entity.PayServer;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="payServerDao") 
public class PayServerDao extends BaseDao{
	public static String tablename="PayServer";
	
	
	public List<PayServer> findAll(){
		SqlXML sqlxml = getSql("findAllPayServer", MapsUtil.newHashMap());
		try {
			return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), PayServer.class);
		} catch (SException e) {
			log.error("", e);
			return null;
		}
	}
	
	@Override
	public void init(){
		List<PayServer> list =  findAll();
		for (PayServer data:list) {
			this.getMemcachedManager().cacheObject(tablename+data.getS_id(), JSONUtil.objectToString(data), this.getMemcachedManager().CACHE_EXP_FOREVER);
			this.getMemcachedManager().cacheObject(tablename+data.getS_name(), JSONUtil.objectToString(data), this.getMemcachedManager().CACHE_EXP_FOREVER);
		}
	}
	
	public PayServer findPayServerById(String id){
		PayServer payServer = null;
		try {
			payServer =(PayServer)JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(tablename+id),PayServer.class);
		} catch (Exception e) {
			log.error("",e);
		}
		if(payServer==null){
			Map m = MapsUtil.newHashMap();
			m.put("s_id", id);
			SqlXML sqlxml = getSql("findPayServerById", m);
			try {
				payServer = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), PayServer.class,sqlxml.getPar());
				if(payServer!=null){
					this.getMemcachedManager().cacheObject(tablename+payServer.getS_id(), JSONUtil.objectToString(payServer), this.getMemcachedManager().CACHE_EXP_FOREVER);
					this.getMemcachedManager().cacheObject(tablename+payServer.getS_name(), JSONUtil.objectToString(payServer), this.getMemcachedManager().CACHE_EXP_FOREVER);
					
				}
			} catch (SException e) {
				log.error("",e);
			}
		}
		return payServer;
	}
	
	
	public PayServer findPayServerByName(String name){
		PayServer payServer = null;
		try {
			payServer =(PayServer)JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(tablename+name),PayServer.class);
		} catch (Exception e) {
			log.error("",e);
		}
		if(payServer==null){
			Map m = MapsUtil.newHashMap();
			m.put("s_name", name);
			SqlXML sqlxml = getSql("findPayServerByName", m);
			try {
				payServer = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), PayServer.class,sqlxml.getPar());
				if(payServer!=null){
					this.getMemcachedManager().cacheObject(tablename+payServer.getS_name(), JSONUtil.objectToString(payServer), this.getMemcachedManager().CACHE_EXP_FOREVER);
					this.getMemcachedManager().cacheObject(tablename+payServer.getS_id(), JSONUtil.objectToString(payServer), this.getMemcachedManager().CACHE_EXP_FOREVER);
					
				}
			} catch (SException e) {
				log.error("",e);
			}
		}
		return payServer;
	}
	
	
	public long insert(Map par) throws SException{
//		Map m = JSONUtil.objToMap(payProduct);
		SqlXML sqlxml = getSql("insertServer", par);
		
		long i = this.getDbManagement().insertAndGetKey(sqlxml.getSearchsql(), sqlxml.getPar());
		par.put("s_id", i+"");
		if(i>0){
			this.getMemcachedManager().cacheObject(tablename+MapsUtil.getString(par, "s_id"), JSONUtil.objectToString(par), this.getMemcachedManager().CACHE_EXP_FOREVER);
			this.getMemcachedManager().cacheObject(tablename+MapsUtil.getString(par, "s_name"), JSONUtil.objectToString(par), this.getMemcachedManager().CACHE_EXP_FOREVER);
			
		}
		return i;
			
	}

	
	public long update(Map par) throws SException{
//		Map m = JSONUtil.objToMap(payProduct);
		SqlXML sqlxml = getSql("updateServer", par);
		
		long i = this.getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		if(i>0){
			this.getMemcachedManager().cacheObject(tablename+MapsUtil.getString(par, "s_id"), JSONUtil.objectToString(par), this.getMemcachedManager().CACHE_EXP_FOREVER);
			this.getMemcachedManager().cacheObject(tablename+MapsUtil.getString(par, "s_name"), JSONUtil.objectToString(par), this.getMemcachedManager().CACHE_EXP_FOREVER);
			
		}
		return i;
			
	}
	
}
