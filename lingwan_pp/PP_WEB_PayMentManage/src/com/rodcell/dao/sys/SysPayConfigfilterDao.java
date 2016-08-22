package com.rodcell.dao.sys;

import java.util.List;
import java.util.Map;

import jetbrick.template.JetTemplate;

import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.BaseDao;
import com.rodcell.entity.sys.SysPayconfigfilter;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月21日 下午4:25:05 
 * 类说明 
 */
@Service(value="sysPayconfigfilterDao") 
public class SysPayConfigfilterDao extends BaseDao{
	public static String tablename="SysPayconfigfilter";
	
	public SysPayconfigfilter selectConfigchannelfilterByfid(int fid) throws SException{
		SysPayconfigfilter c =Constant.SELECT_PAY_CONFIG_CHANNEL_FILTER_MAP.get(fid+"");
		if(c==null){
			Map m = MapsUtil.newHashMap(); 
			m.put("fid", fid);
			SqlXML sqlxml = getSql("findSysPayconfigfilterId",m );
			c = this.getDbManagement().queryForBean(sqlxml.getSearchsql(), SysPayconfigfilter.class,sqlxml.getPar());
			Constant.SELECT_PAY_CONFIG_CHANNEL_FILTER_MAP.put(c.getFid()+"", c);
			
		}
		return c;
	}
	
	
	public List<SysPayconfigfilter> selectConfigchannelfilter() throws SException {
		List<SysPayconfigfilter> c =null;
		SqlXML sqlxml = getSql("findSysPayconfigfilter",MapsUtil.newHashMap() );
		c = this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), SysPayconfigfilter.class,sqlxml.getPar());
		return c;
	}
}
