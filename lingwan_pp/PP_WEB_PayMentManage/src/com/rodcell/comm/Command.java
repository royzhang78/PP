package com.rodcell.comm;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import com.rodcell.exception.SException;


public class Command {

	private static Logger logger = Logger.getLogger(Command.class);
	
	public static String exce(Request request,Response response){		
		try {
			
			
//			Object value = Util.requestByteToMap(o);
//			
//			APPService service =  (APPService) Constant.CTX.getBean("servletService");
			
			return null;//service.exc(request,response);
		}finally{
//			Constant.MAIN_EXCE_TAG=false;
		}
	}

//	
//	public static Object resultMsg(Object o,Request request){		
//		Map value =(Map) Util.requestByteToMap(o);
//		return exce(value,request);
//	}
//	
//	
//	public static Object payGem(Object o,Request request){	
//		Map value =(Map) Util.requestByteToMap(o);
//		return exce(value,request);
//	}
//	
//	
//	public static Object bindEmail(Object o,Request request){	
//		Map value =(Map) Util.requestByteToMap(o);
//		return exce(value,request);
//	}
}
