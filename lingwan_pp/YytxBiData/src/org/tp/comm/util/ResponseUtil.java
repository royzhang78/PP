package org.tp.comm.util;

import javax.servlet.http.HttpServletResponse;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月21日 下午3:31:33 
 * 类说明 
 */
public class ResponseUtil {
	public static void setStatus(Object response,int status){
		((HttpServletResponse)response).setStatus(status);
	}
}
