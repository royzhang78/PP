package com.rodcell.entity;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月3日 下午4:01:38 
 * 类说明 
 */
public class TableObject {
	
	public TableObject(){
		
	}
	public TableObject(int pagecount, Object value) {
		super();
		this.pagecount = pagecount;
		this.value = value;
	}

	private int pagecount;
	
	private Object value;

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	
	
}
