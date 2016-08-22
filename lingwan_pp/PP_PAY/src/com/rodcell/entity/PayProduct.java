package com.rodcell.entity;

import java.util.Date;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午2:59:08 
 * 类说明 
 */
public class PayProduct {
	private long product_id;
	private String product_name;//商品名称
	private String product_showname;//商品显示名称
	private String product_type;//自定义物品类型
	private int product_status;//0创建，1审核中，2审核成功，3下架
	private String product_point;//商品点数
	private String product_price;//商品价格
	private String product_currency;//货币种类
	private Date product_create_time;
	private String product_parameter;//用户自定义参数
	
	private int sid;
	
	
	
	
	
	
	
	
	public String getProduct_showname() {
		return product_showname;
	}
	public void setProduct_showname(String product_showname) {
		this.product_showname = product_showname;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getProduct_parameter() {
		return product_parameter;
	}
	public void setProduct_parameter(String product_parameter) {
		this.product_parameter = product_parameter;
	}
	public long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public int getProduct_status() {
		return product_status;
	}
	public void setProduct_status(int product_status) {
		this.product_status = product_status;
	}
	public String getProduct_point() {
		return product_point;
	}
	public void setProduct_point(String product_point) {
		this.product_point = product_point;
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}
	public String getProduct_currency() {
		return product_currency;
	}
	public void setProduct_currency(String product_currency) {
		this.product_currency = product_currency;
	}
	public Date getProduct_create_time() {
		return product_create_time;
	}
	public void setProduct_create_time(Date product_create_time) {
		this.product_create_time = product_create_time;
	}

	
	
}
