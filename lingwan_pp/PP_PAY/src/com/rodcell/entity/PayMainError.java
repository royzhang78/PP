package com.rodcell.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.rodcell.comm.util.DateTimeUtil;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月30日 下午5:50:05 
 * 类说明 
 */
public class PayMainError {
	
	private String pay_id;
	
	private int type;
	private String text;
	
	@JSONField (format=DateTimeUtil.DEFAULT_DATETIME_FORMAT)
	private Date errordate;

	
	
	
	public PayMainError(String pay_id, int type, String text, Date errordate) {
		super();
		this.pay_id = pay_id;
		this.type = type;
		this.text = text;
		this.errordate = errordate;
	}

	public String getPay_id() {
		return pay_id;
	}

	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getErrordate() {
		return errordate;
	}

	public void setErrordate(Date errordate) {
		this.errordate = errordate;
	}
	
	
	

}
