package com.rodcell.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.ListsUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.entity.PayServer;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="smsSPTableDao") 
public class SmsSPTableDao extends BaseDao{ 
	
	
	public void updateSPTable(String spid, String spname) {
		Map m = MapsUtil.newHashMap();
		m.put("spid", spid);
		m.put("spname", spname);
		SqlXML sqlxml = getSql("updateSPTable", m);
		try {
			this.getDbManagement().update(sqlxml.getSearchsql(),
					sqlxml.getPar());
			// .queryForBeanList(sqlxml.getSearchsql(),
			// Map.class,sqlxml.getPar());
		} catch (SException e) {
			log.error("", e);
		}

	}
	
	
	
	public void insertSPTable(String spid, String spname) {
		Map m = MapsUtil.newHashMap();
		m.put("spid", spid);
		m.put("spname", spname);
		SqlXML sqlxml = getSql("insertSPTable", m);
		try {
			this.getDbManagement().update(sqlxml.getSearchsql(),
					sqlxml.getPar());
			// .queryForBeanList(sqlxml.getSearchsql(),
			// Map.class,sqlxml.getPar());
		} catch (SException e) {
			log.error("", e);
		}

	}
	
}
