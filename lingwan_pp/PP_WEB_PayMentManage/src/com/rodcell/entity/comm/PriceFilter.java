package com.rodcell.entity.comm;

import java.util.Map;

import com.rodcell.comm.util.MapsUtil;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月26日 下午2:08:13 
 * 类说明 
 */
public class PriceFilter {
	private boolean status;
	
	private Map<String,String> returnMap=MapsUtil.newHashMap();

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Map<String, String> getReturnMap() {
		return returnMap;
	}

	public void setReturnMap(Map<String, String> returnMap) {
		this.returnMap = returnMap;
	}
	
	
	public void setMapValue(String key,String value){
		returnMap.put(key, value);
	}
	

}
