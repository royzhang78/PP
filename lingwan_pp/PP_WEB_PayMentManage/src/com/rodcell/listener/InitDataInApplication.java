package com.rodcell.listener;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rodcell.comm.Constant;
 

public class InitDataInApplication implements ServletContextListener{
	 

	

	
	public void contextDestroyed(ServletContextEvent context) {
		
	}

	public void contextInitialized(ServletContextEvent context){
		System.out.println("=== init applicatipon data ===");
		try { 
			Properties pps=System.getProperties();
//			pps.put("file.encoding","ISO-8859-1");
//			pps.list(System.out);
			
			Constant.CTX	 =   WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
			Constant.APP_HTTP_ROOT_PATH=context.getServletContext().getRealPath("");
			System.out.println(Constant.APP_HTTP_ROOT_PATH);
//			DefaultListableBeanFactory acf = (DefaultListableBeanFactory) Constant.CTX.getAutowireCapableBeanFactory();
//			acf.registerBeanDefinition(arg0, arg1);
//			BeanDefinition bean =  new BeanDefinitionReaderUtils();
			
			//			InitService initService = (InitService)Constant.CTX.getBean("initService");//查询guest权限信息
//			initService.init();
			

//			Timer t =TimerTask.timerMap.get("taskService");
			//			System.out.println(ac1.getBean("sysMenuService"));
//			this.initProperties();
//			Constant.TreeList=((SysMenuService)ac1.getBean("sysMenuService")).textureMenu();
//			((SysMenuService)ac1.getBean("sysMenuService")).initSeq();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initProperties() throws Exception{
//		Hashtable filePathHT = new Hashtable();
//		String errorMsgPath = "/com/hola/contract/resources/errorMsg.properties";
//		filePathHT.put("errorMsg", errorMsgPath);
//		PropertyUtil propertyUtil = new PropertyUtil();
//		propertyUtil.initProperty(filePathHT);
	}

	 
	
	
	
	
}
