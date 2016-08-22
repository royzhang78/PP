package com.rodcell.comm;
import java.util.Map;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;

import org.springframework.context.ApplicationContext;

import com.rodcell.comm.util.MapsUtil;
import com.rodcell.entity.sys.SysPayConfigChannelTemplate;
import com.rodcell.entity.sys.SysPayconfigchannelfilter;
import com.rodcell.entity.sys.SysPayconfigfilter;


public class Constant { 
	/**
	 * seq
	 */
	public static Map SEQ_MAP = MapsUtil.newConcurrentMap();
 
	/**
	 * spring 容器
	 */
	public static ApplicationContext CTX;
	/**
	 * CLASS工程跟目录
	 */
	public static String APP_ROOT_PATH ;
	
	
	/**
	 * HTTP工程跟目录
	 */
	public static String APP_HTTP_ROOT_PATH ;
	 
	/**
	 * sql 文件路径
	 */
	public static String SQL_PROP = "sql.properties"; 
	
	
//	public static List<SysTree> TreeList;
	
	/**显示页数**/
	public static final int pagesize= 20;
	
	
	public static int CALL_SERVER_THREAD_SIZE=1;//回调game服务器线程数
	
	
	public static int serverkey=0;
	
	public final static String PAY_IN_QUEUE_NAME = "com.rodcell.pay.server.in";//mq支付队列
	
	/**缓存SELECT_CONFIG_CHANNEL_TEMPLATE_MAP 配置**/
	public static Map<String,SysPayConfigChannelTemplate> SELECT_CONFIG_CHANNEL_TEMPLATE_MAP=MapsUtil.newHashMap();
	
	/**缓存PAY_CONFIG_CHANNEL_FILTER 配置**/
	public static Map<String,SysPayconfigfilter> SELECT_PAY_CONFIG_CHANNEL_FILTER_MAP=MapsUtil.newHashMap();
	
	
	/**缓存sys_pay_config_channel_filter 配置**/
	public static Map<String,SysPayconfigchannelfilter> SELECT_SYS_PAY_CONFIG_CHANNEL_FILTER_MAP=MapsUtil.newHashMap();
	
	
	public static Map<String,JetTemplate> commTemplate=MapsUtil.newHashMap();//模版
	
	public static String PP_CHANNEL_ID="PP_CHANNEL_ID";
	
	public static String MESSAGE_TEMPLATE="MESSAGE_TEMPLATE";
	
	/**
	 * 公用模版引擎
	 */
	public static JetEngine engine;
	
	public final static int PAY_STATUS_0=0;//0创建订单
	public  final static int PAY_STATUS_1=1;//1订单确认(mo确认等),
	public  final static int PAY_STATUS_2=2;//2回调确认（回调验证订单信息）， 
	public  final static int PAY_STATUS_3=3; //3支付确认（dl确认等），
	public  final static int PAY_STATUS_4=4; //4回调充值服务器,
	public  final static int PAY_STATUS_5=5; //5订单完成 成功（根据fail_code查看成功失败）
	public  final static int PAY_STATUS_6=6; //6订单完成 不成功（根据fail_code查看成功失败）
	
	
	/**0服务器充值回调	*/
	public final static int PAY_TYPE0=0;//0服务器充值回调	
	/**1充值不回调 */
	public final static int PAY_TYPE1=1;//，1充值不回调 
	/**2充值pp币	*/
	public final static int PAY_TYPE2=2;//,2充值pp币	
	/**3充值pp币并立即消费*/
	public final static int PAY_TYPE3=3;//，3充值pp币并立即消费
	
	
	
	
	public  final static int FAIL_CODE_0=0;//0未验证，其他为验证状态
	
	public  final static int  PRODUCT_STATUS2=2;//物品状态2，审核通过
	
	
	public  final static int  SERVER_STATUS2=2;//，审核通过
	
	
	public  final static int ORDER_SOURCE0=0;//代表来自pp
	
	
	
	public  final static int SYS_PAY_CONFIG_CHANNEL_TEMPLATE_TYPE1=1;//代表来自mo
	
	public  final static int SYS_PAY_CONFIG_CHANNEL_TEMPLATE_TYPE2=2;//代表来自mt
	
	public  final static int COMM_TEMPLATE_SOURCE_0=0;//CheckTemplate
	public  final static int COMM_TEMPLATE_SOURCE_1=1;//EndTemplate
	public  final static int COMM_TEMPLATE_SOURCE_2=2;//ReturnTemplate
	
	
	public  final static int SYS_PAY_CONFIG_CHANNEL_PRICE_TYPE_0=0;//0完全匹配
	public  final static int SYS_PAY_CONFIG_CHANNEL_PRICE_TYPE_1=1;//1正则匹配
}

