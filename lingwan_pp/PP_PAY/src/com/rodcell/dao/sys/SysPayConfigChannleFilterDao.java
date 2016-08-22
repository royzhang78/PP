package com.rodcell.dao.sys;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rodcell.comm.Constant;
import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.ListsUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.BaseDao;
import com.rodcell.entity.sys.SysPayconfigchannelfilter;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月21日 下午4:25:05 
 * 类说明 
 */
@Service(value="sysPayconfigchannelfilterDao") 
public class SysPayConfigChannleFilterDao extends BaseDao{
	public static String tablename="SysPayconfigChannelfilter";
	
	public List<SysPayconfigchannelfilter> selectConfigchannelfilterByChannelIdtype(long channel_id,String type) throws SException{
		List<SysPayconfigchannelfilter> list = ListsUtil.newArrayList();
		Iterator<SysPayconfigchannelfilter> iterator =Constant.SELECT_SYS_PAY_CONFIG_CHANNEL_FILTER_MAP.values().iterator();
		while (iterator.hasNext()) {
			SysPayconfigchannelfilter c= iterator.next();
			if(c.getChannel_id()==channel_id && c.getType().equals(type)){
				list.add(c);
			}
		}
		return list;
	}
	
	
	public List<SysPayconfigchannelfilter> selectConfigchannelfilter() throws SException {
		List<SysPayconfigchannelfilter> c =null;
		SqlXML sqlxml = getSql("findsyspayconfigchannelfilter",MapsUtil.newHashMap() );
		c = this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), SysPayconfigchannelfilter.class,sqlxml.getPar());
		return c;
	}
}
