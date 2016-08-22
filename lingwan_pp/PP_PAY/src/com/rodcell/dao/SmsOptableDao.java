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
@Service(value="smsOptableDao") 
public class SmsOptableDao extends BaseDao{ 
	
	
	public List<Map> findPayServerByName(String plmn,String price,String currency,String spid){		 
			Map m = MapsUtil.newHashMap();
			m.put("plmn", plmn);
			m.put("price", price);
			m.put("currency", currency);
			String channelid=null;
			if(!"".equals(spid)){
				channelid=spid;
			}
			m.put("spid", channelid);
			SqlXML sqlxml = getSql("findOP", m);
			try {
				return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
			} catch (SException e) {
				log.error("",e);
			}
			return ListsUtil.newArrayList();
		 
	}
	
}
