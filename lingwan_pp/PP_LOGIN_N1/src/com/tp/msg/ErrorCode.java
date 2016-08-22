package com.tp.msg;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午4:52:13 
 * 类说明 
 */
public class ErrorCode {
	
	public static final String NO_ERROR = "0";
	
	public static final String ERROR_400 = "-400";//服务不存在
	
	public static final String ERROR_500 = "-500";//系统错误
	
	//登录帐号为空
	public static final String ERROR_20001="-20001";
	//登录帐号为空
	public static final String ERROR_20001_MSG="login_name is null";
	
	
	//登录类型为空
	public static final String ERROR_20002="-20002";
	public static final String ERROR_20002_MSG="login_type is null";
	
	//手机唯一标识u_deviceid为空
	public static final String ERROR_20003="-20003";
	public static final String ERROR_20003_MSG="u_deviceid is null";
	
	//登录服务器为空serverName
	public static final String ERROR_20004="-20004";
	public static final String ERROR_20004_MSG="serverName is null";
	
	//登录服务器不存在
	public static final String ERROR_20005="-20005";
	public static final String ERROR_20005_MSG="server is null";
	
	//验证facebook失败
	public static final String ERROR_20006="-20006";
	public static final String ERROR_20006_MSG="facebook verify error";
	
	//帐号不可用
	public static final String ERROR_20007="-20007";
	public static final String ERROR_20007_MSG="user status error";
	
	  public static final String ERROR_20008 = "-20008";
	  public static final String ERROR_20008_MSG = "ppid is null";
	 
}
