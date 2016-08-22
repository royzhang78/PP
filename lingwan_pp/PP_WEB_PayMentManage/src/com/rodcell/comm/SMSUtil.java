package com.rodcell.comm;

import org.apache.log4j.Logger;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月22日 下午1:39:21 
 * 类说明 
 */
public class SMSUtil {
	public static Logger logger = Logger.getLogger(SMSUtil.class);
	/**
	 * 根据消息内容获取支付id 解析格式为 TS 20140504122114265
	 * @param smstxt
	 * @return 20140504122114265 为订单号
	 */
	public static String getPayId(String smstxt){
		String pay_id=null;
		try{
			String sp[]=smstxt.split(" ");
			boolean b=false;
			int c =0;
			for (int i = 0; i < sp.length; i++) {
				if(sp[i].equals("TS")){
					b=true;
					c=(i+1);
					break;
				}
			}
			pay_id=sp[c].toLowerCase();
		}catch(Exception e){
			logger.error("smstxt error = "+smstxt, e);
		}
		return pay_id;
	}
}
