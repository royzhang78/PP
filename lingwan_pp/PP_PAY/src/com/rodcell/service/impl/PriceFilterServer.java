package com.rodcell.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.CheckUtil;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.comm.util.MapsUtil;
import com.rodcell.dao.sys.SysPayConfigChannelPriceDao;
import com.rodcell.entity.comm.PriceFilter;
import com.rodcell.entity.sys.SysPayConfigChannelPrice;
import com.rodcell.entity.sys.SysPayConfigChannelPriceValue;
import com.rodcell.exception.SException;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月22日 下午2:44:19 
 * 类说明  根据渠道价格，查询出对应的shortcode 或对应参数值
 */
public class PriceFilterServer {
	

	public static Logger log = Logger.getLogger(PriceFilterServer.class);
	
	
	/**
	 * 支付流程参数过滤
	 * @param channel_id 渠道id
	 * @param price 价格
	 * @param currency 货币类型
	 * @param par 其余参数
	 * @return PriceFilter（若status 为false 则渠道价格和绑定字段失败）
	 * @throws SException 
	 */
	public static PriceFilter filterPar(long channel_id,String price,String currency,Map par ) throws SException{
		boolean b=true;
		PriceFilter   filter=new PriceFilter();//判断
		SysPayConfigChannelPriceDao dao=(SysPayConfigChannelPriceDao)Constant.CTX.getBean("sysPayConfigChannelPriceDao");
		SysPayConfigChannelPrice  price1 = dao.findsysPayConfigChannelPrice(channel_id, price, currency);
		int id;
		if(price1!=null){
			if(price1.getType()==Constant.SYS_PAY_CONFIG_CHANNEL_PRICE_TYPE_0){
				if(price.trim().equals(price1.getPricepoin().trim()) || Math.abs(Double.valueOf(price.trim())-Double.valueOf(price1.getPricepoin().trim()))<=0.01){
					//true
					id=price1.getId();
				}else{
					return null;
				}
			}else{
				if(CheckUtil.checkPattern(price1.getPricepoin(), price)){//验证价格是否匹配
					id=price1.getId();
				}else{
					return null;
				}
			}
		}else{
			filter.setStatus(b);
			return filter;
		}
		
		
		
//		SysPayConfigChannelPriceValueDao dao1=(SysPayConfigChannelPriceValueDao)Constant.CTX.getBean("SysPayConfigChannelPriceValueDao");
		
		List<Map> list =(List<Map>)JSONUtil.JsonToObject(price1.getOther_param(), List.class);// dao1.findsysPayConfigChannelPrice(id);//过滤自定义属性值，若不存在则返回失败
		Map tmp=MapsUtil.newHashMap();
		if(list.size()>0){
			Iterator<String> iterator = par.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				tmp.put(key.toLowerCase().trim(), par.get(key));
			}
		}
		for (Map pricesMap:list) {
			SysPayConfigChannelPriceValue prices=(SysPayConfigChannelPriceValue)JSONUtil.mapToObject(pricesMap, SysPayConfigChannelPriceValue.class);
			String key=prices.getKey()==null?"":prices.getKey();
			key=key.toLowerCase().trim();
			String values=prices.getVal()==null?"":prices.getVal();
			String vs=MapsUtil.getString(tmp, key)==null?"":MapsUtil.getString(tmp, key);
			if(values!=null && (values.indexOf(vs)>0 || values.equals(vs))){
				filter.setMapValue(prices.getKey(), prices);
			}else{
				b=false;
				break;
			}
		}
		filter.setStatus(b);
		return filter;
	}
	
	public static void main(String[] args) {
		String values="12|35";
		String price1="35";
		boolean b = (values!=null && (values.indexOf(price1)>0 || values.equals(price1)));
		System.out.println(b);
	}

}
