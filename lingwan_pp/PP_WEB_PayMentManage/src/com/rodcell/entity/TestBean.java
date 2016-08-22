package com.rodcell.entity;

import java.io.Serializable;
import java.util.Map;

import com.google.code.ssm.api.CacheKeyMethod;
import com.rodcell.comm.util.MapsUtil;

public class TestBean implements Serializable{
	private String id;
	private Map value=MapsUtil.newHashMap();
	
	@CacheKeyMethod  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map getValue() {
		return value;
	}
	public void setValue(Map value) {
		this.value = value;
	}
	
	
	
}
