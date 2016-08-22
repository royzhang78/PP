package com.rodcell.entity;

import java.util.Date;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午12:46:55 
 * 类说明 
 */
public class PayServer {

	private int	s_id;
	private String	s_name;
	private String	s_ip;
	private int	s_port;
	private String	s_call_payment_url;
	private int	s_call_payment_type;
	private int	s_status;
	private Date	s_create_date;	
	private int isSandbox;

	private String s_key;
	
	
	
	public int getIsSandbox() {
		return isSandbox;
	}
	public void setIsSandbox(int isSandbox) {
		this.isSandbox = isSandbox;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_ip() {
		return s_ip;
	}
	public void setS_ip(String s_ip) {
		this.s_ip = s_ip;
	}
	public int getS_port() {
		return s_port;
	}
	public void setS_port(int s_port) {
		this.s_port = s_port;
	}
	public String getS_call_payment_url() {
		return s_call_payment_url;
	}
	public void setS_call_payment_url(String s_call_payment_url) {
		this.s_call_payment_url = s_call_payment_url;
	}
	public int getS_call_payment_type() {
		return s_call_payment_type;
	}
	public void setS_call_payment_type(int s_call_payment_type) {
		this.s_call_payment_type = s_call_payment_type;
	}
	public int getS_status() {
		return s_status;
	}
	public void setS_status(int s_status) {
		this.s_status = s_status;
	}
	public Date getS_create_date() {
		return s_create_date;
	}
	public void setS_create_date(Date s_create_date) {
		this.s_create_date = s_create_date;
	}
	public String getS_key() {
		return s_key;
	}
	public void setS_key(String s_key) {
		this.s_key = s_key;
	}
	
	
	

}
