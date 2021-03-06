package com.rodcell.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rodcell.comm.SqlXML;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.BaseDao;
import com.rodcell.entity.sys.SysPayConfigChannelPriceValue;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月26日 下午2:17:43 
 * 类说明 
 */
@Service(value="sysPayConfigChannelPriceValueDao") 
public class SysPayConfigChannelPriceValueDao extends BaseDao{
	
	public static String tablename="SysPayConfigChannelPriceValue";
	
//	public List<SysPayConfigChannelPrice> findsysPayConfigChannelPrice() throws SException{
//		Map m = MapsUtil.newHashMap(); 
//		SqlXML sqlxml = getSql("findSysPayconfigfilterId",m );
//		return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), SysPayConfigChannelPrice.class,sqlxml.getPar());
//	}
	
	
	/**
	 * 
	 * @param channel_type 支付渠道
	 * @param pricepoin  支付价格
	 * @param currency  支付币种
	 * @return
	 * @throws SException
	 */
	public List<SysPayConfigChannelPriceValue> findsysPayConfigChannelPrice(int fid) throws SException{
		Map m = MapsUtil.newHashMap(); 
		m.put("f_id", fid);
		SqlXML sqlxml = getSql("findsysPayConfigChannelPriceValueByfid",m );
		return this.getDbManagement().queryForBeanList(sqlxml.getSearchsql(), SysPayConfigChannelPriceValue.class,sqlxml.getPar());
	}	
	
	
	
	
	
}
