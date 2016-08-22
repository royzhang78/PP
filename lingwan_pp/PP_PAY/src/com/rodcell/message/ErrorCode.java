package com.rodcell.message;
/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 下午4:52:13 
 * 类说明 
 */
public class ErrorCode {
	public static final String  ERRORCODE_NAME="PP_ERRORCODE";
	
	public static final String NO_ERROR = "0";
	
	public static final String ERROR_CALL_URL_300 = "-300";//回调服务器错误
	
	public static final String ERROR_CALL_URL_301 = "-301";//回调服务器错误
	
	public static final String ERROR_400 = "-400";//服务不存在
	
	public static final String ERROR_500 = "-500";//系统错误
	
	public static final String ERROR_505 = "-505";//数据库插入错误
	
	public static final String  PAY_MAIN_IS_NULL="-10003";//支付订单不存在,或状态不正确
	
	public static final String  PAY_MAIN_STATUS_ERROR1004="-10004";//支付订单状态不正确（判断重复提交）
	
	public static final String  PAY_MAIN_STATUS_ERROR1005="-10005";//支付订单支付渠道不匹配
	
	public static final String  PAYPRODUCT_IS_NULL="-10006";//物品不存在
	
	public static final String  PAYSERVER_IS_NULL="-10007";//支付服务器配置不存在
	
	public static final String  PRODUCT_SERVER_IS_NULL="-10008";//物品和服务器不匹配	
	
	public static final String  PLMN_IS_NULL="-10009";//PLMN为空
	
	public static final String  CARD_CONFIG_IS_NULL="-10010";//点卡配置不存在
	 
	
	public static final String  PAR_LENGTH_MAX_ERROR="-1002";//参数过长
	

	public static final String  PAY_CHANNEL_ID_IS_NULL="-1003";//支付渠道id为空
	
	public static final String  PAY_KEY_ERROR="-1004";//消息加密验证失败
	
	public static final String  PAY_CHANNEL_ID_IS_ERROR="-1005";//支付渠道id错误（需要检查支付渠道配置）
	
	public static final String GOOGLE_VERIFY_ERROR="-1006";//google验证失败
	public static final String GOOGLE_VERIFY_ERROR_MSG="GOOGLE VERIFY ERROR";//google验证失败
	
	public static final String  GOOGLE_CHANNEL_KEY_ERROR="-1007";//google支付渠道key为空
	public static final String  GOOGLE_CHANNEL_KEY_ERROR_MSG="GOOGLE KEY IS NULL";//google支付渠道key为空
	
	
	
	public static final String  CHANNEL_VERIFY_ERROR="-1008";//验证失败
	public static final String  CHANNEL_VERIFY_ERROR_MSG="CHANNEL_VERIFY_ERROR";//验证失败
	
	
	
	public static final String  ORDER_ISNULL_ERROR="-1009";//验证失败
	public static final String  ORDER_ISNULL_ERROR_MSG="ORDERID_IS_NULL_ERROR";//验证失败
	
	
	public static final String  ORDER_STATUS_ERROR="-1010";//订单状态不正确
	public static final String  ORDER_STATUS_ERROR_MSG="ORDERID_STATUS_ERROR";//订单状态不正确
	
	
	public static final int RESPONSE500=500;
	public static final int RESPONSE200=200;
}
