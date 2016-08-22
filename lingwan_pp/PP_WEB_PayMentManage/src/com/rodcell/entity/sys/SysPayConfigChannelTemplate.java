package com.rodcell.entity.sys;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月20日 上午11:26:41 
 * 类说明 
 */
public class SysPayConfigChannelTemplate {
	private long channel_id;
	private String checkTemplate;
	private String endTemplate;
	private String returnTemplate;
	
	private int type;
	public long getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(long channel_id) {
		this.channel_id = channel_id;
	}
	public String getCheckTemplate() {
		return checkTemplate;
	}
	public void setCheckTemplate(String checkTemplate) {
		this.checkTemplate = checkTemplate;
	}
	public String getEndTemplate() {
		return endTemplate;
	}
	public void setEndTemplate(String endTemplate) {
		this.endTemplate = endTemplate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getReturnTemplate() {
		return returnTemplate;
	}
	public void setReturnTemplate(String returnTemplate) {
		this.returnTemplate = returnTemplate;
	}

	
	
}
