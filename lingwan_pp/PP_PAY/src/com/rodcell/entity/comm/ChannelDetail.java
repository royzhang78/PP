package com.rodcell.entity.comm;

import java.util.List;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月30日 下午2:39:24 
 * 类说明 
 */
public class ChannelDetail {
	
	/**消息加密类型如 ：md5，空为不加密验证*/
	private String encryptType;
	/**key(消息加密验证字段: $$表示读取request,##标识读取静态变量字段 )*/
	private String key;
	/**获取request对比key字段名**/
	private String keyName;
	
	private String keyerror;
	/**消息执行类**/
	private String callServiceName;
	/**执行自定义函数类如 需要回调渠道服务器等操作**/
	private String exeServerByServiceName;
	
	private List<ChannelAnalyze> analyze;
	
	private SuccessStatus successStatus;
	
	private String errorText;
	
	
	
	
	

	

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyerror() {
		return keyerror;
	}

	public void setKeyerror(String keyerror) {
		this.keyerror = keyerror;
	}

	public SuccessStatus getSuccessStatus() {
		return successStatus;
	}

	public void setSuccessStatus(SuccessStatus successStatus) {
		this.successStatus = successStatus;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCallServiceName() {
		return callServiceName;
	}

	public void setCallServiceName(String callServiceName) {
		this.callServiceName = callServiceName;
	}

	public String getExeServerByServiceName() {
		return exeServerByServiceName;
	}

	public void setExeServerByServiceName(String exeServerByServiceName) {
		this.exeServerByServiceName = exeServerByServiceName;
	}

	public List<ChannelAnalyze> getAnalyze() {
		return analyze;
	}

	public void setAnalyze(List<ChannelAnalyze> analyze) {
		this.analyze = analyze;
	}
	
	
	
	 
}
