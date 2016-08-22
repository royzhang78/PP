package com.tp.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * service返回的处理结果对象
 */
public class ReturnObj implements Serializable {
	private static final long serialVersionUID = 3081149796214250601L;

	private String returnCode = null;
	private String returnMsg = null;
	private Object returnObjs = null;

	public ReturnObj(String returnCode) {
		this.returnCode = returnCode;
	}

	public ReturnObj(String returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public ReturnObj(String returnCode, String returnMsg, Object returnObjs) {
		this.returnCode = returnCode;
		this.returnObjs = returnObjs;
	}

	 

	public Object getReturnObjs() {
		return returnObjs;
	}

	public void setReturnObjs(Object returnObjs) {
		this.returnObjs = returnObjs;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	 

	public void setReturnValues(Map<String, Object> returnValues) {
		this.returnObjs = returnValues;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}
