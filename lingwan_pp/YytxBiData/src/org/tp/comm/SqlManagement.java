package org.tp.comm;

import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.tp.comm.util.SQLParser;
import org.tp.comm.util.SqlXmlParser;

public class SqlManagement extends  PropertyPlaceholderConfigurer {

	private static Logger logger = Logger.getLogger(SqlManagement.class);

	private static Properties prop ;
	
//	public SqlManagement(){
//		init();
//	}
	
//	@SuppressWarnings("static-access")
//	public void init(){
//
//		try {
//			this.prop = new Properties();
//			InputStream is = new java.io.FileInputStream(Constant.APP_ROOT_PATH+Constant.SQL_PROP);
//			prop.load(is);
//			if(is!=null) is.close();
//			logger.info("--> init SqlManagement");
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			logger.error("file "+Constant.APP_ROOT_PATH+Constant.SQL_PROP+" not found",e);
//		}
//	}
	
	
	protected void processProperties(  
            ConfigurableListableBeanFactory beanFactoryToProcess,  
            Properties props) throws BeansException {  
        super.processProperties(beanFactoryToProcess, props);  
        prop=props;
        logger.info(prop.getProperty("sql"));
//        ctxPropertiesMap = new HashMap<String, Object>();  
//        for (Object key : props.keySet()) {  
//            String keyStr = key.toString();  
//            String value = props.getProperty(keyStr);  
//            ctxPropertiesMap.put(keyStr, value);  
//        }  
    }  
	
	public static String  getprop(String propName){
		return prop.getProperty(propName);
	}
	 
	/***
	 * 解析sql
	 * @param propName
	 * @param pra
	 * @return
	 */
	public static SqlXML  getprop(String propName,Map pra){
		return SQLParser.getSql(propName,prop.getProperty(propName), pra);
	}
	
	
	public static SqlXML  getpropByXml(String propName,Map pra){
		return SQLParser.getSql(propName,SqlXmlParser.SQLXML.get(propName), pra);
	}
}
