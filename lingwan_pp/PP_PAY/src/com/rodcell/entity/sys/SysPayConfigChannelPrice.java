package com.rodcell.entity.sys;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月26日 上午10:09:17 
 * 类说明 
 */
public class SysPayConfigChannelPrice {
	
	
	private int id;
	private String pricepoin;//支付价格 可配置正则
	private long channel_type;//支付渠道
	private String currency;//支付币种
	private int type;//0完全匹配,1正则匹配
	
	private String other_param;//自定义json格式参数 数组格式[{'returnKey':'','returnValue':''}]
	
	
	
	
	
	public String getOther_param() {
		return other_param;
	}
	public void setOther_param(String other_param) {
		this.other_param = other_param;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPricepoin() {
		return pricepoin;
	}
	public void setPricepoin(String pricepoin) {
		this.pricepoin = pricepoin;
	}
	public long getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(long channel_type) {
		this.channel_type = channel_type;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
	

}
