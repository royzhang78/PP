package com.rodcell.exception;

import java.util.Map;

import org.apache.log4j.Logger;

import com.rodcell.client.MsgObject;
import com.rodcell.client.UdpClient;
import com.rodcell.message.ErrorCode;

public class SException extends Exception {

	private static final long serialVersionUID = 628970173525484428L;
	
	private static Logger logger = Logger.getLogger(SException.class);

	private int errorCode ; /** 错误代码 **/
	
	private String errorMsg;/** 错误描述 **/
	
	public int getErrorCode(){
		return this.errorCode;
	}
	
	public String getErrorMsg(){
		return this.errorMsg;
	}
	
	public SException(){
	}
	public SException(String msg){
		this.errorMsg = msg;
		logger.error(msg);
	}
	public SException(Exception e){
		super(e);
		this.printErrorMsg(e);

	}
	public SException(Exception e,int errorCode){
		super(e);
		this.errorCode = errorCode;
		this.printErrorMsg(e);
	}
	public SException(int errorCode){
		this.errorCode = errorCode;
	}
	public SException(String errorMsg,int errorCode){
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		if(errorMsg!=null && !errorMsg.equals(""))
			logger.error(errorMsg);
	} 
	public SException(Exception e,String errorMsg,int errorCode){
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		if(errorMsg!=null && !errorMsg.equals(""))
			logger.error(errorMsg,e);
	} 
	
	
	public SException(Exception e,String errorCode,Map map){
		String msg =null;
		if(e!=null){
			msg=e.getMessage();
		}
		MsgObject o=new MsgObject("", errorCode, msg, map);
		try {
			
			UdpClient.produce(o);
		} catch (Exception e1) {
		}
	}
	private void printErrorMsg(Exception e){
		logger.error(e);
		/*StackTraceElement[]   stacks   =   e.getStackTrace();  
		  for(int   i=0;i<stacks.length;i++){  
			  logger.error("file   name:"+stacks[i].getFileName());  
			  logger.error("class:"+stacks[i].getClassName());  
			  logger.error("method   name:"+stacks[i].getMethodName());  
			  logger.error("Line   No:"+stacks[i].getLineNumber());  
		}
		logger.error("---------Next-----------");  */
	}
}
