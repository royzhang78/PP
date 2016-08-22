package com.rodcell.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.comm.util.StringUtil;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:30:59 
 * 类说明 
 */
@Service(value="tserverPayChannelDao") 
public class TServerPayChannelDao extends BaseDao{ 
	
	/**
	 * 指定默认服务器短信支付渠道（可方便测试）
	 * @param s_name
	 * @return
	 */
	public String findPayServerByName(String s_name){		 
			Map m = MapsUtil.newHashMap();
			m.put("s_name", s_name);
			SqlXML sqlxml = getSql("findServerPayChannel", m);
			try {
				List<Map> data = this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), Map.class,sqlxml.getPar());
				if(data==null&& data.size()==0){
					return null;
				}else{
					List<String> ls = new ArrayList();
					for (Map d:data) {
						ls.add(MapsUtil.getString(d, "channel_id"));
					}
					String tmp = StringUtil.joinSomeStrings(ListToStrings(ls), ",");
					return tmp;					
				}
			} catch (SException e) {
				log.error("",e);
			}
			return null;
		 
	}
	
	public String[] ListToStrings(List<String> ls){
		String[] s = new String[ls.size()];
		int i =0;
		for (String tmp:ls) {
			s[i]=tmp;
			i++;
		}
		return s;
	}
	
}
