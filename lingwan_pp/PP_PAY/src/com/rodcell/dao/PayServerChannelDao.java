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
@Service(value="payServerChannelDao") 
public class PayServerChannelDao extends BaseDao{
	public static String tablename="PayServerChannel";
	
	
	public List<Map> findPayServerChannel(){
		SqlXML sqlxml = getSql("findPayServerChannel", MapsUtil.newHashMap());
		try {
			return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class);
		} catch (SException e) {
			log.error("", e);
			return null;
		}
	}
	
	
	public List<Map> fingServerChannelByTypeServerId(String type,String serverId){
		Map m=MapsUtil.newHashMap();
		m.put("type", type);
		m.put("sid", serverId);
		
		SqlXML sqlxml = getSql("fingServerChannelByTypeServerId", m);
		try {
			return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
		} catch (SException e) {
			log.error("", e);
			return null;
		}
	}
	
	
	public Map fingServerChannel(String channelid,String serverId){
		Map m=MapsUtil.newHashMap();
		m.put("c_id", channelid);
		m.put("s_id", serverId);		
		SqlXML sqlxml = getSql("fingServerChannel", m);
		try {
			return this.getDbManagement().queryForBean(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
		} catch (SException e) {
			log.error("", e);
			return null;
		}
	}
	 
}
