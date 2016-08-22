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
	
	public static final String ERROR_500 = "-500";//系统错误
	
	public static final String  PAY_MAIN_IS_NULL="10003";//支付订单不存在,或状态不正确
	
	public static final String  PAY_MAIN_STATUS_ERROR1004="10004";//支付订单状态不正确（判断重复提交）
	
	public static final String  PAY_MAIN_STATUS_ERROR1005="10005";//支付订单支付渠道不匹配
	
	public static final String  PAYPRODUCT_IS_NULL="10001";//物品不存在
	
	public static final String  PAYSERVER_IS_NULL="10002";//支付服务不存在
	
	public static final String  PAR_LENGTH_MAX_ERROR="1002";//支付服务不存在
	
	public static final String  PAY_CHANNEL_ID_IS_NULL="1003";//支付渠道id为空
	
	public static final String  PAY_PRODUCT_PRICE_ERROR="10006";//物品价格不正确
	
	
}
