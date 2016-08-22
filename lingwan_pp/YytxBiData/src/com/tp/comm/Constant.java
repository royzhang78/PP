package com.tp.comm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

//import jetbrick.template.JetEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.tp.comm.util.MapsUtil;


public class Constant { 
	
	public static String UTF8 = "UTF-8";
	
	
	public static String serverkey ="";
	
	/**
	 * seq
	 */
	public static Map SEQ_MAP = MapsUtil.newConcurrentMap();
 
	/**
	 * spring 容器
	 */
	public static ApplicationContext CTX;
	
	public static XmlWebApplicationContext wac;
	
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
	
	//控制程序 api入站限制
	public static Map DEV_STATUS=new HashMap(); 
		
	/**
	 * 公用模版引擎
	 */
//	public static JetEngine engine;
	
	
	/**
	 * 静态表初始化
	 */
//	public static List<Map> TTStaticParmList=new ArrayList<Map>();
	
	
	public static  BlockingQueue<ConcurrentHashMap> queue = new LinkedBlockingQueue<ConcurrentHashMap>(100);
	
}

