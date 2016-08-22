package com.rodcell.entity.comm;

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
	private Object returnValues = null;

	public ReturnObj(String returnCode) {
		this.returnCode = returnCode;
	}

	public ReturnObj(String returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public ReturnObj(String returnCode, String returnMsg, Object returnValues) {
		this.returnCode = returnCode;
		this.returnValues = returnValues;
	}

	public Object getReturnValues() {
		return returnValues;
	}

	public void setReturnValues(Object returnValues) {
		this.returnValues = returnValues;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	 

	public void setReturnValues(Map<String, Object> returnValues) {
		this.returnValues = returnValues;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}
