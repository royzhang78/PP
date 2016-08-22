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
@Service(value="apkGameParamDao") 
public class TapkGameParamDao extends BaseDao{
	public static String tablename="tapkGameParam";
	
	
	public List findAll(String id){
		Map m = MapsUtil.newHashMap();
		m.put("s_id", id);
		SqlXML sqlxml = getSql("findt_apk_game_param",m);
		try {
			return this.getDbManagement().queryList(sqlxml.getSearchsql(), sqlxml.getPar());
		} catch (SException e) {
			log.error("", e);
			return null;
		}
	}
	  
	
}
