package com.rodcell.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.rodcell.comm.util.DateTimeUtil;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月19日 上午11:25:22 
 * 类说明 
 */
public class PayMain {
	private int adType=0;//0非广告模式，1广告模式
	private String pay_id;//订单id
	private String token;
	private int pay_channel_type;//充值渠道类型
	private int pay_status;//充值状态  充值状态0创建订单,1订单确认(mo确认等),2回调确认（回调验证订单信息），3支付确认（dl确认等），4回调充值服务器,5订单完成 （根据fail_code查看成功失败）
	private String unique_key;//渠道方唯一id
	private String pay_money;//充值金额
	private String currency;//币种
	
	@JSONField (format=DateTimeUtil.DEFAULT_DATETIME_FORMAT)
	private Date create_pay_date;//创建订单日期
	private String fail_code;//
	
	@JSONField (format=DateTimeUtil.DEFAULT_DATETIME_FORMAT)
	private Date succcess_pay_date;//订单完成日期
	private String other_parmeters;
	private int s_id;//充值服务器id(回调时返回该信息)
	private long source;//0代表pp系统
	private String server_order_info;//游戏创建的订单信息(回调时返回该信息)json格式
	private String uid;//游戏用户id(回调时返回该信息)
	private String product_id;//产品id（回调时返回，可设定是否绑定pp）
	private int call_server_count;//回调服务器次数
	private String step1;//创建订单后第1步
	private String step2;//创建订单后第2步
	private String step3;//创建订单后第3步
	private String step4;//创建订单后第4步
	private String error_desc;//错误描述
	private int pay_type;//0,直冲，1充值pp币，2充值pp币并立即消费
	private int product_num;//购买物品数量(默认为1)
	public String productType;//自定义物品id
	private String cparam;//回调gs传递的参数
	private int isSandbox;//0正式模式，1沙箱模式
	private String extraData;//对应cparam
	
	
	public static int DEF_PRODUCT_NUM=1;//默认产品数量为1
	public static int DEF_PAY_TYPE=0;//默认支付类型为0 直冲
	public static int DEF_SOURCE=0;//默认支付类型为0 直冲
	
	public String orderId;//老接口订单编号唯一
	private String amount;//老接口购买的游戏物品数量
	private int payType;//老接口支付渠道，以数字表示不同渠道
	private String gameRoleId;//老接口游戏角色ID
	private long timestamp;//交易时间戳
	private String verify;//验证Key
	
	private String serverCode;//区服ID。不同区服回调使用
	
	private Object opt;
	
	private String operator;
	
	private PayProduct  product;
	
	
	
	
	
	
	public int getAdType() {
		return adType;
	}
	public void setAdType(int adType) {
		this.adType = adType;
	}
	public PayProduct getProduct() {
		return product;
	}
	public void setProduct(PayProduct product) {
		this.product = product;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
	public String getServerCode() {
		return serverCode;
	}
	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}
	public PayMain(String pay_id, String token, int pay_channel_type,
			int pay_status, String unique_key, String pay_money, String currency,
			Date create_pay_date, String fail_code, Date succcess_pay_date,
			String other_parmeters, int s_id,  
			String server_order_info, String uid, String product_id,
			int call_server_count, String step1, String step2, String step3,
			String step4, String error_desc,String productType,String cparam,int isSandbox,String serverCode) {
		super();
		this.serverCode=serverCode;
		this.pay_id = pay_id;
		orderId=this.pay_id;
		this.token = token;
		this.pay_channel_type = pay_channel_type;
		payType=this.pay_channel_type;
		this.pay_status = pay_status;
		this.unique_key = unique_key;
		this.pay_money = pay_money;
		amount=this.server_order_info;
		this.currency = currency;
		this.create_pay_date = create_pay_date;
		this.fail_code = fail_code;
		this.succcess_pay_date = succcess_pay_date;
		this.other_parmeters = other_parmeters;
		this.s_id = s_id;
		this.source = DEF_SOURCE;
		this.server_order_info = server_order_info;
		this.uid = uid;
		this.product_id = product_id;
		this.call_server_count = call_server_count;
		this.step1 = step1;
		this.step2 = step2;
		this.step3 = step3;
		this.step4 = step4;
		this.error_desc = error_desc;
		this.pay_type = DEF_PAY_TYPE;
		this.product_num = DEF_PRODUCT_NUM;
		gameRoleId=this.uid;
		this.productType=productType;
		this.cparam=cparam;
		this.extraData=cparam;
		this.isSandbox=isSandbox;
	}
	public PayMain() {
		// TODO Auto-generated constructor stub
	}
	public int getPay_type() {
		return pay_type;
	}
	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}
	public int getProduct_num() {
		return product_num;
	}
	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}
	public String getPay_id() {
		return pay_id;
	}
	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
		orderId=this.pay_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getPay_channel_type() {
		return pay_channel_type;
	}
	public void setPay_channel_type(int pay_channel_type) {
		this.pay_channel_type = pay_channel_type;
		this.payType=this.pay_channel_type;
	}
	public int getPay_status() {
		return pay_status;
	}
	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}
	public String getUnique_key() {
		return unique_key;
	}
	public void setUnique_key(String unique_key) {
		this.unique_key = unique_key;
	}
	public String getPay_money() {
		return pay_money;
	}
	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
//		amount=this.pay_money;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getCreate_pay_date() {
		return create_pay_date;
	}
	public void setCreate_pay_date(Date create_pay_date) {
		this.create_pay_date = create_pay_date;
	}
	public String getFail_code() {
		return fail_code;
	}
	public void setFail_code(String fail_code) {
		this.fail_code = fail_code;
	}
	public Date getSucccess_pay_date() {
		return succcess_pay_date;
	}
	public void setSucccess_pay_date(Date succcess_pay_date) {
		this.succcess_pay_date = succcess_pay_date;
	}
	public String getOther_parmeters() {
		return other_parmeters;
	}
	public void setOther_parmeters(String other_parmeters) {
		this.other_parmeters = other_parmeters;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public long getSource() {
		return source;
	}
	public void setSource(long source) {
		this.source = source;
	}
	public String getServer_order_info() {
		return server_order_info;
	}
	public void setServer_order_info(String server_order_info) {
		this.server_order_info = server_order_info;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getCall_server_count() {
		return call_server_count;
	}
	public void setCall_server_count(int call_server_count) {
		this.call_server_count = call_server_count;
	}
	public String getStep1() {
		return step1;
	}
	public void setStep1(String step1) {
		this.step1 = step1;
	}
	public String getStep2() {
		return step2;
	}
	public void setStep2(String step2) {
		this.step2 = step2;
	}
	public String getStep3() {
		return step3;
	}
	public void setStep3(String step3) {
		this.step3 = step3;
	}
	public String getStep4() {
		return step4;
	}
	public void setStep4(String step4) {
		this.step4 = step4;
	}
	public String getError_desc() {
		return error_desc;
	}
	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getCparam() {
		return cparam;
	}
	public void setCparam(String cparam) {
		this.cparam = cparam;
		this.extraData=cparam;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public String getGameRoleId() {
		return gameRoleId;
	}
	public void setGameRoleId(String gameRoleId) {
		this.gameRoleId = gameRoleId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public int getIsSandbox() {
		return isSandbox;
	}
	public void setIsSandbox(int isSandbox) {
		this.isSandbox = isSandbox;
	}
	public Object getOpt() {
		return opt;
	}
	public void setOpt(Object opt) {
		this.opt = opt;
	}
	
	
	
	

}
