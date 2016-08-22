package com.rodcell.entity.comm;

import com.rodcell.message.ErrorCode;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月20日 上午11:59:24 
 * 类说明 
 */
public class MessageTemplate {
	private int code=Integer.parseInt(ErrorCode.ERROR_500);//错误码
	private int isstatus=0;//根据该字段判断获取订单方式 0代表使用系统支付id（pay_id）,1代表使用 支付渠道方id（unique_key）
	public String pay_id;//支付id
	public String unique_key;//支付渠道方id
	public int pay_status;//支付状态
	public String fail_code;//支付渠道方code
	public String error_desc;//支付渠道错误描述

	public String getError_desc() {
		return error_desc;
	}
	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}
	public int getPay_status() {
		return pay_status;
	}
	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}
	public String getFail_code() {
		return fail_code;
	}
	public void setFail_code(String fail_code) {
		this.fail_code = fail_code;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getPay_id() {
		return pay_id;
	}
	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}
	public String getUnique_key() {
		return unique_key;
	}
	public void setUnique_key(String unique_key) {
		this.unique_key = unique_key;
	}
	
	
	
	
	
	
	 
	public int getIsstatus() {
		return isstatus;
	}
	public void setIsstatus(int isstatus) {
		this.isstatus = isstatus;
	}
	public static void setError_desc(MessageTemplate mt, String error_desc) {
		mt.error_desc = error_desc;
	}


	public static void setPay_status(MessageTemplate mt,int pay_status) {
		mt.pay_status = pay_status;
	}


	public static void setFail_code(MessageTemplate mt,String fail_code) {
		mt.fail_code = fail_code;
	}


	public static void setCode(MessageTemplate mt,int code) {
		mt.code = code;
	}


	public static void setPay_id(MessageTemplate mt,String pay_id) {
		mt.pay_id = pay_id;
	}


	public static void setUnique_key(MessageTemplate mt,String unique_key) {
		mt.unique_key = unique_key;
	}
	
	public static void setIsstatus(MessageTemplate mt,int isstatus) {
		mt.isstatus = isstatus;
	}

}
