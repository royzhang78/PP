package com.tp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.tp.comm.SqlXML;
import org.tp.comm.util.JSONUtil;
import org.tp.comm.util.MapsUtil;
import org.tp.exception.SException;

import com.tp.entity.PayServer;

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
		Map m = MapsUtil.newHashMap();
		SqlXML sqlxml = getSql("findAllPayServer", m);
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
//			log.error("",e);
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
			m.clear();
			m=null;
		}
		return payServer;
	}
	
	
	public PayServer findPayServerByName(String name){
		PayServer payServer = null;
		try {
			payServer =(PayServer)JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(tablename+name),PayServer.class);
		} catch (Exception e) {
//			log.error("",e);
		}
		if(payServer==null){
			Map m = MapsUtil.newHashMap();
			m.put("s_name", name);
			SqlXML sqlxml = getSql("findPayServerByName", m);
			try {
				payServer = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), PayServer.class,sqlxml.getPar());
				if(payServer!=null)
					this.getMemcachedManager().cacheObject(tablename+payServer.getS_name(), JSONUtil.objectToString(payServer), this.getMemcachedManager().CACHE_EXP_FOREVER);
			} catch (SException e) {
				log.error("",e);
			}
			m.clear();
			m=null;
		}
		return payServer;
	}
	
}
