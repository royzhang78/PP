package com.rodcell.client;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年9月2日 上午10:34:04 
 * 类说明 
 */
public class MsgObject {
	
	private String server;
	private String errorcode;
	private String msg;
	private Object param;
	
	public MsgObject(){}
	
	public MsgObject(String server, String errorcode, String msg, Object paramet) {
		super();
		this.server = server;
		if(errorcode!=null)
			errorcode=errorcode.trim();
		this.errorcode = errorcode;
		this.msg = msg;
		this.param = paramet;
	}
	
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	
	
	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object paramet) {
		this.param = paramet;
	}
	 
	
	
	
	
	

}
