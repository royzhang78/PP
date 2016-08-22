package com.rodcell.entity.comm;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月30日 下午2:39:24 
 * 类说明 
 */
public class ChannelEntity {
	private int channen_id;
	private String channel_name;
	private int channel_type;
	private String request_type;
	
	private String operator;
	
	private ChannelDetail mo;
	
	private ChannelDetail notif;

	
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getChannen_id() {
		return channen_id;
	}

	public void setChannen_id(int channen_id) {
		this.channen_id = channen_id;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public int getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(int channel_type) {
		this.channel_type = channel_type;
	}

	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}

	public ChannelDetail getMo() {
		return mo;
	}

	public void setMo(ChannelDetail mo) {
		this.mo = mo;
	}

	public ChannelDetail getNotif() {
		return notif;
	}

	public void setNotif(ChannelDetail notif) {
		this.notif = notif;
	}
	
	
	
}
