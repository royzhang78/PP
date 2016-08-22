package com.rodcell.entity.sys;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月21日 下午4:26:17 
 * 类说明 
 */
public class SysPayconfigfilter {
	private int fid;
	private String colname;
	private int minlength;
	private int maxlength;
	private String regEx;
	private String returnTxt;
	private String desc;
	private int error_response_status;
	
	
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getColname() {
		return colname;
	}
	public void setColname(String colname) {
		this.colname = colname;
	}
	
	
	
	
	
	public int getMinlength() {
		return minlength;
	}
	public void setMinlength(int minlength) {
		this.minlength = minlength;
	}
	public int getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}
	public String getRegEx() {
		return regEx;
	}
	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}
	public String getReturnTxt() {
		return returnTxt;
	}
	public void setReturnTxt(String returnTxt) {
		this.returnTxt = returnTxt;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getError_response_status() {
		return error_response_status;
	}
	public void setError_response_status(int error_response_status) {
		this.error_response_status = error_response_status;
	}

	
	
}
