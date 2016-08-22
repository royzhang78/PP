package com.rodcell.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.rodcell.comm.util.DateTimeUtil;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月23日 上午10:59:50 
 * 类说明 
 */
public class PayMainRetry {
	
	/**订单id*/
	private String pay_id;
	
	/**回调服务器id*/
	private String server_id;
	
	/**需要执行的时间*/
	@JSONField (format=DateTimeUtil.DEFAULT_DATETIME_FORMAT)
	private Date next_time;
	
	/**回调次数*/
	private int call_server_count;
	
	/**开始的时间*/
	@JSONField (format=DateTimeUtil.DEFAULT_DATETIME_FORMAT)
	private Date start_time;
	
	/**0，未执行,1执行中*/
	private int status;
	
	/** 机器编号+执行时间戳，避免多线程操作相同记录*/
	private String exe_time;
	
	/** 错误代码*/
	private String error_code;
	
	/**错误描述*/
	private String error_desc;
	
	/**0回调服务器,*/
	private int type;
	
	
	public PayMainRetry(){}
	
	
	
	public PayMainRetry(String pay_id, String server_id, Date next_time,
			int call_server_count, Date start_time, int status,
			String exe_time, String error_code, String error_desc, int type) {
		super();
		this.pay_id = pay_id;
		this.server_id = server_id;
		this.next_time = next_time;
		this.call_server_count = call_server_count;
		this.start_time = start_time;
		this.status = status;
		this.exe_time = exe_time;
		this.error_code = error_code;
		this.error_desc = error_desc;
		this.type = type;
	}
	public String getPay_id() {
		return pay_id;
	}
	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}
	public String getServer_id() {
		return server_id;
	}
	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}
	public Date getNext_time() {
		return next_time;
	}
	public void setNext_time(Date next_time) {
		this.next_time = next_time;
	}
	public int getCall_server_count() {
		return call_server_count;
	}
	public void setCall_server_count(int call_server_count) {
		this.call_server_count = call_server_count;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getExe_time() {
		return exe_time;
	}
	public void setExe_time(String exe_time) {
		this.exe_time = exe_time;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_desc() {
		return error_desc;
	}
	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	

}
