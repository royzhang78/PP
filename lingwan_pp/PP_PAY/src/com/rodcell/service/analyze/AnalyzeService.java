package com.rodcell.service.analyze;

import javax.servlet.http.HttpServletRequest;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月30日 下午3:41:33 
 * 类说明 
 */
public interface AnalyzeService {
	public String service(String text,HttpServletRequest request);
}
