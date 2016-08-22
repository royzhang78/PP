package com.rodcell.dao.sys;

import java.util.List;
import java.util.Map;

import jetbrick.template.JetTemplate;

import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.BaseDao;
import com.rodcell.entity.PayServer;
import com.rodcell.entity.sys.SysPayconfigfilter;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月21日 下午4:25:05 
 * 类说明 
 */
@Service(value="sysPayStaticConfigDao") 
public class SysPayStaticConfigDao extends BaseDao{
	public static String tablename="SysPayStaticConfig";
	
	public static String key="key";
	public static String value="value";
	
	public String findPayStaticConfigbyKey(String keyid){
		try{
			Map m =null;
//			Map m =(Map)JSONUtil.JsonToObject((String)this.getMemcachedManager().loadObject(tablename+keyid),Map.class);
//			
//			if(m==null){
				m=MapsUtil.newHashMap();
				m.put(key, keyid);
				SqlXML sqlxml = getSql("findPayStaticConfigbyKey",m );
				Map data = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
//				this.getMemcachedManager().cacheObject(tablename+MapsUtil.getString(data, key), JSONUtil.objectToString(data), this.getMemcachedManager().CACHE_EXP_FOREVER);
				m=data;
//			}
			return MapsUtil.getString(m, value);
		}catch(Exception e){
			log.error("", e);
			return null;
		}
	}
	
	
	public void findALL() throws SException{
//		SqlXML sqlxml = getSql("findPayStaticConfigAll",MapsUtil.newHashMap() );
//		List<Map> list = this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
//		for (Map data: list) {
//			this.getMemcachedManager().cacheObject(tablename+MapsUtil.getString(data, key), JSONUtil.objectToString(data), this.getMemcachedManager().CACHE_EXP_FOREVER);
//			
//		}
		
	}
 
}
