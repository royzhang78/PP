package com.rodcell.entity;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月22日 下午2:55:24 
 * 类说明 
 */
public class FilterObject {
	private boolean status=true;//验证成功或失败 ，当失败测返回文本内容和状态
	private String returnTxt;//需要返回的字符串
	
	private int responseStatus;//需要返回的response状态
	
	 
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getReturnTxt() {
		return returnTxt;
	}
	public void setReturnTxt(String returnTxt) {
		this.returnTxt = returnTxt;
	}
	public int getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	
	
}
