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
	
	
	public List<Map>  findPayServerChannelBySid(String sid) throws SException{
		Map par = MapsUtil.newHashMap();
		par.put("s_id", sid);
		SqlXML sqlxml = getSql("findPayServerChannelBySid", par);
		return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
	}
	
	public int delPayServerChannelBySid(String sid) throws SException{
		Map par = MapsUtil.newHashMap();
		par.put("s_id", sid);
		SqlXML sqlxml = getSql("delPayServerChannelBySid", par);
		return this.getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
	}
	
	public int insertPayServerChannel(Map par) throws SException{
		SqlXML sqlxml = getSql("insertPayServerChannel", par);
		return this.getDbManagement().update(sqlxml.getSearchsql(), sqlxml.getPar());
		 
	}
}
